package zcla71.baudoze.planodevida.service;

import java.time.DayOfWeek;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaMensalDiaDaSemana extends Pratica {
	private DayOfWeek diaDaSemana;
	private Integer ordem;
}
