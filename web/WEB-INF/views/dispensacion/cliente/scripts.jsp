<script>
    $(document).ready(function () {
        $("#telefono").keypress(function (key) {//Cambio Valeria vi
            if (key.charCode < 48 || key.charCode > 57)
                return false;
        });
        $("#dni").keypress(function (key) {
            if (key.charCode < 48 || key.charCode > 57)
                return false;
        });
        $('#modalData-modal').bind({
            Editar: function (event, id, cliente) {
                if (cliente.tipoDocumento) {
                    $('#TipoDocumentoSelect').val(cliente.tipoDocumento.idTipoDocumento);
                }
                event.preventDefault();
                $('#pull-from-personal').hide();
            },
            Añadir: function (event) {
                event.preventDefault();
                $('#pull-from-personal').show();
            }
        });
        $('#btnAgregar').click(function (e) {
            e.preventDefault();
            $('#TipoDocumentoSelect').val(1);
        });
        _TipoDocumentoSelectFill('#TipoDocumentoSelect');

        var personalData = null;
        $('#modalData-modal').on('shown.bs.modal', function () {
            if (personalData) {
                $('#mdl-form').find('input#codPersonal').val(personalData.paciente);
                $('#mdl-form').find('input#apellidoPaterno').val(personalData.paterno);
                $('#mdl-form').find('input#apellidoMaterno').val(personalData.materno);
                $('#mdl-form').find('input#nombre').val(personalData.nombre);
                $('#mdl-form').find('input#direccion').val(personalData.direccion);
                $('#mdl-form').find('input#telefono').val(personalData.telefono);
            }

        });
        $('#modalData-modal').on('hidden.bs.modal', function () {
            personalData = null;
        });

        var pacienteModal = new PacienteGeneral();
        $('#pull-from-personal a').click(function () {
            $('#modalData-modal').modal('hide');
            pacienteModal._modal.show();
        });
        function _selectPersonal(row) {
            if (row.paciente) {
                personalData = {
                    paciente: row.paciente,
                    paterno: row.paterno,
                    materno: row.materno,
                    nombre: row.nombre,
                    direccion: row.direccion,
                    telefono: row.telefono1 || row.teleforno2
                };
            }
            $('#modalData-modal').modal('show');
            pacienteModal._modal.hide();
        }
        pacienteModal._table.set('click', _selectPersonal);
        $('#personal-list-box').delegate('#personal-regresar', 'click', function (event) {
            event.preventDefault();
            $('#modalData-modal').modal('show');
        });
        var pacienteModalId = "#" + pacienteModal.get('modal').get('id');
        $(pacienteModalId).on('hidden.bs.modal', function () {
            $('#modalData-modal').modal('show');
        });
        $('#mdl-form').validate({
            rules: {
                nombre: {
                    required: true
                },
                apellidoPaterno: {
                    required: true
                },
                noAfiliacion: {
                    required: true
                },
                apellidoMaterno: {
                    required: true
                },
                direccion: {
                    required: true
                },
                TipoDocumentoSelect: {
                    required: true
                },
                noDocumento: {
                    required: true,
                    digits: true
                },
                telefono: {
                    required: true
                },
                dni: {
                    required: true
                }
            },
            messages: {
                nombre: {
                    required: 'Este campo es obligatorio'
                },
                apellidoPaterno: {
                    required: 'Este campo es obligatorio'
                },
                noAfiliacion: {
                    required: 'Este campo es obligatorio'
                },
                apellidoMaterno: {
                    required: 'Este campo es obligatorio'
                },
                direccion: {
                    required: 'Este campo es obligatorio'
                },
                TipoDocumentoSelect: {
                    required: 'Este campo es obligatorio'
                },
                noDocumento: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo acepta digitos'
                },
                telefono: {
                    required: 'Este campo es obligatorio'
                },
                dni: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>