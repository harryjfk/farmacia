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

        var dataConsulta = {};
        window.gp.reportData = {
            turno: 0,
            vendedor: 0
        };

        var vendedorModal = new VendedorGeneral(idModulo, true);
        function _selectVendedor(row) {
            if (row.idVendedor) {
                dataConsulta.vendedor = row.idVendedor;
                window.gp.reportData.vendedor = dataConsulta.vendedor;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#vendedor").val(nombre);

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
            dataConsulta.vendedor = null;
        });

        $("#turno").change(function (e) {
            e.preventDefault();
            dataConsulta.turno = {
                idTurno: $(this).val()
            };
            window.gp.reportData.turno = dataConsulta.turno.idTurno;
        });
        $("#startDate").change(function (e) {
            e.preventDefault();
            window.gp.reportData.startDate = $(this).val();
        });
        $("#endDate").change(function (e) {
            e.preventDefault();
            window.gp.reportData.endDate = $(this).val();
        });

        var options = {
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
        _getList(options);

        $('#consultarPreventa').click(function (e) {
            e.preventDefault();

            $.ajax({
                url: 'getReporte',
                type: "GET",
                dataType: "json",
                data: {
                    vendedor: dataConsulta.vendedor || 0,
                    turno: $('#turno').val(),
                    startDate: $('#startDate').val(),
                    endDate: $('#endDate').val()
                },
                success: function (r) {
                    var table = $('#tblData-table').dataTable(),
                            row;
                    console.log(r);
                    table.fnClearTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
    });
</script>