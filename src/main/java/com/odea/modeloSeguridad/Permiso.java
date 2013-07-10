package com.odea.modeloSeguridad;

import java.io.Serializable;

public class Permiso implements Serializable {
	
	private int ID;
	private Funcionalidad funcionalidad;
	private String estado;
	
	@Override
	public String toString() {
		return "Permiso - ID: " + this.ID + " - Funcionalidad: " + this.funcionalidad.getID() + " - Estado: " + this.estado;
	}
	
	
	
	//Getters & Setters
	
	
	public Funcionalidad getFuncionalidad() {
		return funcionalidad;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setFuncionalidad(Funcionalidad funcionalidad) {
		this.funcionalidad = funcionalidad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
