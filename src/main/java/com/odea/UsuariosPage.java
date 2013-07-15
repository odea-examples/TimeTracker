package com.odea;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.behavior.onlyNumber.OnlyNumberBehavior;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class UsuariosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public IModel<List<Usuario>> lstUsuariosModel;
	public IModel<List<Usuario>> lstPerfilesModel;
	public IModel<List<String>> lstNombresPerfilesModel;
	public WebMarkupContainer listViewContainer;
	public PageableListView<Usuario> usuariosListView;
	
	
	public UsuariosPage() {

		this.lstUsuariosModel = new LoadableDetachableModel<List<Usuario>>() {
			
            @Override
            protected List<Usuario> load() {
            	return daoService.getUsuariosConPerfiles();
            }
            
        };
        
        this.lstPerfilesModel = new LoadableDetachableModel<List<Usuario>>() { 
            @Override
            protected List<Usuario> load() {
            	return daoService.getPerfiles();
            }
        };
        
        this.lstNombresPerfilesModel = new LoadableDetachableModel<List<String>>() { 
            @Override
            protected List<String> load() {
            	return daoService.getNombresPerfiles();
            }
        };
        
        
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		
		this.usuariosListView = new PageableListView<Usuario>("usuarios", this.lstUsuariosModel, 20) {
			
			@Override
            protected void populateItem(ListItem<Usuario> item) {
            	final Usuario usuario = item.getModel().getObject();   
            
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	
            	item.add(new Label("nombreLogin", new Model<String>(usuario.getNombreLogin())));
            	
            	
            	final RequiredTextField<Integer> dedicacion = new RequiredTextField<Integer>("dedicacion", new Model<Integer>(daoService.getDedicacion(usuario)));
            	dedicacion.add(new OnlyNumberBehavior(dedicacion.getMarkupId()));
            	
            	dedicacion.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            		
    				@Override
    				protected void onUpdate(AjaxRequestTarget target) {
    					daoService.setDedicacion(usuario, Integer.parseInt(dedicacion.getInput()));
    				}

					@Override
					protected void onError(AjaxRequestTarget target, RuntimeException e) {
						target.add(UsuariosPage.this.listViewContainer);
					}
    				
    			});
            	
            	dedicacion.setOutputMarkupId(true);
            	
            	item.add(dedicacion);
            	
            	final DropDownChoice<String> dropDownPerfil = new DropDownChoice<String>("dropDownPerfil", Model.of(usuario.getPerfil().getNombreLogin()) ,lstNombresPerfilesModel);
            	            	
            	dropDownPerfil.add(new AjaxFormComponentUpdatingBehavior("onchange") {

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						daoService.cambiarPerfil(usuario, dropDownPerfil.getModelObject());
					}
            		
            	});
            	
            	
            	item.add(dropDownPerfil);
            	
            }
        };
        usuariosListView.setOutputMarkupId(true);
        
        listViewContainer.add(new Label("tituloDedicacion", "Dedicacion"));
        listViewContainer.add(usuariosListView);
        listViewContainer.add(new AjaxPagingNavigator("navigator", usuariosListView));
		listViewContainer.setVersioned(false);

        add(listViewContainer);
		
	}
	
}
