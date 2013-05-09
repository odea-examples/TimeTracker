/***
 * Contains basic SlickGrid editors.
 * @module Editors
 * @namespace Slick
 */

(function ($) {
  // register namespace
  $.extend(true, window, {
    "Slick": {
      "Editors": {
        "Text": TextEditor,
        "TextTicketExt": TextTicketExtEditor,
        "Integer": IntegerEditor,
        "Date": DateEditor,
        "YesNoSelect": YesNoSelectEditor,
        "Checkbox": CheckboxEditor,
        "PercentComplete": PercentCompleteEditor,
        "SelectEditor": SelectCellEditor,
        "SelectRelatedEditor":SelectRelatedCellEditor,
        "LongText": LongTextEditor
      }
    }
  });

  function TextEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-text' />")
          .appendTo(args.container)
          .bind("keydown.nav", function (e) {
            if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
              e.stopImmediatePropagation();
            }
          })
          .focus()
          .select();
    };

    this.destroy = function () {
      $input.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.getValue = function () {
      return $input.val();
    };

    this.setValue = function (val) {
      $input.val(val);
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field] || "";
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
        
        
      if (args.column.validator) {
        var validationResults = args.column.validator($input.val());
        if (!validationResults.valid) {
          return validationResults;
        }
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function IntegerEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-text' />");

      $input.bind("keydown.nav", function (e) {
        if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
          e.stopImmediatePropagation();
        }
      });

      $input.appendTo(args.container);
      $input.focus().select();
    };

    this.destroy = function () {
      $input.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return parseInt($input.val(), 10) || 0;
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
      if (isNaN($input.val())) {
        return {
          valid: false,
          msg: "Please enter a valid integer"
        };
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }
  //TODO: MARCAMELO
  function TextTicketExtEditor(args) {
	    var $input;
	    var defaultValue;
	    var scope = this;

	    this.init = function () {
	      $input = $("<INPUT type=text class='editor-text' />")
	          .appendTo(args.container)
	          .bind("keydown.nav", function (e) {
	            if (e.keyCode === $.ui.keyCode.LEFT || e.keyCode === $.ui.keyCode.RIGHT) {
	              e.stopImmediatePropagation();
	            }
	          })
	          .focus()
	          .select();
	    };

	    this.destroy = function () {
	      $input.remove();
	    };

	    this.focus = function () {
	      $input.focus();
	    };

	    this.getValue = function () {
	      return $input.val();
	    };

	    this.setValue = function (val) {
	      $input.val(val);
	    };

	    this.loadValue = function (item) {
	      defaultValue = item[args.column.field] || "";
	      $input.val(defaultValue);
	      $input[0].defaultValue = defaultValue;
	      $input.select();
	    };

	    this.serializeValue = function () {
	      return $input.val();
	    };

	    this.applyValue = function (item, state) {
	      item[args.column.field] = state;
	    };

	    this.isValueChanged = function () {
	      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
	    };

	    this.validate = function () {
	    	//actividad no tiene que estar vacio
	    	var valor= args.item.actividad;
	        if(valor == null || valor == undefined || !valor.length){
	        	return {valid: false, msg: "This is a required field"};
	    	}
	    	//si ambos son nulls acepta
	      if((value == null || value == undefined || !value.length) && (($input.val() == null || $input.val() == undefined || !$input.val().length))){
	    	  return {valid: true, msg: null};
	      }
	      var value= args.item.sistExt;
	      //busca validadores exteriores
	      if (args.column.validator) {
	        var validationResults = args.column.validator($input.val());
	        if (!validationResults.valid) {
	          return validationResults;
	        }
	      }
	      else{
	    	  // si el otro no es null pero este si(porque no logro pasar el anterior -.-) rechaza
	          if(value == null || value == undefined || !value.length){
	    		  return {valid: false, msg: "This is a required field"};
	    	  }
	    	  else {
	    		  //acepta si no logro nada mas
	    	    return {valid: true, msg: null};
	    	  }
	    	  
	      }
	      //por si se queja

	      return {
	        valid: true,
	        msg: null
	      };
	    };

	    this.init();
	  }

  function DateEditor(args) {
    var $input;
    var defaultValue;
    var scope = this;
    var calendarOpen = false;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-text' />");
      $input.appendTo(args.container);
      $input.focus().select();
      $input.datepicker({
        showOn: "button",
        buttonImageOnly: false,
        
		dateFormat:"dd/mm/yy",
        beforeShow: function () {
          calendarOpen = true
        },
        onClose: function () {
          calendarOpen = false
        }
      });
      $input.width($input.width() - 18);
    };

    this.destroy = function () {
      $.datepicker.dpDiv.stop(true, true);
      $input.datepicker("hide");
      $input.datepicker("destroy");
      $input.remove();
    };

    this.show = function () {
      if (calendarOpen) {
        $.datepicker.dpDiv.stop(true, true).show();
      }
    };

    this.hide = function () {
      if (calendarOpen) {
        $.datepicker.dpDiv.stop(true, true).hide();
      }
    };

    this.position = function (position) {
      if (!calendarOpen) {
        return;
      }
      $.datepicker.dpDiv
          .css("top", position.top + 30)
          .css("left", position.left);
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      $input.val(defaultValue);
      $input[0].defaultValue = defaultValue;
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };
    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
    	var regexFecha = new RegExp(/^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/);
    	if(args.column.validator && regex($input.val(),regexFecha)){
    		return args.column.validator($input.val());	
    	}
    	else{
        	return {
        		valid: false,
        		msg: "This is a required field"
        	};
    	}
    	return {
    		valid: true,
    		msg: null
    	};
    };

    this.init();
  }
  function regex(value,matcher){
	  return value.match(matcher)
  }
  //TODO marcamelo
  function SelectCellEditor(args) {
      var $select;
      var defaultValue;
      var scope = this;
      var proyectoDeVerdad;

      this.init = function() {
//    	  alert(args.item.ticket);
//    	  alert(JSON.stringify(args, null, 2));
//    	  alert(JSON.stringify(args.item.proyecto, null, 2));
          if(args.column.options){
            opt_values = args.column.options.split(',');
          }else{
            opt_values ="yes,no".split(',');
          }
          option_str = ""
          for( i in opt_values ){
            v = opt_values[i];
//            alert(JSON.stringify(v, null, 2));
            proyecto_seleccionado = JSON.stringify(args.item.proyecto, null, 2);
            proyecto_seleccionado = proyecto_seleccionado.substring(1);
            proyecto_seleccionado = "\" "+proyecto_seleccionado
//            alert(JSON.stringify(v, null, 2)==proyecto_seleccionado);
            if(JSON.stringify(v, null, 2)==proyecto_seleccionado){
            	option_str += "<OPTION selected='selected' value='"+v+"'>"+v+"</OPTION>";
            	proyectoDeVerdad = proyecto_seleccionado;
            	//selected='selected'
            }
            else{            	
            	option_str += "<OPTION value='"+v+"'>"+v+"</OPTION>";
            }
//            alert(v==args.item.proyecto || JSON.stringify(args.item.proyecto, null, 2)==" "+JSON.stringify(args.item.proyecto, null, 2))
          }
          $select = $("<SELECT tabIndex='0' class='editor-select' style='width:300px'  >"+ option_str +"</SELECT>");
          $select.appendTo(args.container);
          $select.focus();
      };

      this.destroy = function() {
          $select.remove();
      };

      this.focus = function() {
          $select.focus();
      };

      this.loadValue = function(item) {
    	  defaultValue = item[args.column.field];
//          $select.val(defaultValue);
      };

      this.serializeValue = function() {
          if(args.column.options){
            return $select.val();
          }else{
            return $select.val()
          }
      };

      this.applyValue = function(item,state) {
          item[args.column.field] = state;
      };

      this.isValueChanged = function() {
          return ($select.val() != defaultValue);
      };

      this.validate = function() {
          return {
              valid: true,
              msg: null
          };
      };

      this.init();
  }
  
  function SelectRelatedCellEditor(args) {
      var $select;
      var defaultValue;
      var scope = this;

      this.init = function() {
//    	  alert(args.item.ticket);
    	  //primer regex que saca todo lo que esta entre corchetes
          var regexPros = new RegExp(/\[.*?\]/g);
          //busca el texto total(proyectos y actividades)
          var texto = args.column.options;
          //saca todas las actividades que estaban entre corchetes y separa cada proyecto con un ";" (punto y coma)
          proyectosValues= texto.replace(regexPros,";");
          //separa cada uno de los proyectos utilizando el ";" anterior
          proyectos= proyectosValues.split(';');
          //deja solo la lista de actividades separadas por sus respectivos corchetes(es una lista de lista de actividades)
          actividadesValues= texto.match(regexPros);
          var numProyecto= 0;
          //buscador del numero de proyecto
          for (i in proyectos){
        	  // recorre todos los proyectos para encontrar el correcto, capaz se va a mejorar con un while
        	  //TODO poner while
        	  vale2 = (JSON.stringify(args.item.proyecto.substring(1), null, 2) == (JSON.stringify(proyectos[i], null, 2)));
        	  if (vale2 || proyectos[i] == args.item.proyecto){
        		  numProyecto=i;
        	  }
          }
          //le quita ambos corchetes
          actividadesValues[numProyecto]= actividadesValues[numProyecto].replace('[','')
          actividadesValues[numProyecto]= actividadesValues[numProyecto].replace(']','')
          //separa cada una de las actividades por la coma
          actividades = actividadesValues[numProyecto].split(',');
          //al igual que el de proyectos este se encarga de cargar cada una de las opciones.
          if(actividades){
            opt_values = actividades;
          }else{
            opt_values ="yes,no".split(',');
          }
          option_str = ""
          for( i in opt_values ){
            v = opt_values[i];
      	  actividad_seleccionado = JSON.stringify(args.item.actividad, null, 2);
          actividad_seleccionado = actividad_seleccionado.substring(1);
          actividad_seleccionado = "\" "+actividad_seleccionado
//          alert(JSON.stringify(v, null, 2));
//          alert(actividad_seleccionado);
    	  if(JSON.stringify(v, null, 2)==actividad_seleccionado){
          	option_str += "<OPTION selected='selected' value='"+v+"'>"+v+"</OPTION>";
//          	alert("entra")
          	proyectoDeVerdad = actividad_seleccionado;
          	//selected='selected'
          	}
          else{            	
          	option_str += "<OPTION value='"+v+"'>"+v+"</OPTION>";
          	}
          }
          $select = $("<SELECT tabIndex='0' class='editor-select' style='width:300px'>"+ option_str +"</SELECT>");
          $select.appendTo(args.container);
          $select.focus();
      };

      this.destroy = function() {
          $select.remove();
      };

      this.focus = function() {
          $select.focus();
      };

      this.loadValue = function(item) {
          defaultValue = item[args.column.field];
//          $select.val();
      };

      this.serializeValue = function() {
          if(args.column.options){
            return $select.val();
          }else{
            return ($select.val())
          }
      };

      this.applyValue = function(item,state) {
          item[args.column.field] = state;
      };

      this.isValueChanged = function() {
          return ($select.val() != defaultValue);
      };

      this.validate = function() {
          return {
              valid: true,
              msg: null
          };
      };

      this.init();
  }

  function YesNoSelectEditor(args) {
    var $select;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $select = $("<SELECT tabIndex='0' class='editor-yesno'><OPTION value='yes'>Yes</OPTION><OPTION value='no'>No</OPTION></SELECT>");
      $select.appendTo(args.container);
      $select.focus();
    };

    this.destroy = function () {
      $select.remove();
    };

    this.focus = function () {
      $select.focus();
    };

    this.loadValue = function (item) {
      $select.val((defaultValue = item[args.column.field]) ? "yes" : "no");
      $select.select();
    };

    this.serializeValue = function () {
      return ($select.val() == "yes");
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return ($select.val() != defaultValue);
    };

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function CheckboxEditor(args) {
    var $select;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $select = $("<INPUT type=checkbox value='true' class='editor-checkbox' hideFocus>");
      $select.appendTo(args.container);
      $select.focus();
    };

    this.destroy = function () {
      $select.remove();
    };

    this.focus = function () {
      $select.focus();
    };

    this.loadValue = function (item) {
      defaultValue = item[args.column.field];
      if (defaultValue) {
        $select.attr("checked", "checked");
      } else {
        $select.removeAttr("checked");
      }
    };

    this.serializeValue = function () {
      return $select.attr("checked");
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return ($select.attr("checked") != defaultValue);
    };

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  function PercentCompleteEditor(args) {
    var $input, $picker;
    var defaultValue;
    var scope = this;

    this.init = function () {
      $input = $("<INPUT type=text class='editor-percentcomplete' />");
      $input.width($(args.container).innerWidth() - 25);
      $input.appendTo(args.container);

      $picker = $("<div class='editor-percentcomplete-picker' />").appendTo(args.container);
      $picker.append("<div class='editor-percentcomplete-helper'><div class='editor-percentcomplete-wrapper'><div class='editor-percentcomplete-slider' /><div class='editor-percentcomplete-buttons' /></div></div>");

      $picker.find(".editor-percentcomplete-buttons").append("<button val=0>Not started</button><br/><button val=50>In Progress</button><br/><button val=100>Complete</button>");

      $input.focus().select();

      $picker.find(".editor-percentcomplete-slider").slider({
        orientation: "vertical",
        range: "min",
        value: defaultValue,
        slide: function (event, ui) {
          $input.val(ui.value)
        }
      });

      $picker.find(".editor-percentcomplete-buttons button").bind("click", function (e) {
        $input.val($(this).attr("val"));
        $picker.find(".editor-percentcomplete-slider").slider("value", $(this).attr("val"));
      })
    };

    this.destroy = function () {
      $input.remove();
      $picker.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      $input.val(defaultValue = item[args.column.field]);
      $input.select();
    };

    this.serializeValue = function () {
      return parseInt($input.val(), 10) || 0;
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ((parseInt($input.val(), 10) || 0) != defaultValue);
    };

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor= args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
      if (isNaN(parseInt($input.val(), 10))) {
        return {
          valid: false,
          msg: "Please enter a valid positive number"
        };
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }

  /*
   * An example of a "detached" editor.
   * The UI is added onto document BODY and .position(), .show() and .hide() are implemented.
   * KeyDown events are also handled to provide handling for Tab, Shift-Tab, Esc and Ctrl-Enter.
   */
  function LongTextEditor(args) {
    var $input, $wrapper;
    var defaultValue;
    var scope = this;

    this.init = function () {
      var $container = $("body");

      $wrapper = $("<DIV style='z-index:10000;position:absolute;background:white;padding:5px;border:3px solid gray; -moz-border-radius:10px; border-radius:10px;'/>")
          .appendTo($container);

      $input = $("<TEXTAREA hidefocus rows=5 maxlength=10000 style='backround:white;width:250px;height:80px;border:0;outline:0'>")
          .appendTo($wrapper);

      $("<DIV style='text-align:right'><BUTTON>Save</BUTTON><BUTTON>Cancel</BUTTON></DIV>")
          .appendTo($wrapper);

      $wrapper.find("button:first").bind("click", this.save);
      $wrapper.find("button:last").bind("click", this.cancel);
      $input.bind("keydown", this.handleKeyDown);

      scope.position(args.position);
      $input.focus().select();
    };

    this.handleKeyDown = function (e) {
      if (e.which == $.ui.keyCode.ENTER && e.ctrlKey) {
        scope.save();
      } else if (e.which == $.ui.keyCode.ESCAPE) {
        e.preventDefault();
        scope.cancel();
      } else if (e.which == $.ui.keyCode.TAB && e.shiftKey) {
        e.preventDefault();
        args.grid.navigatePrev();
      } else if (e.which == $.ui.keyCode.TAB) {
        e.preventDefault();
        args.grid.navigateNext();
      }
    };

    this.save = function () {
      args.commitChanges();
    };
    
    this.cancel = function () {
      $input.val(defaultValue);
      args.cancelChanges();
    };

    this.hide = function () {
      $wrapper.hide();
    };

    this.show = function () {
      $wrapper.show();
    };

    this.position = function (position) {
      $wrapper
          .css("top", position.top - 5)
          .css("left", position.left - 5)
    };

    this.destroy = function () {
      $wrapper.remove();
    };

    this.focus = function () {
      $input.focus();
    };

    this.loadValue = function (item) {
      $input.val(defaultValue = item[args.column.field]);
      $input.select();
    };

    this.serializeValue = function () {
      return $input.val();
    };

    this.applyValue = function (item, state) {
      item[args.column.field] = state;
    };

    this.isValueChanged = function () {
      return (!($input.val() == "" && defaultValue == null)) && ($input.val() != defaultValue);
    };
    

    this.validate = function () {
    	//actividad no tiene que estar vacio
    	var valor = args.item.actividad;
        if(valor == null || valor == undefined || !valor.length){
        	return {valid: false, msg: "This is a required field"};
    	}
      if (args.column.validator) {
        var validationResults = args.column.validator($input.val());
        if (!validationResults.valid) {
          return validationResults;
        }
      }

      return {
        valid: true,
        msg: null
      };
    };

    this.init();
  }
})(jQuery);
