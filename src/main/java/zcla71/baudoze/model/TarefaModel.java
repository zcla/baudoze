package zcla71.baudoze.model;

import java.util.Comparator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import zcla71.baudoze.model.repository.TarefaRepository;
import zcla71.baudoze.model.service.TarefaService.Contexto;
import zcla71.baudoze.model.service.ValidationException;
import zcla71.baudoze.model.validation.Validation;
import zcla71.utils.Utils;

@Data
@Entity
@Table(name = "tarefa")
public class TarefaModel {
	public static TarefaModel nova() {
		TarefaModel result = new TarefaModel();
        result.setNome("Nova tarefa");
        result.setPeso(0);
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

	public static class TarefaModelComparator implements Comparator<TarefaModel> {
		@Override
		public int compare(TarefaModel t1, TarefaModel t2) {
			if (Utils.trataNull(t1.getCumprida()) && !Utils.trataNull(t2.getCumprida())) {
				return 1;
			}
			if (!Utils.trataNull(t1.getCumprida()) && Utils.trataNull(t2.getCumprida())) {
				return -1;
			}
			if (Utils.trataNull(t1.getPeso()) < Utils.trataNull(t2.getPeso())) {
				return 1;
			}
			if (Utils.trataNull(t1.getPeso()) > Utils.trataNull(t2.getPeso())) {
				return -1;
			}
			return t1.getNome().compareToIgnoreCase(t2.getNome());
		}
	}

	public ValidationException validate(Contexto contexto, TarefaRepository tarefaRepository) {
		ValidationException result = new ValidationException();

		// Validação do id
		switch (contexto) {
			case Contexto.MOSTRAR:
			case Contexto.ALTERAR:
			case Contexto.EXCLUIR:
				if (this.id == null) {
					result.getValidations().add(new Validation("ID da tarefa não informado."));
				} else {
					if (!tarefaRepository.existsById(this.id)) {
						result.getValidations().add(new Validation("Tarefa não encontrada."));
					}
				}
				break;

			case Contexto.INCLUIR:
				if (this.id != null) {
					result.getValidations().add(new Validation("ID da tarefa não deve ser informado."));
				}
				break;

			default:
				throw new RuntimeException("Contexto desconhecido.");
		}

		// Validação dos dados
		switch (contexto) {
			case Contexto.INCLUIR:
			case Contexto.ALTERAR:
				if (this.nome == null || this.nome.trim().length() < 1) {
					result.getValidations().add(new Validation("Informe o nome.", "nome"));
				}
				if (this.peso == null) {
					result.getValidations().add(new Validation("Informe o peso.", "peso"));
				}
				break;

			default:
				// Sem validação
		}

		return result;
	}
}
