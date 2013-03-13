package com.odea;

import com.odea.components.documentInlineFrame.MyPdfPanel;

public class PDFViewer extends BasePage {
	
	public PDFViewer() {
		
		add(new MyPdfPanel("myPdfPanel"));
	}
	
}
