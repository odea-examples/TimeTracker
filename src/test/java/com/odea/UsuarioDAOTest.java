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
		Usuario usuario = new Usuario(356,"Sebastian", "miPassword");
		dao.agregarUsuario(usuario);
		
//		try {
//			dao.agregarUsuario(usuario);			
//		} catch (Exception e) {
//			Assert.fail("Ocurrio un error en el metodo agregarUsuario");
//			System.out.println(e);
//		}
		
	}
	
	@Test
	public void getUsuariosDeProyectoTest() {
		Proyecto proyecto = new Proyecto(1, "Proyecto");
		
		Collection<Usuario> usuarios = dao.getUsuarios(proyecto);
		System.out.println(usuarios.size());
		Assert.assertTrue("No se encontro la cantidad de usuarios esperada", usuarios.size() == 53);
		
	}
	
	

}
