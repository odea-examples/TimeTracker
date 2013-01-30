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


public class ProyectoDAOTest extends AbstractTestCase {

	@Autowired
	private ProyectoDAO proyectoDAO;
	
	@Autowired
	private ActividadDAO actividadDAO;
	
	
	@Test
	public void obtenerTodosLosProyectosTest(){
		List<Proyecto> proyectos = proyectoDAO.getProyectos();
		Assert.notEmpty(proyectos, "No se obtuvieron proyectos. La coleccion es nula o esta vacia.");
	}
	
	
	@Test
	public void obtenerProyectosDeUnaActividadTest(){
		Actividad actividad = new Actividad(10, "Testing");
		List<Proyecto> proyectos = proyectoDAO.getProyectos(actividad);
		Assert.notEmpty(proyectos, "No se obtuvieron proyectos de la actividad. La coleccion es nula o esta vacia.");
	}
	
	
	@Test
	public void insertarProyectoTest(){
		Proyecto proyecto = new Proyecto(0, "proyectoDePrueba");
		proyectoDAO.agregarProyecto(proyecto, new ArrayList<Actividad>());
		
		Proyecto proyectoBuscado = proyectoDAO.buscarPorNombre(proyecto.getNombre());
		proyectoDAO.borrarProyecto(proyectoBuscado);
		
		Assert.notNull(proyectoBuscado, "No se ha encontrado el proyecto. No fue insertado correctamente o la busqueda no lo encontro");
	}
	
	
}
