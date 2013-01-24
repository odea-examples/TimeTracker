package com.odea.components.slickGrid;


public class Data {
	String id;
	String duration;
	String actividad;
	String proyecto;
	String fecha;
	String ticket;
	String ticketExt;
	String sistExt;
	String descripcion;
	
	public Data(String id, String duration, String actividad,
			String proyecto, String fecha, String ticket, String ticketExt,
			String sistExt, String descripcion) {
		super();
		this.id = id;
		this.duration = duration;
		this.actividad = actividad;
		this.proyecto = proyecto;
		this.fecha = fecha;
		this.ticket = ticket;
		this.ticketExt = ticketExt;
		this.sistExt = sistExt;
		this.descripcion = descripcion;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	/**
	 * @return the actividad
	 */
	public String getActividad() {
		return actividad;
	}
	/**
	 * @param actividad the actividad to set
	 */
	public void setActividad(String actividad) {
		this.actividad = actividad;
	}
	/**
	 * @return the proyecto
	 */
	public String getProyecto() {
		return proyecto;
	}
	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(String proyecto) {
		this.proyecto = proyecto;
	}
	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	/**
	 * @return the ticketExt
	 */
	public String getTicketExt() {
		return ticketExt;
	}
	/**
	 * @param ticketExt the ticketExt to set
	 */
	public void setTicketExt(String ticketExt) {
		this.ticketExt = ticketExt;
	}
	/**
	 * @return the sistExt
	 */
	public String getSistExt() {
		return sistExt;
	}
	/**
	 * @param sistExt the sistExt to set
	 */
	public void setSistExt(String sistExt) {
		this.sistExt = sistExt;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String texto= "{";
		texto+=this.getId();
		texto+=",";
		texto+=this.getDuration();
		texto+=",";
		texto+=this.getActividad();
		texto+=",";
		texto+=this.getProyecto();
		texto+=",";
		texto+=this.getFecha();
		texto+=",";
		texto+=this.getTicket();
		texto+=",";
		texto+=this.getTicketExt();
		texto+=",";
		texto+=this.getSistExt();
		texto+=",";
		texto+=this.getDescripcion();
		texto+="";
		texto+="}";
		return texto;
	}
	
}

