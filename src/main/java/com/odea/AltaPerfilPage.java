package com.odea;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class AltaPerfilPage extends BasePage {
	
	public AltaPerfilPage() {
		
		Form<String> form = new Form<String>("form");
		final TextField<String> nombrePerfil = new TextField<String>("textFieldPerfil", new Model<String>());
		
		AjaxButton submit = new AjaxButton("submitButton") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				//TODO: Implementar metodo para guardar el perfil
				//daoService.guardarNuevoPerfil(nombrePerfil.getModelObject());
			}
			
		};
		
		form.add(nombrePerfil);
		form.add(submit);
		
		add(form);
	}

}
