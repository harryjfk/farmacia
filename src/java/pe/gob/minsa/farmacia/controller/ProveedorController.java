package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.services.impl.ProveedorService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Proveedor")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    JsonResponse jsonResponse;

    private ManagerDatatables getProveedorDatatables(HttpServletRequest request, HttpServletResponse response, List<Proveedor> proveedores) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        for (int i = 0; i <= proveedores.size() - 1; ++i) {
            Proveedor c = proveedores.get(i);
            if (c.getRazonSocial().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getRuc().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getTipoProveedor().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                proveedores.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(proveedores, new Comparator<Proveedor>() {
            @Override
            public int compare(Proveedor o1, Proveedor o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdProveedor()).compareTo(o2.getIdProveedor()) * sortDirection;
                    case 1:
                        return o1.getRazonSocial().toLowerCase().compareTo(o2.getRazonSocial().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(proveedores.size());

        if (proveedores.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            proveedores = proveedores.subList(dataTablesParam.iDisplayStart, proveedores.size());
        } else {
            proveedores = proveedores.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(proveedores);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarProveedor() {
        return new ModelAndView("Proveedor");
    }

    @RequestMapping(value = "/proveedoresJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProveedoresJSON(HttpServletRequest request, HttpServletResponse response) {
        String tP;
        if(request.getParameter("tP")!=null){
            tP = request.getParameter("tP");
            if(tP.isEmpty())
                return getProveedorDatatables(request, response, proveedorService.listarPorTipo("PROVEEDOR"));
            if(tP.equalsIgnoreCase("D"))
                return getProveedorDatatables(request, response, proveedorService.listarPorTipo("DEPENDENCIA"));
            if(tP.equalsIgnoreCase("P"))
                return getProveedorDatatables(request, response, proveedorService.listarPorTipo("PROVEEDOR"));
        }
            return getProveedorDatatables(request, response, proveedorService.listar());
        
    }
    
    @RequestMapping(value = "/findBasicByRUC/{ruc}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findBasicByRUC(@PathVariable String ruc){
        
        LinkedHashMap<String, String> datos;
        Proveedor proveedor = proveedorService.obtenerPorRuc(ruc);
        
        if(proveedor != null){
            datos = new LinkedHashMap<String, String>();
            datos.put("id", String.valueOf(proveedor.getIdProveedor()));
            datos.put("ruc", proveedor.getRuc());
            datos.put("razonSocial", proveedor.getRazonSocial());
            datos.put("tipoProveedor", proveedor.getTipoProveedor());
        }else{
            datos = null;
        }
        
        return datos;
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarProveedorGET() {
        return new ModelAndView("Proveedor/registrar");
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarProveedorPOST(@RequestBody Proveedor proveedor) {

        try {
            proveedorService.insertar(proveedor);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarProveedorGET(@PathVariable int id, HttpServletRequest request) {
        try {
            Proveedor proveedor = proveedorService.obtenerPorId(id);

            ModelMap model = new ModelMap();
            model.put("proveedor", proveedor);

            return new ModelAndView("Proveedor/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Proveedor");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarProveedorPOST(@RequestBody Proveedor proveedor) {
        try {
            proveedorService.actualizar(proveedor);
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
            proveedorService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarProveedor(@PathVariable int id) {
        try {
            proveedorService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProveedorDatatables(request, response, proveedorService.listar());

        return new ModelAndView("ProveedorPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProveedorDatatables(request, response,proveedorService.listar());

        return new ModelAndView("ProveedorExcel", "Data", managerDatatables.getAaData());
    }
}