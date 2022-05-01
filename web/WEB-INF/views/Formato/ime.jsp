<%@include file="../includeTagLib.jsp" %>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">
<style>
    .input-group-addon:hover{
        cursor:pointer;
    }

    row + .row {
        margin-top: 15px;
    }

    .form-control{
        height: 30px;    
    }

    .tr-thead > td{
        background-color: #f0f3f5 !important;
        font-weight: bold;
    }
</style>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <table class="table table-bordered table-condensed table-striped">
            <thead>
                <tr>
                    <th colspan="3">
                        <div class="row">
                            <div class="col-md-2">
                                Periodo del:
                            </div>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" id="FechaDesde" class="form-control" data-field-date="" data-req />
                                    <span class="input-group-addon" onclick="mostrarCalendar('FechaDesde');"><i class="splashy-calendar_month"></i></span>
                                </div>
                            </div>
                            <div class="col-md-2">
                                Periodo al:
                            </div>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" id="FechaHasta" class="form-control" data-field-date="" data-req />
                                    <span class="input-group-addon" onclick="mostrarCalendar('FechaHasta');"><i class="splashy-calendar_month"></i></span>
                                </div> 
                            </div>
                        </div>
                    </th>
                </tr>
                <tr>
                    <th colspan="3">SALIDAS POR VENTA O EXONERACIÓN (Boletas de venta)</th>
                </tr>
                <tr>
                    <th colspan="2">Boletas emitidas</th>
                    <th>Importe total de ventas</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="col-md-4">Serie:</td>
                    <td>
                        <input type="text" 
                               class="form-control input-sm"
                               id="NumSerie" 
                               value="001"
                               disabled />
                    </td>
                    <td rowspan="3">
                        <input type="text" 
                               class="form-control input-sm"
                               id="AVenta" 
                               name="AVenta" />
                    </td>
                </tr>
                <tr>
                    <td>Del:</td>
                    <td>
                        <input type="text" 
                               class="form-control input-sm"
                               id="NumBoletaDe" />
                    </td>
                </tr>
                <tr>
                    <td>Al:</td>
                    <td>
                        <input type="text" 
                               class="form-control input-sm"
                               id="NumBoletaA" />
                    </td>
                </tr>
                <tr class="tr-thead">
                    <td></td>
                    <td>EMITIDAS</td>
                    <td>DISPENSADA</td>
                </tr>
                <tr>
                    <td>No. De  Recetas por Demanda</td>
                    <td><input type="text" id="RecetasDemandaD" class="form-control input-sm" /></td>
                    <td><input type="text" id="RecetasDemandaE" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>No. De Recetas por SIS</td>
                    <td><input type="text" id="RecetasSisD" class="form-control input-sm" /></td>
                    <td><input type="text" id="RecetasSisE" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>No. Recetas Interven Sanit.</td>
                    <td><input type="text" id="RecetasIntsanD" class="form-control input-sm" /></td>
                    <td><input type="text" id="RecetasIntsanE" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>No .Recetas por Soat</td>
                    <td><input type="text" id="RecetasSoatD" class="form-control input-sm" /></td>
                    <td><input type="text" id="RecetasSoatE" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>No .Recetas por Exoneración</td>
                    <td><input type="text" id="RecetasExoneracionD" class="form-control input-sm" /></td>
                    <td><input type="text" id="RecetasExoneracionE" class="form-control input-sm" /></td>
                </tr>
                <tr class="tr-thead">
                    <td rowspan="2">IMPORTE</td>
                    <td rowspan="2">IMPORTE DE ATENCIONES FARMACIA(FORMATO ICI)</td>
                    <td>**  CAPTACION O REEMBOLSOS DEL MES</td>
                    <td rowspan="2">CUENTAS POR COBRAR ACUMULADAS DEL MES ANTERIOR</td>
                </tr>
                <tr class="tr-thead">
                    <td>TOTAL (B)</td>
                </tr>
                <tr>
                    <td>VENTAS(+)</td>
                    <td></td>
                    <td></td>
                    <td><input type="text" id="CuentaCobrarAcumMesAnt_Ventas" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>SOAT (+)</td>
                    <td><input type="text" id="DSoat" class="form-control input-sm" /></td>
                    <td><input type="text" id="ESoat" class="form-control input-sm" /></td>
                    <td><input type="text" id="CuentaCobrarAcumMesAnt_Soat" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>SIS (+)</td>
                    <td><input type="text" id="DSis" class="form-control input-sm" /></td>
                    <td><input type="text" id="ESis" class="form-control input-sm" /></td>
                    <td><input type="text" id="CuentaCobrarAcumMesAnt_Sis" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td>INTEVENCIONES SANITARIAS- SOPORTE(+)</td>
                    <td><input type="text" id="DIntsan" class="form-control input-sm" /></td>
                    <td><input type="text" id="EIntsan" class="form-control input-sm" /></td>
                    <td><input type="text" id="CuentaCobrarAcumMesAnt_InterSanit" class="form-control input-sm" /></td>
                </tr>
                <tr class="tr-thead">
                    <td colspan="3"></td> 
                    <td></td> 
                </tr>
                <tr>
                    <td colspan="3">SALDO DISPONIBLE DEL MES ANTERIOR PARA MEDICAMENTOS +</td>
                    <td><input type="text" id="SaldoDisponibleMesAnt_Medicamentos" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td colspan="3">(+) SALDO DISPONIBLE DEL MES PASADO PARA GASTOS ADM. </td>
                    <td><input type="text" id="SaldoDisponibleMesAnt_GastosAdmin" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td colspan="3">(-) EXONERACIONES</td>
                    <td><input type="text" id="Exoneraciones_negativo" class="form-control input-sm" /></td>
                </tr>
                <tr>
                    <td colspan="3">TOTAL DE ABASTECIMIENTO EN ELMES  TRANSF Y/O DONA (Reenbolso SIS)</td>
                    <td><input type="text" id="TotalAbastecimientoMes" class="form-control input-sm" /></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<br>
