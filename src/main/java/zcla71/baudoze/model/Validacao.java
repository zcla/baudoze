package zcla71.baudoze.model;

import lombok.Data;

@Data
public class Validacao {
	private String fieldName;
	private String message;

	public Validacao(String message) {
		super();
		this.message = message;
	}

	public Validacao(String message, String fieldName) {
		this(message);
		this.fieldName = fieldName;
	}
}
