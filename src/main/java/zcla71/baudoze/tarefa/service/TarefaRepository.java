package zcla71.baudoze.tarefa.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends CrudRepository<TarefaEntity, Long> {
}
