package zcla71.baudoze.view.tarefa;

import lombok.Data;
import zcla71.baudoze.entity.Tarefa;

@Data
public class ListaTarefa {
    private Long id;
    private String nome;
    private String notas;
    private Boolean cumprida;
    private Integer indent;

    public ListaTarefa(Tarefa tarefa, Integer indent) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.notas = tarefa.getNotas();
        this.cumprida = tarefa.getCumprida();
        this.indent = indent;
    }
}
