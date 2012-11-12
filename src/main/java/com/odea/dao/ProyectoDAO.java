package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.odea.domain.Proyecto;

@Repository
public class ProyectoDAO extends AbstractDAO {
	
	public List<Proyecto> getProyectos()
	{
		List<Proyecto> proyectos = jdbcTemplate.query("SELECT * FROM projects", new RowMapper<Proyecto>() {
			@Override
			public Proyecto mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Proyecto(rs.getInt(1), rs.getString(3));
			}
		});
		
		Collections.sort(proyectos);
		
		return proyectos;
	}
	
}
