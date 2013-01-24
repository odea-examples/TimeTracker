package com.odea.components.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;


public abstract class AbstractInitializableComponentBehavior extends Behavior {
    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        AjaxRequestTarget target = component.getRequestCycle().find(AjaxRequestTarget.class);

        if (target != null)
        {
            target.appendJavaScript(this.getInitJSCall());
        }
    }

    public abstract String getInitJSCall();

}
