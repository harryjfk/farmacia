<%@include file="../../views/includeTagLib.jsp" %>
<a href="javascript:void(0)" class="sidebar_switch on_switch ttip_r" title="Hide Sidebar">Sidebar switch</a>

<div class="sidebar">
    <div class="sidebar_inner_scroll">
        <div class="sidebar_inner">            
            <div style="padding: 18px 15px 9px;"></div>
            <div id="side_accordion" class="panel-group">
                <c:forEach var="modulo" items="${requestScope.modulosTemplate}">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <a href="#${modulo.idModulo}" data-parent="#side_accordion" data-toggle="collapse" class="accordion-toggle">
                                ${modulo.nombreModulo}
                            </a>
                        </div>
                        <c:choose>
                            <c:when test="${requestScope.idModuloView == modulo.idModulo}">
                                <div class="accordion-body collapse in" id="${modulo.idModulo}">
                                </c:when>
                                <c:when test="${requestScope.idModuloView != modulo.idModulo}">
                                    <div class="accordion-body collapse" id="${modulo.idModulo}">
                                    </c:when>
                                </c:choose>
                                <div class="panel-body">
                                    <ul class="nav nav-pills nav-stacked">
                                        <c:forEach var="submodulo" items="${requestScope.submodulosTemplate}">
                                            <c:if test = "${modulo.idModulo == submodulo.idModulo}">
                                                <c:choose>
                                                    <c:when test="${requestScope.idSubmoduloView == submodulo.idSubmodulo}">
                                                        <li class="active">
                                                            <a href="#" onclick="sidebaronclick(this, event, ${submodulo.idSubmodulo})">${submodulo.nombreSubmodulo}</a>
                                                        </li>
                                                        </c:when>
                                                        <c:when test="${requestScope.idSubmoduloView != submodulo.idSubmodulo}">
                                                        <li>
                                                            <a href="#" data-sidebarlink="" onclick="sidebaronclick(this, event, ${submodulo.idSubmodulo})">${submodulo.nombreSubmodulo}</a>
                                                        </li>
                                                        </c:when>
                                                    </c:choose>                                                    
                                                </c:if>
                                            </c:forEach>                                    
                                    </ul>
                                </div>
                            </div>
                        </div>   
                    </c:forEach>
                </div>
                <div class="push"></div>
            </div>
        </div>
    </div>
</div>