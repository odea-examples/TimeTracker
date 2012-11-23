package com.odea;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;

import com.odea.behavior.noInput.NoInputBehavior;
import com.odea.behavior.numberComma.NumberCommaBehavior;
import com.odea.behavior.onlyNumber.OnlyNumberBehavior;
import com.odea.components.datepicker.DatePickerBehavior;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;
import com.odea.validators.duracion.DurationValidator;

public class EditEntradasPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
    private IModel<Entrada> entradaModel;
    
    public EditEntradasPage(final PageParameters parameters){
    	
    	
    	Subject subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}
		
		
    	
		this.entradaModel = new CompoundPropertyModel<Entrada>(new LoadableDetachableModel<Entrada>() {
            @Override
            protected Entrada load() {
                Entrada entrada = daoService.buscarEntrada(parameters.get("id").toLong());
                Long tiempo = new Long(entrada.getDuracion());
                Time duration= new Time(tiempo);
                entrada.setDuracion(duration.toString().substring(0,5));
                return entrada;
            }
        });
		
        this.preparePage();    
    }
    
    
    private void preparePage(){

        
        EntradaForm form = new EntradaForm("form") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, EntradaForm form) {
				Entrada e = getModelObject();
				daoService.modificarEntrada(e);
				setResponsePage(AgregarEntradasPage.class);
			}
		};
        
		form.setModel(entradaModel);
		form.setDefaultModel(entradaModel);
		
		
        add(new BookmarkablePageLink<AgregarEntradasPage>("link",AgregarEntradasPage.class));
        add(new FeedbackPanel("feedback"));
       
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