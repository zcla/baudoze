package zcla71.baudoze.tarefa.service;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zcla71.baudoze.model.validation.Validation;
import zcla71.baudoze.model.validation.ValidationException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarefa")
public class TarefaEntity {
	public static TarefaEntity nova() {
		TarefaEntity result = new TarefaEntity();
		result.setNome("Nova tarefa");
		result.setPeso(0);
		result.setCumprida(false);
		return result;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String notas;
	private Long idMae;
	private Integer peso;
	private Boolean cumprida;

	private void validaDados(ValidationException result) {
		if (this.nome == null || this.nome.trim().length() < 1) {
			result.getValidations().add(new Validation("Informe o nome.", "nome"));
		}
		if (this.peso == null) {
			result.getValidations().add(new Validation("Informe o peso.", "peso"));
		}
	}

	private void validaIdDeveSerNulo(ValidationException result) {
		if (this.id != null) {
			result.getValidations().add(new Validation("O ID da tarefa não deve ser informado."));
		}
	}

	@SuppressWarnings("null")
	private void validaIdInvalido(TarefaRepository tarefaRepository, ValidationException result) {
		if (this.id == null) {
			result.getValidations().add(new Validation("ID da tarefa não informado."));
		} else {
			if (!tarefaRepository.existsById(this.id)) {
				result.getValidations().add(new Validation("Tarefa não encontrada."));
			}
		}
	}

	public ValidationException validaAlterar(TarefaRepository tarefaRepository) {
		ValidationException result = new ValidationException();

		validaIdInvalido(tarefaRepository, result);
		validaDados(result);

		return result;
	}

	public ValidationException validaExcluir(TarefaRepository tarefaRepository) {
		ValidationException result = new ValidationException();

		List<TarefaEntity> filhos = tarefaRepository.findByIdMae(this.id);
		if (filhos.size() > 0) {
			result.getValidations().add(new Validation("Tarefa não pode ser excluída porque tem subtarefas."));
		}

		validaIdInvalido(tarefaRepository, result);

		return result;
	}

	public ValidationException validaIncluir(TarefaRepository tarefaRepository) {
		ValidationException result = new ValidationException();

		validaIdDeveSerNulo(result);
		validaDados(result);

		return result;
	}
}
