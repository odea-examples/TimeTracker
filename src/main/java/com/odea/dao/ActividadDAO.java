package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;

public class ActividadDAO extends AbstractDAO {
	
	public List<Actividad> getActividades(Proyecto proyecto)
	{
		List<Actividad> actividades = jdbcTemplate.query("SELECT pa.id_actividad, a.nombre FROM actividad a, proyecto_actividad pa WHERE pa.id_actividad = a.id_actividad AND pa.id_proyecto = " + proyecto.getIdProyecto(), new RowMapper<Actividad>() {
			@Override
			public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Actividad(rs.getInt(1), rs.getString(2));
			}
		});
		
		return actividades;
	}
	
	
	
}
