package zcla71.baudoze.biblia.service;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Texto normal, fazendo referência aos versículos
@Data
@EqualsAndHashCode(callSuper=true)
public class TextoParteVersiculo extends TextoParte {
	private List<String> versiculos; // Referência aos versículos, pelo id
}
