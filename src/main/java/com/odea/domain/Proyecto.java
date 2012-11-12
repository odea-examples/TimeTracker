package com.odea.domain;

import java.io.Serializable;


public class Proyecto implements Serializable, Comparable<Proyecto> {
	
	private String nombre;
	private int idProyecto;
	
	
	public Proyecto(int idProyecto, String nombre) {
		this.nombre = nombre;
		this.idProyecto = idProyecto;
	}
	
	
	public String getNombre() {
        String ret = nombre.replaceAll("Ã³","ó").replaceAll("Ã©","é").replaceAll("Ã±","ñ").replaceAll("Ã¡","á").replaceAll("Ã­","í") ;
        return ret;
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
        return this.getNombre();
    }


	@Override
	public int compareTo(Proyecto otroProyecto) {
		//toLowerCase es porque pone a los que tienen mayuscula antes que los que tienen minuscula,
		//y quedan desorganizados
		return this.getNombre().toLowerCase().compareTo(otroProyecto.getNombre().toLowerCase());
	}
	
	
	
}
