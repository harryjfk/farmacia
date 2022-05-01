<%@include file="../includeTagLib.jsp" %>
<h3 class="heading">Editar Parámetro</h3>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <form:form id="frmModificarParametro" method="post" action="modificar" modelAttribute="parametro" class="form_validation_reg" autocomplete="off">
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Parametro <span class="f_req">*</span></label>
                        <form:input path="nombreParametro" value="${parametro.nombreParametro}" class="form-control" req="" maxlength="70"/>
                    </div>
                    <div class="col-sm-8 col-md-8">
                        <label>Descripción <span class="f_req">*</span></label>
                        <form:input path="descripcionParametro" value="${parametro.descripcionParametro}" class="form-control" req="" maxlength="120"/>
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <label>Valor <span class="f_req">*</span></label>
                        <form:input path="valor" value="${parametro.valor}" class="form-control" req="" maxlength="250"/>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button id="btnCancelar" class="btn btn-default">Cancelar</button>
            </div>
            <form:hidden path="idParametro" value="${parametro.idParametro}"></form:hidden>
        </form:form>       
    </div>
</div>

<script>
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = 'listar';
    });
    
    $('#btnGuardar').click(function(e) {
        var dataSend = $('#frmModificarParametro').serialize();

        $.ajax({
            url: $("#frmModificarParametro").attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {
                functionResponse(dataResponse);
            }
        });

        e.preventDefault();
    });
</script>
