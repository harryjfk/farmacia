package pe.gob.minsa.farmacia.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.param.MovimientoParam;
import pe.gob.minsa.farmacia.domain.Movimiento;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.ProductoPrecio;
import pe.gob.minsa.farmacia.domain.dto.DetallePorLoteDto;
import pe.gob.minsa.farmacia.domain.dto.IngresoAlmacenDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoStockFechaDto;
import pe.gob.minsa.farmacia.domain.dto.StockGeneralProductoDto;
import pe.gob.minsa.farmacia.domain.dto.TarjetaControlDto;
import pe.gob.minsa.farmacia.domain.param.ConsultaMovimientoParam;
import pe.gob.minsa.farmacia.domain.param.GuiasRemisionParam;
import pe.gob.minsa.farmacia.domain.param.IngresoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.TarjetaControlParam;
import pe.gob.minsa.farmacia.domain.TipoMovimiento;
import pe.gob.minsa.farmacia.domain.TipoMovimientoConcepto;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockEnAlmacen;
import pe.gob.minsa.farmacia.domain.dto.DetalleStockPorAlmacen;
import pe.gob.minsa.farmacia.domain.dto.IngresoDto;
import pe.gob.minsa.farmacia.domain.dto.MovimientoProductoStock;
import pe.gob.minsa.farmacia.domain.dto.SalidaDto;
import pe.gob.minsa.farmacia.domain.lazyload.IAlmacen;
import pe.gob.minsa.farmacia.services.impl.AlmacenService;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.services.impl.ConceptoTipoDocumentoMovService;
import pe.gob.minsa.farmacia.services.impl.DocumentoOrigenService;
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.services.impl.MovimientoProductoService;
import pe.gob.minsa.farmacia.services.impl.MovimientoService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.ProductoPrecioService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.services.impl.ProveedorService;
import pe.gob.minsa.farmacia.services.impl.TipoCompraService;
import pe.gob.minsa.farmacia.services.impl.TipoDocumentoMovService;
import pe.gob.minsa.farmacia.services.impl.TipoProcesoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
public class MovimientoController {

    @Autowired
    ProductoService productoService;

    @Autowired
    MovimientoService movimientoService;

    @Autowired
    MovimientoProductoService movimientoProductoService;

    @Autowired  
    ConceptoService conceptoService;
    
    @Autowired
    ConceptoTipoDocumentoMovService conceptoTipoDocumentoMovService;
    
    @Autowired
    TipoDocumentoMovService tipoDocumentoMovService;

    @Autowired
    TipoCompraService tipoCompraService;

    @Autowired
    DocumentoOrigenService documentoOrigenService;

    @Autowired
    TipoProcesoService tipoProcesoService;

    @Autowired
    ProductoPrecioService productoPrecioService;

    @Autowired
    AlmacenService almacenService;

    @Autowired
    PeriodoService periodoService;

    @Autowired
    FormaFarmaceuticaService formaFarmaceuticaService;

    @Autowired
    TipoProductoService tipoProductoService;

    @Autowired
    UnidadMedidaService unidadMedidaService;
    
    @Autowired
    ProveedorService proveedorService;

    JsonResponse jsonResponse;

    private final String sessionDetalleIngreso = "detalleIngreso";

    private final String sessionDetalleSalida = "detalleSalida";

    private final String sessionDetalleIngresoMod = "detalleIngresoMod";

    private final String sessionDetalleSalidaMod = "detalleSalidaMod";
    
    private Timestamp fechaHoy = new Timestamp(new GregorianCalendar().getTimeInMillis());

    private ManagerDatatables getMovIngresoOSalidaDatatables(HttpServletRequest request, HttpServletResponse response, TipoMovimiento tipoMovimiento) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        MovimientoParam movimientoParam = new MovimientoParam();
        movimientoParam.setTipoMovimento(tipoMovimiento);        
        long fechaDesde = Long.parseLong(request.getParameter("fechaDesde"));
        long fechaHasta = Long.parseLong(request.getParameter("fechaHasta"));
        movimientoParam.setFechaDesde(new Timestamp(fechaDesde));
        movimientoParam.setFechaHasta(new Timestamp(fechaHasta));

        List<Movimiento> movimientos = movimientoService.listarPorTipo(movimientoParam);

        managerDatatables.setiTotalRecords(0);       

        managerDatatables.setiTotalDisplayRecords(movimientos.size());

