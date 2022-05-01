<%@include file="../includeTagLib.jsp" %> 
<style>
    #modalProveedor .modal-dialog
    {
        margin-top: 5%;
        width: 60%;
    }

    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 60%;
    }

    #modalProductoSalida .modal-dialog
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
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen</label>
                    <input type="hidden" id="idAlmacenOrigen" name="idAlmacenOrigen" value="null"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenOrigen" class="form-control" readonly="" />
                        <span class="input-group-addon" onclick="modalAlmacen('O', event)"><i class="splashy-help"></i></span>
                    </div>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Destino <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenDestino" name="idAlmacenDestino" data-req=""/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenDestino" class="form-control" readonly="" />
                        <span class="input-group-addon" onclick="modalAlmacen('D', event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>                
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Concepto <span class="f_req">*</span></label>
                    <select id="idConcepto" name="idConcepto" class="form-control" data-req="">                        
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="concepto" items="${conceptos}">
                            <option value="${concepto.idConcepto}">${concepto.nombreConcepto}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Tipo Documento <span class="f_req">*</span></label>
                    <select id="idTipoDocumentoMov" name="idTipoDocumentoMov" class="form-control" data-req="">
                        <option value="null">-NINGUNO-</option>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label id="lblNroDocumento">Nro. Documento <span class="f_req">*</span></label>
                    <input type="text" id="numeroDocumentoMov" name="numeroDocumentoMov" class="form-control" maxlength="20" data-req=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Recepción <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaRecepcion" name="fechaRecepcion" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaRecepcion');"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label id="lblDocOrigen">Documento Origen</label>
                    <select id="idDocumentoOrigen" name="idDocumentoOrigen" class="form-control">
                        <option value="null">-NINGUNO-</option>
                    </select>
                </div>               
                <div class="col-sm-3 col-md-3">
                    <label id="lblNroDocOrigen">Nro. Documento Origen</label>
                    <input type="text" id="numeroDocumentoOrigen" name="numeroDocumentoOrigen" class="form-control" maxlength="20"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label id="lblFechaDocOrigen">Fecha Doc. Origen</label>
                    <div class="input-group">
                        <input type="text" id="fechaDocumentoOrigen" name="fechaDocumentoOrigen" class="form-control" data-field-date="" />
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDocumentoOrigen');"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label id="lblProveedor">Proveedor</label>
                    <input type="hidden" id="idProveedor" name="idProveedor" value="null"/>
                    <div class="input-group">
                        <input type="text" id="txtProveedor" class="form-control" readonly="" />
                        <span class="input-group-addon" id="btnProveedor"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Referencia</label>
                    <input type="text" id="referencia" name="referencia" class="form-control" maxlength="100"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">            
                <div class="col-sm-3 col-md-3">
                    <label id="lblTipoCompra">Tipo Compra</label>
                    <select id="idTipoCompra" name="idTipoCompra" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="tipoCompra" items="${tiposCompra}">
                            <option value="${tipoCompra.idTipoCompra}">${tipoCompra.nombreTipoCompra}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label id="lblTipoProceso">Tipo Proceso</label>
                    <select id="idTipoProceso" name="idTipoProceso" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="tipoProceso" items="${tiposProceso}">
                            <option value="${tipoProceso.idTipoProceso}">${tipoProceso.nombreTipoProceso}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label id="lblNroProceso">Nro. Proceso</label>
                    <input type="text" id="numeroProceso" name="numeroProceso" class="form-control" maxlength="20"/>
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
                    <table id="detalleIngreso" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                                <th style="width: 7%;">Item</th>
                                <th style="width: 25%;">Producto</th>
                                <th style="width: 8%;">Cantidad</th>
                                <th style="width: 8%;">Precio U.</th>
                                <th style="width: 12%;">Total</th>
                                <th style="width: 12%;">Lote</th>
                                <th style="width: 12%;">Fecha Vencimiento</th>
                                <th style="width: 12%;">Registro Sanitario</th>
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

