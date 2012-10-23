package com.odea;

import java.awt.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.dao.ActividadDAO;
import com.odea.dao.ProyectoDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.SistemaExterno;
import com.visural.wicket.behavior.dateinput.DateInputBehavior;

public class FormPage extends WebPage {
	
	@SpringBean
	private ProyectoDAO proyectoDAO;
	public ActividadDAO actividadDAO;
	private static final long serialVersionUID = 1L;
	List actividades = new List();
	
	
	public FormPage() {
		super();
		
		
		
		Form<Entrada> form = new Form<Entrada>("form"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				
            }
		};
		
		DropDownChoice<Proyecto> comboProyecto = new DropDownChoice<Proyecto>("proyecto",  proyectoDAO.getProyectos());
		DropDownChoice<Actividad> comboActividad = new DropDownChoice<Actividad>("actividad");
		DropDownChoice<SistemaExterno> sistemaExt = new DropDownChoice<SistemaExterno>("sistemaExterno");
		TextArea<String> nota = new TextArea<String>("nota");
		TextField<Double> duracion = new TextField<Double>("duracion");
		TextField<String> ticketBZ = new TextField<String>("ticketBZ");
		TextField<String> ticketExt = new TextField<String>("ticketExterno");
		Button submit = new Button("submit");
		
		Component fecha = new TextField("fecha");
//		Component fecha = new Component("fecha") {
//			
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			protected void onRender() {
//				// TODO Auto-generated method stub
//				
//			}
//		};
//		
		
		form.add(comboProyecto);
		form.add(comboActividad);
		form.add(duracion);
		form.add(fecha);
		form.add(nota);
		form.add(ticketBZ);
		form.add(sistemaExt);
		form.add(ticketExt);
		form.add(submit);
		
		add(form);
	}
	
	
}
