<%@include file="../includeTagLib.jsp" %>

<style>
    #modalTipoDocumento .modal-dialog
    {
        margin-top: 5%
    }
</style>

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
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblTipoDocumento" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>C?digo</th>
                <th>Tipo de Documento</th>
                <th>Estado</th>
                <th>Acci?n</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalTipoDocumento">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">                    
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Tipo de Documento <span class="f_req">*</span></label>
                            <input type="text" id="nombreTipoDocumento" name="nombreTipoDocumento" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idTipoDocumento" name="idTipoDocumento" />
                        </div>
                    </div>
                    <div id="divMessage"></div>
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

    var tblTipoDocumento = document.getElementById('tblTipoDocumento');

    $(document).ready(function() {
        listarTiposDocumentos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoDocumento).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoDocumento/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoDocumento).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoDocumento/pdf" />', dataTable);
    });

    $('#modalTipoDocumento form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadTiposDocumentos() {
        var dataTable = $(tblTipoDocumento).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarTiposDocumentos() {
        $(tblTipoDocumento).dataTable({
            "sAjaxSource": '<c:url value="/TipoDocumento/tiposDocumentosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idTipoDocumento", sWidth: "14%"},
                {mData: "nombreTipoDocumento", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idTipoDocumento", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
            editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerTipoDocumento(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
            stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoTipoDocumento(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
            deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarTipoDocumento(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>
                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    function obtenerTipoDocumento(id, e, element) {
        e.preventDefault();

        var modalTipoDocumento = $('#modalTipoDocumento');
        modalTipoDocumento.find('form').attr('action', '<c:url value="/TipoDocumento/modificar" />');
        modalTipoDocumento.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalTipoDocumento');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/TipoDocumento/tipoDocumentoJSON"/>/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalTipoDocumento.modal('show');
                
                $('#idTipoDocumento').val(jsonData.idTipoDocumento);
                $('#nombreTipoDocumento').val(jsonData.nombreTipoDocumento);
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

        var modalTipoDocumento = $('#modalTipoDocumento');
        modalTipoDocumento.find('form').attr('action', '<c:url value="/TipoDocumento/registrar" />');
        modalTipoDocumento.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalTipoDocumento');
        $('#idTipoDocumento').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalTipoDocumento.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalTipoDocumento form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalTipoDocumento #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalTipoDocumento #divMessage')) {
                    reloadTiposDocumentos();
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

    function cambiarEstadoTipoDocumento(id, e, element) {
        e.preventDefault();

        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('?Est? seguro que desea cambiar de estado el tipo de documento ' + tipoDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoDocumento/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarTipoDocumento(id, e, element) {
        e.preventDefault();

        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('?Est? seguro que desea eliminar el tipo de documento ' + tipoDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoDocumento/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>