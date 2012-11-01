package com.odea.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.UsuarioDAO;

/**
 * User: pbergonzi
 * Date: 22/10/12
 * Time: 11:32
 */
public class OdeaRealm extends AuthenticatingRealm {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    	
    	UsernamePasswordToken userPasswordToken = (UsernamePasswordToken)authenticationToken;
    	
    	String userName = userPasswordToken.getUsername();
    	String password = String.valueOf(userPasswordToken.getPassword());
    	
    	try {
    		usuarioDAO.getUsuario(userName, password);
    		return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), getName());	
		}
    	catch (Exception e) {
			throw new AuthenticationException("Usuario o password incorrecto");
    	}
    	
    }
}
