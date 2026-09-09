package zcla71.baudoze.estudos.biblia.concordancia.dto;

import java.util.List;

import lombok.Data;

@Data
public class Parte {
	private String titulo;
	private Integer pagina;
	private List<Titulo> titulos;
}
