<%@include file="../includeTagLib.jsp" %>

<style>
    #modalTipoProducto .modal-dialog
    {
        margin-top: 5%
    }
</style>

<h3 class="heading">Mantenimiento de Tipos de Producto</h3>

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
        <table id="tblTipoProducto" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Tipo de Producto</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalTipoProducto">
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
                            <label>Tipo de Producto <span class="f_req">*</span></label>
                            <input type="text" id="nombreTipoProducto" name="nombreTipoProducto" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idTipoProducto" name="idTipoProducto" />
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

    var tblTipoProducto = document.getElementById('tblTipoProducto');

    $(document).ready(function() {        
        listarTiposProducto();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoProducto).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblTipoProducto).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    $('#modalTipoProducto form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });
    
    function reloadTiposProducto(){
        var dataTable = $(tblTipoProducto).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarTiposProducto() {

        if ($.fn.DataTable.fnIsDataTable(tblTipoProducto)) {
            $(tblTipoProducto).dataTable().fnDestroy();
        }
        
        $(tblTipoProducto).dataTable({
            "sAjaxSource": "tiposProductoJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idTipoProducto", sWidth: "14%"},
                {mData: "nombreTipoProducto", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idTipoProducto", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerTipoProducto(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoTipoProducto(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';
        </c:if>
    </c:forEach>

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarTipoProducto(' + data + ', event, this)"><i class="splashy-remove" title="Eliminar"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    function obtenerTipoProducto(id, e, element) {
        e.preventDefault();

        var modalTipoProducto = $('#modalTipoProducto');
        modalTipoProducto.find('form').attr('action', 'modificar');
        modalTipoProducto.find('.modal-header .modal-title').html('Editar Tipo de Producto');
        $('#divMessage').html('');
        $('#chkActivo').removeAttr('disabled');

        modalTipoProducto.modal('show');

        $.ajax({
            url: 'tipoProductoJSON?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#idTipoProducto').val(jsonData.idTipoProducto);
                $('#nombreTipoProducto').val(jsonData.nombreTipoProducto);
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

        var modalTipoProducto = $('#modalTipoProducto');
        modalTipoProducto.find('form').attr('action', 'registrar');
        modalTipoProducto.find('.modal-header .modal-title').html('Registrar Tipo de Producto');
        cleanform('#modalTipoProducto');
        $('#idTipoProducto').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalTipoProducto.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalTipoProducto form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalTipoProducto #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalTipoProducto #divMessage')) {
                    reloadTiposProducto();
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

    function cambiarEstadoTipoProducto(id, e, element) {
        e.preventDefault();

        var tipoProductoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el tipo de producto ' + tipoProductoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposProducto();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarTipoProducto(id, e, element) {
        e.preventDefault();

        var tipoProductoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el tipo de producto ' + tipoProductoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadTiposProducto();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>