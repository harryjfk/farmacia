<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty sessionScope.usuarioIniciado}">
    <c:redirect url="/index" />
</c:if>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>${title}</title>
        
        <tiles:insertAttribute name="meta"></tiles:insertAttribute>
        <tiles:insertAttribute name="head"></tiles:insertAttribute>
        <tiles:insertAttribute name="headScript" ></tiles:insertAttribute>        
        </head>
        <body class="full_width">
            <div id="maincontainer" class="clearfix">
                    
            <tiles:insertAttribute name="menu"></tiles:insertAttribute>

                <div id="contentwrapper">
                    <div class="main_content">                        
                    <tiles:insertAttribute name="breadcrumb"></tiles:insertAttribute>
                    <h3 class="heading">${title}</h3>
                    <tiles:insertAttribute name="body"></tiles:insertAttribute>
                    </div>
                </div>

            <tiles:insertAttribute name="sidebar" ></tiles:insertAttribute>            

        </div>
        <script>
            elementForm();
        </script>
    </body>
</html>