<!--Ver Stocks -->
<div class="container" style="margin-top: 10px">
    <div class="row">
        <div class="col-lg-6 col-md-6 pull-right">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <button class="btn btn-default pull-right" id="btnVerStocks">Ver Stocks</button>
                </div>
                <div class="col-lg-6 col-md-6">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="control-label col-lg-4 col-md-4">Total</label>
                            <div class="col-md-8 col-sm-8 col-lg-8 col-xs-8">
                                <input id="fldtotal" class="form-control" value="" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
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