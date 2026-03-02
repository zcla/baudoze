package zcla71.baudoze.tarefa.view.entity;

import java.util.List;

import lombok.Data;

@Data
@Deprecated
public class TarefaListaView {
	private List<TarefaListaViewTarefa> tarefas;

	public TarefaListaView(List<TarefaListaViewTarefa> tarefas) {
		this.tarefas = tarefas;
	}
}
