package zcla71.repository.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Deprecated // Já foi usado com sucesso, mas não utilizo mais. Mantenho aqui caso seja necessário no futuro, pois funciona perfeitamente.
public abstract class JsonRepository<T, ID> {
    public interface FilePathProvider {
        Path get();
    }

    private final ObjectMapper mapper;
    private final FilePathProvider filePathProvider;

    private final ConcurrentHashMap<Path, StoreState<T, ID>> states = new ConcurrentHashMap<>();

    private boolean autoIdLongEnabled = false;

    protected JsonRepository(ObjectMapper mapper, FilePathProvider filePathProvider) {
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.filePathProvider = Objects.requireNonNull(filePathProvider, "filePathProvider");
    }

    /* =========================
     * Pontos de extensão
     * ========================= */

    protected abstract ID getId(T entity);
    protected abstract void setId(T entity, ID id);
    protected abstract TypeReference<List<T>> listTypeRef();

    /** Habilita auto-id para IDs do tipo Long. */
    protected final void enableAutoIdLong() {
        this.autoIdLongEnabled = true;
    }

    /** Permite customizar a criação/normalização do path. */
    protected Path normalizePath(Path p) {
        return p.toAbsolutePath().normalize();
    }

    /* =========================
     * API estilo CrudRepository
     * ========================= */

