<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@include file="../includes.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <style media="print">
            html {width: 500px}
            table#detalle{width: 450px;margin: 0 auto;}
            #bottom-tbl tbody tr .tlt{text-align: left}
            hr{border-style: dotted}
        </style>
        <style media="screen">
            html {width: 500px}
            table#detalle{width: 450px;margin: 0 auto;}
            hr{border-style: dotted}
        </style>
    </head>
    <body>
        <tiles:insertAttribute name="content" ></tiles:insertAttribute>
    </body>
</html>
