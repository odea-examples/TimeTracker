package com.odea.components.datepicker;

import java.util.Collection;

public class DatePicketDTO {
    private String usuario;
    private int dedicacion;
    private Collection<HorasCargadasPorDia> horasDia;

    public DatePicketDTO() {
    }

    public DatePicketDTO(String usuario, int dedicacion, Collection<HorasCargadasPorDia> horasDia) {
        this.usuario = usuario;
        this.dedicacion = dedicacion;
        this.horasDia = horasDia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(int dedicacion) {
        this.dedicacion = dedicacion;
    }

    public Collection<HorasCargadasPorDia> getHorasDia() {
        return horasDia;
    }

    public void setHorasDia(Collection<HorasCargadasPorDia> horasDia) {
        this.horasDia = horasDia;
    }
}
