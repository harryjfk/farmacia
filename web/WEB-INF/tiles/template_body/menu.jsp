<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand pull-left" href="#"><img src="<c:url value="/img/logo_minsa_small.jpg" />" alt="logo_minsa" height="36"/></a>
                <ul id="ulMenu" class="nav navbar-nav">
                    <c:if test="${idSubmoduloView != -1}">
                        <c:forEach var="menu" items="${requestScope.menusTemplate}">
                            <c:if test="${idSubmoduloView == menu.idSubmodulo}">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle nav_condensed" data-toggle="dropdown">${menu.nombreMenu} <b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <c:forEach var="submenu" items="${requestScope.submenusTemplate}">
                                            <c:if test="${submenu.idMenu == menu.idMenu}">
                                                <li><a href="<c:url value="${submenu.enlace}"/>">${submenu.nombreSubmenu}</a></li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav user_menu pull-right">
                    <li class="divider-vertical hidden-sm hidden-xs"></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="<c:url value="/img/user_avatar.png" />" alt="" class="user_avatar">${sessionScope.usuarioIniciado.nombreUsuario} <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="<c:url value="/blank/profile"/>">Mi Perfil</a></li>
                            <li class="divider"></li>
                            <li><a href="<c:url value="/index"/>">Cerrar Sesión</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>