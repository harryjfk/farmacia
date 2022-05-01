<script>
    $(document).ready(function() {
        $('#cantidad').numeric({decimal: false, negative: false});//Cambio Valeria vi 
        var dataConsulta = {paciente: 0, lote: 0, almacen: 0, producto: 0};
        $('body').addClass('no-change');
        window.gp.ajaxList = 'getConsulta';
        var idModulo = location.pathname.split('/')[3];

        $('#btnAgregar').hide();
        var dataConsulta = {paciente: 0};
        var pacienteModal = new PacienteGeneral();
        pacienteModal._table.set('click', _selectPaciente);
        $("#seleccionaPac").click(function() {
            pacienteModal._modal.show();
            var id = '#' + pacienteModal._modal.get('id');
            $(id).find('.modal-dialog').css({width: 'auto'});
        });
        $('#paciente').on('change', function() {
            dataConsulta.paciente = 0;
            $('#btnAgregar').hide();
            $('#id').val(0);
        });
        function _selectPaciente(row) {
            if (row.nombre) {
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno,
                        fecha = new Date(row.fechaNacimiento).getTime();
                row.fechaNacimiento = fecha;
                dataConsulta.paciente = row.paciente;
                $('#codPaciente').val(row.paciente);
                $('#historia').val(row.historia);
                $('#fechaNacimiento').val(fieldFormat.date(row.fechaNacimiento));
                $('#sexo').val(row.sexo);
                $("#paciente").val(nombre);
                $('#btnAgregar').show();
                $('#id').val(row.paciente);
                window.gp.data = {paciente: row.paciente};
                window.gp.getData();
            }
            pacienteModal._modal.hide();
        }
        window.gp.reportCondition = function() {
            if (!dataConsulta.paciente) {
                alertify.error("Debe escoger un Paciente para realizar esta acción.");
                return false;
            }
            window.gp.reportData = {paciente: dataConsulta.paciente};
            return true;
        };

        $('#producto').change(function(e) {
            e.preventDefault();

            var idproducto = $(this).val();
            var result = $('#producto option[data-id=' + idproducto + ']');
            $('#idProducto').val(idproducto);
            var almacen = result.attr('data-almacen');
            $('#almacen').val(almacen);
            $('#lote').val(result.attr('data-lote'));
            
        });
        $('#modalData-modal').bind({
            Añadir: function(event) {
                event.preventDefault();
                $('#id').val(dataConsulta.paciente);
                var idproducto = $('#producto').val();
                var result = $('#producto option[data-id=' + idproducto + ']');
                $('#idProducto').val(idproducto);
                var almacen = result.attr('data-almacen');
                $('#almacen').val(almacen);
                $('#lote').val(result.attr('data-lote'));


            },
            Editar: function(event, id, data) {
                event.preventDefault();
                $('#productoLote').val(data.productoLote.producto.descripcion);
                $('#almacen').val(data.idAlmacen);
                $('#lote').val(data.idLote);
                $('#idProducto').val(data.idProducto);
                $('#id').val(data.idReceta);
            }});
        $('#mdl-form').validate({
            rules: {
                productoLote: {required: true},
                cantidad: {required: true}

            },
            messages: {
                productoLote: {required: 'Este campo es obligatorio'},
                cantidad: {required: 'Este campo es obligatorio'}
            }
        });
        _ProductoRecetaSelectFill('#producto');

    });
</script>
