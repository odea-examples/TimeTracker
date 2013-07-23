package com.odea.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.slickGrid.Data;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

@Repository
public class EntradaDAO extends AbstractDAO {
	
    private static final Logger logger = LoggerFactory.getLogger(EntradaDAO.class);

	
	
	private String sqlEntradas = "SELECT e.al_timestamp, e.al_project_id, e.al_activity_id, e.al_duration, CAST(e.al_comment AS CHAR(10000) CHARACTER SET utf8), e.ticket_bz, e.ite_id, e.issue_tracker_externo, e.al_user_id, e.al_date, p.p_name , u.u_login, u.u_password, a.a_name, a.a_status, p.p_status FROM activity_log e, projects p, activities a, users u";

	public void agregarEntrada(Entrada entrada){

		String sistemaExterno = this.parsearSistemaExterno(entrada.getSistemaExterno()); 
		double duracion = this.parsearDuracion(entrada.getDuracion());
		
		String s1 = entrada.getNota();
		String s2;
		try{
			s2= decodificarNota(s1);			
		}
		catch(Exception e){
			System.out.println("La nota ingresada no pudo ser codificada.");
			e.printStackTrace();
			s2 = entrada.getNota();
		}

		logger.debug("Insert attempt entrada");
		
		jdbcTemplate.update("INSERT INTO activity_log (al_project_id, al_activity_id, al_duration, al_comment, ticket_bz, issue_tracker_externo, ite_id, al_user_id, al_date) VALUES (?,?,?,?,?,?,?,?,?)", 
				new Object[]{
				entrada.getProyecto().getIdProyecto(), entrada.getActividad().getIdActividad(), new java.sql.Time((long) ((this.parsearDuracion(entrada.getDuracion())*3600000))-(3600000*21)), 
				s2, entrada.getTicketBZ(), 
				sistemaExterno, entrada.getTicketExterno(), entrada.getUsuario().getIdUsuario()
				, entrada.getFecha()},
				new int[]{Types.INTEGER, Types.INTEGER, Types.TIME,Types.BLOB,Types.INTEGER,Types.CHAR,Types.VARCHAR,Types.INTEGER ,Types.TIMESTAMP});
		
		logger.debug(entrada.getDuracion() +"Entrada agregada - " + new Date(System.currentTimeMillis()));
		
	}
	
	
	



	






	private double parsearDuracion(String duracion) {
		double resultado = 0;
		
		boolean tieneFormatoHHMM = duracion.indexOf(':') != -1;
		boolean tieneFormatoDecimal = duracion.indexOf(',') != -1;
		
		if (tieneFormatoHHMM) {
			int pos = duracion.indexOf(':');
			String horas = duracion.substring(0, pos);
			String minutos = duracion.substring(pos+1);
			
			double hs = Double.parseDouble(horas);
			double min = Double.parseDouble(minutos) * 100 / 60;
			
			resultado = hs + (min / 100); 
			
		}
		
		
		if (tieneFormatoDecimal) {
			duracion = duracion.replace(',', '.');
			resultado = Double.parseDouble(duracion);
		}
		
		if (!(tieneFormatoDecimal || tieneFormatoHHMM)) {
			resultado = Double.parseDouble(duracion);
		}
		
		
		return resultado;
		
	}
	
	public Double parsearDuracionViene(Time tiempo){
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String t = tiempo.toString();
		Date fechasdf= null;
		try {
			fechasdf= dateFormat.parse(t);
		} catch (ParseException e1) {
			throw new RuntimeException(e1);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechasdf);
		String tiempoString = cal.get(Calendar.HOUR_OF_DAY)+","+((cal.get(Calendar.MINUTE)));
		return this.parsearDuracion(tiempoString);
	}





//
//	public List<Entrada> getEntradas2(Usuario usuario, Timestamp desdeSQL, Timestamp hastaSQL){
//		
//		return jdbcTemplate.query(sqlEntradas +" WHERE e.al_user_id = ? AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_user_id = u.u_id AND e.al_date BETWEEN ? AND ?", new RowMapper<Entrada>() {
//			@Override
//			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Proyecto proyecto = new Proyecto(rs.getInt(2), rs.getString(11));
//				Actividad actividad = new Actividad(rs.getInt(3), rs.getString(14));
//				Usuario usuario = new Usuario(rs.getInt(9), rs.getString(12), rs.getString(13));
//				return new Entrada(rs.getTimestamp(1), proyecto, actividad, String.valueOf(((Double.parseDouble(String.valueOf(rs.getTime(4).getTime()))/3600) - 3000) /1000).substring(0,3)  /* String.valueOf(((Time.valueOf(rs.getTime(4)).getMilliseconds() /3600)-3000))*/, rs.getString(5), rs.getInt(6), rs.getString(7), parsearSistemaExterno(rs.getString(8)), usuario, rs.getDate(10));
//			}}, usuario.getIdUsuario(), desdeSQL, hastaSQL);
//		
//	}
//	
	
