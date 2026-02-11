package zcla71.baudoze.biblia.service;

import java.util.List;

import lombok.Data;

@Data
public class Biblia {
	private String id;
	private String codigo;
	private String idioma;
    private List<Livro> livros;
}
