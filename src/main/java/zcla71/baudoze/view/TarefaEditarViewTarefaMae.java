package zcla71.baudoze.view;

import lombok.Data;

@Data
public class TarefaEditarViewTarefaMae {
    private String value;
    private String text;

    public TarefaEditarViewTarefaMae() {
		this.value = "";
        this.text = "";
    }

    // TODO Dependência indevida de outra tela, mas apenas de forma utilitária. Para eliminar essa dependência teria que criar uma classe intermediária. Me parece preciosismo demais. Será?
	public TarefaEditarViewTarefaMae(TarefaListarViewTarefa tarefa) {
		this.value = tarefa.getId().toString();
        this.text = tarefa.getNome();
	}
}
