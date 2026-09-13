package zcla71.baudoze.estudos.biblia.minhas.dto;

import java.util.List;

import lombok.Data;

@Data
public class TabelaLinha {
	private String codigo;
	private Integer bibliaRowspan = 1;
	private String nome;
	private String nomeVolume;
	private List<String> editoras;
	private String edicao;
	private String publicacao;
	private List<String> idiomas;
	private String isbn;
	private String formato;
}
