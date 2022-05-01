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
import pe.gob.minsa.farmacia.domain.TipoProceso;
import pe.gob.minsa.farmacia.services.impl.TipoProcesoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoProceso")
public class TipoProcesoController {

    @Autowired
    TipoProcesoService tipoProcesoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getTipoProcesoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoProceso> tiposProceso = tipoProcesoService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposProceso.size() - 1; ++i) {
            TipoProceso c = tiposProceso.get(i);
            if (c.getNombreTipoProceso().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposProceso.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposProceso, new Comparator<TipoProceso>() {
            @Override
            public int compare(TipoProceso o1, TipoProceso o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer) o1.getIdTipoProceso()).compareTo(o2.getIdTipoProceso()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoProceso().toLowerCase().compareTo(o2.getNombreTipoProceso().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposProceso.size());

        if (tiposProceso.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposProceso = tiposProceso.subList(dataTablesParam.iDisplayStart, tiposProceso.size());
        } else {
            tiposProceso = tiposProceso.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposProceso);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoProceso() {
        return new ModelAndView("TipoProceso");
    }

    @RequestMapping(value = "/tiposProcesoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTipoProcesosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTipoProcesoDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoProceso(@ModelAttribute TipoProceso tipoProceso) {

        try {
            tipoProcesoService.insertar(tipoProceso);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoProcesoJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoProceso obtenerTipoProcesoJSON(@PathVariable int id, HttpServletResponse response) {
        TipoProceso tipoProceso = new TipoProceso();

        try {
            tipoProceso = tipoProcesoService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoProceso;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoProceso(@ModelAttribute TipoProceso tipoProceso) {
        try {
            tipoProcesoService.actualizar(tipoProceso);
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
            tipoProcesoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoProceso(@PathVariable int id) {
        try {
            tipoProcesoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoProcesoDatatables(request, response);

        return new ModelAndView("TipoProcesoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoProcesoDatatables(request, response);

        return new ModelAndView("TipoProcesoExcel", "Data", managerDatatables.getAaData());
    }
}