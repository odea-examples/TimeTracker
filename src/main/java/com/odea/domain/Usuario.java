package com.odea.domain;

import java.io.Serializable;

public class Usuario implements Serializable {
	
	private int idUsuario;
	private String nombre;
	private String password;
	
	
	public Usuario(int id, String nombre, String password) {
		this.idUsuario = id;
		this.nombre = nombre;
		this.password = password;
	}
	
	
	
	public Usuario(int id){
		this.idUsuario = id;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
}
