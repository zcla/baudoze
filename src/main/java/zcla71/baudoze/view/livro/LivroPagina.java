package zcla71.baudoze.view.livro;

import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.baudoze.view.Pagina;

@Data
@EqualsAndHashCode(callSuper = true)
public class LivroPagina extends Pagina {
    // form
    private String id;
    private String titulo;
    private Collection<String> obras;
    private String isbn13;
    private String isbn10;
    private Integer ano;
    private Integer paginas;
    private String edicao;
    private Collection<String> editoras;
    private Collection<String> etiquetas;
    // form - selects
    private List<LivroPaginaObra> listaObras; // TODO Map<String, String>
    private List<LivroPaginaEditora> listaEditoras; // TODO Map<String, String>
    private List<LivroPaginaEtiqueta> listaEtiquetas; // TODO Map<String, String>
    // detalhes
    private String colecao;
    private List<String> atividades;
}
