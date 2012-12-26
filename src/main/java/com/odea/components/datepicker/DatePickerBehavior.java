package com.odea.components.datepicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.head.CssContentHeaderItem;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;

import com.google.gson.Gson;

/**
 * User: pbergonzi
 * Date: 25/10/12
 * Time: 11:54
 */

public abstract class DatePickerBehavior extends AbstractAjaxBehavior {
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ENCODING = Application.get().getMarkupSettings().getDefaultMarkupEncoding();
    private String jQuerySelector;

    public DatePickerBehavior(String id){
        this.jQuerySelector = "#" + id;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        //super.renderHead(component, response);    //To change body of overridden methods use File | Settings | File Templates.
        CssResourceReference css = new CssResourceReference(getClass(), "datepicker.css");
        response.render(CssReferenceHeaderItem.forReference(css));
    }
   

    
    @Override
    protected void onComponentRendered() {
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        new JavaScriptContentHeaderItem(this.prepareJSInitCall(),uniqueName + "js",null).render(this.getComponent().getResponse());
        //new CssContentHeaderItem(this.prepareCSS(),uniqueName + "css",null).render(this.getComponent().getResponse());
    }

    private String prepareCSS(){
        PackageResourceReference css = new PackageResourceReference(DatePickerBehavior.class,"datepicker.css");
        System.out.println(css.toString());
        return css.toString();
    }

    private String prepareJSInitCall(){
        Map<String, CharSequence> map = new HashMap<String, CharSequence>(2);
        map.put("selector", this.jQuerySelector);
        map.put("url", this.getCallbackUrl());
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(getClass(), "datepickertemplate.js", "text/javascript");
        return packageTextTemplate.asString(map);
    }

    @Override
    public void onRequest() {
        RequestCycle requestCycle = RequestCycle.get();
        String jsonResultList = this.toJson(this.getDatePickerData());
        requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler(JSON_CONTENT_TYPE, ENCODING, jsonResultList));

    }

    public abstract DatePickerDTO getDatePickerData();

    private String toJson(DatePickerDTO result) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(result));
        return gson.toJson(result);
    }
}
