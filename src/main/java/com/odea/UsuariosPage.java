package com.odea;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.AgregarEntradasPage.EntradaForm;
import com.odea.domain.Entrada;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class UsuariosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	public IModel<List<Usuario>> lstUsuariosModel;	
	public WebMarkupContainer listViewContainer;
	PageableListView<Usuario> usuariosListView;

	
	public UsuariosPage() {
		
		Subject subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}

		this.lstUsuariosModel = new LoadableDetachableModel<List<Usuario>>() { 
            @Override
            protected List<Usuario> load() {
            	return daoService.getUsuarios();
            }
        };
        
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
		
		this.usuariosListView = new PageableListView<Usuario>("usuarios", this.lstUsuariosModel, 20) {
            @Override
            protected void populateItem(ListItem<Usuario> item) {
            	Usuario usuario = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	item.add(new Label("nombre", new Model<String>(usuario.getNombre())));
            }
        };
        listViewContainer.add(usuariosListView);
        listViewContainer.add(new AjaxPagingNavigator("navigator", usuariosListView));
		listViewContainer.setVersioned(false);

        add(listViewContainer);
		
	}
	
}
