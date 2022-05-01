<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        <div class="col-sm-6 col-md-6">
            <label>Producto<span class="f_req">*</span></label>
            <select id="producto" name="producto" class="form-control"></select>
        </div>
        <div class="col-sm-6 col-md-6">
            <div class="form-group">
                <label>Cantidad<span class="f_req">*</span></label>
                <div class="input-group" style="width: 100%">
                    <input type="text" id="cantidad" name="cantidad"  class="form-control" data-req="" maxlength="70" data-autoclose="true"/>
                </div>
            </div>
        </div>
        <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
    </div>
</form>

