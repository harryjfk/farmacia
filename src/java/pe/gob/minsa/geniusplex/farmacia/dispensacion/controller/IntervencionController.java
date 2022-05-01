/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Intervencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.IntervProductoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.IntervencionService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/intervencion/{tipo}/*")
public class IntervencionController {

    @Autowired
    IntervencionService intervencionService;
    @Autowired
    private IntervProductoService intervProductoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarClientes(Model model, @PathVariable String tipo) {
        model.addAttribute("viewTitle", "Mantenimiento de Intervenciones");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"C&oacute;digo", "Descripci&oacute;n del Medicamento/Material", "Cantidad", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "producto.idProducto,producto.idProducto,producto.descripcion,cantidad");
        model.addAttribute("findItem", "");
        model.addAttribute("type", tipo);
        return "Intervencion/listar";
    }

    @RequestMapping(value = "consulta", method = RequestMethod.GET)
    public String consultarIntervenciones(Model model, @PathVariable String tipo) {
        model.addAttribute("viewTitle", "Consulta de Intervenciones");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"C&oacute;digo", "Descripci&oacute;n del Medicamento/Material", "Cantidad"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "cantidad,producto.descripcion,cantidad");
        model.addAttribute("findItem", "");
        model.addAttribute("type", tipo);
        return "Intervencion/consulta";
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<Intervencion> getConsulta(HttpServletRequest request, @PathVariable("tipo") String tipo) {
        List<Intervencion> lista = consultarIntervencion(request, tipo);

        return lista;
    }

    private List<Intervencion> consultarIntervencion(HttpServletRequest request, String tipo) throws NumberFormatException {

        Long medico = null;
        String med = request.getParameter("medico");
        if (med != null && med.length() > 0 && !med.equals("0")) {
            medico = Long.parseLong(request.getParameter("medico"));
        }
        final boolean hasPaciente = request.getParameter("paciente") != null && request.getParameter("paciente").length() > 0 && !request.getParameter("paciente").equals("0");
        String paciente = hasPaciente ? request.getParameter("paciente") : null;
        Integer atendida = null, programada = null;
        String especialidad = request.getParameter("especialidad");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        try {
            String startDate = request.getParameter("date");
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
        } catch (ParseException ex) {
            Logger.getLogger(IntervencionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ("atendida".equals(tipo)) {
            boolean esAtendida = Boolean.parseBoolean(request.getParameter("atendida"));
            if (esAtendida) {
                atendida = 1;
            } else {
                atendida = 0;
            }
        } else if ("programada".equals(tipo)) {
            boolean esProgramada = Boolean.parseBoolean(request.getParameter("programada"));
            if (esProgramada) {
                programada = 1;
            } else {
                programada = 0;
            }
        }
        List<Intervencion> lista = intervencionService.ConsultaIntervencion(medico, paciente, atendida, programada, especialidad, start);
        return lista;
    }

    @RequestMapping(value = "getProductos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Intervencion> getProductos(@PathVariable("tipo") String tipo, @RequestBody Intervencion intervencion, HttpServletRequest request) {

        AjaxResponse<Intervencion> response = new AjaxResponse<Intervencion>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        Intervencion tmpInterv = obtener(intervencion, tipo);
        if (tmpInterv != null) {
            intervencion = tmpInterv;
            intervencion.setUsuarioModificacion(usuario.getIdUsuario());
        } else {
//            intervencion.setActivo(1);
            intervencion.setId(0);
            intervencion.setIntervProductos(new ArrayList<IntervProducto>());
            intervencion.setUsuarioCreacion(usuario.getIdUsuario());
//            intervencionService.guardarCambios(intervencion);
            
        }
        response.setData(intervencion);
        return response;
    }

    @RequestMapping(value = "guardarCambios", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Intervencion> guardarCambios(@PathVariable("tipo") String tipo, @RequestBody Intervencion intervencion,
            HttpServletRequest request) {

        AjaxResponse<Intervencion> response = new AjaxResponse<Intervencion>();

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }
        intervencion.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = intervencionService.guardarCambios(intervencion);

        if (!result) {
            response.setHasError(result);
            response.addMensaje("Ha ocurrido un error al guardar los cambios.");
        } else {
            response.setData(intervencion);
            response.addMensaje("Se han guardado los cambios correctamente.");
        }
        return response;
    }

    private Intervencion obtener(Intervencion intervencion, String tipo) {
        Intervencion tmp;
        int type = 0;
        if (tipo.equals("programada")) {
            type = 1;
        } else if (tipo.equals("atendida")) {
            type = 2;
        }
        switch (type) {
            case 0:  //especialidad
                tmp = intervencionService.obtenerPorEspecialidad(intervencion);
                break;
            case 1://programada
                tmp = intervencionService.obtenerPorProgramada(intervencion);
                break;
            case 2: //atendida
                tmp = intervencionService.obtenerPorAtendida(intervencion);
                break;
            default:
                tmp = null;
        }
        return tmp;
    }

    @RequestMapping(value = "prepararReporte", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse prepararReporte(HttpServletRequest request, @RequestBody FilterData fData) {
        HttpSession session = request.getSession();
        session.setAttribute("fData", fData);
        AjaxResponse response = new AjaxResponse();
        response.setHasError(false);
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response) {
        List<IntervProducto> list;
        Intervencion intervencion = null;
        try {
            HttpSession session = request.getSession();
            FilterData fData = (FilterData) session.getAttribute("fData");
            if (fData != null) {
                long id = Long.parseLong(fData.getParams().get("intervencion:id").toString());
                if (id == 0) {
                    return new ModelAndView("IntervencionPDF", "Data", null);
                }
                intervencion = intervencionService.obtenerPorId(id);
                list = intervProductoService.dynamicQuery(fData);
                intervencion.setIntervProductos(list);
            } else {
                return new ModelAndView("IntervencionPDF", "Data", null);
            }

        } catch (NoSuchFieldException ex) {
            Logger.getLogger(IntervencionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(IntervencionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ModelAndView("IntervencionPDF", "Data", intervencion);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response) {
        List<IntervProducto> list;
        Intervencion intervencion = null;
        try {
            HttpSession session = request.getSession();
            FilterData fData = (FilterData) session.getAttribute("fData");
            if (fData != null) {
                long id = Long.parseLong(fData.getParams().get("intervencion:id").toString());
                if (id == 0) {
                    return new ModelAndView("IntervencionPDF", "Data", null);
                }
                intervencion = intervencionService.obtenerPorId(id);
                list = intervProductoService.dynamicQuery(fData);
                intervencion.setIntervProductos(list);
            } else {
                return new ModelAndView("IntervencionPDF", "Data", null);
            }

        } catch (NoSuchFieldException ex) {
            Logger.getLogger(IntervencionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(IntervencionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ModelAndView("IntervencionExcel", "Data", intervencion);
    }

    @RequestMapping(value = "reporteConsultaPdf", method = RequestMethod.GET)
    public ModelAndView reporteConsultaPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable("tipo") String tipo) {
        ModelAndView model = configConsultaReport(request, "IntervencionConsultaPDF", tipo);

        return model;
    }

    @RequestMapping(value = "reporteConsultaExcel", method = RequestMethod.GET)
    public ModelAndView reporteConsultaExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable("tipo") String tipo) {
        ModelAndView model = configConsultaReport(request, "IntervencionConsultaExcel", tipo);

        return model;
    }

    private ModelAndView configConsultaReport(HttpServletRequest request, String viewName, String tipo) throws NumberFormatException {

        List<Intervencion> lista = this.consultarIntervencion(request, tipo);

        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList(new String[]{"Código", "Descripción del Medicamento/Material", "Cantidad"});
        List<String> contentFields = Arrays.asList("producto:idProducto", "producto:descripcion", "cantidad");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 20);
        contentColumns.put(1, 60);
        contentColumns.put(2, 20);
        String titulo = "Reporte de Intervención ";
        if (tipo.equalsIgnoreCase("atendida")) {
            titulo += "Atendida";
        }
        if (tipo.equalsIgnoreCase("programada")) {
            titulo += "Programada";
        }
        if (tipo.equalsIgnoreCase("especialidad")) {
            titulo += "por Especialidad";
        }
        model.addObject("Title", titulo);
        model.addObject("ContentData", lista.get(0).getIntervProductos());
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        if (!lista.isEmpty()) {
            Intervencion interv = lista.get(0);
            for (Map.Entry<String, String[]> entrySet : request.getParameterMap().entrySet()) {
                String key = entrySet.getKey();
                String[] value = entrySet.getValue();
                if (key.equalsIgnoreCase("paciente") && interv.getPaciente() != null) {
                    headerData.put("Paciente", interv.getPaciente().getNombres());
                }
                if (key.equalsIgnoreCase("medico") && interv.getMedico() != null) {
                    long idPresc = Long.parseLong(value[0]);
                    if (idPresc > 0) {
                        headerData.put("Médico", interv.getMedico().getNombrePrescriptor());
                    }
                }
                if (key.equalsIgnoreCase("date") && (value[0] != null && value[0].length() > 0)) {
                    headerData.put("Fecha de Intervención", value[0]);
                }
                if (key.equalsIgnoreCase("especialidad") && interv.getEspecialidad() != null && interv.getEspecialidad().length() > 0) {
                    headerData.put("Especialidad", interv.getEspecialidad());
                }
            }
        }

        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }
}
