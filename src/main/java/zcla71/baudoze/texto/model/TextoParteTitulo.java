package zcla71.baudoze.texto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

// Título
@Data
@EqualsAndHashCode(callSuper=true)
// @Entity
@DiscriminatorValue("TITULO")
public class TextoParteTitulo extends TextoParte {
	private Integer nivel;
}
