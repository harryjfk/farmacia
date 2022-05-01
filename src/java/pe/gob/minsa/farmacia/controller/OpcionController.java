package pe.gob.minsa.farmacia.controller;

import java.util.ArrayList;
import pe.gob.minsa.farmacia.util.JsonResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Menu;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.domain.Opcion;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.services.impl.MenuService;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.OpcionService;
import pe.gob.minsa.farmacia.services.impl.SubmenuService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;
import pe.gob.minsa.farmacia.util.BusinessException;

@RequestMapping("/Opcion/")
@Controller
public class OpcionController {

    @Autowired
    ModuloService moduloService;
    @Autowired
    SubmoduloService submoduloService;
    @Autowired
    MenuService menuService;
    @Autowired
    SubmenuService submenuService;
    @Autowired
    OpcionService opcionService;

    JsonResponse jsonResponse;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public ModelAndView listarOpcion() {
        List<Modulo> modulos = moduloService.listar();

        ModelMap myModel = new ModelMap();
        myModel.put("modulos", modulos);

        return new ModelAndView("Opcion/listar", myModel);
    }

    @RequestMapping(value = "/submodulosPorModuloJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Submodulo> listarSubmoduloPorModulo(@RequestParam int idModulo) {
        List<Submodulo> submodulos = submoduloService.listarPorIdModulo(idModulo);
        return submodulos;
    }

    @RequestMapping(value = "/menusPorSubmoduloJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> listarMenusPorSubmodulo(@RequestParam int idSubmodulo) {
        List<Menu> menus = menuService.listarPorIdSubmodulo(idSubmodulo);
        return menus;
    }

    @RequestMapping(value = "/submenusPorMenuJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Submenu> listarSubmenusPorMenuJSON(@RequestParam int idMenu) {
        List<Submenu> submenus = submenuService.listarPorIdMenu(idMenu);
        return submenus;
    }

    @RequestMapping(value = "/opcionesJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Opcion> obtenerOpcionesJSON(@RequestParam int idSubmenu) {
        List<Opcion> opciones = opcionService.listarPorSubmenu(idSubmenu);
        return opciones;
    }

    @RequestMapping(value = "/opcionJSON", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap obtenerOpcionJSON(@RequestParam int idOpcion) {

        ModelMap model = new ModelMap();

        try {
            Opcion opcion = opcionService.obtenerPorId(idOpcion);
            Submenu submenu = submenuService.obtenerPorId(opcion.getIdSubmenu());
            Menu menu = menuService.obtenerPorId(submenu.getIdMenu());
            Submodulo submodulo = submoduloService.obtenerPorId(menu.getIdSubmodulo());
            Modulo modulo = moduloService.obtenerPorId(submodulo.getIdModulo());

            model.put("nombreModulo", modulo.getNombreModulo());
            model.put("nombreSubmodulo", submodulo.getNombreSubmodulo());
            model.put("nombreMenu", menu.getNombreMenu());
            model.put("nombreSubmenu", submenu.getNombreSubmenu());
            model.put("opcion", opcion);

        } catch (BusinessException e) {
            //REGRESAR
        }

        return model;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarOpcion(@ModelAttribute Opcion opcion) {

        try {
            String appOpcion = opcionService.obtenerPorId(opcion.getIdOpcion()).getAppOpcion();

            opcion.setAppOpcion(appOpcion);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");

            opcionService.actualizar(opcion);

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            opcionService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

}
