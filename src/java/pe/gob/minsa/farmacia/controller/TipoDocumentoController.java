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
import pe.gob.minsa.farmacia.domain.TipoDocumento;
import pe.gob.minsa.farmacia.services.impl.TipoDocumentoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoDocumento")
public class TipoDocumentoController {

    @Autowired
    TipoDocumentoService tipoDocumentoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getTipoDocumentoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoDocumento> tiposDocumentos = tipoDocumentoService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposDocumentos.size() - 1; ++i) {
            TipoDocumento d = tiposDocumentos.get(i);
            if (d.getNombreTipoDocumento().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || d.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposDocumentos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposDocumentos, new Comparator<TipoDocumento>() {
            @Override
            public int compare(TipoDocumento o1, TipoDocumento o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoDocumento()).compareTo(o2.getIdTipoDocumento()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoDocumento().toLowerCase().compareTo(o2.getNombreTipoDocumento().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposDocumentos.size());

        if (tiposDocumentos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposDocumentos = tiposDocumentos.subList(dataTablesParam.iDisplayStart, tiposDocumentos.size());
        } else {
            tiposDocumentos = tiposDocumentos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposDocumentos);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoDocumento() {
        return new ModelAndView("TipoDocumento");
    }

    @RequestMapping(value = "/tiposDocumentosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposDocumentosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTipoDocumentoDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoDocumento(@ModelAttribute TipoDocumento tipoDocumento) {

        try {
            tipoDocumentoService.insertar(tipoDocumento);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoDocumentoJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoDocumento obtenerTipoDocumentoJSON(@PathVariable int id, HttpServletResponse response) {
        TipoDocumento tipoDocumento = new TipoDocumento();

        try {
            tipoDocumento = tipoDocumentoService.obtenerPorId(id);
        } catch (BusinessException ex){ 
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoDocumento;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoDocumento(@ModelAttribute TipoDocumento tipoDocumento) {
        try {
            tipoDocumentoService.actualizar(tipoDocumento);
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
            tipoDocumentoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoDocumento(@PathVariable int id) {
        try {
            tipoDocumentoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoDocumentoDatatables = getTipoDocumentoDatatables(request, response);

        return new ModelAndView("TipoDocumentoPDF", "Data", tipoDocumentoDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables tipoDocumentoDatatables = getTipoDocumentoDatatables(request, response);

        return new ModelAndView("TipoDocumentoExcel", "Data", tipoDocumentoDatatables.getAaData());
    }
}