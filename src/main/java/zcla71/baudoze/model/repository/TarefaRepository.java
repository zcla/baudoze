package zcla71.baudoze.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zcla71.baudoze.model.entity.Tarefa;

@Repository
public interface TarefaRepository extends CrudRepository<Tarefa, Long> {
}
