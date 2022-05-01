<%@include file="../includeTagLib.jsp" %>

<h3 class="heading">Listado de solicitudes</h3>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <button id="btnBuscar" class="btn btn-primary">Buscar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>M&eacute;dico</label>
                    <input type="text" id="medico" name="medico" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Establecimiento</label>
                    <input type="text" id="establecimiento" name="establecimiento" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Instituci&oacute;n</label>
                    <input type="text" id="institucion" name="institucion" class="form-control uppercase" maxlength="40" autocomplete="off"/>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblSolicitud" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>M&eacute;dico</th>
                <th>Establecimiento</th>
                <th>Instituci&oacute;n</th>
                <th>Fecha</th>
                <th>Estado</th>
                <th>Acción</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    var tblSolicitud = document.getElementById('tblSolicitud');

    $(document).ready(function() {
        listarSolicitudes();
    });

    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSolicitud).dataTable();
        window.location = getUrlExcel(dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblSolicitud).dataTable();
        window.location = getUrlPDF(dataTable);
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        reloadSolicitudes();
    });
    
    function reloadSolicitudes(){
         var dataTable = $(tblSolicitud).dataTable();
         dataTable.fnReloadAjax();
    }

    function listarSolicitudes() {
        $(tblSolicitud).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'establecimiento', "value": $('#establecimiento').val()});
                aoData.push({"name": 'institucion', "value": $('#institucion').val()});
                aoData.push({"name": 'medico', "value": $('#medico').val()});
            },
            "sAjaxSource": "solicitudesJSON",
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idSolicitud", sWidth: "8%"},
                {mData: "medico", sWidth: "23%"},
                {mData: "establecimiento", sWidth: "20%"},
                {mData: "institucion", sWidth: "20%"},
                {mData: "fecha", sWidth: "10%", "mRender": function(data, type, full) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "activoTexto", sWidth: "8%"},
                {mData: "idSolicitud", sWidth: "25%", "bSortable": false, "mRender": function(data, type, row) {
                        var editHTML = '';
                        editHTML = '<a href="balance?idSolicitud=' + data + '&tipoMedicamento=1" class="separator-icon-td"><i class="splashy-pencil" title="Balance"></i></a>';
                        return editHTML;
                 }
                }
            ]
        });

    }
</script>