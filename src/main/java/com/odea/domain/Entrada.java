package com.odea.domain;

public class Entrada {
private int id_entrada;
private int id_proyecto;
private int id_actividad;
private double duracion;
private String nota;
private int id_ticket;
private int id_usuario;

public Entrada(){}

public Entrada(int id_entrada, int id_proyecto, int id_actividad,
		double duracion, String nota, int id_ticket, int id_usuario) {
	this.id_entrada = id_entrada;
	this.id_proyecto = id_proyecto;
	this.id_actividad = id_actividad;
	this.duracion = duracion;
	this.nota = nota;
	this.id_ticket = id_ticket;
	this.id_usuario = id_usuario;
}

public int getId_entrada() {
	return id_entrada;
}

public void setId_entrada(int id_entrada) {
	this.id_entrada = id_entrada;
}

public int getId_proyecto() {
	return id_proyecto;
}

public void setId_proyecto(int id_proyecto) {
	this.id_proyecto = id_proyecto;
}

public int getId_actividad() {
	return id_actividad;
}

public void setId_actividad(int id_actividad) {
	this.id_actividad = id_actividad;
}

public double getDuracion() {
	return duracion;
}

public void setDuracion(double duracion) {
	this.duracion = duracion;
}

public String getNota() {
	return nota;
}

public void setNota(String nota) {
	this.nota = nota;
}

public int getId_ticket() {
	return id_ticket;
}

public void setId_ticket(int id_ticket) {
	this.id_ticket = id_ticket;
}

public int getId_usuario() {
	return id_usuario;
}

public void setId_usuario(int id_usuario) {
	this.id_usuario = id_usuario;
}


}
