<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Año <span class="f_req">*</span></label>
                    <select id="selAnio" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="anio" items="${anios}">                       
                            <option value="${anio}">${anio}</option>
                        </c:forEach>
                    </select> 
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Mes <span class="f_req">*</span></label>
                    <select id="selMes" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                    </select> 
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Desde <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="txtDesde" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" id="iconDesde"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Hasta <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="txtHasta" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" id="iconHasta"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <button id="btnConsultar" class="btn btn-default">Consultar</button>            
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <h3 class="heading">Guías de Remisión sin Anular</h3>
        <table id="tblNoAnuladas" class="table table-bordered table-striped">
            <thead>
                <tr>
                <th style="width: 23%;">Nro. Movimiento</th>
                <th style="width: 24%;">Total</th>
                <th style="width: 20%;">Fecha</th>
                <th style="width: 10%;">Anular</th>
                </tr>
            </thead>
            <tbody>                
            </tbody>
        </table>
    </div>
    <div class="col-sm-6 col-md-6">
        <h3 class="heading">Guías de Remisión Anuladas</h3>
        <table id="tblAnuladas" class="table table-bordered table-striped">
            <thead>
                <tr>
                <th style="width: 25%;">Nro. Movimiento</th>
                <th style="width: 30%;">Total</th>
                <th style="width: 20%;">Fecha</th>
                </tr>
            </thead>
            <tbody class="text-danger">                              
            </tbody>
        </table>
    </div>
</div>

