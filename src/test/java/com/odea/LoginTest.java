package com.odea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.services.EncodingService;
import com.odea.services.LoginService;

public class LoginTest extends AbstractTestCase {
    @Autowired
    LoginService loginService;
    @Autowired
    EncodingService encodingService;

    @Test
    public void loginTest() {
    	loginService.login("Pablo", "bjkbvhjn");
    }

    @Test
    public void hashTest() {
        String plain1 = "prueba";
        String plain2 = "prueba2";
        assertEquals(encodingService.encode(plain1), encodingService.encode(plain1));
        assertFalse(encodingService.encode(plain1).equals(encodingService.encode(plain2)));
    }
}
