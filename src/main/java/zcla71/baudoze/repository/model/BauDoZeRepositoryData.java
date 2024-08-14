package zcla71.baudoze.repository.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import zcla71.baudoze.repository.BauDoZeRepository;
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
    private List<Obra> obras;
    private List<Pessoa> pessoas;
    private List<Editora> editoras;
    private List<Colecao> colecoes;
    private List<Etiqueta> etiquetas;
    private List<Atividade> atividades;

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

    public List<Atividade> listaAtividadePorLivro(Livro livro) {
        return this.atividades.stream().filter(a -> a.getIdLivro().equals(livro.getId())).toList();
    }

    public void excluiAtividade(Atividade atividade) throws RepositoryException {
        if (!this.atividades.contains(atividade)) {
            throw new RepositoryException("Tentativa de excluir uma atividade que não existe");
        }
        this.atividades.remove(atividade);
    }

    public Atividade incluiAtividade(Atividade atividade) {
        atividade.setId(BauDoZeRepository.generateId(atividade));
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
        if (!this.getLivros().contains(livro)) {
            throw new RepositoryException("Tentativa de alterar um livro que não existe");
        }
        this.livros.replaceAll(l -> l.getId().equals(livro.getId()) ? livro : l);
    }

    public Livro buscaLivroPorId(String id) {
        return this.livros.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
    }

    public void excluiLivro(Livro livro) throws RepositoryException {
        if (!this.getLivros().contains(livro)) {
            throw new RepositoryException("Tentativa de alterar um livro que não existe");
        }
        this.livros.remove(livro);
    }

    public Livro incluiLivro(Livro livro) {
        livro.setId(BauDoZeRepository.generateId(livro));
        this.livros.add(livro);
        return livro;
    }

    // pessoas

    public Pessoa buscaPessoaPorId(String id) {
        return this.pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    // obras

    public void alteraObra(Obra obra) throws RepositoryException {
        if (!this.getObras().contains(obra)) {
            throw new RepositoryException("Tentativa de alterar uma obra que não existe");
        }
        this.obras.replaceAll(o -> o.getId().equals(obra.getId()) ? obra : o);
    }

    public Obra buscaObraPorId(String id) {
        return this.obras.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    public void excluiObra(Obra obra) throws RepositoryException {
        if (!this.getObras().contains(obra)) {
            throw new RepositoryException("Tentativa de alterar uma obra que não existe");
        }
        this.obras.remove(obra);
    }

    public Obra incluiObra(Obra obra) {
        obra.setId(BauDoZeRepository.generateId(obra));
        this.obras.add(obra);
        return obra;
    }
}
