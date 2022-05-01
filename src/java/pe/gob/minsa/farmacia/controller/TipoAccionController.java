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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.services.impl.TipoAccionService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoAccion")
public class TipoAccionController {

    @Autowired
    TipoAccionService tipoAccionService;

    JsonResponse jsonResponse;

    private ManagerDatatables getTipoAccionDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoAccion> tiposAcciones = tipoAccionService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposAcciones.size() - 1; ++i) {
            TipoAccion a = tiposAcciones.get(i);
            if (a.getNombreTipoAccion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || a.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposAcciones.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposAcciones, new Comparator<TipoAccion>() {
            @Override
            public int compare(TipoAccion o1, TipoAccion o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoAccion()).compareTo(o2.getIdTipoAccion()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoAccion().toLowerCase().compareTo(o2.getNombreTipoAccion().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposAcciones.size());

        if (tiposAcciones.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposAcciones = tiposAcciones.subList(dataTablesParam.iDisplayStart, tiposAcciones.size());
        } else {
            tiposAcciones = tiposAcciones.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposAcciones);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoAccion() {
        return new ModelAndView("TipoAccion");
    }

    @RequestMapping(value = "/tiposAccionesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposAccionesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTipoAccionDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoAccion(@ModelAttribute TipoAccion tipoAccion) {

        try {
            tipoAccionService.insertar(tipoAccion);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoAccionJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoAccion obtenerTipoAccionJSON(@PathVariable int id, HttpServletResponse response) {
        TipoAccion tipoAccion = new TipoAccion();

        try {
            tipoAccion = tipoAccionService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoAccion;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoAccion(@ModelAttribute TipoAccion tipoAccion) {
        try {
            tipoAccionService.actualizar(tipoAccion);
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
            tipoAccionService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoAccion(@PathVariable int id) {
        try {
            tipoAccionService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoAccionDatatables = getTipoAccionDatatables(request, response);

        return new ModelAndView("TipoAccionPDF", "Data", tipoAccionDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoAccionDatatables = getTipoAccionDatatables(request, response);

        return new ModelAndView("TipoAccionExcel", "Data", tipoAccionDatatables.getAaData());
    }
}