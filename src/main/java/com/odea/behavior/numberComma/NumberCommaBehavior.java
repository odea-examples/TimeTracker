package com.odea.behavior.numberComma;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.template.PackageTextTemplate;

public class NumberCommaBehavior extends Behavior {

	@Override
	public void bind(Component component) {
		component.add(new AttributeAppender("onkeypress", "return isNumberCommaKey(event);"));
	}

    String jQuerySelector;

    public NumberCommaBehavior(String id){
        this.jQuerySelector = "#" + id;
    }
	
	@Override
	public void afterRender(Component component) {
        super.afterRender(component);
        Map<String, CharSequence> map = new HashMap<String, CharSequence>(1);
        map.put("selector", this.jQuerySelector);
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(getClass(), "numbercommabehavior.js", "text/javascript");
        String resource = packageTextTemplate.asString(map);
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        Response response = component.getResponse();
        new JavaScriptContentHeaderItem(resource,uniqueName,null).render(response);
	}
	
	
	
	

}