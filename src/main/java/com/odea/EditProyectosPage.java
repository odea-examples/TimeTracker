//TODO: mejor hacerlo de a poco y despues irle agregando funcionalidades como las listchoices
//TODO: no se porque el texto se hizo todo mayuscula



package com.odea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;


public class EditProyectosPage extends BasePage {
	
	@SpringBean
	public transient DAOService daoService;

	
	
	public EditProyectosPage(){
		
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}
	
		
		EditForm form = new EditForm("form") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, EditForm form) {
				//TODO: onSubmit()
			}
			
		};
		
		form.setOutputMarkupId(true);
	
		add(form);
		
	}
	
	
	  
	  public abstract class EditForm extends Form<Proyecto> {
		  
		  public List<Actividad> selectedOriginals;
		  public List<Actividad> selectedDestinations;
		  public ListMultipleChoice<Actividad> originals;
		  public ListMultipleChoice<Actividad> destinations;
			
		  
			public EditForm(String id) {
				
				super(id);
				
				//RequiredTextField<String> nombre = new RequiredTextField<String>("nombre");
				//this.getModelObject().setNombre("nombreDePrueba");
				
				originals = new ListMultipleChoice<Actividad>("originals", 
						new PropertyModel(this, "selectedOriginals"), new LoadableDetachableModel() {
						      @Override
						      protected Object load() {
						        return daoService.getActividades();
						      }
						    });
				
				originals.setOutputMarkupId(true);
				
				destinations = new ListMultipleChoice<Actividad>("destinations", 
						   new PropertyModel(this, "selectedDestinations"), new LoadableDetachableModel() {
						      @Override
						      protected Object load() {
						        return new ArrayList();
						      }
						    });
				
				destinations.setOutputMarkupId(true);
				
						AjaxButton addButton = new AjaxButton("addButton") {
						      @Override
						      protected void onSubmit(AjaxRequestTarget target, Form form) {
						            update(target,selectedOriginals, originals, destinations);
						      }
						};
	
						AjaxButton removeButton = new AjaxButton("removeButton") {
						      @Override
						      protected void onSubmit(AjaxRequestTarget target, Form form) {
						           update(target, selectedDestinations, destinations, originals);
						      }
						};
					
//						AjaxButton submit = new AjaxButton("submit") {
//							@Override
//						    protected void onSubmit(AjaxRequestTarget target, Form form) {
//						        EditForm.this.onSubmit(target, (EditForm)form);
//						        daoService.agregarProyecto(EditForm.this.getModelObject(), destinations.getModelObject());
//						    }
//						};
						
					//add(nombre);
					add(originals);
					add(destinations);
					add(addButton);
					add(removeButton);
					//add(submit);
				
			}
			
		
			private void update(AjaxRequestTarget target, List<Actividad> selections, ListMultipleChoice<Actividad> from, ListMultipleChoice<Actividad> to) {
				List<Actividad> choicesTo;
				List<Actividad> choicesFrom;
				
				for (Actividad destination : selections) {
					choicesTo = (List<Actividad>) to.getChoices();
					
					if (!choicesTo.contains(destination)) {
						choicesTo.add(destination);
						
						choicesFrom = (List<Actividad>) from.getChoices();
						choicesFrom.remove(destination);
						
						Collections.sort(choicesTo);
						Collections.sort(choicesFrom);
						
						from.setChoices(choicesFrom);
						to.setChoices(choicesTo);
						
					}
			    }
			    
				target.add(to);
			    target.add(from);    
			}

			protected abstract void onSubmit(AjaxRequestTarget target, EditForm form);
			
	  }
}