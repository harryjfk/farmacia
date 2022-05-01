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
                    direccion: row.direccion
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
                tipoPersonal: {
                    required: true
                },
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
                personal: {
                    required: true
                },
                username: {
                    required: true,
                    minlength: 3
                },
                password: {
                    required: true,
                    minlength: 3
                }
            },
            messages: {
                tipoPersonal: {
                    required: 'Este campo es obligatorio'
                },
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
                personal: {
                    required: 'Este campo es obligatorio'
                },
                username: {
                    required: "Este campo es obligatorio",
                    minlength: "Por favor escriba al menos 3 caracteres"
                },
                password: {
                    required: "Este campo es obligatorio",
                    minlength: "Por favor escriba al menos 3 caracteres"
                }
            }
        });

        $('#btnPDF').off('click');
        $('#btnExcel').off('click');
        $('#btnPDF').click(function (event) {
            event.preventDefault();
            var dT = $('#tblData-table').dataTable();
            var fData = _getFilterData(dT);
            $.ajax({
                url: 'prepararReporte',
                type: "GET",
                dataType: "json",
                data: fData,
                success: function (r) {
                    _setPathName('reportePdf');
                },
                failure: function (r) {
                    for (var i in r.mssg)
                        if (r.mssg.hasOwnProperty(i))
                            alertify.error(r.mssg[i]);
                }
            });
            event.stopPropagation();
        });
        $('#btnExcel').click(function (event) {
            event.preventDefault();
            var dT = $('#tblData-table').dataTable();
            var fData = _getFilterData(dT);
            $.ajax({
                url: 'prepararReporte',
                type: "GET",
                dataType: "json",
                data: fData,
                success: function (r) {
                    _setPathName('reporteExcel');
                },
                failure: function (r) {
                    for (var i in r.mssg)
                        if (r.mssg.hasOwnProperty(i))
                            alertify.error(r.mssg[i]);
                }
            });
            event.stopPropagation();

        });

        function _setPathName(path) {
            var myLocation = window.location.href.split('/'),
                    result = "";
            myLocation.pop();

            for (var key in myLocation) {
                if (myLocation.hasOwnProperty(key)) {
                    result += myLocation[key] + "/";
                }
            }
            result += path;
            window.location.href = result;
        }
        function _geFilterFields() {
            var fFields = $('.table-filter');
            var params = new Object();
            var tblProperties = '${tableProperties}'.split(',');
            tblProperties.splice(0, 1);
            fFields.each(function (i, elem) {
                if ($(elem).val()) {
                    var fName = tblProperties[i].replace('.', ':');
                    params[fName] = $(elem).val();
                }
            });
            return params;
        }
        function _getFilterData(table) {
            var params;
            params = _geFilterFields();

            var count = table.fnPagingInfo().iLength;
            var start = 0;
            var fData = {
                'params': params,
                'start': start,
                'count': count
            };
            return fData;
        }
    });
</script>