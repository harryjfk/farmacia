<script>
    $(document).ready(function () {
        $('#StartDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#EndDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#tblData-table').dataTable().fnSetColumnVis(7, false);
        $('.main-button-row').hide();
        $('#btnAgregarProductoLote').hide();
        $('#almacenSelect').change(function (e) {
            e.preventDefault();
        });
        $('#btnConsultarInventario').click(function (e) {
            var start =  $('#StartDate').val();
            var end =  $('#EndDate').val();
            $.ajax({
                url: 'consultarInventarios',
                type: "GET",
                data: {
                    idAlmacen: $('#almacenSelect').val(),
                    start:start,
                    end:end,
                    especialiad: $("#especialidad").val()
                },
                dataType: "json",
                success: function (r) {
                    $('#tblData-table').dataTable().fnClearTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
        _AlmacenSelectFill();
        function _AlmacenSelectFill() {
            $.ajax({
                url: 'getAlmacenes',
                type: "GET",
                dataType: "json",
                success: function (r) {
                    var select = $('#almacenSelect'),
                            td, option;
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            td = r[key];
                            option = new Option(td.descripcion, td.idAlmacen);
                            select.append(option);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        }
        ;
    });
</script>