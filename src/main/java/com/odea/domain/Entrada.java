package com.odea.domain;

import java.sql.Date;

import com.odea.dao.EntradaDAO;

public class Entrada {
	
private int identrada;	
private int idproyecto;
private int idactividad;
private double duracion;
private String nota;
private int idticketbz;
private int idticketext;
private int idusuario;
private String fecha;


public Entrada(){}

public Entrada(int identrada, Proyecto proyecto, Actividad actividad,
		double duracion, String nota, TicketBZ ticketbz,TicketExterno ticketext ,Usuario usuario,String fecha) {
	this.identrada = identrada;
	idproyecto = proyecto.getId();
	idactividad = actividad.getId();
	this.duracion = duracion;
	this.nota = nota;
	idticketbz = ticketbz.getTicketID();
	idusuario = usuario.getId();
	idticketext = ticketext.getTicketid();
	this.fecha = fecha;
}

public int getIdentrada() {
	return identrada;
}

public void setIdentrada(int identrada) {
	this.identrada = identrada;
}

public int getIdproyecto() {
	return idproyecto;
}

public void setIdproyecto(int idproyecto) {
	this.idproyecto = idproyecto;
}

public int getIdactividad() {
	return idactividad;
}

public void setIdactividad(int idactividad) {
	this.idactividad = idactividad;
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

public int getIdticketbz() {
	return idticketbz;
}

public void setIdticketbz(int idticketbz) {
	this.idticketbz = idticketbz;
}

public int getIdticketext() {
	return idticketext;
}

public void setIdticketext(int idticketext) {
	this.idticketext = idticketext;
}

public int getIdusuario() {
	return idusuario;
}

public void setIdusuario(int idusuario) {
	this.idusuario = idusuario;
}

public String getFecha() {
	return fecha;
}

public void setFecha(String fecha) {
	this.fecha = fecha;
}


}
