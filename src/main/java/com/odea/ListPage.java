package com.odea;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
        add(new BookmarkablePageLink<FormPage>("link",FormPage.class));
	}
	
	
	private void addEntradasModule() {
        ListView<Entrada> entradasListView = new ListView<Entrada>("entradas", createModelForEntradas()) {
            @Override
            protected void populateItem(ListItem<Entrada> item) {
            	
        //TODO: Ver el tema de la 'duracion', en la pagina sale un numero erroneo.
        //TODO: el error es porque la duracion se ingresa por un double en el modo "HH,MM" y sale en forma de tiempo en milisegundos
        // ya lo cambie para que salgan las horas solas
            	
            	item.add(new Label("fecha", new PropertyModel<Entrada>(item.getModel(), "fecha")));
                item.add(new Label("proyecto", new PropertyModel<Entrada>(item.getModel(), "proyecto")));
                item.add(new Label("actividad", new PropertyModel<Entrada>(item.getModel(), "actividad")));
                item.add(new Label("duracion", new PropertyModel<Entrada>(item.getModel(), "duracion")));
                item.add(new Label("ticketBZ", new PropertyModel<Entrada>(item.getModel(), "ticketBZ")));
                
            }
        };
        
        entradasListView.setVisible(!entradasListView.getList().isEmpty());
        entradasListView.setOutputMarkupId(true);
        
		Label noHayEntradasLabel = new Label("noHayEntradasLabel", "No se ha ingresado ninguna entrada de esta semana. Si quiere agregar una, puede hacerlo desde el formulario.");
		noHayEntradasLabel.setVisible(!entradasListView.isVisible());
		        
        add(entradasListView);
        add(noHayEntradasLabel);
        
    }
	
    private LoadableDetachableModel<List<Entrada>> createModelForEntradas() {
    	 
        return new LoadableDetachableModel<List<Entrada>>() { 
 
            @Override
            protected List<Entrada> load() {
            	            	
            	Usuario usuario = daoService.getUsuario(SecurityUtils.getSubject().getPrincipal().toString());
            	
            	logger.debug("Buscando entradas de usuario - " + usuario);
            	
                List<Entrada> entradas = daoService.getEntradasSemanales(usuario);
                
                int totalhs = 0;
                
                for (Entrada entrada : entradas) {
					totalhs += entrada.getDuracion();
				}
 
                logger.debug("Busqueda finalizada");
                
                return entradas;
            }
        };
    }
 
 
}
