package com.odea.filtroBusqueda;

import java.util.Collection;

import com.odea.domain.Entrada;

public interface FiltroDecorator {
	
	public Collection<Entrada> filtrar();
}
