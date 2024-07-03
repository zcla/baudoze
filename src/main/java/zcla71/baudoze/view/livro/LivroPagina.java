package zcla71.baudoze.view.livro;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.baudoze.service.Service;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.view.Pagina;

@Data
@EqualsAndHashCode(callSuper = true)
public class LivroPagina extends Pagina {
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
    private List<LivroPaginaObra> listaObras;

    @Override
    public boolean dadosValidos() {
        // TODO Isso tem que estar em BauDoZe.setLivro()
        boolean result = true;

        if (estaVazio(titulo)) {
            adicionaMensagemDeErro("titulo", "Informe o título");
            result = false;
        }

        if (estaVazio(obras)) {
            adicionaMensagemDeErro("obras", "Informe ao menos uma obra");
            result = false;
        }

        return result;
    }

    public Livro toLivro() throws StreamReadException, DatabindException, IOException {
        // TODO Isso tem que estar em BauDoZe.setLivro()
        Service service = Service.getInstance();

        Livro result = null;
        if (this.id == null) {
            result = new Livro();
        } else {
            result = service.buscaLivroPorId(this.id);
        }

        result.setTitulo(this.titulo);
        result.setIdsObras(this.obras);

        return result;
    }
}
