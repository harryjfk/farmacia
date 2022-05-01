<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@include file="../includeTagLib.jsp" %>

<style>
    #modalSubmenu .modal-dialog
    {
        margin-top: 5%;
        width: 60%;
    }
</style>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Módulo</label>
                    <select id="selModulo" class="form-control">
                        <c:forEach var="modulo" items="${modulos}">
                            <option value="${modulo.idModulo}">${modulo.nombreModulo}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Submodulo</label>
                    <select id="selSubmodulo" class="form-control">                        
                    </select>
                </div>
            </div>            
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Menú</label>
                    <select id="selMenu" class="form-control">                        
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblSubmenu" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Submenú</th>
                <th>Orden</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalSubmenu">
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
                                <label>Módulo <span class="f_req">*</span></label>
                                <input type="text" id="nombreModulo" class="form-control" data-req="" disabled=""/>
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <label>Submodulo <span class="f_req">*</span></label>
                                <input type="text" id="nombreSubmodulo" class="form-control" data-req="" disabled=""/>
                            </div>                        
                        </div>
                    </div>
                    <div class="formSep">
                        <div class="row">
                            <div class="col-sm-6 col-md-6">
                                <label>Menú <span class="f_req">*</span></label>
                                <input type="text" id="nombreMenu" class="form-control" data-req="" disabled=""/>
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <label>Submenu <span class="f_req">*</span></label>
                                <input type="text" id="nombreSubmenu" name="nombreSubmenu" class="form-control" data-req="" maxlength="70"/>
                            </div>                        
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-2 col-md-2">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idSubmenu" name="idSubmenu" />
                            <input type="hidden" id="idMenu" name="idMenu" />
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
        $('#selModulo').change();
    });

    $('#selModulo').change(function() {
        var Data = {"id": "", "value": "idSubmodulo", "text": "nombreSubmodulo"};

        llenarSelect('#selSubmodulo', '<c:url value="/Submodulo/submodulosPorModuloJSON" />?idModulo=' + $('#selModulo').val(), Data, function() {
            $('#selSubmodulo').change();
        });
    });

    $('#selSubmodulo').change(function() {
        var Data = {"id": "", "value": "idMenu", "text": "nombreMenu"};

        llenarSelect('#selMenu', '<c:url value="/Menu/menusPorSubmoduloJSON" />?idSubmodulo=' + $('#selSubmodulo').val(), Data, function() {
            $('#selMenu').change();
        });
    });

    $('#selMenu').change(function() {
        listarSubmenus();
    });
    
    $('#modalSubmenu form').keypress(function(e) {
        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function listarSubmenus() {
        var tblSubmenu = document.getElementById('tblSubmenu');

        if ($.fn.DataTable.fnIsDataTable(tblSubmenu)) {
            $(tblSubmenu).dataTable().fnDestroy();
        }
        
        $(tblSubmenu).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idMenu', "value": $('#selMenu').val()});
            },
            "sAjaxSource": '<c:url value="/Submenu/submenusJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[ 2, 'asc']],
            "aoColumns": [
                {mData: "idSubmenu", sWidth: "10%"},
                {mData: "nombreSubmenu", sWidth: "50%"},
                {mData: "orden", sWidth: "16%", mRender: function (data, type, row){
                        var flechaUp = '';
                        var flechaDown = '';
                         <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'orden'}">
                                flechaUp = '<i class="splashy-arrow_small_up" class="separator-icon-td" onclick="cambiarOrden(true, this, event, '+ row.idSubmenu + ');"></i>';
                                flechaDown = '<i class="splashy-arrow_small_down" class="separator-icon-td" onclick="cambiarOrden(false, this, event, '+ row.idSubmenu + ');"></i>';
                            </c:if>
                        </c:forEach>
                        
                        var frm = data + '&nbsp;&nbsp;' + flechaUp + flechaDown;
                        
                        return frm;
                    }
                },
                {mData: "activoTexto", sWidth: "10%"},
                {mData: "idSubmenu", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {
                         var editHTML = '';
                        var stateHTML = '';
                        
                        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerSubmenu(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoSubmenu(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                            </c:if>
                        </c:forEach>
                            
                        return editHTML + stateHTML;                        
                    }
                }
            ]
        });            
    }
    
    function cambiarOrden(subida, element, e, idSubmenu){
        e.preventDefault();
        
        var navegacionOrden = {
            id: idSubmenu,
            subida: subida
        };
            
        $.ajax({
            url: '<c:url value="/Submenu/orden" />',            
            data: JSON.stringify(navegacionOrden),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function (dataResponse) {
                var f = function() {
                    listarSubmenus();
                };
                functionResponse(dataResponse, f);
            }
        });
    }

    function obtenerSubmenu(id, e, element) {
        e.preventDefault();

        var modalSubmenu = $('#modalSubmenu');
        modalSubmenu.find('form').attr('action', '<c:url value="/Submenu/modificar" />');
        modalSubmenu.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalSubmenu');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/Submenu/submenuJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
                modalSubmenu.modal('show');
                
                $('#nombreModulo').val(jsonData.nombreModulo);
                $('#nombreSubmodulo').val(jsonData.nombreSubmodulo);
                $('#nombreMenu').val(jsonData.nombreMenu);

                $('#idMenu').val(jsonData.submenu.idMenu);

                $('#idSubmenu').val(jsonData.submenu.idSubmenu);
                $('#nombreSubmenu').val(jsonData.submenu.nombreSubmenu);
                $('#activo').val(jsonData.submenu.activo);

                if (jsonData.submenu.activo === 1) {
                    $('#chkActivo').prop('checked', true);
                } else {
                    $('#chkActivo').prop('checked', false);
                }
            }
        });
    }
    
    $('#btnGuardar').click(function(e) {
        var frm = $('#modalSubmenu form');
        var dataSend = frm.serialize();

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalSubmenu #divMessage')) {
                    listarSubmenus();
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

    function cambiarEstadoSubmenu(id, e, element) {
        e.preventDefault();

        var submenuTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el submenu ' + submenuTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Submenu/estado/" />' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            listarSubmenus();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>