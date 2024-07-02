package zcla71.baudoze.view.atividades;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AtividadesPaginaAtividade {
    private LocalDate data;
    private String tipo;
    private String livro;
}
