<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <span>Almacén Padre: ${almacen.descripcion}</span>
    </div>
</div>

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

        <a href="<c:url value="/Almacen" />">Regresar a Almacenes</a>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblAlmacen" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Subalmacen</th>                
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblAlmacen = document.getElementById('tblAlmacen');

    $(document).ready(function() {
        listarAlmacenes();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblAlmacen).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Almacen/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblAlmacen).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Almacen/pdf"/>', dataTable);
    });

    function reloadAlmacenes() {
        var dataTable = $(tblAlmacen).dataTable();
        dataTable.fnReloadAjax();
    }

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/Almacen/registrar" />?idAlmacenPadre=${almacen.idAlmacen}';
    });

    function listarAlmacenes() {

        if ($.fn.DataTable.fnIsDataTable(tblAlmacen)) {
            $(tblAlmacen).dataTable().fnDestroy();
        }

        $(tblAlmacen).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idAlmacenPadre', "value": '<c:out value="${almacen.idAlmacen}"/>'});
            },
            "sAjaxSource": "<c:url value="/Almacen/subalmacenesJSON" />",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[1, 'asc']],
            "aoColumns": [
                {mData: "idAlmacen", sWidth: "8%"},
                {mData: "descripcion", sWidth: "40%"},                
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idAlmacen", sWidth: "21%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var enlaceSubAlmacen = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="<c:url value="/Almacen/modificar/" />' + data + '?idAlmacenPadre=${almacen.idAlmacen}" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil" ></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoAlmacen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarAlmacen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML + enlaceSubAlmacen;
                    }
                }
            ]
        });

    }

    function cambiarEstadoAlmacen(id, e, element) {
        e.preventDefault();

        var almacenTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado del almacen ' + almacenTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Almacen/estado"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadAlmacenes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarAlmacen(id, e, element) {
        e.preventDefault();

        var almacenTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el proveedor ' + almacenTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Almacen/eliminar"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadAlmacenes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>