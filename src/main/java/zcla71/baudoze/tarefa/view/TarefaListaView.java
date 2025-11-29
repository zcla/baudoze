package zcla71.baudoze.tarefa.view;

import java.util.List;

import lombok.Data;

@Data
public class TarefaListaView {
	private List<TarefaListaViewTarefa> tarefas;

	public TarefaListaView(List<TarefaListaViewTarefa> tarefas) {
		this.tarefas = tarefas;
	}
}
