package com.odea;

import org.apache.wicket.markup.html.basic.Label;

public class NoAutorizadoPage extends BasePage {

	public NoAutorizadoPage() {
		
		Label mensaje = new Label("mensaje", "Usted no esta autorizado para ingresar");
		add(mensaje);
	}
	
}
