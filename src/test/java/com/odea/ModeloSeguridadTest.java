package com.odea;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.odea.modeloSeguridad.Funcionalidad;
import com.odea.modeloSeguridad.PerfilUsuario;
import com.odea.modeloSeguridad.Permiso;
import com.odea.modeloSeguridad.SeguridadDAO;

public class ModeloSeguridadTest extends AbstractTestCase {

	@Autowired
	private SeguridadDAO seguridadDAO;
	
    private static final Logger logger = LoggerFactory.getLogger(SeguridadDAO.class);
	
	@Test
	public void FuncionalidadesTest() {
		List<Funcionalidad> funcionalidades = seguridadDAO.getFuncionalidades();
		
		for (Funcionalidad funcionalidad : funcionalidades) {
			logger.debug(funcionalidad.toString());
		}
	}
	
	@Test
	public void PermisosUsuarioTest() {
		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setID(1);
		
		List<Permiso> permisos = seguridadDAO.getPermisos(perfilUsuario);
		
		for (Permiso permiso : permisos) {
			logger.debug(permiso.toString());
		}
	}

	

	
}
