package zcla71.baudoze.biblia.service;

import java.util.List;

import lombok.Data;

@Data
public class Livro {
	private String id;
	private String sigla;
    private String nome;
    private List<Capitulo> capitulos;
}
