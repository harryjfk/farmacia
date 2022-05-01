<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $('body').addClass('no-filter');
        $('body').addClass('no-order');

        $("#startDate, #endDate").datepicker({dateFormat: 'dd/mm/yy'});

        var idModulo = location.pathname.split('/')[3];
        var consultaData = {};

        window.gp.reportData = {month: "Enero", medico: 0, cliente: 0, tipoPago: 0, tipoProducto: 0, stardDate: '', endDate: ''};

        var medicoModal = new MedicoGeneral(idModulo);

        function _selectPrescriptor(row) {
            if (row.idMedico) {
                consultaData.medico = row.idMedico;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#medico").val(nombre);
                window.gp.reportData.medico = row.idMedico;
            }
            medicoModal._modal.hide();
        }
        medicoModal._table.set('click', _selectPrescriptor);
        $("#seleccionaMed").click(function () {
            medicoModal._modal.show();
        });

        var clienteModal = new ClienteGeneral(idModulo);
        function _selectCliente(row) {
            if (row.idCliente) {
                consultaData.cliente = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#cliente").val(nombre);
                window.gp.reportData.cliente = row.idCliente;
            }
            clienteModal._modal.hide();
        }
        clienteModal._table.set('click', _selectCliente);
        $("#seleccionaCli").click(function () {
            clienteModal._modal.show();
        });

        var diagnosticoModal = new DiagnosticoGeneral();
        function _selectDiagnostico(row) {
            if (row.descripcion) {
                consultaData.diagnostico = row.codigo;
                var desc = row.descripcion;
                $("#diagnostico").val(desc);
                window.gp.reportData.diagnostico = row.codigo;
            }
            diagnosticoModal._modal.hide();
        }
        diagnosticoModal._table.set('click', _selectDiagnostico);
        $("#seleccionaDiag").click(function () {
            diagnosticoModal._modal.show();
        });
        $('#diagnostico').on('keyup', function () {
            consultaData.diagnostico = null;
            window.gp.reportData.diagnostico = null;
        });

        $('#consumoPacienteConsultar').click(function (e) {
            e.preventDefault();
            var pago = $('#formaPago').val();
            var startDate = $("#startDate").val(),
                    endDate = $("#endDate").val();
            var strTmp = startDate.split('/');
            var endTmp = endDate.split('/');
            var start = new Date(strTmp[2], parseInt(strTmp[1]) - 1, strTmp[0]);
            var end = new Date(endTmp[2], parseInt(endTmp[1]) - 1, endTmp[0]);
            if(start > end) {
                alertify.error("La fecha de inicio debe ser menor que la fecha final");
                return;
            }
            window.gp.reportData.startDate = startDate;
            window.gp.reportData.endDate = endDate;
            $.ajax({
                url: 'getConsulta',
                type: "GET",
                dataType: "json",
                data: {
                    startDate: startDate,
                    endDate: endDate,
                    tipoPago: pago,
                    cliente: consultaData.cliente || 0,
                    medico: consultaData.medico || 0,
                    tipoProducto: $('#tipoSelect').val(),
                    diagnostico: consultaData.diagnostico
                },
                success: function (r) {
                    $(".table-container.active").remove();
                    for (var i in r) {
                        var item = r[i];
                        var dateData = item.monthName.split('-');
                        var table = _addTableContainer(dateData[0] + '/' + dateData[2]).find('table').dataTable(),
                                row;
                        $(".dataTables_length").css({float: 'left'});
                        var date = new Date(dateData[2], dateData[1]);
                        var days = date.getDaysInMonth();

                        var diference = 31 - days;
                        for (var i = 32; i > 0; i--) {
                            table.fnSetColumnVis(i, true);
                            if (32 - i < diference) {
                                table.fnSetColumnVis(i, false);
                            }
                        }

                        for (var i = 0; i < item.ids.length; i++) {
                            row = [];
                            row[0] = item.cod[i];
                            row[1] = item.producto[i];
                            for (var j = 0; j < days; j++) {
                                row[j + 2] = item.days[i][j + 1] || 0;
                            }

                            var k = 3;
                            if(days == 31)
                                k = 2;
                            if(days == 29) 
                                k = 4;
                            if(days == 28)
                                k = 5;
                            row[days + k++] = item.total[i];
                            row[days + k++] = item.price[i];
                            row[days + k] = item.importe[i];
                            if (days <= 30)
                                row[32] = 0;
                            if(days <= 29)
                                row[31] = 0;
                            if(days == 28)
                                row[30] = 0;

                            table.fnAddData(row);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        });

        var options = {
            url: '/GenericFormaPago/listar',
            success: function (r, status, ajaxData) {
                var select = $("#formaPago"),
                        td;
                select.append(new Option("--Forma de Pago-- ", "0"));
                for (var key in r.data) {
                    if (r.data.hasOwnProperty(key)) {
                        td = r.data[key];
                        select.append(new Option(td.descripcion, td.id));
                    }
                }
            }
        };
        _getList(options);

        function _addTableContainer(monthName) {
            $(".table-container:first").clone().appendTo('.consumos');
            $(".table-container:last").find('.monthName').text(monthName);
            $(".table-container:last").show();
            $(".table-container:last").addClass('active');
            return $(".table-container:last");
        }
    });
</script>