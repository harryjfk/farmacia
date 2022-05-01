<%@include file="../includeTagLib.jsp" %>
<script>
    $(function() {
        $('#anio').validCampo('0123456789');
    });
</script>

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
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Inicio</label>
                    <div class="input-group">
                        <input type="text" id="fecIni" name="fecIni" class="form-control uppercase" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecIni');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Fin</label>
                    <div class="input-group">
                        <input type="text" id="fecFin" name="fecFin" class="form-control uppercase" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecFin');"></i></span>
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
                    <th>Fecha Adquirido</th>
                    <th>Medicamento</th>
                    <th>Concentración</th>
                    <th>Forma Presentación</th>
                    <th>Forma Farmaceútica</th>
                    <th>Año</th>
                    <th>Enero</th>
                    <th>Febrero</th>
                    <th>Marzo</th>
                    <th>Abril</th>
                    <th>Mayo</th>
                    <th>Junio</th>
                    <th>Julio</th>
                    <th>Agosto</th>
                    <th>Setiembre</th>
                    <th>Octubre</th>
                    <th>Noviembre</th>
                    <th>Diciembre</th>
                </tr>
            </thead>
        </table>
    </div> 
</div>
<script>

    $('#fecIni').val(new Date().toString(dateFormatJS));
    $('#fecFin').val(new Date().toString(dateFormatJS));

    var tblDetalleMed = document.getElementById('tblDetalleMed');

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlConsultaExcelPDF("excelHistorico", dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblDetalleMed).dataTable();
        window.location = getUrlConsultaExcelPDF("pdfHistorico", dataTable);
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
                aoData.push({"name": 'fecIni', "value": $('#fecIni').val()});
                aoData.push({"name": 'fecFin', "value": $('#fecFin').val()});
            },
            "sAjaxSource": '<c:url value="/Solicitud/consultaHistoricoJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r><'row'<'col-sm-5'><'col-sm-7'>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "fechaAprobacion", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "descripcionProducto", sWidth: "8%"},
                {mData: "concentracion", sWidth: "8%"},
                {mData: "formaPresentacion", sWidth: "8%"},
                {mData: "nombreFormaFarmaceutica", sWidth: "8%"},
                {mData: "anio", sWidth: "10%"},
                {mData: "cantEnero", sWidth: "10%"},
                {mData: "cantFebrero", sWidth: "8%"},
                {mData: "cantMarzo", sWidth: "8%"},
                {mData: "cantAbril", sWidth: "8%"},
                {mData: "cantMayo", sWidth: "8%"},
                {mData: "cantJunio", sWidth: "8%"},
                {mData: "cantJulio", sWidth: "8%"},
                {mData: "cantAgosto", sWidth: "8%"},
                {mData: "cantSetiembre", sWidth: "8%"},
                {mData: "cantOctubre", sWidth: "8%"},
                {mData: "cantNoviembre", sWidth: "8%"},
                {mData: "cantDiciembre", sWidth: "8%"}
            ]
        });
    }
</script>