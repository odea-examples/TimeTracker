//TODO: mejor hacerlo de a poco y despues irle agregando funcionalidades como las listchoices
//TODO: no se porque el texto se hizo todo mayuscula



package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;


public class EditProyectosPage extends BasePage {
	
	@SpringBean
	private transient DAOService daoService;
	private IModel<Proyecto> proyectoModel;

	
	
	public EditProyectosPage(){
		
		EditForm form = new EditForm("form");
		form.setOutputMarkupId(true);
		
		add(form);
		
	}
	
	
	  
	  public class EditForm extends Form<Proyecto> {
		  
		  public List<Actividad> selectedOriginals;
		  public List<Actividad> selectedDestinations;
		  public ListMultipleChoice<Actividad> originals;
		  public ListMultipleChoice<Actividad> destinations;
			
		  
			public EditForm(String id) {
				super(id);
				
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
						            target.add(originals);
						            target.add(destinations);
						      }
						    };
	
						AjaxButton removeButton = new AjaxButton("removeButton") {
						       @Override
						      protected void onSubmit(AjaxRequestTarget target, Form form) {
						           update(target, selectedDestinations, destinations, originals);
						           target.add(destinations);
						           target.add(originals);
						      }
						    };
						    
					add(originals);
					add(destinations);
					add(addButton);
					add(removeButton);
				
			}
		
			private void update(AjaxRequestTarget target, List<Actividad> selections, ListMultipleChoice<Actividad> from, ListMultipleChoice<Actividad> to) {
			    for (Actividad destination : selections) {
			      List<Actividad> choices = (List<Actividad>) from.getChoices();
			      if (!to.getChoices().contains(destination)) {
			       // to.getChoices().add(destination);
			        to.getModelObject().add(destination);
			        choices.remove(destination);
			        from.setChoices(choices);
			      }
			    }
			    target.add(to);
			    target.add(from);
			  }
		  
		  
	  }
}