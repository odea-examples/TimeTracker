package com.odea.validators.ticketExterno;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;


public class TicketExternoValidator extends AbstractFormValidator {
	
	private FormComponent<?>[] components;
	
	
	public TicketExternoValidator(FormComponent<?> formComponent1,
			FormComponent<?> formComponent2) {
		System.out.println("entra hasta aca?");
		if (formComponent1 == null) {
			throw new IllegalArgumentException(
					"argument formComponent1 cannot be null");
		}
		if (formComponent2 == null) {
			throw new IllegalArgumentException(
					"argument formComponent2 cannot be null");
		}
		components = new FormComponent[] { formComponent1, formComponent2 };
	}
	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}
	
	
	

	@Override
	public void validate(Form<?> form) {
		// we have a choice to validate the type converted values or the raw
		// input values, we validate the raw input
		final FormComponent<?> formComponent1 = components[0];
		final FormComponent<?> formComponent2 = components[1];
		if (formComponent1.getInput() == "" && formComponent2.getInput() != ""){
			error(formComponent2,"a key");
		}
	}
	@Override
	public void error(FormComponent<?> fc, String resourceKey) {
		System.out.println("on error");
//		ValidationError error = new ValidationError();
//		error.addKey(getClass().getSimpleName() + "." + resourceKey);
//		error.setMessage("Ingrese un sistema externo para poder ingresar ticket externo");
		fc.error("Debe ingresar un sistema externo para poder ingresar un ticket externo");
	}
	
	
	
}
