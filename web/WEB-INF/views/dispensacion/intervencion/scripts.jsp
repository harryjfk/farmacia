<script>
    $(document).ready(function () {
        var type = '${type}';
        crearCalendar('#fechaInt');
        _ProductoSelectFill('#producto');
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        var medicoModal = new MedicoGeneral(5); //el 5 es el id del submodulo Farmacia de Centro Quirurjico TODO: mala practica
        medicoModal._table.set('click', _selectMedico);
        var intervencion = {
            id: 0,
            paciente: null,
            activo: 0,
            especialidad: null,
            intervProductos: [],
            medico: null,
            atendida: 0,
            programada: 0
        }, editProd, especialidadModal;
        $('#btnAgregar').html("Agregar Producto");
        $('#intfilterfrm').submit(function (evt) {
            evt.preventDefault();
        });
        $("#intfilterbtn").click(function (evt) {
            evt.preventDefault();
            if (!_validateForm()) {
                return;
            }
            _actualizarInterv();
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
        $('#' + medicoModal._modal.get('id')).on('show.bs.modal', function () {
            $("#med-pull-from-personal").remove();
        });
        $('#especialidad').on('change keyup focusout', function () {
            intervencion.especialidad = $(this).val();
            _validateForm("especialidad");
        });
        $('#atendida').change(function () {
            var val = $(this).prop('checked');
            intervencion.atendida = val ? 1 : 0;
        });
        $('#programada').change(function () {
            var val = $(this).prop('checked');
            intervencion.programada = val ? 1 : 0;
        });
        $('#fechaInt').on('change keyup', function () {
            var d = $(this).val().split('/');
            if (!d || d.length == 1)
                return false;
            var anno = d[2], mes = d[1] - 1, dia = d[0];
            var date = new Date(anno, mes, dia);
            if (date != NaN)
                intervencion.fechaIntervencion = date.getTime();
            _validateForm("fechaInt");
        });
        $("#intsavebtn").click(function (evt) {
            evt.preventDefault();
            if (intervencion.intervProductos.length == 0) {
                alertify.error("Debe ingresar al menos un medicamento/material.");
                return;
            }
            if (!_validateForm())
                return;
            intervencion = _cleanData(intervencion);
            var data = JSON.stringify(intervencion);
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
                        _llenarIntervencion(response);
                        $('#tblData-table').dataTable().fnClearTable();
                        window.gp.addRow(intervencion.intervProductos);
                        _hideCambiarEstado();
                        _addListeners();
                        if (intervencion.activo) {
                            $("#intanularbtn").text("Desactivar");
                        } else {
                            $("#intanularbtn").text("Activar");
                        }
                        $("#intanularbtn").show();
                    } else {
//                        $("#intanularbtn").hide();
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
                    intervencion.intervProductos.push(editProd);
                }
                $("#intsavebtn").click();
                editProd = null;
                $('#modalData-modal').modal('hide');
            } else if ($('#mdl-form').attr('action') == 'insertar') {
                var prodId = $("#mdl-form").find('#producto').val();
                var cantidad = $("#mdl-form").find('#cantidad').val();
                var producto = {"producto": {"idProducto": prodId}, "cantidad": cantidad};
                var esNuevo = true;
                for (var key in intervencion.intervProductos) {
                    if (intervencion.intervProductos.hasOwnProperty(key)) {
                        var prod = intervencion.intervProductos[key];
                        if (prod.producto.idProducto == prodId) {
                            intervencion.intervProductos[key] = producto;
                            esNuevo = false;
                        }
                    }
                }
                if (esNuevo) {
                    intervencion.intervProductos.push(producto);
                }
                alertify.success("Debe hacer clic en Guardar Cambios para actualizar la tabla de productos.");
                $('#intsavebtn').prop('disabled', false);
            }
        });
        function _getFilterData() {
            var params = _getFilterFields();
            params['intervencion:especialidad'] = intervencion.especialidad;
            params['intervencion:fechaIntervencion'] = intervencion.fechaIntervencion;
            params['intervencion:paciente:paciente'] = intervencion.paciente.paciente;
            params['intervencion:medico:idPrescriptor'] = intervencion.medico.idPrescriptor;
            params['intervencion:id'] = intervencion.id;
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
        $("#intanularbtn").click(function (e) {
            e.preventDefault();
            var atv = !intervencion.activo;
            intervencion.activo = atv ? 1 : 0;
            var txt = atv ? "Activar" : "Desactivar";
            $(this).text(txt);
            $("#intsavebtn").click();
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
                        intervencion = _cleanData(intervencion);
                        var data = JSON.stringify(intervencion);
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
                                    _llenarIntervencion(response);
                                    $('#tblData-table').dataTable().fnClearTable();
                                    window.gp.addRow(intervencion.intervProductos);
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
            for (var key in intervencion.intervProductos) {
                prod = intervencion.intervProductos[key];
                if (prod.producto.idProducto == idProducto)
                    return prod;
            }
            return null;
        }
        function _eliminarProducto(p) {
            if (typeof p == 'object')
                p = p.producto.idProducto;
            for (var key in intervencion.intervProductos) {
                prod = intervencion.intervProductos[key];
                if (prod.producto.idProducto == p)
                    intervencion.intervProductos.splice(key, 1);
            }
        }
        function _actualizarProducto(intervProd) {
            for (var key in intervencion.intervProductos) {
                prod = intervencion.intervProductos[key];
                if (prod.producto.idProducto == intervProd.idProducto)
                    intervencion.intervProductos[key] = intervProd;
            }
        }
        $.validator.addMethod("date", function (value, element) {
            var date = value.split('/');
            if (!date || date.length == 1)
                return false;
            var anno = date[2], mes = date[1] - 1, dia = date[0];
            var areNumbers = !/NaN/.test(parseInt(anno)) && !/NaN/.test(parseInt(mes)) && !/NaN/.test(parseInt(dia));
            if (!areNumbers)
                return false;
            if (dia <= 0 || mes < 0 || anno <= 0 || mes > 11)
                return false;
            if (mes < 7) {
                if ((mes % 2 == 0) && dia > 31)
                    return false;
                if (mes % 2 != 0 && dia > 30)
                    return false;
                if (mes == 1 && dia > 29)//TODO: falta el anno bisiesto
                    return false;
                if (mes % 2 != 0 && dia > 30)
                    return false;
            } else {
                if (mes % 2 == 0 && dia > 30)
                    return false;
                if (mes % 2 != 0 && dia > 31)
                    return false;
            }
            var isDate = !/Invalid|NaN/.test(new Date(anno, mes, dia));
            return this.optional(element) || (areNumbers && isDate);
        });
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
                intervencion.paciente = row;
                $("#paciente").val(nombre);
            }
            pacienteModal._modal.hide();
            _validateForm("paciente");
        }
        function _selectMedico(row) {
            if (row.idMedico) {
                intervencion.medico = row;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#medico").val(nombre);
            }
            medicoModal._modal.hide();
            _validateForm("medico");
        }
        function _actualizarInterv() {
            if (intervencion.paciente != null)
                intervencion.paciente = _cleanData(intervencion.paciente);
            if (intervencion.medico != null)
                intervencion.medico = _cleanData(intervencion.medico);
            if (intervencion.activo) {
                $("#intanularbtn").text("Desactivar");
            } else {
                $("#intanularbtn").text("Activar");
            }
            var data = JSON.stringify(intervencion);
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
                        _llenarIntervencion(response);
                        if (intervencion.activo) {
                            $("#intanularbtn").text("Desactivar");
                        } else {
                            $("#intanularbtn").text("Activar");
                        }
                        if (intervencion.id)
                            $("#intanularbtn").show();
                        $('#tblData-table').dataTable().fnClearTable();
                        $('body').triggerHandler('addRow', [intervencion.intervProductos]);
                    } else {
                        for (var i in response.mssg) {
                            alertify.error(response.mssg[i]);
                        }
                    }

                },
                failure: function (response) {
                }
            });
        }
        function _crearValidador() {
            if (type == "especialidad") {
                $('#intfilterfrm').validate({
                    rules: {
                        paciente: {
                            required: true
                        },
                        medico: {
                            required: true
                        },
                        especialidad: {
                            required: true
                        },
                        fechaInt: {
                            required: true,
                            date: true
                        }
                    },
                    messages: {
                        paciente: {
                            required: 'Este campo es obligatorio'
                        },
                        medico: {
                            required: 'Este campo es obligatorio'
                        },
                        especialidad: {
                            required: 'Este campo es obligatorio'
                        },
                        fechaInt: {
                            required: 'Este campo es obligatorio',
                            date: 'Por favor escriba una fecha v&aacute;lida'
                        }
                    }
                });
            } else {
                $('#intfilterfrm').validate({
                    rules: {
                        paciente: {
                            required: true
                        },
                        medico: {
                            required: true
                        },
                        fechaInt: {
                            required: true,
                            date: true
                        }
                    },
                    messages: {
                        paciente: {
                            required: 'Este campo es obligatorio'
                        },
                        medico: {
                            required: 'Este campo es obligatorio'
                        },
                        fechaInt: {
                            required: 'Este campo es obligatorio',
                            date: 'Por favor escriba una fecha v&aacute;lida'
                        }
                    }
                });
            }
        }
        function _llenarIntervencion(response) {
            intervencion.id = response.data.id;
            intervencion.especialidad = response.data.especialidad;
            intervencion.fechaIntervencion = response.data.fechaIntervencion;
            intervencion.programada = response.data.programada;
            intervencion.atendida = response.data.atendida;
            intervencion.activo = response.data.activo;
            if (response.data.paciente) {
                var paciente = {
                    paciente: response.data.paciente.paciente,
                    nombre: response.data.paciente.nombre,
                    paterno: response.data.paciente.paterno,
                    materno: response.data.paciente.materno
                };
                intervencion.paciente = paciente;
            }
            if (response.data.medico) {
                var medico = {
                    idMedico: response.data.medico.idMedico,
                    nombre: response.data.medico.nombre,
                    paterno: response.data.medico.paterno,
                    materno: response.data.medico.materno
                };
                intervencion.medico = medico;
            }
            if (response.data.especialidad)
                intervencion.especialidad = response.data.especialidad;
            if (response.data.intervProductos) {
                prods = response.data.intervProductos;
                intervencion.intervProductos = [];
                for (var i in prods) {
                    intervencion.intervProductos.push(prods[i]);
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
                var tmp = dataCleaner[type](interv[field]);
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
                $('#intsavebtn').prop('disabled', true);
                $('#btnPDF').prop('disabled', true);
                $('#btnExcel').prop('disabled', true);
                return false;
            } else {
                $('#btnPDF').prop('disabled', false);
                $('#btnExcel').prop('disabled', false);
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
    });
</script>