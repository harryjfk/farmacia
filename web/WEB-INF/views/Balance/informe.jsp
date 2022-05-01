<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <button id="btnPDF" class="btn btn-primary">PDF</button>
        <button id="btnExcel" class="btn btn-primary">Excel</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>M&eacute;dico</label>
                    <input type="text" id="medico" name="medico" class="form-control" maxlength="40" value="${personal.nombre} ${personal.apellidoPaterno} ${personal.apellidoMaterno}" readonly="true" autocomplete="off"/>
                    <input type="hidden" id="idSolicitud" name="idSolicitud" value="${solicitud.idSolicitud}"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Establecimiento</label>
                    <input type="text" id="establecimiento" name="establecimiento" class="form-control" value="${solicitud.establecimiento}" readonly="true" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Instituci&oacute;n</label>
                    <input type="text" id="institucion" name="institucion" class="form-control" maxlength="40" value="${solicitud.institucion}" readonly="true" autocomplete="off"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha </label>
                    <div class="input-group">
                      <input type="text" id="fecha" name="fecha" class="form-control uppercase" data-field-date="" value='<fmt:formatDate pattern="<%=formatoFecha%>" value="${solicitud.fecha}" />' readonly="true" data-req=""/>
                    </div>
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
                            <th>Fecha Adquisición</th>
                            <th>Medicamento</th>
                            <th>Concentración</th>
                            <th>Forma Farmaceútica</th>
                            <th>Forma Presentación</th>
                            <th>Cantidad Adquirida</th>
                            <th>Precio Adquisición</th>
                            <th>Motivo Aprobación</th>
                            <th>Condición Aprobación</th>
                            </tr>
                        </thead>
                    </table>
                  </div> 
</div>
<script>
    
    
    var tblDetalleMed = document.getElementById('tblDetalleMed');
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlPDF(dataTable);
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
                aoData.push({"name": 'idSolicitud', "value": $('#idSolicitud').val()});
                aoData.push({"name": 'tipoMedicamento', "value": "1"});
            },
            "sAjaxSource": "balanceJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitud", sWidth: "5%"},
                {mData: "idSolicitudDetalleProducto", sWidth: "5%"},
                {mData: "fechaModificacion", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "descripcionProducto", sWidth: "10%"},
                {mData: "concentracion", sWidth: "10%"},
                {mData: "nombreFormaFarmaceutica", sWidth: "10%"},
                {mData: "formaPresentacion", sWidth: "10%"},
                {mData: "cantidad", sWidth: "8%"},
                {mData: "precio", sWidth: "8%"},
                {mData: "motivo", sWidth: "10%"},
                {mData: "condicion", sWidth: "10%"}
            ]
        });
    }
</script>