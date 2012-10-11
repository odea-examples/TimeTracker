package com.odea.services;

import com.odea.dao.UsuarioDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 15:58
 */

@Service
public class SimpleLoginService implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLoginService.class);
    @Autowired
    UsuarioDao usuarioDao;

    @Override
    public boolean login(String user, String passwd) {
        logger.debug("Login attempt user: " + user);
        return user.equalsIgnoreCase(usuarioDao.getUsuario(user).getNombre());
    }
}
