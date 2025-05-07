package zcla71.baudoze.view.model;

import lombok.Data;
import zcla71.baudoze.model.entity.Tarefa;

@Data
public class TarefaViewModelListarTarefa {
    private Long id;
    private String nome;
    private String notas;
    private Boolean cumprida;
    private Integer indent;

    public TarefaViewModelListarTarefa(Tarefa tarefa, Integer indent) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.notas = tarefa.getNotas();
        this.cumprida = tarefa.getCumprida();
        this.indent = indent;
    }
}
