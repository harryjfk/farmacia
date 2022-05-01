<script>
    var idModulo = location.pathname.split('/')[3];
    var subcId = 0;
    var compId = 0;

    $(document).ready(function () {
        window.gp.ajaxList = "getSubComponentes";
        window.gp.addRow = function (r) {
            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    var data = r[key];
                    window.gp.table.fnAddData(data);
                }
            }
        };
        var tbl = document.getElementById("tblData-table");
        if ($.fn.DataTable.fnIsDataTable(tbl)) {
            $(tbl).dataTable().fnDestroy();
        }
        $(tbl).dataTable({
            "aoColumns": [
                {mData: "id"},
                {mData: "descripcion"},
                {mData: "diagnostico.descripcion"},
                {mData: "coordinador.nombreCompleto"},
                {mData: "id", "bSortable": false, mRender: function (data, type, row) {
                        var estado = (row.activo) ? '<i class="splashy-thumb_up" title="Cambiar estado a Desactivado"></i>' : '<i class="splashy-thumb_down" title=" Cambiar estado a Activado"></i>';

                        var edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data + '" data-url="modificar"><i class="splashy-pencil" title="Editar"></i></a>';
                        var change = '<a  href="#" class="row-data-change separator-icon-td" data-activo="' + row.activo + '" data-id="' + data + '" data-url="cambiarEstado">' + estado + '</a>';
                        var remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data + '" data-url="eliminar"><i class="splashy-remove" title="Eliminar"></i></a>';

                        var prods = "<a class='showp' data-id='" + data + "' href='javascript:void(0)'>Productos</a>&nbsp;|&nbsp;";
                        var kits = "<a class='showk' data-id='" + data + "' href='javascript:void(0)'>Kits</a>";
                        return edit + change + remove + prods + kits;
                    }}]
        });
        var coordinadorModal = new PersonalGeneral();
        function _selectCoordinador(row) {
            if (row.personal) {
                var nombre = row.nombres + " " + row.apellidoPaterno + " " + row.apellidoMaterno;
                $("#coor").val(nombre);
                $("#coordinador").val(row.personal);
            }
            coordinadorModal._modal.hide();
        }
        coordinadorModal._table.set('click', _selectCoordinador);
        $("#seleccionaCoor").click(function () {
            coordinadorModal._modal.show();
        });
        $('#coor').on('keyup', function () {
            $("#coordinador").val('');
        });

        var diagnosticoModal = new DiagnosticoGeneral();
        function _selectDiagnostico(row) {
            if (row.descripcion) {
                var desc = row.descripcion;
                $("#diag").val(desc);
                $("#diagnostico").val(row.codigo);
            }
            diagnosticoModal._modal.hide();
        }
        diagnosticoModal._table.set('click', _selectDiagnostico);
        $("#seleccionaDiag").click(function () {
            diagnosticoModal._modal.show();
        });
        $('#diag').on('keyup', function () {
            $("#diagnostico").val(0);
        });
        $('#modalData-modal').bind({
            Editar: function (event, id, data) {
                event.preventDefault();
                $("#mdl-form").find('#coor').val("");
                $("#mdl-form").find('#diag').val("");
                if (data.coordinador) {
                    var nombre = data.coordinador.nombres + " " + data.coordinador.apellidoPaterno + " " + data.coordinador.apellidoMaterno;
                    $("#mdl-form").find('#coor').val(nombre);
                    $("#mdl-form").find('#coordinador').val(data.coordinador.personal);
                }
                if (data.diagnostico) {
                    $("#mdl-form").find('#diagnostico').val(data.diagnostico.codigo);
                    $("#mdl-form").find('#diag').val(data.diagnostico.descripcion);
                }
                $("#mdl-form").find("#componente").val(compId);
            },
            Añadir: function (event) {
                event.preventDefault();
                $("#mdl-form").find('#coor').val("");
                $("#mdl-form").find('#diag').val("");
                $("#mdl-form").find("#componente").val(compId);
            }
        });
        $("#comp").change(function (e) {
            e.preventDefault();
            compId = $(this).val();
            window.gp.data['componente'] = compId;
            window.gp.reportData = {componente: compId};
            window.gp.getData();
        });
        $('#tblData-table').delegate('.showp', 'click', function (e) {
            var elem = $(this);
            mostrarModalProds(e, elem);
        });
        $('#tblData-table').delegate('.showk', 'click', function (e) {
            var elem = $(this);
            mostrarModalKits(e, elem);
        });
        //Productos
        var tblProductos = document.getElementById('gp-tblProductos');
        function mostrarModalProds(e, elem) {
            e.preventDefault();
            subcId = $(elem).attr('data-id');
            $('#mdl-prod-sub').modal('show');
            if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
                $(tblProductos).dataTable().fnDestroy();
            }
            $(tblProductos).dataTable({
                "sAjaxSource": subcId + '/productosJSON',
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "iDisplayLength": 9,
                "aoColumns": [
                    {mData: "idProducto"},
                    {mData: "descripcion"},
                    {mData: "idFormaFarmaceutica.nombreFormaFarmaceutica"},
                    {mData: "idUnidadMedida.nombreUnidadMedida"},
                    {mData: "concentracion"},
                    {mData: "idProducto", "bSortable": false, "mRender": function (data, type, row) {
                            return '<span class="rmprod" data-id=' + data + '><i class="splashy-remove"></i></span>';
                        }
                    }
                ]
            });
        }
        $("#gp-tblProductos").delegate('span.rmprod', 'click', function (e) {
            e.preventDefault();
            var productoId = $(this).attr('data-id');
            smoke.confirm("¿Est&aacute; seguro que desea eliminar este producto?", function (e) {
                if (e) {
                    $.ajax({
                        url: 'eliminarProducto',
                        type: 'POST',
                        data: {idProducto: productoId, idSubComponente: subcId},
                        success: function (response) {
                            if (!response.hasError)
                                _reloadTable(tblProductos);
                            else
                                for (var i in response.mssg) {
                                    if (response.mssg.hasOwnProperty(i))
                                        alertify.error(response.mssg[i]);
                                }
                        },
                        failure: function (response) {
                        },
                        error: function (response) {
                        }
                    });
                }
            });
        });
        $("#btnAgregarProducto").click(function (e) {
            e.preventDefault();
            var productoId = $("#producto").val();
            $.ajax({
                url: 'agregarProducto',
                type: 'POST',
                data: {idProducto: productoId, idSubComponente: subcId},
                success: function (response) {
                    if (!response.hasError)
                        _reloadTable(tblProductos);
                    else
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                },
                failure: function (response) {
                },
                error: function (response) {
                }
            });
        });
        //Kits
        var tblKits = document.getElementById('gp-tblKits');
        function mostrarModalKits(e, elem) {
            e.preventDefault();
            subcId = $(elem).attr('data-id');
            $('#mdl-kit-sub').modal('show');
            if ($.fn.DataTable.fnIsDataTable(tblKits)) {
                $(tblKits).dataTable().fnDestroy();
            }
            $(tblKits).dataTable({
                "sAjaxSource": subcId + '/kitsJSON',
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "iDisplayLength": 9,
                "aoColumns": [
                    {mData: "id"},
                    {mData: "descripcion"},
                    {mData: "id", "bSortable": false, "mRender": function (data, type, row) {
                            return '<span class="rmkit" data-id=' + data + '><i class="splashy-remove"></i></span>';
                        }
                    }
                ]
            });
        }
        $("#gp-tblKits").delegate('span.rmkit', 'click', function (e) {
            e.preventDefault();
            var kitId = $(this).attr('data-id');
            smoke.confirm("¿Est&aacute; seguro que desea eliminar este kit?", function (e) {
                if (e) {
                    $.ajax({
                        url: 'eliminarKit',
                        type: 'POST',
                        data: {id: kitId, idSubComponente: subcId},
                        success: function (response) {
                            if (!response.hasError)
                                _reloadTable(tblKits);
                            else
                                for (var i in response.mssg) {
                                    if (response.mssg.hasOwnProperty(i))
                                        alertify.error(response.mssg[i]);
                                }
                        },
                        failure: function (response) {
                        },
                        error: function (response) {
                        }
                    });
                }
            });
        });
        $("#btnAgregarKit").click(function (e) {
            e.preventDefault();
            var kitId = $("#kit").val();
            $.ajax({
                url: 'agregarKit',
                type: 'POST',
                data: {id: kitId, idSubComponente: subcId},
                success: function (response) {
                    if (!response.hasError)
                        _reloadTable(tblKits);
                    else
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                },
                failure: function (response) {
                },
                error: function (response) {
                }
            });
        });
        function _reloadTable(tbl) {
            var dataTable = $(tbl).dataTable();
            dataTable.fnReloadAjax();
        }
        $('#mdl-form').validate({
            rules: {
                descripcion: {
                    required: true
                },
                coor: {
                    required: true
                },
                diag: {
                    required: true
                }
            },
            messages: {
                descripcion: {
                    required: 'Este campo es obligatorio'
                },
                coor: {
                    required: 'Seleccione un Coordinador'
                },
                diag: {
                    required: 'Seleccione un Diagn&oacute;stico'
                }
            }
        });
        _ProductoSelectFill("#producto");
        _ComponenteSelectFill("#comp");
        $("#comp").bind({
            Lleno: function (e) {
                e.preventDefault();
                compId = $("#comp option:first").attr('value');
                window.gp.data['componente'] = compId;
                window.gp.reportData = {componente: compId};
                window.gp.getData();
            }
        });

        var selector = '#kit';
        var options = {
            url: '/' + idModulo + '/kitatencion/listarJSON',
            success: function (r, status, ajaxData) {
                var select = $(selector);
                var data = r.data || r;
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        var td = data[key];
                        var opt = $('<option value="' + td.id + '">' + td.descripcion + '</option>');
                        select.append(opt);
                    }
                }
                $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
                $(selector + "_chzn").css({width: '100%'});
                $(selector + "_chzn .chzn-drop").css({width: '98%'});
                $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
            }
        };
        _getList(options);
    });
</script>