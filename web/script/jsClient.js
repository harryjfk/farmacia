function elementForm() {
    crearCalendar();
    fileInputLoadPage();

    $('.modal').on('shown.bs.modal', function(e) {
        var txtsModal = $(this).find('input[type="text"]');
        if (txtsModal.length > 0) {
            $(txtsModal[0]).focus();
        }
    });
}

function crearCalendar(selector) {
    if (typeof selector === 'undefined') {
        selector = '[data-field-date]';
    }

    $(selector).inputmask("99/99/9999", {placeholder: "__/__/____"});
    $(selector).datepicker({
        dateFormat: 'dd/mm/yy',
        monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
            'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
        monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
            'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
        dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves', 'Viernes', 'S&aacute;bado'],
        dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b'],
        dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;'],
        showOn: ""
    });
}

function sendFormByKeyEnterForSmoke(selectorForm, f) {
    $(selectorForm).keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            e.stopPropagation();
        }
    });

    $(selectorForm).keyup(function(e) {
        if (e.which === 13) {
            f();
            e.preventDefault();
            e.stopPropagation();
        }
    });
}

function fileInputLoadPage() {
    var fileInputs = $('.fileinput .form-control');

    for (var i = 0; i <= fileInputs.length - 1; ++i) {

        var fileInputName = $(fileInputs[i]).find('.fileinput-filename');

        if (fileInputName !== null) {
            var sizeFileInput = $(fileInputs[i]).width();
            $(fileInputName).width(sizeFileInput - 70);
        }
    }
}

function mostrarCalendar(inputText) {
    $('#' + inputText.toString()).datepicker("show");
}

function limpiarSelect(select) {
    var option = '<option value="-1">-SELECCIONE-</option>';
    $(select).html(option);
}

function limpiarSelectTodos(select) {
    var option = '<option value="-1">-TODOS-</option>';
    $(select).html(option);
}

function llenarSelect(select, action, Data, f) {
    //var Data = {"id":{"":""},"value":"","text":""};

    if (typeof f === 'undefined') {
        f = function() {
        };
    }

    $.ajax({
        url: action,
        type: 'GET',
        dataType: 'json',
        success: function(jsonData, status, metaData) {

            var option = '<option value="-1">-SELECCIONE-</option>';

            for (var i = 0; i <= jsonData.length - 1; ++i) {
                if (Data != null) {
                    option += '<option value="' + jsonData[i][Data['value']] + '">' + jsonData[i][Data['text']] + '</option>';
                } else {
                    option += '<option value="' + jsonData[i] + '">' + jsonData[i] + '</option>';
                }
            }

            $(select).html(option);

            f();
        }
    });
}

function llenarSelectOptional(select, action, Data, valueOptional, f) {
    //var Data = {"id":{"":""},"value":"","text":""};

    if (typeof f === 'undefined') {
        f = function() {
        };
    }

    $.ajax({
        url: action,
        type: 'GET',
        dataType: 'json',
        data: Data['id'],
        success: function(jsonData, status, metaData) {

            var option = '<option value="' + valueOptional + '">-TODOS-</option>';

            for (var i = 0; i <= jsonData.length - 1; ++i) {
                option += '<option value="' + jsonData[i][Data['value']] + '">' + jsonData[i][Data['text']] + '</option>';
            }

            $(select).html(option);

            f();
        }
    });
}

function functionResponse(dataResponse, func) {
    if (dataResponse.estado === true) {
        successResponse(dataResponse, func);
    } else {
        errorResponse(dataResponse);
    }
}

function successResponse(dataResponse, f) {
    if (typeof f === "undefined") {
        f = function() {
            location.replace(dataResponse.paginaRedireccion);
        };
    }

    smoke.alert(dataResponse.mensajesRepuesta[0], false, f);

    $('.smoke-alert').find('button').focus();
}

function smokeConfirm(text, f) {
    smoke.confirm(text, f);
    $('.smoke-confirm').find('button:eq(1)').focus();
}

function smokeAlert(text, f) {
    if (typeof f === "undefined") {
        f = function() {
        };
    }    

    smoke.alert(text, false, f);
    $('.smoke-alert').find('button').focus();
}

function errorResponse(dataResponse) {
    var mensaje = '';

    if (dataResponse.mensajesRepuesta.length === 1) {
        mensaje = dataResponse.mensajesRepuesta[0];
    } else {
        for (var i = 0; i <= dataResponse.mensajesRepuesta.length - 1; ++i) {
            mensaje += '-' + dataResponse.mensajesRepuesta[i] + '<br />';
        }
    }

    smokeAlert(mensaje);

    $('.smoke-alert').find('button').focus();
}
//Agregar funcion para obtener valor de un checkbox
$.fn.checkboxVal = function() {
    var $obj = $(this);
    var val = $obj.val();
    var type = $obj.attr('type');
    if (type && type === 'checkbox') {
        var unVal = $obj.attr('data-unchecked');
        if (typeof unVal === 'undefined')
            unVal = '';
        return $obj.prop('checked') ? val : unVal;
    } else {
        return val;
    }
};

//Agregar funcion para obtener valor null de un hidden y select
$.fn.valNull = function() {
    var $obj = $(this);
    var val = $obj.val();
    var tagName = $obj.prop("tagName").toLowerCase();
    var type = $obj.attr('type');

    if ((tagName === 'input' && type === 'hidden')
            || tagName === 'select') {
        return  (val === 'null') ? null : val;
    } else {
        return val;
    }
};

