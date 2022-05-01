<%@page import="pe.gob.minsa.farmacia.domain.Opcion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pe.gob.minsa.farmacia.services.impl.UsuarioService"%>
<%@page import="pe.gob.minsa.farmacia.util.InterceptorSecurity"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalPerfil .modal-dialog
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
        <table id="tblPerfil" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Perfil</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalPerfil">
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
                            <label>Perfil <span class="f_req">*</span></label>
                            <input type="text" id="nombrePerfil" name="nombrePerfil" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idPerfil" name="idPerfil" />
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

    var tblPerfil = document.getElementById('tblPerfil');

    $(document).ready(function() {
        listarPerfiles();
    });
    
    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblPerfil).dataTable();        
        window.location = getUrlFromDatatables('<c:url value="/Perfil/pdf" />', dataTable);
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblPerfil).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Perfil/excel" />', dataTable);
    });

    $('#modalPerfil form').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadPerfiles() {
        var dataTable = $(tblPerfil).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarPerfiles() {
        
        if ($.fn.DataTable.fnIsDataTable(tblPerfil)) {
            $(tblPerfil).dataTable().fnDestroy();
        }
        
        $(tblPerfil).dataTable({
            "sAjaxSource": '<c:url value="/Perfil/perfilesJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idPerfil", sWidth: "14%"},
                {mData: "nombrePerfil", sWidth: "57%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idPerfil", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerPerfil(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoPerfil(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                                deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarPerfil(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
                            </c:if>
                        </c:forEach>
                            
                        return editHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });
    }

    function obtenerPerfil(id, e, element) {
        e.preventDefault();

        var modalPerfil = $('#modalPerfil');
        modalPerfil.find('form').attr('action', '<c:url value="/Perfil/modificar" />');
        modalPerfil.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalPerfil');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/Perfil/perfilJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalPerfil.modal('show');
                
                $('#idPerfil').val(jsonData.idPerfil);
                $('#nombrePerfil').val(jsonData.nombrePerfil);
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

        var modalPerfil = $('#modalPerfil');
        modalPerfil.find('form').attr('action', '<c:url value="/Perfil/registrar" />');
        modalPerfil.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalPerfil');
        $('#idPerfil').val('0');
        $('#activo').val('0');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalPerfil.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalPerfil form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalPerfil #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {
                if (jsonToDivError(dataResponse, '#modalPerfil #divMessage')) {
                    reloadPerfiles();
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

    function cambiarEstadoPerfil(id, e, element) {
        e.preventDefault();

        var perfilTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el perfil ' + perfilTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Perfil/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadPerfiles();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarPerfil(id, e, element) {
        e.preventDefault();

        var perfilTexto = $(element).parent().parent().find('td:eq(1)').text().trim();
        
        smokeConfirm('¿Está seguro que desea eliminar el perfil ' + perfilTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Perfil/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadPerfiles();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>