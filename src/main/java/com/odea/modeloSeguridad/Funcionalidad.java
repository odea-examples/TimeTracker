package com.odea.modeloSeguridad;

import java.io.Serializable;

public class Funcionalidad implements Serializable {
	
	private int ID;
	private String grupo;
	private String concepto;
	private String estado;
	
	
	@Override
	public String toString() {
		return "Funcionalidad - Grupo:" + this.grupo + " - Concepto: " + this.concepto + " - Estado: " + this.estado;
	}
	
	
	
	//Getters & Setters
	
	
	public String getGrupo() {
		return grupo;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}



	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
