<%@include file="../includeTagLib.jsp" %>

<style>
    #modalTipoDocumentoMov .modal-dialog
    {
        margin-top: 5%
    }
</style>

<h3 class="heading">Mantenimiento de Tipos de Documento de Movimiento</h3>

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
    <div class="col-sm-10 col-md-10">
        <table id="tblTipoDocumentoMov" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Tipo de Documento de Movimiento</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalTipoDocumentoMov">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">
                    <!--<div class="formSep">-->
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Tipo de Documento <span class="f_req">*</span></label>
                            <input type="text" id="nombreTipoDocumentoMov" name="nombreTipoDocumentoMov" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo" /> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idTipoDocumentoMov" name="idTipoDocumentoMov" />
                        </div>
                    </div>
                    <div id="divMessage">

                    </div>
                    <!--</div>-->
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

    var tblTipoDocumentoMov = document.getElementById('tblTipoDocumentoMov');

    $(document).ready(function() {        
        listarTiposDocumentoMov();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoDocumentoMov).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoDocumentoMov).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    $('#modalTipoDocumentoMov form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });
    
    function reloadTiposDocumentoMov(){
        var dataTable = $(tblTipoDocumentoMov).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarTiposDocumentoMov() {

        if ($.fn.DataTable.fnIsDataTable(tblTipoDocumentoMov)) {
            $(tblTipoDocumentoMov).dataTable().fnDestroy();
        }
        
        $(tblTipoDocumentoMov).dataTable({
            "sAjaxSource": '<c:url value="/TipoDocumentoMov/tiposDocumentoMovJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idTipoDocumentoMov", sWidth: "14%"},
                {mData: "nombreTipoDocumentoMov", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idTipoDocumentoMov", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerTipoDocumentoMov(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoTipoDocumentoMov(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarTipoDocumentoMov(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    function obtenerTipoDocumentoMov(id, e, element) {
        e.preventDefault();

        var modalTipoDocumentoMov = $('#modalTipoDocumentoMov');
        modalTipoDocumentoMov.find('form').attr('action', '<c:url value="/TipoDocumentoMov/modificar" />');
        modalTipoDocumentoMov.find('.modal-header .modal-title').html('Editar Tipo de Documento de Movimiento');
        $('#divMessage').html('');
        $('#chkActivo').removeAttr('disabled');

        modalTipoDocumentoMov.modal('show');

        $.ajax({
            url: '<c:url value="/TipoDocumentoMov/tipoDocumentoMovJSON"/>?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#idTipoDocumentoMov').val(jsonData.idTipoDocumentoMov);
                $('#nombreTipoDocumentoMov').val(jsonData.nombreTipoDocumentoMov);
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

        var modalTipoDocumentoMov = $('#modalTipoDocumentoMov');
        modalTipoDocumentoMov.find('form').attr('action', '<c:url value="/TipoDocumentoMov/registrar" />');
        modalTipoDocumentoMov.find('.modal-header .modal-title').html('Registrar Tipo de Documento de Movimiento');
        cleanform('#modalTipoDocumentoMov');
        $('#idTipoDocumentoMov').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalTipoDocumentoMov.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalTipoDocumentoMov form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalTipoDocumentoMov #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalTipoDocumentoMov #divMessage')) {
                    reloadTiposDocumentoMov();
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

    function cambiarEstadoTipoDocumentoMov(id, e, element) {
        e.preventDefault();

        var tipoDocumentoMovTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el tipo de documento de movimiento ' + tipoDocumentoMovTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoDocumentoMov/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposDocumentoMov();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarTipoDocumentoMov(id, e, element) {
        e.preventDefault();

        var tipoDocumentoMovTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el tipo de documento de movimiento ' + tipoDocumentoMovTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoDocumentoMov/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposDocumentoMov();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>