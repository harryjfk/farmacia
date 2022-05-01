<%@include file="../includeTagLib.jsp" %>

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
                <div class="col-sm-6 col-md-6">
                    <label>Descripción</label>
                    <input type="text" id="descripcion" name="descripcion" class="form-control" maxlength="100"/>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Forma Farmaceutica</label>
                    <select id="idFormaFarmaceutica" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="formaFarmaceutica" items="${formasFarmaceuticas}">                                
                            <option value="${formaFarmaceutica.idFormaFarmaceutica}">${formaFarmaceutica.nombreFormaFarmaceutica}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Tipo de Producto</label>
                    <select id="idTipoProducto" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoProducto" items="${tiposProducto}">                                
                            <option value="${tipoProducto.idTipoProducto}">${tipoProducto.nombreTipoProducto}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Unidad de Medida</label>
                    <select id="idUnidadMedida" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="unidadMedida" items="${unidadesMedida}">                                
                            <option value="${unidadMedida.idUnidadMedida}">${unidadMedida.nombreUnidadMedida}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Estr. Sop.</label>
                    <select id="estrSop" class="form-control">
                        <option value="2">-TODOS-</option>
                        <option value="1">SÍ</option>
                        <option value="0">NO</option>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Estr. Vta.</label>
                    <select id="estrVta" class="form-control">
                        <option value="2">-TODOS-</option>
                        <option value="1">SÍ</option>
                        <option value="0">NO</option>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Tra. Nac.</label>
                    <select id="traNac" class="form-control">
                        <option value="2">-TODOS-</option>
                        <option value="1">SÍ</option>
                        <option value="0">NO</option>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Petitorio Institucional</label>
                    <select id="traLoc" class="form-control">
                        <option value="2">-TODOS-</option>
                        <option value="1">SÍ</option>
                        <option value="0">NO</option>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Narcótico</label>
                    <select id="narcotico" class="form-control">
                        <option value="2">-TODOS-</option>
                        <option value="1">SÍ</option>
                        <option value="0">NO</option>
                    </select>
                </div>                
            </div>
        </div>
    </div>        
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblProducto" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Descripción</th>
                <th>Presentación</th>
                <th>Concentración</th>
                <th>Forma Farmacéutica</th>
                <th>Tipo de Producto</th>
                <th>Unidad de Medida</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblProducto = document.getElementById('tblProducto');

    $(document).ready(function() {
        listarProductos();
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadProductos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProducto).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Producto/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProducto).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Producto/pdf" />', dataTable);
    });

    function reloadProductos() {
        var dataTable = $(tblProducto).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarProductos() {

        if ($.fn.DataTable.fnIsDataTable(tblProducto)) {
            $(tblProducto).dataTable().fnDestroy();
        }

        $(tblProducto).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'descripcion', "value": $('#descripcion').val()});
                aoData.push({"name": 'presentacion', "value": $('#presentacion').val()});
                aoData.push({"name": 'concentracion', "value": $('#concentracion').val()});
                aoData.push({"name": 'idFormaFarmaceutica', "value": $('#idFormaFarmaceutica').val()});
                aoData.push({"name": 'idTipoProducto', "value": $('#idTipoProducto').val()});
                aoData.push({"name": 'idUnidadMedida', "value": $('#idUnidadMedida').val()});
                aoData.push({"name": 'estrSop', "value": $('#estrSop').val()});

                aoData.push({"name": 'estrVta', "value": $('#estrVta').val()});
                aoData.push({"name": 'traNac', "value": $('#traNac').val()});
                aoData.push({"name": 'traLoc', "value": $('#traLoc').val()});
                aoData.push({"name": 'narcotico', "value": $('#narcotico').val()});

            },
            "sAjaxSource": '<c:url value="/Producto/productosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "descripcion", sWidth: "20%"},
                {mData: "presentacion", sWidth: "20%", "bSortable": false},
                {mData: "concentracion", sWidth: "10%", "bSortable": false},
                {mData: "formaFarmaceutica", sWidth: "10%", "bSortable": false},
                {mData: "tipoProducto", sWidth: "10%", "bSortable": false},
                {mData: "unidadMedida", sWidth: "10%", "bSortable": false},
                {mData: "activoTexto", sWidth: "10%", "bSortable": false},
                {mData: "idProducto", sWidth: "10", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

                <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                    <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="<c:url value="/Producto/modificar" />/' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                    </c:if>
                        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoProducto(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                    </c:if>
                    <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarProducto(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
                    </c:if>
                </c:forEach>
                    
                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/Producto/registrar" />';
    });

    function cambiarEstadoProducto(id, e, element) {
        e.preventDefault();

        var productoTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el producto ' + productoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Producto/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarProducto(id, e, element) {
        e.preventDefault();

        var productoTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el producto ' + productoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Producto/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>