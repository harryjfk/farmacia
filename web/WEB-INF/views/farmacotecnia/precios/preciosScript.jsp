<script>
    $(document).ready(function () {
        _crearUnidadChosenSelect();
        $('body').addClass('no-change');
        window.gp.ajaxList = "getPrecios";
        $('#startDate,#endDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('.main-button-row').hide();
        $('#modalData-modal').bind({
            Añadir: function (event) {
                event.preventDefault();
                var almacen = $('#almacenSelect').val();
                $('#almacen').val(almacen);
            },
            Editar: function (event, id, r) {
                event.preventDefault();
                $("#unidad").val(r.unidad.idUnidadMedida);
                $('#unidad').removeClass('chzn-done');
                $("#unidad_chzn").remove();
                _crearUnidadChosenSelect();
                var almacen = $('#almacenSelect').val();
                $('#almacen').val(almacen);
                
                $("#tipoProducto").val(r.tipoProducto.idTipoProducto);
                $("#precio").val(fieldFormat.money2(r.precio));
            }
        });

        function _crearUnidadChosenSelect() {
            $("#unidad").chosen({no_results_text: "No se encontraron coincidencias con"});
            $("#unidad_chzn").css({width: '100%'});
            $("#unidad_chzn .chzn-drop").css({width: '98%'});
            $("#unidad_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
        //Validar Precio
        $.validator.addMethod("currency", function (value, element) {
            return this.optional(element) || /^(\d+)[\.|,](\d{1,4})/.test(value);
        }, "Please specify a valid amount");
        $('#mdl-frm').validate({
            rules: {
                nombre: {required: true},
                unidad: {required: true},
                precio: {required: true, currency: true},
                cantidad: {required: true, digits: true}
            },
            messages: {
                nombre: {required: 'Este campo es obligatorio'},
                unidad: {required: 'Este campo es obligatorio'},
                precio: {required: 'Este campo es obligatorio', currency: 'Especifique un precio v&aacute;lido (Solo se admiten de 2 a 4 d&iacute;gitos despu&eacute;s del punto o la coma)'},
                cantidad: {required: 'Este campo es obligatorio', digits: 'Este campo solo admite d&iacute;gitos'}
            }
        });
        $('#almacenSelect,#startDate,#endDate').change(function (e) {
            e.preventDefault();
            window.gp.cleanTable();
            if ($(this).attr('id') === 'almacenSelect' && $(this).val() > 0) {
                $('.main-button-row').show();
                $('.main-button-row').find('#btnAgregar').show();
                window.gp.data = {almacen: $(this).val()};
                window.gp.getData();
            }
            else if ($(this).attr('id') === 'startDate' || $(this).attr('id') === 'endDate') {
                $('.main-button-row').show();
                $('.main-button-row').find('#btnAgregar').hide();
            } else {
                $('.main-button-row').find('#btnAgregar').hide();
            }
        });
        _AlmacenSelectFill();
        function _AlmacenSelectFill() {
            $.ajax({
                url: 'getAlmacenes',
                type: "GET",
                dataType: "json",
                success: function (r) {
                    var select = $('#almacenSelect'),
                            td, option;
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            td = r[key];
                            option = new Option(td.descripcion, td.idAlmacen);
                            select.append(option);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        }
        ;
        $('#consultar').click(function (e) {
            var start = $('#startDate').val();
            var end = $('#endDate').val();
            $.ajax({
                url: 'consultar',
                type: "GET",
                data: {
                    idAlmacen: $('#almacenSelect').val(),
                    start: start,
                    end: end
                },
                dataType: "json",
                success: function (r) {
                    window.gp.cleanTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
        $('#inicializar').click(function () {
            $('#startDate,#endDate').val('');
        });
    });
</script>