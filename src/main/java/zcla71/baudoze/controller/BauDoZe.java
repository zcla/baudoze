package zcla71.baudoze.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import zcla71.baudoze.service.Service;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
import zcla71.baudoze.view.atividades.AtividadesPaginaAtividade;
import zcla71.baudoze.view.colecoes.ColecoesPagina;
import zcla71.baudoze.view.colecoes.ColecoesPaginaColecao;
import zcla71.baudoze.view.editoras.EditorasPagina;
import zcla71.baudoze.view.editoras.EditorasPaginaEditora;
import zcla71.baudoze.view.etiquetas.EtiquetasPagina;
import zcla71.baudoze.view.etiquetas.EtiquetasPaginaEtiqueta;
import zcla71.baudoze.view.livro.LivroForm;
import zcla71.baudoze.view.livro.LivroPagina;
import zcla71.baudoze.view.livros.LivrosPagina;
import zcla71.baudoze.view.obra.ObraForm;
import zcla71.baudoze.view.obra.ObraPagina;
import zcla71.baudoze.view.obras.ObrasPagina;
import zcla71.baudoze.view.pessoas.PessoasPagina;
import zcla71.baudoze.view.pessoas.PessoasPaginaPessoa;
import zcla71.baudoze.view.stats.StatsPagina;

public class BauDoZe {
    private static BauDoZe instance;

    // TODO Essa classe parece ser um intermediário desnecessário

    public static BauDoZe getInstance() {
        if (instance == null) {
            instance = new BauDoZe();
        }
        return instance;
    }

    private LivroController livro;
    private ObraController obra;

    private BauDoZe() {
        super();
        this.livro = LivroController.getInstance();
        this.obra = ObraController.getInstance();
    }

    // atividades

    public Collection<AtividadesPaginaAtividade> getAtividades() throws Exception {
        Service service = Service.getInstance();
        Collection<Atividade> atividades = service.listaAtividades();
        List<AtividadesPaginaAtividade> result = new ArrayList<>();
        for (Atividade atividade : atividades) {
            AtividadesPaginaAtividade apAtividade = new AtividadesPaginaAtividade();

            apAtividade.setData(atividade.getData());
            apAtividade.setTipo(atividade.getTipo().getTexto());
            apAtividade.setLivro(service.buscaLivroPorId(atividade.getIdLivro()).getTitulo());

            result.add(apAtividade);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<AtividadesPaginaAtividade>() {
            @Override
            public int compare(AtividadesPaginaAtividade a1, AtividadesPaginaAtividade a2) {
                return a2.getData().compareTo(a1.getData());
            }
        });

        return result;
    }

    // colecoes

    public ColecoesPagina getColecoes() throws Exception {
        Service service = Service.getInstance();
        Collection<Colecao> colecoes = service.listaColecoes();
        ColecoesPagina result = new ColecoesPagina();
        for (Colecao colecao : colecoes) {
            ColecoesPaginaColecao cpColecao = new ColecoesPaginaColecao();

            cpColecao.setNome(colecao.getNome());

            Collection<String> idsLivros = colecao.getIdsLivros();
            cpColecao.setQtdLivros(idsLivros.size());
            cpColecao.setPaginas(0);
            for (String idLivro : idsLivros) {
                Livro livro = service.buscaLivroPorId(idLivro);
                if (livro.getPaginas() != null) {
                    cpColecao.setPaginas(cpColecao.getPaginas() + livro.getPaginas());
                }
            }
    
            result.add(cpColecao);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<ColecoesPaginaColecao>() {
            @Override
            public int compare(ColecoesPaginaColecao c1, ColecoesPaginaColecao c2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(c1.getNome(), c2.getNome());
            }
        });

        return result;
    }

    // editoras

