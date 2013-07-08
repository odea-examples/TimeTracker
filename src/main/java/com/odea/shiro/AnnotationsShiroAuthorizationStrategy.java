package com.odea.shiro;

import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.LoginPage;
import com.odea.NoAutorizadoPage;
import com.odea.dao.UsuarioDAO;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class AnnotationsShiroAuthorizationStrategy implements IAuthorizationStrategy
{
	private static final Logger LOG = LoggerFactory.getLogger(AnnotationsShiroAuthorizationStrategy.class);
	
	
	public String usuario;
	@SpringBean
	private transient DAOService daoService;
	
	@Autowired
	private transient UsuarioDAO usuarioDAO;
	
	
	@Override
	public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
		return true;
	}

	
	//Siempre pasa por este metodo, por cada componente o pagina que tenga que renderizar.
	
	@Override
	public boolean isActionAuthorized(Component component, Action action) {
		
		
		
		boolean loggedIn = SecurityUtils.getSubject().getPrincipal() != null;
		String paginaActual = component.getPage().getClass().toString();
		
		/*
		 * Si el usuario no se logueo, lo manda a la LoginPage
		 * (a menos de que ya esté ahi porque sino entra en loop infinito)
		 */
		
		if (loggedIn) {
			return this.verificarComponente(component, action);
		} else if (!paginaActual.equals("class com.odea.LoginPage")) {
			throw new RestartResponseAtInterceptPageException(new LoginPage());
		}
		
		return true;
	}
	
	
	//Verifica si el componente se renderiza.
	//Si devuelve true se renderiza, sino no.
	private boolean verificarComponente(Component component, Action action) {
		
		final Subject subject = SecurityUtils.getSubject();
		this.usuario=subject.getPrincipal().toString();
//		this.usuario = usuarioDAO.getUsuario(subject.getPrincipal().toString());
		String idComponent = component.getId();
		String page = component.getPage().getClass().toString();
		String accion = action.toString();
		
		
		
		ArrayList<String> paginasNoAceptadas = new ArrayList<String>();
		paginasNoAceptadas.add("class com.odea.FeriadosPage");

		
		ArrayList<String> componentesNoAceptados = new ArrayList<String>();
		componentesNoAceptados.add("selectorUsuarioContainer");
		componentesNoAceptados.add("deleteLink");
		componentesNoAceptados.add("modifyLink");
		componentesNoAceptados.add("link");
		componentesNoAceptados.add("tituloModificar");
		componentesNoAceptados.add("tituloBorrar");
		componentesNoAceptados.add("dedicacion");
		componentesNoAceptados.add("tituloDedicacion");
		componentesNoAceptados.add("radioContainerProyectos");
		componentesNoAceptados.add("radioContainerActividades");
		componentesNoAceptados.add("tituloHabilitado");
		componentesNoAceptados.add("checkBoxActividad");
		componentesNoAceptados.add("checkBoxProyecto");
		
		
		ArrayList<String> paginasAccesoLimitado = new ArrayList<String>();
		paginasAccesoLimitado.add("class com.odea.EntradasPage");
		paginasAccesoLimitado.add("class com.odea.ProyectosPage");
		paginasAccesoLimitado.add("class com.odea.ActividadesPage");
		paginasAccesoLimitado.add("class com.odea.UsuariosPage");

		//Dice invitado pero debería ser admin. Como lo usamos para hacer pruebas por ahora lo dejamos como invitado.
		if (!(subject.getPrincipal().toString().equals("fbosch"))|!(subject.getPrincipal().toString().equals("fcosta"))|!(subject.getPrincipal().toString().equals("ppagi"))|!(subject.getPrincipal().toString().equals("mdesplats"))|!(subject.getPrincipal().toString().equals("lgrinpelc"))|!(subject.getPrincipal().toString().equals("miriarte"))|!(subject.getPrincipal().toString().equals("mbortolotti"))|!(subject.getPrincipal().toString().equals("yduaisd"))){
			if (paginasAccesoLimitado.contains(page)) {
				if (componentesNoAceptados.contains(idComponent)) {
					return false;
				}				
			}
			
			if (paginasNoAceptadas.contains(page)) {
				throw new RestartResponseAtInterceptPageException(new NoAutorizadoPage());
			}
			
		}
		
		
		//El invitado no debería ser capaz de cambiar su contraseña o nombre de usuario.
		if (usuario.equals("invitado")) {
			if (page.equals("class com.odea.EditarUsuarioPage")) {
				throw new RestartResponseAtInterceptPageException(new NoAutorizadoPage());
			}
		}
		
		
		
		return true;
	}

	
	
	
	

//	if ((idComponent == "selectorUsuario") && (accion=="RENDER")){
//		component.add(new AttributeModifier("style", new Model("display:none")));
//	}
		
	/*
	 * Lo de abajo permite esconder el boton de modify link, con un par mas de ifs agregados
	 * se podria hacer que el link no se vea para usuarios comunes.
	 */
	//TODO: esto.
//	if ((idComponent== "modifyLink") && (accion=="RENDER")){
//		component.add(new AttributeModifier("style", new Model("display:none")));
//	}
	
}
