//paciente
var PacienteGeneral = function () {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Pacientes');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th>Cod.Paciente</th>' +
                '<th>H.C</th>' +
                '<th>Nombres</th>' +
                '<th>A.Paterno</th>' +
                '<th>A.Materno</th>' +
                '<th>Sexo</th>' +
                '<th>F.Nac</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var url = '/' + window.location.pathname.split('/')[1] + '/GenericPaciente/listar';
        var options = {"aoColumns": [
                {mData: "paciente", sClass: "paciente"},
                {mData: "historia", sClass: "historia"},
                {mData: "nombre", sClass: "nombre"},
                {mData: "paterno", sClass: "paterno"},
                {mData: "materno", sClass: "materno"},
                {mData: "sexo", sClass: "sexo", mRender: function (data, type, row) {
                        return fieldFormat.sex(data);
                    }},
                {mData: "fechaNacimiento", mRender: function (data, type, row) {
                        return fieldFormat.date(data);
                    }}
            ],
            sAjaxSource: url,
            bServerSide: true,
            bProcessing: true,
            bAutoWidth: true,
            iDisplayLength: 9
        };

        var id = '#' + me._tableID,
                table = new slDataTable(id, options);
        table.set('dataUrl', url);
        table.set('columns', ['paciente', 'historia', 'nombre', 'paterno', 'materno', 'sexo', 'fechaNacimiento:date']);
        table.set('success', function (r) {
            return r.data;
        });


        return table;
    };

    me.init = function () {
        me._tableID = 'tblPacientesGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};

//prescriptor/medico
var MedicoGeneral = function (idModulo) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de M&eacute;dicos');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '<div style="margin: 20px !important" id="med-pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Personal</a></div>';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th></th>' +
                '<th>C&oacute;digo</th>' +
                '<th>Nombre</th>' +
                '<th>A.Paterno</th>' +
                '<th>A.Materno</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/prescriptor/getPrescriptores');
        table.set('columns', ["idMedico", "codigo", "nombre", "paterno", "materno"]);
        table.setColumnsVisibility([0]);
        table.set('success', function (r) {
            return r;
        });
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblMedicosGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//vendedor
var VendedorGeneral = function (idModulo, noListOnInit) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Vendedores');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '<div style="margin: 20px !important" id="ven-pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Personal</a></div>';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th></th>' +
                '<th>C&oacute;digo</th>' +
                '<th>Nombre</th>' +
                '<th>A.Paterno</th>' +
                '<th>A.Materno</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/vendedor/getVendedores');
        table.set('columns', ["idVendedor", "codigo", "nombre", "paterno", "materno"]);
        table.setColumnsVisibility([0]);
        table.set('success', function (r) {
            return r;
        });
        if (!noListOnInit)
            table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblVendedoresGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//cliente
var ClienteGeneral = function (idModulo, getpersonal) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Clientes');
        modal.set('content', me._createContent());
        if (getpersonal) {
            modal.set('footer', function () {
                return '<div style="margin: 20px !important" id="pull-from-personal"><a style="background: none !important;" href="javascript:void(0);">Seleccionar Paciente</a></div>';
            });
        }
        else {
            modal.set('footer', function () {
                return '';
            });
        }

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th></th>' +
                '<th>C&oacute;digo</th>' +
                '<th>Nombre</th>' +
                '<th>A.Paterno</th>' +
                '<th>A.Materno</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/cliente/getClientes');
        table.set('columns', ["idCliente", "codigo", "nombre", "apellidoPaterno", "apellidoMaterno"]);
        table.setColumnsVisibility([0]);
        table.set('success', function (r) {
            return r;
        });
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblClientesGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//kits de atencion
var KitsGeneral = function (idModulo, params) {

    var me = this;
    me._url = '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/kitatencion/listarPorProcesoPeriodo';
    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Kits de Atenci&oacute;n');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th></th>' +
                '<th>Descripci&oacute;n</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        me._params = params;
        table.set('dataUrl', me._url);
        table.set('columns', ["id", "descripcion"]);
        table.setColumnsVisibility([0]);
        table.set('success', function (r) {
            return r.data || r;
        });
        return table;
    };

    me.init = function () {
        me._tableID = 'tblKitsGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
        $('#' + me._modal.get('id')).on('show.bs.modal', function () {
            me._table.set('parameters', me._params);
            me._table.getData();
        });
        $('#' + me._modal.get('id')).on('hidden.bs.modal', function () {
            me._table._table.fnClearTable();
        });
    };

    me.show = function () {
        me._modal.show();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//diagnostico CIE
var DiagnosticoGeneral = function () {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Diagn&oacute;stico CIE');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th>C&oacute;digo</th>' +
                '<th>Descripcio&oacute;n</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/GenericDiagnostico/listar');
        table.set('columns', ["codigo", "descripcion"]);
        table.set('success', function (r) {
            return r.data;
        });
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblDiagnosticosGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//personal
var PersonalGeneral = function () {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Personal');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };

    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th>C&oacute;digo</th>' +
                '<th>Nombre</th>' +
                '<th>Apellido Paterno</th>' +
                '<th>Apellido Materno</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var url = '/' + window.location.pathname.split('/')[1] + '/personal/listarPersonal';
        var options = {"aoColumns": [
                {mData: "personal", sClass: "personal"},
                {mData: "nombres", sClass: "nombres"},
                {mData: "apellidoPaterno", sClass: "apellidoPaterno"},
                {mData: "apellidoMaterno", sClass: "apellidoMaterno"}
            ],
            sAjaxSource: url,
            bServerSide: true,
            bProcessing: true,
            bAutoWidth: true,
            iDisplayLength: 9
        };
        var id = '#' + me._tableID,
                table = new slDataTable(id, options);
        table.set('dataUrl', url);
        return table;
    };

    me.init = function () {
        me._tableID = 'tblPersonalGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//punto de venta
var PtoVentaGeneral = function (idModulo) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Puntos de Venta');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th>C&oacute;digo</th>' +
                '<th>Nombre de PC</th>' +
                '<th>Serie Boleta</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/ptoventa/getPVentas');
        table.set('columns', ["id", "nombrePc", "serieBoleta"]);
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblPtoVentaGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
//servicio
var ServicioGeneral = function (idModulo) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Servicios');
        modal.set('content', me._createContent());
        modal.set('footer', function () {
            return '';
        });

        modal.appendModal();
        return modal;
    };


    me._createContent = function () {
        return '<table id="' + me._tableID + '" class="table table-bordered table-striped dTableR select-table">' +
                '<thead>' +
                '<tr>' +
                '<th></th>' +
                '<th>Servicio</th>' +
                '<th>Descripci&oacute;n</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/' + idModulo + '/servicio/getServicios?justactive=1');
        table.set('columns', ["id", "nombre", "descripcion"]);
        table.setColumnsVisibility([0]);
        table.set('success', function (r) {
            return r;
        });
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblServiciosGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
        me._table.addRowClickEvent();
    };

    me.get = function (field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function (field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function' && Array.isArray(me[_field])) {

                if (typeof (value) === 'object') {
                    for (var key in value) {
                        if (value.hasOwnProperty(key)) {
                            me[_field].push(value[key]);
                        }
                    }
                }
                else {
                    me[_field].push(value);
                }

                return me[_field];
            }
        }
        return null;
    };

    me.init();

};
