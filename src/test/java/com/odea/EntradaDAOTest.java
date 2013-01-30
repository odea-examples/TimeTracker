package com.odea;

import java.sql.Timestamp;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;
import com.odea.services.DAOService;

public class EntradaDAOTest extends AbstractTestCase {

	@Autowired
	private DAOService daoService;


	@Test
	public void agregarEntradaTest(){
		Usuario usuario = new Usuario(57, "Invitado", "invitado");
		LocalDate fechaHoy = new LocalDate();
		
		Entrada entrada = new Entrada(new Timestamp(fechaHoy.toDate().getTime()), new Proyecto(10, "YPF-DBU"), new Actividad(10, "Testing"), "1", "Nota", 1, "1", "sistemaExterno", usuario, fechaHoy.toDate());
		daoService.agregarEntrada(entrada);
		daoService.borrarEntrada(entrada);
	}


}
