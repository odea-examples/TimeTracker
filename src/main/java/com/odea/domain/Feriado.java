package com.odea.domain;

import java.io.Serializable;
import java.util.Date;


public class Feriado implements Serializable {
	
	public Date fecha;
	public String descripcion;
	
	

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
