package zcla71.baudoze.tarefa.view;

import lombok.Data;

@Data
public class TarefaEditarViewTarefaMae {
    private String value;
    private String text;
    private Integer indent;

    public TarefaEditarViewTarefaMae() {
		this.value = "";
        this.text = "";
        this.indent = 0;
    }

	public TarefaEditarViewTarefaMae(TarefaListaViewTarefa tarefa) {
		this.value = tarefa.getId().toString();
        this.text = tarefa.getNome();
        this.indent = tarefa.getIndent();
	}
}
