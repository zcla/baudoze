package zcla71.baudoze.anotacoes.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// Sugerida pelo Rodrigo Gurgel no curso "Desafio Memorize com a Escrita".
// Diário de aprendizagem: ao final do dia responder às perguntas:
// - O que eu aprendi hoje?
// - O que ainda está confuso ou incompleto?
// - O que vou revisar?
public class DiarioDeAprendizagem extends Anotacao {
	private String queAprendi;
	private String queEstaConfusoOuIncompleto;
	private String queVouRevisar;

	@Override 
	public void setReferencia(Referencia referencia) {
		if (!(referencia instanceof ReferenciaData)) {
			throw new RuntimeException("O diário de aprendizagem deve necessariamente fazer referência a uma data.");
		}
		super.setReferencia(referencia);
	}
}
