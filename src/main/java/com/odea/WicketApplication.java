package com.odea;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.odea.shiro.AnnotationsShiroAuthorizationStrategy;
import com.odea.shiro.ShiroUnauthorizedComponentListener;

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
		
		AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(authz);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				new ShiroUnauthorizedComponentListener(LoginPage.class, PruebaPage.class, authz));
				
        mountPage("login",LoginPage.class);
        mountPage("formulario", EntradasPage.class);
        mountPage("actividades", ActividadesPage.class);
        mountPage("proyectos", ProyectosPage.class);
        mountPage("editProyectos", EditarProyectosPage.class);
        mountPage("miCuenta", EditarUsuarioPage.class);
        mountPage("usuarios", UsuariosPage.class);
        mountPage("noAutorizado", NoAutorizadoPage.class);
        mountPage("yui", YuiPage.class);
        mountPage("feriados", FeriadosPage.class);
        mountPage("pdf", pruebaPDF.class);
        mountPage("reportHoras", VistaHorasPage.class);
        mountPage("pp", PermisosPage.class);
        mountPage("ap", AltaPerfilPage.class);
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
        //setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
	}
}
