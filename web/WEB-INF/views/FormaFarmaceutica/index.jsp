<%@include file="../includeTagLib.jsp" %>

<style>
    #modalFormaFarmaceutica .modal-dialog
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
        <table id="tblFormaFarmaceutica" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Forma Farmaceutica</th>
                    <th>Abreviatura</th>
                    <th>Estado</th>
                    <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalFormaFarmaceutica">
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
                                <label>Forma Farmaceutica <span class="f_req">*</span></label>
                                <input type="text" id="nombreFormaFarmaceutica" name="nombreFormaFarmaceutica" class="form-control" data-req="" maxlength="70"/>
                            </div> 
                            <div class="col-sm-6 col-md-6">
                                <label>Abreviatura</label>
                                <input type="text" id="abreviatura" name="abreviatura" class="form-control" maxlength="20"/>
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
                                <input type="hidden" id="idFormaFarmaceutica" name="idFormaFarmaceutica" />
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

    var tblFormaFarmaceutica = document.getElementById('tblFormaFarmaceutica');

    $(document).ready(function() {
        listarFormasFarmaceuticas();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblFormaFarmaceutica).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/FormaFarmaceutica/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblFormaFarmaceutica).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/FormaFarmaceutica/pdf" />', dataTable);
    });

    $('#modalFormaFarmaceutica form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadFormasFarmaceuticas() {
        var dataTable = $(tblFormaFarmaceutica).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarFormasFarmaceuticas() {

        if ($.fn.DataTable.fnIsDataTable(tblFormaFarmaceutica)) {
            $(tblFormaFarmaceutica).dataTable().fnDestroy();
        }

        $(tblFormaFarmaceutica).dataTable({
            "sAjaxSource": '<c:url value="/FormaFarmaceutica/formasFarmaceuticasJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idFormaFarmaceutica", sWidth: "14%"},
                {mData: "nombreFormaFarmaceutica", sWidth: "40%"},
                {mData: "abreviatura", sWidth: "17%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idFormaFarmaceutica", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerFormaFarmaceutica(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoFormaFarmaceutica(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarFormaFarmaceutica(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    function obtenerFormaFarmaceutica(id, e, element) {
        e.preventDefault();

        var modalFormaFarmaceutica = $('#modalFormaFarmaceutica');
        modalFormaFarmaceutica.find('form').attr('action', '<c:url value="/FormaFarmaceutica/modificar" />');
        modalFormaFarmaceutica.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalFormaFarmaceutica');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/FormaFarmaceutica/formaFarmaceuticaJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                modalFormaFarmaceutica.modal('show');

                $('#idFormaFarmaceutica').val(jsonData.idFormaFarmaceutica);
                $('#nombreFormaFarmaceutica').val(jsonData.nombreFormaFarmaceutica);
                $('#abreviatura').val(jsonData.abreviatura);
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

        var modalFormaFarmaceutica = $('#modalFormaFarmaceutica');
        modalFormaFarmaceutica.find('form').attr('action', '<c:url value="/FormaFarmaceutica/registrar" />');
        modalFormaFarmaceutica.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalFormaFarmaceutica');
        $('#idFormaFarmaceutica').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalFormaFarmaceutica.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalFormaFarmaceutica form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalFormaFarmaceutica #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalFormaFarmaceutica #divMessage')) {
                    reloadFormasFarmaceuticas();
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

    function cambiarEstadoFormaFarmaceutica(id, e, element) {
        e.preventDefault();

        var formaFarmaceuticaTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado la forma farmaceutica ' + formaFarmaceuticaTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/FormaFarmaceutica/estado" /> /' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadFormasFarmaceuticas();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarFormaFarmaceutica(id, e, element) {
        e.preventDefault();

        var formaFarmaceuticaTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar la forma farmaceutica ' + formaFarmaceuticaTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/FormaFarmaceutica/eliminar"/>/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadFormasFarmaceuticas();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>