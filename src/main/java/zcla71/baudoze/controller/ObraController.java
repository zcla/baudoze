package zcla71.baudoze.controller;

import java.io.IOException;
import java.text.Collator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.baudoze.service.Service;
import zcla71.baudoze.service.model.Obra;
import zcla71.baudoze.service.model.Pessoa;
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
}
