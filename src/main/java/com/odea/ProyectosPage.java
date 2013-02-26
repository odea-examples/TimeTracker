package com.odea;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.components.confirmPanel.ConfirmationLink;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;


public class ProyectosPage extends BasePage {

	@SpringBean
	private transient DAOService daoService;
	
	public IModel<List<Proyecto>>lstProyectosModel;
	public WebMarkupContainer listViewContainer;
	
	public ProyectosPage() {
		
		this.lstProyectosModel = new LoadableDetachableModel<List<Proyecto>>() { 
            @Override
            protected List<Proyecto> load() {
            	return daoService.getProyectos();
            }
        };
        

        Label tituloModificar = new Label("tituloModificar", "Modificar");
        Label tituloBorrar = new Label("tituloBorrar", "Borrar");
        
        
        ListView<Proyecto> proyectoListView = new ListView<Proyecto>("proyectos", this.lstProyectosModel) {

			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<Proyecto> item) {
            	Proyecto proyecto = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	
            	item.add(new Label("nombre_proyecto", new Model<String>(proyecto.getNombre())));
            	
                item.add(new ConfirmationLink<Proyecto>("deleteLink","\\u00BFEst\\xE1 seguro de que desea borrar el proyecto?",new Model<Proyecto>(proyecto)) {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        daoService.borrarProyecto(getModelObject());
                        ajaxRequestTarget.add(listViewContainer);
                    }

                });
                item.add(new BookmarkablePageLink<EditarProyectosPage>("modifyLink",EditarProyectosPage.class,new PageParameters().add("proyectoId",proyecto.getIdProyecto()).add("proyectoNombre",proyecto.getNombre())));
                
            };
		};
		
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		this.listViewContainer.add(proyectoListView);
		this.listViewContainer.add(tituloBorrar);
		this.listViewContainer.add(tituloModificar);
		
		add(new BookmarkablePageLink<EditarProyectosPage>("link", EditarProyectosPage.class, new PageParameters().add("proyectoId", 0).add("proyectoNombre", "")));
		add(listViewContainer);        
		
	}
	
}
