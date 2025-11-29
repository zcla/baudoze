package zcla71.baudoze.tarefa.view;

import lombok.Data;

@Data
public class TarefaEditarViewTarefaMae {
    private String value;
    private String text;

    public TarefaEditarViewTarefaMae() {
		this.value = "";
        this.text = "";
    }

	public TarefaEditarViewTarefaMae(TarefaListaViewTarefa tarefa) {
		this.value = tarefa.getId().toString();
        this.text = tarefa.getNome();
	}
}
