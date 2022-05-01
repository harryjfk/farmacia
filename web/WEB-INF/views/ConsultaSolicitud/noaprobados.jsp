<%@include file="../includeTagLib.jsp" %>

<c:forEach var="breadCrumb" items="${requestScope.breadCrumbs}" varStatus="status">    
    <c:if test="${status.count == 5}">
        <h3 class="heading">${breadCrumb}</h3>
    </c:if> 
</c:forEach>
        
<h3 class="heading">Listado de Medicamentos no aprobados</h3>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <button id="btnPDF" class="btn btn-primary">PDF</button>
        <button id="btnExcel" class="btn btn-primary">Excel</button>
        <button id="btnBuscar" class="btn btn-primary">Buscar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Establecimiento</label>
                    <input type="text" id="establecimiento" name="establecimiento" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Instituci&oacute;n</label>
                    <input type="text" id="institucion" name="institucion" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Medicamento</label>
                    <input type="text" id="producto" name="producto" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
            </div>
        </div>
    </div>
</div>
                    
<div class="row">
                  <div class="col-sm-12 col-md-12">
                    <table id="tblDetalleMed" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                            <th>Id Solicitud</th>
                            <th>Id Detalle</th>
                            <th>Establecimiento</th>
                            <th>Institución</th>
                            <th>Fecha Solicitado</th>
                            <th>Tipo Medicamento</th>
                            <th>Medicamento</th>
                            <th>Concentración</th>
                            <th>Forma Farmaceútica</th>
                            <th>Forma Presentación</th>
                            <th>Dosis Diaria</th>
                            <th>Estado</th>
                            </tr>
                        </thead>
                    </table>
                  </div> 
</div>
<script>
    
    //$('#fecha').val(new Date().toString(dateFormatJS));
    
    var tblDetalleMed = document.getElementById('tblDetalleMed');
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlExcel(dataTable);
        //window.location = getUrlConsultaExcel("excelNoAprobado", dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlPDF(dataTable);
        //window.location = getUrlConsultaPDF("pdfNoAprobado", dataTable);
    });
    
    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadDetalleMed();
    });
    
    $(document).ready(function() {
        listarDetalleMed();
    });
    
    function reloadDetalleMed() {
        var dataTable = $(tblDetalleMed).dataTable();
        dataTable.fnReloadAjax();
    }
    
    function listarDetalleMed() {
        $(tblDetalleMed).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'establecimiento', "value": $('#establecimiento').val()});
                aoData.push({"name": 'institucion', "value": $('#institucion').val()});
                aoData.push({"name": 'producto', "value": $('#producto').val()});
                aoData.push({"name": 'tipoConsulta', "value": "2"});
            },
            "sAjaxSource": "consultaSolicitudJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitud", sWidth: "5%"},
                {mData: "idSolicitudDetalleProducto", sWidth: "5%"},
                {mData: "establecimiento", sWidth: "12%"},
                {mData: "institucion", sWidth: "12%"},
                {mData: "fecha", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "tipoMedicamento", sWidth: "10%"},
                {mData: "descripcionProducto", sWidth: "10%"},
                {mData: "concentracion", sWidth: "10%"},
                {mData: "nombreFormaFarmaceutica", sWidth: "10%"},
                {mData: "formaPresentacion", sWidth: "10%"},
                {mData: "cantidadSol", sWidth: "8%"},
                {mData: "aprobado", sWidth: "8%"}
            ]
        });
    }
</script>