package com.odea.components.slickGrid;

public class Enumeraciones {
	public enum editores {
        Text("Slick.Editors.Text"),
        Integer("Slick.Editors.Integer"),
        Date("Slick.Editors.Date"),
        YesNoSelect("Slick.Editors.YesNoSelect"),
        Checkbox("Slick.Editors.Checkbox"),
        PercentComplete("Slick.Editors.PercentComplete"),
        SelectEditor("Slick.Editors.SelectEditor"),
        SelectRelatedEditor("Slick.Editors.SelectRelatedEditor"),
        LongText("Slick.Editors.LongText");

	    private String val;

	    editores(String val) {
	        this.val = val;
	    }

	    public String getVal() {
	        return val;
	    }
	}


	public enum formatos {
		 PercentComplete("Slick.Formatters.PercentComplete"),
	     PercentCompleteBar("Slick.Formatters.PercentCompleteBar"),
	     YesNo("Slick.Formatters.YesNo"),
	     Checkmark("Slick.Formatters.Checkmark"),
		 DeleteButton("Slick.Formatters.DeleteButton");

	    private String val;

	    formatos(String val) {
	        this.val = val;
	    }

	    public String getVal() {
	        return val;
	    }
	}
	public enum validador {
		duracionRequerida("requiredDurationValidator"),
		textoRequerido("requiredFieldValidator");

	    private String val;

	    validador(String val) {
	        this.val = val;
	    }

	    public String getVal() {
	        return val;
	    }
	}
}
