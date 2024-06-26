package zcla71.baudoze.service.model;

import java.util.Collection;

import lombok.Data;

@Data
public class Livro {
    private String id;
    private String titulo;
    private Collection<String> idsObras;
    private String isbn13;
    private String isbn10;
    private Collection<String> idsEditoras;
    private Integer ano;
    private Collection<String> idsEtiquetas;
    private Integer paginas;
    private String edicao;
}
