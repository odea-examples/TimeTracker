package com.odea.components.dualMultipleChoice;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public class DualMultipleChoice<T> extends Component {

	public ListMultipleChoice<T> originals;
	public ListMultipleChoice<T> destinations;
	public List<T> selectedOriginals;
	public List<T> selectedDestinations;
	


	public DualMultipleChoice(String id, IModel<T> model, List<T> selectedOriginals, List<T> selectedDestinations) {
		super(id, model);
		
		this.selectedOriginals = selectedOriginals;
		this.selectedDestinations = selectedDestinations;
		
		WebMarkupContainer container = new WebMarkupContainer("container");
		
		
		originals = new ListMultipleChoice<T>("originals", new PropertyModel(this, "selectedOriginals"),
					new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return DualMultipleChoice.this.originals;
				}							
		});

		originals.setOutputMarkupId(true);
		
		
		destinations = new ListMultipleChoice<T>("destinations", new PropertyModel(this, "selectedDestinations"),
					   new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return DualMultipleChoice.this.destinations;
				}			
		});

		destinations.setOutputMarkupId(true);
		
		
		
		AjaxButton addButton = new AjaxButton("addButton") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				update(target, DualMultipleChoice.this.getSelectedOriginals(), originals, destinations);
			}
		};	

		AjaxButton removeButton = new AjaxButton("removeButton") {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				update(target, DualMultipleChoice.this.selectedDestinations, destinations,
						originals);
			}
		};
		
		//TODO: Habría que ver si es necesario poner todo adentro de un panel, porque sino
		//		no podría agregar los componentes dentro de este
		
		
		
		container.add(originals);
		container.add(destinations);
		container.add(addButton);
		container.add(removeButton);
		
		
	}

	@Override
	protected void onRender() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void update(AjaxRequestTarget target, List<T> selections, ListMultipleChoice<T> from, ListMultipleChoice<T> to) {
		List<T> choicesTo;
		List<T> choicesFrom;

		for (T destination : selections) {
			choicesTo = (List<T>) to.getChoices();

			if (!choicesTo.contains(destination)) {
				choicesTo.add(destination);

				choicesFrom = (List<T>) from.getChoices();
				choicesFrom.remove(destination);

				from.setChoices(choicesFrom);
				to.setChoices(choicesTo);
			}
		}

		target.add(to);
		target.add(from);
	}

	
	//GETTERS & SETTERS
	
	public ListMultipleChoice<T> getOriginals() {
		return originals;
	}

	public void setOriginals(ListMultipleChoice<T> originals) {
		this.originals = originals;
	}

	public ListMultipleChoice<T> getDestinations() {
		return destinations;
	}

	public void setDestinations(ListMultipleChoice<T> destinations) {
		this.destinations = destinations;
	}

	public List<T> getSelectedOriginals() {
		return selectedOriginals;
	}

	public void setSelectedOriginals(List<T> selectedOriginals) {
		this.selectedOriginals = selectedOriginals;
	}

	public List<T> getSelectedDestinations() {
		return selectedDestinations;
	}

	public void setSelectedDestinations(List<T> selectedDestinations) {
		this.selectedDestinations = selectedDestinations;
	}
	
	
}
