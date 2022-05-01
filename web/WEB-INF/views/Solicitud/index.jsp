<%@include file="../includeTagLib.jsp" %>

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
        <button id="btnBuscar" class="btn btn-primary">Buscar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>M&eacute;dico</label>
                    <input type="text" id="medico" name="medico" class="form-control" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-3 col-md-3" style="display:none;">
                    <label>Establecimiento</label>
                    <input type="text" id="establecimiento" name="establecimiento" class="form-control" maxlength="40" autocomplete="off" value="X"/>
                </div>
                <div class="col-sm-4 col-md-4" style="display:none;">
                    <label>Instituci&oacute;n</label>
                    <input type="text" id="institucion" name="institucion" class="form-control" maxlength="40" autocomplete="off" value="X"/>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblSolicitud" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>M&eacute;dico</th>
                <!--
                <th>Establecimiento</th>
                <th>Instituci&oacute;n</th>
                -->
                <th>Fecha</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblSolicitud = document.getElementById('tblSolicitud');

    $(document).ready(function() {
        listarSolicitudes();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSolicitud).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSolicitud).dataTable();
        window.location = getUrlPDF(dataTable);
    });
    
    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="${path}/registrar" />';
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadSolicitudes();
    });
    
    function reloadSolicitudes(){
         var dataTable = $(tblSolicitud).dataTable();
         dataTable.fnReloadAjax();
    }

    function listarSolicitudes() {
        $(tblSolicitud).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'establecimiento', "value": $('#establecimiento').val()});
                aoData.push({"name": 'institucion', "value": $('#institucion').val()});
                aoData.push({"name": 'medico', "value": $('#medico').val()});
            },
            "sAjaxSource": '<c:url value="${path}/solicitudesJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitud", sWidth: "8%"},
                {mData: "medico", sWidth: "23%"},
                //{mData: "establecimiento", sWidth: "20%"},
                //{mData: "institucion", sWidth: "20%"},
                {mData: "fecha", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "activoTexto", sWidth: "8%"},
                {mData: "idSolicitud", sWidth: "25%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML = '<a href="<c:url value="${path}/modificar" />?idSolicitud=' + data + '" class="separator-icon-td"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoSolicitud(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarSolicitud(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    $('#chkActivo').click(function(e) {
        if ($(this).prop('checked')) {
            $('#activo').val('1');
        } else {
            $('#activo').val('0');
        }
    });

    function cambiarEstadoSolicitud(id, e, element) {
        e.preventDefault();

        var solicitudTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado de la solicitud ' + solicitudTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadSolicitudes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarSolicitud(id, e, element) {
        e.preventDefault();

        var solicitudTexto = $(element).parent().parent().find('td:eq(0)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar la solicitud ' + solicitudTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadSolicitudes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>