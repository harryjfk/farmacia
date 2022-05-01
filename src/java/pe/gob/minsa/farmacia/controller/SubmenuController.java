package pe.gob.minsa.farmacia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Menu;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.MenuService;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.SubmenuService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@RequestMapping("/Submenu")
@Controller
public class SubmenuController {

    @Autowired
    ModuloService moduloService;
    
    @Autowired
    SubmoduloService submoduloService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    SubmenuService submenuService;

    JsonResponse jsonResponse;

    private ManagerDatatables getSubmenuDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        int idMenu = Integer.parseInt(request.getParameter("idMenu"));
        List<Submenu> submenus = submenuService.listarPorIdMenu(idMenu);       

        managerDatatables.setiTotalRecords(submenus.size());

        for (int i = 0; i <= submenus.size() - 1; ++i) {
            Submenu submenu = submenus.get(i);
            if (String.valueOf(submenu.getIdSubmenu()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || submenu.getNombreSubmenu().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || String.valueOf(submenu.getOrden()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || submenu.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                submenus.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(submenus, new Comparator<Submenu>() {
            @Override
            public int compare(Submenu o1, Submenu o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdSubmenu()).compareTo(o2.getIdSubmenu()) * sortDirection;
                    case 1:
                        return o1.getNombreSubmenu().toLowerCase().compareTo(o2.getNombreSubmenu().toLowerCase()) * sortDirection;
                    case 2:
                        return ((Integer)o1.getOrden()).compareTo(o2.getOrden()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(submenus.size());

        if (submenus.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            submenus = submenus.subList(dataTablesParam.iDisplayStart, submenus.size());
        } else {
            submenus = submenus.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(submenus);

        return managerDatatables;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarSubmenus() {
        List<Modulo> modulos = moduloService.listar();
        return new ModelAndView("Submenu", "modulos", modulos);
    }    

    @RequestMapping(value = "/submenusJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerSubmenusJSON(HttpServletRequest request, HttpServletResponse response) {        
        return getSubmenuDatatables(request, response);
    }

    @RequestMapping(value = "/submenuJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap obtenerSubmenuJSON(@PathVariable int id, HttpServletResponse response) {

        ModelMap model = new ModelMap();
        try {
            Submenu submenu = submenuService.obtenerPorId(id);
            Menu menu = menuService.obtenerPorId(submenu.getIdMenu());
            Submodulo submodulo = submoduloService.obtenerPorId(menu.getIdSubmodulo());
            Modulo modulo = moduloService.obtenerPorId(submodulo.getIdModulo());

            model.put("nombreModulo", modulo.getNombreModulo());
            model.put("nombreSubmodulo", submodulo.getNombreSubmodulo());
            model.put("nombreMenu", menu.getNombreMenu());
            model.put("submenu", submenu);
        } catch (BusinessException e) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(e, response);
        }

        return model;
    }
    
    @RequestMapping(value = "/orden", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarOrden(@RequestBody NavegacionOrden navegacionOrden) {

        try {
            submenuService.cambiarOrden(navegacionOrden.getId(), navegacionOrden.isSubida());
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarSubmenu(@ModelAttribute Submenu submenu) {
        try {            
            submenuService.actualizar(submenu);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {        
        try {
            submenuService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/submenusPorMenuJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Submenu> listarSubmenusPorMenuJSON(@RequestParam int idMenu) {
        List<Submenu> submenus = submenuService.listarPorIdMenu(idMenu);
        return submenus;
    }

    @RequestMapping(value = "/barMenu", method = RequestMethod.POST)
    @ResponseBody
    public MenuSubmenu barMenu(@RequestParam int idSubmodulo, HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioIniciado");
        List<Menu> menus = menuService.listarParaSession(usuario.getIdUsuario());
        List<Submenu> submenusSession = submenuService.listarParaSession(usuario.getIdUsuario());
        List<Submenu> submenus = new ArrayList<Submenu>();
        
        for (int i = 0; i < menus.size(); ++i) {
            if (menus.get(i).getIdSubmodulo() != idSubmodulo) {
                menus.remove(i);
                i = i - 1;
            }
        }
        
        for (int s = 0; s < submenusSession.size(); ++s) {
            for (int i = 0; i < menus.size(); ++i) {            
                if (submenusSession.get(s).getIdMenu() == menus.get(i).getIdMenu()) {
                    submenus.add(submenusSession.get(s));                    
                }
            }
        }

        MenuSubmenu menuSubmenu = new MenuSubmenu();
        menuSubmenu.setMenus(menus);
        menuSubmenu.setSubmenus(submenus);

        return menuSubmenu;
    }
}

class MenuSubmenu {

    private List<Menu> menus;
    private List<Submenu> submenus;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Submenu> getSubmenus() {
        return submenus;
    }

    public void setSubmenus(List<Submenu> submenus) {
        this.submenus = submenus;
    }

}
