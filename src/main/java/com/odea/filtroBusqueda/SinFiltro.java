package com.odea.filtroBusqueda;

import java.util.Collection;
import java.util.Vector;

import com.odea.domain.Entrada;

public class SinFiltro implements FiltroDecorator {

	public SinFiltro(Collection<Entrada> entradas){
		this.setColeccionEntradas(entradas);
		
	}
	
	private Collection<Entrada> coleccionEntradas = new Vector<Entrada>();
	
	
	@Override
	public Collection<Entrada> filtrar() {
		return this.getColeccionEntradas();
	}

	
	
	//GETTERS & SETTERS

	public Collection<Entrada> getColeccionEntradas() {
		return coleccionEntradas;
	}
	public void setColeccionEntradas(Collection<Entrada> coleccionEntradas) {
		this.coleccionEntradas = coleccionEntradas;
	}
	
}
