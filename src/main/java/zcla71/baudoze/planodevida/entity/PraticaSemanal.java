package zcla71.baudoze.planodevida.entity;

import java.time.DayOfWeek;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaSemanal extends Pratica {
	private Collection<DayOfWeek> diasDaSemana;
}
