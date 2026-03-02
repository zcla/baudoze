package zcla71.baudoze.tarefa.view.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class TarefaEditarViewTarefa {
	private Long id;
	private String nome;
	private String notas;
	private Long idMae;
	private Integer peso;
	private Boolean cumprida;
}
