package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Equipo;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.EquipoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/equipos/*")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarEquipos(Model model) {
        model.addAttribute("viewTitle", "Registro de Equipos");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Descripción", "Fecha de Registro", "Acciones"}));
        model.addAttribute("ajaxList", "getEquipos");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,descripcion,fechaCreacion:date");
        model.addAttribute("findItem", "getEquipo");
        return "Equipo/listar";
    }

    @RequestMapping(value = "getEquipos", method = RequestMethod.GET)
    public @ResponseBody
    List<Equipo> getEquipos(@PathVariable long idModulo) {
        List<Equipo> lista = equipoService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getEquipo", method = RequestMethod.GET)
    public @ResponseBody
    Equipo getEquipo(@RequestParam long id) {
        return equipoService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Equipo>> insertarEquipo(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Equipo>> response = new AjaxResponse<List<Equipo>>();

        Equipo equipo = new Equipo();
        String descripcion = request.getParameter("descripcion");
        equipo.setDescripcion(descripcion);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (equipo.getId() == null || equipo.getId() == 0) {
            equipo.setUsuarioCreacion(usuario.getIdUsuario());
        }
        equipo.setUsuarioModificacion(usuario.getIdUsuario());
        equipo.setIdModulo(idModulo);
        List<Equipo> lista = equipoService.listarModulo(idModulo);

        if (lista.contains(equipo)) {
            response.addMensaje("Este equipo ya existe.");
            response.setHasError(true);
            return response;
        }

        boolean result = equipoService.insertar(equipo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el equipo.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este equipo.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarEquipo(@RequestParam long id) {
        AjaxResponse<Equipo> response = new AjaxResponse<Equipo>();
        boolean result = equipoService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el equipo.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este equipo");
        }
        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Equipo> modificarEquipo(HttpServletRequest request) {
        AjaxResponse<Equipo> response = new AjaxResponse<Equipo>();
        long idEquipo = Long.parseLong(request.getParameter("id"));
        Equipo equipo = equipoService.obtenerPorId(idEquipo);
        String descripcion = request.getParameter("descripcion");
        equipo.setDescripcion(descripcion);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        equipo.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = equipoService.actualizar(equipo);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Equipo.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Equipo.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "EquiposPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<Equipo> lista = equipoService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Descripción", "Fecha de Ingreso");
        List<String> contentFields = Arrays.asList("descripcion", "fechaCreacion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 50);
        contentColumns.put(1, 50);
        model.addObject("Title", "Reporte de Equipos");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

}
