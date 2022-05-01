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
import pe.gob.minsa.farmacia.domain.TipoDocumentoMov;
import pe.gob.minsa.farmacia.services.impl.TipoDocumentoMovService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoDocumentoMov")
public class TipoDocumentoMovController {

    @Autowired
    TipoDocumentoMovService tipoDocumentoMovService;

    JsonResponse jsonResponse;

    private ManagerDatatables getTipoDocumentoMovDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoDocumentoMov> tiposDocumentoMov = tipoDocumentoMovService.listar();

        managerDatatables.setiTotalRecords(tiposDocumentoMov.size());

        for (int i = 0; i <= tiposDocumentoMov.size() - 1; ++i) {
            TipoDocumentoMov d = tiposDocumentoMov.get(i);
            if (d.getNombreTipoDocumentoMov().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || d.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposDocumentoMov.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposDocumentoMov, new Comparator<TipoDocumentoMov>() {
            @Override
            public int compare(TipoDocumentoMov o1, TipoDocumentoMov o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoDocumentoMov()).compareTo(o2.getIdTipoDocumentoMov()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoDocumentoMov().toLowerCase().compareTo(o2.getNombreTipoDocumentoMov().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposDocumentoMov.size());

        if (tiposDocumentoMov.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposDocumentoMov = tiposDocumentoMov.subList(dataTablesParam.iDisplayStart, tiposDocumentoMov.size());
        } else {
            tiposDocumentoMov = tiposDocumentoMov.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposDocumentoMov);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoDocumentoMov() {
        return new ModelAndView("TipoDocumentoMov");
    }

    @RequestMapping(value = "/tiposDocumentoMovJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposDocumentoMovJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTipoDocumentoMovDatatables(request, response);
    }

    @RequestMapping(value = "/tiposDocumentoMov", method = RequestMethod.GET)
    @ResponseBody
    public List<TipoDocumentoMov> obtenerTiposDocumento(HttpServletRequest request, HttpServletResponse response) {        
        return tipoDocumentoMovService.listar();
    }
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoDocumentoMov(@ModelAttribute TipoDocumentoMov tipoDocumentoMov) {

        try {
            tipoDocumentoMovService.insertar(tipoDocumentoMov);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoDocumentoMovJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoDocumentoMov obtenerTipoDocumentoMovJSON(@PathVariable int id, HttpServletResponse response) {
        TipoDocumentoMov tipoDocumentoMov = new TipoDocumentoMov();

        try {
            tipoDocumentoMov = tipoDocumentoMovService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoDocumentoMov;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoDocumentoMov(@ModelAttribute TipoDocumentoMov tipoDocumentoMov) {
        try {
            tipoDocumentoMovService.actualizar(tipoDocumentoMov);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            tipoDocumentoMovService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoDocumentoMov(@PathVariable int id) {
        try {
            tipoDocumentoMovService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoDocumentoMovDatatables = getTipoDocumentoMovDatatables(request, response);

        return new ModelAndView("TipoDocumentoMovPDF", "Data", tipoDocumentoMovDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoDocumentoMovDatatables = getTipoDocumentoMovDatatables(request, response);

        return new ModelAndView("TipoDocumentoMovExcel", "Data", tipoDocumentoMovDatatables.getAaData());
    }
}