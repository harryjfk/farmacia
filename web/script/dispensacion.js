
function _ParseTimestamp(timestamp) {
    var date = new Date();
    date.setTime(timestamp);
    var hours = (date.getHours() < '10') ? '0' + date.getHours() : date.getHours();
    var minutes = (date.getMinutes() < '10') ? '0' + date.getMinutes() : date.getMinutes();
    return hours + ':' + minutes;
}

var fieldFormat = {
    time: function (value) {
        return _ParseTimestamp(value);
    },
    date: function (value) {
        if (value) {
            var date = new Date(value);
            var mes = date.getMonth() + 1;
            return date.getDate() + '/' + mes + '/' + date.getFullYear();
        } else {
            return '';
        }

    },
    'undefined': function (value) {
        if (Number(value) == 0) {
            if (value !== null)
                return String(value);
            return '';
        }
        return value;
    },
    sex: function (value) {
        return (value == 0) ? 'M' : 'F';
    },
    money2: function (value) {
        if (!value)
            return 0.00;
        var tmp = value.toString().split('.');
        if (tmp.length == 1) {
            if (value.toString().indexOf(',') > -1)
                return value;
            return value + ".00";
        }
        if (tmp[1].length < 2)
            return value + "0";
        return value;
    },
    money4: function (value) {
        if (!value)
            return 0.0000;
        var tmp = value.toString().split('.');
        if (tmp.length == 1) {
            if (value.toString().indexOf(',') > -1) {
                for (var i = 0; i <= 4 - value.split(',')[1].length; i++) {
                    value += "0";
                }
                return value;
            }
            return value + ".0000";
        }
        if (tmp[1].length < 4) {
            for (var i = 0; i < 4 - tmp[1].length; i++) {
                value += "0";
            }
        }
        return value;
    },
    yesno: function (value) {
        return (value == 1) ? 'SI' : 'NO';
    },
    download: function (value) {
        return '<a href="descargar/' + value + '"><i class="splashy-arrow_large_down" title="Descargar"></a>';
    },
    number: function (value) {
        var n = Number(value);
        return n ? n : 0;
    }
};

function _cleanForm(selector) {
    $(selector).find('input[type="text"],textarea,input[type="hidden"]').val('');
    $(selector).find('input[type="checkbox"]').prop('checked', false);
    $('#divMessage,.divMessage').html('');
}


function _insertData(form, validate, success, failure) {
    var dataToInsert = form.serialize(),
            url = form.attr('action'),
            mySuccess = success || function (r) {
                var type = 'success';
                if (r.hasError) {
                    type = 'error';
                }
                for (var i in r.mssg) {
                    if (r.mssg.hasOwnProperty(i)) {
                        alertify[type](r.mssg[i]);
                    }
                }
            },
            myFailure = failure || function (r) {
                alertify.error(r.mssg[0]);
            };

    if (validate) {
        if (!validate()) {
            return;
        }
    }
    $.ajax({
        url: url,
        data: dataToInsert,
        type: "POST",
        dataType: 'json',
        success: mySuccess,
        failure: myFailure
    });

}


function _getList(options) {
    var tmp = window.location.pathname.split('/'),
            host = window.location.host,
            protocol = window.location.protocol,
            url = protocol + "//" + host + "/" + tmp[1] + '/' + tmp[2] + (options.url || '');

    $.ajax({
        url: url,
        type: options.type || "GET",
        dataType: "json",
        data: options.data || {},
        success: options.success || new Function(),
        failure: options.failure || new Function(),
        error: options.error || new Function()
    });
}

function _TipoDocumentoSelectFill(selector) {
    var options = {
        url: '/GenericTipoDocumento/listar',
        success: function (r, status, ajaxData) {
            var select = $(selector),
                    td;
            for (var key in r.data) {
                if (r.data.hasOwnProperty(key)) {
                    td = r.data[key];
                    select.append(new Option(td.nombreTipoDocumento, td.idTipoDocumento));
                }
            }
        }
    };
    _getList(options);
}

function obtenerDescripcionProducto(producto) {
    return producto.descripcion;
}


