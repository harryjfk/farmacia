<script>
    $(document).ready(function () {
        $('#tblData-table').dataTable().fnDestroy();
        $('#tblData-table').html('');
        $('#fecha ,#fechaInicioMedicamentos,#fechaFinalMedicamentos,#fechaInicioOtros,#fechaFinalOtros,#fechaInicioReaccion,#fechaFinalReaccion').datepicker({
            dateFormat: 'dd/mm/yy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves', 'Viernes', 'S&aacute;bado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;']
        });
        $('#medicamentosSospechosos_length').hide();
        $('#reaccionesAdversas_length').hide();
        $('#otrosMedicamentos_length').hide();
        $('#btnAgregar').hide();
        $('#guardarHoja').click(function () {
            var empty = false;
            $('#ramForm input, #ramForm textarea').each(function () {

                if ($(this).val() == '0' || $(this).val() == 0) {
                    if ($(this).attr('id') != "ram")
                    {
                        if(!empty)
                        {alertify.error('Debe llenar todos los campos de Datos del Paciente y Persona que Notifica para guardar el registro RAM.');
                        empty = true;
                    }
                    }
                }
            });
            if(empty){
                return;
            }  
            var data = $('#ramForm').serialize();
            $.ajax({
                url: 'guardarDatos',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (r) {
                    _messages(r);
                    var id = r.data[0].id || 0;
                    $('#ram').val(id);
                    window.gp.reportData = {ram: id};

                },
                failure: function (r) {

                }
            });
        });

        $('#limpiarHoja').click(function () {
            _CleanSheet();
        });

        $('#borrarHoja').click(function () {
            var value = $('#ram').val();
            if (value == '0') {
                alertify.error('Solo se puede eliminar una ram previamente guardada.');
                return;
            }
            $.ajax({
                url: 'eliminarRam',
                type: 'POST',
                data: {id: value},
                dataType: 'json',
                success: function (r) {
                    _messages(r);
                    _CleanSheet();
                },
                failure: function (response) {

                }
            });
        });
        function _CleanSheet() {
            $('#ramForm input,#ramForm textarea ').val('');
            medicamentosTable.clear();
            reaccionesTable.clear();
            otrosTable.clear();
            $('#ram').val(0);
        }

        $('#consultar').click(function () {
            var data = $('#consulta').val();
            if (!data) {
                alertify.error("Debe escribir un valor valido en el campo Consulta");
                return;
            }
            _CleanSheet();
            $.ajax({
                url: 'getRAM',
                type: 'GET',
                data: {historia: data},
                dataType: 'json',
                success: function (r) {
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            var input = $('#' + key),
                                    data = r[key];
                            if (key == 'fecha') {
                                data = fieldFormat.date(data);
                            }

                            if (input) {
                                input.val(data);
                            }
                        }
                    }
                    $('#ram').val(r.id);
                    medicamentosTable.set('parameters', {ram: r.id});
                    medicamentosTable.clear();
                    medicamentosTable.getData();
                    reaccionesTable.set('parameters', {ram: r.id});
                    reaccionesTable.clear();
                    reaccionesTable.getData();
                    otrosTable.clear();
                    otrosTable.set('parameters', {ram: r.id});
                    otrosTable.getData();
                    window.gp.reportData = {ram: r.id};
                },
                failure: function (response) {

                }
            });
        });

        function _messages(r) {
            var type = 'success';
            if (r.hasError) {
                type = 'error';
            }

            for (var i in r.mssg) {
                if (r.mssg.hasOwnProperty(i)) {
                    alertify[type](r.mssg[i]);
                }
            }
        }
        $('#btn-agregarMedicamentos').click(function (e) {
            $('#medicamentoForm input').val('');
            if ($('#ram').val() == "0") {
                e.preventDefault();
                alertify.error("Debe guardar el reporte o consultar uno existente.");
                return;
            }

            var modal = $('#medicamentosSospechosos-modal');
            _CleanValidate(modal);
            modal.modal('show');

        });
        $('#btn-agregarReacciones').click(function (e) {
            $('#reaccionesForm input , #reaccionesForm textarea').val('');
            if ($('#ram').val() == "0") {
                e.preventDefault();
                alertify.error("Debe guardar el reporte o consultar uno existente.");
                return;
            }
            var modal = $('#reaccionesAdversas-modal');
            _CleanValidate(modal);
            modal.modal('show');
        });
        $('#btn-agregarOtros').click(function (e) {
            $('#otrosForm input , #otrosForm textarea').val('');
            if ($('#ram').val() == "0") {
                e.preventDefault();
                alertify.error("Debe guardar el reporte o consultar uno existente.");
                return;
            }
            var modal = $('#otrosMedicamentos-modal');
            _CleanValidate(modal);
            modal.modal('show');
        });
        function _Validate(selector) {
            var modal = $(selector);
            var validation = modal.validate();

            if (!validation.checkForm()) {
                validation.showErrors();
                modal.find('.error').parent().addClass('has-error');
                modal.find('label.error').addClass('control-label');
                return false;
            }
            modal.find('.valid').parent('.has-error').removeClass('has-error');
            modal.find('.has-error').removeClass('has-error');
            return true;
        }
        $('#btnGuardarmedicamentosSospechosos').click(function (e) {
            e.preventDefault();
            if (!_Validate('#medicamentoForm')) {
                return;
            }

            var data = $('#medicamentoForm').serialize();
            var ram = $('#ram').val();
            data = data + '&ram=' + ram;
            $.ajax({
                url: 'insertarMedicamentos',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (r) {
                    _messages(r);

                    medicamentosTable.set('parameters', {ram: ram});
                    medicamentosTable.clear();
                    medicamentosTable.getData();
                },
                failure: function (response) {

                }
            });
        });
        $('#btnGuardarReacciones').click(function (e) {
            e.preventDefault();
            if (!_Validate('#reaccionesForm')) {
                return;
            }
            var data = $('#reaccionesForm').serialize();
            var ram = $('#ram').val();
            data = data + '&ram=' + ram;
            $.ajax({
                url: 'insertarReaccion',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (r) {
                    _messages(r);

                    reaccionesTable.set('parameters', {ram: ram});
                    reaccionesTable.clear();
                    reaccionesTable.getData();
                },
                failure: function (response) {

                }
            });
        });
        $('#btnGuardarOtros').click(function (e) {
            e.preventDefault();
            if (!_Validate('#otrosForm')) {
                return;
            }
            var data = $('#otrosForm').serialize();
            var ram = $('#ram').val();
            data = data + '&ram=' + ram;
            $.ajax({
                url: 'insertarOtro',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (r) {
                    _messages(r);

                    otrosTable.set('parameters', {ram: ram});
                    otrosTable.clear();
                    otrosTable.getData();
                },
                failure: function (response) {

                }
            });
        });


        $('body').delegate('.delete-medicamento', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr('data-id');
            $.ajax({
                url: 'eliminarMedicamento',
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (r) {
                    _messages(r);
                    medicamentosTable.set('parameters', {ram: $('#ram').val()});
                    medicamentosTable.clear();
                    medicamentosTable.getData();

                },
                failure: function (response) {

                }
            });
        });
        $('body').delegate('.delete-reaccion', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr('data-id');
            $.ajax({
                url: 'eliminarReaccion',
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (r) {
                    _messages(r);
                    reaccionesTable.clear();
                    reaccionesTable.set('parameters', {ram: $('#ram').val()});
                    reaccionesTable.getData();

                },
                failure: function (response) {

                }
            });
        });
        $('body').delegate('.delete-otros', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr('data-id');
            $.ajax({
                url: 'eliminarOtro',
                type: 'POST',
                data: {id: id},
                dataType: 'json',
                success: function (r) {
                    _messages(r);
                    otrosTable.clear();
                    otrosTable.set('parameters', {ram: $('#ram').val()});
                    otrosTable.getData();

                },
                failure: function (response) {

                }
            });
        });

        var medicamentosTable = new slDataTable('#medicamentosSospechosos');
        medicamentosTable.set('dataUrl', 'getMedicamentos');
        medicamentosTable.add('columns', ['nombre', 'laboratorio', 'lote', 'dosis', 'via', 'fechaInicio:date', 'fechaFinal:date','motivo']);
        var medicamentosDelete = {
            id: 'id',
            url: 'eliminarMedicamento',
            classes: 'separator-icon-td delete-medicamento',
            icon: '<i class="splashy-remove" title="Eliminar">'
        };
        medicamentosTable.add('actions', [medicamentosDelete]);

        var reaccionesTable = new slDataTable('#reaccionesAdversas');
        reaccionesTable.set('dataUrl', 'getReacciones');
        reaccionesTable.add('columns', ['reaccion', 'fechaInicio:date', 'fechaFinal:date', 'evolucion']);
        var reaccionesDelete = {
            id: 'id',
            url: 'eliminarReaccion',
            classes: 'separator-icon-td delete-reaccion',
            icon: '<i class="splashy-remove" title="Eliminar">'
        };
        reaccionesTable.add('actions', [reaccionesDelete]);
        var otrosTable = new slDataTable('#otrosMedicamentos');
        otrosTable.set('dataUrl', 'getOtros');
        otrosTable.add('columns', ['nombre', 'dosis', 'via', 'fechaInicio:date', 'fechaFinal:date', 'indicacion']);
        var otrosDelete = {
            id: 'id',
            url: 'eliminarReaccion',
            classes: 'separator-icon-td delete-otros',
            icon: '<i class="splashy-remove" title="Eliminar">'
        };
        otrosTable.add('actions', [otrosDelete]);

        $('#medicamentoForm').validate({
            rules: {
                nombre: {required: true},
                laboratorio: {required: true},
                lote: {required: true},
                dosis: {required: true},
                via: {required: true},
                fechaInicio: {required: true},
                fechaFinal: {required: true},
                motivo: {required: true}
            },
            messages: {
                nombre: {required: 'Este campo es obligatorio'},
                laboratorio: {required: 'Este campo es obligatorio'},
                lote: {required: 'Este campo es obligatorio'},
                dosis: {required: 'Este campo es obligatorio'},
                via: {required: 'Este campo es obligatorio'},
                fechaInicio: {required: 'Este campo es obligatorio'},
                fechaFinal: {required: 'Este campo es obligatorio'},
                motivo: {required: 'Este campo es obligatorio'}
            }
        });
        $('#reaccionesForm').validate({
            rules: {
                reaccion: {required: true},
                fechaInicio: {required: true},
                fechaFinal: {required: true},
                evolucion: {required: true}
            },
            messages: {
                reaccion: {required: 'Este campo es obligatorio'},
                fechaInicio: {required: 'Este campo es obligatorio'},
                fechaFinal: {required: 'Este campo es obligatorio'},
                evolucion: {required: 'Este campo es obligatorio'}
            }
        });
        $('#otrosForm').validate({
            rules: {
                nombre: {required: true},
                dosis: {required: true},
                via: {required: true},
                fechaInicio: {required: true},
                fechaFinal: {required: true},
                indicacion: {required: true}
            },
            messages: {
                nombre: {required: 'Este campo es obligatorio'},
                dosis: {required: 'Este campo es obligatorio'},
                via: {required: 'Este campo es obligatorio'},
                fechaInicio: {required: 'Este campo es obligatorio'},
                fechaFinal: {required: 'Este campo es obligatorio'},
                indicacion: {required: 'Este campo es obligatorio'}
            }
        });
        function _CleanValidate(modal) {
            var validation = modal.validate();
            validation.resetForm();
            modal.find('.has-error').removeClass('has-error');
            modal.find('.error').removeClass('error');
        }
        window.gp.reportCondition = function () {
            var ram = Number($('#ram').val());

            if (!ram || ram == 0) {
                alertify.error("Debe consultar o guardar la hoja  para realizar esta acción.");
                return false;
            }
            return true;
        };

        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function () {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $('#consulta').val(row.historia);
                $('#paciente').val(nombre);
                $('#pacienteModal').val(nombre);
                $('#historia').val(row.historia);

                pacienteModal._modal.hide();
            }
        }
    });
</script>