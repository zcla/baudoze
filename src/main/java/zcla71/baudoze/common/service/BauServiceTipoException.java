package zcla71.baudoze.common.service;

import org.springframework.http.HttpStatus;

public enum BauServiceTipoException {
    NAO_ENCONTRADO(HttpStatus.NOT_FOUND), // recurso inexistente
    VALIDACAO(HttpStatus.BAD_REQUEST), // dados inválidos; problema no input
    CONFLITO(HttpStatus.CONFLICT), // estado já existente/conflitante (já existe, duplicidade, etc.); colisão com estado atual
    REGRA_DE_NEGOCIO(HttpStatus.UNPROCESSABLE_ENTITY); // input válido, mas operação semanticamente impossível; regra de negócio

    private final HttpStatus httpStatus;

    BauServiceTipoException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
		
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
