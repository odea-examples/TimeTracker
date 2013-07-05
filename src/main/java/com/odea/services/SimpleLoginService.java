package com.odea.services;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 15:58
 */

@Service
public class SimpleLoginService implements LoginService {
		
    private static final Logger logger = LoggerFactory.getLogger(SimpleLoginService.class);

    @Override
    public void login(String user, String passwd) {
        logger.debug("Login attempt user: " + user);
        UsernamePasswordToken token = new UsernamePasswordToken(user, passwd);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.hasRole("admin");
        currentUser.login(token);  	
    }
    
}
