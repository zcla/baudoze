package zcla71.repository;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class JsonRepository<T> extends SingleFileRepository<T> {
    public JsonRepository(Class<T> classe, String fileLocation, Boolean readFile) throws Exception {
        super(classe, fileLocation, readFile);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper result = new ObjectMapper();
        result.registerModule(new JavaTimeModule());
        return result;
    }

    public T readData() throws StreamReadException, DatabindException, IOException {
        File file = getFile();
        if (file.exists()) {
            this.setData(getObjectMapper().readValue(file, this.getClasse()));
        }
        return this.getData();
    }

    @Override
    public void saveData() throws StreamWriteException, DatabindException, IOException {
        getObjectMapper().writeValue(getFile(), this.getData());
    }
}
