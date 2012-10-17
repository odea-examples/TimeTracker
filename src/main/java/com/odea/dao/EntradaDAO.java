package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

@Service
public class EntradaDAO extends AbstractDAO {
	
	private String sqlEntradas = "SELECT e.id_entrada, e.id_proyecto, e.id_actividad, e.duracion, e.nota, e.id_ticket_bz, e.ticket_ext, e.sistema_ext, e.id_usuario, e.fecha, p.nombre, u.nombre, u.apellido, u.password, a.nombre FROM entrada e, proyecto p, actividad a, usuarios u";

	public void agregarEntrada(Entrada entrada){
		jdbcTemplate.update("INSERT INTO entrada (id_proyecto, id_actividad, duracion, nota, id_ticket_bz, ticket_ext, sistema_ext, id_usuario, fecha) VALUES (?,?,?,?,?,?,?,?,?)", 
				entrada.getProyecto().getIdProyecto(), entrada.getActividad().getIdActividad(), entrada.getDuracion(), 
				entrada.getNota(), entrada.getTicketBugZilla(), entrada.getTicketExterno(), entrada.getSistemaExterno(), entrada.getUsuario().getIdUsuario(), entrada.getFecha());
	}
	
	
	public Collection<Entrada> getEntradas(Usuario usuario, Date desde, Date hasta){
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas +" WHERE e.id_usuario = " + usuario.getIdUsuario() + " AND e.id_proyecto = p.id_proyecto AND e.id_actividad = a.id_actividad" , new RowMapperEntradas());
		
		return entradas;
	}
	
	
	public Collection<Entrada> getEntradas(Date desde,Date hasta){
		 Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.id_usuario = u.id_usuario AND e.id_proyecto = p.id_proyecto AND e.id_actividad = a.id_actividad", new RowMapperEntradas());
		 
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.id_usuario = u.id_usuario AND e.id_proyecto = " + proyecto.getIdProyecto() + " AND e.id_actividad = a.id_actividad", new RowMapperEntradas());
		
		return entradas;
	}
	
	
	
	private class RowMapperEntradas implements RowMapper<Entrada>{
		@Override
		public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Proyecto proyecto = new Proyecto(rs.getInt(1), rs.getString(11));
			Actividad actividad = new Actividad(rs.getInt(2), rs.getString(15));
			Usuario usuario = new Usuario(rs.getInt(9), rs.getString(12), rs.getString(13), rs.getString(14));
			
			return new Entrada(rs.getLong(1), proyecto, actividad, rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), usuario, rs.getDate(10));
		}
		
	}
}