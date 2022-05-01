<script>
    $(document).ready(function () {
        $('#fecha').datepicker({
            dateFormat: 'dd/mm/yy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
                'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun',
                'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Mi&eacute;rcoles', 'Jueves', 'Viernes', 'S&aacute;bado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mi&eacute;', 'Juv', 'Vie', 'S&aacute;b'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'S&aacute;']
        });
         $('.main-button-row').hide();
        $('body').addClass('no-change');
        window.gp.ajaxList = 'getPracticas';

        $('body').undelegate('#btnGuardar', 'click');
        $('#modalData-modal .modal-title').val('Agregar');
        var idModulo = location.pathname.split('/')[3];
        var modal = new ClienteGeneral(idModulo),
                modalVendedor = new VendedorGeneral(idModulo);
        $('#productoCorrregido').append(new Option('-- Seleccione el producto correcto --', 0));

        modal.get('table').removeClickEvent();
        modalVendedor.get('table').removeClickEvent();
        $('#seleccionaCli').click(function (e) {
            e.preventDefault();
            modal._modal.show();
        });
        $('#personalCorrige').focus(function (e) {
            e.preventDefault();
            modalVendedor.get('modal').show();
        });
        $('#fecha').change(function(e){
            e.preventDefault();
            window.gp.temp.fecha=$(this).val();
        });
        modal._table.set('click', function (obj) {
            if(!obj.idCliente) return;
            $('#cliente').val(obj.nombre + ' ' + obj.apellidoPaterno + ' ' + obj.apellidoMaterno);
            window.gp.temp = {
                idCliente: obj.idCliente
            };
            modal.get('modal').hide();
            $.ajax({
                url: 'getVentas',
                type: "GET",
                dataType: "json",
                data: {idCliente: obj.idCliente},
                success: function (r) {

                    var select = $('#ventas');
                    select.html('');
                    select.append(new Option('-- Seleccione una Venta --', 0));
                    for (var item in r) {
                        if (r.hasOwnProperty(item)) {
                            select.append(new Option(r[item].nroVenta, r[item].id));
                        }
                    }
                },
                failure: function (r) {
                }
            });
        });
        modal._table.addRowClickEvent();
        modalVendedor._table.set('click', function (obj) {
            window.gp.temp.idVendedor = obj.idVendedor;
            $('#codigoPersCorrige').val(obj.codigo);
            $('#personalCorrige').val(obj.paterno + ' ' + obj.materno + ' ' + obj.nombre);
            modalVendedor.get('modal').hide();

        });
        modalVendedor._table.addRowClickEvent();

        $('#productoErroneo').change(function () {
            var value = $(this).val();
            if (value > 0) {
                window.gp.temp.idProductoErroneo = value;
            }
        });
        $('#productoCorrregido').change(function () {
            var value = $(this).val();
            if (value > 0) {
                window.gp.temp.idProductoCorregido = value;
            }
        });

        $('body').delegate('#btnGuardar', 'click', function (e) {
            e.preventDefault();
            window.gp.temp.observacion = $('#motivo').val();
            var url = $('#mdl-form').attr('action');
            $.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                data: {
                    idVenta: window.gp.temp.idVenta,
                    idProductoErroneo: window.gp.temp.idProductoErroneo,
                    idProductoCorregido: window.gp.temp.idProductoCorregido,
                    idVendedor: window.gp.temp.idVendedor,
                    observacion: window.gp.temp.observacion,
                    id:window.gp.temp.id,
                    fecha:window.gp.temp.fecha
                },
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
                    $('#modalData-modal').modal('hide');
                    window.gp.getData();
                },
                failure: function (r) {
                }
            });
        });


        _ProductoSelectFill('#productoCorrregido');

        $('#btnAgregar').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: 'getVenta',
                type: "GET",
                data: {idVenta: window.gp.temp.idVenta},
                dataType: "json",
                success: function (r) {
                    $('#codigoPersError').val(r.vendedor.codigo);
                    $('#personalError').val(r.vendedor.nombreVendedor);
                    window.gp.temp.venta = r;

                   _ProductoErroneoFill(r);
                    $('#modalData-modal').modal('show');

                },
                failure: function (r) {
                }
            });
        });

        function _ProductoErroneoFill(r){
            var selectError = $('#productoErroneo'),
                    producto;
            selectError.append(new Option('-- Seleccione un Producto Erroneo --', 0));
            for (var item in r.ventaProductos) {
                if (r.ventaProductos.hasOwnProperty(item)) {
                    producto = r.ventaProductos[item].producto;
                    selectError.append(new Option(producto.descripcion, producto.idProducto));
                }
            }
        }


        $('#ventas').change(function () {
            var value = $(this).val();
            if (value > 0) {
                 $('.main-button-row').show();
                window.gp.temp.idVenta = value;
                window.gp.data = {idVenta: value};
                window.gp.getData();
                window.gp.reportData ={ idVenta:value};
            }
            else {
                 $('.main-button-row').hide();
            }
        });

        $('#modalData-modal').bind({
            Editar: function (event, id, data) {
                event.preventDefault();
                _ProductoErroneoFill(data.venta);
                window.gp.temp.idVenta = data.venta.id;
                window.gp.temp.idProductoErroneo=data.producto.idProducto;
                window.gp.temp.idProductoCorregido=data.productoCorregido.idProducto;
                window.gp.temp.idVendedor=data.vendedor.idVendedor;
                window.gp.temp.observacion=data.observacion;
                window.gp.temp.id=data.id;
                $('#productoErroneo').val(data.producto.idProducto);
                $('#productoCorrregido').val(data.productoCorregido.idProducto);
                $('#motivo').val(data.observacion);
                $('#codigoPersError').val(data.venta.vendedor.codigo);
                $('#codigoPersCorrige').val(data.vendedor.codigo);
                $('#personalError').val(data.venta.vendedor.nombreVendedor);
                $('#personalCorrige').val(data.vendedor.nombreVendedor);
                $('#fecha').val(fieldFormat.date(data.fecha));
                window.gp.temp.fecha=fieldFormat.date(data.fecha);
            }});
        $('#mdl-form').validate({
            rules: {
                personalCorrige: {
                    required: true
                },
                productoErroneo: {
                    required: true
                },
                productoCorrregido: {
                    required: true
                },
                motivo: {
                    required: true
                }
            },
            messages: {
                personalCorrige: {
                    required: 'Este campo es obligatorio'
                },
                productoErroneo: {
                    required: 'Este campo es obligatorio'
                },
                productoCorrregido: {
                    required: 'Este campo es obligatorio'
                },
                motivo: {
                    required: 'Este campo es obligatorio'
                }
            }
        });
      
    });
</script>