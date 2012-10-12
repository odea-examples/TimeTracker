package com.odea.util;

import java.util.Collection;
import java.util.Vector;

import com.odea.domain.Entrada;
import com.odea.filtroBusqueda.FiltroDecorator;
import com.odea.filtroBusqueda.SinFiltro;
import com.odea.filtroBusqueda.tipos.FiltroActividad;
import com.odea.filtroBusqueda.tipos.FiltroEntrada;
import com.odea.filtroBusqueda.tipos.FiltroProyecto;
import com.odea.filtroBusqueda.tipos.FiltroTicket;
import com.odea.filtroBusqueda.tipos.FiltroUsuario;

public class UFiltro {
	
	public static Collection<Entrada> filtrarEntradas(Collection<Entrada> entradas, int idEntrada, int idProyecto, int idActividad, int idTicket, int idUsuario){
		Collection<Entrada> nuevaCol = new Vector<Entrada>();
		
		FiltroDecorator componente = new SinFiltro(entradas);
		
		componente = new FiltroProyecto(componente, idProyecto);
		componente = new FiltroActividad(componente, idActividad);
		componente = new FiltroTicket(componente, idTicket);
		componente = new FiltroUsuario(componente, idUsuario);
		componente = new FiltroEntrada(componente, idEntrada);
		
		nuevaCol = componente.filtrar();
		
		return nuevaCol;
	}
}
