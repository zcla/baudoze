package zcla71.baudoze.tarefa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.tarefa.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>, TarefaRepositoryCustom {
}
