package com.odea.components.documentInlineFrame;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.string.Strings;

/**
 * Implementation of an <a href="http://www.w3.org/TR/REC-html40/present/frames.html#h-16.5">inline
 * frame</a> component. Must be used with an iframe (&lt;iframe src...) element. The src attribute
 * will be generated. Its is suitable for displaying <em>generated contend</em> like PDF, EXCEL, WORD, 
 * etc.
 */

public class DocumentInlineFrame extends WebMarkupContainer implements IResourceListener 
{
	private static final long serialVersionUID = 1L;
	
	
	private IResourceListener resourceListener;


	public DocumentInlineFrame(final String id, IResourceListener resourceListener)
	{
		super(id);
		this.resourceListener = resourceListener;
	}

	/**
	 * Gets the url to use for this link.
	 * 
	 * @return The URL that this link links to
	 */
	protected CharSequence getURL()
	{
		return this.urlFor(IResourceListener.INTERFACE, null);
		//return urlFor();
	}


	@Override
	protected final void onComponentTag(final ComponentTag tag)
	{
		checkComponentTag(tag, "iframe");

		// Set href to link to this frame's frameRequested method
		CharSequence url = getURL();

		// generate the src attribute
		tag.put("src", Strings.replaceAll(url, "&", "&amp;"));

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