    public T save(T entity) {
        Objects.requireNonNull(entity, "entity");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            ID id = getId(entity);

            if (autoIdLongEnabled && id == null) {
                @SuppressWarnings("unchecked")
                ID newId = (ID) nextLongIdUnderWriteLock(state);
                setId(entity, newId);
                id = newId;
            }

            if (id == null) {
                throw new IllegalStateException("Entity id is null (auto-id disabled or not supported).");
            }

            state.store.put(id, entity);
            persistUnderWriteLock(state);
            return entity;
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public List<T> saveAll(Iterable<T> entities) {
        Objects.requireNonNull(entities, "entities");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            List<T> saved = new ArrayList<>();
            for (T e : entities) {
                if (e == null) continue;

                ID id = getId(e);
                if (autoIdLongEnabled && id == null) {
                    @SuppressWarnings("unchecked")
                    ID newId = (ID) nextLongIdUnderWriteLock(state);
                    setId(e, newId);
                    id = newId;
                }
                if (id == null) {
                    throw new IllegalStateException("Entity id is null (auto-id disabled or not supported).");
                }

                state.store.put(id, e);
                saved.add(e);
            }

            persistUnderWriteLock(state);
            return saved;
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public Optional<T> findById(ID id) {
        Objects.requireNonNull(id, "id");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.readLock().lock();
        try {
            return Optional.ofNullable(state.store.get(id));
        } finally {
            state.rwLock.readLock().unlock();
        }
    }

    public boolean existsById(ID id) {
        Objects.requireNonNull(id, "id");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.readLock().lock();
        try {
            return state.store.containsKey(id);
        } finally {
            state.rwLock.readLock().unlock();
        }
    }

    public List<T> findAll() {
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.readLock().lock();
        try {
            return new ArrayList<>(state.store.values());
        } finally {
            state.rwLock.readLock().unlock();
        }
    }

    public List<T> findAllById(Iterable<ID> ids) {
        Objects.requireNonNull(ids, "ids");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.readLock().lock();
        try {
            List<T> result = new ArrayList<>();
            for (ID id : ids) {
                if (id == null) continue;
                T e = state.store.get(id);
                if (e != null) result.add(e);
            }
            return result;
        } finally {
            state.rwLock.readLock().unlock();
        }
    }

    public long count() {
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.readLock().lock();
        try {
            return state.store.size();
        } finally {
            state.rwLock.readLock().unlock();
        }
    }

    public void deleteById(ID id) {
        Objects.requireNonNull(id, "id");
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            if (state.store.remove(id) != null) {
                persistUnderWriteLock(state);
            }
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public void delete(T entity) {
        Objects.requireNonNull(entity, "entity");

        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            ID id = getId(entity);
            if (id == null) {
                return; // nada a remover
            }

            if (state.store.remove(id) != null) {
                persistUnderWriteLock(state);
            }
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public void deleteAllById(Iterable<? extends ID> ids) {
        Objects.requireNonNull(ids, "ids");

        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            boolean changed = false;

            for (ID id : ids) {
                if (id == null) continue;
                if (state.store.remove(id) != null) {
                    changed = true;
                }
            }

            if (changed) {
                persistUnderWriteLock(state);
            }
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public void deleteAll(Iterable<? extends T> entities) {
        Objects.requireNonNull(entities, "entities");

        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            boolean changed = false;

            for (T entity : entities) {
                if (entity == null) continue;

                ID id = getId(entity);
                if (id == null) continue;

                if (state.store.remove(id) != null) {
                    changed = true;
                }
            }

            if (changed) {
                persistUnderWriteLock(state);
            }
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    public void deleteAll() {
        StoreState<T, ID> state = stateForCurrentPath();
        ensureLoaded(state);

        state.rwLock.writeLock().lock();
        try {
            if (!state.store.isEmpty()) {
                state.store.clear();
                // reset seq para 0 (opcional). Eu reseto para manter coerência.
                state.longIdSeq.set(0L);
                persistUnderWriteLock(state);
            }
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    /* =========================
     * Internals: estado por Path
     * ========================= */

    private StoreState<T, ID> stateForCurrentPath() {
        Path raw = filePathProvider.get();
        if (raw == null) throw new IllegalStateException("filePathProvider returned null");
        Path path = normalizePath(raw);

        // estado isolado por arquivo
        return states.computeIfAbsent(path, p -> new StoreState<>(p));
    }

    private void ensureLoaded(StoreState<T, ID> state) {
        if (state.loaded) return;

        state.rwLock.writeLock().lock();
        try {
            if (state.loaded) return;
            loadUnderWriteLock(state);
            state.loaded = true;
        } finally {
            state.rwLock.writeLock().unlock();
        }
    }

    private void loadUnderWriteLock(StoreState<T, ID> state) {
        createDirectories(state.path);

        if (!Files.exists(state.path)) {
            state.store = new LinkedHashMap<>();
            initIdSequenceFromStoreUnderWriteLock(state);
            return;
        }

        try (InputStream in = Files.newInputStream(state.path, StandardOpenOption.READ)) {
            List<T> list = mapper.readValue(in, listTypeRef());

            LinkedHashMap<ID, T> map = new LinkedHashMap<>();
            if (list != null) {
                for (T e : list) {
                    if (e == null) continue;
                    ID id = getId(e);
                    if (id == null) {
                        throw new IllegalStateException("Found entity with null id in JSON file: " + state.path);
                    }
                    map.put(id, e);
                }
            }
            state.store = map;
            initIdSequenceFromStoreUnderWriteLock(state);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON repository file: " + state.path, e);
        }
    }

    private void initIdSequenceFromStoreUnderWriteLock(StoreState<T, ID> state) {
        if (!autoIdLongEnabled) return;

        long max = 0L;
        for (ID id : state.store.keySet()) {
            if (id instanceof Long l && l > max) max = l;
        }
        state.longIdSeq.set(max);
    }

    private Long nextLongIdUnderWriteLock(StoreState<T, ID> state) {
        // Pré-condição: writeLock adquirido
        return state.longIdSeq.incrementAndGet();
    }

    private void persistUnderWriteLock(StoreState<T, ID> state) {
        // Pré-condição: writeLock adquirido
        try {
            createDirectories(state.path);

            Path parent = state.path.getParent();
            // temp no mesmo diretório para maximizar chance de ATOMIC_MOVE
            Path tmp = Files.createTempFile(parent != null ? parent : Paths.get("."), "repo-", ".tmp");

            try (OutputStream out = Files.newOutputStream(tmp, StandardOpenOption.TRUNCATE_EXISTING)) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(out, state.store.values());
            }

            try {
                Files.move(tmp, state.path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(tmp, state.path, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to persist JSON repository file: " + state.path, e);
        }
    }

    private void createDirectories(Path file) {
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directories for: " + file, e);
        }
    }

    /**
     * Estado por arquivo (por Path).
     */
    private static final class StoreState<T, ID> {
        final Path path;
        final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);

        volatile boolean loaded = false;
        Map<ID, T> store = new LinkedHashMap<>();

        // sequência por arquivo (quando auto-id Long está habilitado)
        final AtomicLong longIdSeq = new AtomicLong(0L);

        StoreState(Path path) {
            this.path = path;
        }
    }
}
