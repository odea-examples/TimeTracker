package com.odea.shiro;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationsShiroAuthorizationStrategy implements IAuthorizationStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationsShiroAuthorizationStrategy.class);

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
			Class<T> componentClass) {
		System.out.println("Pase por isInstatiationAuthorized()");
		return true;
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		System.out.println("Pase por isInstatiationAuthorized() - Page: " + component.getPage().getClass() + " - Component ID: " + component.getId());
		return true;
	}

	
}
