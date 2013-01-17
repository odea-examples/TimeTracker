package com.odea;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.yuidatepicker.YuiDatePicker;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class YuiPage extends BasePage {
    Date fecha;

    public YuiPage() {
        Form<BasePage> f = new Form<BasePage>("form", new CompoundPropertyModel<BasePage>(this));
        f.add(new YuiDatePicker("fecha") {
            @Override
            public DatePickerDTO getDatePickerData() {

                DatePickerDTO dto = new DatePickerDTO();
                dto.setDedicacion(8);
                dto.setUsuario("invitado");
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                Date d;
                try {
                    d = f.parse("20/01/2013");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                HorasCargadasPorDia h = new HorasCargadasPorDia(d, 8);
                Collection<HorasCargadasPorDia> c = new ArrayList<HorasCargadasPorDia>();
                c.add(h);
                dto.setHorasDia(c);
                return dto;
            }


            @Override
            protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
                System.out.println(selectedDate + " abstract");
            }
        });
        f.add(new AjaxButton("submit") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                Date y = ((YuiPage) form.getModelObject()).getFecha();
                System.out.println(y);
            }
        });
        add(f);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
