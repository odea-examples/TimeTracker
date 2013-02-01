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

	@Test
	public void getEntradasDiariasTest() {
		Usuario usuario = new Usuario(57, "Invitado", "invitado");
		LocalDate fecha = new LocalDate().minusYears(5);
		System.out.println(fecha);
		
		Entrada entrada = new Entrada(new Timestamp(fecha.toDate().getTime()), new Proyecto(10, "YPF-DBU"), new Actividad(10, "Testing"), "1", "Nota", 1, "1", "sistemaExterno", usuario, fecha.toDate());
		
		//TODO: Completar test
//		daoService.agregarEntrada(entrada);
		
//		List<Data> entradasDia = daoService.getEntradasDia(usuario, fecha);
//		
//		Assert.notEmpty(entradasDia, "No se encontro la entrada.");
//		
//		try {
//			Data data = entradasDia.get(0);
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//			Date parsedDate = dateFormat.parse(data.getId());
//			Timestamp timestamp = new Timestamp(parsedDate.getTime());
//			daoService.borrarEntrada(timestamp);
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}

	}
	
	
	

}
