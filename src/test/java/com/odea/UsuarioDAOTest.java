package com.odea;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.UsuarioDAO;
import com.odea.domain.Usuario;

public class UsuarioDAOTest extends AbstractTestCase {

	@Autowired
	private UsuarioDAO dao;
	
	@Test
	public void agregarUsuarioTest() {
		Usuario usuario = new Usuario(8,"Sebastian","Gomez", "miPassword");
		
		try {
			dao.agregarUsuario(usuario);			
		} catch (Exception e) {
			Assert.fail("Ocurrio un error en el metodo agregarUsuario");
		}
		
		Usuario usuarioBuscado = dao.getUsuario("Sebastian");
		
		if (!(usuario.getId() == usuarioBuscado.getId())) {
			Assert.fail("El usuario no es el mismo");
		}
		
	}

}
