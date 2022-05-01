<script>
    $(document).ready(function () {
        $('#btnAgregar').hide();
        $('#btnPDF').hide();
        $('#btnExcel').hide();

        $('#tblData-table').bind({
            Cargada: function (event) {
                event.preventDefault();
                $('.row-data-change').hide();
                $('.row-data-remove').hide();
            }
        });

        var recetaModal = $('#addRecetaModal'),
                productoModal = $('#addProductModal');

        var productosTable = new slDataTable('#tblProductosRecetas');
        productosTable.set('dataUrl', 'getRecetasProductos');
        productosTable.set('columns', ['receta.numero', 'idProducto', 'productoLote.producto.descripcion', 'productoLote.producto.registroSanitario', 'cantidad', 'productoLote.producto.idUnidadMedida.nombreUnidadMedida',
            'productoLote.lote.descripcion', 'productoLote.precio', 'productoLote.fechaVencimiento', 'productoLote.producto.presentacion']);
        var productoDelete = {
            id: 'mainId',
            url: 'eliminar/producto',
            classes: 'separator-icon-td producto-row-delete',
            icon: '<i class="splashy-remove" title="Eliminar">'
        };

        productosTable.add('actions', [productoDelete]);
        productosTable.set('success', function (r) {
            var data = [], obj;
            for (var receta in r) {
                obj = r[receta];
                obj.mainId = r[receta].idReceta + '_' + r[receta].idLote + '_' + r[receta].idProducto + '_' + r[receta].idAlmacen;
                data.push(obj);
            }
            return data;
        });

        var recetasTable = new slDataTable('#tblRecetas');
        recetasTable.set('dataUrl', 'getRecetas');
        recetasTable.add('columns', ['numero', 'atendida']);
        var recetasDelete = {
            id: 'id',
            url: 'eliminar',
            classes: 'separator-icon-td receta-row-delete',
            icon: '<i class="splashy-remove" title="Eliminar">'
        };
        recetasTable.add('actions', [recetasDelete]);


        $('#modalData-modal').find('.modal-dialog').addClass('full-width');
        $('#tblProductosRecetas_length').first('label').hide();
        $('#tblRecetas_length').hide();
        $('#btnGuardar').hide();
        $('#modalData-modal').find('.recetasTab').hide();
        $('#modalData-modal').find('.detalles').show();




        $('#modalData-modal').bind({
            Editar: function (event, id) {
                event.preventDefault();
                window.gp.paciente = id;
                $('#addReceta').attr('data-paciente', id);
                $('#addProducto').attr('data-paciente', id);
                recetasTable.clear();
                productosTable.clear();
                recetasTable.set('parameters', {idPaciente: id});
                productosTable.set('parameters', {idPaciente: id});
                recetasTable.getData();
                productosTable.getData();
                _getPacienteDetails(id);
                _NumeroRecetasSelectFill('select#receta', {idPaciente: id});
                _ProductoSelectFill('select#producto');
            }
        });

        $('body').delegate('.nav.nav-tabs li a', 'click', function (e) {
            e.preventDefault();
            $('.nav.nav-tabs li.active').removeClass('active');
            $(this).parent().addClass('active');
            $('#modalData-modal').find('.recetasTab').hide();
            var showTab = '.' + $(this).attr('data-block');
            $('#modalData-modal').find(showTab).show();
        });

        $('body').delegate('#addReceta', 'click', function (e) {
            e.preventDefault();
            recetaModal.modal('show');
            _cleanForm(recetaModal.find('form'));
            $('#addRecetaModal')
                    .find('form')
                    .find('.IDFIELD')
                    .val($(this).attr('data-paciente'));
        });

        $('body').delegate('#addProducto', 'click', function (e) {
            e.preventDefault();
            _cleanForm('#mdlProducto-form');
            $('#addProductModal').modal('show');

        });

        $('body').delegate('#saveReceta', 'click', function (e) {
            e.preventDefault();
            
            var form = recetaModal.find('form'),
                paciente = $(this).attr('data-paciente');
            var success = function (r) {
                _sendNotification(r);
                recetasTable.clear();
                recetasTable.getData();
                _NumeroRecetasSelectFill('select#receta', {idPaciente: window.gp.paciente});

            };
            _insertData(form, _validate, success);

        });
        $('body').delegate('#saveProducto', 'click', function (e) {
            e.preventDefault();
            var form = productoModal.find('form'),
                    success = function (r) {
                        _sendNotification(r);
                        if (!r.hasError) {
                            productosTable.clear();
                            productosTable.getData();
                            productoModal.modal('hide');
                        }
                    };
            _insertData(form, _validateProducto, success);
        });

        $('body').delegate('.receta-row-delete', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr('data-id'),
                    url = $(this).attr('data-url');
            smoke.confirm('¿Si elimina esta receta tambien eliminara los productos de la misma. esta seguro ?', function (e) {
                if (e) {
                    $.ajax({
                        url: url,
                        data: {id: id},
                        type: 'POST',
                        dataType: 'json',
                        success: function (r) {
                            if (r.hasError) {
                                alertify.error(r.mssg[0]);
                            }
                            else {
                                alertify.success(r.mssg[0]);
                                recetasTable.clear();
                                recetasTable.getData();
                            }
                        },
                        failure: function (r) {
                            alertify.error('Ha ocurrido un error');
                        }
                    });
                }
            });
        });
        $('body').delegate('.producto-row-delete', 'click', function (e) {
            e.preventDefault();
            var id = $(this).attr('data-id'),
                    url = $(this).attr('data-url');
            id = id.split('_');
            smoke.confirm('¿Esta seguro de querer eliminar este producto?', function (e) {
                if (e) {
                    $.ajax({
                        url: url,
                        data: {receta: id[0], lote: id[1], producto: id[2], almacen: id[3]},
                        type: 'POST',
                        dataType: 'json',
                        success: function (r) {
                            if (r.hasError) {
                                alertify.error(r.mssg[0]);
                            }
                            else {
                                alertify.success(r.mssg[0]);
                                productosTable.clear();
                                productosTable.getData();
                            }
                        },
                        failure: function (r) {
                            alertify.error('Ha ocurrido un error');
                        }
                    });
                }
            });
        });

        function _getPacienteDetails(id) {
            var url = '/' + window.location.pathname.split('/')[1];
            url += '/Paciente/getPaciente';
            $.getJSON(url, {id: id}, function (r) {
                console.log(r);
                var span;
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        span = $('#modalData-modal').find('#' + key);
                        if (span) {
                            span.text(r[key]);
                        }
                    }
                }
            });
        }

        function _NumeroRecetasSelectFill(selector, data) {
            $.ajax({
                url: 'getRecetas',
                type: "GET",
                dataType: "json",
                data: data || {},
                success: function (r) {
                    var select = $(selector);
                    select.html('');
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            td = r[key];
                            select.append(new Option(td.numero, td.idReceta));
                        }
                    }
                },
                failure: function (r) {
                    console.log(r);
                }
            });
        }

        function _sendNotification(r) {
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

        $('#mdlReceta-form').validate({
            rules: {
                numero: {
                    required: true,
                    digits: true
                },
                atendida: {
                    required: true
                }
            },
            messages: {
                numero: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo permite números'
                },
                atendida: {
                    required: 'Este campo es obligatorio'
                }
            }
        });

        $('#mdlProducto-form').validate({
            rules: {
                receta: {
                    required: true
                },
                producto: {
                    required: true
                },
                registro: {
                    required: true
                },
                cantidad: {
                    required: true,
                    digits: true
                },
                lote: {
                    required: true
                },
                precio: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                receta: {
                    required: 'Este campo es obligatorio'
                },
                producto: {
                    required: 'Este campo es obligatorio'
                },
                registro: {
                    required: 'Este campo es obligatorio'
                },
                cantidad: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo permite números'
                },
                lote: {
                    required: 'Este campo es obligatorio'
                },
                precio: {
                    required: 'Este campo es obligatorio',
                    digits: 'Este campo solo permite números'
                }
            }
        });

        function _validate() {
            var modalForm = recetaModal.find('form'),
                    validation = modalForm.validate();
            if (!validation.checkForm()) {
                validation.showErrors();
                modalForm.find('.error').parent().addClass('has-error');
                modalForm.find('label.error').addClass('control-label');
                return false;
            }
            recetaModal.modal('hide');
            return true;
        }
        function _validateProducto() {
            var modalForm = productoModal.find('form'),
                    validation = modalForm.validate();
            if (!validation.checkForm()) {
                validation.showErrors();
                modalForm.find('.error').parent().addClass('has-error');
                modalForm.find('label.error').addClass('control-label');
                return false;
            }
            return true;
        }

    });
</script>