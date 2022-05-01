<%@include file="../../includeTagLib.jsp" %>
<script>
    $(document).ready(function () {
        $("#btnAgregar").remove();
        $("#btnPDF").remove();
        $("#btnExcel").remove();
        $("body").addClass('no-change no-remove');
        $('#content').children().remove();
        $('#content').append($("#thead").html());
        $("#tblData-table").remove();
        window.gp.table = $("#tblData-tablersp");
        var tblModal = $("#stcock-edit-mdl").find('table').dataTable({"bAutoWidth": false});
        window.gp.table.dataTable({
            "bAutoWidth": false,
            "fnDrawCallback": function (oSettings) {
                if (window.gp.table.fnSettings().aoData.length > 0) {
                    $("body").undelegate('.row-data-edit', 'click');
                    $("body").delegate('.row-data-edit', 'click', function (e) {
                        e.preventDefault();
                        var id = $(this).attr('data-id');
                        $.ajax({
                            url: '<c:url value="/Inventario/inventarioProductosJSON" />?idProducto=' + id + '&idInventario=' + $('#idInventario').val(),
                            type: 'GET',
                            dataType: 'json',
                            contentType: 'application/json',
                            success: function (response) {
                                tblModal.fnClearTable();
                                var row;
                                for (var i in response) {
                                    if (response.hasOwnProperty(i)) {
                                        row = [];
                                        var lote = response[i]["lote"];
                                        var fechVto = new Date(response[i]["fechaVencimiento"]).toString(dateFormatJS);
                                        var cant = response[i]["cantidad"];
                                        var precio = response[i]["precio"];
                                        var conteo = response[i]["conteo"];
                                        var alterados = response[i]["cantidadAlterado"];

                                        var idInvProd = response[i]['idInventarioProducto'];
                                        row.push('<span>' + lote + '</span>');
                                        row.push('<span>' + fechVto + '</span>');
                                        row.push('<span>' + cant + '</span>');
                                        row.push('<span>' + fieldFormat.number(precio) + '</span>');
                                        row.push('<span class="dtlconteo"><input value="' + conteo + '"/></span>');
                                        row.push('<span class="dtlalterados"><input value="' + alterados + '"/></span>');
                                        var save = '<a href="javascript:void(0)" class="row-data-save separator-icon-td" data-id="' + id + '" data-id-inv-pr="' + idInvProd + '"><i class="splashy-check" title="Guardar Cambios"></a>';
                                        row.push(save);
                                        tblModal.fnAddData(row);
                                    }
                                }
                                $("#stcock-edit-mdl").modal('show');
                            }
                        });
                    });
                }
            }
        });
        $("#stcock-edit-mdl").delegate('.row-data-save', 'click', function (e) {
            e.preventDefault();
            var $tr = $(this).parent().parent();
            var conteo = $tr.find('td:eq(4) span input').val();
            var alterado = $tr.find('td:eq(5) span input').val();
            if (conteo < 0) {
                alertify.error("El conteo f&iacute;sico debe ser mayor o igual a cero.");
                return;
            }
            if (alterado < 0) {
                alertify.error("La cantidad de alterados debe ser mayor que cero.");
                return;
            }
            if (!conteo)
                conteo = 0;
            if (!alterado)
                alterado = 0;
            $.ajax({
                url: '<c:url value="/Inventario/modificarConteo" />?conteo=' + conteo + '&alterado=' + alterado + '&idInventarioProducto=' + $(this).attr('data-id-inv-pr'),
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                success: function (response) {
                    if (response.estado) {
                        for (var i in response.mensajesRepuesta) {
                            if (response.mensajesRepuesta.hasOwnProperty(i))
                                alertify.success(response.mensajesRepuesta[i]);
                        }
                        $("#btnFilter").click();
                    }
                    else
                        for (var i in response.mensajesRepuesta) {
                            if (response.mensajesRepuesta.hasOwnProperty[i])
                                alertify.error(response.mensajesRepuesta[i]);
                        }
                }
            });
        });
        crearCalendar("#fechaCierre");
        _AlmacenSelectFill();
        _PeriodoSelectFill();
        function _AlmacenSelectFill() {
            $.ajax({
                url: 'getAlmacenes',
                type: "GET",
                dataType: "json",
                success: function (r) {
                    var select = $('#almacen'),
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
        function _PeriodoSelectFill() {
            $.ajax({
                url: 'getPeriodos',
                type: "GET",
                dataType: "json",
                success: function (r) {
                    var select = $('#periodo'),
                            td, option;
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            td = r[key];
                            option = new Option(td.anio + " (" + td.nombreMes + ")", td.idPeriodo);
                            select.append(option);
                        }
                    }
                },
                failure: function (r) {
                }
            });
        }
        $("#btnFilter").click(function (e) {
            e.preventDefault();
            var almacen = $("#almacen").val();
            var periodo = $("#periodo").val();
            var fechaCierre = $("#fechaCierre").val();

            if (!almacen) {
                alertify.error("El almac&eacute;n es obligatorio");
                return;
            }

            if (!periodo) {
                alertify.error("El per&iacute;odo es obligatorio");
                return;
            }

            if (!fechaCierre) {
                alertify.error("La fecha de cierre es obligatoria");
                return;
            }

            var data = {
                almacen: almacen,
                periodo: periodo,
                fechaCierre: fechaCierre
            };

            $.ajax({
                url: 'consultar',
                type: 'POST',
                data: data,
                success: function (response) {
                    window.gp.table.fnClearTable();
                    if (response.data.invs) {
                        if (!response.hasError) {
                            window.gp.addRow(response.data.invs);
                            $("#idInventario").val(response.mssg[0].trim());
                            if (response.data.invs.length > 0)
                                $("#btnProcesar").prop("disabled", false);
                        } else {
                            for (var i in response.mssg) {
                                if (response.mssg.hasOwnProperty(i))
                                    alertify.error(response.mssg[i]);
                            }
                            $("#btnProcesar").prop("disabled", true);
                        }
                    } else {
                        $("#btnProcesar").prop("disabled", true);
                    }
                },
                failure: function (response) {

                }
            });
        });
        $('#btnProcesar').click(function (e) {
            e.preventDefault();
            $.ajax({
                url: '<c:url value="/Inventario/procesar" />?&idInventario=' + $('#idInventario').val(),
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                success: function (response) {
                    if (response.estado) {
                        for (var i in response.mensajesRepuesta) {
                            if (response.mensajesRepuesta.hasOwnProperty(i))
                                alertify.success(response.mensajesRepuesta[i]);
                        }
                        $("#btnFilter").click();
                    }
                    else
                        for (var i in response.mensajesRepuesta) {
                            if (response.mensajesRepuesta.hasOwnProperty[i])
                                alertify.error(response.mensajesRepuesta[i]);
                        }
                }
            });
        });
    });
</script>