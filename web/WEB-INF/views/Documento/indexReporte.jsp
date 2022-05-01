<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">            
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
                <div class="col-sm-3 col-md-3">
                    <label>Tipo de Acción</label>
                    <select id="tipoAccion" name="tipoAccion" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoAccion" items="${tiposAccion}">
                            <option value="${tipoAccion.idTipoAccion}">${tipoAccion.nombreTipoAccion}</option>
                        </c:forEach>
                    </select>                    
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Desde <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="txtDesde" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" id="iconDesde" onclick="mostrarCalendar('txtDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Hasta <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="txtHasta" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" id="iconHasta" onclick="mostrarCalendar('txtHasta');"></i></span>
                    </div>
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
                <th>Fecha Documento</th>                
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Num. Dirección</th>
                <th>Asunto</th>
                <th>Observación</th>
                <th>Remitente</th>
                <th>Destino</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>
    $('#txtDesde').val(new Date().toString(dateFormatJS));
    $('#txtHasta').val(new Date().toString(dateFormatJS));
     
    var tblDocumentos = document.getElementById('tblDocumentos');

    $(document).ready(function() {
        listarDocumentos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ReporteDocumentos/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ReporteDocumentos/pdf" />', dataTable);        
    });
   
    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        listarDocumentos();
    });

    function reloadDocumentos() {
        var dataTable = $(tblDocumentos).dataTable();
        dataTable.fnReloadAjax();
    }

    function listarDocumentos() {
        var dataResponse = validateForm('[data-req]');
        
        var fechaDesde = Date.parseExact($('#txtDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#txtHasta').val(), dateFormatJS);
        
        if ($('#txtHasta').val().length > 0) {
            if(fechaHasta == null){
                dataResponse.mensajesRepuesta.push('Fecha hasta inválida.');
                dataResponse.estado = false;
            }
        }
        
        if ($('#txtDesde').val().length > 0) {
            if(fechaDesde == null){
                dataResponse.mensajesRepuesta.push('Fecha desde inválida.');
                dataResponse.estado = false;
            }
        }        
        
        if(fechaDesde != null && fechaHasta != null){
            if(fechaDesde.compareTo(fechaHasta) == 1){
                dataResponse.mensajesRepuesta.push('Fecha hasta debe ser mayor que la fecha desde.');
                dataResponse.estado = false;
            }
        }
        
        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }
        
        if ($.fn.DataTable.fnIsDataTable(tblDocumentos)) {
            $(tblDocumentos).dataTable().fnDestroy();
        }
        
        $(tblDocumentos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'fechaDesde', "value": fechaDesde.getTime()});
                aoData.push({"name": 'fechaHasta', "value": fechaHasta.getTime()});
                aoData.push({"name": 'tipoAccion', "value": $('#tipoAccion').val()});
            },
            "sAjaxSource": '<c:url value="/ReporteDocumentos/documentosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "numeracionInterna", sWidth: "10%", "bSortable": false},
                {mData: "fechaDocumento", sWidth: "10%", "bSortable": false, "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "nombreTipoDocumento", sWidth: "10%", "bSortable": false},
                {mData: "nroDocumento", sWidth: "10%", "bSortable": false},
                {mData: "numeracionDireccion", sWidth: "10%", "bSortable": false},
                {mData: "asunto", sWidth: "15%", "bSortable": false},
                {mData: "observacion", sWidth: "15%", "bSortable": false},
                {mData: "remitente", sWidth: "10%", "bSortable": false},
                {mData: "destino", sWidth: "10%", "bSortable": false}
            ]
        });
    }
</script>