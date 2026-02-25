package zcla71.baudoze.biblia.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "livro_lista")
public class LivroLista {
	@Id
	private Long id;
	private Long bibliaId;
	private String sigla;
	private String nome;
	private Integer capitulos;
}
