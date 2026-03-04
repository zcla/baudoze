package zcla71.baudoze.tarefa.model.repository;

import org.springframework.data.repository.CrudRepository;

import zcla71.baudoze.tarefa.model.entity.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, Long> {
}
