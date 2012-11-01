package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

@Repository
public class EntradaDAO extends AbstractDAO {
	
	
	private String sqlEntradas = "SELECT e.al_project_id, e.al_activity_id, e.al_duration, e.al_comment, e.ticket_bz, e.ite_id, e.issue_tracker_externo, e.al_user_id, e.al_date, p.p_name , u.u_name, u.u_password, a.a_name FROM activity_log e, projects p, activities a, users u";

	public void agregarEntrada(Entrada entrada){

		jdbcTemplate.update("INSERT INTO activity_log (al_project_id, al_activity_id, al_duration, al_comment, ticket_bz, issue_tracker_externo, ite_id, al_user_id, al_date) VALUES (?,?,?,?,?,?,?,?,?)", 
				entrada.getProyecto().getIdProyecto(), entrada.getActividad().getIdActividad(), entrada.getDuracion() * 10000, 
				entrada.getNota(), entrada.getTicketBZ(), 
				entrada.getTicketExterno(), entrada.getSistemaExterno(), (entrada.getUsuario().getIdUsuario())
				, entrada.getFecha());
		
		
	}
	
	
	
	public Collection<Entrada> getEntradas(Usuario usuario, Date desde, Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());
		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas +" WHERE e.al_user_id = " + usuario.getIdUsuario() + " AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_user_id = u.u_id AND e.al_date BETWEEN '"+ desdeSQL +"' AND '"+ hastaSQL +"'", new RowMapperEntradas());
		
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Date desde,Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());
		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.al_user_id = u.u_id AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_date BETWEEN '"+ desdeSQL +"' AND '"+ hastaSQL +"'", new RowMapperEntradas());
		 
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());
		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.al_user_id = u.u_id AND e.al_project_id = " + proyecto.getIdProyecto() + " AND e.al_activity_id = a.a_id AND e.al_project_id = p.p_id AND e.al_date BETWEEN '"+ desdeSQL +"' AND '"+ hastaSQL +"'", new RowMapperEntradas());
		
		return entradas;
	}
	
	public int getHorasSemanales(Usuario usuario)
	{
		LocalDate now = new LocalDate();
		LocalDate lu = now.withDayOfWeek(DateTimeConstants.MONDAY);
		LocalDate vie = now.withDayOfWeek(DateTimeConstants.FRIDAY);
		
		Date lunes = lu.toDateTimeAtStartOfDay().toDate();
		Date viernes = vie.toDateTimeAtStartOfDay().toDate();
	
		return jdbcTemplate.queryForInt("SELECT SUM(al_duration)/10000 FROM activity_log WHERE al_user_id=? and al_date BETWEEN ? AND ?",usuario.getIdUsuario(),lunes, viernes);
	}
	
	
	
	private class RowMapperEntradas implements RowMapper<Entrada>{
		@Override
		public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Proyecto proyecto = new Proyecto(rs.getInt(1), rs.getString(10));
			Actividad actividad = new Actividad(rs.getInt(2), rs.getString(13));
			Usuario usuario = new Usuario(rs.getInt(8), rs.getString(11), rs.getString(12));
			//TODO sacarle el ID a Entrada
			return new Entrada(proyecto, actividad, rs.getTime(3).getTime(), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), usuario, rs.getDate(9));
		}
		
	}
}