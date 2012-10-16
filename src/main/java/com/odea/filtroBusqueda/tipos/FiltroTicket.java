package com.odea.filtroBusqueda.tipos;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroAbstract;
import com.odea.filtroBusqueda.FiltroDecorator;

public class FiltroTicket extends FiltroAbstract {
	
	public FiltroTicket(FiltroDecorator componente, int idTicket)
	{
		this.setSiguiente(componente);
		this.setCondicion(idTicket);
	}
	
	
	@Override
	protected boolean aprueba(Entrada entrada) {
		boolean resultado = entrada.getIdticketbz() == this.getCondicion();
		
		return resultado;
	}
}
