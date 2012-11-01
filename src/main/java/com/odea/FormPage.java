package com.odea;


import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.components.datepicker.DatePickerBehavior;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;


public class FormPage extends BasePage {
	
	@SpringBean
	private transient DAOService daoService;
	private Usuario usuario;
	
	public FormPage() {
		super();
		
		Subject subject = SecurityUtils.getSubject();
		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());

		
		EntradaForm form = new EntradaForm("form"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {
				daoService.agregarEntrada(form.getModelObject(), usuario);
			}
		};

		add(form);	
	
	}

	public abstract class EntradaForm extends Form<Entrada> {
		IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(new Entrada());
		DropDownChoice<Actividad> comboActividad;
		DropDownChoice<Proyecto> comboProyecto; 	
		
		
		IModel<Integer> horasSemanalesModel = new LoadableDetachableModel<Integer>() {
			@Override
			protected Integer load() {
				return daoService.getHorasSemanales(usuario);
			}
			
		}; 
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);
			

			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("1"); //sistema de incidencias de YPF
			sistExt.add("2"); // sistema geminis de YPF
			
			
			
			this.comboProyecto = new DropDownChoice<Proyecto>("proyecto",  daoService.getProyectos());
			this.comboProyecto.setOutputMarkupId(true);	
			this.comboProyecto.setRequired(true);
			this.comboProyecto.setLabel(Model.of("Proyecto"));
			
			this.comboProyecto.add(new AjaxFormComponentUpdatingBehavior("onchange"){
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					EntradaForm.this.comboActividad.setChoices(daoService.getActividades(EntradaForm.this.comboProyecto.getModelObject()));
					target.add(EntradaForm.this.comboActividad);
				}
			});
			
			
			this.comboActividad = new DropDownChoice<Actividad>("actividad");
			this.comboActividad.setOutputMarkupId(true);
			this.comboActividad.setRequired(true);
			this.comboActividad.setLabel(Model.of("Actividad"));
			
			
			DropDownChoice<String> sistemaExterno = new DropDownChoice<String>("sistemaExterno", sistExt);
			sistemaExterno.setRequired(true);
			sistemaExterno.setLabel(Model.of("Sistema Externo"));
			
			TextArea<String> nota = new TextArea<String>("nota");
			
			TextField<Double> duracion = new TextField<Double>("duracion");
			duracion.setRequired(true);
			duracion.setLabel(Model.of("Duracion"));
			 
			TextField<String> ticketBZ = new TextField<String>("ticketBZ");
			ticketBZ.setRequired(true);
			ticketBZ.setLabel(Model.of("Ticket Bugzilla"));
			 
			TextField<String> ticketExt = new TextField<String>("ticketExterno");
			ticketExt.setRequired(true);
			ticketExt.setLabel(Model.of("ID Ticket Externo"));
			
			TextField<Date> fecha = new TextField<Date>("fecha");
			fecha.setRequired(true);
			fecha.add(new DatePickerBehavior(fecha.getMarkupId()));
			fecha.setOutputMarkupId(true);
			fecha.setLabel(Model.of("Fecha"));
			
			final FeedbackPanel feedBackPanel = new FeedbackPanel("feedBackPanel");
			feedBackPanel.setOutputMarkupId(true);
			
			final Label horasAcumuladas = new Label("horasAcumuladas", horasSemanalesModel);
			horasAcumuladas.setOutputMarkupId(true);

			
			
			
			
			AjaxButton submit = new AjaxButton("submit", this) {
			
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EntradaForm.this.onSubmit(target, (EntradaForm)form);								
					target.add(feedBackPanel);
					target.add(horasAcumuladas);
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					target.add(feedBackPanel);
					
				}
				
			};
			
			add(comboProyecto);
			add(comboActividad);
			add(duracion);
			add(fecha);
			add(nota);
			add(ticketBZ);
			add(sistemaExterno);
			add(ticketExt);
			add(feedBackPanel);
			add(horasAcumuladas);
			add(submit);
			this.setOutputMarkupId(true);

		}

		
		protected abstract void onSubmit(AjaxRequestTarget target, EntradaForm form);

	}	
}


