package zcla71.baudoze.model.service;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.baudoze.model.validation.Validation;

@Data
@EqualsAndHashCode(callSuper=true)
public class ValidationException extends Exception {
	private Collection<Validation> validations;

	public ValidationException() {
		super();
		this.validations = new ArrayList<>();
	}
}
