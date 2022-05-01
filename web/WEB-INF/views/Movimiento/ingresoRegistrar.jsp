<%@include file="../includeTagLib.jsp" %> 
<style>
    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 80%;
    }

    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
    }
    
    #tblProveedor tr td:hover{
        cursor:pointer;
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
                    <input type="text" id="fechaRegistro" class="form-control" readonly=""/>                    
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <input type="hidden" id="idAlmacenLogistica" name="idAlmacenLogistica" />
                <input type="hidden" id="idAlmacenEspecializado" name="idAlmacenEspecializado" />
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenOrigen" name="idAlmacenOrigen"/>
                    <input type="hidden" id="idTipoAlmacenOrigen" name="idTipoAlmacenOrigen"/>
                    <input type="hidden" id="indiceAlmacenHijoOrigen" name="indiceAlmacenHijo"/>
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
                        <span class="input-group-addon" onclick=""><i class="splashy-help"></i></span>                        
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
                <div class="col-sm-3 col-md-3">
                    <label id="lblProveedor">Proveedor</label>
                    <div class="input-group">
                        <input type="text" id="txtProveedorRUC" class="form-control" placeholder="RUC" maxlength="11"/>
                        <span class="input-group-addon" onclick="listarProveedores(event)"><i class="splashy-help"></i></span>
                    </div>
                    </div>
                <div class="col-sm-4 col-md-4">
                    <label>&nbsp;</label>
                    <input type="hidden" id="idProveedor" name="idProveedor" value="null"/>
                    <input type="text" id="txtProveedor" class="form-control" placeholder="Razón Social" maxlength="100"/>
                </div>
                <div class="col-sm-5 col-md-5">
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
    </div>
    <div class="col-sm-12 col-md-12">        
        <button id="btnAgregarProducto" class="btn btn-default" onclick="modalProductos(event)"><i class="splashy-add_small"></i> Agregar Producto</button>
        <span class="help-block">Tecla rápida: CTRL + Q</span>
        
        <table id="detalleIngreso" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th style="width: 7%;">Item</th>
                    <th style="width: 24%;">Producto</th>
                    <th style="width: 8%;">Cantidad</th>
                    <th style="width: 8%;">Precio U.</th>
                    <th style="width: 12%;">Total</th>
                    <th style="width: 12%;">Lote</th>
                    <th style="width: 12%;">Fecha Vencimiento</th>
                    <th style="width: 12%;">Registro Sanitario</th>
                    <th style="width: 5%;"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <table id="detalleSalida" class="table table-bordered table-striped dTableR hide">
            <thead>
                <tr>
                    <th style="width: 8%;">Item</th>
                    <th style="width: 25%;">Producto</th>
                    <th style="width: 8%;">Cantidad</th>
                    <th style="width: 8%;">Precio U.</th>
                    <th style="width: 12%;">Total</th>
                    <th style="width: 19%;">Lote</th>
                    <th style="width: 15%;">Fecha Vencimiento</th>
                    <th style="width: 5%;"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
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
            </div>
            <div class="modal-footer">
                <button id="btnCerrarModalAlmacen" data-dismiss="modal" class="btn btn-primary">Cerrar</button>                
            </div>
        </div>
    </div>
</div>
      
<div class="modal fade" id="modalProveedor">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar proveedor</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblProveedor" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                <th>Código</th>
                                <th>Razón Social</th>
                                <th>Ruc</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
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

<div class="modal fade" id="modProducto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Modificar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="modidProducto" name="modidProducto" value="0"/>
                                    <input type="text" id="modtxtProducto" name="modtxtProducto" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-4 col-md-4">
                                    <label>Lote <span class="f_req">*</span></label>
                                    <input type="text" id="modlote" name="lote" class="form-control" />
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="modcantidad" name="cantidad" class="form-control" />
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Precio <span class="f_req">*</span></label>
                                    <input type="text" id="modprecio" name="modprecio" class="form-control" />
                                </div>                          
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-3 col-md-4">
                                    <label>Registro Sanitario <span class="f_req">*</span></label>
                                    <input type="text" id="modregistroSanitario" name="modregistroSanitario" class="form-control" />
                                </div> 
                                <div class="col-sm-4 col-md-4">
                                    <label>Fecha Vencimiento <span class="f_req">*</span></label>
                                    <div class="input-group">
                                        <input type="text" id="modfechaVencimiento" name="modfechaVencimiento" class="form-control" data-field-date="" />
                                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('modfechaVencimiento');"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="modDivMessage">

                        </div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnModificarDetalle" class="btn btn-default" type="submit">Guardar</button>
                <button data-dismiss="modal" class="btn btn-primary">Cancelar</button>
            </div>
        </div>
    </div>
