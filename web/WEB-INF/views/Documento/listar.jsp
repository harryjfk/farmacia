<%@include file="../includeTagLib.jsp" %>

<h3 class="heading">Mantenimiento de Documentos</h3>

<div class="row">
    <div class="col-sm-12 col-md-12">
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
                    <label>Tipo de Documento</label>
                    <select id="tipoDocumento" name="tipoDocumento" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoDocumento" items="${tiposDocumento}">
                            <option value="${tipoDocumento.idTipoDocumento}">${tipoDocumento.nombreTipoDocumento}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Nro de Documento</label>
                    <input type="text" id="nroDocumento" name="nroDocumento" class="form-control" maxlength="50" autocomplete="off"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Tipo de Acción</label>
                    <select id="tipoAccion" name="tipoAccion" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoAccion" items="${tiposAccion}">
                            <option value="${tipoAccion.idTipoAccion}">${tipoAccion.nombreTipoAccion}</option>
                        </c:forEach>
                    </select>                    
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">

        <table id="tblDocumentos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Fecha Documento</th>
                <th>Fecha Salida</th>
                <th>Tipo Acción</th>
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Asunto</th>                   
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>
    var tblDocumentos = document.getElementById('tblDocumentos');

    $(document).ready(function() {        
        listarDocumentos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = 'registrar';
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadDocumentos();
    });

    function reloadDocumentos() {
        var dataTable = $(tblDocumentos).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarDocumentos() {
        $(tblDocumentos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'tipoDocumento', "value": $('#tipoDocumento').val()});
                aoData.push({"name": 'nroDocumento', "value": $('#nroDocumento').val()});
                aoData.push({"name": 'tipoAccion', "value": $('#tipoAccion').val()});
            },
            "sAjaxSource": "documentosJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "fechaDocumento", sWidth: "12%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "fechaSalida", sWidth: "12%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "nombreTipoAccion", sWidth: "12%"},
                {mData: "nombreTipoDocumento", sWidth: "12%"},
                {mData: "nroDocumento", sWidth: "12%", "bSortable": false},
                {mData: "asunto", sWidth: "20%", "bSortable": false},
                {mData: "activoTexto", sWidth: "8%"},
                {mData: "idDocumento", sWidth: "12%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var downloadFile = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML = '<a href="modificar?idDocumento=' + data + '" class="separator-icon-td"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML = '<a href="#" class="separator-icon-td" onclick="cambiarEstadoDocumento(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML = '<a href="#" class="separator-icon-td" onclick="eliminarDocumento(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>
                        if(row.extension !== null){
                            downloadFile = '<a href="<c:url value="/Documento/descargar/" />' + data + '" class="separator-icon-td"><i class="splashy-download" title="Descargar"></i></a>';
                        }
                        
                        return editHTML + stateHTML + deleteHTML + downloadFile;
                    }
                }
            ]
        });
    }

    function cambiarEstadoDocumento(id, e, element) {
        e.preventDefault();

        var nroDocumentoTexto = $(element).parent().parent().find('td:eq(4)').text().trim();
        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(3)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el / la ' + tipoDocumentoTexto + ' Nro. ' + nroDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }


    function eliminarDocumento(id, e, element) {
        e.preventDefault();

        var nroDocumentoTexto = $(element).parent().parent().find('td:eq(4)').text().trim();
        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(3)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el / la ' + tipoDocumentoTexto + ' Nro. ' + nroDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>