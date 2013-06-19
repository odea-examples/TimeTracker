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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.collections.ArrayListStack;
import org.apache.wicket.util.convert.converter.DateConverter;
import org.joda.time.LocalDate;
import com.odea.FeriadosPage.FeriadosForm;
import com.odea.behavior.focusOnLoad.FocusOnLoadBehavior;
import com.odea.components.confirmPanel.ConfirmationLink;
import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.dao.ListaHorasDAO;
import com.odea.domain.Actividad;
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
	public IModel<DatePickerDTO> horasUsuarioModel;
	public YuiDatePicker fechaDesde;
	public YuiDatePicker fechaHasta;
	public LocalDate fechaActual = new LocalDate();
	
	public VistaHorasPage(){
		
		final Subject subject = SecurityUtils.getSubject();
		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());

		VistaHorasForm form = new VistaHorasForm("form", horasUsuarioModel){
        	
        	@Override
            protected void onSubmit(AjaxRequestTarget target, VistaHorasForm form) {
                //null
            }
        };
        form.setOutputMarkupId(true);

        add(form);
		
		
		
		
		
		
		
		this.lstUsuariosModel = new LoadableDetachableModel<List<UsuarioListaHoras>>() { 
            @Override
            protected List<UsuarioListaHoras> load() {
//            	List<HorasCargadasPorDia> horasDia = null;
//            	System.out.println(usuario.getIdUsuario());
//            	horasDia = daoService.getHorasDiaras(usuario);
//            	DatePickerDTO dto = new DatePickerDTO(usuario.getNombre(),8, horasDia);
//            	ArrayList<DatePickerDTO> arrdto;
//            	arrdto = new ArrayList<DatePickerDTO>();
//            	System.out.println("aca esta el dto \n\n\n\n\n\n");
//            	System.out.println(dto);
//            	arrdto.add(dto);
//            	System.out.println("\n\n\n\n\n");
//            	System.out.println(arrdto);
//            	System.out.println("esta arriba");
//            	return arrdto;
            	return daoService.obtenerHorasUsuarios();
            	
//            	return null;
            	
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
            	System.out.println("\n\n\n\n\n");
            	System.out.println(new Date().toString());
            	System.out.println(usuarioHoras.getDiaHoras());
            	
            	
            	Label dia1 = new Label("1",usuarioHoras.getDiaHoras().get(new Date()).toString());
            	item.add(dia1);
            	dia1.add(new AttributeModifier("style", Model.of("display:block")));
            	
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
	
	public abstract class VistaHorasForm extends Form<DatePickerDTO> {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2L;


		public VistaHorasForm(String id, IModel<DatePickerDTO> horasUsuarioModel) {
			super(id, horasUsuarioModel);
	        
			DropDownChoice<String> sector= new DropDownChoice<String>("sector");
			//despues del "sector" pones una coma y las opciones.
			
			fechaDesde = new YuiDatePicker("fechaDesde") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					String json = selectedDate;
					List<String> campos = Arrays.asList(json.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));

					VistaHorasPage.this.fechaActual = new LocalDate(anio, mes, dia);
					fechaHasta.setDefaultModelObject(fechaActual.toDate());
					target.add(fechaHasta);
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					DatePickerDTO dto = new DatePickerDTO();
					dto.setDedicacion(0);
					dto.setUsuario("admin");
					Collection<HorasCargadasPorDia> horas = daoService.getFeriadosData();
					dto.setHorasDia(horas);							
					return dto;
				}
			};
			fechaDesde.setOutputMarkupId(true);
			fechaDesde.setDefaultModel(new Model<Serializable>(new Date()));
			fechaHasta = new YuiDatePicker("fechaHasta") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					//fechaDesde.setDefaultModelObject(selectedDate);
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					DatePickerDTO dto = new DatePickerDTO();
					dto.setDedicacion(0);
					dto.setUsuario("admin");
					Collection<HorasCargadasPorDia> horas = null;
					dto.setHorasDia(horas);							
					return dto;
				}
			};
			fechaHasta.setOutputMarkupId(true);
			fechaHasta.setDefaultModel(new Model<Serializable>(new Date()));
	        
	        
	        AjaxButton submit = new AjaxButton("submit") {
	        	@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
	        	//daoservice aceptar busqueda, cambia el modelo, loquesea
	        	target.add(listViewContainer);
	        	VistaHorasForm.this.onSubmit(target, (VistaHorasForm) form);
	        	}
	        };
	        add(sector);
	        add(fechaDesde);
	        add(fechaHasta);	        
			add(submit);
		}	
	    
		
		protected abstract void onSubmit(AjaxRequestTarget target, VistaHorasForm form);
		
	}
	
}
