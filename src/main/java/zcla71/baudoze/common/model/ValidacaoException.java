package zcla71.baudoze.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ValidacaoException extends Exception {
	private Validacoes validacoes;

	public ValidacaoException() {
		super();
		this.validacoes = new Validacoes();
	}
}
