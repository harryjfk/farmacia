<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $('#StartDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#EndDate').datepicker({dateFormat: 'dd/mm/yy'});
        window.gp.ajaxList = 'getConsulta';
        window.gp.reportData = {idCliente: 0, startDate: '', endDate: ''};

        $('#StartDate').change(function () {
            window.gp.reportData.startDate = $(this).val();
        });
        $('#EndDate').change(function () {
            window.gp.reportData.endDate = $(this).val();
        });

        $('#tblData-table').bind({
            Cargada: function (event) {
                event.preventDefault();
                $('.row-data-edit').hide();
                $('.row-data-change').hide();
            }
        });
        var idModulo = location.pathname.split('/')[3];
        var clienteModal = new ClienteGeneral(idModulo);
        function _selectCliente(row) {
            if (row.idCliente) {
                window.gp.reportData.idCliente = row.idCliente;
                var nombre = row.nombre + ' ' + row.apellidoPaterno + ' ' + row.apellidoMaterno;
                $("#clientesSelect").val(nombre);
                $('.main-button-row').show();
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
        });

        $('#consultarDemandas').click(function (e) {
            e.preventDefault();
            window.gp.data = {
                idCliente: window.gp.reportData.idCliente || '',
                startDate: $('#StartDate').val(),
                endDate: $('#EndDate').val(),
                idAlmacen: window.gp.reportData.idAlmacen || 0
            };

            $('#tblData-table').dataTable().fnClearTable();
            window.gp.getData();
        });
    });
</script>