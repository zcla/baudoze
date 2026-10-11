package zcla71.baudoze.planodevida.entity;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PraticaAnual extends Pratica {
	private LocalDate data;
}
