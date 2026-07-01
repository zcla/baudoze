package zcla71.baudoze.biblia.dto;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Immutable
public class CapituloLista {
	@Id
	private Long id;
	private Long bibliaId;
	private Long livroId;
	private String numero;
	private Integer versiculos;
}
