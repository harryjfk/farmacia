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
import pe.gob.minsa.farmacia.domain.TipoProducto;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoProducto")
public class TipoProductoController {

    @Autowired
    TipoProductoService tipoProductoService;

    JsonResponse jsonResponse;

    private ManagerDatatables getTipoProductoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoProducto> tiposProducto = tipoProductoService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposProducto.size() - 1; ++i) {
            TipoProducto c = tiposProducto.get(i);
            if (c.getNombreTipoProducto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || c.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposProducto.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposProducto, new Comparator<TipoProducto>() {
            @Override
            public int compare(TipoProducto o1, TipoProducto o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoProducto()).compareTo(o2.getIdTipoProducto()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoProducto().toLowerCase().compareTo(o2.getNombreTipoProducto().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposProducto.size());

        if (tiposProducto.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposProducto = tiposProducto.subList(dataTablesParam.iDisplayStart, tiposProducto.size());
        } else {
            tiposProducto = tiposProducto.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposProducto);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTiposProducto() {
        return new ModelAndView("TipoProducto");
    }

    @RequestMapping(value = "/tiposProductoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposProductoJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTipoProductoDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoProducto(@ModelAttribute TipoProducto tipoProducto) {

        try {
            tipoProductoService.insertar(tipoProducto);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoProductoJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoProducto obtenerTipoProductoJSON(@PathVariable int id, HttpServletResponse response) {
        TipoProducto tipoProducto = new TipoProducto();

        try {
            tipoProducto = tipoProductoService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoProducto;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoProducto(@ModelAttribute TipoProducto tipoProducto) {
        try {
            tipoProductoService.actualizar(tipoProducto);
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
            tipoProductoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoProducto(@PathVariable int id) {
        try {
            tipoProductoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoProductoDatatables(request, response);

        return new ModelAndView("TipoProductoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoProductoDatatables(request, response);

        return new ModelAndView("TipoProductoExcel", "Data", managerDatatables.getAaData());
    }
}