package pe.gob.minsa.farmacia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Medicamento;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.farmacia.domain.Solicitud;
import pe.gob.minsa.farmacia.domain.SolicitudDetalle;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.services.impl.SolicitudDetalleService;
import pe.gob.minsa.farmacia.services.impl.SolicitudService;
import pe.gob.minsa.farmacia.services.impl.TipoAccionService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
@RequestMapping({"/FormatoSolicitud", "/FormatoAprobacion", "/RegistroSolicitud", "/EvaluacionSolicitud"})
public class SolicitudController {

    @Autowired
    SolicitudService solicitudService;
    @Autowired
    ProductoService productoService;
    @Autowired
    PersonalService personalService;
    @Autowired
    SolicitudDetalleService solicitudDetalleService;

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

    private ManagerDatatables getSolicitudDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Solicitud> solicitudes = solicitudService.listar();
        String establecimiento = request.getParameter("establecimiento")!=null?String.valueOf(request.getParameter("establecimiento")):"";
        String institucion = request.getParameter("institucion")!=null?String.valueOf(request.getParameter("institucion")):"";
        String medico = request.getParameter("medico")!=null?String.valueOf(request.getParameter("medico")):"";
        
        managerDatatables.setiTotalRecords(solicitudes.size());

        for (int i = 0; i <= solicitudes.size() - 1; ++i) {
            Solicitud d = solicitudes.get(i);
            if (d.getMedico().toLowerCase().contains(medico.toLowerCase()) && d.getEstablecimiento().toLowerCase().contains(establecimiento.toLowerCase())
                    && d.getInstitucion().toLowerCase().contains(institucion.toLowerCase())) {
            } else {
                solicitudes.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(solicitudes, new Comparator<Solicitud>() {
            @Override
            public int compare(Solicitud o1, Solicitud o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((o1.getIdSolicitud() == o2.getIdSolicitud()) ? 0 : 1) * sortDirection;
                    case 1:
                        return o1.getMedico().toLowerCase().compareTo(o2.getMedico().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getEstablecimiento().toLowerCase().compareTo(o2.getEstablecimiento().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getInstitucion().toLowerCase().compareTo(o2.getInstitucion().toLowerCase()) * sortDirection;
                    case 4:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(solicitudes.size());

        if (solicitudes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, solicitudes.size());
        } else {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(solicitudes);

        return managerDatatables;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarSolicitud(HttpServletRequest request) {
        
        ModelMap modelMap = new ModelMap();
        modelMap.put("path", request.getRequestURI().replace(request.getContextPath(), ""));
        
        return new ModelAndView("Solicitud", modelMap);
    }
    
    @RequestMapping(value = "/solicitudesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerSolicitudesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSolicitudDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarSolicitud(HttpServletRequest request) {
        List<Personal> medicos = personalService.listarMedico();
        List<ProductoComp> productos = productoService.listarMedicamento();
        
        ModelMap model = new ModelMap();
        model.put("medicos", medicos);
        model.put("productos", productos);
        model.put("path", "/" + request.getRequestURI().replace(request.getContextPath(), "").split("/")[1]);

        return new ModelAndView("Solicitud/registrar", model);
    }
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public String registrarSolicitudPOST(@RequestParam("fileDocumento") MultipartFile fileDocumento, @RequestParam("jsonForm") String jsonForm) {
        String reponse = "";
        ObjectMapper mapper = new ObjectMapper();

        try {

            Solicitud solicitud = mapper.readValue(jsonForm, Solicitud.class);

            if (!fileDocumento.isEmpty()) {

                if (fileDocumento.getSize() > tamanioDocumento) {
                    throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
                }

                int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                String extension = fileDocumento.getOriginalFilename().substring(posicionIndex);
                solicitud.setExtension(extension);
            } else {
                solicitud.setExtension(null);
            }

            Integer id = solicitudService.insertarSolicitud(solicitud);

            if (!fileDocumento.isEmpty()) {

                byte[] bytes = fileDocumento.getBytes();

                File directorioArchivos = new File(context.getRealPath(rutaDocumentos));
                if (!directorioArchivos.exists()) {
                    directorioArchivos.mkdirs();
                }

                File serverFile = new File(directorioArchivos.getAbsolutePath() + File.separator + id + solicitud.getExtension());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            }

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
            jsonResponse.setPaginaRedireccion("modificar?idSolicitud="+String.valueOf(id));

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
    
    @RequestMapping(value = "/modificar", method = RequestMethod.GET)
    public ModelAndView modificarSolicitud(@RequestParam int idSolicitud, HttpServletRequest request) {

        try {
            Solicitud solicitud = solicitudService.obtenerPorId(idSolicitud);
            List<Personal> medicos = personalService.listarMedico();
            List<ProductoComp> productos = productoService.listarMedicamento();
            
            ModelMap model = new ModelMap();
            model.put("medicos", medicos);
            Personal personal = personalService.obtenerPorId(solicitud.getIdMedico());
            model.put("personal", personal);
            model.put("solicitud", solicitud);        
            model.put("productos", productos);
            model.put("path", "/" + request.getRequestURI().replace(request.getContextPath(), "").split("/")[1]);

            return new ModelAndView("Solicitud/modificar", model);
        } catch (BusinessException e) {
            //REGRESAR
            return new ModelAndView("redirect:error", "errores", e.getMensajesError());
        }
    }
    
    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public String modificarSolicitudPOST(@RequestParam("fileDocumento") MultipartFile fileDocumento, @RequestParam("jsonForm") String jsonForm, @RequestParam("DeleteFile") boolean DeleteFile) {
        String reponse = "";
        ObjectMapper mapper = new ObjectMapper();

        try {

            Solicitud solicitud = mapper.readValue(jsonForm, Solicitud.class);

            try {
                
                Solicitud solicitudTemp = solicitudService.obtenerPorId(solicitud.getIdSolicitud());
                solicitud.setExtension(solicitudTemp.getExtension());

                if (!fileDocumento.isEmpty()) {

                    if (fileDocumento.getSize() > tamanioDocumento) {
                        throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
                    }

                    int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                    String extension = fileDocumento.getOriginalFilename().substring(posicionIndex);
                    solicitud.setExtension(extension);
                } else {
                    if (DeleteFile) {
                        solicitud.setExtension(null);
                    }
                }

                solicitudService.actualizar(solicitud);

                File directorioArchivos = new File(context.getRealPath(rutaDocumentos));
                
                if (!directorioArchivos.exists()) {
                    directorioArchivos.mkdirs();
                }
                
                if (DeleteFile) {
                    File documentoArchivo = new File(directorioArchivos.getAbsolutePath() + File.separator + solicitudTemp.getIdSolicitud() + solicitudTemp.getExtension());
                    
                    if(documentoArchivo.exists()){
                        documentoArchivo.delete();
                    }                    
                }

                if (!fileDocumento.isEmpty()) {
                    File documentoArchivo = new File(directorioArchivos.getAbsolutePath() + File.separator + solicitud.getIdSolicitud() + solicitud.getExtension());

                    byte[] bytes = fileDocumento.getBytes();

                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(documentoArchivo));
                    stream.write(bytes);
                    stream.close();
                }

                jsonResponse = new JsonResponse();
                jsonResponse.respuestaModificar();
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
    
    @RequestMapping(value = "/descargar/{id}", method = RequestMethod.GET)
    public ModelAndView descargarSolicitud(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

        try {
            Solicitud solicitud = new Solicitud();

            try {
                solicitud = solicitudService.obtenerPorId(id);
            } catch (BusinessException ex) {
                //REGRESAR
            }
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + solicitud.getIdSolicitud() + solicitud.getExtension() + "\"");

            File directorioArchivos = new File(context.getRealPath(rutaDocumentos));

            InputStream is = new FileInputStream(directorioArchivos.getAbsolutePath() + File.separator + solicitud.getIdSolicitud() + solicitud.getExtension());
            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (IOException ex) {

        }

        return null;
    }
    
    @RequestMapping(value = "/descargarDet", method = RequestMethod.GET)
    public ModelAndView descargarSolicitudDet(@RequestParam int idSolicitud, @RequestParam int idSolicitudDet, @RequestParam int tipoMedicamento, HttpServletRequest request, HttpServletResponse response) {
        try {
            SolicitudDetalle solicitud = new SolicitudDetalle();

            try {
                solicitud = solicitudDetalleService.obtenerPorId(idSolicitudDet,idSolicitud,tipoMedicamento);
            } catch (BusinessException ex) {
                //REGRESAR
            }
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + solicitud.getIdSolicitud() + "_" + solicitud.getIdSolicitudDetalle() + solicitud.getExtension() + "\"");

            File directorioArchivos = new File(context.getRealPath(rutaDocumentos));

            InputStream is = new FileInputStream(directorioArchivos.getAbsolutePath() + File.separator + solicitud.getIdSolicitud() + "_" + solicitud.getIdSolicitudDetalle() + solicitud.getExtension());
            IOUtils.copy(is, response.getOutputStream());

            response.flushBuffer();
        } catch (IOException ex) {

        }

        return null;
    }

    @RequestMapping(value = "/solicitudJSON", method = RequestMethod.GET)
    @ResponseBody
    public Solicitud obtenerSolicitudJSON(@RequestParam int id) {
        Solicitud solicitud = new Solicitud();

        try {
            solicitud = solicitudService.obtenerPorId(id);
        } catch (BusinessException e){ 
            //REGRESAR
        }

        return solicitud;
    }
    
    @RequestMapping(value = "/medicoJSON", method = RequestMethod.GET)
    @ResponseBody
    public Personal obtenerMedicoJSON(@RequestParam String id) {
        Personal medico = new Personal();

        try {
            medico = personalService.obtenerPorId(id);
        } catch (BusinessException e){ 
            //REGRESAR
        }

        return medico;
    }
    
    @RequestMapping(value = "/productoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ProductoComp obtenerProductoJSON(@RequestParam String id) {
        ProductoComp producto = new ProductoComp();

        try {
            producto = productoService.obtenerProductoCompPorId(Integer.valueOf(id).intValue());
        } catch (BusinessException e){ 
            //REGRESAR
        }

        return producto;
    }
    
    @RequestMapping(value = "/productoSolicitudJSON", method = RequestMethod.GET)
    @ResponseBody
    public ProductoComp obtenerProductoJSON(@RequestParam String id, @RequestParam String idSolicitud) {
        ProductoComp producto = new ProductoComp();

        try {
            producto = productoService.obtenerProductoCompPorIdPorSolicitud(Integer.valueOf(id).intValue(), Integer.valueOf(idSolicitud).intValue());
        } catch (BusinessException e){ 
            //REGRESAR
        }

        return producto;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            solicitudService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarSolicitud(@PathVariable int id) {
        try {
            solicitudService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/eliminarDet/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarSolicitudDetalle(@PathVariable int id) {
        try {
            solicitudDetalleService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/registrarDetMed", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarMedicamento(@ModelAttribute Medicamento medicamento) {
        
        if(medicamento.getIdSol()==null){
            jsonResponse = new JsonResponse(false, Arrays.asList("Es necesario registrar la solicitud"));
        }else{
            SolicitudDetalle solicitudDet = new SolicitudDetalle();
            solicitudDet.setIdSolicitud(Integer.valueOf(medicamento.getIdSol()).intValue());
            solicitudDet.setIdProducto(Integer.valueOf(medicamento.getIdProducto()).intValue());
            
            String concentracion = medicamento.getConcentracion()!=null?(!medicamento.getConcentracion().equals("")?medicamento.getConcentracion():""):"";
            String forma = medicamento.getForma()!=null?(!medicamento.getForma().equals("")?medicamento.getForma():""):"";
            String dosis = medicamento.getDosis()!=null?(!medicamento.getDosis().equals("")?medicamento.getDosis():"0"):"0";
            String via = medicamento.getVia()!=null?(!medicamento.getVia().equals("")?medicamento.getVia():""):"";
            String duracion = medicamento.getDuracion()!=null?(!medicamento.getDuracion().equals("")?medicamento.getDuracion():"0"):"0";
            String costo = medicamento.getCosto()!=null?(!medicamento.getCosto().equals("")?medicamento.getCosto():"0"):"0";
            String costotrat = medicamento.getCostotrat()!=null?(!medicamento.getCostotrat().equals("")?medicamento.getCostotrat():"0"):"0";
            solicitudDet.setDescripcion(concentracion+"~"+forma+"~"+via+"~"+dosis+"~"+costo+"~"+duracion+"~"+costotrat);
            solicitudDet.setTipoMedicamento(1);
            solicitudDet.setActivo(1);
            try {
                    Integer id = solicitudDetalleService.insertarSolicitudDetalle(solicitudDet);
                  
                    jsonResponse = new JsonResponse();
                    jsonResponse.respuestaInsertar();
                    jsonResponse.setPaginaRedireccion("modificar?idSolicitud="+medicamento.getIdSol());

            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/registrarDetMedAlt", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarMedicamentoAlt(@ModelAttribute Medicamento medicamento) {
        
        if(medicamento.getIdSol()==null){
            jsonResponse = new JsonResponse(false, Arrays.asList("Es necesario registrar la solicitud"));
        }else{
            SolicitudDetalle solicitudDet = new SolicitudDetalle();
            solicitudDet.setIdSolicitud(Integer.valueOf(medicamento.getIdSol()).intValue());
            solicitudDet.setIdProducto(Integer.valueOf(medicamento.getIdProducto()).intValue());
            
            String concentracion = medicamento.getConcentracion()!=null?(!medicamento.getConcentracion().equals("")?medicamento.getConcentracion():""):"";
            String forma = medicamento.getForma()!=null?(!medicamento.getForma().equals("")?medicamento.getForma():""):"";
            String dosis = medicamento.getDosis()!=null?(!medicamento.getDosis().equals("")?medicamento.getDosis():"0"):"0";
            String via = medicamento.getVia()!=null?(!medicamento.getVia().equals("")?medicamento.getVia():""):"";
            String duracion = medicamento.getDuracion()!=null?(!medicamento.getDuracion().equals("")?medicamento.getDuracion():"0"):"0";
            String costo = medicamento.getCosto()!=null?(!medicamento.getCosto().equals("")?medicamento.getCosto():"0"):"0";
            String costotrat = medicamento.getCostotrat()!=null?(!medicamento.getCostotrat().equals("")?medicamento.getCostotrat():"0"):"0";
            solicitudDet.setDescripcion(concentracion+"~"+forma+"~"+via+"~"+dosis+"~"+costo+"~"+duracion+"~"+costotrat);
            solicitudDet.setTipoMedicamento(2);
            solicitudDet.setActivo(1);
            try {
                Integer id = solicitudDetalleService.insertarSolicitudDetalle(solicitudDet);
                jsonResponse = new JsonResponse();
                jsonResponse.respuestaInsertar();
                jsonResponse.setPaginaRedireccion("modificar?idSolicitud="+medicamento.getIdSol());

            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/aprobarDetMed", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse aprobarMedicamento(@RequestParam("fileDocumentoAprobar") MultipartFile fileDocumento, @RequestParam("jsonFormApr") String jsonForm) {
        String reponse = "";
        ObjectMapper mapper = new ObjectMapper();

            try {
                    SolicitudDetalle solicitudDet = mapper.readValue(jsonForm, SolicitudDetalle.class);
                    System.out.println("solicitudDet: "+solicitudDet);
                    System.out.println("jsonForm: "+jsonForm);
                    System.out.println("fileDocumento: "+fileDocumento);
                    if(solicitudDet.getMotivo().isEmpty()){
                        jsonResponse = new JsonResponse(false, Arrays.asList("Es necesario ingresar un motivo"));
                    }else{
                    if (!fileDocumento.isEmpty()) {

                        if (fileDocumento.getSize() > tamanioDocumento) {
                            throw new BusinessException(Arrays.asList("Ha superado el límite tamaño permitido para el documento"));
                        }

                        int posicionIndex = fileDocumento.getOriginalFilename().lastIndexOf(".");
                        String extension = fileDocumento.getOriginalFilename().substring(posicionIndex);
                        solicitudDet.setExtension(extension);
                    }

                    solicitudDetalleService.aprobar(solicitudDet);

                    if (!fileDocumento.isEmpty()) {

                        byte[] bytes = fileDocumento.getBytes();

                        File directorioArchivos = new File(context.getRealPath(rutaDocumentos));
                        if (!directorioArchivos.exists()) {
                            directorioArchivos.mkdirs();
                        }

                        File serverFile = new File(directorioArchivos.getAbsolutePath() + File.separator + solicitudDet.getIdSolicitud() + "_" + solicitudDet.getIdSolicitudDetalle() + solicitudDet.getExtension());
                        BufferedOutputStream stream = new BufferedOutputStream(
                                new FileOutputStream(serverFile));
                        stream.write(bytes);
                        stream.close();
                    }
                jsonResponse = new JsonResponse();
                jsonResponse.respuestaInsertar();
                jsonResponse.setPaginaRedireccion("modificar?idSolicitud="+solicitudDet.getIdSolicitud());
              }
            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }catch (IOException ex) {
                jsonResponse = new JsonResponse(false, Arrays.asList(ex.getMessage()));
            }

        return jsonResponse;
    }    
    
    @RequestMapping(value = "/listarMed", method = RequestMethod.GET)
    public ModelAndView listarSolicitudDet() {
        return new ModelAndView("Solicitud/listarMed");
    }

    @RequestMapping(value = "/solicitudesDetJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerSolicitudesDetJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSolicitudDetDatatables(request, response);
    }
    
    private ManagerDatatables getSolicitudDetDatatables(HttpServletRequest request, HttpServletResponse response) {
        
        String idSolicitud = request.getParameter("idSolicitud");
        String tipoMedicamento = request.getParameter("tipoMedicamento");
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<SolicitudDetalle> solicitudes = solicitudDetalleService.listar(Integer.valueOf(idSolicitud).intValue(),Integer.valueOf(tipoMedicamento).intValue());
        
        managerDatatables.setiTotalRecords(solicitudes.size());

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;
        
        Collections.sort(solicitudes, new Comparator<SolicitudDetalle>() {
            @Override
            public int compare(SolicitudDetalle o1, SolicitudDetalle o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((o1.getIdSolicitudDetalle() == o2.getIdSolicitudDetalle()) ? 0 : 1) * sortDirection;
                    case 1:
                        return o1.getDescripcionProd().toLowerCase().compareTo(o2.getDescripcionProd().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(solicitudes.size());

        if (solicitudes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, solicitudes.size());
        } else {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }
        
        List<Medicamento> lista = new ArrayList<Medicamento>();
        for (int i = 0; i <= solicitudes.size() - 1; ++i) {
            SolicitudDetalle sd = solicitudes.get(i);
            Medicamento m = new Medicamento();
            m.setIdSol(String.valueOf(sd.getIdSolicitud()));
            m.setIdSolicitudDetalle(String.valueOf(sd.getIdSolicitudDetalle()));
            m.setIdProducto(String.valueOf(sd.getIdProducto()));
            m.setNomProducto(sd.getDescripcionProd());
            m.setNomFarmaceutica(sd.getDescripcionFarm());
            m.setAprobadoTexto(sd.getAprobado()>0?"Aprobado":"No Aprobado");
            String[] des = sd.getDescripcion().split("~");
            m.setConcentracion(des[0]!=null?des[0].toString().trim():"");
            m.setVia(des[2]!=null?des[2].toString().trim():"");
            m.setDosis(des[3]!=null?des[3].toString().trim():"");
            m.setCosto(des[4]!=null?des[4].toString().trim():"");
            m.setDuracion(des[5]!=null?des[5].toString().trim():"");
            m.setCostotrat(des[6]!=null?des[6].toString().trim():"");
            m.setExtension(sd.getExtension());
            lista.add(m);
        }
        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(lista);

        return managerDatatables;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables solicitudDatatables = getSolicitudDatatables(request, response);

        return new ModelAndView("SolicitudPDF", "Data", solicitudDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables solicitudDatatables = getSolicitudDatatables(request, response);

        return new ModelAndView("SolicitudExcel", "Data", solicitudDatatables.getAaData());
    }

}
