package zcla71.baudoze.tarefa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zcla71.baudoze.model.ValidacaoException;

@Service
public class TarefaService {
	@Autowired
	private TarefaRepository tarefaRepository;

	public void alterar(TarefaEntity tarefa) throws ValidacaoException {
		ValidacaoException validation = tarefa.validaAlterar(this.tarefaRepository);
		if (!validation.getValidacoes().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.save(tarefa);
	}

	@SuppressWarnings("null")
	public TarefaEntity buscar(Long id) {
		return this.tarefaRepository.findById(id).orElse(null);
	}

	public void excluir(TarefaEntity tarefa) throws ValidacaoException {
		ValidacaoException validation = tarefa.validaExcluir(this.tarefaRepository);
		if (!validation.getValidacoes().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.delete(tarefa);
	}

	public void incluir(TarefaEntity tarefa) throws ValidacaoException {
		ValidacaoException validation = tarefa.validaIncluir(this.tarefaRepository);
		if (!validation.getValidacoes().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.save(tarefa);
	}

	public List<TarefaEntity> listar() {
		List<TarefaEntity> result = new ArrayList<>();
		this.tarefaRepository.findAll().forEach(result::add);
		return result;
	}
}
