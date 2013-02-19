package com.odea.services;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.slickGrid.Data;
import com.odea.dao.ActividadDAO;
import com.odea.dao.EntradaDAO;
import com.odea.dao.FeriadoDAO;
import com.odea.dao.ProyectoDAO;
import com.odea.dao.UsuarioDAO;
import com.odea.domain.Actividad;
import com.odea.domain.Entrada;
import com.odea.domain.Feriado;
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
	@Autowired
	private transient FeriadoDAO feriadosDAO;
	
	private Gson gson= new Gson();
	
	public Actividad getActividad(String nombre){
		return actividadDAO.buscarPorNombre(nombre);
	}
	
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
	
	public List<Actividad> actividadesOrigen(Proyecto proyecto){
		return actividadDAO.actividadesOrigen(proyecto);
	}
	
	public void insertarActividad(Actividad actividad, List<Proyecto> proyectosRelacionados) {
		actividadDAO.insertarActividad(actividad, proyectosRelacionados);
	}
	
	public Actividad buscarActividadPorNombre(String nombre) {
		return actividadDAO.buscarPorNombre(nombre);
	}
	
	
	public void agregarEntrada(Entrada entrada){
		entradaDAO.agregarEntrada(entrada);
	}
	
	public List<Data> getEntradas(Usuario usuario, Timestamp desde, Timestamp hasta){
		return entradaDAO.getData(usuario, desde, hasta);
	}
	
	public List<Data> getEntradasMensuales(Usuario usuario, LocalDate fechaElegida) {
		return entradaDAO.getEntradasMensuales(usuario, fechaElegida);
	}
	
	public List<Data> getEntradasSemanales(Usuario usuario, LocalDate fechaElegida){
		return entradaDAO.getEntradasSemanales(usuario, fechaElegida);
	}

	public List<Data> getEntradasDia(Usuario usuario, LocalDate hoy) {
		return entradaDAO.getEntradasDia(usuario, hoy);
	}
	
	public List<HorasCargadasPorDia> getHorasDiaras(Usuario usuario){
		return entradaDAO.horasPorDia(usuario);
	}
	
	public void agregarEntrada(Entrada entrada, Usuario usuario) {
		entrada.setUsuario(usuario);
		if (entrada.getSistemaExterno()==null){
			entrada.setTicketExterno(null);
		}
		this.entradaDAO.agregarEntrada(entrada);
	}
	
	public Entrada buscarEntrada(long id) {
		return entradaDAO.buscarEntrada(id);
	}

	
	public Collection<Entrada> getEntradas(Date desde,Date hasta){
		return entradaDAO.getEntradas(desde, hasta);
	}
	
	public Collection<Entrada> getEntradas(Proyecto proyecto, Date desde, Date hasta){
		return entradaDAO.getEntradas(proyecto, desde, hasta);
	}
	
	
	public int getHorasSemanales(Usuario usuario, LocalDate now){
		return entradaDAO.getHorasSemanales(usuario, now);
	}
	
	public int getHorasDiarias(Usuario usuario, LocalDate now){
		return entradaDAO.getHorasDiarias(usuario, now);
	}

	public int getHorasMensuales(Usuario usuario, LocalDate now){
		return entradaDAO.getHorasMensuales(usuario, now);
	}
	
	
	public void borrarEntrada(Entrada entrada){
		entradaDAO.borrarEntrada(entrada);
	}
	public void borrarEntrada(Timestamp entradaID){
		entradaDAO.borrarEntrada(entradaID);
	}
	
	public void modificarEntrada(Entrada entrada) {
		entradaDAO.modificarEntrada(entrada);
	}
	
	public List<String> getSistemasExternos() {
		return entradaDAO.getSistemasExternos();
	}
	
	public Proyecto getProyecto(String nombre){
		return proyectoDAO.buscarPorNombre(nombre);
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
		proyectoDAO.insertarProyecto(proyecto, actividadesRelacionadas);
	}
	
	public List<Proyecto> obtenerOrigen(Actividad actividad) {
		return proyectoDAO.obtenerOrigen(actividad);
	}
	
	public List<Proyecto> getProyectos(Actividad actividad) {
		return proyectoDAO.getProyectos(actividad);
	}
	
	public Proyecto buscarProyectoPorNombre(String nombre) {
		return proyectoDAO.buscarPorNombre(nombre);
	}
	
	public Usuario getUsuario(String nombre){
		System.out.println("Aca esta el otro: " + nombre);
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

	public void modificarUsuario(Usuario usuario) {
		usuarioDAO.modificarUsuario(usuario);
	}
	
	public List<Usuario> getUsuarios() {
		return usuarioDAO.getUsuarios();
	}
	
	public Integer getDedicacion(Usuario usuario, Date fechaAct){
		return usuarioDAO.getDedicacion(usuario, fechaAct);
	}
	
	public String toJson(Object o){
		return gson.toJson(o);
	}

	public List<HorasCargadasPorDia> getFeriadosData(LocalDate now) {
		return feriadosDAO.getFeriadosData(now);
	}
	
	public void insertarFeriado(Feriado feriado) {
		feriadosDAO.insertarFeriado(feriado);
	}
	
	public Feriado getFeriadoHoy(LocalDate hoy) {
		return feriadosDAO.getFeriadoHoy(hoy);
	}
	
	public void borrarFeriado(Feriado feriado) {
		feriadosDAO.borrarFeriado(feriado);
	}

	public List<Feriado> getFeriados(LocalDate now){
		return feriadosDAO.getFeriados(now);
	}
}
