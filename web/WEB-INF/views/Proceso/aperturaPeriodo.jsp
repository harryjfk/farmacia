<%@include file="../includeTagLib.jsp" %>

<div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="formSep">
            <div class="row">
                <div class="col-sm-2 col-md-2">
                    <label>Año</label>
                    <input type="text" id="Anio" class="form-control" readonly="readonly"/>
                </div>
            </div>
        </div>
        <div class="form-actions">
            <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                <c:if test="${opcionSubmenu.appOpcion == 'aperturar'}">
                    <button id="btnApeturar" class="btn btn-default" data-loading-text="Procesando..." title="${opcionSubmenu.nombreOpcion}">${opcionSubmenu.nombreOpcion}</button>                
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-5 col-md-12">
        <div class="alert alert-info"
             <label>
                <b>¡Aviso!</b> 
                <ul>
                    <li>El proceso de apertura del tiene como finalidad habilitar las operaciones del año.</li>
                    <li>El proceso de apertura del nuevo periodo tiene como finalidad habilitar operaciones del mes y cerrar el anterior.</li>                    
                </ul>
            </label>
        </div>
    </div>
</div>

<div class="row">
    <div id="divTable" class="col-sm-6 col-md-6">

    </div>
</div>

<script>
    $('#Anio').val(new Date().toString('yyyy'));
    $('#Anio').spinner({
        spin: function(event, ui) {
            getPeriodosPorAnio(ui.value);
        }
    });

    $('.ui-spinner').css({'background-color': '#eeeeee'});

    $(document).ready(function() {
        getPeriodosPorAnio($('#Anio').val());
    });

    function getPeriodosPorAnio(anio) {
        $.ajax({
            url: '<c:url value="/Periodo/periodosPorAnio" />/' + anio,
            type: 'GET',
            contentType: 'application/json',
            success: function(jsonData) {
                createTable(jsonData);
            }
        });
    }

    function createTable(jsonData) {
        var $htmlTable = $('<table id="tblPeriodo" class="table table-bordered table-striped dTableR"><thead><th>Mes</th><th>Abierto / Cerrado</th><th>Acción</th></thead><tbody></tbody></table>');
        var $tbody = $htmlTable.find('tbody');
        
        if (jsonData.length > 0) {
            var indice = null;
            
            for (var i = 0; i < jsonData.length; ++i) {
                var estadoText = '';
                var $btnOpenClose = null;
                
                if (jsonData[i].activo == 1) {
                    indice = i;
                    estadoText = 'Abierto';
                }else{
                    estadoText = 'Cerrado';
                }
                
                if(indice != null){
                    if(i == indice + 1){
                        $btnOpenClose = $('<button class="btn btn-sm btn-default"></button>');
                        $btnOpenClose.text('Abrir');                        
                        $btnOpenClose.attr('data-loading-text', 'Procesando...');
                        $btnOpenClose.attr('onclick', 'abrirMes(' + jsonData[i].idPeriodo + ',this, event)');
                    }
                }

                var row = $('<tr></tr>');
                var cellNombreMes = $('<td></td>)');
                var cellEstado = $('<td></td>)');
                var cellButton = $('<td></td>)');
                cellNombreMes.append(jsonData[i].nombreMes);
                cellEstado.append(estadoText);

                <c:forEach var="opcionSubmenu" items="${opcionesSubmenu}">
                    <c:if test="${opcionSubmenu.appOpcion == 'estado'}">
                        if($btnOpenClose != null){
                            cellButton.append($btnOpenClose);
                        }
                    </c:if>
                </c:forEach>

                row.append(cellNombreMes);
                row.append(cellEstado);
                row.append(cellButton);
                $tbody.append(row);
            }

            $('#btnApeturar').attr('disabled', 'disabled');
        } else {
            var row = '<tr><td colspan="3">Año sin aperturar.</td></tr>';
            $tbody.append(row);
            $('#btnApeturar').removeAttr('disabled');
        }

        $('#divTable').html($htmlTable);
    }

    $('#btnApeturar').click(function(e) {
        e.preventDefault();

        var $btn = $(this).button('loading');

        var anio = $('#Anio').val();

        $.ajax({
            url: '<c:url value="/Periodo/aperturar" />/' + anio,
            type: 'GET',
            contentType: 'application/json',
            success: function(dataResponse) {
                $btn.button('reset');
                var f = function() {
                    getPeriodosPorAnio(anio);
                };
                functionResponse(dataResponse, f);
            }
        });
    });

    function abrirMes(idPeriodo, element, e) {
        e.preventDefault();
        var $btn = $(element).button('loading');

        $.ajax({
            url: '<c:url value="/Periodo/abrirMes" />/' + idPeriodo,
            type: 'GET',
            contentType: 'application/json',
            success: function(dataResponse) {
                $btn.button('reset');
                var f = function() {
                    var anio = $('#Anio').val();
                    getPeriodosPorAnio(anio);
                };

                functionResponse(dataResponse, f);
            }
        });
    }
</script>