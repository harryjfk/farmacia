<form id="mdl-form" method="post" class="form_validation_reg" autocomplete="off" action="insertar">
    <div class="row">
        <div class="col-sm-6 col-md-6">
            <label>DNI<span class="f_req">*</span></label>
            <input type="text" id="dni" name="dni" class="form-control" data-req=""  maxlength="10"/><!--Cambio Valeria ml-->
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Cod.Paciente</label>
            <input type="text" id="codPersonal" name="codPersonal" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>No.Afiliacion<span class="f_req">*</span></label>
            <input type="text" id="noAfiliacion" name="noAfiliacion" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Nombre o razón social<span class="f_req">*</span></label>
            <input type="text" id="nombre" name="nombre" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Apellido Paterno<span class="f_req">*</span></label>
            <input type="text" id="apellidoPaterno" name="apellidoPaterno" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Apellido Materno<span class="f_req">*</span></label>
            <input type="text" id="apellidoMaterno" name="apellidoMaterno" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Dirección<span class="f_req">*</span></label>
            <input type="text" id="direccion" name="direccion" class="form-control" data-req="" maxlength="70"/>
        </div>


        <div class="col-sm-6 col-md-6">
            <label>Tipo de Documento <span class="f_req">*</span></label>
            <select id="TipoDocumentoSelect" class="form-control" name="TipoDocumentoSelect"></select>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Número de documento <span class="f_req">*</span></label>
            <input type="text" id="noDocumento" name="noDocumento" class="form-control" data-req="" maxlength="70"/>
        </div>
        <div class="col-sm-6 col-md-6">
            <label>Telefono<span class="f_req">*</span></label>
            <input type="text" id="telefono" name="telefono" class="form-control" data-req="" maxlength="70"/>
        </div>

        <input type="hidden" id="idCliente" class="IDFIELD" name="idCliente" value="0" />
    </div>
    <div id="divMessage"></div>
    <div style="margin-top: 10px" id="pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Paciente</a></div>

    <!--</div>-->
</form>