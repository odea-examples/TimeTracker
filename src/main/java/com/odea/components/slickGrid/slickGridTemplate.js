


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
  if (value == null || value == undefined || !value.length || !value.match(regexTiempo) ) {
    return {valid: false, msg: "Invalid duration"};
  }
  else {
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





//Now define your buttonFormatter function
  function queueAndExecuteCommand(item, column, editCommand) {
    editCommand.execute();
    if (JSON.stringify(column.name, null, 2) == JSON.stringify("Proyecto", null, 2)){
    	alert("Ingrese una Actividad o no se guardara su cambio.");
//    	alert(JSON.stringify(editCommand, null, 2));
//    	grid.gotoCell(editCommand.row, editCommand.cell+1);
    	item.actividad = "";
    	grid.updateRow(editCommand.row);
    }
    else if (JSON.stringify(column.name, null, 2) == JSON.stringify("Sistema Externo", null, 2)&& (editCommand.serializedValue==" Ninguno") ){
    	item.ticketExt="";
    	alert("El ticket externo ha sido borrado junto con su sistema externo.")
    	Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'modificar':JSON.stringify(item, null, 2)}});
    	}
    else if (JSON.stringify(column.name, null, 2) == JSON.stringify("Sistema Externo", null, 2) && (item.ticketExt == null || item.ticketExt=="" ) ){
    	alert("Ingrese un ticket externo o no se validara su cambio")
//    	alert(JSON.stringify(item, null, 2));
    	grid.updateRow(editCommand.row);
    	}
    else if (JSON.stringify(column.name, null, 2) == JSON.stringify("Ticket Externo", null, 2) && item.sistExt == null){
    	alert("Ingrese un sistema externo o no se validara su cambio")
//    	alert(JSON.stringify(item, null, 2));
    	grid.updateRow(editCommand.row);
    	}
    else if (JSON.stringify(column.name, null, 2) == JSON.stringify("Ticket Externo", null, 2) && (item.ticketExt == null || item.ticketExt=="")){
    	alert("Ingrese algun ticket acompañado a este sistema externo, o eliga la opcion Ninguno en los sistemas externos para eliminar el sistema y el ticket.")
//    	alert(JSON.stringify(item, null, 2));
    	grid.updateRow(editCommand.row);
    	}
    else{
//    	alert(JSON.stringify(column.name, null, 2))
//    	alert(JSON.stringify(item, null, 2));
//    	alert(item.ticketExt == null)
//        var entrada = document.getElementById("${selector}");
//        entrada.value=item;
    	Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'modificar':JSON.stringify(item, null, 2)}});
//    	alert(JSON.stringify(item, null, 2));    	
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
//	  alert(JSON.stringify(objeto, null, 2));
	  if (grid.getColumns()[args.cell].name=='Delete' && objeto!=undefined && confirm(' \u00BFEst\xE1 seguro de que desea borrar la entrada? ')){
	    objeto = dataView.getItem(args.row);
        dataView.deleteItem(objeto.id);
	    Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'borrar':JSON.stringify(objeto.id, null, 2)}});
	    grid.invalidate();
	  }
	});

  // wire up model events to drive the grid
  dataView.onRowCountChanged.subscribe(function (e, args) {
    grid.updateRowCount();
    grid.render();
  });
// parece que todo lo comentado desde aca no hace falta, pero igual lo dejo por si acaso a ver si hay que volverlo a agregar.
//  dataView.onRowsChanged.subscribe(function (e, args) {
//    grid.invalidateRows(args.rows);
//    grid.render();
//  });
//
//  dataView.onPagingInfoChanged.subscribe(function (e, pagingInfo) {
//    var isLastPage = pagingInfo.pageNum == pagingInfo.totalPages - 1;
//    var enableAddRow = false;
//    var options = grid.getOptions();
//
//    if (options.enableAddRow != enableAddRow) {
//      grid.setOptions({enableAddRow: enableAddRow});
//    }
//  });


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


  // initialize the model after all the events have been hooked up
  dataView.beginUpdate();
  dataView.setItems(data);
  dataView.endUpdate();

  // if you don't want the items that are not visible (due to being filtered out
  // or being on a different page) to stay selected, pass 'false' to the second arg
  dataView.syncGridSelection(grid, true);
};
  $(document).ready(init(null));
function iniciar(dataSecundaria){
	$(document).ready(init(dataSecundaria));
}
