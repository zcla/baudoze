package zcla71.baudoze.view.obra;

import lombok.Data;

@Data
public class ObraPaginaLivro {
    private String id;
    private String titulo;
    private String isbn;
    private String editoraPrincipal;
    private Integer qtdOutrasEditoras;
    private Integer ano;
    private Integer paginas;
    private String edicao;
    private String status;
}
