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
	private Date antes;
	private Date despues;
	private Date fechaIntermedia;
	private Proyecto proyecto;
	private Usuario usuario;
	private Actividad actividad;

	
	@Override
	@Before
	public void setUp() {
		super.setUp();
		
		proyecto = new Proyecto(1, "Proyecto 1");
		usuario = new Usuario(1, "Nombre", "Apellido", "mi contraseña");
		actividad = new Actividad(1,"Actividad 1");
		
		antes = new Date(System.currentTimeMillis() - 1000000000);
		despues = new Date(System.currentTimeMillis() + 1000000000);
		fechaIntermedia = new Date(System.currentTimeMillis() + 100000000);
		
		String ticketBZ = "1";
		String ticketExt = "1";
		String sistemaExt = "1";
		
		Date fecha = new Date(System.currentTimeMillis());
		Proyecto proyecto2 = new Proyecto(2,"otroProyecto");
		Usuario usuario2 = new Usuario(2, "unNombre", "unApellido", "algunPassword");
		
		dao.agregarEntrada(new Entrada(1, proyecto, actividad, 2387, "Nota", ticketBZ, ticketExt, sistemaExt, usuario, fecha));
		dao.agregarEntrada(new Entrada(2, proyecto, actividad, 2387, "Nota", ticketBZ, ticketExt, sistemaExt, usuario2, fecha));
		dao.agregarEntrada(new Entrada(3, proyecto2, actividad, 2387, "Nota", ticketBZ, ticketExt, sistemaExt, usuario, fechaIntermedia));
		
	}

	@Test
	public void getEntradasTest(){
		

		Collection<Entrada> col;
		col = dao.getEntradas(antes, despues);
		
		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 3);
	}
	
	@Test
	public void getEntradasDeProyectoTest(){
		Collection<Entrada> col;
		col = dao.getEntradas(proyecto, antes, despues);

		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 4);
	}
	
	@Test
	public void getEntradasDeUsuarioTest(){
		Collection<Entrada> col;
		col = dao.getEntradas(usuario, antes, despues);

		Assert.assertTrue("La cantidad de entradas encontradas no es la esperada", col.size() == 6);
	}

	
	@Test
	public void getTotalHorasTest(){
		Collection<Entrada> col;
		col = dao.getEntradas(usuario, antes, despues);
		int numero = dao.totalHorasSemana(col);
		Assert.assertTrue("La cantidad de horas es incorrecta", numero==19096);
	}


}
