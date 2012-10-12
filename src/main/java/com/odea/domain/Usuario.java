package com.odea.domain;

public class Usuario {
	private int id;
	private String nombre;
	private String apellido;
	private String password;
	
	
	public Usuario(){}
	
	public Usuario(int id, String nombre, String apellido, String password) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
