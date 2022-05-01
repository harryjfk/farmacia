<%@include file="../includes.jsp" %>
<div class="row">
    <div class="col-sm-12 col-md-12">
        <table id="tblData-table" class="table table-bordered table-striped dTableR">
            <thead>
                <tr class="filter-row">
                    <c:forEach var="columnTitle" items="${tableHeaders}">
                        <c:if test="${columnTitle != 'Acciones'}">
                            <th>
                                <input type="text" id="id${columnTitle}" class="form-control table-filter" placeholder="Filtrar ${columnTitle}" />
                            </th>

                        </c:if>
                        <c:if test="${columnTitle == 'Acciones'}">
                            <th>
                            </th>
                        </c:if>

                    </c:forEach>
                </tr>
                <tr>
                    <c:forEach var="columnTitle" items="${tableHeaders}">
                        <th>${columnTitle}</th>
                        </c:forEach>
                </tr>
            </thead>
        </table>
    </div>             
</div>
<script>

    $(document).ready(function () {
        _storeUrl('${ajaxList}');
        window.gp.data = {};
        var modal = $('#modalData-modal'),
                table = $('#tblData-table'),
                modalForm = modal.find('form');
        window.gp.table = table;

        getTableData();
        window.gp.getData = getTableData;
        window.gp.reportCondition = null;
        window.gp.addRow = addRow;
        window.gp.cleanForm = _cleanForm;
        window.gp.cleanTable = function () {
            window.gp.table.fnClearTable();
        };
        window.gp.obtenerEstado = _obtenerEstado;


        function getTableData() {

            var url = window.gp.ajaxList;
            var t = document.getElementById("tblData-table");
            if (!$.fn.DataTable.fnIsDataTable(t)) {
                window.gp.table.dataTable({bAutoWidth: false});
            }
            window.gp.table.fnClearTable();
            if (url) {
                $.getJSON(url, window.gp.data, function (r) {
                    window.gp.addRow(r);
                    $('#tblData-table').triggerHandler('Cargada');
                });
            }
            window.gp.table.fnAdjustColumnSizing();
            $('#tblData-table').triggerHandler('Cargada');
        }
        function fillForm(url, form, id) {
            if (url !== '') {
                $.getJSON(url, {id: id}, function (r) {
                    var input = null;
                    if (r.data)
                    {
                        r = r.data;
                    }
                    for (var key in r) {
                        if (r.hasOwnProperty(key)) {
                            input = form.find('#' + key);
                            if (input) {
                                input.val(r[key]);
                            }
                        }
                    }
                    modal.triggerHandler('Editar', [id, r]);
                });

            }
            else {
                modal.triggerHandler('Editar', [id]);
            }
        }

        function addRow(r) {
            var properties = '${tableProperties}'.split(','),
                    mainId = properties.splice(0, 1)[0],
                    row, data, edit, change, remove;

            for (var key in r) {
                if (r.hasOwnProperty(key)) {
                    data = r[key];
                    var id = data[mainId];
                    if (mainId.indexOf('.') > 0) {
                        var path = mainId.split('.');
                        var tmp = data[path[0]];
                        for (var i = 1; i < path.length; i++) {
                            id = tmp[path[i]];
                            tmp = tmp[path[i]] || '';
                        }
                    }

                    edit = '<a  class="row-data-edit separator-icon-td" href="#" data-id="' + id + '" data-url="${editUrl}"><i class="splashy-pencil" title="Editar"></i></a>';
                    change = '<a  href="#" class="row-data-change separator-icon-td" data-activo="' + data.activo + '" data-id="' + id + '" data-url="${changeUrl}">' + _obtenerEstado(data.activo) + '</a>';
                    remove = '<a href="#" class="row-data-remove separator-icon-td" data-id="' + id + '" data-url="${removeUrl}"><i class="splashy-remove" title="Eliminar"></i></a>';
                    row = [];
                    for (var property in properties) {
                        if (properties.hasOwnProperty(property)) {

                            var index = properties[property].split(':'),
                                    value = data[index[0]] || '';

                            if (index[0].indexOf('.') > 0) {
                                var path = index[0].split('.');
                                if (data[path[0].trim()]) {
                                    var tmp = data[path[0].trim()];
                                    for (var i = 1; i < path.length; i++) {
                                        value = tmp[path[i].trim()];
                                        tmp = tmp[path[i]] || '';
                                    }
                                } else {
                                    value = '';
                                }
                            }

                            row.push('<span class=' + properties[property] + '>' + fieldFormat[index[1]](value) + '</span>');
                        }
                    }
                    row.push(edit + change + remove);
                    window.gp.table.fnAddData(row);
                }
            }
        }
        $('body').bind({
            addRow: function (evt, data) {
                window.gp.addRow(data);
                $('#tblData-table').triggerHandler('Cargada');
            }
        });
        $('body').delegate('.row-data-edit', 'click', function (event) {
            event.preventDefault();
            modalForm.attr('action', $(this).attr('data-url'));
            modal.find('.modal-title').text('Editar');
            fillForm('${findItem}', modalForm, $(this).attr('data-id'));
            modal.modal('show');
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
                            _cleanForm(modalForm);
                        },
                        failure: function (r) {
                            alertify.error(r.mssg[0]);
                        }
                    });
                }
            });


        });

        $('body').delegate('#btnAgregar', 'click', function (event) {
            event.preventDefault();
            modalForm.attr('action', 'insertar');
            modal.find('.modal-title').text('Agregar');
            _cleanForm(modalForm);
            modalForm.find('.IDFIELD').val('0');
            var validation = modalForm.validate();
            validation.resetForm();
            modalForm.find('.has-error').removeClass('has-error');
            modal.modal('show');
            modal.triggerHandler('Añadir');
        });

        $('body').delegate('#btnGuardar', 'click', function (event) {
            event.preventDefault();
            var dataToSend = modalForm.serialize(),
                    url = modalForm.attr('action');
            var validation = modalForm.validate();
            if (!validation.checkForm()) {
                validation.showErrors();
                modalForm.find('.error').parent().addClass('has-error');
                modalForm.find('label.error').addClass('control-label');
                return;
            }
            if (window.gp.dataToSend) {
                dataToSend = window.gp.dataToSend;
            }
            modalForm.find('.valid').parent('.has-error').removeClass('has-error');
            modalForm.find('.has-error').removeClass('has-error');
            modal.modal('hide');

            $.ajax({
                url: url,
                data: dataToSend,
                type: "POST",
                dataType: 'json',
                success: function (r) {
                    window.gp.dataToSend = null;
                    _cleanForm(modalForm);
                    window.gp.table.fnClearTable();
                    if (r.data) {
                        window.gp.addRow(r.data);
                    }
                    else {
                        window.gp.table.fnClearTable();
                        window.gp.getData();
                    }
                    var type = 'success';
                    if (r.hasError) {
                        type = 'error';
                    }

                    for (var i in r.mssg) {
                        if (r.mssg.hasOwnProperty(i)) {
                            alertify[type](r.mssg[i]);
                        }
                    }
                },
                failure: function (r) {
                    alertify.error(r.mssg[0]);
                    window.gp.dataToSend = null;
                }
            });
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
                                window.gp.table.fnClearTable();
                                window.gp.getData();
                            }
                        },
                        failure: function (r) {
                            alertify.error('Ha ocurrido un error');
                        }
                    });
                }
            });
        });

        $('body').delegate('#btnPDF', 'click', function (event) {
            event.preventDefault();
            if(window.gp.reportCondition){
                if(!window.gp.reportCondition()) return;
            }
            _setPathName("/reportePdf");
        });
        $('body').delegate('#btnExcel', 'click', function (event) {
            event.preventDefault();
             if(window.gp.reportCondition){
                if(!window.gp.reportCondition()) return;
            }
            _setPathName("/reporteExcel");
        });
        $('body').delegate('#btnConsultaPDF', 'click', function (event) {
            event.preventDefault();
             if(window.gp.reportCondition){
                if(!window.gp.reportCondition()) return;
            }
            _setPathName("/reporteConsultaPdf");
        });
        $('body').delegate('#btnConsultaExcel', 'click', function (event) {
            event.preventDefault();
             if(window.gp.reportCondition){
                if(!window.gp.reportCondition()) return;
            }
            _setPathName("/reporteConsultaExcel");
        });

        $("#tblData-table thead th .table-filter").on('keyup change', function () {

            window.gp.table.fnFilter($(this).val(), $(this).parent(':visible').index());


        });

        function _obtenerEstado(estado) {
            return (estado) ? '<i class="splashy-thumb_up" title="Cambiar estado a Desactivado"></i>' : '<i class="splashy-thumb_down" title=" Cambiar estado a Activado"></i>';
        }
        function _cleanForm(selector) {
            $(selector).find('input[type="text"],textarea,input[type="hidden"]').val('');
            $(selector).find('input').val('');
            $(selector).find('input[type="checkbox"]').prop('checked', false);
            $('#divMessage,.divMessage').html('');
            $(selector).triggerHandler('cleanForm');
        }
        ;


        function _setPathName(path) {
            var myLocation = window.location.href.split('/'),
                    result = "";
            myLocation.pop();

            for (var key in myLocation) {
                if (myLocation.hasOwnProperty(key)) {
                    result += myLocation[key] + "/";
                }
            }
            if (window.gp.reportData) {
                var data = window.gp.reportData;
                path += '?';
                for (var i in data) {
                    if (data.hasOwnProperty(i)) {
                        path += i + '=' + data[i] + '&';
                    }
                }
            }
            result += path;
            window.location.href = result;
        }

        function _storeUrl(url) {
            if (window.gp) {
                window.gp.ajaxList = url;
            }
            else {
                window.gp = {};
                _storeUrl(url);
            }
        }

    });</script>
