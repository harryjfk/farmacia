<%@include file="../includeTagLib.jsp" %>

<style>
    #modalUbigeo .modal-dialog
    {
        margin-top: 5%
    }
</style>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <span>
            <c:if test="${empty nombreUbigeo}">
                <b>Departamentos</b>
            </c:if>
            <c:if test="${not empty nombreUbigeo}">
                <b><c:if test="${fn:length(param.idUbigeo) == 2}">
                        Provincias de 
                   </c:if>
                   <c:if test="${fn:length(param.idUbigeo) == 4}">
                        Distritos de 
                   </c:if>${nombreUbigeo}</b>
            </c:if>
        </span>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'registrar'}">
                <button id="btnAgregar" class="btn btn-primary">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
        </c:forEach>
        <c:if test="${not empty param.idUbigeo}">
            <c:if test="${fn:length(param.idUbigeo) == 2}">
                <a href="<c:url value="/Ubigeo" />">Regresar a Departamentos</a>
            </c:if>

            <c:if test="${fn:length(param.idUbigeo) == 4}">
                <a href="<c:url value="/Ubigeo" />?idUbigeo=${fn:substring(param.idUbigeo, 0, 2)}">Regresar a Provincias</a>
            </c:if>
        </c:if>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblUbigeo" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Ubigeo</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalUbigeo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">
                    <div class="formSep">
                        <div class="row">                            
                            <div class="col-sm-4 col-md-4">
                                <label>Código <span class="f_req">*</span></label>
                                <input type="text" id="idUbigeo" name="idUbigeo" class="form-control" data-req="" maxlength="6"/>
                            </div>
                            <div class="col-sm-8 col-md-8">
                                <label>Ubigeo <span class="f_req">*</span></label>
                                <input type="text" id="nombreUbigeo" name="nombreUbigeo" class="form-control" data-req="" maxlength="70"/>
                            </div>                            
                        </div>
                    </div>
                    <!--<div class="formSep">-->
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />                                
                        </div>
                    </div>
                    <!--</div>-->
                    <div id="divMessage">

                    </div>                    
                </form>
            </div>
            <div class="modal-footer">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
            </div>
        </div>
    </div>
</div>

<script>

    var tblUbigeo = document.getElementById('tblUbigeo');

    $(document).ready(function() {
        listarUbigeos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblUbigeo).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Ubigeo/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblUbigeo).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Ubigeo/pdf"/>', dataTable);
    });

    $('#modalUbigeo form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadUbigeos() {
        var dataTable = $(tblUbigeo).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarUbigeos() {
        $(tblUbigeo).dataTable({
            "fnServerParams": function(aoData) {               
                <c:if test="${not empty param.idUbigeo}">
                            aoData.push({"name": 'idUbigeo', "value": '<c:out value="${param.idUbigeo}"/>'});
                </c:if>
            },
            "sAjaxSource": '<c:url value="/Ubigeo/ubigeosJSON"/>',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idUbigeo", sWidth: "14%"},
                {mData: "nombreUbigeo", sWidth: "45%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idUbigeo", sWidth: "26%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerUbigeo(\'' + data + '\', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoUbigeo(\'' + data + '\', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarUbigeo(\'' + data + '\', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>

                        var enlaceSubUbigeo = '';
    <c:if test="${empty param.idUbigeo}">
                        enlaceSubUbigeo = '<a href="<c:url value="/Ubigeo" />?idUbigeo=' + data + '" class="separator-icon-td">Ver Provincias</a>';
    </c:if>

    <c:if test="${not empty param.idUbigeo}">
        <c:if test="${fn:length(param.idUbigeo) == 2}">
                        enlaceSubUbigeo = '<a href="<c:url value="/Ubigeo" />?idUbigeo=' + data + '" class="separator-icon-td">Ver Distrito</a>';
        </c:if>
    </c:if>

                        return editHTML + stateHTML + deleteHTML + enlaceSubUbigeo;
                    }
                }
            ]
        });

    }

    function obtenerUbigeo(id, e, element) {
        e.preventDefault();

        var modalUbigeo = $('#modalUbigeo');
        modalUbigeo.find('form').attr('action', 'modificar');
        modalUbigeo.find('.modal-header .modal-title').html('Editar Ubigeo');
        $('#divMessage').html('');
        $('#chkActivo').removeAttr('disabled');

        $('#idUbigeo').attr('readonly', 'readonly');
        modalUbigeo.modal('show');

        $.ajax({
            url: 'ubigeoJSON?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#idUbigeo').val(jsonData.idUbigeo);
                $('#nombreUbigeo').val(jsonData.nombreUbigeo);
                $('#activo').val(jsonData.activo);

                if (jsonData.activo === 1) {
                    $('#chkActivo').prop('checked', true);
                } else {
                    $('#chkActivo').prop('checked', false);
                }
            }
        });
    }

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        var modalUbigeo = $('#modalUbigeo');
        modalUbigeo.find('form').attr('action', 'registrar');
        modalUbigeo.find('.modal-header .modal-title').html('Registrar Ubigeo');
        cleanform('#modalUbigeo');
        $('#idUbigeo').val('');
        $('#idUbigeo').removeAttr('readonly');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalUbigeo.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalUbigeo form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalUbigeo #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalUbigeo #divMessage')) {
                    reloadUbigeos();
                }
            }
        });

        e.preventDefault();
    });

    $('#chkActivo').click(function(e) {
        if ($(this).prop('checked')) {
            $('#activo').val('1');
        } else {
            $('#activo').val('0');
        }
    });

    function cambiarEstadoUbigeo(id, e, element) {
        e.preventDefault();

        var ubigeoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el ubigeo ' + ubigeoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadUbigeos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarUbigeo(id, e, element) {
        e.preventDefault();

        var ubigeoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el ubigeo ' + ubigeoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadUbigeos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>