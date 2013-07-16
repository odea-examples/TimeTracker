package com.odea;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
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
	private DAOService daoService;
	
	public IModel<List<Proyecto>>lstProyectosModel;
	public IModel<List<Proyecto>>lstProyectosHabilitadosModel;
	public WebMarkupContainer listViewContainer;
	public WebMarkupContainer radioContainer;
	
	public ProyectosPage() {
		
		
		this.lstProyectosModel = new LoadableDetachableModel<List<Proyecto>>() { 
            @Override
            protected List<Proyecto> load() {
            	return daoService.getProyectos();
            }
        };
        
        this.lstProyectosHabilitadosModel = new LoadableDetachableModel<List<Proyecto>>() { 
            @Override
            protected List<Proyecto> load() {
            	return daoService.getProyectosHabilitados();
            }
        };
        

        Label tituloModificar = new Label("tituloModificar", "Modificar");
        Label tituloBorrar = new Label("tituloBorrar", "Borrar");
        Label tituloHabilitado = new Label("tituloHabilitado", "Habilitado");
        
        
        final PageableListView<Proyecto> proyectoListView = new PageableListView<Proyecto>("proyectos", this.lstProyectosHabilitadosModel, 10) {

			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<Proyecto> item) {
            	final Proyecto proyecto = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	
            	item.add(new Label("nombre_proyecto", new Model<String>(proyecto.getNombre())));
            	
                
                item.add(new BookmarkablePageLink<EditarProyectosPage>("modifyLink",EditarProyectosPage.class,new PageParameters().add("proyectoId",proyecto.getIdProyecto()).add("proyectoNombre",proyecto.getNombre()).add("proyectoHabilitado",proyecto.isHabilitado())));
                
                CheckBox checkBox = new CheckBox("checkBoxProyecto", new Model<Boolean>(proyecto.isHabilitado()));
                checkBox.add(new AjaxEventBehavior("onchange") {
           
		            protected void onEvent(AjaxRequestTarget target) {
		               daoService.cambiarStatus(proyecto);
		            }
		           
		        });
                
                item.add(checkBox);
                
                item.add(new ConfirmationLink<Proyecto>("deleteLink","\\u00BFEst\\xE1 seguro de que desea borrar el proyecto? \\nAdvertencia: Se eliminar\\xE1n todas las entradas relacionadas.", new Model<Proyecto>(proyecto)) {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        daoService.borrarProyecto(getModelObject());
                        ajaxRequestTarget.add(listViewContainer);
                    }

                });
            };
		};
		
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		this.listViewContainer.add(proyectoListView);
		this.listViewContainer.add(tituloBorrar);
		this.listViewContainer.add(tituloModificar);
		this.listViewContainer.add(tituloHabilitado);
		this.listViewContainer.add(new AjaxPagingNavigator("navigator", proyectoListView));
		
		
		
		
		
		radioContainer = new WebMarkupContainer("radioContainerProyectos");
		radioContainer.setOutputMarkupId(true);
		
		RadioGroup<String> radiog = new RadioGroup<String>("radioGroup", new Model<String>());
		
		Radio<String> mostrarTodos = new Radio<String>("mostrarTodos", Model.of("Todos"));
		Radio<String> mostrarHabilitados = new Radio<String>("mostrarHabilitados", Model.of("Solo Habilitados"));
		
		mostrarTodos.add(new AjaxEventBehavior("onchange") {
           
            protected void onEvent(AjaxRequestTarget target) {
                proyectoListView.setModel(ProyectosPage.this.lstProyectosModel);
                target.add(listViewContainer);
            }
           
        });
		
		mostrarHabilitados.add(new AjaxEventBehavior("onchange") {
	           
            protected void onEvent(AjaxRequestTarget target) {
                proyectoListView.setModel(ProyectosPage.this.lstProyectosHabilitadosModel);
                target.add(listViewContainer);
            }
           
        });
		
		radiog.add(mostrarTodos);
		radiog.add(mostrarHabilitados);
		
		radioContainer.add(radiog);
		
		add(radioContainer);
		add(new BookmarkablePageLink<EditarProyectosPage>("link", EditarProyectosPage.class, new PageParameters().add("proyectoId", 0).add("proyectoNombre", "")));

		add(listViewContainer);        
		
	}
	
}
