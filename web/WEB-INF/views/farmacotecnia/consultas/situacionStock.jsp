<%@include file="../../includeTagLib.jsp" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Desde</label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" name="txtFechaInicio" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta</label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" name="txtFechaFin" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
                    </div>
                </div>           
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Almacén <span class="f_req">*</span></label>
                    <div class="input-group">
                        <select id="idAlmacen" class="form-control" data-req="">
                            <option value="-1">-- Seleccione un almac&eacute;n --</option>
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
                    <button id="btnBuscar" class="btn btn-primary">Consultar</button>
                </c:if>
            </c:forEach>
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                    <button id="btnPDF" class="btn btn-primary">PDF</button>
                </c:if>
            </c:forEach>
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                    <button id="btnExcel" class="btn btn-primary">Excel</button>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblStocks" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Insumo</th>
                    <th>Unidad de Medida</th>
                    <th>Ingreso</th>
                    <th>Stock</th>
                    <th>Precio</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>
    var idModulo = location.pathname.split('/')[3];

    $('#btnBuscar').click(function (e) {
        e.preventDefault();
        var dataResponse = validateForm('[data-req]');
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);

        if ($('#fechaHasta').val().length > 0) {
            if (fechaHasta == null) {
                dataResponse.mensajesRepuesta.push('Fecha hasta inválida.');
                dataResponse.estado = false;
            }
        }

        if ($('#fechaDesde').val().length > 0) {
            if (fechaDesde == null) {
                dataResponse.mensajesRepuesta.push('Fecha desde inválida.');
                dataResponse.estado = false;
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

        var tblStocks = document.getElementById('tblStocks');

        if ($.fn.DataTable.fnIsDataTable(tblStocks)) {
            $(tblStocks).dataTable().fnDestroy();
        }

        $(tblStocks).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
                aoData.push({"name": 'fechaDesde', "value": ((fechaDesde == null) ? null : fechaDesde.toString(dateFormatJS))});
                aoData.push({"name": 'fechaHasta', "value": ((fechaHasta == null) ? null : fechaHasta.toString(dateFormatJS))});
            },
            "sAjaxSource": '<c:url value="/dispensacion/' + idModulo + '/situacionstock/JSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "id", "bSortable": false},
                {mData: "nombre", "bSortable": false},
                {mData: "unidad.nombreUnidadMedida", "bSortable": false},
                {mData: "ingreso", "bSortable": false},
                {mData: "stock", "bSortable": false},
                {mData: "precio", "bSortable": false}
            ]
        });

        $("#btnPDF").click(function (e) {
            e.preventDefault();
            window.location.href = '<c:url value="/dispensacion/' + idModulo + '/situacionstock/reportePdf"/>';
        });
        $("#btnExcel").click(function (e) {
            e.preventDefault();
            window.location.href = '<c:url value="/dispensacion/' + idModulo + '/situacionstock/reporteExcel"/>';
        });
    });
</script>