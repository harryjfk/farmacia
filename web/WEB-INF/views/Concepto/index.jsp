<%@include file="../includeTagLib.jsp" %>

<style>
    #modalConcepto .modal-dialog
    {
        margin-top: 5%
    }
</style>

<div class="row">
    <div class="col-sm-11 col-md-11">
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
    <div class="col-sm-11 col-md-11">
        <table id="tblConcepto" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Concepto</th>
                    <th>Tipo Movimiento</th>
                    <th>Estado</th>
                    <th>Acción</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalConcepto">
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
                                <label>Concepto <span class="f_req">*</span></label>
                                <input type="text" id="nombreConcepto" name="nombreConcepto" class="form-control" data-req="" maxlength="70"/>
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <label>Tipo Movimiento <span class="f_req">*</span></label>
                                <select id="tipoMovimientoConcepto" name="tipoMovimientoConcepto" class="form-control" data-req="">
                                    <option value="TODOS">TODOS</option>
                                    <option value="INGRESO">INGRESO</option>
                                    <option value="SALIDA">SALIDA</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-md-6">
                            <label>Tipo Precio <span class="f_req">*</span></label>
                            <select id="tipoPrecio" name="tipoPrecio" class="form-control" data-req="">
                                <option value="null">NINGUNO</option>
                                <option value="ADQUISICION">ADQUISICION</option>
                                <option value="OPERACION">OPERACION</option>
                                <option value="DISTRIBUCION">DISTRIBUCION</option>
                            </select>
                        </div>
                        <div class="col-sm-6 col-md-6">
                            <label>Estado <span class="f_req">*</span></label>
                            <div class="separator-form-checkbox"></div>
                            <label class="checkbox-inline">
                                <input type="checkbox" id="chkActivo" name="chkActivo" /> Activo
                            </label>
                            <input type="hidden" id="activo" name="activo" />
                            <input type="hidden" id="idConcepto" name="idConcepto" />
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

