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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Parametro;
import pe.gob.minsa.farmacia.services.impl.ParametroService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
public class ParametroController {

    @Autowired
    ParametroService parametroService;

    JsonResponse jsonResponse;

    private ManagerDatatables getParametroDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Parametro> parametros = parametroService.listarSinDependencia();        

        managerDatatables.setiTotalRecords(parametros.size());

        for (int i = 0; i <= parametros.size() - 1; ++i) {
            Parametro p = parametros.get(i);
            if (String.valueOf(p.getIdParametro()).contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getNombreParametro().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getDescripcionParametro().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getValor().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                parametros.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(parametros, new Comparator<Parametro>() {
            @Override
            public int compare(Parametro o1, Parametro o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdParametro()).compareTo(o2.getIdParametro()) * sortDirection;
                    case 1:
                        return o1.getNombreParametro().toLowerCase().compareTo(o2.getNombreParametro().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getDescripcionParametro().toLowerCase().compareTo(o2.getDescripcionParametro().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(parametros.size());

        if (parametros.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            parametros = parametros.subList(dataTablesParam.iDisplayStart, parametros.size());
        } else {
            parametros = parametros.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(parametros);

        return managerDatatables;
    }

    private ManagerDatatables getDependenciaDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Parametro> parametros = parametroService.listarDependencia();        
        
        for (int i = 0; i <= parametros.size() - 1; ++i) {
            Parametro p = parametros.get(i);
            if (String.valueOf(p.getIdParametro()).contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getNombreParametro().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getDescripcionParametro().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || p.getValor().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                parametros.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;
        
        if (parametros.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            parametros = parametros.subList(dataTablesParam.iDisplayStart, parametros.size());
        } else {
            parametros = parametros.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(parametros);

        return managerDatatables;
    }
    
    @RequestMapping("/Parametro")
    public ModelAndView listarParametro() {
        return new ModelAndView("Parametro");
    }

    @RequestMapping("/DependenciaDestino")
    public ModelAndView listarDependencia() {
        return new ModelAndView("DependenciaDestino");
    }

    @RequestMapping(value = "/Parametro/parametrosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerParametrosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getParametroDatatables(request, response);
    }

    @RequestMapping(value = "/DependenciaDestino/dependenciasJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerDependenciasJSON(HttpServletRequest request, HttpServletResponse response) {
        return getDependenciaDatatables(request, response);
    }

    @RequestMapping(value = "/Parametro/parametroJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Parametro obtenerParametroJSON(@PathVariable int id, HttpServletResponse response) {
        Parametro parametro = new Parametro();

        try {
            parametro = parametroService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return parametro;
    }
    
    @RequestMapping(value = "/Parametro/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarParametro(@ModelAttribute Parametro parametro) {
        try {
            parametroService.insertar(parametro);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();           
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Parametro/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarParametro(@ModelAttribute Parametro parametro) {
        try {
            parametroService.actualizar(parametro);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();            
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Parametro/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables parametroDatatables = getParametroDatatables(request, response);

        return new ModelAndView("ParametroPDF", "Data", parametroDatatables.getAaData());
    }

    @RequestMapping(value = "/Parametro/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables parametroDatatables = getParametroDatatables(request, response);

        return new ModelAndView("ParametroExcel", "Data", parametroDatatables.getAaData());
    }

    @RequestMapping(value = "/DependenciaDestino/pdf", method = RequestMethod.GET)
    public ModelAndView rptDependenciaPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables dependenciaDatatables = getParametroDatatables(request, response);

        return new ModelAndView("DependenciaDestinoPDF", "Data", dependenciaDatatables.getAaData());
    }

    @RequestMapping(value = "/DependenciaDestino/excel", method = RequestMethod.GET)
    public ModelAndView rptDependenciaExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables dependenciaDatatables = getParametroDatatables(request, response);

        return new ModelAndView("DependenciaDestinoExcel", "Data", dependenciaDatatables.getAaData());
    }
}
