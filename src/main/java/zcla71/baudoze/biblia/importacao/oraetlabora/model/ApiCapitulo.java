package zcla71.baudoze.biblia.importacao.oraetlabora.model;

import java.util.List;

import lombok.Data;

@Data
public class ApiCapitulo {
	private String fonte;
	private String livro;
	private String abbrev;
	private Integer capitulo;
	List<ApiVersiculo> versiculos;
}
