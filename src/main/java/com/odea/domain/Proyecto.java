package com.odea.domain;


public class Proyecto {
	
	private String nombre;
	private int idProyecto;
	
	
	public Proyecto(int idProyecto, String nombre) {
		super();
		this.nombre = nombre;
		this.idProyecto = idProyecto;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdProyecto() {
		return idProyecto;
	}
	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
