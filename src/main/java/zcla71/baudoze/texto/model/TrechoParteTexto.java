package zcla71.baudoze.texto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

// Texto simples, com formatação padrão
@Data
@EqualsAndHashCode(callSuper=true)
// @Entity
@DiscriminatorValue("TEXTO")
public class TrechoParteTexto extends TrechoParte {
	private String texto;
}
