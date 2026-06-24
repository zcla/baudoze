package zcla71.baudoze.tarefa.view.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.tarefa.view.entity.TarefaLista;

public interface TarefaListaRepository extends JpaRepository<TarefaLista, Long> {
}
