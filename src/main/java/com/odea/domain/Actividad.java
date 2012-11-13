package com.odea.domain;

import java.io.Serializable;

public class Actividad implements Serializable, Comparable<Actividad> {
	
	
	private int idActividad;
	private String nombre;
	
	public Actividad() {
		
	}
	
	public Actividad(int idActividad, String nombre) {
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
		String ret = null;
		
		if (nombre != null) {
			ret = nombre.replaceAll("Ã³","ó").replaceAll("Ã©","é").replaceAll("Ã±","ñ").replaceAll("Ã¡","á").replaceAll("Ã­","í") ;			
		}
		
        return ret;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
        return this.getNombre();
    }




	@Override
	public int compareTo(Actividad otraActividad) {
		return this.getNombre().compareTo(otraActividad.getNombre());
	}
	
	
	
}
