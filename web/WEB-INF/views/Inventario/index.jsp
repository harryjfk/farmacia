<%@include file="../includeTagLib.jsp" %>
<style>
    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
    }
    .input-group-addon{
        cursor:pointer;
    }
</style>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Año</label>
                    <select id="selAnio" class="form-control" data-req="">
                        <c:forEach var="anio" items="${anios}">
                            <option value="${anio}">${anio}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Mes <span class="f_req">*</span></label>
                    <select id="selMes" class="form-control" data-req="">
                    </select>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Almacén <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacen" value=""/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacen" class="form-control" readonly="" data-req=""/>
                        <span class="input-group-addon" onclick="modalAlmacen(event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Inventario <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaProceso" name="fechaProceso" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon" onclick="mostrarCalendar('fechaProceso');"><i class="splashy-calendar_month"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Nro. Inventario</label>
                    <input type="text" id="numeroInventario" name="numeroInventario" class="form-control" readonly=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Cierre</label>
                    <input type="text" id="fechaCierre" name="fechaCierre" class="form-control" readonly=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Tipo Proceso<span class="f_req">*</span></label>
                    <select id="idTipoProceso" class="form-control" data-req="">                        
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="proceso" items="${procesos}">                                
                            <option value="${proceso.idTipoProceso}" ${proceso.idTipoProceso==100?'selected':''}>${proceso.nombreTipoProceso}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <input type="hidden" id="idInventario" />
            <button id="btnConsultar" class="btn btn-primary" type="submit" title="Consultar">Consultar</button>
            
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'procesar'}">
                    <button id="btnProcesar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                    <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                    <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
            </c:forEach>
        </div>
        <br>
        <div class="alert alert-info">Si se indica una fecha mayor a la actual, se procesara el inventario con la fecha actual.</div>
    </div>
</div>

<!--<div id="idBuscar"></div>-->
<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblInventario" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th colspan="3">Producto</th>
                    <th colspan="3">Valor Actual</th>
                    <th colspan="1">Conteo</th>
                    <th colspan="2">Faltantes</th>
                    <th colspan="2">Sobrantes</th>
                    <th colspan="1"></th>
                    <th colspan="1"></th>
                </tr>
                <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>F. F.</th>
                    <th>Cant.</th>
                    <th>Precio</th>
                    <th>Total</th>
                    <th>Físico</th>
                    <th>Cant.</th>
                    <th>Total</th>
                    <th>Cant.</th>
                    <th>Total</th>
                    <th>Total</th>
                    <th>Alter./Rotos</th>
                </tr>
            </thead>
        </table>
        <input type="hidden" id="idProducto" />
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblInventarioDetalle" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>                    
                    <th style="width: 12%;">Lote</th>
                    <th style="width: 12%;">Fecha Vto.</th>
                    <th style="width: 12%;">Cant.</th>
                    <th style="width: 12%;">Precio</th>
                    <th style="width: 12%;">Conteo Fis.</th>
                    <th style="width: 12%;">Alter./Rotos</th>
                    <th style="width: 16%;"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="modalAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Selecionar Almacén</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div class="col-sm-10 col-md-10">
                        <div id="almacenesTreeview"></div>
                    </div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cerrar</button>                
            </div>
        </div>
    </div>
</div>

