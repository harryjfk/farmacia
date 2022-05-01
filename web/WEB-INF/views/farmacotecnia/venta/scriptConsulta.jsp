<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        var idModulo = location.pathname.split('/')[3];
        if ($("#btnPDF").length > 0) {
            $("#btnPDF").attr('id', 'btnConsultaPDF');
        }
        if ($("#btnExcel").length > 0)
            $("#btnExcel").attr('id', 'btnConsultaExcel');
        $('#startDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#endDate').datepicker({dateFormat: 'dd/mm/yy'});
         window.gp.reportData = {};
        var dataConsulta = {
            vendedor: 0,
            cliente: 0,
            turno: 0,
            formaPago: 0,
            servicio: 0
        };
        var servicioModal = new ServicioGeneral(idModulo);
        function _selectServicio(row) {
            if (row.nombre) {
                dataConsulta.servicio = row.id;
                var nombre = row.nombre;
                $("#servicio").val(nombre);
            }
            servicioModal._modal.hide();
        }
        servicioModal.get('table').set('click', _selectServicio);
        $("#seleccionaServ").click(function () {
            servicioModal.get('modal').show();
        });
        $('#servicio').on('keyup', function () {
            dataConsulta.servicio = null;
        });
        var vendedorModal = new VendedorGeneral(idModulo, true);
        function _selectVendedor(row) {
            if (row.idVendedor) {
                dataConsulta.vendedor = row.idVendedor;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#vendedor").val(nombre);
                window.gp.reportData = dataConsulta;
            }
            vendedorModal._modal.hide();
        }
        vendedorModal._table.set('click', _selectVendedor);
        $("#seleccionaVen").click(function () {
            var turno = dataConsulta.turno;
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
            dataConsulta.vendedor = 0;
        });
        var clienteModal = new ClienteGeneral(idModulo);
        function _selectCliente(row) {
            if (row.idCliente) {
                dataConsulta.cliente = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
                window.gp.reportData = dataConsulta;
            }
            clienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function () {
            clienteModal._modal.show();
        });
        $('#cliente').on('keyup', function () {
            dataConsulta.cliente = null;
        });

        var options = {
            url: '/GenericFormaPago/listar',
            success: function (r, status, ajaxData) {
                var select = $("#formaPago"),
                        td;
                select.append(new Option("--Todos--", "0"));
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
                select.append(new Option("--Todos--", "0"));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.descripcion, td.idTurno));
                    }
                }
            }
        };
        _getList(options1);
        $("#turno").change(function (e) {
            e.preventDefault();
            dataConsulta.turno = $(this).val();
            window.gp.reportData = dataConsulta;
        });
        $("#formaPago").on('change', function () {
            dataConsulta.formaPago = $(this).val();
            window.gp.reportData = dataConsulta;
        });
        $("#startDate").on('change keyup', function (e) {
            e.preventDefault();
            window.gp.reportData.startDate = $(this).val();
        });
        $("#endDate").on('change keyup', function (e) {
            e.preventDefault();
            window.gp.reportData.endDate = $(this).val();
        });
        $('#consultar').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: 'getReporte',
                type: "GET",
                dataType: "json",
                data: {
                    cliente: dataConsulta.cliente || 0,
                    vendedor: dataConsulta.vendedor || 0,
                    formaPago: dataConsulta.formaPago || 0,
                    turno: dataConsulta.turno || 0,
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val(),
                    servicio: dataConsulta.servicio || 0
                },
                success: function (r) {
                    var table = $('#tblData-table').dataTable();
                    table.fnClearTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
    });
</script>