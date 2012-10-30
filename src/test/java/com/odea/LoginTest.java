package com.odea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.LoginDAO;
import com.odea.services.EncodingService;
import com.odea.services.LoginService;

public class LoginTest extends AbstractTestCase {
	
@Autowired
	LoginDAO loginDAO;
//    @Autowired
//    LoginService loginService;
//    @Autowired
//    EncodingService encodingService;
/*
    @Test
    public void loginTest() {

    	loginService.login("Pablo", "bjkbvhjn");

        //assertTrue(loginService.login("Pablo", "bjkbvhjn"));

    }
*/
//    @Test
//    public void hashTest() {
//        String plain1 = "invitado";
//        String plain2 = "prueba2";
//        assertEquals(encodingService.encode(plain1), encodingService.encode(plain1));
//        assertFalse(encodingService.encode(plain1).equals(encodingService.encode(plain2)));
//        System.out.println(encodingService.encode(plain1));
//    }
    
    @Test public void logintest() {
    	boolean boolean2=(loginDAO.Logear("invitado","invitado"));
    	System.out.println(boolean2);
    	
    }
}
