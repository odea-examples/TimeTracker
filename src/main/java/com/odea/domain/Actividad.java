package com.odea.domain;

public class Actividad {
	private int idActividad;
	private String nombre;
	
	public Actividad(int idActividad, String nombre) {
		super();
		this.idActividad = idActividad;
		this.nombre = nombre;
	}
	
	public int getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
