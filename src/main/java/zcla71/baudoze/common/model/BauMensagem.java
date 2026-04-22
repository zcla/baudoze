package zcla71.baudoze.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BauMensagem {
	private String tipo;
	private String texto;
}
