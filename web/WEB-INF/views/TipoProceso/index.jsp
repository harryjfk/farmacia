<%@include file="../includeTagLib.jsp" %>

<style>
    #modalTipoProceso .modal-dialog
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
        <table id="tblTipoProceso" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Tipo de Proceso</th>
                    <th>Estado</th>
                    <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalTipoProceso">
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
                            <label>Tipo de Proceso <span class="f_req">*</span></label>
                            <input type="text" id="nombreTipoProceso" name="nombreTipoProceso" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo" /> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idTipoProceso" name="idTipoProceso" />
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

    var tblTipoProceso = document.getElementById('tblTipoProceso');

    $(document).ready(function() {
        listarTiposProceso();
    });    

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoProceso).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoProceso/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoProceso).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoProceso/pdf"/>', dataTable);
    });

    $('#modalTipoProceso form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadTiposProceso() {
        var dataTable = $(tblTipoProceso).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarTiposProceso() {

        if ($.fn.DataTable.fnIsDataTable(tblTipoProceso)) {
            $(tblTipoProceso).dataTable().fnDestroy();
        }

        $(tblTipoProceso).dataTable({
            "sAjaxSource": '<c:url value="/TipoProceso/tiposProcesoJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idTipoProceso", sWidth: "14%"},
                {mData: "nombreTipoProceso", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idTipoProceso", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerTipoProceso(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarTipoProceso(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarTipoProceso(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    function obtenerTipoProceso(id, e, element) {
        e.preventDefault();

        var modalTipoProceso = $('#modalTipoProceso');
        modalTipoProceso.find('form').attr('action', '<c:url value="/TipoProceso/modificar" />');
        modalTipoProceso.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalTipoProceso');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/TipoProceso/tipoProcesoJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                modalTipoProceso.modal('show');

                $('#idTipoProceso').val(jsonData.idTipoProceso);
                $('#nombreTipoProceso').val(jsonData.nombreTipoProceso);
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

        var modalTipoProceso = $('#modalTipoProceso');
        modalTipoProceso.find('form').attr('action', '<c:url value="/TipoProceso/registrar" />');
        modalTipoProceso.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalTipoProceso');
        $('#idTipoProceso').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalTipoProceso.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalTipoProceso form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalTipoProceso #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalTipoProceso #divMessage')) {
                    reloadTiposProceso();
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

    function cambiarTipoProceso(id, e, element) {
        e.preventDefault();

        var tipoProcesoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el tipo de proceso ' + tipoProcesoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoProceso/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposProceso();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarTipoProceso(id, e, element) {
        e.preventDefault();

        var tipoProcesoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el tipo de proceso ' + tipoProcesoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoProceso/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposProceso();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>