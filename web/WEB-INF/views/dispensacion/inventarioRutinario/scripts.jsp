<script>
    $(document).ready(function() {
        $("#btnAgregarProductoLote").remove();
        if($("#btnAgregar").length > 0) {
            $("#btnAgregar").attr('id', 'btnAgregarProductoLote');
        }
        $('body').addClass('no-change');
        window.gp.ajaxList = 'getInventarios';
        window.gp.reportData ={idAlmacen:0};
        window.gp.reportCondition=function(){
            if(!window.gp.reportData.idAlmacen || window.gp.reportData.idAlmacen==0 ){
                alertify.error("Debe escoger un Almacen para realizar esta acción.");
                return false;
            }
            return true;
        };
        var modal = new ProductoLoteGeneral();
        modal._table.setColumnsVisibility([0, 1, 2, 8]);
        modal._table.set('dataUrl', 'getProductos');


        modal._table.set('click', function(obj) {

            modal._table.removeClickEvent();

            var data = {
                idAlmacen: obj['idAlmacen'],
                idLote: obj['lote.descripcion'],
                idProducto: obj['producto.idProducto']
            };
            $(this).addClass('insert-disabled');
            setTimeout(function() {
                modal._table.addRowClickEvent();
            }, 3000);

            $.ajax({
                url: 'insertar',
                data: data,
                type: "POST",
                success: function(r) {
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
                        window.gp.getData();
                    }
                },
                failure: function(r) {
                    alertify.error(r.mssg[0]);
                }
            });
        });
        modal._table.addRowClickEvent();

        $('#btnAgregarProductoLote').hide();
        $('#almacenSelect').change(function(e) {
            e.preventDefault();
                            window.gp.reportData.idAlmacen =$(this).val();

            if ($(this).val() > 0) {
                $('#btnAgregarProductoLote').show();
                modal._table.set('parameters', {almacen: $(this).val()});
                modal._table.clear();
                window.gp.data = {idAlmacen: $(this).val()};
                window.gp.getData();
                modal._table.getData();
                
            }
            else {
                $('#btnAgregarProductoLote').hide();
                modal._table.clear();
            }
        });
        $('#btnAgregarProductoLote').click(function(e) {
            e.preventDefault();
            modal._modal.show();
        });

        $('#modalData-modal').bind({
            Editar: function(event, id, inventario) {
                event.preventDefault();
                $('#mdl-form #lote').val(inventario.productolote.lote.descripcion);
                var date = new Date(inventario.productolote.fechaVencimiento);
                date = date.getMonth() + 1 + '-' + date.getDate() + '-' + date.getFullYear()
                $('#mdl-form #fechaVencimiento').val(date);


            }});

        _AlmacenSelectFill();
        function _AlmacenSelectFill() {
            $.ajax({
                url: 'getAlmacenes',
                type: "GET",
                dataType: "json",
                success: function(r) {
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
                failure: function(r) {
                }
            });
        }
        ;
        $('#mdl-form').validate({
            rules: {
                stock: {
                    required: true,
                    digits: true
                },
                stockReal: {
                    required: true,
                    digits: true
                },
                precio: {
                    required: true
                }
            },
            messages: {
                stock: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo acepta digitos'
                },
                stockReal: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo acepta digitos'
                },
                precio: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
    });
</script>