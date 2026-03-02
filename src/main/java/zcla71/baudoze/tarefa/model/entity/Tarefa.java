package zcla71.baudoze.tarefa.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zcla71.baudoze.common.model.Validacao;
import zcla71.baudoze.common.model.ValidacaoException;
import zcla71.baudoze.tarefa.model.repository.TarefaRepository;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tarefa {
	// public static Tarefa nova() {
	// 	Tarefa result = new Tarefa();
	// 	result.setNome("Nova tarefa");
	// 	result.setPeso(0);
	// 	result.setCumprida(false);
	// 	return result;
	// }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long authUserId;
	private String titulo;
	private String descricao;
	private Long idMae;
	private Integer ordem;
	private Boolean cumprida;

	// private void validaDados(ValidacaoException result) {
	// 	if (this.nome == null || this.nome.trim().length() < 1) {
	// 		result.getValidacoes().add(new Validacao("Informe o nome.", "nome"));
	// 	}
	// 	if (this.peso == null) {
	// 		result.getValidacoes().add(new Validacao("Informe o peso.", "peso"));
	// 	}
	// 	if ((this.idMae != null) && this.idMae.equals(this.id)) {
	// 		result.getValidacoes().add(new Validacao("A tarefa não pode ser filha dela mesma.", "idMae"));
	// 	}
	// }

	// private void validaIdDeveSerNulo(ValidacaoException result) {
	// 	if (this.id != null) {
	// 		result.getValidacoes().add(new Validacao("O ID da tarefa não deve ser informado."));
	// 	}
	// }

	// private void validaIdInvalido(TarefaRepository tarefaRepository, ValidacaoException result) {
	// 	if (this.id == null) {
	// 		result.getValidacoes().add(new Validacao("ID da tarefa não informado."));
	// 	} else {
	// 		if (!tarefaRepository.existsById(this.id)) {
	// 			result.getValidacoes().add(new Validacao("Tarefa não encontrada."));
	// 		}
	// 	}
	// }

	// public ValidacaoException validaAlterar(TarefaRepository tarefaRepository) {
	// 	ValidacaoException result = new ValidacaoException();

	// 	validaIdInvalido(tarefaRepository, result);
	// 	validaDados(result);

	// 	return result;
	// }

	// public ValidacaoException validaExcluir(TarefaRepository tarefaRepository) {
	// 	ValidacaoException result = new ValidacaoException();

	// 	List<Tarefa> filhos = tarefaRepository.findByIdMae(this.id);
	// 	if (filhos.size() > 0) {
	// 		result.getValidacoes().add(new Validacao("Tarefa não pode ser excluída porque tem subtarefas."));
	// 	}

	// 	validaIdInvalido(tarefaRepository, result);

	// 	return result;
	// }

	// public ValidacaoException validaIncluir(TarefaRepository tarefaRepository) {
	// 	ValidacaoException result = new ValidacaoException();

	// 	validaIdDeveSerNulo(result);
	// 	validaDados(result);

	// 	return result;
	// }
}
