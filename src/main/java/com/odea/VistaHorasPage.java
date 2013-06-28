package com.odea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.domain.Feriado;
import com.odea.domain.FormHoras;
import com.odea.domain.Usuario;
import com.odea.domain.UsuarioListaHoras;
import com.odea.services.DAOService;

public class VistaHorasPage extends BasePage{
	
	private static final long serialVersionUID = 1L;

	@SpringBean
	private transient DAOService daoService;
	
	public Usuario usuario;
	public IModel<List<UsuarioListaHoras>> lstUsuariosModel;
	public WebMarkupContainer listViewContainer;
	public IModel<FormHoras> horasUsuarioModel;
	public LocalDate fechaActual = new LocalDate();
	public Date desde = fechaActual.withDayOfMonth(1).toDateTimeAtStartOfDay().toDate();
	public Date hasta = fechaActual.plusMonths(1).withDayOfMonth(1).minusDays(1).toDateTimeAtStartOfDay().toDate();
	
	public VistaHorasPage(){
		
		final Subject subject = SecurityUtils.getSubject();
		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());

		VistaHorasForm form = new VistaHorasForm("form");
        form.setOutputMarkupId(true);
        add(form);
				
		this.lstUsuariosModel = new LoadableDetachableModel<List<UsuarioListaHoras>>() { 
	        @Override
	        protected List<UsuarioListaHoras> load() {
	          	return daoService.obtenerHorasUsuarios(desde, hasta);
	        }
	    };
        
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
	    final PageableListView<UsuarioListaHoras> usuariosHorasListView = new PageableListView<UsuarioListaHoras>("tabla", this.lstUsuariosModel, 1000) {

			private static final long serialVersionUID = 1L;
			
	
			@Override
	        protected void populateItem(ListItem<UsuarioListaHoras> item) {
	           	final UsuarioListaHoras usuarioHoras = item.getModel().getObject();   
//	           	
//	           	if((item.getIndex() % 2) == 0){
//	           		item.add(new AttributeModifier("class","odd"));
//	           	}
            	
	           	
            	Label nombre = new Label("apellidoNombre",usuarioHoras.getUsuario().getNombre());
            	item.add(nombre);
            	
            	Map<Date, Double> colHoras = new HashMap<Date, Double>();
            	colHoras.putAll(usuarioHoras.getDiaHoras());
            	
            	List<Feriado> feriados = daoService.getFeriados();
            	List<Date> fechaFeriados = new ArrayList<Date>();
				for (Feriado feriado : feriados) {
					fechaFeriados.add(feriado.getFecha());
				}
            	LocalDate diaActual = new LocalDate(VistaHorasPage.this.desde).withDayOfMonth(1);
            	
            	for (int j = 1; j <= 31; j++) {
            		Label lbHoras;
            		Double horasDia = new Double(0);
            		
            		horasDia = colHoras.get(diaActual.toDate());
            		
            		if(horasDia != null) {    			
            			lbHoras = new Label("contenidoDia" + j, Double.toString(horasDia));
            			lbHoras.add(new AttributeModifier("style", Model.of("display: block; ")));
            			if(fechaFeriados.contains(diaActual.toDate()) || diaActual.getDayOfWeek()==DateTimeConstants.SATURDAY || diaActual.getDayOfWeek()==DateTimeConstants.SUNDAY ){
//            				lbHoras.add(new AttributeAppender("style", Model.of("background-color:gray;")));
            				//background-clip: border-box; background-color: blue; color: white; border: 5px solid blue; border-radius: 5px;
            			}
            			if (horasDia < usuarioHoras.getDedicacion()) {
            				lbHoras.add(new AttributeAppender("style", Model.of("color:red;")));
            			}
            		}else if(fechaFeriados.contains(diaActual.toDate()) || diaActual.getDayOfWeek()==DateTimeConstants.SATURDAY || diaActual.getDayOfWeek()==DateTimeConstants.SUNDAY ){
            			lbHoras = new Label("contenidoDia" + j, "0");
//            			lbHoras.add(new AttributeModifier("style", Model.of("background-color:gray;")));
            		}else{
            			lbHoras = new Label("contenidoDia" + j, "0");
            		}
            		
            		item.add(lbHoras);
            		diaActual = diaActual.plusDays(1);
            	}
            	
            	
            	/*
            	int i = 1;
            	while (i < 10) {
            		Label dia = new Label(Integer.toString(i),"lalalalalal");
            		dia.add(new AttributeModifier("style",Model.of("display:block")));
            		item.add(dia);
					i++;
				}
            	*/
            	
//            	Day asd = new Day(24,6,2013);
//            	Date asd2 = new Date(asd.getFirstMillisecond());
//            	
//            	if(usuarioHoras.getDiaHoras().containsKey(asd2)){
//            	Label dia1 = new Label("1",usuarioHoras.getDiaHoras().get(asd2).toString());
//            	item.add(dia1);
//            	dia1.add(new AttributeModifier("style", Model.of("display:block")));
//            	}else{
//            		Label dia1 = new Label("1","lalalallalalala");
//                	item.add(dia1);
//                	dia1.add(new AttributeModifier("style", Model.of("display:block")));
//            	}
//            	item.add(new Label("2","lalalala"));
//            	item.add(new Label("3","lalalala"));
//            	item.add(new Label("4","lalalala"));
//            	item.add(new Label("5","lalalala"));
//            	item.add(new Label("6","lalalala"));
//            	item.add(new Label("7","lalalala"));
//            	item.add(new Label("8","lalalala"));
//            	//aca esta, todos como el 9
//            	Label dia9 = new Label("9",Integer.toString(usuarioHoras.getDedicacion()));
//            	item.add(dia9);
//            	dia9.add(new AttributeModifier("style", Model.of("display:block")));
            	
            };
            
            	
		};
		this.listViewContainer.add(usuariosHorasListView);
		
