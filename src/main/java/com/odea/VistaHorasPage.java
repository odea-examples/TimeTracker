package com.odea;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.collections.ArrayListStack;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.eclipse.jetty.ajp.Ajp13Parser.Input;
import org.jfree.data.time.Day;
import org.joda.time.LocalDate;

import com.odea.EntradasPage.EntradaForm;
import com.odea.FeriadosPage.FeriadosForm;
import com.odea.VistaHorasPage.VistaHorasForm;
import com.odea.behavior.focusOnLoad.FocusOnLoadBehavior;
import com.odea.components.confirmPanel.ConfirmationLink;
import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.dao.ListaHorasDAO;
import com.odea.domain.Actividad;
import com.odea.domain.FormHoras;
import com.odea.domain.Proyecto;
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
            	return daoService.obtenerHorasUsuarios(desde,hasta);
            }
        };
        
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
        final PageableListView<UsuarioListaHoras> usuariosHorasListView = new PageableListView<UsuarioListaHoras>("tabla", this.lstUsuariosModel, 10) {

			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<UsuarioListaHoras> item) {
            	final UsuarioListaHoras usuarioHoras = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	Label nombre = new Label("apellidoNombre",usuarioHoras.getUsuario().getNombre());
            	item.add(nombre);
            	usuarioHoras.getDiaHoras().get(new Date());
            	nombre.add(new AttributeModifier("style", Model.of("display:block")));
            	Day asd = new Day(24,6,2013);
            	Date asd2 = new Date(asd.getFirstMillisecond());
            	
            	if(usuarioHoras.getDiaHoras().containsKey(asd2)){
            	Label dia1 = new Label("1",usuarioHoras.getDiaHoras().get(asd2).toString());
            	item.add(dia1);
            	dia1.add(new AttributeModifier("style", Model.of("display:block")));
            	}else{
            		Label dia1 = new Label("1","lalalallalalala");
                	item.add(dia1);
                	dia1.add(new AttributeModifier("style", Model.of("display:block")));
            	}
            	item.add(new Label("2","lalalala"));
            	item.add(new Label("3","lalalala"));
            	item.add(new Label("4","lalalala"));
            	item.add(new Label("5","lalalala"));
            	item.add(new Label("6","lalalala"));
            	item.add(new Label("7","lalalala"));
            	item.add(new Label("8","lalalala"));
            	//aca esta, todos como el 9
            	Label dia9 = new Label("9",Integer.toString(usuarioHoras.getDedicacion()));
            	item.add(dia9);
            	dia9.add(new AttributeModifier("style", Model.of("display:block")));
            	
            };
            
            	
		};
		listViewContainer.add(usuariosHorasListView);
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
					// TODO Auto-generated method stub
					
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
					// TODO Auto-generated method stub
					
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
					System.out.println("logro setear");
					target.add(listViewContainer);
					System.out.println("logro el add");
					System.out.println(lstUsuariosModel.getObject());
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
