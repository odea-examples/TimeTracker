package com.odea;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.odea.domain.Feriado;
import com.odea.domain.FormHoras;
import com.odea.domain.Usuario;
import com.odea.domain.UsuarioListaHoras;
import com.odea.services.DAOService;

public class VistaHorasPage extends BasePage{
	
	private static final long serialVersionUID = 1L;

	@SpringBean
	private DAOService daoService;
	
	private Usuario usuario;
	private DecimalFormat decimal = new DecimalFormat("#0.00");
	private double[] totales;
	
	private IModel<List<UsuarioListaHoras>> lstUsuariosModel;
	private IModel<List<UsuarioListaHoras>> lstUsuariosEnRojoModel;
	private IModel<String> labelDesdeModel;
	private IModel<List<String>> fechasModel;
	private IModel<FormHoras> horasUsuarioModel;
	
	private WebComponent titulos;
	private WebComponent totalesHtml;
	private WebMarkupContainer listViewContainer;
	private WebMarkupContainer radioContainer;
	
	private LocalDate fechaActual = new LocalDate();
	private Date desde = fechaActual.withDayOfMonth(1).toDateTimeAtStartOfDay().toDate();
	private Date hasta = fechaActual.plusMonths(1).withDayOfMonth(1).minusDays(1).toDateTimeAtStartOfDay().toDate();
	
	private String sectorGlobal= "Todos";
	
	public VistaHorasPage(){
		
		final Subject subject = SecurityUtils.getSubject();
		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());
        
        this.labelDesdeModel= new LoadableDetachableModel<String>() {

			@Override
			protected String load() {
				return desde.toString();
			}
		};
		
		this.fechasModel = new LoadableDetachableModel<List<String>>() {

			@Override
			protected List<String> load() {
				
				List<String> fechas = new ArrayList<String>();
				
				for (int j = 2005; j < 2014; j++) {	
					for (int i = 1; i < 13; i++) {
						fechas.add(i+"/"+j);
					}
				}
				
				return fechas;
			}
		};
				
		this.lstUsuariosModel = new LoadableDetachableModel<List<UsuarioListaHoras>>() { 
	        @Override
	        protected List<UsuarioListaHoras> load() {
	          	return daoService.obtenerHorasUsuarios(desde, hasta, VistaHorasPage.this.sectorGlobal);
	        }
	    };
	    
	    this.lstUsuariosEnRojoModel = new LoadableDetachableModel<List<UsuarioListaHoras>>() {

			@Override
			protected List<UsuarioListaHoras> load() {
//				List<UsuarioListaHoras> devolver = daoService.obtenerHorasUsuarios(desde, hasta,VistaHorasPage.this.sectorGlobal);
				List<UsuarioListaHoras> devolver = VistaHorasPage.this.lstUsuariosModel.getObject();
				List<UsuarioListaHoras> itemsToRemove = new ArrayList<UsuarioListaHoras>();
				for (UsuarioListaHoras usuarioHoras : devolver) {
					if(!usuarioHoras.tieneDiaMenorDedicacion(VistaHorasPage.this.desde)){
						itemsToRemove.add(usuarioHoras);
					}
				}
				devolver.removeAll(itemsToRemove);
				return devolver;
			}
	    	
		};
		
		VistaHorasForm form = new VistaHorasForm("form");
        form.setOutputMarkupId(true);
        add(form);
		
