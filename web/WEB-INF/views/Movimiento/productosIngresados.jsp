<%@include file="../includeTagLib.jsp" %>
<style>
    #modalAlmacen .modal-dialog
    {
        margin-top: 5%;
        width: 35%;
    }

    #modalProveedor .modal-dialog
    {
        margin-top: 5%;
        width: 60%;
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
                        <input type="text" id="fechaDesde" name="txtFechaInicio" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta</label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" name="txtFechaFin" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
                    </div>
                </div>           
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen</label>
                    <input type="hidden" id="idAlmacenOrigen" value="0"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenOrigen" class="form-control" readonly="" value="TODOS" />
                        <span class="input-group-addon" onclick="modalAlmacen('O', event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>                
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Destino <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenDestino" value=""/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenDestino" class="form-control" readonly="" data-req="" />
                        <span class="input-group-addon" onclick="modalAlmacen('D', event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Proveedor</label>
                    <input type="hidden" id="idProveedor" value="0" />
                    <div class="input-group">
                        <input type="text" id="txtProveedor" class="form-control" readonly="" value="TODOS"/>
                        <span class="input-group-addon" onclick="modalProveedor(event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
            </div>
        </div>
        <div class="form-actions">            
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'consultar'}">
                    <button id="btnBuscar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
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

<div id="divProductosIngresados" class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblProductosIngresados" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                    <th>F. Registro</th>
                    <th>Código</th>
                    <th>Medicamento o Insumo</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<div class="modal fade" id="modalAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"></h3>
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
                <button id="btnCerrarModalAlmacen" data-dismiss="modal" class="btn btn-primary">Cerrar</button>
                <button id="btnTodosAlmacen" class="btn btn-default" data-tipo="">Todos</button>                
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalProveedor">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Proveedor</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblProveedores" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Razón Social</th>                   
                                    <th>Ruc</th>                                
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-primary">Cerrar</button>
                <button id="btnQuitarProveedor" class="btn btn-default" data-tipo="">Quitar Selección</button>
            </div>
        </div>
    </div>
</div>

