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
                    <label>Tipo de Documento</label>
                    <select id="tipoDocumento" name="tipoDocumento" class="form-control">
                        <option value="0">-TODOS-</option>
                        <c:forEach var="tipoDocumento" items="${tiposDocumento}">
                            <option value="${tipoDocumento.idTipoDocumento}">${tipoDocumento.nombreTipoDocumento}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Nro de Documento</label>
                    <input type="text" id="nroDocumento" name="nroDocumento" class="form-control" maxlength="50" autocomplete="off"/>
                </div>
                <div class="col-sm-3 col-md-3">
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
                <th>Fecha Documento</th>
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Destino</th>
                <th>Num. Dirección</th>                
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>
    var tblDocumentos = document.getElementById('tblDocumentos');

    $(document).ready(function() {
        listarDocumentos();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/AlertaDocumentaria/excel" />', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDocumentos).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/AlertaDocumentaria/pdf" />', dataTable);
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
        
        if ($.fn.DataTable.fnIsDataTable(tblDocumentos)) {
            $(tblDocumentos).dataTable().fnDestroy();
        }
        
        $(tblDocumentos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'tipoDocumento', "value": $('#tipoDocumento').val()});
                aoData.push({"name": 'nroDocumento', "value": $('#nroDocumento').val()});
                aoData.push({"name": 'tipoAccion', "value": $('#tipoAccion').val()});
                aoData.push({"name": 'numeracionInterna', "value": $('#numeracionInterna').val()});                
            },
            "sAjaxSource": '<c:url value="/AlertaDocumentaria/despachosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $('td:eq(6)', nRow).addClass("center");
            },
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
                {mData: "nombreTipoDocumento", sWidth: "10%"},
                {mData: "nroDocumento", sWidth: "10%", "bSortable": false},
                {mData: "destino", sWidth: "30%", "bSortable": false},
                {mData: "numeracionDireccion", sWidth: "30%", "bSortable": false}
            ]
        });
    }
</script>