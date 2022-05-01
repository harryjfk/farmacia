<h3 class="heading">Mantenimiento de Parámetros</h3>

<div class="row">
    <div class="col-sm-6 col-md-6">
        <button id="btnAgregar" class="btn btn-primary">Agregar</button>
    </div>
</div>

<div class="row">
    <div class="col-sm-10 col-md-10">
        
        <table id="tblParametro" class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Parámetro</th>
                    <th>Descripción</th>
                    <th>Valor</th>
                    <th>Acción</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<script>
    $('#btnAgregar').click(function (e){
        window.location = 'registrar';
    });
    
    $(document).ready(function() {
        listarParametros();
    });
    
    function listarParametros(){
        var tblParametro = document.getElementById('tblParametro') ;
        
        if ($.fn.DataTable.fnIsDataTable(tblParametro)) {
            $(tblParametro).dataTable().fnDestroy();
        }

        $.ajax({
            url: 'parametrosJSON',
            type: 'GET',
            dataType: 'json',
            success: function(jsonData, status, metaData) {
                $(tblParametro).dataTable({
                    "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'i><'col-sm-7'p>>",
                    "sPaginationType": "bootstrap_alt",
                    "aaData": jsonData,
                    "aoColumns": [
                        {mData: "idParametro", sWidth: "10%"},
                        {mData: "nombreParametro", sWidth: "15%"},
                        {mData: "descripcionParametro"},
                        {mData: "valor", sWidth: "15%"},
                        {mData: "idParametro", sWidth: "10%", "bSortable": false, "mRender": function(data, type, row) {
                                return '<a href="editar?id=' + data + '" class="separator-icon-td"><i class="splashy-pencil"></i></a>' +
                                       '<a href="#" class="separator-icon-td" onclick="eliminarParametro(' + data + ', event, this)"><i class="splashy-remove"></i></a>';
                            }
                        }
                    ]
                });
            }
        });
    }
    
    function eliminarParametro(id, e, element) {
        e.preventDefault();

        var parametroTexto = $(element).parent().parent().find('td:eq(1)').text().trim();

        smoke.confirm('¿Está seguro que desea eliminar el parámetro ' + parametroTexto + '?', function(e) {
            if (e) {
                $.ajax({
                    url: 'eliminar/' + id,
                    type: 'POST',
                    dataType: 'json',
                    success: function(dataResponse, status, metaData) {
                        var f = function (){ listarParametroes(); }
                        functionResponse(dataResponse, f);
                    }
                });
            }
        });
    }
</script>