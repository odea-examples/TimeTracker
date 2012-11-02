package com.odea;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odea.domain.Entrada;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;


public class ListPage extends BasePage {
	
    private static final Logger logger = LoggerFactory.getLogger(ListPage.class);

	
    @SpringBean
	private DAOService daoService;
	
	
	public ListPage(){
		addEntradasModule();
	}
	
	
	private void addEntradasModule() {
        ListView<Entrada> entradas = new ListView<Entrada>("entradas", createModelForEntradas()) {
            @Override
            protected void populateItem(ListItem<Entrada> item) {
            	item.add(new Label("fecha", new PropertyModel<Entrada>(item.getModel(), "fecha")));
                item.add(new Label("proyecto", new PropertyModel<Entrada>(item.getModel(), "proyecto")));
                item.add(new Label("actividad", new PropertyModel<Entrada>(item.getModel(), "actividad")));
                item.add(new Label("duracion", new PropertyModel<Entrada>(item.getModel(), "duracion")));
                item.add(new Label("ticketBZ", new PropertyModel<Entrada>(item.getModel(), "ticketBZ")));
            }
        };
 
        add(entradas);
    }
	
    private LoadableDetachableModel<List<Entrada>> createModelForEntradas() {
    	 
        return new LoadableDetachableModel<List<Entrada>>() { 
 
            @Override
            protected List<Entrada> load() {
            	
            	Usuario usuario = new Usuario(57, "invitado", "invitado");
            	
                List<Entrada> entradas = daoService.getEntradasSemanales(usuario);
                
                return entradas;
            }
        };
    }
 
 
}
