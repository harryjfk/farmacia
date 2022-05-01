<%@include file="../../includeTagLib.jsp" %>
<script>
    var idModulo = location.pathname.split('/')[3];
    var tblKits = document.getElementById('tblKits');
    var editKit = null;
    $(document).ready(function () {
        $('#selAnio').change(function () {
            if (!Number($(this).val()) || $(this).val().length < 4) {
                limpiarSelect('#selMes');
            } else {
                var Data = {"id": "", "value": "mes", "text": "nombreMes"};
                llenarSelect('#selMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function () {
                    var mes = new Date().toString('MM');
                    $('#selMes').val(mes);
                });
            }
        });
        $('#selMAnio').change(function () {//modal form
            if (!Number($(this).val()) || $(this).val().length < 4) {
                limpiarSelect('#selMMes');
            } else {
                var Data = {"id": "", "value": "mes", "text": "nombreMes"};
                llenarSelect('#selMMes', '<c:url value="/Periodo/periodosPorAnio" />/' + $(this).val(), Data, function () {
                    var mes = new Date().toString('MM');
                    $('#selMMes').val(mes);
                    if (editKit && editKit.periodo) {
                        var periodo = new Date(editKit.periodo);
                        if (periodo) {
                            var mes = (Number(periodo.getMonth()) + 1) >= 10 ? Number(periodo.getMonth()) + 1 : '0' + (Number(periodo.getMonth()) + 1);
                            var anio = periodo.getFullYear();
                            $("#mdl-form").find('#selMAnio').val(anio);
                            $("#mdl-form").find('#selMMes').val(mes);
                        }
                    }
                });
            }
        });
        var anio = new Date().toString('yyyy');
        $('#selAnio').val(anio);
        $('#selAnio').change();
        $("#selAnio, #selMAnio").keypress(function (key) {
            if (key.charCode < 48 || key.charCode > 57)
                return false;
        });
        _ProductoSelectFill("#producto");
        $(tblKits).dataTable({
            "fnServerParams": function (aoData) {
                aoData.push({"name": 'componente', "value": $('#componente').val() || 0});
                aoData.push({"name": 'subComponente', "value": $('#subcomponente').val() || 0});
                aoData.push({"name": 'proceso', "value": $('#proceso').val() || 0});
                if ($("#selMes").val()) {
                    var periodo = '1/' + $("#selMes").val().replace('0', '') + '/' + $("#selAnio").val();
                    if ($("#selMes").val().replace('0', '') != '-1') {
                        aoData.push({"name": 'periodo', "value": periodo || null});
                    }
                }
            },
            "sAjaxSource": '<c:url value="/dispensacion/' + idModulo + '/kitatencion/getKitsAtencion" />',
            "bServerSide": true,
            "bProcessing": true,
            "sDom": "<'row'<'col-sm-6'l><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
            "sPaginationType": "bootstrap_alt",
            "bAutoWidth": true,
            "aoColumns": [
                {mData: "id", "bSortable": true},
                {mData: "descripcion", "bSortable": true},
                {mData: "id", "bSortable": false, mRender: function (data, type, row) {
                        var edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + data + '" data-url="editar"><i class="splashy-pencil" title="Editar"></i></a>';
                        var change = '<a  href="#" class="row-data-change separator-icon-td" data-activo="' + row.activo + '" data-id="' + data + '" data-url="cambiarEstado">' + _obtenerEstado(row.activo) + '</a>';
                        var remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + data + '" data-url="eliminarKit"><i class="splashy-remove" title="Eliminar"></i></a>';

                        return edit + change + remove + '<a href="javascript:void(0)" class="row-data-productos separator-icon-td" data-id="' + data + '" title="Ver productos asociados a este kit">Productos</a>';
                    }}
            ]
        });

        $("#btnAgregar").click(function (e) {
            e.preventDefault();
            $('#modalData-modal').find('.modal-title').text("Agregar");
            $('#modalData-modal').modal('show');
        });
        $('#modalData-modal').on('show.bs.modal', function () {
            $("#mcomponente").val($("#componente").val());
            $("#mproceso").val($("#proceso").val());
            $("#mcomponente").change();
            $("#msubComponente").bind({
                subcomponentelisto: function (e) {
                    e.preventDefault();
                    if (editKit)
                        $("#msubComponente").val(editKit.subComponente.id);
                    else
                        $("#msubComponente").val($("#subComponente").val());
                    _remakeChz("#msubComponente");
                }
            });
            $("#selMAnio").val($("#selAnio").val());
            $("#selMAnio").change();
        });
        $('#modalData-modal').on('hide.bs.modal', function () {
            editKit = null;
        });

        $("#btnGuardar").click(function (e) {
            e.preventDefault();
            var desc = $("#mdl-form").find('#descripcion').val();
            var id = $("#mdl-form").find('#id').val();
            var compId = $("#mdl-form").find('#mcomponente').val();
            var subId = $("#mdl-form").find('#msubComponente').val();
            var procId = $("#mdl-form").find('#mproceso').val();
            var periodo = '1/' + $("#selMMes").val().replace('0', '') + '/' + $("#selMAnio").val();
            if ($("#selMMes").val().replace('0', '') == '-1') {
                alertify.error("Seleccione un mes");
                return;
            }
            if (desc.length == 0) {
                alertify.error("Debe especificar un nombre");
                return;
            }
            if (!Number(procId)) {
                alertify.error("Debe seleccionar un proceso");
                return;
            }
            var url = $("#mdl-form").attr('action') || 'insertar';
            $.ajax({
                url: url,
                type: 'POST',
                data: {'desc': desc, 'id': id, 'componente': compId, 'subComponente': subId, 'proceso': procId, periodo: periodo},
                success: function (response) {
                    if (!response.hasError) {
                        $("#componente").val($("#mdl-form").find('#mcomponente').val());
                        _subComponenteSelectFill("#subcomponente", $("#mdl-form").find('#mcomponente').val(), function () {
                            $("#subcomponente").val($("#mdl-form").find('#msubcomponente').val());
                            $("#proceso").val($("#mdl-form").find('#mproceso').val());
                        });
                        $("#selAnio").val($("#selMAnio").val());
                        $("#selAnio").change();
                        setTimeout(function () {
                            $("#selMes").val($("#selMMes").val());
                            for (var i in response.mssg) {
                                if (response.mssg.hasOwnProperty(i))
                                    alertify.success(response.mssg[i]);
                            }
                            $(tblKits).dataTable().fnReloadAjax();
                            _cleanForm($("#mdl-form"));
                        }, 2000);
                        $('#modalData-modal').modal('hide');
                    }
                    else
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                },
                failure: function (response) {

                }
            });
        });
        function _obtenerEstado(estado) {
            return (estado) ? '<i class="splashy-thumb_up" title="Cambiar estado a Desactivado"></i>' : '<i class="splashy-thumb_down" title=" Cambiar estado a Activado"></i>';
        }
        function _cleanForm(selector) {
            $(selector).find('input[type="text"],textarea,input[type="hidden"]').val('');
            $(selector).find('input').val('');
            $(selector).find('input[type="checkbox"]').prop('checked', false);
            $('#divMessage,.divMessage').html('');
        }
        function _setPathName(path) {
            var myLocation = window.location.href.split('/'),
                    result = "";
            myLocation.pop();

            for (var key in myLocation) {
                if (myLocation.hasOwnProperty(key)) {
                    result += myLocation[key] + "/";
                }
            }
            result += path;
            window.location.href = result;
        }
        function fillForm(url, form, id) {
            if (url !== '') {
                $.getJSON(url, {id: id}, function (r) {
                    var input = null;
                    if (r.data) {
                        r = r.data;
                    }
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            input = form.find('#' + key);
                            if (input.length > 0) {
                                input.val(r[key]);
                            } else if (typeof r[key] == 'object') {
                                input = form.find('#m' + key.toLowerCase());
                                if (input.length > 0)
                                    input.val(r[key].id);
                            }
                        }
                    }
                    editKit = r;
                    $("#mcomponente").change();
                });
            }
            else {
                modal.triggerHandler('Editar', [id]);
            }
        }

        $('body').delegate('#btnPDF', 'click', function (event) {
            event.preventDefault();
            _setPathName("/reportePdf");
        });
        $('body').delegate('#btnExcel', 'click', function (event) {
            event.preventDefault();
            _setPathName("/reporteExcel");
        });
        $('body').delegate('.row-data-remove', 'click', function (event) {
            event.preventDefault();
            var id = $(this).attr('data-id');
            var url = $(this).attr('data-url');
            smoke.confirm('¿Está seguro que desea eliminar estos datos?', function (e) {
                if (e) {
                    $.ajax({
                        url: url,
                        data: {id: id},
                        type: 'POST',
                        dataType: 'json',
                        success: function (r) {
                            if (r.hasError) {
                                alertify.error(r.mssg[0]);
                            }
                            else {
                                alertify.success(r.mssg[0]);
                                $(tblKits).dataTable().fnReloadAjax();
                            }
                        },
                        failure: function (r) {
                            alertify.error('Ha ocurrido un error');
                        }
                    });
                }
            });
        });
        $('body').delegate('.row-data-change', 'click', function (event) {
            event.preventDefault();
            var url = $(this).attr('data-url'),
                    id = $(this).attr('data-id'),
                    dataObject = $(this);
            smoke.confirm('¿Está seguro que desea cambiar el estado?', function (e) {
                if (e) {
                    $.ajax({
                        url: url,
                        data: {id: id},
                        type: "POST",
                        dataType: 'json',
                        success: function (r) {
                            alertify.success(r.mssg[0]);
                            var activo = (dataObject.attr('data-activo') == 0) ? 1 : 0;
                            var estado = _obtenerEstado(activo);
                            dataObject.html(estado);
                            dataObject.attr('data-activo', activo);
                            _cleanForm($("#mdl-form"));
                        },
                        failure: function (r) {
                            alertify.error(r.mssg[0]);
                        }
                    });
                }
            });
        });
        $('body').delegate('.row-data-edit', 'click', function (event) {
            event.preventDefault();
            var modal = $('#modalData-modal');
            $('#modalData-modal').find('.modal-title').text("Agregar");
            $("#mdl-form").attr('action', $(this).attr('data-url'));
            modal.find('.modal-title').text('Editar');
            fillForm('getKitAtencion', $("#mdl-form"), $(this).attr('data-id'));
            modal.modal('show');
        });
        $("body").delegate('.row-data-productos', 'click', function (e) {
            e.preventDefault();
            $("#mdl-kit").find('#kitid').val($(this).attr('data-id'));
            $("#mdl-kit").modal('show');
        });
        var tblProductos = document.getElementById('gp-tblProductos');
        $('#mdl-kit').on('show.bs.modal', function () {
            var kitid = $(this).find("#kitid").val();
            if ($.fn.DataTable.fnIsDataTable(tblProductos)) {
                $(tblProductos).dataTable().fnDestroy();
            }
            $(tblProductos).dataTable({
                "sAjaxSource": kitid + '/productosJSON',
                "bServerSide": true,
                "bProcessing": true,
                "sDom": "<'row'<'col-sm-6'><'col-sm-6'f>r>t<'row'<'col-sm-5'><'col-sm-7'p>>",
                "sPaginationType": "bootstrap_alt",
                "bAutoWidth": false,
                "iDisplayLength": 9,
                "aoColumns": [
                    {mData: "producto.idProducto"},
                    {mData: "producto.descripcion"},
                    {mData: "cantidad"},
                    {mData: "producto.idProducto", "bSortable": false, "mRender": function (data, type, row) {
                            var rm = '<span class="rmprod separator-icon-td" data-id=' + row.kitAtencion.id + '-' + data + '><i class="splashy-remove" style="cursor: pointer;"></i></span>';
                            var edit = '<span class="editprod separator-icon-td" data-id=' + row.kitAtencion.id + '-' + data + '><i class="splashy-pencil" style="cursor: pointer;"></i></span>';
                            return edit + rm;
                        }
                    }
                ]
            });
        });
        $("#btnAgregarProducto").click(function (e) {
            e.preventDefault();
            var idProducto = $("#mdl-kit").find('#producto').val();
            var kitid = $("#mdl-kit").find("#kitid").val();
            var cant = $("#mdl-kit").find("#cantidad").val();
            if (cant <= 0) {
                alertify.error("La cantidad debe ser un n&uacute;mero mayor que cero");
                return;
            }
            $.ajax({
                url: kitid + '/agregarProducto',
                type: 'POST',
                data: {'idProducto': idProducto, 'cantidad': cant},
                success: function (response) {
                    if (!response.hasError) {
                        $(tblProductos).dataTable().fnReloadAjax();
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.success(response.mssg[i]);
                        }
                    }
                    else {
                        for (var i in response.mssg) {
                            if (reponse.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function (response) {

                }
            });
        });
        $("body").delegate('.editprod', 'click', function (e) {
            e.preventDefault();
            var llaves = $(this).attr('data-id');
            $.ajax({
                url: 'getKitAtencionProducto',
                type: 'POST',
                data: {id: llaves},
                success: function (response) {
                    var cant = response.data.cantidad;
                    var producto = response.data.producto;
                    $("#mdl-kit").find('#producto').val(producto);
                    $("#mdl-kit").find("#cantidad").val(cant);

                    $("#producto").removeClass('chzn-done');
                    $("#producto_chzn").remove();
                    $('#producto').chosen({no_results_text: "No se encontraron coincidencias con"});
                    $("#producto_chzn").css({width: '100%'});
                    $("#producto_chzn .chzn-drop").css({width: '98%'});
                    $("#producto_chzn .chzn-drop .chzn-search input").css({width: '98%'});
                },
                failure: function (response) {

                }
            });
        });
        $("body").delegate('.rmprod', 'click', function (e) {
            e.preventDefault();
            var llaves = $(this).attr('data-id');
            smoke.confirm("¿Est&aacute; seguro que desea eliminar este producto?", function (e) {
                if (e) {
                    $.ajax({
                        url: 'eliminar',
                        type: 'POST',
                        data: {id: llaves},
                        success: function (response) {
                            if (!response.hasError) {
                                for (var i in response.mssg) {
                                    if (response.mssg.hasOwnProperty(i))
                                        alertify.success(response.mssg[i]);
                                }
                                $(tblProductos).dataTable().fnReloadAjax();
                            } else {
                                for (var i in response.mssg) {
                                    if (response.mssg.hasOwnProperty(i))
                                        alertify.error(response.mssg[i]);
                                }
                            }
                        },
                        failure: function (response) {

                        }
                    });
                }
            });
        });
        $("#componente").change(function (e) {
            e.preventDefault();
            _subComponenteSelectFill("#subcomponente", $(this).val());
        });
        $("#mcomponente").change(function (e) {
            e.preventDefault();
            _subComponenteSelectFill("#msubcomponente", $(this).val());
        });
        function _subComponenteSelectFill(selector, idComponente, callback) {
            var select = $(selector);
            select.children().remove();
            if (idComponente && idComponente > 0) {
                var options = {
                    url: '/' + idModulo + '/subcomponente/getSubComponentes',
                    success: function (r, status, ajaxData) {
                        select.append(new Option('-- Seleccione un Subcomponente --', 0));
                        for (var key in r) {
                            if (r.hasOwnProperty(key)) {
                                td = r[key];
                                select.append(new Option(td.descripcion, td.id));
                            }
                        }
                        if (callback)
                            callback();
                        if (editKit) {
                            $("#msubcomponente").val(editKit.subComponente.id);
                        }
                        select.triggerHandler("subcomponentelisto");
                        $(selector).removeClass('chzn-done');
                        $(selector + "_chzn").remove();
                        $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
                        $(selector + "_chzn").css({width: '100%'});
                        $(selector + "_chzn .chzn-drop").css({width: '98%'});
                        $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
                    },
                    data: {'componente': idComponente}
                };
                _getList(options);
            } else {
                select.append(new Option('-- Seleccione un Subcomponente --', 0));
            }
        }
        function _remakeChz(selector) {
            $(selector).removeClass('chzn-done');
            $(selector + "_chzn").remove();
            $(selector).chosen({no_results_text: "No se encontraron coincidencias con"});
            $(selector + "_chzn").css({width: '100%'});
            $(selector + "_chzn .chzn-drop").css({width: '98%'});
            $(selector + "_chzn .chzn-drop .chzn-search input").css({width: '98%'});
        }
        $("#componente").change();
        $("#btnBuscar").click(function (e) {
            e.preventDefault();
            $(tblKits).dataTable().fnReloadAjax();
        });
    });
</script>