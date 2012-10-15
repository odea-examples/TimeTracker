package com.odea.filtroBusqueda;

import java.util.Collection;
import java.util.Vector;

import com.odea.domain.Entrada;

public abstract class FiltroAbstract implements FiltroDecorator {

	protected int condicion;
	protected FiltroDecorator siguiente;

	protected abstract boolean aprueba(Entrada entrada);
	
	public Collection<Entrada> filtrar()
	{
		Collection<Entrada> entradas = this.getSiguiente().filtrar();
		
		Collection<Entrada> nuevaCol = new Vector<Entrada>();
		
		for (Entrada unaEntrada : entradas) {
			if (this.aprueba(unaEntrada) || !this.tieneCondicionDeFiltrado()) {
				nuevaCol.add(unaEntrada);
			}
		}
		
		return nuevaCol;
	}
	
	
	private boolean tieneCondicionDeFiltrado()
	{
		return !(this.getCondicion() == 0);
	}
	
	//GETTERS & SETTERS
	
	public FiltroDecorator getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(FiltroDecorator siguiente) {
		this.siguiente = siguiente;
	}

	public int getCondicion() {
		return condicion;
	}

	public void setCondicion(int condicion) {
		this.condicion = condicion;
	}

}
