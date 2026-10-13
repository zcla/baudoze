package zcla71.baudoze.anotacoes.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ReferenciaLivre extends Referencia {
	String referencia;
}
