package zcla71.baudoze.controller;

import java.util.Collection;

public class ValidationUtils {
    public static void checkNotEmpty(Collection<?> value, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            throw new ValidationException(mensagem, campo);
        }
        if (value.size() == 0) {
            throw new ValidationException(mensagem, campo);
        }
    }

    public static void checkNotEmpty(String value, String mensagem, String campo) throws ValidationException {
        if (value == null) {
            throw new ValidationException(mensagem, campo);
        }
        if (value.trim().length() == 0) {
            throw new ValidationException(mensagem, campo);
        }
    }
}
