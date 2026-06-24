package zcla71.baudoze.tarefa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.tarefa.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>, TarefaRepositoryCustom {
}
