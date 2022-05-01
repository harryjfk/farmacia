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
            </div>
        </div>
        <div class="form-actions">
            <button class="btn btn-default">Procesar</button>
        </div>
    </div>
</div>

<div class="row">    
    <div class="col-sm-12 col-md-12">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Nro.</th>
                    <th>Descripción</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>01</td>
                    <td>No presentan ICI</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>02</td>
                    <td>Diferencias entre saldo Final y Saldo Inicial</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>03</td>
                    <td>Diferencia entre Stock Automático e Informado</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>04</td>
                    <td>Medicamentos Sin fecha de Vencimiento</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>05</td>
                    <td>Medicamentos Vencidos en el Mes</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>06</td>
                    <td>Medicamentos Vencidos por Fecha de Vencimiento</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>07</td>
                    <td>Medicamento No Estratégico Sin Precio</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>08</td>
                    <td>Medicamento Estratégico no Entregado por Interv. Sanitarias</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>09</td>
                    <td>Medicamentos No Soporte entregado por Intervenciones Sanitarias</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>10</td>
                    <td>Diferencias de Precios de Establecimientos(Compara)</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>11</td>
                    <td>Distribuciones No Registradas</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>12</td>
                    <td>Ingresos No Enviados por Almacén</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
                <tr>
                    <td>13</td>
                    <td>Productos con Precio de Operación con Porcentaje Superior</td>
                    <td><input type="checkbox"></td>
                    <td><a href="#">Imprimir</a></td>
                </tr>
            </tbody>
        </table>
    </div>    
</div>

<script>
    $('#selAnio').change(function() {
        if($(this).val() == '-1'){            
            limpiarSelect('#selMes');
        }else{
            var Data = {"id": "", "value": "mes", "text": "nombreMes"};
            llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data);
        }
    });
</script>