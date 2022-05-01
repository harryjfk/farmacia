package pe.gob.minsa.farmacia.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Inventario;
import pe.gob.minsa.farmacia.domain.InventarioProducto;
import pe.gob.minsa.farmacia.domain.dto.InventarioProductoTotalDto;
import pe.gob.minsa.farmacia.services.impl.InventarioProductoService;
import pe.gob.minsa.farmacia.services.impl.InventarioService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProcesoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Inventario")
public class InventarioController {

    @Autowired
    PeriodoService periodoService;

    @Autowired
    InventarioService inventarioService;

    @Autowired
    InventarioProductoService inventarioProductoService;
    
    @Autowired
    TipoProcesoService tipoProcesoService;
    
    JsonResponse jsonResponse;

    private ManagerDatatables getInventarioTotalDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        Map<String, Object> dataExtra = managerDatatables.getExtraData();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(sdf.format(date));
        } catch (ParseException ex) {
            Logger.getLogger(InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Timestamp fechaHoy = new Timestamp(date.getTime());
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        int tipoProceso = Integer.parseInt(request.getParameter("idTipoProceso"));        
        Timestamp fechaProceso = new Timestamp(Long.parseLong(request.getParameter("fechaProceso")));
        int idInventario = 0;

        if (inventarioService.existe(idPeriodo, idAlmacen, fechaProceso, tipoProceso) == false) {
            Inventario inventario = new Inventario();
            inventario.setIdPeriodo(idPeriodo);
            inventario.setIdAlmacen(idAlmacen);
            inventario.setFechaProceso(fechaProceso);
            inventario.setIdTipoProceso(tipoProceso);

            try {
                inventarioService.insertar(inventario);
                idInventario = inventario.getIdInventario();
                inventarioProductoService.preparar(idInventario, 0);
                dataExtra.put("idInventario", idInventario);
                dataExtra.put("numeroInventario", inventario.getNumeroInventario());
                dataExtra.put("fechaCierre", inventario.getFechaCierre());
            } catch (BusinessException ex) {

            }
        } else {
            Inventario inventario = inventarioService.obtener(idPeriodo, idAlmacen, fechaProceso, tipoProceso);
            idInventario = inventario.getIdInventario();
            dataExtra.put("idInventario", idInventario);
            dataExtra.put("numeroInventario", inventario.getNumeroInventario());
            dataExtra.put("fechaCierre", inventario.getFechaCierre());
        }

        List<InventarioProductoTotalDto> inventarios = inventarioProductoService.listarTotales(idInventario);

        
        for (int i = 0; i <= inventarios.size() - 1; ++i) {
            InventarioProductoTotalDto c = inventarios.get(i);
            if (c.getDescripcion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                inventarios.remove(i);
                i = i - 1;
            }
        }
        
        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(inventarios.size());
        
        if (inventarios.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            inventarios = inventarios.subList(dataTablesParam.iDisplayStart, inventarios.size());
        } else {
            inventarios = inventarios.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(inventarios);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelMap model = new ModelMap();
        model.put("procesos", tipoProcesoService.listar());
        model.put("anios", periodoService.listarAnios());
       
        return new ModelAndView("Inventario", model);
    }

    @RequestMapping(value = "/inventarioTotalesJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables consultaInventarioTotales(HttpServletRequest request, HttpServletResponse response) {
        return getInventarioTotalDatatables(request, response);
    }

    @RequestMapping(value = "/inventarioProductosJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<InventarioProducto> inventarioProducto(@RequestParam int idProducto, @RequestParam int idInventario) {
        return inventarioProductoService.listar(idProducto, idInventario);
    }
    
    @RequestMapping(value = "/agregarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse agregarDetalle(@RequestBody InventarioProducto inventarioProducto){
        try {
            inventarioProductoService.insertar(inventarioProducto);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        
        return jsonResponse;
    }
    
    @RequestMapping(value = "/modificarConteo", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarConteo(@RequestParam int idInventarioProducto, @RequestParam int conteo, @RequestParam int alterado){
        try {
            inventarioProductoService.modificarConteo(idInventarioProducto, conteo, alterado, 0);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        
        return jsonResponse;
    }
    
    @RequestMapping(value = "/procesar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse procesar(@RequestParam int idInventario){
        try {
            inventarioProductoService.procesar(idInventario, 1);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se procesó correctamente"));            
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }
        
        return jsonResponse;
    }
    
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getInventarioTotalDatatables(request, response);
        return new ModelAndView("InventarioExcel", "Data", managerDatatables.getAaData());
    }
    
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPdf(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getInventarioTotalDatatables(request, response);
        return new ModelAndView("InventarioPDF", "Data", managerDatatables.getAaData());
    }
}
