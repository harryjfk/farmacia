<%@include file="../../includeTagLib.jsp" %> 
<%@ taglib uri="stcJdbc" prefix="stcJdbc"%>


<script>
    var idPedido = '${idPedido}' || null;
    var idModulo = location.pathname.split('/')[3];
    $('#fechaRegistro').val(new Date().toString(dateFormatJS));
    var fReg = '${fReg}' || null;
    if (fReg)
        $('#fechaRegistro').val(Date.parseExact(fReg, dateFormatJS).toString(dateFormatJS));

    var tblProductos = document.getElementById('tblProductos');

    $('#btnCancelar').click(function (e) {
        e.preventDefault();
        window.location = '<c:url value="/dispensacion/' + idModulo + '/notapedido/procesar" />';
    });

    $('#fechaRegistro').change(function (e) {
        smokeConfirm('Al cambiar la fecha de registro perderá los productos agregados ¿desea continuar?', function (ev) {
            if (ev) {
                e.preventDefault();
            }
        });
    });

    $('#idConcepto').change(function () {
        if (!idPedido) {
            var value = $(this).val();
            $.ajax({
                url: '<c:url value="/ConceptoTipoDocumentoMov/listarIdDocumento" />?idConcepto=' + value,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    var optionTipoDocumento = '<option value="null">-NINGUNO-</option>';
                    for (var i = 0; i <= data.length - 1; ++i) {
                        if (data[i].tipoDocumentoMov.activo == 1) {
                            optionTipoDocumento += '<option value="' + data[i].tipoDocumentoMov.idTipoDocumentoMov + '">' + data[i].tipoDocumentoMov.nombreTipoDocumentoMov + '</option>';
                        }
                    }
                    $('#idTipoDocumentoMov').html(optionTipoDocumento);
                }
            });
        }
    });

    $(document).keyup(function (e) {
        if (e.which === 81 && e.ctrlKey) {//Ctrl + Q
            modalProductos(e);
        }
    });

    $(document).ready(function (e) {
        $('#precio').numeric({decimalPlaces: 8, negative: false});
        $('#cantidad').numeric({decimal: false, negative: false});
        if (idPedido) {
            getDetallePedido();
            $("#referencia").val('${pedido.referencia}');
        }
    });
    _AlmacenSelectFill();
    function _AlmacenSelectFill() {
        $.ajax({
            url: '<c:url value="/GenericAlmacen/listar" />',
            type: "GET",
            dataType: "json",
            success: function (r) {
                var select = $('#txtAlmacenOrigen'),
                        td, option;
                option = new Option("TODOS", -1);
                select.append(option);
                r = r.data || r;
                var idAlmacen = '${pedido.idAlmacenOrigen.idAlmacen}' || -1;
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        option = new Option(td.descripcion, td.idAlmacen);
                        if (idAlmacen == td.idAlmacen) {
                            option.selected = true;
                            $('#idAlmacenOrigen').val(td.idAlmacen);
                        }
                        select.append(option);
                    }
                }
            },
            failure: function (r) {
            }
        });
    }
    $('#txtAlmacenOrigen').change(function (e) {
        $("#idAlmacenOrigen").val($(this).val());
    });

    $('#buscaProducto').keyup(function (e) {
        e.preventDefault();
        if (e.which === 13) {
            $('#btnBuscar').click();
        }
    });

    $('#btnBuscar').click(function (e) {
        e.preventDefault();

        if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
            $(tblProductos).dataTable().fnDestroy();
        }
        var url = '<c:url value="/Producto/productosSalidaJSON" />';
        $(tblProductos).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'criterio', "value": $('#criterio').val()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacenOrigen').val()});
            },
            "sAjaxSource": url,
            "bServerSide": true,
            "bProcessing": true,
            "sDom": " <'ro w'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idProducto", sWidth: "6%", "bSortable": false, "mRender": function (data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectProduct(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "codigoSismed", sWidth: "12%", "bSortable": false},
                {mData: "producto", sWidth: "55%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "15%", "bSortable": false},
                {mData: "cantidad", sWidth: "12%", "bSortable": false}
            ]
        });
    });

    function modalProductos(e) {
        e.preventDefault();
        var dataResponse = new Object();
        dataResponse.mensajesRepuesta = new Array();

        if ($('#idAlmacenOrigen').val().length == 0) {
            alertify.error('Debe seleccionar un almacén para cargar sus productos.');
            return;
        }

        if ($('#idConcepto').val() == '-1') {
            alertify.error('Debe seleccionar un concepto para establecer precios.');
            return;
        }

        if (Date.parseExact($('#fechaRegistro').val(), dateFormatJS) == null) {
            alertify.error('Debe colocar una fecha de registro válida para establecer precios.');
            return;
        }

        cleanform('#modalProducto');
        $('#modalProducto').modal('show');
        $('#btnBuscar').click();
    }

    function selectProduct(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(2)').text());
        $('#idProducto').val(idProducto);
    }

    function llenarDetalle(dataResponse) {
        $('#detallePedido tbody').html('');
        var fila = '';
        for (var i = 0; i <= dataResponse.length - 1; ++i) {
            fila += '<tr>';
            fila += '<td>' + (i + 1) + '</td>';
            fila += '<td>' + dataResponse[i].idProducto.descripcion + '</td>';
            fila += '<td>' + dataResponse[i].cantidad + '</td>';
            fila += '<td>' + dataResponse[i].precio + '</td>';
            fila += '<td>' + dataResponse[i].total + '</td>';
            fila += '<td>' + dataResponse[i].lote + '</td>';
            fila += '<td>' + new Date(dataResponse[i].fechaVencimiento).toString(dateFormatJS) + '</td>';
            fila += '<td><a href="#" class="separator-icon-td" onclick="quitarDetalle(' + dataResponse[i].idProducto.idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
            fila += '</tr>';
        }
        $('#detallePedido tbody').html(fila);
    }

    function getDetallePedido() {
        $.ajax({
            url: '<c:url value="/dispensacion/' + idModulo + '/notapedido/cargarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    function quitarDetalle(idProducto, e) {
        e.preventDefault();
        $.ajax({
            url: '<c:url value="/dispensacion/' + idModulo + '/notapedido/quitarDetalle" />/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    function borrarDetalle() {
        $.ajax({
            url: '<c:url value="/dispensacion/' + idModulo + '/notapedido/borrarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    $('[data-id="btnAgregarDetalle"]').click(function (e) {
        e.preventDefault();

        var salidaDetalle = {
            idProducto: $('#idProducto').val(),
            cantidad: $('#cantidad').val()
        };

        var urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').val() + '&idConcepto=' + $('#idConcepto').val() + '&fechaRegistro=' + Date.parseExact($('#fechaRegistro').val(), dateFormatJS).getTime();

        $.ajax({
            url: '<c:url value="/dispensacion/' + idModulo + '/notapedido/agregarDetalle" />' + urlExtend,
            data: JSON.stringify(salidaDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                if (dataResponse.estado) {
                    getDetallePedido();
                    $('#modalProducto').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#divMessage');
                }
            }
        });
    });

    $('#btnGuardar').click(function (e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        var fechaRegistro = Date.parseExact($('#fechaRegistro').val(), dateFormatJS);
        var fechaRegistroTime = null;

        if (fechaRegistro === null) {
            dataResponse.mensajesRepuesta.push('Fecha de Registro inválida.');
            dataResponse.estado = false;
        } else {
            fechaRegistroTime = fechaRegistro.getTime();
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var notaPedido;
        if (idPedido) {
            notaPedido = {
                idMovimiento: idPedido,
                idAlmacenOrigen: {
                    idAlmacen: $('#idAlmacenOrigen').val()
                },
                concepto: {
                    idConcepto: $('#idConcepto').val()
                },
                referencia: $('#referencia').val()
            };
        } else {
            notaPedido = {
                idAlmacenOrigen: {
                    idAlmacen: $('#idAlmacenOrigen').val()
                },
                concepto: {
                    idConcepto: $('#idConcepto').val()
                },
                idTipoDocumentoMov: {
                    idTipoDocumentoMov: $('#idTipoDocumentoMov').valNull()
                },
                numeroDocumentoMov: $('#numeroDocumentoMov').val(),
                referencia: $('#referencia').val()
            };
        }

        var url = '<c:url value="/dispensacion/' + idModulo + '/notapedido/registrar" />';
        if (idPedido) {
            url = '<c:url value="/dispensacion/' + idModulo + '/notapedido/modificar" />';
        }
        $.ajax({
            url: url,
            data: JSON.stringify(notaPedido),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                functionResponse(dataResponse, function () {
                    $('#btnCancelar').click();
                });
            }
        });
    });
</script>