package com.odea;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.services.DAOService;

public class AltaPerfilPage extends BasePage {
	
	@SpringBean
	private DAOService daoService;
	
	public AltaPerfilPage() {
		
		Form<String> form = new Form<String>("form");
		final TextField<String> nombrePerfil = new TextField<String>("textFieldPerfil", new Model<String>());
		
		AjaxButton submit = new AjaxButton("submitButton") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				daoService.altaPerfil(nombrePerfil.getModelObject());
			}
			
		};
		
		form.add(nombrePerfil);
		form.add(submit);
		
		add(form);
	}

}
