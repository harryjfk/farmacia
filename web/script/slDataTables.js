$(document).ready(function () {

    slDataTable = function (selector, options) {
        var me = this;

        me._selector = selector;
        me._options = options;
        me._dataUrl = '';
        me._actions = [];
        me._columns = [];
        me._parameters = {};
        me._success = function (r) {
            return r;
        };
        me._failure = function (r) {
            return r;
        };
        me._init = function () {

            me._table = $(me._selector).dataTable(me._options || {});
        };

        me._click = function (row) {
            return row;
        };

        me._getValue = function (object, key) {
            key = key.split(':');
            if (key[0].indexOf('.') > 0) {
                var list = key[0].split('.'),
                        result = object;
                for (var i in list) {
                    if (list.hasOwnProperty(i)) {
                        result = result[list[i]];
                    }
                }

                return fieldFormat[key[1]](result);

            }
            return fieldFormat[key[1]](object[key[0]]);
        };

        me._addRow = function (r) {
            var row, myObject, actions, action, simpleKey;
            for (var index in r) {
                if (r.hasOwnProperty(index)) {
                    myObject = r[index];
                    row = [];
                    for (var key in me._columns) {
                        if (me._columns.hasOwnProperty(key)) {
                            var columnValue = me._getValue(myObject, me._columns[key]);
                            simpleKey = me._columns[key].split(':')[0];
                            row.push('<span class=' + simpleKey + '>' + columnValue + '</span>');
                        }
                    }
                    if (me._actions.length && me._actions.length > 0) {
                        actions = '';
                        for (var item in me._actions) {
                            if (me._actions.hasOwnProperty(item)) {
                                action = me._actions[item];
                                actions += new slAction(myObject[action.id], action.url, action.classes, action.icon).get('body');
                            }
                        }
                        row.push(actions);
                    }
                    me._table.fnAddData(row);
                }
            }

        };

        me.setColumnsVisibility = function (columns, vis) {
            if (!Array.isArray(columns)) {
                columns = [columns];
            }
            for (var i in columns) {
                if (columns.hasOwnProperty(i)) {
                    me._table.fnSetColumnVis(columns[i], vis);
                }
            }
        };

        me.getData = function () {
            $.ajax({
                url: me._dataUrl || '',
                data: me._parameters || {},
                type: "GET",
                dataType: 'json',
                success: function (r) {
                    var respond = me._success(r);
                    me._addRow(respond);
                },
                failure: function (r) {
                    me._failure(r);
                }
            });
        };

        me.addRowClickEvent = function () {
            var selector = me._selector + ' tbody tr';
            $('body').delegate(selector, 'click', function (e) {
                e.preventDefault();
                var row = me._table.fnGetData(this), key, value, obj = {};
                for (var i in row) {
                    if (row.hasOwnProperty(i)) {
                        if(Number(i) || i=='0'){
                            key = $(row[i]).attr('class');
                            key = key.trim();
                            value = $(row[i]).html();
                            value =  value.trim();
                        }
                        else{
                            key =i;
                            value = row[i];
                        }                      
                        obj[key] = value;
                    }
                }
                me._click(obj);
            });
        };
        me.removeClickEvent = function () {
            var selector = me._selector + ' tbody tr';
            $('body').undelegate(selector, 'click');
        };

        me.clear = function () {
            me._table.fnClearTable();
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

        me._init();
    };

});
