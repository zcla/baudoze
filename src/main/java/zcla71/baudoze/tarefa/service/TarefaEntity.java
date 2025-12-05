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
import zcla71.baudoze.model.Validacao;
import zcla71.baudoze.model.ValidacaoException;

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

	private void validaDados(ValidacaoException result) {
		if (this.nome == null || this.nome.trim().length() < 1) {
			result.getValidacoes().add(new Validacao("Informe o nome.", "nome"));
		}
		if (this.peso == null) {
			result.getValidacoes().add(new Validacao("Informe o peso.", "peso"));
		}
	}

	private void validaIdDeveSerNulo(ValidacaoException result) {
		if (this.id != null) {
			result.getValidacoes().add(new Validacao("O ID da tarefa não deve ser informado."));
		}
	}

	@SuppressWarnings("null")
	private void validaIdInvalido(TarefaRepository tarefaRepository, ValidacaoException result) {
		if (this.id == null) {
			result.getValidacoes().add(new Validacao("ID da tarefa não informado."));
		} else {
			if (!tarefaRepository.existsById(this.id)) {
				result.getValidacoes().add(new Validacao("Tarefa não encontrada."));
			}
		}
	}

	public ValidacaoException validaAlterar(TarefaRepository tarefaRepository) {
		ValidacaoException result = new ValidacaoException();

		// TODO Não pode ser mãe de si mesma
		validaIdInvalido(tarefaRepository, result);
		validaDados(result);

		return result;
	}

	public ValidacaoException validaExcluir(TarefaRepository tarefaRepository) {
		ValidacaoException result = new ValidacaoException();

		List<TarefaEntity> filhos = tarefaRepository.findByIdMae(this.id);
		if (filhos.size() > 0) {
			result.getValidacoes().add(new Validacao("Tarefa não pode ser excluída porque tem subtarefas."));
		}

		validaIdInvalido(tarefaRepository, result);

		return result;
	}

	public ValidacaoException validaIncluir(TarefaRepository tarefaRepository) {
		ValidacaoException result = new ValidacaoException();

		validaIdDeveSerNulo(result);
		validaDados(result);

		return result;
	}
}
