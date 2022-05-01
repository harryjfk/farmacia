<script>
    $(document).ready(function () {
        if ($("#btnPDF").length > 0) {
            $("#btnPDF").attr('id', 'btnConsultaPDF');
        }
        if ($("#btnExcel").length > 0)
            $("#btnExcel").attr('id', 'btnConsultaExcel');
        $("#btnAgregar").remove();
        var type = '${type}';
        window.gp.reportData = {tipo: type};
        $('#fechaInt').datepicker({dateFormat: 'dd/mm/yy'});
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        var medicoModal = new MedicoGeneral(5); //el 5 es el id del submodulo Farmacia de Centro Quirurjico TODO: mala practica
        medicoModal._table.set('click', _selectMedico);

        var dataConsulta = {medico: 0, paciente: 0, tipo: type};
        window.gp.reportData = {medico: 0, paciente: 0, tipo: type};
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            dataConsulta.medico = 0;
            window.gp.reportData.medico = 0;

        });
        $("#seleccionaMed").click(function () {
            medicoModal._modal.show();
        });
        $('#' + medicoModal._modal.get('id')).on('show.bs.modal', function () {
            $("#med-pull-from-personal").remove();
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                dataConsulta.paciente = row.paciente;
                window.gp.reportData.paciente = row.paciente;
                $("#paciente").val(nombre);
            }
            pacienteModal._modal.hide();
        }
        function _selectMedico(row) {
            if (row.idMedico) {
                dataConsulta.medico = row.idMedico;
                window.gp.reportData.medico = row.idMedico;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#medico").val(nombre);
            }
            medicoModal._modal.hide();
        }
        $("#fechaInt").change(function (e) {
            e.preventDefault();
            if ($(this).val()) {
                window.gp.reportData.date = $(this).val();
            } else {
                window.gp.reportData.date = null;
            }
        });

        $("#especialidad").change(function (e) {
            e.preventDefault();
            if ($(this).val()) {
                window.gp.reportData.especialidad = $(this).val();
            } else {
                window.gp.reportData.especialidad = null;
            }
        });

        $('#intfilterbtn').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: 'getConsulta',
                type: "GET",
                dataType: "json",
                data: {
                    medico: dataConsulta.medico || "",
                    paciente: dataConsulta.paciente || "",
                    tipo: dataConsulta.tipo,
                    date: $('#fechaInt').val(),
                    especialidad: $("#especialidad").val(),
                    atendida: $("#atendida").prop('checked'),
                    programada: $("#programada").prop('checked')

                },
                success: function (r) {
                    var table = $('#tblData-table').dataTable();
                    table.fnClearTable();
                    var row;
                    for (var record in r) {
                        if (r.hasOwnProperty(record)) {
                            row = [];
                            var intervencion = r[record].intervProductos;
                            for (var item in intervencion) {
                                if (intervencion.hasOwnProperty(item)) {
                                    var prod = [intervencion[item].producto.idProducto, intervencion[item].producto.descripcion, intervencion[item].cantidad];
                                    row.push(prod);
                                }
                            }
                        }
                        table.fnAddData(row);
                    }
                },
                failure: function (r) {
                }
            });
        });

    });
</script>