		List<Feriado> feriados = daoService.getFeriados();
    	final List<Date> fechaFeriados = new ArrayList<Date>();
    	for (Feriado feriado : feriados) {
			fechaFeriados.add(feriado.getFecha());
		}
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		totales = new double [31];
//		for (int i = 0; i < 32; i++) {
//			totales[i]= new Double(0);
//		}
	    final PageableListView<UsuarioListaHoras> usuariosHorasListView = new PageableListView<UsuarioListaHoras>("tabla", this.lstUsuariosModel, 1000) {

			private static final long serialVersionUID = 1L;
			
	
			@Override
	        protected void populateItem(ListItem<UsuarioListaHoras> item) {
	           	final UsuarioListaHoras usuarioHoras = item.getModel().getObject();   
//	           	
//	           	if((item.getIndex() % 2) == 0){
//	           		item.add(new AttributeModifier("class","odd"));
//	           	}
            	String nombreCorregido= daoService.getNombreApellido(usuarioHoras.getUsuario()).replaceAll("Ã³","ó").replaceAll("Ã©","é").replaceAll("Ã±","ñ").replaceAll("Ã¡","á").replaceAll("Ã­","í");
	           	
            	Label nombre = new Label("apellidoNombre",nombreCorregido);
            	item.add(nombre);
            	Map<Date, Double> colHoras = new HashMap<Date, Double>();
            	colHoras.putAll(usuarioHoras.getDiaHoras());
            	
            	
				
            	LocalDate diaActual = new LocalDate(VistaHorasPage.this.desde);
            	Double totalHoras = new Double(0);
            	for (int j = 1; j <= 31; j++) {
            		Label lbHoras;
            		Double horasDia = new Double(0);
            		
            		horasDia = colHoras.get(diaActual.toDate());
            		if(horasDia != null) {    
            			totales[j-1]=totales[j-1]+horasDia;
            			String mensaje = decimal.format(horasDia);
            			totalHoras = totalHoras+horasDia;
            			lbHoras = new Label("contenidoDia" + j, Model.of(mensaje));
            			lbHoras.add(new AttributeModifier("style", Model.of("display: block; ")));
            			if(fechaFeriados.contains(diaActual.toDate()) || diaActual.getDayOfWeek()==DateTimeConstants.SATURDAY || diaActual.getDayOfWeek()==DateTimeConstants.SUNDAY ){
            				lbHoras.add(new AttributeAppender("style", Model.of("background-color: rgb(223, 223, 223); height:100%;")));
            				//background-clip: border-box; background-color: blue; color: white; border: 5px solid blue; border-radius: 5px;
            			}
            			if (horasDia < usuarioHoras.getDedicacion()) {
            				lbHoras.add(new AttributeAppender("style", Model.of("color:red;")));
            			}
            		}else if(fechaFeriados.contains(diaActual.toDate()) || diaActual.getDayOfWeek()==DateTimeConstants.SATURDAY || diaActual.getDayOfWeek()==DateTimeConstants.SUNDAY ){
            			lbHoras = new Label("contenidoDia" + j, "0");
            			lbHoras.add(new AttributeModifier("style", Model.of("background-color:rgb(223, 223, 223); height:100%;")));
            		}else{
            			lbHoras = new Label("contenidoDia" + j, "0");
            		}
            		
            		item.add(lbHoras);
            		diaActual = diaActual.plusDays(1);
            	}
//            	System.out.println(totalHoras);
            	//Model.of(totalHoras)
            	item.add(new Label("totalPersona",Model.of(totalHoras)));
            };
            
            	
		};
		//html dinamico
	    this.titulos = new WebComponent("tituloHtml"){
			@Override
			public void onComponentTagBody(MarkupStream markupStream,ComponentTag openTag) {
				Response response = getRequestCycle().getResponse();
				String respuesta= "";
				respuesta+="<th class='tablaTitulo' scope='col'>Usuarios</th>";
				LocalDate diaActual = new LocalDate(VistaHorasPage.this.desde);
				for(int i = 1;i<32;i++){
					
					respuesta+="<th class='tablaTitulo' scope='col' >"+ diaActual.getDayOfMonth()+"</th>";
					diaActual = diaActual.plusDays(1);
				}
				respuesta+="<th class='tablaTitulo' scope='col' > Total </th>";
                response.write(respuesta);
			}
	    	
        };
        
        this.totalesHtml = new WebComponent("totalesHtml"){
        	public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
        		Response response = getRequestCycle().getResponse();
				String respuesta= "";
				respuesta+="<td class='tablaTitulo' scope='col'>Totales:</td>";
				LocalDate diaActual = new LocalDate(VistaHorasPage.this.desde);
				for(int i = 1;i<32;i++){
					
					respuesta+="<td class='tablaTitulo' scope='col' >"+ decimal.format(VistaHorasPage.this.totales[i-1]) +"</td>";
					diaActual = diaActual.plusDays(1);
				}
				respuesta+="<td class='tablaTitulo' scope='col' >  </td>";
                response.write(respuesta);
        		for (int i = 0; i < 31; i++) {
        			totales[i]= new Double(0);
        		}
                
        	};
        };
        
        this.listViewContainer.add(titulos);
        this.listViewContainer.add(totalesHtml);
		this.listViewContainer.add(usuariosHorasListView);
