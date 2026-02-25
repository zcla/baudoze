package zcla71.baudoze.biblia.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "biblia_lista")
public class BibliaLista {
	@Id
	private Long id;
	private String nome;
	private String idioma;
	private Integer livros;
}
