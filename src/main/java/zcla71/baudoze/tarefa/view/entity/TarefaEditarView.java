package zcla71.baudoze.tarefa.view.entity;

import java.util.List;

import lombok.Data;

@Data
@Deprecated
public class TarefaEditarView {
	private TarefaEditarViewTarefa tarefa;
	private List<TarefaEditarViewTarefaMae> tarefasMae;

	public TarefaEditarView(TarefaEditarViewTarefa tarefa, List<TarefaEditarViewTarefaMae> tarefasMae) {
		super();
		this.tarefa = tarefa;
		this.tarefasMae = tarefasMae;
	}
}
