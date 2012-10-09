package com.odea.services;

import org.springframework.stereotype.Service;

/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 15:58
 */

@Service
public class SimpleLoginService implements LoginService{
    @Override
    public boolean login(String user, String passwd) {
        return user.equalsIgnoreCase(passwd);
    }
}
