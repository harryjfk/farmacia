<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $("#fechaIni, #fechaFin").datepicker({'dateFormat': 'dd/mm/yy'});
        var dataConsulta = {paciente: 0};
        $('#consultar').hide();
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            dataConsulta.paciente = 0;
            $('#consultar').hide();
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                dataConsulta.paciente = row.paciente;
                $('#codPaciente').val(row.paciente);
                $('#historia').val(row.historia);
                $('#fechaNacimiento').val(fieldFormat.date(row.fechaNacimiento));
                $('#sexo').val(row.sexo);
                $("#paciente").val(nombre);
                $('#consultar').show();
            }
            pacienteModal._modal.hide();
        }
        $("#fechaIni").change(function (e) {
            e.preventDefault();
            dataConsulta.fechaIni = $(this).val();
        });
        $("#fechaFin").change(function (e) {
            e.preventDefault();
            dataConsulta.fechaFin = $(this).val();
        });
        window.gp.reportCondition = function () {
            if (!dataConsulta.paciente) {
                alertify.error("Debe escoger un Paciente para realizar esta acción.");
                return false;
            }
            window.gp.reportData = {paciente: dataConsulta.paciente};
            return true;
        };
        $('#consultar').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: 'getConsulta',
                type: "GET",
                dataType: "json",
                data: {
                    paciente: dataConsulta.paciente,
                    fechaIni: dataConsulta.fechaIni,
                    fechaFin: dataConsulta.fechaFin
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