package zcla71.baudoze.tarefa.view;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TarefaListaViewTarefa {
	private Long id;
	private String nome;
	private String notas;
	private Boolean cumprida;
	private Integer indent;
}
