package zcla71.baudoze.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zcla71.baudoze.model.TarefaModel;

@Repository
public interface TarefaRepository extends CrudRepository<TarefaModel, Long> {
}
