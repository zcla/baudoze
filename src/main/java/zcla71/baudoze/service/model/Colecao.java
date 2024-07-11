package zcla71.baudoze.service.model;

import java.util.Collection;

import lombok.Data;

@Data
public class Colecao {
    private String id;
    private String nome;
    // TODO cascade: excluiu coleção -> não afeta livro
    // TODO cascade: excluiu livro -> retira da coleção
    private Collection<String> idsLivros;
}
