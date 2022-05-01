<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="jCrumbs" class="breadCrumb module">
    <ul>
        <li>
            <i class="glyphicon glyphicon-home"></i>
        </li>
        <c:forEach var="breadCrumb" items="${requestScope.breadCrumbs}">
            <li>
                ${breadCrumb}
            </li>
        </c:forEach>
    </ul>
</div>