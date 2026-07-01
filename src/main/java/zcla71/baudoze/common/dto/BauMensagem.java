package zcla71.baudoze.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BauMensagem {
	private String tipo;
	private String texto;
	private String contexto;

	public BauMensagem(String tipo, String texto) {
		this(tipo, texto, null);
	}
}
