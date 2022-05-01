<%@include file="../includeTagLib.jsp" %>
<style>    
    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
    }
</style>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacen" />
                    <div class="input-group">
                        <input type="text" id="txtAlmacen" class="form-control" readonly="" data-req="" />
                        <span class="input-group-addon" onclick="modalAlmacen(event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fecha" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecha');"></i></span>
                    </div>
                </div> 
            </div>
        </div>
        <div class="form-actions">
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'consulta'}">
                    <button id="btnBuscar" class="btn btn-primary" type="submit" title="${opcionSubmenu.nombreOpcion}">Consultar</button>
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

<div id="divStockProductoAFecha" class="row">
    <div class="col-sm-8 col-md-8">
        <table id="tblStockProductos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Producto</th>
                <th>Tipo</th>
                <th>F.F.</th>
                <th>Stock</th>
                </tr>
            </thead>                    
        </table>
    </div>
    <div class="col-sm-4 col-md-4">
        <h3 class="heading">Detalle del Producto</h3>
        <label id="nombreProducto"></label>
        <table id="tblDetalleStockProdutos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>                
                <th>Lote</th>
                <th>F. Vencimiento</th>
                <th>Stock</th>
                </tr>
            </thead>                    
        </table>
    </div>
</div>

<div class="modal fade" id="modalAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Selecionar Almacén</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div class="col-sm-10 col-md-10">
                        <div id="almacenesTreeview"></div>
                    </div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cerrar</button>                
            </div>
        </div>
    </div>
</div>

<script>    
    var tblStockProductos = document.getElementById('tblStockProductos');
    var tblDetalleStockProdutos = document.getElementById('tblDetalleStockProdutos');

    $(document).ready(function (){
        $('#almacenesTreeview').treeview({data: []});
        $('#btnLimpiarBusqueda').click();
        
        $('#fecha').val(new Date().toString(dateFormatJS));
    });    
    
     $('#btnLimpiarBusqueda').click(function (){
        $('#divStockProductoAFecha').attr('style', 'display:none');
        
        var nodeSelect = $('#almacenesTreeview').treeview('getSelected');
        if(nodeSelect.length){
            $('#almacenesTreeview').treeview('unselectNode', nodeSelect[0].nodeId);
        }
        
        $('#btnCerrarModalAlmacen').click();
        $('#fecha').val('');
    });
        
    function modalAlmacen(e) {
        e.preventDefault();

        $('#modalAlmacen').modal('show');

        $.ajax({
            url: '<c:url value="/Almacen/almacenesTree" />',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {

                var treeView = new Array();
                for (var i = 0; i <= dataResponse.length - 1; ++i) {
                    var tree = new Object();
                    tree.text = dataResponse[i].almacen;
                    tree.idAlmacen = dataResponse[i].idAlmacen;
                    tree.selectable = false;
                    tree.nodes = new Array();
                    
                    if (dataResponse[i].almacenes.length > 0) {
                        for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {
                            var treeLevelTwo = new Object();
                            treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                            treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                            treeLevelTwo.parent = dataResponse[i].almacen;
                            treeLevelTwo.nodes = new Array();
                            tree.nodes.push(treeLevelTwo);
                        }
                    }

                    treeView.push(tree);
                }

                $('#almacenesTreeview').treeview('remove');

                $('#almacenesTreeview').treeview({
                    levels: 1,
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    nodeIcon: '',
                    data: treeView,
                    onNodeSelected: function(event, node) {
                        $('#txtAlmacen').val(node.parent + ' - ' + node.text);
                        $('#idAlmacen').val(node.idAlmacen);                        
                    },
                    onNodeUnselected: function(event, node) {                        
                        $('#txtAlmacen').val('');
                        $('#idAlmacen').val('');
                    }
                });

                var idAlmacen = $('#idAlmacen').val();
                if (idAlmacen.length) {
                    $('#almacenesTreeview').treeview('selectByKey', [idAlmacen, 'idAlmacen',{silent:true}]);
                }
            }
        });
    }
    
    function validate(){
        var dataResponse = validateForm('[data-req]');
        
        var fecha = Date.parseExact($('#fecha').val(), dateFormatJS);
        
        if ($('#fecha').val().length > 0) {
            if (fecha == null) {
                dataResponse.mensajesRepuesta.push('Fecha inválida.');
                dataResponse.estado = false;
            }
        }
        
        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return false;
        }
        
        return true;
    }
    
    $('#btnBuscar').click(function(e){
        e.preventDefault();
        
        if(validate() == false){
            return;
        }
        
        var fecha = Date.parseExact($('#fecha').val(), dateFormatJS);
        
        $('#divStockProductoAFecha').removeAttr('style');
        
        if ($.fn.DataTable.fnIsDataTable(tblStockProductos)) {
            $(tblStockProductos).dataTable().fnDestroy();
        }

        $(tblStockProductos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'fecha', "value": fecha.getTime()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
            },
            "sAjaxSource": '<c:url value="/StockProductoFecha/JSON" />',            
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-3'<'#filter'>><'col-sm-9'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 10,
            "fnServerData": function(sSource, aoData, fnCallback, oSettings) {
                oSettings.jqXHR = $.ajax({
                    "dataType": 'json',
                    "type": "GET",
                    "url": sSource,
                    "data": aoData,
                    "success": fnCallback
                }).done(function(data) {
                        $('#filter').html('<span class="help-block">Filtros por: Código Sismed.<span>');
                });
            },
            "aoColumns": [
                {mData: "codigoSismed", "bSortable": false},
                {mData: "descripcion", "bSortable": false},
                {mData: "tipoProducto", "bSortable": false},
                {mData: "formaFarmaceutica", "bSortable": false},                
                {mData: "stock", "bSortable": false}                
            ]
        });
    });
    
    function showDetails(idProducto){
    if ($.fn.DataTable.fnIsDataTable(tblDetalleStockProdutos)) {
           $(tblDetalleStockProdutos).dataTable().fnDestroy();
        }
        var fecha = Date.parseExact($('#fecha').val(), dateFormatJS);
        
        $(tblDetalleStockProdutos).dataTable({
            "fnServerParams": function(aoData) {
                 aoData.push({"name": 'idProducto', "value": idProducto});
                 aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
                 aoData.push({"name": 'fecha', "value": fecha.getTime()})
            },
            "sAjaxSource": '<c:url value="/StockProductoFecha/DetalleAlmacen" />',
            "bServerSide": true,
            "bProcessing": true,
            "bPaginate": false,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [                  
                {mData: "lote", sWidth: "35%", "bSortable": false},
                {mData: "fechaVencimiento", sWidth: "35%", "bSortable": false, "mRender": function(data, type, row) {
                        return new Date(data).toString(dateFormatJS);
                    }
                },
                {mData: "cantidad", sWidth: "35%", "bSortable": false}
            ]
        });
    }
    
    $("#tblStockProductos tbody tr").live("click", function(event){
        var datatable = $(tblStockProductos).dataTable();
        var position = $(tblStockProductos).dataTable().fnGetPosition(this);
        var rowData = datatable.fnGetData(position);
        var idProducto = rowData['idProducto'];
        $('#nombreProducto').text(rowData['descripcion']);
        showDetails(idProducto);
    });
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();
        
        if(validate() == false){
            return;
        }
        
        var dataTable = $(tblStockProductos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/StockProductoFecha/Excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        if(validate() == false){
            return;
        }

        var dataTable = $(tblStockProductos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/StockProductoFecha/PDF"/>', dataTable);
    });
</script>