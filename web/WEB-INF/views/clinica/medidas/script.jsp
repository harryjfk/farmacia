<script>
    $(document).ready(function () {
        $('#fecha').datepicker({dateFormat: 'dd/mm/yy'});
        $('body').addClass('no-change');
        window.gp.dataToSend = {
            paciente: 0,
            id: 0,
            fecha: null,
            boleta: 1,
            diptico: 0,
            triptico: 0,
            afiches: 0,
            otros: 0,
            servicio: null,
            tema: null
        };

        $('#filtrar').click(function (e) {
            e.preventDefault();
            var servicio = $('#servicioFiltro').val();
            window.gp.data = {servicio: servicio};
            window.gp.getData();
            window.gp.data = {};
        });

        $('#modalData-modal').bind({
            Añadir: function (event) {
                event.preventDefault();
                window.gp.dataToSend = {paciente: 0, id: 0, fecha: null, boleta: 1, diptico: 0, triptico: 0, afiches: 0, otros: 0, servicio: null, tema: null};
                $('.radio input[data-value=boleta]').prop('checked',true);

            },
            Editar: function (event, id, data) {
                event.preventDefault();
                window.gp.dataToSend = {paciente: 0, id: 0, fecha: null, boleta: 1, diptico: 0, triptico: 0, afiches: 0, otros: 0, servicio: null, tema: null};
                $('#mdl-frm #paciente').val(data.paciente.nombres);
                var date = new Date(data.fecha);
                date = date.getMonth() + 1 + '/' + date.getDate() + '/' + date.getFullYear();
                $('#mdl-frm #fecha').val(date);
                window.gp.dataToSend.paciente = data.paciente.paciente;
                window.gp.dataToSend.fecha = date;

                window.gp.dataToSend.boleta = data.boleta;
                $('.radio input[data-value=boleta]').prop('checked', data.boleta);
                window.gp.dataToSend.diptico = data.diptico;
                $('.radio input[data-value=diptico]').prop('checked', data.diptico);
                window.gp.dataToSend.triptico = data.triptico;
                $('.radio input[data-value=triptico]').prop('checked', data.triptico);
                window.gp.dataToSend.afiches = data.afiches;
                $('.radio input[data-value=afiches]').prop('checked', data.afiches);
                window.gp.dataToSend.otros = data.otros;
                $('.radio input[data-value=otros]').prop('checked', data.otros);
                window.gp.dataToSend.servicio = data.servicio;
                window.gp.dataToSend.tema = data.tema;
                window.gp.dataToSend.id = data.id;

            }});
        $('#fecha').change(function () {
            window.gp.dataToSend.fecha = $(this).val();
        });
        $('.radio input').change(function () {
            window.gp.dataToSend.boleta = 0;
            window.gp.dataToSend.diptico = 0;
            window.gp.dataToSend.triptico = 0;
            window.gp.dataToSend.otros = 0;
            window.gp.dataToSend.afiches = 0;
            var tipo = $(this).attr('data-value');
            window.gp.dataToSend[tipo] = 1;
        });
        $('#servicio').change(function () {
            window.gp.dataToSend.servicio = $(this).val();
        });
        $('#tema').change(function () {
            window.gp.dataToSend.tema = $(this).val();
        });

        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            window.gp.dataToSend.paciente = 0;
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                window.gp.dataToSend.paciente = row.paciente;
                $("#paciente").val(nombre);
            }
            pacienteModal._modal.hide();
        }
        $('#mdl-frm').validate({
            rules: {
                servicio: {
                    required: true
                },
                fecha: {
                    required: true
                },
                tema: {
                    required: true
                },
                paciente: {
                    required: true
                }
            },
            messages: {
                servicio: {
                    required: 'Este campo es obligatorio'
                },
                fecha: {
                    required: 'Este campo es obligatorio'
                },
                tema: {
                    required: 'Este campo es obligatorio'
                },
                paciente: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>