	public String parsearSistemaExternoParaGrid(String iniciales) {
		String resultado = "Ninguno";
		if (iniciales != null && iniciales!="") {			
			if (iniciales.equals("SIY")) {
				resultado = "Sistema de Incidencias de YPF";
			} else if (iniciales.equals("SGY") || iniciales.equals("GEM")) {
				resultado = "Sistema Geminis de YPF";
			}
		}
		
		return resultado;
	}
	
	
	public List<Data> getData(Usuario usuario, Timestamp desdeSQL, Timestamp hastaSQL){
		
		List<Entrada> listaEntradas = jdbcTemplate.query(sqlEntradas +" WHERE e.al_user_id = ? AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_user_id = u.u_id AND e.al_date BETWEEN ? AND ?", new RowMapper<Entrada>() {
			@Override
			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				boolean actividadHabilitada = rs.getInt(15) == 1;
				boolean proyectoHabilitado = rs.getInt(16) == 1;
				
				Proyecto proyecto = new Proyecto(rs.getInt(2), rs.getString(11),proyectoHabilitado);
				Actividad actividad = new Actividad(rs.getInt(3), rs.getString(14), actividadHabilitada);
				Usuario usuario = new Usuario(rs.getInt(9), rs.getString(12), rs.getString(13));
				
				//string to double
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				String t = rs.getTime(4).toString();
				//Date fec =  new Date(rs.getTime(4).getTime());
				Date fechasdf= null;
				try {
					fechasdf= dateFormat.parse(t);
				} catch (ParseException e1) {
					throw new RuntimeException(e1);
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechasdf);
				
				
				String sistemaExternoCompleto = EntradaDAO.this.parsearSistemaExternoParaGrid(rs.getString(8));
				
				
				Entrada e = new Entrada(rs.getTimestamp(1), proyecto, actividad, cal.get(Calendar.HOUR_OF_DAY)+","+((cal.get(Calendar.MINUTE))*10/6)  /* String.valueOf(((Time.valueOf(rs.getTime(4)).getMilliseconds() /3600)-3000))*/, rs.getString(5), rs.getInt(6), rs.getString(7), sistemaExternoCompleto, usuario, rs.getDate(10));
				return e;
				
			}}, usuario.getIdUsuario(), desdeSQL, hastaSQL);
		
		
		Collections.sort(listaEntradas);
		
		List<Data> listaData = new ArrayList<Data>();
		Data data;
		
		for (Entrada entrada : listaEntradas) {			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(entrada.getIdEntrada());
			String stringTiempo="";
			String[] tiempos = entrada.getDuracion().split(",");
			stringTiempo+=tiempos[0]+":"+(Integer.parseInt(tiempos[1])*60/100);
			
			//System.out.println(entrada.getDuracion());
			calendar.setTime(entrada.getFecha());
			String stringFecha ="";
			stringFecha+=calendar.get(calendar.DAY_OF_MONTH);
			stringFecha+="/";
			if (calendar.get(calendar.MONTH)<9){
				stringFecha+="0";
			}
			stringFecha+=calendar.get((calendar.MONTH))+1;
			stringFecha+="/";
			stringFecha+=calendar.get(Calendar.YEAR);
			String ticket = Integer.toString(entrada.getTicketBZ());
			if (ticket.equals("0")){
				ticket="";
			}
			data = new Data(entrada.getIdEntrada().toString(),stringTiempo,entrada.getActividad().toString(),entrada.getProyecto().toString(),stringFecha,ticket , entrada.getTicketExterno(), entrada.getSistemaExterno(), entrada.getNota());
			listaData.add(data);
		}
		
