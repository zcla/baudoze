package zcla71.baudoze.estudos.dto.concordancia;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class TabelaLinha {
	private Map<String, Object> parte;
	private String titulo;
	private String mt;
	private String mc;
	private String lc;
	private String jo;

	public TabelaLinha() {
		this.parte = new HashMap<>();
	}
}
