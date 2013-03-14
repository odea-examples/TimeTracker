package com.odea;

import org.apache.wicket.markup.html.link.InlineFrame;

import com.odea.components.documentInlineFrame.MyPdfPanel;

public class PDFViewer extends BasePage {
	
	public PDFViewer() {
		//InlineFrame iframe = new InlineFrame("iframeloco", News.class);
		//add(iframe);
		add(new MyPdfPanel("myPdfPanel"));
	}
	
}
