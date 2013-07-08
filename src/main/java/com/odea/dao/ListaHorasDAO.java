package com.odea.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.odea.domain.Usuario;
import com.odea.domain.UsuarioListaHoras;
import com.odea.services.DAOService;

@Repository
public class ListaHorasDAO extends AbstractDAO {
	
	@Autowired
	private transient EntradaDAO entradaDAO;
	
	@Autowired
	private transient UsuarioDAO usuarioDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ListaHorasDAO.class);
	
	public List<UsuarioListaHoras> obtenerHorasUsuarios(Date desde, Date hasta,String sector){
		List<UsuarioListaHoras> horasDeLosUsuarios = new ArrayList<UsuarioListaHoras>();
		List<Usuario> usuarios;
		if(sector=="Todos"){
			usuarios= usuarioDAO.getUsuarios();
		}else{
		usuarios= usuarioDAO.getUsuarios(sector);
		}
		for (Usuario usuario : usuarios) {
			UsuarioListaHoras usuarioConHoras = new UsuarioListaHoras();
			usuarioConHoras.setUsuario(usuario);
			usuarioConHoras.setDedicacion(usuarioDAO.getDedicacion(usuario));
			usuarioConHoras.setDiaHoras(entradaDAO.getHorasDia(usuario,desde,hasta));
			usuarioConHoras.setSector(usuarioDAO.getSector(usuario));
			horasDeLosUsuarios.add(usuarioConHoras);
		}
		return horasDeLosUsuarios;
	}
	
}
