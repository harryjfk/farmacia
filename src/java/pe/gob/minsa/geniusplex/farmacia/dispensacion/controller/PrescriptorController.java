/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PrescriptorService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/prescriptor/*")
public class PrescriptorController {

    @Autowired
    private PrescriptorService prescriptorService;
    @Autowired
    private PersonalService personalService;

    /**
     * Genera todas las variables necesarias para mostrar la vista de
     * Mantenimiento de Prescriptores
     * <b>viewTitle</b>: titulo de la vista
     * <b>tableHeaders</b>: lista con los nombres de las columnas de la tabla
     * <b>ajaxList</b>: URL para obtener listado via Ajax
     * <b>editUrl</b>: URL para editar elemento via Ajax
     * <b>changeUrl</b>: URL para cambiar el estado del elemento elemento via
     * Ajax
     * <b>removeUrl</b>: URL para eliminar elemento elemento via Ajax
     * <b>tableProperties</b>: String de las propiedades del elemento que van a
     * ser mostradas en la tabla separadas por coma. <b>Nota</b>: la primera
     * propiedad debe ser el identificador del elemento (el cual no sera
     * mostrado, es solo para acciones del CRUD)
     * <b>findItem</b>: URL para encontrar un elemento via Ajax
     *
     * @param model
     * @return vista del listado de Prescriptores
     */
    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPrescriptores(Model model) {
        model.addAttribute("viewTitle", "Mantenimiento de Prescriptores");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Apellidos y nombres", "Acciones"}));
        model.addAttribute("ajaxList", "getPrescriptores");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "idMedico,idMedico,nombrePrescriptor");
        model.addAttribute("findItem", "getPrescriptor");
        return "Prescriptor/listar";
    }

    @RequestMapping(value = "getPrescriptores", method = RequestMethod.GET)
    public @ResponseBody
    List<Prescriptor> getPrescriptores(@PathVariable long idModulo) {
        return prescriptorService.listarModulo(idModulo);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Prescriptor>> insertarMedico(@PathVariable long idModulo,
            @ModelAttribute Prescriptor prescriptor, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        AjaxResponse<List<Prescriptor>> response = new AjaxResponse<List<Prescriptor>>();

        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para realizar esta acci&oacute;n");
            return response;
        }
        prescriptor.setUsuarioCreacion(usuario.getIdUsuario());
        prescriptor.setUsuarioModificacion(usuario.getIdUsuario());
        prescriptor.setIdModulo(idModulo);

        boolean result = prescriptorService.insertar(prescriptor);
        List<Prescriptor> data = prescriptorService.listarModulo(idModulo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Prescriptor.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Prescriptor.");
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarPrescriptor(@RequestParam long id) {
        AjaxResponse<Prescriptor> response = new AjaxResponse<Prescriptor>();
        boolean result = prescriptorService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el Prescriptor.");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al borrar a este Prescriptor");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Prescriptor> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Prescriptor> response = new AjaxResponse<Prescriptor>();
        boolean result = prescriptorService.cambiarEstado(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        Prescriptor prescriptor = prescriptorService.obtenerPorId(id);
        prescriptor.setUsuarioModificacion(usuario.getIdUsuario());
        prescriptorService.actualizar(prescriptor);
        
        if (result) {
            response.addMensaje("Se ha cambiado el estado del Prescriptor");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Prescriptor");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Prescriptor> modificarPrescriptor(@ModelAttribute Prescriptor prescriptor, HttpServletRequest request) {
        AjaxResponse<Prescriptor> response = new AjaxResponse<Prescriptor>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        prescriptor.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = prescriptorService.actualizar(prescriptor);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Prescriptor.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Prescriptor.");
            response.setHasError(!result);
        }
        return response;
    }

    @RequestMapping(value = "getPrescriptor", method = RequestMethod.GET)
    public @ResponseBody
    Prescriptor getPrescriptor(@RequestParam long id) {
        return prescriptorService.obtenerPorId(id);
    }

    /**
     *
     * @return lista de personal
     */
    @RequestMapping(value = "listarPersonal", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<HashMap<String, String>> listarPersonal() {
        HashMap<String, String> names = new java.util.HashMap<String, String>();
        List<Personal> personalSinUsuario = personalService.listarSinUsuario();
        for (Personal p : personalSinUsuario) {
            names.put(p.getIdPersonal(), String.format("%s/%s/%s/%s", p.getIdPersonal(), p.getApellidoPaterno(), p.getApellidoMaterno(), p.getNombre()));
        }
//        for (int i = 0; i < 10; i++) {
//            Personal p = new Personal();
//            p.setApellidoMaterno("Apellido Materno de " + i);
//            p.setApellidoPaterno("Apellido Paterno de " + i);
//            p.setCargo("Cargo de " + i);
//            p.setIdPersonal("000" + i);
//            p.setNombre("Nombre de " + i);
//            names.put(p.getIdPersonal(), String.format("%s/%s/%s/%s", p.getIdPersonal(), p.getApellidoPaterno(), p.getApellidoMaterno(), p.getNombre()));
//        }
        AjaxResponse<HashMap<String, String>> response = new AjaxResponse<HashMap<String, String>>(names);
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Prescriptor> prescriptores = prescriptorService.listarModulo(idModulo);
        return new ModelAndView("PrescriptorPDF", "Data", prescriptores);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Prescriptor> prescriptores = prescriptorService.listarModulo(idModulo);
        return new ModelAndView("PrescriptorExcel", "Data", prescriptores);
    }

}
