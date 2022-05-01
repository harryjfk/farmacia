<%@include file="../includeTagLib.jsp" %>

<style>
    #modalSubmodulo .modal-dialog
    {
        margin-top: 5%;
        width: 70%;
    }
</style>

<h3 class="heading">Mantenimiento de Submodulos</h3>

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
                        <div class="col-sm-5 col-md-5">
                            <label>Módulo <span class="f_req">*</span></label>
                            <input type="text" id="nombreModulo" class="form-control" data-req="" disabled=""/>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Submodulo <span class="f_req">*</span></label>
                            <input type="text" id="nombreSubmodulo" name="nombreSubmodulo" class="form-control" data-req="" maxlength="70"/>
                        </div>
                        <div class="col-sm-1 col-md-1">
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
        listarSubmodulos();
    });

    $('#selModulo').change(function() {
        listarSubmodulos();
    });

    function listarSubmodulos() {
        var tblSubmodulos = document.getElementById('tblSubmodulos');

        if ($.fn.DataTable.fnIsDataTable(tblSubmodulos)) {
            $(tblSubmodulos).dataTable().fnDestroy();
        }

        $.ajax({
            url: 'submodulosJSON?idModulo=' + $('#selModulo').val(),
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $(tblSubmodulos).dataTable({
                    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "idSubmodulo", sWidth: "15%"},
                        {mData: "nombreSubmodulo", sWidth: "60%"},
                        {mData: "activoTexto", sWidth: "15%"},
                        {mData: "idSubmodulo", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                                var td = '';
                                var editHTML = '<a href="#" class="separator-icon-td" onclick="obtenerSubmodulo(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
                                editHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoSubmodulo(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';

                                td = td + editHTML;

                                return td;
                            }
                        }
                    ]
                });
            }
        });
    }

    function obtenerSubmodulo(id, e, element) {
        e.preventDefault();

        var modalSubmodulo = $('#modalSubmodulo');
        modalSubmodulo.find('form').attr('action', 'modificar');
        modalSubmodulo.find('.modal-header .modal-title').html('Editar Submodulo');
        cleanform('#modalSubmodulo');
        $('#chkActivo').removeAttr('disabled');

        modalSubmodulo.modal('show');

        $.ajax({
            url: 'submoduloJSON?idSubmodulo=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                
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
    
    function cambiarEstadoSubmodulo(id, e, element){
        e.preventDefault();

        var submoduloTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea cambiar de estado el submodulo ' + submoduloTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
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
    
    $('#btnGuardar').click(function(e) {
        var frm = $('#modalSubmodulo form');
        var dataSend = frm.serialize();

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

</script>
