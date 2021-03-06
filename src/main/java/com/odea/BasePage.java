package com.odea;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.odea.domain.Entrada;

public class BasePage extends WebPage {
	
	public AjaxButton botonLogout;
	public AjaxButton botonLogin;
	
	public BasePage(){
		super();
		this.preparePage();
	}
	
	public BasePage(PageParameters parameters){
		super(parameters);
		this.preparePage();
	}
	private void preparePage(){
		
		
	    BaseForm form = new BaseForm("formLogout") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, BaseForm form) {
				SecurityUtils.getSubject().logout();
				setResponsePage(LoginPage.class);
			}
		};  
		add(form);
	    if (!SecurityUtils.getSubject().isAuthenticated()){
	    	botonLogout.add(new AttributeModifier("style", new Model("display:none")));
	    }
	    else{
	    	botonLogin.add(new AttributeModifier("style", new Model("display:none")));
	    }
	}
	public abstract class BaseForm extends Form<Entrada> {

		public BaseForm(String id) {
			
			super(id);
			this.setOutputMarkupId(true);
			
			botonLogin = new AjaxButton("login") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					BaseForm.this.onSubmit(target, (BaseForm) form);
				}
				
			};
			
			botonLogout = new AjaxButton("logout") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					BaseForm.this.onSubmit(target, (BaseForm) form);
				}
				
			};
			
	
			
			add(botonLogin);
			add(botonLogout);	
			
		}

		protected abstract void onSubmit(AjaxRequestTarget target, BaseForm form);
		
		}
}


