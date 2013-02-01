package com.odea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.slickGrid.Data;
import com.odea.dao.EntradaDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

public class Datatest extends AbstractTestCase{
	@Autowired
	private EntradaDAO dao;
	@Test
    public void testData() {
		Data datas = new Data("20/01/2010","15hs","vacaciones","RRHH","25/08/2002","Task 117","Task118","Task 119","Esta es la gran descripcion");
		//System.out.println(getdata());
    }

public String getdata(){
		
		Data datas = new Data("20/01/2010","15hs","vacaciones","RRHH","25/08/2002","Task 117","Task118","Task 119","Esta es la gran descripcion");
		ArrayList<Data> Datos = new ArrayList<Data>();
		Datos.add(datas);
		return toJson(Datos);
	}
	private String toJson(ArrayList<Data> result) {
        Gson gson = new Gson();
        return gson.toJson(result);
    }
}

