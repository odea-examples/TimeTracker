package com.odea.domain;

import java.io.Serializable;
import java.util.Date;

public class Entrada implements Serializable {

	private Proyecto proyecto;
	private Actividad actividad;
	private double duracion;
	private String nota;
	private int ticketBZ;
	private String ticketExterno;
	private String sistemaExterno;
	private Usuario usuario;
	private Date fecha;

	public Entrada() {
	}

	public Entrada(Proyecto proyecto, Actividad actividad, double duracion,
			String nota, int ticketBugZilla, String ticketExterno,
			String sistemaExterno, Usuario usuario, Date fecha) {
		this.proyecto = proyecto;
		this.actividad = actividad;
		this.duracion = duracion;
		this.nota = nota;
		this.ticketBZ = ticketBugZilla;
		this.ticketExterno = ticketExterno;
		this.sistemaExterno = sistemaExterno;
		this.usuario = usuario;
		this.fecha = fecha;
	}
	


	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
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

	public int getTicketBZ() {
		return ticketBZ;
	}

	public void setTicketBZ(int ticketBZ) {
		this.ticketBZ = ticketBZ;
	}

	public String getTicketExterno() {
		return ticketExterno;
	}

	public void setTicketExterno(String ticketExterno) {
		this.ticketExterno = ticketExterno;
	}

	public String getSistemaExterno() {
		return sistemaExterno;
	}

	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
