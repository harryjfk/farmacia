package pe.gob.minsa.farmacia.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@RequestMapping("/Submodulo")
@Controller
public class SubmoduloController {

    @Autowired
    SubmoduloService submoduloService;

    @Autowired
    ModuloService moduloService;

    JsonResponse jsonResponse;
    
    private ManagerDatatables getSubmoduloDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        int idModulo = Integer.parseInt(request.getParameter("idModulo"));
        List<Submodulo> submodulos = submoduloService.listarPorIdModulo(idModulo);

        managerDatatables.setiTotalRecords(submodulos.size());

        for (int i = 0; i <= submodulos.size() - 1; ++i) {
            Submodulo submodulo = submodulos.get(i);
            if (String.valueOf(submodulo.getIdSubmodulo()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || submodulo.getNombreSubmodulo().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || String.valueOf(submodulo.getOrden()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || submodulo.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                submodulos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(submodulos, new Comparator<Submodulo>() {
            @Override
            public int compare(Submodulo o1, Submodulo o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdSubmodulo()).compareTo(o2.getIdSubmodulo()) * sortDirection;
                    case 1:
                        return o1.getNombreSubmodulo().toLowerCase().compareTo(o2.getNombreSubmodulo().toLowerCase()) * sortDirection;
                    case 2:
                        return ((Integer)o1.getOrden()).compareTo(o2.getOrden()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(submodulos.size());

        if (submodulos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            submodulos = submodulos.subList(dataTablesParam.iDisplayStart, submodulos.size());
        } else {
            submodulos = submodulos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(submodulos);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarSubmodulos() {
        List<Modulo> modulos = moduloService.listar();
        return new ModelAndView("Submodulo", "modulos", modulos);
    }

    @RequestMapping(value = "/submodulosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerSubmodulosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSubmoduloDatatables(request, response);
    }

    @RequestMapping(value = "/submoduloJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap obtenerSubmoduloJSON(@PathVariable int id, HttpServletResponse response) {

        ModelMap model = new ModelMap();
        
        try {
            Submodulo submodulo = submoduloService.obtenerPorId(id);
            Modulo modulo = moduloService.obtenerPorId(submodulo.getIdModulo());

            model.put("nombreModulo", modulo.getNombreModulo());
            model.put("submodulo", submodulo);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return model;
    }    
    
    @RequestMapping(value = "/orden", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarOrden(@RequestBody NavegacionOrden navegacionOrden) {

        try {
            submoduloService.cambiarOrden(navegacionOrden.getId(), navegacionOrden.isSubida());
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarSubmodulo(@ModelAttribute Submodulo submodulo) {

        try {
            submoduloService.actualizar(submodulo);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {        
        try {
            submoduloService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/submodulosPorModuloJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Submodulo> listarSubmoduloPorModulo(@RequestParam int idModulo) {
        List<Submodulo> submodulos = submoduloService.listarPorIdModulo(idModulo);
        return submodulos;
    }
}
