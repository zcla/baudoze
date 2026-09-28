package zcla71.baudoze.estudos.biblia.minhas.dto;

import java.util.List;

import lombok.Data;

@Data
public class CitacaoGrupo {
	private String nome;
	private List<Citacao> citacoes;
}
