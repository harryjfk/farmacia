<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-11 col-md-11">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">            
            <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
            <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblProductos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Almacén</th>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Tipo</th>
                    <th>Lote</th>
                    <th>Vence</th>
                    <th>Stock Actual</th>
                    <th>Stock Mínimo</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>
    var tblProductos = document.getElementById('tblProductos');
    if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
        $(tblProductos).dataTable().fnDestroy();
    }

    $(tblProductos).dataTable({
        "sAjaxSource": '<c:url value="/productosStockMinimoJSON" />',
        "bServerSide": true,
        "bProcessing": true,
        "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
        "sPaginationType": "bootstrap_alt",
        "bAutoWidth": false,
        "aaSorting": [[1, 'asc']],
        "aoColumns": [
            {mData: "almacen", sWidth: "15%", "bSortable": false},
            {mData: "codigoSismed", sWidth: "8%", "bSortable": false},
            {mData: "producto", sWidth: "27%", "bSortable": false},
            {mData: "tipoProducto", sWidth: "10%", "bSortable": false},
            {mData: "lote", sWidth: "8%", "bSortable": false},
            {mData: "fechaVencimiento", sWidth: "8%", "bSortable": false, "mRender": function(data, type, row) {
                    return new Date(data).toString(dateFormatJS);
                }
            },
            {mData: "stock", sWidth: "7%", "bSortable": false},
            {mData: "stockMin", sWidth: "7%", "bSortable": false}
        ],
        "fnServerData": function(sSource, aoData, fnCallback, oSettings) {
            oSettings.jqXHR = $.ajax({
                "dataType": 'json',
                "type": "GET",
                "url": sSource,
                "data": aoData,
                "success": fnCallback
            }).done(function(data) {
                if ($(tblProductos).dataTable().fnGetData().length > 0) {
                    $('#divProductosVencimiento').attr('style', '');
                }
            });
        }
    });
    
     $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductosStockMinimo/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductosStockMinimo/pdf"/>', dataTable);
    });
</script>