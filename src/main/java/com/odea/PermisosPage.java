package com.odea;

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Usuario;
import com.odea.services.DAOService;


public class PermisosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	public IModel<List<Usuario>> lstUsuariosModel;
	public IModel<List<Usuario>> lstPermisosModel;
	
	
		
	public PermisosPage() {
		this.lstUsuariosModel = new LoadableDetachableModel<List<Usuario>>() {

			@Override
			protected List<Usuario> load() {
				// TODO Auto-generated method stub
				return daoService.getUsuarios();
			}
		};
		
		ListView<Usuario> listaPermisos = new ListView<Usuario>("lista",this.lstUsuariosModel) {

			@Override
			protected void populateItem(ListItem<Usuario> item) {
				final Usuario usuario = item.getModel().getObject();
				
				final CheckBox check = new CheckBox("check1");
				check.setMarkupId(usuario.getNombreLogin()+"_"+"check1");
				check.add(new AjaxEventBehavior("onChange") {
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						System.out.println("changed!"+check.getMarkupId().toString());
					}
				});
//				item.add(check);
//				item.add(new Label("asd2","texto"));
				
				final CheckBox check2 = new CheckBox("check2");
				check2.setMarkupId(usuario.getNombreLogin()+"_"+"check2");
				check2.add(new AjaxEventBehavior("onChange") {
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						System.out.println("changed!"+check2.getMarkupId().toString());
					}
				});
				final CheckBox check3 = new CheckBox("check3");
				check3.setMarkupId(usuario.getNombreLogin()+"_"+"check3");
				check3.add(new AjaxEventBehavior("onChange") {
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						System.out.println("changed!"+check3.getMarkupId().toString());
					}
				});
				final CheckBox check4 = new CheckBox("check4");
				check4.setMarkupId(usuario.getNombreLogin()+"_"+"check4");
				check4.add(new AjaxEventBehavior("onChange") {
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						System.out.println("changed!"+check4.getMarkupId().toString());
					}
				});
				final CheckBox check5 = new CheckBox("check5");
				check5.setMarkupId(usuario.getNombreLogin()+"_"+"check5");
				check5.add(new AjaxEventBehavior("onChange") {
					
					@Override
					protected void onEvent(AjaxRequestTarget target) {
						System.out.println("changed!"+check5.getMarkupId().toString());
					}
				});
				item.add(new Label("nombre_usuario",usuario.getNombreLogin()));
				item.add(check);
				item.add(check2);
				item.add(check3);
				item.add(check4);
				item.add(check5);
			}
		};
		WebMarkupContainer listconContainer = new WebMarkupContainer("listaContainer");
		listconContainer.setOutputMarkupId(true);
		listconContainer.add(listaPermisos);
		add(listconContainer);
	}
	
}

