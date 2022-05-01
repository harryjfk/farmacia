
<div class="row">
    <div class="col-sm-4 col-md-4">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <label>Nombre Completo <span class="f_req">*</span></label>
                    <input type="text" id="txtNombreCompleto" class="form-control" readonly="" data-req="" value="${usuario.personal.nombreCompleto}"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <label>Usuario <span class="f_req">*</span></label>
                    <input type="text" id="nombreUsuario" class="form-control" maxlength="70" data-req="" value="${usuario.nombreUsuario}"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <label>Clave <span class="f_req">*</span></label>
                    <input type="password" id="clave" class="form-control" maxlength="30" data-req="" value="${usuario.clave}"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <label>Confirmar Clave</label>
                    <input type="password" id="confirmarClave" class="form-control" maxlength="30" data-req="" value="${usuario.clave}"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-12 col-md-12">
                    <label>Correo <span class="f_req">*</span></label>
                    <input type="text" id="correo" class="form-control" data-req="" value="${usuario.correo}"/>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <button id="btnGuardar" class="btn btn-default" type="submit">Actualizar Datos</button>            
        </div>
    </div>
</div>

<script>
    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        if ($('#clave').val() !== $('#confirmarClave').val()) {
            dataResponse.mensajesRepuesta.push('Los campos de clave no coinciden, verifique.');
            dataResponse.estado = false;
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var usuario = {
            nombreUsuario: $('#nombreUsuario').val(),
            clave: $('#clave').val(),
            correo: $('#correo').val()
        };

        $.ajax({
            url: 'profile',
            data: JSON.stringify(usuario),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse, status, metaData) {
                var f = function() {
                    window.location.reload();
                };
                
                functionResponse(dataResponse, f);
            }
        });

    });
</script>