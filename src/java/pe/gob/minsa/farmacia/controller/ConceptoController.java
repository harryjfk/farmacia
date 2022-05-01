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
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Concepto")
public class ConceptoController {

    @Autowired
    ConceptoService conceptoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getConceptoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Concepto> conceptos = conceptoService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= conceptos.size() - 1; ++i) {
            Concepto c = conceptos.get(i);
            if (c.getNombreConcepto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                conceptos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(conceptos, new Comparator<Concepto>() {
            @Override
            public int compare(Concepto o1, Concepto o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdConcepto()).compareTo(o2.getIdConcepto()) * sortDirection;
                    case 1:
                        return o1.getNombreConcepto().toLowerCase().compareTo(o2.getNombreConcepto().toLowerCase()) * sortDirection;
                    case 2:                        
                        return o1.getTipoMovimientoConcepto().toString().compareTo(o2.getTipoMovimientoConcepto().toString()) * sortDirection;    
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(conceptos.size());

        if (conceptos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            conceptos = conceptos.subList(dataTablesParam.iDisplayStart, conceptos.size());
        } else {
            conceptos = conceptos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(conceptos);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarConcepto() {
        return new ModelAndView("Concepto");
    }

    @RequestMapping(value = "/conceptosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerConceptosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getConceptoDatatables(request, response);
    }
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarConcepto(@ModelAttribute Concepto concepto) {

        try {
            conceptoService.insertar(concepto);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/conceptoJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Concepto obtenerConceptoJSON(@PathVariable int id, HttpServletResponse response) {
        Concepto concepto = new Concepto();

        try {
            concepto = conceptoService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return concepto;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarConcepto(@ModelAttribute Concepto concepto) {
        try {
            conceptoService.actualizar(concepto);
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
            conceptoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarConcepto(@PathVariable int id) {
        try {
            conceptoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getConceptoDatatables(request, response);

        return new ModelAndView("ConceptoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getConceptoDatatables(request, response);

        return new ModelAndView("ConceptoExcel", "Data", managerDatatables.getAaData());
    }
}