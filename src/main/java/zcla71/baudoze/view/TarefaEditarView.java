package zcla71.baudoze.view;

import java.util.List;

import lombok.Data;
import zcla71.baudoze.model.TarefaModel;

@Data
public class TarefaEditarView {
	private TarefaModel tarefa;
	private List<TarefaEditarViewTarefaMae> tarefasMae;

	public TarefaEditarView(TarefaModel tarefa, List<TarefaEditarViewTarefaMae> tarefasMae) {
		super();
		this.tarefa = tarefa;
		this.tarefasMae = tarefasMae;
	}
}
