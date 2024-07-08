package zcla71.baudoze.view.livro;

import java.util.Collection;

import lombok.Data;

@Data
public class LivroForm {
    private String titulo;
    private Collection<String> obras;
    private String isbn13;
    private String isbn10;
    private Integer ano;
    private Integer paginas;
    private String edicao;
    private Collection<String> editoras;
    private Collection<String> etiquetas;
}
