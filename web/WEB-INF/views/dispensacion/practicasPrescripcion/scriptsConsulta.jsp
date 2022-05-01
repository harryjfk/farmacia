<script>
    $(document).ready(function() {
        $("body").addClass('no-change');
        $("#btnAgregar").hide();
        $("body").undelegate('#btnGuardar', 'click');
        _ProductoSelectFill("#producto");
        var periodo = new Date();
        $("#periodo").val(periodo.getFullYear());
        crearCalendar('#fechaRegistro');
        var idModulo = location.pathname.split('/')[3];
        fData = {
            params: {'periodo': periodo.getFullYear()}
        };
        $("#mdl-form").validate({
            rules: {
                cantidad: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                cantidad: {
                    required: "Este campo es obligatorio",
                    digits: "Este campo solo admite d&iacute;gitos"
                }
            }
        });
        var clienteModal = new ClienteGeneral(idModulo);
        function _selectCliente(row) {
            if (row.idCliente) {
                fData.params['cliente:idCliente'] = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
            }
            clienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function() {
            clienteModal._modal.show();
        });
        $('#cliente').on('keyup', function() {
            _borrarProp('cliente:idCliente');
        });

        var diagnosticoModal = new DiagnosticoGeneral();
        function _selectDiagnostico(row) {
            if (row.descripcion) {
                fData.params['diagnostico:codigo'] = row.id;
                var desc = row.descripcion;
                $("#diagnostico").val(desc);
            }
            diagnosticoModal._modal.hide();
        }
        diagnosticoModal._table.set('click', _selectDiagnostico);
        $("#seleccionaDiag").click(function() {
            diagnosticoModal._modal.show();
        });
        $('#diagnostico').on('keyup', function() {
            _borrarProp('diagnostico:codigo');
        });

        $("#fechaRegistro").on('change keyup', function(e) {
            e.preventDefault();
            var val = $(this).val();
            if (!val)
                _borrarProp('ventafechaRegistro');
            else
                fData.params['ventafechaRegistro'] = val;
        });

        $("#numeroDeReceta").on('change keyup', function(e) {
            e.preventDefault();
            var val = $(this).val();
            if (!val)
                _borrarProp('numeroDeReceta');
            else
                fData.params['numeroDeReceta'] = val;
        });

        $("#periodo").on('change keyup', function(e) {
            e.preventDefault();
            var val = $(this).val();
            if (!val)
                _borrarProp('periodo');
            else
                fData.params['periodo'] = val;
            ;
        });

        function _borrarProp(prop) {
            var params = {};
            for (var p in fData.params) {
                if (fData.params.hasOwnProperty(p)) {
                    if (p != prop)
                        params[p] = fData.params[p];
                }
            }
            fData.params = params;
        }

        $("#turno").change(function(e) {
            e.preventDefault();
            var val = $(this).val();
            if (!val)
                _borrarProp('turno:idTurno');
            else
                fData.params['turno:idTurno'] = val;
            ;
            
        });
        $("#filtrarbtn").click(function(e) {
            e.preventDefault();
            if (!$("#cliente").val()) {
                alertify.error("El campo cliente es obligatorio");
                return;
            }
            var data = JSON.stringify(fData);
            $.ajax({
                url: 'consultar',
                type: 'POST',
                data: data,
                contentType: 'application/json',
                dataType: 'json',
                success: function(response) {
                    window.console.log(response);
                    if (!response.hasError) {
                        $("#tblData-table").dataTable().fnClearTable();
                        window.gp.addRow(response.data.detalles);
                    } else {
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function(response) {
                    window.console.log(response);
                }
            });
        });

        $("#btnGuardar").click(function(e) {
            e.preventDefault();
            var cantidad = $("#mdl-form").find('#cantidad').val();
            var id = $("#mdl-form").find('#id').val();
            var producto = $("#mdl-form").find('#producto').val();
            $.ajax({
                url: 'modificar',
                type: 'GET',
                data: "cantidad=" + cantidad + "&id=" + id + "&producto=" + producto,
                dataType: 'json',
                success: function(response) {
                    alertify.showMessage = alertify.success;
                    if (response.hasError)
                        alertify.showMessage = alertify.error;
                    for (var i in response.mssg) {
                        if (response.mssg.hasOwnProperty(i)) {
                            alertify.showMessage(response.mssg[i]);
                        }
                    }
                    $("#filtrarbtn").click();
                    $("#modalData-modal").modal('hide');
                },
                failure: function(response) {

                }
            });
        });
        function addListeners() {
            $(".row-data-remove").click(function(event) {
                event.preventDefault();
                var id = $(this).attr('data-id');
                smoke.confirm('¿Está seguro que desea eliminar estos datos?', function(e) {
                    if (e) {
                        $.ajax({
                            url: 'eliminar',
                            data: {id: id},
                            type: 'POST',
                            dataType: 'json',
                            success: function(r) {
                                if (r.hasError) {
                                    alertify.error(r.mssg[0]);
                                }
                                else {
                                    alertify.success(r.mssg[0]);
                                    $("#filtrarbtn").click();
                                }
                            },
                            failure: function(r) {
                                alertify.error('Ha ocurrido un error');
                            }
                        });
                    }
                });
            });
        }
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
    });
</script>