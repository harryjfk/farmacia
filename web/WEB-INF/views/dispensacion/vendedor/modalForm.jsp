<div id="vendedor-mdl-body">
    <form id="mdl-form" class="form_validation_reg" autocomplete="off">
        <!--<div class="formSep">-->
        <div class="row">
            <!--<input type="text" id="codigo" name="codigo" class="form-control" data-req="" maxlength="70"/>-->
            <input type="hidden" class="IDFIELD" id="idVendedor" name="idVendedor" value="0"/>
            <div class="col-sm-6 col-md-6">
                <label>Cod. Personal <span class="f_req">*</span></label>
                <input type="text" id="personal" name="personal" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Nombre <span class="f_req">*</span></label>
                <input type="text" id="nombre" name="nombre" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Ap. Paterno <span class="f_req">*</span></label>
                <input type="text" id="paterno" name="paterno" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Ap. Materno <span class="f_req">*</span></label>
                <input type="text" id="materno" name="materno" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Direccion <span class="f_req">*</span></label>
                <input type="text" id="direccion" name="direccion" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Tel&eacute;fono <span class="f_req">*</span></label>
                <input type="text" id="telefonoUno" name="telefonoUno" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Tipo de Personal <span class="f_req">*</span></label>
                <input type="text" id="tipoPersonal" name="tipoPersonal" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Nombre de Usuario (Login) <span class="f_req">*</span></label>
                <input type="text" id="username" name="username" minlength:="3" class="form-control"/>
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Contrase&ntilde;a <span class="f_req">*</span></label>
                <input type="password" id="password" minlength:="3" name="password" class="form-control"/>
            </div>
        </div>
        <div id="divMessage"></div>
        <div style="margin-top: 10px" id="pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Personal</a></div>
        <!--</div>-->
    </form>
</div>