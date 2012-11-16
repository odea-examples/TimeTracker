package com.odea.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odea.dao.ActividadDAO;
import com.odea.dao.EntradaDAO;
import com.odea.dao.ProyectoDAO;
import com.odea.dao.UsuarioDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Proyecto;
import com.odea.domain.Usuario;

@Service
public class DAOService {
	
	@Autowired
	private transient EntradaDAO entradaDAO;
	@Autowired
	private transient UsuarioDAO usuarioDAO;
	@Autowired
	private transient ActividadDAO actividadDAO;
	@Autowired
	private transient ProyectoDAO proyectoDAO;
		
	
	
	public List<Actividad> getActividades(Proyecto proyecto){
		return actividadDAO.getActividades(proyecto);
	}
	
	public List<Actividad> getActividades(){
		return actividadDAO.getActividades();
	}
	
	public void insertarNuevaActividad(Actividad actividad)
	{
		actividadDAO.insertarNuevaActividad(actividad);
	}
	
	public void borrarActividad(Actividad actividad){
		actividadDAO.borrarActividad(actividad);
	}
	
	public void modificarActividad(String nombre, int idFinal) {
		actividadDAO.modificarActividad(nombre, idFinal);
	}
	
	public List<Actividad> actividadesOrigen(List<Actividad> Todas, List<Actividad> delProyecto){
		return actividadDAO.actividadesOrigen(Todas, delProyecto);
	}
	
	
	public void agregarEntrada(Entrada entrada){
		entradaDAO.agregarEntrada(entrada);
	}
	
	public Collection<Entrada> getEntradas(Usuario usuario, Date desde, Date hasta){
		return entradaDAO.getEntradas(usuario, desde, hasta);
	}
	
	
	public Collection<Entrada> getEntradas(Date desde,Date hasta){
		return entradaDAO.getEntradas(desde, hasta);
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		return entradaDAO.getEntradas(proyecto, desde, hasta);
	}
	
	
	public int getHorasSemanales(Usuario usuario){
		return entradaDAO.getHorasSemanales(usuario);
	}
	
	
	
	public List<Proyecto> getProyectos(){
		return proyectoDAO.getProyectos();
	}
	
	public void actualizarRelaciones(int idProyecto, List<Actividad> borrar, List<Actividad> añadir){
		proyectoDAO.actualizarRelaciones(idProyecto, borrar, añadir);
	}
	
	public void borrarProyecto(Proyecto proyecto)
	{
		proyectoDAO.borrarProyecto(proyecto);
	}
	
	public void agregarProyecto(Proyecto proyecto, Collection<Actividad> actividadesRelacionadas) {
		proyectoDAO.agregarProyecto(proyecto, actividadesRelacionadas);
	}
	
	public Usuario getUsuario(String nombre){
		return usuarioDAO.getUsuario(nombre);
	}
		
	public void agregarUsuario(Usuario usuario){
		usuarioDAO.agregarUsuario(usuario);
	}
	
	public Collection<Usuario> getUsuarios(Proyecto proyecto){
		return usuarioDAO.getUsuarios(proyecto);
	}
	
	public Usuario getUsuario(String nombre, String password){
		return usuarioDAO.getUsuario(nombre, password);
	}

	public void agregarEntrada(Entrada entrada, Usuario usuario) {
		entrada.setUsuario(usuario);
		if (entrada.getSistemaExterno()==null){
			entrada.setTicketExterno(null);
		}
		this.entradaDAO.agregarEntrada(entrada);
	}
	
	public List<Entrada> getEntradasSemanales(Usuario usuario){
		return entradaDAO.getEntradasSemanales(usuario);
	}



}
