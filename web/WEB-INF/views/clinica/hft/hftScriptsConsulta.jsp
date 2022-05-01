<script>
    $(document).ready(function () {
        $('#btnAgregar').hide();
        $('#btnPDF').hide();
        $('#btnExcel').hide();
        $('body').addClass('no-change no-edit');
        var dataConsulta = {paciente: 0, dirty: 0};
        window.gp.ajaxList = 'getMedicamentos';

        
        $('#pacientefrm input,#pacientefrm textarea ').change(function () {
            dataConsulta.dirty = 1;
        });
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $('#pacienteModal').val(dataConsulta.paciente);
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                dataConsulta.paciente = row.paciente;
                $("#paciente").val(nombre);
                $('#historia').val(row.historia);
                $('#edad').val(row.edad);
                $('#sexo').val(row.sexo);
                $('#nombre').val(row.nombres);
                $('#idPaciente').val(row.paciente);
                $.ajax({
                    url: 'getOneHft',
                    type: 'POST',
                    data: {paciente: row.paciente},
                    dataType: 'json',
                    success: function (r) {
                        for (var key in r) {
                            if (r.hasOwnProperty(key)) {
                                if (key == 'paciente') {
                                    continue;
                                }
                                var input = $('#' + key),
                                        data = r[key];
                                if (key == 'fechaIngreso' || key == 'fechaAlta')
                                {
                                    data = fieldFormat.date(data);
                                }
                                if (input) {

                                    input.val(data);
                                }
                            }
                        }
                        $('#idHT').val(r.id);
                        window.gp.data = {hft: r.id};
                        window.gp.cleanTable();
                        window.gp.getData();
                    },
                    failure: function (response) {

                    }
                });
            }
            pacienteModal._modal.hide();
        }

    });

</script>