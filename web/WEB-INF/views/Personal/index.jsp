<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-10 col-md-10">       
        <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
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
    <div class="col-sm-12 col-md-12">
        <table id="tblPersonal" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Nombre Personal</th>                   
                <th>Tipo Documento</th>
                <th>Nro Documento</th>
                <th>Unidad</th>
                <th>Cargo</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script>

    $(document).ready(function() {
        listarPersonal();
    });

    function listarPersonal() {

        $('#tblPersonal').dataTable({
            "sAjaxSource": '<c:url value="/Personal/personalJSON"/>',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idPersonal", sWidth: "8%", "bSortable": false},
                {mData: "nombreCompleto", sWidth: "35%"},
                {mData: "tipoDocumento", sWidth: "8%"},
                {mData: "nroDocumento", sWidth: "9%"},
                {mData: "unidad", sWidth: "23%"},
                {mData: "cargo", sWidth: "17%"}
            ]
        });
    }
</script>
