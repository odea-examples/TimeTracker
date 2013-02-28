package com.odea;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.dao.UsuarioDAO;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

public class UsuarioDAOTest extends AbstractTestCase {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Test
	public void getUsuariosDeProyectoTest() {
		Proyecto proyecto = new Proyecto(1, "Proyecto",true);
		
		Collection<Usuario> usuarios = usuarioDAO.getUsuarios(proyecto);
		Assert.assertTrue("No se encontro la cantidad de usuarios esperada", usuarios.size() == 53);
		Usuario usuario= usuarioDAO.getUsuario("invitado");
		Assert.assertTrue("Error al buscar invitado", usuario.getIdUsuario()==57);
		
	}
	
	@Test
	public void obtenerTodosLosUsuariosTest() {
		List<Usuario> usuarios = usuarioDAO.getUsuarios();
		org.springframework.util.Assert.notEmpty(usuarios, "No se encontraron usuarios. La coleccion es nula o esta vacia.");
	}
	
	

}
