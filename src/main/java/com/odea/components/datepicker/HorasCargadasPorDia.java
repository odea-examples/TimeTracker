package com.odea.components.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HorasCargadasPorDia {
    private String dia;
    private double horasCargadas;

    public HorasCargadasPorDia(Date dia, double horasCargadas) {
        SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy");
        this.dia = DATE_FMT.format(dia);
        this.horasCargadas = horasCargadas;
    }

    public String getDia() {
        return dia;
    }
    public Date getDiaDate(){
    	SimpleDateFormat DATE_FMT = new SimpleDateFormat("dd/MM/yyyy");
    	try {
			return DATE_FMT.parse(dia);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

    public double getHorasCargadas() {
        return horasCargadas;
    }

	@Override
	public String toString() {
		return "dia:"+dia+", Horas:"+horasCargadas+".";
	}
    
    
}