		for(int i = 1; i <= 31; i++)
		{
			this.listViewContainer.add(new Label("dia" + i, Integer.toString(i)));
		}
		
		
		this.listViewContainer.add(new AjaxPagingNavigator("navigator", usuariosHorasListView));
		add(listViewContainer);
		
	}
	
	public class VistaHorasForm extends Form<FormHoras>{
		
		public IModel<FormHoras> modeloHoras = new CompoundPropertyModel<FormHoras>(new FormHoras());
		public LocalDate fechaActual = new LocalDate();
		public YuiDatePicker fechaDesde;
		public YuiDatePicker fechaHasta;
		public DropDownChoice<String> sector;
		
		
		public VistaHorasForm(String id) {
			super(id);
			this.setDefaultModel(modeloHoras);
			this.setOutputMarkupId(true);
			// TODO Auto-generated constructor stub
			fechaDesde= new YuiDatePicker("fechaDesde") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					String json = selectedDate;
					List<String> campos = Arrays.asList(json.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));
					desde = new LocalDate(anio,mes,dia).toDate();
					
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					// TODO Auto-generated method stub
					return new DatePickerDTO();
				}
			};
			fechaDesde.setOutputMarkupId(true);
			
			fechaHasta = new YuiDatePicker("fechaHasta") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					String json = selectedDate;
					List<String> campos = Arrays.asList(json.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));
					hasta = new LocalDate(anio,mes,dia).toDate();
					
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					// TODO Auto-generated method stub
					return new DatePickerDTO();
				}
			};
			fechaHasta.setOutputMarkupId(true);
			
			sector = new DropDownChoice<String>("sector");
			
			AjaxButton submit = new AjaxButton("submit") {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					lstUsuariosModel.setObject(daoService.obtenerHorasUsuarios(fechaDesde.getModelObject(),fechaHasta.getModelObject()));
					target.add(listViewContainer);
				}
				
			};
			submit.setOutputMarkupId(true);
			add(fechaDesde);
			add(fechaHasta);
			add(sector);
			add(submit);
		}
		
		
	}
	
}
