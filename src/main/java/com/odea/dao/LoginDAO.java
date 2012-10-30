package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Usuario;

@Service
public class LoginDAO extends AbstractDAO{
	public Boolean Logear(String login, String psswd){
		try {
			Usuario usuario = jdbcTemplate.queryForObject("SELECT * FROM users WHERE u_login='" + login + "' AND u_password=password('" + psswd + "')	", new RowMapper<Usuario>(){
					@Override
					public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Usuario(1,"no te interesa","tampoco te interesa");
					}
				});
			return true;
		} catch (DataAccessException e) {
			System.out.println("clave incorrecta");
			return false;
		}
	}
}
