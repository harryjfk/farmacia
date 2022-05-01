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
import pe.gob.minsa.farmacia.domain.TipoCompra;
import pe.gob.minsa.farmacia.services.impl.TipoCompraService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/TipoCompra")
public class TipoCompraController {
    
    @Autowired
    TipoCompraService tipoCompraService;

    JsonResponse jsonResponse;
    
    private ManagerDatatables getTipoCompraDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<TipoCompra> tiposCompras = tipoCompraService.listar();

        managerDatatables.setiTotalRecords(0);

        for (int i = 0; i <= tiposCompras.size() - 1; ++i) {
            TipoCompra a = tiposCompras.get(i);
            if (a.getNombreTipoCompra().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || a.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                tiposCompras.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(tiposCompras, new Comparator<TipoCompra>() {
            @Override
            public int compare(TipoCompra o1, TipoCompra o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((Integer)o1.getIdTipoCompra()).compareTo(o2.getIdTipoCompra()) * sortDirection;
                    case 1:
                        return o1.getNombreTipoCompra().toLowerCase().compareTo(o2.getNombreTipoCompra().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(tiposCompras.size());

        if (tiposCompras.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            tiposCompras = tiposCompras.subList(dataTablesParam.iDisplayStart, tiposCompras.size());
        } else {
            tiposCompras = tiposCompras.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(tiposCompras);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarTipoCompras() {
        return new ModelAndView("TipoCompra");
    }

    @RequestMapping(value = "/tiposComprasJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerTiposComprasJSON(HttpServletRequest request, HttpServletResponse response) {        
        return getTipoCompraDatatables(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public  JsonResponse registrarTipoCompra(@ModelAttribute TipoCompra tipoCompra) {

        try {
            tipoCompraService.insertar(tipoCompra);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/tipoCompraJSON/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TipoCompra obtenerTipoCompraJSON(@PathVariable int id, HttpServletResponse response) {
        TipoCompra tipoCompra = new TipoCompra();

        try {
            tipoCompra = tipoCompraService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return tipoCompra;
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarTipoAlmacen(@ModelAttribute TipoCompra tipoCompra) {
        try {
            tipoCompraService.actualizar(tipoCompra);
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
            tipoCompraService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoCompra(@PathVariable int id) {
        try {
            tipoCompraService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoCompraDatatables(request, response);

        return new ModelAndView("TipoCompraPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getTipoCompraDatatables(request, response);

        return new ModelAndView("TipoCompraExcel", "Data", managerDatatables.getAaData());
    }    
}