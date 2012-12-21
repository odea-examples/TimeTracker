package com.odea.validators.ticketExterno;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;


public class OnRelatedFieldsNullValidator extends AbstractFormValidator {
	
	private FormComponent<?>[] components;
	private String mensaje;
	
	
	public OnRelatedFieldsNullValidator(FormComponent<?> formComponent1,
			FormComponent<?> formComponent2, String Error) {
		if (formComponent1 == null) {
			throw new IllegalArgumentException(
					"argument formComponent1 cannot be null");
		}
		if (formComponent2 == null) {
			throw new IllegalArgumentException(
					"argument formComponent2 cannot be null");
		}
		components = new FormComponent[] { formComponent1, formComponent2 };
		mensaje= Error;
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
			error(formComponent2,"a key", mensaje);
		}
	}
	public void error(FormComponent<?> fc, String resourceKey, String MsjError) {
		fc.error(MsjError);
	}
	
	
	
}
