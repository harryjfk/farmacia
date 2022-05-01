<%@include file="../includeTagLib.jsp" %> 

<form id="form" name="form" action="<c:url value="${path}/registrar" />" target="ifrm_registrar" method="POST" enctype="multipart/form-data" autocomplete="off">
    <input type="hidden" id="idSolicitud" name="idSolicitud" />
    <input type="hidden" id="activo" name="activo" valu="1" />
    <div class="row">
       <div class="modal-cabecera"> 
        <div class="col-sm-12 col-md-12">
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-3 col-md-3">
                        <label>M&eacute;dico <span class="f_req">*</span></label>
                        <select id="idMedico" name="idMedico" class="form-control" data-req="" onchange="obtenerMedico(this.value,event, this);">
                            <option value="-1">-SELECCIONE-</option>
                            <c:forEach var="medico" items="${medicos}">
                                <option value="${medico.idPersonal}">${medico.nombre} ${medico.apellidoPaterno} ${medico.apellidoMaterno}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>N&ordm; Colegiatura</label>
                        <input type="text" id="colegiatura" name="colegiatura" class="form-control" readonly="true" />
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Profesi&oacute;n / Especialidad</label>
                        <input type="text" id="profesion" name="profesion" class="form-control" readonly="true" />
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Servicio / Departamento:</label>
                        <input type="text" id="unidad" name="unidad" class="form-control" readonly="true" />
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Establecimiento <span class="f_req">*</span></label>
                        <input type="text" id="establecimiento" name="establecimiento" class="form-control uppercase" data-req="" maxlength="50" />
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <label>Fecha <span class="f_req">*</span></label>
                        <div class="input-group">
                            <input type="text" id="fecha" name="fecha" class="form-control uppercase" data-field-date="" data-req=""/>
                            <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecha');"></i></span>
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Instituci&oacute;n / DISA <span class="f_req">*</span></label>
                        <input type="text" id="institucion" name="institucion" class="form-control uppercase" data-req="" maxlength="50" />
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                   <div class="col-sm-4 col-md-4">
                        <label>Existen medicamentos alternativos en el PNME <input type="checkbox" id="existeMedicamento" name="existeMedicamento" value="1" data-unchecked="0" /></label>
                   </div> 
                </div>
            </div>
            <div class="formSep">            
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Motivos de la solicitud</label>
                        <textarea id="motivo" name="motivo" rows="4" class="form-control uppercase"></textarea>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Justificaci&oacute;n de la solicitud</label>
                        <textarea id="justificacion" name="justificacion" rows="4" class="form-control uppercase"></textarea>
                    </div>
                </div>
            </div>
            <div class="formSep">            
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Documento</label>
                        <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                            <div class="form-control" data-trigger="fileinput">
                                <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                <span class="fileinput-filename"></span>
                            </div>
                            <span class="input-group-addon btn btn-default btn-file">
                                <span class="fileinput-new">Seleccionar</span>
                                <span class="fileinput-exists">Cambiar</span>
                                <input type="file" name="fileDocumento">
                            </span>
                            <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Quitar</a>
                        </div>
                    </div>
                 </div>
             </div>
            
            <div class="form-actions">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button id="btnCancelar" class="btn btn-default">Cancelar</button>
            </div>
        </div>
       </div> 
    </div>
    <input type="hidden" id="jsonForm" name="jsonForm" />
    <iframe id='ifrm_registrar' name='ifrm_registrar' src="" style="display:none;"></iframe>
</form>

<script>
    
    $('#fecha').val(new Date().toString(dateFormatJS));

    $('#ifrm_registrar').load(function() {
        var dataResponse = $(this).contents().find("body").html();
        if (dataResponse.length) {
            dataResponse = jQuery.parseJSON(dataResponse);
            functionResponse(dataResponse);
        }
    });

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="${path}" />';
    });
    
    function obtenerMedico(id, e, element) {
        e.preventDefault();
        $('#colegiatura').val("Cargando...");
        $('#profesion').val("Cargando...");
        $('#unidad').val("Cargando...");
        $.ajax({
            url: '<c:url value="${path}/medicoJSON" />?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#colegiatura').val(jsonData.colegiatura);
                $('#profesion').val(jsonData.cargo);
                $('#unidad').val(jsonData.unidad);
            }
        });
    }

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('.modal-cabecera [data-req]');

        var fecha = Date.parseExact($('#fecha').val(), dateFormatJS);
        var fechaTime = null;

        if ($('#fecha').val().length > 0) {
            if (fecha === null) {
                dataResponse.mensajesRepuesta.push('Fecha inválida.');
                dataResponse.estado = false;
            } else {
                fechaTime = fecha.getTime();
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var solicitud = {
            idMedico: $('#idMedico').val(),
            establecimiento: $('#establecimiento').val(),
            institucion: $('#institucion').val(),
            justificacion: $('#justificacion').val(),
            motivo: $('#motivo').val(),
            existeMedicamento: $('#existeMedicamento').checkboxVal(),
            fecha: fechaTime
        };

        $('#jsonForm').val(JSON.stringify(solicitud));

        $('#form').submit();
    });
</script>