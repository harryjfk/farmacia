<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Lote<span class="f_req">*</span></label>
                <input type="text" id="lote" name="lote" class="form-control" data-req="" maxlength="70" disabled="disabled"/>
            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Fecha Vencimiento<span class="f_req">*</span></label>
                <input type="text" id="fechaVencimiento" name="fechaVencimiento" class="form-control" data-req="" maxlength="70" disabled="disabled"/>
            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Stock<span class="f_req">*</span></label>
                <input type="text" id="stock" name="stock" class="form-control" data-req="" maxlength="70"/>
            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Stock Real<span class="f_req">*</span></label>
                <input type="text" id="stockReal" name="stockReal" class="form-control" data-req="" maxlength="70"/>
            </div>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Precio<span class="f_req">*</span></label>
                <input type="text" id="precio" name="precio" class="form-control" data-req="" maxlength="70"/>
            </div>
        </div>

        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
    </div>
    <div id="divMessage">

    </div>
    <!--</div>-->
</form>