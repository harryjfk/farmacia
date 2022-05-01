<div class="modal fade second-modal" id="addRecetaModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Agregar Receta</h4>
            </div>
            <div class="modal-body">

                <form id="mdlReceta-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar/receta">
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>No.Receta<span class="f_req">*</span></label>
                            <input type="text" id="numero" name="numero" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Tipo de Receta<span class="f_req">*</span></label>
                            <select id="atendida" name="atendida" class="form-control">
                                <option value="0">No Atendida</option>
                                <option value="1">Atendida</option>
                            </select>
                        </div>
                        <input type="hidden" id="idPaciente" class="IDFIELD" name="idPaciente" value="0" />
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button id="saveReceta" type="button" class="btn btn-default">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade second-modal" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Agregar Producto</h4>
            </div>
            <div class="modal-body">
                <form id="mdlProducto-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar/producto">
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>No.Receta<span class="f_req">*</span></label>
                            <select id="receta" class="form-control" name="receta"></select>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Descripción<span class="f_req">*</span></label>
                            <select id="producto" class="form-control" name="producto"></select>
                        </div>
                        
                        <div class="col-sm-6 col-md-6">
                            <label>Cantidad<span class="f_req">*</span></label>
                            <input type="text" id="cantidad" name="cantidad" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Lote<span class="f_req">*</span></label>
                            <input type="text" id="lote" name="lote" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Almacen<span class="f_req">*</span></label>
                            <input type="text" id="almacen" name="almacen" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <input type="hidden" id="idPaciente" class="IDFIELD" name="idPaciente" value="0" />
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveProducto" type="button" class="btn btn-default">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
