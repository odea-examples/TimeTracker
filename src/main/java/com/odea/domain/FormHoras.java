package com.odea.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormHoras implements Serializable {
	
	public Date fechaDesde;
	public Date fechaHasta;
	public String sector;
	
	public FormHoras(Date desde, Date hasta, String sector){
		this.fechaDesde=desde;
		this.fechaHasta=hasta;
		this.sector=sector;
	}
	
	public FormHoras(){
		
	}
	
	/**
	 * @return the desde
	 */
	public Date getDesde() {
		return fechaDesde;
	}
	/**
	 * @param desde the desde to set
	 */
	public void setDesde(Date desde) {
		this.fechaDesde = desde;
	}
	/**
	 * @return the hasta
	 */
	public Date getHasta() {
		return fechaHasta;
	}
	/**
	 * @param hasta the hasta to set
	 */
	public void setHasta(Date hasta) {
		this.fechaHasta = hasta;
	}
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	
	
}
