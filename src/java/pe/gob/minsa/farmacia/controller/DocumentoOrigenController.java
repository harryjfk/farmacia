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
import pe.gob.minsa.farmacia.domain.DocumentoOrigen;
import pe.gob.minsa.farmacia.services.impl.DocumentoOrigenService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/DocumentoOrigen")
public class DocumentoOrigenController {

    @Autowired
    DocumentoOrigenService documentoOrigenService;

    JsonResponse jsonResponse;

    private ManagerDatatables getDocumentoOrigenDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<DocumentoOrigen> documentosOrigen = documentoOrigenService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= documentosOrigen.size() - 1; ++i) {
            DocumentoOrigen c = documentosOrigen.get(i);
            if (c.getNombreDocumentoOrigen().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                documentosOrigen.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(documentosOrigen, new Comparator<DocumentoOrigen>() {
            @Override
            public int compare(DocumentoOrigen o1, DocumentoOrigen o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdDocumentoOrigen()).compareTo(o2.getIdDocumentoOrigen()) * sortDirection;
                    case 1:
                        return o1.getNombreDocumentoOrigen().toLowerCase().compareTo(o2.getNombreDocumentoOrigen().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(documentosOrigen.size());

        if (documentosOrigen.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            documentosOrigen = documentosOrigen.subList(dataTablesParam.iDisplayStart, documentosOrigen.size());
        } else {
            documentosOrigen = documentosOrigen.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(documentosOrigen);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarDocumentoOrigen() {
        return new ModelAndView("DocumentoOrigen");
    }

    @RequestMapping(value = "/documentosOrigenJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerDocumentoOrigensJSON(HttpServletRequest request, HttpServletResponse response) {
        return getDocumentoOrigenDatatables(request, response);
    } 
    
    @RequestMapping(value = "/documentosOrigen", method = RequestMethod.GET)
    @ResponseBody
    public List<DocumentoOrigen> obtenerDocumentosOrigen(HttpServletRequest request, HttpServletResponse response) {        
        return documentoOrigenService.listar();
    } 
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarDocumentoOrigen(@ModelAttribute DocumentoOrigen documentoOrigen) {

        try {
            documentoOrigenService.insertar(documentoOrigen);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/documentoOrigenJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DocumentoOrigen obtenerDocumentoOrigenJSON(@PathVariable int id, HttpServletResponse response) {
        DocumentoOrigen documentoOrigen = new DocumentoOrigen();

        try {
            documentoOrigen = documentoOrigenService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return documentoOrigen;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarDocumentoOrigen(@ModelAttribute DocumentoOrigen documentoOrigen) {
        try {
            documentoOrigenService.actualizar(documentoOrigen);
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
            documentoOrigenService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarDocumentoOrigen(@PathVariable int id) {
        try {
            documentoOrigenService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getDocumentoOrigenDatatables(request, response);

        return new ModelAndView("DocumentoOrigenPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getDocumentoOrigenDatatables(request, response);

        return new ModelAndView("DocumentoOrigenExcel", "Data", managerDatatables.getAaData());
    }    
}