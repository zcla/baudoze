package zcla71.baudoze.view;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import zcla71.baudoze.controller.ValidationException;

@Data
public abstract class Pagina {
    public enum Estado {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }
    @Data
    @AllArgsConstructor
    public class MensagemDeErro {
        String campo;
        String mensagem;
    }

    private Estado estadoPagina = Estado.READ;
    private Map<String, ValidationException> exceptionMap;

    public Pagina() {
        this.exceptionMap = new HashMap<>();
        this.estadoPagina = Estado.READ;
    }

    public String getExceptionMessage(String campo) {
        if (this.exceptionMap.get(campo) != null) {
            return this.exceptionMap.get(campo).getMessage();
        }
        return null;
    }
}
