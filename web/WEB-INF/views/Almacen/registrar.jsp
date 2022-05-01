<%@include file="../includeTagLib.jsp" %> 
<style>
    #modalPersonal .modal-dialog
    {
        margin-top: 5%;
        width: 70%;
    }
</style>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Descripción <span class="f_req">*</span></label>
                    <input type="text" id="descripcion" class="form-control" maxlength="50" data-req=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Abreviatura <span class="f_req">*</span></label>
                    <input type="text" id="abreviatura" class="form-control" maxlength="10" data-req=""/>
                </div>
                <div class="col-sm-2 col-md-2">
                    <label>Ruc</label>
                    <input type="text" id="ruc" class="form-control" maxlength="11" value="${almacenPadre.ruc}"/>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Responsable</label>
                    <div class="input-group">
                        <input type="text" id="responsable" class="form-control" maxlength="70" value="${almacenPadre.responsable}"/>
                        <span class="input-group-addon"><i class="splashy-help" onclick="modalPersonal(event)"></i></span>
                    </div>                    
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Fax</label>
                    <input type="text" id="fax" class="form-control" maxlength="20" value="${almacenPadre.fax}"/>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Teléfono</label>
                    <input type="text" id="telefono" class="form-control" maxlength="20" value="${almacenPadre.telefono}"/>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Dirección</label>
                    <input type="text" id="direccion" class="form-control" maxlength="50" value="${almacenPadre.direccion}"/>
                </div> 
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-4 col-md-4">
                    <label>Departamento</label>
                    <select id="selDepartamento" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="departamento" items="${departamentos}">
                            <option value="${departamento.idUbigeo}" <c:if test="${departamento.idUbigeo == fn:substring(almacenPadre.idUbigeo, 0, 2)}"> selected="selected" </c:if> >${departamento.nombreUbigeo}</option>
                        </c:forEach>                        
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Provincia</label>
                    <select id="selProvincia" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="provincia" items="${provincias}">
                            <option value="${provincia.idUbigeo}" <c:if test="${provincia.idUbigeo == fn:substring(almacenPadre.idUbigeo, 0, 4)}"> selected="selected" </c:if> >${provincia.nombreUbigeo}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-4 col-md-4">
                    <label>Distrito</label>
                    <select id="selDistrito" class="form-control">
                        <option value="null">-NINGUNO-</option>
                        <c:forEach var="distrito" items="${distritos}">
                            <option value="${distrito.idUbigeo}" <c:if test="${distrito.idUbigeo == almacenPadre.idUbigeo}"> selected="selected" </c:if> >${distrito.nombreUbigeo}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-3 col-md-3">
                    <label>Tipo de Almacén <span class="f_req">*</span></label>
                    <select id="selTipoAlmacen" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="tipoAlmacen" items="${tiposAlmacen}">                                
                            <option value="${tipoAlmacen.idTipoAlmacen}">${tipoAlmacen.nombreTipoAlmacen}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-3 col-md-3">
                    <label>Farmacia <span class="f_req">*</span></label>
                    <select id="selFarmacia" class="form-control">
                        <option value="0" <c:if test="${almacen.farmacia == 0}"> selected="selected" </c:if>>-NINGUNO-</option>
                        <option value="4" <c:if test="${almacen.farmacia == 4}"> selected="selected" </c:if>>FARMACIA DE EMERGENCIA</option>
                        <option value="5" <c:if test="${almacen.farmacia == 5}"> selected="selected" </c:if>>FARMACIA DE CENTRO QUIRÚRGICO</option>
                        <option value="6" <c:if test="${almacen.farmacia == 6}"> selected="selected" </c:if>>FARMACIA DE AMBULATORIO</option>
                        <option value="7" <c:if test="${almacen.farmacia == 7}"> selected="selected" </c:if>>FARMACIA DE DOSIS UNITARIA</option>
                        <option value="8" <c:if test="${almacen.farmacia == 8}"> selected="selected" </c:if>>FARMACIA DE FARMACOTECNIA</option>
                    </select>
                </div>
                <div class="col-sm-3 col-md-3">                        
                    <label>Código Almacén <span class="f_req">*</span></label>
                    <input type="text" id="codigoAlmacen" class="form-control" maxlength="10" data-req=""/>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <button id="btnGuardar" class="btn btn-default" type="submit">Guardar</button>
            <button id="btnCancelar" class="btn btn-default">Cancelar</button>
        </div>
    </div>
</div>

<div class="modal fade" id="modalPersonal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title">Seleccionar Destino</h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-12 col-md-12">
                        <div id="buscaProducto" class="formSep">
                            <div class="row">
                                <div class="col-sm-6 col-md-6">
                                    <label>Unidad</label>
                                    <select id="selUnidad" class="form-control">                                         
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="formSep">
                            <table id="tblPersonal" class="table table-bordered table-striped dTableR">
                                <thead>
                                    <tr>
                                    <th></th>
                                    <th>Nombre Personal</th>
                                    <th>Tipo Documento</th>
                                    <th>Nro Documento</th>
                                    <th>Unidad</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer"></div>
        </div>
    </div>
</div>
                    
