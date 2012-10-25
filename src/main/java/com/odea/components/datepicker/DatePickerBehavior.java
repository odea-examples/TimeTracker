package com.odea.components.datepicker;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * User: pbergonzi
 * Date: 25/10/12
 * Time: 11:54
 */
public class DatePickerBehavior extends Behavior{
    String jQuerySelector;

    public DatePickerBehavior(String id){
        this.jQuerySelector = "#" + id;
    }

    @Override
    public void afterRender(Component component) {
        super.afterRender(component);
        Map<String, CharSequence> map = new HashMap<String, CharSequence>(1);
        map.put("selector", this.jQuerySelector);
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(getClass(), "datepickertemplate.js", "text/javascript");
        String resource = packageTextTemplate.asString(map);
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        Response response = component.getResponse();
        new JavaScriptContentHeaderItem(resource,uniqueName,null).render(response);
    }

}
