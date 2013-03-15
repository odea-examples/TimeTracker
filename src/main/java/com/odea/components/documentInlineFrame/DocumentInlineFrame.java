package com.odea.components.documentInlineFrame;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;



public class DocumentInlineFrame extends WebMarkupContainer implements IResourceListener 
{
	private static final long serialVersionUID = 1L;
	
	
	private IResourceListener resourceListener;


	public DocumentInlineFrame(final String id, IResourceListener resourceListener)
	{
		super(id);
		this.resourceListener = resourceListener;
	}

	protected CharSequence getURL()
	{
		return this.urlFor(IResourceListener.INTERFACE, new PageParameters());
	}


	@Override
	protected final void onComponentTag(final ComponentTag tag)
	{
		checkComponentTag(tag, "iframe");

		// Set href to link to this frame's frameRequested method
		CharSequence url = getURL();
		
		// generate the src attribute
//		tag.put("src", Strings.replaceAll(url, "&", "&amp;"));
		tag.put("src","/latin9.pdf");
		super.onComponentTag(tag);
	}

	

	@Override
	protected boolean getStatelessHint()
	{	
		return false;
	}
	
	public void onResourceRequested() {
		this.resourceListener.onResourceRequested();
	}
	
}

