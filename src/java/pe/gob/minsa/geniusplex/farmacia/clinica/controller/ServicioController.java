package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

import java.util.Arrays;
import java.util.List;
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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.Servicio;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.ServicioService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.CustomResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/servicio/*")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarServicios(Model model, @PathVariable Long idModulo) {
        if (idModulo != 7 && idModulo != 10) {
            CustomResponse response = new CustomResponse();
            response.getMensajesRepuesta().add("La p&aacute;gina que solicita no existe");
            model.addAttribute("reponseError", response);
            return "response/error";
        }
        model.addAttribute("viewTitle", "Mantenimiento de Servicios");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Nombre", "Descripción", "Acciones"}));
        model.addAttribute("ajaxList", "getServicios");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,nombre,descripcion");
        model.addAttribute("findItem", "getServicio");
        return "Servicio/listar";
    }

    @RequestMapping(value = "getServicios", method = RequestMethod.GET)
    public @ResponseBody
    List<Servicio> getServicios(@PathVariable long idModulo, @RequestParam(required = false) Integer justactive) {
        List<Servicio> lista = servicioService.listarModulo(idModulo);
        if(justactive != null && justactive == 1) {
            for (Servicio servicio : lista) {
                if(servicio.getActivo() == 0) {
                    lista.remove(servicio);
                }
            }
        }
        return lista;
    }

    @RequestMapping(value = "getServicio", method = RequestMethod.GET)
    public @ResponseBody
    Servicio getServicio(@RequestParam long id) {
        return servicioService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Servicio>> insertarServicio(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Servicio>> response = new AjaxResponse<List<Servicio>>();

        Servicio servicio = this.getData(request);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        servicio.setUsuarioCreacion(usuario.getIdUsuario());
        servicio.setUsuarioModificacion(usuario.getIdUsuario());
        servicio.setIdModulo(idModulo);

        boolean result = servicioService.insertar(servicio);
        List<Servicio> data = servicioService.listarModulo(idModulo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Servicio.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Servicio.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarCliente(@RequestParam long id) {
        AjaxResponse<Servicio> response = new AjaxResponse<Servicio>();
        try {
            boolean result = servicioService.eliminar(id);
            if (result) {
                response.addMensaje("Se ha borrado exitosamente el cliente.");
            } else {
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error al borrar a este cliente");
            }
        } catch (Exception e) {
            response.setHasError(true);
            response.addMensaje("No se puede eliminar este cliente. Verifique que no est&eacute; relacionado con alg&uacute;n otro elemento como una venta o pre venta.");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Servicio> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Servicio> response = new AjaxResponse<Servicio>();
        boolean result = servicioService.cambiarEstado(id);
        Servicio cliente = servicioService.obtenerPorId(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        cliente.setUsuarioModificacion(usuario.getIdUsuario());
        servicioService.actualizar(cliente);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del cliente");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este cliente");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Servicio> modificarServicio(HttpServletRequest request) {
        AjaxResponse<Servicio> response = new AjaxResponse<Servicio>();
        Servicio servicio = this.getData(request);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        servicio.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = servicioService.actualizar(servicio);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Servicio.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Servicio.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Servicio> lista = servicioService.listarModulo(idModulo);
        return new ModelAndView("ServicioPDF", "Data", lista);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Servicio> lista = servicioService.listarModulo(idModulo);
        return new ModelAndView("ServicioExcel", "Data", lista);
    }

    private Servicio getData(HttpServletRequest request) {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        long id = Long.parseLong(request.getParameter("id"));
        Servicio servicio;

        if (id == 0) {
            servicio = new Servicio(nombre, descripcion);
        } else {
            servicio = servicioService.obtenerPorId(id);
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
        }
        return servicio;
    }
}
