package zcla71.baudoze.view.obra;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.baudoze.view.Pagina;

@Data
@EqualsAndHashCode(callSuper = true)
public class ObraPagina extends Pagina {
    // form
    // TODO Devia ser ObraForm
    private String id;
    private String titulo;
    private List<String> autores;
    // form - selects
    private List<ObraPaginaAutor> listaAutores;
    // detalhes
    private List<ObraPaginaLivro> livros;
}
