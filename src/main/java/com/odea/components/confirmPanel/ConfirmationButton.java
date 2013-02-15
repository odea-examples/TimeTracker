package com.odea.components.confirmPanel;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.model.IModel;

public class ConfirmationButton extends AjaxButton{
	
	private final String text;
	
	public ConfirmationButton(String id, String text, IModel model)
	{
		super(id, model);
		this.text = text;
	}
	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition( "return confirm('" + text + "');" );
		attributes.getAjaxCallListeners().add( ajaxCallListener );
	}
	
	
	
}
