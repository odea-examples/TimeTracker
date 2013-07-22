package com.odea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.domain.Feriado;
import com.odea.services.DAOService;


public class FeriadosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
		
	public FeriadosPage() {
		FeriadosForm form = new FeriadosForm("form");
		add(form);
	}

	
	
	public class FeriadosForm extends Form<Feriado> {
		
		public IModel<Feriado> feriadoModel = new CompoundPropertyModel<Feriado>(new Feriado());
		public LocalDate fechaActual = new LocalDate();
		public TextArea<String> descripcion;
		public YuiDatePicker datePicker;
		
		
		public FeriadosForm(String id) {
			super(id);
			this.setDefaultModel(this.feriadoModel);
			this.setOutputMarkupId(true);
			
			
			datePicker = new YuiDatePicker("fecha") {
				
				@Override
				protected void onDateSelect(AjaxRequestTarget target, String selectedDate) {
					String json = selectedDate;
					List<String> campos = Arrays.asList(json.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));

					FeriadosForm.this.fechaActual = new LocalDate(anio, mes, dia);
					FeriadosForm.this.setModelObject(daoService.getFeriadoHoy(FeriadosForm.this.fechaActual));
					
					
					target.add(FeriadosForm.this.descripcion);						
					
				}
				
				@Override
				public DatePickerDTO getDatePickerData() {
					DatePickerDTO dto = new DatePickerDTO();
					dto.setDedicacion(-1);
					dto.setUsuario("admin");
					Collection<HorasCargadasPorDia> horas = daoService.getFeriadosData();
					dto.setHorasDia(horas);
															
					return dto;				
				}
				
			};
			
			datePicker.setOutputMarkupId(true);
			datePicker.setRequired(true);

			descripcion = new TextArea<String>("descripcion");
			descripcion.setRequired(true);
			descripcion.setOutputMarkupId(true);
			
			final FeedbackPanel feedBackPanel = new FeedbackPanel("feedBackPanel");
			feedBackPanel.setOutputMarkupId(true);
			feedBackPanel.setMarkupId("feedBackPanel");
			
			
			AjaxButton submit = new AjaxButton("submit", this) {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					daoService.insertarFeriado(FeriadosForm.this.getModelObject());
					target.add(FeriadosForm.this.datePicker);
					target.add(FeriadosForm.this.descripcion);
					target.add(feedBackPanel);

				}
				
				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					target.add(FeriadosForm.this.datePicker);
					target.add(FeriadosForm.this.descripcion);
					target.add(feedBackPanel);
				}
			};
			
			submit.setOutputMarkupId(true);
			
			AjaxButton borrar = new AjaxButton("borrar", this) {
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					daoService.borrarFeriado(FeriadosForm.this.getModelObject());
					FeriadosForm.this.setModelObject(new Feriado());
					target.add(FeriadosForm.this.datePicker);
					target.add(FeriadosForm.this.descripcion);
					target.add(feedBackPanel);
				}
				
				
			};
			
			borrar.setOutputMarkupId(true);
			

			add(feedBackPanel);
			add(datePicker);
			add(descripcion);
			add(submit);
			add(borrar);
		}
		
	}
}

