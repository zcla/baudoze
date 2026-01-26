package zcla71.baudoze.tarefa.service;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import zcla71.baudoze.BauDoZeProperties;
import zcla71.baudoze.tarefa.service.chatgpt.UserScopedPathResolver;
import zcla71.repository.json.JsonRepository;
import zcla71.utils.OidcUtils;

@SuppressWarnings("unchecked")
@Profile("json")
@Repository
public class TarefaJsonRepository extends JsonRepository<TarefaEntity, Long> implements TarefaRepository {
    public TarefaJsonRepository(ObjectMapper mapper, BauDoZeProperties props) {
        super(mapper, () -> {
            return new UserScopedPathResolver(props.getStoragePath()).tarefasFileFor(OidcUtils.getLoggedUser());
        });

        enableAutoIdLong();
    }

    @Override
    protected Long getId(TarefaEntity entity) {
        return entity.getId();
    }

    @Override
    protected void setId(TarefaEntity entity, Long id) {
        entity.setId(id);
    }

    @Override
    protected TypeReference<List<TarefaEntity>> listTypeRef() {
        return new TypeReference<>() {};
    }

    @Override
    public List<TarefaEntity> findByIdMae(Long idMae) {
        return this.findAll().stream().filter(t -> idMae.equals(t.getIdMae())).toList();
    }
}
