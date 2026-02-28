package zcla71.baudoze.biblia.view.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "versiculo_lista")
public class VersiculoLista {
	@Id
	private Long id;
	private Long bibliaId;
	private Long livroId;
	private Long capituloId;
	private String numero;
	private String texto;
}
