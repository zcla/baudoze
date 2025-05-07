package zcla71.baudoze.view.model;

import lombok.Data;
import zcla71.baudoze.model.entity.Tarefa;

@Data
public class TarefaViewModelIncluirTarefaMae {
    private Long id;
    private String nome;

    public TarefaViewModelIncluirTarefaMae(Tarefa tarefa, Integer indent) {
        this.id = tarefa.getId();
        this.nome = "&nbsp;".repeat(4).repeat(indent) + tarefa.getNome();
    }
}
