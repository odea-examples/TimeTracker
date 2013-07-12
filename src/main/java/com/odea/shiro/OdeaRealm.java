package com.odea.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.UsuarioDAO;
import com.odea.modeloSeguridad.SeguridadDAO;

/**
 * User: pbergonzi
 * Date: 22/10/12
 * Time: 11:32
 */


public class OdeaRealm extends AuthorizingRealm{
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	@Autowired
	private SeguridadDAO seguridadDAO;
	
	
	
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	
    	UsernamePasswordToken myToken = (UsernamePasswordToken)authenticationToken;
    	
    	String userName = myToken.getUsername();
    	String password = String.valueOf(myToken.getPassword());

    	try {
    		usuarioDAO.getUsuario(userName, password);
    		SecurityUtils.getSubject().getSession().setAttribute("Perfil", seguridadDAO.getPerfil(userName));
    		return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), getName());	
		}
    	catch (Exception e) {
			throw new AuthenticationException("Usuario o password incorrecto");
    	}
    	
    }



	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String>			roles			= new HashSet<String>();
		Set<Permission>		permissions		= new HashSet<Permission>();
		Collection<String>	principalsList	= principals.byType(String.class);
		
//		if (principalsList.isEmpty()) {
//			throw new AuthorizationException("Empty principals list!");
//		}
		
//		for (Object prin : principals) {
//			Usuario usuario = usuarioDAO.getUsuario(prin.toString());
//			if(usuario.getEsCoManager()){
//				roles.add("admin");
//			}
//		}
		
		
		//LOADING STUFF FOR PRINCIPAL 
//		for (String userPrincipal : principalsList) {
//				
////				User user = this.userManager.loadById(userPrincipal.getId());
//				Usuario usuario = usuarioDAO.getUsuario(userPrincipal);
//				
////				Set<String> userRoles	= user.getRoles();
//				if(usuario.getEsCoManager()){
//					roles.add("admin");
//				}
//				
//				//si tenes mas de un rol
////				for (String r : userRoles) {
////					roles.add(r.getName());
////					Set<WildcardPermission> userPermissions	= r.getPermissions();
////					for (WildcardPermission permission : userPermissions) {
////						if (!permissions.contains(permission)) {
////							permissions.add(permission);
////						}
////					}
////				}
//		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		info.addRole("admin");
//		info.setRoles(roles);
		return info;
	}
}
