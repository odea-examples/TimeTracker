package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Usuario;
import com.odea.modeloSeguridad.Permiso;
import com.odea.services.DAOService;

/* 
 *  Los perfiles estan modelados como objetos de clase Usuario.
 *  Esto es para seguir el modelo de datos
 */



public class PermisosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public WebComponent titulos;
	public IModel<ArrayList<Permiso>> lstPermisosModel;
	public IModel<List<Usuario>> lstPerfilesModel;
	
	
		
	public PermisosPage() {

		
		this.lstPermisosModel = new LoadableDetachableModel<ArrayList<Permiso>>() {
			@Override
			protected ArrayList<Permiso> load() {
				return daoService.getPermisos();
			}
		};
		
		this.lstPerfilesModel = new LoadableDetachableModel<List<Usuario>>() {
			@Override
			protected List<Usuario> load() {
				return daoService.getPerfiles();
			}
		};
		
		
		
		//List View
		
		WebMarkupContainer listContainer = new WebMarkupContainer("listaContainer");
		listContainer.setOutputMarkupId(true);
		
		
		this.titulos = new WebComponent("tituloHtml"){
			@Override
			public void onComponentTagBody(MarkupStream markupStream,ComponentTag openTag) {
				Response response = getRequestCycle().getResponse();
				
				String respuesta ="<th class='skinnyTable' scope='col'>Usuarios</th>";
				
				for (Usuario unUsuario : lstPerfilesModel.getObject()) {
					
					respuesta += "<th class='skinnyTable' scope='col' >"+ unUsuario.getNombre() +"</th>";
				}
				
                response.write(respuesta);
			}
	    	
        };
        
        listContainer.add(titulos);
        
		
		
		
		ListView<Permiso> listaPermisos = new ListView<Permiso>("listaPermisos", this.lstPermisosModel) {

			@Override
			protected void populateItem(ListItem<Permiso> item) {

				final Permiso permiso = item.getModel().getObject();
				final List<Usuario> usuariosConPermiso = PermisosPage.this.daoService.getUsuariosQueTienenUnPermiso(permiso);
				
				item.add(new Label("nombre_permiso", "Permiso ID: " + permiso.getID()));
				
				
				ListView<Usuario> listaInterna = new ListView<Usuario>("listaUsuarios",lstPerfilesModel) {
					
					
					@Override
					protected void populateItem(ListItem<Usuario> item) {
						
						final Usuario usuario = item.getModelObject();
						final CheckBox checkBox = new CheckBox("check", new Model<Boolean>());
					
						Boolean habilitado = this.usuarioEstaHabilitado(usuariosConPermiso, usuario);
						checkBox.getModel().setObject(habilitado);
						
						checkBox.add(new AjaxEventBehavior("onChange") {
							
							@Override
							protected void onEvent(AjaxRequestTarget target) {
								
								//Cambio el estado del Checkbox manualmente
								Boolean estado = checkBox.getModelObject();
								checkBox.setModelObject(!estado);
								
								PermisosPage.this.daoService.cambiarStatusPermiso(usuario, permiso, checkBox.getModelObject());
							}
						});
						
						item.add(checkBox);
			
					}
					
					
					//Veo si el usuario esta habilitado (para marcar el CheckBox)
					private Boolean usuarioEstaHabilitado(List<Usuario> usuariosConPermiso, Usuario usuario) {
						
						for (Usuario usuarioConPermiso : usuariosConPermiso) {
							if(usuario.getIdUsuario() == usuarioConPermiso.getIdUsuario()) {
								return true;
							}
						}
						
						return false;
					}
					
					
				};
				
				item.add(listaInterna);
			}	
			
		};
		
		
		listContainer.add(listaPermisos);
		
        
		add(listContainer);
	}

	
}

