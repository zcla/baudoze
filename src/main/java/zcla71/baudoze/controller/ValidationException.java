package zcla71.baudoze.controller;

import java.util.List;

import lombok.Getter;

public class ValidationException extends Exception {
    @Getter
    private String campo;
    @Getter
    private List<ValidationException> exceptions;

    public ValidationException(String mensagem, String campo) {
        super(mensagem);
        this.campo = campo;
    }

    public ValidationException(List<ValidationException> exceptions) {
        super();
        this.exceptions = exceptions;
    }
}
