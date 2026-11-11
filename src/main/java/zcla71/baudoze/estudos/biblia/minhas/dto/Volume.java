package zcla71.baudoze.estudos.biblia.minhas.dto;

import java.util.List;

import lombok.Data;

@Data
public class Volume {
	private String nome;
	private List<String> editoras;
	private String edicao;
	private String publicacao;
	private List<String> idiomas;
	private String isbn;
	private String formato;
	private PrimeiraEdicao primeiraEdicao;
	private List<Aprovacao> aprovacoes;
	private List<Livro> livros;

	public String getFormatoDisplay() {
		return switch (formato) {
			case "fisico" -> "Física";
			case "kindle" -> "Kindle";
			case "pdf" -> "PDF";
			default -> formato;
		};
	}
}
