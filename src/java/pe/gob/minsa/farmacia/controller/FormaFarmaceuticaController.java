package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
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
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/FormaFarmaceutica")
public class FormaFarmaceuticaController {

    @Autowired
    FormaFarmaceuticaService formaFarmaceuticaService;

    JsonResponse jsonResponse;

    private ManagerDatatables getFormaFarmaceuticaDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<FormaFarmaceutica> formasFarmaceuticas = formaFarmaceuticaService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= formasFarmaceuticas.size() - 1; ++i) {
            FormaFarmaceutica c = formasFarmaceuticas.get(i);
            if (c.getNombreFormaFarmaceutica().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getAbreviatura().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                formasFarmaceuticas.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(formasFarmaceuticas, new Comparator<FormaFarmaceutica>() {
            @Override
            public int compare(FormaFarmaceutica o1, FormaFarmaceutica o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer) o1.getIdFormaFarmaceutica()).compareTo(o2.getIdFormaFarmaceutica()) * sortDirection;
                    case 1:
                        return o1.getNombreFormaFarmaceutica().toLowerCase().compareTo(o2.getNombreFormaFarmaceutica().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getAbreviatura().toLowerCase().compareTo(o2.getAbreviatura().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(formasFarmaceuticas.size());

        if (formasFarmaceuticas.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            formasFarmaceuticas = formasFarmaceuticas.subList(dataTablesParam.iDisplayStart, formasFarmaceuticas.size());
        } else {
            formasFarmaceuticas = formasFarmaceuticas.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(formasFarmaceuticas);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarFormaFarmaceutica() {
        return new ModelAndView("FormaFarmaceutica");
    }

    @RequestMapping(value = "/formasFarmaceuticasJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerFormaFarmaceuticasJSON(HttpServletRequest request, HttpServletResponse response) {
        return getFormaFarmaceuticaDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarFormaFarmaceutica(@ModelAttribute FormaFarmaceutica formaFarmaceutica) {

        try {
            formaFarmaceuticaService.insertar(formaFarmaceutica);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/formaFarmaceuticaJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FormaFarmaceutica obtenerFormaFarmaceuticaJSON(@PathVariable int id, HttpServletResponse response) {
        FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica();

        try {
            formaFarmaceutica = formaFarmaceuticaService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return formaFarmaceutica;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarFormaFarmaceutica(@ModelAttribute FormaFarmaceutica formaFarmaceutica) {
        try {
            formaFarmaceuticaService.actualizar(formaFarmaceutica);
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
            formaFarmaceuticaService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarFormaFarmaceutica(@PathVariable int id) {
        try {
            formaFarmaceuticaService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getFormaFarmaceuticaDatatables(request, response);

        return new ModelAndView("FormaFarmaceuticaPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {
       
        ManagerDatatables managerDatatables = getFormaFarmaceuticaDatatables(request, response);
        
        return new ModelAndView("FormaFarmaceuticaExcel", "Data", managerDatatables.getAaData());
    }
}
