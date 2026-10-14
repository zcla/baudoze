package zcla71.baudoze.anotacoes.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
// Modelo inspirado no curso "Desafio Memorize com a Escrita", do Rodrigo Gurgel.
public abstract class Anotacao {
	private Long id;
	private LocalDateTime dataInclusao; // Automática
	private LocalDateTime dataUltimaAlteracao; // Automática
	private Referencia referencia;
	private List<Revisao> revisoes;
}
