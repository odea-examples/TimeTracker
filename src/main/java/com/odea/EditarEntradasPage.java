package com.odea;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
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
import com.odea.validators.ticketExterno.OnRelatedFieldsNullValidator;

public class EditarEntradasPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
    private IModel<Entrada> entradaModel;
    
    public EditarEntradasPage(final PageParameters parameters){
		
    	
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
				setResponsePage(EntradasPage.class);
			}
		};
        

		
        add(new BookmarkablePageLink<EntradasPage>("link",EntradasPage.class));
        add(new FeedbackPanel("feedback"));
       
		add(form);
		 
    }
    
    public abstract class EntradaForm extends Form<Entrada> {
	
    	public DropDownChoice<Actividad> comboActividad;
		public DropDownChoice<Proyecto> comboProyecto; 	
		public TextField<String> ticketExt;
		public DropDownChoice<String> sistemaExterno;
		public TextField<String> duracion;
		public TextField<String> ticketBZ;
		public TextField<Date> fecha;
		
		public EntradaForm(String id) {
			super(id);
			this.setDefaultModel(EditarEntradasPage.this.entradaModel);
			

			ArrayList<String> sistExt = new ArrayList<String>();
			sistExt.add("SIY");
			sistExt.add("SGY");
			sistExt.add("Ninguno");
			
			
			this.comboProyecto = new DropDownChoice<Proyecto>("proyecto",  daoService.getProyectos(),new IChoiceRenderer<Proyecto>() {
				@Override
				public Object getDisplayValue(Proyecto object) {
					return object.getNombre();
				}

				@Override
				public String getIdValue(Proyecto object, int index) {
					return Integer.toString(object.getIdProyecto());
				}
				
			});
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
			
			
			this.comboActividad = new DropDownChoice<Actividad>("actividad",new ArrayList<Actividad>(),new IChoiceRenderer<Actividad>() {
				@Override
				public Object getDisplayValue(Actividad object) {
					return object.getNombre();
				}

				@Override
				public String getIdValue(Actividad object, int index) {
					return Integer.toString(object.getIdActividad());
				}
				
			});
			this.comboActividad.setOutputMarkupId(true);
			this.comboActividad.setRequired(true);
			this.comboActividad.setLabel(Model.of("Actividad"));
			this.comboActividad.setChoices(daoService.getActividades(this.getModelObject().getProyecto()));
			
			
			sistemaExterno = new DropDownChoice<String>("sistemaExterno", sistExt, new IChoiceRenderer<String>() {
				@Override
				public Object getDisplayValue(String object) {
					if (object.equals("SGY")) {
						object = "Sistema Geminis de YPF";
					}
					if (object.equals("SIY")) {
						object = "Sistema de incidencias de YPF";
					}
					
					return object;
				}

				@Override
				public String getIdValue(String object, int index) {
					if (object=="Ninguno"){
						object="";
					}
					return object;
				}

//TODO: No se si lo vamos a usar, pero, aca esta bien hecho
				
//				private String parsear(String nombre) {
//					String resultado = "";
//					for (int i = 0; i < nombre.length(); i++) {
//						if (Character.isUpperCase(nombre.charAt(i))){
//							resultado += nombre.charAt(i);
//						}
//					}
//					
//					return resultado;
//					
//				}
		
				
			}
			);
			
			
			sistemaExterno.setLabel(Model.of("Sistema Externo"));
			sistemaExterno.setOutputMarkupId(true);
			
			
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
			ticketExt.add(new PatternValidator("^[a-z0-9_-]{1,15}$"));
						
			
			
			fecha = new TextField<Date>("fecha");
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
					
					if (duracion.isValid()) {
						duracion.add(new AttributeModifier("style", new Model("border-color:none")));
					}else{
						duracion.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}
					
					if (ticketExt.isValid()) {
						ticketExt.add(new AttributeModifier("style", new Model("border-color:none")));
					}else{
						ticketExt.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}
					
					if (fecha.isValid()) {
						fecha.add(new AttributeModifier("style", new Model("border-color:none")));
					}else{
						fecha.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}
					
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					if (!duracion.isValid()) {
						duracion.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}else{
						duracion.add(new AttributeModifier("style", new Model("border-color:none")));
					}
					
					if (!ticketExt.isValid()) {
						ticketExt.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}else{
						ticketExt.add(new AttributeModifier("style", new Model("border-color:none")));
					}					
					
					if (!fecha.isValid()) {
						fecha.add(new AttributeModifier("style", new Model("border-style:solid; border-color:red;")));
					}else{
						fecha.add(new AttributeModifier("style", new Model("border-color:none")));
					}	
					
					target.add(feedBackPanel);
					target.add(fecha);
					target.add(duracion);
					target.add(ticketExt);
				}
				
			};

			
			this.add(new OnRelatedFieldsNullValidator(sistemaExterno, ticketExt,"Debe poner un sistema externo para poder poner un ticket externo"));
			this.add(new OnRelatedFieldsNullValidator(ticketExt, sistemaExterno,"Debe ingresar un ticket con ese sistema externo elegido"));
			
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