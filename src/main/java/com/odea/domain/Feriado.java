package com.odea.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Feriado implements Serializable {
	
	public Date fecha;
	public String descripcion;
	public String fechaFormateada;
	
	public Feriado() {
		
	}

	public Feriado(Date date, String string) {
		this.fecha = date;
		this.descripcion = string;
        SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaFormateada = DATE_FMT.format(this.fecha);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
