<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalDependenciaDestino .modal-dialog
    {
        margin-top: 5%
    }
</style>

<div class="row">
    <div class="col-sm-10 col-md-10">        
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
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
        <table id="tblDependenciaDestino" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>                    
                    <th>Descripción</th>
                    <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalDependenciaDestino">
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
                                <label>Nombre <span class="f_req">*</span></label>
                                <input type="text" id="nombreParametro" name="nombreParametro" class="form-control" data-req="" maxlength="70" readonly="" />
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <label>Descripción <span class="f_req">*</span></label>
                                <input type="text" id="descripcionParametro" name="descripcionParametro" class="form-control" data-req="" maxlength="250"/>
                            </div>
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-6 col-md-6">
                                <label>Valor <span class="f_req">*</span></label>
                                <input type="text" id="valor" name="valor" class="form-control" data-req="" maxlength="250"/>
                                <input type="hidden" id="idParametro" name="idParametro" />
                            </div>                            
                        </div>
                    </div>
                    <div id="divMessage">

                    </div>
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
    var tblDependenciaDestino = document.getElementById('tblDependenciaDestino') ;
        
    $(document).ready(function() {
        listarDependenciaDestino();
    });
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDependenciaDestino).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDependenciaDestino).dataTable();
        window.location = getUrlPDF(dataTable);
    });
    
    $('#modalDependenciaDestino form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });
    
    function reloadDependencias(){
        var dataTable = $(tblDependenciaDestino).dataTable();
        dataTable.fnReloadAjax();
    }
    
    function listarDependenciaDestino(){
        
        if ($.fn.DataTable.fnIsDataTable(tblDependenciaDestino)) {
            $(tblDependenciaDestino).dataTable().fnDestroy();
        }

        $(tblDependenciaDestino).dataTable({
            "sAjaxSource": "<c:url value="/DependenciaDestino/dependenciasJSON"/>",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [                        
                        {mData: "valor", sWidth: "15%", "bSortable": false},
                        {mData: "nombreParametro", sWidth: "15%", "bSortable": false},
                        {mData: "descripcionParametro" , "bSortable": false},
                        {mData: "idParametro", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                                
                                var editHTML = '';
                                
    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                                    editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerDependencia(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
        </c:if>
    </c:forEach>
                                    
                                    return editHTML;
                            }
                        }
                    ]
                });
            }
    
     $('#btnGuardar').click(function(e) {
        var frm = $('#modalDependenciaDestino form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalDependenciaDestino #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalDependenciaDestino #divMessage')) {
                    reloadDependencias();
                }
            }
        });

        e.preventDefault();
    });
    
    function obtenerDependencia(id, e, element) {
        e.preventDefault();

        var modalDependenciaDestino = $('#modalDependenciaDestino');
        modalDependenciaDestino.find('form').attr('action', '<c:url value="/Parametro/modificar" />');
        modalDependenciaDestino.find('.modal-header .modal-title').html('Editar Parámetro');
        $('#divMessage').html('');
        $('#chkActivo').removeAttr('disabled');

        modalDependenciaDestino.modal('show');

        $.ajax({
            url: '<c:url value="/Parametro/parametroJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#idParametro').val(jsonData.idParametro);
                $('#nombreParametro').val(jsonData.nombreParametro);
                $('#descripcionParametro').val(jsonData.descripcionParametro);
                $('#valor').val(jsonData.valor);                
            }
        });
    }
</script>