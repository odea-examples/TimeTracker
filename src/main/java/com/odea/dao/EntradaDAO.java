package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Entrada;
import com.odea.util.UFiltro;

@Service
public class EntradaDAO extends AbstractDAO {
	
	public Collection<Entrada> buscarEntradas(int idEntrada, int idProyecto, int idActividad, int idTicket, int idUsuario)
	{
		/*
		 * Si el valor de un id es igual a 0, no tiene criterio de busqueda, encuentra todos.
		 * De esta manera pueden buscarse todas las combinaciones posibles.
	     */
		
		
		Collection<Entrada> todasLasEntradas = new ArrayList<Entrada>();
		Collection<Entrada> entradas = new Vector<Entrada>();
		
		todasLasEntradas = this.obtenerTodasLasEntradas();
		
		entradas = UFiltro.filtrarEntradas(todasLasEntradas, idEntrada, idProyecto, idActividad, idTicket, idUsuario);
		
		return entradas;
	}
	

	
	public void agregarEntrada(Entrada entrada){
		jdbcTemplate.update("INSERT INTO entrada (id_proyecto, id_actividad, duracion, nota, id_ticket, id_usuario) VALUES (?,?,?,?,?,?)", 
				new Object [] {entrada.getId_proyecto(), entrada.getId_actividad(), entrada.getDuracion(), entrada.getNota(), entrada.getId_ticket(), entrada.getId_usuario()});
	}
	
	public Collection<Entrada> obtenerTodasLasEntradas(){
		Collection<Entrada> entradas = new ArrayList<Entrada>();
		
		entradas = jdbcTemplate.query("SELECT id_entrada, id_proyecto, id_actividad, duracion, nota, id_ticket, id_usuario FROM entrada", new RowMapper<Entrada>(){
			@Override
			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Entrada(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
			}});
		
		
		return entradas;
	}

	
	public void borrarTodosLosRegistros()
	{
		jdbcTemplate.update("DELETE FROM entrada");
	}
}