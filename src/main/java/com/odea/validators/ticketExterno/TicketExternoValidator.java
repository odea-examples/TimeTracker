package com.odea.validators.ticketExterno;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;


public class TicketExternoValidator extends AbstractFormValidator {
	
	private FormComponent<?>[] components;
	
	
	public TicketExternoValidator(FormComponent<?> formComponent1,
			FormComponent<?> formComponent2) {
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
		final FormComponent<?> formComponent1 = components[0];
		final FormComponent<?> formComponent2 = components[1];
		if (formComponent1.getInput() == "" && formComponent2.getInput() != ""){
			error(formComponent2,"a key");
		}
	}
	@Override
	public void error(FormComponent<?> fc, String resourceKey) {
		fc.error("Debe ingresar un sistema externo para poder ingresar un ticket externo");
	}
	
	
	
}
