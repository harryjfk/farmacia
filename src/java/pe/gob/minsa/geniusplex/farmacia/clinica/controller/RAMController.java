/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.MedicamentoSospechoso;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.OtrosMedicamentos;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.RAM;
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.ReaccionesAdversas;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.MedicamentoSospechososService;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.OtrosMedicamentosService;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.RAMService;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.ReaccionesAdversasService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author stark
 */
@Controller
@RequestMapping("/{idModulo}/ram/*")
public class RAMController {

    @Autowired
    private RAMService ramService;

    @Autowired
    private MedicamentoSospechososService medicamentoService;

    @Autowired
    private ReaccionesAdversasService reaccionesService;

    @Autowired
    private OtrosMedicamentosService otroMedicamentoServicio;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Registro RAM");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "");
        model.addAttribute("findItem", "");
        return "RAM/Procesar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultar(Model model) {
        model.addAttribute("viewTitle", "Consultar RAM");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No", "Fecha", "Paciente", "Edad", "H.C", "DX", "PRM", "Intervencion Farmaceutica", "RAM", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "");
        model.addAttribute("findItem", "");
        return "RAM/Consultar";
    }

    @RequestMapping(value = "guardarDatos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<RAM>> guardarDatos(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<RAM>> response = new AjaxResponse<List<RAM>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        boolean exist = false;
        RAM ram;
        if (!"0".equals(request.getParameter("ram"))) {
            long id = Long.parseLong(request.getParameter("ram"));
            ram = ramService.obtenerPorId(id);
            exist = true;
            ram.setUsuarioModificacion(usuario.getIdUsuario());

        } else {
            ram = new RAM();
            ram.setUsuarioCreacion(usuario.getIdUsuario());
            ram.setUsuarioModificacion(usuario.getIdUsuario());
        }

        getData(request, ram);
        setDate(request, ram);

        boolean result;
        if (exist) {
            result = ramService.actualizar(ram);
        } else {
            result = ramService.insertar(ram);

        }
        ArrayList<RAM> data = new ArrayList<RAM>();
        data.add(ram);
        if (result) {
            response.addMensaje("Se ha guardado el documento.");
            response.setData(data);
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al guardar este documento");
        }

        return response;
    }

    @RequestMapping(value = "insertarMedicamentos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<MedicamentoSospechoso>> insertarMedicamentos(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<MedicamentoSospechoso>> response = new AjaxResponse<List<MedicamentoSospechoso>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerReferencia(idRam);
        List<MedicamentoSospechoso> medicamentos = ram.getMedicamentos();
        MedicamentoSospechoso medicamento = new MedicamentoSospechoso();
        medicamento.setUsuarioCreacion(usuario.getIdUsuario());
        medicamento.setUsuarioModificacion(usuario.getIdUsuario());
        setMedicamentoDates(request, medicamento);

        if (medicamentos.contains(medicamento)) {
            response.addMensaje("Ya existe este medicamento.");
            response.setHasError(true);
            return response;
        }

        medicamentos.add(medicamento);
        ArrayList<RAM> temp = new ArrayList<RAM>();
        temp.add(ram);
        medicamento.setRams(temp);

        boolean result = ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el medicamento.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este medicamento.");
            response.setHasError(true);
        }

        return response;

    }

    @RequestMapping(value = "insertarReaccion", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<ReaccionesAdversas>> insertarReaccion(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<ReaccionesAdversas>> response = new AjaxResponse<List<ReaccionesAdversas>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerReferencia(idRam);
        List<ReaccionesAdversas> reacciones = ram.getReacciones();

        ReaccionesAdversas reaccion = new ReaccionesAdversas();

        setReaccion(request, reaccion);
        reaccion.setUsuarioCreacion(usuario.getIdUsuario());
        reaccion.setUsuarioModificacion(usuario.getIdUsuario());
        if (reacciones.contains(reaccion)) {
            response.addMensaje("Ya existe esta Reacción.");
            response.setHasError(true);
            return response;
        }
        reacciones.add(reaccion);
        ArrayList<RAM> temp = new ArrayList<RAM>();
        temp.add(ram);
        reaccion.setRams(temp);

        boolean result = ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente esta reacción.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar esta reacción.");
            response.setHasError(true);
        }

        return response;

    }

    @RequestMapping(value = "insertarOtro", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<OtrosMedicamentos>> insertarOtro(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<OtrosMedicamentos>> response = new AjaxResponse<List<OtrosMedicamentos>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerReferencia(idRam);
        List<OtrosMedicamentos> otros = ram.getOtros();
        OtrosMedicamentos medicamento = new OtrosMedicamentos();

        setOtros(request, medicamento);
        medicamento.setUsuarioCreacion(usuario.getIdUsuario());
        medicamento.setUsuarioModificacion(usuario.getIdUsuario());
        if (otros.contains(medicamento)) {
            response.addMensaje("Ya existe este Medicamento.");
            response.setHasError(true);
            return response;
        }
        otros.add(medicamento);
        ArrayList<RAM> temp = new ArrayList<RAM>();
        temp.add(ram);
        medicamento.setRams(temp);
        boolean result = ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el medicamento.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este medicamento.");
            response.setHasError(true);
        }

        return response;

    }

    private void setOtros(HttpServletRequest request, OtrosMedicamentos medicamento) {
        String nombre = request.getParameter("nombre");
        String dosis = request.getParameter("dosis");
        String via = request.getParameter("via");
        String indicacion = request.getParameter("indicacion");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("fechaInicio");
            String endDate = request.getParameter("fechaFinal");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(HFTController.class.getName()).log(Level.SEVERE, null, ex);
        }

        medicamento.setDosis(dosis);
        medicamento.setNombre(nombre);
        medicamento.setVia(via);
        medicamento.setFechaInicio(start);
        medicamento.setFechaFinal(end);
        medicamento.setIndicacion(indicacion);
    }

    private void setReaccion(HttpServletRequest request, ReaccionesAdversas reaccion) {
        String r = request.getParameter("reaccion");
        String evolucion = request.getParameter("evolucion");

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("fechaInicio");
            String endDate = request.getParameter("fechaFinal");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(HFTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        reaccion.setFechaInicio(start);
        reaccion.setFechaFinal(end);
        reaccion.setReaccion(r);
        reaccion.setEvolucion(evolucion);
    }

    @RequestMapping(value = "getMedicamentos")
    public @ResponseBody
    List<MedicamentoSospechoso> getMedicamentos(@PathVariable long idModulo, HttpServletRequest request) {
        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerPorId(idRam);

        return ram.getMedicamentos();
    }

    @RequestMapping(value = "getReacciones")
    public @ResponseBody
    List<ReaccionesAdversas> getReacciones(@PathVariable long idModulo, HttpServletRequest request) {
        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerPorId(idRam);

        return ram.getReacciones();
    }

    @RequestMapping(value = "getOtros")
    public @ResponseBody
    List<OtrosMedicamentos> getOtros(@PathVariable long idModulo, HttpServletRequest request) {
        long idRam = Long.parseLong(request.getParameter("ram"));
        RAM ram = ramService.obtenerPorId(idRam);

        return ram.getOtros();
    }

    @RequestMapping(value = "eliminarMedicamento", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarCliente(@RequestParam long id) {
        AjaxResponse<MedicamentoSospechoso> response = new AjaxResponse<MedicamentoSospechoso>();
        MedicamentoSospechoso medicamento = medicamentoService.obtenerPorId(id);
        RAM ram = medicamento.getRams().get(0);
        boolean result = medicamentoService.eliminar(id);
        ram.getMedicamentos().remove(medicamento);
        ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el medicamento.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este medicamento");
        }
        return response;
    }

    @RequestMapping(value = "eliminarRam", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarRam(@RequestParam long id) {
        AjaxResponse<RAM> response = new AjaxResponse<RAM>();
        boolean result = ramService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el registro RAM.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este registro.");
        }
        return response;
    }

    @RequestMapping(value = "eliminarReaccion", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarReaccion(@RequestParam long id) {
        AjaxResponse<MedicamentoSospechoso> response = new AjaxResponse<MedicamentoSospechoso>();
        ReaccionesAdversas reaccion = reaccionesService.obtenerPorId(id);
        List<RAM> rams = reaccion.getRams();
        boolean result = reaccionesService.eliminar(id);
        RAM ram = rams.get(0);
        ram.getReacciones().remove(reaccion);
        ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente la reaccion.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar esta reaccion");
        }
        return response;
    }

    @RequestMapping(value = "eliminarOtro", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarOtro(@RequestParam long id) {
        AjaxResponse<OtrosMedicamentos> response = new AjaxResponse<OtrosMedicamentos>();
        OtrosMedicamentos otro = otroMedicamentoServicio.obtenerPorId(id);
        RAM ram = otro.getRams().get(0);
        boolean result = otroMedicamentoServicio.eliminar(id);

        ram.getOtros().remove(otro);
        ramService.actualizar(ram);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el medicamento.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este medicamento");
        }
        return response;
    }

    private void setMedicamentoDates(HttpServletRequest request, MedicamentoSospechoso medicamento) {
        String nombre = request.getParameter("nombre");
        String laboratorio = request.getParameter("laboratorio");
        String lote = request.getParameter("lote");
        String dosis = request.getParameter("dosis");
        String via = request.getParameter("via");
        String motivo = request.getParameter("motivo");

        medicamento.setNombre(nombre);
        medicamento.setLaboratorio(laboratorio);
        medicamento.setLote(lote);
        medicamento.setDosis(dosis);
        medicamento.setVia(via);
        medicamento.setMotivo(motivo);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("fechaInicio");
            String endDate = request.getParameter("fechaFinal");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(HFTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        medicamento.setFechaInicio(start);
        medicamento.setFechaFinal(end);
    }

    @RequestMapping(value = "getRAM")
    public @ResponseBody
    RAM getRam(@PathVariable long idModulo, HttpServletRequest request) {
        String historia = request.getParameter("historia");
        List<RAM> listar = ramService.listar(historia);
        if (listar != null && listar.size() > 0) {
            return listar.get(0);
        }
        return null;
    }

    private void getData(HttpServletRequest request, RAM ram) {
        String paciente = request.getParameter("paciente");
        String edad = request.getParameter("edad");
        String sexo = request.getParameter("sexo");
        String peso = request.getParameter("peso");
        String historia = request.getParameter("historia");
        String establecimiento = request.getParameter("establecimiento");
        String categoria = request.getParameter("categoria");
        String medico = request.getParameter("medico");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String observaciones = request.getParameter("observaciones");

        ram.setPaciente(paciente);
        ram.setEdad(edad);
        ram.setSexo(sexo);
        ram.setPeso(peso);
        ram.setHistoria(historia);
        ram.setEstablecimiento(establecimiento);
        ram.setCategoria(categoria);
        ram.setMedico(medico);
        ram.setDireccion(direccion);
        ram.setTelefono(telefono);
        ram.setObservaciones(observaciones);
    }

    private void setDate(HttpServletRequest request, RAM ram) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        try {
            String startDate = request.getParameter("fecha");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(RAMController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("fecha" + start);
        ram.setFecha(start);
    }

   @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        long ram = Long.parseLong(request.getParameter("ram"));
                
        RAM lista = ramService.obtenerPorId(ram);
        return new ModelAndView("RAMPDF", "Data", lista);
    }

   @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        long ram = Long.parseLong(request.getParameter("ram"));
                
        RAM lista = ramService.obtenerPorId(ram);
        return new ModelAndView("RAMExcel", "Data", lista);
    }
    
}
