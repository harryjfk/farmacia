package pe.gob.minsa.farmacia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Documento;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.domain.TipoDocumento;
import pe.gob.minsa.farmacia.domain.dto.DocumentoComp;
import pe.gob.minsa.farmacia.services.impl.DocumentoService;
import pe.gob.minsa.farmacia.services.impl.TipoAccionService;
import pe.gob.minsa.farmacia.services.impl.TipoDocumentoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @Autowired
    TipoDocumentoService tipoDocumentoService;

    @Autowired
    TipoAccionService tipoAccionService;

    @Autowired
    ServletContext context;

    JsonResponse jsonResponse;

    int tamanioDocumento;

    String rutaDocumentos;

    public void setTamanioDocumento(int tamanioDocumento) {
        this.tamanioDocumento = tamanioDocumento * 1024;
    }

    public void setRutaDocumentos(String rutaDocumentos) {
        this.rutaDocumentos = rutaDocumentos;
    }

    private ManagerDatatables getDocumentoDatatables(HttpServletRequest request, HttpServletResponse response, boolean esDespacho) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        
        int idUuario = InterceptorSecurity.getIdUsuario(request);
        List<DocumentoComp> documentos = documentoService.listarComp(idUuario);

        managerDatatables.setiTotalRecords(0);
        
        int idTipoDocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
        int idTipoAccion = Integer.parseInt(request.getParameter("tipoAccion"));
        String nroDocumento = request.getParameter("nroDocumento");
        String numeracionInterna = request.getParameter("numeracionInterna");
        
        //filtro fechas
        Timestamp fechaDesde = new Timestamp(Long.parseLong(request.getParameter("fechaDesde")));
        Timestamp fechaHasta = new Timestamp(Long.parseLong(request.getParameter("fechaHasta")));
        //

        for (int i = 0; i <= documentos.size() - 1; ++i) {
            DocumentoComp d = documentos.get(i);
            if ((d.getIdTipoDocumento() == idTipoDocumento || idTipoDocumento == 0)
                    && (d.getIdTipoAccion() == idTipoAccion || idTipoAccion == 0)
                    && d.getNroDocumento().toLowerCase().contains(nroDocumento)
                    && d.getNumeracionInterna().toLowerCase().contains(numeracionInterna)) {
            } else {
                documentos.remove(i);
                i = i - 1;
            }
        }
        
        for (int i = 0; i <= documentos.size() - 1; ++i) {
            DocumentoComp d = documentos.get(i);
            if (d.getFechaDocumento().after(fechaHasta) || d.getFechaDocumento().before(fechaDesde)) {
                documentos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        if (esDespacho == false) {
            Collections.sort(documentos, new Comparator<DocumentoComp>() {
                @Override
                public int compare(DocumentoComp o1, DocumentoComp o2) {
                    switch (sortColumnIndex) {
                        case 0:
                            return o1.getFechaDocumento().compareTo(o2.getFechaDocumento()) * sortDirection;
                        case 1:
                            Timestamp ts1 = (o1.getFechaSalida() == null) ? new Timestamp(0) : o1.getFechaSalida();
                            Timestamp ts2 = (o2.getFechaSalida() == null) ? new Timestamp(0) : o2.getFechaSalida();
                            return ts1.compareTo(ts2) * sortDirection;
                        case 2:
                            return o1.getNombreTipoAccion().toLowerCase().compareTo(o2.getNombreTipoAccion().toLowerCase()) * sortDirection;
                        case 3:
                            return o1.getNombreTipoDocumento().toLowerCase().compareTo(o2.getNombreTipoDocumento().toLowerCase()) * sortDirection;
                        case 6:
                            return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                    }
                    return 0;
                }
            });
        } else {
            Collections.sort(documentos, new Comparator<DocumentoComp>() {
                @Override
                public int compare(DocumentoComp o1, DocumentoComp o2) {
                    switch (sortColumnIndex) {
                        case 0:
                            return o1.getFechaDocumento().compareTo(o2.getFechaDocumento()) * sortDirection;
                        case 1:
                            return o1.getNombreTipoDocumento().toLowerCase().compareTo(o2.getNombreTipoDocumento().toLowerCase()) * sortDirection;
                        case 5:
                            Timestamp ts1 = (o1.getFechaDespacho() == null) ? new Timestamp(0) : o1.getFechaDespacho();
                            Timestamp ts2 = (o2.getFechaDespacho() == null) ? new Timestamp(0) : o2.getFechaDespacho();
                            return ts1.compareTo(ts2) * sortDirection;
                    }
                    return 0;
                }
            });
        }

        managerDatatables.setiTotalDisplayRecords(documentos.size());

        if (documentos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, documentos.size());
        } else {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(documentos);

        return managerDatatables;
    }

    private ManagerDatatables getAlertaDocumentariaDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        
        int idUuario = InterceptorSecurity.getIdUsuario(request);
        List<DocumentoComp> documentos = documentoService.listarComp(idUuario);

        for (int i = 0; i < documentos.size(); ++i) {
            if (documentos.get(i).getFechaDespacho() != null) {
                documentos.remove(i);
                i = i - 1;
            }
        }

        managerDatatables.setiTotalRecords(documentos.size());
        int idTipoDocumento = Integer.parseInt(request.getParameter("tipoDocumento"));
        int idTipoAccion = Integer.parseInt(request.getParameter("tipoAccion"));
        String nroDocumento = request.getParameter("nroDocumento");
        String numeracionInterna = request.getParameter("numeracionInterna");

        for (int i = 0; i <= documentos.size() - 1; ++i) {
            DocumentoComp d = documentos.get(i);
            if ((d.getIdTipoDocumento() == idTipoDocumento || idTipoDocumento == 0)
                    && (d.getIdTipoAccion() == idTipoAccion || idTipoAccion == 0)
                    && d.getNroDocumento().toLowerCase().contains(nroDocumento)
                    && d.getNumeracionInterna().toLowerCase().contains(numeracionInterna)) {
            } else {
                documentos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(documentos, new Comparator<DocumentoComp>() {
            @Override
            public int compare(DocumentoComp o1, DocumentoComp o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return o1.getFechaDocumento().compareTo(o2.getFechaDocumento()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoDocumento().toLowerCase().compareTo(o2.getNombreTipoDocumento().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(documentos.size());

        if (documentos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, documentos.size());
        } else {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(documentos);

        return managerDatatables;
    }
    
    private ManagerDatatables getReporteDocumentosDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        
        int idUuario = InterceptorSecurity.getIdUsuario(request);
        List<DocumentoComp> documentos = documentoService.listarComp(idUuario);
        
        managerDatatables.setiTotalRecords(0);
        
        int idTipoDocumento = Integer.parseInt(request.getParameter("tipoAccion"));
        long fechaDesdeTime = Long.parseLong(request.getParameter("fechaDesde"));
        long fechaHastaTime = Long.parseLong(request.getParameter("fechaHasta"));
        
        Timestamp fechaDesde = new Timestamp(fechaDesdeTime);
        Timestamp fechaHasta = new Timestamp(fechaHastaTime);
                
        for (int i = 0; i < documentos.size(); ++i) {
            int fechaDesdeComparacion = documentos.get(i).getFechaDocumento().compareTo(fechaDesde);
            int fechaHastaComparacion = documentos.get(i).getFechaDocumento().compareTo(fechaHasta);
                       
            if ((fechaDesdeComparacion == 0 || fechaDesdeComparacion == 1)
                    && (fechaHastaComparacion ==  0 || fechaHastaComparacion == -1) 
                    && (idTipoDocumento == documentos.get(i).getIdTipoDocumento() || idTipoDocumento == 0)) {
            }else{
                documentos.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;
        
        managerDatatables.setiTotalDisplayRecords(0);

        if (documentos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, documentos.size());
        } else {
            documentos = documentos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(documentos);

        return managerDatatables;
    }

    @RequestMapping(value = "/Documento", method = RequestMethod.GET)
    public ModelAndView listarDocumento() {

        List<TipoDocumento> tiposDocumento = tipoDocumentoService.listar();
        List<TipoAccion> tiposAccion = tipoAccionService.listar();

        ModelMap model = new ModelMap();
        model.put("tiposDocumento", tiposDocumento);
        model.put("tiposAccion", tiposAccion);

        return new ModelAndView("Documento", model);
    }

    @RequestMapping(value = "/Despacho", method = RequestMethod.GET)
    public ModelAndView listarDespacho() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.listar();
        List<TipoAccion> tiposAccion = tipoAccionService.listar();
        ModelMap model = new ModelMap();
        model.put("tiposDocumento", tiposDocumento);
        model.put("tiposAccion", tiposAccion);
        return new ModelAndView("Despacho", model);
    }

    @RequestMapping(value = "/AlertaDocumentaria", method = RequestMethod.GET)
    public ModelAndView listarAlertaDocumentaria() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoService.listar();
        List<TipoAccion> tiposAccion = tipoAccionService.listar();
        ModelMap model = new ModelMap();
        model.put("tiposDocumento", tiposDocumento);
        model.put("tiposAccion", tiposAccion);
        return new ModelAndView("AlertaDocumentaria", model);
    }

    @RequestMapping(value = "/ReporteDocumentos", method = RequestMethod.GET)
    public ModelAndView listarReporteDocumentos() {
        List<TipoAccion> tiposAccion = tipoAccionService.listar();
        ModelMap model = new ModelMap();
        model.put("tiposAccion", tiposAccion);
        return new ModelAndView("ReporteDocumentos", model);
    }

    @RequestMapping(value = "/Documento/documentosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerDocumentosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getDocumentoDatatables(request, response, false);
    }

    @RequestMapping(value = "/Despacho/despachosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerDespachosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getDocumentoDatatables(request, response, true);
    }

    @RequestMapping(value = "/AlertaDocumentaria/despachosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerAlertaDespachosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getAlertaDocumentariaDatatables(request, response);
    }

    @RequestMapping(value = "/ReporteDocumentos/documentosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerReporteDocumentosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getReporteDocumentosDatatables(request, response);
    }
    
    @RequestMapping(value = "/Despacho/actualizar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse actualizarDespachoPOST(@RequestBody Documento documento) {

        try {
            documentoService.actualizarDespacho(documento.getIdDocumento(), documento.getDespacho());
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Documento/registrar", method = RequestMethod.GET)
    public ModelAndView registrarDocumento() {
        List<TipoDocumento> tiposDocumentos = tipoDocumentoService.listarActivos();
        List<TipoAccion> tiposAcciones = tipoAccionService.listarActivos();

        ModelMap model = new ModelMap();
        model.put("tiposDocumentos", tiposDocumentos);
        model.put("tiposAcciones", tiposAcciones);

        return new ModelAndView("Documento/registrar", model);
    }

    @RequestMapping(value = "/Documento/registrar", method = RequestMethod.POST)
    @ResponseBody
    public String registrarDocumentoPOST(@RequestParam("fileDocumento") MultipartFile fileDocumento, @RequestParam("jsonForm") String jsonForm, HttpServletRequest request) {
        String reponse = "";
        ObjectMapper mapper = new ObjectMapper();

        try {

            Documento documento = mapper.readValue(jsonForm, Documento.class);

            if (!fileDocumento.isEmpty()) {

                if (fileDocumento.getSize() > tamanioDocumento) {
                    throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
                }

                int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                String extension = fileDocumento.getOriginalFilename().substring(posicionIndex);
                documento.setExtension(extension);
            } else {
                documento.setExtension(null);
            }

            int idUsuario = InterceptorSecurity.getIdUsuario(request);
            documento.setUsuarioCreacion(idUsuario);
            documento.setIdUsuario(idUsuario);
            
            documentoService.insertar(documento);

            if (!fileDocumento.isEmpty()) {

                byte[] bytes = fileDocumento.getBytes();

                File directorioArchivos = new File(context.getRealPath(rutaDocumentos));
                if (!directorioArchivos.exists()) {
                    directorioArchivos.mkdirs();
                }

                File serverFile = new File(directorioArchivos.getAbsolutePath() + File.separator + documento.getIdDocumento() + documento.getExtension());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            }

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        } catch (IOException ex) {
            jsonResponse = new JsonResponse(false, Arrays.asList(ex.getMessage()));
        }

        try {
            reponse = mapper.writeValueAsString(jsonResponse);
        } catch (IOException ex) {
        }

        return reponse;
    }

    @RequestMapping(value = "/Documento/modificar", method = RequestMethod.GET)
    public ModelAndView modificarDocumento(@RequestParam int idDocumento, HttpServletRequest request) {

        try {
            Documento documento = documentoService.obtenerPorId(idDocumento);

            List<TipoDocumento> tiposDocumentos = tipoDocumentoService.listarActivos(documento.getIdTipoDocumento());
            List<TipoAccion> tiposAcciones = tipoAccionService.listarActivos(documento.getIdTipoAccion());

            ModelMap model = new ModelMap();
            model.put("tiposDocumentos", tiposDocumentos);
            model.put("tiposAcciones", tiposAcciones);
            model.put("documento", documento);

            return new ModelAndView("Documento/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Documento");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/Documento/modificar", method = RequestMethod.POST)
    @ResponseBody
    public String modificarDocumentoPOST(@RequestParam("fileDocumento") MultipartFile fileDocumento, @RequestParam("jsonForm") String jsonForm, @RequestParam("DeleteFile") boolean DeleteFile, HttpServletRequest request) {
        String reponse = "";
        ObjectMapper mapper = new ObjectMapper();

        try {

            Documento documento = mapper.readValue(jsonForm, Documento.class);

            try {

                Documento documentoTemp = documentoService.obtenerPorId(documento.getIdDocumento());
                documento.setExtension(documentoTemp.getExtension());

                if (!fileDocumento.isEmpty()) {

                    if (fileDocumento.getSize() > tamanioDocumento) {
                        throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
                    }

                    int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                    String extension = fileDocumento.getOriginalFilename().substring(posicionIndex);
                    documento.setExtension(extension);
                } else {
                    if (DeleteFile) {
                        documento.setExtension(null);
                    }
                }

                documento.setIdUsuario(documentoTemp.getIdUsuario());
                documento.setUsuarioModificacion(InterceptorSecurity.getIdUsuario(request));
                documentoService.actualizar(documento);

                File directorioArchivos = new File(context.getRealPath(rutaDocumentos));

                if (!directorioArchivos.exists()) {
                    directorioArchivos.mkdirs();
                }

                if (DeleteFile) {
                    File documentoArchivo = new File(directorioArchivos.getAbsolutePath() + File.separator + documentoTemp.getIdDocumento() + documentoTemp.getExtension());

                    if (documentoArchivo.exists()) {
                        documentoArchivo.delete();
                    }
                }

                if (!fileDocumento.isEmpty()) {
                    File documentoArchivo = new File(directorioArchivos.getAbsolutePath() + File.separator + documento.getIdDocumento() + documento.getExtension());

                    byte[] bytes = fileDocumento.getBytes();

                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(documentoArchivo));
                    stream.write(bytes);
                    stream.close();
                }

                jsonResponse = new JsonResponse();
                jsonResponse.respuestaModificar();
                jsonResponse.setPaginaRedireccion("listar");

            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }
        } catch (IOException ex) {
            jsonResponse = new JsonResponse(false, Arrays.asList(ex.getMessage()));
        }

        try {
            reponse = mapper.writeValueAsString(jsonResponse);
        } catch (IOException ex) {
        }

        return reponse;
    }

    @RequestMapping(value = "/Documento/descargar/{id}", method = RequestMethod.GET)
    public ModelAndView descargarDocumento(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

        try {
            Documento documento = new Documento();

            try {
                documento = documentoService.obtenerPorId(id);
            } catch (BusinessException ex) {
                jsonResponse = new JsonResponse(false, ex.getMensajesError());
                jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Documento");
                return new ModelAndView("blank/error", "reponseError", jsonResponse);
            }
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + documento.getNroDocumento() + documento.getExtension() + "\"");

            File directorioArchivos = new File(context.getRealPath(rutaDocumentos));

            InputStream is = new FileInputStream(directorioArchivos.getAbsolutePath() + File.separator + documento.getIdDocumento() + documento.getExtension());
            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (IOException ex) {
            jsonResponse = new JsonResponse(false, Arrays.asList("Error al procesar el documento, inténtelo nuevamente."));
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Documento");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }

        return null;
    }

    @RequestMapping(value = "/Documento/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            documentoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Documento/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarDocumento(@PathVariable int id) {

        try {
            documentoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Documento/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getDocumentoDatatables(request, response, false);

        return new ModelAndView("DocumentoPDF", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/Documento/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getDocumentoDatatables(request, response, false);

        return new ModelAndView("DocumentoExcel", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/Despacho/pdf", method = RequestMethod.GET)
    public ModelAndView rptDespachoPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getDocumentoDatatables(request, response, true);

        return new ModelAndView("DespachoPDF", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/Despacho/excel", method = RequestMethod.GET)
    public ModelAndView rptDespachoExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getDocumentoDatatables(request, response, true);

        return new ModelAndView("DespachoExcel", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/AlertaDocumentaria/pdf", method = RequestMethod.GET)
    public ModelAndView rptAlertaDocumentariaPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getAlertaDocumentariaDatatables(request, response);

        return new ModelAndView("AlertaDocumentariaPDF", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/AlertaDocumentaria/excel", method = RequestMethod.GET)
    public ModelAndView rptAlertaDocumentariaExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables documentoDatatables = getAlertaDocumentariaDatatables(request, response);

        return new ModelAndView("AlertaDocumentariaExcel", "Data", documentoDatatables.getAaData());
    }

    @RequestMapping(value = "/ReporteDocumentos/excel", method = RequestMethod.GET)
    public ModelAndView rptReporteDocumentosExcel(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables documentoDatatables = getReporteDocumentosDatatables(request, response);
        return new ModelAndView("ReporteDocumentosExcel", "Data", documentoDatatables.getAaData());
    }
    
    @RequestMapping(value = "/ReporteDocumentos/pdf", method = RequestMethod.GET)
    public ModelAndView rptReporteDocumentosPDF(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables documentoDatatables = getReporteDocumentosDatatables(request, response);
        return new ModelAndView("ReporteDocumentosPDF", "Data", documentoDatatables.getAaData());
    }
}
