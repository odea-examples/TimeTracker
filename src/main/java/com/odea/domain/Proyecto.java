package com.odea.domain;

import java.awt.List;

public class Proyecto {
	
	private String nombre;
	private int id;
	private List tareasPermitidas;

	public Proyecto(int idProyecto) {
		id = idProyecto;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public List getTareasPermitidas() {
		return tareasPermitidas;
	}

	public void setTareasPermitidas(List tareaspermitidas) {
		this.tareasPermitidas = tareaspermitidas;
	}

}
