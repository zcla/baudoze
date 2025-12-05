package zcla71.baudoze.model.validation;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ValidationException extends Exception {
	private Collection<Validation> validations;

	public ValidationException() {
		super();
		this.validations = new ArrayList<>();
	}
}
