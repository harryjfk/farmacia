/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.FormaPago;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.VentaKitProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.FormaPagoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.TurnoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VendedorService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpPersonalService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/preventa/*")
public class PreventaController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private FormaPagoService fpService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private GpPersonalService personalService;
//    @Autowired
//    private VentaProductoService ventaProductoService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public ModelAndView procesarPreventa(@PathVariable long idModulo, ModelAndView model, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        } else {
            model.addObject("viewTitle", "Pre Venta");
            model.addObject("tableHeaders", Arrays.asList(new String[]{"Producto", "C&oacute;digo", "Cantidad", "Precio", "Imp. Parcial", "Acciones"}));
            model.addObject("ajaxList", "");
            model.addObject("editUrl", "");
            model.addObject("changeUrl", "");
            model.addObject("removeUrl", "");
            model.addObject("tableProperties", "producto.idProducto,producto.descripcion,producto.idProducto,cantidad,precio:money4,importeTotal:money4");
            model.addObject("findItem", "");
            if(idModulo == 7) {
                //hardcoded because there is no config for this
                model.addObject("showService", 1);
            } else {
                 model.addObject("showService", 0);
            }
            //Inferir el turno segun la hora
            Calendar gCalendar = GregorianCalendar.getInstance();
            Turno turno = turnoService.obtenerPorHora(gCalendar.getTime(), idModulo);
            if (turno != null) {
                model.addObject("turnoActual", turno.getIdTurno());
                //Inferir si el usuario es vendedor
                for (Vendedor vendedor : turno.getVendedores()) {
                    if (vendedor.getPersonal().trim().equalsIgnoreCase(usuario.getPersonal().getIdPersonal().trim())) {
                        model.addObject("vendedorActual", vendedor.getNombreVendedor() + ":" + vendedor.getIdVendedor());
                        break;
                    }
                }
            }
            Vendedor loggedInVendedor = vendedorService.obtenerPorPersonalYModulo(usuario.getPersonal().getIdPersonal(), null);
            if (loggedInVendedor != null) {
                model.addObject("isUserSeller", true);
            } else {
                model.addObject("isUserSeller", false);
            }
            model.setViewName("Preventa/procesar");
        }

        return model;
    }

    @RequestMapping(value = "reportePreventa", method = RequestMethod.GET)
    public String reportePreventa(Model model) {
        model.addAttribute("viewTitle", "Pre Venta");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"No ProForma", "Fecha", "Vendedor", "Cliente", "Total", "Turno"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,preventa,fechaRegistro:date,vendedor.nombreVendedor,cliente.nombreCliente,preventaNetoAPagar:money4,turno.descripcion");
        model.addAttribute("findItem", "");
        return "PreVenta/reportar";
    }

    @RequestMapping(value = "getReporte", method = RequestMethod.GET)
    public @ResponseBody
    List<Venta> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        List<Venta> lista = procesarConsulta(request, idModulo);

        return lista;
    }

    private List<Venta> procesarConsulta(HttpServletRequest request, long idModulo) throws NumberFormatException {
        long vendedor = Long.parseLong(request.getParameter("vendedor"));
        long turno = Long.parseLong(request.getParameter("turno"));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        List<Venta> lista = ventaService.ReportePreventas(idModulo, turno, vendedor, start, end);
        return lista;
    }

    @RequestMapping(value = "getProductos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> getProductos(@PathVariable long idModulo, @RequestBody Venta venta, HttpServletRequest request) {

        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        response.setData(venta);
        return response;
    }

    @RequestMapping(value = "guardarCambios", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> guardarCambios(@PathVariable long idModulo, @RequestBody Venta preventa, HttpServletRequest request) {
        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        preventa.setIdModulo(idModulo);
        boolean result;
        try {
            if (usuario != null && usuario.getIdUsuario() > 0) {
                if (preventa.getId() == 0) {
                    preventa.setUsuarioCreacion(usuario.getIdUsuario());
                }
                preventa.setUsuarioModificacion(usuario.getIdUsuario());
                boolean esVendeorPersonal = preventa.getVendedor() != null ? preventa.getVendedor().getPersonal() != null : false;
                result = ventaService.guardarCambios(preventa, true);
                if (esVendeorPersonal) {

                    try {
                        GpPersonal personal = personalService.obtenerPorId(preventa.getVendedor().getPersonal());
                        String username = String.format("%s%s%s",
                                personal.getNombres().toLowerCase(),
                                personal.getApellidoPaterno().toLowerCase(),
                                personal.getApellidoMaterno().toLowerCase());

//                    username += Calendar.getInstance().getTimeInMillis();
                        String password = WebUtil.generarClaveUsuario(8);
                        vendedorService.crearUsuario(personal, username, password, usuario);
                    } catch (BusinessException businessException) {
                        //seguramente el correo dio palo y no se creo el usuario (la expresion regular esta muy restrictiva)
                        java.util.logging.Logger.getLogger(PreventaController.class.getName()).log(Level.INFO, null, businessException);
                    }

                    //actualizar el listado de vendedores del turno seleccionado
                    Vendedor vendedor = preventa.getVendedor();
                    Turno turno = preventa.getTurno();

                    turno.getVendedores().add(vendedor);
                    vendedor.getTurnos().add(turno);
                    turnoService.actualizar(turno);
                }
            } else {
                result = false;
                response.setHasError(true);
                response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta aci&oacute;n.");
            }
        } catch (BusinessException ex) {
            Logger.getLogger(PreventaController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
            return response;
        }
        if (result) {
            for (VentaProducto ventaProducto : preventa.getVentaProductos()) {
                int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
                ventaProducto.setStock(stock);
            }
            response.setData(preventa);
            response.addMensaje("Se han guardado los cambios correctamente");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al guardar los cambios");
        }
        return response;
    }

    @RequestMapping(value = "borrarProductos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse limpiarProductos(@RequestBody Venta preventa) {
        AjaxResponse response = new AjaxResponse();
        boolean result = ventaService.borrarProductos(preventa);
        if (result) {
            response.setHasError(false);
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error");
        }
        return response;
    }

    @RequestMapping(value = "{idFormaPago}/obtenerProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<VentaProducto> obtenerProducto(@PathVariable long idModulo, @RequestBody VentaProducto vProducto,
            @PathVariable Long idFormaPago, HttpServletRequest request) {

        AjaxResponse<VentaProducto> response = new AjaxResponse<VentaProducto>();
        FormaPago formaPago = null;
        if (idFormaPago != null) {
            formaPago = fpService.obtenerPorId(idFormaPago);
        }
        if (formaPago != null) {
            ventaService.sincronizarVentaProducto(vProducto, formaPago);
            int stock = ventaService.obtenerStock(vProducto.getProducto().getIdProducto(), idModulo);
            vProducto.setStock(stock);
            response.setData(vProducto);
        } else {
            response.setHasError(true);
            response.addMensaje("Por favor seleccione una forma de pago");
        }
        return response;
    }

    @RequestMapping(value = "obtenerStock", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse obtenerStock(@PathVariable long idModulo, @RequestBody VentaProducto ventaProducto, HttpServletRequest request) {
        //TODO Falta el Almacen
        int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
        AjaxResponse response = new AjaxResponse(stock);
        return response;
    }

    @RequestMapping(value = "verStocks", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Integer>> obtenerStocks(@PathVariable long idModulo, @RequestBody List<VentaProducto> ventaProductos, HttpServletRequest request) {
        AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }

        List<Integer> stockList = new ArrayList<Integer>();
        //TODO Falta el Almacen
        for (VentaProducto ventaProducto : ventaProductos) {
            int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
            stockList.add(stock);
        }
        response.setData(stockList);
        return response;
    }

    @RequestMapping(value = "datosPrepago", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> obtenerDatosPrepago(@PathVariable long idModulo, @RequestBody Venta venta, HttpServletRequest request) {
        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        venta = ventaService.obtenerDatosPrepago(venta);
        response.setData(venta);
        return response;
    }

    @RequestMapping(value = "consultarPreventa", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> consultar(@PathVariable long idModulo, HttpServletRequest request) {
        String preventa = request.getParameter("preventa");
        AjaxResponse<Venta> responsse = new AjaxResponse<Venta>();
        if (preventa == null || preventa.length() == 0 || preventa.length() < 9) {
            responsse.setHasError(true);
            responsse.addMensaje("El n&uacute;mero de la preventa no es v&aacute;lido.");
            return responsse;
        }
        Venta venta = ventaService.consultarPreventa(preventa, idModulo, true);
        if (venta == null) {
            responsse.addMensaje("No se ha encontrado ninguna Pre Venta con ese n&uacute;mero");
            responsse.setHasError(true);
        } else {
            for (VentaProducto ventaProducto : venta.getVentaProductos()) {
                int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
                ventaProducto.setStock(stock);
            }
            responsse.setData(venta);
        }
        return responsse;
    }

    @RequestMapping(value = "{kitId}/obtenerProductosPorKit", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<VentaProducto>> obtenerProductosPorKit(@PathVariable long idModulo, @PathVariable String kitId, @RequestBody List<GpProducto> userSelected, HttpServletRequest request) {
        AjaxResponse<List<VentaProducto>> response = new AjaxResponse<List<VentaProducto>>();
        try {
            long kId = Long.parseLong(kitId);
            VentaKitProductoDTO vkpdto = ventaService.obtenerProductosPorKit(kId, userSelected);
            List<VentaProducto> ventaProductos = vkpdto.getVentaProductos();
            if (ventaProductos.isEmpty()) {
                response.setHasError(true);
                response.addMensaje("No hay productos que agregar.");
            } else {
                if (vkpdto.isHasMessage()) {
                    response.setHasError(true);
                    response.addMensaje(vkpdto.getMessage());
                }
                for (VentaProducto ventaProducto : ventaProductos) {
                    int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
                    ventaProducto.setStock(stock);
                }
                response.setData(ventaProductos);

            }
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, numberFormatException);
            response.setHasError(true);
            response.addMensaje("Lo sentimos, ha ocurrido un error");
        } catch (BusinessException ex) {
            Logger.getLogger(PreventaController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PreVentaPDF");

        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PreVentaExcel");

        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        String ventaId = request.getParameter("idVenta");
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Descripción del Producto", "Cantidad", "Precio", "Imp. Parcial");
        List<String> contentFields = Arrays.asList("producto:descripcion", "cantidad", "precio", "importeTotal");
        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);

        List<VentaProducto> lista = new ArrayList<VentaProducto>();
        String formaDePago = " ";
        String nombreCliente = " ";
        String preventaNetoAPagar = "0.00";

        contentColumns.put(0, 50);
        contentColumns.put(1, 12);
        contentColumns.put(2, 13);
        contentColumns.put(3, 25);

        if (ventaId != null && ventaId.length() > 0) {
            long idVenta = Long.parseLong(ventaId);
            Venta preVenta = ventaService.obtenerPorId(idVenta);
            lista = preVenta.getVentaProductos();
            FormaPago fp = preVenta.getFormaDePago();
            formaDePago = fp.getDescripcion();

            nombreCliente = preVenta.getCliente().getNombreCliente();
            preventaNetoAPagar = preVenta.getPreventaNetoAPagar();

        }

        headerData.put("Importe Total", preventaNetoAPagar);
        headerData.put("Cliente", nombreCliente);
        headerData.put("Forma de Pago", formaDePago);

        model.addObject("Title", formaDePago);
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    @RequestMapping(value = "reporteConsultaPdf", method = RequestMethod.GET)
    public ModelAndView reporteConsultaPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultaReport(request, idModulo, "PreVentaConsultaPDF");

        return model;
    }

    @RequestMapping(value = "reporteConsultaExcel", method = RequestMethod.GET)
    public ModelAndView reporteConsultaExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultaReport(request, idModulo, "PreVentaConsultaExcel");

        return model;
    }

    private ModelAndView configConsultaReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<Venta> preVentas = this.procesarConsulta(request, idModulo);

        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("No. ProForma", "Fecha", "Vendedor", "Cliente", "Total", "Turno");
        List<String> contentFields = Arrays.asList("preventa", "fechaRegistro", "vendedor:nombreVendedor", "cliente:nombreCliente", "preventaNetoAPagar", "turno:descripcion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 5);
        contentColumns.put(2, 37);
        contentColumns.put(3, 32);
        contentColumns.put(4, 20);
        contentColumns.put(5, 20);
        model.addObject("Title", "Reporte de Pre Ventas");
        model.addObject("ContentData", preVentas);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        if (!preVentas.isEmpty()) {
            Venta preventa = preVentas.get(0);
            for (Map.Entry<String, String[]> entrySet : request.getParameterMap().entrySet()) {
                String key = entrySet.getKey();
                String[] value = entrySet.getValue();
                if (key.equalsIgnoreCase("vendedor") && preventa.getVendedor() != null) {
                    long idVen = Long.parseLong(value[0]);
                    if (idVen > 0) {
                        headerData.put("Cajero", preventa.getVendedor().getNombreVendedor());
                    }
                }
                if (key.equalsIgnoreCase("turno") && preventa.getTurno() != null) {
                    long idTurno = Long.parseLong(value[0]);
                    if (idTurno > 0) {
                        headerData.put("Turno", preventa.getTurno().getDescripcion());
                    }
                }
                if (key.equalsIgnoreCase("startDate")) {
                    headerData.put("Del", value[0]);
                }
                if (key.equalsIgnoreCase("endDate")) {
                    headerData.put("Al", value[0]);
                }
            }
        }

        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        headerColumns.put(1, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    @RequestMapping(value = "getlastdate", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<String> getLastPurchaseDate(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<String> response = new AjaxResponse<String>();
        long idCliente = request.getParameter("clienteId") != null ? Long.parseLong(request.getParameter("clienteId")) : 0;
        int idProducto = request.getParameter("productoId") != null ? Integer.parseInt(request.getParameter("productoId")) : 0;
        Date lastDate = ventaService.getLastPurchaseDate(idModulo, idCliente, idProducto);
        if (lastDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            response.setData(sdf.format(lastDate));
        } else {
            response.setData("No hay ventas anteriores");
        }
        return response;
    }

    @RequestMapping(value = "addDemandaInsatisfecha", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse addDemandaInsatisfecha(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        long idCliente = request.getParameter("clienteId") != null ? Long.parseLong(request.getParameter("clienteId")) : 0;
        int idProducto = request.getParameter("productoId") != null ? Integer.parseInt(request.getParameter("productoId")) : 0;
        Boolean ok = ventaService.addDemandaInsatisfecha(idModulo, idCliente, idProducto);
        if (!ok) {
            response.setData("Ha ocurrido un error inesperado");
        }
        return response;
    }

}
