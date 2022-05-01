<%@include file="../includeTagLib.jsp" %>

<h3 class="heading">Mantenimiento de Almacenes
    <c:if test="${not empty param.idAlmacenPadre}">
        - Subalmances de ${nombreAlmacenPadre}
    </c:if>
</h3>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'registrar'}">
                <button id="btnAgregar" class="btn btn-primary">Agregar</button>
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
        <c:if test="${not empty param.idAlmacenPadre}">
            <a href="<c:url value="/Almacen/listar" />">Regresar a Almacenes</a>
        </c:if>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblAlmacen" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Almacen</th>
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
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblAlmacen).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    function reloadAlmacenes() {
        var dataTable = $(tblAlmacen).dataTable();
        dataTable.fnReloadAjax();
    }

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/Almacen/registrar" /><c:if test="${not empty param.idAlmacenPadre}">?idAlmacenPadre=${param.idAlmacenPadre}</c:if>';
            });

            function listarAlmacenes() {

                if ($.fn.DataTable.fnIsDataTable(tblAlmacen)) {
                    $(tblAlmacen).dataTable().fnDestroy();
                }

                $(tblAlmacen).dataTable({
                    "fnServerParams": function(aoData) {
    <c:if test="${not empty param.idAlmacenPadre}">
                        aoData.push({"name": 'idAlmacenPadre', "value": '<c:out value="${param.idAlmacenPadre}"/>'});
    </c:if>
                    },
                    "sAjaxSource": "almacenesJSON",
                    "bServerSide": true,
                    "bProcessing": true,
                    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "bAutoWidth": false,
                    "aoColumns": [
                        {mData: "idAlmacen", sWidth: "14%"},
                        {mData: "descripcion", sWidth: "50%"},
                        {mData: "activoTexto", sWidth: "15%"},
                        {mData: "idAlmacen", sWidth: "21%", "bSortable": false, "mRender": function(data, type, row) {

                                var editHTML = '';
                                var stateHTML = '';
                                var deleteHTML = '';

                                var enlaceSubAlmacen = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML += '<a href="<c:url value="/Almacen/modificar/" />' + data + '" class="separator-icon-td"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoAlmacen(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                                deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarAlmacen(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>

    <c:if test="${empty param.idAlmacenPadre}">
                                enlaceSubAlmacen = '<a href="<c:url value="/Almacen/listar" />?idAlmacenPadre=' + data + '" class="separator-icon-td">Ver Subalmacenes</a>';
    </c:if>

                                return editHTML + stateHTML + deleteHTML + enlaceSubAlmacen;
                            }
                        }
                    ]
                });

            }

            function cambiarEstadoAlmacen(id, e, element) {
                e.preventDefault();

                var almacenTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

                smoke.confirm('¿Está seguro que desea cambiar de estado del almacen ' + almacenTexto + '?', function(e) {
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

                smoke.confirm('¿Está seguro que desea eliminar el proveedor ' + almacenTexto + '?', function(e) {
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