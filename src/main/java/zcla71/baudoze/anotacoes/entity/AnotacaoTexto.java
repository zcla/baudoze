package zcla71.baudoze.anotacoes.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
// Anotação em texto livre
public class AnotacaoTexto extends Anotacao {
	private String texto;
	private String formato;
}
