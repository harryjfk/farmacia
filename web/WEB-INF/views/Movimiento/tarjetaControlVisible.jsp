<%@include file="../includeTagLib.jsp" %>
<style>
    #modalProducto .modal-dialog
    {
        margin-top: 1%;
        width: 60%;
    }
    
    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
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
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Desde</label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta</label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
                    </div>
                </div>           
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacen" />
                    <div class="input-group">
                        <input type="text" id="txtAlmacen" class="form-control" readonly="" data-req="" />
                        <span class="input-group-addon" onclick="modalAlmacen(event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div> 
                <div class="col-sm-6 col-md-6">
                    <label>Producto <span class="f_req">*</span></label>
                    <input type="hidden" id="idProducto" />
                    <div class="input-group">
                        <input type="text" id="txtProducto" class="form-control" readonly="" data-req=""/>
                        <span class="input-group-addon" onclick="modalProductos(event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'consulta'}">
                    <button id="btnBuscar" class="btn btn-primary" type="submit" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                    <button id="btnLimpiarBusqueda" class="btn btn-primary" title="Limpiar Búsqueda">Limpiar Búsqueda</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'pdf'}">
                    <button id="btnPDF" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
                <c:if test="${opcionSubmenu.appOpcion == 'excel'}">
                    <button id="btnExcel" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<div id="divTblTarjetaControl" class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblTarjetaControl" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>                
                <th>F. Registro</th>
                <th>Tipo</th>
                <th>N° Movimiento</th>
                <th>Producto</th>
                <th>Concepto</th>                
                <th>Lote</th>
                <th>Fecha Vcto.</th>
                <th>Saldo Inicial</th>
                <th>Ingresos</th>
                <th>Salidas</th>
                <th>Saldo</th>
                </tr>
            </thead>                    
        </table>
    </div>        
</div>