</div>                        

<!--                
<div class="modal fade" id="modalProductoStock">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProductoStock" class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Criterio</label>
                                    <input type="text" id="criterioStock" name="criterioStock" class="form-control" maxlength="100"/>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>&nbsp;</label>
                                    <button id="btnBuscarStock" class="btn btn-primary">Buscar</button>
                                </div>
                            </div>
                        </div>                       
                        <div class="formSep">
                            <table id="tblProductosStock" class="table table-bordered table-striped">
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
                                    <input type="hidden" id="idProductoStock" name="idProductoSalidaStock" value="0"/>
                                    <input type="text" id="txtProductoStock" name="txtProductoSalidaStock" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidadStock" name="cantidadSalidaStock" class="form-control" />
                                </div>
                            </div>
                        </div>                
                        <div id="divMessageStock"></div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-id="btnAgregarDetalleStock" class="btn btn-primary" type="submit">Guardar</button>
                <button data-dismiss="modal" class="btn btn-default">Cancelar</button>                
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modProductoStock">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Modificar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="modidProductoStock" name="modidProductoStock" value="0"/>
                                    <input type="text" id="modtxtProductoStock" name="modtxtProductoStock" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="modcantidadStock" name="cantidadStock" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div id="modDivMessageStock">

                        </div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default">Cancelar</button>
                <button id="btnModificarDetalleStock" class="btn btn-primary" type="submit">Guardar</button>
            </div>
        </div>
    </div>
</div>-->

