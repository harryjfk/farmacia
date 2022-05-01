<script>
    $(document).ready(function () {
        var table = $('#tblData-table').dataTable();
        var idModulo = location.pathname.split('/')[3];

        _VendedorSelectFill('#vendedoresTurno',${idModulo});

        $('#horaInicio').focus(function () {
            $('#horaInicio').blur();
        });
        $('#horaFinal').focus(function () {
            $('#horaFinal').blur();
        });

        _setTimePicker('#horaInicio');
        _setTimePicker('#horaFinal');
        _setTimePicker('#idInicio');
        _setTimePicker('#idTermino');

        $('#vendedoresSelect').change(function (e) {
            var value = $(this).val(),
                    button = $('#agregarVendedoresbtn-frame');
            var vTable = $('#tblVendedoresTurnoExt').dataTable();
            vTable.fnClearTable();
            if (value > 0) {
                button.removeClass('hidden');
                _getVendedores(value, vTable);
            }
            else {
                button.addClass('hidden');
            }
        });
        var options1 = {
            url: '/' + idModulo + '/turno/getTurnos',
            success: function (r, status, ajaxData) {
                var select = $("#vendedoresSelect"),
                        td;
                select.html('');
                select.append(new Option('-- Seleccione un Turno--', 0));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.descripcion, td.idTurno));
                    }
                }
            }
        };
        $('#tblData-table').bind({
            Cargada: function (event) {
                event.preventDefault();
                _setTime('.horaInicio');
                _setTime('.horaFinal');
            }
        });
        var vendedorModal = new VendedorGeneral(idModulo, true);
        var idTable = vendedorModal.get("tableID");
        $("#" + idTable).dataTable().fnClearTable();
        vendedorModal.get("table").getData();
        function _selectVendedor(row) {
            if (row.idVendedor) {
                var turno = $('#vendedoresSelect').val();
                $.ajax({
                    url: 'insertarVendedor',
                    type: 'POST',
                    data: {
                        vendedor: row.idVendedor,
                        turno: turno
                    },
                    dataType: 'json',
                    success: function (r) {
                        var vTable = $('#tblVendedoresTurnoExt').dataTable();
                        vTable.fnClearTable();
                        _getVendedores(turno, vTable);
                    },
                    failure: function (r) {

                    }
                });
            }
            vendedorModal._modal.hide();
        }
        vendedorModal._table.set('click', _selectVendedor);
        $("#agregarVendedoresbtn").click(function () {
            vendedorModal._modal.show();
        });
        $('#' + vendedorModal.get('modal').get('id')).on('show.bs.modal', function (e) {
            $('#ven-pull-from-personal').remove();
        });
        $('#tblVendedoresTurnoExt').dataTable();

        $('#agregarVendedores').click(function (e) {
            e.preventDefault();
            var modal = $('#vendedores-modal');
            modal.modal('show');
            $('#verVendedoresTurno').show();
            _getList(options1);


        });

        $('#modalData-modal').bind({
            Editar: function (event, id, turno) {
                event.preventDefault();
                _hideVendedoresTable();
                _setInputTime('#horaInicio');
                _setInputTime('#horaFinal');
                $('#verVendedoresTurno').show();
                $('#verVendedoresTurno a').text('Mostrar Vendedores');
                $('#no-vendedor-available').hide();
                if (turno) {
                    ids = [];
                    for (var i in turno.vendedores) {
                        if (turno.vendedores.hasOwnProperty(i)) {
                            ids.push(turno.vendedores[i].idVendedor);
                        }
                    }
                    $('#mdl-form').find('#vendedoresTurno').val(ids);
                }
                $('#vendedoresTurno').select2();
            },
            Añadir: function (event) {
                event.preventDefault();
                _hideVendedoresTable();
                var value = $('#turnosPorFarmacia').val();
                $('#verVendedoresTurno').hide();
                $('#verVendedoresTurno a').text('Mostrar Vendedores');
                $('#no-vendedor-available').show();
            }
        });

        $('#mdl-form').bind({
            cleanForm: function () {
                $('#vendedoresTurno').val([]);
                $('#vendedoresTurno').select2();
            }
        });

        $('body').delegate('#verVendedoresTurno', 'click', function (event) {
            event.preventDefault();

            $(this).find('a').text('Mostrar Vendedores');
            var form = $('#modalData-modal').find('form'),
                    tableBox = $('#vendedoresTurno-list-box');
            form.toggle();
            tableBox.toggleClass('hidden');
            var table = $('#tblVendedoresTurno').dataTable();
            if (!tableBox.hasClass('hidden')) {
                $(this).find('a').text('Mostrar Turno');
                table.fnClearTable();
                var turno = form.find('.IDFIELD').val();
                _getVendedores(turno, table);

            }
        });

        $('body').delegate('.vendedor-remove', 'click', function (e) {
            e.preventDefault();
            var turno = $(this).attr('data-turno');
            $.ajax({
                url: $(this).attr('data-url'),
                type: 'POST',
                data: {
                    turno: turno,
                    vendedor: $(this).attr('data-id')
                },
                dataType: 'json',
                success: function (r) {
                    var vTable = $('#tblVendedoresTurnoExt').dataTable();
                    vTable.fnClearTable();
                    _getVendedores(turno, vTable);
                },
                failure: function (r) {

                }
            });

        });

        function _getVendedores(turno, table) {
            $.getJSON(turno + '/getVendedores', function (r) {
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {

                        table.fnAddData([
                            r[key].codigo,
                            r[key].nombre,
                            r[key].paterno,
                            r[key].materno,
                            '<a href="#" class="vendedor-remove separator-icon-td" data-turno="' + turno + '"  data-id="' + r[key].idVendedor + '" data-url="eliminarVendedor"><i class="splashy-remove" title="Eliminar"></a>'
                        ]);
                    }
                }
            });
        }

        $('#mdl-form').validate({
            rules: {
                descripcion: {
                    required: true
                },
                horaInicio: {
                    required: true
                },
                horaFinal: {
                    required: true
                }

            },
            messages: {
                descripcion: {
                    required: 'Este campo es obligatorio'
                },
                horaInicio: {
                    required: 'Este campo es obligatorio'
                },
                horaFinal: {
                    required: 'Este campo es obligatorio'
                }

            }
        });

        function _setTime(selector) {
            $(selector).each(function (index) {
                var timeStamp = $(this).text();
                if (timeStamp) {
                    $(this).text(_ParseTimestamp(timeStamp));
                }
            });
        }

        function _setInputTime(selector) {
            var time = $(selector).val();
            time = _ParseTimestamp(time);
            $(selector).val(time);
        }

        function _setTimePicker(selector) {
            $(selector).clockpicker({placement: 'bottom',
                donetext: 'Escoger'});
        }



        function addRow(r) {
            var properties = '${tableProperties}'.split(','),
                    mainId = properties.splice(0, 1)[0],
                    row, data, edit, change, remove;

            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    data = r[key];
                    edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data[mainId] + '" data-url="${editUrl}"><i class="splashy-pencil" title="Editar"></i></a>';
                    change = '<a  href="#" class="row-data-change separator-icon-td" data-activo="' + data.activo + '" data-id="' + data[mainId] + '" data-url="${changeUrl}">' + _obtenerEstado(data.activo) + '</a>';
                    remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data[mainId] + '" data-url="${removeUrl}"><i class="splashy-remove" title="Eliminar"></a>';
                    row = [];
                    for (var property in properties) {
                        if (properties.hasOwnProperty(property)) {
                            var index = properties[property].split(':'),
                                    value = data[index[0]] || '';

                            row.push('<span class=' + properties[property] + '>' + fieldFormat[index[1]](value) + '</span>');
                        }
                    }
                    row.push(edit + change + remove);
                    table.fnAddData(row);
                }
            }
            $('#tblData-table').triggerHandler('Cargada');
        }
        function _obtenerEstado(estado) {
            return (estado) ? '<i class="splashy-thumb_up" title="Cambiar estado a Desactivado"></i>' : '<i class="splashy-thumb_down" title=" Cambiar estado a Activado"></i>';
        }

        function _hideVendedoresTable() {
            $(this).find('a').text('Mostrar Vendedores');
            var form = $('#modalData-modal').find('form'),
                    tableBox = $('#vendedoresTurno-list-box');

            var table = $('#tblVendedoresTurno').dataTable();
            if (!tableBox.hasClass('hidden')) {
                $(this).find('a').text('Mostrar Turno');
                table.fnClearTable();
                form.toggle();
                tableBox.toggleClass('hidden');
            }
        }

    });
</script>