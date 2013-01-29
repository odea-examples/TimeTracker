


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
var percentCompleteThreshold = 0;
var searchString = "";
var searchString1 = "";
var searchString2 = "";
var searchString3 = "";


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
  if (value == null || value == undefined || !value.length || !value.match(regexTiempo)) {
    return {valid: false, msg: "Invalid duration"};
  }
  else {
    return {valid: true, msg: null};
  }
}



function myFilter(item, args) {

  if (args.searchString != "" && item["ticket"].indexOf(args.searchString) == -1) {
    return false;
  }
  if (args.searchString1 != "" && item["actividad"].indexOf(args.searchString1) == -1) {
    return false;
  }
  if (args.searchString2 != "" && item["proyecto"].indexOf(args.searchString2) == -1) {
    return false;
  }

  return true;
}

function percentCompleteSort(a, b) {
  return a["percentComplete"] - b["percentComplete"];
}

function comparer(a, b) {
  var x = a[sortcol], y = b[sortcol];
  return (x == y ? 0 : (x > y ? 1 : -1));
}

function toggleFilterRow() {
  grid.setTopPanelVisibility(!grid.getOptions().showTopPanel);
}

//Now define your buttonFormatter function


$(".grid-header .ui-icon")
        .addClass("ui-state-default ui-corner-all")
        .mouseover(function (e) {
          $(e.target).addClass("ui-state-hover")
        })
        .mouseout(function (e) {
          $(e.target).removeClass("ui-state-hover")
        });
  function queueAndExecuteCommand(item, column, editCommand) {
    editCommand.execute();
    if (JSON.stringify(column.name, null, 2) == JSON.stringify("Proyecto", null, 2)){
    	alert("ingrese una actividad o no se guardara su cambio.");
//    	alert(JSON.stringify(editCommand, null, 2));
//    	grid.gotoCell(editCommand.row, editCommand.cell+1);
    	item.actividad = "";
    	grid.updateRow(editCommand.row);
    }
    else{
//        var entrada = document.getElementById("${selector}");
//        entrada.value=item;
    	Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'modificar':JSON.stringify(item, null, 2)}});
//    	alert(JSON.stringify(item, null, 2));    	
    }
    
  }

  function undo() {
    var command = commandQueue.pop();
    editado.pop();
    if (command && Slick.GlobalEditorLock.cancelCurrentEdit()) {
      command.undo();
      grid.gotoCell(command.row, command.cell, false);
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


  // move the filter panel defined in a hidden div into grid top panel
  $("#inlineFilterPanel")
      .appendTo(grid.getTopPanel())
      .show();

  grid.onCellChange.subscribe(function (e, args) {
    dataView.updateItem(args.item.id, args.item);
  });

  grid.onAddNewRow.subscribe(function (e, args) {
    var item = {"num": data.length, "id": "new_" + (Math.round(Math.random() * 10000)), "title": "New task", "duration": "1 day", "percentComplete": 0, "start": "01/01/2009", "finish": "01/01/2009", "effortDriven": false};
    $.extend(item, args.item);
    dataView.addItem(item);
  });
  

  grid.onKeyDown.subscribe(function (e) {
    // select all rows on ctrl-a
    if (e.which != 65 || !e.ctrlKey) {
      return false;
    }

    var rows = [];
    for (var i = 0; i < dataView.getLength(); i++) {
      rows.push(i);
    }

    grid.setSelectedRows(rows);
    e.preventDefault();
  });
  grid.onClick.subscribe(function(e, args) {
	  if (grid.getColumns()[args.cell].name=='Delete' && confirm('¿Seguro desea borrar?')){
	    objeto = dataView.getItem(args.row);
        dataView.deleteItem(objeto.id);
	    Wicket.Ajax.ajax({"u":"${url}","c":"${gridId}","ep":{'borrar':JSON.stringify(objeto.id, null, 2)}});
	    grid.invalidate();
	  }
	});

  grid.onSort.subscribe(function (e, args) {
    sortdir = args.sortAsc ? 1 : -1;
    sortcol = args.sortCol.field;

    if ($.browser.msie && $.browser.version <= 8) {
      // using temporary Object.prototype.toString override
      // more limited and does lexicographic sort only by default, but can be much faster

      var percentCompleteValueFn = function () {
        var val = this["percentComplete"];
        if (val < 10) {
          return "00" + val;
        } else if (val < 100) {
          return "0" + val;
        } else {
          return val;
        }
      };

      // use numeric sort of % and lexicographic for everything else
      dataView.fastSort((sortcol == "percentComplete") ? percentCompleteValueFn : sortcol, args.sortAsc);
    } else {
      // using native sort with comparer
      // preferred method but can be very slow in IE with huge datasets
      dataView.sort(comparer, args.sortAsc);
    }
  });

  // wire up model events to drive the grid
  dataView.onRowCountChanged.subscribe(function (e, args) {
    grid.updateRowCount();
    grid.render();
  });

  dataView.onRowsChanged.subscribe(function (e, args) {
    grid.invalidateRows(args.rows);
    grid.render();
  });

  dataView.onPagingInfoChanged.subscribe(function (e, pagingInfo) {
    var isLastPage = pagingInfo.pageNum == pagingInfo.totalPages - 1;
    var enableAddRow = isLastPage || pagingInfo.pageSize == 0;
    var options = grid.getOptions();

    if (options.enableAddRow != enableAddRow) {
      grid.setOptions({enableAddRow: enableAddRow});
    }
  });


  var h_runfilters = null;


  // wire up the search textbox to apply the filter to the model
  $("#txtSearch,#txtSearch2").keyup(function (e) {
    Slick.GlobalEditorLock.cancelCurrentEdit();

    // clear on Esc
    if (e.which == 27) {
      this.value = "";
    }

    searchString = this.value;
    updateFilter();
  });
  $("#txtSearch3,#txtSearch6").keyup(function (e) {
    Slick.GlobalEditorLock.cancelCurrentEdit();

    // clear on Esc
    if (e.which == 27) {
      this.value = "";
    }

    searchString1 = this.value;
    updateFilter();
  });
  $("#txtSearch4,#txtSearch7").keyup(function (e) {
    Slick.GlobalEditorLock.cancelCurrentEdit();

    // clear on Esc
    if (e.which == 27) {
      this.value = "";
    }

    searchString2 = this.value;
    updateFilter();
  });

  function updateFilter() {
    dataView.setFilterArgs({
      percentCompleteThreshold: percentCompleteThreshold,
      searchString: searchString,
      searchString1: searchString1,
      searchString2: searchString2
    });
    dataView.refresh();
  }

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
  dataView.setFilterArgs({
    percentCompleteThreshold: percentCompleteThreshold,
    searchString: searchString,
    searchString1: searchString1,
    searchString2: searchString2
  });
  dataView.setFilter(myFilter);
  dataView.endUpdate();

  // if you don't want the items that are not visible (due to being filtered out
  // or being on a different page) to stay selected, pass 'false' to the second arg
  dataView.syncGridSelection(grid, true);
};
  $(document).ready(init(null));
function iniciar(dataSecundaria){
	$(document).ready(init(dataSecundaria));
}
