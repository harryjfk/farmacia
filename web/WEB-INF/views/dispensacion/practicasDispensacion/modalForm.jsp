<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        
        <div class="col-sm-6 col-md-6">
            <label>Código<span class="f_req">*</span></label>
            <input type="text" id="codigoPersError" name="codigoPersError" class="form-control" data-req="" maxlength="70" disabled="disabled"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Personal<span class="f_req">*</span></label>
            <input type="text" id="personalError" name="personalError" class="form-control" data-req="" maxlength="70" disabled="disabled"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Código<span class="f_req">*</span></label>
            <input type="text" id="codigoPersCorrige" name="codigoPersCorrige" class="form-control" data-req="true" maxlength="70" disabled="disabled"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Personal Corrige<span class="f_req">*</span></label>
            <input type="text" id="personalCorrige" name="personalCorrige" class="form-control" data-req="true" placeholder="Click para seleccionar" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Producto Erróneo<span class="f_req">*</span></label>
            <select id="productoErroneo" data-req="true" class="form-control"></select>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Producto Corregido<span class="f_req">*</span></label>
            <select id="productoCorrregido" data-req="true" class="form-control"></select>
        </div>
        
    </div>
    <div class="row">
        <div class=" form-group col-sm-6 col-md-6">
            <label>Fecha<span class="f_req">*</span></label>
            <input id="fecha" name="fecha" data-req="true"class="form-control" >
        </div>
    </div>
    <div class="row">
        
        <div class=" form-group col-sm-12 col-md-12 clearfix">
            <label for="motivo">Motivo Corrección<span class="f_req">*</span></label>
            <textarea id="motivo" class="form-control "></textarea>
        </div>
        
        
        
            <input type="hidden" id="id" class="IDFIELD" name="id" value="0" />
    </div>
   
   
</form>