package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;



@Repository
public class UsuarioDAO extends AbstractDAO {
	
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);
	
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_tipo = 'U'", new RowMapperUsuario());
		
		Collections.sort(usuarios);
		
		return usuarios;
	}
	
	
	public Usuario getUsuario(String nombre){		
		
		logger.debug("Buscando usuario con nombre de login: " + nombre);
		
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager, u.u_grupo, p.u_name, p.u_login FROM users u, SEC_ASIG_PERFIL ap, users p WHERE u.u_id = ap.SEC_USUARIO_ID AND ap.SEC_PERFIL_ID = p.u_id AND u.u_login = ?", 
				new RowMapperUsuario2(), nombre);
		
		return usuario;
		//SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager FROM users u WHERE u_login = ?
	}
	
	public void modificarUsuario(Usuario usuario)
	{
		jdbcTemplate.update("UPDATE users SET u_login=?, u_password=password(?) WHERE u_id=?", usuario.getNombreLogin(), usuario.getPassword(), usuario.getIdUsuario());
	}
	
	
	public Usuario getUsuario(int id){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_id = ?", 
				new RowMapperUsuario(), id);
		
		return usuario;
	}
	
	public void agregarUsuario(Usuario usuario){
		
		jdbcTemplate.update("INSERT INTO users(u_id,u_login,u_password) VALUES (?,?,?)", usuario.getIdUsuario(), usuario.getNombreLogin(), usuario.getPassword());
	}
	
	public Collection<Usuario> getUsuarios(Proyecto proyecto){
		Collection<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password FROM users u, user_bind up WHERE u.u_id=up.ub_id_u AND up.ub_id_p = ?", 
				new RowMapperUsuario(), proyecto.getIdProyecto());
		
		return usuarios;
		
	}
	
		
	public Usuario getUsuario(String nombre, String password){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u_id, u_login, u_password FROM users WHERE u_login=? AND u_password=password(?)", 
				new RowMapperUsuario(), nombre, password);
		
		return usuario;
	}
	
	public Integer getDedicacion(Usuario usuario){
		return jdbcTemplate.queryForInt("SELECT dedicacion FROM dedicacion_usuario WHERE usuario_id=? AND fecha_hasta is NULL", usuario.getIdUsuario());
	}
	
	public String getNombreYApellido(Usuario usuario){
		return jdbcTemplate.queryForObject("SELECT u_name FROM users WHERE u_id=?", String.class ,usuario.getIdUsuario());
	}
	
	
	  public void setDedicacion(Usuario usuario, int dedicacion){
		Date fechaActual = new Date();	
		try {
			  jdbcTemplate.update("UPDATE dedicacion_usuario SET fecha_hasta=? WHERE fecha_hasta is NULL AND usuario_id=?", fechaActual, usuario.getIdUsuario());
			  jdbcTemplate.update("INSERT INTO dedicacion_usuario (usuario_id, fecha_desde, dedicacion) VALUES (?,?,?)", usuario.getIdUsuario(), fechaActual, dedicacion);			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	 }
	
	

	public String getSector(Usuario usuario) {
		return jdbcTemplate.queryForObject("SELECT u_grupo FROM users WHERE u_id=?", String.class ,usuario.getIdUsuario());
	}


	public List<Usuario> getUsuarios(String sector) {
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_tipo = 'U'", 
				new RowMapperUsuario(), sector);
		
		return usuarios;
	}
	
	
	//Alta usuarios
	public void guardarNuevoUsuario(Usuario usuario) {
		
		String sqlUsuario = "INSERT INTO users (u_login, u_password, u_name, u_grupo) VALUES (?, password(?), ?, ?)";
		
		jdbcTemplate.update(sqlUsuario, usuario.getNombreLogin(), usuario.getPassword(), usuario.getNombre(), usuario.getGrupo());
		
		
		Integer ID = jdbcTemplate.queryForInt("SELECT u_id FROM users WHERE u_login = ?", usuario.getNombreLogin());
		
		
		String sqlPerfil = "INSERT INTO SEC_ASIG_PERFIL (SEC_USUARIO_ID, SEC_PERFIL_ID) VALUES (?,?)";
		
		jdbcTemplate.update(sqlPerfil, usuario.getIdUsuario(), usuario.getPerfil().getIdUsuario());
	}
	
	//Modificacion usuarios
	public void modificacionUsuario(Usuario usuario) {
			
			String sqlUsuario = "UPDATE users SET u_login = ?, u_name = ?, u_password = password(?), u_grupo = ? WHERE u_id = ?";
			
			jdbcTemplate.update(sqlUsuario, usuario.getNombreLogin(), usuario.getNombre(), usuario.getPassword(), usuario.getGrupo(), usuario.getIdUsuario());
			
			String sqlPerfil = "UPDATE SEC_ASIG_PERFIL SET SEC_PERFIL_ID = ? WHERE SEC_USUARIO_ID = ?";
			
			jdbcTemplate.update(sqlPerfil, usuario.getPerfil().getIdUsuario(), usuario.getIdUsuario());
			
	}
	
	
	
	public void cambiarPerfil(Usuario usuario, String nombrePerfil) {
	
		logger.debug("SE CAMBIA EL PERFIL DEL USUARIO: " + usuario.getNombreLogin() + " POR PERFIL: " + nombrePerfil);
		
		int perfilID = jdbcTemplate.queryForInt("SELECT u_id FROM users WHERE u_login = ?", nombrePerfil);
		
		logger.debug("PERFIL ID: " + perfilID);
		
		String sql = "UPDATE SEC_ASIG_PERFIL SET SEC_PERFIL_ID = ? WHERE SEC_USUARIO_ID = ?";
		
		jdbcTemplate.update(sql, perfilID, usuario.getIdUsuario());
		
		logger.debug("CAMBIO DE ROL REALIZADO");
	}
	
	
	public List<Usuario> getUsuariosConPerfiles() {
		
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager, u.u_grupo, p.u_name, p.u_login FROM users u, SEC_ASIG_PERFIL ap, users p WHERE u.u_id = ap.SEC_USUARIO_ID AND ap.SEC_PERFIL_ID = p.u_id ORDER BY u.u_name ASC", new RowMapperUsuario2());
		
		return usuarios;
	}
	public List<Usuario> getUsuariosConPerfiles(String sector) {
		
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager, u.u_grupo, p.u_name, p.u_login FROM users u, SEC_ASIG_PERFIL ap, users p WHERE u.u_id = ap.SEC_USUARIO_ID AND ap.SEC_PERFIL_ID = p.u_id AND u.u_grupo = ? ORDER by u.u_name", new RowMapperUsuario2(), sector);
		
		return usuarios;
	}
	
	public List<Usuario> getUsuariosConPerfilesNingunSector() {
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager, u.u_grupo, p.u_name, p.u_login FROM users u, SEC_ASIG_PERFIL ap, users p WHERE u.u_id = ap.SEC_USUARIO_ID AND ap.SEC_PERFIL_ID = p.u_id AND (u.u_grupo = ? OR u.u_grupo is NULL OR u.u_grupo='') ORDER by u.u_name", new RowMapperUsuario2(),"Ninguno");
		
		return usuarios;
	}
	
	
	//RowMappers
	
	private class RowMapperUsuario implements RowMapper<Usuario>{

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3));
		}
		
	}
	
	private class RowMapperUsuario2 implements RowMapper<Usuario>{

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			Usuario perfil = new Usuario(0, rs.getString(7), rs.getString(8), "PasswordNula");
			Usuario usuario = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), (rs.getInt(5) == 1), perfil);
			usuario.setGrupo(rs.getString(6));
			
			return usuario;
		}
		
	}

	public void cambiarGrupo(Usuario usuario, String grupo) {
		
		logger.debug("SE PROCEDE A CAMBIAR AL USUARIO - ID: "+ usuario.getIdUsuario() +" - Login: " + usuario.getNombreLogin() + " - AL GRUPO: " + grupo);
		
		jdbcTemplate.update("UPDATE users SET u_grupo = ? WHERE u_id = ?", grupo, usuario.getIdUsuario());
		
		logger.debug("SE HA CAMBIADO AL USUARIO - ID: "+ usuario.getIdUsuario() +" - Login: " + usuario.getNombreLogin() + " - AL GRUPO: " + grupo);
		
	}





	

	
}