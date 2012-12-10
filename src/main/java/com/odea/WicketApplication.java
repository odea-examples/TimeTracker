package com.odea;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication
{    	

	@Override
	public Class<LoginPage> getHomePage()
	{
		return LoginPage.class;
	}


	
	@Override
	public void init()
	{
		super.init();
        mountPage("login",LoginPage.class);
        mountPage("formulario", AgregarEntradasPage.class);
        mountPage("actividades", ActividadesPage.class);
        mountPage("proyectos", ProyectosPage.class);
        mountPage("editProyectos", EditProyectosPage.class);
        mountPage("miCuenta", EditarUsuarioPage.class);
        mountPage("usuarios", UsuariosPage.class);
        mountPage("pp", PruebaPage.class);

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
	}
}
