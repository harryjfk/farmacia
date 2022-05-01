<script>
    $(document).ready(function () {
        _ProductoSelectFill('#producto');
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        var medicoModal = new MedicoGeneral(5); //el 5 es el id del submodulo Farmacia de Centro Quirurjico TODO: mala practica
        medicoModal._table.set('click', _selectMedico);
        var compraDeUrgencia = {
            id: 0,
            paciente: null,
            medico: null,
            compraProductos: []
        }, editProd;
        $('#btnAgregar').html("Agregar Producto");
        $('#intfilterfrm').submit(function (evt) {
            evt.preventDefault();
        });
        $("#intfilterbtn").click(function (evt) {
            evt.preventDefault();
            if (!_validateForm()) {
                return;
            }
            _actualizarCompra();
        });
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
        });
        $("#seleccionaMed").click(function () {
            medicoModal._modal.show();
        });
        $('#medico').on('change', function () {
        });
        $("#intsavebtn").click(function (evt) {
            evt.preventDefault();
            if (compraDeUrgencia.compraProductos.length == 0) {
                alertify.error("Debe ingresar al menos un medicamento/material.");
                return;
            }
            if (!_validateForm())
                return;
            compraDeUrgencia = _cleanData(compraDeUrgencia);
            var data = JSON.stringify(compraDeUrgencia);
            $.ajax({
                url: 'guardarCambios',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: data,
                success: function (response) {
                    if (!response.hasError) {
                        for (var i in response.mssg) {
                            alertify.success(response.mssg[i]);
                        }
                        _llenarCompra(response);
                        $('#tblData-table').dataTable().fnClearTable();
                        window.gp.addRow(compraDeUrgencia.compraProductos);
                        _hideCambiarEstado();
                        _addListeners();
                    } else {
                        for (var i in response.mssg) {
                            alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function (response) {
                    window.console.error(response);
                }
            });
        });
        $('#tblData-table').bind({
            Cargada: function (evt) {
                evt.preventDefault();
                _hideCambiarEstado();
                _addListeners();
            }
        });
        $('#btnGuardar').click(function (evt) {
            if ($('#mdl-form').attr('action') == 'modificar') {
                evt.stopPropagation();
                var cantidad = $('#mdl-form').find('#cantidad').val();
                var idProducto = $('#mdl-form').find('#producto').val();
                if (idProducto == editProd.producto.idProducto) {
                    editProd.cantidad = cantidad;
                    _actualizarProducto(editProd);
                } else {
                    _eliminarProducto(editProd);
                    editProd = {
                        cantidad: cantidad,
                        producto: {
                            idProducto: idProducto
                        }
                    };
                    compraDeUrgencia.compraProductos.push(editProd);
                }
                $("#intsavebtn").click();
                editProd = null;
                $('#modalData-modal').modal('hide');
            } else if ($('#mdl-form').attr('action') == 'insertar') {
                var prodId = $("#mdl-form").find('#producto').val();
                var cantidad = $("#mdl-form").find('#cantidad').val();
                var producto = {"producto": {"idProducto": prodId}, "cantidad": cantidad};
                var esNuevo = true;
                for (var key in compraDeUrgencia.compraProductos) {
                    if (compraDeUrgencia.compraProductos.hasOwnProperty(key)) {
                        var prod = compraDeUrgencia.compraProductos[key];
                        if (prod.producto.idProducto == prodId) {
                            compraDeUrgencia.compraProductos[key] = producto;
                            esNuevo = false;
                        }
                    }
                }
                if (esNuevo) {
                    compraDeUrgencia.compraProductos.push(producto);
                }
                alertify.success("Debe hacer clic en Guardar Cambios para actualizar la tabla de productos.");
            }
        });
        function _getFilterData() {
            var params = _getFilterFields();
            if (compraDeUrgencia.paciente) {
                params['compraDeUrgencia:paciente:paciente'] = compraDeUrgencia.paciente.paciente;
            }
            if (compraDeUrgencia.medico) {
                params['compraDeUrgencia:medico:idPrescriptor'] = compraDeUrgencia.medico.idPrescriptor;
            }
            params['compraDeUrgencia:id'] = compraDeUrgencia.id;
            var table = $('#tblData-table').dataTable();
            var count = table.fnPagingInfo().iLength;
            var start = table.fnPagingInfo().iStart;
            var fData = {
                'params': params,
                'start': start,
                'count': count
            };
            return fData;
        }
        $('#btnPDF').off('click');
        $('#btnPDF').click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var fData = _getFilterData();
            fData = JSON.stringify(fData);
            $.ajax({
                url: 'prepararReporte',
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: fData,
                success: function (response) {
                    _setPathName('reportePdf');
                },
                failure: function (r) {
                    for (var i in r.mssg)
                        if (r.mssg.hasOwnProperty(i))
                            alertify.error(r.mssg[i]);
                }
            });
        });
        $('#btnExcel').off('click');
        $('#btnExcel').click(function (e) {
            e.preventDefault();
            e.stopPropagation();
            var fData = _getFilterData();
            fData = JSON.stringify(fData);
            $.ajax({
                url: 'prepararReporte',
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: fData,
                success: function (response) {
                    _setPathName('reporteExcel');
                },
                failure: function (r) {
                    for (var i in r.mssg)
                        if (r.mssg.hasOwnProperty(i))
                            alertify.error(r.mssg[i]);
                }
            });
        });
        function _addListeners() {
            $('.row-data-edit').click(function (evt) {
                evt.preventDefault();
                var idProducto = $(this).attr('data-id');
                var producto = _encontrarProducto(idProducto);
                editProd = producto;
                $('#mdl-form').find('#cantidad').val(producto.cantidad);
                $('#mdl-form').find('#producto').val(idProducto);
            });
            $('.row-data-remove').click(function (evt) {
                evt.preventDefault();
                evt.stopPropagation();
                var idProducto = $(this).attr('data-id');
                smoke.confirm('¿Está seguro que desea eliminar este producto?', function (e) {
                    if (e) {
                        _eliminarProducto(idProducto);
                        compraDeUrgencia = _cleanData(compraDeUrgencia);
                        var data = JSON.stringify(compraDeUrgencia);
                        $.ajax({
                            url: 'guardarCambios',
                            type: 'POST',
                            dataType: 'json',
                            contentType: 'application/json',
                            data: data,
                            success: function (response) {
                                if (!response.hasError) {
                                    for (var i in response.mssg) {
                                        alertify.success(response.mssg[i]);
                                    }
                                    _llenarCompra(response);
                                    $('#tblData-table').dataTable().fnClearTable();
                                    window.gp.addRow(compraDeUrgencia.compraProductos);
                                    _hideCambiarEstado();
                                    _addListeners();
                                } else {
                                    for (var i in response.mssg) {
                                        alertify.error(response.mssg[i]);
                                    }
                                }
                            },
                            failure: function (response) {
                                window.console.error(response);
                            }
                        });
                    }
                });
            });
        }
        function _encontrarProducto(idProducto) {
            for (var key in compraDeUrgencia.compraProductos) {
                prod = compraDeUrgencia.compraProductos[key];
                if (prod.producto.idProducto == idProducto)
                    return prod;
            }
            return null;
        }
        function _eliminarProducto(p) {
            if (typeof p == 'object')
                p = p.producto.idProducto;
            for (var key in compraDeUrgencia.compraProductos) {
                prod = compraDeUrgencia.compraProductos[key];
                if (prod.producto.idProducto == p)
                    compraDeUrgencia.compraProductos.splice(key, 1);
            }
        }
        function _actualizarProducto(compraProd) {
            for (var key in compraDeUrgencia.compraProductos) {
                prod = compraDeUrgencia.compraProductos[key];
                if (prod.producto.idProducto == compraProd.idProducto)
                    compraDeUrgencia.compraProductos[key] = compraProd;
            }
        }
        $('#intfilterfrm').find('input').on('change', function () {
            if ($(this).hasClass('valid')) {
                $(this).parent().removeClass('has-error');
            }
        });
        $('#mdl-form').validate({
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
                    required: 'Este campo es obligatorio',
                    digits: 'La cantidad debe ser un n&uacute;mero'
                },
                producto: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                compraDeUrgencia.paciente = row;
                $("#paciente").val(nombre);
            }
            pacienteModal._modal.hide();
            _validateForm("paciente");
        }
        function _selectMedico(row) {
            if (row.idMedico) {
                compraDeUrgencia.medico = row;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#medico").val(nombre);
            }
            medicoModal._modal.hide();
            _validateForm("medico");
        }
        function _actualizarCompra() {
            if (compraDeUrgencia.paciente != null)
                compraDeUrgencia.paciente = _cleanData(compraDeUrgencia.paciente);
            if (compraDeUrgencia.medico != null)
                compraDeUrgencia.medico = _cleanData(compraDeUrgencia.medico);
            var data = JSON.stringify(compraDeUrgencia);
            $.ajax({
                url: 'getProductos',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: data,
                success: function (response) {
                    if (!response.hasError) {
                        for (var i in response.mssg) {
                            alertify.success(response.mssg[i]);
                        }
                        _llenarCompra(response);
                        $('#tblData-table').dataTable().fnClearTable();
                        window.gp.addRow(compraDeUrgencia.compraProductos);
                        _hideCambiarEstado();
                        _addListeners();
                    } else {
                        for (var i in response.mssg) {
                            alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function (response) {
                    window.console.log(response);
                },
                error: function (response) {
                    window.console.log(response);
                }
            });
        }
        function _crearValidador() {
            $('#intfilterfrm').validate({
                rules: {
                    paciente: {
                        required: true
                    },
                    medico: {
                        required: true
                    }
                },
                messages: {
                    paciente: {
                        required: 'Este campo es obligatorio'
                    },
                    medico: {
                        required: 'Este campo es obligatorio'
                    }
                }
            });
        }
        function _llenarCompra(response) {
            compraDeUrgencia.id = response.data.id;
            if (response.data.paciente) {
                var paciente = {
                    paciente: response.data.paciente.paciente,
                    nombre: response.data.paciente.nombre,
                    paterno: response.data.paciente.paterno,
                    materno: response.data.paciente.materno
                };
                compraDeUrgencia.paciente = paciente;
            }
            if (response.data.medico) {
                var medico = {
                    idMedico: response.data.medico.idMedico,
                    nombre: response.data.medico.nombre,
                    paterno: response.data.medico.paterno,
                    materno: response.data.medico.materno
                };
                compraDeUrgencia.medico = medico;
            }
            if (response.data.compraUrgenciaProductos) {
                prods = response.data.compraUrgenciaProductos;
                compraDeUrgencia.compraProductos = [];
                for (var i in prods) {
                    compraDeUrgencia.compraProductos.push(prods[i]);
                }
            }
        }
        var dataCleaner = {
            'object': function (value) {
                var data = {};
                if (value && value.length != undefined)
                    data = [];
                if (value !== null && value !== undefined && value !== 'undefined') {
                    for (var fld in value) {
                        var type = typeof value[fld];
                        var tmp = dataCleaner[type](value[fld]);
                        if (tmp !== null) {
                            data[fld] = tmp;
                        }
                    }
                    return data;
                } else {
                    return null;
                }
            },
            'string': function (value) {
                return (value !== "null" && value !== 'undefined') ? value : null;
            },
            'undefined': function (value) {
                return null;
            },
            'number': function (value) {
                return value;
            },
            'boolean': function (value) {
                return value;
            }
        };
        function _cleanData(interv) {
            var data = {};
            if (interv && interv.length != undefined)
                data = [];
            for (var field in interv) {
                var type = typeof interv[field];
                tmp = dataCleaner[type](interv[field]);
                if (tmp !== null)
                    data[field] = tmp;
            }
            return data;
        }
        function _hideCambiarEstado() {
            $('.row-data-change').remove();
        }
        function _validateForm(field) {
            var form = $('#intfilterfrm');
            var validation = form.validate();
            validation.resetForm();
            if (!validation.checkForm()) {
                validation.showErrors();
                if (!field) {
                    form.find('.error').each(function (i, elem) {
                        if (!$(elem).parent().hasClass('has-error'))
                            $(elem).parent().addClass('has-error');
                    });
                    form.find('label.error').each(function (i, elem) {
                        if (!$(elem).hasClass('control-label'))
                            $(elem).addClass('control-label');
                    });
                } else {
                    form.find('input.error').each(function (i, elem) {
                        if ($(elem).attr('id') != field)
                            $(elem).removeClass('error');
                    });
                    form.find('label.error').each(function (i, elem) {
                        if ($(elem).parent().find('.error').attr('id') != field)
                            $(elem).remove();
                    });
                    $('#' + field).parent().addClass('has-error');
                    $('#' + field).addClass('control-label');
                    form.find('.valid').parent().removeClass('has-error');
                }

                $('.form-horizontal .control-label').css({"text-align": "left"});
                return false;
            }
            form.find('.valid').parent('.has-error').removeClass('has-error');
            form.find('.has-error').removeClass('has-error');
            return true;
        }
        function _getFilterFields() {
            var fFields = $('.table-filter');
            var params = new Object();
            var tblProperties = '${tableProperties}'.split(',');
            tblProperties.splice(0, 1);
            fFields.each(function (i, elem) {
                if ($(elem).val()) {
                    var fName = tblProperties[i].replace('.', ':');
                    params[fName] = $(elem).val();
                }
            });
            return params;
        }
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

        _crearValidador();
    }
    );
</script>