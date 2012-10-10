package com.odea;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.services.LoginService;

public class LoginTest extends AbstractTestCase{
	@Autowired
	LoginService loginService;
	@Test
	public void loginTest(){
		assertTrue(loginService.login("Pablo", "bjkbvhjn"));
	}

}
