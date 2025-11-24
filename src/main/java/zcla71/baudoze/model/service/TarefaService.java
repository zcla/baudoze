package zcla71.baudoze.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import zcla71.baudoze.model.TarefaModel;
import zcla71.baudoze.model.repository.TarefaRepository;

@Service
@Primary // Para ter precedência sobre TarefaServiceApi, que é só um exemplo.
public class TarefaService {
	public static enum Contexto {
		MOSTRAR("mostrar", "Tarefa"),
		INCLUIR("incluir", "Incluir tarefa"),
		EXCLUIR("excluir", "Excluir tarefa"),
		ALTERAR("alterar", "Alterar tarefa");

		public String id;
		public String titulo;

		Contexto(String id, String titulo) {
			this.id = id;
			this.titulo = titulo;
		}
	}

	@Autowired
	private TarefaRepository tarefaRepository;

	public Optional<TarefaModel> getById(Long id) {
		return this.tarefaRepository.findById(id);
	}

	public List<TarefaModel> listar() {
		List<TarefaModel> result = new ArrayList<>();
		this.tarefaRepository.findAll().forEach(result::add);
		return result;
	}

	public void incluir(TarefaModel tarefa) throws ValidationException {
		ValidationException validation = tarefa.validate(Contexto.INCLUIR, this.tarefaRepository);
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.save(tarefa);
	}

	public void alterar(TarefaModel tarefa) throws ValidationException {
		ValidationException validation = tarefa.validate(Contexto.ALTERAR, this.tarefaRepository);
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.save(tarefa);
	}

	public void excluir(TarefaModel tarefa) throws ValidationException {
		ValidationException validation = tarefa.validate(Contexto.EXCLUIR, this.tarefaRepository);
		if (!validation.getValidations().isEmpty()) {
			throw validation;
		}

		this.tarefaRepository.delete(tarefa);
	}
}
