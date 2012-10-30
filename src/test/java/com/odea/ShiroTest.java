package com.odea;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: pbergonzi
 * Date: 22/10/12
 * Time: 11:58
 */
public class ShiroTest extends AbstractTestCase {
    @Autowired
    DefaultSecurityManager securityManager;

    @Test
    public void loginTest() {
        SecurityUtils.setSecurityManager(securityManager);
        UsernamePasswordToken token = new UsernamePasswordToken("invitado", "invitado");
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
    }
}
