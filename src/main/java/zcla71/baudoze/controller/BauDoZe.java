package zcla71.baudoze.controller;

import java.io.IOException;
import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.repository.model.RepositoryException;
import zcla71.baudoze.service.Service;
import zcla71.baudoze.service.model.Atividade;
import zcla71.baudoze.service.model.Colecao;
import zcla71.baudoze.service.model.Editora;
import zcla71.baudoze.service.model.Etiqueta;
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
import zcla71.baudoze.view.Pagina.Estado;
import zcla71.baudoze.view.atividades.AtividadesPaginaAtividade;
import zcla71.baudoze.view.colecoes.ColecoesPagina;
import zcla71.baudoze.view.colecoes.ColecoesPaginaColecao;
import zcla71.baudoze.view.editoras.EditorasPagina;
import zcla71.baudoze.view.editoras.EditorasPaginaEditora;
import zcla71.baudoze.view.etiquetas.EtiquetasPagina;
import zcla71.baudoze.view.etiquetas.EtiquetasPaginaEtiqueta;
import zcla71.baudoze.view.livro.LivroForm;
import zcla71.baudoze.view.livro.LivroPagina;
import zcla71.baudoze.view.livro.LivroPaginaObra;
import zcla71.baudoze.view.livros.LivrosPagina;
import zcla71.baudoze.view.livros.LivrosPaginaLivro;
import zcla71.baudoze.view.obras.ObrasPagina;
import zcla71.baudoze.view.obras.ObrasPaginaObra;
import zcla71.baudoze.view.pessoas.PessoasPagina;
import zcla71.baudoze.view.pessoas.PessoasPaginaPessoa;
import zcla71.baudoze.view.stats.StatsPagina;

public class BauDoZe {
    private static BauDoZe instance;

    // TODO Fazer testes unitários

    public static BauDoZe getInstance() {
        if (instance == null) {
            instance = new BauDoZe();
        }
        return instance;
    }

    // atividades

