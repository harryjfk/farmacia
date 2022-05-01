<div id="mdl-prod-sub" class="modal fade">
    <div class="modal-dialog" style="width: auto">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Productos</h4>
            </div>
            <div class="modal-body">
                <form id="mdl-prod-form" method="post" class="form_validation_reg" autocomplete="off" action="">
                    <div class="form-group">
                        <label>Seleccionar Producto</label>
                        <select type="text" id="producto" name="producto"  class="form-control"></select>
                    </div>
                    <button id="btnAgregarProducto" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Agregar</button>
                </form><!-- /form -->
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="gp-tblProductos" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>F.F.</th>
                                    <th>U.M.</th>
                                    <th>Concentraci&oacute;n</th>
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


<div id="mdl-kit-sub" class="modal fade">
    <div class="modal-dialog" style="width: auto">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Kits de Atenci&oacute;n</h4>
            </div>
            <div class="modal-body">
                <form id="mdl-kit-form" method="post" class="form_validation_reg" autocomplete="off" action="">
                    <div class="form-group">
                        <label>Seleccionar Kit</label>
                        <select id="kit" name="kit"  class="form-control"></select>
                    </div>
                    <button id="btnAgregarKit" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span> Agregar</button>
                </form><!-- /form -->
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="gp-tblKits" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th>C&oacute;digo</th>
                                    <th>Descripci&oacute;n</th>
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
</div><!-- /.kits modal -->