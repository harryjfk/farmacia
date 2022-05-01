<%@include file="../includeTagLib.jsp" %> 
<%@ taglib uri="stcJdbc" prefix="stcJdbc"%>
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
                    <label>Periodo <span class="f_req">*</span></label>                    
                    <input type="text" class="form-control" readonly="" value="${periodo.nombreMes} - ${periodo.anio}"/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Fecha Registro <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fechaRegistro" name="fechaRegistro" readonly="" class="form-control" data-field-date="" data-req="" value='<fmt:formatDate pattern="<%=formatoFecha%>" value="${movimiento.fechaRegistro}" />'/>
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <input type="hidden" id="idAlmacenLogistica" name="idAlmacenLogistica" />
                <input type="hidden" id="idAlmacenEspecializado" name="idAlmacenEspecializado" />
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenOrigen" name="idAlmacenOrigen" value="${movimiento.almacenOrigen.idAlmacen}"/>
                    <input type="hidden" id="idTipoAlmacenOrigen" name="idTipoAlmacenOrigen"/>
                    <input type="hidden" id="indiceAlmacenHijoOrigen" name="indiceAlmacenHijo"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenOrigen" class="form-control" readonly="" value="${movimiento.almacenOrigen.descripcion}"/>
                        <span class="input-group-addon" onclick="modalAlmacen('O',event)"><i class="splashy-help"></i></span>                        
                    </div>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Destino <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenDestino" name="idAlmacenDestino" data-req=""value="${movimiento.almacenDestino.idAlmacen}"/>
                    <div class="input-group">
                        <input type="text" id="txtAlmacenDestino" class="form-control" readonly="" value="${movimiento.almacenDestino.descripcion}" />
                        <span class="input-group-addon" onclick="modalAlmacen('D', event)"><i class="splashy-help"></i></span>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Concepto <span class="f_req">*</span></label>
                    <select id="idConcepto" name="idConcepto" class="form-control" data-req="">                        
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="concepto" items="${conceptos}">
                            <option value="${concepto.idConcepto}" <c:if test="${concepto.idConcepto == movimiento.concepto.idConcepto}">selected="selected"</c:if>>${concepto.nombreConcepto}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Tipo Documento</label>
                    <select id="idTipoDocumentoMov" name="idTipoDocumentoMov" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="documento" items="${documentos}">
                            <option value="${documento.tipoDocumentoMov.idTipoDocumentoMov}" <c:if test="${documento.tipoDocumentoMov.idTipoDocumentoMov == movimiento.tipoDocumentoMov.idTipoDocumentoMov}">selected="selected"</c:if>>${documento.tipoDocumentoMov.nombreTipoDocumentoMov}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label id="lblNroDocumento">Nro. Documento <span class="f_req">*</span></label>
                    <input type="text" id="numeroDocumentoMov" name="numeroDocumentoMov" class="form-control" maxlength="20" data-req=""value="${movimiento.numeroDocumentoMov}"/>
                </div>                
            </div>
        </div>
                        
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Referencia</label>
                    <input type="text" id="referencia" name="referencia" class="form-control" maxlength="100" value="${movimiento.referencia}"/>
                </div>
            </div>
        </div>
        <div>
            <div class="col-sm-12 col-md-12">
                <div class="row">
                    <button id="btnAgregarProducto" class="btn btn-default" onclick="modalProductos(event)"><i class="splashy-add_small"></i> Agregar Producto</button>
                    <span class="help-block">Tecla rápida: CTRL + Q</span>
                </div>
                <div class="row">
                    <table id="detalleSalida" class="table table-bordered table-striped dTableR">
                        <thead>
                            <tr>
                                <th style="width: 8%;">Item</th>
                                <th style="width: 24%;">Producto</th>
                                <th style="width: 8%;">Cantidad</th>
                                <th style="width: 8%;">Precio</th>
                                <th style="width: 12%;">Importe</th>
                                <th style="width: 20%;">Lote</th>
                                <th style="width: 15%;">Fecha Vencimiento</th>
                                <th style="width: 5%;"></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="form-actions">
            <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
            <button id="btnCancelar" class="btn btn-default">Cancelar</button>
        </div>
    </div>
