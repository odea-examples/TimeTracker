package com.odea;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.EntradaDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

public class EntradaDAOTest extends AbstractTestCase {

	@Autowired
	private EntradaDAO dao;
	Date antes;
	Date despues;
	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		

		Proyecto proyecto = new Proyecto(1, "Proyecto 1");
		Actividad actividad = new Actividad(1,"Actividad 1");
		String ticketBZ = "1";
		String ticketExt = "1";
		String sistemaExt = "1";
		Usuario usuario = new Usuario(1, "Nombre", "Apellido", "mi contraseña");
		Date fecha = new Date(System.currentTimeMillis());
		antes = new Date(System.currentTimeMillis() - 1000000000);
		despues = new Date(System.currentTimeMillis() + 1000000000);
		
		
		dao.agregarEntrada(new Entrada(1, proyecto, actividad, 2387, "Nota", ticketBZ, ticketExt, sistemaExt, usuario, null));
	}

	@Test
	public void getEntradaTest(){
		Collection<Entrada> col;
		col = dao.getEntradas(antes, despues);
		System.out.println(col.size());
		
		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 1);
	}
	
	/*
	@Test
	public void obtenerTodasLasEntradasTest() {
		Collection<Entrada> entradas = new ArrayList<Entrada>();
		
		
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
	*/
}
