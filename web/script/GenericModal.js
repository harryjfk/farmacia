
var GenericModal = function() {
    var me = this;


    me._modal = function() {

        var modal = '<div class="modal fade" id="'+me._id+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
                '   <div class="modal-dialog">' +
                '   <div class="modal-content">'+
                me._head()+
                me._body()+
                me._footer()+
                '</div>'+
                '</div>'+
                '</div>';
           $('body').append(modal);
    };

    me._head = function() {
        return '<div class="modal-header">' +
                '   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
                '   <h4 class="modal-title">' + me._title + '</h4>' +
                '</div>';
    };

    me._body = function() {
        return '<div class="modal-body">' + me._content + '</div>';
    };

    me._footer = function() {
        return '<div class="modal-footer">' +
                '<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
                '<button type="button" class="btn btn-primary">Save changes</button>' +
                '</div>';
    };


    me.init = function() {
        me._id = Math.round(Math.random() * 100) + new Date().getTime() + 'genericModal';
        me._title = 'Modal title';
        me._content = '...';
    };
    
    me.appendModal = function(){
        me._modal();
    };
    
    me.show = function(){
        var id = '#'+me._id;
        $(id).modal('show');
    };
    
    me.hide = function(){
        var id = '#'+me._id;
        $(id).modal('hide');
    };
    
    me.get = function(field) {

        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field) && typeof (me[_field]) !== 'function')
                return me[_field];
        }
        return null;
    };

    me.set = function(field, value) {
        if (field) {
            var _field = '_' + field;

            if (me.hasOwnProperty(_field)) {
                me[_field] = value;
                return me[_field];
            }
        }
        return null;
    };

    me.add = function(field, value) {
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

