package zcla71.baudoze.tarefa.view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.tarefa.view.entity.TarefaLista;
import zcla71.baudoze.tarefa.view.repository.TarefaListaRepository;

@Service
public class TarefaViewService {
	// TarefaLista

	@Autowired
	private TarefaListaRepository tarefaListaRepository;

	public List<TarefaLista> listaTarefas() {
		return this.tarefaListaRepository.findAll();
	}
}
