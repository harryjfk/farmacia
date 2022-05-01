package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Concepto;
import pe.gob.minsa.farmacia.domain.MovimientoProducto;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.domain.dto.MovimientoProductoStock;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.services.impl.MovimientoProductoService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimiento;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.PedidoService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpConcepto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpMovimientoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProductoPrecio;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpConceptoService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/notapedido/*")
public class PedidoController {

    @Autowired
    private ConceptoService conceptoService;
    @Autowired
    private GpConceptoService gpConceptoService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private FormaFarmaceuticaService formaFarmaceuticaService;
    @Autowired
    private TipoProductoService tipoProductoService;
    @Autowired
    private UnidadMedidaService unidadMedidaService;
    @Autowired
    private GpProductoService gpProductoService;
    @Autowired
    private MovimientoProductoService movimientoProductoService;
    @Autowired
    private GpAlmacenService almacenService;

    private final String sessionDetallePedido = "detallePedido";

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public ModelAndView procesarPedidos(@PathVariable long idModulo, ModelAndView model, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("viewTitle", "Procesar Nota de Pedido");
            model.addObject("conceptos", conceptoService.listar());
            model.addObject("almacenes", almacenService.listarPorModulo(idModulo));
            model.setViewName("NotaPedido/procesar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "pedidosJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables getPedidosJSON(@PathVariable long idModulo, HttpServletRequest request) {
        DataTablesParam params = DataTablesParamUtility.getParam(request);
        ManagerDatatables manager = new ManagerDatatables();

        int iDisplayStart, iDisplayLength;
        if (params != null) {
            iDisplayStart = params.iDisplayStart;
            iDisplayLength = params.iDisplayLength + 1;
        } else {
            iDisplayStart = 0;
            iDisplayLength = 1;
            params = new DataTablesParam();
            params.sEcho = "2";
        }

        Object[] sort = new Object[]{
            params.iSortingCols == 0 ? null : 1,
            params.iSortColumnIndex,
            params.sSortDirection
        };
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        String search = params.sSearch;
        String periodo = request.getParameter("idPeriodo");
        long idAlmacen = Long.parseLong(request.getParameter("idAlmacenOrigen"));
        long idConcepto = Long.parseLong(request.getParameter("idConcepto"));
        List<GpMovimiento> lista = pedidoService.listarRango(range, search, sort, periodo, idAlmacen, idConcepto);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = pedidoService.contarPaginado(search, periodo, idAlmacen, idConcepto);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "registrar", method = RequestMethod.GET)
    public ModelAndView registrarGET(@PathVariable long idModulo, HttpSession session) {
        session.setAttribute(sessionDetallePedido, null);

        ModelMap model = new ModelMap();
        model.put("periodo", periodoService.obtenerPeriodoActivo());
        List<GpConcepto> conceptosPedido = gpConceptoService.listarConceptosActivos('S');
        model.put("conceptos", conceptosPedido);
        model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
        model.put("tiposProducto", tipoProductoService.listar());
        model.put("unidadesMedida", unidadMedidaService.listar());
        model.put("almacenes", almacenService.listarPorModulo(idModulo));

        return new ModelAndView("NotaPedido/registrar", model);
    }

    @RequestMapping(value = "registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarPOST(@RequestBody GpMovimiento movimiento, HttpServletRequest request) {
        JsonResponse jsonResponse;
        HttpSession session = request.getSession();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            try {
                List<GpMovimientoProducto> movimientoProductos = getMovimientoDetalle(session, sessionDetallePedido);
                movimiento.setUsuarioCreacion(usuario.getIdUsuario());
                boolean ok = pedidoService.insertar(movimiento, movimientoProductos);
                jsonResponse = new JsonResponse();
                if (ok) {
                    jsonResponse.respuestaInsertar();
                } else {
                    jsonResponse = new JsonResponse(false, Arrays.asList("Ha ocurrido un error. No se ha podido guardar"));
                }
            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }
        } else {
            jsonResponse = new JsonResponse(false, Arrays.asList("No tiene permisos para realizar esta acci&oacute;n"));
        }
        return jsonResponse;
    }

    @RequestMapping(value = "modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarSalidaGET(@PathVariable long idModulo, @PathVariable int id, HttpSession session, HttpServletRequest request) {
        GpMovimiento pedido = pedidoService.obtenerPorId(id);
        if (pedido == null) {
            JsonResponse jsonResponse = new JsonResponse(false, Arrays.asList("No existe el pedido solicitado"));
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/" + idModulo + "/notapedido");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
        List<GpMovimientoProducto> movimientoProductos = pedido.getGpMovimientoProductoList();
        setMovimientoDetalle(movimientoProductos, session, sessionDetallePedido);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fReg = sdf.format(pedido.getFechaRegistro());

        ModelMap model = new ModelMap();
        model.put("movimiento", pedido);
        Integer gpp = pedido.getIdPeriodo().getIdPeriodo();
        Periodo periodo = new Periodo();
        periodo.setIdPeriodo(gpp);
        model.put("periodo", periodo);
        List<GpConcepto> conceptosPedido = gpConceptoService.listarConceptosActivos('S');
        model.put("conceptos", conceptosPedido);
        model.put("pedido", pedido);
        model.put("fReg", fReg);
        model.put("idPedido", id);
        model.put("almacenes", almacenService.listarPorModulo(idModulo));
        model.put("formasFarmaceuticas", formaFarmaceuticaService.listar());
        model.put("tiposProducto", tipoProductoService.listar());
        model.put("unidadesMedida", unidadMedidaService.listar());

        return new ModelAndView("NotaPedido/modificar", model);
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarPOST(@RequestBody GpMovimiento movimiento, HttpServletRequest request) {
        JsonResponse jsonResponse;
        HttpSession session = request.getSession();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            try {
                List<GpMovimientoProducto> movimientoProductos = getMovimientoDetalle(session, sessionDetallePedido);
                movimiento.setUsuarioModificacion(usuario.getIdUsuario());
                boolean ok = pedidoService.actualizar(movimiento, movimientoProductos);
                jsonResponse = new JsonResponse();
                if (ok) {
                    jsonResponse.respuestaModificar();
                } else {
                    jsonResponse = new JsonResponse(false, Arrays.asList("Ha ocurrido un error. No se ha podido guardar"));
                }
            } catch (BusinessException e) {
                jsonResponse = new JsonResponse(false, e.getMensajesError());
            }
        } else {
            jsonResponse = new JsonResponse(false, Arrays.asList("No tiene permisos para realizar esta acci&oacute;n"));
        }
        return jsonResponse;
    }
    
    @RequestMapping(value = "cargarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<GpMovimientoProducto> cargarDetalleSalida(HttpSession session) {
        return getMovimientoDetalle(session, sessionDetallePedido);
    }

    @RequestMapping(value = "borrarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public List<GpMovimientoProducto> borrarDetalleSalida(HttpSession session) {

        List<GpMovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetallePedido);
        movimientoDetalles.clear();
        setMovimientoDetalle(movimientoDetalles, session, sessionDetallePedido);
        return movimientoDetalles;
    }

    @RequestMapping(value = "quitarDetalle/{idProducto}", method = RequestMethod.POST)
    @ResponseBody
    public List<GpMovimientoProducto> quitarDetalle(@PathVariable int idProducto, HttpSession session) {
        List<GpMovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetallePedido);
        for (int i = 0; i < movimientoDetalles.size(); i++) {
            if (movimientoDetalles.get(i).getIdProducto().getIdProducto() == idProducto) {
                movimientoDetalles.remove(movimientoDetalles.get(i));
                break;
            }
        }
        setMovimientoDetalle(movimientoDetalles, session, sessionDetallePedido);
        return movimientoDetalles;
    }

    @RequestMapping(value = "agregarDetalle", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse agregarDetalleSalida(@RequestBody MovimientoProducto movimientoDetalle, HttpServletRequest request, HttpSession session) {
        JsonResponse jsonResponse = new JsonResponse();
        List<String> mensajeRespuesta = new ArrayList<String>();
        String idAlmacen = request.getParameter("idAlmacen");
        String idConcepto = request.getParameter("idConcepto");
        Concepto concepto = new Concepto();
        Timestamp fechaRegistro = new Timestamp(Long.parseLong(request.getParameter("fechaRegistro")));
        GpProducto producto = gpProductoService.obtenerPorId(movimientoDetalle.getIdProducto());
        List<GpProductoPrecio> precios = producto.getPrecios();

        try {
            concepto = conceptoService.obtenerPorId(Integer.parseInt(idConcepto));
        } catch (BusinessException ex) {

        }

        BigDecimal precio = null;
        if (precios.isEmpty()) {
            mensajeRespuesta.add("No se han configurado los precios de este producto para las notas de pedido.");
        } else {
            long fechaMasCercana = 0;
            for (int i = 0; i < precios.size(); i++) {

                if (i == 0) {
                    fechaMasCercana = fechaRegistro.getTime() - precios.get(i).getFechaVigencia().getTime();
                    switch (concepto.getTipoPrecio()) {
                        case ADQUISICION:
                            precio = precios.get(i).getPrecioAdquisicion();
                            break;
                        case DISTRIBUCION:
                            precio = precios.get(i).getPrecioDistribucion();
                            break;
                        case OPERACION:
                            precio = precios.get(i).getPrecioOperacion();
                            break;
                    }
                }
                if (fechaMasCercana > (fechaRegistro.getTime() - precios.get(i).getFechaVigencia().getTime())) {
                    fechaMasCercana = fechaRegistro.getTime() - precios.get(i).getFechaVigencia().getTime();

                    switch (concepto.getTipoPrecio()) {
                        case ADQUISICION:
                            precio = precios.get(i).getPrecioAdquisicion();
                            break;
                        case DISTRIBUCION:
                            precio = precios.get(i).getPrecioDistribucion();
                            break;
                        case OPERACION:
                            precio = precios.get(i).getPrecioOperacion();
                            break;
                    }
                }
            }
        }

        if (precio == null && precios.isEmpty() == false) {
            mensajeRespuesta.add("No se ha consigurado los precios de este producto para las notas de salida.");
        }

        List<GpMovimientoProducto> movimientoDetalles = getMovimientoDetalle(session, sessionDetallePedido);

        if (movimientoDetalle.getCantidad() <= 0) {
            mensajeRespuesta.add("La cantidad debe ser mayor a 0.");
        }

        if (movimientoDetalle.getIdProducto() > 0 && precio != null) {
            int cantidadSolicitada = movimientoDetalle.getCantidad();

            List<MovimientoProductoStock> movimientosProductoStocks = movimientoProductoService.obtenerMovimientos(Integer.parseInt(idAlmacen), movimientoDetalle.getIdProducto());
            int cantidadActual = 0;

            for (int i = 0; i < movimientosProductoStocks.size(); ++i) {
                cantidadActual += movimientosProductoStocks.get(i).getCantidad();
            }

            if (cantidadActual < cantidadSolicitada) {
                mensajeRespuesta.add("No hay stock suficiente.");
            } else {
                int cantidadRestante = cantidadSolicitada;

                for (MovimientoProductoStock movimientosProductoStock : movimientosProductoStocks) {
                    GpProducto p = gpProductoService.obtenerPorId(movimientosProductoStock.getIdProducto());
                    GpMovimientoProducto mp = new GpMovimientoProducto();
                    mp.setIdProducto(p);
                    mp.setPrecio(precio);
                    mp.setLote(movimientosProductoStock.getLote());
                    mp.setFechaVencimiento(movimientosProductoStock.getFechaVencimiento());
                    if (cantidadRestante >= movimientosProductoStock.getCantidad() && cantidadRestante > 0) {
                        mp.setCantidad(movimientosProductoStock.getCantidad());
                        mp.setTotal(precio.multiply(new BigDecimal(movimientosProductoStock.getCantidad())));
                        cantidadRestante = cantidadRestante - movimientosProductoStock.getCantidad();
                        movimientoDetalles.add(mp);
                    } else if (cantidadRestante < movimientosProductoStock.getCantidad() && cantidadRestante > 0) {
                        mp.setCantidad(cantidadRestante);
                        mp.setTotal(precio.multiply(new BigDecimal(cantidadRestante)));
                        movimientoDetalles.add(mp);
                        cantidadRestante = 0;
                    }
                    if (cantidadRestante == 0) {
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

            setMovimientoDetalle(movimientoDetalles, session, sessionDetallePedido);
        }

        return jsonResponse;
    }

    private List<GpMovimientoProducto> getMovimientoDetalle(HttpSession session, String nombreAtributo) {
        List<GpMovimientoProducto> movimientoDetalles;

        if (session.getAttribute(nombreAtributo) == null) {
            movimientoDetalles = new ArrayList<GpMovimientoProducto>();
        } else {
            movimientoDetalles = (List<GpMovimientoProducto>) session.getAttribute(nombreAtributo);
        }
        return movimientoDetalles;
    }

    private void setMovimientoDetalle(List<GpMovimientoProducto> movimientoDetalles, HttpSession session, String nomAtributo) {
        session.setAttribute(nomAtributo, movimientoDetalles);
    }

//    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
//    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
//        List<GpMovimiento> lista = clienteService.listarModulo(idModulo);
//        return new ModelAndView("EquipoPDF", "Data", lista);
//    }
//
//    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
//    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
//        List<GpMovimiento> lista = clienteService.listarModulo(idModulo);
//        return new ModelAndView("EquipoExcel", "Data", lista);
//    }
}
