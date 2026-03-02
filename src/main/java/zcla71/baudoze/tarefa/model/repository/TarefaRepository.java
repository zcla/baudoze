package zcla71.baudoze.tarefa.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import zcla71.baudoze.tarefa.model.entity.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, Long> {
	// public List<Tarefa> findByIdMae(Long idMae);
}
