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

import com.odea.domain.Actividad;
import com.odea.services.DAOService;

public class ActividadesPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
	public IModel<List<Actividad>>lstActividadesModel;
	public WebMarkupContainer listViewContainer;
	
	public ActividadesPage(){

		Subject subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}
		add(new BookmarkablePageLink<EditarActividadesPage>("link",EditarActividadesPage.class));
		
		this.lstActividadesModel = new LoadableDetachableModel<List<Actividad>>() { 
            @Override
            protected List<Actividad> load() {
            	return daoService.getActividades();
            }
        };
		this.listViewContainer = new WebMarkupContainer("listViewContainer");
		this.listViewContainer.setOutputMarkupId(true);
        
        ListView<Actividad> actividadListView = new ListView<Actividad>("actividades", this.lstActividadesModel) {

			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<Actividad> item) {
            	Actividad actividad = item.getModel().getObject();   
            	if((item.getIndex() % 2) == 0){
            		item.add(new AttributeModifier("class","odd"));
            	}
            	
            	item.add(new Label("nombre_actividad", new Model<String>(actividad.getNombre())));
            	
            	
                item.add(new AjaxLink<Actividad>("deleteLink",new Model<Actividad>(actividad)) {
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
		
		add(listViewContainer);
		
		
		
	}
	
	
	
	
	
	
	
}
