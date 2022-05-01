<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="col-sm-12 col-md-12" style="margin-bottom: 10px;margin-top:10;text-align: right;">
            <button id="btnAgregar" class="btn btn-primary">Agregar</button>
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
                            <label class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label"></label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <button id="verstocks" class="form-control">Ver Stocks</button>
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