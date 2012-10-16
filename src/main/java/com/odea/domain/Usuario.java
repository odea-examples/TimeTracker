package com.odea.domain;

public class Usuario {
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String password;
	
	
	public Usuario(int id, String nombre, String apellido, String password) {
		this.idUsuario = id;
		this.nombre = nombre;
		this.apellido = apellido;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
}