<script>

    $(document).ready(function() {
        bloquearRangoFecha();
    });

    function bloquearRangoFecha() {
        $('#txtDesde').val('');
        $('#txtDesde').attr('disabled', 'disabled');
        $('#iconDesde').attr('onclick', '');
        $('#txtHasta').val('');
        $('#txtHasta').attr('disabled', 'disabled');
        $('#iconHasta').attr('onclick', '');
    }

    $('#selAnio').change(function() {
        if ($(this).val() == '-1') {
            limpiarSelect('#selMes');
        } else {
            var Data = {"id": "", "value": "mes", "text": "nombreMes"};
            llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data);
        }
        bloquearRangoFecha();
    });

    $('#selMes').change(function() {
        if ($(this).val() == '-1') {
            bloquearRangoFecha();
        } else {
            desbloquearRangoFecha();
        }
    });
    
    var fechaMin = null;
    var fechaMax = null;

    function desbloquearRangoFecha() {
        $('#txtDesde').val('');
        $('#txtDesde').removeAttr('disabled');
        $('#iconDesde').attr('onclick', 'mostrarCalendar("txtDesde");');
        $('#txtHasta').val('');
        $('#txtHasta').removeAttr('disabled');
        $('#iconHasta').attr('onclick', 'mostrarCalendar("txtHasta");');
        var cantidadDias = Date.getDaysInMonth($('#selAnio').val(), parseInt($('#selMes').val()) - 1);

        fechaMin = Date.parseExact('01' + '/' + $('#selMes').val() + '/' + $('#selAnio').val(), 'dd/MM/yyyy');
        fechaMax = Date.parseExact(cantidadDias.toString() + '/' + $('#selMes').val() + '/' + $('#selAnio').val(), 'dd/MM/yyyy');

        $("#txtHasta").datepicker("option", "minDate", fechaMin);
        $("#txtHasta").datepicker("option", "maxDate", fechaMax);

        $("#txtDesde").datepicker("option", "minDate", fechaMin);
        $("#txtDesde").datepicker("option", "maxDate", fechaMax);
    }

    $('#btnConsultar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        var fechaDesde = Date.parseExact($('#txtDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#txtHasta').val(), dateFormatJS);

        if ($('#txtHasta').val().length > 0) {
            if (fechaHasta == null) {
                dataResponse.mensajesRepuesta.push('Fecha hasta inválida.');
                dataResponse.estado = false;
            }else{
               if(fechaHasta.compareTo(fechaMax) == 1){
                    dataResponse.mensajesRepuesta.push('Fecha hasta debe estar en el periodo seleccionado.');
                    dataResponse.estado = false;
                }
            }
        }

        if ($('#txtDesde').val().length > 0) {
            if (fechaDesde == null) {
                dataResponse.mensajesRepuesta.push('Fecha desde inválida.');
                dataResponse.estado = false;
            }else{
                if(fechaMin.compareTo(fechaDesde) == 1){
                    dataResponse.mensajesRepuesta.push('Fecha desde debe estar en el periodo seleccionado.');
                    dataResponse.estado = false;
                }
            }
        }

        if (fechaDesde != null && fechaHasta != null) {
            if (fechaDesde.compareTo(fechaHasta) == 1) {
                dataResponse.mensajesRepuesta.push('Fecha hasta debe ser mayor que la fecha desde.');
                dataResponse.estado = false;
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var guiasRemisionParam = {
            idPeriodo: parseInt($('#selAnio').val() + $('#selMes').val()),
            fechaDesde: fechaDesde.getTime(),
            fechaHasta: fechaHasta.getTime()
        };

        $.ajax({
            url: '<c:url value="/Movimiento/guiasRemision" />',
            data: JSON.stringify(guiasRemisionParam),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(data, textStatus, jqXHR) {

                $('#tblNoAnuladas tbody tr').remove();
                $('#tblAnuladas tbody tr').remove();
                
                for (var i = 0; i < data.length; ++i) {

                    var rowAnuladas = $('<tr></tr>');
                    var rowNoAnuladas = $('<tr></tr>');

                    var cellNumeroMov = $('<td></td>)');
                    var cellTotal = $('<td></td>)');
                    var cellFecha = $('<td></td>)');
                    cellNumeroMov.append(data[i].numeroMovimiento);
                    cellTotal.append(data[i].total);

                    if (data[i].fechaRegistro == null) {
                        cellFecha.append('');
                    } else {
                        cellFecha.append(new Date(data[i].fechaRegistro).toString(dateFormatJS));
                    }

                    if (data[i].activo == 1) {
                        var cellCheck = $('<td></td>)');
                        var checkbox = $('<input type="checkbox" onclick="anularGuia(' + data[i].idMovimiento + ');"/>');
                        cellCheck.append(checkbox);

                        rowNoAnuladas.append(cellNumeroMov);
                        rowNoAnuladas.append(cellTotal);
                        rowNoAnuladas.append(cellFecha);
                        rowNoAnuladas.append(cellCheck);
                    } else {
                        rowAnuladas.append(cellNumeroMov);
                        rowAnuladas.append(cellTotal);
                        rowAnuladas.append(cellFecha);
                    }
                    
                    if(rowNoAnuladas.find('td').length > 0){
                        $('#tblNoAnuladas tbody').append(rowNoAnuladas);
                    }
                    
                    if(rowAnuladas.find('td').length > 0){
                       $('#tblAnuladas tbody').append(rowAnuladas); 
                    }
                }
                
                if($('#tblNoAnuladas tbody tr').length == 0){
                    var rowEmpty = $('<tr></tr>');
                    var cellEmpty = $('<td></td>');
                    cellEmpty.append('No hay registros');
                    cellEmpty.attr('colspan', '4');
                    rowEmpty.append(cellEmpty);
                    $('#tblNoAnuladas tbody').append(rowEmpty);
                }
                
                if($('#tblAnuladas tbody tr').length == 0){
                    var rowEmpty = $('<tr></tr>');
                    var cellEmpty = $('<td></td>');
                    cellEmpty.append('No hay registros');
                    cellEmpty.attr('colspan', '3');
                    rowEmpty.append(cellEmpty);
                    $('#tblAnuladas tbody').append(rowEmpty); 
                }
            }
        });
    });
    
    function anularGuia(id){
        $.ajax({
            url: '<c:url value="/Movimiento/anularGuia" />/'+ id,            
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse, textStatus, jqXHR) {
                functionResponse(dataResponse, function(){
                    $('#btnConsultar').click();
                });
            }
        });
    }
</script>