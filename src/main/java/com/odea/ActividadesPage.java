package com.odea;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.components.confirmPanel.ConfirmationLink;
import com.odea.domain.Actividad;
import com.odea.services.DAOService;

public class ActividadesPage extends BasePage{
	
	private static final long serialVersionUID = 1L;

	@SpringBean
	private transient DAOService daoService;
	
	public IModel<List<Actividad>> lstActividadesModel;
	public IModel<List<Actividad>> lstActividadesHabilitadasModel;
	public WebMarkupContainer listViewContainer;
	public WebMarkupContainer radioContainer;
	
	public ActividadesPage(){


		add(new BookmarkablePageLink<EditarActividadesPage>("link", EditarActividadesPage.class));
		
		Label tituloModificar = new Label("tituloModificar", "Modificar");
		Label tituloBorrar = new Label("tituloBorrar", "Borrar");
		
		this.lstActividadesModel = new LoadableDetachableModel<List<Actividad>>() { 
            @Override
            protected List<Actividad> load() {
            	return daoService.getActividades();
            }
        };
        
		this.lstActividadesHabilitadasModel = new LoadableDetachableModel<List<Actividad>>() { 
            @Override
            protected List<Actividad> load() {
            	return daoService.getActividadesHabilitadas();
            }
        };
        
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
        
        final ListView<Actividad> actividadListView = new ListView<Actividad>("actividades", this.lstActividadesHabilitadasModel) {

			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<Actividad> item) {
            	Actividad actividad = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	
            	item.add(new Label("nombre_actividad", new Model<String>(actividad.getNombre())));
            	
            	
                item.add(new ConfirmationLink<Actividad>("deleteLink","\\u00BFEst\\xE1 seguro de que desea borrar la actividad?",new Model<Actividad>(actividad)) {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        daoService.borrarActividad(getModelObject());
                        ajaxRequestTarget.add(getPage().get("listViewContainer"));
                    }
                    
                });
                item.add(new BookmarkablePageLink<EditarActividadesPage>("modifyLink",EditarActividadesPage.class,new PageParameters().add("id",actividad.getIdActividad()).add("nombre",actividad.getNombre())));
                
            };
            
            	
		};
		listViewContainer.add(actividadListView);
		listViewContainer.add(tituloModificar);
		listViewContainer.add(tituloBorrar);

		radioContainer = new WebMarkupContainer("radioContainerActividades");
		radioContainer.setOutputMarkupId(true);
		
		RadioGroup<String> radiog = new RadioGroup<String>("radioGroup", new Model<String>());
		
		Radio<String> mostrarTodas = new Radio<String>("mostrarTodas", Model.of("Mostrar todas"));
		Radio<String> mostrarHabilitadas = new Radio<String>("mostrarHabilitadas", Model.of("Mostrar solo habilitadas"));
		
		mostrarTodas.add(new AjaxEventBehavior("onchange") {
           
            protected void onEvent(AjaxRequestTarget target) {
                actividadListView.setModel(ActividadesPage.this.lstActividadesModel);
                target.add(listViewContainer);
            }
           
        });
		
		mostrarHabilitadas.add(new AjaxEventBehavior("onchange") {
	           
            protected void onEvent(AjaxRequestTarget target) {
                actividadListView.setModel(ActividadesPage.this.lstActividadesHabilitadasModel);
                target.add(listViewContainer);
            }
           
        });
		
		radiog.add(mostrarTodas);
		radiog.add(mostrarHabilitadas);
		radioContainer.add(radiog);
		
		
		add(radioContainer);
		add(listViewContainer);
		
	}
	
	
	
}