<div class="modal fade" id="modalDocumentoOrigen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <div class="col-sm-12 col-md-12">        
                    <select id="documentoOrigen" multiple="multiple">

                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalTipoDocumentoMov">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
            </div>
            <div class="modal-body">
                <div class="col-sm-12 col-md-12">
                    <select id="tipoDocumentoMov" multiple="multiple"></select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<script>

    var tblConcepto = document.getElementById('tblConcepto');

    $(document).ready(function() {
        listarConceptos();

        modalDocumentoOrigen();
        modalTipoDocumentoMov();
        
        $('#tipoMovimientoConcepto').change();
    });

    $('#tipoMovimientoConcepto').change(function (){
       if($(this).val() == 'INGRESO') {
           $('#tipoPrecio').val('null');
           $('#tipoPrecio').attr('disabled', 'disabled');
           $('#tipoPrecio').removeAttr('data-req');
           $('#tipoPrecio option:eq(0)').text('NINGUNO');
       }else{
           $('#tipoPrecio').removeAttr('disabled');
           $('#tipoPrecio').attr('data-req', '');   
           $('#tipoPrecio option:eq(0)').text('-SELECCIONE-');
       }
    });

    function modalDocumentoOrigen() {
        $.ajax({
            url: '<c:url value="/DocumentoOrigen/documentosOrigen" />',
            type: 'GET',
            dataType: 'json',
            success: function(data, status, metaData) {

                var option = '';

                for (var i = 0; i < data.length; ++i) {
                    option += '<option value="' + data[i].idDocumentoOrigen + '">' + data[i].nombreDocumentoOrigen + '</option>';
                }

                $('#documentoOrigen').html(option);

                $('#documentoOrigen').multiSelect({
                    selectableHeader: "<div class='custom-header'>Documentos Origen</div>",
                    selectionHeader: "<div class='custom-header'>Documentos Origen del Concepto</div>",
                    afterSelect: function(values) {
                        if ($("#modalDocumentoOrigen").data('bs.modal') && $("#modalDocumentoOrigen").data('bs.modal').isShown) {
                              var conceptoDocumentoOrigen = {
                                idConcepto: $('#idConcepto').val(),
                                idDocumentoOrigen: values[0]
                            };

                            $.ajax({
                                url: '<c:url value="/ConceptoDocumentoOrigen/registrar" />',
                                type: "POST",
                                dataType: 'json',
                                contentType: 'application/json',
                                data: JSON.stringify(conceptoDocumentoOrigen),
                                success: function(dataResponse, status, metaData) {
                                    $('#documentoOrigen option[value="' + values[0].toString() + '"]').attr('data-id', dataResponse.data.toString());
                                }
                            });
                        }
                    },
                    afterDeselect: function(values) {
                        if ($("#modalDocumentoOrigen").data('bs.modal') && $("#modalDocumentoOrigen").data('bs.modal').isShown) {
                            var $option = $('#documentoOrigen option[value="' + values[0] + '"]');
                            var id = $option.attr('data-id');
                            $.ajax({
                                url: '<c:url value="/ConceptoDocumentoOrigen/eliminar" />/' + id,
                                type: "POST",
                                dataType: 'json',
                                contentType: 'application/json',
                                data: {},
                                success: function(data, status, metaData) {
                                    $option.attr('data-id', '');
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    function modalTipoDocumentoMov() {
        $.ajax({
            url: '<c:url value="/TipoDocumentoMov/tiposDocumentoMov" />',
            type: 'GET',
            dataType: 'json',
            success: function(data, status, metaData) {

                var option = '';

                for (var i = 0; i < data.length; ++i) {
                    option += '<option value="' + data[i].idTipoDocumentoMov + '">' + data[i].nombreTipoDocumentoMov + '</option>';
                }

                $('#tipoDocumentoMov').html(option);

                $('#tipoDocumentoMov').multiSelect({
                    selectableHeader: "<div class='custom-header'>Tipos Documento</div>",
                    selectionHeader: "<div class='custom-header'>Tipos Documento del Concepto</div>",
                    afterSelect: function(values) {
                        if ($("#modalTipoDocumentoMov").data('bs.modal') && $("#modalTipoDocumentoMov").data('bs.modal').isShown) {
                            var tipoDocumentoOrigen = {
                                idConcepto: $('#idConcepto').val(),
                                idTipoDocumentoMov: values[0]
                            };

                            $.ajax({
                                url: '<c:url value="/ConceptoTipoDocumentoMov/registrar" />',
                                type: "POST",
                                dataType: 'json',
                                contentType: 'application/json',
                                data: JSON.stringify(tipoDocumentoOrigen),
                                success: function(dataResponse, status, metaData) {
                                    $('#tipoDocumentoMov option[value="' + values[0].toString() + '"]').attr('data-id', dataResponse.data.toString());
                                }
                            });
                        }                        
                    },
                    afterDeselect: function(values) {
                        if ($("#modalTipoDocumentoMov").data('bs.modal') && $("#modalTipoDocumentoMov").data('bs.modal').isShown) {
                            var $option = $('#tipoDocumentoMov option[value="' + values[0] + '"]');
                            var id = $option.attr('data-id');
                            $.ajax({
                                url: '<c:url value="/ConceptoTipoDocumentoMov/eliminar" />/' + id,
                                type: "POST",
                                dataType: 'json',
                                contentType: 'application/json',
                                data: {},
                                success: function(data, status, metaData) {
                                    $option.attr('data-id', '');
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblConcepto).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Concepto/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblConcepto).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Concepto/pdf"/>', dataTable);
    });

    $('#modalConcepto form').keypress(function(e) {

        if (e.which === 13) {
            e.preventDefault();
            $('#btnGuardar').click();
        }
    });

    function reloadConceptos() {
        var dataTable = $(tblConcepto).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarConceptos() {

        if ($.fn.DataTable.fnIsDataTable(tblConcepto)) {
            $(tblConcepto).dataTable().fnDestroy();
        }

        $(tblConcepto).dataTable({
            "sAjaxSource": '<c:url value="/Concepto/conceptosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idConcepto", sWidth: "10%"},
                {mData: "nombreConcepto", sWidth: "42%"},
                {mData: "tipoMovimientoConcepto", sWidth: "15%"},
                {mData: "activoTexto", sWidth: "15%"},
                {mData: "idConcepto", sWidth: "18%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var documentoOrigenHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';

    <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
        <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                        editHTML += '<a href="#" class="separator-icon-td" onclick="obtenerConcepto(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'documentos'}">
                        documentoOrigenHTML += '<a href="#" class="separator-icon-td" onclick="documentoOrigen(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-document_a4"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'tiposDocumentos'}">
                        documentoOrigenHTML += '<a href="#" class="separator-icon-td" onclick="tipoDocumentoMov(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-document_a4"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        stateHTML += '<a href="#" class="separator-icon-td" onclick="cambiarEstadoConcepto(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
        </c:if>
        <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                        deleteHTML += '<a href="#" class="separator-icon-td" onclick="eliminarConcepto(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
        </c:if>
    </c:forEach>

                        return editHTML + documentoOrigenHTML + stateHTML + deleteHTML;
                    }
                }
            ]
        });

    }

    function obtenerConcepto(id, e, element) {
        e.preventDefault();

        var modalConcepto = $('#modalConcepto');
        modalConcepto.find('form').attr('action', '<c:url value="/Concepto/modificar" />');
        modalConcepto.find('.modal-header .modal-title').html($(element).attr('title'));
        cleanform('#modalConcepto');
        $('#chkActivo').removeAttr('disabled');

        $.ajax({
            url: '<c:url value="/Concepto/conceptoJSON" />/' + id,
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {

                modalConcepto.modal('show');

                $('#idConcepto').val(jsonData.idConcepto);
                $('#nombreConcepto').val(jsonData.nombreConcepto);
                $('#tipoMovimientoConcepto').val(jsonData.tipoMovimientoConcepto);
                $('#tipoMovimientoConcepto').change();
                $('#tipoPrecio').val(ValueNullText(jsonData.tipoPrecio));
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

        var modalConcepto = $('#modalConcepto');
        modalConcepto.find('form').attr('action', '<c:url value="Concepto/registrar" />');
        modalConcepto.find('.modal-header .modal-title').html($(this).attr('title'));
        cleanform('#modalConcepto');
        $('#idConcepto').val('0');
        $('#activo').val('1');
        $('#chkActivo').attr('disabled', 'disabled');
        $('#chkActivo').prop('checked', true);

        modalConcepto.modal('show');
    });

    $('#btnGuardar').click(function(e) {
        var frm = $('#modalConcepto form');
        var dataSend = frm.serialize();

        var dataResponse = validateForm('.modal-body [data-req]');

        if (dataResponse.estado === false) {
            jsonToDivError(dataResponse, '#modalConcepto #divMessage');
            return;
        }

        $.ajax({
            url: frm.attr("action"),
            data: dataSend,
            type: "POST",
            success: function(dataResponse) {

                if (jsonToDivError(dataResponse, '#modalConcepto #divMessage')) {
                    reloadConceptos();
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

    function cambiarEstadoConcepto(id, e, element) {
        e.preventDefault();

        var conceptoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea cambiar de estado el concepto ' + conceptoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Concepto/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadConceptos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function eliminarConcepto(id, e, element) {
        e.preventDefault();

        var conceptoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el concepto ' + conceptoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="/Concepto/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadConceptos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }

    function documentoOrigen(id, e, element) {
        e.preventDefault();
        var conceptoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        $('#idConcepto').val(id);
        $('#documentoOrigen').multiSelect('deselect_all');
        $('#modalDocumentoOrigen').find('.modal-header .modal-title').html(conceptoTexto);
        $('#documentoOrigen option').attr('data-id', '');

        $.ajax({
            url: '<c:url value="/ConceptoDocumentoOrigen/listarIdDocumento" />?idConcepto=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(dataResponse, status, metaData) {
                var ids = new Array();

                for (var i = 0; i < dataResponse.length; ++i) {
                    ids.push(dataResponse[i].idDocumentoOrigen.toString());
                    $('#documentoOrigen option[value="' + dataResponse[i].idDocumentoOrigen.toString() + '"]').attr('data-id', dataResponse[i].idConceptoDocumentoOrigen.toString());
                }

                $('#documentoOrigen').multiSelect('select', ids);
                $('#modalDocumentoOrigen').modal('show');
            }
        });
    }

    function tipoDocumentoMov(id, e, element) {
        e.preventDefault();
        var conceptoTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        $('#idConcepto').val(id);
        $('#tipoDocumentoMov').multiSelect('deselect_all');
        $('#modalTipoDocumentoMov').find('.modal-header .modal-title').html(conceptoTexto);
        $('#tipoDocumentoMov option').attr('data-id', '');

        $.ajax({
            url: '<c:url value="/ConceptoTipoDocumentoMov/listarIdDocumento" />?idConcepto=' + id,
            type: 'GET',
            dataType: 'json',
            success: function(dataResponse, status, metaData) {
                var ids = new Array();

                for (var i = 0; i < dataResponse.length; ++i) {
                    ids.push(dataResponse[i].idTipoDocumentoMov.toString());
                    $('#tipoDocumentoMov option[value="' + dataResponse[i].idTipoDocumentoMov.toString() + '"]').attr('data-id', dataResponse[i].idConceptoTipoDocumentoMov.toString());
                }

                $('#tipoDocumentoMov').multiSelect('select', ids);
                $('#modalTipoDocumentoMov').modal('show');
            }
        });
    }
</script>