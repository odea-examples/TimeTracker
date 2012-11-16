package com.odea;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.domain.Actividad;
import com.odea.services.DAOService;

public class EditActividadesPage extends BasePage{
	
	@SpringBean
	private transient DAOService daoService;
	
    private IModel<Actividad> actividadModel;
    
    public EditActividadesPage(){
        this.actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
            @Override
            protected Actividad load() {
                return new Actividad(0,"");
            }
        });
        this.preparePage();    
    }
    
    public EditActividadesPage(final PageParameters parameters) {
        this.actividadModel = new CompoundPropertyModel<Actividad>(new LoadableDetachableModel<Actividad>() {
            @Override
            protected Actividad load() {
                return new Actividad(parameters.get("id").toInt(),parameters.get("nombre").toString());
            }
        });
        this.preparePage();
    }
    
    private void preparePage(){
        add(new BookmarkablePageLink<ActividadesPage>("link",ActividadesPage.class));
        add(new FeedbackPanel("feedback"));
        
        Form<Actividad> form = new Form<Actividad>("form",actividadModel){
            @Override
            protected void onSubmit() {
                Actividad a = getModelObject();
                if(a.getIdActividad()==0){
                	daoService.insertarNuevaActividad(a);
                }
                else{
                	daoService.modificarActividad(a.getNombre(),a.getIdActividad());
                }
                setResponsePage(ActividadesPage.class);
            }
        };
        

        RequiredTextField<String> nombre = new RequiredTextField<String>("nombre");
        RequiredTextField<Integer> idActividad = new RequiredTextField<Integer>("idActividad"); 
        idActividad.setEnabled(false);
        idActividad.setVisible(false);
        Button submit = new Button("submit");
//        AjaxButton button= new AjaxButton("submit") {
//		};
		
		form.add(submit);
        form.add(nombre);
        form.add(idActividad);

        add(form);
    }
    
    
    
    
}
