package com.odea;

import java.util.ArrayList;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.components.dualMultipleChoice.DualMultipleChoice;
import com.odea.domain.Actividad;
import com.odea.services.DAOService;

public class PruebaPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public PruebaPage() {
		
		IModel<Actividad> actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
            @Override
            protected Actividad load() {
                return new Actividad(1,"actividad1");
            }
        });
		
		Formulario form = new Formulario("form", actividadModel);
		
		add(form);
	
	}
	
	public class Formulario extends Form<Actividad> {

		public Formulario(String id, IModel<Actividad> model) {
			super(id, model);
			
			
			LoadableDetachableModel originalsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return new ArrayList<Actividad>();
				}							
	        };
		
	    	LoadableDetachableModel destinationsModel = new LoadableDetachableModel() {
				@Override
				protected Object load() {
					return daoService.getActividades();
				}							
	        };
		
	        
			DualMultipleChoice<Actividad> dual = new DualMultipleChoice<Actividad>("dual", originalsModel, destinationsModel);
			
			add(dual);
			
		}
		
	}
	
}
