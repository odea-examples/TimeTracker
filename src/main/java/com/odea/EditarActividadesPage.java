package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.behavior.focusOnLoad.FocusOnLoadBehavior;
import com.odea.components.dualMultipleChoice.DualMultipleChoice;
import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;

public class EditarActividadesPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
    private IModel<Actividad> actividadModel;
    
    public EditarActividadesPage(){
    	
    	
        this.actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
            @Override
            protected Actividad load() {
                return new Actividad(0,"");
            }
        });
        this.preparePage();    
    }
    
    public EditarActividadesPage(final PageParameters parameters) {
        this.actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
            @Override
            protected Actividad load() {
                return new Actividad(parameters.get("id").toInt(),parameters.get("nombre").toString());
            }
        });
        this.preparePage();
    }
    
    private void preparePage(){
        add(new BookmarkablePageLink<ActividadesPage>("link",ActividadesPage.class));
        add(new FeedbackPanel("feedback"));
        
        ActividadForm form = new ActividadForm("form",actividadModel){
        	
        	@Override
            protected void onSubmit(AjaxRequestTarget target, ActividadForm form) {
                setResponsePage(ActividadesPage.class);
            }
        	
        };


        add(form);
    }
    

	
	public abstract class ActividadForm extends Form<Actividad> {

		DualMultipleChoice<Proyecto> dualMultiple;
		
		public ActividadForm(String id, IModel<Actividad> model) {
			super(id, model);
			
	        RequiredTextField<String> nombre = new RequiredTextField<String>("nombre");
	        nombre.add(new FocusOnLoadBehavior());
	        
	        LoadableDetachableModel originalsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return ActividadForm.this.obtenerListaOrigen();
				}							
	        };
	        
	        LoadableDetachableModel destinationsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return ActividadForm.this.obtenerListaDestino();
				}							
	        };
	        
	        dualMultiple = new DualMultipleChoice<Proyecto>("dual", originalsModel, destinationsModel);
	        
	        AjaxButton submit = new AjaxButton("submit") {
	        	@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {
	        		ActividadForm.this.onSubmit(target, (ActividadForm) form);
	        		daoService.insertarActividad(ActividadForm.this.getModelObject(), (List<Proyecto>) dualMultiple.getDestinations().getChoices());
	        	}
	        };

	        
	        add(nombre);
			add(dualMultiple);
			add(submit);
		}
		
		public List<Proyecto> obtenerListaDestino() {
			if (actividadModel.getObject().getIdActividad() > 0) {
				return daoService.getProyectos(actividadModel.getObject());
			}
			else{
				return new ArrayList<Proyecto>();
			}
		}


		public List<Proyecto> obtenerListaOrigen() {
			if (actividadModel.getObject().getIdActividad() > 0) {
				return daoService.obtenerOrigen(actividadModel.getObject());
			} else {
				return daoService.getProyectos();
			}
		}		
	    
		
		protected abstract void onSubmit(AjaxRequestTarget target, ActividadForm form);
		
	}
    
}
