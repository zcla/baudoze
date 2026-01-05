package zcla71.baudoze.planodevida.service;

import java.time.LocalTime;
import java.util.Collection;

import lombok.Data;

@Data
public abstract class Pratica {
	private String nome;
	private String descricao;
	private LocalTime inicio;
	private Integer duracaoMinutos;
	private Collection<Execucao> execucoes;
}
