package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;



@Service
public class UsuarioDAO extends AbstractDAO {
	
	public Usuario getUsuario(String nombre){
		Usuario usuario = jdbcTemplate.queryForObject("SELECT * FROM USUARIOS WHERE nombre='" + nombre + "'", new RowMapper<Usuario>(){
				@Override
				public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
				}
			});
		return usuario;
	}
	
	
	public void agregarUsuario(Usuario usuario){
		
		jdbcTemplate.update("INSERT INTO usuarios VALUES (?,?,?,?)", usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(), usuario.getPassword());
	}
	
	public Collection<Usuario> getUsuarios(Proyecto proyecto){
		Collection<Usuario> usuarios = jdbcTemplate.query("SELECT u.id_usuario, u.nombre, u.apellido, u.password FROM usuarios u, usuarios_proyecto up WHERE u.id_Usuario = up.id_usuario", new RowMapper<Usuario>() {
			
			@Override
			public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Usuario(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3));
			}
		});
		
		return usuarios;
		
	}

}
