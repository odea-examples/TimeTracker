package com.odea.components.documentInlineFrame;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class DocumentInlineFrame extends Panel {
	
	private static final long serialVersionUID = 1L;

	public DocumentInlineFrame(String id){
		super(id);
		ResourceReference pdfResource = new ResourceReference("pdfResource") {
	        private static final long serialVersionUID = 1L;
	        
	        @Override
	        public IResource getResource() {
	            return new ByteArrayResource("pdf", DocumentInlineFrame.this.getPdfBytes());
	        }
	        
	    };
	    
		WebMarkupContainer wmc = new WebMarkupContainer("myPdfPanel");
		wmc.add(new AttributeModifier("src", (String) urlFor(pdfResource, null)));
		wmc.setOutputMarkupId(true);
		add(wmc);
	}
	
	public abstract byte[] getPdfBytes();
}
