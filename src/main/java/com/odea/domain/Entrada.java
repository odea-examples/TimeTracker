package com.odea.domain;

import java.util.Date;

public class Entrada {
    private long idEntrada;
    private Proyecto proyecto;
    private Actividad actividad;
    private double duracion;
    private String nota;
    private String ticketBugZilla;
    private String ticketExterno;
    private String sistemaExterno;
    private Usuario usuario;
    private Date fecha;
    

    public Entrada(long idEntrada, Proyecto proyecto, Actividad actividad, double duracion, String nota, String ticketBugZilla, String ticketExterno, String sistemaExterno, Usuario usuario, Date fecha) {
        this.idEntrada = idEntrada;
        this.proyecto = proyecto;
        this.actividad = actividad;
        this.duracion = duracion;
        this.nota = nota;
        this.ticketBugZilla = ticketBugZilla;
        this.ticketExterno = ticketExterno;
        this.sistemaExterno = sistemaExterno;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getTicketBugZilla() {
        return ticketBugZilla;
    }

    public void setTicketBugZilla(String ticketBugZilla) {
        this.ticketBugZilla = ticketBugZilla;
    }

    public String getTicketExterno() {
        return ticketExterno;
    }

    public void setTicketExterno(String ticketExterno) {
        this.ticketExterno = ticketExterno;
    }

    public String getSistemaExterno() {
        return sistemaExterno;
    }

    public void setSistemaExterno(String sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
