package pe.gob.minsa.farmacia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Ubigeo;
import pe.gob.minsa.farmacia.services.impl.UbigeoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Ubigeo")
public class UbigeoController {

    @Autowired
    UbigeoService ubigeoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getUbigeoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Ubigeo> ubigeos = new ArrayList<Ubigeo>();
        String idUbigeo = request.getParameter("idUbigeo");

        if (idUbigeo == null) {
            ubigeos = ubigeoService.listarDepartamentos();
        } else {

            if (idUbigeo.length() == 2) {
                ubigeos = ubigeoService.listarPronvincias(idUbigeo);
            }

            if (idUbigeo.length() == 4) {
                ubigeos = ubigeoService.listarDistritos(idUbigeo);
            }
        }

        managerDatatables.setiTotalRecords(ubigeos.size());

        for (int i = 0; i <= ubigeos.size() - 1; ++i) {
            Ubigeo a = ubigeos.get(i);
            if (a.getIdUbigeo().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || a.getNombreUbigeo().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || a.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                ubigeos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(ubigeos, new Comparator<Ubigeo>() {
            @Override
            public int compare(Ubigeo o1, Ubigeo o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return String.valueOf(o1.getIdUbigeo()).toLowerCase().compareTo(String.valueOf(o2.getIdUbigeo()).toLowerCase()) * sortDirection;
                    case 1:
                        return o1.getNombreUbigeo().toLowerCase().compareTo(o2.getNombreUbigeo().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(ubigeos.size());

        if (ubigeos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            ubigeos = ubigeos.subList(dataTablesParam.iDisplayStart, ubigeos.size());
        } else {
            ubigeos = ubigeos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(ubigeos);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarUbigeo(HttpServletRequest request) throws BusinessException {

        String idUbigeo = request.getParameter("idUbigeo");
        String nombreUbigeo = null;

        if (idUbigeo != null) {
            Ubigeo ubigeo = ubigeoService.obtenerPorId(idUbigeo);
            nombreUbigeo = ubigeo.getNombreUbigeo();
        }

        return new ModelAndView("Ubigeo", "nombreUbigeo", nombreUbigeo);
    }

    @RequestMapping(value = "/listarProvincias", method = RequestMethod.POST)
    @ResponseBody
    public List<Ubigeo> listarProvincias(@RequestParam(value = "dpto", required = true) String dpto, 
                                         @RequestParam(value = "prov", required = false) String prov) {
        
        
        List<Ubigeo> provincias = null;
        
        if(prov == null){
            provincias = ubigeoService.listarProvinciasActivos(dpto);
        }else{
            provincias = ubigeoService.listarProvinciasActivos(dpto, prov);
        }
        
        return provincias;
    }
    
    @RequestMapping(value = "/listarDistritos", method = RequestMethod.POST)
    @ResponseBody
    public List<Ubigeo> listarDistritos(@RequestParam(value = "prov", required = true) String prov, 
                                         @RequestParam(value = "dist", required = false) String dist) {
        
        
        List<Ubigeo> distritos = null;
        
        if(prov == null){
            distritos = ubigeoService.listarDistritosActivos(prov);
        }else{
            distritos = ubigeoService.listarDistritosActivos(prov, dist);
        }
        
        return distritos;
    }
    
    

    @RequestMapping(value = "/ubigeosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerUbigeosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getUbigeoDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarUbigeo(@ModelAttribute Ubigeo ubigeo) {

        try {
            ubigeoService.insertar(ubigeo);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/ubigeoJSON", method = RequestMethod.GET)
    @ResponseBody
    public Ubigeo obtenerUbigeoJSON(@RequestParam String id) {
        Ubigeo ubigeo = new Ubigeo();

        try {
            ubigeo = ubigeoService.obtenerPorId(id);
        } catch (BusinessException ex) {
            //REGRESAR
        }

        return ubigeo;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarUbigeo(@ModelAttribute Ubigeo ubigeo) {
        try {
            ubigeoService.actualizar(ubigeo);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable String id) {
        try {
            ubigeoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarUbigeo(@PathVariable String id) {
        try {
            ubigeoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        ManagerDatatables managerDatatables = getUbigeoDatatables(request, response);
                
        ModelMap modelMap = new ModelMap();
        modelMap.put("Data", managerDatatables.getAaData());
        
        return new ModelAndView("UbigeoPDF", modelMap);
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) throws BusinessException {

        ManagerDatatables managerDatatables = getUbigeoDatatables(request, response);

        ModelMap modelMap = new ModelMap();
        modelMap.put("Data", managerDatatables.getAaData());
       
        
        return new ModelAndView("UbigeoExcel", modelMap);
    }
}
