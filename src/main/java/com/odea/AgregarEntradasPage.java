package com.odea;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.odea.behavior.noInput.NoInputBehavior;
import com.odea.behavior.numberComma.NumberCommaBehavior;
import com.odea.behavior.onlyNumber.OnlyNumberBehavior;
import com.odea.components.datepicker.DatePickerBehavior;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;
import com.odea.validators.duracion.DurationValidator;


public class AgregarEntradasPage extends BasePage {

	@SpringBean
	private transient DAOService daoService;
	
	private Usuario usuario;
	
	IModel<List<Entrada>> lstEntradasModel;
	IModel<Integer> horasSemanalesModel;

	WebMarkupContainer listViewContainer;
	
	public AgregarEntradasPage() {
		Subject subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}
		
		this.usuario = this.daoService.getUsuario(subject.getPrincipal().toString());
		this.lstEntradasModel = new LoadableDetachableModel<List<Entrada>>() { 
            @Override
            protected List<Entrada> load() {
            	return daoService.getEntradasSemanales(usuario);
            }
        };
        
        this.horasSemanalesModel = new LoadableDetachableModel<Integer>() {
    		@Override
    		protected Integer load() {
    			return daoService.getHorasSemanales(usuario);
    		}
    		
    	}; 
    	

		if(usuario == null){
			this.setResponsePage(LoginPage.class);
		}

		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		
		EntradaForm form = new EntradaForm("form"){
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {
				daoService.agregarEntrada(form.getModelObject(), usuario);
				target.add(listViewContainer);
			}
		};
		
		ListView<Entrada> entradasListView = new ListView<Entrada>("entradas", this.lstEntradasModel) {
            @Override
            protected void populateItem(ListItem<Entrada> item) {
            	Entrada entrada = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	item.add(new Label("fecha_entrada", new Model<Date>(entrada.getFecha())));
                item.add(new Label("proyecto_entrada", entrada.getProyecto().getNombre()));
                item.add(new Label("actividad_entrada", entrada.getActividad().getNombre()));
                item.add(new Label("duracion_entrada", new Model<String>(entrada.getDuracion())));
                item.add(new Label("ticketBZ_entrada", new Model<Integer>(entrada.getTicketBZ())));
            }
        };
        
		Label horasAcumuladas = new Label("horasAcumuladas", this.horasSemanalesModel);

        listViewContainer.setOutputMarkupId(true);
		listViewContainer.add(entradasListView);
		listViewContainer.add(horasAcumuladas);
		add(listViewContainer);
		add(form);	
	
	}

	public abstract class EntradaForm extends Form<Entrada> {
		public IModel<Entrada> entradaModel = new CompoundPropertyModel<Entrada>(new Entrada());
		public DropDownChoice<Actividad> comboActividad;
		public DropDownChoice<Proyecto> comboProyecto; 	
		public TextField<String> ticketExt;
		public DropDownChoice<String> sistemaExterno;
		public TextField<String> duracion;
		public TextField<String> ticketBZ;
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(this.entradaModel);
			

			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("Sistema de Incidencias de YPF");
			sistExt.add("Sistema Geminis de YPF");
			
			
			
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
			
			
			sistemaExterno = new DropDownChoice<String>("sistemaExterno", sistExt);
			sistemaExterno.setLabel(Model.of("Sistema Externo"));
			sistemaExterno.setOutputMarkupId(true);
			sistemaExterno.add(new AjaxFormComponentUpdatingBehavior("onchange"){
				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					if (EntradaForm.this.sistemaExterno.getValue() == "") {
						EntradaForm.this.ticketExt.setEnabled(false);
					} else {
						EntradaForm.this.ticketExt.setEnabled(true);
					}
					target.add(EntradaForm.this.ticketExt);
				}
				
			});
			
			TextArea<String> nota = new TextArea<String>("nota");
			
			duracion = new TextField<String>("duracion");
			duracion.setRequired(true);
			duracion.setOutputMarkupId(true);
			duracion.setLabel(Model.of("Duracion"));
			duracion.add(new NumberCommaBehavior(duracion.getMarkupId()));
			duracion.add(new DurationValidator());


			
			
			ticketBZ = new TextField<String>("ticketBZ");
			ticketBZ.setRequired(true);
			ticketBZ.setOutputMarkupId(true);
			ticketBZ.setLabel(Model.of("Ticket Bugzilla"));
			ticketBZ.add(new OnlyNumberBehavior(ticketBZ.getMarkupId()));
			
			
			ticketExt = new TextField<String>("ticketExterno");
			ticketExt.setLabel(Model.of("ID Ticket Externo"));
			ticketExt.setOutputMarkupId(true);
			ticketExt.setEnabled(false);
			ticketExt.add(new PatternValidator("^[a-z0-9_-]{1,15}$"));
			
			
			TextField<Date> fecha = new TextField<Date>("fecha");
			fecha.setRequired(true);
			fecha.add(new DatePickerBehavior(fecha.getMarkupId()));
			fecha.setOutputMarkupId(true);
			fecha.setLabel(Model.of("Fecha"));
			fecha.add(new NoInputBehavior());
			
			final FeedbackPanel feedBackPanel = new FeedbackPanel("feedBackPanel");
			feedBackPanel.setOutputMarkupId(true);
			
			
			AjaxButton submit = new AjaxButton("submit", this) {
			
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					EntradaForm.this.onSubmit(target, (EntradaForm)form);								
					target.add(feedBackPanel);
					target.add(listViewContainer);
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
			add(submit);
			this.setOutputMarkupId(true);

		}

		
		protected abstract void onSubmit(AjaxRequestTarget target, EntradaForm form);

	}	
}


