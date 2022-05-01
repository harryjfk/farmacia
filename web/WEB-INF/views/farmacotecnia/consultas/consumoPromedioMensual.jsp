<%@include file="../../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <strong>Per&iacute;odo</strong>
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Año</label>
                    <select id="selAnio" class="form-control" data-req="">                        
                        <c:forEach var="anio" items="${anios}">
                            <option value="${anio}">${anio}</option>
                        </c:forEach>
                    </select> 
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Mes <span class="f_req">*</span></label>
                    <select id="selMes" class="form-control" data-req="">                        
                    </select> 
                </div>       
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Desde</label>
                    <div class="input-group">
                        <select type="text" id="desde" class="form-control" >
                            <option value="01/01/">Enero</option>
                            <option value="02/01/">Febrero</option>
                            <option value="03/01/">Marzo</option>
                            <option value="04/01/">Abril</option>
                            <option value="05/01/">Mayo</option>
                            <option value="06/01/">Junio</option>
                            <option value="07/01/">Julio</option>
                            <option value="08/01/">Agosto</option>
                            <option value="09/01/">Septiembre</option>
                            <option value="10/01/">Octubre</option>
                            <option value="11/01/">Noviembre</option>
                            <option value="12/01/">Diciembre</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Hasta</label>
                    <div class="input-group">
                        <select type="text" id="hasta" class="form-control" >
                            <option value="01/31/">Enero</option>
                            <option value="02/29/">Febrero</option>
                            <option value="03/31/">Marzo</option>
                            <option value="04/30/">Abril</option>
                            <option value="05/31/">Mayo</option>
                            <option value="06/30/">Junio</option>
                            <option value="07/31/">Julio</option>
                            <option value="08/31/">Agosto</option>
                            <option value="09/30/">Septiembre</option>
                            <option value="10/31/">Octubre</option>
                            <option value="11/30/">Noviembre</option>
                            <option value="12/31/">Diciembre</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <div class="checkbox">
                        <label>
                            <input id="allmonths" type="checkbox"> Todos los meses
                        </label>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Almacén</label>
                    <div class="input-group">
                        <select type="text" id="idAlmacen" class="form-control">
                            <option value="">-- TODOS --</option>
                            <c:forEach items="${almacenes}" var="almacen">
                                <option value="${almacen.idAlmacen}">${almacen.descripcion}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'consulta'}">
                    <button id="btnBuscar" class="btn btn-primary" type="submit" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                    <button id="btnLimpiarBusqueda" class="btn btn-primary" title="Limpiar Búsqueda">Limpiar Búsqueda</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                    <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                    <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<div id="divTblConsumoPromedioMen" class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblConsumoPromedioMen" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>                
                    <th>C&oacute;d.</th>
                    <th>Descripci&oacute;n</th>
                    <th>En</th>
                    <th>Feb</th>
                    <th>Mar</th>                
                    <th>Abr</th>
                    <th>May</th>
                    <th>Jun</th>
                    <th>Jul</th>
                    <th>Ag</th>
                    <th>Sep</th>
                    <th>Oct</th>
                    <th>Nov</th>
                    <th>Dic</th>
                    <th>Promedio</th>
                    <th>Saldo Actual</th>
                </tr>
            </thead>                    
        </table>
    </div>        
</div>
<script>
    var idModulo = location.pathname.split('/')[3];
    var tblProductos = document.getElementById('tblProductos');
    var tblConsumoPromedioMen = document.getElementById('tblConsumoPromedioMen');

    $(document).ready(function () {
        $('#btnLimpiarBusqueda').click();
        $('#selAnio').change();
    });

    $('#btnLimpiarBusqueda').click(function (e) {
        e.preventDefault();

        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
        $('#fechaDesde,#fechaHasta').val('');
        $("#idAlmacen").val('-1');
        $("#tipoProducto").val('0');
        $('#divTblConsumoPromedioMen').attr('style', 'display:none');
    });

    $('#selAnio').change(function () {
        var Data = {"id": "", "value": "mes", "text": "nombreMes"};
        llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function () {
            var mes = new Date().toString('MM');
            $('#selMes').val(mes);
        });
    });

    function validate() {
        var dataResponse = validateForm('[data-req]');

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return false;
        }
        return true;
    }

    $('#btnBuscar').click(function (e) {
        e.preventDefault();

        if (validate() == false) {
            return;
        }
        $('#divTblConsumoPromedioMen').removeAttr('style');
        var desde = $("#desde").val();
        var hasta = $("#hasta").val();

        if ($.fn.DataTable.fnIsDataTable(tblConsumoPromedioMen)) {
            $(tblConsumoPromedioMen).dataTable().fnDestroy();
        }

        $(tblConsumoPromedioMen).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'desde', "value": ((desde == null) ? '' : desde)});
                aoData.push({"name": 'hasta', "value": ((hasta == null) ? '' : hasta)});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
                aoData.push({"name": 'allmonths', "value": $('#allmonths').prop('checked')});
            },
            "sAjaxSource": '<c:url value="/dispensacion/' + idModulo + '/consumopromedomen/JSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 10,
            "aoColumns": [
                {mData: "codigo", "bSortable": false},
                {mData: "descripcion", "bSortable": false},
                {mData: "enero", "bSortable": false},
                {mData: "febrero", "bSortable": false},
                {mData: "marzo", "bSortable": false},
                {mData: "abril", "bSortable": false},
                {mData: "mayo", "bSortable": false},
                {mData: "junio", "bSortable": false},
                {mData: "julio", "bSortable": false},
                {mData: "agosto", "bSortable": false},
                {mData: "septiembre", "bSortable": false},
                {mData: "octubre", "bSortable": false},
                {mData: "noviembre", "bSortable": false},
                {mData: "diciembre", "bSortable": false},
                {mData: "promedio", "bSortable": false, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "saldoActual", "bSortable": false}
            ]
        });
    });
    $("body").undelegate("#btnExcel, #btnPDF", 'click');
    $('#btnExcel').click(function (e) {
        e.preventDefault();
        window.location.href = '<c:url value="/dispensacion/' + idModulo + '/consumopromedomen/reporteExcel"/>';
        e.stopPropagation();
    });

    $('#btnPDF').click(function (e) {
        e.preventDefault();
        window.location.href = '<c:url value="/dispensacion/' + idModulo + '/consumopromedomen/reportePdf"/>';
        e.stopPropagation();
    });
</script>