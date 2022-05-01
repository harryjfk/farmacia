<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <p>Han ocurrido los siguientes errores:</p>
        <ul>
            <c:forEach var="error" items="${reponseError.mensajesRepuesta}">
                <li>${error}</li>
                </c:forEach>
        </ul>
        <a href="${requestScope['javax.servlet.forward.request_uri']}">Para continuar haga click aquí</a>
    </body>
</html>
