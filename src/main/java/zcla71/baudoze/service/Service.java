package zcla71.baudoze.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.repository.Repository;
import zcla71.baudoze.repository.model.BauDoZeRepositoryData;
import zcla71.baudoze.repository.model.RepositoryException;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
import zcla71.baudoze.service.model.Atividade.AtividadeTipo;

public class Service {
    private static final String JSON_FILE_LOCATION = "data/baudoze.json"; // TODO Jogar pro application.properties
    private static Service instance;

    public static Service getInstance() throws StreamReadException, DatabindException, IOException {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Repository<BauDoZeRepositoryData> repository;

    private Service() throws StreamReadException, DatabindException, IOException {
        this.repository = new Repository<BauDoZeRepositoryData>(BauDoZeRepositoryData.class, JSON_FILE_LOCATION, true);
    }

    // Atividades

    public Atividade buscaUltimaAtividadeDoLivro(String idLivro) {
        return this.listaAtividades().stream()
                .filter(a -> a.getIdLivro().equals(idLivro)).sorted((a1, a2) -> {
                    int result = a2.getData().compareTo(a1.getData());
                    if (result == 0) {
                        result = a2.getTipo().getOrdem().compareTo(a1.getTipo().getOrdem());
                    }
                    return result;
                })
                .findFirst()
                .orElse(null);
    }

    public Collection<Atividade> listaAtividades() {
        return this.repository.getData().getAtividades();
    }

    // Coleções

    public Collection<Colecao> listaColecoes() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getColecoes();
    }

    // Editoras

    public Collection<Editora> listaEditoras() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getEditoras();
    }

    public Editora buscaEditoraPorId(String id) {
        return this.repository.getData().buscaEditoraPorId(id);
    }

    public Editora buscaEditoraPorNome(String nome) {
        return this.repository.getData().buscaEditoraPorNome(nome);
    }

    // Etiquetas

    public Etiqueta buscaEtiquetaPorId(String id) {
        return this.repository.getData().buscaEtiquetaPorId(id);
    }

    public Collection<Etiqueta> listaEtiquetas() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getEtiquetas();
    }

    // Livros

    public void alteraLivro(Livro livro) throws RepositoryException, StreamWriteException, DatabindException, IOException {
        this.repository.beginTransaction();
        this.repository.getData().alteraLivro(livro);
        this.repository.commitTransaction();
    }

    public Livro buscaLivroPorId(String id) {
        return this.repository.getData().buscaLivroPorId(id);
    }

    public void excluiLivro(Livro livro) throws StreamWriteException, DatabindException, IOException, RepositoryException {
        this.repository.beginTransaction();
        // TODO Excluir as atividades e outras dependências
        this.repository.getData().excluiLivro(livro);
        this.repository.commitTransaction();
    }

    public Livro incluiLivro(Livro livro) throws StreamWriteException, DatabindException, IOException {
        this.repository.beginTransaction();
        Livro result = this.repository.getData().incluiLivro(livro);
        Atividade atividade = new Atividade(); // TODO Pensar: isso não deveria estar no BauDoZe (controller)? Caso positivo, o controle de transação também.
        atividade.setId(Repository.generateId());
        atividade.setTipo(AtividadeTipo.CADASTRO);
        atividade.setData(LocalDate.now());
        atividade.setIdLivro(result.getId());
        this.repository.getData().incluiAtividade(atividade);
        this.repository.commitTransaction();
        return result;
    }

    public Collection<Livro> listaLivros() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getLivros();
    }

    public Collection<Livro> listaLivrosDaObra(String idObra) throws StreamReadException, DatabindException, IOException {
        return this.listaLivros().stream().filter(l -> l.getIdsObras().contains(idObra)).toList();
    }

    // Obras

    public Collection<Obra> listaObras() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getObras();
    }

    public Obra buscaObraPorId(String id) {
        return this.repository.getData().buscaObraPorId(id);
    }

    // Pessoas

    public Collection<Pessoa> listaPessoas() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getPessoas();
    }

    public Pessoa buscaPessoaPorId(String id) throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().buscaPessoaPorId(id);
    }
}
