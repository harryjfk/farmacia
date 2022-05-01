<%@include file="../includeTagLib.jsp" %> 
<%@ taglib uri="stcJdbc" prefix="stcJdbc"%>
<style>
    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 60%;
    }

    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
    }
</style>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Periodo <span class="f_req">*</span></label>                    
                    <input type="text" class="form-control" readonly="" value="${periodo.nombreMes} - ${periodo.anio}"/>
                </div>               
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Registro <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaRegistro" name="fechaRegistro" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaRegistro');"></i></span>
                    </div>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenOrigen" name="idAlmacenOrigen"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenOrigen" class="form-control" readonly="" />
                        <span class="input-group-addon" onclick="modalAlmacen('O', event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">                
                <div class="col-sm-3 col-md-3">
                    <label>Concepto <span class="f_req">*</span></label>
                    <select id="idConcepto" name="idConcepto" class="form-control" data-req="">                        
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="concepto" items="${conceptos}">
                            <option value="${concepto.idConcepto}">${concepto.nombreConcepto}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Tipo Documento</label>
                    <select id="idTipoDocumentoMov" name="idTipoDocumentoMov" class="form-control">
                        <option value="null">-NINGUNO-</option>                      
                    </select>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Nro. Documento</label>
                    <input type="text" id="numeroDocumentoMov" name="numeroDocumentoMov" class="form-control" maxlength="20"/>
                </div>
            </div>            
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Referencia</label>
                    <input type="text" id="referencia" name="referencia" class="form-control" maxlength="100"/>
                </div>
            </div>
        </div>
        <div>
            <div class="col-sm-12 col-md-12">
                <div class="row">
                    <button id="btnAgregarProducto" class="btn btn-default" onclick="modalProductos(event)"><i class="splashy-add_small"></i> Agregar Producto</button>
                    <span class="help-block">Tecla rápida: CTRL + Q</span>
                </div>
                <div class="row">
                    <table id="detalleSalida" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                                <th style="width: 8%;">Item</th>
                                <th style="width: 25%;">Producto</th>
                                <th style="width: 8%;">Cantidad</th>
                                <th style="width: 8%;">Precio U.</th>
                                <th style="width: 12%;">Total</th>
                                <th style="width: 20%;">Lote</th>                                
                                <th style="width: 15%;">Fecha Vencimiento</th>
                                <th style="width: 4%;"></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="form-actions">
            <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
            <button id="btnCancelar" class="btn btn-default">Cancelar</button>
        </div>
    </div>
</div>

