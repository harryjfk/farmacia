<div class="row">
    <div class="col-sm-12 col-md-12">
        <form id="mdl-frm" class="form_validation_reg form-horizontal" autocomplete="off">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Producto<span class="f_req">*</span></label>
                    <select id="producto" name="producto" class="form-control"></select>
                </div>
                <div class="col-sm-5 col-md-5 col-xs-5 col-lg-5">
                    <div class="form-group">
                        <label>Cantidad<span class="f_req">*</span></label>
                        <div class="input-group" style="width: 100%">
                            <input type="text" id="cantidad" name="cantidad"  class="form-control" data-req="" maxlength="70" data-autoclose="true"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="valorUnitario" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Valor Unitario</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="valorUnitario" name="valorUnitario" class="form-control"  />
                    </div>
                </div>
                <div class="form-group">
                    <label for="servicio" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Servicio</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <input id="servicio" name="servicio" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="tipo" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Tipo</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <select id="tipo" name="tipo" class="form-control">
                            <option value="SIS">SIS</option>
                            <option value="SOAT">SOAT</option>
                            <option value="OTROS">OTROS</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="observaciones" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Observaciones</label>
                    <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6">
                        <textarea id="observaciones" name="observaciones" class="form-control"></textarea>
                    </div>
                </div>
                <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
                <input type="hidden" id="pacienteModal"  name="paciente" value="0" />
            </div>
        </form>
    </div>
</div>