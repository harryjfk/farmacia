<%@include file="../includeTagLib.jsp" %>

<h3 class="heading">Mantenimiento de Proveedores</h3>

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
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblProveedor" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Razón Social</th>
                <th>Ruc</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblProveedor = document.getElementById('tblProveedor');

    $(document).ready(function() {
        listarProveedores();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProveedor).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProveedor).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    function reloadProveedores() {
        var dataTable = $(tblProveedor).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarProveedores() {

        if ($.fn.DataTable.fnIsDataTable(tblProveedor)) {
            $(tblProveedor).dataTable().fnDestroy();
        }

        $(tblProveedor).dataTable({
            "sAjaxSource": "proveedoresJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idProveedor", sWidth: "10%"},
                {mData: "razonSocial", sWidth: "35%"},
                {mData: "ruc", sWidth: "20%", "bSortable": false},
                {mData: "tipoProveedor", sWidth:"13%",
                {mData: "activoTexto", sWidth: "10%"},
                {mData: "idProveedor", sWidth: "12%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="<c:url value="/Proveedor/modificar"/>/' + data + '" class="separator-icon-td"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoProveedor(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarProveedor(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
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

        window.location = '<c:url value="/Proveedor/registrar"/>';
    });

    function cambiarEstadoProveedor(id, e, element) {
        e.preventDefault();

        var proveedorTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el proveedor ' + proveedorTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Proveedor/estado"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProveedores();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarProveedor(id, e, element) {
        e.preventDefault();

        var proveedorTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el proveedor ' + proveedorTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Proveedor/eliminar"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProveedores();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>