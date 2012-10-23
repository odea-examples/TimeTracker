package com.odea;

<<<<<<< HEAD

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
=======
import java.awt.List;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
>>>>>>> refs/remotes/origin/master
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.eclipse.jetty.security.MappedLoginService.Anonymous;
import org.joda.time.LocalDateTime;

<<<<<<< HEAD
import com.jquery.JQueryResourceReference;
=======
import com.odea.dao.ActividadDAO;
import com.odea.dao.EntradaDAO;
>>>>>>> refs/remotes/origin/master
import com.odea.dao.ProyectoDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.SistemaExterno;
<<<<<<< HEAD
import com.visural.wicket.behavior.dateinput.DateInputBehavior;
=======
import com.odea.domain.Usuario;
>>>>>>> refs/remotes/origin/master

public class FormPage extends WebPage {
	
	@SpringBean
	private ProyectoDAO proyectoDAO;
	@SpringBean
	private ActividadDAO actividadDAO;
	@SpringBean
	private EntradaDAO entradaDAO;
	private static final long serialVersionUID = 1L;
	
	//List actividades = new List();
	
	
	public FormPage() {
		super();
		EntradaForm form = new EntradaForm("form"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {
				Entrada e = form.getModelObject();
				FormPage.this.entradaDAO.agregarEntrada(e);
			}
			
		};
<<<<<<< HEAD
		
		DropDownChoice<Proyecto> comboProyecto = new DropDownChoice<Proyecto>("proyecto",  proyectoDAO.getProyectos());
		DropDownChoice<Actividad> comboActividad = new DropDownChoice<Actividad>("actividad");
		DropDownChoice<SistemaExterno> sistemaExt = new DropDownChoice<SistemaExterno>("sistemaExterno");
		TextArea<String> nota = new TextArea<String>("nota");
		TextField<Double> duracion = new TextField<Double>("duracion");
		TextField<String> ticketBZ = new TextField<String>("ticketBZ");
		TextField<String> ticketExt = new TextField<String>("ticketExterno");
		Button submit = new Button("submit");
		TextField fecha = new TextField("fecha");

		
		
		form.add(comboProyecto);
		form.add(comboActividad);
		form.add(duracion);
		form.add(fecha).add(new DateInputBehavior());
		form.add(nota);
		form.add(ticketBZ);
		form.add(sistemaExt);
		form.add(ticketExt);
		form.add(submit);
		
=======
>>>>>>> refs/remotes/origin/master
		add(form);
	}


	@Override
	public void renderHead(IHeaderResponse response) {
            response.render(JavaScriptHeaderItem.forReference(new JQueryResourceReference()));
	}
	
	public abstract class EntradaForm extends Form<Entrada> {
		IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(new Entrada());
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);
			DropDownChoice<Proyecto> comboProyecto = new DropDownChoice<Proyecto>("proyecto",  proyectoDAO.getProyectos());
			DropDownChoice<Actividad> comboActividad = new DropDownChoice<Actividad>("actividad", actividadDAO.getActividades());
			DropDownChoice<SistemaExterno> sistemaExt = new DropDownChoice<SistemaExterno>("sistemaExterno");
			TextArea<String> nota = new TextArea<String>("nota");
			TextField<Double> duracion = new TextField<Double>("duracion");
			TextField<String> ticketBZ = new TextField<String>("ticketBZ");
			TextField<String> ticketExt = new TextField<String>("ticketExterno");
			
			AjaxButton submit = new AjaxButton("submit",this) {
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
