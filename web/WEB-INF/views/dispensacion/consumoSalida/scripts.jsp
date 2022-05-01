<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $('body').addClass('no-filter');
        $('body').addClass('no-order');

        $("#startDate, #endDate").datepicker({dateFormat: 'dd/mm/yy'});

        window.gp.reportData = {startDate: '', endDate: '', tipoPago: 0};

        $('#consumoPacienteConsultar').click(function (e) {
            e.preventDefault();
            var pago = $('#formaPago').val();
            var startDate = $("#startDate").val(),
                    endDate = $("#endDate").val();
            var strTmp = startDate.split('/');
            var endTmp = endDate.split('/');
            var start = new Date(strTmp[2], parseInt(strTmp[1]) - 1, strTmp[0]);
            var end = new Date(endTmp[2], parseInt(endTmp[1]) - 1, endTmp[0]);
            if (start > end) {
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
                    tipoPago: pago
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
                            if (days == 31)
                                k = 2;
                            if (days == 29)
                                k = 4;
                            if (days == 28)
                                k = 5;
                            row[days + k++] = item.total[i];
                            row[days + k++] = item.price[i];
                            row[days + k] = item.importe[i];
                            if (days <= 30)
                                row[32] = 0;
                            if (days <= 29)
                                row[31] = 0;
                            if (days == 28)
                                row[30] = 0;

                            table.fnAddData(row);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        });
        function _addTableContainer(monthName) {
            $(".table-container:first").clone().appendTo('.consumos');
            $(".table-container:last").find('.monthName').text(monthName);
            $(".table-container:last").show();
            $(".table-container:last").addClass('active');
            return $(".table-container:last");
        }

        $('#formaPago').change(function () {
            window.gp.reportData.tipoPago = $(this).val();
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

        $("#btnNuevo").click(function (e) {
            e.preventDefault();
            $(".table-container.active").remove();
            $("#formaPago").val(0);
        });
    });
</script>