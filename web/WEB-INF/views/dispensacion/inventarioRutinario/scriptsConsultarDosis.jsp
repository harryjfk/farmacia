<script>
    $(document).ready(function () {
        var tbl = document.getElementById("tblData-table");
        if ($.fn.DataTable.fnIsDataTable(tbl)) {
            $(tbl).dataTable().fnDestroy();
        }

        $(tbl).dataTable({
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "id", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "productolote.producto.descripcion", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "productolote.producto.idTipoProducto.nombreTipoProducto", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "productolote.producto.idFormaFarmaceutica.nombreFormaFarmaceutica", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "stock", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "stockReal", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "precio", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "id", "bSortable": true, mRender: function (data, type, row) {
                        var remove = '<a href="#" class="row-data-stock separator-icon-td" data-id="' + data + '"><i class="splashy-arrow_large_right" title="Ver Stock En Otras Farmacias"></i></a>';
                        return remove;
                    }}
            ]
        });

        $('#StartDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#EndDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('.main-button-row').hide();
        $('#btnAgregarProductoLote').hide();
        $('#almacenSelect').change(function (e) {
            e.preventDefault();
        });
        $("#tblData-table").delegate('a.row-data-stock', 'click', function (e) {
            e.preventDefault();
            var me = $(this);
            if(me.attr('clicked')) {
                return false;
            }
            me.attr('clicked', true);
            var elem = $("#otrosAlmacenes").find("div");
            elem.html('');
            $("#otrosAlmacenes").find("span").html();
            var idProducto = $(this).attr('data-id');
            $.ajax({
                url: 'obtenerStocksGeneral',
                type: 'POST',
                data: {'idProducto': idProducto},
                success: function (response) {
                    if (!response.hasError) {
                        $("#otrosAlmacenes").find("span").show();
                        elem.append('<table class="table table-bordered table-striped dTableR select-table"><thead><tr><th>Almac&eacute;n</th><th>Stock</th></tr></thead><tbody></tbody></table>');
                        var data = response.data;
                        for (var almacen in data) {
                            var row = $("<tr>");
                            row.append('<td>' + almacen + '</td>');
                            row.append('<td>' + data[almacen] + '</td>');
                            elem.find('table').find('tbody').append(row);
                        }
                    } else {
                        alertify.error(response.msg);
                        elem.html('');
                        $("#otrosAlmacenes").find("span").hide();
                    }
                    me.removeAttr('clicked');
                },
                failure: function (response) {
                    $("#otrosAlmacenes").find("span").hide();
                     elem.html('');
                     me.removeAttr('clicked');
                }
            });
        });
        $('#btnConsultarInventario').click(function (e) {
            e.preventDefault();
            $("#otrosAlmacenes").find("div").html('');
            $("#otrosAlmacenes").find("span").hide('');
            var start = $('#StartDate').val();
            var end = $('#EndDate').val();
            $.ajax({
                url: 'consultarInventarios',
                type: "GET",
                data: {
                    idAlmacen: $('#almacenSelect').val(),
                    start: start,
                    end: end,
                    especialiad: $("#especialidad").val()
                },
                dataType: "json",
                success: function (r) {
                    $('#tblData-table').dataTable().fnClearTable();
                    addRow(r);
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
        function addRow(r) {
            for (var i in r) {
                if (r.hasOwnProperty(i))
                    $('#tblData-table').dataTable().fnAddData(r[i]);
            }
        }
    });
</script>