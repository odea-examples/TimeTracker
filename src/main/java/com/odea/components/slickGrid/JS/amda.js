  var data = [];
  var grid;
  var columns = [
                 {id: "borrar", name: "Borrar", width: 80, minWidth: 20, maxWidth: 80, cssClass: "cell-effort-driven", field: "borrar",formatter: Slick.Formatters.Deletemark, editor: Slick.Editors.Checkbox},
                 {id: "duration", name: "Duracion", field: "duration", editor: Slick.Editors.Text, validator: requiredFieldValidator},
                 {id: "actividad", name: "Actividad", field: "actividad", width: 120, cssClass: "cell-title", editor: Slick.Editors.Text},
                 {id: "proyecto", name: "Proyecto", field: "proyecto", width: 120, cssClass: "cell-title", editor: Slick.Editors.Text},
                 {id: "start", name: "Fecha", field: "start", minWidth: 60, editor: Slick.Editors.Date},
                 {id: "ticket", name: "Ticket", field: "ticket", width: 120, cssClass: "cell-title", editor: Slick.Editors.Text, sortable: true, validator: requiredFieldValidator},
                 {id: "ticketExt", name: "Ticket Externo", field: "ticketExt", width: 120, cssClass: "cell-title", editor: Slick.Editors.Text, sortable: true, validator: requiredFieldValidator},
                 {id: "sistExt", name: "Sistema Externo", field: "sistExt", width: 120, cssClass: "cell-title", editor: Slick.Editors.Text},
                 {id: "desc", name: "Nota", field: "description", width: 100, editor: Slick.Editors.LongText, validator: requiredFieldValidator},

             ];

  var options = {
		  editable: true,
		  enableAddRow: false,
		  enableCellNavigation: true,
		  asyncEditorLoading: true,
		  forceFitColumns: false,
		  topPanelHeight: 25,
		  autoEdit:false,
		  editCommandHandler: queueAndExecuteCommand
		};
  
  function queueAndExecuteCommand(item, column, editCommand) {
		alert(item);
  }
  
  function requiredFieldValidator(value) {
	  if (value == null || value == undefined || !value.length) {
	    return {valid: false, msg: "This is a required field"};
	  }
	  else {
	    return {valid: true, msg: null};
	  }
	}

  
  $(function () {
	  // prepare the data
	  for (var i = 0; i < 500;i++) {
	    var d = (data[i] = {});

	    d["id"] = (i+1) + "/01/2009";
	      d["borrar"] = ((i % 2) == 0);
	      d["duration"] = i + "hs";
	      d["actividad"] = "vacaciones";
	      d["proyecto"] = "RRHH";
	      d["start"] = (i+1) + "/01/2009";
	      d["ticket"] = "Task " + i;
	      d["ticketExt"] = "Task " + i;
	      d["sistExt"] = "vacaciones";
	      d["description"] = "vacaciones de usuario numero:" + (i*345);

	  }

    grid = new Slick.Grid("${selector}", data, columns, options);
    grid.setSelectionModel(new Slick.RowSelectionModel());
    $("#inlineFilterPanel")
    .appendTo(grid.getTopPanel())
    .show();
    
    
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
  })

