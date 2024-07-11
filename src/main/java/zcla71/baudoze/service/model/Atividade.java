package zcla71.baudoze.service.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Atividade {
    public enum AtividadeTipo {
        CADASTRO(1, "Cadastro", ""),
        INICIO_LEITURA(2, "Início de leitura", "Lendo"),
        TERMINO_LEITURA(3, "Término de leitura", "Lido");

        @Getter
        private Integer ordem;
        @Getter
        private String texto;
        @Getter
        private String status;

        private AtividadeTipo(Integer ordem, String texto,String status) {
            this.ordem = ordem;
            this.texto = texto;
            this.status = status;
        }
    }

    private String id;
    private AtividadeTipo tipo;
    private LocalDate data;
    // cascade: excluiu atividade -> não afeta livro
    // cascade: excluiu livro -> exclui atividade
    private String idLivro;
}
