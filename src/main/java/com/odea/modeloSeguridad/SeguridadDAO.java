package com.odea.modeloSeguridad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.dao.AbstractDAO;
import com.odea.domain.Usuario;

@Repository
public class SeguridadDAO extends AbstractDAO {
	
	public List<Funcionalidad> getFuncionalidades() {
		
		List<Funcionalidad> funcionalidades = jdbcTemplate.query("SELECT ID, GRUPO, CONCEPTO, ESTADO FROM SEC_FUNCIONALIDAD", new RowMapperFuncionalidad());
		
		return funcionalidades;
	}
	
	public List<Permiso> getPermisos(PerfilUsuario usuarioPerfil) {
		List<Permiso> permisos = jdbcTemplate.query("SELECT SEC_PERMISO_ID, SEC_FUNCIONALIDAD_ID, SEC_USUARIO_PERFIL_ID, ESTADO FROM SEC_PERMISO where SEC_USUARIO_PERFIL_ID = ?", new RowMapperPermiso(), usuarioPerfil.getID());
				
		return permisos;
	}
	public ArrayList<Permiso> getPermisos() {
		List<Permiso> permisos = jdbcTemplate.query("SELECT SEC_PERMISO_ID, SEC_FUNCIONALIDAD_ID, SEC_USUARIO_PERFIL_ID, ESTADO FROM SEC_PERMISO", new RowMapperPermiso());
			ArrayList<Permiso> arrayPermisos = new ArrayList<Permiso>();
				for (Permiso permiso : permisos) {
					arrayPermisos.add(permiso);
				}
		return arrayPermisos;
	}
	

	private class RowMapperFuncionalidad implements RowMapper<Funcionalidad>{
		@Override
		public Funcionalidad mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Funcionalidad funcionalidad = new Funcionalidad();
			funcionalidad.setID(rs.getInt(1));
			funcionalidad.setGrupo(rs.getString(2));
			funcionalidad.setConcepto(rs.getString(3));
			funcionalidad.setEstado(rs.getString(4));
			
			return funcionalidad;
		}
	}	
	

	private class RowMapperPermiso implements RowMapper<Permiso>{
		@Override
		public Permiso mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Permiso permiso = new Permiso();
			permiso.setID(rs.getInt(1));
			Funcionalidad funcionalidad = new Funcionalidad();
			funcionalidad.setID(rs.getInt(2));
			permiso.setFuncionalidad(funcionalidad);
			permiso.setEstado(rs.getString(4));
			
			return permiso;
		}
	}


	public void cambiarStatus(Usuario usuario, Integer permiso_id) {
		jdbcTemplate.update("UPDATE SEC_PERMISO SET ESTADO='Inactivo' WHERE SEC_PERMISO_ID=? AND SEC_USUARIO_PERFIL_ID=?",permiso_id,usuario.getIdUsuario());
		
	}	
	
}
