package zcla71.baudoze.view.livros;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import lombok.Data;
import zcla71.baudoze.service.Service;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;

@Data
public class LivrosPaginaLivro {
    private String id;
    private String titulo;
    private Integer qtdObras;
    private String autorPrincipal;
    private Integer qtdOutrosAutores;
    private String isbn;
    private String editoraPrincipal;
    private Integer qtdOutrasEditoras;
    private Integer ano;
    private String colecao;
    private String etiquetas;
    private Integer paginas;
    private String edicao;
    private String status;

    public LivrosPaginaLivro(Livro livro) throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();

        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.qtdObras = livro.getIdsObras().size();

        this.autorPrincipal = null;
        this.qtdOutrosAutores = 0;
        for (String idObra : livro.getIdsObras()) {
            Obra obra = service.buscaObraPorId(idObra);
            for (String idPessoa : obra.getIdsAutores()) {
                Pessoa autor = service.buscaPessoaPorId(idPessoa);
                if (autorPrincipal == null) {
                    this.autorPrincipal = autor.getNome();
                } else {
                    this.qtdOutrosAutores++;
                }
            }
        }

        this.isbn = (livro.getIsbn13() != null ? livro.getIsbn13() : livro.getIsbn10());

        this.qtdOutrasEditoras = 0;
        for (String idEditora : livro.getIdsEditoras()) {
            Editora editora = service.buscaEditoraPorId(idEditora);
            if (editoraPrincipal == null) {
                this.editoraPrincipal = editora.getNome();
            } else {
                this.qtdOutrasEditoras++;
            }
        }

        this.ano = livro.getAno();

        Colecao colecao = service.listaColecoes().stream().filter(c -> c.getIdsLivros().contains(livro.getId())).findFirst().orElse(null);
        if (colecao != null) {
            this.colecao = colecao.getNome();
        }

        Collection<Etiqueta> etiquetas = service.listaEtiquetas().stream().filter(e -> livro.getIdsEtiquetas().contains(e.getId())).toList();
        this.etiquetas = "";
        for (Etiqueta etiqueta : etiquetas) {
            this.etiquetas += etiqueta.getNome() + ", ";
        }
        if (this.etiquetas.length() > 0) {
            this.etiquetas = this.etiquetas.substring(0, this.etiquetas.length() - 2);
        }

        this.paginas = livro.getPaginas();
        this.edicao = livro.getEdicao();
        this.status = service.buscaUltimaAtividadeDoLivro(livro.getId()).getTipo().getStatus();
    }
}