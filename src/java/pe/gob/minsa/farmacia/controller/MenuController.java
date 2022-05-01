package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import pe.gob.minsa.farmacia.util.JsonResponse;
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
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.services.impl.MenuService;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@RequestMapping("/Menu")
@Controller
public class MenuController {

    @Autowired
    MenuService menuService;
    @Autowired
    ModuloService moduloService;
    @Autowired
    SubmoduloService submoduloService;

    JsonResponse jsonResponse;
    
    private ManagerDatatables getMenuDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        int idSubmodulo = Integer.parseInt(request.getParameter("idSubmodulo"));
        List<Menu> menus = menuService.listarPorIdSubmodulo(idSubmodulo);        

        managerDatatables.setiTotalRecords(menus.size());

        for (int i = 0; i <= menus.size() - 1; ++i) {
            Menu menu = menus.get(i);
            if (String.valueOf(menu.getIdSubmodulo()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || menu.getNombreMenu().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || String.valueOf(menu.getOrden()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || menu.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                menus.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdMenu()).compareTo(o2.getIdMenu()) * sortDirection;
                    case 1:
                        return o1.getNombreMenu().toLowerCase().compareTo(o2.getNombreMenu().toLowerCase()) * sortDirection;
                    case 2:
                        return ((Integer)o1.getOrden()).compareTo(o2.getOrden()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(menus.size());

        if (menus.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            menus = menus.subList(dataTablesParam.iDisplayStart, menus.size());
        } else {
            menus = menus.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(menus);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarMenu() {
        List<Modulo> modulos = moduloService.listar();
        return new ModelAndView("Menu", "modulos", modulos);
    }   

    @RequestMapping(value = "/menusJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerMenusJSON(HttpServletRequest request, HttpServletResponse response) {        
        return getMenuDatatables(request, response);
    }

    @RequestMapping(value = "/menuJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap obtenerMenuJSON(@PathVariable int id, HttpServletResponse response) {

        ModelMap model = new ModelMap();

        try {
            Menu menu = menuService.obtenerPorId(id);
            Submodulo submodulo = submoduloService.obtenerPorId(menu.getIdSubmodulo());
            Modulo modulo = moduloService.obtenerPorId(submodulo.getIdModulo());

            model.put("nombreModulo", modulo.getNombreModulo());
            model.put("nombreSubmodulo", submodulo.getNombreSubmodulo());
            model.put("menu", menu);
        }catch (BusinessException e) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(e, response);
        }
        
        return model;
    }
    
    @RequestMapping(value = "/orden", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarOrden(@RequestBody NavegacionOrden navegacionOrden) {

        try {
            menuService.cambiarOrden(navegacionOrden.getId(), navegacionOrden.isSubida());
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
    public JsonResponse modificarMenu(@ModelAttribute Menu menu) {
        try {
            menuService.actualizar(menu);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }
        
        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
         try {
            menuService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }
        
        return jsonResponse;
    }
    
    @RequestMapping(value = "/menusPorSubmoduloJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> listarMenusPorSubmodulo(@RequestParam int idSubmodulo) {
        List<Menu> menus = menuService.listarPorIdSubmodulo(idSubmodulo);
        return menus;
    }
}
