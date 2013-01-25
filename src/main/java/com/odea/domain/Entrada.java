package com.odea.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Entrada implements Serializable,Comparable<Entrada> {

	private Timestamp idEntrada;
	private Proyecto proyecto;
	private Actividad actividad;
	private String duracion;
	private String nota;
	private int ticketBZ;
	private String ticketExterno;
	private String sistemaExterno;
	private Usuario usuario;
	private Date fecha;

	public Entrada() {
	}

	public Entrada(Timestamp idEntrada, Proyecto proyecto, Actividad actividad,
			String duracion, String nota, int ticketBugZilla,
			String ticketExterno, String sistemaExterno, Usuario usuario, Date fecha) {
		this.idEntrada = idEntrada;
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
	


	public Timestamp getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Timestamp idEntrada) {
		this.idEntrada = idEntrada;
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

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
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

	@Override
	public String toString() {
		return "Entrada [idEntrada=" + idEntrada + ", proyecto=" + proyecto
				+ ", actividad=" + actividad + ", duracion=" + duracion
				+ ", nota=" + nota + ", ticketBZ=" + ticketBZ
				+ ", ticketExterno=" + ticketExterno + ", sistemaExterno="
				+ sistemaExterno + ", usuario=" + usuario + ", fecha=" + fecha
				+ "]";
	}

	@Override
	public int compareTo(Entrada otraEntrada) {
		
		int resFecha = this.getFecha().compareTo(otraEntrada.getFecha());
		int resProyecto = this.getProyecto().compareTo(otraEntrada.getProyecto());
		int resActividad = this.getActividad().compareTo(otraEntrada.getActividad());
		int resTicketBZ = this.getTicketBZ() - otraEntrada.getTicketBZ();
		
		if (resFecha != 0) {
			return resFecha;
			
		} else if (resProyecto != 0) {
			return resProyecto;
			
		} else if (resActividad != 0) {
			return resActividad;
			
		} else if (resTicketBZ != 0) {
			return resTicketBZ;
		} 		


		if (this.getSistemaExterno() != null && otraEntrada.getSistemaExterno() != null) {
			int resSistemaExterno = this.getSistemaExterno().compareTo(otraEntrada.getSistemaExterno());
			
			if (resSistemaExterno != 0) {
				return resSistemaExterno;
			}
			
		}
		
		if (this.getTicketExterno() != null && otraEntrada.getTicketExterno() != null) {
			int resTicketExterno = this.getTicketExterno().compareTo(otraEntrada.getTicketExterno());
			
			if (resTicketExterno != 0) {
				return resTicketExterno;
			}
			
		}
		
		
		return 0;
			
	}
	
}
