package zcla71.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Deprecated // Já foi usado com sucesso, mas não utilizo mais. Mantenho aqui caso seja necessário no futuro, pois funciona perfeitamente.
public class JsonUtils {
	private final ObjectMapper objectMapper;

    public JsonUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

	public String toJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar objeto para JSON", e);
        }
    }

	public void writeToFile(Object object, File file) {
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gravar JSON no arquivo", e);
		}
	}
}
