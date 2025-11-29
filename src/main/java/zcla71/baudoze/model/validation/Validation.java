package zcla71.baudoze.model.validation;

import java.util.List;

import lombok.Data;

// TODO Mover para ..
@Data
public class Validation {
	// TODO Eliminar, transformando em método de classe (ver htmls que usam)
	public static final String getMessage(List<Validation> validations, String fieldName) {
		return validations == null ? null : validations.stream()
				.filter(v -> (v.getFieldName() != null) && v.getFieldName().equals(fieldName))
				.findFirst()
				.get()
				.getMessage();
	}

	// TODO Eliminar, transformando em método de classe (ver htmls que usam)
	public static final boolean isValid(List<Validation> validations, String fieldName) {
		return validations == null ? true : !validations.stream()
				.anyMatch(v -> (v.getFieldName() != null) && v.getFieldName().equals(fieldName));
	}

	// TODO Eliminar, transformando em método de classe (ver htmls que usam)
	public static final String validationClass(List<Validation> validations, String fieldName) {
		if (validations == null) {
			return "";
		}
		if (isValid(validations, fieldName)) {
			return "is-valid";
		}
		return "is-invalid";
	}

	private String fieldName;
	private String message;

	public Validation(String message) {
		super();
		this.message = message;
	}

	public Validation(String message, String fieldName) {
		this(message);
		this.fieldName = fieldName;
	}
}
