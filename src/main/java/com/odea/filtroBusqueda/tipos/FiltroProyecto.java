package com.odea.filtroBusqueda.tipos;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroAbstract;
import com.odea.filtroBusqueda.FiltroDecorator;

public class FiltroProyecto extends FiltroAbstract{

	public FiltroProyecto(FiltroDecorator componente, int idProyecto)
	{
		this.setSiguiente(componente);
		this.setCondicion(idProyecto);
	}
	
	
	@Override
	protected boolean aprueba(Entrada entrada) {
		boolean resultado = entrada.getId_proyecto() == this.getCondicion();
		
		return resultado;
	}

}
