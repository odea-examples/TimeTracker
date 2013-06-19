package com.odea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import com.google.common.collect.MapConstraint;
import com.google.gson.Gson;
import com.odea.components.slickGrid.SlickGrid;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class PruebaPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	static final String A = "a";
	static final String B = "b";
	static final String C = "c";
	
	public PruebaPage() {
		        final List<String> keys = Arrays.asList(A, B,C);
		        final List<Map<String, Integer>> data = Arrays.asList(
		            map(A, 1).put(B, 11).put(C, 22).toMap(),
		            map(A, 2).put(B, 12).put(C, 16).toMap(),
		            map(A, 3).put(B, 13).put(C, 32).toMap(),
		            map(A, 4).put(B, 14).put(C, 22).toMap(),
		            map(A, 5).put(B, 15).put(C, 7).put(B,4).toMap(),
		            map(A, 6).put(B, 16).toMap(),
		            map(A, 7).put(B, 17).put(C, 6).toMap(),
		            map(A, 8).put(B, 18).put(C, 3).toMap(),
		            map(A, 9).put(B, 19).put(C, 4).toMap());

		        // Using a DefaultDataTable
		        ISortableDataProvider dataProvider = new SortableDataProvider() {
		            public Iterator iterator(int first, int count) {
		                int start = Math.min(0, first);
		                int end = Math.min(data.size(), start + count);
		                return data.subList(start, end).iterator();
		            }
		            public long size() {
		                return data.size();
		            }
		            public IModel model(Object object) {
		                return new CompoundPropertyModel(object);
		            }
					@Override
					public Iterator iterator(long first, long count) {
						long start = Math.min(0, first);
		                long end = Math.min(data.size(), start + count);
		                return data.subList((int)start,(int)end).iterator();
					}
		        };
		        List columns = new ArrayList();
		        for (String key : keys)
		            columns.add(new PropertyColumn(Model.of(key), key));
		        add(new DefaultDataTable("dataTable", columns, dataProvider, 20));

		        // Using a nested ListViews
	}

		    // to make building the data structure a little more fun :)
		    MapBuilder<String, Integer> map(String key, Integer value) {
		        return new MapBuilder<String, Integer>().put(key, value);
		    }
		    class MapBuilder<K, V> {
		        Map<K, V> map = new HashMap<K, V>();
		        MapBuilder<K, V> put(K key, V value) {
		            map.put(key, value);
		            return this;
		        }
		        Map<K, V> toMap() {
		            return map;
		        }
		    }
		
		
		
		
		
		
		
		
		
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
//	}
	
