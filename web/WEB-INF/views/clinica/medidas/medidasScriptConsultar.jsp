<script>
    $(document).ready(function () {

        window.gp.reportData = {startDate: '', endDate: '', paciente: 0};
        var consultaData = {paciente: 0};
        $('#prmConsultar').click(function (e) {
            e.preventDefault();
            var year = $('#periodoSelect').val(),
                    month = $('#monthSelect').val();
            var startDate = new Date(year, month - 1, 1),
                    endDate = new Date(year, month - 1, new Date(year, month - 1).getDaysInMonth());
            window.gp.reportData.startDate = fieldFormat.date(startDate);
            window.gp.reportData.endDate = fieldFormat.date(endDate);
            $.ajax({
                url: 'getConsulta',
                type: "GET",
                dataType: "json",
                data: {
                    startDate: fieldFormat.date(startDate),
                    endDate: fieldFormat.date(endDate),
                    paciente: consultaData.paciente
                },
                success: function (r) {
                    window.gp.cleanTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                consultaData.paciente = row.paciente;
                window.gp.reportData.paciente = row.paciente;
                $("#paciente").val(nombre);
            }
            pacienteModal._modal.hide();
        }
        $("#paciente").change(function (e) {
            consultData.paciente = 0;
            window.gp.reportData.paciente = 0;
        });
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
    });
</script>