package zcla71.baudoze.tarefa.view.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import zcla71.baudoze.tarefa.view.entity.TarefaLista;

public interface TarefaListaRepository extends Repository<TarefaLista, Long> {
	public List<TarefaLista> findAll();
}
