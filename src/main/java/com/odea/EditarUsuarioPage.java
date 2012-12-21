package com.odea;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.odea.components.confirmPanel.ConfirmationButton;
import com.odea.components.modalWindow.SelectModalWindow;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class EditarUsuarioPage extends BasePage {


	private static final long serialVersionUID = 1L;

	@SpringBean
	public DAOService daoService;
	
	public CompoundPropertyModel<Usuario> usuarioModel;	
	
	
	public EditarUsuarioPage() {
		
		this.usuarioModel = new CompoundPropertyModel<Usuario>(new LoadableDetachableModel<Usuario>() {

					private static final long serialVersionUID = 1L;

					@Override
					protected Usuario load() {
						return daoService.getUsuario(SecurityUtils.getSubject().getPrincipal().toString());
					}
				});
		
		
		
		
		final SelectModalWindow selectModalWindow = new SelectModalWindow("modalwindow"){
            
			public void onCancel(AjaxRequestTarget target) {
                close(target);
            }
        };
        
		
		EditUsuarioForm form = new EditUsuarioForm("form", usuarioModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, EditUsuarioForm form) {
				daoService.modificarUsuario(getModelObject());
				selectModalWindow.show(target);
			}

		};
		
		form.setOutputMarkupId(true);	
		
		add(selectModalWindow);
		add(form);
		
	}
	
	public abstract class EditUsuarioForm extends Form<Usuario> {

		private static final long serialVersionUID = 1L;
		
		public PasswordTextField password;
		public PasswordTextField confirmPassword;
		
		public EditUsuarioForm(String id, IModel<Usuario> model) {
			super(id, model);
			
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);
			
			RequiredTextField<String> login = new RequiredTextField<String>("nombre");
			
			password = new PasswordTextField("password");
			
			confirmPassword = new PasswordTextField("confirmPassword", new Model<String>());
			
			IValidator<String> passwordValidator = new IValidator<String>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void validate(IValidatable<String> validatable) {
					
					if (!password.getConvertedInput().equals(confirmPassword.getConvertedInput())) {
						error(validatable, "Los passwords que ha ingresado son diferentes");
					}
					
				}
				
				private void error(IValidatable<String> validatable, String errorKey) {
					ValidationError error = new ValidationError();
					error.addKey(getClass().getSimpleName() + "." + errorKey);
					error.setMessage(errorKey);
					validatable.error(error);
				}
				
			};
			
			
			confirmPassword.add(passwordValidator);

			
			ConfirmationButton submit = new ConfirmationButton("submit","Seguro?", new Model<String>("Submit")) {
	
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EditUsuarioForm.this.onSubmit(target, (EditUsuarioForm) form);
					target.add(feedback);
				}
				
				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					target.add(feedback);
				}
			};
			
			
			add(feedback);
			add(login);
			add(password);
			add(confirmPassword);
			add(submit);
			
		}
		
		protected abstract void onSubmit(AjaxRequestTarget target, EditUsuarioForm form);

	}
	
}
