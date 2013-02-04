package com.odea.components.modalWindow;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class SelectContentPanel extends Panel {

    public SelectContentPanel(String id) {
        super(id);

        Form form = new Form("form");
        
        Label mensaje = new Label("mensaje", "Sus cambios se han realizado correctamente");
        
        AjaxButton aceptarButton = new AjaxButton("close") {    
        	public void onSubmit(AjaxRequestTarget target, Form form) {
                onCancel(target);
            }
        }; 
        
        form.add(mensaje);
        form.add(aceptarButton);

        add(form);
    }

    public abstract void onCancel(AjaxRequestTarget target);


}
