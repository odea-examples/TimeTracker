package com.odea.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odea.LoginPage;

public class AnnotationsShiroAuthorizationStrategy implements IAuthorizationStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationsShiroAuthorizationStrategy.class);

	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
//		System.out.println("Pase por isInstatiationAuthorized()");
//		System.out.println(componentClass);
		return true;
		
	}

	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		
		boolean loggedIn = SecurityUtils.getSubject().getPrincipal() != null;
		
		if (loggedIn) {
			return this.verificarComponente(component, action);
		} else if (!component.getPage().getClass().toString().equals("class com.odea.LoginPage")) {
			throw new RestartResponseAtInterceptPageException(new LoginPage());
		}
		
		
		//Si sale del if es porque es la LoginPage, entonces todo se renderiza
		return true;
		
		
		
		
		
		
//		if ((idComponent == "selectorUsuario") && (accion=="RENDER")){
//			component.add(new AttributeModifier("style", new Model("display:none")));
//		}
			
		/*
		 * Lo de abajo permite esconder el boton de modify link, con un par mas de ifs agregados
		 * se podria hacer que el link no se vea para usuarios comunes.
		 */
		//TODO: esto.
//		if ((idComponent== "modifyLink") && (accion=="RENDER")){
//			component.add(new AttributeModifier("style", new Model("display:none")));
//		}
		
	}
	
	private boolean verificarComponente(Component component, Action action) {
		
		String usuario = SecurityUtils.getSubject().getPrincipal().toString();
		String idComponent = component.getId();
		String accion = action.toString();
		
		if (!usuario.equals("pgotelli")) {
			if (idComponent == "selectorUsuario") {
				return false;
			}
		}
		
		return true;
	}

	
}
