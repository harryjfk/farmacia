<%@include file="../includeTagLib.jsp" %> 

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-8 col-md-8">
                    <label>Razón Social <span class="f_req">*</span></label>
                    <input type="text" id="razonSocial" class="form-control" maxlength="100" data-req="" value="${proveedor.razonSocial}"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Ruc <span class="f_req">*</span></label>
                    <input type="text" id="ruc" class="form-control" maxlength="11" data-req="" data-validate-ruc="" value="${proveedor.ruc}"/>
                </div>                
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Fax</label>
                    <input type="text" id="fax" class="form-control" maxlength="20" value="${proveedor.fax}"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Teléfono</label>
                    <input type="text" id="telefono" class="form-control" maxlength="20" value="${proveedor.telefono}"/>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Dirección</label>
                    <input type="text" id="direccion" class="form-control" maxlength="100" value="${proveedor.direccion}"/>
                </div> 
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Correo</label>
                    <input type="text" id="correo" class="form-control" maxlength="100" value="${proveedor.correo}"/>                    
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Contacto</label>
                    <input type="text" id="contacto" class="form-control" maxlength="70" value="${proveedor.contacto}"/>                    
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Teléfono Contacto</label>
                    <input type="text" id="telefonoContacto" class="form-control" maxlength="20" value="${proveedor.telefonoContacto}"/>
                </div>
            </div>
        </div>  
        <div class="formSep">
            <div class="row">                        
                <div class="col-sm-4 col-md-4">
                    <label>Estado <span class="f_req">*</span></label>
                    <div class="separator-form-checkbox"></div>                    
                    <label class="checkbox-inline">
                        <input type="checkbox" id="chkActivo" name="chkActivo" 
                               <c:if test="${proveedor.activo == 1}">
                                   checked=""
                               </c:if>
                               > Activo
                    </label>
                    <c:if test="${proveedor.activo == 1}">
                        <input type="hidden" id="activo" name="activo" value="1" />
                    </c:if>
                    <c:if test="${proveedor.activo == 0}">
                        <input type="hidden" id="activo" name="activo" value="0" />
                    </c:if>
                </div>
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
            <input type="hidden" id="idProveedor" value="${proveedor.idProveedor}"/>            
            <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
            <button id="btnCancelar" class="btn btn-default">Cancelar</button>
        </div>
    </div>
</div>

<script>
    $('#chkActivo').click(function(e) {
        if ($(this).prop('checked')) {
            $('#activo').val('1');
        } else {
            $('#activo').val('0');
        }
    });

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
            idProveedor: $('#idProveedor').val(),
            razonSocial: $('#razonSocial').val(),
            ruc: $('#ruc').val(),
            fax: $('#fax').val(),
            telefono: $('#telefono').val(),
            contacto: $('#contacto').val(),
            telefonoContacto: $('#telefonoContacto').val(),
            direccion: $('#direccion').val(),
            correo: $('#correo').val(),
            activo: $('#activo').val(),
            tipoProveedor: $('#tipoProveedor').val()
        };

        $.ajax({
            url: '<c:url value="/Proveedor/modificar"/>',
            data: JSON.stringify(proveedor),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function() {
                    $('#btnCancelar').click();
                });
            }
        });
    });
</script>