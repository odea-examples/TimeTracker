package com.odea.components.yuidatepicker;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.joda.time.LocalDate;

import com.google.gson.Gson;
import com.odea.behavior.noInput.NoInputBehavior;
import com.odea.components.ajax.AbstractInitializableComponentBehavior;
import com.odea.components.datepicker.DatePickerDTO;

public abstract class YuiDatePicker extends FormComponentPanel<Date> implements IHeaderContributor {
    
	private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ENCODING = Application.get().getMarkupSettings().getDefaultMarkupEncoding();

    private TextField<Date> datePicker;
    private WebMarkupContainer calContainer;
    private AbstractDefaultAjaxBehavior ajaxBehavior;
    protected Date ultimaFecha = new LocalDate().toDate();
    
    
    public YuiDatePicker(String id) {
        super(id);
        this.setOutputMarkupId(true);

        this.calContainer = new WebMarkupContainer("calContainer");
        this.calContainer.setOutputMarkupId(true);

        this.datePicker = new TextField<Date>("date");
        this.datePicker.setOutputMarkupId(true);
        this.datePicker.add(new NoInputBehavior());
        
        add(this.calContainer);
        add(this.datePicker);

        
		this.add(new AbstractInitializableComponentBehavior(){

			public String getInitJSCall() {
				return "initYUI();";
			}
			
		});

        
        
        this.ajaxBehavior = new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(AjaxRequestTarget target) {
                if (getRequest().getRequestParameters().getParameterNames().contains("selectedDate")) {
                	String strFecha = getRequest().getRequestParameters().getParameterValue("selectedDate").toString();
                    YuiDatePicker.this.onDateSelect(target, strFecha);
                    
                    List<String> campos = Arrays.asList(strFecha.split("/"));
					int dia = Integer.parseInt(campos.get(0));
					int mes = Integer.parseInt(campos.get(1));
					int anio = Integer.parseInt(campos.get(2));
                    Date nuevaFecha = new LocalDate(anio, mes, dia).toDate();
                    YuiDatePicker.this.ultimaFecha = nuevaFecha;
                    
                }
                
                if (getRequest().getRequestParameters().getParameterNames().contains("updateF")) {
                    YuiDatePicker.this.respondHorasOcupacion();
                }
                
            }
        };
        
        add(this.ajaxBehavior);
        
        
        AjaxButton btnFechaActual = new AjaxButton("btnFechaActual") {
			
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				LocalDate fecha = new LocalDate();
				String strFecha = fecha.getDayOfMonth() + "/" + fecha.getMonthOfYear() + "/" + fecha.getYear();
			    YuiDatePicker.this.onDateSelect(target, strFecha);
			    YuiDatePicker.this.ultimaFecha = fecha.toDate();
			    YuiDatePicker.this.datePicker.setModelObject(fecha.toDate());
			    target.add(YuiDatePicker.this);
			}
			
		};
		
		btnFechaActual.setDefaultFormProcessing(false);
		btnFechaActual.setOutputMarkupId(true);
		
		add(btnFechaActual);
		
    }

    public void renderHead(IHeaderResponse response) {
        this.renderCSS(response, "fonts-min.css");
        this.renderCSS(response, "calendar.css");
        this.renderJS(response, "yahoo-dom-event.js");
        this.renderJS(response, "calendar-min.js");
    }

    private void renderJS(IHeaderResponse response, String jsFile) {
        JavaScriptResourceReference js = new JavaScriptResourceReference(YuiDatePicker.class, jsFile);
        response.render(JavaScriptReferenceHeaderItem.forReference(js));
    }

    private void renderCSS(IHeaderResponse response, String cssFile) {
        CssResourceReference css = new CssResourceReference(YuiDatePicker.class, cssFile);
        response.render(CssReferenceHeaderItem.forReference(css));
    }

    @Override
    protected void onAfterRender() {
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        new JavaScriptContentHeaderItem(this.prepareJSInitCall(), uniqueName + "js", null).render(this.getResponse());
        super.onAfterRender();
    }

    private String prepareJSInitCall() {
        Map<String, CharSequence> map = new HashMap<String, CharSequence>(4);
        map.put("selector", this.datePicker.getMarkupId());
        map.put("calContainer", this.calContainer.getMarkupId());
        map.put("url", this.ajaxBehavior.getCallbackUrl());
        map.put("datePickerId", this.getMarkupId());
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(YuiDatePicker.class, "yuidatepickertemplate.js", "text/javascript");
        return packageTextTemplate.asString(map);
    }

    @Override
    protected void convertInput() {
        setConvertedInput(this.datePicker.getConvertedInput());
    }

    @Override
    protected void onBeforeRender() {
    	this.setModelObject(this.ultimaFecha);
        this.datePicker.setModel(getModel());
        super.onBeforeRender();
    }

    public abstract DatePickerDTO getDatePickerData();

    private String toJson(DatePickerDTO result) {
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    protected void respondHorasOcupacion() {
        String jsonResultList = this.toJson(this.getDatePickerData());
        TextRequestHandler jsonHandler = new TextRequestHandler(JSON_CONTENT_TYPE, ENCODING, jsonResultList);
        getRequestCycle().scheduleRequestHandlerAfterCurrent(jsonHandler);
    }
    
    
    protected abstract void onDateSelect(AjaxRequestTarget target, String selectedDate);

}
