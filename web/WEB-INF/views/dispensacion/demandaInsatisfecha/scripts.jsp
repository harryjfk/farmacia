<script>
    $(document).ready(function () {
        $('body').addClass('no-change');
        $('body').addClass('no-edit');
        $('.main-button-row').hide();
        $('body').undelegate('#btnAgregar', 'click');
        window.gp.ajaxList = 'getDemandas';
        window.gp.reportData = {idCliente: 0};
        $('#tblData-table').bind({
            Cargada: function (event) {
                event.preventDefault();
                $('.row-data-edit').hide();
                $('.row-data-change').hide();
            }
        });
        var path = window.location.pathname.split('/');
        path = path[1] + '/' + path[2] + '/' + path[3] + '/' + path[4] + '/getProductosAlmacen';
        var modal = new ProductoLoteGeneral(path);
        modal._table.setColumnsVisibility([0, 1, 2]);
        modal._table.set('click', function (obj) {
            var data = {
                idCliente: window.gp.reportData.idCliente,
                idAlmacen: obj['idAlmacen'],
                idLote: obj['lote.descripcion'],
                idProducto: obj['producto.idProducto']
            };
            window.gp.data = {
                idCliente: data.idCliente
            };

            $.ajax({
                url: 'insertar',
                data: data,
                type: "POST",
                success: function (r) {
                    var type = 'success';
                    if (r.hasError) {
                        type = 'error';
                    }

                    for (var i in r.mssg) {
                        if (r.mssg.hasOwnProperty(i)) {
                            alertify[type](r.mssg[i]);
                        }
                    }
                    window.gp.getData();
                },
                failure: function (r) {
                    alertify.error(r.mssg[0]);
                }
            });

        });
        modal._table.addRowClickEvent();

        $('#btnAgregar').click(function (e) {
            e.preventDefault();
            modal._modal.show();
        });
        var idModulo = location.pathname.split('/')[3];
        var clienteModal = new ClienteGeneral(idModulo, true);
        function _selectCliente(row) {
            if (row.idCliente) {
                window.gp.reportData.idCliente = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#clientesSelect").val(nombre);
                $('.main-button-row').show();
                window.gp.data = {
                    idCliente: row.idCliente
                };
                window.gp.getData();
            }
            clienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function () {
            clienteModal._modal.show();
        });
        $('#cliente').on('keyup', function () {
            window.gp.reportData.idCliente = '';
        });
        _AlmacenSelectFill();
        function _AlmacenSelectFill() {
            $.ajax({
                url: '/' + window.location.pathname.split('/')[1] + '/GenericAlmacen/listar',
                type: "GET",
                dataType: "json",
                success: function (r) {
                    var select = $('#almacenSelect'),
                            td, option;
                    r = r.data;
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            td = r[key];
                            option = new Option(td.descripcion, td.idAlmacen);
                            select.append(option);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        }
        ;
        $('#almacenSelect').change(function (e) {
            e.preventDefault();
            window.gp.reportData.idAlmacen = $(this).val();

            if ($(this).val() > 0) {
                modal._table.set('parameters', {almacen: $(this).val()});
                modal._table.clear();
                modal._table.getData();
            }
            else {
                modal._table.clear();
            }
        });
        var pacienteModal = new PacienteGeneral();
        $('#pull-from-personal a').click(function () {
            pacienteModal._modal.show();
        });
        function _selectPersonal(row) {
            if (row.paciente) {

                personalData = {
                    paciente: row.paciente,
                    paterno: row.paterno,
                    materno: row.materno,
                    nombre: row.nombre,
                    direccion: row.direccion,
                    telefono: row.telefono1 || row.telefono2
                };
                $.ajax({
                    url: 'insertarCliente',
                    type: 'POST',
                    data: personalData,
                    dataType: 'json',
                    success: function (r) {
                        if (r.hasError) {
                            alertify.error(r.mssg[0]);
                            return;
                        }
                        var cliente = r.data[0];
                        console.log(r);
                        window.gp.reportData.idCliente = cliente.idCliente;
                        $("#clientesSelect").val(cliente.nombreCliente);
                        $('.main-button-row').show();
                        window.gp.data = {
                            idCliente: cliente.idCliente
                        };
                        window.gp.getData();
                        clienteModal._modal.hide();

                        clienteModal._table.clear();
                        clienteModal._table.getData();
                    },
                    failure: function (response) {

                    }
                });
            }
            pacienteModal._modal.hide();
        }
        pacienteModal._table.set('click', _selectPersonal);

    });
</script>