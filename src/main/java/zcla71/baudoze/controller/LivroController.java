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

import zcla71.baudoze.Application;
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
import zcla71.baudoze.view.livro.LivroForm;
import zcla71.baudoze.view.livro.LivroPagina;
import zcla71.baudoze.view.livro.LivroPaginaEditora;
import zcla71.baudoze.view.livro.LivroPaginaEtiqueta;
import zcla71.baudoze.view.livro.LivroPaginaObra;
import zcla71.baudoze.view.livros.LivrosPagina;
import zcla71.baudoze.view.livros.LivrosPaginaLivro;

public class LivroController {
    private static LivroController instance;

    public static LivroController getInstance() {
        if (instance == null) {
            instance = new LivroController();
        }
        return instance;
    }

    // TODO Fazer testes unitários

    public void deleteLivro(String id) throws StreamReadException, DatabindException, IOException, RepositoryException {
        Service service = Service.getInstance();

        service.excluiLivro(service.buscaLivroPorId(id));
    }

    public LivroPagina getLivro(String id) throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();

        LivroPagina result = new LivroPagina();
        result.setObras(new ArrayList<>());
        result.setEditoras(new ArrayList<>());
        result.setEtiquetas(new ArrayList<>());

        // form

        if (id != null) {
            Livro livro = service.buscaLivroPorId(id);
            result.setId(livro.getId());
            result.setTitulo(livro.getTitulo());
            for (String idObra : livro.getIdsObras()) {
                result.getObras().add(idObra);
            }
            result.setIsbn13(livro.getIsbn13());
            result.setIsbn10(livro.getIsbn10());
            result.setAno(livro.getAno());
            result.setPaginas(livro.getPaginas());
            result.setEdicao(livro.getEdicao());
            for (String idEditora : livro.getIdsEditoras()) {
                result.getEditoras().add(idEditora);
            }
            for (String idEtiqueta : livro.getIdsEtiquetas()) {
                result.getEtiquetas().add(idEtiqueta);
            }

            // detalhes
            Colecao colecao = service.buscaColecaoDoLivro(livro);
            if (colecao != null) {
                result.setColecao(colecao.getNome());
            }

            List<Atividade> atividades = service.listaAtividadesDoLivro(livro);
            result.setAtividades(new ArrayList<>());
            for (Atividade atividade : atividades) {
                result.getAtividades().add(Application.format(atividade.getData()) + " " + atividade.getTipo().getTexto());
            }
        }

        // form - selects

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

        Collection<Editora> editoras = service.listaEditoras();
        result.setListaEditoras(new ArrayList<>());
        for (Editora editora : editoras) {
            LivroPaginaEditora lpEditora = new LivroPaginaEditora();
            lpEditora.setId(editora.getId());
            lpEditora.setNome(editora.getNome());
            result.getListaEditoras().add(lpEditora);
        }
        Collections.sort(result.getListaEditoras(), new Comparator<LivroPaginaEditora>() {
            @Override
            public int compare(LivroPaginaEditora o1, LivroPaginaEditora o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getNome(), o2.getNome());
            }
        });

        Collection<Etiqueta> etiquetas = service.listaEtiquetas();
        result.setListaEtiquetas(new ArrayList<>());
        for (Etiqueta etiqueta : etiquetas) {
            LivroPaginaEtiqueta lpEtiqueta = new LivroPaginaEtiqueta();
            lpEtiqueta.setId(etiqueta.getId());
            lpEtiqueta.setNome(etiqueta.getNome());
            result.getListaEtiquetas().add(lpEtiqueta);
        }
        Collections.sort(result.getListaEtiquetas(), new Comparator<LivroPaginaEtiqueta>() {
            @Override
            public int compare(LivroPaginaEtiqueta o1, LivroPaginaEtiqueta o2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(o1.getNome(), o2.getNome());
            }
        });

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
            lpLivro.setStatus(service.buscaUltimaAtividadeDoLivro(livro).getTipo().getStatus());

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

    public LivroPagina setLivro(String id, LivroForm form) throws StreamReadException, DatabindException, IOException, RepositoryException {
        LivroPagina result = getLivro(id);
        result.setEstadoPagina(Estado.CREATE);
        if (id != null) {
            result.setEstadoPagina(Estado.UPDATE); 
        }
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
        result.setEditoras(form.getEditoras());
        result.setEtiquetas(form.getEtiquetas());

        Estado estadoAnterior = result.getEstadoPagina();
        try {
            Livro livro = validaLivro(id, result);
            result.setEstadoPagina(Estado.READ);
            Service service = Service.getInstance();
            if (livro.getId() == null) {
                livro = service.incluiLivro(livro);
                result.setId(livro.getId());
            } else {
                service.alteraLivro(livro);
            }
        } catch (ValidationException e) {
            for (ValidationException ve : e.getExceptions()) {
                result.getExceptionMap().put(ve.getCampo(), ve);
            }
            result.setEstadoPagina(estadoAnterior);
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

        // editoras
        try {
            ValidationUtils.checkNotEmpty(livro.getEditoras(), "Informe ao menos uma editora", "editoras");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // etiquetas (nada)

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
        result.setIdsEditoras(livro.getEditoras());
        result.setIdsEtiquetas(livro.getEtiquetas());

        return result;
    }
}
