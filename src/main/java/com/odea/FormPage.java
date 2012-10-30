package com.odea;


import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.components.datepicker.DatePickerBehavior;
import com.odea.dao.ActividadDAO;
import com.odea.dao.EntradaDAO;
import com.odea.dao.ProyectoDAO;
import com.odea.dao.UsuarioDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;


public class FormPage extends BasePage {
	
	@SpringBean
	private transient ProyectoDAO proyectoDAO;
	@SpringBean
	private transient ActividadDAO actividadDAO;
	@SpringBean
	private transient EntradaDAO entradaDAO;
	@SpringBean
	private transient UsuarioDAO usuarioDAO;
	
	private Usuario usuario;


	
	public FormPage() {
		
		super();
		
		EntradaForm form = new EntradaForm("form"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {

				Subject subject = SecurityUtils.getSubject();
				usuario = usuarioDAO.getUsuario(subject.getPrincipal().toString());
				Entrada e = form.getModelObject();
				e.setUsuario(usuario);
				FormPage.this.entradaDAO.agregarEntrada(e);
			}
			
		};

		add(form);	
	
	}

	public abstract class EntradaForm extends Form<Entrada> {
		IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(new Entrada());
		DropDownChoice<Actividad> comboActividad;
		DropDownChoice<Proyecto> comboProyecto; 	
		
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);
			
			this.comboActividad = new DropDownChoice<Actividad>("actividad");
			this.comboActividad.setOutputMarkupId(true);
			this.comboProyecto = new DropDownChoice<Proyecto>("proyecto",  proyectoDAO.getProyectos());
			this.comboProyecto.setOutputMarkupId(true);	
			
			this.comboProyecto.add(new AjaxFormComponentUpdatingBehavior("onchange"){
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					EntradaForm.this.comboActividad.setChoices(actividadDAO.getActividades(EntradaForm.this.comboProyecto.getModelObject()));
					target.add(EntradaForm.this.comboActividad);
				}
			});
			
			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("1");
			sistExt.add("2");
			
			DropDownChoice<String> sistemaExt = new DropDownChoice<String>("sistemaExterno", sistExt);
			TextArea<String> nota = new TextArea<String>("nota");
			TextField<Double> duracion = new TextField<Double>("duracion");
			TextField<String> ticketBZ = new TextField<String>("ticketBZ");
			TextField<String> ticketExt = new TextField<String>("ticketExterno");
			TextField<Date> fecha = new TextField<Date>("fecha");
			fecha.setOutputMarkupId(true);
			fecha.add(new DatePickerBehavior(fecha.getMarkupId()));
			
			
			AjaxButton submit = new AjaxButton("submit", this) {
			
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EntradaForm.this.onSubmit(target, (EntradaForm)form);
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					super.onError(target, form);
				}
				
			};
			
			add(comboProyecto);
			add(comboActividad);
			add(duracion);
			add(fecha);
			add(nota);
			add(ticketBZ);
			add(sistemaExt);
			add(ticketExt);
			add(submit);
			this.setOutputMarkupId(true);

		}

		protected abstract void onSubmit(AjaxRequestTarget target, EntradaForm form);
	}	
}