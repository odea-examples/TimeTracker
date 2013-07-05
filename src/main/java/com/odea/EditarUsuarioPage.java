package com.odea;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
import org.springframework.dao.EmptyResultDataAccessException;

import com.odea.components.confirmPanel.ConfirmationButton;
import com.odea.components.modalWindow.SelectModalWindow;
import com.odea.components.slickGrid.Enumeraciones.formatos;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class EditarUsuarioPage extends BasePage {


	private static final long serialVersionUID = 1L;

	@SpringBean
	public DAOService daoService;
	
	public CompoundPropertyModel<Usuario> usuarioModel;	
	public EditUsuarioForm form;
	
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
        
		
		this.form = new EditUsuarioForm("form", usuarioModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, EditUsuarioForm form) {
				daoService.modificarUsuario(getModelObject());
                UsernamePasswordToken token = new UsernamePasswordToken(getModelObject().getNombreLogin(), getModelObject().getPassword());
                Subject currentUser = SecurityUtils.getSubject();
                currentUser.login(token);
				selectModalWindow.show(target);
			}

		};
		
		form.setOutputMarkupId(true);	
		
		add(selectModalWindow);
		add(form);
		
	}
	
	public abstract class EditUsuarioForm extends Form<Usuario> {

		private static final long serialVersionUID = 1L;
		
		RequiredTextField<String> login;
		public PasswordTextField password;
		public PasswordTextField confirmPassword;
		
		public EditUsuarioForm(String id, IModel<Usuario> model) {
			super(id, model);
			
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);
			
			login = new RequiredTextField<String>("nombreLogin");
			
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

			
			
			IValidator<String> nombreValidator = new IValidator<String>() {

				private static final long serialVersionUID = 1L;

				@Override
				public void validate(IValidatable<String> validatable) {
					
					Usuario usuarioEncontrado = null;
					
					try {
						usuarioEncontrado = daoService.getUsuario(login.getConvertedInput());						
					} catch (EmptyResultDataAccessException e) {
						//Si no encuentra ninguno está bien, porque es que no hay usuario existente con el
						//mismo nombre de usuario.
					}
					
					if (usuarioEncontrado != null) {
						if (usuarioEncontrado.getIdUsuario() != EditUsuarioForm.this.getModelObject().getIdUsuario()) {
							error(validatable, "El nombre de usuario se encuentra en uso");
						}						
					}
					
					
				}
				
				private void error(IValidatable<String> validatable, String errorKey) {
					ValidationError error = new ValidationError();
					error.addKey(getClass().getSimpleName() + "." + errorKey);
					error.setMessage(errorKey);
					validatable.error(error);
				}
				
			};
			
			login.add(nombreValidator);
			
			
			ConfirmationButton submit = new ConfirmationButton("submit","\\u00BFEst\\xE1 seguro de que desea realizar los cambios?", new Model<String>("Confirmar")) {
	
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