<div class="modal fade" id="modalProveedor">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Proveedor</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblProveedores" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Razón Social</th>                   
                                    <th>Ruc</th>                                
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cerrar</button>
                <button id="btnQuitarProveedor" class="btn btn-default" data-tipo="">Quitar Selección</button>
            </div>
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
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="idProducto" name="idProducto" value="0"/>
                                    <input type="text" id="txtProducto" name="txtProducto" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Lote <span class="f_req">*</span></label>
                                    <input type="text" id="lote" name="lote" class="form-control"/> 
                                    <span class="help-block">Seleccione un producto para desbloquear.</span>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-3 col-md-3">
                                    <label>Precio <span class="f_req">*</span></label>
                                    <input type="text" id="precio" name="precio" class="form-control" />
                                </div>
                                <div class="col-sm-3 col-md-3">
                                    <label>Registro Sanitario <span class="f_req">*</span></label>
                                    <input type="text" id="registroSanitario" name="registroSanitario" class="form-control" />
                                </div>
                                <div class="col-sm-3 col-md-3">
                                    <label>Fecha Vencimiento <span class="f_req">*</span></label>
                                    <div class="input-group">
                                        <input type="text" id="fechaVencimiento" name="fechaVencimiento" class="form-control" data-field-date="" />
                                        <span class="input-group-addon"><i id="iconFechaVencimiento" class="splashy-calendar_month" onclick="mostrarCalendar('fechaVencimiento')"></i></span>
                                    </div>
                                </div>
                                <div class="col-sm-3 col-md-3">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidad" name="cantidad" class="form-control" />
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

