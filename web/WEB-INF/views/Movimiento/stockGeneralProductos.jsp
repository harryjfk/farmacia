<%@include file="../includeTagLib.jsp" %>

<style>
    #modalStockPorAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 40%;
    }
</style>

<div class="row">    
    <div class="col-sm-12 col-md-12">
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
        <table id="tblStockGeneral" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Stock</th>
                    <th>Forma Farmacéutica</th>
                    <th>Presentación</th>
                    <th>Concentración</th>
                    <th>Precio Ref.</th>
                    <th>Stock Min</th>
                    <th>Stock Max</th>
                    <th>Stock Por Almacénes</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalStockPorAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Stock en almacenes</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblStockPorAlmacen" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th>Almacén</th>
                                    <th>Código</th>
                                    <th>Stock</th>
                                    <th>Precio</th>
                                    <th>Importe</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cerrar</button>                
            </div>
        </div>
    </div>
</div>

<script>
    var tblStockPorAlmacen = document.getElementById('tblStockPorAlmacen');
    var tblStockGeneral = document.getElementById('tblStockGeneral');
    
    $(document).ready(function() {
        listarStockGeneral();
    });

    function listarStockGeneral() {
        
        if ($.fn.DataTable.fnIsDataTable(tblStockGeneral)) {
            $(tblStockGeneral).dataTable().fnDestroy();
        }

        $(tblStockGeneral).dataTable({
            "sAjaxSource": '<c:url value="/StockGeneralProductos/JSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "codigoSismed", "bSortable": false},
                {mData: "descripcion", "bSortable": false},
                {mData: "cantidad", "bSortable": false},
                {mData: "nombreFormaFarmaceutica", "bSortable": false},
                {mData: "presentacion", "bSortable": false},
                {mData: "concentracion", "bSortable": false},
                {mData: "precioRef", "bSortable": false},
                {mData: "stockMin", "bSortable": false},
                {mData: "stockMax", "bSortable": false},
                {mData: "idProducto", "bSortable": false, sWidth: "10%", mRender: function (data, row){                        
                        return '<a href="#" class="separator-icon-td" title="Detalle de Stock en Almacenes" onclick="modalStockPorAlmacen(' + data + ', event)"><i class="splashy-box"></i></a>';
                    }
                }
            ]
        });
    }
    
    function modalStockPorAlmacen(idProducto, e) {
        e.preventDefault();       
        $('#modalStockPorAlmacen').modal('show');

        if ($.fn.DataTable.fnIsDataTable(tblStockPorAlmacen)) {
           $(tblStockPorAlmacen).dataTable().fnDestroy();
        }
        
        $(tblStockPorAlmacen).dataTable({
            "fnServerParams": function(aoData) {
                 aoData.push({"name": 'idProducto', "value": idProducto});
            },
            "sAjaxSource": '<c:url value="/StockGeneralProductos/DetalleAlmacenes" />',
            "bServerSide": true,
            "bProcessing": true,
            "bPaginate": false,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [                  
                {mData: "almacen", sWidth: "65%", "bSortable": false},
                {mData: "codigoAlmacen", sWidth: "10%", "bSortable": false},
                {mData: "cantidad", sWidth: "10%", "bSortable": false},
                {mData: "precio", sWidth: "10%", "bSortable": false},
                {mData: "importe", sWidth: "15%", "bSortable": false}
            ]
        });       
    }

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblStockGeneral).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/StockGeneralProductos/Excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblStockGeneral).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/StockGeneralProductos/PDF"/>', dataTable);
    });
</script>