package zcla71.baudoze.estudos.dto.concordancia;

import lombok.Data;

@Data
public class TabelaLinha {
	private String parte;
	private Integer parteRowspan = 0;
	private String titulo;
	private String mt = "-";
	private String mc = "-";
	private String lc = "-";
	private String jo = "-";
}