//	public class Columna {
//		String id;
//		String name;
//		int width;
//		int minWidth;
//		int maxWidth;
//		String cssClass;
//		String field;
//		String formatter;
//		String editor;
//		String validator;
//		public Columna(String id, String name, int width, int minWidth, int maxWidth,
//				String cssClass, String field, String formatter, String editor,
//				String validator) {
//			super();
//			this.id = id;
//			this.name = name;
//			this.width = width;
//			this.minWidth = minWidth;
//			this.maxWidth = maxWidth;
//			this.cssClass = cssClass;
//			this.field = field;
//			this.formatter = formatter;
//			this.editor = editor;
//			this.validator = validator;
//		}
//		/**
//		 * @return the id
//		 */
//		public String getId() {
//			return id;
//		}
//		/**
//		 * @param id the id to set
//		 */
//		public void setId(String id) {
//			this.id = id;
//		}
//		/**
//		 * @return the name
//		 */
//		public String getName() {
//			return name;
//		}
//		/**
//		 * @param name the name to set
//		 */
//		public void setName(String name) {
//			this.name = name;
//		}
//		/**
//		 * @return the width
//		 */
//		public int getWidth() {
//			return width;
//		}
//		/**
//		 * @param width the width to set
//		 */
//		public void setWidth(int width) {
//			this.width = width;
//		}
//		/**
//		 * @return the minWidth
//		 */
//		public int getMinWidth() {
//			return minWidth;
//		}
//		/**
//		 * @param minWidth the minWidth to set
//		 */
//		public void setMinWidth(int minWidth) {
//			this.minWidth = minWidth;
//		}
//		/**
//		 * @return the maxWidth
//		 */
//		public int getMaxWidth() {
//			return maxWidth;
//		}
//		/**
//		 * @param maxWidth the maxWidth to set
//		 */
//		public void setMaxWidth(int maxWidth) {
//			this.maxWidth = maxWidth;
//		}
//		/**
//		 * @return the cssClass
//		 */
//		public String getCssClass() {
//			return cssClass;
//		}
//		/**
//		 * @param cssClass the cssClass to set
//		 */
//		public void setCssClass(String cssClass) {
//			this.cssClass = cssClass;
//		}
//		/**
//		 * @return the field
//		 */
//		public String getField() {
//			return field;
//		}
//		/**
//		 * @param field the field to set
//		 */
//		public void setField(String field) {
//			this.field = field;
//		}
//		/**
//		 * @return the formatter
//		 */
//		public String getFormatter() {
//			return formatter;
//		}
//		/**
//		 * @param formatter the formatter to set
//		 */
//		public void setFormatter(String formatter) {
//			this.formatter = formatter;
//		}
//		/**
//		 * @return the editor
//		 */
//		public String getEditor() {
//			return editor;
//		}
//		/**
//		 * @param editor the editor to set
//		 */
//		public void setEditor(String editor) {
//			this.editor = editor;
//		}
//		/**
//		 * @return the validator
//		 */
//		public String getValidator() {
//			return validator;
//		}
//		/**
//		 * @param validator the validator to set
//		 */
//		public void setValidator(String validator) {
//			this.validator = validator;
//		}
//	}
//	
//	public class Data {
//		String id;
//		String duration;
//		String actividad;
//		String proyecto;
//		String fecha;
//		String ticket;
//		String ticketExt;
//		String sistExt;
//		String descripcion;
//		
//		public Data(String id, String duration, String actividad,
//				String proyecto, String fecha, String ticket, String ticketExt,
//				String sistExt, String descripcion) {
//			super();
//			this.id = id;
//			this.duration = duration;
//			this.actividad = actividad;
//			this.proyecto = proyecto;
//			this.fecha = fecha;
//			this.ticket = ticket;
//			this.ticketExt = ticketExt;
//			this.sistExt = sistExt;
//			this.descripcion = descripcion;
//		}
//		/**
//		 * @return the id
//		 */
//		public String getId() {
//			return id;
//		}
//		/**
//		 * @param id the id to set
//		 */
//		public void setId(String id) {
//			this.id = id;
//		}
//		/**
//		 * @return the duration
//		 */
//		public String getDuration() {
//			return duration;
//		}
//		/**
//		 * @param duration the duration to set
//		 */
//		public void setDuration(String duration) {
//			this.duration = duration;
//		}
//		/**
//		 * @return the actividad
//		 */
//		public String getActividad() {
//			return actividad;
//		}
//		/**
//		 * @param actividad the actividad to set
//		 */
//		public void setActividad(String actividad) {
//			this.actividad = actividad;
//		}
//		/**
//		 * @return the proyecto
//		 */
//		public String getProyecto() {
//			return proyecto;
//		}
//		/**
//		 * @param proyecto the proyecto to set
//		 */
//		public void setProyecto(String proyecto) {
//			this.proyecto = proyecto;
//		}
//		/**
//		 * @return the fecha
//		 */
//		public String getFecha() {
//			return fecha;
//		}
//		/**
//		 * @param fecha the fecha to set
//		 */
//		public void setFecha(String fecha) {
//			this.fecha = fecha;
//		}
//		/**
//		 * @return the ticket
//		 */
//		public String getTicket() {
//			return ticket;
//		}
//		/**
//		 * @param ticket the ticket to set
//		 */
//		public void setTicket(String ticket) {
//			this.ticket = ticket;
//		}
//		/**
//		 * @return the ticketExt
//		 */
//		public String getTicketExt() {
//			return ticketExt;
//		}
//		/**
//		 * @param ticketExt the ticketExt to set
//		 */
//		public void setTicketExt(String ticketExt) {
//			this.ticketExt = ticketExt;
//		}
//		/**
//		 * @return the sistExt
//		 */
//		public String getSistExt() {
//			return sistExt;
//		}
//		/**
//		 * @param sistExt the sistExt to set
//		 */
//		public void setSistExt(String sistExt) {
//			this.sistExt = sistExt;
//		}
//		/**
//		 * @return the descripcion
//		 */
//		public String getDescripcion() {
//			return descripcion;
//		}
//		/**
//		 * @param descripcion the descripcion to set
//		 */
//		public void setDescripcion(String descripcion) {
//			this.descripcion = descripcion;
//		}
		
	}
