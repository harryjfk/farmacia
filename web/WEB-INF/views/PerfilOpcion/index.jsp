<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Módulo <span class="f_req">*</span></label>
                    <select id="selModulo" class="form-control" data-req="">
                        <c:forEach var="modulo" items="${modulos}">
                            <option value="${modulo.idModulo}">${modulo.nombreModulo}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Submodulo <span class="f_req">*</span></label>
                    <select id="selSubmodulo" class="form-control" data-req="">
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Menú <span class="f_req">*</span></label>
                    <select id="selMenu" class="form-control" data-req="">
                    </select>
                </div>
                <div class="col-sm-6 col-md-6">
                    <label>Submenu <span class="f_req">*</span></label>
                    <select id="selSubmenu" class="form-control" data-req="">
                    </select>
                </div>
            </div>
        </div>
        <div class="formSep">
            <div class="row">
                <div class="col-sm-6 col-md-6">
                    <label>Perfil <span class="f_req">*</span></label>
                    <select id="selPerfil" class="form-control" data-req="">
                        <option value="-1">-SELECCIONE-</option>
                        <c:forEach var="perfil" items="${perfiles}">
                            <option value="${perfil.idPerfil}">${perfil.nombrePerfil}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        <table id="tblPerfilOpcion" class="table table-bordered table-striped dTableR">
            <thead>
                <tr>
                <th>Opción</th>
                <th>Acción</th>
                </tr>
            </thead>            
        </table>
    </div>
    <div class="col-sm-2 col-md-2">
         <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
            <c:if test="${opcionSubmenu.appOpcion == 'change'}">                
                <button id="btnGuardar" class="btn btn-primary" title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>
            </c:if>
         </c:forEach>        
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#selModulo').change();
    });

    $('#selModulo').change(function() {
        var Data = {"id": "", "value": "idSubmodulo", "text": "nombreSubmodulo"};

        llenarSelect('#selSubmodulo', '<c:url value="/Submodulo/submodulosPorModuloJSON" />?idModulo=' + $('#selModulo').val(), Data, function() {
            $('#selSubmodulo').change();
        });
    });

    $('#selSubmodulo').change(function() {
        var Data = {"id": "", "value": "idMenu", "text": "nombreMenu"};

        llenarSelect('#selMenu', '<c:url value="/Menu/menusPorSubmoduloJSON" />?idSubmodulo=' + $('#selSubmodulo').val(), Data, function() {
            $('#selMenu').change();
        });
    });

    $('#selMenu').change(function() {
        var Data = {"id": "", "value": "idSubmenu", "text": "nombreSubmenu"};

        llenarSelect('#selSubmenu', '<c:url value="/Submenu/submenusPorMenuJSON" />?idMenu=' + $('#selMenu').val(), Data, function() {
            $('#selSubmenu').change();
        });
    });

    $('#selSubmenu').change(function() {
        opcionesPorSubmenu();
    });

    $('#selPerfil').change(function() {
        opcionesPorSubmenu();
    });

    function opcionesPorSubmenu() {

        var tblPerfilOpcion = document.getElementById('tblPerfilOpcion');

        if ($.fn.DataTable.fnIsDataTable(tblPerfilOpcion)) {
            $(tblPerfilOpcion).dataTable().fnDestroy();
        }

        $.ajax({
            url: '<c:url value="/PerfilOpcion/opcionesPorSubmenuJSON" />?idSubMenu=' + $('#selSubmenu').val() + '&idPerfil=' + $('#selPerfil').val(),
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $(tblPerfilOpcion).dataTable({
                    "sDom": "t",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "nombreOpcion", sWidth: "50%"},
                        {mData: "activo", bSortable: false, sWidth: "50%", mRender: function(data, type, row) {
                                var td = '';

                                if (data === 1) {
                                    td = '<input type="checkbox" checked="" data-idPerfilOpcion="' + row.idPerfilOpcion + '" data-idOpcion="' + row.idOpcion + '" />';
                                } else {
                                    td = '<input type="checkbox" data-idPerfilOpcion="' + row.idPerfilOpcion + '" data-idOpcion="' + row.idOpcion + '" />';
                                }

                                return td;
                            }
                        }
                    ]
                });
            }
        });
    }

    $('#btnGuardar').click(function(e) {
        e.preventDefault();

        if($('select[data-req] option:selected[value="-1"]').length > 0) {            
            smoke.alert('Seleccione filtros requeridos.');
            return;
        }

        if (tblPerfilOpcionEsVacia() === false) {

            var opciones = new Array();
            var trs = $('#tblPerfilOpcion tbody tr');

            for (var i = 0; i <= trs.length - 1; ++i) {
                var opcion = new Object();
                var checkbox = $(trs[i]).find('td:eq(1)').find('input');
                opcion.idPerfilOpcion = checkbox.attr('data-idPerfilOpcion');
                opcion.idOpcion = checkbox.attr('data-idOpcion');
                if(checkbox.prop('checked')){
                    opcion.activo = 1;
                }else{
                    opcion.activo = 0;
                }
                opciones.nombreOpcion = '';
                opciones.push(opcion);
            }

            var opcionesPerfil = {
                idPerfil: parseInt($('#selPerfil').val()),
                opciones: opciones
            };
            
            $.ajax({
                url: '<c:url value="/PerfilOpcion/insertarOModificarOpciones" />',
                type: 'POST',
                data: JSON.stringify(opcionesPerfil),
                contentType: 'application/json',
                success: function(jsonData, status, metaData) {
                    opcionesPorSubmenu();
                }
            });
        }else{
           smokeAlert('No hay opciones registradas, no se puede aplicar cambios.');
        }

    });

    function tblPerfilOpcionEsVacia() {
        var table = $('#tblPerfilOpcion').dataTable();
        if (table.fnSettings().aoData.length === 0) {
            return true;
        } else {
            return false;
        }
    }
</script>