package zcla71.baudoze.repository.model;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import zcla71.baudoze.repository.Repository;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;

@Data
public class BauDoZeRepositoryData {
    private ArrayList<Livro> livros;
    private Collection<Obra> obras;
    private Collection<Pessoa> pessoas;
    private Collection<Editora> editoras;
    private Collection<Colecao> colecoes;
    private Collection<Etiqueta> etiquetas;
    private Collection<Atividade> atividades;

    public BauDoZeRepositoryData() {
        this.livros = new ArrayList<>();
        this.obras = new ArrayList<>();
        this.pessoas = new ArrayList<>();
        this.editoras = new ArrayList<>();
        this.colecoes = new ArrayList<>();
        this.etiquetas = new ArrayList<>();
        this.atividades = new ArrayList<>();
    }

    // atividades

    public Atividade incluiAtividade(Atividade atividade) {
        atividade.setId(Repository.generateId());
        this.atividades.add(atividade);
        return atividade;
    }

    // colecoes

    public Colecao buscaColecaoPorNome(String nome) {
        return this.colecoes.stream().filter(c -> c.getNome().equals(nome)).findFirst().orElse(null);
    }

    // editoras

    public Editora buscaEditoraPorId(String id) {
        return this.editoras.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public Editora buscaEditoraPorNome(String nome) {
        return this.editoras.stream().filter(e -> e.getNome().equals(nome)).findFirst().orElse(null);
    }

    // etiquetas

    public Etiqueta buscaEtiquetaPorId(String id) {
        return this.etiquetas.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    public Etiqueta buscaEtiquetaPorNome(String nome) {
        return this.etiquetas.stream().filter(e -> e.getNome().equals(nome)).findFirst().orElse(null);
    }

    // livros

    public void alteraLivro(Livro livro) throws RepositoryException {
        if (buscaLivroPorId(livro.getId()) == null) {
            throw new RepositoryException("Tentativa de alterar um livro que não existe");
        }
        this.livros.replaceAll(l -> l.getId().equals(livro.getId()) ? livro : l);
    }

    public Livro buscaLivroPorId(String id) {
        return this.livros.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    public void excluiLivro(Livro livro) throws RepositoryException {
        if (buscaLivroPorId(livro.getId()) == null) {
            throw new RepositoryException("Tentativa de alterar um livro que não existe");
        }
        this.livros.remove(livro);
    }

    public Livro incluiLivro(Livro livro) {
        livro.setId(Repository.generateId());
        this.livros.add(livro);
        return livro;
    }

    // pessoas

    public Pessoa buscaPessoaPorId(String id) {
        return this.pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    // obras

    public Obra buscaObraPorId(String id) {
        return this.obras.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }
}
