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
import pe.gob.minsa.farmacia.domain.ProductoSiga;
import pe.gob.minsa.farmacia.services.impl.ProductoSigaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/ProductoSiga")
public class ProductoSigaController {
    
    @Autowired
    ProductoSigaService productoSigaService;

    JsonResponse jsonResponse;
    
     private ManagerDatatables getProductoSigaDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<ProductoSiga> productosSiga = productoSigaService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= productosSiga.size() - 1; ++i) {
            ProductoSiga c = productosSiga.get(i);
            if (c.getNombreProductoSiga().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getCodigoSiga().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                productosSiga.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(productosSiga, new Comparator<ProductoSiga>() {
            @Override
            public int compare(ProductoSiga o1, ProductoSiga o2) {
                switch (sortColumnIndex) {                    
                    case 0:
                        return o1.getCodigoSiga().toLowerCase().compareTo(o2.getCodigoSiga().toLowerCase()) * sortDirection;
                    case 1:
                        return o1.getNombreProductoSiga().toLowerCase().compareTo(o2.getNombreProductoSiga().toLowerCase()) * sortDirection;                    
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(productosSiga.size());

        if (productosSiga.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productosSiga = productosSiga.subList(dataTablesParam.iDisplayStart, productosSiga.size());
        } else {
            productosSiga = productosSiga.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(productosSiga);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarProductoSiga() {
        return new ModelAndView("ProductoSiga");
    }

    @RequestMapping(value = "/productosSigaJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductoSigasJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductoSigaDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarProductoSiga(@ModelAttribute ProductoSiga productoSiga) {

        try {
            productoSigaService.insertar(productoSiga);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/productoSigaJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProductoSiga obtenerProductoSigaJSON(@PathVariable int id, HttpServletResponse response) {
        ProductoSiga productoSiga = new ProductoSiga();

        try {
            productoSiga = productoSigaService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return productoSiga;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarProductoSiga(@ModelAttribute ProductoSiga productoSiga) {
        try {
            productoSigaService.actualizar(productoSiga);
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
            productoSigaService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarProductoSiga(@PathVariable int id) {
        try {
            productoSigaService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProductoSigaDatatables(request, response);

        return new ModelAndView("ProductoSigaPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {
       
        ManagerDatatables managerDatatables = getProductoSigaDatatables(request, response);
        
        return new ModelAndView("ProductoSigaExcel", "Data", managerDatatables.getAaData());
    }
}
