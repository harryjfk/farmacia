<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
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
        <button id="btnBuscar" class="btn btn-primary">Buscar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Tipo de Documento</label>
                    <select id="tipoDocumento" name="tipoDocumento" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoDocumento" items="${tiposDocumento}">
                            <option value="${tipoDocumento.idTipoDocumento}">${tipoDocumento.nombreTipoDocumento}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Desde</label>
                    <div class="input-group">
                        <input type="text" id="FechaDesde" class="form-control" data-field-date="" data-req />
                        <span class="input-group-addon" onclick="mostrarCalendar('FechaDesde');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Hasta</label>
                    <div class="input-group">
                        <input type="text" id="FechaHasta" class="form-control" data-field-date="" data-req />
                        <span class="input-group-addon" onclick="mostrarCalendar('FechaHasta');"><i class="splashy-calendar_month"></i></span>
                    </div> 
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Nro de Documento</label>
                    <input type="text" id="nroDocumento" name="nroDocumento" class="form-control" maxlength="50" autocomplete="off"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Num. Interna</label>
                    <input type="text" id="numeracionInterna" name="numeracionInterna" class="form-control" maxlength="50" autocomplete="off"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Tipo de Acción</label>
                    <select id="tipoAccion" name="tipoAccion" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoAccion" items="${tiposAccion}">
                            <option value="${tipoAccion.idTipoAccion}">${tipoAccion.nombreTipoAccion}</option>
                        </c:forEach>
                    </select>                    
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">

        <table id="tblDocumentos" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Num. Interna</th>
                <th>Fecha Doc.</th>
                <th>Fecha Salida</th>
                <th>Tipo Acción</th>
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Asunto</th>                   
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>

    var tblDocumentos = document.getElementById('tblDocumentos');

    $(document).ready(function() {
        
        var date = new Date();
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        $('#FechaDesde').datepicker().datepicker('setDate', firstDay);
        $('#FechaHasta').datepicker().datepicker('setDate', date);
        
        listarDocumentos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Documento/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Documento/pdf" />', dataTable);        
    });

    $('#btnAgregar').click(function(e) {
        e.preventDefault();

        window.location = '<c:url value="/Documento/registrar" />';
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        listarDocumentos()
    });

    function reloadDocumentos() {
        var dataTable = $(tblDocumentos).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarDocumentos() {
        
        if ($.fn.DataTable.fnIsDataTable(tblDocumentos)) {
            $(tblDocumentos).dataTable().fnDestroy();
        }
        
        var fechaDesde = Date.parseExact($('#FechaDesde').val(), dateFormatJS).getTime();
        var fechaHasta = Date.parseExact($('#FechaHasta').val(), dateFormatJS).getTime();
        
        $(tblDocumentos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'tipoDocumento', "value": $('#tipoDocumento').val()});
                aoData.push({"name": 'nroDocumento', "value": $('#nroDocumento').val()});
                aoData.push({"name": 'tipoAccion', "value": $('#tipoAccion').val()});
                aoData.push({"name": 'numeracionInterna', "value": $('#numeracionInterna').val()});
                
                aoData.push({"name": 'fechaDesde', "value": fechaDesde});
                aoData.push({"name": 'fechaHasta', "value": fechaHasta});
            },
            "sAjaxSource": '<c:url value="/Documento/documentosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "numeracionInterna", sWidth: "10%"},
                {mData: "fechaDocumento", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "fechaSalida", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "nombreTipoAccion", sWidth: "10%"},
                {mData: "nombreTipoDocumento", sWidth: "10%"},
                {mData: "nroDocumento", sWidth: "10%", "bSortable": false},
                {mData: "asunto", sWidth: "20%", "bSortable": false},
                {mData: "activoTexto", sWidth: "8%"},
                {mData: "idDocumento", sWidth: "12%", "bSortable": false, "mRender": function(data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var downloadFile = '';

                        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                            <c:if test="${opcionSubmenu.appOpcion == 'modificar'}">
                                editHTML = '<a href="<c:url value="/Documento/modificar" />?idDocumento=' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                                stateHTML = '<a href="#" class="separator-icon-td" onclick="cambiarEstadoDocumento(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'eliminar'}">
                                deleteHTML = '<a href="#" class="separator-icon-td" onclick="eliminarDocumento(' + data + ', event, this)" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
                            </c:if>
                            <c:if test="${opcionSubmenu.appOpcion == 'descargar'}">
                                if(row.extension !== null){
                                    downloadFile = '<a href="<c:url value="/Documento/descargar/" />' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-download"></i></a>';
                                }
                            </c:if>                                
                        </c:forEach>
                        
                        return editHTML + stateHTML + deleteHTML + downloadFile;
                    }
                }
            ]
        });
    }

    function cambiarEstadoDocumento(id, e, element) {
        e.preventDefault();

        var nroDocumentoTexto = $(element).parent().parent().find('td:eq(4)').text().trim();
        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(3)').text().trim();
        
        smokeConfirm('¿Está seguro que desea cambiar de estado el / la ' + tipoDocumentoTexto + ' Nro. ' + nroDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="Documento/estado" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }


    function eliminarDocumento(id, e, element) {
        e.preventDefault();

        var nroDocumentoTexto = $(element).parent().parent().find('td:eq(4)').text().trim();
        var tipoDocumentoTexto = $(element).parent().parent().find('td:eq(3)').text().trim();

        smokeConfirm('¿Está seguro que desea eliminar el / la ' + tipoDocumentoTexto + ' Nro. ' + nroDocumentoTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: '<c:url value="Documento/eliminar" />/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function() {
                            reloadDocumentos();
                        };
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>