package com.odea;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.domain.Feriado;


public class FeriadosPage extends BasePage {
	
//	@SpringBean
//	public DAOService daoService;
	
		
	public FeriadosPage() {
		FeriadosForm form = new FeriadosForm("form");
		add(form);
	}

	
	
	public class FeriadosForm extends Form<Feriado> {
		
		public IModel<Feriado> feriadoModel = new CompoundPropertyModel<Feriado>(new Feriado());
		
		public FeriadosForm(String id) {
			super(id);
			this.setDefaultModel(this.feriadoModel);
			this.setOutputMarkupId(true);
			
			
			YuiDatePicker datePicker = new YuiDatePicker("fecha") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					//TODO
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					return new DatePickerDTO();
				}
			};
			
			datePicker.setOutputMarkupId(true);
			datePicker.setRequired(true);

			TextArea<String> descripcion = new TextArea<String>("descripcion");
			descripcion.setRequired(true);
			descripcion.setOutputMarkupId(true);
			
			
			AjaxButton submit = new AjaxButton("submit", this) {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					//TODO: enviar entrada de Feriado a BD
				}
			};
			
			submit.setOutputMarkupId(true);
			
			
			add(datePicker);
			add(descripcion);
			add(submit);
		}
		
	}
}

