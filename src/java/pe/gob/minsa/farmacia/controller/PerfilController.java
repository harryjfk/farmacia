package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import pe.gob.minsa.farmacia.util.JsonResponse;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.services.impl.PerfilService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Perfil")
public class PerfilController {

    @Autowired
    PerfilService perfilService;

    JsonResponse jsonResponse;

    private ManagerDatatables getPerfilDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Perfil> perfiles = perfilService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= perfiles.size() - 1; ++i) {
            Perfil p = perfiles.get(i);
            if (String.valueOf(p.getIdPerfil()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    ||p.getNombrePerfil().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                perfiles.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(perfiles, new Comparator<Perfil>() {
            @Override
            public int compare(Perfil o1, Perfil o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdPerfil()).compareTo(o2.getIdPerfil()) * sortDirection;
                    case 1:
                        return o1.getNombrePerfil().toLowerCase().compareTo(o2.getNombrePerfil().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(perfiles.size());

        if (perfiles.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            perfiles = perfiles.subList(dataTablesParam.iDisplayStart, perfiles.size());
        } else {
            perfiles = perfiles.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(perfiles);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarPerfil() {
        return new ModelAndView("Perfil");
    }

    @RequestMapping(value = "/perfilesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerPerfilesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getPerfilDatatables(request, response);
    }
    
    @RequestMapping(value = "/perfilJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Perfil obtenerPerfilJSON(@PathVariable int id, HttpServletResponse response) {
        
        Perfil perfil = new Perfil();
        
        try {
            perfil = perfilService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return perfil;
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarPerfil(@ModelAttribute Perfil perfil) {
        try {
            perfilService.insertar(perfil);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarPerfil(@ModelAttribute Perfil perfil) {
        try {
            perfilService.actualizar(perfil);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            perfilService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarPerfil(@PathVariable int id) {
        
        try {
            perfilService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables perfilDatatables = getPerfilDatatables(request, response);

        return new ModelAndView("PerfilPDF", "Data", perfilDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables perfilDatatables = getPerfilDatatables(request, response);

        return new ModelAndView("PerfilExcel", "Data", perfilDatatables.getAaData());
    }
}
