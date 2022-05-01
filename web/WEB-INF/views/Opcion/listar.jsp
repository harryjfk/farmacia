<%@include file="../includeTagLib.jsp" %>

<style>
    #modalOpcion .modal-dialog
    {
        margin-top: 5%;
        width: 60%;
    }
</style>

<h3 class="heading">Mantenimiento de Opciones</h3>

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
                <div class="col-sm-6 col-md-6">
                    <label>Submenu</label>
                    <select id="selSubmenu" class="form-control">
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblOpcion" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Opción</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalOpcion">
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
                                <input type="text" id="nombreSubmenu" class="form-control" data-req="" disabled=""/>
                            </div>                        
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Opción <span class="f_req">*</span></label>
                            <input type="text" id="nombreOpcion" name="nombreOpcion" class="form-control" data-req="" maxlength="30"/>
                        </div>
                        <div class="col-sm-2 col-md-2">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo"> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idSubmenu" name="idSubmenu" />
                            <input type="hidden" id="idOpcion" name="idOpcion" />
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
    $(document).ready(function() {
        $('#selModulo').change();
    });

    $('#selModulo').change(function() {
        var Data = {"id": "", "value": "idSubmodulo", "text": "nombreSubmodulo"};

        llenarSelect('#selSubmodulo', 'submodulosPorModuloJSON?idModulo=' + $('#selModulo').val(), Data, function() {
            $('#selSubmodulo').change();
        });
    });

    $('#selSubmodulo').change(function() {
        var Data = {"id": "", "value": "idMenu", "text": "nombreMenu"};

        llenarSelect('#selMenu', 'menusPorSubmoduloJSON?idSubmodulo=' + $('#selSubmodulo').val(), Data, function() {
            $('#selMenu').change();
        });
    });

    $('#selMenu').change(function() {
        var Data = {"id": "", "value": "idSubmenu", "text": "nombreSubmenu"};

        llenarSelect('#selSubmenu', 'submenusPorMenuJSON?idMenu=' + $('#selMenu').val(), Data, function() {
            $('#selSubmenu').change();
        });
    });

    $('#selSubmenu').change(function() {
        listarOpciones();
    });

    function listarOpciones() {
        var tblOpcion = document.getElementById('tblOpcion');

        if ($.fn.DataTable.fnIsDataTable(tblOpcion)) {
            $(tblOpcion).dataTable().fnDestroy();
        }

        $.ajax({
            url: 'opcionesJSON?idSubmenu=' + $('#selSubmenu').val(),
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $(tblOpcion).dataTable({
                    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "idOpcion", sWidth: "15%"},
                        {mData: "nombreOpcion", sWidth: "60%"},
                        {mData: "activoTexto", sWidth: "15%"},
                        {mData: "idOpcion", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                                var td = '';
                                var editHTML = '<a href="#" class="separator-icon-td" onclick="obtenerOpcion(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
                                editHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoOpcion(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';

                                td = td + editHTML;

                                return td;
                            }
                        }
                    ]
                });
            }
        });
    }

    function obtenerOpcion(id, e, element) {
        e.preventDefault();

        var modalOpcion = $('#modalOpcion');
        modalOpcion.find('form').attr('action', 'modificar');
        modalOpcion.find('.modal-header .modal-title').html('Editar Opción');
        cleanform('#modalOpcion');
        $('#chkActivo').removeAttr('disabled');

        modalOpcion.modal('show');

        $.ajax({
            url: 'opcionJSON?idOpcion=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $('#nombreModulo').val(jsonData.nombreModulo);
                $('#nombreSubmodulo').val(jsonData.nombreSubmodulo);
                $('#nombreMenu').val(jsonData.nombreMenu);
                $('#nombreSubmenu').val(jsonData.nombreSubmenu);

                $('#idSubmenu').val(jsonData.opcion.idSubmenu);

                $('#idOpcion').val(jsonData.opcion.idOpcion);
                $('#nombreOpcion').val(jsonData.opcion.nombreOpcion);
                $('#activo').val(jsonData.opcion.activo);

                if (jsonData.opcion.activo === 1) {
                    $('#chkActivo').prop('checked', true);
                } else {
                    $('#chkActivo').prop('checked', false);
                }
            }
        });
    }

    function cambiarEstadoOpcion(id, e, element) {
        e.preventDefault();

        var opcionTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado de la opción ' + opcionTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            listarOpciones();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalOpcion form');
        var dataSend = frm.serialize();

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalOpcion #divMessage')) {
                    listarOpciones();
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
</script>

