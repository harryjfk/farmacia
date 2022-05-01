<%@include file="../includeTagLib.jsp" %> 

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-8 col-md-8">
                    <label>Descripcion <span class="f_req">*</span></label>
                    <input type="text" id="descripcion" class="form-control" maxlength="100" data-req=""/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Abreviatura</label>
                    <input type="text" id="abreviatura" class="form-control" maxlength="20" />
                </div>                
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Código SIGA</label>
                    <select id="selProductoSiga" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="productoSiga" items="${productosSiga}">
                            <option value="${productoSiga.idProductoSiga}">${productoSiga.nombreProductoSiga}</option>
                        </c:forEach>
                    </select>                    
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Código SISMED</label>
                    <select id="selProductoSismed" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="productoSismed" items="${productosSismed}">
                            <option value="${productoSismed.idProductoSismed}">${productoSismed.nombreProductoSismed}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Forma Farmaceutica <span class="f_req">*</span></label>
                    <select id="selFormaFarmaceutica" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="formaFarmaceutica" items="${formasFarmaceuticas}">
                            <option value="${formaFarmaceutica.idFormaFarmaceutica}">${formaFarmaceutica.nombreFormaFarmaceutica}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Tipo de Producto <span class="f_req">*</span></label>
                    <select id="selTipoProducto" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="tipoProducto" items="${tiposProducto}">
                            <option value="${tipoProducto.idTipoProducto}" <c:if test="${tipoProducto.idTipoProducto == 1}">selected="selected"</c:if>>${tipoProducto.nombreTipoProducto}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Unidad de Medida <span class="f_req">*</span></label>
                    <select id="selUnidadMedida" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="unidadMedida" items="${unidadesMedida}">
                            <option value="${unidadMedida.idUnidadMedida}"  <c:if test="${unidadMedida.idUnidadMedida == 68}">selected="selected"</c:if>>${unidadMedida.nombreUnidadMedida}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Presentación</label>
                    <input type="text" id="presentacion" class="form-control" maxlength="100"/>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Concentración</label>
                    <input type="text" id="concentracion" class="form-control" maxlength="100"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Stock Min.</label>
                    <input type="text" id="stockMin" class="form-control" maxlength="10" value="0"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Stock Max.</label>
                    <input type="text" id="stockMax" class="form-control" maxlength="10" value="0"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Requerimiento</label>
                    <input type="text" id="requerimiento" class="form-control" maxlength="10" value="0"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <label>Seleccione <span class="f_req">*</span></label>
            <div class="separator-form-checkbox"></div>
            <label class="checkbox-inline">
                <input type="checkbox" id="petitorio" value="1" data-unchecked="0" />
                Petitorio Nacional
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="estrSop" value="1" data-unchecked="0" />
                Estr. Sop.
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="estrVta" value="1" data-unchecked="0" />
                Estr. Vta.
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="traNac" value="1" data-unchecked="0" />
                Tra. Nac.
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="traLoc" value="1" data-unchecked="0" />
                Petitorio Institucional
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="narcotico" value="1" data-unchecked="0" />
                Narcótico
            </label>
            <label class="checkbox-inline">
                <input type="checkbox" id="adscrito" value="1" data-unchecked="0" />
                Adscrito
            </label>
        </div>
    </div>
    <div class="form-actions">
        <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
        <button id="btnCancelar" class="btn btn-default">Cancelar</button>
    </div>
</div>

<script>

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/Producto" />';
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var producto = {
            descripcion: $('#descripcion').val(),
            abreviatura: $('#abreviatura').val(),
            idProductoSiga: $('#selProductoSiga').valNull(),
            idProductoSismed: $('#selProductoSismed').valNull(),
            idFormaFarmaceutica: $('#selFormaFarmaceutica').val(),
            idTipoProducto: $('#selTipoProducto').val(),
            idUnidadMedida: $('#selUnidadMedida').val(),
            presentacion: $('#presentacion').val(),
            concentracion: $('#concentracion').val(),
            stockMin: $('#stockMin').val(),
            stockMax: $('#stockMax').val(),
            requerimiento: $('#requerimiento').val(),
            petitorio: $('#petitorio').checkboxVal(),
            estrSop: $('#estrSop').checkboxVal(),
            estrVta: $('#estrVta').checkboxVal(),
            traNac: $('#traNac').checkboxVal(),
            traLoc: $('#traLoc').checkboxVal(),
            narcotico: $('#narcotico').checkboxVal(),
            adscrito: $('#adscrito').checkboxVal()
        };

        $.ajax({
            url: '<c:url value="/Producto/registrar"/>',
            data: JSON.stringify(producto),
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