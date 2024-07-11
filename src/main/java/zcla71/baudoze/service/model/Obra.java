package zcla71.baudoze.service.model;

import java.util.Collection;

import lombok.Data;

@Data
public class Obra {
    private String id;
    private String titulo;
    // TODO cascade: excluiu obra -> não afeta pessoa
    // TODO cascade: excluiu pessoa -> não permite
    private Collection<String> idsAutores;
}
