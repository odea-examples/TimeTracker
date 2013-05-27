package com.odea.validators.duracion;

import java.util.regex.Pattern;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.odea.services.DAOService;

public class DurationValidator implements IValidator<String>{
	
	@SpringBean
	private transient DAOService daoService;
	
	private final String DURACION_PATTERN 
	= "^([0-9]{1,2}+(,[0-9]{1,2}|:[0-5]{1}+[0-9]{1}|\\b))$";

	private final String WRONG_PATTERN 
	= "^([0]{1,2}+(,[0]{1,2}|:00|\\b))$";
	
	private final String HORA_PATTERN 
	= "^([0-9]{1,2}:[0-5]{1}[0-9]{1})$";
	
	private final String DECIMAL_PATTERN 
	= "^([0-9]{1,2}(,[0-9]{1,2}|\\b))$";
	
	
	private final Pattern duracionPattern;
	private final Pattern wrongPattern;
	private final Pattern horaPattern;
	private final Pattern decimalPattern;

	public DurationValidator() {
		duracionPattern = Pattern.compile(DURACION_PATTERN);
		wrongPattern = Pattern.compile(WRONG_PATTERN);
		horaPattern = Pattern.compile(HORA_PATTERN);
		decimalPattern = Pattern.compile(DECIMAL_PATTERN);
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		final String duracion = validatable.getValue();
		Double duracionParseada; 	
		
		if (!duracionPattern.matcher(duracion).matches()) {	
			error(validatable, "Duración con formato equivocado");
		} else if (wrongPattern.matcher(duracion).matches()) {
			error(validatable, "Duración no puede ser cero.");
		} else if (decimalPattern.matcher(duracion).matches())  {

			duracionParseada = Double.parseDouble(duracion.replace(',', '.'));
			if (duracionParseada >= 24) {
				error(validatable, "Duración no puede ser mayor a 24 horas");
			}
			
		} else if (horaPattern.matcher(duracion).matches())  {
			int pos = duracion.indexOf(":");
			String strHora = duracion.substring(0, pos);
			duracionParseada = Double.parseDouble(strHora);
			
			if (duracionParseada >= 24) {
				error(validatable, "Duración no puede ser mayor a 24 horas");
			}
		}
		
	}
	
	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addKey(getClass().getSimpleName() + "." + errorKey);
		error.setMessage(errorKey);
		validatable.error(error);
	}

}
