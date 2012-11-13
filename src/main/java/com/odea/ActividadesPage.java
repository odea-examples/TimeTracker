package com.odea;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.AgregarEntradasPage.EntradaForm;
import com.odea.domain.Actividad;
import com.odea.services.DAOService;


public class ActividadesPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
	public WebMarkupContainer listViewContainer;
	public IModel<List<Actividad>>lstActividadesModel;
	
	public ActividadesPage() {
		
		Subject subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			this.redirectToInterceptPage(new LoginPage());
		}
		
		this.lstActividadesModel = new LoadableDetachableModel<List<Actividad>>() { 
            @Override
            protected List<Actividad> load() {
            	return daoService.getActividades();
            }
        };
		
		ActividadForm form = new ActividadForm("form"){

			@Override
			protected void onSubmit(AjaxRequestTarget target, ActividadForm form) {
				daoService.insertarNuevaActividad(form.getModelObject());
				target.add(listViewContainer);
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
            }
        };
		
   
		listViewContainer.add(actividadListView);
	
		add(listViewContainer);
		add(form);
	}
	
	public abstract class ActividadForm extends Form<Actividad> {

		public IModel<Actividad> actividadModel = new CompoundPropertyModel<Actividad>(new Actividad());
		public RequiredTextField<String> nombre;
		
		public ActividadForm(String id) {
			super(id);
			
			this.setOutputMarkupId(true);
			this.setDefaultModel(this.actividadModel);
			
			nombre = new RequiredTextField<String>("nombre");
			nombre.setOutputMarkupId(true);
			
			AjaxButton submitButton = new AjaxButton("submit", this) {
				
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					ActividadForm.this.onSubmit(target, (ActividadForm)form);
				}
				
			};
			
			add(nombre);
			add(submitButton);
		}
		
		protected abstract void onSubmit(AjaxRequestTarget target, ActividadForm form);
	}

}
