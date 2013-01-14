package com.odea.components.slickGrid;

import java.util.Collection;

public class SlickGridDTO {
private Collection<Columna> columnas;
private Collection<Data> datos;
public String obtenerCols(Collection<Columna> col){
	String texto="";
	for (Columna columna : col) {
		texto+="{id: \""+ columna.getId() +"\", name: \""+ columna.getName() +"\", width: "+ columna.getWidth() +", minWidth: "+ columna.getMinWidth() +", maxWidth: "+ columna.getMaxWidth() +", cssClass: \""+ columna.getCssClass() +"\", field: \""+ columna.getField() +"\",formatter: "+ columna.getFormatter() +", editor: "+ columna.getEditor() +"}";
	}
	return texto;
	
}
}