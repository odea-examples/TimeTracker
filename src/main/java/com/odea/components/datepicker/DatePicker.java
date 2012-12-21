package com.odea.components.datepicker;

import org.apache.wicket.markup.html.form.TextField;

import java.util.Date;

public abstract class DatePicker extends TextField<Date> {

    public DatePicker(String id) {
        super(id);
        this.setOutputMarkupId(true);
        add(new DatePickerBehavior(this.getMarkupId()) {
            @Override
            public DatePicketDTO getDatePickerData() {
                return DatePicker.this.getDatePickerData();
            }
        });
    }

    public abstract DatePicketDTO getDatePickerData();
}
