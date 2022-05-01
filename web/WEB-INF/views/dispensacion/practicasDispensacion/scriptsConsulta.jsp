<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $('#tblData-table').dataTable().fnSetColumnVis(4, false);
        $('#btnPDF').attr('id', 'btnConsultaPDF');
        $('#btnExcel').attr('id', 'btnConsultaExcel');
        window.gp.reportData = {idCliente: 0, startDate: '', endDate: ''};
        $('#StartDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#EndDate').datepicker({dateFormat: 'dd/mm/yy'});
        window.gp.ajaxList = 'getConsulta';
        window.gp.reportCondition = function () {
            var idCli = Number(window.gp.reportData.idCliente);
            if (!idCli || idCli == 0) {
                alertify.error("Debe seleccionar un cliente  para realizar esta acción.");
                return false;
            }
            return true;
        };

        $('#tblData-table').bind({
            Cargada: function (event) {
                event.preventDefault();
                $('.row-data-edit').hide();
                $('.row-data-change').hide();
            }
        });
        var idModulo = location.pathname.split('/')[3];
        var modal = new ClienteGeneral(idModulo);
        $('#seleccionaCli').click(function (e) {
            e.preventDefault();
            modal._modal.show();
        });
        modal._table.set('click', function (obj) {
            if (obj.idCliente) {
                $('#clientesSelect').val(obj.nombre + ' ' + obj.apellidoPaterno + ' ' + obj.apellidoMaterno);
                window.gp.reportData.idCliente = obj.idCliente;
                modal._modal.hide();
            }
        });
        $('#StartDate').change(function () {
            var temp = $(this).val();
            if (new Date(temp)) {
                window.gp.reportData.startDate = temp;
            }
        });
        $('#EndDate').change(function () {
            var temp = $(this).val();
            if (new Date(temp)) {
                window.gp.reportData.endDate = temp;
            }
        });
        $('#consultarDemandas').click(function (e) {
            e.preventDefault();
            window.gp.data = {
                idCliente: window.gp.reportData.idCliente || '',
                startDate: $('#StartDate').val(),
                endDate: $('#EndDate').val(),
                nroVenta: $("#nroVenta").val()
            };
            $('#tblData-table').dataTable().fnClearTable();
            window.gp.getData();
        });
    });
</script>