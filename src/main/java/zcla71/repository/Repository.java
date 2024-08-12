package zcla71.repository;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public abstract class Repository<T> {
    public static String generateId(Object object) {
        // TODO Tem que garantir ids únicos por tabela
        return UUID.randomUUID().toString();
    }

    @Getter
    private Class<T> classe;
    @Getter @Setter
    private T data;

    public Repository(Class<T> classe) {
        this.classe = classe; // Tentei de todo modo fazer um código sem esse parâmetro, mas não consegui. Fica feio, mas funciona.
    }

    public abstract void beginTransaction();

    public abstract void commitTransaction() throws Exception;

    public abstract void rollbackTransaction() throws Exception;
}
