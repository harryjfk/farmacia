<%@include file="../includeTagLib.jsp" %>

<style>
    #modalPrecio .modal-dialog
    {
        margin-top: 5%;
        width: 50%;
    }
</style>

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
    <div class="col-sm-9 col-md-9">
        <input type="hidden" id="seleccionadoProducto"/>
        <input type="hidden" id="porcentajeDistribucion" value="${porcentajeDistribucion}"/>
        <input type="hidden" id="porcentajeOperacion" value="${porcentajeOperacion}"/>
        <table id="tblPrecio" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th></th>
                    <th>Código Sismed</th>
                    <th>Medicamento o Insumo</th>
                    <th>Precio Adquisición</th>
                    <th>Precio Distribución</th>
                    <th>Precio Operación</th>                    
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="row">
    <div id="historicoPrecio" class="col-sm-9 col-md-9" style="display:none;">        
        <div class="dataTables_wrapper">
            <div class="row">
                <div class="col-sm-6">
                    <input type="submit" id="btnAgregarPrecio" value="Agregar Precio" class="btn btn-primary"/>
                </div>                
            </div>
            <table id="tblHistoricoPrecio" class="table table-bordered table-striped dTableR" >
                <thead>
                <th>Fecha Registro</th>
                <th>Tipo</th>
                <th>Precio Adquisición</th>
                <th>Precio Distribución</th>
                <th>Precio Operación</th>                
                </thead>
                <tbody></tbody>
            </table>
        </div>        
    </div>
</div>

