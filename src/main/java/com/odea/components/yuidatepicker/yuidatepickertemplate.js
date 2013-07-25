YAHOO.namespace("example.calendar");

	


initYUI = function() {
/*	var txtBox = document.getElementById("${selector}");
	txtBob.onfocus = function (e) {
		var divContainer = document.getElementById("${calContainer}");
		if(divContainer.style.display != 'block'){
			divContainer.style.display = 'block';
		}else{
			divContainer.style.display = 'none';
		}
	};*/
	

    function handleSelect(type,args,obj) {
        var dates = args[0];
        var date = dates[0];
        var year = date[0], month = date[1], day = date[2];

        
        var txtDate1 = document.getElementById("${selector}");
        txtDate1.value = day + "/" + month + "/" + year;
        Wicket.Ajax.ajax({"u":"${url}","c":"${datePickerId}","ep":{'selectedDate':txtDate1.value}});
        
        //var p = Wicket.Ajax.ajax({"u":"${url}","c":"${datePickerId}","ep":{'updateF':"true"},"async":"false","dt":"json"});
        //getRemote();
    }

    function updateCal() {
        var txtDate1 = document.getElementById("${selector}");

        if (txtDate1.value != "") {
            YAHOO.example.calendar.cal1.select(txtDate1.value);

            var selectedDates = YAHOO.example.calendar.cal1.getSelectedDates();
            if (selectedDates.length > 0) {
                var firstDate = selectedDates[0];
                YAHOO.example.calendar.cal1.cfg.setProperty("pagedate", (firstDate.getMonth()+1) + "/" + firstDate.getFullYear());
                YAHOO.example.calendar.cal1.render();
            } else {
                alert("Cannot select a date before 1/1/2009 or after 12/31/2013");
            }

        }
    }

    // For this example page, stop the Form from being submitted, and update the cal instead
    function handleSubmit(e) {
        updateCal();
        YAHOO.util.Event.preventDefault(e);
    }

    function getRemote() {
        var jsonString = $.ajax({
            type: "POST",
            dataType: "json",
            url: "${url}",
            data: {
                'updateF': 'true'
            },
            async: false
        }).responseText;
        return  $.parseJSON(jsonString);
    }
    
    
    YAHOO.example.calendar.cal1 = new YAHOO.widget.Calendar("cal1","${calContainer}");
    YAHOO.example.calendar.cal1.selectEvent.subscribe(handleSelect, YAHOO.example.calendar.cal1, true);
    YAHOO.example.calendar.cal1.cfg.setProperty("MDY_YEAR_POSITION", 3);
    YAHOO.example.calendar.cal1.cfg.setProperty("MDY_MONTH_POSITION", 2);
    YAHOO.example.calendar.cal1.cfg.setProperty("MDY_DAY_POSITION", 1);

    var data = getRemote();
    var fechaText= JSON.stringify(data.fechaSeleccionada, null, 2);
    
    
    YAHOO.example.calendar.cal1.cfg.setProperty("selected",data.fechaSeleccionada,false);
    YAHOO.example.calendar.cal1.setMonth(data.fecha[0]-1);
    YAHOO.example.calendar.cal1.setYear(data.fecha[1]);
//    alert(JSON.stringify(data.fechaSeleccionada, null, 2)); 
    YAHOO.example.calendar.cal1.addRenderer(data.fechaSeleccionada,customSelectedRenderer);
    YAHOO.example.calendar.cal1.addRenderer(data.fechaSeleccionada,customSelectedRenderer);
    
    var myCustomRenderer5 = function(workingDate, cell) { 
    	YAHOO.util.Dom.addClass(cell, "highlight5"); 
    	return true;
    } 
    
    var myCustomRenderer6 = function(workingDate, cell) { 
    	YAHOO.util.Dom.addClass(cell, "highlight6"); 
    	return true;
    } 
    var customSelectedRenderer = function(workingDate, cell) { 
    	YAHOO.util.Dom.addClass(cell, "selected"); 
    	return true;
    }



    
    for(var i in data.horasDia){
    	var horasHoy = data.horasDia[i].horasCargadas;
    	if(data.dedicacion == -1){
    		YAHOO.example.calendar.cal1.addRenderer(data.horasDia[i].dia,myCustomRenderer5);
    	}else{
    	if (horasHoy < data.dedicacion) {
    		YAHOO.example.calendar.cal1.addRenderer(data.horasDia[i].dia, YAHOO.example.calendar.cal1.renderCellStyleHighlight3);
    		YAHOO.example.calendar.cal1.addRenderer(data.horasDia[i].dia, YAHOO.example.calendar.cal1.renderCellStyleHighlight3);
		} else {
			YAHOO.example.calendar.cal1.addRenderer(data.horasDia[i].dia, YAHOO.example.calendar.cal1.renderCellStyleHighlight4);
			YAHOO.example.calendar.cal1.addRenderer(data.horasDia[i].dia, YAHOO.example.calendar.cal1.renderCellStyleHighlight4);
		}
    	}

    	
    }
    
    for(var j in data.feriados) {
    	YAHOO.example.calendar.cal1.addRenderer(data.feriados[j].fechaFormateada, YAHOO.example.calendar.cal1.renderCellStyleHighlight1);
	}

    //Sabados y domingos
    YAHOO.example.calendar.cal1.addWeekdayRenderer(1, YAHOO.example.calendar.cal1.renderCellStyleHighlight1);
    YAHOO.example.calendar.cal1.addWeekdayRenderer(7, YAHOO.example.calendar.cal1.renderCellStyleHighlight1);

    
    
    YAHOO.example.calendar.cal1.render();
    
    
    YAHOO.util.Event.addListener("update", "click", updateCal);
    YAHOO.util.Event.addListener("dates", "submit", handleSubmit);
}

YAHOO.util.Event.onDOMReady(initYUI);