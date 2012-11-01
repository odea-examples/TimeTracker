package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;

@Repository
public class ActividadDAO extends AbstractDAO {
	
	public List<Actividad> getActividades(Proyecto proyecto)
	{
		List<Actividad> actividades = jdbcTemplate.query("SELECT pa.ab_id_a, a.a_name FROM activities a, activity_bind pa WHERE pa.ab_id_a = a.a_id AND pa.ab_id_p = ?", new RowMapperActividad(), proyecto.getIdProyecto());
		
		return actividades;
	}
	
	
	
	public List<Actividad> getActividades()
	{
		List<Actividad> actividades = jdbcTemplate.query("SELECT a.a_id, a.a_name FROM activities a", new RowMapperActividad());
		
		return actividades;
	}
	
	
	
	
	private class RowMapperActividad implements RowMapper<Actividad>{
		@Override
		public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Actividad(rs.getInt(1), rs.getString(2));
		}
	}
	
	
	
	
}
