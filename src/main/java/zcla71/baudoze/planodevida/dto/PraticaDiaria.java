package zcla71.baudoze.planodevida.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaDiaria extends Pratica {
	private Integer dias;
}
