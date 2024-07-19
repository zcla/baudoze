package zcla71.baudoze.view.livro;

import java.util.List;

import lombok.Data;

@Data
public class LivroForm {
    private String titulo;
    private List<String> obras;
    private String isbn13;
    private String isbn10;
    private Integer ano;
    private Integer paginas;
    private String edicao;
    private List<String> editoras;
    private List<String> etiquetas;
}
