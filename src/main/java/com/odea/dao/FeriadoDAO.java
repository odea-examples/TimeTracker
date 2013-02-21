package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.domain.Feriado;

@Repository
public class FeriadoDAO extends AbstractDAO {
	
	public List<Feriado> getFeriados(){
//		LocalDate primeroDelMes = now.withDayOfMonth(1);
//		LocalDate ultimoDelMes = now.plusMonths(1).withDayOfMonth(1).minusDays(1);
//		
//		Date primero = primeroDelMes.toDateTimeAtStartOfDay().toDate();
//		Date ultimo = ultimoDelMes.toDateTimeAtStartOfDay().toDate();
//		
//		Timestamp desdeSQL = new Timestamp(primero.getTime());
//		Timestamp hastaSQL = new Timestamp(ultimo.getTime());
		
		
		return jdbcTemplate.query("SELECT fecha, descripcion FROM feriados", new RowMapperFeriados());
		
	}
	
	public Feriado getFeriadoHoy(LocalDate now){
		Date hoy = now.toDateTimeAtStartOfDay().toDate();
		Feriado feriado;
		
		try {
			feriado	= jdbcTemplate.queryForObject("SELECT fecha, descripcion FROM feriados WHERE fecha=?", new RowMapperFeriados(), hoy);
		} catch (EmptyResultDataAccessException ex) {
			feriado = new Feriado();
		}
		
		return feriado;
	}
	
	
	public void insertarFeriado(Feriado feriado){
		jdbcTemplate.update("REPLACE INTO feriados VALUES(?,?)", feriado.getFecha(), feriado.getDescripcion());
	}
	
	
	public void borrarFeriado(Feriado feriado){
		jdbcTemplate.update("DELETE FROM feriados WHERE fecha = ?", feriado.getFecha());
	}
	
	public List<HorasCargadasPorDia> getFeriadosData() {
		
		return jdbcTemplate.query("SELECT fecha FROM feriados",
				new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new HorasCargadasPorDia(rs.getDate(1), 0);
					}
				});
		
	}
	
	
	private class RowMapperFeriados implements RowMapper<Feriado> {
		@Override
		public Feriado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Feriado(rs.getDate(1),rs.getString(2));
		}
		
	}
	
	
}
