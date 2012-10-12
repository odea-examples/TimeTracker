package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.odea.domain.Entrada;
import com.odea.domain.Usuario;

@Service
public class EntradaDAO extends AbstractDAO {
	
	public Entrada getEntrada(int id)
	{
		Entrada entrada= jdbcTemplate.queryForObject("SELECT * FROM entrada WHERE id_entrada='" + id + "'", new RowMapper<Entrada>(){
		@Override
		public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Entrada(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
		}});
	
		return entrada;
	}
	
		public void agregarEntrada(Entrada entrada){
			
		jdbcTemplate.update("INSERT INTO entrada VALUES (?,?,?,?,?,?,?)", new Object [] {entrada.getId_entrada(), entrada.getId_proyecto(), entrada.getId_actividad(), entrada.getDuracion(), entrada.getNota(), entrada.getId_ticket(), entrada.getId_usuario()});
	}


}