<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(document).ready(function () {
        window.gp.addRow = function (r) {
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    var data = r[key];
                    window.gp.table.fnAddData(data);
                }
            }
        };
        var tbl = document.getElementById("tblData-table");
        if ($.fn.DataTable.fnIsDataTable(tbl)) {
            $(tbl).dataTable().fnDestroy();
        }
        var zeroStocks = [];
        var zeroStockCount = 0;
        $(tbl).dataTable({
            "aoColumns": [
                {mData: "producto.descripcion", mRender: function (data, type, row) {
                        if (row.stock <= 0) {
                            var esta = false;
                            zeroStocks.filter(function (elem, j) {
                                if (elem == row.producto.idProducto) {
                                    esta = true;
                                }
                            });
                            if (!esta) {
                                zeroStockCount++;
                                zeroStocks.push(row.producto.idProducto);
                            }
                            return '<span style="color:red;">' + data + '</span>';
                        }
                        return data;
                    }},
                {mData: "producto.idProducto"},
                {mData: "cantidad"},
                {mData: "precio", mRender: function (data, type, row) {
                        return fieldFormat.money4(data);
                    }},
                {mData: "importeParcial", mRender: function (data, type, row) {
                        return fieldFormat.money4(data);
                    }},
                {mData: "producto.idProducto", "bSortable": false, mRender: function (data, type, row) {
                        var edit = '';
                        var remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data + '" data-url="eliminar"><i class="splashy-remove" title="Eliminar"></i></a>';
                        if (row.stock === null || row.stock > 0)
                            edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data + '" data-url="modificar"><i class="splashy-pencil" title="Editar"></i></a>';
                        return edit + remove;
                    }}]
        });
        if ($("#btnAgregar").length > 0) {
            $("#btnAgregar").before('<button id="btnNuevo" class="btn btn-success">Nuevo</button>');
            $("#btnAgregar").html("Agregar Producto");
        }
        $('.row.main-button-row').children(':first').append("<button class='btn btn-primary' id='btnGuardarCambios'>Guardar</button>");
        $('.row.main-button-row').children(':first').append("<button id='btnAnular' class='btn btn-danger' style='display:none;margin-left:3px;'>Anular</button>");
        $('.row.main-button-row').children(':first').append("<button id='btnConsultar' class='btn btn-primary' style='margin-left:3px'>Buscar por N&uacute;mero</button>");
        $('#btnConsultar').after("<div class='pull-right alert alert-danger hidden' id='guardar-cambios-msg' style='margin-top: 5px;'>Hay cambios en la Intervencion, haga clic en el bot&oacute;n \'Guardar\' para hacerlos permanentes.</div>");
        var frtComponente;
        _ComponenteSelectFill("#componente");
        $("#componente").bind({
            Lleno: function (e) {
                e.preventDefault();
                frtComponente = $("#componente").val();
                _subComponenteSelectFill("#subcomponente", {componente: frtComponente});
            }
        });
        _ProcesoSelectFill("#proceso");
        var fechaRegistro = new Date();
        $("#fechaRegistro").val(fechaRegistro.toLocaleDateString());
        var interv = {
            nroSalida: null,
            fechaRegistro: fechaRegistro.getTime(),
            intervSanitariaProductos: []
        };
        var idModulo = location.pathname.split('/')[3];
        var kitModal;
        var medicoModal = new MedicoGeneral(idModulo);
        function _selectPrescriptor(row) {
            if (row.idMedico) {
                interv.prescriptor = row;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#prescriptor").val(nombre);
            }
            medicoModal._modal.hide();
        }
        medicoModal._table.set('click', _selectPrescriptor);
        $("#seleccionaMed").click(function () {
            medicoModal._modal.show();
        });
        $('#prescriptor').on('keyup', function () {
            interv.prescriptor = null;
        });
        var clienteModal = new ClienteGeneral(idModulo);
        var pacienteModal;
        function _selectCliente(row) {
            $("#nroHistClinica").val('');
            if (row.idCliente) {
                interv.cliente = {
                    idCliente: row.idCliente,
                    apellidoMaterno: row.apellidoMaterno,
                    nombre: row.nombre,
                    apellidoPaterno: row.apellidoPaterno
                };
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
                if (interv.id > 0)
                    $('#guardar-cambios-msg').show();
                $("#cliente").change();
            } else {
                if (row.paciente) {
                    interv.cliente = {
                        codPersonal: row.paciente,
                        apellidoMaterno: row.materno,
                        nombre: row.nombre,
                        apellidoPaterno: row.paterno
                    };
                    if (row.historia) {
                        $("#nroHistClinica").val(row.historia);
                    }
                    var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                    $("#cliente").val(nombre);
                    if (interv.id > 0)
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
            interv.cliente = null;
        });
        $("#cliente").on('change', function (e) {
            e.preventDefault();
            var idCliente = interv.cliente.idCliente;
            var url = '<c:url value="/dispensacion/"/>' + idModulo + '/cliente/obtenerHC';
            $.ajax({
                url: url,
                type: 'POST',
                data: {idCliente: idCliente},
                success: function (response) {
                    if (response)
                        $("#nroHistClinica").val(response);
                },
                failure: function (response) {

                }
            });
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
                interv.diagnostico = row;
                var desc = row.descripcion;
                $("#diagnostico").val(desc);
            }
            diagnosticoModal._modal.hide();
        }
        diagnosticoModal._table.set('click', _selectDiagnostico);
        $("#seleccionaDiag").click(function () {
            diagnosticoModal._modal.show();
        });
        $('#diagnostico').on('keyup', function () {
            interv.diagnostico = null;
        });
        var coordinadorModal = new PersonalGeneral();
        function _selectCoordinador(row) {
            if (row.personal) {
                interv.coordinador = row.personal;
                var nombre = row.nombres + " " + row.apellidoPaterno + " " + row.apellidoMaterno;
                $("#coordinador").val(nombre);
            }
            coordinadorModal._modal.hide();
        }
        coordinadorModal._table.set('click', _selectCoordinador);
        $("#seleccionaCoor").click(function () {
            coordinadorModal._modal.show();
        });
        $('#coordinador').on('keyup', function () {
            interv.coordinador = null;
        });
        $("#referencia").on('change keyup', function (e) {
            e.preventDefault();
            interv.referencia = $(this).val();
        });
        $("#nroHistClinica").on('change keyup', function (e) {
            e.preventDefault();
            interv.nroHistClinica = $(this).val();
        });
        $("#componente").change(function (e) {
            e.preventDefault();
            interv.componente = {
                id: $(this).val()
            };
            _subComponenteSelectFill("#subcomponente", {componente: $(this).val()});
        });
        $("#subcomponente").change(function (e) {
            e.preventDefault();
            interv.subComponente = {
                id: $(this).val()
            };
            _productoSelectFill('#producto', $(this).val());
            _loadCoorDiag($(this).val());
        });
        function _loadCoorDiag(idSubComp) {
            if (idSubComp) {
                $("#coordinador").val('');
                var gtCoorOptions = {
                    url: '/' + idModulo + '/subcomponente/' + idSubComp + '/getCoordinador',
                    type: 'POST',
                    success: function (response) {
                        if (!response.hasError) {
                            var coor = response.data;
                            interv.coordinador = coor.personal;
                            var nombre = coor.nombres + ' ' + coor.apellidoPaterno + ' ' + coor.apellidoMaterno;
                            $("#coordinador").val(nombre);
                        }
                    }
                };
                _getList(gtCoorOptions);
                var gtDiagOptions = {
                    url: '/' + idModulo + '/subcomponente/' + idSubComp + '/getDiagnostico',
                    type: 'POST',
                    success: function (response) {
                        if (!response.hasError) {
                            var diag = response.data;
                            interv.diagnostico = diag;
                            var nombre = diag.descripcion;
                            $("#diagnostico").val(nombre);
                        }
                    }
                };
                _getList(gtDiagOptions);
            } else {
                $("#diagnostico").val("SIN DIAGNOSTICO");
                $("#coordinador").val('');
                interv.diagnostico = null;
                interv.coordinador = null;
            }
        }
        function _selectKit(row) {
            kitModal._modal.hide();
            if (row.id) {
                var url = row.id + '/obtenerProductosPorKit';
                var productos = [];
//                for (var i in interv.intervSanitariaProductos) {
//                    var producto = interv.intervSanitariaProductos[i].producto;
//                    productos.push(producto);
//                }
                window.gp.cleanTable();
                var data = JSON.stringify(productos);
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: data,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function (response) {
                        var prods = response.data;
                        interv.intervSanitariaProductos = [];
                        var total = 0;
                        for (var i in prods) {
                            if (prods.hasOwnProperty(i)) {
                                var prod = prods[i];
                                interv.intervSanitariaProductos.push(prod);
                                if (prod.stock > 0)
                                    total += prod.importeParcial;
                            }
                        }
                        if (total)
                            total = fieldFormat.money4(total);
                        else
                            total = '0.0000';
                        $("#fldtotal").val(total);
                        if (response.hasError) {
                            for (var i in response.mssg) {
                                if (response.mssg.hasOwnProperty(i))
                                    alertify.error(response.mssg[i]);
                            }
                        }

                        _actualizarTabla();
                    },
                    failure: function (response) {
                        window.console.log(response);
                    }
                });
            }
        }
        $("#proceso").on('change', function () {
            var proceso = $(this).val();
            var componente = $("#componente").val();
            var subComponente = $("#subcomponente").val();
            if (proceso && proceso.length > 0) {
                interv.proceso = {
                    id: proceso
                };
                var params = {
                    "proceso:id": proceso,
                    "componente:id": componente,
                    "subComponente:id": subComponente
                };
                if (!kitModal) {
                    kitModal = new KitsGeneral(idModulo, {params: params});
                    var url = '/' + window.location.pathname.split('/')[1] + '/dispensacion/' + idModulo + '/kitatencion/listarJSON';
                    kitModal.get('table').set('dataUrl', url);
                    kitModal._table.set('click', _selectKit);
                } else {
                    kitModal.set('params', {params: params});
                }
                kitModal.show();
            }
        });
        function _actualizarTabla() {
            $("#tblData-table").dataTable().fnClearTable();
            window.gp.addRow(interv.intervSanitariaProductos);
            $('.row-data-change').remove();
            $('body').undelegate('.row-data-edit', 'click');
            $('.row-data-edit').off('click');
            $("body").delegate('.row-data-edit', 'click', function (event) {
                event.preventDefault();
                var productoId = $(this).attr('data-id');
                var producto = _encontrarProducto(productoId);
                $("#mdl-form").find('#cantidad').val(producto.cantidad);
                $("#mdl-form").find('#producto').val(productoId);
                $("#mdl-form").find('#producto').prop('disabled', true);
                $('#modalData-modal').modal('show');
            });
            $('body').undelegate('.row-data-remove', 'click');
            $('.row-data-remove').off('click');
            $('body').delegate('.row-data-remove', 'click', function (event) {
                event.preventDefault();
                var productoId = $(this).attr('data-id');
                _eliminarProducto(productoId);
            });
        }
        function _eliminarProducto(productoId) {
            var index = null;
            for (var i in interv.intervSanitariaProductos) {
                var vProd = interv.intervSanitariaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    index = i;
                    break;
                }
            }
            if (index !== null) {
                smoke.confirm("¿Est&aacute; seguro que desea eliminar el producto " + vProd.producto.descripcion + "?", function (e) {
                    if (e) {
                        interv.intervSanitariaProductos.splice(index, 1);
                        _actualizarTabla();
                        zeroStocks.filter(function (elem, j) {
                            if (elem == productoId) {
                                zeroStocks.splice(j, 1);
                                zeroStockCount--;
                            }
                        });
                        $('#guardar-cambios-msg').removeClass('hidden');
                        $('#guardar-cambios-msg').show();
                    }
                });
            }
        }
        function _encontrarProducto(productoId) {
            for (var i in interv.intervSanitariaProductos) {
                var vProd = interv.intervSanitariaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    return interv.intervSanitariaProductos[i];
                }
            }
            return '';
        }
        function _actualizarProducto(productoId, cant) {
            var actualizado = false;
            for (var i in interv.intervSanitariaProductos) {
                var vProd = interv.intervSanitariaProductos[i];
                if (vProd.producto.idProducto == productoId) {
                    actualizado = true;
                    smoke.confirm("¿Est&aacute; seguro que desea actualizar el producto " + vProd.producto.descripcion + "?", function (e) {
                        if (e) {
                            var intervProducto = {
                                cantidad: cant,
                                precio: vProd.precio,
                                importeParcial: vProd.importeParcial,
                                producto: {
                                    idProducto: productoId,
                                    descripcion: vProd.producto.descripcion
                                }
                            };
                            interv.intervSanitariaProductos[i] = intervProducto;
                            _actualizarTabla();
                            $('#guardar-cambios-msg').removeClass('hidden');
                            $('#guardar-cambios-msg').show();
                        }
                    });
                }
            }
            return actualizado;
        }
        function _actualizarInterv(data) {
            interv = {
                nroSalida: data.nroSalida,
                fechaRegistro: data.fechaRegistro,
                diagnostico: data.diagnostico,
                IdModulo: data.IdModulo,
                nroHistClinica: data.nroHistClinica,
                componente: data.componente,
                subComponente: data.subComponente,
                proceso: data.proceso,
                referencia: data.referencia
            };

            if (data.cliente) {
                var cliente = {
                    idCliente: data.cliente.idCliente,
                    apellidoMaterno: data.cliente.apellidoMaterno,
                    nombre: data.cliente.nombre,
                    apellidoPaterno: data.cliente.apellidoPaterno
                };
                interv.cliente = cliente;
            }
            if (data.prescriptor) {
                var prescriptor = {
                    codigo: data.prescriptor.codigo,
                    idMedico: data.prescriptor.idMedico,
                    paterno: data.prescriptor.paterno,
                    materno: data.prescriptor.materno,
                    nombre: data.prescriptor.nombre
                };
                interv.prescriptor = prescriptor;
            }
            if (data.coordinador) {
                interv.coordinador = data.coordinador;
                if (data.coorPersonal) {
                    var nombreComp = data.coorPersonal.nombreCompleto.split(" ");
                    var l = nombreComp.length;
                    interv.datosCoor = {
                        idPersonal: data.coorPersonal.idPersonal,
                        nombre: ''
                    };
                    interv.datosCoor.apellidoPaterno = nombreComp[l - 2] || "";
                    interv.datosCoor.apellidoMaterno = nombreComp[l - 1] || "";
                    for (var i = 0; i < l - 2; i++) {
                        interv.datosCoor.nombre += nombreComp[i] + ' ';
                    }
                    interv.datosCoor.nombre = interv.datosCoor.nombre.trim();
                } else if (data.datosCoor) {
                    interv.datosCoor = {
                        idPersonal: data.coorPersonal.idPersonal,
                        nombre: data.coorPersonal.nombre,
                        apellidoPaterno: data.coorPersonal.apellidoPaterno,
                        apellidoMaterno: data.coorPersonal.apellidoMaterno
                    };
                }
            } else
                interv.coordinador = null;
            var vProds = [];
            for (var i in data.intervSanitariaProductos) {
                var tmp = data.intervSanitariaProductos[i];
                var vProd = {
                    activo: tmp.activo,
                    cantidad: tmp.cantidad,
                    idProducto: tmp.idProducto,
                    idIntervSanitaria: tmp.idVenta,
                    precio: tmp.precio,
                    importeParcial: tmp.importeParcial,
                    producto: {
                        idProducto: tmp.producto.idProducto,
                        descripcion: tmp.producto.descripcion
                    }
                };
                vProds.push(vProd);
            }
            interv.intervSanitariaProductos = vProds;
        }
        function _guardarCambios() {
//            if (!interv.cliente) {
//                alertify.error("Debe seleccionar un cliente.");
//                return;
//            }
//            if (!interv.prescriptor) {
//                alertify.error("Debe seleccionar un prescriptor");
//                return;
//            }
            if (zeroStockCount > 0) {
                alertify.error("Hay productos sin stock en el listado. Por favor remplazelos");
                return;
            }
            if (!interv.proceso) {
                alertify.error("Debe seleccionar un proceso");
                return;
            }
            if (!interv.coordinador) {
                alertify.error("Debe seleccionar un coordinador");
                return;
            }
            if (interv.intervSanitariaProductos.length == 0) {
                alertify.error("Debe existir al menos un producto, por favor seleccione un Kit de Atenci&oacute;n para agregar sus productos o seleccionelos manualmente.");
                return false;
            }
            if (!interv.componente) {
                interv.componente = {
                    id: $("#componente").val()
                };
            }
            if (!interv.subComponente) {
                interv.subComponente = {
                    id: $("#subcomponente").val()
                };
            }
            $.ajax({
                url: 'guardarCambios',
                type: 'POST',
                data: JSON.stringify(interv),
                dataType: 'json',
                contentType: 'application/json',
                success: function (response) {
                    zeroStocks = [];
                    zeroStockCount = 0;
                    alertify.showMessage = alertify.success;
                    if (response.hasError)
                        alertify.showMessage = alertify.error;
                    else {
                        _actualizarInterv(response.data);
                        $("#nroSalida").val(interv.nroSalida);
                        $('#guardar-cambios-msg').hide();
                        $("#btnAnular").show();
                    }
                    for (var i in response.mssg) {
                        alertify.showMessage(response.mssg[i]);
                    }
                },
                failure: function (response) {
                }
            });
        }
        function _actualizarForm() {
            if (interv.nroSalida)
                $("#nroSalida").val(interv.nroSalida);
            if (interv.nroHistClinica)
                $("#nroHistClinica").val(interv.nroHistClinica);
            if (interv.cliente)
                $("#cliente").val(interv.cliente.nombre + " " + interv.cliente.apellidoPaterno + " " + interv.cliente.apellidoMaterno);
            if (interv.prescriptor)
                $("#prescriptor").val(interv.prescriptor.nombre + " " + interv.prescriptor.paterno + " " + interv.prescriptor.materno);
            if (interv.diagnostico)
                $("#diagnostico").val(interv.diagnostico.descripcion);
            if (interv.fechaRegistro) {
                var tmp = new Date(interv.fechaRegistro);
                $("#fechaRegistro").val(tmp.toLocaleDateString());
            }
            if (interv.componente) {
                $("#componete").val(interv.componente.id);
            }
            if (interv.subComponente) {
                $("#subcomponente").val(interv.subComponente.id);
                _productoSelectFill('#producto');
            }
            if (interv.proceso) {
                $("#proceso").val(interv.proceso.id);
                _remakeChz("#proceso");
            }
            if (interv.coordinador && interv.datosCoor) {
                var nombre = interv.datosCoor.nombre + " " + interv.datosCoor.apellidoPaterno + " " + interv.datosCoor.apellidoMaterno;
                $("#coordinador").val(nombre);
            }
            if (interv.referencia)
                $("#referencia").val(interv.referencia);

        }
        $('body').undelegate('#btnGuardar', 'click');
        $("#btnGuardar").click(function (e) {
            e.preventDefault();
            var modalForm = $("#mdl-form");
            var validation = modalForm.validate();
            if (!validation.checkForm()) {
                validation.showErrors();
                modalForm.find('.error').parent().addClass('has-error');
                modalForm.find('label.error').addClass('control-label');
                return;
            }

            var cant = $("#mdl-form").find("#cantidad").val();
            var productoId = $("#mdl-form").find("#producto").val();
            var dataToSend = {
                cantidad: cant,
                producto: {
                    idProducto: productoId
                }
            };

            modalForm.find('.valid').parent('.has-error').removeClass('has-error');
            modalForm.find('.has-error').removeClass('has-error');

            $.ajax({
                url: "obtenerProducto",
                data: JSON.stringify(dataToSend),
                type: "POST",
                dataType: 'json',
                contentType: "application/json",
                success: function (r) {
                    if (r.data) {
                        if (r.data.stock > 0) {
                            var actualizado = _actualizarProducto(r.data.producto.idProducto, r.data.cantidad);
                            if (!actualizado) {
                                interv.intervSanitariaProductos.push({
                                    cantidad: r.data.cantidad,
                                    precio: r.data.precio,
                                    importeParcial: r.data.importeParcial,
                                    producto: {
                                        idProducto: r.data.producto.idProducto,
                                        descripcion: r.data.producto.descripcion
                                    }
                                });
                            }
                            _actualizarTabla();

                            var total = 0;
                            if (r.data.stock > 0)
                                total += r.data.importeParcial;
                            if (total)
                                total = fieldFormat.money4(total);
                            else
                                total = '0.0000';
                            $("#fldtotal").val(total);
                        } else {
                            alertify.error("Este producto no tiene stock disponible");
                        }
                    }
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
                failure: function (r) {
                    alertify.error(r.mssg[0]);
                }
            });
        });
        $("#btnGuardarCambios").click(function (e) {
            e.preventDefault();
            _guardarCambios();
        });
        $("#btnNuevo").click(function (e) {
            e.preventDefault();
            interv = {
                nroSalida: null,
                fechaRegistro: fechaRegistro.getTime(),
                intervSanitariaProductos: []
            };
            $("#nroSalida").val("");
            $("#cliente").val("");
            $("#nroHistClinica").val("");
            $("#prescriptor").val("");
            $("#diagnostico").val("SIN DIAGNOSTICO");
            $("#proceso").val("");
            $("#coordinador").val("");
            $("#referencia").val("");
            $("#tblData-table").dataTable().fnClearTable();
            $("#btnAnular").hide();
            $("#proceso").val('');
            _remakeChz("#proceso");
            $("#fldtotal").val('');
        });
        $("#mdl-form").validate({
            rules: {
                cantidad: {
                    required: true,
                    digits: true
                },
                producto: {
                    required: true
                }
            },
            messages: {
                cantidad: {
                    required: "Este campo es obligatoro",
                    digits: "Solo se admiten n&uacute;meros"
                },
                producto: {
                    required: "Este campo es obligatorio"
                }
            }
        });
        function _setPathName(path) {
            var myLocation = window.location.href.split('/'),
                    result = "";
            myLocation.pop();

            for (var key in myLocation) {
                if (myLocation.hasOwnProperty(key)) {
                    result += myLocation[key] + "/";
                }
            }
            result += path;
            window.location.href = result;
        }
        $("body").undelegate("#btnPDF", 'click');
        $("body").undelegate("#btnExcel", 'click');
        $("#btnExcel").off('click');
        $("#btnPDF").click(function (e) {
            e.preventDefault();
            if (!interv.nroSalida) {
                alertify.error("No hay Intervenci&oacute;n Sanitaria cargada");
                return;
            }
            _setPathName(interv.nroSalida + "/reportePdf");
        });
        $("#btnExcel").click(function (e) {
            e.preventDefault();
            if (!interv.nroSalida) {
                alertify.error("No hay Intervenci&oacute;n Sanitaria cargada");
                return;
            }
            _setPathName(interv.nroSalida + "/reporteExcel");
        });
        $("#btnAnular").click(function (e) {
            e.preventDefault();
            if (interv.nroSalida != null) {
                smoke.confirm("Est&aacute; seguro que desea anular la salida #" + interv.nroSalida, function (e) {
                    if (e) {
                        $.ajax({
                            url: 'anularIntervSanitaria',
                            type: 'POST',
                            data: "nroSalida=" + interv.nroSalida,
                            success: function (response) {
                                alertify.showMessage = alertify.error;
                                if (!response.hasError) {
                                    $("#btnNuevo").click();
                                    alertify.showMessage = alertify.success;
                                }
                                for (var i in response.mssg) {
                                    if (response.mssg.hasOwnProperty(i))
                                        alertify.showMessage(response.mssg[i]);
                                }
                            },
                            failure: function (response) {
                                window.console.log(response);
                            }
                        });
                    }
                });
            } else {
                alertify.error("No hay Salida por Intervenci&oacute;n Sanitaria seleccionada.");
            }
        });
        $("#btnConsultar").click(function (e) {
            e.preventDefault();
            if ($("#nroSalida").val()) {
                $.ajax({
                    url: 'filtrarPorNroSalida',
                    type: 'POST',
                    data: "nroSalida=" + $("#nroSalida").val(),
                    dataType: 'json',
                    success: function (response) {
                        alertify.showMessage = alertify.error;
                        zeroStocks = [];
                        zeroStockCount = 0;
                        if (!response.hasError) {
                            $("#btnNuevo").click();
                            alertify.showMessage = alertify.success;
                            _actualizarInterv(response.data);
                            _actualizarForm();
                            _actualizarTabla();
                            $("#btnAnular").show();
                        }
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.showMessage(response.mssg[i]);
                        }
                    },
                    failure: function (response) {
                    }
                });
            } else {
                alertify.error("Debe escribir un n&uacute;mero de salida.");
            }
        });
        $("#btnVerStocks").click(function (e) {
            e.preventDefault();
            if (interv.intervSanitariaProductos.length > 0) {
                $.ajax({
                    url: 'verStocks',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(interv.intervSanitariaProductos),
                    success: function (response) {
                        var stocks = response.data;
                        var tb = $("#verstocks-mdl").find('table tbody');
                        tb.children().remove();
                        for (var i in interv.intervSanitariaProductos) {
                            if (interv.intervSanitariaProductos.hasOwnProperty(i)) {
                                var row = $('<tr></tr>');
                                row.append('<td>' + interv.intervSanitariaProductos[i].producto.descripcion + '</td>');
                                row.append('<td>' + stocks[i] + '</td>');
                                tb.append(row);
                            }
                        }
                        $("#verstocks-mdl").modal('show');
                    },
                    failure: function (response) {

                    }
                });
            } else {
                alertify.error("Debe agregar productos");
            }
        });
        $("#mdl-form").validate({
            rules: {
                cantidad: {required: true, digits: true},
                producto: {required: true}
            },
            messages: {
                cantidad: {
                    required: "Este campo es obligatorio",
                    digits: "Este campo solo admite d&iacute;gitos"
                },
                producto: {
                    required: "Este campo es obligatorio"
                }
            }
        });
        function _productoSelectFill(selector, idSubComp) {
            var idSubComponente = idSubComp || $("#subcomponente option:first").attr('value');
            if (idSubComponente) {
                var options = {
                    url: '/' + idModulo + '/subcomponente/' + idSubComponente + '/productosAllJSON',
                    type: 'POST',
                    success: function (r, status, ajaxData) {
                        var select = $(selector);
                        select.children().remove();
                        for (var key in r) {
                            if (r.hasOwnProperty(key)) {
                                var td = r[key];
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
                                var td = r[k];
                                $(elem).attr('data-stock', td.stock);
                                $(elem).attr('data-id', td.idProducto);
                                k++;
                            }
                        });
                    }
                };
                _getList(options);
            }
        }
        function _subComponenteSelectFill(selector, data) {
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

                    _productoSelectFill('#producto');
                    _loadCoorDiag($(selector + " option:first").attr('value'));
                },
                data: data
            };
            _getList(options);
        }
        function _remakeChz(selector) {
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
    });
</script>