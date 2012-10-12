package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Usuario;



@Service
public class UsuarioDao extends AbstractDAO {
	
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
		
		int hash = usuario.getPassword().hashCode();
		String hashPassword=String.valueOf(hash);
		
		jdbcTemplate.update("INSERT INTO usuarios VALUES (?,?,?,?)", new Object [] {usuario.getId(), usuario.getNombre(), usuario.getApellido(), hashPassword});
	}

}
