package com.odea;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.odea.dao.ActividadDAO;
import com.odea.dao.ProyectoDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Proyecto;
import com.odea.services.DAOService;


public class ProyectoDAOTest extends AbstractTestCase {

	@Autowired
	private DAOService daoService;
	
	
	@Test
	public void obtenerTodosLosProyectosTest(){
		List<Proyecto> proyectos = daoService.getProyectos();
		Assert.notEmpty(proyectos, "No se obtuvieron proyectos. La coleccion es nula o esta vacia.");
	}
	
	
//	@Test
//	public void obtenerProyectosDeUnaActividadTest(){
//		Actividad actividad = new Actividad(10, "Testing", true);
//		List<Proyecto> proyectos = daoService.getProyectos(actividad);
//		Assert.notEmpty(proyectos, "No se obtuvieron proyectos de la actividad. La coleccion es nula o esta vacia.");
//	}
//	
	
	@Test
	public void insertarProyectoTest(){
		Proyecto proyecto = new Proyecto(0, "proyectoDePrueba",true);
		daoService.agregarProyecto(proyecto, new ArrayList<Actividad>());
		
		Proyecto proyectoBuscado = daoService.buscarProyectoPorNombre(proyecto.getNombre());
		daoService.borrarProyecto(proyectoBuscado);
		
		Assert.notNull(proyectoBuscado, "No se ha encontrado el proyecto. No fue insertado correctamente o la busqueda no lo encontro");
	}
	
	
}
