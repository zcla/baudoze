package zcla71.baudoze.biblia.view.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
public class LivroLista {
	@Id
	private Long id;
	private Long bibliaId;
	private String sigla;
	private String nome;
	private Integer capitulos;
}
