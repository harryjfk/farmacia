<%@include file="../../dispensacion/includes.jsp" %>
<script>
    $(document).ready(function () {
        $(".filter-row").remove();
        $("#mdl-form").find("#unidad").chosen({no_results_text: "No se encontraron coincidencias con"});
        $("#mdl-form").find('#unidad_chzn').css({width: '100%'});
        $("#mdl-form").find('#unidad_chzn  .chzn-drop').css({width: '98%'});
        $("#mdl-form").find('#unidad_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});

        $("#insumo-mdl-form").find("#iunidad").chosen({no_results_text: "No se encontraron coincidencias con"});
        $("#insumo-mdl-form").find('#iunidad_chzn').css({width: '100%'});
        $("#insumo-mdl-form").find('#iunidad_chzn  .chzn-drop').css({width: '98%'});
        $("#insumo-mdl-form").find('#iunidad_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
        var tbl = document.getElementById("tblData-table");
        var tblinsumos = document.getElementById("tblData-tableinsumos");
        if ($.fn.DataTable.fnIsDataTable(tbl)) {
            $(tbl).dataTable().fnDestroy();
        }
        $(tbl).dataTable({
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "nombre", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "unidad.nombreUnidadMedida", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "porcentaje", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "precio", "bSortable": true, mRender: function (data, type, row) {
                        return fieldFormat.money2(venta.precio);
                    }},
                {mData: "idModulo", "bSortable": true, mRender: function (data, type, row) {
                        return venta.cantidad;
                    }},
                {mData: "id", "bSortable": false, mRender: function (data, type, row) {
                        var edit = '';
                        var remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data + '" data-url="${removeUrl}"><i class="splashy-remove" title="Eliminar"></i></a>';
                        if (row.cantidad === null || row.cantidad > 0)
                            edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data + '" data-url="${editUrl}"><i class="splashy-pencil" title="Editar"></i></a>';
                        return edit + remove;
                    }}
            ]
        });
        $(tbl).delegate('.row-data-remove', 'click', function (event) {
            event.preventDefault();
            venta.matriz = null;
            venta.cantidad = 0;
            venta.impuestoPreventa = 0.0;
            venta.subTotalPreventa = 0.0;
            venta.redondeoPreventa = 0.0;
            venta.preventaNetoAPagar = 0.0;
            $(tbl).dataTable().fnClearTable();
            $(tblinsumos).dataTable().fnClearTable();
            $("#matriz").val('');
            $('#matriz').removeClass('chzn-done');
            $("#matriz_chzn").remove();
            $("#matriz").chosen({no_results_text: "No se encontraron coincidencias con"});
            $('#matriz_chzn').css({width: '100%'});
            $('#matriz_chzn  .chzn-drop').css({width: '98%'});
            $('#matriz_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
            _actualizarTabla();
        });
        $(tbl).delegate('.row-data-edit', 'click', function (event) {
            event.preventDefault();
            setTimeout(function () {
                $("#mdl-form").find('#cantidad').val(venta.cantidad);
                $("#mdl-form").find('#precio').val(venta.precio);
                $("#mdl-form").find('#unidad').val(venta.unidad.idUnidadMedida);
                $("#mdl-form").find('#id').val(venta.matriz.id);
            }, 800);
        });
        window.gp.addRow = function (r) {
            $(tbl).dataTable().fnAddData(r);
        };
        $(tblinsumos).dataTable({
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "insumo.nombre", "bSortable": true, mRender: function (data, type, row) {
                        if (row.cantidad === null || row.cantidad > 0)
                            return data;
                        else
                            return '<span style="color:red">' + data + '</span>';
                    }},
                {mData: "insumo.unidad.nombreUnidadMedida", "bSortable": true, mRender: function (data, type, row) {
                        if (row.cantidad === null || row.cantidad > 0)
                            return data;
                        else
                            return '<span style="color:red">' + data + '</span>';
                    }},
                {mData: "insumo.precio", "bSortable": true, mRender: function (data, type, row) {
                        if (row.cantidad === null || row.cantidad > 0)
                            return data;
                        else
                            return '<span style="color:red">' + data + '</span>';
                    }},
                {mData: "cantidad", "bSortable": true, mRender: function (data, type, row) {
                        if (row.cantidad === null || row.cantidad > 0)
                            return data;
                        else
                            return '<span style="color:red">' + data + '</span>';
                    }},
                {mData: "insumo.id", "bSortable": false, mRender: function (data, type, row) {
                        var remove = '<a href="#" class="row-data-remove-insumo separator-icon-td" data-id="' + data + '"><i class="splashy-remove" title="Eliminar"></i></a>';
                        var edit = '<a  class="row-data-edit-insumo separator-icon-td" href="#" data-id="' + data + '"><i class="splashy-pencil" title="Editar"></i></a>';
                        return edit + remove;
                    }}
            ]
        });
        if ($('#btnAgregar').length > 0)
            $('#btnAgregar').attr('id', 'btnGuardarCambios').text('Guardar');
        else
            $('.row.main-button-row').children(':first').append("<button class='btn btn-primary' id='btnGuardarCambios'>Guardar</button>");
        $('#btnGuardarCambios').before('<button id="btnNuevo" class="btn btn-success" style="margin-right: 4px;">Nuevo</button>');
        if ($('#btnPDF').length > 0) {
            var txt = $('#btnPDF').text();
            $('#btnPDF').attr('id', 'btnConsultar').text('Consultar');
            $("#btnConsultar").after("<button class='btn btn-primary' id='btnPDF'>" + txt + "</button>");
        }
        else
            $('.row.main-button-row').children(':first').append("<button class='btn btn-primary' id='btnConsultar'>Consultar</button>");
        $('#mgn-preventa').find('form').submit(function (evt) {
            evt.preventDefault();
        });
        $('.row.main-button-row').children(':first').append("<div class='pull-right alert alert-danger' id='guardar-cambios-msg' style='display:none;'>Hay cambios en la preventa, haga clic en el bot&oacute;n \'Guardar\' para hacerlos permanentes o, si la preventa ya existe, <br \> puede consultar para mostrar los datos nuevamente.</div>");
        var fechaRegistro = new Date();
        var venta = {
            id: 0,
            fechaRegistro: fechaRegistro.getTime(),
            preventa: null,
            exonerados: false,
            especificaciones: null,
            substitutos: null,
            servicio: null,
            matriz: null
        };
        var vendedorActual = '${vendedorActual}';
        if (vendedorActual) {
            var split = vendedorActual.split(":");
            $("#vendedor").val(split[0]);
            venta.vendedor = {idVendedor: split[1]};
        }
        $('#myTab a:first').tab('show');
        var periodo = new Date();
        var re = new RegExp(/\s\d{4}\s/);
        periodo = re.exec(periodo.toUTCString())[0].trim();
        $("#periodo").val(periodo.substring(2, 4));
        var fechaRegistro = new Date();
        $("#fechaRegistro").val(fechaRegistro.toLocaleDateString());
        var idModulo = location.pathname.split('/')[3];
        var personalModal;

        var medicoModal = new MedicoGeneral(idModulo);
        function _selectPrescriptor(row) {
            if (row.idMedico) {
                venta.prescriptor = row;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#medico").val(nombre);
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            } else {
                if (row.personal) {
                    $("#medico").val(row.nombreCompleto);
                    venta.prescriptor = {
                        nombre: row.nombres,
                        materno: row.apellidoMaterno,
                        paterno: row.apellidoPaterno,
                        personal: row.personal,
                        plaza: row.plaza,
                        cargo: row.cargo
                    };
                    if (venta.id > 0)
                        $('#guardar-cambios-msg').show();
                }
            }
            medicoModal._modal.hide();
            if (personalModal) {
                personalModal._modal.hide();
            }
        }
        medicoModal._table.set('click', _selectPrescriptor);
        $("#seleccionaMed").click(function () {
            medicoModal._modal.show();
        });
        $('#medico').on('keyup', function () {
            venta.medico = null;
        });
        var idMdlMed = medicoModal.get("modal").get("id");
        $("#" + idMdlMed).on('show.bs.modal', function () {
            $(this).find("#med-pull-from-personal a").off('click');
            $(this).find("#med-pull-from-personal a").on('click', function (e) {
                e.preventDefault();
                vendedorModal.get("modal").hide();
                if (!personalModal) {
                    personalModal = new PersonalGeneral();
                }
                personalModal._table.set('click', _selectPrescriptor);
                personalModal.get("modal").show();
            });
        });

        var vendedorModal = new VendedorGeneral(idModulo, true);
        function _selectVendedor(row) {
            if (row.idVendedor) {
                venta.vendedor = {
                    idVendedor: row.idVendedor,
                    materno: row.materno,
                    nombre: row.nombre,
                    paterno: row.paterno
                };
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#vendedor").val(nombre);
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            } else {
                $("#vendedor").val(row.nombreCompleto);
                venta.vendedor = {
                    personal: row.personal,
                    materno: row.apellidoMaterno,
                    nombre: row.nombres,
                    paterno: row.apellidoPaterno
                };
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            }
            vendedorModal._modal.hide();
            if (personalModal) {
                personalModal._modal.hide();
            }
        }
        vendedorModal._table.set('click', _selectVendedor);
        $("#seleccionaVen").click(function () {
            var turno = venta.turno;
            if (turno) {
                var idTable = vendedorModal.get("tableID");
                $("#" + idTable).dataTable().fnClearTable();
                vendedorModal.get("table").set("parameters", {"turno": turno.idTurno});
                vendedorModal.get("table").getData();
                vendedorModal._modal.show();
            }
            else
                alertify.error("Debe seleccionar un turno.");
        });
        $('#vendedor').on('keyup', function () {
            venta.vendedor = null;
        });
        var idMdlVen = vendedorModal.get("modal").get("id");
        $("#" + idMdlVen).on('show.bs.modal', function () {
            $(this).find("#ven-pull-from-personal a").off('click');
            $(this).find("#ven-pull-from-personal a").on('click', function (e) {
                e.preventDefault();
                vendedorModal.get("modal").hide();
                if (!personalModal) {
                    personalModal = new PersonalGeneral();
                }
                personalModal._table.set('click', _selectVendedor);
                personalModal.get("modal").show();
            });
        });
        var clienteModal = new ClienteGeneral(idModulo);
        var pacienteModal;
        function _selectCliente(row) {
            if (row.idCliente) {
                venta.cliente = {
                    idCliente: row.idCliente,
                    apellidoMaterno: row.apellidoMaterno,
                    nombre: row.nombre,
                    apellidoPaterno: row.apellidoPaterno
                };
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            } else {
                if (row.paciente) {
                    venta.cliente = {
                        codPersonal: row.paciente,
                        apellidoMaterno: row.materno,
                        nombre: row.nombre,
                        apellidoPaterno: row.paterno
                    };
                    var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                    $("#cliente").val(nombre);
                    if (venta.id > 0)
                        $('#guardar-cambios-msg').show();
                }
            }
            clienteModal._modal.hide();
            if (pacienteModal)
                pacienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function () {
            clienteModal._modal.show();
        });
        $('#cliente').on('keyup', function () {
            var fp = $("#formaPago option:selected").html().toLowerCase();
            var nomb = $(this).val().trim();
            if (fp.indexOf('cr') != 0 && fp.indexOf('dito') != 3 && nomb) {
                venta.cliente = {
                    idCliente: 0,
                    nombre: nomb
                };

            } else
                venta.cliente = null;
        });
        var idMdlCli = clienteModal.get("modal").get("id");
        $("#" + idMdlCli).on('show.bs.modal', function () {
            var tblInfo = $(this).find('.dataTables_info');
            if (!tblInfo.hasClass('col-lg-6 col-md-6'))
                tblInfo.addClass('col-lg-6 col-md-6');
            if (tblInfo.parent().children("#pull-from-paciente").length == 0) {
                tblInfo.after('<div id="pull-from-paciente" class="col-lg-6 col-md-6">');
                $(this).find('#pull-from-paciente').append("<a href='#'>Seleccionar Paciente</a>");
                $(this).find("#pull-from-paciente").css({"paddingTop": "8px"});
            }
            $(this).find("#pull-from-paciente a").off('click');
            $(this).find("#pull-from-paciente a").on('click', function (e) {
                e.preventDefault();
                clienteModal.get("modal").hide();
                if (!pacienteModal) {
                    pacienteModal = new PacienteGeneral();
                    pacienteModal._table.set('click', _selectCliente);
                }
                pacienteModal.get("modal").show();
            });
        });
        var diagnosticoModal = new DiagnosticoGeneral();
        function _selectDiagnostico(row) {
            if (row.descripcion) {
                venta.diagnostico = row;
                var desc = row.descripcion;
                $("#diagnostico").val(desc);
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            }
            diagnosticoModal._modal.hide();
        }
        diagnosticoModal._table.set('click', _selectDiagnostico);
        $("#seleccionaDiag").click(function () {
            diagnosticoModal._modal.show();
        });
        $('#diagnostico').on('keyup', function () {
            venta.diagnostico = null;
        });
        var servicioModal = new ServicioGeneral(idModulo);
        function _selectServicio(row) {
            if (row.nombre) {
                venta.servicio = row;
                var nombre = row.nombre;
                $("#servicio").val(nombre);
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            }
            servicioModal._modal.hide();
        }
        servicioModal.get('table').set('click', _selectServicio);
        $("#seleccionaServ").click(function () {
            servicioModal.get('modal').show();
        });
        $('#servicio').on('keyup', function () {
            venta.servicio = null;
        });
        $("#turno").change(function (e) {
            e.preventDefault();
            if ($(this).val()) {
                venta.turno = {
                    idTurno: $(this).val()
                };
                if (venta.id > 0)
                    $('#guardar-cambios-msg').show();
            } else {
                venta.turno = null;
                $('#guardar-cambios-msg').hide();
            }
        });
        var options = {
            url: '/GenericFormaPago/listar',
            success: function (r, status, ajaxData) {
                var select = $("#formaPago"),
                        td;
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        var opt = new Option(td.descripcion, td.id);
                        if (td.descripcion.toLowerCase() == 'contado') {
                            opt.selected = true;
                        }
                        select.append(opt);
                    }
                }
                select.change();
            }
        };
        _getList(options);
        var isUserSeller = ${isUserSeller};
        var turnoUrl = '/' + idModulo + '/turno/getTurnos';
        if (isUserSeller) {
            var idVendedor = vendedorActual.split(':')[1];
            if (idVendedor) {
                turnoUrl += '?idVendedor=' + idVendedor;
                $("#vendedor").prop('disabled', true);
                $("#seleccionaVen").hide();
            }
        }
        var options = {
            url: turnoUrl,
            success: function (r, status, ajaxData) {
                var select = $("#turno"),
                        td;
                select.append(new Option("-- TURNOS --", ""));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.descripcion, td.idTurno));
                    }
                }
                var turnoActual = '${turnoActual}';
                if (turnoActual) {
                    var selected = "#turno option[value='" + turnoActual + "']";
                    $(selected).prop('selected', true);
                    venta.turno = {idTurno: turnoActual};
                }
            }
        };
        _getList(options);
        var matricesUrl = "/" + idModulo + "/matriz/getMatrices";
        var options = {
            url: matricesUrl,
            success: function (r, status, ajaxData) {
                var select = $("#matriz"),
                        td;
                select.append(new Option("", ""));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.nombre, td.id));
                    }
                }
                $(select).chosen({no_results_text: "No se encontraron coincidencias con"});
            }
        };
        _getList(options);
        $("#matriz").change(function (e) {
            e.preventDefault();
            var url = '<c:url value="/" />' + idModulo + "/matriz/getMatriz";
            var idMatriz = $(this).val();
            $(tbl).dataTable().fnClearTable();
            $.ajax({
                url: url,
                type: 'GET',
                data: {id: idMatriz},
                success: function (response) {
                    $('#modalData-modal').modal('show');
                    venta.matriz = response;
                    $("#verstocks").val(venta.matriz.cantidad);
                },
                failure: function (response) {

                }
            });
        });
        $('#especs').on('change keyup', function () {
            venta.especificaciones = $(this).val();
            if (venta.id > 0)
                $('#guardar-cambios-msg').show();
        });
        $('#subs').on('change keyup', function () {
            venta.substitutos = $(this).val();
            if (venta.id > 0)
                $('#guardar-cambios-msg').show();
        });
        $('#numeroDeReceta').on('change keyup', function () {
            venta.numeroDeReceta = $(this).val();
            if (venta.id > 0)
                $('#guardar-cambios-msg').show();
        });
        $('#tipoDeReceta').on('change', function () {
            venta.tipoDeReceta = $(this).val();
            if (venta.id > 0)
                $('#guardar-cambios-msg').show();
        });
        $('#exonerados').on('change', function () {
            venta.exonerados = $(this).prop('checked');
            if (venta.id > 0)
                $('#guardar-cambios-msg').show();
        });
        $("#formaPago").on('change', function () {
            if ($(this).val()) {
                venta.formaDePago = {
                    id: $(this).val()
                };
                _obtenerDatosPrepago();
                $("#cliente").val('');
//                if (venta.id > 0)
//                    $('#guardar-cambios-msg').show();
            }
        });
        var histModal = null;
        $("#show-hist").click(function (e) {
            e.preventDefault();
            if (venta.cliente) {
                if (!histModal) {
                    histModal = new HistorialModal(idModulo);
                }
                histModal.loadHistorial(venta.cliente.idCliente, 30);
            } else {
                alertify.error("Debe seleccionar un cliente");
            }
        });
        //Validar Precio
        $.validator.addMethod("currency", function (value, element) {
            return this.optional(element) || /^(\d+)[\.|,](\d{1,4})/.test(value);
        }, "Please specify a valid amount");
        $("#mdl-form").validate({
            rules: {
                cantidad: {
                    required: true,
                    digits: true
                },
                unidad: {
                    required: true
                },
                precio: {
                    currency: true
                }
            },
            messages: {
                cantidad: {
                    required: "Este campo es obligatorio",
                    digits: "Este campo solo admite d&iacute;gitos"
                },
                unidad: {
                    required: "Este campo es obligatorio"
                },
                precio: {
                    currency: "Introduzca un precio v&aacute;lido"
                }
            }
        });
        function _guardarCambios() {

            var valid = true;
            if (!venta.cliente || !venta.cliente.nombre.trim()) {
                alertify.error("Debe seleccionar un Cliente");
                valid = false;
            }
            var elNombre = venta.cliente.apellidoMaterno + " " + venta.cliente.apellidoPaterno + " " + venta.cliente.nombre;
            if (!valid || !venta.cliente || !venta.cliente.apellidoMaterno || !venta.cliente.apellidoPaterno || elNombre.split(" ").length < 3) {
                alertify.error("El nombre del cliente no es v&aacute;lido. Verifique que tenga al menos un nombre y dos apellidos");
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
            if (!venta.matriz || venta.matriz.matrizInsumos.length == 0) {
                alertify.error("Debe seleccionar una matriz y sus insumos");
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
        $("#btnConsultar").click(function () {
            $("#guardar-cambios-ms").hide();
            var preventa = $('#preventa').val();
            var periodo = $('#periodo').val();
            if (!preventa) {
                alertify.error("Debe escribir un n&uacute;mero de Pre Venta");
                return;
            }
            if (preventa.length < 7 || preventa.length > 7) {
                alertify.error("Debe escribir un n&uacute;mero de Pre Venta v&aacute;lido");
                return;
            }
            if (isNaN(parseInt(preventa)) || /[a-zA-Z]{1,}/.test(preventa)) {
                alertify.error("Debe escribir un n&uacute;mero de Pre Venta v&aacute;lido");
                return;
            }
            var data = 'preventa=' + periodo + preventa;
            cantidadSinStock = [];
            $.ajax({
                url: 'consultarPreventa',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (response) {
                    if (!response.hasError) {
                        _actualizarVenta(response.data);
                        _actualizarTabla();
                        _actualizarForm();
                        $('#guardar-cambios-msg').hide();
                    } else {
                        $("#btnNuevo").click();
                        for (var i in response.mssg) {
                            alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function (response) {
                }
            });
        });
        function _actualizarVenta(data) {
            if (data) {
                window.gp.reportData = {idVenta: data.id};
                venta = {
                    id: data.id,
                    fechaRegistro: data.fechaRegistro,
                    preventa: data.preventa,
                    cliente: {
                        idCliente: data.cliente.idCliente,
                        apellidoMaterno: data.cliente.apellidoMaterno,
                        nombre: data.cliente.nombre,
                        apellidoPaterno: data.cliente.apellidoPaterno
                    },
                    diagnostico: data.diagnostico, exonerados: data.exonerados,
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
                    proceso: data.proceso,
                    puntoDeVenta: data.puntoDeVenta,
                    servicio: data.servicio,
                    matriz: data.matriz,
                    unidad: data.unidad,
                    cantidad: data.cantidad,
                    precio: data.precio
                };
                if (data.prescriptor) {
                    venta.prescriptor = {
                        codigo: data.prescriptor.codigo,
                        idMedico: data.prescriptor.idMedico,
                        paterno: data.prescriptor.paterno,
                        materno: data.prescriptor.materno,
                        nombre: data.prescriptor.nombre
                    };
                }

            }
        }

        function _actualizarTabla() {
            $("#tblData-table").dataTable().fnClearTable();
            if (venta.matriz && venta.matriz.id && venta.matriz.id > 0)
                window.gp.addRow(venta.matriz);
            $('#tblData-tableinsumos').dataTable().fnClearTable();
            if (venta.matriz && venta.matriz.matrizInsumos && venta.matriz.matrizInsumos.length > 0)
                $('#tblData-tableinsumos').dataTable().fnAddData(venta.matriz.matrizInsumos);
            if(venta.cantidad)
                $('#stocks').val(venta.cantidad);
            else
                $('#stocks').val('');
            if(venta.matriz)
                $("#verstocks").val(venta.matriz.cantidad);
            else
                $("#verstocks").val('');
            if(venta.redondeoPreventa)
                $('#redondeo').val(fieldFormat.money2(venta.redondeoPreventa));
            else
                $('#redondeo').val('');
            if (venta.preventa)
                $('#preventa').val(venta.preventa.substring(2));
            if (venta.preventaNetoAPagar) {
                $('#netoAPagar').val(fieldFormat.money2(venta.preventaNetoAPagar));
            } else
                $('#netoAPagar').val('');
            if (venta.subTotalPreventa)
                $('#subTotal').val(fieldFormat.money2(venta.subTotalPreventa));
            else
                $('#subTotal').val('');
            if (venta.impuestoPreventa)
                $('#impuesto').val(fieldFormat.money2(venta.impuestoPreventa));
            else
                $('#impuesto').val('');
        }
        function _actualizarForm() {
            if (venta.vendedor) {
                var nombre = venta.vendedor.nombre + ' ' + venta.vendedor.paterno + ' ' + venta.vendedor.materno;
                $('#vendedor').val(nombre);
            } else {
                $('#vendedor').val('');
            }
            if (venta.prescriptor) {
                var nombre = venta.prescriptor.nombre + ' ' + venta.prescriptor.paterno + ' ' + venta.prescriptor.materno;
                $('#medico').val(nombre);
            } else {
                $('#medico').val('');
            }
            if (venta.cliente) {
                var nombre = venta.cliente.nombre + ' ' + venta.cliente.apellidoPaterno + ' ' + venta.cliente.apellidoMaterno;
                $('#cliente').val(nombre);
            } else {
                $('#cliente').val('');
            }
            if (venta.diagnostico) {
                $('#diagnostico').val(venta.diagnostico.descripcion);
            } else {
                $('#diagnostico').val('SIN DIAGNOSTICO');
            }
            if (venta.formaDePago) {
                $('#formaPago').val(venta.formaDePago.id);
            }
            if (venta.proceso) {
                $('#proceso').val(venta.proceso.id);
            }
            if (venta.fechaRegistro) {
                var fech = new Date();
                fech.setTime(venta.fechaRegistro);
                $('#fechaRegistro').val(fech.toLocaleDateString());
            }
            if (venta.turno) {
                $("#turno").val(venta.turno.idTurno);
            }
            if (venta.exonerados)
                $('#exonerados').prop('checked', true);
            else
                $('#exonerados').prop('checked', false);
            if (venta.especificaciones)
                $('#especs').val(venta.especificaciones);
            else
                $('#especs').val('');
            if (venta.substitutos)
                $('#subs').val(venta.substitutos);
            else
                $('#subs').val('');
            if (venta.servicio) {
                $("#servicio").val(venta.servicio.nombre);
            } else {
                $("#servicio").val('');
            }
            $('#tipoDeReceta').val(venta.tipoDeReceta);
            $('#numeroDeReceta').val(venta.numeroDeReceta);
            if (venta.preventa) {
                $("#preventa").val(venta.preventa.substring(2));
                $("#periodo").val(venta.preventa.substring(0, 2));
            }
            if (venta.proceso)
                $("#proceso").val(venta.proceso.codigo);
            if (venta.puntoDeVenta) {
                $("#puntoDeVenta").val(venta.puntoDeVenta);
            }
            if (venta.matriz) {
                $('#matriz').val(venta.matriz.id);
                $('#matriz').removeClass('chzn-done');
                $("#matriz_chzn").remove();

                $("#matriz").chosen({no_results_text: "No se encontraron coincidencias con"});
                $('#matriz_chzn').css({width: '100%'});
                $('#matriz_chzn  .chzn-drop').css({width: '98%'});
                $('#matriz_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
            }
        }
        $('#btnGuardarCambios').click(function () {
            _guardarCambios();
        });
        $("body").undelegate('#btnGuardar', 'click');
        $("#btnGuardar").click(function () {
            $(tbl).dataTable().fnClearTable();
            var cant = $("#mdl-form").find('#cantidad').val();
            var unidadId = $("#mdl-form").find('#unidad').val();
            var nombreUnidad = $("#mdl-form").find('#unidad option:selected').html();
            var unidad = {
                idUnidadMedida: unidadId,
                nombreUnidadMedida: nombreUnidad
            };
            var precio = $("#mdl-form").find('#precio').val();

            if (!cant || cant <= 0) {
                alertify.error("La cantidad debe ser mayor que cero");
                return;
            }

            venta.cantidad = cant;
            venta.precio = precio ? precio : venta.matriz.precio;
            venta.unidad = unidad.idUnidadMedida ? unidad : venta.matriz.unidad;
            window.gp.addRow(venta.matriz);
            $(tblinsumos).dataTable().fnClearTable();
            $(tblinsumos).dataTable().fnAddData(venta.matriz.matrizInsumos);
            $('#modalData-modal').modal("hide");
            _obtenerDatosPrepago();
        });
        function _obtenerDatosPrepago() {
            var insumos = venta.matriz.matrizInsumos;
            venta.matriz.matrizInsumos = [];
            $.ajax({
                url: 'datosPrepago',
                type: 'POST',
                data: JSON.stringify(venta),
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    $('#netoAPagar').val(response.data.preventaNetoAPagar);
                    $('#redondeo').val(response.data.redondeoPreventa);
                    $('#subTotal').val(response.data.subTotalPreventa);
                    $('#impuesto').val(response.data.impuestoPreventa);
                    $("#stocks").val(venta.cantidad);

                    venta.matriz.matrizInsumos = insumos;
                },
                failure: function (response) {

                }
            });
        }
        $('#btnLimpiar').click(function () {
            smoke.confirm("¿Est&aacute; seguro que desea eliminar todos los productos?", function (e) {
                if (e) {
                    $("#tblData-table").dataTable().fnClearTable();
                    $("#tblData-tableinsumos").dataTable().fnClearTable();
                    $('#stocks').val(0);
                    $('#redondeo').val(0.00);
                    $('#netoAPagar').val(0.00);
                    $('#subTotal').val(0.00);
                    $('#impuesto').val(0.00);
                    $('#guardar-cambios-msg').removeClass('hidden');
                    $('#guardar-cambios-msg').show();
                    $("#btnNuevo").click();
                }
            });
            $('#btnAgregar').prop('disabled', false);
        });
        $('#btnNuevo').click(function () {
            var fp = $('#formaPago option:first').attr('value');
            venta = {
                id: 0,
                fechaRegistro: fechaRegistro.getTime(),
                preventa: null,
                exonerados: false,
                especificaciones: null,
                substitutos: null,
                prescriptor: null, cliente: null,
                vendedor: null,
                tipoDeReceta: 0,
                formaDePago: {id: fp},
                servicio: null,
                matriz: {
                    matrizInsumos: []
                }
            };
            _actualizarTabla();
            $('#preventa').val('');
            $('#vendedor').val('');
            $('#medico').val('');
            $('#cliente').val('');
            $('#servicio').val('');
            $('#diagnostico').val('SIN DIAGNOSTICO');
            $('#formaPago option').each(function (i, elem) {
                var desc = $(elem).html();
                if (desc.toLowerCase() == 'contado') {
                    $('#formaPago').val($(elem).attr('value'));
                    venta.formaDePago = {id: $('#formaPago').val()};
                }
            });
            $('#tipoDeReceta').val(0);
            $('#numeroDeReceta').val('');
            $("#turno").val('');
            $("#especs").val('');
            $("#subs").val('');
            $("#puntoDeVenta").val('');
            $('#exonerados').prop('checked', false);

            $('#stocks').val(0);
            $('#redondeo').val(0.00);
            $('#netoAPagar').val(0.00);
            $('#subTotal').val(0.00);
            $('#impuesto').val(0.00);
            $("#proceso").val("");
            $('#guardar-cambios-msg').hide();

            $('#matriz').val('');
            $('#matriz').removeClass('chzn-done');
            $("#matriz_chzn").remove();

            $("#matriz").chosen({no_results_text: "No se encontraron coincidencias con"});
            $('#matriz_chzn').css({width: '100%'});
            $('#matriz_chzn  .chzn-drop').css({width: '98%'});
            $('#matriz_chzn  .chzn-drop  .chzn-search input').css({width: '98%'});
        });
        //historial
      var HistorialModal = function (idModulo) {

        var me = this;

        me._createModal = function () {
            var modal = new GenericModal();
            modal.set('title', 'Historial de Ventas (no se muestran las Pre Vetas)');
            modal.set('content', me._createContent());
            modal.set('footer', function () {
                return '';
            });

            modal.appendModal();
            var mId = modal.get('id');
            $("#" + mId).find('.modal-body').css({
                'height': '285px',
                'overflow': 'overlay'
            });
            $("#" + mId).find('.modal-dialog').css({
                'width': '1000px'
            });
            $("#" + mId).delegate('.hist-mdl-toggle-prod', 'click', function (e) {
                e.preventDefault();
                $(this).next().toggle();
            });
            return modal;
        };


        me._createContent = function () {
            return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                    '<thead>' +
                    '<tr>' +
                    '<th>Fecha</th>' +
                    '<th>Nro. Docum.</th>' +
                    '<th>Nro. Oper.</th>' +
                    '<th>Doc.</th>' +
                    '<th>F.P.</th>' +
                    '<th>Total</th>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody></tbody>' +
                    '</table>';
        };

        me.loadHistorial = function (idCliente, dias) {
            var url = me._url.replace("{idCliente}", idCliente).replace('{dias}', dias);
            me._clearTable();
            me.getData(url);
            me._modal.show();
        };

        me._clearTable = function () {
            var id = "#" + me._tableID;
            $(id + ' tbody').html('');
        };

        me.getData = function (url) {
            $.ajax({
                url: url || '',
                type: "GET",
                dataType: 'json',
                success: function (r) {
                    me._addRow(r.data);
                },
                failure: function (r) {
                    me._failure(r);
                }
            });
        };

        me._addRow = function (ventas) {
            if (ventas) {
                var id = "#" + me._tableID;
                var elem = $(id + ' tbody');
                for (var i in ventas) {
                    var item = ventas[i];
                    var srow = $("<tr></tr>");
                    srow.append('<td>' + fieldFormat.date(item.ventafechaRegistro) + '</td>');
                    srow.append('<td>' + item.nroVenta + '</td>');
                    srow.append('<td>' + item.nroOperacion + '</td>');
                    srow.append('<td>' + item.docTipo + '</td>');
                    srow.append('<td>' + item.formaDePago.descripcion + '</td>');
                    srow.append('<td>' + fieldFormat.money2(item.preventaNetoAPagar) + '</td>');
                    elem.append(srow);

                    elem.append("<tr class='hist-mdl-toggle-prod'><td colspan='6'><a href='#' style='font-weight:bold'>&raquo;Matriz</a></td></tr>");
                    var prow = $("<tr></tr>");
                    var pTable = $("<td colspan='6'><table class='table table-bordered table-striped dTableR select-table'><thead><tr><th>Nombre</th><th>Unidad de Medida</th><th>Precio</th><th>Cantidad</th></tr></thead><tbody></tbody></table><td>");
                    var row = $("<tr></tr>");
                    row.append('<td>' + item.matriz.nombre + '</td>');
                    row.append('<td>' + item.matriz.unidad.nombreUnidadMedida + '</td>');
                    row.append('<td>' + fieldFormat.money2(item.precio) + '</td>');
                    row.append('<td>' + item.cantidad + '</td>');
                    pTable.find('tbody').append(row);
                    prow.append(pTable);
                    elem.append(prow);
                }
                elem.find('td[colspan="6"]').next().remove();
                $('.hist-mdl-toggle-prod').click();
            }
        };

        me.init = function () {
            me._tableID = 'tblHistorialModal';
            me._modal = me._createModal();
            me._url = '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/fventa/{idCliente}/{dias}/historial';
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
        $("#mdl-form").delegate("#matriz", 'change', function (e) {
            $("#lastdate").html('');
            $("#addid").hide();
        });
        $("#modalData-modal").on('show.bs.modal', function (e) {
            if ($(this).find('#seeklastdate').length == 0) {
                $(this).find('form').after('<a href="javascript:void(0)" id="seeklastdate">Buscar &Uacute;ltima Fecha</a>');
                $("#seeklastdate").after(':&nbsp;&nbsp;<span id="lastdate"></span>');
                $("#lastdate").after('<br /><button id="addid" class="btn btn-primary btn-sm" style="display:none">A&ntilde;adir Demanda Insatisfecha</button>');
                $("#seeklastdate").click(function (e) {
                    e.preventDefault();
                    var cli = venta.cliente;
                    var productoId = $(this).parent().find('form').find('#matriz').val();
                    if (!cli) {
                        alertify.error("Debe seleccionar un cliente");
                    } else {
                        $.ajax({
                            url: 'getlastdate',
                            type: 'POST',
                            data: {clienteId: cli.idCliente, productoId: productoId},
                            success: function (response) {
                                $("#lastdate").text(response.data);
                            },
                            failure: function (response) {

                            }
                        });
                    }
                });
            }
        });
        $("#modalData-modal").on('hide.bs.modal', function (e) {
            $("#lastdate").text('');
            $("#addid").hide();
        });
        $("#btnAgregarIn").click(function (e) {
            if (venta.matriz) {
                $("#addInsumo-mdl").modal("show");
            } else {
                alertify.error("Seleccione una Matriz");
            }
        });
        $("#btnAgregarInsumo").click(function (e) {
            if (venta.matriz) {
                $(this).prop("disabled", true);
                var insumo = $("#insumo-mdl-form").find('#insumo').val();
                var cantidad = $("#insumo-mdl-form").find('#icantidad').val();
                var id = $("#insumo-mdl-form").find('#id').val();
                var mmId = $("#insumo-mdl-form").find('#mm-id').val();
                $.ajax({
                    url: 'agregarInsumo',
                    type: 'POST',
                    data: {
                        "id": insumo,
                        "cantidad": cantidad,
                        "matriz": venta.matriz.id,
                        "mmId": mmId ? mmId : null
                    },
                    success: function (response) {
                        $("#btnAgregarInsumo").prop("disabled", false);
                        if (!response.hasError) {
                            if (id != null) {
                                var row = $(tblinsumos).find('td a[data-id="' + id + '"]').parent().parent().get(0);
                                if(row) {
                                    var index = $(tblinsumos).dataTable().fnGetPosition(row);
                                    $(tblinsumos).dataTable().fnDeleteRow(index);
                                }
                                for (var i in venta.matriz.matrizInsumos) {
                                    var elem = venta.matriz.matrizInsumos[i];
                                    if (elem.id == response.data.id) {
                                        venta.matriz.matrizInsumos[i] = response.data;
                                    }
                                }
                            } else {
                                venta.matriz.matrizInsumos.push(response.data);
                            }
                            $(tblinsumos).dataTable().fnAddData(response.data);
                        } else {
                            alertify.error(response.mssg[0]);
                        }
                    },
                    failure: function (response) {
                        $(this).prop("disabled", false);
                    }
                });

            } else {
                alertify.error("Seleccione una Matriz");
            }
        });
        $("#insumo-mdl-form").validate({
            rules: {
                insumo: {required: true},
                icantidad: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                insumo: {required: "Este campo es obligatorio"},
                icantidad: {
                    required: "Este campo es obligatorio",
                    digits: "Este campo solo admite d&iacute;gitos"
                }
            }
        });
        $(tblinsumos).delegate('.row-data-remove-insumo', 'click', function (e) {
            e.preventDefault();
            var insumoId = $(this).attr('data-id');
            var url = '<c:url value="/" />dispensacion/' + idModulo + '/matriz/eliminarInsumo';
            $.ajax({
                url: url,
                type: 'POST',
                data: {
                    matrizId: venta.matriz.id,
                    materiaId: insumoId
                },
                success: function (response) {
                    if (!response.hasError) {
                        $(tblinsumos).dataTable().fnClearTable();
                        $(tblinsumos).dataTable().fnAddData(response.data.matrizInsumos);
                        venta.matriz = response.data;
                    } else {
                        alertify.error(response.mssg[0]);
                    }
                },
                failure: function (response) {

                }
            });
        });
        $(tblinsumos).delegate('.row-data-edit-insumo', 'click', function (e) {
            e.preventDefault();
            var elem = null;
            var id = $(this).attr('data-id');
            var insumos = venta.matriz.matrizInsumos;
            for (var i in insumos) {
                if (insumos[i].insumo.id == id) {
                    elem = insumos[i];
                    break;
                }
            }
            $("#addInsumo-mdl").find("#insumo").val(elem.insumo.id);
            $("#addInsumo-mdl").find("#icantidad").val(elem.cantidad);
            $("#addInsumo-mdl").find("#id").val(elem.insumo.id);
            $("#addInsumo-mdl").find("#mm-id").val(elem.id);
            $("#addInsumo-mdl").modal('show');
        });
        $("#addInsumo-mdl").on("hidden.bs.modal", function () {
            $("#addInsumo-mdl").find('#id').val('0');
        });
    });
</script>
