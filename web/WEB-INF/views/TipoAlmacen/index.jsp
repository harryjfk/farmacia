<%@include file="../includeTagLib.jsp" %>

<style>
    #modalTipoAlmacen .modal-dialog
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
        <table id="tblTipoAlmacen" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>C�digo</th>
                <th>Tipo de Almacen</th>
                <th>Estado</th>
                <th>Acci�n</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalTipoAlmacen">
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
                            <label>Tipo de Almacen <span class="f_req">*</span></label>
                            <input type="text" id="nombreTipoAlmacen" name="nombreTipoAlmacen" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idTipoAlmacen" name="idTipoAlmacen" />
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

    var tblTipoAlmacen = document.getElementById('tblTipoAlmacen');

    $(document).ready(function() {        
        listarTiposAlmacenes();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoAlmacen).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoAlmacen/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoAlmacen).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TipoAlmacen/pdf" />', dataTable);
    });

    $('#modalTipoAlmacen form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });
    
    function reloadTiposAlmacenes(){
        var dataTable = $(tblTipoAlmacen).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarTiposAlmacenes() {

        if ($.fn.DataTable.fnIsDataTable(tblTipoAlmacen)) {
            $(tblTipoAlmacen).dataTable().fnDestroy();
        }
        
        $(tblTipoAlmacen).dataTable({
            "sAjaxSource": '<c:url value="/TipoAlmacen/tiposAlmacenesJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idTipoAlmacen", sWidth: "14%"},
                {mData: "nombreTipoAlmacen", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idTipoAlmacen", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

                <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                    <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerTipoAlmacen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                    </c:if>
                    <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoTipoAlmacen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                    </c:if>
                    <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarTipoAlmacen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
                    </c:if>
                </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    function obtenerTipoAlmacen(id, e, element) {
        e.preventDefault();

        var modalTipoAlmacen = $('#modalTipoAlmacen');
        modalTipoAlmacen.find('form').attr('action', '<c:url value="/TipoAlmacen/modificar" />');
        modalTipoAlmacen.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalTipoAlmacen');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/TipoAlmacen/tipoAlmacenJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalTipoAlmacen.modal('show');
                
                $('#idTipoAlmacen').val(jsonData.idTipoAlmacen);
                $('#nombreTipoAlmacen').val(jsonData.nombreTipoAlmacen);
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

        var modalTipoAlmacen = $('#modalTipoAlmacen');
        modalTipoAlmacen.find('form').attr('action', '<c:url value="/TipoAlmacen/registrar" />');
        modalTipoAlmacen.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalTipoAlmacen');
        $('#idTipoAlmacen').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalTipoAlmacen.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalTipoAlmacen form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalTipoAlmacen #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalTipoAlmacen #divMessage')) {
                    reloadTiposAlmacenes();
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

    function cambiarEstadoTipoAlmacen(id, e, element) {
        e.preventDefault();

        var tipoAlmacenTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('�Est� seguro que desea cambiar de estado el tipo de almacen ' + tipoAlmacenTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoAlmacen/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposAlmacenes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarTipoAlmacen(id, e, element) {
        e.preventDefault();

        var tipoAlmacenTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('�Est� seguro que desea eliminar el tipo de almacen ' + tipoAlmacenTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/TipoAlmacen/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposAlmacenes();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>