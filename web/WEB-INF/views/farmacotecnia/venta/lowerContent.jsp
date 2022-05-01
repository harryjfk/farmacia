<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="bold">INSUMOS</div>
        <table id="tblData-tableinsumos" class="table table-bordered table-striped dTableR dataTable">
            <thead>
                <tr>
                    <th>Nombre del Insumo</th>
                    <th>Unidad de Medida</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
        </table>
    </div>
    <div class="col-sm-12 col-md-12" style="margin-top: 10px" >
        <div class="col-sm-12 col-md-12" style="margin-bottom: 10px;text-align: right;">
            <button id="btnAgregarIn" class="btn btn-primary">Agregar</button>
            <button id="btnLimpiar" class="btn btn-primary">Limpiar</button>
        </div>
        <form id="preventafrm2" class="form_validation_reg form-horizontal" autocomplete="off">
            <!--Especificaciones farmaceuticas-->
            <div class="col-lg-6 col-md-6 col-sm-6">
                <ul id="myTab" class="nav nav-tabs">
                    <li><a href="#especificaciones" data-toggle="tab">Especificaciones Farmac&eacute;uticas</a></li>
                    <li><a href="#substitutos" data-toggle="tab">Sustitutos</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active fade"  id="especificaciones">
                        <textarea id="especs" class="form-control" rows="3"></textarea>
                    </div>
                    <div class="tab-pane fade" id="substitutos">
                        <textarea id="subs" class="form-control" rows="3"></textarea>
                    </div>
                </div>
            </div>
            <!--Stocks, sub total, etc.-->
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="row" style="border: 1px solid;padding: 5px;">
                    <div class="col-sm-6 col-md-6">
                        <div class="form-group">
                            <label class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Stock</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="verstocks" disabled="" class="form-control" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="stocks" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Stocks Disponibles</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="stocks" name="stocks" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="redondeo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Redondeo</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="redondeo" name="redondeo" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-md-6">
                        <div class="form-group">
                            <label for="subTotal" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Sub Total</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="subTotal" name="subTotal" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="impuesto" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Impuesto</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="impuesto" name="impuesto" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="netoAPagar" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label" style="font-weight: bolder !important">Neto a Pagar</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="netoAPagar" name="netoAPagar" class="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<!--Ver Stocks -->
<div id="verstocks-mdl" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Stocks</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Descripci&oacute;n</th>
                            <th>Stock</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- Agregar Insumo -->
<div id="addInsumo-mdl" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Agregar Insumo</h4>
            </div>
            <div class="modal-body">
                <form id="insumo-mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Insumo<span class="f_req">*</span></label>
                            <div class="input-group" style="width: 100%">
                                <select id="insumo" name="insumo"  class="form-control" data-req="">
                                    <c:forEach items="${insumos}" var="insumo">
                                        <option value="${insumo.id}" 
                                                data-nombre="${insumo.nombre}"
                                                data-unidad-nombre="${insumo.unidad.nombreUnidadMedida}"
                                                data-unidad-id="${insumo.unidad.idUnidadMedida}"
                                                data-precio="${insumo.precio}">
                                            ${insumo.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <div class="form-group">
                                <label>Cantidad<span class="f_req">*</span></label>
                                <div class="input-group" style="width: 100%">
                                    <input type="text" id="icantidad" name="icantidad"  class="form-control" data-req="" maxlength="70" data-autoclose="true"/>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
                        <input type="hidden" id="mm-id" class="IDFIELD" name="mm-id" value="0" />
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                <button id="btnAgregarInsumo" type="button" class="btn btn-primary">Agregar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->