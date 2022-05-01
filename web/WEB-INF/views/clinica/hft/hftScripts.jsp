<script>
    $(document).ready(function () {
        $('#btnAgregar').hide();
       
        $('body').addClass('no-change no-edit');
        var dataConsulta = {paciente: 0, dirty: 0};
        window.gp.ajaxList = 'getMedicamentos';

        $('#limpiarHoja').click(function () {
            $('#pacientefrm input,#pacientefrm textarea ').val('');
            window.gp.cleanTable();
            dataConsulta.dirty = 0;
            dataConsulta.paciente = 0;
            $('#idHT').val(0);
        });
         window.gp.reportCondition=function(){
             var value =Number($('#idHT').val());
            if(!value || value == 0){
                alertify.error("Debe escoger un Paciente para realizar esta acción.");
                return false;
            }
            window.gp.reportData={hft:value};
            return true;
        };
        $('#pacientefrm input,#pacientefrm textarea ').change(function () {
            dataConsulta.dirty = 1;
        });
        _ProductoSelectFill('#producto');

        $('#guardarHoja').click(function () {
            if (dataConsulta.paciente == 0) {
                alertify.error('Debe Elegir un Paciente');
                return;
            }
            var data = $('#pacientefrm').serialize();
            $.ajax({
                url: 'guardarDatos',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (r) {
                    var type = 'success';
                    if (r.hasError) {
                        type = 'error';
                    }

                    for (var i in r.mssg) {
                        if (r.mssg.hasOwnProperty(i)) {
                            alertify[type](r.mssg[i]);
                        }
                    }
                    if (!r.hasError) {
                        dataConsulta.dirty = 0;
                    }
                    $('#idHT').val(r.data[0].id);
                    $('#btnAgregar').show();
                    window.gp.data = {hft: r.data[0].id};
                    window.gp.cleanTable();
                    window.gp.getData();
                },
                failure: function (r) {

                }
            });
        });
        $('#fechaIngreso,#fechaAlta').datepicker({
            dateFormat: 'dd/mm/yy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves', 'Viernes', 'S&aacute;bado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;']
        });
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $('#pacienteModal').val(dataConsulta.paciente);
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function () {
            dataConsulta.paciente = 0;
            $('#btnAgregar').hide();
            $('#pacienteModal').val(0);
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
                        if (r.id) {
                            $('#btnAgregar').show();
                        }
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
                        if (!($('#idHT').val())) {
                            dataConsulta.dirty = 1;
                            alertify.error('Debe guardar esta hoja para poder insertar medicamentos');
                            $('#btnAgregar').hide();
                        }
                    },
                    failure: function (response) {

                    }
                });
            }
            pacienteModal._modal.hide();
        }
        $('#dosis').change(function () {
            $('#hft').val($('#idHT').val());
        });
        $('#mdl-frm').validate({
            rules: {
                producto: {required: true},
                dosis: {required: true},
                frecuencia: {required: true},
                via: {required: true},
                total: {required: true},
                prm: {required: true}
            },
            messages: {
                producto: {required: 'Este campo es obligatorio'},
                dosis: {required: 'Este campo es obligatorio'},
                frecuencia: {required: 'Este campo es obligatorio'},
                via: {required: 'Este campo es obligatorio'},
                total: {required: 'Este campo es obligatorio'},
                prm: {required: 'Este campo es obligatorio'}

            }
        });

    });

</script>
