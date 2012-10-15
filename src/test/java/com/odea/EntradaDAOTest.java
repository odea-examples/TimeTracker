package com.odea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.EntradaDAO;
import com.odea.domain.Entrada;

public class EntradaDAOTest extends AbstractTestCase {
	
	
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		
		dao.borrarTodosLosRegistros();
		
		dao.agregarEntrada(new Entrada(1,1,1,1,"No",1,1));
		dao.agregarEntrada(new Entrada(2,1,1,1,"No",1,1));
		dao.agregarEntrada(new Entrada(3,3,1,1,"No",1,1));
		dao.agregarEntrada(new Entrada(4,4,1,1,"No",1,1));
		
	}

	@Autowired
	private EntradaDAO dao;
	
	@Test
	public void getEntradaTest(){
		
		Collection<Entrada> col = new Vector<Entrada>();
		col = dao.buscarEntradas(1, 0, 0, 0, 0);
		
		if (col.size() != 1) {
			Assert.fail("La cantidad de entradas encontradas no es la esperada");
		}
	}
	
	@Test
	public void obtenerTodasLasEntradasTest() {
		Collection<Entrada> entradas = new ArrayList<Entrada>();
		
		entradas = dao.obtenerTodasLasEntradas();
		
		if (entradas.size() != 4) {
			Assert.fail("No devolvio la cantidad de entradas esperada");
		}
		
	}
	
	@Test
	public void getEntradaPorCriterioTest(){
		
		Collection<Entrada> col = new Vector<Entrada>();
		dao.agregarEntrada(new Entrada(1,1,1,1,"No",1,1));
		col = dao.buscarEntradas(1, 0, 0, 0, 0);
		
		if (col.size() != 2) {
			Assert.fail("La cantidad de entradas encontradas no es la esperada");
		}
	}
}
