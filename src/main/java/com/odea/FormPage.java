package com.odea;


import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

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
	private DropDownChoice<Actividad> comboActividad;

	
	public FormPage() {
		
		super();
		
		EntradaForm form = new EntradaForm("form"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {

				//Subject subject = SecurityUtils.getSubject();
				//usuario = usuarioDAO.getUsuario(subject.getPrincipal().toString());
				
				Entrada e = form.getModelObject();
				//e.setUsuario(usuario);
				FormPage.this.entradaDAO.agregarEntrada(e);
			}
			
		};

		add(form);	
	
	}

	public abstract class EntradaForm extends Form<Entrada> {
		IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(new Entrada());
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);
			
			comboActividad = new DropDownChoice<Actividad>("actividad");

			DropDownChoice<Proyecto> comboProyecto = new DropDownChoice<Proyecto>("proyecto",  proyectoDAO.getProyectos()){
				
				protected boolean wantOnSelectionChangedNotifications() {
                    return true;
				}
            
				@Override
            	protected void onSelectionChanged(final Proyecto newSelection) {
                    comboActividad.setChoices(actividadDAO.getActividades(newSelection));
				}
				
			};
			
			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("1");
			sistExt.add("2");
			
			DropDownChoice<String> sistemaExt = new DropDownChoice<String>("sistemaExterno", sistExt);
			TextArea<String> nota = new TextArea<String>("nota");
			TextField<Double> duracion = new TextField<Double>("duracion");
			TextField<String> ticketBZ = new TextField<String>("ticketBZ");
			TextField<String> ticketExt = new TextField<String>("ticketExterno");
			
			
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
