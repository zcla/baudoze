package zcla71.baudoze.tarefa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import zcla71.baudoze.tarefa.dto.TarefaLista;

public interface TarefaListaRepository extends JpaRepository<TarefaLista, Long> {
}
