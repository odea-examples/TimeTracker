package com.odea.components.confirmPanel;

import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

public abstract class ConfirmationLink<T> extends AjaxLink<T>
{
	private static final long serialVersionUID = 1L;
	private final String text;

	public ConfirmationLink( String id, String text, IModel<T> model)
	{
		super(id, model);
		this.text = text;
	}
	
	@Override
	protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
	{
		super.updateAjaxAttributes( attributes );
		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition( "return confirm('" + text + "');" );
		attributes.getAjaxCallListeners().add( ajaxCallListener );
	}
}
