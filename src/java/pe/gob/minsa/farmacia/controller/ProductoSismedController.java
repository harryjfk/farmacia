package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.ProductoSismed;
import pe.gob.minsa.farmacia.services.impl.ProductoSismedService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/ProductoSismed")
public class ProductoSismedController {
 
    @Autowired
    ProductoSismedService productoSismedService;

    JsonResponse jsonResponse;
    
     private ManagerDatatables getProductoSismedDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<ProductoSismed> productosSismed = productoSismedService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= productosSismed.size() - 1; ++i) {
            ProductoSismed c = productosSismed.get(i);
            if (c.getNombreProductoSismed().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getCodigoSismed().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                productosSismed.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(productosSismed, new Comparator<ProductoSismed>() {
            @Override
            public int compare(ProductoSismed o1, ProductoSismed o2) {
                switch (sortColumnIndex) {                    
                    case 0:
                        return o1.getCodigoSismed().toLowerCase().compareTo(o2.getCodigoSismed().toLowerCase()) * sortDirection;                        
                    case 1:
                        return o1.getNombreProductoSismed().toLowerCase().compareTo(o2.getNombreProductoSismed().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(productosSismed.size());

        if (productosSismed.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productosSismed = productosSismed.subList(dataTablesParam.iDisplayStart, productosSismed.size());
        } else {
            productosSismed = productosSismed.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(productosSismed);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarProductoSismed() {
        return new ModelAndView("ProductoSismed");
    }

    @RequestMapping(value = "/productosSismedJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductoSismedsJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductoSismedDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarProductoSismed(@ModelAttribute ProductoSismed productoSismed) {

        try {
            productoSismedService.insertar(productoSismed);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/productoSismedJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProductoSismed obtenerProductoSismedJSON(@PathVariable int id, HttpServletResponse response) {
        ProductoSismed productoSismed = new ProductoSismed();

        try {
            productoSismed = productoSismedService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return productoSismed;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarProductoSismed(@ModelAttribute ProductoSismed productoSismed) {
        try {
            productoSismedService.actualizar(productoSismed);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            productoSismedService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarProductoSismed(@PathVariable int id) {
        try {
            productoSismedService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProductoSismedDatatables(request, response);

        return new ModelAndView("ProductoSismedPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {
       
        ManagerDatatables managerDatatables = getProductoSismedDatatables(request, response);
        
        return new ModelAndView("ProductoSismedExcel", "Data", managerDatatables.getAaData());
    }    
}