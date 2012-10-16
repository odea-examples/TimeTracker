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
	
	private String sqlEntradas = "SELECT e.id_entrada, e.id_proyecto, e.id_actividad, e.duracion, e.nota, e.ticket_bz, e.ticket_ext, e.sistema_ext, e.id_usuario, e.fecha FROM entrada e";

	public void agregarEntrada(Entrada entrada){
		jdbcTemplate.update("INSERT INTO entrada (id_proyecto, id_actividad, duracion, nota, id_ticket_bz, ticket_ext, sistema_ext, id_usuario) VALUES (?,?,?,?,?,?,?,?)", 
				entrada.getProyecto().getIdProyecto(), entrada.getActividad().getIdActividad(), entrada.getDuracion(), 
				entrada.getNota(), entrada.getTicketBugZilla(), entrada.getTicketExterno(), entrada.getSistemaExterno(), entrada.getUsuario().getIdUsuario());
	}
	
	
	public Collection<Entrada> getEntradas(Usuario usuario, Date desde, Date hasta){
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas+", usuario u WHERE e.id_usuario = u.id_usuario" , new RowMapperEntradas());
		
		return entradas;
	}
	
	
	public Collection<Entrada> getEntradas(Date desde,Date hasta){
		 Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas, new RowMapperEntradas());
		 
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas+", proyectos p WHERE e.id_proyecto = u.id_proyecto" , new RowMapperEntradas());
		
		return entradas;
	}
	
	
	
	private class RowMapperEntradas implements RowMapper<Entrada>{
		@Override
		public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Proyecto proyecto = new Proyecto(rs.getInt(1), "Nombre");
			Actividad actividad = new Actividad(rs.getInt(2), "Nombre");
			Usuario usuario = new Usuario(rs.getInt(8), "Nombre", "Apellido", "password");
			
			return new Entrada(rs.getLong(0), proyecto, actividad, rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), usuario, rs.getDate(9));
		}
		
	}
}