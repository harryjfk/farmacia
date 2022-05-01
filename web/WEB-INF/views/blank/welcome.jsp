<%@include file="../includeTagLib.jsp" %>

<br/>

<div id="divProductosVencimiento" class="row" style="display: none;">
    <div class="col-sm-12 col-md-12">
        <h4>Productos Vencidos y por Vencer</h4><br />
        <table id="tblProductos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Almacén</th>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Tipo</th>
                    <th>Lote</th>
                    <th>Estado</th>
                    <th>Vence</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Total</th>                    
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
        "sAjaxSource": '<c:url value="/Producto/productosAlertaJSON" />',
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
            {mData: "estado", sWidth: "8%", "bSortable": false},
            {mData: "fechaVencimiento", sWidth: "8%", "bSortable": false, "mRender": function(data, type, row) {
                    return new Date(data).toString(dateFormatJS);
                }
            },            
            {mData: "precio", sWidth: "8%", "bSortable": false},
            {mData: "stock", sWidth: "7%", "bSortable": false},
            {mData: "total", sWidth: "8%", "bSortable": false}            
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
</script>

