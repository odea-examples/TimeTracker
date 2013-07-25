package com.odea.components.datepicker;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.odea.domain.Feriado;


public class DatePickerDTO {

	private String usuario;
	private int dedicacion;
    private Collection<HorasCargadasPorDia> horasDia;
    private List<Feriado> feriados;
    private List<Integer> fecha;
    private String fechaSeleccionada;
    
    public DatePickerDTO() {
    	
    }

    public DatePickerDTO(String usuario, int dedicacion, Collection<HorasCargadasPorDia> horasDia) {
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
    
    public int getHorasDia(String dia){
    	
    	return 0;
    }

    public void setHorasDia(Collection<HorasCargadasPorDia> horasDia) {
        this.horasDia = horasDia;
    }
    
    public List<Feriado> getFeriados() {
		return feriados;
	}

	public void setFeriados(List<Feriado> feriados) {
		this.feriados = feriados;
	}
	
    public List<Integer> getFecha() {
		return fecha;
	}

	public void setFecha(List<Integer> fecha) {
		this.fecha = fecha;
	}
	
	public String getFechaSeleccionada() {
		return fechaSeleccionada;
	}

	public void setFechaSeleccionada(Date fechaSeleccionada) {
		SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy");
		this.fechaSeleccionada = DATE_FMT.format(fechaSeleccionada);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DatePickerDTO [usuario=" + usuario + ", dedicacion="
				+ dedicacion + ", horasDia=" + horasDia + ", feriados="
				+ feriados + ", fecha=" + fecha + ", fechaSeleccionada="+ fechaSeleccionada +"]";
	}
	
}
