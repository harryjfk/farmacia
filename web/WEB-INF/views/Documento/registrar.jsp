<%@include file="../includeTagLib.jsp" %>
<style>
    #modalPersonal .modal-dialog
    {
        margin-top: 5%;
        width: 70%;
    }
</style>

<form id="frmDocumentoRegistrar" name="frmDocumentoRegistrar" action="<c:url value="/Documento/registrar" />" target="ifrm_registrar" method="POST" enctype="multipart/form-data" autocomplete="off">
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Numeración Interna</label>
                        <input type="text" id="numeracionInterna" name="numeracionInterna" class="form-control" maxlength="20" />
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <label>Fecha Documento <span class="f_req">*</span></label>
                        <div class="input-group">
                            <input type="text" id="fechaDocumento" name="fechaDocumento" class="form-control" data-field-date="" data-req=""/>
                            <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDocumento');"></i></span>
                        </div>
                    </div>
                    <div class="col-sm-2 col-md-2">
                        <label>Fecha Salida</label>
                        <div class="input-group">
                            <input type="text" id="fechaSalida" name="fechaSalida" class="form-control" data-field-date=""/>    
                            <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaSalida');"></i></span>
                        </div>                    
                    </div>
                    <div class="col-sm-3 col-md-3">
                        <label>Tipo Documento <span class="f_req">*</span></label>
                        <select id="tipoDocumento" name="tipoDocumento" class="form-control" data-req="">
                            <option value="-1">-SELECCIONE-</option>
                            <c:forEach var="tipoDocumento" items="${tiposDocumentos}">
                                <option value="${tipoDocumento.idTipoDocumento}">${tipoDocumento.nombreTipoDocumento}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-3 col-md-3">
                        <label>Nro Documento <span class="f_req">*</span></label>
                        <input type="text" id="nroDocumento" name="nroDocumento" class="form-control" maxlength="50" data-req="" />
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Asunto</label>
                        <input type="text" id="asunto" name="asunto" class="form-control" maxlength="200" />
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Remitente</label>
                        <input type="text" id="remitente" name="remitente" class="form-control" maxlength="200" />                        
                    </div>
                </div>
            </div>
            <div class="formSep">
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Destino</label>
                        <div class="input-group">
                            <input type="text" id="destino" name="destino" class="form-control" maxlength="200" />                            
                            <span class="input-group-addon"><i class="splashy-help" onclick="modalPersonal(event)"></i></span>
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Num. Dirección</label>
                        <input type="text" id="numeracionDireccion" name="numeracionDireccion" class="form-control" maxlength="100" />                    
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Acción <span class="f_req">*</span></label>
                        <select id="tipoAccion" name="tipoAccion" class="form-control" data-req="">
                            <option value="-1">-SELECCIONE-</option>
                            <c:forEach var="tipoAccion" items="${tiposAcciones}">
                                <option value="${tipoAccion.idTipoAccion}">${tipoAccion.nombreTipoAccion}</option>
                            </c:forEach>
                        </select>  
                    </div>
                </div>
            </div>
            <div class="formSep">            
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Documento</label>
                        <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                            <div class="form-control" data-trigger="fileinput">
                                <i class="glyphicon glyphicon-file fileinput-exists"></i>
                                <span class="fileinput-filename"></span>
                            </div>
                            <span class="input-group-addon btn btn-default btn-file">
                                <span class="fileinput-new">Seleccionar</span>
                                <span class="fileinput-exists">Cambiar</span>
                                <input type="file" name="fileDocumento">
                            </span>
                            <a href="#" class="input-group-addon btn btn-default fileinput-exists" data-dismiss="fileinput">Quitar</a>
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Observación</label>
                        <textarea id="observacion" name="observacion" rows="4" class="form-control"></textarea>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
                <button id="btnCancelar" class="btn btn-default">Cancelar</button>
            </div>
        </div>
    </div>
    <input type="hidden" id="jsonForm" name="jsonForm" />
    <iframe id='ifrm_registrar' name='ifrm_registrar' src="" style="display:none;"></iframe>
</form>

