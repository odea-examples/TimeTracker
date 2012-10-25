package com.odea;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class BasePage extends WebPage {
	public BasePage(){
		super();
	}
	
	public BasePage(PageParameters parameters){
		super(parameters);
	}
}
