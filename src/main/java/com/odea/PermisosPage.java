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
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.LocalDate;

import com.odea.components.datepicker.DatePickerDTO;
import com.odea.components.datepicker.HorasCargadasPorDia;
import com.odea.components.slickGrid.Columna;
import com.odea.components.slickGrid.Data;
import com.odea.components.slickGrid.SlickGrid;
import com.odea.components.yuidatepicker.YuiDatePicker;
import com.odea.domain.Feriado;
import com.odea.services.DAOService;


public class PermisosPage extends BasePage {
	
	@SpringBean
	public DAOService daoService;
	
	private IModel<String> slickGridJsonCols;
	
		
	public PermisosPage() {
		this.slickGridJsonCols = Model.of(this.getColumns());
		
		
		SlickGrid slickgrid = new SlickGrid("slickgrid", null, null) {
			
			@Override
			protected void onInfoSend(AjaxRequestTarget target, String realizar,
					Data data) {
				System.out.println(data);
				
			}
		};
	}


	private String getColumns() {
		//crear cols
		Columna columna1 = new Columna("col1","usuario",80,80, 150,"cell-title","usuario",null,"Slick.Editors.Text",null,null);
		Columna columna2 = new Columna("col2","dia1",80,80, 150,"cell-title","dia1",null,"Slick.Editors.Text",null,null);
		Columna columna3 = new Columna("col3","dia2",80,80, 150,"cell-title","dia2",null,"Slick.Editors.Text",null,null);
		ArrayList<Columna> columnas = new ArrayList<Columna>();
		//columnas.add columnax
		columnas.add(columna1);
		//parseador
		
		String texto = "[";
		for (Columna col : columnas) {
			texto += "{id:\"" + col.getId() + "\", name: \"" + col.getName()
					+ "\", width: " + col.getWidth() + ", minWidth: "
					+ col.getMinWidth() + ", maxWidth: " + col.getMaxWidth()
					+ ", cssClass: \"" + col.getCssClass() + "\", field: \""
					+ col.getField() + "\",formatter: " + col.getFormatter()
					+ ", editor: " + col.getEditor() + ", validator: "
					+ col.getValidator() + ", options: \"" + col.getOptions()
					+ "\"},";
		}
		texto += "]";
		return texto;
	}	
	
}

