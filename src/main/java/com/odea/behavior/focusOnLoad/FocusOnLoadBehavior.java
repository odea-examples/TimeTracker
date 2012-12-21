package com.odea.behavior.focusOnLoad;

import java.util.Calendar;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.request.Response;

public class FocusOnLoadBehavior extends Behavior {
    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    @Override
    public void afterRender(Component component) {
        String resource = "document.getElementById('" + component.getMarkupId() + "').focus();";
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        Response response = component.getResponse();
        new JavaScriptContentHeaderItem(resource, uniqueName, null).render(response);
    }

}  