</div>

<div class="modal fade" id="modalProducto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Agregar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProducto" class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Criterio</label>
                                    <input type="text" id="criterio" name="criterio" class="form-control" maxlength="100"/>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>&nbsp;</label>
                                    <button id="btnBuscar" class="btn btn-primary">Buscar</button>
                                </div>
                            </div>
                        </div>                       
                        <div class="formSep">
                            <table id="tblProductos" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Codigo Sismed</th>
                                        <th>Medicamento o Insumo</th>
                                        <th>FF</th>
                                        <th>Cantidad</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="idProducto" name="idProductoSalida" value="0"/>
                                    <input type="text" id="txtProducto" name="txtProductoSalida" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="cantidad" name="cantidadSalida" class="form-control" />
                                </div>
                            </div>
                        </div>                
                        <div id="divMessage"></div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-id="btnAgregarDetalle" class="btn btn-primary" type="submit">Guardar</button>
                <button data-dismiss="modal" class="btn btn-default">Cancelar</button>                
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modProducto">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Modificar Producto</h3>
            </div>
            <div class="modal-body">              
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div class="formSep">
                            <div class="row">
                                <div class="col-sm-8 col-md-8">
                                    <label>Producto <span class="f_req">*</span></label>
                                    <input type="hidden" id="modidProducto" name="modidProducto" value="0"/>
                                    <input type="text" id="modtxtProducto" name="modtxtProducto" class="form-control" readonly="readonly"/>
                                    <span class="help-block">Seleccione un producto.</span>
                                </div>
                                <div class="col-sm-4 col-md-4">
                                    <label>Cantidad <span class="f_req">*</span></label>
                                    <input type="text" id="modcantidad" name="cantidad" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div id="modDivMessage">

                        </div>
                    </div>                    
                </div>
            </div>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default">Cancelar</button>
                <button id="btnModificarDetalle" class="btn btn-primary" type="submit">Guardar</button>
            </div>
        </div>
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
                <div class="row">
                    <div class="col-sm-1 col-md-1"></div>
                    <div id="mensajeTree" class="col-sm-10 col-md-10"></div>
                    <div class="col-sm-1 col-md-1"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="btnCerrarModalAlmacen" data-dismiss="modal" class="btn btn-primary">Cerrar</button>
                <button id="btnQuitarNodoSeleccion" class="btn btn-default" data-tipo="">Quitar Selección</button>
            </div>
        </div>
    </div>
</div>
                        
