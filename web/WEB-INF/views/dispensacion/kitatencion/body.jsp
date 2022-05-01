<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblKits" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Acciones</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div id="mdl-kit" class="modal fade">
    <div class="modal-dialog" style="width: auto">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Productos</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <form id="mdl-prod-form" method="post" class="form_validation_reg" autocomplete="off" action="">
                            <div class="col-lg-6 col-md-6">
                                <div class="form-group">
                                    <label>Seleccionar Producto<span class="f_req">*</span></label>
                                    <select type="text" id="producto" name="producto"  class="form-control"></select>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2">
                                <div class="form-group">
                                    <label>Cantidad por Atenci&oacute;n<span class="f_req">*</span></label>
                                    <input type="text" id="cantidad" name="cantidad" data-req=""  class="form-control" />
                                </div>
                            </div>
                            <input type="hidden" name="kitid" id="kitid" value="" />
                            <div class="col-lg-2 col-md-2">
                                <div class="form-group">
                                    <label>&nbsp;</label>
                                    <button id="btnAgregarProducto" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Agregar</button>
                                </div>
                            </div>
                        </form><!-- /form -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="gp-tblProductos" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Cantidad</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                        </table><!-- /table -->
                    </div>             
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.productos modal -->