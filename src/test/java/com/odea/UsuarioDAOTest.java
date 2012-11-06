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
	public void getUsuariosDeProyectoTest() {
		Proyecto proyecto = new Proyecto(1, "Proyecto");
		
		Collection<Usuario> usuarios = dao.getUsuarios(proyecto);
		System.out.println(usuarios.size());
		Assert.assertTrue("No se encontro la cantidad de usuarios esperada", usuarios.size() == 53);
		Usuario usuario= dao.getUsuario("invitado");
		Assert.assertTrue("error al buscar invitado", usuario.getIdUsuario()==57);
		
	}
	
	

}
