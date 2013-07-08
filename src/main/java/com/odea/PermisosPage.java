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
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Usuario;
import com.odea.modeloSeguridad.Permiso;
import com.odea.services.DAOService;


public class PermisosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public IModel<List<Usuario>> lstUsuariosModel;
//	public IModel<List<Usuario>> lstPermisosModel;
	public IModel<ArrayList<Permiso>> lstPermisosModel;
	public WebComponent titulos;
	
	
		
	public PermisosPage() {
		this.lstUsuariosModel = new LoadableDetachableModel<List<Usuario>>() {

			@Override
			protected List<Usuario> load() {
				return daoService.getUsuarios();
				//TODO return daoservice OBTENER TODOS LOS USUARIOSPERFIL;
			}
		};
		
		this.lstPermisosModel = new LoadableDetachableModel<ArrayList<Permiso>>() {
			@Override
			protected ArrayList<Permiso> load() {
				return daoService.getPermisos();
			}
		};
		
		ListView<Usuario> listaPermisos = new ListView<Usuario>("lista",this.lstUsuariosModel) {

			@Override
			protected void populateItem(ListItem<Usuario> item) {
				final Usuario usuario = item.getModel().getObject();
				
				ListView<Permiso> listaDatos = new ListView<Permiso>("listaPermisos",lstPermisosModel) {

					@Override
					protected void populateItem(ListItem<Permiso> item) {
						final CheckBox check = new CheckBox("check");
						check.setMarkupId(usuario.getNombreLogin()+"_"+item.getModelObject().getID());
						check.add(new AjaxEventBehavior("onChange") {
							
							@Override
							protected void onEvent(AjaxRequestTarget target) {
								System.out.println("changed!"+check.getMarkupId().toString());
								String[] datos = check.getMarkupId().split("_");
								CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
							}
						});
						item.add(check);
						
					}
				};
				
				item.add(listaDatos);
				
//				final CheckBox check = new CheckBox("check1");
//				check.setMarkupId(usuario.getLogin()+"_"+"1");
//				check.add(new AjaxEventBehavior("onChange") {
//					
//					@Override
//					protected void onEvent(AjaxRequestTarget target) {
//						System.out.println("changed!"+check.getMarkupId().toString());
//						String[] datos = check.getMarkupId().split("_");
//						CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
//					}
//				});
//				
////				item.add(check);
////				item.add(new Label("asd2","texto"));
//				
//				final CheckBox check2 = new CheckBox("check2");
//				check2.setMarkupId(usuario.getLogin()+"_"+"2");
//				check2.add(new AjaxEventBehavior("onChange") {
//					
//					@Override
//					protected void onEvent(AjaxRequestTarget target) {
//						System.out.println("changed!"+check2.getMarkupId().toString());
//						String[] datos = check2.getMarkupId().split("_");
//						CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
//					}
//				});
//				final CheckBox check3 = new CheckBox("check3");
//				check3.setMarkupId(usuario.getLogin()+"_"+"3");
//				check3.add(new AjaxEventBehavior("onChange") {
//					
//					@Override
//					protected void onEvent(AjaxRequestTarget target) {
//						System.out.println("changed!"+check3.getMarkupId().toString());
//						String[] datos = check3.getMarkupId().split("_");
//						CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
//					}
//				});
//				final CheckBox check4 = new CheckBox("check4");
//				check4.setMarkupId(usuario.getLogin()+"_"+"4");
//				check4.add(new AjaxEventBehavior("onChange") {
//					
//					@Override
//					protected void onEvent(AjaxRequestTarget target) {
//						System.out.println("changed!"+check4.getMarkupId().toString());
//						String[] datos = check4.getMarkupId().split("_");
//						CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
//					}
//				});
//				final CheckBox check5 = new CheckBox("check5");
//				check5.setMarkupId(usuario.getLogin()+"_"+"5");
//				check5.add(new AjaxEventBehavior("onChange") {
//					
//					@Override
//					protected void onEvent(AjaxRequestTarget target) {
//						System.out.println("changed!"+check5.getMarkupId().toString());
//						String[] datos = check5.getMarkupId().split("_");
//						CambiarPermiso(Integer.parseInt(datos[1]),datos[0]);
//					}
//				});
				item.add(new Label("nombre_usuario",usuario.getNombreLogin()));
//				item.add(check);
//				item.add(check2);
//				item.add(check3);
//				item.add(check4);
//				item.add(check5);
			}
		};
		WebMarkupContainer listconContainer = new WebMarkupContainer("listaContainer");
		listconContainer.setOutputMarkupId(true);
		listconContainer.add(listaPermisos);
		this.titulos = new WebComponent("tituloHtml"){
			@Override
			public void onComponentTagBody(MarkupStream markupStream,ComponentTag openTag) {
				Response response = getRequestCycle().getResponse();
				String respuesta= "";
				respuesta+="<th class='skinnyTable' scope='col'>Usuarios</th>";
				for (Permiso permiso : lstPermisosModel.getObject()) {
					
					respuesta+="<th class='skinnyTable' scope='col' >"+ permiso.getID() +"</th>";
				}
                response.write(respuesta);
			}
	    	
        };
        listconContainer.add(titulos);
		add(listconContainer);
	}



	private void CambiarPermiso(Integer check,String nombreUsuario) {
		Usuario usuario= daoService.getUsuario(nombreUsuario);
		daoService.cambiarStatusPermiso(usuario,check);
	}
	
}

