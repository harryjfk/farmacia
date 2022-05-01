<%@include file="../includeTagLib.jsp" %>

<style>
    #modalProductoSismed .modal-dialog
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
        <table id="tblProductoSismed" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Producto Sismed</th>                    
                    <th>Estado</th>
                    <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalProductoSismed">
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
                                <label>Código Sismed <span class="f_req">*</span></label>
                                <input type="text" id="codigoSismed" name="codigoSismed" class="form-control" data-req="" maxlength="20"/>
                            </div> 
                            <div class="col-sm-6 col-md-6">
                                <label>Nombre Producto Sismed <span class="f_req">*</span></label>
                                <input type="text" id="nombreProductoSismed" name="nombreProductoSismed" class="form-control" data-req="" maxlength="70"/>
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
                                <input type="hidden" id="idProductoSismed" name="idProductoSismed" />
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

    var tblProductoSismed = document.getElementById('tblProductoSismed');

    $(document).ready(function() {
        listarProductosSismed();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductoSismed).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductoSismed/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblProductoSismed).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductoSismed/pdf" />', dataTable);
    });

    $('#modalProductoSismed form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadProductosSismed() {
        var dataTable = $(tblProductoSismed).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarProductosSismed() {

        if ($.fn.DataTable.fnIsDataTable(tblProductoSismed)) {
            $(tblProductoSismed).dataTable().fnDestroy();
        }

        $(tblProductoSismed).dataTable({
            "sAjaxSource": '<c:url value="/ProductoSismed/productosSismedJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "codigoSismed", sWidth: "14%"},
                {mData: "nombreProductoSismed", sWidth: "40%"},                
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idProductoSismed", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerProductoSismed(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoProductoSismed(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarProductoSismed(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    function obtenerProductoSismed(id, e, element) {
        e.preventDefault();

        var modalProductoSismed = $('#modalProductoSismed');
        modalProductoSismed.find('form').attr('action', '<c:url value="/ProductoSismed/modificar" />');
        modalProductoSismed.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalProductoSismed');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/ProductoSismed/productoSismedJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                modalProductoSismed.modal('show');

                $('#idProductoSismed').val(jsonData.idProductoSismed);
                $('#codigoSismed').val(jsonData.codigoSismed);
                $('#nombreProductoSismed').val(jsonData.nombreProductoSismed);                
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

        var modalProductoSismed = $('#modalProductoSismed');
        modalProductoSismed.find('form').attr('action', '<c:url value="/ProductoSismed/registrar" />');
        modalProductoSismed.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalProductoSismed');
        $('#idProductoSismed').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalProductoSismed.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalProductoSismed form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalProductoSismed #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalProductoSismed #divMessage')) {
                    reloadProductosSismed();
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

    function cambiarEstadoProductoSismed(id, e, element) {
        e.preventDefault();

        var productoSismedTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el producto sismed ' + productoSismedTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/ProductoSismed/estado" /> /' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductosSismed();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarProductoSismed(id, e, element) {
        e.preventDefault();

        var productoSismedTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el producto sismed ' + productoSismedTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/ProductoSismed/eliminar"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadProductosSismed();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>