function _VendedorSelectFill(selector, idModulo) {
    var options = {
        url: '/' + idModulo + '/vendedor/getVendedores',
        success: function (r, status, ajaxData) {
            var select = $(selector);
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    td = r[key];
                    select.append(new Option(td.nombre, td.idVendedor));
                }
            }
        }
    };
    _getList(options);
}
function _ProductoSelectFill(selector, width) {
    var options = {
        url: '/GenericProducto/listarconstock',
        success: function (r, status, ajaxData) {
            var select = $(selector);
            for (var key in r.data) {
                if (r.data.hasOwnProperty(key)) {
                    var td = r.data[key];
                    var opt = $('<option value="' + td.idProducto + '">' + td.descripcion + '</option>');
                    opt.attr('data-stock', td.stock);
                    opt.attr('data-id', td.idProducto);
                    select.append(opt);
                }
            }
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
            var k = 0;
            $(selector + "_chzn .chzn-drop .chzn-results li").each(function (i, elem) {
                var ih = $(elem).html();
                if (ih != '-- Seleccione el producto correcto --') {
                    var td = r.data[k];
                    $(elem).attr('data-stock', td.stock);
                    $(elem).attr('data-id', td.idProducto);
                    k++;
                }
            });
        }
    };
    _getList(options);
}
function _ProductoRecetaSelectFill(selector, width) {
    var options = {
        url: '/GenericProductoLote/listarTodos',
        success: function (r, status, ajaxData) {
            var select = $(selector);
            for (var key in r.data) {
                if (r.data.hasOwnProperty(key)) {
                    var td = r.data[key];
                    var opt = $('<option value="' + td.producto.idProducto + '">' + td.producto.descripcion + '</option>');
                    opt.attr('data-lote', td.lote.descripcion);
                    opt.attr('data-almacen', td.idAlmacen);
                    opt.attr('data-id', td.producto.idProducto);
                    select.append(opt);
                }
            }
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
            var k = 0;
            $(selector + "_chzn .chzn-drop .chzn-results li").each(function (i, elem) {
                var ih = $(elem).html();
                if (ih != '-- Seleccione el producto correcto --') {
                    var td = r.data[k];
                    opt.attr('data-lote', td.lote.descripcion);
                    opt.attr('data-almacen', td.idAlmacen);
                    opt.attr('data-id', td.producto.idProducto);
                    k++;
                }
            });
        }
    };
    _getList(options);
}
function _ComponenteSelectFill(selector) {
    var idModulo = location.pathname.split('/')[3];
    var select = $(selector);
    select.children().remove();
    var options = {
        url: '/' + idModulo + '/componente/getComponentes',
        success: function (r, status, ajaxData) {
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    td = r[key];
                    select.append(new Option(td.descripcion, td.id));
                }
            }
            select.triggerHandler('Lleno');
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
    };
    _getList(options);
}
function _SubComponenteSelectFill(selector, data) {
    var idModulo = location.pathname.split('/')[3];
    var select = $(selector);
    select.children().remove();
    var options = {
        url: '/' + idModulo + '/subcomponente/getSubComponentes',
        success: function (r, status, ajaxData) {
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    td = r[key];
                    select.append(new Option(td.descripcion, td.id));
                }
            }
            select.triggerHandler('Lleno');
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        },
        data: data
    };
    _getList(options);
}
function _ProcesoSelectFill(selector) {
    var select = $(selector);
    select.children().remove();
    var options = {
        url: '/proceso/getProcesos',
        success: function (r, status, ajaxData) {
            select.append(new Option("-- Seleccione un Proceso", ""));
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    td = r[key];
                    select.append(new Option(td.descripcion, td.id));
                }
            }
            select.triggerHandler('Lleno');
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
    };
    _getList(options);
}
function _PtoVentaSelectFill(selector, idModulo) {
    var options = {
        url: '/' + idModulo + '/ptoventa/getPVentas',
        success: function (r, status, ajaxData) {
            var select = $(selector);
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    td = r[key];
                    select.append(new Option(td.nombre, td.idVendedor));
                }
            }
        }
    };
    _getList(options);
}
