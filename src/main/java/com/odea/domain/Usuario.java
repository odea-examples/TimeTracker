package com.odea.domain;

import java.io.Serializable;

public class Usuario implements Serializable, Comparable<Usuario> {
	
	private int idUsuario;
	private String nombreLogin;
	private String password;
	private String nombre;
	private Boolean esCoManager;
	private Usuario perfil;
	private String grupo;
	
	public Usuario(int id, String nombreLogin, String password, String nombre, Boolean coManager, Usuario perfil) {
		this.idUsuario = id;
		this.nombreLogin = nombreLogin;
		this.password = password;
		this.nombre = nombre;
		this.esCoManager = coManager;
		this.perfil = perfil;
	}
	
	public Usuario(int id, String nombreLogin, String password,String nombre,Boolean coManager) {
		this.idUsuario = id;
		this.nombreLogin = nombreLogin;
		this.password = password;
		this.nombre = nombre;
		this.esCoManager = coManager;
	}

	public Usuario(int id, String nombre, String password) {
		this.idUsuario = id;
		this.nombreLogin = nombre;
		this.password = password;
		this.nombre="default";
		this.esCoManager = false;
	}
	
	public Boolean getEsCoManager() {
		return esCoManager;
	}
	public void setEsCoManager(Boolean esCoManager) {
		this.esCoManager = esCoManager;
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

	public String getNombreLogin() {
		return nombreLogin;
	}
	public void setNombreLogin(String nombre) {
		this.nombreLogin = nombre;
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
	
	public Usuario getPerfil() {
		return perfil;
	}

	public void setPerfil(Usuario perfil) {
		this.perfil = perfil;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return this.nombreLogin;
	}

	
	
	@Override
	public int compareTo(Usuario otroUsuario) {
		return this.getNombreLogin().compareTo(otroUsuario.getNombreLogin());
	}

	
}
