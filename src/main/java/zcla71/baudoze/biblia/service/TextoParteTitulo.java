package zcla71.baudoze.biblia.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Título
@Data
@EqualsAndHashCode(callSuper=true)
public class TextoParteTitulo extends TextoParte {
	private Trecho trecho;
	private Integer nivel;
}
