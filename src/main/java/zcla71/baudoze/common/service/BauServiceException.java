package zcla71.baudoze.common.service;

import lombok.Getter;

public class BauServiceException extends RuntimeException {
	@Getter
	private String contexto;

	public BauServiceException(String message) {
		super(message);
	}

	public BauServiceException(String message, String contexto) {
		this(message);
		this.contexto = contexto;
	}
}
