<%@include file="../includeTagLib.jsp" %>

<style>
    .input-group-addon:hover{
        cursor:pointer;
    }
    
</style>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Año <span class="f_req">*</span></label>
                    <select id="selAnio" class="form-control" data-req="">
                        <c:forEach var="anio" items="${anios}">
                            <option value="${anio}">${anio}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Mes <span class="f_req">*</span></label>
                    <select id="selMes" class="form-control" data-req="">
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 col-md-8">
                    <label>Almacén</label>
                    <select id="idAlmacen" name="idAlmacen" class="form-control">
                        <option value="100">TODOS</option>
                        <c:forEach var="almacen" items="${almacenes}">
                            <option value="${almacen.idAlmacen}">${almacen.descripcion}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Tipo Suministro <span class="f_req">*</span></label>
                    <select id="idTipoSuministro" name="idTipoSuministro" class="form-control" data-req=''>
                        <c:forEach var="suministro" items="${suministros}">
                            <option value="${suministro.idTipoSuministro}">${suministro.descripcion}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>                
        </div>
    </div>
    <div class="col-sm-6 col-md-6">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Fecha Desde <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" name="txtFechaInicio" class="form-control" data-field-date="" data-consulta-req=""/>
                        <span class="input-group-addon" onclick="mostrarCalendar('fechaDesde');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Fecha Hasta <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" name="txtFechaFin" class="form-control" data-field-date="" data-consulta-req=""/>
                        <span class="input-group-addon"  onclick="mostrarCalendar('fechaHasta');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
            </div>            
        </div>         
    </div>
</div>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
            <c:if test="${opcionSubmenu.appOpcion == 'procesar'}">
                <button id="btnProcesar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
    </div>
    <div class="col-sm-6 col-md-6">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'consulta'}">
                <button id="btnConsultar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
    </div>
</div>

<script>
    $(document).ready(function() {
        
        var date = new Date();
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
        
        $('#btnProcesar').prop('disabled', true);
        $('#btnExcel').prop('disabled', true);
        
        var anio = date.toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
        
        
        $('#fechaDesde').datepicker().datepicker("setDate", firstDay);
        $('#fechaHasta').datepicker().datepicker("setDate", lastDay);
    });
    
    $('#selAnio').change(function() {
         if($(this).val() == '-1'){            
            limpiarSelect('#selMes');
        }else{
            var Data = {"id": "", "value": "mes", "text": "nombreMes"};
            llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function(){
                var mes = new Date().toString('MM');                
                $('#selMes').val(mes);
                $('#selMes').change();
            });
        }
    });
    
    $('#selMes').change(function (){
        if($(this).val() != '-1'){
            $.ajax({
                url: '<c:url value="/FormatoICI/existe" />?idPeriodo=' + $('#selAnio').val() + $('#selMes').val()+'&idAlmacen='+$('#idAlmacen').val()+'&idTipoSuministro='+$('#idTipoSuministro').val(),            
                type: 'POST',            
                success: function(dataResponse) {
                    if(dataResponse.idICI == 0){
                       $('#btnProcesar').prop('disabled', false);
                       $('#btnExcel').prop('disabled', true);
                    }else{
                        $('#btnProcesar').prop('disabled', true);
                        $('#btnExcel').prop('disabled', false);
                    }
                }
            });
        }        
    });
    
    $('#btnProcesar').click(function (e){
        e.preventDefault();
         
        var dataResponse = validateForm('[data-req]');
        if(dataResponse.estado == false){
            errorResponse(dataResponse);
            return;
        }
          
        $.ajax({
            url: '<c:url value="/FormatoICI/procesar" />?idAlmacen='+$('#idAlmacen').val()+'&idPeriodo=' + $('#selAnio').val() + $('#selMes').val()+'&idTipoSuministro='+$('#idTipoSuministro').val(),
            type: 'POST',
            success: function (dataResponse, textStatus, jqXHR) {
                functionResponse(dataResponse, function(){
                    $('#selMes').change();
                });
            }
        });
    });
    
    $('#btnExcel').click(function (e){
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');
        if(dataResponse.estado == false){
            errorResponse(dataResponse);
            return;
        }

        window.location = '<c:url value="/FormatoICI/excel" />?idPeriodo=' + $('#selAnio').val() + $('#selMes').val()+'&idAlmacen='+$('#idAlmacen').val()+'&idTipoSuministro='+$('#idTipoSuministro').val();
    });
    
    $('#btnConsultar').click(function (){
        
        var dataResponse = validateForm('[data-consulta-req]');        
        
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);
        
        if (fechaHasta == null) {
            dataResponse.mensajesRepuesta.push('Fecha hasta inválida.');
            dataResponse.estado = false;
        }
        
        if (fechaDesde == null) {
            dataResponse.mensajesRepuesta.push('Fecha desde inválida.');
            dataResponse.estado = false;
        }
        
        if(dataResponse.estado == false){
            errorResponse(dataResponse);
            return;
        }
        
        window.location = '<c:url value="/FormatoICI/consulta" />?fechaDesde=' + fechaDesde.getTime() + '&fechaHasta=' + fechaHasta.getTime()+'&idAlmacen='+$('#idAlmacen').val()+'&idTipoSuministro='+$('#idTipoSuministro').val();
    });    
    
    $('#idAlmacen').change(function(){
        $('#selMes').change();
    })
</script>

