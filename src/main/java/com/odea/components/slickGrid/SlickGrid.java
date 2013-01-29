package com.odea.components.slickGrid;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.odea.components.ajax.AbstractInitializableComponentBehavior;
import com.odea.dao.EntradaDAO;



public abstract class SlickGrid extends WebMarkupContainer{
	private IModel<String> data;
	private IModel<String> columnas;
	private String jQuerySelector;
	private static final long serialVersionUID = 1L;
	private AbstractDefaultAjaxBehavior ajaxBehavior;
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ENCODING = Application.get().getMarkupSettings().getDefaultMarkupEncoding();
	//asd
	
    @Autowired
    EntradaDAO entradadao;
    
	public SlickGrid(String id, IModel<String> data, IModel<String> columnas) {
		super(id);
		this.data = data;
		this.columnas = columnas;
		this.setOutputMarkupId(true);
        this.jQuerySelector= "#" +this.getMarkupId();
        this.ajaxBehavior = new AbstractDefaultAjaxBehavior() {
			
			@Override
			protected void respond(AjaxRequestTarget target) {
                if (getRequest().getRequestParameters().getParameterNames().contains("modificar")) {
                	Gson gson = new Gson();
                	String respuestaGson = getRequest().getRequestParameters().getParameterValue("modificar").toString();
					Data data = gson.fromJson(respuestaGson, Data.class);
//					System.out.println(data);
					SlickGrid.this.onInfoSend(target,"modificar",data);
                }
                if (getRequest().getRequestParameters().getParameterNames().contains("borrar")) {
                	Gson gson = new Gson();
                	String respuestaGson = getRequest().getRequestParameters().getParameterValue("borrar").toString();
                	System.out.println(respuestaGson);
                	Data data = new Data(gson.fromJson(respuestaGson, String.class),null,null,null,null,null,null,null,null);
                	SlickGrid.this.onInfoSend(target,"borrar",data);
                    //(target, getRequest().getRequestParameters().getParameterValue("selectedDate").toString());
                }
				
			}
		}; 
		
        this.add(this.ajaxBehavior);
        this.add(new AbstractInitializableComponentBehavior(){

			@Override
			public String getInitJSCall() {
				return "iniciar();";
			}
        	
        });

	}
	
	
	@Override
	public void renderHead(IHeaderResponse response) {
		JSCrearResource("JS/lib/firebugx.js",response);
		JSCrearResource("JS/lib/jquery-1.7.min.js",response);
		JSCrearResource("JS/lib/jquery-ui-1.8.16.custom.min.js",response);
		JSCrearResource("JS/lib/jquery.event.drag-2.0.min.js",response);
		JSCrearResource("JS/slick.core.js",response);
		JSCrearResource("JS/slick.formatters.js",response);
		JSCrearResource("JS/slick.editors.js",response);
		JSCrearResource("JS/plugins/slick.rowselectionmodel.js",response);
		JSCrearResource("JS/slick.grid.js",response);
		JSCrearResource("JS/slick.dataview.js",response);
		JSCrearResource("JS/controls/slick.pager.js",response);
		JSCrearResource("JS/controls/slick.columnpicker.js",response);
		// ---------------------separacion-------------------------
		
		CssCrearResource("JS/slick.grid.css",response);
		CssCrearResource("JS/controls/slick.pager.css",response);
		CssCrearResource("JS/css/smoothness/jquery-ui-1.8.16.custom.css",response);
		CssCrearResource("JS/examples.css",response);
		CssCrearResource("JS/controls/slick.columnpicker.css",response);
		CssCrearResource("JS/otro.css",response);
	}
	
	private void CssCrearResource(String string, IHeaderResponse response) {
        CssResourceReference css = new CssResourceReference(SlickGrid.class, string);
        response.render(CssReferenceHeaderItem.forReference(css));
	}
	private void JSCrearResource(String string, IHeaderResponse response) {
		JavaScriptResourceReference jrr= new JavaScriptResourceReference(SlickGrid.class, string);
		response.render(JavaScriptReferenceHeaderItem.forReference(jrr));
		
	}
	@Override
	protected void onAfterRender() {
		super.onAfterRender();
		String uniqueName =Long.toString(Calendar.getInstance().getTimeInMillis());
        new JavaScriptContentHeaderItem(this.prepareJSInitCall(),uniqueName + "js",null).render(this.getResponse());
	}
	private String prepareJSInitCall() {
		Map<String, CharSequence> map = new HashMap<String, CharSequence>(5);
        map.put("selector", this.jQuerySelector);
        map.put("columns",getColumns());
        map.put("data",getData());
        map.put("url", this.ajaxBehavior.getCallbackUrl());
        map.put("gridId",this.getMarkupId());
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(SlickGrid.class, "slickGridTemplate.js", "text/javascript");
        return packageTextTemplate.asString(map);
	}
	protected abstract void onInfoSend(AjaxRequestTarget target, String realizar, Data data);
	
	protected String getColumns() {
		return this.columnas.getObject();
	}
	protected String getData() {
		return this.data.getObject();
	}
}