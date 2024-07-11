package zcla71.baudoze.service.model;

import java.util.Collection;

import lombok.Data;

@Data
public class Livro {
    private String id;
    private String titulo;
    private Collection<String> idsObras; // TODO cascade
    private String isbn13;
    private String isbn10;
    private Collection<String> idsEditoras; // TODO cascade
    private Integer ano;
    private Collection<String> idsEtiquetas; // TODO cascade
    private Integer paginas;
    private String edicao;
}
