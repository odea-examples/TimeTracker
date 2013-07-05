package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;



@Repository
public class UsuarioDAO extends AbstractDAO {
	
	public List<Usuario> getUsuarios() {
		List<Usuario> usuarios = jdbcTemplate.query("SELECT u.u_id, u.u_login, u.u_password FROM users u", new RowMapperUsuario());
		
		Collections.sort(usuarios);
		
		return usuarios;
	}
	
	
	public Usuario getUsuario(String nombre){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password, u.u_name, u.u_comanager FROM users u WHERE u_login=?", 
				new RowMapperUsuario2(), nombre);
		
		return usuario;
	}
	
	public void modificarUsuario(Usuario usuario)
	{
		jdbcTemplate.update("UPDATE users SET u_login=?, u_password=password(?) WHERE u_id=?", usuario.getNombreLogin(), usuario.getPassword(), usuario.getIdUsuario());
	}
	
	
	public Usuario getUsuario(int id){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_id=?", 
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
	
	
	public class RowMapperUsuario implements RowMapper<Usuario>{

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3));
		}
		
	}
	public class RowMapperUsuario2 implements RowMapper<Usuario>{

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),(rs.getInt(5)==1));
		}
		
	}
	
	
}