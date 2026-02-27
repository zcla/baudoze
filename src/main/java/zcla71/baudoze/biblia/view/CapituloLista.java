package zcla71.baudoze.biblia.view;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "capitulo_lista")
public class CapituloLista {
	@Id
	private Long id;
	private Long bibliaId;
	private Long livroId;
	private String numero;
	private Integer versiculos;
}
