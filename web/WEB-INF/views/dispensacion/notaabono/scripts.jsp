<%@include file="../includes.jsp" %>
<script>
    var perfil = '${perfil}';
    $(document).ready(function () {
        $("#preventafrm").find('input, select').prop('disabled', true);
        $("#preventafrm2").find('input, select, textarea').prop('disabled', true);
        if ($('#btnAgregar').length > 0)
            $('#btnAgregar').attr('id', 'btnGuardarCambios').text('Guardar');
        else
            $('.row.main-button-row').children(':first').append("<button class='btn btn-primary' id='btnGuardarCambios'>Guardar</button>");
        $('#btnGuardarCambios').before('<button id="btnNuevo" class="btn btn-success" style="margin-right: 4px;">Nuevo</button>');

        if ($('#btnPDF').length > 0) {
            var txt = $("#btnPDF").html();
            $('#btnPDF').attr('id', 'btnConsultarVenta').text('Consultar Venta');
            $("#btnConsultarVenta").after("<button class='btn btn-primary' id='btnPDF'>" + txt + "</button>");
        }
        else
            $('.row.main-button-row').children(':first').append("<button class='btn btn-primary' id='btnConsultarVenta'>Consultar Venta</button>");

        $('.row.main-button-row').children(':first').append("<button style='display:none' class='btn btn-danger' id='btnAnular'>Anular</button>");
        $("#btnAnular").css({marginLeft: '3px'});

        $('#mgn-preventa').find('form').submit(function (evt) {
            evt.preventDefault();
        });
        $('.row.main-button-row').children(':first').append("<div class='pull-right alert alert-danger hidden' id='guardar-cambios-msg' style='margin-top: 5px;'>Hay cambios en la venta, haga clic en el bot&oacute;n \'Guardar\' para hacerlos permanentes.</div>");
        var ventafechaRegistro = new Date();
        var hayCambios = false;
        var venta = {
            id: 0,
            nroOperacion: null,
            nroVenta: null,
            ventaProductos: [],
            exonerados: false,
            especificaciones: null,
            substitutos: null,
            modalidad: 0
        };
        $('#myTab a:first').tab('show');
        var idModulo = location.pathname.split('/')[3];
        var consultarModal = new ConsultarModal(idModulo);
        var options = {
            url: '/GenericFormaPago/listar',
            success: function (r, status, ajaxData) {
                var select = $("#formaPago"),
                        td;
                select.append(new Option("", ""));
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        select.append(new Option(td.descripcion, td.id));
                    }
                }
            }
        };
        _getList(options);
        var options1 = {
            url: '/' + idModulo + '/turno/getTurnos',
            success: function (r, status, ajaxData) {
                var select = $("#turno"),
                        td;
                select.append(new Option("", ""));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.descripcion, td.idTurno));
                    }
                }
            }
        };
        _getList(options1);
        var options2 = {
            url: '/GenericProceso/listar',
            success: function (r, status, ajaxData) {
                var select = $("#proceso"),
                        td;
                select.append(new Option("", ""));
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        select.append(new Option(td.descripcion, td.codigo));
                    }
                }
            }
        };
        _getList(options2);
        var options3 = {
            url: '/' + idModulo + '/venta/listarDocumentos',
            type: 'POST',
            success: function (r, status, ajaxData) {
                var select = $("#documento"),
                        td;
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        if (!venta.documento)
                            venta.documento = key;
                        select.append(new Option(td, key));
                    }
                }
            }
        };
        _getList(options3);
        _ProductoSelectFill('#producto');
        function _guardarCambios() {
            if (!hayCambios) {
                alertify.error("No hay cambios que guardar");
                return;
            }
            var valid = true;
            if (!venta.cliente) {
                alertify.error("Debe seleccionar un cliente");
                valid = false;
            }
            if (!venta.prescriptor) {
                alertify.error("Debe seleccionar un Prescriptor");
                valid = false;
            }
            if (!venta.vendedor) {
                alertify.error("Debe seleccionar un Vendedor");
                valid = false;
            }
            if (!venta.formaDePago) {
                alertify.error("Debe seleccionar una Forma de pago");
                valid = false;
            }
            if (!venta.ventaProductos || venta.ventaProductos.length == 0) {
                alertify.error("Debe agregar al menos un producto");
                valid = false;
            }
            if (!venta.turno) {
                alertify.error("Debe seleccionar un turno");
                valid = false;
            }
            if (!valid)
                return;
            var mensaje = "¿Est&aacute; seguro que desear guardar los cambios?";
            smoke.confirm(mensaje, function (e) {
                if (e) {
                    var data = JSON.stringify(venta);
                    $.ajax({
                        url: 'guardarCambios',
                        type: 'POST',
                        data: data,
                        contentType: 'application/json',
                        dataType: 'json',
                        success: function (response) {
                            $('#guardar-cambios-msg').hide();
                            if (!response.hasError) {
                                _actualizarVenta(response.data);
                                _actualizarTabla();
                                _actualizarForm();
                                for (var i in response.mssg) {
                                    alertify.success(response.mssg[i]);
                                }
                            }
                            else {
                                for (var i in response.mssg) {
                                    alertify.error(response.mssg[i]);
                                }
                                window.console.log("ERROR:\n", response);
                            }
                        },
                        failure: function (response) {
                            window.console.log(response);
                        }
                    });
                }
            });
        }
        $("#btnConsultarVenta").click(function () {
            consultarModal.get("modal").show();
            var modalId = consultarModal.get("modal").get("id");
            $("#" + modalId + " .modal-dialog").attr('style', "width:auto!important;");
            crearCalendar('#fechIni');
            crearCalendar('#fechFin');
        });
        function _selectVenta(row) {
            consultarModal._modal.hide();
            var data = {id: row.id};
            $.ajax({
                url: 'getVenta',
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (response) {
                    if (response.hasError) {
                        alertify.error(response.mssg[0]);
                    } else {
                        _actualizarVenta(response.data);
                        _actualizarForm();
                        _actualizarTabla();
                    }
                },
                failure: function (response) {

                }
            });
        }
        consultarModal._table.set('click', _selectVenta);
        function _actualizarVenta(data) {
            window.gp.reportData = {idVenta: data.id};
            if (data) {
                $("#btnNuevo").click();
                venta = {
                    id: data.id,
                    ventafechaRegistro: data.ventafechaRegistro,
                    fechaRegistro: data.fechaRegistro,
                    preventa: data.preventa,
                    nroVenta: data.nroVenta,
                    nroOperacion: data.nroOperacion,
                    cliente: {
                        idCliente: data.cliente.idCliente,
                        apellidoMaterno: data.cliente.apellidoMaterno,
                        nombre: data.cliente.nombre,
                        apellidoPaterno: data.cliente.apellidoPaterno
                    },
                    diagnostico: data.diagnostico,
                    exonerados: data.exonerados,
                    formaDePago: data.formaDePago,
                    IdModulo: data.IdModulo,
                    redondeoPreventa: data.redondeoPreventa,
                    subTotalPreventa: data.subTotalPreventa,
                    impuestoPreventa: data.impuestoPreventa,
                    preventaNetoAPagar: data.preventaNetoAPagar,
                    especificaciones: data.especificaciones,
                    substitutos: data.substitutos,
                    tipoDeReceta: data.tipoDeReceta,
                    numeroDeReceta: data.numeroDeReceta,
                    modalidad: data.modalidad,
                    documento: data.documento || $("#documento").val(),
                    prescriptor: {
                        codigo: data.prescriptor.codigo,
                        idMedico: data.prescriptor.idMedico,
                        paterno: data.prescriptor.paterno,
                        materno: data.prescriptor.materno,
                        nombre: data.prescriptor.nombre
                    },
                    vendedor: {
                        idVendedor: data.vendedor.idVendedor,
                        paterno: data.vendedor.paterno,
                        materno: data.vendedor.materno,
                        nombre: data.vendedor.nombre
                    },
                    turno: {
                        idTurno: data.turno.idTurno,
                        descripcion: data.turno.descripcion
                    },
                    proceso: data.proceso
                };
                var vProds = [];
                for (var i in data.ventaProductos) {
                    var tmp = data.ventaProductos[i];
                    var vProd = {
                        activo: tmp.activo,
                        cantidad: tmp.cantidad,
                        cantidadActual: tmp.cantidadActual,
                        idProducto: tmp.idProducto,
                        idVenta: tmp.idVenta,
                        precio: tmp.precio,
                        importeTotal: tmp.importeTotal,
                        producto: {
                            idProducto: tmp.producto.idProducto,
                            descripcion: obtenerDescripcionProducto(tmp.producto)
                        }
                    };
                    vProds.push(vProd);
                }
                venta.ventaProductos = vProds;
                if (venta.nroVenta && perfil == 'supervisor') {
                    $('#btnAnular').show();
                }
                if (!venta.ventafechaRegistro)
                    venta.ventafechaRegistro = ventafechaRegistro.getTime();
                if (data.puntoDeVenta) {
                    venta.puntoDeVenta = data.puntoDeVenta;
                }
            }
        }
        function _actualizarProducto(ventaProducto) {
            var actualizado = false;
            var productoId = ventaProducto.producto.idProducto;
            for (var i in venta.ventaProductos) {
                var vProd = venta.ventaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    actualizado = true;
                    var index = i;
                    smoke.confirm("¿Est&aacute; seguro que desea actualizar el producto " + vProd.producto.descripcion + "?", function (e) {
                        if (e) {
                            venta.ventaProductos[index] = ventaProducto;
                            _actualizarTabla();
                            $('#guardar-cambios-msg').removeClass('hidden');
                            $('#guardar-cambios-msg').show();
                            hayCambios = true;
                        }
                    });
                }
            }
            return actualizado;
        }
        function _eliminarProducto(productoId) {
            for (var i in venta.ventaProductos) {
                var vProd = venta.ventaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    smoke.confirm("¿Est&aacute; seguro que desea eliminar el producto " + vProd.producto.descripcion + "?", function (e) {
                        if (e) {
                            venta.ventaProductos.splice(i, 1);
                            $('#guardar-cambios-msg').removeClass('hidden');
                            $('#guardar-cambios-msg').show();
                            _actualizarTabla();
                        }
                    });
                }
            }
        }
        function _encontrarProducto(productoId) {
            for (var i in venta.ventaProductos) {
                var vProd = venta.ventaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    return venta.ventaProductos[i];
                }
            }
            return '';
        }
         function _obtenerDatosPrepago() {
            $.ajax({
                url: '<c:url value="/dispensacion/' + idModulo + '/venta/datosPrepago" />',
                type: 'POST',
                data: JSON.stringify(venta),
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    $('#netoAPagar').val(response.data.preventaNetoAPagar);
                    $('#redondeo').val(response.data.redondeoPreventa);
                    $('#subTotal').val(response.data.subTotalPreventa);
                    $('#impuesto').val(response.data.impuestoPreventa);
                },
                failure: function (response) {

                }
            });
        }
        function _actualizarTabla() {
            $("#tblData-table").dataTable().fnClearTable();
            window.gp.addRow(venta.ventaProductos);
            $('.row-data-change').remove();
            $('body').undelegate('.row-data-edit', 'click');
            $('.row-data-edit').off('click');
            $('body').delegate('.row-data-edit', 'click', function (event) {
                event.preventDefault();
                var productoId = $(this).attr('data-id');
                var producto = _encontrarProducto(productoId);
                $("#mdl-form").find('#cantidad').val(producto.cantidadActual || producto.cantidad);
                $("#mdl-form").find('#c').val(producto.cantidad);
                $("#mdl-form").find('#producto').val(productoId);
                $("#mdl-form").find('#producto').prop('disabled', true);
                $("#mdl-form").find('#producto').removeClass('chzn-done');
                $("#mdl-form").find('#producto_chzn').remove();
                $("#mdl-form").find('#producto').chosen({no_results_text: "No se encontraron coincidencias con"});
                $("#mdl-form").find('#producto_chzn').css({width: '100%'});
                $("#mdl-form").find('#producto_chzn  .chzn-drop').css({width: '98%'});
                $("#mdl-form").find('#producto_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
                $('#modalData-modal').modal('show');
            });
            $('body').undelegate('.row-data-remove', 'click');
            $('.row-data-remove').off('click');
            $('body').delegate('.row-data-remove', 'click', function (event) {
                event.preventDefault();
                var productoId = $(this).attr('data-id');
                _eliminarProducto(productoId);
                hayCambios = true;
            });
            var stocks = 0;
            for (var i in venta.ventaProductos) {
                var cant = venta.ventaProductos[i].cantidadActual || venta.ventaProductos[i].cantidad;
                stocks += cant;
            }
            $('#stocks').val(stocks);
            $('#redondeo').val(fieldFormat.money2(venta.redondeoPreventa));
            if (venta.nroOperacion)
                $('#nroOperacion').val(venta.nroOperacion.substring(2));
            if (venta.nroVenta)
                $('#nroVenta').val(venta.nroVenta);
            if (venta.preventaNetoAPagar) {
                $('#netoAPagar').val(fieldFormat.money2(venta.preventaNetoAPagar));
            }
            if (venta.subTotalPreventa)
                $('#subTotal').val(fieldFormat.money2(venta.subTotalPreventa));
            if (venta.impuestoPreventa)
                $('#impuesto').val(fieldFormat.money2(venta.impuestoPreventa));
            _obtenerDatosPrepago();
        }
        function _actualizarForm() {
            if (venta.vendedor) {
                var nombre = venta.vendedor.nombre + ' ' + venta.vendedor.paterno + ' ' + venta.vendedor.materno;
                $('#vendedor').val(nombre);
            }
            if (venta.prescriptor) {
                var nombre = venta.prescriptor.nombre + ' ' + venta.prescriptor.paterno + ' ' + venta.prescriptor.materno;
                $('#medico').val(nombre);
            }
            if (venta.cliente) {
                var nombre = venta.cliente.nombre + ' ' + venta.cliente.apellidoPaterno + ' ' + venta.cliente.apellidoMaterno;
                $('#cliente').val(nombre);
            }
            if (venta.diagnostico) {
                $('#cliente').val(venta.diagnostico.descripcion);
            }
            if (venta.formaDePago) {
                $('#formaPago').val(venta.formaDePago.id);
            }
            if (venta.proceso) {
                $('#proceso').val(venta.proceso.codigo);
            }
            if (venta.exonerados)
                $('#exonerados').prop('checked', true);
            else
                $('#exonerados').prop('checked', false);
            if (venta.especificaciones)
                $('#especs').val(venta.especificaciones);
            else
                $('#especs').val();
            if (venta.subs)
                $('#substitutos').val(venta.substitutos);
            else
                $('#substitutos').val();
            if (venta.documento)
                $('#documento').val(venta.documento);
            if (venta.modalidad) {
                $('#modalidad').val(venta.modalidad).change();
            }
            if (venta.turno)
                $("#turno").val(venta.turno.idTurno);
            if (venta.preventa) {
                $("#preventa").val(venta.preventa.substring(2));
                $("#periodo").val(venta.preventa.substring(0, 2));
            } else if (venta.modalidad == 1) {
                $("#preventabox").hide();
            }
            $("#periodo2").val(venta.nroOperacion.substring(0, 2));
            $("#nroOperacion").val(venta.nroOperacion.substring(2));
            var tmpDate = new Date();
            tmpDate.setTime(venta.ventafechaRegistro);
            $("#fechaRegistro").val(tmpDate.toLocaleDateString());
            $('#tipoDeReceta').val(venta.tipoDeReceta);
            $('#numeroDeReceta').val(venta.numeroDeReceta);
            if (venta.puntoDeVenta) {
                $("#puntoDeVenta").val(venta.puntoDeVenta.nombrePc);
            }
        }
        $('#btnGuardarCambios').click(function () {
            _guardarCambios();
        });
        $("body").undelegate('#btnGuardar', 'click');
        $("#btnGuardar").click(function () {
            var cant = $("#mdl-form").find('#cantidad').val();
            var c = $("#mdl-form").find('#c').val();
            var prodId = $("#mdl-form").find('#producto').val();
            var stock = $("#mdl-form").find('.chzn-results .result-selected').attr('data-stock');

            if (!cant || cant <= 0) {
                alertify.error("La cantidad debe ser mayor que cero");
                return;
            }
            ventaProducto = {
                cantidadActual: cant,
                cantidad: c,
                producto: {
                    idProducto: prodId
                }
            };
            if (stock)
                stock = parseInt(stock);
            else
                stock = 0;
            if (stock > 0) {
                if (cant > stock) {
                    cant = stock;
                    $("#mdl-form").find('#cantidad').val(cant);
                    alertify.error("Ese producto no tiene el Stock solicitado. (Stock Disponible: " + cant + ")");
                }
                obtenerProducto(ventaProducto);
            } else {
                $.ajax({
                    url: 'obtenerStock',
                    type: 'POST',
                    data: JSON.stringify(ventaProducto),
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function (response) {
                        stock = response.data;
                        if (stock > 0) {
                            if (cant > stock) {
                                cant = stock;
                                ventaProducto.cantidadActual = cant;
                                $("#mdl-form").find('#cantidad').val(cant);
                                alertify.error("Ese producto no tiene el Stock solicitado. (Stock Disponible: " + cant + ")");
                            }
                            $("#producto_chzn .chzn-drop .chzn-results li[data-id='" + prodId + "']").attr('data-stock', stock);
                        } else {
                            alertify.error("Ese producto no tiene Stock disponible.");
                            return;
                        }
                        obtenerProducto(ventaProducto);

                    },
                    failure: function (response) {
                    }
                });
            }
            $("#mdl-form").find('#producto').prop('disabled', false);
            $("#mdl-form").find('#producto').removeClass('chzn-done');
            $("#mdl-form").find('#producto_chzn').remove();
            $("#mdl-form").find('#producto').chosen({no_results_text: "No se encontraron coincidencias con"});
            $("#mdl-form").find('#producto_chzn').css({width: '100%'});
            $("#mdl-form").find('#producto_chzn  .chzn-drop').css({width: '98%'});
            $("#mdl-form").find('#producto_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
        });
        function obtenerProducto(ventaProducto) {
            $.ajax({
                url: $("#formaPago").val() + '/obtenerProducto',
                type: 'POST',
                data: JSON.stringify(ventaProducto),
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    var vp = response.data;
                    var actualizar = _actualizarProducto(vp);
                    if (!actualizar) {
                        venta.ventaProductos.push(vp);
                        _actualizarTabla();
                    }
                    $('#guardar-cambios-msg').removeClass('hidden');
                    $('#guardar-cambios-msg').show();
                    $("#modalData-modal").modal('hide');
                },
                failure: function (response) {
                }
            });
        }
        $('#btnNuevo').click(function () {
            hayCambios = false;
            venta = {
                id: 0,
                nroOperacion: null,
                nroVenta: null,
                ventaProductos: [],
                exonerados: false,
                especificaciones: null,
                substitutos: null,
                modalidad: 0
            };
            $('#btnAnular').hide();
            _actualizarTabla();
            $('#preventa').val('');
            $('#nroOperacion').val('');
            $('#vendedor').val('');
            $('#medico').val('');
            $('#cliente').val('');
            $('#diagnostico').val('SIN DIAGNOSTICO');
            $('#nroVenta').val('');
            $('#formaPago').val('');
            $('#proceso').val('');
            $('#tipoDeReceta').val('');
            $('#numeroDeReceta').val('');
            $('#stocks').val(0);
            $('#redondeo').val(0.00);
            $('#netoAPagar').val(0.00);
            $('#subTotal').val(0.00);
            $('#impuesto').val(0.00);
            $("#proceso").val("");
            $("#turno").val('');
            $('#guardar-cambios-msg').hide();
        });
        $('#btnAnular').click(function () {
            var anularModal = new AnularModal(venta);
            anularModal.get('modal').show();
        });
    });
    //consultar modal
    var ConsultarModal = function () {

        var me = this;
        me._createModal = function () {
            var modal = new GenericModal();
            modal.set('title', 'Consultar Venta');
            modal.set('content', me._createContent());
            modal.set('footer', function () {
                return '';
            });
            modal.appendModal();
            return modal;
        };
        me._createContent = function () {
            return '<div class="row"><form id="vconsultarfrm" class="form-inline  col-md-8 col-lg-8" role="form">\n\
                        <div class = "form-group">\n\
                            <label for = "fechIni">Entre el</label>\n\
                            <input type="text" class="form-control" name="fechIni" id="fechIni" />\n\
                        </div>\n\
                        <div class="form-group">\n\
                            <label for = "fechFin">y el</label>\n\
                            <input type="text" class = "form-control" name="fechFin" id="fechFin"/>\n\
                        </div>\n\
                        <div class = "form-group">\n\
                            <label for="nroV">Nro. Documento</label>\n\
                            <input type="text" class="form-control" name="nroV" id="nroV" />\n\
                        </div>\n\
                        <div class = "form-group">\n\
                            <label for = "per">Per&iacute;odo</label>\n\
                            <input type="text" class="form-control" name="per" id="per" />\n\
                        </div>\n\
                    </form>\n\
                    <form class="form-inline col-md-4 col-lg-4">\n\
                        <div class="form-group">\n\
                            <legend style="margin-bottom:0; font-size:14px">Ordenar por</legend>\n\
                            <div class="checkbox">\n\
                                <label>\n\
                                  <input id="doc" type="checkbox" /> Nro. Doc.\n\
                                </label>\n\
                            </div>\n\
                            <div class="checkbox">\n\
                                <label>\n\
                                  <input id="cli" type="checkbox" /> Cliente\n\
                                </label>\n\
                           </div>\n\
                            <div class="checkbox">\n\
                                <label>\n\
                                  <input id="cajero" type="checkbox" /> Cajero\n\
                                </label>\n\
                            </div>\n\
                        </div>\n\
                        <button id="btnMdlConsultar" class="btn btn-primary" style="margin-top: 12px;margin-left: 10px;">Buscar</button>\n\
                    </form></div>\n\
                    <table id = "' + me._tableID + '" class = "table table-bordered table-striped dTableR select-table"> ' +
                    '<thead>' +
                    '<tr>' +
                    '<th>Id</th>' +
                    '<th>N&uacute;mero</th>' +
                    '<th>Fecha</th>' +
                    '<th>Cliente</th>' +
                    '<th>Forma de Pago</th>' +
                    '<th># Operaci&oacute;n</th>' +
                    '<th>Cajero</th>' +
                    '<th>TOTAL</th>' +
                    '</tr>' +
                    '</thead>' +
                    '</table>';
        };
        me._createTable = function () {
            var id = '#' + me._tableID,
                    table = new slDataTable(id);
            table.set('dataUrl', 'consultarVenta'); //TODO falta cliente.nombreCliente
            table.set('columns', ["id", "nroVenta", "ventafechaRegistro:date", "cliente.nombreCliente", "formaDePago.descripcion", "nroOperacion", "vendedor.nombreVendedor", "preventaNetoAPagar"]);
            table.setColumnsVisibility([0]);
            table.set('success', function (r) {
                if (r.hasError)
                    for (var i in r.mssg) {
                        alertify.error(r.mssg[i]);
                    }
                return r.data || r;
            });
            return table;
        };
        me.consultar = function () {
            me._table._table.fnClearTable();
            var fi = $("#fechIni").val();
            var ff = $("#fechFin").val();
            var nroV = $("#nroV").val();
            var per = $("#per").val();
            var doc = $("#doc").prop("checked");
            var cliente = $("#cli").prop("checked");
            var cajero = $("#cajero").prop("checked");
            var ordenarPor = "";
            if (doc)
                ordenarPor += "nroVenta";
            if (cliente)
                ordenarPor += "-cliente";
            if (cajero)
                ordenarPor += "-vendedor";
            ordenarPor = ordenarPor.trim("-");
            me._params = "fIni=" + fi + "&fFin=" + ff + "&nroVenta=" + nroV + "&periodo=" + per + "&ordenarPor=" + ordenarPor;
            me._table.set('parameters', me._params);
            me._table.get('table').fnSettings().aaSorting = [];
            me._table.getData();
        };
        me.init = function () {
            me._params = '';
            me._tableID = 'tblConsultarVenta';
            me._modal = me._createModal();
            me._table = me._createTable();
            me._table.addRowClickEvent();
            $('#' + me._modal.get('id')).on('hidden.bs.modal', function () {
                me._table._table.fnClearTable();
            });
            $("#btnMdlConsultar").click(function (evt) {
                evt.preventDefault();
                me.consultar();
            });
            $("#vconsultarfrm").click(function (evt) {
                evt.preventDefault();
            });

        };
        me.get = function (field) {

            if (field) {
                var _field = '_' + field;
                if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                    return me[_field];
            }
            return null;
        };
        me.set = function (field, value) {
            if (field) {
                var _field = '_' + field;
                if (me.hasOwnProperty(_field)) {
                    me[_field] = value;
                    return me[_field];
                }
            }
            return null;
        };
        me.add = function (field, value) {
            if (field) {
                var _field = '_' + field;
                if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                    if (typeof (value) === 'object') {
                        for (var key in value) {
                            if (value.hasOwnProperty(key)) {
                                me[_field].push(value[key]);
                            }
                        }
                    }
                    else {
                        me[_field].push(value);
                    }

                    return me[_field];
                }
            }
            return null;
        };
        me.init();
    };
    //anular venta
    var AnularModal = function (venta) {

        var me = this;

        me._createModal = function () {
            var modal = new GenericModal();
            modal.set('title', 'Anular venta #<strong>' + venta.nroVenta + '</strong>');
            modal.set('content', me._createContent());
            modal.set('footer', function () {
                return '';
            });
            modal.appendModal();
            return modal;
        };

        me._createContent = function () {
            return '<form id="anularfrm" role="form">\n\
                        <div class="form-group">\n\
                            <label for="anularmotivos">Motivos de anulaci&oacute;n</label>\n\
                                <textarea class="form-control" rows="3" id="anularmotivos"></textarea>\n\
                        </div><button id="btnDoAnular" class="btn btn-primary">Anular</button>\n\
                    </form>';
        };

        me._anular = function () {
            var motivos = $('#anularmotivos').val();
            if (!motivos) {
                alertify.error("Debe especificar los motivos de la anulaci&oacute;n");
                return;
            }
            venta.motivosAnulacion = motivos;
            var data = JSON.stringify(venta);
            $.ajax({
                url: 'anularVenta',
                type: 'POST',
                data: data,
                dataType: 'json',
                contentType: 'application/json',
                success: function (response) {
                    if (!response.hasError) {
                        $('#btnNuevo').click();
                        me._modal.hide();
                    }
                    else
                        for (var i in response.mssg) {
                            alertify.error(response.mssg[i]);
                        }
                },
                failure: function (response) {

                }
            });
        };

        me.init = function () {
            me._modal = me._createModal();
            $('#btnDoAnular').click(function (evt) {
                evt.preventDefault();
                me._anular();
            });
            $('#anularfrm').submit(function (evt) {
                evt.preventDefault();
            });
        };

        me.get = function (field) {

            if (field) {
                var _field = '_' + field;

                if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                    return me[_field];
            }
            return null;
        };

        me.set = function (field, value) {
            if (field) {
                var _field = '_' + field;

                if (me.hasOwnProperty(_field)) {
                    me[_field] = value;
                    return me[_field];
                }
            }
            return null;
        };

        me.add = function (field, value) {
            if (field) {
                var _field = '_' + field;

                if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                    if (typeof (value) === 'object') {
                        for (var key in value) {
                            if (value.hasOwnProperty(key)) {
                                me[_field].push(value[key]);
                            }
                        }
                    }
                    else {
                        me[_field].push(value);
                    }

                    return me[_field];
                }
            }
            return null;
        };

        me.init();

    };
</script>
