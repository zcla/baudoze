package zcla71.baudoze.tarefa.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends CrudRepository<TarefaEntity, Long> {
	public List<TarefaEntity> findByIdMae(Long idMae);
}
