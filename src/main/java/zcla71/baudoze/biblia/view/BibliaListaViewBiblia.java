package zcla71.baudoze.biblia.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BibliaListaViewBiblia {
	private Long id;
	private String nome;
	private String idioma;
	private Integer livros;
}
