package com.odea;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.odea.behavior.focusOnLoad.FocusOnLoadBehavior;
import com.odea.services.LoginService;


/**
 * User: pbergonzi
 * Date: 09/10/12
 * Time: 16:04
 */
public class LoginPage extends BasePage {
	
    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private LoginService loginService;


    private String userName;
    private String passwd;

    public LoginPage() {
        LoginForm loginForm = new LoginForm("loginForm");
        add(loginForm);
    }

    public void login() {
        this.loginService.login(this.userName, passwd);
    }

    class LoginForm extends Form {
        private IModel<LoginPage> loginModel = new CompoundPropertyModel<LoginPage>(LoginPage.this);

        public LoginForm(String id) {
            super(id);
            this.setDefaultModel(this.loginModel);
            RequiredTextField<String> userName = new RequiredTextField<String>("userName");
            userName.setLabel(Model.of("Usuario"));
            userName.add(new FocusOnLoadBehavior());
            PasswordTextField passwd = new PasswordTextField("passwd");
            passwd.setLabel(Model.of("Password"));
            FeedbackPanel feedBackPanel = new FeedbackPanel("feedBackPanel");         
            
            AjaxButton submit = new AjaxButton("submit", this) {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    try{
                    	LoginPage.this.login();
                    	//Mejor que entre directamente a EntradasPage
                    	//this.continueToOriginalDestination();
                    	setResponsePage(EntradasPage.class);
                    }catch(AuthenticationException ex){
                    	error(ex.getMessage());
                    }
                    target.add(form);
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                	target.add(form);
                }
            };

            
            add(feedBackPanel);
            add(userName);
            add(passwd);
            add(submit);
            setOutputMarkupId(true);
        }
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}
