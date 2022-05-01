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
import pe.gob.minsa.farmacia.domain.UnidadMedida;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/UnidadMedida")
public class UnidadMedidaController {

    @Autowired
    UnidadMedidaService unidadMedidaService;

    JsonResponse jsonResponse;

    private ManagerDatatables getUnidadMedidaDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<UnidadMedida> unidadesMedida = unidadMedidaService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= unidadesMedida.size() - 1; ++i) {
            UnidadMedida c = unidadesMedida.get(i);
            if (c.getNombreUnidadMedida().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getAbreviatura().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                unidadesMedida.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(unidadesMedida, new Comparator<UnidadMedida>() {
            @Override
            public int compare(UnidadMedida o1, UnidadMedida o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdUnidadMedida()).compareTo(o2.getIdUnidadMedida()) * sortDirection;
                    case 1:
                        return o1.getNombreUnidadMedida().toLowerCase().compareTo(o2.getNombreUnidadMedida().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getAbreviatura().toLowerCase().compareTo(o2.getAbreviatura().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(unidadesMedida.size());

        if (unidadesMedida.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            unidadesMedida = unidadesMedida.subList(dataTablesParam.iDisplayStart, unidadesMedida.size());
        } else {
            unidadesMedida = unidadesMedida.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(unidadesMedida);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarUnidadesMedida() {
        return new ModelAndView("UnidadMedida");
    }
    
    @RequestMapping(value = "/unidadesMedidaJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerUnidadesMedidaJSON(HttpServletRequest request, HttpServletResponse response) {
        return getUnidadMedidaDatatables(request, response);
    }
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarUnidadMedida(@ModelAttribute UnidadMedida unidadMedida) {

        try {
            unidadMedidaService.insertar(unidadMedida);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/unidadMedidaJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UnidadMedida obtenerUnidadMedidaJSON(@PathVariable int id, HttpServletResponse response) {
        UnidadMedida unidadMedida = new UnidadMedida();

        try {
            unidadMedida = unidadMedidaService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return unidadMedida;
    }
    
    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarUnidadMedida(@ModelAttribute UnidadMedida unidadMedida) {
        try {
            unidadMedidaService.actualizar(unidadMedida);
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
            unidadMedidaService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarUnidadMedida(@PathVariable int id) {
        try {
            unidadMedidaService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getUnidadMedidaDatatables(request, response);

        return new ModelAndView("UnidadMedidaPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getUnidadMedidaDatatables(request, response);

        return new ModelAndView("UnidadMedidaExcel", "Data", managerDatatables.getAaData());
    }
}