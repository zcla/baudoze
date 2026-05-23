package zcla71.baudoze.common.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

import lombok.Getter;

public class BauServiceException extends RuntimeException {
	@Getter
	private BauServiceTipoException motivo;
	@Getter
	private String contexto;

	public BauServiceException(BauServiceTipoException motivo, String message) {
		super(message);
		this.motivo = motivo;
	}

	public BauServiceException(BauServiceTipoException motivo, String message, String contexto) {
		this(motivo, message);
		this.contexto = contexto;
	}

	public BodyBuilder getResponseEntity() {
		return ResponseEntity.status(Objects.requireNonNull(this.motivo.getHttpStatus()));
	}
}
