package zcla71.baudoze.tarefa.service;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository {
	// ----- Métodos que existem em org.springframework.data.repository.CrudRepository<TarefaEntity, Long>
	<S extends TarefaEntity> S save(S entity);

	// <S extends TarefaEntity> Iterable<S> saveAll(Iterable<S> entities);

	Optional<TarefaEntity> findById(Long id);

	boolean existsById(Long id);

	Iterable<TarefaEntity> findAll();

	// Iterable<TarefaEntity> findAllById(Iterable<Long> ids);

	// long count();

	// void deleteById(Long id);

	void delete(TarefaEntity entity);

	// void deleteAllById(Iterable<? extends Long> ids);

	// void deleteAll(Iterable<? extends TarefaEntity> entities);

	// void deleteAll();

	// ----- Outros métodos

	public List<TarefaEntity> findByIdMae(Long idMae);
}
