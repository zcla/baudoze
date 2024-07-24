package zcla71.baudoze.controller;

import java.io.IOException;
import java.text.Collator;
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
import zcla71.baudoze.service.model.Livro;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
import zcla71.baudoze.view.Pagina.Estado;
import zcla71.baudoze.view.obra.ObraForm;
import zcla71.baudoze.view.obra.ObraPagina;
import zcla71.baudoze.view.obra.ObraPaginaAutor;
import zcla71.baudoze.view.obra.ObraPaginaLivro;
import zcla71.baudoze.view.obras.ObrasPagina;
import zcla71.baudoze.view.obras.ObrasPaginaObra;

public class ObraController {
    private static ObraController instance;

    public static ObraController getInstance() {
        if (instance == null) {
            instance = new ObraController();
        }
        return instance;
    }

    // TODO Fazer testes unitários

    public void deleteObra(String id) throws StreamReadException, DatabindException, IOException, RepositoryException {
        Service service = Service.getInstance();

        service.excluiObra(service.buscaObraPorId(id));
    }

    public ObraPagina getObra(String id) throws StreamReadException, DatabindException, IOException {
        Service service = Service.getInstance();

        ObraPagina result = new ObraPagina();
        result.setAutores(new ArrayList<>());

        // form

        if (id != null) {
            Obra obra = service.buscaObraPorId(id);
            result.setId(obra.getId());
            result.setTitulo(obra.getTitulo());
            for (String idAutor : obra.getIdsAutores()) {
                result.getAutores().add(idAutor);
            }

            // detalhes

            List<Livro> livros = service.listaLivrosDaObra(obra);
            result.setLivros(new ArrayList<>());
            for (Livro livro : livros) {
                ObraPaginaLivro opl = new ObraPaginaLivro();
                opl.setId(livro.getId());
                opl.setNome(livro.getTitulo());
                result.getLivros().add(opl);
            }
        }

        // form - selects

        Collection<Pessoa> pessoas = service.listaPessoas();
        result.setListaAutores(new ArrayList<>());
        for (Pessoa pessoa : pessoas) {
            ObraPaginaAutor lpPessoa = new ObraPaginaAutor();
            lpPessoa.setId(pessoa.getId());
            lpPessoa.setNome(pessoa.getNome());
            result.getListaAutores().add(lpPessoa);
        }
        Collections.sort(result.getListaAutores(), new Comparator<ObraPaginaAutor>() {
            @Override
            public int compare(ObraPaginaAutor a1, ObraPaginaAutor a2) {
                Collator ptBrCollator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
                return ptBrCollator.compare(a1.getNome(), a2.getNome());
            }
        });

        return result;
    }

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
    
            opObra.setQtdLivros(service.listaLivrosDaObra(obra).size());
    
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

    public ObraPagina setObra(String id, ObraForm form) throws StreamReadException, DatabindException, IOException, RepositoryException {
        ObraPagina result = getObra(id);
        result.setEstadoPagina(Estado.CREATE);
        if (id != null) {
            result.setEstadoPagina(Estado.UPDATE); 
        }
        result.setTitulo(form.getTitulo());
        result.setAutores(form.getAutores());

        Estado estadoAnterior = result.getEstadoPagina();
        try {
            Obra obra = validaObra(id, result);
            result.setEstadoPagina(Estado.READ);
            Service service = Service.getInstance();
            if (obra.getId() == null) {
                obra = service.incluiObra(obra);
                result.setId(obra.getId());
            } else {
                service.alteraObra(obra);
            }
        } catch (ValidationException e) {
            for (ValidationException ve : e.getExceptions()) {
                result.getExceptionMap().put(ve.getCampo(), ve);
            }
            result.setEstadoPagina(estadoAnterior);
        }

        return result;
    }

    private Obra validaObra(String id, ObraPagina obra) throws StreamReadException, DatabindException, IOException, ValidationException {
        List<ValidationException> exceptions = new ArrayList<>();
        
        Obra result = null;
        if (id == null) {
            result = new Obra();
        } else {
            Service service = Service.getInstance();
            result = service.buscaObraPorId(id);
        }

        // titulo
        try {
            ValidationUtils.checkNotEmpty(obra.getTitulo(), "Informe o título", "titulo");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        // autores
        try {
            ValidationUtils.checkNotEmpty(obra.getAutores(), "Informe ao menos um autor", "autores");
        } catch (ValidationException e) {
            exceptions.add(e);
        }

        if (exceptions.size() > 0) {
            throw new ValidationException(exceptions);
        }

        result.setTitulo(obra.getTitulo());
        result.setIdsAutores(obra.getAutores());

        return result;
    }
}
