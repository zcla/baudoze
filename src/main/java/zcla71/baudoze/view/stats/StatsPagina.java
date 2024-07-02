package zcla71.baudoze.view.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsPagina {
    private Integer obras;
    private Integer livros;
    private Integer pessoas;
    private Integer editoras;
    private Integer colecoes;
    private Integer etiquetas;
    private Integer atividades;
}
