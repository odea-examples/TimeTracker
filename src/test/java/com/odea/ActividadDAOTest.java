package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.odea.dao.ActividadDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;

public class ActividadDAOTest extends AbstractTestCase {
	
	@Autowired
	private DAOService daoService;
	
	@Test
	public void obtenerTodasLasActividadesTest() {
		List<Actividad> actividades = daoService.getActividades();
		Assert.notEmpty(actividades, "No se encontraron actividades. La coleccion es nula o esta vacia");
	}
	
	@Test
	public void obtenerActividadesDeProyectoTest() {
		Proyecto proyecto = new Proyecto(10, "YPF-DBU");
		
		List<Actividad> actividades = daoService.getActividades(proyecto);
		Assert.notEmpty(actividades, "No se encontraron actividades de este proyecto. La coleccion es nula o esta vacia");
	}
	
	@Test
	public void insertarActividadTest(){
		Actividad actividad = new Actividad(0, "ActividadDePrueba");
		daoService.insertarActividad(actividad, new ArrayList<Proyecto>());
		
		Actividad actividadBuscada = daoService.buscarActividadPorNombre(actividad.getNombre());
		daoService.borrarActividad(actividadBuscada);
		
		Assert.notNull(actividadBuscada, "No se ha encontrado la actividad buscada. No se inserto correctamente o la busqueda fallo");
	}

}
