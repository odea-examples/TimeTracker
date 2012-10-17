package com.odea;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.UsuarioDAO;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

public class UsuarioDAOTest extends AbstractTestCase {

	@Autowired
	private UsuarioDAO dao;
	
	@Test
	public void getUsuarioTest() {
		Usuario usuario = new Usuario(8,"Sebastian","Gomez", "miPassword");
		
		try {
			dao.agregarUsuario(usuario);			
		} catch (Exception e) {
			Assert.fail("Ocurrio un error en el metodo agregarUsuario");
		}
		
		Usuario usuarioBuscado = dao.getUsuario(usuario);
		
		Assert.assertTrue("El usuario no es el mismo", usuario.getIdUsuario() == usuarioBuscado.getIdUsuario());
		
	}
	
	@Test
	public void getUsuariosDeProyectoTest() {
		Proyecto proyecto = new Proyecto(1, "Proyecto");
		
		Collection<Usuario> usuarios = dao.getUsuarios(proyecto);
		
		Assert.assertTrue("No se encontro la cantidad de usuarios esperada", usuarios.size() == 2);
		
	}
	
	

}
