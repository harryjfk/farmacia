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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PuntoDeVenta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ComponenteService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/componente/*")
public class ComponenteController {

    @Autowired
    private ComponenteService componenteService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ModelAndView listarDiagnosticos(ModelAndView model, HttpServletRequest request) {
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Acciones"}));
        model.addObject("ajaxList", "getComponentes");
        model.addObject("editUrl", "modificar");
        model.addObject("changeUrl", "cambiarEstado");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "id,id,descripcion");
        model.addObject("findItem", "getComponente");
        model.setViewName("Componente/listar");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model = new ModelAndView("redirect:/index", "usuario", usuario);
            return model;
        }
        return model;
    }

    @RequestMapping(value = "getComponentes", method = RequestMethod.GET)
    public @ResponseBody
    List<Componente> getComponentes(@PathVariable long idModulo) {
        return componenteService.listar();
    }

    @RequestMapping(value = "getComponente", method = RequestMethod.GET)
    public @ResponseBody
    Componente getDiagnostico(@RequestParam long id) {
        return componenteService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Componente>> insertarComponte(@ModelAttribute Componente componente, HttpServletRequest request) {
        AjaxResponse<List<Componente>> response = new AjaxResponse<List<Componente>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        componente.setUsuarioCreacion(usuario.getIdUsuario());
        componente.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = componenteService.insertar(componente);
        List<Componente> data = componenteService.listar();
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Componente.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Componente.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarComponente(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Componente> response = new AjaxResponse<Componente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        boolean result = componenteService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el Componente.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este Componente.");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Componente> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Componente> response = new AjaxResponse<Componente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        boolean result = componenteService.cambiarEstado(id);
        Componente componente = componenteService.obtenerPorId(id);
        componente.setUsuarioModificacion(usuario.getIdUsuario());;
        componenteService.actualizar(componente);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del Componente.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Componente.");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Componente> modificarDiagnostico(@ModelAttribute Componente componente, HttpServletRequest request) {
        AjaxResponse<Componente> response = new AjaxResponse<Componente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        componente.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = componenteService.actualizar(componente);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Componente.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Componente.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "ComponentePDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "ComponenteExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        List<Componente> data = componenteService.listar();

        List<String> contentLabels = Arrays.asList("Código", "Descripción");
        List<String> contentFields = Arrays.asList("id", "descripcion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 10);
        contentColumns.put(1, 90);

        ModelAndView model = new ModelAndView(viewName);
        model.addObject("Title", "Componentes");
        model.addObject("ContentData", data);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
}
