package com.odea.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odea.dao.UsuarioDao;

/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 15:58
 */

@Service
public class SimpleLoginService implements LoginService{
    @Autowired
    UsuarioDao usuarioDao;
    
	@Override
    public boolean login(String user, String passwd) {
        return user.equalsIgnoreCase(usuarioDao.getUsuario(user).getNombre());
    }
}
