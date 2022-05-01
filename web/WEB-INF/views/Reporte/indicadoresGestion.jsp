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
                <div class="col-sm-3 col-md-3">
                    <label>Ultimos meses analizados <span class="f_req">*</span></label>
                    <select class="form-control">
                        <option value="3">3</option>
                        <option value="6">6</option>                        
                    </select> 
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Tipo de Producto <span class="f_req">*</span></label>
                    <select id="selTipoProducto" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoProducto" items="${tiposProducto}">                                
                            <option value="${tipoProducto.idTipoProducto}">${tipoProducto.nombreTipoProducto}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="form-actions">
                <button class="btn btn-default">Consultar</button>
                <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                    <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                        <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="alert alert-success alert-dismissable">
            <strong>TOTAL</strong> 100.00, <strong>CRÍTICOS</strong> 100.00%, <strong>ACEPTABLE</strong> 100.00%, <strong>NO ACEPTABLE</strong> 100.00%, <strong>DISPONIBLES</strong> 100.00%
        </div>
    </div>
</div>
<div class="row">    
    <div class="col-sm-12 col-md-12">
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>F.F</th>
                    <th>E/S</th>
                    <th>P</th>
                    <th>ENE</th>
                    <th>FEB</th>
                    <th>MAR</th>
                    <th>ABR</th>
                    <th>MAY</th>
                    <th>JUN</th>                    
                    <th>CPMA</th>
                    <th>STOCK</th>
                    <th>MESES PROV.</th>
                    <th>REQU</th>
                    <th>SITUACIÓN</th>
                    <th>SOBRE STOCK / SIN ROTACIÓN</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>                                        
                </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="alert alert-success alert-dismissable">
    <strong>TOTAL</strong> 100.00, <strong>CRÍTICOS</strong> 100.00%, <strong>ACEPTABLE</strong> 100.00%, <strong>NO ACEPTABLE</strong> 100.00%, <strong>DISPONIBLES</strong> 100.00%
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