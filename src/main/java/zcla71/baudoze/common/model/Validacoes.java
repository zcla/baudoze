package zcla71.baudoze.common.model;

import java.util.ArrayList;

public class Validacoes extends ArrayList<Validacao> {
	public String getMessage(String fieldName) {
		return this.stream()
				.filter(v -> (v.getFieldName() != null) && v.getFieldName().equals(fieldName))
				.findFirst()
				.get()
				.getMessage();
	}

	public boolean isValid(String fieldName) {
		return !this.stream()
				.anyMatch(v -> (v.getFieldName() != null) && v.getFieldName().equals(fieldName));
	}

	public String validationClass(String fieldName) {
		if (isValid(fieldName)) {
			return "is-valid";
		}
		return "is-invalid";
	}
}
