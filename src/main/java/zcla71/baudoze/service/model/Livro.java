package zcla71.baudoze.service.model;

import java.util.Collection;

import lombok.Data;

@Data
public class Livro {
    private String id;
    private String titulo;
    // TODO cascade: excluiu livro -> não afeta obra
    // TODO cascade: excluiu obra -> se houver outras obras, retira do livro; se não, não permite (confirmar)
    private Collection<String> idsObras;
    private String isbn13;
    private String isbn10;
    // TODO cascade: excluiu livro -> não afeta editora
    // TODO cascade: excluiu editora -> se houver outras editoras, retira do livro; se não, não permite (confirmar)
    private Collection<String> idsEditoras;
    private Integer ano;
    // TODO cascade: excluiu livro -> não afeta etiqueta
    // TODO cascade: excluiu etiqueta -> retira do livro (confirmar)
    private Collection<String> idsEtiquetas;
    private Integer paginas;
    private String edicao;
}
