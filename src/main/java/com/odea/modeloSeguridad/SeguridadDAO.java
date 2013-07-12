package com.odea.modeloSeguridad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.dao.AbstractDAO;
import com.odea.dao.ActividadDAO;
import com.odea.domain.Usuario;

@Repository
public class SeguridadDAO extends AbstractDAO {
	
    private static final Logger logger = LoggerFactory.getLogger(SeguridadDAO.class);
	
	
	public List<Funcionalidad> getFuncionalidades() {
		
		List<Funcionalidad> funcionalidades = jdbcTemplate.query("SELECT SEC_FUNCIONALIDAD_ID, GRUPO, CONCEPTO, ESTADO FROM SEC_FUNCIONALIDAD", new RowMapperFuncionalidad());
		
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
	
	
	
	public List<Usuario> getPerfiles()
	{
		List<Usuario> listaUsuarios = jdbcTemplate.query("SELECT u_id, u_login, u_password, u_name FROM users WHERE u_tipo = 'P'", new RowMapperUsuario());
		
		return listaUsuarios;
	}
	
	
	//Cambio de estado de permisos
	public void cambiarStatus(Usuario usuario, Funcionalidad funcionalidad, Boolean habilitado) {
		
		String estadoPermiso = habilitado ? "Habilitado" : "Inhabilitado";
		
		logger.debug("Se actualiza Permiso: " + funcionalidad.getID() + " - Usuario: " + usuario.getNombre() + " - Estado: " + estadoPermiso);
		
		Integer updatedRows = jdbcTemplate.update("UPDATE SEC_PERMISO SET ESTADO = '"+ estadoPermiso +"' WHERE SEC_USUARIO_PERFIL_ID = ? AND SEC_FUNCIONALIDAD_ID = ? ", usuario.getIdUsuario(),funcionalidad.getID());
		
		if(updatedRows.equals(0))
		{
			jdbcTemplate.update("INSERT INTO SEC_PERMISO VALUES(0,?,?,'Habilitado')",funcionalidad.getID(),usuario.getIdUsuario());
			
//			throw new RuntimeException("Permiso no encontrado en BD. INFO: PermisoID: " + permiso.getID() + " - UsuarioID: " + usuario.getIdUsuario());
		}
		
	}	
	
	public List<Permiso> getPermisos(Usuario usuario) {
		
		List<Permiso> permisos = jdbcTemplate.query("SELECT SEC_PERMISO_ID, SEC_FUNCIONALIDAD_ID, SEC_USUARIO_PERFIL_ID, ESTADO FROM SEC_PERMISO WHERE SEC_USUARIO_PERFIL_ID = ?", new RowMapperPermiso(), usuario.getIdUsuario());
		
		return permisos;
		
	}
	
	public List<Usuario> getUsuariosQueTienenPermiso(Permiso permiso) {
		
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u_id, u_login, u_password, u_name FROM users WHERE u_id in (SELECT SEC_USUARIO_PERFIL_ID FROM SEC_PERMISO WHERE SEC_PERMISO_ID = ? AND ESTADO = 'Habilitado')", new RowMapperUsuario(), permiso.getID());
		
		return usuarios;
	}
	
	
	public void altaPerfil(String nombre) {
		logger.debug("Se procede a guardar nuevo Perfil con nombre: " + nombre);
		
		jdbcTemplate.update("INSERT INTO users (u_login, u_name, u_tipo) VALUES (?,?,'P')", nombre, nombre);
		
		logger.debug("Perfil guardado satisfactoriamente");
	}
	
	public String getPerfil(String loginUsuario) {
		
		logger.debug("Se busca nombre de perfil del usuario: " + loginUsuario);
		
		String sql = "SELECT p.u_name FROM users u, users p, SEC_ASIG_PERFIL ap WHERE u.u_id = ap.SEC_USUARIO_ID AND ap.SEC_PERFIL_ID = p.u_id AND u.u_login = ?";
		
		String nombrePerfil = jdbcTemplate.queryForObject(sql, String.class, loginUsuario);
		
		if (nombrePerfil != null){
			logger.debug("Perfil encontrado: " + nombrePerfil);
		} else {
			throw new RuntimeException("No se puede encontrar el perfil del usuario: " + loginUsuario);
		}
		
		return nombrePerfil;
	}
	
	public List<String> getNombresPerfiles() {
		List<String> nombresPerfiles = jdbcTemplate.query("SELECT u_login FROM users WHERE u_tipo = 'P'", new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
			
		});
		
		return nombresPerfiles;
	}
	
	
	//RowMappers

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
	
	private class RowMapperUsuario implements RowMapper<Usuario> {
		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Usuario unUsuario = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
			
			unUsuario.setNombre(rs.getString(4));
			
			return unUsuario;
		}
	}

	public List<Usuario> getUsuariosQueTienenUnaFuncionalidad(Funcionalidad funcionalidad) {
		
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u_id, u_login, u_password, u_name FROM users WHERE u_id in (SELECT SEC_USUARIO_PERFIL_ID FROM SEC_PERMISO WHERE SEC_FUNCIONALIDAD_ID = ? AND ESTADO = 'Habilitado')", new RowMapperUsuario(), funcionalidad.getID());
		return usuarios;
	}



	
}
