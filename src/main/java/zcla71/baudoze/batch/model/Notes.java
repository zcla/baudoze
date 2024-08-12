package zcla71.baudoze.batch.model;

import lombok.Data;

@Data
public class Notes {
    private String id;
    private String edicao;
    private String editora;
    private Integer publicacao;
    private Integer paginas;
    private String isbn13;
}
