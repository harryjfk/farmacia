<%@include file="../includeTagLib.jsp" %>
<style>
    .input-group-addon:hover{
        cursor:pointer;
    }
    
</style>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Desde <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" name="fechaDesde" class="form-control" data-field-date="" data-req />
                        <span class="input-group-addon" onclick="mostrarCalendar('fechaDesde');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Hasta <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" name="fechaHasta" class="form-control" data-field-date="" data-req />
                        <span class="input-group-addon" onclick="mostrarCalendar('fechaHasta');"><i class="splashy-calendar_month"></i></span>
                    </div> 
                </div>
            <div class="col-sm-6 col-md-6">
                <br>
                <h3><center>Fechas del IME anterior</center></h3>
            </div>

        </div>
        
        <div class="row">
            <div class="col-sm-6 col-md-6">
                    <label>N&deg; serie</label>
                    <input type="text" id="numSerie" name="numSerie" value="001" class="form-control" disabled>
            </div>
     
            <div class="col-sm-6 col-md-6">
                
                <div class="col-sm-6 col-md-6">
                    <label>Desde</label>
                    <input type="text" id="fechaDesdeAnterior" name="fechaDesdeAnterior" class="form-control" disabled />
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Hasta</label>
                    <input type="text" id="fechaHastaAnterior" name="fechaHastaAnterior" class="form-control" disabled/>
                </div>
                <br>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3 col-md-3">
                    <label>N&deg; Boleta de:</label>
                    <input type="text" id="numBoletaDe" name="numBoletaDe" class="form-control" data-req>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>N&deg; Boleta a:</label>
                    <input type="text" id="numBoletaA" name="numBoletaA" class="form-control" data-req>
                </div>
                <div class="col-sm-6 col-md-6">
                    <div class="col-sm-12 col-md-12">
                        <label>Almacen</label>
                        <input type="text" class="form-control" id="almacen" disabled>
                    </div>
                </div> 
        </div>
        <div class="row">
            <div class="col-sm-4 col-md-4">
                <label>Almacén</label>
                <select id="idAlmacen" name="idAlmacen" class="form-control">
                    <option value="100">TODOS</option>
                    <c:forEach var="almacen" items="${almacenes}">
                        <option value="${almacen.idAlmacen}">${almacen.descripcion}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-sm-2 col-md-2">
                <label>Importe total de ventas</label>
                <input type="text" id="AVenta" name="AVenta" class="form-control">
            </div>
        </div>
    </div>
</div>
<br>
<div class="formSep"></div>

<div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas por Demanda</label>
        <input type="text" id="recetasDemandaE" name="recetasDemandaE" class="form-control">
    </div>
</div><div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas SIS Emitidas</label>
        <input type="text" id="recetasSisE" name="recetasSisE" class="form-control">
    </div>
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas SIS Dispensadas</label>
        <input type="text" id="recetasSisD" name="recetasSisD" class="form-control">
    </div>
</div><div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas Interv. Sanit. Emitidas</label>
        <input type="text" id="recetasIntsanE" name="recetasIntsanE" class="form-control">
    </div>
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas Inter. Sanit. Dispensadas</label>
        <input type="text" id="recetasIntsanD" name="recetasIntsanD" class="form-control">
    </div>
</div><div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas SOAT Emitidas</label>
        <input type="text" id="recetasSoatE" name="recetasSoatE" class="form-control">
    </div>
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas SOAT Dispensadas</label>
        <input type="text" id="recetasSoatD" name="recetasSoatD" class="form-control">
    </div>
</div><div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas Exoneracion Emitidas</label>
        <input type="text" id="recetasExoneracionE" name="recetasExoneracionE" class="form-control">
    </div>
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas Exoneracion Dispensadas</label>
        <input type="text" id="recetasExoneracionD" name="recetasExoneracionD" class="form-control">
    </div>
</div>

<div class="row">
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas externas Emitidas</label>
        <input type="text" id="recetasExternasE" name="recetasExternasE" class="form-control">
    </div>
    <div class="col-sm-3 col-md-3">
        <label>N&deg; Recetas externas Dispensadas</label>
        <input type="text" id="recetasExternasD" name="recetasExternasD" class="form-control">
    </div>
