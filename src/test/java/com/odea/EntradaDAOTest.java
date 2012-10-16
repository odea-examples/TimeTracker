package com.odea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.EntradaDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.TicketBZ;
import com.odea.domain.TicketExterno;
import com.odea.domain.Usuario;

public class EntradaDAOTest extends AbstractTestCase {

	@Autowired
	private EntradaDAO dao;
	
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		
		dao.borrarTodosLosRegistros();
		Proyecto proyecto = new Proyecto();
		Actividad actividad = new Actividad();
		TicketBZ ticketbz = new TicketBZ();
		TicketExterno ticketext = new TicketExterno();
		Usuario usuario = new Usuario();
		
		dao.agregarEntrada(new Entrada(0, proyecto, actividad, 3.30, "esta es la nota", ticketbz, ticketext, usuario,"DATE"));
	}

	@Test
	public void getEntradaTest(){
		
		Collection<Entrada> col;
		col = dao.buscarEntradas(5, 0, 0, 0, 0);

		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 1);
	}
	
	@Test
	public void obtenerTodasLasEntradasTest() {
		Collection<Entrada> entradas = new ArrayList<Entrada>();
		
		entradas = dao.obtenerTodasLasEntradas();
		
		Assert.assertTrue("No devolvio la cantidad de entradas esperada", entradas.size() == 4);
	}
	
	@Test
	public void getEntradaPorCriterioTest(){
		
		Proyecto proyecto = new Proyecto();
		Actividad actividad = new Actividad();
		TicketBZ ticketbz = new TicketBZ();
		TicketExterno ticketext = new TicketExterno();
		Usuario usuario = new Usuario();
		Collection<Entrada> col;
		dao.agregarEntrada(new Entrada(0, proyecto, actividad, 3.30, "esta es la nota", ticketbz, ticketext, usuario,"DATE"));
		col = dao.buscarEntradas(14, 0, 0, 0, 0);
		
		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 1);
		
	}
}
