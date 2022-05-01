<script>
    $(document).ready(function () {
        $("#btnAgregar").html('Inicializar');
        $("body").undelegate('#btnAgregar', 'click');
        $("body").addClass('no-change no-edit');
        crearCalendar("#fechIni");
        crearCalendar("#fechFin");
        $("body").undelegate('#btnAgregar', 'click');
        $("#btnAgregar").text("Nuevo");
        $("#cuentacorrientefrm").submit(function (e) {
            e.preventDefault();
        });
        $("#cuentacorrientefrm").validate({
            rules: {
                periodo: {
                    digits: true
                }
            },
            messages: {
                periodo: {
                    digits: "Este campo solo admite d&iacute;gitos."
                }
            }
        });
        var fData = {
            params: {
                estado: 'X_COBRAR'
            }
        };
        $("#btnAgregar").click(function (e) {
            e.preventDefault();
            document.getElementById("cuentacorrientefrm").reset();
            fData = {
                params: {
                    estado: 'X_COBRAR'
                }
            };
            $("#tblData-table").dataTable().fnClearTable();
        });
        var idModulo = location.pathname.split('/')[3];
        var clienteModal = new ClienteGeneral(idModulo);
        function _selectCliente(row) {
            if (row.idCliente) {
                fData.params['cliente'] = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
            }
            clienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function () {
            clienteModal._modal.show();
        });

        var options = {
            url: '/GenericFormaPago/listar',
            success: function (r, status, ajaxData) {
                var select = $("#formaPago"),
                        td;
                select.append(new Option("", ""));
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        if (td.descripcion.toLowerCase() != 'contado')
                            select.append(new Option(td.descripcion, td.id));
                    }
                }
            }
        };
        _getList(options);
        $("#formaPago").on('change', function () {
            fData.params['formaDePago'] = $(this).val();
        });

        $("#xcobrar").change(function (e) {
            e.preventDefault();
            fData.params['estado'] = $(this).val();
        });

        $("#cancelado").change(function (e) {
            e.preventDefault();
            fData.params['estado'] = $(this).val();
        });
        $("#fechIni").on('change keyup', function (e) {
            e.preventDefault();
            fData.params['fechIni'] = $(this).val();
        });
        $("#fechFin").on('change keyup', function (e) {
            e.preventDefault();
            fData.params['fechFin'] = $(this).val();
        });
        $("#periodo").on('change keyup', function (e) {
            e.preventDefault();
            fData.params['periodo'] = $(this).val();
        });
        $("#btnFilter").click(function (e) {
            e.preventDefault();
            var params = fData.params;
            if (!params.cliente && !params.fechIni && !params.fechFin && !params.formaDePago && !params.periodo) {
                alertify.error("Debe especificar almenos un criterio de b&uacute;squeda adem&aacute;s de la situaci&oacute;n del documento");
                return;
            }
            if ($("body").hasClass('no-remove') && params['estado'] == 'X_COBRAR')
                $("body").removeClass('no-remove');
            else if (!$("body").hasClass('no-remove') && params['estado'] == 'CANCELADA')
                $("body").addClass('no-remove');
            var data = JSON.stringify(fData);
            $.ajax({
                url: 'consultar',
                type: 'POST',
                data: data,
                contentType: 'application/json',
                dataType: 'json',
                success: function (response) {
                    $("#tblData-table").dataTable().fnClearTable();
                    if (!response.hasError) {
                        window.gp.addRow(response.data);
                        $('.row-data-remove').children('i').removeClass('splashy-remove').addClass('splashy-gem_okay').attr('title', 'Cancelar');
                        $("body").undelegate('.row-data-remove', 'click');
                        $("body").delegate('.row-data-remove', 'click', function (e) {
                            var elem = $(this);
                            e.preventDefault();
                            smoke.confirm("¿Est&aacute; seguro que desea cancelar este documento?", function (e) {
                                if (e) {
                                    $.ajax({
                                        url: 'cancelar',
                                        type: 'POST',
                                        data: {id: elem.attr('data-id')},
                                        success: function (response) {
                                            alertify.showMessage = alertify.error;
                                            if (!response.hasError) {
                                                alertify.showMessage = alertify.success;
                                                $("#btnFilter").click();
                                            }
                                            for (var i in response.mssg) {
                                                alertify.showMessage(response.mssg[i]);
                                            }
                                        },
                                        failure: function (response) {
                                            window.console.log(response);
                                        }
                                    });
                                }
                            });
                        });
                    } else {
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i)) {
                                alertify.error(response.mssg[i]);
                            }
                        }
                    }
                },
                failure: function (response) {
                    window.console.log(response);
                }
            });
        });
    });
</script>