    public EditorasPagina getEditoras() throws Exception {
        Service service = Service.getInstance();
        Collection<Editora> editoras = service.listaEditoras();
        EditorasPagina result = new EditorasPagina();
        for (Editora editora : editoras) {
            EditorasPaginaEditora epEditora = new EditorasPaginaEditora();

            epEditora.setNome(editora.getNome());
            epEditora.setQtdLivros(service.listaLivros().stream().filter(l -> l.getIdsEditoras().contains(editora.getId())).toArray().length);

            result.add(epEditora);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<EditorasPaginaEditora>() {
            @Override
            public int compare(EditorasPaginaEditora o1, EditorasPaginaEditora o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getNome(), o2.getNome());
            }
        });

        return result;
    }

    // etiquetas

    public EtiquetasPagina getEtiquetas() throws Exception {
        Service service = Service.getInstance();
        Collection<Etiqueta> etiquetas = service.listaEtiquetas();
        EtiquetasPagina result = new EtiquetasPagina();
        for (Etiqueta etiqueta : etiquetas) {
            EtiquetasPaginaEtiqueta epEtiqueta = new EtiquetasPaginaEtiqueta();

            epEtiqueta.setNome(etiqueta.getNome());
            epEtiqueta.setQtdLivros(service.listaLivros().stream().filter(l -> l.getIdsEtiquetas().contains(etiqueta.getId())).toArray().length);
    
            result.add(epEtiqueta);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<EtiquetasPaginaEtiqueta>() {
            @Override
            public int compare(EtiquetasPaginaEtiqueta o1, EtiquetasPaginaEtiqueta o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getNome(), o2.getNome());
            }
        });

        return result;
    }

    // livros

    public void deleteLivro(String id) throws Exception {
        livro.deleteLivro(id);
    }

    public LivroPagina getLivro(String id) throws Exception {
        return livro.getLivro(id);
    }

    public LivrosPagina getLivros() throws Exception {
        return livro.getLivros();
    }

    public LivroPagina setLivro(String id, LivroForm form) throws Exception {
        return livro.setLivro(id, form);
    }

    // obras

    public void deleteObra(String id) throws Exception {
        obra.deleteObra(id);
    }

    public ObraPagina getObra(String id) throws Exception {
        return obra.getObra(id);
    }

    public ObrasPagina getObras() throws Exception {
        return obra.getObras();
    }

    public ObraPagina setObra(String id, ObraForm form) throws Exception {
        return obra.setObra(id, form);
    }

    // pessoas

    public PessoasPagina getPessoas() throws Exception {
        Service service = Service.getInstance();
        Collection<Pessoa> pessoas = service.listaPessoas();
        PessoasPagina result = new PessoasPagina();
        for (Pessoa pessoa : pessoas) {
            PessoasPaginaPessoa ppPessoa = new PessoasPaginaPessoa();

            ppPessoa.setNome(pessoa.getNome());

            Collection<Obra> obrasAutor = service.listaObras().stream().filter(o -> o.getIdsAutores().contains(pessoa.getId())).toList();
            ppPessoa.setQtdObras(obrasAutor.size());

            Collection<Livro> livros = service.listaLivros();
            ppPessoa.setQtdLivros(0);
            for (Livro livro : livros) {
                ppPessoa.setQtdLivros(ppPessoa.getQtdLivros() + obrasAutor.stream().filter(o -> livro.getIdsObras().contains(o.getId()) && o.getIdsAutores().contains(pessoa.getId())).toArray().length);
            }

            result.add(ppPessoa);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<PessoasPaginaPessoa>() {
            @Override
            public int compare(PessoasPaginaPessoa p1, PessoasPaginaPessoa p2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(p1.getNome(), p2.getNome());
            }
        });

        return result;
    }

    // stats

    public StatsPagina getStats() throws Exception {
        Service service = Service.getInstance();
        StatsPagina result = new StatsPagina(service.listaObras().size(),
                service.listaLivros().size(),
                service.listaPessoas().size(),
                service.listaEditoras().size(),
                service.listaColecoes().size(),
                service.listaEtiquetas().size(),
                service.listaAtividades().size());
        return result;
    }
}
