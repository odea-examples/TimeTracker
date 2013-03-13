package com.odea.components.documentInlineFrame;

import org.apache.wicket.markup.html.panel.Panel;

public class MyPdfPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public MyPdfPanel(String id) {
		super(id);
		
		setRenderBodyOnly(true);
		add(new DocumentInlineFrame("mypdf", new MyPdfResource("prueba")));
	}
}