<div class="modal fade" id="modalPersonal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Destino</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProducto" class="formSep">
                            <div class="row">
                                <div class="col-sm-6 col-md-6">
                                    <label>Unidad</label>
                                    <select id="selUnidad" class="form-control">                                         
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <table id="tblPersonal" class="table table-bordered table-striped dTableR">
                                <thead>
                                    <tr>
                                    <th></th>
                                    <th>Nombre Personal</th>
                                    <th>Tipo Documento</th>
                                    <th>Nro Documento</th>
                                    <th>Unidad</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>

<script>
    
    $('#fechaDocumento').val(new Date().toString(dateFormatJS));
    
    function modalPersonal(e) {
        e.preventDefault();
        
        var Data = {"id": "", "value": "idUnidad", "text": "nombreUnidad"};
        llenarSelectOptional('#selUnidad', '<c:url value="/Unidad/unidadesJSON" />', Data, '', function() {
           listarPersonal();
        });
        
        $('#modalPersonal').modal('show');
    }
    
    $('#selUnidad').change(function (){
        listarPersonal();
    });
    
    function listarPersonal(){
        var tblPersonal = document.getElementById('tblPersonal');        
        
        if ($.fn.DataTable.fnIsDataTable(tblPersonal)) {
            $(tblPersonal).dataTable().fnDestroy();
        }
        
        $(tblPersonal).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idUnidad', "value": $('#selUnidad').val()});
            },
            "sAjaxSource": '<c:url value="/Personal/personalPorUnidadJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idPersonal", sWidth: "5%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectPersonal(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "nombreCompleto", sWidth: "40%"},
                {mData: "tipoDocumento", sWidth: "13%", "bSortable": false},
                {mData: "nroDocumento", sWidth: "14%","bSortable": false},
                {mData: "unidad", sWidth: "28%","bSortable": false}                
            ]
        });        
    }
    
    function selectPersonal(idPersonal, element){
        var nombrePersonal = $(element).parent().parent().find('td:eq(1)').text();
        $('#destino').val(nombrePersonal);
        $('#modalPersonal').modal('hide');
    }
    
    $('#ifrm_registrar').load(function() {        
        var dataResponse = $(this).contents().find("body").html();
        if (dataResponse.length) {
            dataResponse = jQuery.parseJSON(dataResponse);
            functionResponse(dataResponse, function() {
                $('#btnCancelar').click();
            });
        }
    });
    
    sendFormByKeyEnterForSmoke('#frmDocumentoRegistrar', function() {
        $('#btnGuardar').click();
    });
    
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        
        location.replace('<c:url value="/Documento" />');
    });
    
    $('#btnGuardar').click(function(e) {
        e.preventDefault();
        var dataResponse = validateForm('[data-req]');
        var fechaSalida = Date.parseExact($('#fechaSalida').val(), dateFormatJS);
        var fechaDocumento = Date.parseExact($('#fechaDocumento').val(), dateFormatJS);
        var fechaSalidaTime = null;
        if ($('#fechaSalida').val().length > 0) {
            if (fechaSalida === null) {
                dataResponse.mensajesRepuesta.push('Fecha de Salida inválida.');
                dataResponse.estado = false;
            } else {
                fechaSalidaTime = fechaSalida.getTime();
            }
        }

        if ($('#fechaDocumento').val().length > 0) {
            if (fechaDocumento === null) {
                dataResponse.mensajesRepuesta.push('Fecha de Documento inválida.');
                dataResponse.estado = false;
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var documento = {
            numeracionInterna: $('#numeracionInterna').val(),
            fechaDocumento: fechaDocumento.getTime(),
            fechaSalida: fechaSalidaTime,
            idTipoAccion: $('#tipoAccion').val(),
            idTipoDocumento: $('#tipoDocumento').val(),
            nroDocumento: $('#nroDocumento').val(),
            asunto: $('#asunto').val(),
            remitente: $('#remitente').val(),
            destino: $('#destino').val(),
            numeracionDireccion: $('#numeracionDireccion').val(),
            observacion: $('#observacion').val()
        };
        
        $('#jsonForm').val(JSON.stringify(documento));
        $('#frmDocumentoRegistrar').submit();
    });
</script>