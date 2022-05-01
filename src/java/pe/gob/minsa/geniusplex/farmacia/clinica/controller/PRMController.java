/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.PRM;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.PRMService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author stark
 */
@Controller
@RequestMapping("/{idModulo}/prm/*")
public class PRMController {

    @Autowired
    private PRMService prmService;
    @Autowired
    private PacienteService pacienteService;

    private String fechaInicio;
    private String fechaFinal;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Registro PRM");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM", "Acciones"}));
        model.addAttribute("ajaxList", "getPRM");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,id,fecha:date,paciente.nombres,edad,historia,diagnostico,prm,intervencion,ram");
        model.addAttribute("findItem", "getOnePRM");
        return "PRM/Listar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultar(Model model) {
        model.addAttribute("viewTitle", "Consultar PRM");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,id,fecha:date,paciente.nombres,edad,historia,diagnostico,prm,intervencion,ram");
        model.addAttribute("findItem", "");
        return "PRM/Consultar";
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<PRM> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        List<PRM> lista = getConsultData(request, idModulo);
        return lista;
    }

    @RequestMapping(value = "getPRM", method = RequestMethod.GET)
    public @ResponseBody
    List<PRM> getPRM(@PathVariable long idModulo, HttpServletRequest request) {
        String servicio = request.getParameter("servicio");
        if (servicio != null && servicio.length() > 0) {
            List<PRM> lista = prmService.listarPorModuloConParams(idModulo, servicio);
            return lista;
        }
        List<PRM> lista = prmService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<PRM>> insertar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<PRM>> response = new AjaxResponse<List<PRM>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        PRM prm = new PRM();

        getData(request, prm);

        setMetaData(prm, usuario, idModulo);

        boolean result = prmService.actualizar(prm);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Registro.");

        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Registro.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminar(@RequestParam long id) {
        AjaxResponse<PRM> response = new AjaxResponse<PRM>();
        boolean result = prmService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente este registro.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este registro");
        }
        return response;
    }

    @RequestMapping(value = "getOnePRM", method = RequestMethod.GET)
    public @ResponseBody
    PRM getOnePRM(@RequestParam long id) {
        return prmService.obtenerPorId(id);
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<PRM>> modificar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<PRM>> response = new AjaxResponse<List<PRM>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        long idMedida = Long.parseLong(request.getParameter("id"));
        PRM prm = prmService.obtenerPorId(idMedida);
        getData(request, prm);

        setMetaData(prm, usuario, idModulo);
        prm.setUsuarioModificacion(usuario.getIdUsuario());

        boolean result = prmService.actualizar(prm);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Registro.");

        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Registro.");
            response.setHasError(true);
        }

        return response;
    }

    private void setMetaData(PRM prm, Usuario usuario, long idModulo) {
        if (prm.getId() == null || prm.getId() == 0) {
            prm.setUsuarioCreacion(usuario.getIdUsuario());
        }
        prm.setUsuarioModificacion(usuario.getIdUsuario());
        prm.setIdModulo(idModulo);
    }

    private void getData(HttpServletRequest request, PRM prm) throws NumberFormatException {
        String idPaciente = request.getParameter("paciente");
        String historia = request.getParameter("historia");
        String diagnostico = request.getParameter("diagnostico");
        String intervencion = request.getParameter("intervencion");
        String ram = request.getParameter("ram");
        String vPrm = request.getParameter("prm");

        Paciente paciente = pacienteService.obtenerReferencia(idPaciente);
        String servicio = request.getParameter("servicio");
        Integer edad = Integer.parseInt(request.getParameter("edad"));

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            String startDate = request.getParameter("fecha");
            if (startDate != null && startDate.length() > 0) {
                fecha = format.parse(startDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        prm.setFecha(fecha);
        prm.setPaciente(paciente);
        prm.setServicio(servicio);
        prm.setDiagnostico(diagnostico);
        prm.setHistoria(historia);
        prm.setIntervencion(intervencion);
        prm.setRam(ram);
        prm.setPrm(vPrm);
        prm.setEdad(edad);

    }

    private List<PRM> getConsultData(HttpServletRequest request, long idModulo) throws NumberFormatException {
        String idPaciente = request.getParameter("paciente");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            fechaInicio = startDate;
            fechaFinal = endDate;
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(PRMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<PRM> lista = prmService.ConsultaPRM(idModulo, idPaciente, start, end);
        return lista;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PRMPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PRMExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<PRM> lista = prmService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM");
        List<String> contentFields = Arrays.asList("id", "fecha", "paciente:nombres", "edad", "historia", "diagnostico", "prm", "intervencion", "ram");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 5);
        contentColumns.put(2, 15);
        contentColumns.put(3, 15);
        contentColumns.put(4, 15);
        contentColumns.put(5, 10);
        contentColumns.put(6, 10);
        contentColumns.put(7, 15);
        contentColumns.put(8, 10);
        model.addObject("Title", "PRM");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

    private ModelAndView configReportConsulta(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<PRM> lista = getConsulta(idModulo, request);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM");
        List<String> contentFields = Arrays.asList("id", "fecha", "paciente:nombres", "edad", "historia", "diagnostico", "prm", "intervencion", "ram");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 5);
        contentColumns.put(2, 15);
        contentColumns.put(3, 15);
        contentColumns.put(4, 15);
        contentColumns.put(5, 10);
        contentColumns.put(6, 10);
        contentColumns.put(7, 15);
        contentColumns.put(8, 10);
        model.addObject("Title", "Acciones Educativas y/o Preventivas de los Medicamentos");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

    @RequestMapping(value = "reporteConsultaPdf", method = RequestMethod.GET)
    public ModelAndView reporteConsultaPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReportConsulta(request, idModulo, "PRMConsultaPDF");

        return model;
    }

    @RequestMapping(value = "reporteConsultaExcel", method = RequestMethod.GET)
    public ModelAndView reporteConsultaExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReportConsulta(request, idModulo, "PRMConsultaExcel");

        return model;
    }
}
