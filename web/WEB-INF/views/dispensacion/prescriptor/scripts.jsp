<script>
    $(document).ready(function () {
        $("#telefonoUno").keypress(function (key) {
            if (key.charCode < 48 || key.charCode > 57)
                return false;
        });
        $('#modalData-modal').bind({
            Añadir: function (event) {
                event.preventDefault();
                $('#pull-from-personal').show();
            },
            Editar: function (event) {
                event.preventDefault();
                $('#pull-from-personal').hide();
            }
        });
        var personalData = null;
        $('#modalData-modal').on('shown.bs.modal', function () {
            if (personalData) {
                $('#mdl-form').find('input#personal').val(personalData.personal);
                $('#mdl-form').find('input#paterno').val(personalData.paterno);
                $('#mdl-form').find('input#materno').val(personalData.materno);
                $('#mdl-form').find('input#nombre').val(personalData.nombre);
                $('#mdl-form').find('input#direccion').val(personalData.direccion);
                $('#mdl-form').find('input#telefonoUno').val(personalData.telefono);
                $('#mdl-form').find('input#colegio').val(personalData.colegiatura);
            }

        });
        $('#modalData-modal').on('shown.bs.modal', function () {
            personalData = null;
        });
        var personalModal = new PersonalGeneral();
        $('#pull-from-personal a').click(function () {
            $('#modalData-modal').modal('hide');
            personalModal._modal.show();
        });
        function _selectPersonal(row) {
            if (row.personal) {
                personalData = {
                    personal: row.personal,
                    paterno: row.apellidoPaterno,
                    materno: row.apellidoMaterno,
                    nombre: row.nombres,
                    direccion: row.direccion,
                    telefono: row.telefono1 || row.teleforno2,
                    colegiatura: row.nroColegiatura
                };
            }
            $('#modalData-modal').modal('show');
            personalModal._modal.hide();
        }
        personalModal._table.set('click', _selectPersonal);
        $('#personal-list-box').delegate('#personal-regresar', 'click', function (event) {
            event.preventDefault();
            $('#modalData-modal').modal('show');
        });
        var personalModalId = "#" + personalModal.get('modal').get('id');
        $(personalModalId).on('hidden.bs.modal', function () {
            $('#modalData-modal').modal('show');
        });

        $('#mdl-form').validate({
            rules: {
                nombre: {
                    required: true
                },
                paterno: {
                    required: true
                },
                materno: {
                    required: true
                },
                direccion: {
                    required: true
                },
                telefonoUno: {
                    required: true
                },
                colegio: {
                    required: true
                },
                profesion: {
                    required: true
                },
                personal: {
                    required: true
                }
            },
            messages: {
                nombre: {
                    required: 'Este campo es obligatorio'
                },
                paterno: {
                    required: 'Este campo es obligatorio'
                },
                materno: {
                    required: 'Este campo es obligatorio'
                },
                direccion: {
                    required: 'Este campo es obligatorio'
                },
                telefonoUno: {
                    required: 'Este campo es obligatorio'
                },
                colegio: {
                    required: 'Este campo es obligatorio'
                },
                profesion: {
                    required: 'Este campo es obligatorio'
                },
                personal: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>