</div>
<br>
<div class="formSep"></div>
<div class="row">
    <div class="col-sm-3 col-md-3">
        <label>Intevenciones Sanitarias - Soporte</label>
        <input type="text" id="AIntsan" name="AIntsan" class="form-control">
    </div>
    
</div>
<div class="row">
    
    <div class="col-sm-3 col-md-3">
        <label>Reembolso del SOAT</label>
        <input type="text" id="reembolsoSoat" name="reembolsoSoat" class="form-control" />
    </div>

    <div class="col-sm-3 col-md-3">
        <label>Reembolso del SIS</label>
        <input type="text" id="reembolsoSis" name="reembolsoSis" class="form-control" />
    </div>
</div>
<br>
<div class="row">
    <div class="col-sm-8 col-md-8">
        <div class="formSep">
            <center><h3>Gastos administrativos de la unidad ejecutora (20%)</h3></center>
            <div class="row">
                <div class="col-sm-4 cold-md-4">
                    <label>Fecha</label>
                    <div class="input-group">
                        <input type="text" id="fechaGasto" name="fechaGasto" class="form-control" data-field-date="" />
                        <span class="input-group-addon" onclick="mostrarCalendar('fechaGasto');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
                <div class="col-sm-4 cold-md-4">
                    <label>Partida</label>
                    <input type="text" id="partida" name="partida" class="form-control"/>
                </div>

                <div class="col-sm-4 cold-md-4">
                    <label>Doc. Fuente</label>
                    <input type="text" id="docFuente" name="docFuente" class="form-control"/>
                </div>
            </div>    
            <div class="row">
                <div class="col-sm-5 cold-md-5">
                    <label>Detalle del gasto</label>
                    <input type="text" id="detGasto" name="detGasto" class="form-control"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Importe</label>
                    <input type="text" id="importe" name="importe" class="form-control"/>
                </div>
                <div class="col-sm-3 cold-md-3">
                    <label></label>
                    <br>
                    <input type="button" id="btnAgregarDetalle" value="Agregar Gasto" class="form-control"/>
                </div>
            </div>
        </div>         
    </div>

</div>
<br>
<table id="tblDetalleGastos" class="table table-striped">
    <thead>
        <tr>
            <th></th>
            <th>Fecha</th>
            <th>Partida</th>
            <th>Detalle del Gasto</th>
            <th>Doc. Fuente</th>
            <th>Importe</th>
            <th></th>
        </tr>
    </thead>
    <tbody></tbody>
