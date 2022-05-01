<div id="prescriptor-mdl-body">
    <form id="mdl-form" class="form_validation_reg" autocomplete="off">
        <!--<div class="formSep">-->
        <div class="row">
            <!--<input type="text" id="codigo" name="codigo" class="form-control" data-req="" maxlength="70"/>-->
            <input type="hidden" class="IDFIELD" id="idMedico" name="idMedico" value="0"/>
            <div class="col-sm-6 col-md-6">
                <label>Cod. Personal <span class="f_req">*</span></label>
                <input type="text" id="personal" name="personal" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Nombre <span class="f_req">*</span></label>
                <input type="text" id="nombre" name="nombre" class="form-control"maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Ap. Paterno <span class="f_req">*</span></label>
                <input type="text" id="paterno" name="paterno" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Ap. Materno <span class="f_req">*</span></label>
                <input type="text" id="materno" name="materno" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Direccion <span class="f_req">*</span></label>
                <input type="text" id="direccion" name="direccion" class="form-control" maxlength="200"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Telefono <span class="f_req">*</span></label>
                <input type="text" id="telefonoUno" name="telefonoUno" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Colegiatura <span class="f_req">*</span></label>
                <input type="text" id="colegio" name="colegio" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
            <div class="col-sm-6 col-md-6">
                <label>Tipo de Profesion <span class="f_req">*</span></label>
                <input type="text" id="profesion" name="profesion" class="form-control" maxlength="70"/><!--Cambio Valeria ml-->
            </div>
        </div>
        <div id="divMessage"></div>
        <div style="margin-top: 10px" id="pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Personal</a></div>
        <!--</div>-->
    </form>
</div>