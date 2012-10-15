package com.odea.filtroBusqueda.tipos;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroAbstract;
import com.odea.filtroBusqueda.FiltroDecorator;

public class FiltroActividad extends FiltroAbstract {

	public FiltroActividad(FiltroDecorator componente, int idActividad)
	{
		this.setSiguiente(componente);
		this.setCondicion(idActividad);
	}
	
	
	@Override
	protected boolean aprueba(Entrada entrada) {
		boolean resultado = entrada.getId_actividad() == this.getCondicion();
		
		return resultado;
	}
}
