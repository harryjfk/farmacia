$(document).ready(function(){
    
    slAction = function(mainId,url,myClass,icon){
        var me =this;
         
        me._mainId = mainId;
        me._class = myClass;
        me._url = url;
        me._icon = icon;
        
        me._body = '<a  class="'+me._class+'" href="javascript:void(0)" data-id="' + me._mainId + '" data-url="'+me._url+'">'+me._icon+'</i></a>';
      
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

    };
    
});
