package zcla71.baudoze.tarefa.service;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Profile("db")
@Repository
public interface TarefaDatabaseRepository extends TarefaRepository, CrudRepository<TarefaEntity, Long> {
}
