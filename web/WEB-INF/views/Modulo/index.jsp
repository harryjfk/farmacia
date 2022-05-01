<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalModulo .modal-dialog
    {
        margin-top: 5%
    }
</style>

<%
    List<String> urlsAjax = new ArrayList<String>();
    urlsAjax.add("/Modulo/modulosJSON");
    urlsAjax.add("/Modulo/moduloJSON");
    session.setAttribute("urlsAjax", urlsAjax);
%>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblModulo" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Módulo</th>
                <th>Orden</th>
                <th>Estado</th>
                <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalModulo">
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
                            <label>Modulo <span class="f_req">*</span></label>
                            <input type="text" id="nombreModulo" name="nombreModulo" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idModulo" name="idModulo" />                                
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
    $(document).ready(function() {
        listarModulos();
    });
    
    $('#modalModulo form').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function listarModulos() {

        var tblModulo = document.getElementById('tblModulo');

        if ($.fn.DataTable.fnIsDataTable(tblModulo)) {
            $(tblModulo).dataTable().fnDestroy();
        }
        
         $(tblModulo).dataTable({             
            "sAjaxSource": '<c:url value="/Modulo/modulosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[ 2, 'asc']],
            "aoColumns": [
                {mData: "idModulo", sWidth: "10%"},
                {mData: "nombreModulo", sWidth: "50%"},
                {mData: "orden", sWidth: "16%", mRender: function (data, type, row){
                        var flechaUp = '';
                        var flechaDown = '';
                         <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'orden'}">
                                flechaUp = '<i class="splashy-arrow_small_up" class="separator-icon-td" onclick="cambiarOrden(true, this, event, '+ row.idModulo + ');"></i>';
                                flechaDown = '<i class="splashy-arrow_small_down" class="separator-icon-td" onclick="cambiarOrden(false, this, event, '+ row.idModulo + ');"></i>';
                            </c:if>
                        </c:forEach>
                        
                        var frm = data + '&nbsp;&nbsp;' + flechaUp + flechaDown;
                        
                        return frm;
                    }
                },
                {mData: "activoTexto", sWidth: "10%"},
                {mData: "idModulo", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        
                        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerModulo(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoModulo(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                            </c:if>
                        </c:forEach>
                            
                        return editHTML + stateHTML;
                    }
                }
            ]
        });
    }
    
    function cambiarOrden(subida, element, e, idModulo){
        e.preventDefault();
        
        var navegacionOrden = {
            id: idModulo,
            subida: subida
        };
            
        $.ajax({
            url: '<c:url value="/Modulo/orden" />',            
            data: JSON.stringify(navegacionOrden),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                var f = function() {
                    listarModulos();
                };
                functionResponse(dataResponse, f);
            }
        });
    }

    function obtenerModulo(id, e, element) {
        e.preventDefault();
        
        var modalModulo = $('#modalModulo');
        modalModulo.find('form').attr('action', '<c:url value="/Modulo/modificar" />');
        modalModulo.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalModulo');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/Modulo/moduloJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalModulo.modal('show');
                
                $('#idModulo').val(jsonData.idModulo);
                $('#nombreModulo').val(jsonData.nombreModulo);
                $('#activo').val(jsonData.activo);

                if (jsonData.activo === 1) {
                    $('#chkActivo').prop('checked', true);
                } else {
                    $('#chkActivo').prop('checked', false);
                }
            }
        });
    }

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalModulo form');
        var dataSend = frm.serialize();
        
        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalModulo #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalModulo #divMessage')) {
                    listarModulos();
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

    function cambiarEstadoModulo(id, e, element) {
        e.preventDefault();

        var moduloTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el módulo ' + moduloTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Modulo/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            listarModulos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>