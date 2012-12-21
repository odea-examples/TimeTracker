package com.odea.validators.duracion;

import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class DurationValidator implements IValidator<String>{
	
	private final String DURACION_PATTERN 
	= "^([0-9]{1,2}+(,[0-9]{1,2}|:[0-5]{1}+[0-9]{1}|\\b))$";
	
	private final Pattern pattern;

	public DurationValidator() {
		pattern = Pattern.compile(DURACION_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		final String duracion = validatable.getValue();
		 	
		if (pattern.matcher(duracion).matches() == false) {	
			error(validatable, "Duración con formato equivocado");
		}
		
	}
	
	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addKey(getClass().getSimpleName() + "." + errorKey);
		error.setMessage(errorKey);
		validatable.error(error);
	}

}