//		this.listViewContainer.add(new AjaxPagingNavigator("navigator", usuariosHorasListView));
		add(listViewContainer);
		
		radioContainer = new WebMarkupContainer("radioContainerUsuarios");
		radioContainer.setOutputMarkupId(true);
		
		RadioGroup<String> radiog = new RadioGroup<String>("radioGroup", new Model<String>());
		
		Radio<String> mostrarTodas = new Radio<String>("mostrarTodas", Model.of("Todos"));
		Radio<String> mostrarEnRojo = new Radio<String>("mostrarEnRojo", Model.of("Menor que dedicación"));
		
		mostrarTodas.add(new AjaxEventBehavior("onchange") {
           
            protected void onEvent(AjaxRequestTarget target) {
            	VistaHorasPage.this.lstUsuariosModel.detach();
            	VistaHorasPage.this.lstUsuariosEnRojoModel.detach();
                usuariosHorasListView.setModel(VistaHorasPage.this.lstUsuariosModel);
                target.add(VistaHorasPage.this.listViewContainer);
            }
           
        });
		
		mostrarEnRojo.add(new AjaxEventBehavior("onchange") {
	           
            protected void onEvent(AjaxRequestTarget target) {
            	VistaHorasPage.this.lstUsuariosModel.detach();
            	VistaHorasPage.this.lstUsuariosEnRojoModel.detach();
                usuariosHorasListView.setModel(VistaHorasPage.this.lstUsuariosEnRojoModel);
                target.add(VistaHorasPage.this.listViewContainer);
            }
           
        });
		
		radiog.add(mostrarTodas);
		radiog.add(mostrarEnRojo);
		radioContainer.add(radiog);
		
		add(radioContainer);
		
	}
	
	public class VistaHorasForm extends Form<FormHoras>{
		
		public IModel<FormHoras> modeloHoras = new CompoundPropertyModel<FormHoras>(new FormHoras());
		public LocalDate fechaActual = new LocalDate();
		public DropDownChoice<String> sector;
		public DropDownChoice<String> fechaDesde;
		
		
		public VistaHorasForm(String id) {
			super(id);
			this.setDefaultModel(modeloHoras);
			this.setOutputMarkupId(true);
			
			this.fechaDesde= new DropDownChoice<String>("fechaDesde", Model.of("7/2013") ,VistaHorasPage.this.fechasModel);
			
			this.fechaDesde.add(new AjaxFormComponentUpdatingBehavior("onchange"){

				@Override
				protected void onUpdate(AjaxRequestTarget target) {

					VistaHorasPage.this.lstUsuariosModel.detach();
					VistaHorasPage.this.lstUsuariosEnRojoModel.detach();
					String seleccionado = fechaDesde.getModelObject();
					List<String> fechas = Arrays.asList(seleccionado.split("/"));
					int dia = 1;
					int mes = Integer.parseInt(fechas.get(0));
					int anio = Integer.parseInt(fechas.get(1));
					VistaHorasPage.this.desde = new LocalDate(anio,mes,dia).toDate();
					target.add(VistaHorasPage.this.listViewContainer);
					
				}
				
			});
			this.fechaDesde.setOutputMarkupId(true);
			List<String> sectores = new ArrayList<String>();
			sectores.add("TI");
			sectores.add("E&P");
			sectores.add("Administracion");
			sectores.add("ExOdea");
			sectores.add("Todos");
			sectores.add("Ninguno");
			sector = new DropDownChoice<String>("sector",Model.of("Todos"),sectores);
			sector.add(new AjaxFormComponentUpdatingBehavior("onchange"){

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					VistaHorasPage.this.lstUsuariosModel.detach();
					VistaHorasPage.this.lstUsuariosEnRojoModel.detach();
					VistaHorasPage.this.sectorGlobal= sector.getModelObject();
					target.add(VistaHorasPage.this.listViewContainer); 
				}
				
			});
			add(fechaDesde);
			add(sector);
		}
		
		
	}
	
}
