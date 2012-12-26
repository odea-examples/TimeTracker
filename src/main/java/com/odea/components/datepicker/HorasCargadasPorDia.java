package com.odea.components.datepicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HorasCargadasPorDia {
    private String dia;
    private int horasCargadas;

    public HorasCargadasPorDia(Date dia, int horasCargadas) {
        SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy");
        this.dia = DATE_FMT.format(dia);
        this.horasCargadas = horasCargadas;
    }

    public String getDia() {
        return dia;
    }

    public int getHorasCargadas() {
        return horasCargadas;
    }

	@Override
	public String toString() {
		return "dia:"+dia+", Horas:"+horasCargadas+".";
	}
    
    
}
