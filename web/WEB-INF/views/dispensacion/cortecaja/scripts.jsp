<script>
    $(document).ready(function () {
        $("body").addClass("no-edit no-change no-remove");
        var table = $('#tblData-table').dataTable();
        len = table.fnSettings().aoColumns.length - 1;
        table.fnSetColumnVis(len);
        $("body").undelegate('#btnAgregar', 'click');
        $("#btnAgregar").text("Nuevo");

        crearCalendar("#ventafechaRegistro");
        $("#cortecajafrm").submit(function (e) {
            e.preventDefault();
        });
        var fecha = new Date();
        var fData = {
            params: {}
        };
        $("#btnAgregar").click(function (e) {
            e.preventDefault();
            document.getElementById('cortecajafrm').reset();
            fData = {
                params: {}
            };
            $("#tblData-table").dataTable().fnClearTable();
            $("#formaPagoResumen tbody tr.resdata").remove();
            $("#totalAnulados tr.adata").remove();
            $("#totalVentas").html('');
            $("#totalIGV").html('');
            $("#overall").html('');
        });
        var idModulo = location.pathname.split('/')[3];
        var vendedorModal = new VendedorGeneral(idModulo, true);
        function _selectVendedor(row) {
            if (row.idVendedor) {
                fData.params['vendedor:idVendedor'] = row.idVendedor;
                var nombre = row.nombre + ' ' + row.paterno + ' ' + row.materno;
                $("#vendedor").val(nombre);
            }
            vendedorModal._modal.hide();
        }
        vendedorModal._table.set('click', _selectVendedor);
        $("#seleccionaVen").click(function () {
            var turno = fData.params['turno:idTurno'];
            if (turno) {
                var idTable = vendedorModal.get("tableID");
                $("#" + idTable).dataTable().fnClearTable();
                vendedorModal.get("table").set("parameters", {"turno": turno});
                vendedorModal.get("table").getData();
                vendedorModal._modal.show();
            }
            else
                alertify.error("Debe seleccionar un turno.");
        });
        $('#vendedor').on('keyup', function () {
            _borrarProp('vendedor:idVendedor');
        });

        var ptoVentaModal = new PtoVentaGeneral(idModulo);
        function _selectPtoVenta(row) {
            if (row.id) {
                fData.params['puntoDeVenta:id'] = row.id;
                var desc = row.nombrePc;
                $("#puntoVenta").val(desc);
            }
            ptoVentaModal._modal.hide();
        }
        ptoVentaModal._table.set('click', _selectPtoVenta);
        $("#seleccionaPtoVen").click(function () {
            ptoVentaModal._modal.show();
        });
        $('#puntoDeVenta').on('keyup', function () {
            venta.puntoDeVenta = null;
        });

        $("#periodo").on('change keyup', function (e) {
            e.preventDefault();
            var val = $(this).val();
            if (val.length == 4) {
                if (parseInt(val) == NaN) {
                    alertify.error("El per&iacute;odo debe ser a&ntilde;o v&aacute;lido");
                    return;
                }
                fData.params['periodo'] = val;
            }
        });

        $("#ventafechaRegistro").on('change keyup', function (e) {
            e.preventDefault();
            if ($(this).val().length > 0)
                fData.params['ventafechaRegistro'] = $(this).val() + ':dd/MM/yyyy';
            else
                _borrarProp('ventafechaRegistro');
        });

        var options1 = {
            url: '/' + idModulo + '/turno/getTurnos',
            success: function (r, status, ajaxData) {
                var select = $("#turno"),
                        td;
                select.append(new Option("-- TURNOS --", ""));
                for (var key in r) {
                    if (r.hasOwnProperty(key)) {
                        td = r[key];
                        select.append(new Option(td.descripcion, td.idTurno));
                    }
                }
            }
        };
        _getList(options1);
        $("#turno").change(function (e) {
            e.preventDefault();
            if ($(this).val())
                fData.params['turno:idTurno'] = $(this).val();
            else {
                _borrarProp('turno:idTurno');
            }
        });

        $("#btnFiltrar").click(function (e) {
            e.preventDefault();
            var valid = _limpiarDatos();
            if (!valid) {
                return;
            }
            if (fData.params['periodo'] && fData.params['periodo'].length > 4) {
                alertify.error("El per&iacute;odo debe ser a&ntilde;o v&aacute;lido");
                return;
            }
            var data = JSON.stringify(fData);
            $.ajax({
                url: 'filtrar',
                type: 'POST',
                data: data,
                dataType: 'json',
                contentType: 'application/json',
                success: function (response) {
                    $("#tblData-table").dataTable().fnClearTable();
                    $("#formaPagoResumen tbody tr.resdata").remove();
                    $("#totalAnulados tr.adata").remove();
                    $("#totalVentas").html('');
                    $("#totalIGV").html('');
                    $("#overall").html('');
                    if (!response.hasError) {
                        _llenarResumenFp(response.data.resumenFormPago);
                        _llenarTotalAnulados(response.data);
                        $("#overall").text(fieldFormat.money2(response.data.overallTotal) + " (Total)");
                        $("#totalVentas").text(fieldFormat.money2(response.data.totalVenta) + " (Venta)");
                        $("#totalIGV").text(fieldFormat.money2(response.data.totalImpuestoIGV) + " (IGV)");
                        window.gp.addRow(response.data.tblVentas);
                        window.gp.reportData = fData.params;
                        _checkAnuladas();
                    } else {
                        for (var i in response.mssg) {
                            if (response.mssg.hasOwnProperty(i))
                                alertify.error(response.mssg[i]);
                        }
                    }
                },
                failure: function (response) {
                    $("#tblData-table").dataTable().fnClearTable();
                    $("#formaPagoResumen tbody tr.resdata").remove();
                    $("#totalAnulados tr.adata").remove();
                }
            });
        });
        function  _checkAnuladas() {
            $('span[class="venta.anulada:yesno"]').each(function (i, elem) {
                if($(elem).html().trim().toLowerCase() === 'si')
                    $(elem).parent().parent().css({'color': 'red'});
            });
        }
        function _llenarResumenFp(data) {
            $("#formaPagoResumen tbody tr.resdata").remove();
            for (var fp in data) {
                if (data.hasOwnProperty(fp)) {
                    var tmp = data[fp].split('-');
                    var row = $('<tr class="resdata"></tr>');
                    row.append('<td>' + fp + '</td>');
                    row.append('<td>' + tmp[0] + '</td>');
                    row.append('<td>' + tmp[1] + '</td>');
                    $("#formaPagoResumen tbody").append(row);
                }
            }
        }
        function _llenarTotalAnulados(data) {
            $("#totalAnulados tr.adata").remove();
            var row = $("<tr class='adata'></tr>");
            row.append("<td>" + data.cantidadAnulados + "</td>");
            row.append("<td>" + fieldFormat.money2(data.montoAnulados) + "</td>");
            $("#totalAnulados tbody").append(row);
        }
        function _borrarProp(prop) {
            var params = {};
            for (var p in fData.params) {
                if (fData.params.hasOwnProperty(p)) {
                    if (p != prop)
                        params[p] = fData.params[p];
                }
            }
            fData.params = params;
        }
        function _limpiarDatos() {
            var cant = 4; //esto debe aumentar, es la cantidad de campos del form de filtrar
            var flgTieneCampos = false;
            for (var prop in fData.params) {
                flgTieneCampos = true;
                if (fData.params.hasOwnProperty(prop)) {
                    if (!fData.params[prop]) {
                        _borrarProp(prop);
                        cant--;
                    }
                }
            }
            if (!flgTieneCampos || cant == 0) {
                alertify.error("Debe filtrar por almenos un campo del formulario.");
                return false;
            }
            return true;
        }
    });
</script>