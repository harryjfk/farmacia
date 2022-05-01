<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(document).ready(function () {
        var idModulo = location.pathname.split('/')[3];
        _crearUnidadChosenSelect();
        $('body').addClass('no-change');
        $('#startDate,#endDate').datepicker({dateFormat: 'dd/mm/yy'});
        $('#modalData-modal').bind({
            Añadir: function (event) {
                event.preventDefault();
                var almacen = $('#almacenSelect').val();
                $('#almacen').val(almacen);
                $('#porcentaje').val('100');
            },
            Editar: function (event, id, r) {
                event.preventDefault();
                $("#unidad").val(r.unidad.idUnidadMedida);
                $('#unidad').removeClass('chzn-done');
                $("#unidad_chzn").remove();
                _crearUnidadChosenSelect();
                var almacen = $('#almacenSelect').val();
                $('#almacen').val(almacen);
            }

        });
        function _crearUnidadChosenSelect() {
            $("#unidad").chosen({no_results_text: "No se encontraron coincidencias con"});
            $("#unidad_chzn").css({width: '100%'});
            $("#unidad_chzn .chzn-drop").css({width: '98%'});
            $("#unidad_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
        //Validar Precio
        $.validator.addMethod("currency", function (value, element) {
            return this.optional(element) || /^(\d+)[\.|,](\d{1,4})/.test(value);
        }, "Please specify a valid amount");
        $('#mdl-frm').validate({
            rules: {
                nombre: {required: true},
                unidad: {required: true},
                precio: {required: true, currency: true},
                cantidad: {required: true, digits: true}
            },
            messages: {
                nombre: {required: 'Este campo es obligatorio'},
                unidad: {required: 'Este campo es obligatorio'},
                precio: {required: 'Este campo es obligatorio', currency: 'Especifique un precio v&aacute;lido (Solo se admiten de 2 a 4 d&iacute;gitos despu&eacute;s del punto o la coma)'},
                cantidad: {required: 'Este campo es obligatorio', digits: 'Este campo solo admite d&iacute;gitos'}
            }
        });
        $('#consultar').click(function (e) {
            var start = $('#startDate').val();
            var end = $('#endDate').val();
            var desc = $('#descripcion').val();
            $.ajax({
                url: 'consultar',
                type: "GET",
                data: {
                    start: start,
                    end: end,
                    descripcion: desc
                },
                dataType: "json",
                success: function (r) {
                    window.gp.cleanTable();
                    window.gp.addRow(r);
                },
                failure: function (r) {
                }
            });
        });
        $('#inicializar').click(function () {
            $('#startDate,#endDate,#descripcion').val('');
            window.gp.cleanTable();
        });
        var tbl = document.getElementById("tblData-table");
        if ($.fn.DataTable.fnIsDataTable(tbl)) {
            $(tbl).dataTable().fnDestroy();
        }

        $(tbl).dataTable({
            "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": false,
            "aoColumns": [
                {mData: "nombre", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "unidad.nombreUnidadMedida", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "porcentaje", "bSortable": true, mRender: function (data, type, row) {
                        return data;
                    }},
                {mData: "precio", "bSortable": false, mRender: function (data, type, row) {
                        return fieldFormat.money2(data);
                    }},
                {mData: "cantidad", "bSortable": true},
                {mData: "id", "bSortable": false, mRender: function (data, type, row) {
                        var edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data + '" data-url="modificar"><i class="splashy-pencil" title="Editar"></i></a>';
                        var remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data + '" data-url="eliminar"><i class="splashy-remove" title="Eliminar"></i></a>';
                        var materias = '<a href="javascript:void(0)" class="row-data-insumos separator-icon-td" data-id="' + data + '" title="Ver Insumos">Insumos</a>';
                        return edit + remove + materias;
                    }}
            ]
        });
        var onload = true;
        window.gp.addRow = function (row) {
            $(tbl).dataTable().fnAddData(row);
            $(tbl).triggerHandler('addRow');
            onload = false;
        };

        $(tbl).bind({
            addRow: function (e) {
                e.preventDefault();
                var url = '<c:url value="/"/>' + "dispensacion/" + idModulo + "/materias/getMaterias";
                if (!onload) {
                    $.ajax({
                        url: url,
                        type: 'GET',
                        success: function (response) {
                            $("#selectinsumo").html("");
                            for (var i in response) {
                                var item = response[i];
                                $("#selectinsumo").append('<option value="' + item.id + '">' + item.nombre + '</option>');
                            }
                            $('#selectinsumo').removeClass('chzn-done');
                            $("#selectinsumo_chzn").remove();
                            _createInsumosChosen();
                        },
                        failure: function (response) {

                        }
                    });
                }
            }
        });

        $(tbl).delegate('.row-data-insumos', 'click', function (e) {
            e.preventDefault();
            var tblI = document.getElementById("gp-tblInsumos");
            if ($.fn.DataTable.fnIsDataTable(tblI)) {
                $(tblI).dataTable().fnDestroy();
            }
            $(tblI).dataTable({
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "aoColumns": [
                    {mData: "insumo.nombre", "bSortable": true},
                    {mData: "insumo.precio", "bSortable": true, mRender: function (data, type, row) {
                            return fieldFormat.money2(data);
                        }},
                    {mData: "cantidad", "bSortable": true},
                    {mData: "insumo.id", "bSortable": true, mRender: function (data, type, row) {
                            var remove = '<a href="#" class="row-data-remove-insumo separator-icon-td" data-id="' + data + '"><i class="splashy-remove" title="Eliminar"></i></a>';
                            return remove;
                        }}
                ]
            });
            var dataId = $(this).attr('data-id');
            var name = $("#insumos-mdl").find("#selectinsumo option[value='" +
                    dataId + "']").html();
            $('#selectinsumo_chzn ul li:contains("' + name + '")').hide();
            if ($("#selectinsumo_chzn a span").html().trim() == name.trim()) {
                $("#selectinsumo_chzn a span").html('');
            }
            $("#insumos-mdl").find("#matrizId").val(dataId);
            $(tblI).dataTable().fnClearTable();
            _loadInsumos(dataId);
        });
        var _loadInsumos = function (matrizId) {
            var tblI = document.getElementById("gp-tblInsumos");
            $(tblI).dataTable().fnClearTable();
            $.ajax({
                url: 'getInsumos',
                type: 'POST',
                data: {matrizId: matrizId},
                success: function (response) {
                    $(tblI).dataTable().fnAddData(response);
                    $("#insumos-mdl").modal('show');
                },
                failure: function (response) {

                }
            });
        };
        $("#agregarInsumo").click(function (e) {
            e.preventDefault();
            var materiaId = $("#insumos-mdl").find("#selectinsumo").val();
            var matrizId = $("#insumos-mdl").find("#matrizId").val();
            var cantidad = $("#insumos-mdl").find("#icantidad").val();
            $.ajax({
                url: 'agregarInsumo',
                type: 'POST',
                data: {matrizId: matrizId, materiaId: materiaId, cant: cantidad},
                success: function (response) {
                    if (!response.hasError) {
                        alertify.success(response.mssg[0]);
                        $("#gp-tblInsumos").dataTable().fnAddData(response.data);
                    } else {
                        alertify.error(response.mssg[0]);
                    }
                },
                failure: function (response) {

                }
            });
        });
        $('#insumos-mdl').on('hidden.bs.modal', function () {
            $('#selectinsumo_chzn ul li').show();
        });
        $("#gp-tblInsumos").delegate('.row-data-remove-insumo', 'click', function (e) {
            e.preventDefault();
            var insumoId = $(this).attr('data-id');
            var matrizId = $("#addmatcontainer").find('#matrizId').val();
            $.ajax({
                url: 'eliminarInsumo',
                type: 'POST',
                data: {matrizId: matrizId, materiaId: insumoId},
                success: function (response) {
                    if (!response.hasError) {
                        _loadInsumos(matrizId);
                        alertify.success(response.mssg[0]);
                    } else {
                        alertify.error(response.mssg[0]);
                    }
                },
                failure: function (response) {

                }
            });
        });
        $(tbl).delegate('.row-data-remove', 'click', function (e) {
            e.preventDefault();
            $(tbl).triggerHandler('addRow');
        });

        _createInsumosChosen = function () {
            $("#insumos-mdl").find("#selectinsumo").chosen({no_results_text: "No se encontraron coincidencias con"});
            $("#selectinsumo_chzn").css({width: '100%'});
            $("#selectinsumo_chzn .chzn-drop").css({width: '98%'});
            $("#selectinsumo_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        };
        _createInsumosChosen();
    });
</script>