<script>
    
    var tblProductos = document.getElementById('tblProductos');
    
    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        window.location = '<c:url value="/NotaSalida" />';
    });
    
    $(document).keyup(function (e){
        if (e.which === 81 && e.ctrlKey) {
            modalProductos(e);
        }
    });
    
    $(document).ready(function(e){        
        getDetalleSalida();    
    });
    
    $('#buscaProducto').keyup(function (e){
        e.preventDefault();
        
        if (e.which === 13) {
            $('#btnBuscar').click();
        }
    });
    
    $('#almacenesTreeview').treeview({data: []});
    
    $('#btnQuitarNodoSeleccion').click(function (e){
        e.preventDefault();
       
        if($(this).attr('data-tipo') == 'O'){
            $('#txtAlmacenOrigen').val('');
            $('#idAlmacenOrigen').val('');
            borrarDetalle();
        }
       
        $('#almacenesTreeview').treeview('clearActivateNode');
        
        $('#btnCerrarModalAlmacen').click();
    });
    
    $('#btnBuscar').click(function (e){
       e.preventDefault();
       
       reloadProductos();
    });
    
    function modalAlmacen(tipo, e) {
        e.preventDefault();
        var eventNodeSelect = null;
        var eventNodeUnselect = null;
        
        var idTipoAlmacenLogistica = 6;
        var idTipoAlmacenEspecializado = 16;
        
        var almacenLogistica = null;
        var almacenEspecializado=null;
        var indiceAlmacenHijo = 1;
                    
        if (tipo === 'O') {
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Origen');            
            eventNodeSelect = function(event, node) {
                
                
                borrarDetalle();
                indiceAlmacenHijo = node.idAlmacen - node.idAlmacenPadre;
                
                $('#txtAlmacenOrigen').val(node.parent + ' - ' + node.text);
                $('#idAlmacenOrigen').val(node.idAlmacen);
                $('#idTipoAlmacenOrigen').val(node.idTipoAlmacen);
                $('#indiceAlmacenHijoOrigen').val(indiceAlmacenHijo);
                
                if(node.parentIdTipoAlmacen!=idTipoAlmacenEspecializado){
                    $('#txtAlmacenDestino').val(almacenEspecializado.almacen+" - "+node.text);
                    $('#idAlmacenDestino').val(almacenEspecializado.idAlmacen+indiceAlmacenHijo);                    
                }else{
                    $('#txtAlmacenDestino').val(almacenLogistica.almacen);
                    $('#idAlmacenDestino').val(almacenLogistica.idAlmacen+indiceAlmacenHijo);
                }
                
                
                    
                
                
                $('#modalAlmacen').modal("hide");
            };
            eventNodeUnselect = function(event, node){
                $('#txtAlmacenOrigen').val('');
                $('#idTipoAlmacenOrigen').val('');
                $('#idAlmacenOrigen').val('');
                $('#txtAlmacenDestino').val('');
                $('#idAlmacenDestino').val('');
                $('#indiceAlmacenHijoOrigen').val('');
                
                borrarDetalle();
            };
        } else if (tipo === 'D') {
            
            borrarDetalle();
            $('#modalAlmacen').find('.modal-title').html('Selecionar Almacén Destino');            
            eventNodeSelect = function(event, node) {
                
                var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                if(node.idAlmacen != idAlmacenOrigen){
                    $('#txtAlmacenDestino').val(node.text+" - "+node.child);
                    $('#idAlmacenDestino').val(node.idAlmacen);
                    $('#modalAlmacen').modal("hide");
                    
                }
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
                if(tipo=='O'){
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenLogistica){
                            almacenLogistica = dataResponse[i];
                            $('#idAlmacenLogistica').val(almacenLogistica.idAlmacen);
                        }
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenEspecializado){
                            almacenEspecializado = dataResponse[i];
                            $('#idAlmacenEspecializado').val(almacenEspecializado.idAlmacen);
                        }
                        if(dataResponse[i].idTipoAlmacen!=idTipoAlmacenLogistica){
                            var tree = new Object();
                            tree.text = dataResponse[i].almacen;
                            tree.idAlmacen = dataResponse[i].idAlmacen;
                            tree.idTipoAlmacen = dataResponse[i].idTipoAlmacen;
                            tree.selectable = false;
                            tree.nodes = new Array();

                            if (dataResponse[i].almacenes.length > 0) {
                                for (var x = 0; x <= dataResponse[i].almacenes.length - 1; ++x) {

                                    var treeLevelTwo = new Object();
                                    treeLevelTwo.text = dataResponse[i].almacenes[x].almacen;
                                    treeLevelTwo.idAlmacen = dataResponse[i].almacenes[x].idAlmacen;
                                    treeLevelTwo.idTipoAlmacen = dataResponse[i].almacenes[x].idTipoAlmacen;
                                    treeLevelTwo.idAlmacenPadre = dataResponse[i].idAlmacen;
                                    treeLevelTwo.parent = dataResponse[i].almacen;
                                    treeLevelTwo.parentIdTipoAlmacen = dataResponse[i].idTipoAlmacen;
                                    treeLevelTwo.nodes = new Array();
                                    tree.nodes.push(treeLevelTwo);
                                }
                            }

                            treeView.push(tree);
                        }
                    }
                }else if(tipo=='D'){
                    
                    var almacenPadreOrigen;
                    var almacenHijoOrigen;
                    var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                    
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        for (var x = 0; x <= dataResponse[i].almacenes.length-1; ++x){
                            if(idAlmacenOrigen == dataResponse[i].almacenes[x].idAlmacen){
                                indiceAlmacenHijo = dataResponse[i].almacenes[x].idAlmacen - dataResponse[i].idAlmacen;
                                almacenHijoOrigen = dataResponse[i].almacenes[x];
                                almacenPadreOrigen = dataResponse[i];
                            }
                        }
                    }                    
                    for (var i = 0; i <= dataResponse.length - 1; ++i) {
                        
                        if(dataResponse[i].idTipoAlmacen==idTipoAlmacenLogistica){
                            
                                var tree = new Object();
                                tree.text = dataResponse[i].almacen;
                                tree.child = almacenHijoOrigen.almacen;
                                tree.idAlmacen = dataResponse[i].idAlmacen + indiceAlmacenHijo;
                                tree.selectable = true;
                                tree.nodes = new Array();
                                treeView.push(tree);

                        }
                    }
                }
                
                                
                $('#almacenesTreeview').treeview('remove');
                
                if(tipo=='O'){
                    $('#almacenesTreeview').treeview({
                        levels: 1,
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-down', nodeIcon: '',
                        data: treeView,
                        onNodeSelected: eventNodeSelect,
                        onNodeUnselected: eventNodeUnselect
                    });
                }else{
                    $('#almacenesTreeview').treeview({
                        levels: 0,
                        expandIcon: 'glyphicon glyphicon-chevron-right',
                        collapseIcon: 'glyphicon glyphicon-chevron-right', nodeIcon: '',
                        data: treeView,
                        onNodeSelected: eventNodeSelect,
                        onNodeUnselected: eventNodeUnselect
                    });
                }
                
                if (tipo == 'O') {
                    var idAlmacenOrigen = $('#idAlmacenOrigen').val();
                    if (idAlmacenOrigen.length) {
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
    
    function modalProductos(e){        
        e.preventDefault();
        
        
       if($('#idAlmacenOrigen').val().length == 0){
           var dataResponse = new Object();
           dataResponse.mensajesRepuesta = new Array();
           dataResponse.mensajesRepuesta.push('Debe seleccionar un almacén para cargar sus productos.');
           errorResponse(dataResponse);
           return;
       }
        
        var urlAjax;
        var aoDataParams = new Array();
        aoDataParams.push({"name": 'descripcion', "value": $('#descripcion').val()});
        aoDataParams.push({"name": 'idFormaFarmaceutica', "value": $('#idFormaFarmaceutica').val()});
        aoDataParams.push({"name": 'idTipoProducto', "value": $('#idTipoProducto').val()});
        aoDataParams.push({"name": 'idUnidadMedida', "value": $('#idUnidadMedida').val()});
        
        urlAjax = '<c:url value="/Producto/productosPorAlmacenJSON" />';
        aoDataParams.push({"name": 'idAlmacen', "value": $('#idAlmacenOrigen').val()});
        
        $('#txtStock').val('');
        $('#descripcion').val('');
        $('#idFormaFarmaceutica').val('0');
        $('#idTipoProducto').val('0');
        $('#idUnidadMedida').val('0');
        $('#lote').val('');
        $('#idProducto').val('');
        $('#txtProducto').val('');
        $('#txtStock').val('');
        $('#cantidad').val('');
        $('#precio').val('');
        $('#fechaVencimiento').val('');
        $('#divMessage').html('');
        $('#modalProducto').modal('show');        
        
        if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
            $(tblProductos).dataTable().fnDestroy();
        }
        
        $(tblProductos).dataTable({
            "fnServerParams": function (aoData){
                for(var key in aoDataParams){
                     if (aoDataParams.hasOwnProperty(key)){                        
                        aoData.push(aoDataParams[key]);
                     }
                }
            },
            "sAjaxSource": urlAjax,
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
    }
    
    function reloadProductos(){
        var dataTable = $(tblProductos).dataTable();
        dataTable.fnReloadAjax();
    }
       
    function selectProduct(idProducto, element){
        var tr = $(element).parent().parent();
        $('#txtProducto').val($(tr).find('td:eq(1)').text());        
        $('#idProducto').val(idProducto);
        
        var idAlmacen = $('#idAlmacenOrigen').val();
        
        if(idAlmacen !== null){
            $.ajax({
                url: '<c:url value="/Movimiento/stock" />?idProducto='+idProducto+'&idAlmacen=' + idAlmacen,
                data: null,
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                success: function(data) {
                    $('#txtStock').val(data);
                }
            });
        }
    }    

    function llenarDetalle(dataResponse){
        $('#detalleSalida tbody').html('');
                
        var fila = '';
        for(var i = 0; i <= dataResponse.length - 1; ++i){
            fila += '<tr>';
            fila += '<td>' + (i+1) + '</td>';
            fila += '<td>' + dataResponse[i].nombreProducto + '</td>';
            fila += '<td>' + dataResponse[i].cantidad + '</td>';
            fila += '<td>' + dataResponse[i].precio + '</td>';
            fila += '<td>' + dataResponse[i].total + '</td>';
            fila += '<td>' + dataResponse[i].lote + '</td>';
            fila += '<td>' + new Date(dataResponse[i].fechaVencimiento).toString(dateFormatJS) + '</td>';
            fila += '<td><a href="#" class="separator-icon-td" onclick="modificarDetalle('+ dataResponse[i].idProducto+', event);"><i class="splashy-pencil" title="Editar"></i></a>' +
                            '<a href="#" class="separator-icon-td" onclick="quitarDetalle(' + dataResponse[i].idProducto + ',event)"><i class="splashy-remove" title="Eliminar"></i></a></td>';
            fila += '</tr>';
        }                

        $('#detalleSalida tbody').html(fila);
    }

    function getDetalleSalida(){
        $.ajax({
            url: '<c:url value="/NotaSalida/cargarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });
    }
    
    function quitarDetalle(idProducto, e){
        e.preventDefault();
        
        $.ajax({
            url: '<c:url value="/NotaSalida/quitarDetalle" />/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });        
    }
    
    function borrarDetalle(){
        $.ajax({
            url: '<c:url value="/NotaSalida/borrarDetalle" />',
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                llenarDetalle(dataResponse);
            }
        });        
    }

    $('#btnAgregarDetalle').click(function (e){
        e.preventDefault();
        
        var fechaVencimiento = Date.parseExact($('#fechaVencimiento').val(), dateFormatJS);
        var fechaVencimientoTime = null;
        if (fechaVencimiento === null) {           
        } else {
            fechaVencimientoTime = fechaVencimiento.getTime();
        }
        
        var salidaDetalle = {            
            idProducto: $('#idProducto').val(),
            cantidad: $('#cantidad').val()
        };
        
        var urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').val();;
        
        $.ajax({
            url: '<c:url value="/NotaSalida/agregarDetalle" />' + urlExtend,
            data: JSON.stringify(salidaDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if(dataResponse.estado){
                    getDetalleSalida();
                    $('#modalProducto').modal('hide');
                }else{
                    jsonToDivError(dataResponse, '#divMessage')
                }
            }
        });
    });

    $('#btnModificarDetalle').click(function(e){
        e.preventDefault();
        var fechaVencimiento = Date.parseExact($('#modfechaVencimiento').val(), dateFormatJS);
        var fechaVencimientoTime = null;
        
        if (fechaVencimiento === null) {
        } else {
            fechaVencimientoTime = fechaVencimiento.getTime();
        }
        
        var ingresoDetalle = {
            lote: $('#modlote').val(),
            idProducto: $('#modidProducto').val(),
            nombreProducto: $('#modtxtProducto').val(),
            cantidad: $('#modcantidad').val(),
            precio: $('#modprecio').val(),
            fechaVencimiento: fechaVencimientoTime,
            registroSanitario: $('#modregistroSanitario').val(),
            stock: $('#modtxtStock').val()
        };

        var urlExtend = '';

        if ($('#idAlmacenOrigen').valNull() != null) {
            urlExtend = '?idAlmacen=' + $('#idAlmacenOrigen').valNull() 
                    + '&idConcepto=' + $('#idConcepto').valNull()
                    + '&fechaRegistro=' + $('#idConcepto').valNull();
        }
        $.ajax({
            url: '<c:url value="/NotaSalida/modificarDetalle" />' + urlExtend,
            data: JSON.stringify(ingresoDetalle),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                if (dataResponse.estado) {
                    getDetalleSalida();
                    $('#modProducto').modal('hide');
                } else {
                    jsonToDivError(dataResponse, '#modDivMessage')
                }
            }
        });
    });
		
    function modificarDetalle(idProducto, e){
        e.preventDefault();
        $.ajax({
            url: '<c:url value="/NotaSalida/modificarDetalle"/>/' + idProducto.toString(),
            data: {},
            type: 'POST',
            dataType: 'json',
            contentType: 'application.jsp',
            success: function(dataResponse){
                $('#modidProducto').val(dataResponse.data.idProducto);
                $('#modtxtProducto').val(dataResponse.data.nombreProducto);
                $('#modlote').val(dataResponse.data.lote);
                $('#modcantidad').val(dataResponse.data.cantidad);
                $('#modprecio').val(dataResponse.data.precio);
                $('#modfechaVencimiento').val(new Date(dataResponse.data.fechaVencimiento).toString(dateFormatJS));
                $('#modregistroSanitario').val(dataResponse.data.registroSanitario);
                $('#modProducto').modal('show');
            }
        });
    }
    $('#btnGuardar').click(function(e) {
        e.preventDefault();
        var dataResponse = validateForm('[data-req]');
        
        var fechaRegistro = Date.parseExact($('#fechaRegistro').val(), dateFormatJS);        
        var fechaRegistroTime = null;        
                
        if (fechaRegistro === null) {
            dataResponse.mensajesRepuesta.push('Fecha de Registro inválida.');
            dataResponse.estado = false;
        } else {
            fechaRegistroTime = fechaRegistro.getTime();
        }
        
        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var notaSalida = {
            idMovimiento: ${movimiento.idMovimiento},           
            fechaRegistro: fechaRegistroTime,
            idAlmacenOrigen: $('#idAlmacenOrigen').val(),
            idAlmacenDestino: $('#idAlmacenDestino').val(),
            idTipoDocumentoMov: $('#idTipoDocumentoMov').val(),
            numeroDocumentoMov: $('#numeroDocumentoMov').val(),
            numeroMovimiento: '${movimiento.numeroMovimiento}',
            idMovimientoIngreso: '${movimiento.idMovimientoIngreso}',
            idConcepto: $('#idConcepto').valNull(),
            referencia: $('#referencia').val()
        };

        $.ajax({
            url: '<c:url value="/NotaSalida/modificar" />',
            data: JSON.stringify(notaSalida),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function (){
                   $('#btnCancelar').click(); 
                });
            }
        });
    });
    $('#idConcepto').change(function() {
        var value = $(this).val();
        $.ajax({
            url: '<c:url value="/ConceptoTipoDocumentoMov/listarIdDocumento" />?idConcepto=' + value,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                var optionTipoDocumento = '<option value="null">-NINGUNO-</option>';
                for (var i = 0; i <= data.length - 1; ++i) {
                    if (data[i].tipoDocumentoMov.activo == 1) {
                        optionTipoDocumento += '<option value="' + data[i].tipoDocumentoMov.idTipoDocumentoMov + '">' + data[i].tipoDocumentoMov.nombreTipoDocumentoMov + '</option>';
                    }
                }
                $('#idTipoDocumentoMov').html(optionTipoDocumento);
            }
        });
    });
    
</script>