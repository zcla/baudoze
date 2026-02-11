package zcla71.baudoze.biblia.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

// Texto simples, com formatação padrão
@Data
@EqualsAndHashCode(callSuper=true)
public class TrechoParteTextoSimples extends TrechoParte {
	private String texto;
}
