


var commandQueue = [];
var editado = [];
var dataView;
var grid;
var last= [];
var columns = 
	${columns};
var data = [];
var cantidadVeces = 0;
var idBorrada ="";
var ticketCheker= true;
var sistemaChecker= true;
var actividadChecker= true;


var options = {
  editable: true,
//  enableAddRow: false,
  enableCellNavigation: true,
  asyncEditorLoading: true,
  forceFitColumns: false,
  topPanelHeight: 25,
  autoEdit:false,
  editCommandHandler: queueAndExecuteCommand
};

var sortcol = "title";
var sortdir = 1;


function requiredFieldValidator(value) {
  if (value == null || value == undefined || !value.length) {
    return {valid: false, msg: "This is a required field"};
  }
  else {
    return {valid: true, msg: null};
  }
}


function requiredDurationValidator(value) {
	var regexTiempo = (/^([0-9]{1,2}(,[0-9]{1,2}|:[0-5]{1}[0-9]{1}|\b))$/);
	var regexHora = (/^([0-9]{1,2}:[0-5]{1}[0-9]{1})$/)
	var regexDecimal = (/^([0-9]{1,2}(,[0-9]{1,2}|\b))$/)
	
	var horas;
	
	if (value.match(regexHora)) {
		var pos = value.indexOf(":");
		var strHoras = value.substring(0, pos);
		horas = parseFloat(strHoras);
	}
	
	if (value.match(regexDecimal)) {
		horas = parseFloat(value);
	}
	
  if (value == null || value == undefined || !value.length || !value.match(regexTiempo) || ((value.match(regexHora) || value.match(regexDecimal)) && horas >= 24)) {
    return {valid: false, msg: "Invalid duration"};
  } else {
    return {valid: true, msg: null};
  }
}


function ticketExternoValidator(value) {
	var regexVal = (/^([a-z0-9_-]{1,15})$/);
  if (value == null || value == undefined || !value.length || !value.match(regexVal)) {
    return {valid: false, msg: "Invalid value"};
  }
  else {
    return {valid: true, msg: null};
  }
}

function descripcionValidator(value) {
	var regexVal = (/(^(.{0,5000})$)/);
  if (!value.match(regexVal)) {
	  alert("No se puede ingresar una nota mayor a 5000 caracteres.");
    return {valid: false, msg: "Invalid value"};
  }
  else {
    return {valid: true, msg: null};
  }
}

function ticketBugzillaValidator(value) {
	var regexVal = (/^([0-9]{0,5})$/);
  if (!value.match(regexVal)) {
	  return {valid: false, msg: "Invalid value"};
  }
  else {
    return {valid: true, msg: null};
  }
}






  function queueAndExecuteCommand(item, column, editCommand) {
    editCommand.execute();
    if (!sistemaChecker && !actividadChecker){
    	alert("Arregle los errores antes de continuar.");
    }
    else if ((JSON.stringify(column.name, null, 2) == JSON.stringify("Proyecto", null, 2))){
    	alert("Ingrese una Actividad o no se guardara su cambio.");
    	item.actividad = "";
    	grid.updateRow(editCommand.row);
    }
    else if ((JSON.stringify(column.name, null, 2) == JSON.stringify("Sistema Externo", null, 2)&& (editCommand.serializedValue==" Ninguno") )){
    	item.ticketExt="";
    	alert("El ticket externo ha sido borrado junto con su sistema externo.")
    	Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'modificar':JSON.stringify(item, null, 2)}});
    	}
    else if(actividadChecker && sistemaChecker && ((item.sistExt==null || item.sistExt=="Ninguno") && (item.ticketExt==null || item.ticketExt=="") || item.sistExt!="Ninguno" && item.sistExt!=null && item.ticketExt!=null && item.ticketExt!="")){
    	Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'modificar':JSON.stringify(item, null, 2)}});
    }
    else if(item.sistExt!=null || item.ticketExt!=null || item.ticketExt!="" || item.sistExt!="Ninguno"){
    	alert("Por favor complete el sistema externo y ticket externo o, en su defecto, deje el ticket externo vacio, y el sistema externo en ninguno")
    }
    else{
    	alert("Arregle la actividad o el sistema y el ticket externo");
    	grid.updateRow(row);
    }
    
  }


function init(dataSecundaria) {
	if ((dataSecundaria == null || dataSecundaria == undefined || !dataSecundaria.length) && ${data}!=null) {
		data= ${data};
	  }
	  else if(dataSecundaria != "vacio" && !(dataSecundaria == null || dataSecundaria == undefined || !dataSecundaria.length)){
		data= dataSecundaria;
	  }
	  else{
		  data = [];
	  }
  dataView = new Slick.Data.DataView({ inlineFilters: true });
  grid = new Slick.Grid("${selector}", dataView, columns, options);
  grid.setSelectionModel(new Slick.RowSelectionModel());

  var columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);

  grid.onCellChange.subscribe(function (e, args) {
    dataView.updateItem(args.item.id, args.item);
  });
  

  grid.onClick.subscribe(function(e, args) {
	  objeto = dataView.getItem(args.row);
	  if (grid.getColumns()[args.cell].id=='delCol' && objeto!=undefined && confirm(' \u00BFEst\xE1 seguro de que desea borrar la entrada? ')){
		    objeto = dataView.getItem(args.row);
	        dataView.deleteItem(objeto.id);
		    Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'borrar':JSON.stringify(objeto.id, null, 2)}});
		    grid.invalidate();
	  }
	  if (grid.getColumns()[args.cell].id=='editCol' && objeto!=undefined && confirm(' \u00BFEst\xE1 seguro de que desea editar la entrada? ')){
		    objeto = dataView.getItem(args.row);
	        dataView.deleteItem(objeto.id);
		    Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'editar':JSON.stringify(objeto.id, null, 2)}});
		    grid.invalidate();
	  }
	});

  // wire up model events to drive the grid
  dataView.onRowCountChanged.subscribe(function (e, args) {
    grid.updateRowCount();
    grid.render();
  });


  var h_runfilters = null;

  $("#btnSelectRows").click(function () {
    if (!Slick.GlobalEditorLock.commitCurrentEdit()) {
      return;
    }

    var rows = [];
    for (var i = 0; i < 10 && i < dataView.getLength(); i++) {
      rows.push(i);
    }

    grid.setSelectedRows(rows);
  });

  dataView.beginUpdate();
  dataView.setItems(data);
  dataView.endUpdate();

  dataView.syncGridSelection(grid, true);
};
  $(document).ready(init(null));
function iniciar(dataSecundaria){
	$(document).ready(init(dataSecundaria));
}
