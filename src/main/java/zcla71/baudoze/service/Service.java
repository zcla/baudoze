package zcla71.baudoze.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.repository.BauDoZeRepository;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Atividade.AtividadeTipo;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
import zcla71.repository.JsonRepository;

public class Service {
    private static Service instance;

    public static Service getInstance() throws Exception {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private BauDoZeRepository repository;

    private Service() throws Exception {
        this.repository = BauDoZeRepository.getInstance(true);
    }

    // Atividades

    public Atividade buscaUltimaAtividadeDoLivro(Livro livro) {
        return listaAtividadesDoLivro(livro).stream()
                .findFirst()
                .orElse(null);
    }

    public List<Atividade> listaAtividades() {
        return this.repository.getData().getAtividades();
    }

    public List<Atividade> listaAtividadesDoLivro(Livro livro) {
        return this.listaAtividades().stream()
                .filter(a -> a.getIdLivro().equals(livro.getId())).sorted((a1, a2) -> {
                    int result = a2.getData().compareTo(a1.getData());
                    if (result == 0) {
                        result = a2.getTipo().getOrdem().compareTo(a1.getTipo().getOrdem());
                    }
                    return result;
                })
                .toList();
    }

    // Coleções

    public List<Colecao> listaColecoes() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getColecoes();
    }

    public Colecao buscaColecaoDoLivro(Livro livro) throws StreamReadException, DatabindException, IOException {
        return this.listaColecoes().stream().filter(c -> c.getIdsLivros().contains(livro.getId())).findFirst().orElse(null);
    }

    // Editoras

    public List<Editora> listaEditoras() throws StreamReadException, DatabindException, IOException {
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

    public List<Etiqueta> listaEtiquetas() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getEtiquetas();
    }

    // Livros

    public void alteraLivro(Livro livro) throws Exception {
        this.repository.beginTransaction();
        this.repository.getData().alteraLivro(livro);
        this.repository.commitTransaction();
    }

    public Livro buscaLivroPorId(String id) {
        return this.repository.getData().buscaLivroPorId(id);
    }

    public void excluiLivro(Livro livro) throws Exception {
        this.repository.beginTransaction();
        List<Atividade> atividades = this.repository.getData().listaAtividadePorLivro(livro);
        for (Atividade atividade : atividades) {
            this.repository.getData().excluiAtividade(atividade);
        }
        this.repository.getData().excluiLivro(livro);
        this.repository.commitTransaction();
    }

    public Livro incluiLivro(Livro livro) throws Exception {
        this.repository.beginTransaction();
        Livro result = this.repository.getData().incluiLivro(livro);
        Atividade atividade = new Atividade(); // TODO Pensar: isso não deveria estar no BauDoZe (controller)? Caso positivo, o controle de transação também.
        atividade.setTipo(AtividadeTipo.CADASTRO);
        atividade.setData(LocalDate.now());
        atividade.setIdLivro(result.getId());
        atividade.setId(JsonRepository.generateId(atividade));
        this.repository.getData().incluiAtividade(atividade);
        this.repository.commitTransaction();
        return result;
    }

    public List<Livro> listaLivros() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getLivros();
    }

    public List<Livro> listaLivrosDaObra(Obra obra) throws StreamReadException, DatabindException, IOException {
        return this.listaLivros().stream().filter(l -> l.getIdsObras().contains(obra.getId())).toList();
    }

    // Obras

    public void alteraObra(Obra obra) throws Exception {
        this.repository.beginTransaction();
        this.repository.getData().alteraObra(obra);
        this.repository.commitTransaction();
    }

    public void excluiObra(Obra obra) throws Exception {
        this.repository.beginTransaction();
        this.repository.getData().excluiObra(obra);
        this.repository.commitTransaction();
    }

    public List<Obra> listaObras() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getObras();
    }

    public Obra incluiObra(Obra obra) throws Exception {
        this.repository.beginTransaction();
        Obra result = this.repository.getData().incluiObra(obra);
        this.repository.commitTransaction();
        return result;
    }

    public Obra buscaObraPorId(String id) {
        return this.repository.getData().buscaObraPorId(id);
    }

    // Pessoas

    public List<Pessoa> listaPessoas() throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().getPessoas();
    }

    public Pessoa buscaPessoaPorId(String id) throws StreamReadException, DatabindException, IOException {
        return this.repository.getData().buscaPessoaPorId(id);
    }
}