<script>
    var tblProductosIngresados = document.getElementById('tblProductosIngresados');
    
    $(document).ready(function() {
       $('#almacenesTreeview').treeview({data: []});
       $('#btnLimpiarBusqueda').click();
       
       var anio = parseInt(new Date().toString('yyyy'));
       var mes = parseInt(new Date().toString('MM'));
       var fechaPrimer = new Date(anio, mes - 1, 1);
       var fechaUltimo = new Date(anio, mes - 1, 1).moveToLastDayOfMonth();
       
       $('#fechaDesde').val(fechaPrimer.toString(dateFormatJS));
       $('#fechaHasta').val(fechaUltimo.toString(dateFormatJS));
    });
    
    $('#btnPDF').click(function (e){
        e.preventDefault();
        
        var dataTable = $(tblProductosIngresados).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductosIngresados/PDF"/>', dataTable);
    });
    
    $('#btnExcel').click(function (e){
        e.preventDefault();
        
        var dataTable = $(tblProductosIngresados).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/ProductosIngresados/Excel"/>', dataTable);        
    });
    
    $('#btnLimpiarBusqueda').click(function (e){
         e.preventDefault();
         
        $('#fechaDesde').val('');
        $('#fechaHasta').val('');
        $('#btnTodos').click();
        $('#txtAlmacenOrigen').val('TODOS');
        $('#idAlmacenOrigen').val('0');
        $('#btnQuitarProveedor').click('');
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
        $('#divProductosIngresados').attr('style', 'display:none');
     });

    $('#selAnio').change(function() {
        var Data = {"id": "", "value": "mes", "text": "nombreMes"};
        llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function() {
            var mes = new Date().toString('MM');
            console.log(mes);
            $('#selMes').val(mes);
        });
    });    

    function modalAlmacen(tipo, e) {
        e.preventDefault();
        var eventNodeSelect = null;
        var eventNodeUnselect = null;
        
        if (tipo === 'O') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Origen');
            $('#btnTodosAlmacen').css('display', '');
            eventNodeSelect = function(event, node) {                
                $('#txtAlmacenOrigen').val(node.parent + ' - ' + node.text);
                $('#idAlmacenOrigen').val(node.idAlmacen);
            };
            eventNodeUnselect = function(event, node){
                $('#txtAlmacenOrigen').val('TODOS');
                $('#idAlmacenOrigen').val('0');
            };
        } else if (tipo === 'D') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Destino');
            $('#btnTodosAlmacen').css('display', 'none');
            eventNodeSelect = function(event, node) {
                $('#txtAlmacenDestino').val(node.parent + ' - ' + node.text);
                $('#idAlmacenDestino').val(node.idAlmacen);
            };
            eventNodeUnselect = function(event, node) {
                $('#txtAlmacenDestino').val('');
                $('#idAlmacenDestino').val('');
            };
        }

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
                    collapseIcon: 'glyphicon glyphicon-chevron-down', nodeIcon: '',
                    data: treeView,
                    onNodeSelected: eventNodeSelect,
                    onNodeUnselected: eventNodeUnselect
                });
                
                if (tipo == 'O') {
                    var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                    if (idAlmacenOrigen.length && idAlmacenOrigen != '0') {
                        $('#almacenesTreeview').treeview('selectByKey', [idAlmacenOrigen, 'idAlmacen',{silent:true}]);
                    }
                } else if (tipo == 'D') {
                    var idAlmacenDestino = $('#idAlmacenDestino').val();
                    if (idAlmacenDestino.length) {
                        $("#almacenesTreeview").treeview("selectByKey", [idAlmacenDestino, 'idAlmacen',{silent:true}]);
                    }
                }
            }
        });
    }

    $('#btnTodosAlmacen').click(function(e) {
        e.preventDefault();

        $('#txtAlmacenOrigen').val('TODOS');
        $('#idAlmacenOrigen').val('0');
        $('#btnCerrarModalAlmacen').click();
    });

    function modalProveedor(e) {
        e.preventDefault();

        $('#modalProveedor').modal('show');

        if ($.fn.DataTable.fnIsDataTable(tblProveedores)) {
            var dataTable = $(tblProveedores).dataTable();
            dataTable.fnReloadAjax();
        } else {
            $(tblProveedores).dataTable({
                "sAjaxSource": '<c:url value="/Proveedor/proveedoresJSON" />',
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "iDisplayLength": 8,
                "aoColumns": [
                    {mData: "idProveedor", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                            return '<i class="splashy-arrow_medium_right" onclick="selectProveedor(\'' + data + '\', this)"></i>';
                        }
                    },
                    {mData: "razonSocial", sWidth: "60%", "bSortable": false},
                    {mData: "ruc", sWidth: "30%", "bSortable": false}
                ]
            });
        }
    }

    function selectProveedor(idProveedor, element) {
        var tr = $(element).parent().parent();
        $('#txtProveedor').val($(tr).find('td:eq(1)').text());
        $('#idProveedor').val(idProveedor);
        $('#modalProveedor').modal('hide');
    }

    $('#btnQuitarProveedor').click(function(e) {
        e.preventDefault();
        $('#txtProveedor').val('');
        $('#idProveedor').val('0');
        $('#modalProveedor').modal('hide');
    });

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

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
            return;
        }
        
        $('#divProductosIngresados').removeAttr('style');
        
        

        if ($.fn.DataTable.fnIsDataTable(tblProductosIngresados)) {
            $(tblProductosIngresados).dataTable().fnDestroy();
        }

        $(tblProductosIngresados).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'idAlmacenOrigen', "value": $('#idAlmacenOrigen').val()});
                aoData.push({"name": 'idAlmacenDestino', "value": $('#idAlmacenDestino').val()});
                aoData.push({"name": 'fechaDesde', "value": ((fechaDesde == null) ? '' : fechaDesde.getTime())});
                aoData.push({"name": 'fechaHasta', "value": ((fechaHasta == null) ? '' : fechaHasta.getTime())});
                aoData.push({"name": 'idProveedor', "value": $('#idProveedor').val()});
            },
            "sAjaxSource": '<c:url value="/ProductosIngresados/JSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "fechaRegistro", "bSortable": false, "mRender": function(data) {
                        return new Date(data).toString(dateFormatJS);
                    }
                },
                {mData: "codigoSismed", "bSortable": false},
                {mData: "descripcion", "bSortable": false}
            ]
        });
    });
</script>