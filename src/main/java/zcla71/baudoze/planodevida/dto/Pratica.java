package zcla71.baudoze.planodevida.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public abstract class Pratica {
	// TODO Cada prática tem que ter ligação com uma ou mais identidades
	private String nome;
	private String descricao;
	// TODO Repensar daqui pra frente; quero uma lista de "templates", cuja execução será registrada em outro lugar; porém, já tem que ter algo de temporal por aqui (frequência, por exemplo)
	private LocalTime inicio;
	private Integer duracaoMinutos;
	private List<Execucao> execucoes;
}
