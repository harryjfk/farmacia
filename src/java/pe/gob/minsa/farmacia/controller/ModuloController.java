package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import pe.gob.minsa.farmacia.util.JsonResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@RequestMapping("/Modulo")
@Controller
public class ModuloController {

    @Autowired
    ModuloService moduloService;

    JsonResponse jsonResponse;
    
    private ManagerDatatables getModuloDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Modulo> modulos = moduloService.listar();

        managerDatatables.setiTotalRecords(modulos.size());

        for (int i = 0; i <= modulos.size() - 1; ++i) {
            Modulo modulo = modulos.get(i);
            if (String.valueOf(modulo.getIdModulo()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || modulo.getNombreModulo().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || String.valueOf(modulo.getOrden()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || modulo.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                modulos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(modulos, new Comparator<Modulo>() {
            @Override
            public int compare(Modulo o1, Modulo o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdModulo()).compareTo(o2.getIdModulo()) * sortDirection;
                    case 1:
                        return o1.getNombreModulo().toLowerCase().compareTo(o2.getNombreModulo().toLowerCase()) * sortDirection;
                    case 2:
                        return ((Integer)o1.getOrden()).compareTo(o2.getOrden()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(modulos.size());

        if (modulos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            modulos = modulos.subList(dataTablesParam.iDisplayStart, modulos.size());
        } else {
            modulos = modulos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(modulos);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarModulo() {
        return new ModelAndView("Modulo");
    }

    @RequestMapping(value = "/modulosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerModulosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getModuloDatatables(request, response);
    }

    @RequestMapping(value = "/moduloJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Modulo obtenerModuloJSON(@PathVariable int id, HttpServletResponse response) {
        Modulo modulo = new Modulo();
        try {
            modulo = moduloService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return modulo;
    }
    
    @RequestMapping(value = "/orden", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarOrden(@RequestBody NavegacionOrden navegacionOrden) {

        try {
            moduloService.cambiarOrden(navegacionOrden.getId(), navegacionOrden.isSubida());
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
    public JsonResponse modificarModulo(@ModelAttribute Modulo modulo) {

        try {
            moduloService.actualizar(modulo);
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
    public JsonResponse cambiarEstado(@PathVariable int id) {

        try {
            moduloService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }
}
