<%@include file="../includeTagLib.jsp" %>
<style>
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
                <div class="col-sm-4 col-md-4">
                    <label>Tipo Movimiento</label>
                    <select id="selTipoMovimiento" class="form-control">
                        <option value="">TODOS</option>
                        <option value="I">INGRESOS</option>
                        <option value="S">SALIDAS</option>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Concepto</label>
                    <select id="selConcepto" class="form-control">
                        <option value="0">TODOS</option>
                        <c:forEach var="concepto" items="${conceptos}">                                
                            <option value="${concepto.idConcepto}">${concepto.nombreConcepto}</option>
                        </c:forEach>
                    </select>
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
                    <label>Almacén Destino</label>
                    <input type="hidden" id="idAlmacenDestino" value="0"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenDestino" class="form-control" readonly="" value="TODOS" />
                        <span class="input-group-addon" onclick="modalAlmacen('D', event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
            </div>
        </div>        
        <div class="formSep">
            <div class="row">                
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Desde</label>
                    <div class="input-group">
                        <input type="text" id="fechaDesde" name="fechaDesde" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaDesde');"></i></span>
                    </div>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Hasta</label>
                    <div class="input-group">
                        <input type="text" id="fechaHasta" name="fechaHasta" class="form-control" data-field-date=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fechaHasta');"></i></span>
                    </div>
                </div>                
                <div class="col-sm-3 col-md-3">
                    <label>Estado</label>
                    <select id="selEstado" class="form-control">
                        <option value="-1">TODOS</option>
                        <option value="1">ACTIVOS</option>
                        <option value="0">ANULADOS</option>
                    </select>
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

<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblMovimiento" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>F. Registro</th>
                <th>Tipo</th>
                <th>N° Mov.</th>
                <th>Almacén Origen</th>
                <th>Almacén Destino</th>
                <th>Concepto</th>
                <th>Importe</th>
                <th>F. Recepción</th>
                <th>Tipo Doc.</th>
                <th>N° Doc.</th>
                <th>Proveedor</th>
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
                <button id="btnTodos" class="btn btn-default" data-tipo="">Todos</button>
            </div>
        </div>
    </div>
</div>

<script>

    var tblMovimiento = document.getElementById('tblMovimiento');

    $(document).ready(function() {
        $('#almacenesTreeview').treeview({data: []});
        $('#btnLimpiarBusqueda').click();
    });
    
    $('#btnLimpiarBusqueda').click(function (e){
         e.preventDefault();
                  
        $('#selTipoMovimiento').val('');
        $('#selConcepto').val('0');
        $('#btnTodos').click();
        $('#txtAlmacenOrigen').val('TODOS');
        $('#idAlmacenOrigen').val('0');
        $('#txtAlmacenDestino').val('TODOS');
        $('#idAlmacenDestino').val('0');
        $('#selEstado').val('-1');
        $('#fechaDesde').val('');
        $('#fechaHasta').val('');
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
    });

    $('#selAnio').change(function() {
        var Data = {"id": "", "value": "mes", "text": "nombreMes"};
        llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function() {
            var mes = new Date().toString('MM');
            $('#selMes').val(mes);
            $('#btnBuscar').click();
        });
    });   

    $('#btnTodos').click(function(e) {
        e.preventDefault();

        if ($(this).attr('data-tipo') == 'O') {
            $('#txtAlmacenOrigen').val('TODOS');
            $('#idAlmacenOrigen').val('0');
        } else if ($(this).attr('data-tipo') == 'D') {
            $('#txtAlmacenDestino').val('TODOS');
            $('#idAlmacenDestino').val('0');
        }
        
        var nodeSelect = $('#almacenesTreeview').treeview('getSelected');
        if(nodeSelect.length){
            $('#almacenesTreeview').treeview('unselectNode', nodeSelect[0].nodeId);
        }

        $('#btnCerrarModalAlmacen').click();
    });    

    $('#btnBuscar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        var tipoMovimiento;
        var fechaDesde = Date.parseExact($('#fechaDesde').val(), dateFormatJS);
        var fechaHasta = Date.parseExact($('#fechaHasta').val(), dateFormatJS);

        if ($('#selTipoMovimiento').val() == '') {
            tipoMovimiento = null;
        } else {
            tipoMovimiento = $('#selTipoMovimiento').val();
        }

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

        if ($.fn.DataTable.fnIsDataTable(tblMovimiento)) {
            $(tblMovimiento).dataTable().fnDestroy();
        }

        $(tblMovimiento).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idPeriodo', "value": $('#selAnio').val() + $('#selMes').val()});
                aoData.push({"name": 'tipoMovimiento', "value": tipoMovimiento});
                aoData.push({"name": 'idAlmacenOrigen', "value": $('#idAlmacenOrigen').val()});
                aoData.push({"name": 'idAlmacenDestino', "value": $('#idAlmacenDestino').val()});
                aoData.push({"name": 'idConcepto', "value": $('#selConcepto').val()});
                aoData.push({"name": 'fechaDesde', "value": ((fechaDesde == null) ? '' : fechaDesde.getTime())});
                aoData.push({"name": 'fechaHasta', "value": ((fechaHasta == null) ? '' : fechaHasta.getTime())});
                aoData.push({"name": 'activo', "value": $('#selEstado').val()});
            },
            "sAjaxSource": '<c:url value="/Movimiento/movimientosJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "fechaRegistro", "bSortable": false, "mRender": function(data){
                        return new Date(data).toString(dateFormatJS);
                    }
                },
                {mData: "tipoMovimiento", "bSortable": false},
                {mData: "numeroMovimiento", "bSortable": false},
                {mData: "almacenOrigen", "bSortable": false},
                {mData: "almacenDestino", "bSortable": false},
                {mData: "nombreConcepto", "bSortable": false},
                {mData: "total", "bSortable": false},
                {mData: "fechaRecepcion", "bSortable": false, "mRender": function(data){
                        if(data == null){
                            return '';
                        }else{
                            return new Date(data).toString(dateFormatJS);
                        }                        
                    }
                },
                {mData: "nombreTipoDocumento", "bSortable": false},
                {mData: "numeroDocumentoMov", "bSortable": false},
                {mData: "razonSocial", "bSortable": false}
            ]
        });
    });
    
    function modalAlmacen(tipo, e) {
        e.preventDefault();
        var eventNodeSelect = null;
        var eventNodeUnselect = null;
        
        if (tipo === 'O') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Origen');            
            eventNodeSelect = function(event, node) {                
                $('#txtAlmacenOrigen').val(node.parent + ' - ' + node.text);
                $('#idAlmacenOrigen').val(node.idAlmacen);
            };
            eventNodeUnselect = function(event, node){
                $('#txtAlmacenOrigen').val('');
                $('#idAlmacenOrigen').val('');
            };
        } else if (tipo === 'D') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Destino');            
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
                    if (idAlmacenDestino.length && idAlmacenDestino != '0') {
                        $("#almacenesTreeview").treeview("selectByKey", [idAlmacenDestino, 'idAlmacen',{silent:true}]);
                    }
                }
            }
        });
    }
    
    $('#btnExcel').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblMovimiento).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Movimiento/movimientosExcel"/>', dataTable);
    });

    $('#btnPDF').click(function(e) {
        e.preventDefault();

        var dataTable = $(tblMovimiento).dataTable();
        window.location = getUrlFromDatatables('<c:url value="/Movimiento/movimientosPDF"/>', dataTable);
    });
</script>
