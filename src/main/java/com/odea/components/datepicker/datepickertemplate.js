var data = getRemote();

var datepicker = $("${selector}").datepicker({ dateFormat:"dd/mm/yy", beforeShowDay:highlightState});

function getRemote() {
    var jsonString = $.ajax({
        type: "POST",
      //  data:{ month: month },
        dataType: "json",
        url: "${url}",
        async: false
    }).responseText;

    return  $.parseJSON(jsonString);
}

function highlightState(date) {
    // verde light #90ee90
    // light blue #add8e6

    var curr_date = date.getDate();
    var curr_month = date.getMonth() + 1; //Months are zero based
    var curr_year = date.getFullYear();
    var strDate = curr_date + "/" + curr_month + "/" + curr_year;

    for(var i in data.horasDia){
        if(data.horasDia[i].dia == strDate){
            return [true, data.horasDia[i].horasCargadas >= data.dedicacion ? '.completo' : '.parcial'];
        }
    }

    return [true, "vacio" ? '' : ''];

}
