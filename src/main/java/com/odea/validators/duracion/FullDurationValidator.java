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
	private Date fecha;
	private Usuario usuario;
	
	
	public FullDurationValidator(FormComponent<?> formComponent1,
			Date fecha, Usuario usuario, String error) {
		if (formComponent1 == null) {
			throw new IllegalArgumentException(
					"argument formComponent1 cannot be null");
		}
		components = new FormComponent[] { formComponent1};
		mensaje= error;
		this.usuario = usuario;
		this.fecha=fecha;
	}
	
	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}
	
	
	

	@Override
	public void validate(Form<?> form) {
		final FormComponent<?> formComponent1 = components[0];
		if(!(daoService.puedeEntrar(formComponent1.getInput(), fecha, usuario))){
		error(formComponent1,"a key", mensaje);
		}
	}
	public void error(FormComponent<?> fc, String resourceKey, String MsjError) {
		fc.error(MsjError);
	}
	
	
	
	
	
	
}
