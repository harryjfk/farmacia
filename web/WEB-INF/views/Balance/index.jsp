<%@include file="../includeTagLib.jsp" %>

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
                <div class="col-sm-3 col-md-3" style="display:none;">
                    <label>Establecimiento</label>
                    <input type="text" id="establecimiento" name="establecimiento" class="form-control uppercase" value="${solicitud.establecimiento}" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-4 col-md-4" style="display:none;">
                    <label>Instituci&oacute;n</label>
                    <input type="text" id="institucion" name="institucion" class="form-control uppercase" maxlength="40" value="${solicitud.institucion}" autocomplete="off"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha</label>
                    <div class="input-group">
                        <input type="text" id="fechita" name="fechita" class="form-control uppercase" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechita');"></i></span>
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
                    <!--
                    <th>Establecimiento</th>
                    <th>Instituci&oacute;n</th>
                    -->
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

    $('#fechita').val(new Date().toString(dateFormatJS));

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
                aoData.push({"name": 'fechita', "value": $('#fechita').val()});
            },
            "sAjaxSource": '<c:url value="/Balance/balanceJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitud", sWidth: "5%"},
                {mData: "idSolicitudDetalleProducto", sWidth: "5%"},
                //{mData: "establecimiento", sWidth: "30%"},
                //{mData: "institucion", sWidth: "30%"},
                {mData: "fechaAprobacion", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "descripcionProducto", sWidth: "30%"},
                {mData: "concentracion", sWidth: "20%"},
                {mData: "nombreFormaFarmaceutica", sWidth: "20%"},
                {mData: "formaPresentacion", sWidth: "10%"},
                {mData: "cantidad", sWidth: "8%"},
                {mData: "precio", sWidth: "8%"},
                {mData: "motivo", sWidth: "10%"},
                {mData: "condicion", sWidth: "10%"}
            ]
        });
    }
</script>