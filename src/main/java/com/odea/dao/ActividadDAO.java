package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;

@Repository
public class ActividadDAO extends AbstractDAO {
	
    private static final Logger logger = LoggerFactory.getLogger(ActividadDAO.class);

	
	public List<Actividad> getActividades(Proyecto proyecto)
	{
		logger.debug("Buscando actividades por proyecto - Proyecto: " + proyecto.getNombre());
		
		List<Actividad> actividades = jdbcTemplate.query("SELECT pa.ab_id_a, a.a_name FROM activities a, activity_bind pa WHERE pa.ab_id_a = a.a_id AND pa.ab_id_p = ?", new RowMapperActividad(), proyecto.getIdProyecto());
		
		logger.debug("Busqueda exitosa");
		
		Collections.sort(actividades);
		
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