<script>
    $(document).ready(function (){
        $('#ruc').numeric({decimal: false, negative: false});
        
        <c:if test="${empty provincias}">
            onloadSelDepartamento();
            onloadSelProvincia();    
        </c:if>        
    });
    
    function modalPersonal(e) {
        e.preventDefault();
        
        var Data = {"id": "", "value": "idUnidad", "text": "nombreUnidad"};
        llenarSelectOptional('#selUnidad', '<c:url value="/Unidad/unidadesJSON" />', Data, '', function() {
           listarPersonal();
        });
        
        $('#modalPersonal').modal('show');
    }
    
    $('#selUnidad').change(function (){
        listarPersonal();
    });
    
    function listarPersonal(){
        var tblPersonal = document.getElementById('tblPersonal');        
        
        if ($.fn.DataTable.fnIsDataTable(tblPersonal)) {
            $(tblPersonal).dataTable().fnDestroy();
        }
        
        $(tblPersonal).dataTable({
            "fnServerParams": function(aoData) {
                aoData.push({"name": 'idUnidad', "value": $('#selUnidad').val()});
            },
            "sAjaxSource": '<c:url value="/Personal/personalPorUnidadJSON" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "iDisplayLength": 8,
            "aoColumns": [
                {mData: "idPersonal", sWidth: "5%", "bSortable": false, "mRender": function(data, type, row) {
                        return '<i class="splashy-arrow_medium_right" onclick="selectPersonal(\'' + data + '\', this)"></i>';
                    }
                },
                {mData: "nombreCompleto", sWidth: "40%"},
                {mData: "tipoDocumento", sWidth: "13%", "bSortable": false},
                {mData: "nroDocumento", sWidth: "14%","bSortable": false},
                {mData: "unidad", sWidth: "28%","bSortable": false}                
            ]
        });        
    }
    
    function selectPersonal(idPersonal, element){
        var nombrePersonal = $(element).parent().parent().find('td:eq(1)').text();
        $('#responsable').val(nombrePersonal);
        $('#modalPersonal').modal('hide');
    }
    
    $('#selDepartamento').change(function() {
        listarProvincias();        
    });
    
    $('#selProvincia').change(function() {
        listarDistritos();
    });

    $('#btnCancelar').click(function(e) {
        e.preventDefault();
        <c:if test="${not empty param.idAlmacenPadre}">
        window.location = '<c:url value="/Almacen/subalmacen/" />${param.idAlmacenPadre}';
        </c:if>
        <c:if test="${empty param.idAlmacenPadre}">
            window.location = '<c:url value="/Almacen" />';
        </c:if>
    });

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        var dataResponse = validateForm('[data-req]');

        if (dataResponse.estado === false) {
            errorResponse(dataResponse);
            return;
        }

        var almacen = {
            descripcion: $('#descripcion').val(),
            abreviatura: $('#abreviatura').val(),
            ruc: $('#ruc').val(),
            responsable: $('#responsable').val(),
            fax: $('#fax').val(),
            telefono: $('#telefono').val(),
            direccion: $('#direccion').val(),
            idUbigeo: $('#selDistrito').valNull(),            
            idTipoAlmacen: $('#selTipoAlmacen').val(),
            farmacia: $('#selFarmacia').val(),
            codigoAlmacen: $('#codigoAlmacen').val()<c:if test="${not empty param.idAlmacenPadre}">,            
            idAlmacenPadre: <c:out value="${param.idAlmacenPadre}" />
            </c:if>
        };

        $.ajax({
            url: '<c:url value="/Almacen/registrar"/>',
            data: JSON.stringify(almacen),
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            success: function(dataResponse) {
                functionResponse(dataResponse, function(){
                    $('#btnCancelar').click();
                });
            }
        });
    });
    
    function onloadSelDepartamento(){
        $('#selProvincia').find("option").remove();
        $('#selProvincia').append('<option value="null">-NINGUNO-</option>');
    }
    
    function onloadSelProvincia(){        
        $('#selDistrito').find("option").remove();
        $('#selDistrito').append('<option value="null">-NINGUNO-</option>');
    }

    function listarProvincias() {
        
        $.ajax({
            url: '<c:url value="/Ubigeo/listarProvincias?dpto="/>' + $('#selDepartamento').val(),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(jsonData, status, metaData) {
                onloadSelDepartamento();
                
                for (var i = 0; i <= jsonData.length - 1; ++i) {
                    $('#selProvincia').append('<option value="' + jsonData[i].idUbigeo + '">' + jsonData[i].nombreUbigeo + '</option>');
                }
                
                onloadSelProvincia();
            }
        });
    }
    
    function listarDistritos() {
        
        $.ajax({
            url: '<c:url value="/Ubigeo/listarDistritos?prov="/>' + $('#selProvincia').val(),
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(jsonData, status, metaData) {
                onloadSelProvincia();
                
                for (var i = 0; i <= jsonData.length - 1; ++i) {
                    $('#selDistrito').append('<option value="' + jsonData[i].idUbigeo + '">' + jsonData[i].nombreUbigeo + '</option>');
                }
            }
        });
    }
</script>