<div class="row">
    <div class="col-sm-6 col-md-6">
        <div class="panel panel-info">
            <div class="panel-heading">
                Gastos administrativos de la unidad ejecutora (20%)
            </div>
            <div class="panel-body">
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
                    <div class="col-sm-12 cold-md-12">
                        <label>Detalle del gasto</label>
                        <input type="text" id="detGasto" name="detGasto" class="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Importe</label>
                        <input type="text" id="importe" name="importe" class="form-control"/>
                    </div>
                    <div class="col-sm-4 cold-md-4 col-md-offset-4">
                        <label>&nbsp;</label>
                        <button type="button" id="btnAgregarDetalle" class="btn btn-info btn-sm form-control">
                            <i class="fa fa-plus"></i>&nbsp;&nbsp;Agregar Gasto
                        </button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-md-12 table-responsive">
                        <table id="tblDetalleGastos" class="table table-condensed table-striped">
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <div class="text-center">
            <button id="btn-procesar" class="btn btn-primary"><i class="fa fa-cog"></i>&nbsp;Procesar IME</button>
            <button id="btn-excel" class="btn btn-primary">
                <i class="fa fa-file-excel-o"></i>&nbsp;Exportar Excel</button>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        var date = new Date();
        var date1 = new Date();
        date1.setMonth((date.getMonth() - 1) < 0 ? 12 : date.getMonth() - 1);
        $('#FechaDesde').datepicker().datepicker('setDate', date1);
        $('#FechaHasta').datepicker().datepicker('setDate', date);

        $('#fechaGasto').datepicker().datepicker('setDate', date);
        cargarDetalleGasto();
    });

    $('#btn-procesar').click(function () {
        var imeB = {
            "NumSerie": $('#NumSerie').val(),
            "NumBoletaDe": $('#NumBoletaDe').val(),
            "NumBoletaA": $('#NumBoletaA').val(),
            "AVenta": parseFloat(($('#AVenta').val() == '') ? 0 : $('#AVenta').val()),
            "RecetasDemandaD": parseFloat(($('#RecetasDemandaD').val() == '') ? 0 : $('#RecetasDemandaD').val()),
            "RecetasDemandaE": parseFloat(($('#RecetasDemandaE').val() == '') ? 0 : $('#RecetasDemandaE').val()),
            "RecetasSisD": parseFloat(($('#RecetasSisD').val() == '') ? 0 : $('#RecetasSisD').val()),
            "RecetasSisE": parseFloat(($('#RecetasSisE').val() == '') ? 0 : $('#RecetasSisE').val()),
            "RecetasIntsanD": parseFloat(($('#RecetasIntsanD').val() == '') ? 0 : $('#RecetasIntsanD').val()),
            "RecetasIntsanE": parseFloat(($('#RecetasIntsanE').val() == '') ? 0 : $('#RecetasIntsanE').val()),
            "RecetasSoatD": parseFloat(($('#RecetasSoatD').val() == '') ? 0 : $('#RecetasSoatD').val()),
            "RecetasSoatE": parseFloat(($('#RecetasSoatE').val() == '') ? 0 : $('#RecetasSoatE').val()),
            "RecetasExoneracionD": parseFloat(($('#RecetasExoneracionD').val() == '') ? 0 : $('#RecetasExoneracionD').val()),
            "RecetasExoneracionE": parseFloat(($('#RecetasExoneracionE').val() == '') ? 0 : $('#RecetasExoneracionE').val()),
            "DSoat": parseFloat(($('#DSoat').val() == '') ? 0 : $('#DSoat').val()),
            "ESoat": parseFloat(($('#ESoat').val() == '') ? 0 : $('#ESoat').val()),
            "DSis": parseFloat(($('#DSis').val() == '') ? 0 : $('#DSis').val()),
            "ESis": parseFloat(($('#ESis').val() == '') ? 0 : $('#ESis').val()),
            "DIntsan": parseFloat(($('#DIntsan').val() == '') ? 0 : $('#DIntsan').val()),
            "EIntsan": parseFloat(($('#EIntsan').val() == '') ? 0 : $('#EIntsan').val()),
            "CuentaCobrarAcumMesAnt_Ventas": parseFloat(($('#CuentaCobrarAcumMesAnt_Ventas').val() == '') ? 0 : $('#CuentaCobrarAcumMesAnt_Ventas').val()),
            "CuentaCobrarAcumMesAnt_Soat": parseFloat(($('#CuentaCobrarAcumMesAnt_Soat').val() == '') ? 0 : $('#CuentaCobrarAcumMesAnt_Soat').val()),
            "CuentaCobrarAcumMesAnt_Sis": parseFloat(($('#CuentaCobrarAcumMesAnt_Sis').val() == '') ? 0 : $('#CuentaCobrarAcumMesAnt_Sis').val()),
            "CuentaCobrarAcumMesAnt_InterSanit": parseFloat(($('#CuentaCobrarAcumMesAnt_InterSanit').val() == '') ? 0 : $('#CuentaCobrarAcumMesAnt_InterSanit').val()),
            "SaldoDisponibleMesAnt_Medicamentos": parseFloat(($('#SaldoDisponibleMesAnt_Medicamentos').val() == '') ? 0 : $('#SaldoDisponibleMesAnt_Medicamentos').val()),
            "SaldoDisponibleMesAnt_GastosAdmin": parseFloat(($('#SaldoDisponibleMesAnt_GastosAdmin').val() == '') ? 0 : $('#SaldoDisponibleMesAnt_GastosAdmin').val()),
            "Exoneraciones_negativo": parseFloat(($('#Exoneraciones_negativo').val() == '') ? 0 : $('#Exoneraciones_negativo').val()),
            "TotalAbastecimientoMes": parseFloat(($('#TotalAbastecimientoMes').val() == '') ? 0 : $('#TotalAbastecimientoMes').val())
        };
        var fechaDesde = Date.parseExact($('#FechaDesde').val(), dateFormatJS).getTime();
        var fechaHasta = Date.parseExact($('#FechaHasta').val(), dateFormatJS).getTime();

        console.log('json');
        console.log(JSON.stringify(imeB));

        $.ajax({
            url: '<c:url value="/FormatoIME/procesar2" />?' + "fechaDesde=" + fechaDesde + "&fechaHasta=" + fechaHasta,
            data: JSON.stringify(imeB),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                functionResponse(dataResponse, function () {
                    
                });
            }
        });
    });

    $('#btn-excel').click(function (e) {
        e.preventDefault();
        window.location = '<c:url value="/FormatoIME/excel2" />';
    });

    $('#btnAgregarDetalle').click(function (e) {
        e.preventDefault();
        var detalle = {
            "partida": $('#partida').val(),
            "detalleGasto": $('#detGasto').val(),
            "docFuente": $('#docFuente').val(),
            "importe": parseFloat($('#importe').val())
        };

        $.ajax({
            url: '<c:url value="/FormatoIME/agregarDetalle"/>?fecha=' + Date.parseExact($('#fechaGasto').val(), dateFormatJS).getTime(),
            data: JSON.stringify(detalle),
            dataType: 'json',
            type: 'POST',
            contentType: 'application/json',
            success: function (dataResponse) {
                cargarDetalleGasto();
            }
        });

        limpiarGasto();
    });

    function cargarDetalleGasto() {
        $.ajax({
            url: '<c:url value="/FormatoIME/cargarDetalle"/>',
            type: 'POST',
            success: function (dataResponse) {

                var tblDetalleGastos = $('#tblDetalleGastos > tbody');
                tblDetalleGastos.html("");

                var datos = '';
                var importeTotal = 0.0;
                for (var i = 0; i < dataResponse.length; i++) {
                    datos += '<tr>';
                    datos += '<td>' + (i + 1) + "</td>";
                    datos += '<td>' + dataResponse[i].fechaString + "</td>";
                    datos += '<td>' + dataResponse[i].partida + "</td>";
                    datos += '<td>' + dataResponse[i].detalleGasto + "</td>";
                    datos += '<td>' + dataResponse[i].docFuente + "</td>";
                    datos += '<td>' + dataResponse[i].importe + "</td>";
                    datos += '<td><a href="#" onClick="quitarDetalle(' + i + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>'
                    datos += '</tr>';

                    importeTotal += parseFloat(dataResponse[i].importe);
                }

                if (dataResponse.length == 0) {
                    datos += '<tr><td colspan="7"><center>No ha registrado gastos.</center></td></tr>';
                } else {
                    datos += '<tr>';
                    datos += '<td></td>';
                    datos += '<td></td>';
                    datos += '<td></td>';
                    datos += '<td></td>';
                    datos += '<td>Importe Total</td>';
                    datos += '<td>' + importeTotal + '</td>';
                    //datos += '<td><a href="#" onClick="borrarDetalle(event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
                    datos += '<td></td>';        
                    datos += '</tr>';
                }
                tblDetalleGastos.append(datos);
            }
        });
    }
    
    function quitarDetalle(indice, e) {
        e.preventDefault();
        $.ajax({
            url: '<c:url value="/FormatoIME/quitarDetalle"/>' + '?indice=' + indice,
            type: 'POST',
            success: function () {
                cargarDetalleGasto();
            }
        });
    }
    
    function limpiarGasto() {
        $('#partida').val('');
        $('#detGasto').val('');
        $('#docFuente').val('');
        $('#importe').val('');
        $('#partida').focus();
    }
</script>