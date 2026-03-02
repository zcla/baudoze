package zcla71.baudoze.tarefa.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.common.model.ValidacaoException;
import zcla71.baudoze.tarefa.model.entity.Tarefa;
import zcla71.baudoze.tarefa.model.repository.TarefaRepository;

@Service
public class TarefaService {
	// @Autowired
	// private TarefaRepository tarefaRepository;

	// public void alterar(Tarefa tarefa) throws ValidacaoException {
	// 	ValidacaoException validation = tarefa.validaAlterar(this.tarefaRepository);
	// 	if (!validation.getValidacoes().isEmpty()) {
	// 		throw validation;
	// 	}

	// 	this.tarefaRepository.save(tarefa);
	// }

	// public Tarefa buscar(Long id) {
	// 	return this.tarefaRepository.findById(id).orElse(null);
	// }

	// public void excluir(Tarefa tarefa) throws ValidacaoException {
	// 	ValidacaoException validation = tarefa.validaExcluir(this.tarefaRepository);
	// 	if (!validation.getValidacoes().isEmpty()) {
	// 		throw validation;
	// 	}

	// 	this.tarefaRepository.delete(tarefa);
	// }

	// public void incluir(Tarefa tarefa) throws ValidacaoException {
	// 	ValidacaoException validation = tarefa.validaIncluir(this.tarefaRepository);
	// 	if (!validation.getValidacoes().isEmpty()) {
	// 		throw validation;
	// 	}

	// 	this.tarefaRepository.save(tarefa);
	// }

	// public List<Tarefa> listar() {
	// 	List<Tarefa> result = new ArrayList<>();
	// 	this.tarefaRepository.findAll().forEach(result::add);
	// 	return result;
	// }
}
