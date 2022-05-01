package pe.gob.minsa.farmacia.util;

import javax.servlet.http.HttpServletRequest;

public class DataTablesParamUtility {

    public static DataTablesParam getParam(HttpServletRequest request) {
        if (request.getParameter("sEcho") != null && request.getParameter("sEcho") != "") {
            DataTablesParam param = new DataTablesParam();
            param.sEcho = request.getParameter("sEcho");
            param.sSearch = request.getParameter("sSearch");
            param.sColumns = request.getParameter("sColumns");
            param.iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
            param.iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
            
            //Para exportacion colocamos como exportacion 1,000,000 registros es decir -1 asi las funciones
            //previamente realizadas exportaran todo de lo contrario no, si hay más de un millon de registros
            //aumentar el campo.
            if(param.iDisplayLength == -1){
                param.iDisplayLength = 1000000;
            }
            
            param.iColumns = Integer.parseInt(request.getParameter("iColumns"));

            if (request.getParameter("iSortingCols") != null) {
                param.iSortingCols = Integer.parseInt(request.getParameter("iSortingCols"));
            }

            if (request.getParameter("iSortCol_0") != null) {
                param.iSortColumnIndex = Integer.parseInt(request.getParameter("iSortCol_0"));
            }

            if (request.getParameter("sSortDir_0") != null) {
                param.sSortDirection = request.getParameter("sSortDir_0");
            }else{
                param.sSortDirection = "asc";
            }

            return param;
        } else {
            return null;
        }
    }
}
