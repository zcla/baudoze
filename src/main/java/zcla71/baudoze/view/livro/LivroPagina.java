package zcla71.baudoze.view.livro;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.baudoze.view.Pagina;

@Data
@EqualsAndHashCode(callSuper = true)
public class LivroPagina extends Pagina {
    // form
    // TODO Devia ser LivroForm
    private String id;
    private String titulo;
    private List<String> obras;
    private String isbn13;
    private String isbn10;
    private Integer ano;
    private Integer paginas;
    private String edicao;
    private List<String> editoras;
    private List<String> etiquetas;
    // form - selects
    private List<LivroPaginaObra> listaObras;
    private List<LivroPaginaEditora> listaEditoras;
    private List<LivroPaginaEtiqueta> listaEtiquetas;
    // detalhes
    private String colecao;
    private List<String> atividades;
}
