package zcla71.baudoze.anotacoes.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
// Modelo inspirado no curso "Desafio Memorize com a Escrita", do Rodrigo Gurgel.
public class Anotacao {
	private Long id;
	private LocalDateTime dataInclusao;
	private LocalDateTime dataUltimaAlteracao;
	private AnotacaoTipo tipo;
	private Referencia referencia;
	private String texto;
	private String formato;
	private LocalDateTime dataUltimaRevisao; // TODO PEnsar melhor; talvez seja melhor uma lista.
}
