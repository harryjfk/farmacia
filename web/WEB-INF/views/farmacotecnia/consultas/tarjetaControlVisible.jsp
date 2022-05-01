<%@include file="../../includeTagLib.jsp" %>
<style>
    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 60%;
    }
</style>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Desde</label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta</label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
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

<div id="divTblTarjetaControl" class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblTarjetaControl" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>                
                    <th>Insumo</th>
                    <th>Tipo de Producto</th>
                    <th>Unidad de Medida</th>
                    <th>Ingreso</th>
                    <th>Salida</th>
                    <th>Saldo</th>
                </tr>
            </thead>                    
        </table>
    </div>        
</div>
<script>
    var idModulo = location.pathname.split('/')[3];
    var tblProductos = document.getElementById('tblProductos');
    var tblTarjetaControl = document.getElementById('tblTarjetaControl');

    $(document).ready(function () {
        $('#btnLimpiarBusqueda').click();
        $('#selAnio').change();
    });

    $('#btnLimpiarBusqueda').click(function (e) {
        e.preventDefault();
        $('#fechaDesde,#fechaHasta').val('');
        $('#btnTodos').click();
        $('#divTblTarjetaControl').attr('style', 'display:none');
    });

    function validate() {
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
            return false;
        }
        return true;
    }

    $('#btnBuscar').click(function (e) {
        e.preventDefault();

        if (validate() == false) {
            return;
        }
        $('#divTblTarjetaControl').removeAttr('style');
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);

        if ($.fn.DataTable.fnIsDataTable(tblTarjetaControl)) {
            $(tblTarjetaControl).dataTable().fnDestroy();
        }

        $(tblTarjetaControl).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'fechaDesde', "value": ((fechaDesde == null) ? '' : fechaDesde.getTime())});
                aoData.push({"name": 'fechaHasta', "value": ((fechaHasta == null) ? '' : fechaHasta.getTime())});
            },
            "sAjaxSource": '<c:url value="/dispensacion/' + idModulo + '/tarjetacontrolvisible/JSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 10,
            "aoColumns": [
                {mData: "insumo.nombre", "bSortable": true},
                {mData:"insumo.tipoProducto.nombreTipoProducto", "bSortable":true},
                {mData: "insumo.unidad.nombreUnidadMedida", "bSortable": true},
                {mData: "ingresos", "bSortable": true},
                {mData: "salidas", "bSortable": true},
                {mData: "saldo", "bSortable": true}
            ]
        });
    });

    $('#btnExcel').click(function (e) {
        e.preventDefault();

        if (validate() == false) {
            return;
        }

        var dataTable = $(tblTarjetaControl).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/dispensacion/' + idModulo + '/tarjetacontrolvisible/TarjetaControlVisible/Excel"/>', dataTable);
    });

    $('#btnPDF').click(function (e) {
        e.preventDefault();

        if (validate() == false) {
            return;
        }

        var dataTable = $(tblTarjetaControl).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/dispensacion/' + idModulo + '/tarjetacontrolvisible/TarjetaControlVisible/PDF"/>', dataTable);
    });
</script>