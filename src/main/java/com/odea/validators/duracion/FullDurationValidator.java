package com.odea.validators.duracion;

import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Usuario;
import com.odea.services.DAOService;



public class FullDurationValidator extends AbstractFormValidator{
	
	@SpringBean
	private transient DAOService daoService;
	
	private FormComponent<?>[] components;
	private String mensaje;
	private Usuario usuario;
	
	
	public FullDurationValidator(FormComponent<?> formComponent1,
			FormComponent<?> fecha, Usuario usuario, String error) {
		if (formComponent1 == null) {
			throw new IllegalArgumentException(
					"argument formComponent1 cannot be null");
		}
		components = new FormComponent[] { formComponent1, fecha};
		mensaje= error;
		this.usuario = usuario;
	}
	
	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}
	
	
	

	@Override
	public void validate(Form<?> form) {
		final FormComponent<?> formComponent1 = components[0];
		final FormComponent<?> fecha = components[1];
		if(!(daoService.puedeEntrar(formComponent1.getInput(), (Date) fecha.getConvertedInput(), usuario,"10800000"))){
		error(formComponent1,"a key", mensaje);
		}
	}
	public void error(FormComponent<?> fc, String resourceKey, String MsjError) {
		fc.error(MsjError);
	}
	
	
	
	
	
	
}
