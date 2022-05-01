<%@include file="../includeTagLib.jsp" %>

<style>
    #modalProductoSiga .modal-dialog
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
        <table id="tblProductoSiga" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Producto Siga</th>                    
                    <th>Estado</th>
                    <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalProductoSiga">
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
                            <div class="col-sm-6 col-md-6">
                                <label>Código Siga <span class="f_req">*</span></label>
                                <input type="text" id="codigoSiga" name="codigoSiga" class="form-control" data-req="" maxlength="20"/>
                            </div> 
                            <div class="col-sm-6 col-md-6">
                                <label>Nombre Producto Siga <span class="f_req">*</span></label>
                                <input type="text" id="nombreProductoSiga" name="nombreProductoSiga" class="form-control" data-req="" maxlength="70"/>
                            </div>
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">                        
                            <div class="col-sm-6 col-md-6">
                                <label>Estado <span class="f_req">*</span></label>
                                <div class="separator-form-checkbox"></div>
                                <label class="checkbox-inline">
                                    <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                                </label>
                                <input type="hidden" id="activo" name="activo" />
                                <input type="hidden" id="idProductoSiga" name="idProductoSiga" />
                            </div>
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

    var tblProductoSiga = document.getElementById('tblProductoSiga');

    $(document).ready(function() {
        listarProductosSiga();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductoSiga).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductoSiga/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductoSiga).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductoSiga/pdf" />', dataTable);
    });

    $('#modalProductoSiga form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadProductosSiga() {
        var dataTable = $(tblProductoSiga).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarProductosSiga() {

        if ($.fn.DataTable.fnIsDataTable(tblProductoSiga)) {
            $(tblProductoSiga).dataTable().fnDestroy();
        }

        $(tblProductoSiga).dataTable({
            "sAjaxSource": '<c:url value="/ProductoSiga/productosSigaJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "codigoSiga", sWidth: "14%"},
                {mData: "nombreProductoSiga", sWidth: "40%"},                
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idProductoSiga", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerProductoSiga(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoProductoSiga(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarProductoSiga(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    function obtenerProductoSiga(id, e, element) {
        e.preventDefault();

        var modalProductoSiga = $('#modalProductoSiga');
        modalProductoSiga.find('form').attr('action', '<c:url value="/ProductoSiga/modificar" />');
        modalProductoSiga.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalProductoSiga');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/ProductoSiga/productoSigaJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                modalProductoSiga.modal('show');

                $('#idProductoSiga').val(jsonData.idProductoSiga);
                $('#codigoSiga').val(jsonData.codigoSiga);
                $('#nombreProductoSiga').val(jsonData.nombreProductoSiga);                
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

        var modalProductoSiga = $('#modalProductoSiga');
        modalProductoSiga.find('form').attr('action', '<c:url value="/ProductoSiga/registrar" />');
        modalProductoSiga.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalProductoSiga');
        $('#idProductoSiga').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalProductoSiga.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalProductoSiga form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalProductoSiga #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalProductoSiga #divMessage')) {
                    reloadProductosSiga();
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

    function cambiarEstadoProductoSiga(id, e, element) {
        e.preventDefault();

        var productoSigaTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el producto siga ' + productoSigaTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/ProductoSiga/estado" /> /' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductosSiga();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarProductoSiga(id, e, element) {
        e.preventDefault();

        var productoSigaTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el producto siga ' + productoSigaTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/ProductoSiga/eliminar"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductosSiga();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>