<script>
    var tblInventario = document.getElementById('tblInventario');
    var date = Date.now();
    
    $(document).ready(function() {
        $('#btnProcesar').attr('disabled', 'disabled');
        $('#btnExcel').attr('disabled', 'disabled');
        $('#btnPDF').attr('disabled', 'disabled');        
        
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
        $('#fechaProceso').datepicker().datepicker("setDate", date);
        ocultarDetalle();
    });
    
    $('#selAnio').change(function() {
        var Data = {"id": "", "value": "mes", "text": "nombreMes"};
        llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function() {
            var mes = new Date().toString('MM');
            $('#selMes').val(mes);
        });
    });
    
    $('#almacenesTreeview').treeview({data: []});
    
    function ocultarDetalle() {
        $('#tblInventarioDetalle').css({'display': 'none'});
    }

    function modalAlmacen(e) {
        e.preventDefault();
        $('#modalAlmacen').modal('show');
        $.ajax({
            url: '<c:url value="/Almacen/almacenesTree" />',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {

                var idTipoAlmacenLogistica = 6;
                var idTipoAlmacenEspecializado = 16;

                var almacenLogistica = null;
                var almacenEspecializado=null;

                for (var i = 0; i <= dataResponse.length - 1; ++i) {
                    if(dataResponse[i].idTipoAlmacen==idTipoAlmacenLogistica){
                        almacenLogistica = dataResponse[i];
                    }
                    if(dataResponse[i].idTipoAlmacen==idTipoAlmacenEspecializado){
                        almacenEspecializado = dataResponse[i];
                    }
                }
                var treeView = new Array();
                for (var i = 0; i <= dataResponse.length - 1; ++i) {
                    if(dataResponse[i].idTipoAlmacen!=idTipoAlmacenLogistica){
                        var tree = new Object();
                        tree.text = dataResponse[i].almacen;
                        tree.idAlmacen = dataResponse[i].idAlmacen;
                        tree.nodes = new Array();
                        tree.selectable = false;
                        if (dataResponse[i].almacenes.length > 0) {
                            for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {
                                var treeLevelTwo = new Object();
                                treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                                treeLevelTwo.parent = dataResponse[i].almacen;
                                treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                                treeLevelTwo.nodes = new Array();
                                tree.nodes.push(treeLevelTwo);
                            }
                        }

                        treeView.push(tree);
                    }
                }

                $('#almacenesTreeview').treeview('remove');
                $('#almacenesTreeview').treeview({
                    levels: 1,
                    expandIcon: 'glyphicon glyphicon-chevron-right',
                    collapseIcon: 'glyphicon glyphicon-chevron-down',
                    nodeIcon: '',
                    data: treeView,
                    onNodeSelected: function(event, node) {
                        $('#txtAlmacen').val(node.parent + ' - ' + node.text);
                        $('#idAlmacen').val(node.idAlmacen);
                        $('#modalAlmacen').modal('hide');
                    }
                });
                if ($('#idAlmacen').val() != '') {
                    $("#almacenesTreeview").treeview("activateNode", ['idAlmacen', $('#idAlmacen').val()]);
                }
            }
        });
    }

    $('#btnConsultar').click(function(e) {
        e.preventDefault();

        listarInventario();
    });
    
    $('#btnExcel').click(function (e){
        e.preventDefault();

        if(validar() == false){
            return;
        }

        var dataTable = $(tblInventario).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Inventario/excel" />', dataTable);
    });
    
    $('#btnPDF').click(function (e){
        e.preventDefault();

        if(validar() == false){
            return;
        }

        var dataTable = $(tblInventario).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Inventario/pdf" />', dataTable);
    });    

    function validar(){
        var dataResponse = validateForm('[data-req]');
        
        var fechaProceso = Date.parseExact($('#fechaProceso').val(), dateFormatJS);
        
        if ($('#fechaProceso').val().length > 0) {
            if (fechaProceso == null) {
                dataResponse.mensajesRepuesta.push('Fecha Inventario inválida.');
                dataResponse.estado = false;
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return false;
        }
        
        return true;
    }
    
    function reloadInventario(e){
        e.preventDefault();
        
        var dataTable = $('#tblInventario').dataTable();
        
        dataTable.fnReloadAjax();
    }
    
    function listarInventario() {
        if(validar() == false){
            return;
        }
        
        var fechaProceso = Date.parseExact($('#fechaProceso').val(), dateFormatJS);

        ocultarDetalle();

        
        if ($.fn.DataTable.fnIsDataTable(tblInventario)) {
            $(tblInventario).dataTable().fnDestroy();
        }

        $(tblInventario).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
                aoData.push({"name": 'idTipoProceso', "value": $('#idTipoProceso').val()});
                aoData.push({"name": 'fechaProceso', "value": fechaProceso.getTime()});
            },
            "sAjaxSource": '<c:url value="/Inventario/inventarioTotalesJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aaSorting":[[1,"asc"]],
            "aoColumns": [
                {mData: "idProducto", "bSortable": false},
                {mData: "descripcion", "bSortable": false},
                {mData: "formaFarmaceutica", "bSortable": false},
                {mData: "cantidad", "bSortable": false},
                {mData: "precio", "bSortable": false},
                {mData: "total", "bSortable": false},
                {mData: "conteo", "bSortable": false},
                {mData: "cantidadFaltante", "bSortable": false},
                {mData: "totalFaltante", "bSortable": false},
                {mData: "cantidadSobrante", "bSortable": false},
                {mData: "totalSobrante", "bSortable": false},
                {mData: "totalFisico", "bSortable": false},
                {mData: "cantidadAlterado", "bSortable": false}
            ],
            "fnDrawCallback": function(oSettings) {
                if ($(tblInventario).dataTable().fnSettings().aoData.length > 0) {
                    $('#tblInventario tbody tr td').click(function() {
                        $('#tblInventario tbody tr td').css({'background-color': ''});
                        $(this).parent().find('td').css({'background-color': '#efefef'});
                        $('#idProducto').val($(this).parent().find('td:eq(0)').text());
                        seleccionarProducto();
                    });                    
                }
            },
            "fnServerData": function(sSource, aoData, fnCallback, oSettings) {
                oSettings.jqXHR = $.ajax({
                    "dataType": 'json',
                    "type": "GET",
                    "url": sSource,
                    "data": aoData,
                    "success": fnCallback
                }).done(function(data) {
                    $('#idInventario').val(data.extraData["idInventario"]);
                    $('#numeroInventario').val(data.extraData["numeroInventario"]);                    
                    
                    if (data.extraData["fechaCierre"] != null) {                        
                        $('#btnProcesar').attr('disabled', 'disabled');
                        $('#btnExcel').removeAttr('disabled');
                        $('#btnPDF').removeAttr('disabled');
                        $('#fechaCierre').val(new Date(data.extraData["fechaCierre"]).toString(dateFormatJS));
                    }else{
                        $('#btnProcesar').removeAttr('disabled');
                        $('#btnExcel').attr('disabled', 'disabled');
                        $('#btnPDF').attr('disabled', 'disabled');
                        $('#fechaCierre').val('');
                    }
                });
            }
        });
        var divBuscar  = $('#idBuscar');
        divBuscar.html('<div class="dataTables_filter" id="tblInventario_filter"><label><input type="text" placeholder="Buscar" aria-controls="tblInventario"></label></div>');
    }

    function seleccionarProducto() {
        var idProducto = $('#idProducto').val();        
        
        $.ajax({
            url: '<c:url value="/Inventario/inventarioProductosJSON" />?idProducto=' + idProducto + '&idInventario=' + $('#idInventario').val(),
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                $('#tblInventarioDetalle').css({'display': ''});
                var $tbody = $('#tblInventarioDetalle tbody');
                $tbody.find('tr').remove();
                for (var i = 0; i < dataResponse.length; ++i) {
                    var $tr = $('<tr></tr>');                    
                    var $tdLote = $('<td></td>');
                    var $tdFechaVto = $('<td></td>');
                    var $tdCant = $('<td></td>');
                    var $tdPrecio = $('<td></td>');
                    var $tdConteoFis = $('<td></td>');
                    var $tdAlterRotos = $('<td></td>');
                    var $tdAccion = $('<td><i class="splashy-check" data-id="' + dataResponse[i]["idInventarioProducto"] + '" onclick="modificarProducto(this)"></i></td>');
                    var $inputConteoFis = $('<input data-conteo="" class="form-control input-sm" data-numeric=""/>');
                    var $inputAlterotos = $('<input data-alterado="" class="form-control input-sm" data-numeric=""/>');

                    $tdLote.text(dataResponse[i]["lote"]);
                    $tdFechaVto.text(new Date(dataResponse[i]["fechaVencimiento"]).toString(dateFormatJS));
                    $tdCant.text(dataResponse[i]["cantidad"]);
                    $tdPrecio.text(dataResponse[i]["precio"]);
                    
                    if($('#fechaCierre').val().length == 0){
                        $inputConteoFis.val(dataResponse[i]["conteo"]);
                        $inputAlterotos.val(dataResponse[i]["cantidadAlterado"]);
                        $tdConteoFis.append($inputConteoFis);
                        $tdAlterRotos.append($inputAlterotos);
                    }else{
                        $tdConteoFis.append(dataResponse[i]["conteo"]);
                        $tdAlterRotos.append(dataResponse[i]["cantidadAlterado"]);
                    }
                 
                    $tr.append($tdLote);
                    $tr.append($tdFechaVto);
                    $tr.append($tdCant);
                    $tr.append($tdPrecio);
                    $tr.append($tdConteoFis);
                    $tr.append($tdAlterRotos);
                    
                    if($('#fechaCierre').val().length > 0){
                        $tdAccion = '<td></td>';
                    }
                    
                    $tr.append($tdAccion);
                    $tbody.append($tr);
                }
                
                if($('#fechaCierre').val().length == 0){
                    trAgregarProducto($tbody);
                }
                
                $('[data-numeric=""]').numeric({decimal: false, negative: false});
                $('[data-decimal=""]').numeric({decimalPlaces: 8, negative: false});
            }
        });
    }

    function trAgregarProducto(tbody) {
        var trAgregarProducto = $('<tr>' +                
                '<td><input type="text" data-lote="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><input type="text" data-fechaVencimiento="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><input type="text" data-numeric="" data-cantidad="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><input type="text" data-decimal="" data-precio="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><input type="text" data-numeric="" data-conteo="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><input type="text" data-numeric="" data-cantidadAlterado="" class="form-control input-sm" data-req-agregarDetalle=""/></td>' +
                '<td><i class="splashy-check" onclick="agregarProducto(this)"></i></td>' +
                '</tr>');
        tbody.append(trAgregarProducto);
    }

    function agregarProducto(element) {
        var $tr = $(element).parent().parent();

        var dataResponse = validateElement('#tblInventarioDetalle [data-req-agregarDetalle]');

        var fechaVencimiento = Date.parseExact($tr.find('[data-fechaVencimiento]').val(), dateFormatJS);
        if ($tr.find('[data-fechaVencimiento]').val().length > 0) {
            if (fechaVencimiento == null) {
                dataResponse.mensajesRepuesta.push('Fecha Vencimiento inválida.');
                dataResponse.estado = false;
            }
        }

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var productoDetalle = new Object();
        productoDetalle.idInventario = $('#idInventario').val();
        productoDetalle.idProducto = $('#idProducto').val();
        productoDetalle.lote = $tr.find('[data-lote]').val();
        productoDetalle.fechaVencimiento = fechaVencimiento.getTime();
        productoDetalle.cantidad = $tr.find('[data-cantidad]').val();
        productoDetalle.precio = $tr.find('[data-precio]').val();
        productoDetalle.conteo = $tr.find('[data-conteo]').val();
        productoDetalle.cantidadAlterado = $tr.find('[data-alterado]').val();

        $.ajax({
            url: '<c:url value="/Inventario/agregarDetalle" />',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(productoDetalle),
            success: function(dataResponse) {
                functionResponse(dataResponse, function() {
                    listarInventario();
                    seleccionarProducto();
                });
            }
        });
    }
    
    function modificarProducto(element){
        var $tr = $(element).parent().parent();

        var dataResponse = validateElement('');
       
        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }
    
        var conteo = $tr.find('[data-conteo]').val();
        var alterado = $tr.find('[data-alterado]').val();        

        $.ajax({
            url: '<c:url value="/Inventario/modificarConteo" />?conteo=' + conteo + '&alterado=' + alterado + '&idInventarioProducto=' + $(element).attr('data-id'),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function() {
                    listarInventario();
                    seleccionarProducto();
                });
            }
        });
    }
    
    $('#btnProcesar').click(function (e){
        e.preventDefault();
        
         $.ajax({
            url: '<c:url value="/Inventario/procesar" />?&idInventario=' + $('#idInventario').val(),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function() {
                    listarInventario();                    
                });
            }
        });
    });
</script>