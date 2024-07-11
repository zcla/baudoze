package zcla71.baudoze.repository;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;

public class Repository<T> {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    private String fileLocation;
    private Boolean inTransaction;
    private Class<T> classe;
    @Getter
    private T data;

    public Repository(Class<T> classe, String fileLocation, Boolean readFile) throws StreamReadException, DatabindException, IOException {
        this.classe = classe; // TODO Tentei de todo modo fazer um código sem esse parâmetro, mas não consegui. Fica feio, mas funciona.
        this.fileLocation = fileLocation;
        this.inTransaction = false;
        try {
            this.data = this.classe.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        if (readFile) {
            this.readData();
        }
    }

    public void beginTransaction() {
        if (this.inTransaction) {
            throw new RuntimeException("Já há transação iniciada");
        }
        this.inTransaction = true;
    }

    public void commitTransaction() throws StreamWriteException, DatabindException, IOException {
        if (!this.inTransaction) {
            throw new RuntimeException("Não há transação iniciada");
        }
        saveData();
        this.inTransaction = false;
    }

    public void rollbackTransaction() throws StreamReadException, DatabindException, IOException {
        if (!this.inTransaction) {
            throw new RuntimeException("Não há transação iniciada");
        }
        readData();
        this.inTransaction = false;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper result = new ObjectMapper();
        result.registerModule(new JavaTimeModule());
        return result;
    }

    private File getFile() {
        return new File(this.fileLocation);
    }

    protected T readData() throws StreamReadException, DatabindException, IOException {
        File file = getFile();
        if (file.exists()) {
            this.data = getObjectMapper().readValue(file, this.classe);
        }
        return this.data;
    }

    private void saveData() throws StreamWriteException, DatabindException, IOException {
        getObjectMapper().writeValue(getFile(), this.data);
    }
}
