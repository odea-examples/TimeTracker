package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
		
		Collections.sort(actividades);
		
		return actividades;
	}
	
	public void insertarActividad(Actividad actividad){
		jdbcTemplate.update("INSERT INTO activities(a_id,a_name) VALUES(?,?)", actividad.getIdActividad(), actividad.getNombre());
	}
	
	public void insertarNuevaActividad(Actividad actividad){
		int id = jdbcTemplate.queryForInt("SELECT max(a_id) FROM activities") + 1;
		jdbcTemplate.update("INSERT INTO activities(a_id,a_name) VALUES(?,?)", id, actividad.getNombre());
	}
	
	public Actividad buscarPorNombre(String nombre){
		
		if (nombre.charAt(0)== " ".charAt(0)){
			nombre = nombre.substring(1);
		}
		
		Integer id;
		try {
			id = jdbcTemplate.queryForInt("SELECT a_id FROM activities where a_name=?", nombre);
		} catch (DataAccessException e) {
			nombre = nombre.replaceAll("ó","Ã³").replaceAll("é","Ã©").replaceAll("ñ","Ã±").replaceAll("á","Ã¡").replaceAll("í","Ã­") ;		
			id = jdbcTemplate.queryForInt("SELECT a_id FROM activities where a_name=?", nombre);
		}
		
		
		System.out.println(nombre);
		
		
		
		
		return new Actividad(id, nombre);
	}
	

	public void borrarActividad(Actividad actividad){
		jdbcTemplate.update("DELETE FROM activities WHERE a_id=?",actividad.getIdActividad());
		jdbcTemplate.update("DELETE FROM activity_bind WHERE ab_id_a=?",actividad.getIdActividad());
	}
	
	public void modificarActividad(String nombre, int idFinal) {
		jdbcTemplate.update("UPDATE activities SET a_name=? WHERE a_id=?",nombre, idFinal);
	}
	
	public List<Actividad> actividadesOrigen(Proyecto proyecto){
		
		String sql = "SELECT DISTINCT pa.ab_id_a, a.a_name FROM activities a, activity_bind pa ";
		sql += "WHERE pa.ab_id_a = a.a_id AND pa.ab_id_p <> ? ";
		sql += "AND a.a_id NOT IN (SELECT DISTINCT a.a_id FROM activities a, activity_bind pa WHERE pa.ab_id_a = a.a_id AND pa.ab_id_p = ?)";
		
		List<Actividad> actividades = jdbcTemplate.query(sql, new RowMapperActividad(), proyecto.getIdProyecto(), proyecto.getIdProyecto());
		
		Collections.sort(actividades);
		
		return actividades;
	}

	private void agregarActividad(Actividad actividad, Collection<Proyecto> proyectosRelacionados) {
		
		int idActividad = jdbcTemplate.queryForInt("SELECT max(a_id) FROM activities")+1;
		
		jdbcTemplate.update("INSERT INTO activities (a_id, a_name) VALUES (?,?)", idActividad, actividad.getNombre());

		
		int idActivityBind = jdbcTemplate.queryForInt("SELECT max(ab_id) FROM activity_bind");
		
		for (Proyecto proyecto : proyectosRelacionados) {
			idActivityBind += 1;
			jdbcTemplate.update("INSERT INTO activity_bind (ab_id, ab_id_a, ab_id_p) VALUES (?,?,?)", idActivityBind, idActividad, proyecto.getIdProyecto());			
		}

	}
	
	private void modificarActividad(Actividad actividad, Collection<Proyecto> proyectosRelacionados) {
		int idActivityBind = jdbcTemplate.queryForInt("SELECT max(ab_id) FROM activity_bind");
		
		jdbcTemplate.update("UPDATE activities SET a_name=? WHERE a_id=?", actividad.getNombre(), actividad.getIdActividad());
		
		jdbcTemplate.update("DELETE FROM activity_bind WHERE ab_id_a=?", actividad.getIdActividad());
		
		for (Proyecto proyecto : proyectosRelacionados) {
			idActivityBind += 1;
			jdbcTemplate.update("INSERT INTO activity_bind (ab_id, ab_id_a, ab_id_p) VALUES (?,?,?)", idActivityBind, actividad.getIdActividad(), proyecto.getIdProyecto());
		}
	}
	
	
	public void insertarActividad(Actividad actividad, Collection<Proyecto> proyectosRelacionados) {
		
		if (actividad.getIdActividad() == 0) {
			this.agregarActividad(actividad, proyectosRelacionados);
		}else{
			this.modificarActividad(actividad, proyectosRelacionados);
		}
		
	}
	
	
	private class RowMapperActividad implements RowMapper<Actividad>{
		@Override
		public Actividad mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Actividad(rs.getInt(1), rs.getString(2));
		}
	}
	
	
	
	
}
