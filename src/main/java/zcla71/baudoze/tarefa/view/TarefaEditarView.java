package zcla71.baudoze.tarefa.view;

import java.util.List;

import lombok.Data;

@Data
public class TarefaEditarView {
	private TarefaEditarViewTarefa tarefa;
	private List<TarefaEditarViewTarefaMae> tarefasMae;

	public TarefaEditarView(TarefaEditarViewTarefa tarefa, List<TarefaEditarViewTarefaMae> tarefasMae) {
		super();
		this.tarefa = tarefa;
		this.tarefasMae = tarefasMae;
	}
}
