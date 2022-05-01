<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/js/jquery-migrate.min.js" />"></script>
<script src="<c:url value="/lib/jquery-ui/jquery-ui-1.10.0.custom.min.js" />"></script>
<!-- touch events for jquery ui-->
<script src="<c:url value="/js/forms/jquery.ui.touch-punch.min.js" />"></script>
<!-- input mask -->
<script src="<c:url value="/js/forms/jquery.inputmask.min.js" />"></script>
<!-- easing plugin -->
<script src="<c:url value="/js/jquery.easing.1.3.min.js" />"></script>
<!-- smart resize event -->
<script src="<c:url value="/js/jquery.debouncedresize.min.js" />"></script>
<!-- js cookie plugin -->
<script src="<c:url value="/js/jquery_cookie_min.js" />"></script>
<!-- main bootstrap js -->
<script src="<c:url value="/bootstrap/js/bootstrap.min.js" />"></script>
<!-- bootstrap plugins -->
<script src="<c:url value="/js/bootstrap.plugins.min.js" />"></script>
<!-- typeahead -->
<script src="<c:url value="/lib/typeahead/typeahead.min.js" />"></script>
<!-- code prettifier -->
<script src="<c:url value="/lib/google-code-prettify/prettify.min.js" />"></script>
<!-- sticky messages -->
<script src="<c:url value="/lib/sticky/sticky.min.js" />"></script>
<!-- tooltips -->
<script src="<c:url value="/lib/qtip2/jquery.qtip.min.js" />"></script>
<!-- lightbox -->
<script src="<c:url value="/lib/colorbox/jquery.colorbox.min.js" />"></script>
<!-- jBreadcrumbs -->
<script src="<c:url value="/lib/jBreadcrumbs/js/jquery.jBreadCrumb.1.1.min.js" />"></script>
<!-- hidden elements width/height -->
<script src="<c:url value="/js/jquery.actual.min.js" />"></script>
<!-- custom scrollbar -->
<script src="<c:url value="/lib/slimScroll/jquery.slimscroll.js" />"></script>
<!-- fix for ios orientation change -->
<script src="<c:url value="/js/ios-orientationchange-fix.js" />"></script>
<!-- to top -->
<script src="<c:url value="/lib/UItoTop/jquery.ui.totop.min.js" />"></script>
<!-- mobile nav -->
<script src="<c:url value="/js/selectNav.js" />"></script>
<!-- datatable -->
<script src="<c:url value="/lib/datatables/jquery.dataTables.min.js" />"></script>
<script src="<c:url value="/lib/datatables/fnReloadAjax.js" />"></script>
<!-- datatables bootstrap integration -->
<script src="<c:url value="/lib/datatables/jquery.dataTables.bootstrap.min.js" />"></script>
<!-- smoke_js -->
<script src="<c:url value="/lib/smoke/smoke.js" />"></script>
<!-- enhanced select (chosen) -->
<script src="<c:url value="/lib/chosen/chosen.jquery.min.js" />"></script>
<!-- dateJS-->
<script src="<c:url value="/lib/dateJS/date.js" />" type="text/javascript"></script>
<!-- bootstrap-treeview -->
<%--No usar la versión minificada, fueron agregadas nuevas carácterísticas a la versión "desarrollador"--%>
<script src="<c:url value="/lib/bootstrap-treeview/bootstrap-treeview-edit.min.js" />" type="text/javascript"></script>
<!-- javascript Cliente-->
<script src="<c:url value="/script/jsClient.js" />"></script>
<!-- javascript Cliente-->
<script src="<c:url value="/script/valida.js" />"></script>
<!-- gebo functions -->
<script src="<c:url value="/script/gebo_init.js" />"></script>
<!-- multiselect -->
<script src="<c:url value="/lib/multi-select/js/jquery.multi-select.js" />"></script>
<script src="<c:url value="/lib/multi-select/js/jquery.quicksearch.js" />"></script>
<script src="<c:url value="/lib/alertify/alertify.min.js"/>"></script>
<script src="<c:url value="/lib/clockpicker/clockpicker.min.js"/>"></script>

<script src="<c:url value="/lib/numeric/jquery.numeric.min.js" />"></script>

<script src="<c:url value="/lib/validation/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/lib/select2/select2.min.js"/>"></script>
<script src="<c:url value="/lib/select2/select2_locale_es.js"/>"></script>
<script src="<c:url value="/script/slDataTables.js"/>"></script>
<script src="<c:url value="/script/slAction.js"/>"></script>
<script src="<c:url value="/script/dispensacion.js"/>"></script>
<script src="<c:url value="/script/GenericModal.js"/>"></script>
<script src="<c:url value="/script/PacienteGeneral.js"/>"></script>


<script>
    var dateFormatJS = 'dd/MM/yyyy';
    
    function sidebaronclick(element, e, idSubmodulo) {
        e.preventDefault();
        $('[data-sidebarlink]').css({'background-color' : ''});
        if($(element).parent().attr('class') != 'active'){
            $(element).css({'background-color' : '#c9c9c9'});
        }
        
        $.ajax({
            url: '<c:url value="/Submenu/barMenu" />?idSubmodulo=' + idSubmodulo,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            success: function(data, textStatus, jqXHR) {
                var menus = data.menus;
                var submenus = data.submenus;
                
                var ulMenuHMTL = '';

                for (var i = 0; i <= menus.length - 1; ++i) {
                    ulMenuHMTL += '<li class="dropdown">';
                    ulMenuHMTL += '<a href="#" class="dropdown-toggle nav_condensed" data-toggle="dropdown">' + menus[i].nombreMenu + ' <b class="caret"></b></a>';
                    ulMenuHMTL += '<ul class="dropdown-menu">';

                    for (var x = 0; x <= submenus.length - 1; ++x) {
                        if (submenus[x].idMenu === menus[i].idMenu) {
                            ulMenuHMTL += '<li><a href="<c:out value="${pageContext.servletContext.contextPath}" />' + submenus[x].enlace + '">' + submenus[x].nombreSubmenu + '</a></li>';
                        }
                    }

                    ulMenuHMTL += '</ul>';
                    ulMenuHMTL += '</li>';
                }
                $('#ulMenu').html(ulMenuHMTL);
            }
        });
    }
    
     $(document).ajaxError(function(event, jqxhr, request, settings){        
        if(jqxhr.status === 401){
           window.location = '<c:url value="/index"/>';
        }
        
        if(jqxhr.status === 403){
           window.location = '<c:url value="/index"/>';
        }
        
        if(jqxhr.status === 404){
            if(typeof jqxhr.responseJSON != "undefined"){
                errorResponse(jqxhr.responseJSON);
            }
        }
    });
</script>