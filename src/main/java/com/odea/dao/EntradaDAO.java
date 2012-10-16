package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Entrada;
import com.odea.util.UFiltro;

@Service
public class EntradaDAO extends AbstractDAO {
	
	public Collection<Entrada> buscarEntradas(int idEntrada, int idProyecto, int idActividad, int idTicket, int idUsuario)
	{	
		Collection<Entrada> todasLasEntradas = new ArrayList<Entrada>();
		Collection<Entrada> entradas = new Vector<Entrada>();
		
		todasLasEntradas = this.obtenerTodasLasEntradas();
		
		entradas = UFiltro.filtrarEntradas(todasLasEntradas, idEntrada, idProyecto, idActividad, idTicket, idUsuario);
		
		return entradas;
	}
	

	public void agregarEntrada(Entrada entrada){
		jdbcTemplate.update("INSERT INTO entrada (id_proyecto, id_actividad, duracion, nota, id_ticket, id_usuario) VALUES (?,?,?,?,?,?)", 
				new Object [] {entrada.getIdproyecto(), entrada.getIdactividad(), entrada.getDuracion(), entrada.getNota(), entrada.getIdticketbz(), entrada.getIdusuario()});
	}
	
	
	public Collection<Entrada> obtenerTodasLasEntradas(){
		Collection<Entrada> entradas = new ArrayList<Entrada>();
/*		
		entradas = jdbcTemplate.query("SELECT id_entrada, id_proyecto, id_actividad, duracion, nota, id_ticket, id_usuario FROM entrada", new RowMapper<Entrada>(){
			@Override
			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Proyecto proyecto = new Proyecto(rs.getInt(2));
				Actividad actividad = new Actividad(rs.getInt(3));
				TicketBZ ticketBZ = new TicketBZ(rs.getInt(6));
				Usuario usuario = new Usuario(rs.getInt(7));
				
			return new Entrada(rs.getInt(1), proyecto, actividad, rs.getDouble(4), rs.getString(5), ticketBZ, usuario);
			}});
		
		
		return entradas;
		*/
		return null;
	}
	
	//TODO que quede bien y traiga todo bien empaquetado
	public Collection<Entrada> obtenerTodasLasEntradas(Date desde,Date hasta){
		 Collection<Entrada> entradas = jdbcTemplate.query("SELECT id_entrada, id_proyecto, id_actividad, duracion, nota, id_ticket, id_usuario FROM entrada", new RowMapper<Entrada>(){
			@Override
			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
				Proyecto proyecto = new Proyecto(rs.getInt(2));
				Actividad actividad = new Actividad(rs.getInt(3));
				TicketBZ ticketBZ = new TicketBZ(rs.getInt(6));
				Usuario usuario = new Usuario(rs.getInt(7));
				
			return new Entrada(rs.getInt(1), proyecto, actividad, rs.getDouble(4), rs.getString(5), ticketBZ, usuario);
			}});
		
		
		return entradas;
		
		return null;
	}
	
	
	public void borrarTodosLosRegistros()
	{
		jdbcTemplate.update("DELETE FROM entrada;");
	}
}