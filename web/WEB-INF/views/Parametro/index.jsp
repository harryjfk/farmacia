<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalParametro .modal-dialog
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
        <table id="tblParametro" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Parámetro</th>
                    <th>Descripción</th>
                    <th>Valor</th>
                    <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalParametro">
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
                                <label>Parámetro <span class="f_req">*</span></label>
                                <input type="text" id="nombreParametro" name="nombreParametro" class="form-control" data-req="" maxlength="70"/>
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
    var tblParametro = document.getElementById('tblParametro') ;
        
    $(document).ready(function() {
        listarParametros();
    });
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblParametro).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Parametro/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblParametro).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Parametro/pdf"/>', dataTable);
    });
    
    $('#modalParametro form').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });
    
    function reloadParametros(){
        var dataTable = $(tblParametro).dataTable();
        dataTable.fnReloadAjax();
    }
    
    function listarParametros(){
        
        if ($.fn.DataTable.fnIsDataTable(tblParametro)) {
            $(tblParametro).dataTable().fnDestroy();
        }

        $(tblParametro).dataTable({
            "sAjaxSource": "<c:url value="/Parametro/parametrosJSON"/>",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                        {mData: "idParametro", sWidth: "10%"},
                        {mData: "nombreParametro", sWidth: "15%"},
                        {mData: "descripcionParametro"},
                        {mData: "valor", sWidth: "15%", "bSortable": false},
                        {mData: "idParametro", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                                
                                var editHTML = '';
                                
                                <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                                    <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerParametro(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                                    </c:if>
                                </c:forEach>
                                    
                                    return editHTML;
                            }
                        }
                    ]
                });
            }
    
    function obtenerParametro(id, e, element) {
        e.preventDefault();

        var modalParametro = $('#modalParametro');
        modalParametro.find('form').attr('action', '<c:url value="/Parametro/modificar" />');
        modalParametro.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalParametro');
        $('#nombreParametro').attr('readonly', 'readonly');
        
        $.ajax({
            url: '<c:url value="/Parametro/parametroJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalParametro.modal('show');
        
                $('#idParametro').val(jsonData.idParametro);
                $('#nombreParametro').val(jsonData.nombreParametro);
                $('#descripcionParametro').val(jsonData.descripcionParametro);
                $('#valor').val(jsonData.valor);                
            }
        });
    }
        
    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        var modalParametro = $('#modalParametro');
        modalParametro.find('form').attr('action', '<c:url value="/Parametro/registrar" />');
        modalParametro.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalParametro');
        
        $('#idParametro').val('0');
        $('#nombreParametro').removeAttr('readonly');
        modalParametro.modal('show');
    });
    
     $('#btnGuardar').click(function(e) {
        var frm = $('#modalParametro form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body input[type="text"][data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalParametro #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalParametro #divMessage')) {
                    reloadParametros();
                }
            }
        });

        e.preventDefault();
    });
</script>