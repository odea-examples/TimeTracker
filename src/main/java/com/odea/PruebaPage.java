package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.odea.components.slickGrid.SlickGrid;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;
import com.odea.components.slickGrid.Data;

public class PruebaPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public PruebaPage() {
		
//		IModel<Actividad> actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
//            @Override
//            protected Actividad load() {
//                return new Actividad(1,"actividad1");
//            }
//        });
//		
//		Formulario form = new Formulario("form", actividadModel);
//		
//		add(form);


	
//	public class Formulario extends Form<Actividad> {
//
//		public Formulario(String id, IModel<Actividad> model) {
//			super(id, model);
//			
//			
//			LoadableDetachableModel originalsModel = new LoadableDetachableModel() {
//				@Override
//				protected Object load() {
//					return new ArrayList<Actividad>();
//				}							
//	        };
//		
//	    	LoadableDetachableModel destinationsModel = new LoadableDetachableModel() {
//				@Override
//				protected Object load() {
//					return daoService.getActividades();
//				}							
//	        };
//		
//	        
//			DualMultipleChoice<Actividad> dual = new DualMultipleChoice<Actividad>("dual", originalsModel, destinationsModel);
//			
//			add(dual);
//			
//		}
	}
	
	public class Columna {
		String id;
		String name;
		int width;
		int minWidth;
		int maxWidth;
		String cssClass;
		String field;
		String formatter;
		String editor;
		String validator;
		public Columna(String id, String name, int width, int minWidth, int maxWidth,
				String cssClass, String field, String formatter, String editor,
				String validator) {
			super();
			this.id = id;
			this.name = name;
			this.width = width;
			this.minWidth = minWidth;
			this.maxWidth = maxWidth;
			this.cssClass = cssClass;
			this.field = field;
			this.formatter = formatter;
			this.editor = editor;
			this.validator = validator;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * @param width the width to set
		 */
		public void setWidth(int width) {
			this.width = width;
		}
		/**
		 * @return the minWidth
		 */
		public int getMinWidth() {
			return minWidth;
		}
		/**
		 * @param minWidth the minWidth to set
		 */
		public void setMinWidth(int minWidth) {
			this.minWidth = minWidth;
		}
		/**
		 * @return the maxWidth
		 */
		public int getMaxWidth() {
			return maxWidth;
		}
		/**
		 * @param maxWidth the maxWidth to set
		 */
		public void setMaxWidth(int maxWidth) {
			this.maxWidth = maxWidth;
		}
		/**
		 * @return the cssClass
		 */
		public String getCssClass() {
			return cssClass;
		}
		/**
		 * @param cssClass the cssClass to set
		 */
		public void setCssClass(String cssClass) {
			this.cssClass = cssClass;
		}
		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}
		/**
		 * @param field the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}
		/**
		 * @return the formatter
		 */
		public String getFormatter() {
			return formatter;
		}
		/**
		 * @param formatter the formatter to set
		 */
		public void setFormatter(String formatter) {
			this.formatter = formatter;
		}
		/**
		 * @return the editor
		 */
		public String getEditor() {
			return editor;
		}
		/**
		 * @param editor the editor to set
		 */
		public void setEditor(String editor) {
			this.editor = editor;
		}
		/**
		 * @return the validator
		 */
		public String getValidator() {
			return validator;
		}
		/**
		 * @param validator the validator to set
		 */
		public void setValidator(String validator) {
			this.validator = validator;
		}
	}
	
	public class Data {
		String id;
		String duration;
		String actividad;
		String proyecto;
		String fecha;
		String ticket;
		String ticketExt;
		String sistExt;
		String descripcion;
		
		public Data(String id, String duration, String actividad,
				String proyecto, String fecha, String ticket, String ticketExt,
				String sistExt, String descripcion) {
			super();
			this.id = id;
			this.duration = duration;
			this.actividad = actividad;
			this.proyecto = proyecto;
			this.fecha = fecha;
			this.ticket = ticket;
			this.ticketExt = ticketExt;
			this.sistExt = sistExt;
			this.descripcion = descripcion;
		}
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * @return the duration
		 */
		public String getDuration() {
			return duration;
		}
		/**
		 * @param duration the duration to set
		 */
		public void setDuration(String duration) {
			this.duration = duration;
		}
		/**
		 * @return the actividad
		 */
		public String getActividad() {
			return actividad;
		}
		/**
		 * @param actividad the actividad to set
		 */
		public void setActividad(String actividad) {
			this.actividad = actividad;
		}
		/**
		 * @return the proyecto
		 */
		public String getProyecto() {
			return proyecto;
		}
		/**
		 * @param proyecto the proyecto to set
		 */
		public void setProyecto(String proyecto) {
			this.proyecto = proyecto;
		}
		/**
		 * @return the fecha
		 */
		public String getFecha() {
			return fecha;
		}
		/**
		 * @param fecha the fecha to set
		 */
		public void setFecha(String fecha) {
			this.fecha = fecha;
		}
		/**
		 * @return the ticket
		 */
		public String getTicket() {
			return ticket;
		}
		/**
		 * @param ticket the ticket to set
		 */
		public void setTicket(String ticket) {
			this.ticket = ticket;
		}
		/**
		 * @return the ticketExt
		 */
		public String getTicketExt() {
			return ticketExt;
		}
		/**
		 * @param ticketExt the ticketExt to set
		 */
		public void setTicketExt(String ticketExt) {
			this.ticketExt = ticketExt;
		}
		/**
		 * @return the sistExt
		 */
		public String getSistExt() {
			return sistExt;
		}
		/**
		 * @param sistExt the sistExt to set
		 */
		public void setSistExt(String sistExt) {
			this.sistExt = sistExt;
		}
		/**
		 * @return the descripcion
		 */
		public String getDescripcion() {
			return descripcion;
		}
		/**
		 * @param descripcion the descripcion to set
		 */
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
	}
}
