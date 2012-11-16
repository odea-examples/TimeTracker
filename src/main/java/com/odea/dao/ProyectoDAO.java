package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Actividad;
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
	
	public void cambiarNombreProyecto(Proyecto proyecto){
		jdbcTemplate.update("UPDATE projects SET p_name=? WHERE p_id=?",proyecto.getNombre(),proyecto.getIdProyecto());
		
	}
	
	public void borrarProyecto(Proyecto proyecto){
		jdbcTemplate.update("DELETE FROM projects WHERE p_id=?",proyecto.getIdProyecto());
		jdbcTemplate.update("DELETE FROM activity_bind WHERE ab_id_p=?",proyecto.getIdProyecto());
	}
	
	
	
	public void actualizarRelaciones(int idProyecto, List<Actividad> borrar, List<Actividad> añadir){
		
		for (Actividad actividad : añadir) {
			
			int max=jdbcTemplate.queryForInt("SELECT max(ab_id) FROM activity_bind")+1;
			
			String sql= "INSERT INTO activity_bind (ab_id,ab_id_a,ab_id_p)";
				sql+="SELECT * FROM (SELECT ?,?,?) AS tmp";
				sql+="WHERE NOT EXISTS (";
				sql+="SELECT * FROM activity_bind WHERE (ab_id_a=? and ab_id_p=? )";
				sql+=") LIMIT 1;";
		jdbcTemplate.update(sql,max,actividad.getIdActividad(),idProyecto,actividad.getIdActividad(),idProyecto);
		}
		
		for (Actividad actividad2 : borrar) {
			jdbcTemplate.update("DELETE FROM activity_bind WHERE ab_id_p=? and ab_id_a=?",idProyecto, actividad2.getIdActividad());
		}
		
		
		
	}
	
	public void agregarProyecto(Proyecto proyecto, Collection<Actividad> actividadesRelacionadas) {
		
		int idProyecto = jdbcTemplate.queryForInt("SELECT max(p_id) FROM projects")+1;
		
		jdbcTemplate.update("INSERT INTO projects (p_id, p_name) VALUES (?,?)", idProyecto, proyecto.getNombre());
		
		
		int idActivityBind = jdbcTemplate.queryForInt("SELECT max(ab_id) FROM activity_bind")+1;
		
		for (Actividad actividad : actividadesRelacionadas) {
			jdbcTemplate.update("INSERT INTO activity_bind (ab_id, ab_id_a, ab_id_p) VALUES (?,?,?)", idActivityBind, actividad.getIdActividad(), proyecto.getIdProyecto());			
		}
	}
	
}
