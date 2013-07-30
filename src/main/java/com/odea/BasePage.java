package com.odea;

import java.awt.Component;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.LocalDate;

import com.odea.domain.Entrada;

public class BasePage extends WebPage {
	
	private AjaxButton botonLogout;
	private AjaxButton botonLogin;
	private WebMarkupContainer formulario;
	private WebMarkupContainer actividades;
	private WebMarkupContainer proyectos;
	private WebMarkupContainer usuarios;
	private WebMarkupContainer miCuenta;
	private WebMarkupContainer feriados;
	private WebMarkupContainer reportHoras;
	private WebComponent wb;
	private String page;
	
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
			page = this.getRequest().getUrl().toString();
			
			formulario= armarWmc("formulario");
			actividades= armarWmc("actividades");
			proyectos= armarWmc("proyectos");
			miCuenta = armarWmc("miCuenta");
			reportHoras = armarWmc("reportHoras");
			feriados = armarWmc("feriados");
			usuarios = armarWmc("usuarios");
			
			add(formulario);
			add(actividades);
			add(proyectos);
			add(miCuenta);
			add(reportHoras);
			add(feriados);
			add(usuarios);
			
			add(botonLogin);
			add(botonLogout);	
			
		}

		private WebMarkupContainer armarWmc(String string) {
			WebMarkupContainer WebMc= new WebMarkupContainer(string);
			if(page.equals(string)&&SecurityUtils.getSubject().isAuthenticated()){
				WebMc.add(new AttributeModifier("class","current"));
			}
			return WebMc;
		}

		protected abstract void onSubmit(AjaxRequestTarget target, BaseForm form);
		
		}
}


