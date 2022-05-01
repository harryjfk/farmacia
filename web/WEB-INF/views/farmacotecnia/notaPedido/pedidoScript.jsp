<%@include file="../../includeTagLib.jsp" %>
<script>
    var idModulo = location.pathname.split('/')[3];
    var tblPedido = document.getElementById('tblPedido');
    $(document).ready(function () {
        $('#selAnio').val(new Date().toString('yyyy'));
    });
    $('#btnExcel').click(function (e) {
        e.preventDefault();
        var dataTable = $(tblPedido).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/NotaSalida/excel" />', dataTable);
    });
    $('#btnPDF').click(function (e) {
        e.preventDefault();
        var dataTable = $(tblPedido).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/NotaSalida/pdf" />', dataTable);
    });
    $('#btnAgregar').click(function (e) {
        e.preventDefault();
        window.location = '<c:url value="/dispensacion/' + idModulo + '/notapedido/registrar" />';
    });
    $('#btnBuscar').click(function (e) {
        e.preventDefault();
        reloadPedidos();
    });
    $(document).ready(function () {
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
    });

    $('#selAnio').change(function () {
        if (!Number($(this).val()) || $(this).val().length < 4) {
            limpiarSelect('#selMes');
        } else {
            var Data = {"id": "", "value": "mes", "text": "nombreMes"};
            llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function () {
                var mes = new Date().toString('MM');
                $('#selMes').val(mes);
                listarPedidos();
            });
        }
    });
    $('#txtAlmacenOrigen').change(function (e) {
        $("#idAlmacenOrigen").val($(this).val());
    });
    function reloadPedidos() {
        var dataTable = $(tblPedido).dataTable();
        dataTable.fnReloadAjax();
    }
    function listarPedidos() {
        if ($.fn.DataTable.fnIsDataTable(tblPedido)) {
            $(tblPedido).dataTable().fnDestroy();
        }
        $(tblPedido).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'idAlmacenOrigen', "value": $('#idAlmacenOrigen').val()});
                aoData.push({"name": 'idConcepto', "value": $('#idConcepto').val()});
            },
            "sAjaxSource": '<c:url value="/dispensacion/' + idModulo + '/notapedido/pedidosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting": [[1, 'desc']],
            "aoColumns": [
                {mData: "fechaRegistro", sWidth: "12%", "mRender": function (data, type, row) {
                        if (data === null) {
                            return '';
                        } else {
                            return new Date(data).toString(dateFormatJS);
                        }
                    }
                },
                {mData: "idAlmacenOrigen", sWidth: "28%", "mRender": function (data) {
                        if (!data) {
                            return '';
                        } else {
                            return data.descripcion;
                        }
                    }},
                {mData: "idTipoDocumentoMov", sWidth: "15%", "bSortable": false, "mRender": function (data, type, row) {
                        if (!data) {
                            return '';
                        } else {
                            return data.nombreTipoDocumentoMov;
                        }
                    }},
                {mData: "numeroDocumentoMov", sWidth: "10%", "bSortable": false},
                {mData: "concepto.nombreConcepto", sWidth: "15%", "bSortable": false},
                {mData: "idMovimiento", sWidth: "12%", "bSortable": false, "mRender": function (data, type, row) {

                        var editHTML = '';
                        var stateHTML = '';
                        var deleteHTML = '';
                        var printHTML = '';

                        < c:forEach var = "opcionSubmenu" items = "${opcionesSubmenu}" >
    <c:if test = "${opcionSubmenu.appOpcion == 'modificar'}" >
                        editHTML += '<a href="<c:url value="/dispensacion/' + idModulo +'/notapedido/modificar" />/' + data + '" class="separator-icon-td" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-pencil"></i></a>';
    </c:if>
    <c:if test = "${opcionSubmenu.appOpcion == 'estado'}" >
                        stateHTML += '<a href="#" class="separator-icon-td" data-id="' + data + '" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-refresh"></i></a>';
    </c:if>
    <c:if test = "${opcionSubmenu.appOpcion == 'eliminar'}" >
                        deleteHTML += '<a href="#" class="separator-icon-td" data-id="' + data + '" title="${opcionSubmenu.nombreOpcion}"><i class="splashy-remove"></i></a>';
    </c:if>
    <c:if test = "${opcionSubmenu.appOpcion == 'imprimir'}" >
                        printHTML += '<a href="#" class="separator-icon-td" data-id="' + data + '" title="${opcionSubmenu.nombreOpcion}" onclick="printPedido(' + data + ', event)"><i class="splashy-download"></i></a>';
    </c:if>
</c:forEach>

                        return printHTML;
                    }
                }
            ]
        });
    }
    function printPedido(idPedido, e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaSalida/imprimir" />/' + idPedido;
    }
</script>