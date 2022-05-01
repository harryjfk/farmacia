<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Almacén Origen <span class="f_req">*</span></label>
                    <input type="hidden" id="idAlmacenOrigen" />
                    <div class="input-group">
                        <input type="text" id="txtAlmacenOrigen" class="form-control" readonly="" data-req="" />  
                        <span class="input-group-addon" onclick="modalAlmacenOrigen()"><i class="splashy-help"></i></span> 
                    </div>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Fecha <span class="f_req">*</span></label>
                    <div class="input-group">
                        <input type="text" id="fecha" name="fechaRegistro" class="form-control" data-field-date="" data-req=""/>
                        <span class="input-group-addon"><i class="splashy-calendar_month" onclick="mostrarCalendar('fecha');"></i></span>
                    </div>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <div class="form-actions">
                        <button id="btnCancelar" class="btn btn-default">Inicializar</button>
                        <button id="btnGuardar" class="btn btn-default" type="submit">Consultar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="formSep">
<div class="row">
    <div class="col-sm-9 col-md-9">
        <table id="tblConcepto" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Código</th>
                <th>Descripción Producto</th>
                <th>Tipo</th>
                <th>F. F</th> 
                <th>Stock</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td><td></td><td></td><td></td><td></td>
                </tr>
                <tr>
                    <td></td><td></td><td></td><td></td><td></td>
                </tr>
                <tr>
                    <td></td><td></td><td></td><td></td><td></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
    <div class="row">
        <div class="col-sm-9 col-md-9">
            <div style="float:right">
                Total de Productos <input type="text" class="form-control" />
            </div>
        </div>
    </div>
</div>

<div class="formSep">
    <div class="row">
        <div class="col-sm-6 col-md-6">Stock Detallado</div></div>
<div class="row">
    <div class="col-sm-6 col-md-6">
        <table id="tblConcepto" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Lote</th>
                <th>F. Vencimiento</th>
                <th>Saldo</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td><td></td><td></td>
                </tr>
                <tr>
                    <td></td><td></td><td></td>
                </tr>
                <tr>
                    <td></td><td></td><td></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
    </div>

<div class="modal fade" id="modalAlmacen">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Almacenes</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <table id="tblAlmacen" class="table table-bordered table-striped dTableR">
                            <thead>
                                <tr>
                                <th>Sel.</th>
                                <th>Almacén</th>                   
                                <th>Código</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="almacen" items="${almacenes}">   
                                    <tr>
                                        <td align="center">
                                            <a href="#" onclick="seleccionarAlmacen(${almacen.idAlmacen}, '${almacen.descripcion}')">
                                                <i class="splashy-arrow_large_right" title="Seleccionar"></i>
                                            </a>
                                        </td>
                                        <td>${almacen.descripcion}</td>
                                        <td>${almacen.idAlmacen}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>

<script>
    var tblAlmacen = document.getElementById('tblAlmacen');
    
    $(document).ready(function() {
        listarAlmacenes();
    });
    
    function modalAlmacenOrigen() {
        $('#modalAlmacen').modal('show');
    }
    
    function listarAlmacenes(){
        $(tblAlmacen).dataTable({
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false
        });
    }
    
    function seleccionarAlmacen(id, descripcion){

            $('#idAlmacenOrigen').val(id);
            $('#txtAlmacenOrigen').val(descripcion);

        $('#modalAlmacen').modal('hide');        
    }
    
</script>
