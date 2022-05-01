<%@include file="../../includeTagLib.jsp" %> 
<%@ taglib uri="stcJdbc" prefix="stcJdbc"%>
<style>
    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 60%;
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
                        <select type="text" id="txtAlmacenOrigen" class="form-control"></select>
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
                            <c:if test="${pedido.concepto.idConcepto == concepto.idConcepto}">
                                <option value="${concepto.idConcepto}" selected="">${concepto.nombreConcepto}</option>
                            </c:if>
                            <c:if test="${pedido.concepto.idConcepto != concepto.idConcepto}">
                               <option value="${concepto.idConcepto}">${concepto.nombreConcepto}</option>
                            </c:if>
                        </c:forEach>
                    </select>
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
                    <table id="detallePedido" class="table table-bordered table-striped dTableR">
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
                                    <input type="hidden" id="idProducto" name="idProductoPedido" value="0"/>
                                    <input type="text" id="txtProducto" name="txtProductoPedido" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidad" name="cantidadPedido" class="form-control" />
                                </div>
                            </div>
                        </div>                
                        <div id="divMessage"></div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cancelar</button>
                <button data-id="btnAgregarDetalle" class="btn btn-default" type="submit">Guardar</button>
            </div>
        </div>
    </div>
</div>
