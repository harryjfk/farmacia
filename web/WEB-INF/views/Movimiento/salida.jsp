<%@include file="../includeTagLib.jsp" %>
<%@ page import="java.util.Date" %>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'registrar'}">
                <button id="btnAgregar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
            <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
            <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
        <button id="btnBuscar" class="btn btn-primary">Buscar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">                
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Desde <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>                    
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
                    </div>                    
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblSalida" class="table table-bordered table-striped table-condensed">
            <thead>
                <tr>
                    <th>Fecha de Registro</th>
                    <th>Nro.</th>
                    <th>Almacén Origen</th>
                    <th>Almacén Destino</th>
                    <th>Tipo Documento</th>
                    <th>Nro. Documento</th>
                    <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblSalida = document.getElementById('tblSalida');

    $(document).ready(function() {
        var anio = parseInt(${anio});
        var mes = parseInt(${mes}) - 1;
        var firstDay = new Date(anio, mes);
        var lastDay = new Date(anio, mes + 1, 0);
        
        $('#fechaDesde').val(firstDay.toString(dateFormatJS));
        $('#fechaHasta').val(lastDay.toString(dateFormatJS));
        
        listarSalidas();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSalida).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/NotaSalida/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSalida).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/NotaSalida/pdf" />', dataTable);
    });

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/NotaSalida/registrar" />';
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadSalidas();
    });

    function reloadSalidas() {
        var dataTable = $(tblSalida).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarSalidas() {

        if ($.fn.DataTable.fnIsDataTable(tblSalida)) {
            $(tblSalida).dataTable().fnDestroy();
        }

        $(tblSalida).dataTable({
            "fnServerParams": function(aoData) {
                var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
                var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);
                
                aoData.push({"name": 'fechaDesde', "value": fechaDesde.getTime()});
                aoData.push({"name": 'fechaHasta', "value": fechaHasta.getTime()});
            },
            "sAjaxSource": '<c:url value="/NotaSalida/salidasJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-12'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[1, 'desc']],
            "aoColumns": [                
                {mData: "fechaRegistro", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                        return new Date(data).toString(dateFormatJS);
                    }
                },
                {mData: "numeroMovimiento", sWidth: "8%", "bSortable": false},
                {mData: "almacenOrigen", sWidth: "25%", "bSortable": false},
                {mData: "almacenDestino", sWidth: "25%", "bSortable": false},
                {mData: "tipoDocumento", sWidth: "11%", "bSortable": false},
                {mData: "numeroDocumento", sWidth: "11%", "bSortable": false},                
                {mData: "idMovimiento", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var printHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="<c:url value="/NotaSalida/modificar" />/' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'imprimir'}">
                        printHTML += '<a href="#" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}" onclick="printSalida(' + data + ', event)"><i class="splashy-download"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML + printHTML;
                    }
                }
            ]
        });
    }

    function printSalida(idSalida, e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaSalida/imprimir" />/' + idSalida;
    }
</script>