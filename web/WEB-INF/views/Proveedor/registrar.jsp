<%@include file="../includeTagLib.jsp" %> 

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-8 col-md-8">
                    <label>Razón Social <span class="f_req">*</span></label>
                    <input type="text" id="razonSocial" class="form-control" maxlength="100" data-req=""/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Ruc <span class="f_req">*</span></label>
                    <input type="text" id="ruc" class="form-control" maxlength="11" data-req="" data-validate-ruc=""/>
                </div>                
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Fax</label>
                    <input type="text" id="fax" class="form-control" maxlength="20"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Teléfono</label>
                    <input type="text" id="telefono" class="form-control" maxlength="20"/>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Dirección</label>
                    <input type="text" id="direccion" class="form-control" maxlength="100"/>
                </div> 
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Correo</label>
                    <input type="text" id="correo" class="form-control" maxlength="100"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Contacto</label>
                    <input type="text" id="contacto" class="form-control" maxlength="70"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Teléfono Contacto</label>
                    <input type="text" id="telefonoContacto" class="form-control" maxlength="20"/>
                </div>
            </div>
        </div>     
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Tipo de proveedor</label>
                    <select id="tipoProveedor" class="form-control" maxlength="50">
                        <option value="PROVEEDOR">PROVEEDOR</option>
                        <option value="DEPENDENCIA">DEPENDENCIA</option>
                    </select>
                </div>
            </div>
        </div>   
        <div class="form-actions">
            <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
            <button id="btnCancelar" class="btn btn-default">Cancelar</button>
        </div>
    </div>
</div>

<script>
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/Proveedor" />';
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var proveedor = {
            razonSocial: $('#razonSocial').val(),
            ruc: $('#ruc').val(),
            fax: $('#fax').val(),
            telefono: $('#telefono').val(),
            contacto: $('#contacto').val(),
            telefonoContacto: $('#telefonoContacto').val(),
            direccion: $('#direccion').val(),
            correo: $('#correo').val(),
            tipoProveedor: $('#tipoProveedor').val()
        };

        $.ajax({
            url: '<c:url value="/Proveedor/registrar"/>',
            data: JSON.stringify(proveedor),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function(){
                    $('#btnCancelar').click();
                });
            }
        });
    });
</script>