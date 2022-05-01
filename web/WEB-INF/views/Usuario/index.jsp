<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
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
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">

        <table id="tblUsuarios" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Usuario</th>
                <th>Nombre Personal</th>                   
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Unidad</th>
                <th>Cargo</th>
                <th>Perfiles</th>
                <th>Estado</th>                    
                <th>Acción</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>
    $(document).ready(function() {
        listarUsuarios();
    });

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/Usuario/registrar" />';
    });

    function listarUsuarios() {
        var tblUsuarios = document.getElementById('tblUsuarios');

        if ($.fn.DataTable.fnIsDataTable(tblUsuarios)) {
            $(tblUsuarios).dataTable().fnDestroy();
        }

        $(tblUsuarios).dataTable({
            "sAjaxSource": '<c:url value="/Usuario/usuariosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idUsuario", sWidth: "6%", "bSortable": false},
                {mData: "nombreUsuario", sWidth: "10%"},
                {mData: "personal.nombreCompleto", sWidth: "16%"},
                {mData: "personal.tipoDocumento", sWidth: "9%", "bSortable": false},
                {mData: "personal.nroDocumento", sWidth: "9%"},
                {mData: "personal.unidad", sWidth: "13%", "bSortable": false},
                {mData: "personal.cargo", sWidth: "13%", "bSortable": false},
                {mData: "perfiles", sWidth: "8%", "bSortable": false, "mRender": function(data, type, row) {
                        var td = '';
                        for (var i = 0; i <= data.length - 1; ++i) {
                            if (i === data.length - 1) {
                                td += data[i].nombrePerfil;
                            } else {
                                td += data[i].nombrePerfil + ', ';
                            }
                        }
                        return td;
                    }
                },
                {mData: "activoTexto", sWidth: "8%"},
                {mData: "idUsuario", sWidth: "8%", "bSortable": false, "mRender": function(data, type, row) {

                var editHTML = '';
                var stateHTML = '';
                var deleteHTML = '';

                    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                            editHTML = '<a href="<c:url value="/Usuario/modificar" />/' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                        </c:if>
                        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                            stateHTML = '<a href="#" class="separator-icon-td" onclick="cambiarEstadoUsuario(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                        </c:if>
                        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                            deleteHTML = '<a href="#" class="separator-icon-td" onclick="eliminarUsuario(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
                        </c:if>
                    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });            
    }
    
    function cambiarEstadoUsuario(id, e, element) {
        e.preventDefault();

        var usuarioTexto = $(element).parent().parent().find('td:eq(1)').text().trim();
        
        smokeConfirm('¿Está seguro que desea cambiar de estado el usuario ' + usuarioTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Usuario/estado/" />' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            listarUsuarios();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
    
    function eliminarUsuario(id, e, element){
        alert('No está implementado el eliminar.');
    }
</script>