package zcla71.baudoze.view.model;

import java.util.List;

import lombok.Data;
import zcla71.baudoze.model.entity.Tarefa;

@Data
public class TarefaViewModelIncluir {
    private Tarefa tarefa;
    private List<TarefaViewModelIncluirTarefaMae> tarefasMae;
}