</table>

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
</div>
<script>
    var date = new Date();

    $(document).ready(function() {
        borrarDetalle('');
        
        var date1 = new Date();
        
        date1.setMonth((date.getMonth()-1)<0?12:date.getMonth()-1);
    
        $('#fechaDesde').datepicker().datepicker('setDate', date1);
        $('#fechaHasta').datepicker().datepicker('setDate', date);
        
        $('#btnProcesar').prop('disabled', true);
        $('#btnExcel').prop('disabled', true);
        
        var anio = date.toString('yyyy');
        $('#fechaGasto').datepicker().datepicker('setDate', date);
        $('#idAlmacen').change();
        
        $('#AVenta').numeric({decimalPlaces: 4, negative: false});
        $('#importe').numeric({decimalPlaces: 4, negative: false});
        $('#reembolsoSoat').numeric({decimalPlaces: 4, negative: false});
        $('#exonNegativo').numeric({decimalPlaces: 4, negative: false});
        $('#AIntsan').numeric({decimalPlaces: 4, negative: false});
        
        $('#recetasDemandaE').numeric({decimal:false, negative: false});
        $('#recetasSisE').numeric({decimal:false, negative: false});
        $('#recetasIntSanE').numeric({decimal:false, negative: false});
        $('#recetasSoatE').numeric({decimal:false, negative: false});
        $('#recetasExoneracionE').numeric({decimal:false, negative: false});
        $('#recetasExternasE').numeric({decimal:false, negative: false});
        
        $('#recetasDemandaD').numeric({decimal:false, negative: false});
        $('#recetasSisD').numeric({decimal:false, negative: false});
        $('#recetasIntSanD').numeric({decimal:false, negative: false});
        $('#recetasSoatD').numeric({decimal:false, negative: false});
        $('#recetasExoneracionD').numeric({decimal:false, negative: false});
        $('#recetasExternasD').numeric({decimal:false, negative: false});
        
        $('#numBoletaDe').focus();
        borrarDetalle('');
    });
    
    $('input[type=text]').focus(function(e){
        e.preventDefault();
    });
    
    $('#idAlmacen').change(function (){        
        if($(this).val() != '-1'){
            $.ajax({
                url: '<c:url value="/FormatoIME/existe" />?idAlmacen='+$('#idAlmacen').val(),            
                type: 'POST',            
                success: function(dataResponse) {
                    if(dataResponse.idIme == 0){ 
                       $('#fechaDesdeAnterior').val('');
                       $('#fechaHastaAnterior').val('');
                       $('#btnProcesar').prop('disabled', false);
                       $('#btnExcel').prop('disabled', true);
                       $('#almacen').val($('#idAlmacen').children(':selected').text());
                    }else{
                        $('#fechaDesdeAnterior').val(dataResponse.fechaDesdeString);
                        $('#fechaHastaAnterior').val(dataResponse.fechaHastaString);
                        $('#btnProcesar').prop('disabled', false);
                        $('#btnExcel').prop('disabled', false);
                       $('#almacen').val($('#idAlmacen').children(':selected').text());
                    }
                }
            });
        }        
    });
    
    function cargarDetalleGasto(){
        $.ajax({
            url:'<c:url value="/FormatoIME/cargarDetalle"/>',
            type:'POST',
            success:function(dataResponse){
                
                var tblDetalleGastos = $('#tblDetalleGastos > tbody');
                tblDetalleGastos.html("");
                
                var datos = '';
                var importeTotal = 0.0;
                for(var i = 0; i<dataResponse.length; i++){
                    datos+='<tr>';
                    datos+='<td>'+(i+1)+"</td>";
                    datos+='<td>'+dataResponse[i].fechaString+"</td>";
                    datos+='<td>'+dataResponse[i].partida+"</td>";
                    datos+='<td>'+dataResponse[i].detalleGasto+"</td>";
                    datos+='<td>'+dataResponse[i].docFuente+"</td>";
                    datos+='<td>'+dataResponse[i].importe+"</td>";
                    datos+='<td><a href="#" onClick="quitarDetalle('+i+',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>'
                    datos+='</tr>';
                    
                    importeTotal += parseFloat(dataResponse[i].importe);
                }
                                
                if(dataResponse.length==0){
                    datos+='<tr><td colspan="7"><center>No ha registrado gastos.</center></td></tr>';
                }else{
                    datos+='<tr>';
                    datos+='<td></td>';
                    datos+='<td></td>';
                    datos+='<td></td>';
                    datos+='<td></td>';
                    datos+='<td>Importe Total</td>';
                    datos+='<td>'+importeTotal+'</td>';
                    datos+='<td><a href="#" onClick="borrarDetalle(event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
                    datos+='</tr>';
                }
                tblDetalleGastos.append(datos);

            }
            
                
        });
    }
    
    $('#btnProcesar').click(function (e){
        e.preventDefault();
         
        
        var dataResponse = validateForm('[data-req]');
        if(dataResponse.estado == false){
            errorResponse(dataResponse);
            return;
        }
        
        
        var imeB = {
            "aVenta": parseFloat(($('#AVenta').val()=='') ? 0:$('#AVenta').val()),
            "aIntsan": parseFloat(($('#AIntsan').val()=='') ? 0:$('#AIntsan').val()),
            "recetasDemandaE": $('#recetasDemandaE').val()==''?0:$('#recetasDemandaE').val(),
            "recetasSisE": $('#recetasSisE').val()==''?0:$('#recetasSisE').val(),
            "recetasIntsanE": $('#recetasIntsanE').val()==''?0:$('#recetasIntsanE').val(),
            "recetasSoatE": $('#recetasSoatE').val()==''?0:$('#recetasSoatE').val(),
            "recetasExoneracionE": $('#recetasExoneracionE').val()==''?0:$('#recetasExoneracionE').val(),
            "recetasExternasE": $('#recetasExternasE').val()==''?0:$('#recetasExternasE').val(),
            "recetasDemandaD": $('#recetasDemandaE').val()==''?0:$('#recetasDemandaE').val(),
            "recetasSisD": $('#recetasSisD').val()==''?0:$('#recetasSisD').val(),
            "recetasIntsanD": $('#recetasIntsanD').val()==''?0:$('#recetasIntsanD').val(),
            "recetasSoatD": $('#recetasSoatD').val()==''?0:$('#recetasSoatD').val(),
            "recetasExoneracionD": $('#recetasExoneracionD').val()==''?0:$('#recetasExoneracionD').val(),
            "recetasExternasD": $('#recetasExternasD').val()==''?0:$('#recetasExternasD').val(),
            "numSerie": $('#numSerie').val(),
            "numBoletaA": $('#numBoletaA').val(),
            "numBoletaDe": $('#numBoletaDe').val(),
            "bcredito": 0, 
            "bsoat": parseFloat(($('#reembolsoSoat').val()=='') ? 0:$('#reembolsoSoat').val()), 
            "botros": 0, 
            "bsis": parseFloat(($('#reembolsoSis').val()=='') ? 0:$('#reembolsoSis').val()), 
            "bintSan": 0, 
            "bdn": 0, 
            "totalGastosAbastecimiento": 0, 
            "totalGAdmin": 0,
            "exonNegativo":0
        };
          var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS).getTime();
          var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS).getTime();
          
        $.ajax({
            url: '<c:url value="/FormatoIME/procesar" />?idAlmacen='+$('#idAlmacen').val()+"&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta,
            data: JSON.stringify(imeB),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                functionResponse(dataResponse, function(){
                    $('#idAlmacen').change();
                    limpiarTodo();
                });
            }
        });
    });
    
    $('#btnExcel').click(function (e){
        e.preventDefault();

        window.location = '<c:url value="/FormatoIME/excel" />?idAlmacen='+$('#idAlmacen').val();
    });   
    
    $('#btnAgregarDetalle').click(function(e){
        e.preventDefault();
        var detalle = {
            "partida": $('#partida').val(),
            "detalleGasto": $('#detGasto').val(),
            "docFuente": $('#docFuente').val(),
            "importe": parseFloat($('#importe').val())
        };
        
        $.ajax({
            url: '<c:url value="/FormatoIME/agregarDetalle"/>?fecha='+Date.parseExact($('#fechaGasto').val(), dateFormatJS).getTime(),
            data: JSON.stringify(detalle),
            dataType: 'json',
            type:'POST',
            contentType:'application/json',
            success:function(dataResponse){
                cargarDetalleGasto();
            }
        });
        
        limpiarGasto();
    });
    
    function quitarDetalle(indice,e){
        e.preventDefault();
        $.ajax({
            url:'<c:url value="/FormatoIME/quitarDetalle"/>'+'?indice='+indice,
            type:'POST',
            success:function(){
                cargarDetalleGasto();
            }
        });
    }
    
    function borrarDetalle(event){
        if(event == ''){
        }else{
            event.preventDefault();
        }
        $.ajax({
            
            url:'<c:url value="/FormatoIME/borrarDetalle"/>',
            type:'POST',
            success:function(){
                cargarDetalleGasto();
            }
        });
    }
    
    function limpiarGasto(){
        $('#partida').val('');
        $('#detGasto').val('');
        $('#docFuente').val('');
        $('#importe').val('');
        $('#partida').focus();
    }
    
    function limpiarTodo(){
        
        limpiarGasto();
        $('#fechaGasto').datepicker().datepicker('setDate', date);
        
        var date1 = new Date();
        
        date1.setMonth((date.getMonth()-1)<0?12:date.getMonth()-1);
    
        $('#fechaDesde').datepicker().datepicker('setDate', date1);
        $('#fechaHasta').datepicker().datepicker('setDate', date);

        $('#AVenta').val('');
        $('#AIntsan').val('');
        $('#numBoletaDe').val('');
        $('#numBoletaA').val('');
        $('#reembolsoSoat').val('');
        $('#reembolsoSis').val('');
        $('#recetasExternas').val('');
        $('#idAlmacen').change();
        
        borrarDetalle();
    }
</script>
