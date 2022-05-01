package pe.gob.minsa.farmacia.controller;

import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.domain.Proveedor;
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockMinimo;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.services.impl.MovimientoService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.services.impl.ProveedorService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
public class ConsultaController {

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    ConceptoService conceptoService;

    @Autowired
    AlmacenService almacenService;

    @Autowired
    PeriodoService periodoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    ProveedorService proveedorService;

    JsonResponse jsonResponse;

    @Autowired
    FormaFarmaceuticaService formaFarmaceuticaService;

    @Autowired
    TipoProductoService tipoProductoService;

    @Autowired
    UnidadMedidaService unidadMedidaService;

    @RequestMapping(value = "/ProductosVencimiento", method = RequestMethod.GET)
    public ModelAndView ProductosVencimiento() {
        ModelMap model = new ModelMap();
        model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
        model.put("unidadesMedida", unidadMedidaService.listar());
        model.put("tiposProducto", tipoProductoService.listar());
        return new ModelAndView("ProductosVencimiento", model);
    }

    @RequestMapping(value = "/productosVencimientoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosAlertaVencimientoJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductosVencimiento(request, response);
    }

    @RequestMapping(value = "/ProductosVencimiento/pdf", method = RequestMethod.GET)
    public ModelAndView obtenerProductosAlertaVencimientoPDF(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getProductosVencimiento(request, response);

        return new ModelAndView("ProductosVencimientoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/ProductosVencimiento/excel", method = RequestMethod.GET)
    public ModelAndView obtenerProductosAlertaVencimientoExcel(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getProductosVencimiento(request, response);

        return new ModelAndView("ProductosVencimientoExcel", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/ProductosStockMinimo", method = RequestMethod.GET)
    public ModelAndView ProductosStockMinimo() {
        ModelMap model = new ModelMap();
        return new ModelAndView("ProductosStockMinimo", model);
    }

    @RequestMapping(value = "/productosStockMinimoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosStockMinimoJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductosStockMinimo(request, response);
    }
    
     @RequestMapping(value = "/ProductosStockMinimo/pdf", method = RequestMethod.GET)
    public ModelAndView ProductosStockMinimoPDF(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getProductosStockMinimo(request, response);
        return new ModelAndView("ProductosStockMinimoPDF", "Data", managerDatatables.getAaData());
    }
    
     @RequestMapping(value = "/ProductosStockMinimo/excel", method = RequestMethod.GET)
    public ModelAndView ProductosStockMinimo(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getProductosStockMinimo(request, response);
        return new ModelAndView("ProductosStockMinimoExcel", "Data", managerDatatables.getAaData());
    }

    private ManagerDatatables getProductosVencimiento(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Timestamp fechaVenDesde = null;
        Timestamp fechaVenHasta = null;

        if (request.getParameter("fechaVenDesde").isEmpty() == false) {
            fechaVenDesde = new Timestamp(Long.parseLong(request.getParameter("fechaVenDesde")));
        }

        if (request.getParameter("fechaVenHasta").isEmpty() == false) {
            fechaVenHasta = new Timestamp(Long.parseLong(request.getParameter("fechaVenHasta")));
        }

        List<ProductoAlertaVencimientoDto> productos = productoService.listarProductosVencimiento(idAlmacen, idProducto, fechaVenDesde, fechaVenHasta);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(productos.size());

        if (productos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productos = productos.subList(dataTablesParam.iDisplayStart, productos.size());
        } else {
            productos = productos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(productos);

        return managerDatatables;
    }

    private ManagerDatatables getProductosStockMinimo(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        
        List<ProductoStockMinimo> productos = productoService.listarConStockMinimo();

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(productos.size());

        if (productos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productos = productos.subList(dataTablesParam.iDisplayStart, productos.size());
        } else {
            productos = productos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(productos);

        return managerDatatables;        
    }
}