        if (movimientos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, movimientos.size());
        } else {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        if (movimientoParam.getTipoMovimento() == TipoMovimiento.INGRESO) {

            List<IngresoDto> ingresos = new ArrayList<IngresoDto>();
            for (Movimiento movimiento : movimientos) {
                ingresos.add(new IngresoDto(
                        movimiento.getIdMovimiento(),
                        movimiento.getNumeroMovimiento(),
                        movimiento.getFechaRegistro(),
                        movimiento.getAlmacenOrigen().getDescripcion(),
                        movimiento.getAlmacenDestino().getDescripcion(),
                        movimiento.getTipoDocumentoMov().getNombreTipoDocumentoMov(),
                        movimiento.getNumeroDocumentoMov(),
                        movimiento.getDocumentoOrigen().getNombreDocumentoOrigen(),
                        movimiento.getNumeroDocumentoOrigen()
                ));
            }

            managerDatatables.setAaData(ingresos);
        } else if (movimientoParam.getTipoMovimento() == TipoMovimiento.SALIDA) {
            List<SalidaDto> salidas = new ArrayList<SalidaDto>();

            for (Movimiento movimiento : movimientos) {
                salidas.add(new SalidaDto(
                        movimiento.getIdMovimiento(),
                        movimiento.getNumeroMovimiento(),
                        movimiento.getFechaRegistro(),
                        movimiento.getAlmacenOrigen().getDescripcion(),
                        movimiento.getAlmacenDestino().getDescripcion(),
                        movimiento.getTipoDocumentoMov().getNombreTipoDocumentoMov(),
                        movimiento.getNumeroDocumentoMov()
                ));
            }

            managerDatatables.setAaData(salidas);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);

        return managerDatatables;
    }

    private ManagerDatatables getMovimientosDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        ConsultaMovimientoParam movimientoParam = new ConsultaMovimientoParam();
        movimientoParam.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
        if (request.getParameter("tipoMovimiento").isEmpty() == false) {
            movimientoParam.setTipoMovimiento(TipoMovimiento.fromString(request.getParameter("tipoMovimiento")));
        }
        movimientoParam.setIdAlmacenOrigen(Integer.parseInt(request.getParameter("idAlmacenOrigen")));
        movimientoParam.setIdAlmacenDestino(Integer.parseInt(request.getParameter("idAlmacenDestino")));
        movimientoParam.setIdConcepto(Integer.parseInt(request.getParameter("idConcepto")));
        
        if (request.getParameter("fechaDesde").isEmpty() == false) {
            movimientoParam.setFechaDesde(new Timestamp(Long.parseLong(request.getParameter("fechaDesde"))));
        }
        if (request.getParameter("fechaHasta").isEmpty() == false) {
            movimientoParam.setFechaHasta(new Timestamp(Long.parseLong(request.getParameter("fechaHasta"))));
        }
        movimientoParam.setActivo(Integer.parseInt(request.getParameter("activo")));

        List<MovimientoDto> movimientos = movimientoService.consultaMovimiento(movimientoParam);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(movimientos.size());

        if (movimientos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, movimientos.size());
        } else {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(movimientos);

        return managerDatatables;
    }

    private ManagerDatatables getTarjetaControlVisibleDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        TarjetaControlParam tarjetaControlParam = new TarjetaControlParam();
        tarjetaControlParam.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
        tarjetaControlParam.setIdAlmacen(Integer.parseInt(request.getParameter("idAlmacen")));
        tarjetaControlParam.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));

        if (request.getParameter("fechaDesde").isEmpty() == false) {
            tarjetaControlParam.setFechaDesde(new Timestamp(Long.parseLong(request.getParameter("fechaDesde"))));
        }
        if (request.getParameter("fechaHasta").isEmpty() == false) {
            tarjetaControlParam.setFechaHasta(new Timestamp(Long.parseLong(request.getParameter("fechaHasta"))));
        }

        List<TarjetaControlDto> movimientos = movimientoService.tarjetaControl(tarjetaControlParam);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(movimientos.size());

        if (movimientos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, movimientos.size());
        } else {
            movimientos = movimientos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(movimientos);

        return managerDatatables;
    }

    private ManagerDatatables getStockGeneralProductosDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        List<StockGeneralProductoDto> productos = productoService.listarStockGeneral(dataTablesParam.sSearch);

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

    private ManagerDatatables getStockAFechaProductosDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        List<ProductoStockFechaDto> productos = productoService.listarProductoAFecha(
                new Timestamp(Long.parseLong(request.getParameter("fecha"))),
                Integer.parseInt(request.getParameter("idAlmacen"))
        );
        
        if(dataTablesParam.sSearch.length() > 0){
             for (int i = 0; i <= productos.size() - 1; ++i) {
                ProductoStockFechaDto c = productos.get(i);
                if ((c.getCodigoSismed() == null ? "": c.getCodigoSismed()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                        || c.getDescripcion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
                } else {
                    productos.remove(i);
                    i = i - 1;
                }
            }
        }

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

    private ManagerDatatables getIngresosPorAlmacenDatatables(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        IngresoAlmacenParam ingresoAlmacenParam = new IngresoAlmacenParam();
        ingresoAlmacenParam.setIdPeriodo(Integer.parseInt(request.getParameter("idPeriodo")));
        ingresoAlmacenParam.setIdAlmacenDestino(Integer.parseInt(request.getParameter("idAlmacenDestino")));
        ingresoAlmacenParam.setIdAlmacenOrigen(Integer.parseInt(request.getParameter("idAlmacenOrigen")));
        ingresoAlmacenParam.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));

        if (request.getParameter("fechaDesde").isEmpty() == false) {
            ingresoAlmacenParam.setFechaDesde(new Timestamp(Long.parseLong(request.getParameter("fechaDesde"))));
        }
        if (request.getParameter("fechaHasta").isEmpty() == false) {
            ingresoAlmacenParam.setFechaHasta(new Timestamp(Long.parseLong(request.getParameter("fechaHasta"))));
        }

        List<IngresoAlmacenDto> productos = movimientoService.listarIngresoPorAlmacen(ingresoAlmacenParam);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(productos.size());
        
        if(dataTablesParam.sSearch.length() > 0){
            
             for (int i = 0; i <= productos.size() - 1; ++i) {
                IngresoAlmacenDto c = productos.get(i);
                if ((c.getCodigoSismed() == null ? "": c.getCodigoSismed()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                        || c.getDescripcion().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
                } else {
                    productos.remove(i);
                    i = i - 1;
                }
            }
        }

        if (productos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productos = productos.subList(dataTablesParam.iDisplayStart, productos.size());
        } else {
            productos = productos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(productos);

        return managerDatatables;
    }

    private ManagerDatatables getStockGeneralProductosDetalle(HttpServletRequest request, HttpServletResponse response){
                        
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));

        List<DetalleStockPorAlmacen> detalleStockPorAlmacen = productoService.listarStockPorAlmacenes(idProducto);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(0);

        /*if (productos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productos = productos.subList(dataTablesParam.iDisplayStart, productos.size());
        } else {
            productos = productos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }*/

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(detalleStockPorAlmacen);

        return managerDatatables;
    }
    
    private ManagerDatatables getProductosDetalleFecha(HttpServletRequest request, HttpServletResponse response){
                        
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        Timestamp fecha = new Timestamp(Long.parseLong(request.getParameter("fecha")));

        List<DetalleStockEnAlmacen> detalleStockPorAlmacen = productoService.listarStockPorAlmacen(idProducto, idAlmacen, fecha);

        managerDatatables.setiTotalRecords(0);

        managerDatatables.setiTotalDisplayRecords(0);

        /*if (productos.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            productos = productos.subList(dataTablesParam.iDisplayStart, productos.size());
        } else {
            productos = productos.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }*/

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(detalleStockPorAlmacen);

        return managerDatatables;
    }
    
    @RequestMapping(value = "/NotaIngreso", method = RequestMethod.GET)
    public ModelAndView listarIngreso() {
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        
        ModelMap model = new ModelMap();        
        model.put("anio", periodo.getAnio());
        model.put("mes", periodo.getMesEntero());
        
        return new ModelAndView("NotaIngreso", model);
    }

    @RequestMapping(value = "/NotaSalida", method = RequestMethod.GET)
    public ModelAndView listarSalida() {
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        
        ModelMap model = new ModelMap();
        model.put("anio", periodo.getAnio());
        model.put("mes", periodo.getMesEntero());
        
        return new ModelAndView("NotaSalida", model);
    }

    @RequestMapping(value = "/Movimiento", method = RequestMethod.GET)
    public ModelAndView consultaMovimiento() {

        ModelMap model = new ModelMap();
        model.put("conceptos", conceptoService.listar());
        model.put("anios", periodoService.listarAnios());

        return new ModelAndView("Movimiento", model);
    }

    @RequestMapping(value = "/TarjetaControlVisible", method = RequestMethod.GET)
    public ModelAndView tarjetaControlVisible() {

        ModelMap model = new ModelMap();        
        model.put("anios", periodoService.listarAnios());
        model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
        model.put("unidadesMedida", unidadMedidaService.listar());
        model.put("tiposProducto", tipoProductoService.listar());
        

        return new ModelAndView("TarjetaControlVisible", model);
    }

    @RequestMapping(value = "/StockGeneralProductos", method = RequestMethod.GET)
    public ModelAndView stockGeneralProductos() {
        return new ModelAndView("StockGeneralProductos");
    }

    @RequestMapping(value = "/StockProductoFecha", method = RequestMethod.GET)
    public ModelAndView stockProductosAFecha() {
        return new ModelAndView("StockProductoFecha");
    }

    @RequestMapping(value = "/ProductosIngresados", method = RequestMethod.GET)
    public ModelAndView productosIngresados() {
        ModelMap model = new ModelMap();
        model.put("anios", periodoService.listarAnios());
        return new ModelAndView("ProductosIngresados", model);
    }

    @RequestMapping(value = "/StockProductoFecha/JSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables stockProductosAFechaJSON(HttpServletRequest request, HttpServletResponse response) {
        return getStockAFechaProductosDatatables(request, response);
    }
    
    @RequestMapping(value = "/StockProductoFecha/DetalleAlmacen", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables stockProductosDetalleAFechaJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductosDetalleFecha(request, response);
    }
    
    
    @RequestMapping(value = "/StockProductoFecha/PDF", method = RequestMethod.GET)    
    public ModelAndView stockProductosAFechaPDF(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getStockAFechaProductosDatatables(request, response);
        return new ModelAndView("StockProductoFechaPDF", "Data", managerDatatables.getAaData());
    }
    
    @RequestMapping(value = "/StockProductoFecha/Excel", method = RequestMethod.GET)    
    public ModelAndView stockProductosAFechaExcel(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getStockAFechaProductosDatatables(request, response);
        return new ModelAndView("StockProductoFechaExcel", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/ProductosIngresados/JSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables productosIngresadosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getIngresosPorAlmacenDatatables(request, response);
    }
    
    @RequestMapping(value = "/ProductosIngresados/PDF", method = RequestMethod.GET)
    public ModelAndView productosIngresadosPDF(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getIngresosPorAlmacenDatatables(request, response);
        return new ModelAndView("IngresoAlmacenPDF", "Data", managerDatatables.getAaData());
    }
    
    @RequestMapping(value = "/ProductosIngresados/Excel", method = RequestMethod.GET)
    public ModelAndView productosIngresadosExcel(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getIngresosPorAlmacenDatatables(request, response);
        return new ModelAndView("IngresoAlmacenExcel", "Data", managerDatatables.getAaData());
    }
    
    @RequestMapping(value = "/StockGeneralProductos/JSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables stockGeneralProductos(HttpServletRequest request, HttpServletResponse response) {
        return getStockGeneralProductosDatatables(request, response);
    }
    
    @RequestMapping(value = "/StockGeneralProductos/DetalleAlmacenes", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables stockGeneralProductosDetalle(HttpServletRequest request, HttpServletResponse response) {
        return getStockGeneralProductosDetalle(request, response);
    }
        
    @RequestMapping(value = "/StockGeneralProductos/PDF", method = RequestMethod.GET)  
    public ModelAndView rptPdfStockGeneralProductos(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getStockGeneralProductosDatatables(request, response);
        return new ModelAndView("StockGeneralProductoPDF", "Data", managerDatatables.getAaData());        
    }    
    
    @RequestMapping(value = "/StockGeneralProductos/Excel", method = RequestMethod.GET)
    public ModelAndView rptExcelStockGeneralProductos(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getStockGeneralProductosDatatables(request, response);
        return new ModelAndView("StockGeneralProductoExcel", "Data", managerDatatables.getAaData());        
    }

    @RequestMapping(value = "/TarjetaControlVisible/JSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables tarjetaControlVisibleJSON(HttpServletRequest request, HttpServletResponse response) {
        return getTarjetaControlVisibleDatatables(request, response);
    }
    
    @RequestMapping(value = "/TarjetaControlVisible/PDF", method = RequestMethod.GET)  
    public ModelAndView rptPdfTarjetaControlVisible(HttpServletRequest request, HttpServletResponse response) {
         ManagerDatatables managerDatatables = getTarjetaControlVisibleDatatables(request, response);

        return new ModelAndView("TarjetaControlVisiblePDF", "Data", managerDatatables.getAaData());        
    }    
    
    @RequestMapping(value = "/TarjetaControlVisible/Excel", method = RequestMethod.GET)
    public ModelAndView rptExcelTarjetaControlVisible(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getTarjetaControlVisibleDatatables(request, response);

        return new ModelAndView("TarjetaControlVisibleExcel", "Data", managerDatatables.getAaData());        
    }

    @RequestMapping(value = "/Movimiento/movimientosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerMovimientosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getMovimientosDatatables(request, response);
    }
    
    @RequestMapping(value = "/Movimiento/movimientosPDF", method = RequestMethod.GET)  
    public ModelAndView rptPdfMovimiento(HttpServletRequest request, HttpServletResponse response) {
         ManagerDatatables managerDatatables = getMovimientosDatatables(request, response);

        return new ModelAndView("MovimientoPDF", "Data", managerDatatables.getAaData());        
    }    
    
    @RequestMapping(value = "/Movimiento/movimientosExcel", method = RequestMethod.GET)
    public ModelAndView rptExcelMovimiento(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = getMovimientosDatatables(request, response);

        return new ModelAndView("MovimientoExcel", "Data", managerDatatables.getAaData());        
    }
    
    @RequestMapping(value = "/NotaIngreso/ingresosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerIngresoJSON(HttpServletRequest request, HttpServletResponse response) {
        return getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.INGRESO);
    }

    @RequestMapping(value = "/NotaSalida/salidasJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerSalidasJSON(HttpServletRequest request, HttpServletResponse response) {
        return getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.SALIDA);
    }

    @RequestMapping(value = "/NotaIngreso/registrar", method = RequestMethod.GET)
    public ModelAndView registrarIngresoGET(HttpSession session) {
        session.setAttribute(sessionDetalleIngreso, null);
        Periodo periodo = periodoService.obtenerPeriodoActivo();
        GregorianCalendar grego = new GregorianCalendar();
        int anioActual = grego.get(Calendar.YEAR);
        int mesActual = grego.get(Calendar.MONTH) + 1;
        ModelMap model = new ModelMap();
        
        if(anioActual == periodo.getAnio()
                && periodo.getMesEntero() == mesActual){
           
           model.put("periodo", periodo);
           model.put("fechaRegistro", fechaHoy.getTime());
           model.put("conceptos", conceptoService.listarActivos());
           model.put("tiposCompra", tipoCompraService.listarActivos());
           model.put("tiposProceso", tipoProcesoService.listarActivos());

           return new ModelAndView("NotaIngreso/registrar", model);   
        }else{
            model.put("periodo", periodo);
            return new ModelAndView("NotaIngreso/periodoNoValido", model);
        }        
    }

    @RequestMapping(value = "/NotaIngreso/registrar/{tipoRegistro}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarIngresoPOST(@RequestBody Movimiento movimiento, HttpSession session, 
                                                @PathVariable("tipoRegistro")int tR) {

        List<MovimientoProducto> movimientoProductos;
        try {    
            //1 = producto, 2 = producto con stock
            if(tR==2){
                movimientoProductos = getMovimientoDetalle(session, sessionDetalleSalida);
            }else{
                movimientoProductos = getMovimientoDetalle(session, sessionDetalleIngreso);
            }
            
            movimiento.setFechaRegistro(fechaHoy);            
            

            movimientoService.insertarIngresoConDetalle(movimiento, movimientoProductos);

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaIngreso/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarIngresoGET(@PathVariable int id, HttpSession session, HttpServletRequest request) {
        try {

            Movimiento movimiento = movimientoService.obtenerPorId(id);

            IAlmacen almacenOrigen = movimiento.getAlmacenOrigen();
            int indiceAlmacenHijo = almacenOrigen.getIdAlmacen() - almacenOrigen.getIdAlmacenPadre();
            
            List<MovimientoProducto> movimientoDetalles = movimientoProductoService.listarPorMovimiento(movimiento.getIdMovimiento());

            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleIngreso);

            ModelMap model = new ModelMap();
            Periodo periodo = new Periodo();
            periodo.setIdPeriodo(movimiento.getIdPeriodo());
            
            model.put("periodo", periodo);
            model.put("movimiento", movimiento);

            model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
            model.put("tiposProducto", tipoProductoService.listar());
            model.put("unidadesMedida", unidadMedidaService.listar());
            
            
            model.put("tiposCompra", tipoCompraService.listarActivos());
            model.put("tiposProceso", tipoProcesoService.listarActivos());
            model.put("conceptos", conceptoService.listarActivos(TipoMovimientoConcepto.INGRESO));
            model.put("documentos", conceptoTipoDocumentoMovService.listar(movimiento.getConcepto().getIdConcepto()));
            model.put("indiceAlmacenHijo", indiceAlmacenHijo);
            model.put("documentosOrigen", documentoOrigenService.listar());
            
            return new ModelAndView("NotaIngreso/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/NotaIngreso");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/NotaIngreso/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarIngresoPOST(@RequestBody Movimiento movimiento, HttpSession session) {

        try {

            List<? extends MovimientoProducto> movimientoProductos = getMovimientoDetalle(session, sessionDetalleIngreso);

            movimientoService.modificarIngresoConDetalle(movimiento, (List<MovimientoProducto>) movimientoProductos);

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaSalida/registrar", method = RequestMethod.GET)
    public ModelAndView registrarSalidaGET(HttpSession session) {
        session.setAttribute(sessionDetalleSalida, null);
          
        ModelMap model = new ModelMap();
        model.put("periodo", periodoService.obtenerPeriodoActivo());
        model.put("fechaRegistro", fechaHoy.getTime());
        model.put("conceptos", conceptoService.listarActivos(TipoMovimientoConcepto.SALIDA));

        return new ModelAndView("NotaSalida/registrar", model);
    }

    @RequestMapping(value = "/NotaSalida/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarSalidaPOST(@RequestBody Movimiento movimiento, HttpSession session) {

        try {

            movimiento.setFechaRegistro(fechaHoy);
            List<MovimientoProducto> movimientoProductos = getMovimientoDetalle(session, sessionDetalleSalida);
                       
            movimientoService.insertarSalidaConDetalle(movimiento, movimientoProductos);

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();

        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaSalida/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarSalidaGET(@PathVariable int id, HttpSession session, HttpServletRequest request) {
        try {

            Movimiento movimiento = movimientoService.obtenerPorId(id);
 
            List<MovimientoProducto> movimientoDetalles = movimientoProductoService.listarPorMovimiento(movimiento.getIdMovimiento());

            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleSalida);

            ModelMap model = new ModelMap();
            Periodo periodo = new Periodo();
            periodo.setIdPeriodo(movimiento.getIdPeriodo());
            model.put("movimiento", movimiento);
            model.put("periodo", periodo);
            
            model.put("conceptos", conceptoService.listarActivos(TipoMovimientoConcepto.SALIDA));
            model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
            model.put("tiposProducto", tipoProductoService.listar());
            model.put("unidadesMedida", unidadMedidaService.listar());
            model.put("documentos", conceptoTipoDocumentoMovService.listar(movimiento.getConcepto().getIdConcepto()));
            
            return new ModelAndView("NotaSalida/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/NotaSalida");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/NotaSalida/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarSalidaPOST(@RequestBody Movimiento movimiento, HttpSession session) {

        try {

            List<? extends MovimientoProducto> movimientoProductos = getMovimientoDetalle(session, sessionDetalleSalida);

            movimientoService.modificarSalidaConDetalle(movimiento, (List<MovimientoProducto>) movimientoProductos);

            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaIngreso/agregarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse agregarDetalleIngreso(@RequestBody MovimientoProducto movimientoDetalle, HttpServletRequest request, HttpSession session) {
        jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleIngreso);

        if (movimientoDetalle.getCantidad() <= 0) {
            mensajeRespuesta.add("La cantidad debe ser mayor a 0.");
        }
        
        if (movimientoDetalle.getIdProducto() == 0) {
            mensajeRespuesta.add("El producto es un campo requerido.");
        }

        if (movimientoDetalle.getLote().isEmpty()) {
            mensajeRespuesta.add("El lote es un campo requerido.");
        }

        if (movimientoDetalle.getRegistroSanitario().isEmpty()) {
            mensajeRespuesta.add("El registro sanitario es un campo requerido.");
        }

        if (movimientoDetalle.getFechaVencimiento() == null) {
            mensajeRespuesta.add("La fecha de vencimiento es un campo requerido.");
        }

        if (movimientoDetalle.getPrecio() == null) {
            mensajeRespuesta.add("El precio es un campo requerido.");
        } else {
            if (movimientoDetalle.getPrecio().compareTo(BigDecimal.ZERO) == 0
                    || movimientoDetalle.getPrecio().compareTo(BigDecimal.ZERO) == -1) {
                mensajeRespuesta.add("El precio debe ser mayor a 0.");
            }
        }

        if (movimientoDetalle.getIdProducto() > 0) {
            for (MovimientoProducto movimientoDetalleTemp : movimientoDetalles) {
                if (movimientoDetalleTemp.getIdProducto() == movimientoDetalle.getIdProducto()
                        && movimientoDetalleTemp.getLote().equalsIgnoreCase(movimientoDetalle.getLote())
                        && movimientoDetalleTemp.getFechaVencimiento() == movimientoDetalle.getFechaVencimiento()) {
                    mensajeRespuesta.add("Ya está agregado este producto, con lote y fecha vencimiento.");
                    break;
                }
            }
        }        

        if (mensajeRespuesta.isEmpty() == false) {
            jsonResponse.setEstado(false);
            jsonResponse.setMensajesRepuesta(mensajeRespuesta);
        } else {
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se agregó el producto."));           
            
            movimientoDetalle.setTotal(movimientoDetalle.getPrecio().multiply(new BigDecimal(movimientoDetalle.getCantidad())));
            movimientoDetalles.add(movimientoDetalle);            
            
            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleIngreso);
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaSalida/agregarDetalle/{tipoMovimiento}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse agregarDetalleSalida(@RequestBody MovimientoProducto movimientoDetalle, HttpServletRequest request, HttpSession session,
                                                @PathVariable("tipoMovimiento")int tipoMov) {
        jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        String idAlmacen = request.getParameter("idAlmacen");
        String idConcepto = request.getParameter("idConcepto");
        Concepto concepto = new Concepto();
        Timestamp fechaRegistro = new Timestamp(Long.parseLong(request.getParameter("fechaRegistro")));
        List<ProductoPrecio> productoPrecios = productoPrecioService.listarPorProducto(movimientoDetalle.getIdProducto());

        try {
            concepto = conceptoService.obtenerPorId(Integer.parseInt(idConcepto));
        } catch (BusinessException ex) {

        }
        
        BigDecimal precio = null;
            if (productoPrecios.isEmpty()) {  
                mensajeRespuesta.add("No se ha configurado los precios de este producto para las notas de salida.");
            } else {
                long fechaMasCercana = 0;
                for (int i = 0; i < productoPrecios.size(); ++i) {
                
                
                    if (i == 0) {
                        fechaMasCercana = fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime();
                        switch (concepto.getTipoPrecio()) {
                            case ADQUISICION:
                                precio = productoPrecios.get(i).getPrecioAdquisicion();
                                break;
                            case DISTRIBUCION:
                                precio = productoPrecios.get(i).getPrecioDistribucion();
                                break;
                            case OPERACION:
                                precio = productoPrecios.get(i).getPrecioOperacion();
                                break;
                        }
                    }
                    if (fechaMasCercana > (fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime())) {
                        fechaMasCercana = fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime();

                        switch (concepto.getTipoPrecio()) {
                            case ADQUISICION:
                                precio = productoPrecios.get(i).getPrecioAdquisicion();
                                break;
                            case DISTRIBUCION:
                                precio = productoPrecios.get(i).getPrecioDistribucion();
                                break;
                            case OPERACION:
                                precio = productoPrecios.get(i).getPrecioOperacion();
                                break;
                        }
                    }
                }
            }
        
        
        if(precio == null && productoPrecios.isEmpty() == false){
            mensajeRespuesta.add("No se ha configurado los precios de este producto para las notas de salida.");
        }
        
        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleSalida);
        
        //Validar que no se repita el producto
        for(MovimientoProducto mp : movimientoDetalles){
            if(mp.getIdProducto()==movimientoDetalle.getIdProducto()){
                mensajeRespuesta.add("Ya ingreso este producto. Modifique el producto ingresado.");
            }
        }

        if (movimientoDetalle.getCantidad() <= 0) {
            mensajeRespuesta.add("La cantidad debe ser mayor a 0.");
        }

        if (movimientoDetalle.getIdProducto() > 0 && precio != null) {
            int cantidadSolicitada = movimientoDetalle.getCantidad();

            List<MovimientoProductoStock> movimientosProducto = movimientoProductoService.obtenerMovimientos(Integer.parseInt(idAlmacen), movimientoDetalle.getIdProducto());
            int cantidadActual = 0;

            for (int i = 0; i < movimientosProducto.size(); ++i) {
                cantidadActual += movimientosProducto.get(i).getCantidad();
            }

            if (cantidadActual < cantidadSolicitada) {
                mensajeRespuesta.add("No hay stock suficiente. ");
            } else {
                int cantidadRestante = cantidadSolicitada;

                for (int i = 0; i < movimientosProducto.size(); ++i) {
                    MovimientoProducto movimientoProducto = new MovimientoProducto();
                    movimientoProducto.setIdProducto(movimientosProducto.get(i).getIdProducto());
                    movimientoProducto.setFechaVencimiento(movimientosProducto.get(i).getFechaVencimiento());
                    movimientoProducto.setNombreProducto(movimientosProducto.get(i).getNombreProducto());

                    //2 = Nota de Salida
                    if(tipoMov==2){
                        String lote = movimientosProducto.get(i).getLote();

                        String registroSanitario = movimientoProductoService.obtenerDetallePorLote(movimientoProducto.getIdProducto(), lote).getRegistroSanitario();
                        movimientoProducto.setPrecio(precio);
                        movimientoProducto.setLote(lote);
                        movimientoProducto.setRegistroSanitario(registroSanitario);
                    
                    }else{
                    
                        String lote = movimientosProducto.get(i).getLote();
                        String registroSanitario = movimientoProductoService.obtenerDetallePorLote(movimientoProducto.getIdProducto(), lote).getRegistroSanitario();
                        BigDecimal precioLote = movimientoProductoService.obtenerPorLote(movimientoProducto.getIdProducto(), lote).getPrecio();
                        movimientoProducto.setPrecio(precioLote);
                        movimientoProducto.setLote(lote);
                        movimientoProducto.setRegistroSanitario(registroSanitario);

                    }
                    
                    if (cantidadRestante >= movimientosProducto.get(i).getCantidad() && cantidadRestante > 0) {
                        movimientoProducto.setCantidad(movimientosProducto.get(i).getCantidad());
                        movimientoProducto.setTotal(precio.multiply(new BigDecimal(movimientosProducto.get(i).getCantidad())));
                        cantidadRestante = cantidadRestante - movimientosProducto.get(i).getCantidad();
                        movimientoDetalles.add(movimientoProducto);
                    } else if (cantidadRestante < movimientosProducto.get(i).getCantidad() && cantidadRestante > 0) {
                        movimientoProducto.setCantidad(cantidadRestante);
                        movimientoProducto.setTotal(precio.multiply(new BigDecimal(cantidadRestante)));
                        movimientoDetalles.add(movimientoProducto);
                        cantidadRestante = 0;
                    }
                    
                    if(cantidadRestante == 0){
                        break;
                    }
                }
            }
        }

        if (mensajeRespuesta.isEmpty() == false) {
            jsonResponse.setEstado(false);
            jsonResponse.setMensajesRepuesta(mensajeRespuesta);
        } else {
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se agregó el producto."));

            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleSalida);
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/NotaIngreso/quitarDetalle/{idProducto}", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> quitarDetalleIngreso(@PathVariable int idProducto, HttpSession session) {

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleIngreso);

        for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
            if (movimientoDetalles.get(i).getIdProducto() == idProducto) {
                movimientoDetalles.remove(i);
                break;
            }
        }

        setMovimientoDetalle(movimientoDetalles, session, sessionDetalleIngreso);

        return movimientoDetalles;
    }
    
    @RequestMapping(value = "/NotaIngreso/modificarDetalle/{idProducto}",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarDetalle(@PathVariable int idProducto, HttpSession session){
        jsonResponse = new JsonResponse();
        MovimientoProducto movimiento = new MovimientoProducto();
        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleIngreso);
        
        for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
            if (movimientoDetalles.get(i).getIdProducto() == idProducto) {
                movimiento = movimientoDetalles.get(i);
                break;
            }
        }
       jsonResponse.setData(movimiento);
        
        return jsonResponse;
    }

    
    @RequestMapping(value = "/NotaIngreso/modificarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarDetalleIngreso(@RequestBody MovimientoProducto movimientoDetalle, HttpServletRequest request, HttpSession session) {
        jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleIngreso);

        if (movimientoDetalle.getCantidad() <= 0) {
            mensajeRespuesta.add("La cantidad debe ser mayor a 0.");
        }
        
        if (movimientoDetalle.getIdProducto() == 0) {
            mensajeRespuesta.add("El producto es un campo requerido.");
        }

        if (movimientoDetalle.getLote().isEmpty()) {
            mensajeRespuesta.add("El lote es un campo requerido.");
        }

        if (movimientoDetalle.getRegistroSanitario().isEmpty()) {
            mensajeRespuesta.add("El registro sanitario es un campo requerido.");
        }

        if (movimientoDetalle.getFechaVencimiento() == null) {
            mensajeRespuesta.add("La fecha de vencimiento es un campo requerido.");
        }

        if (movimientoDetalle.getPrecio() == null) {
            mensajeRespuesta.add("El precio es un campo requerido.");
        } else {
            if (movimientoDetalle.getPrecio().compareTo(BigDecimal.ZERO) == 0
                    || movimientoDetalle.getPrecio().compareTo(BigDecimal.ZERO) == -1) {
                mensajeRespuesta.add("El precio debe ser mayor a 0.");
            }
        }
        

        if (mensajeRespuesta.isEmpty() == false) {
            jsonResponse.setEstado(false);
            jsonResponse.setMensajesRepuesta(mensajeRespuesta);
        } else {
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se agregó el producto."));  
            
            for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
                if (movimientoDetalles.get(i).getIdProducto() == movimientoDetalle.getIdProducto()) {
                    movimientoDetalles.remove(i);
                    break;
                }
            }         
            
            movimientoDetalle.setTotal(movimientoDetalle.getPrecio().multiply(new BigDecimal(movimientoDetalle.getCantidad())));
            movimientoDetalles.add(movimientoDetalle);            
            
            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleIngreso);
        }

        return jsonResponse;
    }
        
    @RequestMapping(value = "/NotaSalida/modificarDetalle/{idProducto}",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarDetalleSalida(@PathVariable int idProducto, HttpSession session){
        jsonResponse = new JsonResponse();
        MovimientoProducto movimiento = new MovimientoProducto();
        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleSalida);
        
        for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
            if (movimientoDetalles.get(i).getIdProducto() == idProducto) {
                movimiento = movimientoDetalles.get(i);
                break;
            }
        }
       jsonResponse.setData(movimiento);
        
        return jsonResponse;
    }
    @RequestMapping(value = "/NotaSalida/modificarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarDetalleSalida(@RequestBody MovimientoProducto movimientoDetalle, HttpServletRequest request, HttpSession session) {
        jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        MovimientoProducto movimientoDetalleOld = new MovimientoProducto();
        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleSalida);
        int indice = 0;
        for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
            if (movimientoDetalles.get(i).getIdProducto() == movimientoDetalle.getIdProducto()) {
                movimientoDetalleOld = movimientoDetalles.get(i);
                indice = i;
                break;
            }
        }
        /*DetalleSalida*/
        String idAlmacen = request.getParameter("idAlmacen");
        String idConcepto = request.getParameter("idConcepto");
        Concepto concepto = new Concepto();
        Timestamp fechaRegistro = fechaHoy;
        List<ProductoPrecio> productoPrecios = productoPrecioService.listarPorProducto(movimientoDetalle.getIdProducto());

        try {
            concepto = conceptoService.obtenerPorId(Integer.parseInt(idConcepto));
        } catch (BusinessException ex) {

        }
        
        BigDecimal precio = null;
        if (productoPrecios.isEmpty()) {
            mensajeRespuesta.add("No se ha configurado los precios de este producto para las notas de salida.");
        } else {
            long fechaMasCercana = 0;
            for (int i = 0; i < productoPrecios.size(); ++i) {
               
                if (i == 0) {
                    fechaMasCercana = fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime();
                    switch (concepto.getTipoPrecio()) {
                        case ADQUISICION:
                            precio = productoPrecios.get(i).getPrecioAdquisicion();
                            break;
                        case DISTRIBUCION:
                            precio = productoPrecios.get(i).getPrecioDistribucion();
                            break;
                        case OPERACION:
                            precio = productoPrecios.get(i).getPrecioOperacion();
                            break;
                    }
                }
                if (fechaMasCercana > (fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime())) {
                    fechaMasCercana = fechaRegistro.getTime() - productoPrecios.get(i).getFechaVigencia().getTime();
                    
                    switch (concepto.getTipoPrecio()) {
                        case ADQUISICION:
                            precio = productoPrecios.get(i).getPrecioAdquisicion();
                            break;
                        case DISTRIBUCION:
                            precio = productoPrecios.get(i).getPrecioDistribucion();
                            break;
                        case OPERACION:
                            precio = productoPrecios.get(i).getPrecioOperacion();
                            break;
                    }
                }
            }
        }
        
        if(precio == null && productoPrecios.isEmpty() == false){
            mensajeRespuesta.add("No se ha configurado los precios de este producto para las notas de salida.");
        }

        if (movimientoDetalle.getCantidad() <= 0) {
            mensajeRespuesta.add("La cantidad debe ser mayor a 0.");
        }

        if (movimientoDetalle.getIdProducto() > 0 && precio != null) {
            int cantidadSolicitada =  0;
            if(movimientoDetalle.getCantidad() > movimientoDetalleOld.getCantidad()){
            cantidadSolicitada = movimientoDetalle.getCantidad();
            

            List<MovimientoProductoStock> movimientosProducto = movimientoProductoService.obtenerMovimientos(Integer.parseInt(idAlmacen), movimientoDetalle.getIdProducto());
            int cantidadActual = 0;

            for (int i = 0; i < movimientosProducto.size(); ++i) {
                cantidadActual += movimientosProducto.get(i).getCantidad();
            }

            if (cantidadActual < cantidadSolicitada) {
                mensajeRespuesta.add("No hay stock suficiente.");
            } else {
                int cantidadRestante = cantidadSolicitada;

                for (int i = 0; i < movimientosProducto.size(); ++i) {
                    MovimientoProducto movimientoProducto = new MovimientoProducto();
                    movimientoProducto.setIdProducto(movimientosProducto.get(i).getIdProducto());
                    movimientoProducto.setPrecio(precio);
                    movimientoProducto.setLote(movimientosProducto.get(i).getLote());
                    movimientoProducto.setFechaVencimiento(movimientosProducto.get(i).getFechaVencimiento());
                    movimientoProducto.setRegistroSanitario("");
                    movimientoProducto.setNombreProducto(movimientosProducto.get(i).getNombreProducto());

                    if (cantidadRestante >= movimientosProducto.get(i).getCantidad() && cantidadRestante > 0) {
                        movimientoProducto.setCantidad(movimientosProducto.get(i).getCantidad());
                        movimientoProducto.setTotal(precio.multiply(new BigDecimal(movimientosProducto.get(i).getCantidad())));
                        cantidadRestante = cantidadRestante - movimientosProducto.get(i).getCantidad();
                        movimientoDetalles.add(movimientoProducto);
                    } else if (cantidadRestante < movimientosProducto.get(i).getCantidad() && cantidadRestante > 0) {
                        movimientoProducto.setCantidad(cantidadRestante);
                        movimientoProducto.setTotal(precio.multiply(new BigDecimal(cantidadRestante)));
                        movimientoDetalles.add(movimientoProducto);
                        cantidadRestante = 0;
                    }
                    
                    if(cantidadRestante == 0){
                        break;
                    }
                }/*for*/
            }/*else*/
            } else if(movimientoDetalle.getCantidad() == movimientoDetalleOld.getCantidad()){
                mensajeRespuesta.add("La cantidad es la misma.");
            }else if (movimientoDetalle.getCantidad() < movimientoDetalleOld.getCantidad()){
                movimientoDetalleOld.setCantidad(movimientoDetalle.getCantidad());
                movimientoDetalleOld.setTotal(precio.multiply(new BigDecimal(movimientoDetalle.getCantidad())));
                movimientoDetalles.add(movimientoDetalleOld);
            }
        }
        /**/
        
        if (mensajeRespuesta.isEmpty() == false) {
            jsonResponse.setEstado(false);
            jsonResponse.setMensajesRepuesta(mensajeRespuesta);
        } else {
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se modificó el producto."));
            movimientoDetalles.remove(indice);
            
            setMovimientoDetalle(movimientoDetalles, session, sessionDetalleSalida);
        }

        return jsonResponse;
    }
        
    @RequestMapping(value = "/NotaSalida/quitarDetalle/{idProducto}", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> quitarDetalleSalida(@PathVariable int idProducto, HttpSession session) {

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleSalida);

        for (int i = 0; i <= movimientoDetalles.size() - 1; ++i) {
            if (movimientoDetalles.get(i).getIdProducto() == idProducto) {
                movimientoDetalles.remove(i);
                break;
            }
        }

        setMovimientoDetalle(movimientoDetalles, session, sessionDetalleSalida);

        return movimientoDetalles;
    }

    @RequestMapping(value = "/NotaIngreso/cargarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> cargarDetalleIngreso(HttpSession session) {
        return getMovimientoDetalle(session, sessionDetalleIngreso);
    }

    @RequestMapping(value = "/NotaSalida/cargarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> cargarDetalleSalida(HttpSession session) {
        return getMovimientoDetalle(session, sessionDetalleSalida);
    }

    @RequestMapping(value = "/NotaIngreso/borrarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> borrarDetalleIngreso(HttpSession session) {

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleIngreso);
        movimientoDetalles.clear();

        setMovimientoDetalle(movimientoDetalles, session, sessionDetalleIngreso);

        return movimientoDetalles;
    }

    @RequestMapping(value = "/NotaSalida/borrarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<MovimientoProducto> borrarDetalleSalida(HttpSession session) {

        List<MovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetalleSalida);
        movimientoDetalles.clear();

        setMovimientoDetalle(movimientoDetalles, session, sessionDetalleSalida);

        return movimientoDetalles;
    }

    @RequestMapping(value = "/Movimiento/stock", method = RequestMethod.POST)
    @ResponseBody
    public int stockPorProductoYAlmacen(@RequestParam int idProducto, @RequestParam int idAlmacen, @RequestParam String lote) {
        return movimientoProductoService.stockPorProductoYAlmacen(idProducto, idAlmacen, lote);
    }

    @RequestMapping(value = "/Movimiento/DetallePorLote", method = RequestMethod.POST)
    @ResponseBody
    public DetallePorLoteDto obtenerDetallePorLote(@RequestParam int idProducto, @RequestParam String lote) {
        return movimientoProductoService.obtenerDetallePorLote(idProducto, lote);
    }

    @RequestMapping(value = "/Movimiento/guiasRemision", method = RequestMethod.POST)
    @ResponseBody
    public List<Movimiento> listarGuiasRemision(@RequestBody GuiasRemisionParam guiasRemisionParam) {
        return movimientoService.listarGuiasRemision(guiasRemisionParam);
    }

    @RequestMapping(value = "/Movimiento/anularGuia/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse anularGuiaRemision(@PathVariable int id) {
        jsonResponse = new JsonResponse();
        try {
            movimientoService.anularGuia(id);
            jsonResponse.setEstado(true);
            jsonResponse.setMensajesRepuesta(Arrays.asList("Se anuló correctamente."));
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    private List<MovimientoProducto> getMovimientoDetalle(HttpSession session, String nombreAtributo) {

        List<MovimientoProducto> movimientoDetalles;

        if (session.getAttribute(nombreAtributo) == null) {
            movimientoDetalles = new ArrayList<MovimientoProducto>();
        } else {
            movimientoDetalles = (List<MovimientoProducto>) session.getAttribute(nombreAtributo);
        }

        return movimientoDetalles;
    }

    private void setMovimientoDetalle(List<MovimientoProducto> movimientoDetalles, HttpSession session, String nomAtributo) {
        session.setAttribute(nomAtributo, movimientoDetalles);
    }

    @RequestMapping(value = "/NotaIngreso/pdf", method = RequestMethod.GET)
    public ModelAndView rptIngresoPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.INGRESO);

        return new ModelAndView("IngresoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/NotaIngreso/imprimir/{id}", method = RequestMethod.GET)
    public ModelAndView rptNotaIngresoConDetalle(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

        try {
            Movimiento movimiento = movimientoService.obtenerPorId(id);
            List<MovimientoProducto> movimientosProducto = movimientoProductoService.listarPorMovimiento(id);

            ModelMap model = new ModelMap();
            model.put("movimiento", movimiento);
            model.put("movimientosProducto", movimientosProducto);

            return new ModelAndView("NotaIngresoPDF", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/NotaSalida");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/NotaSalida/imprimir/{id}", method = RequestMethod.GET)
    public ModelAndView rptNotaSalidaConDetalle(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

        try {
            Movimiento movimiento = movimientoService.obtenerPorId(id);
            List<MovimientoProducto> movimientosProducto = movimientoProductoService.listarPorMovimiento(id);

            ModelMap model = new ModelMap();
            model.put("movimiento", movimiento);
            model.put("movimientosProducto", movimientosProducto);

            return new ModelAndView("NotaSalidaPDF", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/NotaSalida");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/NotaSalida/pdf", method = RequestMethod.GET)
    public ModelAndView rptSalidaPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.SALIDA);

        return new ModelAndView("SalidaPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/NotaIngreso/excel", method = RequestMethod.GET)
    public ModelAndView rptIngresoExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.INGRESO);

        return new ModelAndView("IngresoExcel", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/NotaSalida/excel", method = RequestMethod.GET)
    public ModelAndView rptSalidaExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getMovIngresoOSalidaDatatables(request, response, TipoMovimiento.SALIDA);

        return new ModelAndView("SalidaExcel", "Data", managerDatatables.getAaData());
    }
}
