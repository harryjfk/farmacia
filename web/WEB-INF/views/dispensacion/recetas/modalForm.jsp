<div class="row">
    <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
        <form id="mdl-form" method="post" class="form_validation_reg form-horizontal" autocomplete="off" action="insertar">

            
            <div class="col-sm-6 col-md-6">
            <label>Producto<span class="f_req">*</span></label>
            <select id="producto" name="producto" class="form-control"></select>
        </div>
            
            <div class="form-group">
                <label for="cantidad" class="col-md-4 col-sm-4 col-lg-4 col-xs-4 control-label">Cantidad</label>
                <div class="col-md-6 col-sm-6 col-lg-6 col-xs-6" >
                    <input id="cantidad" name="cantidad" class="form-control" maxlength="20" /><!--Cambio Valeria ml-->
                </div>
            </div>
            <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
            <input type="hidden" id="idProducto"  name="idProducto" value="0" />
            <input type="hidden" id="lote"  name="lote" value="0" />
            <input type="hidden" id="almacen"  name="almacen" value="0" />
            
        </form>
    </div>
</div>