		return listaData;
		
	}
	
	public Collection<Entrada> getEntradas2(Date desde,Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());
		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.al_user_id = u.u_id AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_date BETWEEN ? AND ?", new RowMapperEntradas(), desdeSQL, hastaSQL);
		 
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());
		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.al_user_id = u.u_id AND e.al_project_id = ? AND e.al_activity_id = a.a_id AND e.al_project_id = p.p_id AND e.al_date BETWEEN ? AND ?", new RowMapperEntradas(), proyecto.getIdProyecto(), desdeSQL, hastaSQL);
		
		return entradas;
	}
	
	public Collection<Entrada> getEntradas(Date desde, Date hasta){
		Timestamp desdeSQL = new Timestamp(desde.getTime());
		Timestamp hastaSQL = new Timestamp(hasta.getTime());		
		Collection<Entrada> entradas = jdbcTemplate.query(sqlEntradas + " WHERE e.al_user_id = u.u_id  AND e.al_activity_id = a.a_id AND e.al_project_id = p.p_id AND e.al_date BETWEEN ? AND ?", new RowMapperEntradas(), desdeSQL, hastaSQL);
		
		
		return entradas;
	}
	
	public Double getHorasSemanales(Usuario usuario, LocalDate now) 
	{
		LocalDate lu = now.withDayOfWeek(DateTimeConstants.MONDAY);
		LocalDate dom = now.withDayOfWeek(DateTimeConstants.SUNDAY);
		Date lunes = lu.toDateTimeAtStartOfDay().toDate();
		Date domingo = dom.toDateTimeAtStartOfDay().toDate();
		
		return this.getHorasDesdeHasta(usuario, lunes, domingo);
	}
	public Double getHorasDiarias(Usuario usuario, LocalDate hoy) 
	{
		LocalDate maniana = new LocalDate(hoy.toDate().getTime()+1);
		
		Date diaHoy = hoy.toDateTimeAtStartOfDay().toDate();
		Date diaManiana = maniana.toDateTimeAtStartOfDay().toDate();
		
		return this.getHorasDesdeHasta(usuario, diaHoy, diaManiana);
	}
	
	public Double getHorasMensuales(Usuario usuario, LocalDate now) 
	{
		LocalDate primeroDelMes = now.withDayOfMonth(1);
		LocalDate ultimoDelMes = now.plusMonths(1).withDayOfMonth(1).minusDays(1);
		
		Date primero = primeroDelMes.toDateTimeAtStartOfDay().toDate();
		Date ultimo = ultimoDelMes.toDateTimeAtStartOfDay().toDate();
		
		return this.getHorasDesdeHasta(usuario, primero, ultimo);
	}
	
	public Double getHorasDesdeHasta(Usuario usuario, Date desde, Date hasta){
		Double horas = jdbcTemplate.queryForObject("SELECT IFNULL(SUM(TIME_TO_SEC(al_duration))/3600, 0) FROM activity_log WHERE al_user_id=? and al_date BETWEEN ? AND ?",Double.class,usuario.getIdUsuario(),desde, hasta);
		return horas;
	}
	
	public List<HorasCargadasPorDia> horasPorDia(Usuario usuario){
		return jdbcTemplate.query("select al_date as fecha, IFNULL(SUM(TIME_TO_SEC(al_duration))/3600, 0) as duracion from activity_log where al_user_id=? group by fecha order by fecha desc ",
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HorasCargadasPorDia(rs.getTimestamp(1),rs.getDouble(2));
					}
				},usuario.getIdUsuario());
	}
	
	public List<HorasCargadasPorDia> horasPorDiaLimitado(Usuario usuario, Date desde, Date hasta){
		return jdbcTemplate.query("select al_date as fecha , IFNULL(SUM(TIME_TO_SEC(al_duration))/3600, 0) as duracion from  activity_log where al_user_id=? and al_date BETWEEN ? AND ? group by fecha order by fecha asc ",
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new HorasCargadasPorDia(rs.getTimestamp(1),rs.getDouble(2));
					}
				},usuario.getIdUsuario(),desde, hasta);
	}
	
	private String parsearSistemaExterno(String sistemaExterno) {
		String resultado = sistemaExterno;
		
		if (sistemaExterno != null) {
			if (sistemaExterno.equals("Sistema de Incidencias de YPF")) {
				resultado = "SIY";
			}
			if (sistemaExterno.equals("Sistema Geminis de YPF")) {
				resultado = "SGY";
			}
			if (sistemaExterno.equals("Ninguno")) {
				resultado = null;
			}
		}
		
		return resultado;
	}
	
	
	
	public List<Data> getEntradasSemanales(Usuario usuario, LocalDate now){
		LocalDate lu = now.withDayOfWeek(DateTimeConstants.MONDAY);
		LocalDate dom = now.withDayOfWeek(DateTimeConstants.SUNDAY);
		
		Date lunes = lu.toDateTimeAtStartOfDay().toDate();
		Date domingo = dom.toDateTimeAtStartOfDay().toDate();
		
		Timestamp desdeSQL = new Timestamp(lunes.getTime());
		Timestamp hastaSQL = new Timestamp(domingo.getTime());
				
		return this.getData(usuario, desdeSQL, hastaSQL); 
	}
	
	public List<Data> getEntradasDia(Usuario usuario, LocalDate hoy){
		LocalDate maniana = hoy.plusDays(1);
		
		Date diaHoy = hoy.toDateTimeAtStartOfDay().toDate();
		Date diaManiana = maniana.toDateTimeAtStartOfDay().toDate();
		
		Timestamp desdeSQL = new Timestamp(diaHoy.getTime());
		Timestamp hastaSQL = new Timestamp(diaManiana.getTime());
				
		return this.getData(usuario, desdeSQL, desdeSQL); 
	}
	
	public List<Data> getEntradasMensuales(Usuario usuario, LocalDate now){
		LocalDate primeroDelMes = now.withDayOfMonth(1);
		LocalDate ultimoDelMes = now.plusMonths(1).withDayOfMonth(1).minusDays(1);
		
		Date primero = primeroDelMes.toDateTimeAtStartOfDay().toDate();
		Date ultimo = ultimoDelMes.toDateTimeAtStartOfDay().toDate();
		
		Timestamp desdeSQL = new Timestamp(primero.getTime());
		Timestamp hastaSQL = new Timestamp(ultimo.getTime());
				
		return this.getData(usuario, desdeSQL, hastaSQL); 
	}
	
	

		
	public void borrarEntrada(Entrada entrada)
	{
		jdbcTemplate.update("DELETE FROM activity_log WHERE al_timestamp=?", entrada.getIdEntrada());
	}
	public void borrarEntrada(Timestamp entradaID)
	{
		//System.out.println(entradaID);
		jdbcTemplate.update("DELETE FROM activity_log WHERE al_timestamp=?", entradaID);
	}
		
		
		public void modificarEntrada(Entrada entrada) {
			String sistemaExterno = null;			
			if (entrada.getSistemaExterno() != null && !entrada.getSistemaExterno().equals("Ninguno") ){
				sistemaExterno = parsearSistemaExterno(entrada.getSistemaExterno());
			}
			String s1= entrada.getNota();
			String s2;
			try {
				s2= decodificarNota(s1);
			} catch (Exception e) {
				System.out.println("La nota ingresada no pudo ser codificada.");
				e.printStackTrace();
				s2 = entrada.getNota();
			}
			java.sql.Date fechaSQL = new java.sql.Date(entrada.getFecha().getTime());
			jdbcTemplate.update("UPDATE activity_log SET al_date=?, al_duration=?, al_project_id=?, al_activity_id=?, al_comment=?, ticket_bz=?, issue_tracker_externo=?, ite_id=? WHERE al_timestamp=?", 
					new Object[]{
					fechaSQL, new java.sql.Time((long) ((this.parsearDuracion(entrada.getDuracion())*3600000))-(3600000*21)), entrada.getProyecto().getIdProyecto(), entrada.getActividad().getIdActividad(), s2, entrada.getTicketBZ(), sistemaExterno, entrada.getTicketExterno(), entrada.getIdEntrada()},
					new int[]{Types.DATE, Types.TIME, Types.INTEGER, Types.INTEGER, Types.BLOB, Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.TIMESTAMP});
		}

		public Entrada buscarEntrada(long id) {
			Timestamp fecha = new Timestamp(id);
			return jdbcTemplate.queryForObject(sqlEntradas+" WHERE e.al_user_id = u.u_id AND e.al_project_id = p.p_id AND e.al_activity_id = a.a_id AND e.al_timestamp=?", new RowMapperEntradas(), fecha);
		}
		
		
		public List<String> getSistemasExternos() {
			ArrayList<String> sistemasExternos = new ArrayList<String>();
			sistemasExternos.add("Sistema de Incidencias de YPF");
			sistemasExternos.add("Sistema Geminis de YPF");
			sistemasExternos.add("Ninguno");
			
			return sistemasExternos;
		}
		
		
		public boolean puedeEntrar(String duracion, Date fecha, Usuario usuario, String duracionVieja) {
			//la duracion vieja recibida sera la cantidad de horas en milisegundos.
			LocalDate diaBuscado = new LocalDate(fecha.getTime());
			// TODO Auto-generated method stub
			Boolean resultado;
			double duracionViejaFinal = (this.parsearDuracion(duracionVieja)-10800000)/3600000;
			resultado= getHorasDiarias(usuario, diaBuscado)+this.parsearDuracion(duracion)-duracionViejaFinal<24.1;
			return resultado;
		}
		
		public List<DatePickerDTO> getHorasUsuarios(List<Usuario> usuarios) {
			
			List<DatePickerDTO> listaDPdto = new ArrayList<DatePickerDTO>();
			for (Usuario usuario : usuarios) {
				List<HorasCargadasPorDia> horasDia = EntradaDAO.this.horasPorDia(usuario);
				DatePickerDTO dto= new DatePickerDTO(usuario.getNombreLogin(), 8, horasDia);
				listaDPdto.add(dto);
			}
			return listaDPdto;
		}

		public Map<Date, Double> getHorasDia(Usuario usuario,Date desde, Date hasta) {
			
			List<HorasCargadasPorDia> horasPorDia = this.horasPorDiaLimitado(usuario, desde, hasta);
			Map<Date,Double> mapa = new HashMap<Date, Double>();
			for (HorasCargadasPorDia horasCargadas : horasPorDia) {
				mapa.put(horasCargadas.getDiaDate(), horasCargadas.getHorasCargadas());
			}
			return mapa;
		}
		

		
		private class RowMapperEntradas implements RowMapper<Entrada>{
			@Override
			public Entrada mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				boolean actividadHabilitada = rs.getInt(15) == 1;
				boolean proyectoHabilitado = rs.getInt(16) == 1;
				
				Proyecto proyecto = new Proyecto(rs.getInt(2), rs.getString(11),proyectoHabilitado);
				Actividad actividad = new Actividad(rs.getInt(3), rs.getString(14), actividadHabilitada);
				Usuario usuario = new Usuario(rs.getInt(9), rs.getString(12), rs.getString(13));
				return new Entrada(rs.getTimestamp(1), proyecto, actividad, String.valueOf(rs.getTime(4).getTime()), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), usuario, rs.getDate(10));
			}
			
		}
		
		public String blobToString(Blob blob){
			
			byte[] b;
			try {
				
				b = blob.getBytes(1, (int) blob.length());
				ByteArrayInputStream inputStream = new ByteArrayInputStream(b);  
	            Reader r = new InputStreamReader(inputStream);
	            
	            StringWriter sw = new StringWriter();     
	            char[] buffer = new char[20000];     
	            
	            for (int n; (n = r.read(buffer)) != -1; ){
	            	sw.write(buffer, 0, n);     	            	
	            }
	            
	            String str = sw.toString();
	            
	            return str;
	            
			} catch (Exception e) {
				throw new RuntimeException(e);
			}  
            
		}
		private String decodificarNota(String s1) throws UnsupportedEncodingException {
			byte[] bytes;
			bytes = s1.getBytes("UTF-8");
			String s2= new String(bytes,"ISO-8859-1");
			return s2;
		}
		
	}

	
	
