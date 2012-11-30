package com.odea;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Actividad;
import com.odea.services.DAOService;


public class ActividadesPage2 extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
	public WebMarkupContainer listViewContainer;
	public IModel<List<Actividad>>lstActividadesModel;
	public ActividadFormModify formModify;
	public int idFinal;
	public WebMarkupContainer panel1;
	
	
	
	public ActividadesPage2() {
		
		
		
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
		
		panel1 = new WebMarkupContainer("panel1");
		this.panel1.setOutputMarkupPlaceholderTag(true);
		this.panel1.setOutputMarkupId(true);		
		this.panel1.setVisible(false);
		
		
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
                item.add(new AjaxLink<Actividad>("modifyLink",new Model<Actividad>(actividad)) {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        
                        panel1.setVisible(true);
                        formModify.setVisibilityAllowed(true);
//                        ajaxRequestTarget.add(panel1);
                        
                        idFinal= getModelObject().getIdActividad();
                        ajaxRequestTarget.add(getPage().get("listViewContainer"));
                    }

                });
            };
            	
		};
		
		
		formModify = new ActividadFormModify("formModify"){

			@Override
			protected void onSubmit(AjaxRequestTarget target, ActividadFormModify formModify) {
				daoService.modificarActividad(formModify.getModelObject().getNombre(), idFinal );
				panel1.setVisible(false);
//				target.add(panel1);
				target.add(listViewContainer);
			}
			
		};
		
   
		listViewContainer.add(actividadListView);
	
		add(listViewContainer);
		add(form);
		add(formModify);
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
	public abstract class ActividadFormModify extends Form<Actividad> {

		public IModel<Actividad> actividadModel = new CompoundPropertyModel<Actividad>(new Actividad());
		public RequiredTextField<String> nombre;
		
		public ActividadFormModify(String id) {
			super(id);
			
			this.setOutputMarkupPlaceholderTag(true);
			this.setOutputMarkupId(true);
			this.setDefaultModel(this.actividadModel);

			
			nombre = new RequiredTextField<String>("nombre");
			nombre.setOutputMarkupId(true);
			
			AjaxButton submitButton1 = new AjaxButton("submit1", this) {
				
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					ActividadFormModify.this.onSubmit(target, (ActividadFormModify)form);
				}
				
			};
			
			panel1.add(nombre);
			panel1.add(submitButton1);
			add(panel1);
		}
		
		protected abstract void onSubmit(AjaxRequestTarget target, ActividadFormModify form);
	}

}
