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
import pe.gob.minsa.geniusplex.farmacia.clinica.services.MedidasService;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedidasEducativas;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author stark
 */
@Controller
@RequestMapping("/{idModulo}/medidas/*")
public class MedidasController {

    @Autowired
    private MedidasService medidasService;
    @Autowired
    private PacienteService pacienteService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Acciones Educativas y/o Preventivas de los Medicamentos");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha","Tema", "Boletin","Diptico","Triptico","Afiches","Otros","Paciente","Acciones"}));
        model.addAttribute("ajaxList", "getMedida");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,id,fecha:date,tema,boleta:yesno,diptico:yesno,triptico:yesno,afiches:yesno,otros:yesno,paciente.nombres");
        model.addAttribute("findItem", "getOneMedidas");
        return "Medidas/Listar";
    }
    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultar(Model model) {
        model.addAttribute("viewTitle", "Acciones Educativas y/o Preventivas de los Medicamentos");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha","Tema", "Boletin","Diptico","Triptico","Afiches","Otros","Paciente"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,id,fecha:date,tema,boleta:yesno,diptico:yesno,triptico:yesno,afiches:yesno,otros:yesno,paciente.nombres");
        model.addAttribute("findItem", "");
        return "Medidas/Consultar";
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<MedidasEducativas> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        List<MedidasEducativas> lista = getConsultData(request, idModulo);
        return lista;
    }

    @RequestMapping(value = "getMedida", method = RequestMethod.GET)
    public @ResponseBody
    List<MedidasEducativas> getMedidas(@PathVariable long idModulo,HttpServletRequest request) {
        String servicio = request.getParameter("servicio");
         if(servicio != null && servicio.length() >0){
            List<MedidasEducativas> lista = medidasService.listarPorModuloConParams(idModulo, servicio);
            return lista;
        }
        List<MedidasEducativas> lista = medidasService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<MedidasEducativas>> insertar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<MedidasEducativas>> response = new AjaxResponse<List<MedidasEducativas>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        MedidasEducativas medidas = new MedidasEducativas();

        getData(request, medidas);

        setMetaData(medidas, usuario, idModulo);

        boolean result = medidasService.actualizar(medidas);
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
        AjaxResponse<MedidasEducativas> response = new AjaxResponse<MedidasEducativas>();
        boolean result = medidasService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente este registro.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este registro");
        }
        return response;
    }

    @RequestMapping(value = "getOneMedidas", method = RequestMethod.GET)
    public @ResponseBody
    MedidasEducativas getOneMedidas(@RequestParam long id) {
        return medidasService.obtenerPorId(id);
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<MedidasEducativas>> modificar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<MedidasEducativas>> response = new AjaxResponse<List<MedidasEducativas>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        long idMedida = Long.parseLong(request.getParameter("id"));
        MedidasEducativas medidas = medidasService.obtenerPorId(idMedida);
        getData(request, medidas);

        setMetaData(medidas, usuario, idModulo);
        medidas.setUsuarioModificacion(usuario.getIdUsuario());

        
        boolean result = medidasService.actualizar(medidas);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Registro.");

        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Registro.");
            response.setHasError(true);
        }

        return response;
    }

    private void setMetaData(MedidasEducativas medidas, Usuario usuario, long idModulo) {
        if (medidas.getId() == null || medidas.getId() == 0) {
            medidas.setUsuarioCreacion(usuario.getIdUsuario());
        }
        medidas.setUsuarioModificacion(usuario.getIdUsuario());
        medidas.setIdModulo(idModulo);
    }

    private void getData(HttpServletRequest request, MedidasEducativas medidas) throws NumberFormatException {
        String idPaciente = request.getParameter("paciente");
        int boleta = Integer.parseInt(request.getParameter("boleta"));
        int diptico = Integer.parseInt(request.getParameter("diptico"));
        int triptico = Integer.parseInt(request.getParameter("triptico"));
        int afiches = Integer.parseInt(request.getParameter("afiches"));
        int otros = Integer.parseInt(request.getParameter("otros"));

        Paciente paciente = pacienteService.obtenerReferencia(idPaciente);
        String servicio = request.getParameter("servicio");
        String tema = request.getParameter("tema");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date fecha = null;
        try {
            String startDate = request.getParameter("fecha");
            if (startDate != null && startDate.length() > 0) {
                fecha = format.parse(startDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        medidas.setFecha(fecha);
        medidas.setPaciente(paciente);
        medidas.setServicio(servicio);
        medidas.setTema(tema);
        medidas.setBoleta(boleta);
        medidas.setDiptico(diptico);
        medidas.setTriptico(triptico);
        medidas.setAfiches(afiches);
        medidas.setOtros(otros);
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "MedidaPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "MedidaExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<MedidasEducativas> lista = medidasService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("No", "Fecha","Tema", "Boletin","Diptico","Triptico","Afiches","Otros","Paciente");
        List<String> contentFields = Arrays.asList("id", "fecha", "tema", "boleta" , "diptico" ,"triptico", "afiches", "otros", "paciente:nombres");
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
    private List<MedidasEducativas> getConsultData(HttpServletRequest request, long idModulo) throws NumberFormatException {
        String idPaciente = request.getParameter("paciente");

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(PRMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<MedidasEducativas> lista = medidasService.consultar(idPaciente, start, end, idModulo);
        return lista;
    }

}
