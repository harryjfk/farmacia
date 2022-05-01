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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.TipoAlmacen;
import pe.gob.minsa.farmacia.services.impl.TipoAlmacenService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoAlmacen")
public class TipoAlmacenController {
    
    @Autowired
    TipoAlmacenService tipoAlmacenService;

    JsonResponse jsonResponse;
    
    private ManagerDatatables getTipoAlmacenDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoAlmacen> tiposAlmacenes = tipoAlmacenService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposAlmacenes.size() - 1; ++i) {
            TipoAlmacen a = tiposAlmacenes.get(i);
            if (a.getNombreTipoAlmacen().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || a.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposAlmacenes.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposAlmacenes, new Comparator<TipoAlmacen>() {
            @Override
            public int compare(TipoAlmacen o1, TipoAlmacen o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoAlmacen()).compareTo(o2.getIdTipoAlmacen()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoAlmacen().toLowerCase().compareTo(o2.getNombreTipoAlmacen().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposAlmacenes.size());

        if (tiposAlmacenes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposAlmacenes = tiposAlmacenes.subList(dataTablesParam.iDisplayStart, tiposAlmacenes.size());
        } else {
            tiposAlmacenes = tiposAlmacenes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposAlmacenes);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoAlmacen() {
        return new ModelAndView("TipoAlmacen");
    }

    @RequestMapping(value = "/tiposAlmacenesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposAlmacenesJSON(HttpServletRequest request, HttpServletResponse response) {        
        return getTipoAlmacenDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public  JsonResponse registrarTipoAlmacen(@ModelAttribute TipoAlmacen tipoAlmacen) {

        try {
            tipoAlmacenService.insertar(tipoAlmacen);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoAlmacenJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoAlmacen obtenerTipoAlmacenJSON(@PathVariable int id, HttpServletResponse reponse) {
        TipoAlmacen tipoAlmacen = new TipoAlmacen();

        try {
            tipoAlmacen = tipoAlmacenService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, reponse);
        }

        return tipoAlmacen;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoAlmacen(@ModelAttribute TipoAlmacen tipoAlmacen) {
        try {
            tipoAlmacenService.actualizar(tipoAlmacen);
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
            tipoAlmacenService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoAlmacen(@PathVariable int id) {
        try {
            tipoAlmacenService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoAlmacenDatatables(request, response);

        return new ModelAndView("TipoAlmacenPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoAlmacenDatatables(request, response);

        return new ModelAndView("TipoAlmacenExcel", "Data", managerDatatables.getAaData());
    }
}