<div class="modal fade" id="modalPrecio">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Registar nuevo precio</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-3 col-md-3">
                        <label>Fecha Registro</label>
                        <input type="text" id="fechaRegistro" class="form-control" readonly=""/>
                    </div>
                    <div class="col-sm-3 col-md-3">
                        <label>Código SISMED</label>
                        <input type="text" id="codigoSismed" class="form-control" readonly=""/>
                    </div>                    
                    <div class="col-sm-6 col-md-6">
                        <label>Descripción</label>
                        <input type="text" id="descripcionProducto" class="form-control" readonly=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-md-4">
                        <label>Precio Adquisición</label>
                        <input type="text" id="precioAdquisicion" class="form-control" data-productoprecio=""/>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Precio Distribución</label>
                        <input type="text" id="precioDistribucion" class="form-control" data-productoprecio=""/>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Precio Operación</label>
                        <input type="text" id="precioOperacion" class="form-control" data-productoprecio=""/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div class="col-sm-3 col-md-3">
                        <label>&nbsp;</label>
                        <label class="h3">Sugerido</label>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Precio Distribución</label>
                        <input type="text" id="precioDistribucionSugerido" class="form-control" readonly="" data-productoprecio=""/>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <label>Precio Operación</label>
                        <input type="text" id="precioOperacionSugerido" class="form-control" readonly="" data-productoprecio=""/>
                    </div>
                </div>
                <div id="divMessage"></div>
            </div>
            <div class="modal-footer">
                <button id="btnCerrar" data-dismiss="modal" class="btn btn-primary">Cancelar</button>
                <button id="btnGuardar" class="btn btn-default" data-tipo="">Guardar</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#fechaRegistro').val(new Date().toString(dateFormatJS));

    var tblPrecio = document.getElementById('tblPrecio');

    $(document).ready(function() {
        listarPrecios();
        $('#precioAdquisicion').numeric({decimalPlaces: 8, negative: false});
        $('#precioDistribucion').numeric({decimalPlaces: 8, negative: false});
        $('#precioOperacion').numeric({decimalPlaces: 8, negative: false});
    });
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblPrecio).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Precio/excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblPrecio).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Precio/pdf"/>', dataTable);
    });

    function listarPrecios() {

        if ($.fn.DataTable.fnIsDataTable(tblPrecio)) {
            $(tblPrecio).dataTable().fnDestroy();
        }

        $(tblPrecio).dataTable({
            "fnServerParams": function(aoData) {
            },
            "sAjaxSource": '<c:url value="/Precio/preciosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "idProducto", sWidth: "8%", "bSortable": false, "mRender": function(data) {
                        return '<i class="splashy-arrow_medium_right" onclick="historicoPrecioTabla(' + data + ', this)"></i>';
                    }
                },
                {mData: "codigoSismed", sWidth: "10%", "bSortable": false},
                {mData: "nombreProducto", sWidth: "52%", "bSortable": false},
                {mData: "precioAdquisicion", sWidth: "10%", "bSortable": false},
                {mData: "precioDistribucion", sWidth: "10%", "bSortable": false},
                {mData: "precioOperacion", sWidth: "10%", "bSortable": false}
            ]
        });
    }

    function historicoPrecioTabla(idProducto, element) {
        $('#seleccionadoProducto').val(idProducto);

        $('#tblPrecio').find('tr').find('td').css({'background-color': ''});
        $(element).parent().parent().find('td').css({'background-color': '#efefef'});
        $(element).parent().parent().find('td:eq(0)').attr('idProducto', idProducto);

        var codigoSismed = $(element).parent().parent().find('td:eq(1)').text();
        var descripcionProducto = $(element).parent().parent().find('td:eq(2)').text();

        $('#codigoSismed').val(codigoSismed);
        $('#descripcionProducto').val(descripcionProducto);
        
        historicoPrecio();
    }

    function historicoPrecio() {

        var idProducto = $('#seleccionadoProducto').val();

        $.ajax({
            url: '<c:url value="/Precio/historicoJSON" />/' + idProducto,
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {

                var $historicoPrecio = $('#historicoPrecio');
                $historicoPrecio.css({'display': ''});

                var $tblHistoricoPrecio = $('#tblHistoricoPrecio');
                var $tbody = $tblHistoricoPrecio.find('tbody');
                $tbody.find('tr').remove();

                for (var i = 0; i < dataResponse.length; ++i) {

                    var row = $('<tr></tr>');
                    var cellFecha = $('<td></td>)');
                    var cellTipo = $('<td></td>)');
                    var cellPrecio = $('<td></td>)');
                    var cellPrecioDistribucion = $('<td></td>)');
                    var cellPrecioOperacion = $('<td></td>)');

                    cellFecha.append(new Date(dataResponse[i].fechaRegistro).toString(dateFormatJS));
                    cellTipo.append(dataResponse[i].tipo);
                    cellPrecio.append(dataResponse[i].precioAdquisicion);
                    cellPrecioDistribucion.append(dataResponse[i].precioDistribucion);
                    cellPrecioOperacion.append(dataResponse[i].precioOperacion);

                    row.append(cellFecha);
                    row.append(cellTipo);
                    row.append(cellPrecio);
                    row.append(cellPrecioDistribucion);
                    row.append(cellPrecioOperacion);

                    $tbody.append(row);
                }
            }
        });
    }
    
    $('#btnAgregarPrecio').click(function (){
        $('[data-productoprecio=""]').val('');
        $('#divMessage').html('');
        $('#modalPrecio').modal('show');
    });

    $('#precioAdquisicion').change(function() {
        var precioAdquisicion = parseFloat($(this).val());
        
        var precioDistribucion = precioAdquisicion + (parseFloat($('#porcentajeDistribucion').val()) * precioAdquisicion);
        var porcentajeOperacion = precioAdquisicion + (parseFloat($('#porcentajeOperacion').val()) * precioAdquisicion);

        $('#precioDistribucionSugerido').val(precioDistribucion);
        $('#precioOperacionSugerido').val(porcentajeOperacion);
    });

    $('#btnGuardar').click(function() {

        var productoPrecio = {
            idProducto: $('#seleccionadoProducto').val(),
            precioAdquisicion: $('#precioAdquisicion').val(),
            precioDistribucion: $('#precioDistribucion').val(),
            precioOperacion: $('#precioOperacion').val()
        };
        
        $.ajax({
            url: '<c:url value="/Precio/registrar" />',
            data: JSON.stringify(productoPrecio),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (jsonToDivError(dataResponse, '#modalPrecio #divMessage')) {
                    $('#modalPrecio').modal('hide');
                    historicoPrecio();
                    var $trs = $('#tblPrecio').find('tbody tr');
                    for (var i = 0; i < $trs.length; ++i) {
                        if($('#seleccionadoProducto').val() == $($trs[i]).find('td:eq(0)').attr('idProducto')){
                            $($trs[i]).find('td:eq(3)').text($('#precioAdquisicion').val());
                            $($trs[i]).find('td:eq(4)').text($('#precioDistribucion').val());
                            $($trs[i]).find('td:eq(5)').text($('#precioOperacion').val());
                        }
                    }
                }
            }
        });
    });
</script>