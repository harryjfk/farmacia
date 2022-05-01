
var ProductoLoteGeneral = function (url) {

    var me = this;

    me._createModal = function () {
        var modal = new GenericModal();
        modal.set('title', 'Listado de Productos');
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
                '<th>idAlmacen</th>' +
                '<th>idProducto</th>' +
                '<th>idLote</th>' +
                '<th>Codigo</th>' +
                '<th>Descripcion</th>' +
                '<th>Tipo</th>' +
                '<th>F.F</th>' +
                '<th>Precio</th>' +
                '<th>Almacen</th>' +
                '<th>Lote</th>' +
                '</tr>' +
                '</thead>' +
                '</table>';
    };

    me._createTable = function () {
        var id = '#' + me._tableID,
                table = new slDataTable(id);
        var idModulo = location.pathname.split('/')[3];
        idModulo = Number(idModulo) || 0;
        if (url) {
            table.set('dataUrl', '/' + url);
        }
        else {
            table.set('dataUrl', '/' + window.location.pathname.split('/')[1] + '/GenericProductoLote/listar?idModulo=' + idModulo);
        }
        table.set('columns', ['idAlmacen', 'idLote', 'producto.idProducto', 'producto.idProducto', 'producto.singleDescripcion', 'producto.idTipoProducto.nombreTipoProducto',
            'producto.idFormaFarmaceutica.nombreFormaFarmaceutica', 'precio', 'almacen.descripcion', 'lote.descripcion']);
        table.set('success', function (r) {
            return r.data || r;
        });
        table.getData();
        return table;
    };

    me.init = function () {
        me._tableID = 'tblProductoLoteGeneral';
        me._modal = me._createModal();
        me._table = me._createTable();
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