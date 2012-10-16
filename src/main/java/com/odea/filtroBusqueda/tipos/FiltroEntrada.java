package com.odea.filtroBusqueda.tipos;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroAbstract;
import com.odea.filtroBusqueda.FiltroDecorator;

public class FiltroEntrada extends FiltroAbstract{
	
	public FiltroEntrada(FiltroDecorator componente, int idEntrada)
	{
		this.setSiguiente(componente);
		this.setCondicion(idEntrada);
	}
	
	
	@Override
	protected boolean aprueba(Entrada entrada) {
		boolean resultado = entrada.getIdentrada() == this.getCondicion();
		
		return resultado;
	}

}
