package com.odea;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<LoginPage> getHomePage()
	{
		return LoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        mountPage("login",LoginPage.class);
        mountPage("formulario", FormPage.class);

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
	}
}
