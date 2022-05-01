<div class="row" id="mgn-repstock">
    <div class="col-sm-12 col-md-12">
        <form id="repstockfrm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="almacen" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Almac&eacute;n</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="almacen" name="almacen" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="fechaCierre" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fecha de Cierre</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="fechaCierre" name="fechaCierre" class="form-control" />
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-sm-6">
                <div class="form-group">
                    <label for="periodo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Per&iacute;odo</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <select id="periodo" name="periodo" class="form-control"></select>
                    </div>
                </div>
                <div class="form-group" style="display: none;">
                    <label for="fechaReposicion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Fec. Reposici&oacute;n</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="fechaReposicion" name="fechaReposicion" class="form-control" />
                    </div>
                </div>
                <div class="form-group" style="display: none;">
                    <label for="nroReposicion" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Nro. Reposici&oacute;n</label>
                    <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                        <input id="nroReposicion" name="nroReposicion" class="form-control" />
                    </div>
                </div>
            </div>
            <input class="hidden" name="idInventario" id="idInventario" value=""/>
            <button id="btnFilter" class="btn btn-primary">Consultar</button>
            <button id="btnProcesar" class="btn btn-primary" type="submit" title="Procesar" disabled="disabled">Procesar</button>
        </form>
    </div>
</div>
<div id="thead">
    <table id="tblData-tablersp" class="table table-bordered table-striped dTableR">
        <thead>
            <tr>
                <th colspan="3">Producto</th>
                <th colspan="3">Valor Actual</th>
                <th colspan="1">Conteo</th>
                <th colspan="2">Faltantes</th>
                <th colspan="2">Sobrantes</th>
                <th colspan="1"></th>
                <th colspan="1"></th>
            </tr>
            <tr>
                <th>Código</th>
                <th>Descripción</th>
                <th>F. F.</th>
                <th>Cant.</th>
                <th>Precio</th>
                <th>Total</th>
                <th>Físico</th>
                <th>Cant.</th>
                <th>Total</th>
                <th>Cant.</th>
                <th>Total</th>
                <th>Total</th>
                <th>Alter./Rotos</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<div id="stcock-edit-mdl" class="modal fade">
    <div class="modal-dialog" style="width: 646px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Editar Stocks por Lote</h4>
            </div>
            <div class="modal-body">
                <table id="tblInventarioDetalle" class="table table-bordered table-striped dTableR">
                    <thead>
                        <tr>                    
                            <th style="width: 12%;">Lote</th>
                            <th style="width: 12%;">Fecha Vto.</th>
                            <th style="width: 12%;">Cant.</th>
                            <th style="width: 12%;">Precio</th>
                            <th style="width: 12%;">Conteo Fis.</th>
                            <th style="width: 12%;">Alter./Rotos</th>
                            <th style="width: 16%;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->