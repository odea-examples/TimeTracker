package com.odea.shiro;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

public class ShiroUnauthorizedComponentListener implements
	IUnauthorizedComponentInstantiationListener
{
	private final Class<? extends Page> loginPage;
	private final Class<? extends Page> unauthorizedPage;
	private AnnotationsShiroAuthorizationStrategy annotationStrategy = null;

	public ShiroUnauthorizedComponentListener(final Class<? extends Page> loginPage,
		final Class<? extends Page> unauthorizedPage, final AnnotationsShiroAuthorizationStrategy s)
	{
		this.loginPage = loginPage;
		this.unauthorizedPage = unauthorizedPage;
	}

	@Override
	public void onUnauthorizedInstantiation(Component component) {
		System.out.println("-- Pase por onUnauthorizedInstantiation() - Page: " + component.getPage().getClass() + " - Component ID: " + component.getId() );
	}

}
