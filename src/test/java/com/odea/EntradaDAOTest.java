package com.odea;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.EntradaDAO;
import com.odea.domain.Entrada;

public class EntradaDAOTest extends AbstractTestCase {
	
	@Autowired
	private EntradaDAO dao;
	
	@Test
	public void getEntrada(){
		Entrada entrada = new Entrada(1,1,1,3.30,"No",1,1);
		
		dao.agregarEntrada(entrada);
		Entrada entradaBuscada = dao.getEntrada(1);
		
		if (!(entradaBuscada.getId_entrada() == entrada.getId_entrada())) {
			Assert.fail("La entrada encontrada no es la esperada");
		}
	}
	
	
}
