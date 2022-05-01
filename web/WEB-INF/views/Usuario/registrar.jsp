<%@include file="../includeTagLib.jsp" %>

<style>
    #modalPersonal .modal-dialog
    {
        margin-top: 5%;
        width: 80%;
    }
</style>

<form id="frmRegistrarUsuario">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Usuario <span class="f_req">*</span></label>
                        <input type="text" id="nombreUsuario" class="form-control" maxlength="70" data-req=""/>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Clave <span class="f_req">*</span></label>
                        <input type="password" id="clave" class="form-control" maxlength="30" data-req=""/>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Confirmar Clave <span class="f_req">*</span></label>
                        <input type="password" id="confirmarClave" class="form-control" maxlength="30" data-req=""/>
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Personal <span class="f_req">*</span></label>
                        <input type="hidden" id="idPersonal" />
                        <div class="input-group">
                            <input type="text" id="txtNombreCompleto" class="form-control" readonly="" data-req="" />
                            <span class="input-group-addon" onclick="modalPersonal()"><i class="splashy-help"></i></span>
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Correo <span class="f_req">*</span></label>
                        <input type="text" id="correo" class="form-control" data-req="" />
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Confirmar Correo <span class="f_req">*</span></label>
                        <input type="text" id="confirmarCorreo" class="form-control" data-req="" />
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Perfiles <span class="f_req">*</span></label>
                        <select id="selPerfil" class="form-control" multiple="" data-placeholder="Seleccione los perfiles">
                            <c:forEach var="perfil" items="${perfiles}">
                                <option value="${perfil.idPerfil}">${perfil.nombrePerfil}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button id="btnCancelar" class="btn btn-default">Cancelar</button>
            </div>
        </div>
    </div>
</form>

<div class="modal fade" id="modalPersonal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Personal</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblPersonal" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                <th></th>
                                <th>Nombre Personal</th>
                                <th>Tipo Documento</th>
                                <th>Nro Documento</th>
                                <th>Unidad</th>
                                <th>Cargo</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>

<script>
    $('#selPerfil').chosen({no_results_text: "No se encontró coincidencias con"});

    function modalPersonal() {

        $('#tblPersonal').css({display: 'none'});
        var tblPersonal = document.getElementById('tblPersonal');

        if ($.fn.DataTable.fnIsDataTable(tblPersonal)) {
            $(tblPersonal).dataTable().fnDestroy();
        }

        $.ajax({
            url: '<c:url value="/Usuario/personalSinUsuario" />',
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                $(tblPersonal).dataTable({
                    "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "idPersonal", sWidth: "3%", "bSortable": false, "mRender": function(data, type, row) {
                                return '<i class="splashy-arrow_medium_right" onclick="selectPersonal(\'' + data + '\', this)"></i>';
                            }
                        },
                        {mData: "nombreCompleto", sWidth: "14%"},
                        {mData: "tipoDocumento", sWidth: "7%"},
                        {mData: "nroDocumento", sWidth: "7%"},
                        {mData: "unidad", sWidth: "10%"},
                        {mData: "cargo", sWidth: "10%"}
                    ],
                    "iDisplayLength": 8
                });

                $('#tblPersonal').css({display: ''});
            }
        });

        $('#modalPersonal').modal('show');
    }

    function selectPersonal(idPersonal, element) {
        var tr = $(element).parent().parent();
        $('#txtNombreCompleto').val($(tr).find('td:eq(1)').text());
        $('#idPersonal').val(idPersonal);
        $('#modalPersonal').modal('hide');
    }

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/Usuario" />';
    });
    
    sendFormByKeyEnterForSmoke('#frmRegistrarUsuario', function() {
        $('#btnGuardar').click();
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        if ($('#clave').val() !== $('#confirmarClave').val()) {
            dataResponse.mensajesRepuesta.push('Los campos de clave no coinciden, verifique.');
            dataResponse.estado = false;
        }

        if ($('#correo').val() !== $('#confirmarCorreo').val()) {
            dataResponse.mensajesRepuesta.push('Los campos de correo no coinciden, verifique.');
            dataResponse.estado = false;
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var perfiles = new Array();

        var selPerfil = $('#selPerfil').val();

        if (selPerfil !== null) {
            for (var i = 0; i <= selPerfil.length - 1; ++i) {
                var perfil = new Object();
                perfil.idPerfil = selPerfil[i];
                perfiles.push(perfil);
            }
        }

        var usuario = {
            nombreUsuario: $('#nombreUsuario').val(),
            clave: $('#clave').val(),
            correo: $('#correo').val(),
            personal: {idPersonal: $('#idPersonal').val()},
            perfiles: perfiles
        };

        $.ajax({
            url: '<c:url value="/Usuario/registrar" />',
            data: JSON.stringify(usuario),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function(){
                    $('#btnCancelar').click();
                });
            }
        });
    });
</script>