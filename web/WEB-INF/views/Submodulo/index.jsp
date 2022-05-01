<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalSubmodulo .modal-dialog
    {
        margin-top: 5%;
        width: 70%;
    }
</style>

<%
    List<String> urlsAjax = new ArrayList<String>();
    urlsAjax.add("/Submodulo/submodulosJSON");
    urlsAjax.add("/Submodulo/submoduloJSON");
    session.setAttribute("urlsAjax", urlsAjax);
%>

<div class="row">
    <div class="col-sm-8 col-md-8">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Módulo</label>
                    <select id="selModulo" class="form-control">
                        <c:forEach var="modulo" items="${modulos}">
                            <option value="${modulo.idModulo}">${modulo.nombreModulo}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>            
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-8 col-md-8">
        <table id="tblSubmodulos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Submódulo</th>
                <th>Orden</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalSubmodulo">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <form method="post" class="form_validation_reg" autocomplete="off">
                    <div class="row">
                        <div class="col-sm-4 col-md-4">
                            <label>Módulo <span class="f_req">*</span></label>
                            <input type="text" id="nombreModulo" class="form-control" data-req="" disabled=""/>
                        </div>
                        <div class="col-sm-5 col-md-5">
                            <label>Submodulo <span class="f_req">*</span></label>
                            <input type="text" id="nombreSubmodulo" name="nombreSubmodulo" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-2 col-md-2">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idModulo" name="idModulo" />
                            <input type="hidden" id="idSubmodulo" name="idSubmodulo" />
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
        listarSubmodulos();
    });

    $('#selModulo').change(function() {
        listarSubmodulos();
    });
    
    $('#modalSubmodulo form').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function listarSubmodulos() {
        var tblSubmodulos = document.getElementById('tblSubmodulos');

        if ($.fn.DataTable.fnIsDataTable(tblSubmodulos)) {
            $(tblSubmodulos).dataTable().fnDestroy();
        }
        
        $(tblSubmodulos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idModulo', "value": $('#selModulo').val()});
            },
            "sAjaxSource": '<c:url value="/Submodulo/submodulosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[ 2, 'asc']],
            "aoColumns": [
                {mData: "idSubmodulo", sWidth: "10%"},
                {mData: "nombreSubmodulo", sWidth: "50%"},
                {mData: "orden", sWidth: "16%", mRender: function (data, type, row){
                        var flechaUp = '';
                        var flechaDown = '';
                         <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'orden'}">
                                flechaUp = '<i class="splashy-arrow_small_up" class="separator-icon-td" onclick="cambiarOrden(true, this, event, '+ row.idSubmodulo + ');"></i>';
                                flechaDown = '<i class="splashy-arrow_small_down" class="separator-icon-td" onclick="cambiarOrden(false, this, event, '+ row.idSubmodulo + ');"></i>';
                            </c:if>
                        </c:forEach>
                        
                        var frm = data + '&nbsp;&nbsp;' + flechaUp + flechaDown;
                        
                        return frm;
                    }
                },
                {mData: "activoTexto", sWidth: "10%"},
                {mData: "idSubmodulo", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {
                        var editHTML = '';
                        var stateHTML = '';
                        
                        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerSubmodulo(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoSubmodulo(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                            </c:if>
                        </c:forEach>

                        return editHTML + stateHTML;
                    }
                }
            ]
        });            
    }
    
    function cambiarOrden(subida, element, e, idSubmodulo){
        e.preventDefault();
        
        var navegacionOrden = {
            id: idSubmodulo,
            subida: subida
        };
            
        $.ajax({
            url: '<c:url value="/Submodulo/orden" />',            
            data: JSON.stringify(navegacionOrden),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                var f = function() {
                    listarSubmodulos();
                };
                functionResponse(dataResponse, f);
            }
        });
    }

    function obtenerSubmodulo(id, e, element) {
        e.preventDefault();

        var modalSubmodulo = $('#modalSubmodulo');
        modalSubmodulo.find('form').attr('action', '<c:url value="/Submodulo/modificar" />');
        modalSubmodulo.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalSubmodulo');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/Submodulo/submoduloJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalSubmodulo.modal('show');
                
                $('#nombreModulo').val(jsonData.nombreModulo);                
                $('#idModulo').val(jsonData.submodulo.idModulo);
                
                $('#idSubmodulo').val(jsonData.submodulo.idSubmodulo);                
                $('#nombreSubmodulo').val(jsonData.submodulo.nombreSubmodulo);
                $('#activo').val(jsonData.submodulo.activo);

                if (jsonData.submodulo.activo === 1) {
                    $('#chkActivo').prop('checked', true);
                } else {
                    $('#chkActivo').prop('checked', false);
                }
            }
        });
    }
    
    $('#btnGuardar').click(function(e) {
        var frm = $('#modalSubmodulo form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalSubmodulo #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalSubmodulo #divMessage')) {
                    listarSubmodulos();
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
    
    function cambiarEstadoSubmodulo(id, e, element){
        e.preventDefault();

        var submoduloTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

       smokeConfirm('¿Está seguro que desea cambiar de estado el submódulo ' + submoduloTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Submodulo/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            listarSubmodulos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>