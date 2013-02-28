package com.odea.domain;

import java.io.Serializable;


public class Proyecto implements Serializable, Comparable<Proyecto> {
	
	private String nombre;
	private int idProyecto;
	private boolean habilitado;
	
	public Proyecto() {
		
	}
	
	public Proyecto(int idProyecto, String nombre, boolean habilitado) {
		this.nombre = nombre;
		this.idProyecto = idProyecto;
		this.habilitado = habilitado;
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
	public boolean isHabilitado() {
		return habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
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
