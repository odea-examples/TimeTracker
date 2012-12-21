function isNumberCommaKey(evt)
{
	var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode != 58 && charCode != 44 && charCode > 31 && (charCode < 48 || charCode > 57))
    	return false;
 
    return true;
}