<div class="modal fade" id="modalProducto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProducto" class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Criterio</label>
                                    <input type="text" id="criterio" name="criterio" class="form-control" maxlength="100"/>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>&nbsp;</label>
                                    <button id="btnBuscar" class="btn btn-primary">Buscar</button>
                                </div>
                            </div>
                        </div>                       
                        <div class="formSep">
                            <table id="tblProductos" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Codigo Sismed</th>
                                        <th>Medicamento o Insumo</th>
                                        <th>FF</th>
                                        <th>Cantidad</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="idProducto" name="idProductoSalida" value="0"/>
                                    <input type="text" id="txtProducto" name="txtProductoSalida" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidad" name="cantidadSalida" class="form-control" />
                                </div>
                            </div>
                        </div>                
                        <div id="divMessage"></div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-id="btnAgregarDetalle" class="btn btn-default" type="submit">Guardar</button>
                <button data-dismiss="modal" class="btn btn-primary">Cancelar</button>                
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div class="col-sm-10 col-md-10">
                        <div id="almacenesTreeview"></div>
                    </div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div id="mensajeTree" class="col-sm-10 col-md-10"></div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnCerrarModalAlmacen" data-dismiss="modal" class="btn btn-primary">Cerrar</button>
                <button id="btnQuitarNodoSeleccion" class="btn btn-default" data-tipo="">Quitar Selección</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#fechaRegistro').val(new Date().toString(dateFormatJS));

    var tblProductos = document.getElementById('tblProductos');

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaSalida" />';
    });
    
    var $fechaRegistro = $('#fechaRegistro');
    $fechaRegistro.data('oldVal',  $fechaRegistro.val());
     
    $fechaRegistro.change(function (e){
        if($('#detalleSalida tbody tr').length > 0){
            smokeConfirm('Al cambiar la fecha de registro perderá los productos agregados ¿desea continuar?', function (ev){
                if(ev){
                    borrarDetalle();
                    $fechaRegistro.data('oldVal', $fechaRegistro.val());
                }else{
                    $fechaRegistro.val($fechaRegistro.data('oldVal'));
                }
            });
        }
    });

    $('#idConcepto').change(function() {
        var value = $(this).val();
        $.ajax({
            url: '<c:url value="/ConceptoTipoDocumentoMov/listarIdDocumento" />?idConcepto=' + value,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                var optionTipoDocumento = '<option value="null">-NINGUNO-</option>';
                for (var i = 0; i <= data.length - 1; ++i) {
                    if (data[i].tipoDocumentoMov.activo == 1) {
                        optionTipoDocumento += '<option value="' + data[i].tipoDocumentoMov.idTipoDocumentoMov + '">' + data[i].tipoDocumentoMov.nombreTipoDocumentoMov + '</option>';
                    }
                }
                $('#idTipoDocumentoMov').html(optionTipoDocumento);
            }
        });
    });

    $(document).keyup(function(e) {
        if (e.which === 81 && e.ctrlKey) {
            modalProductos(e);
        }
    });

    $(document).ready(function(e) {
        $('#precio').numeric({decimalPlaces: 8, negative: false});
        $('#cantidad').numeric({decimal: false, negative: false});
    });

    $('#almacenesTreeview').treeview({data: []});

    $('#btnQuitarNodoSeleccion').click(function(e) {
        e.preventDefault();

        if ($(this).attr('data-tipo') == 'O') {
            $('#txtAlmacenOrigen').val('');
            $('#idAlmacenOrigen').val('');
            borrarDetalle();
        }

        $('#almacenesTreeview').treeview('clearActivateNode');

        $('#btnCerrarModalAlmacen').click();
    });

    function modalAlmacen(tipo, e) {
        e.preventDefault();

        var eventNodeSelect = null;

        $('#btnQuitarNodoSeleccion').attr('data-tipo', tipo);

        if (tipo === 'O') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Origen');
            $('#mensajeTree').html('<p class="help-block">Al cambiar la selección del almacén perderá los productos agregados.</p>');
            eventNodeSelect = function(event, node) {
                borrarDetalle();
                $('#txtAlmacenOrigen').val(node.text);
                $('#idAlmacenOrigen').val(node.idAlmacen);
            };
        }

        $('#modalAlmacen').modal('show');

        $.ajax({
            url: '<c:url value="/Almacen/almacenesTree" />',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {

                var treeView = new Array();

                for (var i = 0; i <= dataResponse.length - 1; ++i) {
                    var tree = new Object();
                    tree.text = dataResponse[i].almacen;
                    tree.idAlmacen = dataResponse[i].idAlmacen;
                    tree.nodes = new Array();

                    if (dataResponse[i].almacenes.length > 0) {
                        for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {
                            var treeLevelTwo = new Object();
                            treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                            treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                            treeLevelTwo.nodes = new Array();
                            tree.nodes.push(treeLevelTwo);
                        }
                    }

                    treeView.push(tree);
                }

                $('#almacenesTreeview').treeview('remove');

                $('#almacenesTreeview').treeview({
                    levels: 1,
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    nodeIcon: '',
                    data: treeView,
                    onNodeSelected: eventNodeSelect
                });

                if (tipo == 'O') {
                    if ($('#idAlmacenOrigen').val().length) {
                        $("#almacenesTreeview").treeview("activateNode", ['idAlmacen', $('#idAlmacenOrigen').val()]);
                    }
                }
            }
        });
    }

    $('#buscaProducto').keyup(function(e) {
        e.preventDefault();

        if (e.which === 13) {
            $('#btnBuscar').click();
        }
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
            $(tblProductos).dataTable().fnDestroy();
        }

        $(tblProductos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'criterio', "value": $('#criterio').val()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacenOrigen').val()});
            },
            "sAjaxSource": '<c:url value="/Producto/productosSalidaJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": " <'ro w'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-12'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idProducto", sWidth: "6%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectProduct(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "codigoSismed", sWidth: "12%", "bSortable": false},
                {mData: "producto", sWidth: "55%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "15%", "bSortable": false},
                {mData: "cantidad", sWidth: "12%", "bSortable": false}
            ]
        });
    });

    function modalProductos(e) {
        e.preventDefault();
        var dataResponse = new Object();
        dataResponse.mensajesRepuesta = new Array();
        
        if ($('#idAlmacenOrigen').val().length == 0) {            
            dataResponse.mensajesRepuesta.push('Debe seleccionar un almacén para cargar sus productos.');
            errorResponse(dataResponse);
            return;
        }        
        
        if ($('#idConcepto').val() == '-1') {
            dataResponse.mensajesRepuesta.push('Debe seleccionar un concepto para establecer precios.');
            errorResponse(dataResponse);
            return;
        }
        
        if (Date.parseExact($('#fechaRegistro').val(), dateFormatJS) == null) {
            dataResponse.mensajesRepuesta.push('Debe colocar una fecha de registro válida para establecer precios.');
            errorResponse(dataResponse);
            return;
        }
        
        cleanform('#modalProducto');
        $('#divMessage').html('');
        $('#modalProducto').modal('show');
        $('#btnBuscar').click();
    }

    function selectProduct(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(2)').text());
        $('#idProducto').val(idProducto);
    }

    function llenarDetalle(dataResponse) {
        $('#detalleSalida tbody').html('');

        var fila = '';
        for (var i = 0; i <= dataResponse.length - 1; ++i) {
            fila += '<tr>';
            fila += '<td>' + (i + 1) + '</td>';
            fila += '<td>' + dataResponse[i].nombreProducto + '</td>';
            fila += '<td>' + dataResponse[i].cantidad + '</td>';
            fila += '<td>' + dataResponse[i].precio + '</td>';
            fila += '<td>' + dataResponse[i].total + '</td>';
            fila += '<td>' + dataResponse[i].lote + '</td>';
            fila += '<td>' + new Date(dataResponse[i].fechaVencimiento).toString(dateFormatJS) + '</td>';
            fila += '<td><a href="#" class="separator-icon-td" onclick="quitarDetalle(' + dataResponse[i].idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
            fila += '</tr>';
        }

        $('#detalleSalida tbody').html(fila);
    }

    function getDetalleSalida() {
        $.ajax({
            url: '<c:url value="/NotaSalida/cargarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    function quitarDetalle(idProducto, e) {
        e.preventDefault();

        $.ajax({
            url: '<c:url value="/NotaSalida/quitarDetalle" />/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    function borrarDetalle() {
        $.ajax({
            url: '<c:url value="/NotaSalida/borrarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }

    $('[data-id="btnAgregarDetalle"]').click(function(e) {
        e.preventDefault();

        var salidaDetalle = {
            idProducto: $('#idProducto').val(),
            cantidad: $('#cantidad').val()
        };

        var urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').val() + '&idConcepto=' + $('#idConcepto').val() + '&fechaRegistro=' + Date.parseExact($('#fechaRegistro').val(), dateFormatJS).getTime();

        $.ajax({
            url: '<c:url value="/NotaSalida/agregarDetalle" />' + urlExtend,
            data: JSON.stringify(salidaDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleSalida();
                    $('#modalProducto').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#divMessage');
                }
            }
        });
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        var fechaRegistro = Date.parseExact($('#fechaRegistro').val(), dateFormatJS);
        var fechaRegistroTime = null;

        if (fechaRegistro === null) {
            dataResponse.mensajesRepuesta.push('Fecha de Registro inválida.');
            dataResponse.estado = false;
        } else {
            fechaRegistroTime = fechaRegistro.getTime();
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var notaSalida = {
            fechaRegistro: fechaRegistroTime,
            idAlmacenOrigen: $('#idAlmacenOrigen').val(),
            idConcepto: $('#idConcepto').val(),
            idTipoDocumentoMov: $('#idTipoDocumentoMov').valNull(),
            numeroDocumentoMov: $('#numeroDocumentoMov').val(),
            referencia: $('#referencia').val()
        };

        $.ajax({
            url: '<c:url value="/NotaSalida/registrar" />',
            data: JSON.stringify(notaSalida),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function() {
                    $('#btnCancelar').click();
                });
            }
        });
    });
</script>