//Configurar Datatables
$.extend(true, $.fn.dataTable.defaults, {
    "oLanguage": {
        "sLengthMenu": "_MENU_",
        "sZeroRecords": "No hay coincidencias",
        "sInfo": "Mostrando _START_ - _END_ de _TOTAL_ registros",
        "sInfoEmpty": "",
        "sEmptyTable": "No hay registros",
        "sInfoFiltered": "(_MAX_ registros totales)",
        "sProcessing": "Procesando...",
        "oPaginate": {
            "sFirst": "Primero",
            "sLast": "&#218;ltimo",
            "sNext": "Siguiente",
            "sPrevious": "Atr&#225;s"
        }
    }
});

function jsonToDivError(json, divSelector, pathContext) {

    if (typeof pathContext === "undefined") {
        pathContext = '';
    }

    $(divSelector).html('');
    $(divSelector).removeAttr('style');
    var htmlDiv;
    if (json.estado) {
        htmlDiv = '<div class="alert alert-success" style="margin-top: 15px;margin-bottom: 0px;"> <img src="' + pathContext + 'img/success.gif" width="15" /> ' + json.mensajesRepuesta[0] + '</div>';
        $(divSelector).html(htmlDiv);
        setTimeout(function() {
            $(divSelector).fadeOut();
        }, 1500);
        return true;
    } else {
        htmlDiv = '<div class="mensajeErrorPopUp">';

        htmlDiv = htmlDiv + '<h4>Errores</h4>';
        htmlDiv = htmlDiv + '<ul>';

        for (var i = 0; i <= json.mensajesRepuesta.length - 1; ++i) {
            htmlDiv = htmlDiv + '<li>' + json.mensajesRepuesta[i] + '</li>';
        }

        htmlDiv = htmlDiv + '</ul>';
        htmlDiv = htmlDiv + '</div>';

        $(divSelector).html(htmlDiv);
        return false;
    }
}

function cleanform(selector) {
    $(selector).find('input[type="text"],textarea,input[type="hidden"]').val('');
    $(selector).find('.f_error').removeClass('f_error');
    $(selector).find('input[type="checkbox"]').prop('checked', false);
    $('#divMessage,.divMessage').html('');
}

function ValueNullText(value) {
    if (value == null) {
        return 'null';
    } else {
        return value;
    }
}

function validateElement(selector) {
    var elementFrm = $(selector);
    var dataResponse = new Object();
    dataResponse.estado = true;
    dataResponse.mensajesRepuesta = new Array();

    for (var i = 0; i <= elementFrm.length - 1; ++i) {
        var isError = false;
        var tagNameElement = $(elementFrm[i]).prop("tagName").toLowerCase();

        if (tagNameElement === 'input') {
            if ($(elementFrm[i]).val().replace(/\s/g, '').length === 0) {
                isError = true;
            } else {
                isError = false;
            }
        }

        if (tagNameElement === 'select') {
            if ($(elementFrm[i]).val() === '-1') {
                isError = true;
            } else {
                isError = false;
            }
        }

        if (isError) {
            dataResponse.estado = false;
            break;
        }
    }

    if (dataResponse.estado === false) {
        dataResponse.mensajesRepuesta.push('Complete campos requeridos.');
    }

    return dataResponse;
}

function validateForm(selector) {

    var divsFrm = $(selector).parent();
    var elementFrm = $(selector);

    var dataResponse = new Object();
    dataResponse.estado = true;
    dataResponse.mensajesRepuesta = new Array();

    for (var i = 0; i <= divsFrm.length - 1; ++i) {

        var classTexto = $(divsFrm[i]).attr('class');
        classTexto = classTexto.replace(/ f_error/g, '');

        var isError = false;
        var tagNameElement = $(elementFrm[i]).prop("tagName").toLowerCase();

        if (tagNameElement === 'input') {
            if ($(elementFrm[i]).val().replace(/\s/g, '').length === 0 || $(elementFrm[i]).val() === 'null') {
                isError = true;
            } else {
                isError = false;
            }
        }

        if (tagNameElement === 'select') {
            if ($(elementFrm[i]).val() === '-1' || $(elementFrm[i]).val() == 'null') {
                isError = true;
            } else {
                isError = false;
            }
        }

        if (isError) {
            $(divsFrm[i]).attr('class', classTexto + ' f_error');
            dataResponse.estado = false;
        } else {
            $(divsFrm[i]).attr('class', classTexto);
        }
    }

    if (dataResponse.estado === false) {
        dataResponse.mensajesRepuesta.push('Complete campos requeridos.');
    }

    return dataResponse;
}

function getUrlFromDatatables(prefixUrl, dataTable) {

    var settings = dataTable.dataTable().fnSettings();
    var params = dataTable.oApi._fnAjaxParameters(settings);
    var serverParams = new Array();

    if (settings.aoServerParams.length > 0) {
        //Estamos asumiendo que sólo se usó fnServerParams para llenar parámetros
        var fn = settings.aoServerParams[0]["fn"];
        fn(serverParams);
    }

    var url = prefixUrl + "?";
    
    for (var i = 0; i <= params.length - 1; ++i) {
        //Hacemos la simulación de no paginación("bPaginate: false"), lo cual permite la exportación del pdf y excel
        if(params[i].name == 'iDisplayLength'){
            params[i].value = -1;
        }
        
        url += params[i].name + '=' + params[i].value;
        
        if (i < params.length - 1) {
            url += '&';
        }
    }

    for (var i = 0; i <= serverParams.length - 1; ++i) {

        if (i === 0) {
            url += '&';
        }

        url += serverParams[i].name + '=' + serverParams[i].value;

        if (i < serverParams.length - 1) {
            url += '&';
        }
    }

    return url;
}

$(document).ready(function(){
    $('input[type=text]').keyup(function () {
        this.value = this.value.toUpperCase();
    });
});