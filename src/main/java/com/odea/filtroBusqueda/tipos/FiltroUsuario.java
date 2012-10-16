package com.odea.filtroBusqueda.tipos;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroAbstract;
import com.odea.filtroBusqueda.FiltroDecorator;

public class FiltroUsuario extends FiltroAbstract{
	
	public FiltroUsuario(FiltroDecorator componente, int idUsuario)
	{
		this.setSiguiente(componente);
		this.setCondicion(idUsuario);
	}
	
	
	@Override
	protected boolean aprueba(Entrada entrada) {
		boolean resultado = entrada.getIdusuario() == this.getCondicion();
		
		return resultado;
	}
	
}
