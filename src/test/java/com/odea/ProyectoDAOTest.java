package com.odea;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.ProyectoDAO;

public class ProyectoDAOTest extends AbstractTestCase {

	@Autowired
	private ProyectoDAO dao;
	
	@Test
	public void getEntradasTest(){
		Assert.assertTrue("La coleccion de proyectos es nula", dao.getProyectos() != null);
	}
	
}