<div class="modal fade" id="modalProductoSalida">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProductoSalida" class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Criterio</label>
                                    <input type="text" id="criterioSalida" name="criterioSalida" class="form-control" maxlength="100"/>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>&nbsp;</label>
                                    <button id="btnBuscarSalida" class="btn btn-primary">Buscar</button>
                                </div>
                            </div>
                        </div>                       
                        <div class="formSep">
                            <table id="tblProductosSalida" class="table table-bordered table-striped">
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
                                    <input type="hidden" id="idProductoSalida" name="idProductoSalida" value="0"/>
                                    <input type="text" id="txtProductoSalida" name="txtProductoSalida" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidadSalida" name="cantidadSalida" class="form-control" />
                                </div>
                            </div>
                        </div>                
                        <div id="divMessageSalida"></div>
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
    var $fechaRegistro = $('#fechaRegistro');
    $fechaRegistro.val(new Date().toString(dateFormatJS));

    var tblProveedores = document.getElementById('tblProveedores');
    var tblProductos = document.getElementById('tblProductos');
    var tblProductosSalida = document.getElementById('tblProductosSalida');

    function state(selector, isEnabled) {
        if (isEnabled == 0) {
            $(selector).attr('disabled', 'disabled');
        } else {
            $(selector).removeAttr('disabled');
        }
    }

    function documentoOrigenReq(isReq) {
        if (isReq == '1') {
            $('#lblDocOrigen').html('Documento Origen <span class="f_req">*</span>');
            $('#idDocumentoOrigen').attr('data-req', '');
            $('#idDocumentoOrigen').removeAttr('disabled');
            $('#lblNroDocOrigen').html('Nro. Documento Origen <span class="f_req">*</span>');
            $('#numeroDocumentoOrigen').attr('data-req', '');
            $('#numeroDocumentoOrigen').removeAttr('disabled');
            $('#lblFechaDocOrigen').html('Fecha Doc. Origen <span class="f_req">*</span>');
            $('#fechaDocumentoOrigen').attr('data-req', '');
            $('#fechaDocumentoOrigen').removeAttr('disabled');
        } else {
            $('#lblDocOrigen').html('Documento Origen');
            $('#idDocumentoOrigen').removeAttr('data-req');
            $('#idDocumentoOrigen').val('null');
            $('#idDocumentoOrigen').attr('disabled', 'disabled');
            $('#lblNroDocOrigen').html('Nro. Documento Origen');
            $('#numeroDocumentoOrigen').removeAttr('data-req');
            $('#numeroDocumentoOrigen').val('');
            $('#numeroDocumentoOrigen').attr('disabled', 'disabled');
            $('#lblFechaDocOrigen').html('Fecha Doc. Origen');
            $('#fechaDocumentoOrigen').removeAttr('data-req');
            $('#fechaDocumentoOrigen').val('');
            $('#fechaDocumentoOrigen').attr('disabled', 'disabled');
        }
    }

    function compraReq(isReq) {
        if (isReq == '1') {
            $('#lblTipoCompra').html('Tipo Compra <span class="f_req">*</span>');
            $('#idTipoCompra').attr('data-req', '');
            $('#idTipoCompra').removeAttr('disabled');
            $('#lblTipoProceso').html('Tipo Proceso <span class="f_req">*</span>');
            $('#idTipoProceso').attr('data-req', '');
            $('#idTipoProceso').removeAttr('disabled');
            $('#lblNroProceso').html('Nro. Proceso <span class="f_req">*</span>');
            $('#numeroProceso').attr('data-req', '');
            $('#numeroProceso').removeAttr('disabled');
        } else {
            $('#lblTipoCompra').html('Tipo Compra');
            $('#idTipoCompra').removeAttr('data-req');
            $('#idTipoCompra').val('null');
            $('#idTipoCompra').attr('disabled', 'disabled');
            $('#lblTipoProceso').html('Tipo Proceso');
            $('#idTipoProceso').removeAttr('data-req');
            $('#idTipoProceso').val('null');
            $('#idTipoProceso').attr('disabled', 'disabled');
            $('#lblNroProceso').html('Nro. Proceso');
            $('#numeroProceso').removeAttr('data-req');
            $('#numeroProceso').val('');
            $('#numeroProceso').attr('disabled', 'disabled');
        }
    }

    function proveedorReq(isReq) {
        if (isReq == '1') {
            $('#lblProveedor').html('Proveedor <span class="f_req">*</span>');
            $('#txtProveedor').attr('data-req', '');
            $('#btnProveedor').attr('onclick', 'modalProveedor(event)');
        } else {
            $('#lblProveedor').html('Proveedor');
            $('#txtProveedor').removeAttr('data-req');
            $('#btnProveedor').attr('onclick', 'noProveedor()');
            $('#txtProveedor').val('');
            $('#idProveedor').val('null');
        }
    }
   
    $fechaRegistro.data('oldVal',  $fechaRegistro.val());
     
    $fechaRegistro.change(function (e){
        if($('#detalleIngreso tbody tr').length > 0){
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
        documentoOrigenReq(0);
        compraReq(0);
        proveedorReq(0);
        $('#lblNroDocumento').html('Nro. Documento <span class="f_req">*</span>');
        $('#numeroDocumentoMov').attr('data-req', '');
        $('#numeroDocumentoMov').removeAttr('disabled');

        $.when(
                $.ajax({url: '<c:url value="/ConceptoDocumentoOrigen/listarIdDocumento" />?idConcepto=' + value, type: 'GET', dataType: 'json'}),
        $.ajax({url: '<c:url value="/ConceptoTipoDocumentoMov/listarIdDocumento" />?idConcepto=' + value, type: 'GET', dataType: 'json'})
                ).then(function(data1, data2) {
            var optionDocumentoOrigen = '<option value="null">-NINGUNO-</option>';

            for (var i = 0; i <= data1[0].length - 1; ++i) {
                if (data1[0][i].documentoOrigen.activo == 1) {
                    optionDocumentoOrigen += '<option value="' + data1[0][i].documentoOrigen.idDocumentoOrigen + '">' + data1[0][i].documentoOrigen.nombreDocumentoOrigen + '</option>';
                }
            }
            $('#idDocumentoOrigen').html(optionDocumentoOrigen);

            var optionTipoDocumento = '<option value="null">-NINGUNO-</option>';
            for (var i = 0; i <= data2[0].length - 1; ++i) {
                if (data2[0][i].tipoDocumentoMov.activo == 1) {
                    optionTipoDocumento += '<option value="' + data2[0][i].tipoDocumentoMov.idTipoDocumentoMov + '">' + data2[0][i].tipoDocumentoMov.nombreTipoDocumentoMov + '</option>';
                }
            }
            $('#idTipoDocumentoMov').html(optionTipoDocumento);

            //Compra
            if (value == '1') {
                documentoOrigenReq(1);
                if($("#idTipoDocumentoMov option[value='2']").length > 0){
                    $('#idTipoDocumentoMov').val('2');
                }
                if($("#idDocumentoOrigen option[value='1']").length > 0){
                    $('#idDocumentoOrigen').val('1');
                }                
                compraReq(1);
                proveedorReq(1);
            }

            //Encargo
            //Donacion
            if (value == '2' || value == '3') {
                documentoOrigenReq(1);
                if($("#idTipoDocumentoMov option[value='2']").length > 0){
                    $('#idTipoDocumentoMov').val('2');
                }                
                if($("#idDocumentoOrigen option[value='2']").length > 0){
                    $('#idDocumentoOrigen').val('2');
                }                
                compraReq(0);
                proveedorReq(1);
            }

            //Distribucion
            if (value == '4') {
                documentoOrigenReq(1);
                if($("#idTipoDocumentoMov option[value='3']").length > 0){
                    $('#idTipoDocumentoMov').val('3');
                }
                if($("#idDocumentoOrigen option[value='2']").length > 0){
                    $('#idDocumentoOrigen').val('2');
                }
                compraReq(0);
                proveedorReq(1);
            }

            //Devolucion x vecimiento
            //Devolucion x deterioro
            //Devolucion x stock
            if (value == '5' || value == '6' || value == '7') {
                documentoOrigenReq(0);
                if($("#idTipoDocumentoMov option[value='4']").length > 0){
                    $('#idTipoDocumentoMov').val('4');
                }                
                compraReq(0);
                proveedorReq(0);
            }

            //TRANSFERENCIA OTRAS UE
            if (value == '8' || value == '22') {
                documentoOrigenReq(1);
                if($("#idTipoDocumentoMov option[value='2']").length > 0){
                    $('#idTipoDocumentoMov').val('2');
                }
                if($("#idDocumentoOrigen option[value='3']").length > 0){
                    $('#idDocumentoOrigen').val('3');
                }                
                compraReq(0);
                proveedorReq(1);
            }

            //INVENTARIO
            if (value == '18') {
                documentoOrigenReq(0);
                if($("#idTipoDocumentoMov option[value='11']").length > 0){
                    $('#idTipoDocumentoMov').val('11');
                }                
                compraReq(0);
                proveedorReq(0);
                $('#lblNroDocumento').html('Nro. Documento');
                $('#numeroDocumentoMov').removeAttr('data-req');
                $('#numeroDocumentoMov').attr('disabled', 'disabled');
            }

            //DEVOLUCION X PACIENTE
            if (value == '19') {
                documentoOrigenReq(1);
                if($("#idTipoDocumentoMov option[value='1']").length > 0){
                    $('#idTipoDocumentoMov').val('1');
                }
                if($("#idDocumentoOrigen option[value='2']").length > 0){
                    $('#idDocumentoOrigen').val('2');
                }
                compraReq(0);
                proveedorReq(1);
            }
        });
    });
    
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaIngreso" />';
    });

    $(document).keyup(function(e) {
        if (e.which === 81 && e.ctrlKey) {
            modalProductos(e);
        }
    });

    $(document).ready(function(e) {
        $('#precio').numeric({decimalPlaces: 8, negative: false});
        $('#cantidad').numeric({decimal: false, negative: false});
        $('#cantidadSalida').numeric({decimal: false, negative: false});
    });

    $('#almacenesTreeview').treeview({data: []});
    
    $('#btnQuitarNodoSeleccion').click(function(e) {
        e.preventDefault();
        if ($(this).attr('data-tipo') == 'O') {
            $('#txtAlmacenOrigen').val('');
            $('#idAlmacenOrigen').val('null');
            borrarDetalle();
        } else if ($(this).attr('data-tipo') == 'D') {
            $('#txtAlmacenDestino').val('');
            $('#idAlmacenDestino').val('');
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
                $('#btnQuitarProveedor').click();
            };
        } else if (tipo === 'D') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Destino');
            $('#mensajeTree').html('');
            eventNodeSelect = function(event, node) {
                $('#txtAlmacenDestino').val(node.text);
                $('#idAlmacenDestino').val(node.idAlmacen);
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
                    collapseIcon: 'glyphicon glyphicon-chevron-down', nodeIcon: '',
                    data: treeView,
                    onNodeSelected: eventNodeSelect
                });
                if (tipo == 'O') {
                    if ($('#idAlmacenOrigen').val().length) {
                        $("#almacenesTreeview").treeview("activateNode", ['idAlmacen', $('#idAlmacenOrigen').val()]);
                    }
                } else if (tipo == 'D') {
                    if ($('#idAlmacenDestino').val().length) {
                        $("#almacenesTreeview").treeview("activateNode", ['idAlmacen', $('#idAlmacenDestino').val()]);
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

    $('#buscaProductoSalida').keyup(function(e) {
        e.preventDefault();
        if (e.which === 13) {
            $('#btnBuscarSalida').click();
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
            },
            "sAjaxSource": '<c:url value="/Producto/productosIngresoJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": " <'ro w'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idProducto", sWidth: "6%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectProduct(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "codigoSismed", sWidth: "12%", "bSortable": false},
                {mData: "producto", sWidth: "67%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "15%", "bSortable": false}
            ]
        });
    });
    
    $('#lote').change(function (){
        $.ajax({
            url: '<c:url value="/Movimiento/DetallePorLote" />?idProducto=' + $('#idProducto').val() + '&lote=' + $(this).val(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if(dataResponse.fechaVencimiento != null){
                    $('#fechaVencimiento').prop('disabled', true);
                    $('#precio').prop('disabled', true);
                    $('#iconFechaVencimiento').attr('onclick', '');
                    $('#fechaVencimiento').val(new Date(dataResponse.fechaVencimiento).toString(dateFormatJS));
                    $('#registroSanitario').val(dataResponse.registroSanitario);
                    $('#precio').val(dataResponse.precio);                    
                }else{
                    $('#fechaVencimiento').prop('disabled', false);
                    $('#precio').prop('disabled', false);
                    $('#iconFechaVencimiento').attr('onclick', 'mostrarCalendar("fechaVencimiento")');
                }
            }
        });
    });

    $('#btnBuscarSalida').click(function(e) {
        e.preventDefault();

        if ($.fn.DataTable.fnIsDataTable(tblProductosSalida)) {
            $(tblProductosSalida).dataTable().fnDestroy();
        }

        $(tblProductosSalida).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'criterio', "value": $('#criterio').val()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacenOrigen').val()});
            },
            "sAjaxSource": '<c:url value="/Producto/productosSalidaJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": " <'ro w'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idProducto", sWidth: "6%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectProductSalida(\'' + data + '\', this)"></i>';
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

        if ($('#idAlmacenOrigen').val() == 'null') {
            var fechaRegistro = Date.parseExact($fechaRegistro.val(), dateFormatJS);
            
            if(fechaRegistro == null){
                smokeAlert('Debe ingresar una fecha de registro válida.');
            }else{
                cleanform('#modalProducto');
                $('#divMessage').html('');
                $('#modalProducto').modal('show');
                $('#btnBuscar').click();
                $('#lote').attr('disabled', 'disabled');
            }
        } else {
            cleanform('#modalProductoSalida');
            $('#divMessageSalida').html('');
            $('#modalProductoSalida').modal('show');
            $('#btnBuscarSalida').click();
        }        
    }

    function selectProduct(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(2)').text());
        $('#idProducto').val(idProducto);
        $('#lote').removeAttr('disabled');
    }

    function selectProductSalida(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProductoSalida').val($(tr).find('td:eq(2)').text());
        $('#idProductoSalida').val(idProducto);
    }

    function modalProveedor(e) {
        e.preventDefault();
        $('#modalProveedor').modal('show');
        if ($.fn.DataTable.fnIsDataTable(tblProveedores)) {
            var dataTable = $(tblProveedores).dataTable();
            dataTable.fnReloadAjax();
        } else {
            $(tblProveedores).dataTable({
                "sAjaxSource": '<c:url value="/Proveedor/proveedoresJSON" />',
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "iDisplayLength": 8,
                "aoColumns": [
                    {mData: "idProveedor", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                            return '<i class="splashy-arrow_medium_right" onclick="selectProveedor(\'' + data + '\', this)"></i>';
                        }
                    },
                    {mData: "razonSocial", sWidth: "60%", "bSortable": false},
                    {mData: "ruc", sWidth: "30%", "bSortable": false}
                ]
            });
        }
    }

    function selectProveedor(idProveedor, element) {
        var tr = $(element).parent().parent();
        $('#txtProveedor').val($(tr).find('td:eq(1)').text());
        $('#idProveedor').val(idProveedor);
        $('#modalProveedor').modal('hide');
    }

    function noProveedor() {
        var dataResponse = new Object();
        dataResponse.mensajesRepuesta = new Array();
        dataResponse.mensajesRepuesta.push('No puede seleccionar un proveedor para este concepto.');
        dataResponse.estado = false;
        errorResponse(dataResponse);
    }

    $('#btnQuitarProveedor').click(function(e) {
        e.preventDefault();
        $('#txtProveedor').val('');
        $('#idProveedor').val('null');
        $('#modalProveedor').modal('hide');
    });

    function llenarDetalle(dataResponse) {
        $('#detalleIngreso tbody').html('');
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
            fila += '<td>' + dataResponse[i].registroSanitario + '</td>';
            fila += '<td><a href="#" class="separator-icon-td" onclick="quitarDetalle(' + dataResponse[i].idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
            fila += '</tr>';
        }
        $('#detalleIngreso tbody').html(fila);
    }

    function getDetalleIngreso() {
        $.ajax({
            url: '<c:url value="/NotaIngreso/cargarDetalle" />',
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
            url: '<c:url value="/NotaIngreso/quitarDetalle" />/' + idProducto.toString(),
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
            url: '<c:url value="/NotaIngreso/borrarDetalle" />', data: {},
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

        var ingresoDetalle;
        var url = '<c:url value="/NotaIngreso/agregarDetalle" />';
        if ($('#idAlmacenOrigen').val() == 'null') {
            var fechaVencimiento = Date.parseExact($('#fechaVencimiento').val(), dateFormatJS);
            
            var fechaVencimientoTime = null;
            if (fechaVencimiento === null) {                
            } else {
                var fechaRegistro = Date.parseExact($fechaRegistro.val(), dateFormatJS);
                var fechaRegistroTime = fechaRegistro.getTime();
                fechaVencimientoTime = fechaVencimiento.getTime();
                if(fechaVencimientoTime < fechaRegistroTime){
                    var dataResponse = new Object();
                    dataResponse.mensajesRepuesta = new Array();
                    dataResponse.mensajesRepuesta.push('No puede ingresar una fecha de vencimiento menor a la fecha de registro.');
                    dataResponse.estado = false;
                    jsonToDivError(dataResponse, '#divMessage');
                    return;
                }
            }

            ingresoDetalle = {
                lote: $('#lote').val(),
                idProducto: $('#idProducto').val(),
                nombreProducto: $('#txtProducto').val(),
                cantidad: $('#cantidad').val(),
                precio: $('#precio').val(),
                fechaVencimiento: fechaVencimientoTime,
                registroSanitario: $('#registroSanitario').val()
            };
        } else {
             ingresoDetalle = {
                 idProducto: $('#idProductoSalida').val(),
                 cantidad: $('#cantidadSalida').val()
             };
             url += '?idAlmacen=' + $('#idAlmacenOrigen').val();
        }

        $.ajax({
            url: url,
            data: JSON.stringify(ingresoDetalle),
            type: 'POST',
            dataType: 'json', 
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleIngreso();
                    if ($('#idAlmacenOrigen').val() == 'null') {
                        $('#modalProducto').modal('hide');
                    }else{
                        $('#modalProductoSalida').modal('hide');
                    }                    
                } else {
                    if ($('#idAlmacenOrigen').val() == 'null') {
                        jsonToDivError(dataResponse, '#divMessage');
                    }else{
                        jsonToDivError(dataResponse, '#divMessageSalida');
                    }                    
                }
            }
        });
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();
        var dataResponse = validateForm('[data-req]');
        var fechaRegistro = Date.parseExact($('#fechaRegistro').val(), dateFormatJS);
        var fechaRecepcion = Date.parseExact($('#fechaRecepcion').val(), dateFormatJS);
        var fechaDocumentoOrigen = Date.parseExact($('#fechaDocumentoOrigen').val(), dateFormatJS);
        var fechaRegistroTime = null;
        var fechaRecepcionTime = null;
        var fechaDocumentoOrigenTime = null;
        if (fechaRegistro === null) {
            dataResponse.mensajesRepuesta.push('Fecha de Registro inválida.');
            dataResponse.estado = false;
        } else {
            fechaRegistroTime = fechaRegistro.getTime();
        }

        if (fechaRecepcion === null) {
            dataResponse.mensajesRepuesta.push('Fecha de Recepción inválida.');
            dataResponse.estado = false;
        } else {
            fechaRecepcionTime = fechaRecepcion.getTime();
        }

        if ($('#fechaDocumentoOrigen').val().length > 0) {
            if (fechaDocumentoOrigen === null) {
                dataResponse.mensajesRepuesta.push('Fecha de Documento Origen inválida.');
                dataResponse.estado = false;
            } else {
                fechaDocumentoOrigenTime = fechaDocumentoOrigen.getTime();
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var notaIngreso = {
            fechaRegistro: fechaRegistroTime,
            idAlmacenOrigen: $('#idAlmacenOrigen').valNull(),
            idAlmacenDestino: $('#idAlmacenDestino').val(),
            idConcepto: $('#idConcepto').val(),
            idTipoDocumentoMov: $('#idTipoDocumentoMov').valNull(),
            numeroDocumentoMov: $('#numeroDocumentoMov').val(),
            fechaRecepcion: fechaRecepcionTime,
            idDocumentoOrigen: $('#idDocumentoOrigen').valNull(),
            numeroDocumentoOrigen: $('#numeroDocumentoOrigen').valNull(),
            fechaDocumentoOrigen: fechaDocumentoOrigenTime,
            idProveedor: $('#idProveedor').valNull(),
            idTipoCompra: $('#idTipoCompra').valNull(),
            idTipoProceso: $('#idTipoProceso').valNull(),
            numeroProceso: $('#numeroProceso').val(),
            referencia: $('#referencia').val()
        };
        $.ajax({
            url: '<c:url value="/NotaIngreso/registrar" />',
            data: JSON.stringify(notaIngreso),
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