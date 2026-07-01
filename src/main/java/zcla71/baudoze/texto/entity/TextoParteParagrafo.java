package zcla71.baudoze.texto.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

// Texto normal, fazendo referência aos versículos
@Data
@EqualsAndHashCode(callSuper=true)
// @Entity
@DiscriminatorValue("PARAGRAFO")
public class TextoParteParagrafo extends TextoParte {
}
