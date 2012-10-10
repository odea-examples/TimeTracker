package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Usuario;

@Service
public class UsuarioDao extends AbstractDAO {
	
	public Usuario getUsuario(String nombre){
		Usuario usuario = jdbcTemplate.queryForObject("select * from usuarios where nombre='" + nombre + "'", new RowMapper<Usuario>(){
				@Override
				public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3));
				}
			});
		return usuario;
	}

}