<script>
    var date = new Date();
    
    var conceptos = [
    <c:forEach var="concepto" items="${conceptos}">
        {idConcepto:'<c:out value="${concepto.idConcepto}"/>', tipoMovConcepto:'<c:out value="${concepto.tipoMovimientoConcepto}"/>', nombreConcepto:'<c:out value="${concepto.nombreConcepto}"/>'},
    </c:forEach>
    ];

    var tblProductos = document.getElementById('tblProductos');
    
    $(document).ready(function(e) {
        $('#precio').numeric({decimalPlaces: 8, negative: false});
        $('#cantidad').numeric({decimal: false, negative: false});
        $('#fechaRegistro').val(new Date(${fechaRegistro}).toString(dateFormatJS));
        $('#almacenesTreeview').treeview({data: []});
        $('#fechaRecepcion').datepicker().datepicker("setDate", date);
        $('#fechaDocumentoOrigen').datepicker().datepicker("setDate", date);
        
    });
    
    $(document).keyup(function(e) {
        if (e.which === 81 && e.ctrlKey) {
            modalProductos(e);
        }
    });
    
    $('#btnGuardar').click(function(e) {
        e.preventDefault();
        
        
        var dataResponse = validateForm('[data-req]');
        
        var fechaRecepcion = Date.parseExact($('#fechaRecepcion').val(), dateFormatJS);
        var fechaDocumentoOrigen = Date.parseExact($('#fechaDocumentoOrigen').val(), dateFormatJS);        
        var fechaRecepcionTime = null;
        var fechaDocumentoOrigenTime = null;        
        var tipoProveedor=getTipoProveedor();

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
            idAlmacenOrigen: $('#idAlmacenOrigen').val(),
            idAlmacenDestino: $('#idAlmacenDestino').val(),
            idConcepto: $('#idConcepto').val(),
            idTipoDocumentoMov: $('#idTipoDocumentoMov').valNull(),
            numeroDocumentoMov: $('#numeroDocumentoMov').val(),
            fechaRecepcion: fechaRecepcionTime,
            idDocumentoOrigen: $('#idDocumentoOrigen').valNull(),
            numeroDocumentoOrigen: $('#numeroDocumentoOrigen').val(),
            fechaDocumentoOrigen: fechaDocumentoOrigenTime,            
            idTipoCompra: $('#idTipoCompra').valNull(),
            idTipoProceso: $('#idTipoProceso').valNull(),
            numeroProceso: $('#numeroProceso').val(),
            referencia: $('#referencia').val()
        };
        
        if($('#idProveedor').valNull() == null
                && $('#txtProveedorRUC').val() 
                && $('#txtProveedor').val()){
            
            if(tipoProveedor!='NADA'){
                if(tipoProveedor=='D'){
                    notaIngreso.proveedor = {
                    idProveedor: 0,
                    ruc: $('#txtProveedorRUC').val(),
                    razonSocial: $('#txtProveedor').val(),
                    tipoProveedor: "DEPENDENCIA"
                    };
                }else{
                    notaIngreso.proveedor = {
                    idProveedor: 0,
                    ruc: $('#txtProveedorRUC').val(),
                    razonSocial: $('#txtProveedor').val()
                    };
                }
            }
            
        }else{
            notaIngreso.idProveedor = $('#idProveedor').valNull();
        }
         
        //Diferenciar productosStock, y sin stock
        var urlAgregacion='/1';
        if(tipoProveedor=='NADA'){
            urlAgregacion='/2';
        }
        $.ajax({
            url: '<c:url value="/NotaIngreso/registrar" />'+urlAgregacion,
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
    
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaIngreso" />';
    });    
    
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
            $('#txtProveedorRUC').attr('data-req', ''); 
        } else {
            $('#lblProveedor').html('Proveedor');
            $('#txtProveedor').removeAttr('data-req');
            $('#txtProveedorRUC').removeAttr('data-req');
            $('#txtProveedor').val('');
            $('#txtProveedorRUC').val('');
            $('#idProveedor').val('null');
        }
    }    
        
    function modalAlmacen(tipo, e) {
        e.preventDefault();
        var eventNodeSelect = null;
        var eventNodeUnselect = null;
        
        var idTipoAlmacenLogistica = 6;
        var idTipoAlmacenEspecializado = 16;
        
        var almacenLogistica = null;
        var almacenEspecializado=null;
        var indiceAlmacenHijo = 1;
                    
        if (tipo === 'O') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Origen');            
            eventNodeSelect = function(event, node) {
                
                
                //borrarDetalleStock();
                borrarDetalle();
                indiceAlmacenHijo = node.idAlmacen - node.idAlmacenPadre;
                
                $('#txtAlmacenOrigen').val(node.parent + ' - ' + node.text);
                $('#idAlmacenOrigen').val(node.idAlmacen);
                $('#idTipoAlmacenOrigen').val(node.idTipoAlmacen);
                $('#indiceAlmacenHijoOrigen').val(indiceAlmacenHijo);
                
                if(node.parentIdTipoAlmacen==idTipoAlmacenLogistica){
                    $('#txtAlmacenDestino').val(almacenEspecializado.almacen+" - "+node.text);
                    $('#idAlmacenDestino').val(almacenEspecializado.idAlmacen+indiceAlmacenHijo);                    
                }else{
                    $('#txtAlmacenDestino').val('');
                    $('#idAlmacenDestino').val('');
                }
                
                //tipoProveedor
                var tP = getTipoProveedor();
                if(tP=='NADA'){
                    
                    $("#txtProveedor").attr('disabled','disabled');
                    $("#txtProveedor").val('');
                    $("#txtProveedorRUC").val('');
                    $("#idProveedor").val('');
                    $("#txtProveedorRUC").attr('disabled','disabled');
                    proveedorReq(0);
                }else{
                    $("#txtProveedor").removeAttr('disabled');
                    $("#txtProveedorRUC").removeAttr('disabled');
                    $("#txtProveedor").val('');
                    $("#txtProveedorRUC").val('');
                    $("#idProveedor").val('');
                    proveedorReq(1);
                }
                    
                
                
                $('#modalAlmacen').modal("hide");
                //listarConceptos();
            };
            eventNodeUnselect = function(event, node){
                $('#txtAlmacenOrigen').val('');
                $('#idTipoAlmacenOrigen').val('');
                $('#idAlmacenOrigen').val('');
                $('#txtAlmacenDestino').val('');
                $('#idAlmacenDestino').val('');
                $('#indiceAlmacenHijoOrigen').val('');
                
                //borrarDetalleStock();
                borrarDetalle();
            };
        } else if (tipo === 'D') {
            

            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Destino');            
            eventNodeSelect = function(event, node) {
                
                var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                if(node.idAlmacen != idAlmacenOrigen){
                    $('#txtAlmacenDestino').val(node.text+" - "+node.child);
                    $('#idAlmacenDestino').val(node.idAlmacen);
                    $('#modalAlmacen').modal("hide");
                }
            };
            eventNodeUnselect = function(event, node) {
                $('#txtAlmacenDestino').val('');
                $('#idAlmacenDestino').val('');
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
                if(tipo=='O'){
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenLogistica){
                            almacenLogistica = dataResponse[i];
                            $('#idAlmacenLogistica').val(almacenLogistica.idAlmacen);
                        }
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenEspecializado){
                            almacenEspecializado = dataResponse[i];
                            $('#idAlmacenEspecializado').val(almacenEspecializado.idAlmacen);
                        }
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenLogistica){
                            var tree = new Object();
                            tree.text = dataResponse[i].almacen;
                            tree.idAlmacen = dataResponse[i].idAlmacen;
                            tree.idTipoAlmacen = dataResponse[i].idTipoAlmacen;
                            tree.selectable = false;
                            tree.nodes = new Array();

                            if (dataResponse[i].almacenes.length > 0) {
                                for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {

                                    var treeLevelTwo = new Object();
                                    treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                                    treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                                    treeLevelTwo.idTipoAlmacen = dataResponse[i].almacenes[x].idTipoAlmacen;
                                    treeLevelTwo.idAlmacenPadre = dataResponse[i].idAlmacen;
                                    treeLevelTwo.parent = dataResponse[i].almacen;
                                    treeLevelTwo.parentIdTipoAlmacen = dataResponse[i].idTipoAlmacen;
                                    treeLevelTwo.nodes = new Array();
                                    tree.nodes.push(treeLevelTwo);
                                }
                            }

                            treeView.push(tree);
                        }
                    }
                }else if(tipo=='D'){
                    
                    var almacenPadreOrigen;
                    var almacenHijoOrigen;
                    var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                    
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        for (var x = 0; x <= dataResponse[i].almacenes.length-1; ++x){
                            if(idAlmacenOrigen == dataResponse[i].almacenes[x].idAlmacen){
                                indiceAlmacenHijo = dataResponse[i].almacenes[x].idAlmacen - dataResponse[i].idAlmacen;
                                almacenHijoOrigen = dataResponse[i].almacenes[x];
                                almacenPadreOrigen = dataResponse[i];
                            }
                        }
                    }                    
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        
                        if(almacenPadreOrigen.idTipoAlmacen == idTipoAlmacenLogistica){
                            
                            if(dataResponse[i].idTipoAlmacen == idTipoAlmacenEspecializado){
                                var tree = new Object();
                                tree.text = dataResponse[i].almacen;
                                tree.child = almacenHijoOrigen.almacen;
                                tree.idAlmacen = dataResponse[i].idAlmacen + indiceAlmacenHijo;
                                tree.selectable = true;
                                tree.nodes = new Array();
                                treeView.push(tree);
                            }
                        }
                        if(almacenPadreOrigen.idTipoAlmacen == idTipoAlmacenEspecializado){
                            if(!(dataResponse[i].idTipoAlmacen == idTipoAlmacenLogistica)){
                                if(!(dataResponse[i].idTipoAlmacen == idTipoAlmacenEspecializado)){
                                    var tree = new Object();
                                    tree.text = dataResponse[i].almacen;
                                    tree.child = almacenHijoOrigen.almacen;
                                    tree.idAlmacen = dataResponse[i].idAlmacen + indiceAlmacenHijo;
                                    tree.selectable = true;
                                    tree.nodes = new Array();
                                    treeView.push(tree);
                                    
                                }         
                            }
                        }
                        
                        
                    }
                }
                
                                
                $('#almacenesTreeview').treeview('remove');
                
                if(tipo=='O'){
                    $('#almacenesTreeview').treeview({
                        levels: 1,
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-down', nodeIcon: '',
                        data: treeView,
                        onNodeSelected: eventNodeSelect,
                        onNodeUnselected: eventNodeUnselect
                    });
                }else{
                    $('#almacenesTreeview').treeview({
                        levels: 0,
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-right', nodeIcon: '',
                        data: treeView,
                        onNodeSelected: eventNodeSelect,
                        onNodeUnselected: eventNodeUnselect
                    });
                }
                
                if (tipo == 'O') {
                    var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                    if (idAlmacenOrigen.length) {
                        $('#almacenesTreeview').treeview('selectByKey', [idAlmacenOrigen, 'idAlmacen',{silent:true}]);
                    }
                } else if (tipo == 'D') {
                    var idAlmacenDestino = $('#idAlmacenDestino').val();
                    if (idAlmacenDestino.length) {
                        $("#almacenesTreeview").treeview("selectByKey", [idAlmacenDestino, 'idAlmacen',{silent:true}]);
                    }
                }
            }
        });
    }
    
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

            if(getTipoProveedor()=='NADA'){    
                proveedorReq(0);
            }
        });
    });

    function modalProductos(e) {
        e.preventDefault();
        
        //proveedor
        var prov = getTipoProveedor();
        
        if(prov == 'NADA'){
            var dataResponse = new Object();
            dataResponse.mensajesRepuesta = new Array();
        
            if ($('#idConcepto').val() == '-1') {
            dataResponse.mensajesRepuesta.push('Debe seleccionar un concepto para establecer precios.');
            errorResponse(dataResponse);
            return;
            }else{
                cleanform('#modalProductoStock');
                $('#divMessage').html('');
                $('#modalProductoStock').modal('show');
                $('#btnBuscarStock').click();
                //$('#lote').attr('disabled', 'disabled');
            }
        }else{
            cleanform('#modalProducto');
            $('#fechaVencimiento').datepicker().datepicker("setDate", date);
            $('#divMessage').html('');
            $('#modalProducto').modal('show');
            $('#btnBuscar').click();
            $('#lote').attr('disabled', 'disabled');
        }
    }

    function selectProduct(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(2)').text());
        $('#idProducto').val(idProducto);
        $('#lote').removeAttr('disabled');
        $('#lote').focus();
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
            },
            "sAjaxSource": '<c:url value="/Producto/productosIngresoJSON" />',
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
                {mData: "producto", sWidth: "67%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "15%", "bSortable": false}
            ]
        });
    });
    
    $('#txtProveedorRUC').keyup(function(e) {
        var ruc = $(this).val();
        var tipoProveedor = getTipoProveedor();
        e.preventDefault();
        
        if(ruc.length == 11){
            $.ajax({
            url: '<c:url value="/Proveedor/findBasicByRUC" />/' + ruc,
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse){
                    if(dataResponse != null){

                        if(tipoProveedor=='P'){
                            if(dataResponse.tipoProveedor == 'PROVEEDOR'){
                                $('#idProveedor').val(dataResponse.id);
                                $('#txtProveedor').val(dataResponse.razonSocial);
                                
                            }else{
                                $('#idProveedor').val('');
                                $('#txtProveedor').val('Este RUC no es de un PROVEEDOR.');
                            }
                            $('#txtProveedor').attr('readonly', 'readonly');
                        }else{
                            if(dataResponse.tipoProveedor == 'DEPENDENCIA'){

                                $('#idProveedor').val(dataResponse.id);
                                $('#txtProveedor').val(dataResponse.razonSocial);
                            }else{
                                $('#idProveedor').val('');
                                $('#txtProveedor').val('El RUC no es de una DEPENDENCIA.');
                            }
                            

                            $('#txtProveedor').attr('readonly', 'readonly');
                        }
                    }else{
                        $('#idProveedor').val('null');
                        $('#txtProveedor').removeAttr('readonly');
                    }
                }
            });
        }else{
            $('#txtProveedor').removeAttr('readonly');
        }
    });
    
    function llenarDetalle(dataResponse, tipoDetalle) {
    
        //tipoDetalle: 1 = detalleIngreso
        //tipoDetalle: 2 = detalleConStock
        if(tipoDetalle===1){
            
            $('#detalleSalida').addClass('hide');
            $('#detalleIngreso').removeClass('hide');
            $('#detalleIngreso tbody').html('');
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
                fila += '<td>' + dataResponse[i].registroSanitario + '</td>';
                fila += '<td><a href="#" class="separator-icon-td" onclick="modificarDetalle('+ dataResponse[i].idProducto+', event);"><i class="splashy-pencil" title="Editar"></i></a>' +
                                '<a href="#" class="separator-icon-td" onclick="quitarDetalle(' + dataResponse[i].idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
                fila += '</tr>';
            }
            $('#detalleIngreso tbody').html(fila);
    
        }
        if(tipoDetalle===2){
            
            $('#detalleIngreso').addClass('hide');
            $('#detalleSalida').removeClass('hide');
            $('#detalleSalida tbody').html('');
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
                fila += '<td><a href="#" class="separator-icon-td" onclick="modificarDetalleStock('+ dataResponse[i].idProducto+', event);"><i class="splashy-pencil" title="Editar"></i></a>' +
                                '<a href="#" class="separator-icon-td" onclick="quitarDetalleStock(' + dataResponse[i].idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
                fila += '</tr>';
            }

            $('#detalleSalida tbody').html(fila);

        }
    }

    function getDetalleIngreso() {
        
        var tipoProveedor = getTipoProveedor();
        if(tipoProveedor=='NADA'){
            $.ajax({
                url: '<c:url value="/NotaSalida/cargarDetalle" />',
                data: {},
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                success: function(dataResponse) {

                    
                    llenarDetalle(dataResponse, 2);
                    

                }
            });
        }else{
            $.ajax({
                url: '<c:url value="/NotaIngreso/cargarDetalle" />',
                data: {},
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                success: function(dataResponse) {

                    
                    llenarDetalle(dataResponse, 1);
                    

                }
            });
        }
        
        
          
        
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
                llenarDetalle(dataResponse, 1);
            }
        });
    }
        function quitarDetalleStock(idProducto, e) {
        e.preventDefault();

        $.ajax({
            url: '<c:url value="/NotaSalida/quitarDetalle" />/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse, 2);
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
                llenarDetalle(dataResponse,1);
            }
        });
    }

    $('[data-id="btnAgregarDetalle"]').click(function(e) {
        e.preventDefault();
       
        var fechaVencimiento = Date.parseExact($('#fechaVencimiento').val(), dateFormatJS);

        var fechaVencimientoTime = null;
        if (fechaVencimiento === null) {
        } else {
            var fechaRegistro = Date.parseExact($('#fechaRegistro').val(), dateFormatJS);
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

        var ingresoDetalle = {
            lote: $('#lote').val(),
            idProducto: $('#idProducto').val(),
            nombreProducto: $('#txtProducto').val(),
            cantidad: $('#cantidad').val(),
            precio: $('#precio').val(),
            fechaVencimiento: fechaVencimientoTime,
            registroSanitario: $('#registroSanitario').val()
        };
            
        $.ajax({
            url: '<c:url value="/NotaIngreso/agregarDetalle" />',
            data: JSON.stringify(ingresoDetalle),
            type: 'POST',
            dataType: 'json', 
            contentType: 'application/json',
            success: function(dataResponse) {
                
                if (dataResponse.estado) {                    
                    getDetalleIngreso();                    
                    $('#modalProducto').modal('hide');                    
                } else {                    
                    jsonToDivError(dataResponse, '#divMessage');
                }
            }
        });
    });
    $('#btnModificarDetalle').click(function(e){
        e.preventDefault();
        var fechaVencimiento = Date.parseExact($('#modfechaVencimiento').val(), dateFormatJS);
        var fechaVencimientoTime = null;
        
        if (fechaVencimiento === null) {
        } else {
            fechaVencimientoTime = fechaVencimiento.getTime();
        }
        
        var ingresoDetalle = {
            lote: $('#modlote').val(),
            idProducto: $('#modidProducto').val(),
            nombreProducto: $('#modtxtProducto').val(),
            cantidad: $('#modcantidad').val(),
            precio: $('#modprecio').val(),
            fechaVencimiento: fechaVencimientoTime,
            registroSanitario: $('#modregistroSanitario').val()
        };

        var urlExtend = '';

        if ($('#idAlmacenOrigen').valNull() != null) {
            urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').valNull();
        }
        $.ajax({
            url: '<c:url value="/NotaIngreso/modificarDetalle" />' + urlExtend,
            data: JSON.stringify(ingresoDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleIngreso();
                    $('#modProducto').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#modDivMessage')
                }
            }
        });
    });
		
    function modificarDetalle(idProducto, e){
        e.preventDefault();
        $.ajax({
            url: '<c:url value="/NotaIngreso/modificarDetalle"/>/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application.jsp',
            success: function(dataResponse){
                $('#modidProducto').val(dataResponse.data.idProducto);
                $('#modtxtProducto').val(dataResponse.data.nombreProducto);
                $('#modlote').val(dataResponse.data.lote);
                $('#modcantidad').val(dataResponse.data.cantidad);
                $('#modprecio').val(dataResponse.data.precio);
                $('#modfechaVencimiento').val(new Date(dataResponse.data.fechaVencimiento).toString(dateFormatJS));
                $('#modregistroSanitario').val(dataResponse.data.registroSanitario);
                $('#modProducto').modal('show');
            }
        });
    }
    
    
    function reloadProveedores() {
        var dataTable = $(tblProveedor).dataTable();
        dataTable.fnReloadAjax();
    }
    
    var tProveedor = $('#tblProveedor');
    
    function listarProveedores(e) {
        e.preventDefault();
        
        
            $(tProveedor).dataTable().fnDestroy();
        
        
        
        var tipoProveedor = getTipoProveedor();
        
        if(tipoProveedor != 'NADA'){
            $('#modalProveedor').modal('show');
            
            $(tProveedor).dataTable({
                "sAjaxSource": '<c:url value="/Proveedor/proveedoresJSON?tP=" />'+tipoProveedor,
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "aoColumns": [
                    {mData: "idProveedor", sWidth: "10%"},
                    {mData: "razonSocial", sWidth: "40%"},
                    {mData: "ruc", sWidth: "30%", "bSortable": false}


                ]
            });
        }
            
        
    }
    
    $(tProveedor).on('click', 'tr', function(){       
        
        var $tds = $(this).find('td');
        

        //Razon social
        $("#txtProveedor").val($tds.eq(1).text());
        $("#txtProveedorRUC").val($tds.eq(2).text());
        $("#idProveedor").val($tds.eq(0).text());
        $('#txtProveedor').attr('readonly', 'readonly');
        $('#modalProveedor').modal('hide');
        
    });
    
    function getTipoProveedor(){

        var indiceAHO = parseInt($('#indiceAlmacenHijoOrigen').val());
        
        var idAlmacenOrigen = parseInt($('#idAlmacenOrigen').val())-indiceAHO;
        var idAlmacenLogistica = parseInt($('#idAlmacenLogistica').val());
        
        if(idAlmacenOrigen == (idAlmacenLogistica)){
            
            if(indiceAHO == 2){return 'D';}
            return 'P';
        
        }else{
            return 'NADA';
        }
    }
    /*
    var tblProductosStock = document.getElementById('tblProductosStock');
    
    $('#btnBuscarStock').click(function(e) {
        e.preventDefault();

        if ($.fn.DataTable.fnIsDataTable(tblProductosStock)) {
            $(tblProductosStock).dataTable().fnDestroy();
        }

        $(tblProductosStock).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'criterio', "value": $('#criterioStock').val()});
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
                        return '<i class="splashy-arrow_medium_right" onclick="selectProductStock(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "codigoSismed", sWidth: "12%", "bSortable": false},
                {mData: "producto", sWidth: "55%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "15%", "bSortable": false},
                {mData: "cantidad", sWidth: "12%", "bSortable": false}
            ]
        });
    });

    function selectProductStock(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProductoStock').val($(tr).find('td:eq(2)').text());
        $('#idProductoStock').val(idProducto);
        $('#cantidadStock').focus();
    }
    
    $('[data-id="btnAgregarDetalleStock"]').click(function(e) {
        e.preventDefault();

        var salidaDetalle = {
            idProducto: $('#idProductoStock').val(),
            cantidad: $('#cantidadStock').val()
        };

        var urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').val() + '&idConcepto=' + $('#idConcepto').val() + '&fechaRegistro=' + Date.parseExact($('#fechaRegistro').val(), dateFormatJS).getTime();

        $.ajax({
            url: '<c:url value="/NotaSalida/agregarDetalle/1" />' + urlExtend,
            data: JSON.stringify(salidaDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleIngreso();
                    $('#modalProductoStock').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#divMessageStock');
                }
            }
        });
    });

    $('#btnModificarDetalleStock').click(function(e){
        e.preventDefault();
        var fechaVencimiento = Date.parseExact($('#modfechaVencimientoStock').val(), dateFormatJS);
        var fechaVencimientoTime = null;
        
        if (fechaVencimiento === null) {
        } else {
            fechaVencimientoTime = fechaVencimiento.getTime();
        }
        
        var ingresoDetalle = {
            idProducto: $('#modidProductoStock').val(),
            nombreProducto: $('#modtxtProductoStock').val(),
            cantidad: $('#modcantidadStock').val()
        };

        var urlExtend = '';

        if ($('#idAlmacenOrigen').valNull() != null) {
            urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').valNull() 
                    + '&idConcepto=' + $('#idConcepto').valNull()
                    + '&fechaRegistro=' + $('#idConcepto').valNull();
        }
        $.ajax({
            url: '<c:url value="/NotaSalida/modificarDetalle" />' + urlExtend,
            data: JSON.stringify(ingresoDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleIngreso();
                    $('#modProductoStock').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#modDivMessageStock')
                }
            }
        });
    });
    
    function borrarDetalleStock() {
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

		
    function modificarDetalleStock(idProducto, e){
        e.preventDefault();
        $.ajax({
            url: '<c:url value="/NotaSalida/modificarDetalle"/>/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application.jsp',
            success: function(dataResponse){
                $('#modidProductoStock').val(dataResponse.data.idProducto);
                $('#modtxtProductoStock').val(dataResponse.data.nombreProducto);
                $('#modloteStock').val(dataResponse.data.lote);
                $('#modcantidadStock').val(dataResponse.data.cantidad);
                $('#modprecioStock').val(dataResponse.data.precio);
                $('#modfechaVencimientoStock').val(new Date(dataResponse.data.fechaVencimiento).toString(dateFormatJS));
                $('#modregistroSanitarioStock').val(dataResponse.data.registroSanitario);
                $('#modProductoStock').modal('show');
            }
        });
    }
    */
    function listarConceptos(){
        var $cboConcepto = $('#idConcepto');
        var opciones='<option value="-1">-SELECCIONE-</option>';
        var tipoProveedor = getTipoProveedor();
        
        if(tipoProveedor==='NADA'){
            for(var i = 0; i < conceptos.length; i++){
                if(conceptos[i].tipoMovConcepto == 'M'){
                    opciones+='<option value="'+conceptos[i].idConcepto+'">'+conceptos[i].nombreConcepto+'</option>';
                }
                
            }
        }else{
            for(var i = 0; i < conceptos.length; i++){
                if(conceptos[i].tipoMovConcepto != 'S'){
                    opciones+='<option value="'+conceptos[i].idConcepto+'">'+conceptos[i].nombreConcepto+'</option>';
                }
            }
        }
        $cboConcepto.html(opciones);
        
    }
    $('#modalProducto .modal-dialog .modal-body input').keyup(function(e){
        if(e.which == 13){
            $('[data-id = "btnAgregarDetalle"]').click();
        }
    });
    
    $('#modProducto .modal-dialog .modal-body input').keyup(function(e){
        if(e.which == 13){
            $('#btnModificarDetalle').click();
        }
    });
    
    /*
    $('#modalProductoStock .modal-dialog .modal-body input').keyup(function(e){
        if(e.which == 13){
            $('[data-id = "btnAgregarDetalleStock"]').click();
        }
    });*/
    
</script>    