<div class="modal fade" id="modalProducto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProducto" class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Descripción</label>
                                    <input type="text" id="descripcion" name="descripcion" class="form-control" maxlength="100"/>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>&nbsp;</label>
                                    <button id="btnBuscarProducto" class="btn btn-primary">Buscar</button>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-4 col-md-4">
                                    <label>Forma Farmaceutica</label>
                                    <select id="idFormaFarmaceutica" class="form-control">
                                        <option value="0">-TODOS-</option>
                                        <c:forEach var="formaFarmaceutica" items="${formasFarmaceuticas}">                                
                                            <option value="${formaFarmaceutica.idFormaFarmaceutica}">${formaFarmaceutica.nombreFormaFarmaceutica}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Tipo de Producto</label>
                                    <select id="idTipoProducto" class="form-control">
                                        <option value="0">-TODOS-</option>
                                        <c:forEach var="tipoProducto" items="${tiposProducto}">                                
                                            <option value="${tipoProducto.idTipoProducto}">${tipoProducto.nombreTipoProducto}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Unidad de Medida</label>
                                    <select id="idUnidadMedida" class="form-control">
                                        <option value="0">-TODOS-</option>
                                        <c:forEach var="unidadMedida" items="${unidadesMedida}">                                
                                            <option value="${unidadMedida.idUnidadMedida}">${unidadMedida.nombreUnidadMedida}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <table id="tblProductos" class="table table-bordered table-striped dTableR">
                                <thead>
                                    <tr>
                                    <th></th>
                                    <th>Descripcion</th>
                                    <th>Forma Farmacéutica</th>
                                    <th>Tipo de Producto</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnCerrarModalProducto" data-dismiss="modal" class="btn btn-primary">Cerrar</button>                
            </div>
        </div>
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
    var tblProductos = document.getElementById('tblProductos');
    var tblTarjetaControl = document.getElementById('tblTarjetaControl'); 
    var $selMes = $('#selMes');
    $(document).ready(function() {
        $('#almacenesTreeview').treeview({data: []});
       $('#btnLimpiarBusqueda').click();
    });
    
    $('#btnLimpiarBusqueda').click(function (e){
        e.preventDefault();
        
        $('#fechaDesde,#fechaHasta,#idAlmacen,#txtAlmacen').val('');
        $('#btnTodos').click();  
        $('#divTblTarjetaControl').attr('style', 'display:none');
        
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();        
    });

    $('#selAnio').change(function() {
        var Data = {"id": "", "value": "mes", "text": "nombreMes"};
        llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function() {
            var mes = new Date().toString('MM');
            if($selMes.find("option[value='" + mes + "']").length > 0){
                $selMes.val(mes);
                $selMes.change();
            }else{
                $selMes.val('-1');
            }
        });
    });
    
    $selMes.change(function (){
        if($(this).val() != '-1'){
            var mes = parseInt($('#selMes').val());
            var anio = parseInt($('#selAnio').val());

            var fechaMin = new Date(anio, mes - 1, 1);
            var fechaMax = new Date(anio, mes - 1).moveToLastDayOfMonth();

            $("#fechaDesde").datepicker("option", "minDate", fechaMin);
            $("#fechaDesde").datepicker("option", "maxDate", fechaMax);
            $("#fechaHasta").datepicker("option", "minDate", fechaMin);
            $("#fechaHasta").datepicker("option", "maxDate", fechaMax);
        }
    });

    function modalAlmacen(e) {
        e.preventDefault();

        $('#modalAlmacen').modal('show');

        $.ajax({
            url: '<c:url value="/Almacen/almacenesTree" />',
            type: 'GET',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {

                var treeView = new Array();
                for (var i = 0; i <= dataResponse.length - 1; ++i) {
                    var tree = new Object();
                    tree.text = dataResponse[i].almacen;
                    tree.idAlmacen = dataResponse[i].idAlmacen;
                    tree.selectable = false;
                    tree.nodes = new Array();
                    
                    if (dataResponse[i].almacenes.length > 0) {
                        for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {
                            var treeLevelTwo = new Object();
                            treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                            treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                            treeLevelTwo.parent = dataResponse[i].almacen;
                            treeLevelTwo.nodes = new Array();
                            tree.nodes.push(treeLevelTwo);
                        }
                    }

                    treeView.push(tree);
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
                        $('#idProducto').val('');
                        $('#txtProducto').val('');
                    },
                    onNodeUnselected: function(event, node) {
                        $('#txtAlmacen').val('');
                        $('#idAlmacen').val('');
                        $('#idProducto').val('');
                        $('#txtProducto').val('');
                    }
                });

                var idAlmacen = $('#idAlmacen').val();
                if (idAlmacen.length) {
                    $('#almacenesTreeview').treeview('selectByKey', [idAlmacen, 'idAlmacen',{silent:true}]);
                }
            }
        });
    }

    $('#buscaProducto').keyup(function(e) {
        e.preventDefault();

        if (e.which === 13) {
            $('#btnBuscarProducto').click();
        }
    });

    $('#btnBuscarProducto').click(function(e) {
        e.preventDefault();

        if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
            $(tblProductos).dataTable().fnDestroy();
        }

        $(tblProductos).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'descripcion', "value": $('#descripcion').val()});
                aoData.push({"name": 'idFormaFarmaceutica', "value": $('#idFormaFarmaceutica').val()});
                aoData.push({"name": 'idTipoProducto', "value": $('#idTipoProducto').val()});
                aoData.push({"name": 'idUnidadMedida', "value": $('#idUnidadMedida').val()});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
            },
            "sAjaxSource": '<c:url value="/Producto/productosPorAlmacenJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idProducto", sWidth: "8%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectProduct(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "descripcion", sWidth: "52%"},
                {mData: "formaFarmaceutica", sWidth: "20%", "bSortable": false},
                {mData: "tipoProducto", sWidth: "20%", "bSortable": false}                
            ]
        });
    });

    function modalProductos(e) {
        e.preventDefault();

        if ($('#idAlmacen').val().length == 0) {
            var dataResponse = new Object();
            dataResponse.mensajesRepuesta = new Array();
            dataResponse.mensajesRepuesta.push('Debe seleccionar un almacén para cargar sus productos.');
            errorResponse(dataResponse);
            return;
        }

        $('#descripcion').val('');
        $('#idFormaFarmaceutica').val('0');
        $('#idTipoProducto').val('0');
        $('#idUnidadMedida').val('0');

        $('#modalProducto').modal('show');

        $('#btnBuscarProducto').click();
    }

    function selectProduct(idProducto, element) {
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(1)').text());
        $('#idProducto').val(idProducto);
        $('#btnCerrarModalProducto').click();
    }
    
    function validate(){
        var dataResponse = validateForm('[data-req]');
        
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);
        
        if ($('#fechaHasta').val().length > 0) {
            if (fechaHasta == null) {
                dataResponse.mensajesRepuesta.push('Fecha hasta inválida.');
                dataResponse.estado = false;
            }
        }

        if ($('#fechaDesde').val().length > 0) {
            if (fechaDesde == null) {
                dataResponse.mensajesRepuesta.push('Fecha desde inválida.');
                dataResponse.estado = false;
            }
        }
        
        if (fechaDesde != null && fechaHasta != null) {
            if (fechaDesde.compareTo(fechaHasta) == 1) {
                dataResponse.mensajesRepuesta.push('Fecha hasta debe ser mayor que la fecha desde.');
                dataResponse.estado = false;
            }
        }
        
        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return false;
        }
        return true;
    }
    
    $('#btnBuscar').click(function (e){
        e.preventDefault();
        
        if(validate() == false){
            return;
        }
        $('#divTblTarjetaControl').removeAttr('style');
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);
        
        if ($.fn.DataTable.fnIsDataTable(tblTarjetaControl)) {
            $(tblTarjetaControl).dataTable().fnDestroy();
        }

        $(tblTarjetaControl).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'fechaDesde', "value": ((fechaDesde == null) ? '' : fechaDesde.getTime())});
                aoData.push({"name": 'fechaHasta', "value": ((fechaHasta == null) ? '' : fechaHasta.getTime())});
                aoData.push({"name": 'idAlmacen', "value": $('#idAlmacen').val()});
                aoData.push({"name": 'idProducto', "value": $('#idProducto').val()});
            },
            "sAjaxSource": '<c:url value="/TarjetaControlVisible/JSON" />',            
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 10,
            "aoColumns": [
                {mData: "fechaRegistro", "bSortable": false, "mRender": function(data){
                        return new Date(data).toString(dateFormatJS);
                    }
                },
                {mData: "tipoMovimiento", "bSortable": false},
                {mData: "numeroMovimiento", "bSortable": false},
                {mData: "producto", "bSortable": false},                
                {mData: "nombreConcepto", "bSortable": false},
                {mData: "lote", "bSortable": false},
                {mData: "fechaVencimiento", "bSortable": false, "mRender": function(data){
                        if(data != null){
                            return new Date(data).toString(dateFormatJS);
                        }else{
                            return '';
                        }                        
                    }
                },
                {mData: "saldoInicial", "bSortable": false},
                {mData: "ingresos", "bSortable": false},
                {mData: "salidas", "bSortable": false},
                {mData: "saldo", "bSortable": false}
            ]
        });
    });
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        if(validate() == false){
            return;
        }

        var dataTable = $(tblTarjetaControl).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TarjetaControlVisible/Excel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();
        
        if(validate() == false){
            return;
        }

        var dataTable = $(tblTarjetaControl).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/TarjetaControlVisible/PDF"/>', dataTable);
    });
</script>