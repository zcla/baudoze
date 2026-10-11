package zcla71.baudoze.planodevida.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaMensalDia extends Pratica {
	private Integer dia;
}
