package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;



@Repository
public class UsuarioDAO extends AbstractDAO {
	
	public Usuario getUsuario(String nombre){
		System.out.println("Aca esta el otro 2: " + nombre);
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_login=?", 
				new RowMapperUsuario(), nombre);
		
		return usuario;
	}
	
	public void modificarUsuario(Usuario usuario)
	{
		jdbcTemplate.update("UPDATE users SET u_login=?, u_password=password(?) WHERE u_id=?", usuario.getNombre(), usuario.getPassword(), usuario.getIdUsuario());
	}
	
	
	public Usuario getUsuario(int id){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT u.u_id, u.u_login, u.u_password FROM users u WHERE u_id=?", 
				new RowMapperUsuario(), id);
		
		return usuario;
	}
	
	public void agregarUsuario(Usuario usuario){
		
		jdbcTemplate.update("INSERT INTO users(u_id,u_login,u_password) VALUES (?,?,?)", usuario.getIdUsuario(), usuario.getNombre(), usuario.getPassword());
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
	
	
	public class RowMapperUsuario implements RowMapper<Usuario>{

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3));
		}
		
	}
	
	
}