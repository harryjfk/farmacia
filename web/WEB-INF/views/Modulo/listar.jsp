<style>
    #modalModulo .modal-dialog
    {
        margin-top: 5%
    }
</style>

<h3 class="heading">Mantenimiento de Módulos</h3>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblModulo" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Modulo</th>
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
                    <!--<div class="formSep">-->
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
    $('#btnAgregar').click(function(e) {
        $('#modalModulo form').attr('id', 'frmRegistrarModulo');
        $('#modalModulo form').attr('action', 'registrar');
    });

    $(document).ready(function() {
        listarModulos();
    });

    function listarModulos() {

        var tblModulo = document.getElementById('tblModulo');

        if ($.fn.DataTable.fnIsDataTable(tblModulo)) {
            $(tblModulo).dataTable().fnDestroy();
        }

        $.ajax({
            url: 'modulosJSON',
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $(tblModulo).dataTable({
                    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "idModulo", sWidth: "14%"},
                        {mData: "nombreModulo", sWidth: "57%"},
                        {mData: "activoTexto", sWidth: "15%"},
                        {mData: "idModulo", sWidth: "14%", "bSortable": false, "mRender": function(data, type, row) {

                                var td = '';
                                var editHTML = '<a href="#" class="separator-icon-td" onclick="obtenerModulo(' + data + ', event, this)"><i class="splashy-pencil" title="Editar"></i></a>';
                                editHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoModulo(' + data + ', event, this)"><i class="splashy-refresh" title="Cambiar Estado"></i></a>';                                
                                
                                td = td + editHTML;                               

                                return td;
                            }
                        }
                    ]
                });
            }
        });
    }

    function obtenerModulo(id, e, element) {
        e.preventDefault();
        
        var modalModulo = $('#modalModulo');
        modalModulo.find('form').attr('action', 'modificar');
        modalModulo.find('.modal-header .modal-title').html('Editar Modulo');
        cleanform('#modalModulo');
        $('#chkActivo').removeAttr('disabled');
        
        modalModulo.modal('show');

        $.ajax({
            url: 'moduloJSON?id=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
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

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        var modalModulo = $('#modalModulo');
        modalModulo.find('form').attr('action', 'insertar');
        modalModulo.find('.modal-header .modal-title').html('Registrar Modulo');
        cleanform('#modalModulo');
        $('#idModulo').val('0');
        $('#activo').val('0');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);
        
        modalModulo.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalModulo form');
        var dataSend = frm.serialize();

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

        smoke.confirm('¿Está seguro que desea cambiar de estado del módulo ' + moduloTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'estado/' + id,
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

    function eliminarModulo(id, e, element) {
        e.preventDefault();

        var moduloTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el módulo ' + moduloTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
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