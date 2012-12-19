package com.odea.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odea.NoAutorizadoPage;

public class AnnotationsShiroAuthorizationStrategy implements IAuthorizationStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationsShiroAuthorizationStrategy.class);

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		//System.out.println("Pase por isInstatiationAuthorized()");
		//System.out.println(componentClass);
		
		return true;
		
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		//System.out.println("Pase por isActionAuthorized() - Page: " + component.getPage().getClass() + " - Component ID: " + component.getId());
		//System.out.println("Action: " + action);
		
		boolean notLoggedIn = SecurityUtils.getSubject().getPrincipal() == null;
		
		if (notLoggedIn && !component.getPage().getClass().toString().equals("class com.odea.LoginPage")) {
			
			if (!component.getPage().getClass().toString().equals("class com.odea.NoAutorizadoPage")) {
				throw new RestartResponseAtInterceptPageException(new NoAutorizadoPage());				
			}
			
		}
		
		return true;
	}

	
}
