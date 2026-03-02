package zcla71.baudoze.tarefa.view.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
public class TarefaLista {
	@Id
	private Long id;
	private String titulo;
	private String descricao;
    private Boolean cumprida;
}
