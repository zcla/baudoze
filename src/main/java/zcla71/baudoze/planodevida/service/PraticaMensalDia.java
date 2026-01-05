package zcla71.baudoze.planodevida.service;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaMensalDia extends Pratica {
	private Integer dia;
}