    public Collection<AtividadesPaginaAtividade> getAtividades() throws StreamReadException, DatabindException, IOException {
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

    public ColecoesPagina getColecoes() throws StreamReadException, DatabindException, IOException {
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

    public EditorasPagina getEditoras() throws StreamReadException, DatabindException, IOException {
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

    public EtiquetasPagina getEtiquetas() throws StreamReadException, DatabindException, IOException {
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

    public LivroPagina getLivro(String id) throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();

        Livro livro = service.buscaLivroPorId(id);
        LivroPagina result = new LivroPagina();
        result.setId(livro.getId());
        result.setTitulo(livro.getTitulo());
        result.setObras(new ArrayList<>());
        for (String idObra : livro.getIdsObras()) {
            result.getObras().add(idObra);
        }
        result.setIsbn13(livro.getIsbn13());
        result.setIsbn10(livro.getIsbn10());
        result.setAno(livro.getAno());
        result.setPaginas(livro.getPaginas());
        result.setEdicao(livro.getEdicao());
        result.setEditoras(new ArrayList<>());
        for (String idEditora : livro.getIdsEditoras()) {
            result.getEditoras().add(service.buscaEditoraPorId(idEditora).getNome());
        }
        result.setEtiquetas(new ArrayList<>());
        for (String idEtiqueta : livro.getIdsEtiquetas()) {
            result.getEtiquetas().add(service.buscaEtiquetaPorId(idEtiqueta).getNome());
        }

        Collection<Obra> obras = service.listaObras();
        result.setListaObras(new ArrayList<>());
        for (Obra obra : obras) {
            LivroPaginaObra lpObra = new LivroPaginaObra();
            lpObra.setId(obra.getId());
            lpObra.setNome(obra.getTitulo());
            if (obra.getIdsAutores().size() > 0) {
                Pessoa autor = service.buscaPessoaPorId(obra.getIdsAutores().iterator().next());
                lpObra.setNome(lpObra.getNome() + " (" + autor.getNome() + ")");
            }
            result.getListaObras().add(lpObra);
        }
        Collections.sort(result.getListaObras(), new Comparator<LivroPaginaObra>() {
            @Override
            public int compare(LivroPaginaObra o1, LivroPaginaObra o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getNome(), o2.getNome());
            }
        });

        return result;
    }

    public LivroPagina setLivro(String id, LivroForm form) throws StreamReadException, DatabindException, IOException, RepositoryException {
        LivroPagina result = getLivro(id);
        result.setTitulo(form.getTitulo());
        result.setObras(form.getObras());
        result.setIsbn13(form.getIsbn13());
        result.setIsbn10(form.getIsbn10());
        if (result.getIsbn10() != null) {
            result.setIsbn10(result.getIsbn10().toUpperCase());
        }
        result.setAno(form.getAno());
        result.setPaginas(form.getPaginas());
        result.setEdicao(form.getEdicao());

        try {
            Livro livro = validaLivro(id, result);
            result.setEstadoPagina(null);
            Service service = Service.getInstance();
            if (livro.getId() == null) {
                service.incluiLivro(livro);
            } else {
                service.alteraLivro(livro);
            }
        } catch (ValidationException e) {
            for (ValidationException ve : e.getExceptions()) {
                result.getExceptionMap().put(ve.getCampo(), ve);
            }
            result.setEstadoPagina(Estado.UPDATE);
        }

        return result;
    }

    private Livro validaLivro(String id, LivroPagina livro) throws ValidationException, StreamReadException, DatabindException, IOException {
        List<ValidationException> exceptions = new ArrayList<>();
        
        Livro result = null;
        if (id == null) {
            result = new Livro();
        } else {
            Service service = Service.getInstance();
            result = service.buscaLivroPorId(id);
        }

        // titulo
        try {
            ValidationUtils.checkNotEmpty(livro.getTitulo(), "Informe o título", "titulo");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // obras
        try {
            ValidationUtils.checkNotEmpty(livro.getObras(), "Informe ao menos uma obra", "obras");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // isbn13
        try {
            ValidationUtils.checkRegex(livro.getIsbn13(), "^[0-9]{13}$", "Código ISBN-13 inválido", "isbn13");
            ValidationUtils.checkIsbn13(livro.getIsbn13(), "Código ISBN-13 inválido (dígito verificador)", "isbn13");
            // TODO Só na inclusão: verificar se o código já está cadastrado
            // TODO Colocar um checkbox para ignorar a verificação de código já cadastrado
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // isbn10
        try {
            ValidationUtils.checkRegex(livro.getIsbn10(), "^[0-9]{9}[0-9X]$", "Código ISBN-10 inválido", "isbn10");
            ValidationUtils.checkIsbn10(livro.getIsbn10(), "Código ISBN-10 inválido (dígito verificador)", "isbn10");
            // TODO Só na inclusão: verificar se o código já está cadastrado
            // TODO Colocar um checkbox para ignorar a verificação de código já cadastrado
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // ano
        try {
            ValidationUtils.checkLessThanOrEqual(livro.getAno(), LocalDate.now().getYear(), "Ano inválido", "ano");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // paginas
        try {
            ValidationUtils.checkNotEmpty(livro.getPaginas(), "Informe o número de páginas", "paginas");
            ValidationUtils.checkMoreThanOrEqual(livro.getPaginas(), 1, "Número de páginas inválido", "paginas");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // edicao (nada)

        if (exceptions.size() > 0) {
            throw new ValidationException(exceptions);
        }

        result.setTitulo(livro.getTitulo());
        result.setIdsObras(livro.getObras());
        result.setIsbn13(livro.getIsbn13());
        result.setIsbn10(livro.getIsbn10());
        result.setAno(livro.getAno());
        result.setPaginas(livro.getPaginas());
        result.setEdicao(livro.getEdicao());

        return result;
    }

    public LivrosPagina getLivros() throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();
        Collection<Livro> livros = service.listaLivros();
        LivrosPagina result = new LivrosPagina();
        for (Livro livro : livros) {
            LivrosPaginaLivro lpLivro = new LivrosPaginaLivro();

            lpLivro.setId(livro.getId());
            lpLivro.setTitulo(livro.getTitulo());
            lpLivro.setQtdObras(livro.getIdsObras().size());

            lpLivro.setAutorPrincipal(null);
            lpLivro.setQtdOutrosAutores(0);
            for (String idObra : livro.getIdsObras()) {
                Obra obra = service.buscaObraPorId(idObra);
                for (String idPessoa : obra.getIdsAutores()) {
                    Pessoa autor = service.buscaPessoaPorId(idPessoa);
                    if (lpLivro.getAutorPrincipal() == null) {
                        lpLivro.setAutorPrincipal(autor.getNome());
                    } else {
                        lpLivro.setQtdOutrosAutores(lpLivro.getQtdOutrosAutores() + 1);
                    }
                }
            }

            lpLivro.setIsbn(livro.getIsbn13() != null ? livro.getIsbn13() : livro.getIsbn10());

            lpLivro.setEditoraPrincipal(null);
            lpLivro.setQtdOutrasEditoras(0);
            for (String idEditora : livro.getIdsEditoras()) {
                Editora editora = service.buscaEditoraPorId(idEditora);
                if (lpLivro.getEditoraPrincipal() == null) {
                    lpLivro.setEditoraPrincipal(editora.getNome());
                } else {
                    lpLivro.setQtdOutrasEditoras(lpLivro.getQtdOutrasEditoras() + 1);
                }
            }

            lpLivro.setAno(livro.getAno());

            Colecao colecao = service.listaColecoes().stream().filter(c -> c.getIdsLivros().contains(livro.getId())).findFirst().orElse(null);
            if (colecao != null) {
                lpLivro.setColecao(colecao.getNome());
            }

            Collection<Etiqueta> etiquetas = service.listaEtiquetas().stream().filter(e -> livro.getIdsEtiquetas().contains(e.getId())).toList();
            lpLivro.setEtiquetas("");
            for (Etiqueta etiqueta : etiquetas) {
                lpLivro.setEtiquetas(etiqueta.getNome() + ", ");
            }
            if (lpLivro.getEtiquetas().length() > 0) {
                lpLivro.setEtiquetas(lpLivro.getEtiquetas().substring(0, lpLivro.getEtiquetas().length() - 2));
            }

            lpLivro.setPaginas(livro.getPaginas());
            lpLivro.setEdicao(livro.getEdicao());
            lpLivro.setStatus(service.buscaUltimaAtividadeDoLivro(livro.getId()).getTipo().getStatus());

            result.add(lpLivro);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<LivrosPaginaLivro>() {
            @Override
            public int compare(LivrosPaginaLivro o1, LivrosPaginaLivro o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getTitulo(), o2.getTitulo());
            }
        });

        return result;
    }

    // obras

    public ObrasPagina getObras() throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();

        Collection<Obra> obras = service.listaObras();
        ObrasPagina result = new ObrasPagina();
        for (Obra obra : obras) {
            ObrasPaginaObra opObra = new ObrasPaginaObra();

            opObra.setId(obra.getId());
            opObra.setTitulo(obra.getTitulo());

            opObra.setAutorPrincipal(null);
            opObra.setQtdOutrosAutores(0);
            for (String idPessoa : obra.getIdsAutores()) {
                Pessoa autor = service.buscaPessoaPorId(idPessoa);
                if (opObra.getAutorPrincipal() == null) {
                    opObra.setAutorPrincipal(autor.getNome());
                } else {
                    opObra.setQtdOutrosAutores(opObra.getQtdOutrosAutores() + 1);
                }
            }
    
            opObra.setQtdLivros(service.listaLivrosDaObra(obra.getId()).size());
    
            result.add(opObra);
        }

        // TODO Testar
        Collections.sort(result, new Comparator<ObrasPaginaObra>() {
            @Override
            public int compare(ObrasPaginaObra o1, ObrasPaginaObra o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getTitulo(), o2.getTitulo());
            }
        });

        return result;
    }

    // pessoas

    public PessoasPagina getPessoas() throws StreamReadException, DatabindException, IOException {
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

    public StatsPagina getStats() throws StreamReadException, DatabindException, IOException {
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
