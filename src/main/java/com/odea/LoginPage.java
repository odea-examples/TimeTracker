package com.odea;

import com.odea.services.LoginService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 16:04
 */
public class LoginPage extends WebPage {
    private static final long serialVersionUID = 1L;
    @SpringBean
    private LoginService loginService;
    
    public LoginPage(final PageParameters parameters) {
        super(parameters);

        String result = "Error";

        if(this.loginService.login("Pablo","Pablo")){
            result = "OK";
        }

        add(new Label("login",result));

    }

    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }
}
