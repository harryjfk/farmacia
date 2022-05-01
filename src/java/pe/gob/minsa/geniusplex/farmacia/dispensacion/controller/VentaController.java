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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Perfil;
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
@RequestMapping("/{idModulo}/venta/*")
public class VentaController {

    @Autowired
    VentaService ventaService;
    @Autowired
    TurnoService turnoService;
    @Autowired
    private FormaPagoService fpService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private GpPersonalService personalService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public ModelAndView procesarVenta(@PathVariable long idModulo, ModelAndView model, HttpServletRequest request) {

        model.addObject("tableHeaders", Arrays.asList(new String[]{"Producto", "C&oacute;digo", "Cantidad", "Precio", "Imp. Parcial", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "");
        model.addObject("tableProperties", "producto.idProducto,producto.descripcion,producto.idProducto,cantidadActual,precio:money4,importeTotal:money4");
        model.addObject("findItem", "");

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model.addObject("usuario", usuario);
            model.setViewName("redirect:/index");
        } else {
            if (idModulo == 7) {
                //hardcoded because there is no config for this
                model.addObject("showService", 1);
            } else {
                model.addObject("showService", 0);
            }
            Perfil perfilObj = WebUtil.usuarioPertenecePerfil("Supervisor", usuario);
            String perfil = "";
            if (perfilObj != null) {
                perfil = perfilObj.getNombrePerfil().trim();
            }
            model.addObject("perfil", perfil.toLowerCase());
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

            String contextPath = request.getContextPath();
            model.addObject("contextPath", contextPath);
            model.addObject("viewTitle", "Venta");
            model.setViewName("Venta/procesar");
        }
        return model;

    }

    @RequestMapping(value = "reportar", method = RequestMethod.GET)
    public ModelAndView reportarVenta(@PathVariable long idModulo, ModelAndView model, HttpServletRequest request) {

        model.addObject("tableHeaders", Arrays.asList(new String[]{"Nro Op", "Fecha", "Cliente", "Caja", "Vendedor", "No Doc", "Doc", "F.P", "Venta", "IGV", "Total"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "");
        model.addObject("tableProperties", "id,nroOperacion,ventafechaRegistro:date,cliente.nombreCliente,puntoDeVenta.nombrePc,vendedor.nombreVendedor,nroVenta,docTipo,formaDePago.descripcion,subTotalPreventa:money4,impuestoPreventa:money4,preventaNetoAPagar:money4");
        model.addObject("findItem", "");

        model.addObject("viewTitle", "Venta");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model.setViewName("index");
        } else {
            model.addObject("viewTitle", "Venta");
            if (idModulo == 7) {
                //hardcoded because there is no config for this
                model.addObject("showService", 1);
            } else {
                model.addObject("showService", 0);
            }
            model.setViewName("Venta/reportar");
        }
        return model;
    }

    @RequestMapping(value = "getReporte", method = RequestMethod.GET)
    public @ResponseBody
    List<Venta> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        List<Venta> lista = procesarConsulta(request, idModulo);
        return lista;
    }

    private List<Venta> procesarConsulta(HttpServletRequest request, long idModulo) throws NumberFormatException {
        long cliente = 0;
        long vendedor = 0;
        long turno = 0;
        long tipoPago = 0;
        String servicio = null;
        List<Venta> lista = new ArrayList<Venta>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;

        try {
            cliente = Long.parseLong(request.getParameter("cliente"));
            vendedor = Long.parseLong(request.getParameter("vendedor"));
            turno = Long.parseLong(request.getParameter("turno"));
            tipoPago = Long.parseLong(request.getParameter("formaPago"));
            servicio = request.getParameter("servicio");

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
                Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            lista = ventaService.ConsultaVentas(idModulo, tipoPago, start, end, cliente, vendedor, turno, servicio);
        } catch (NumberFormatException nfEx) {
            java.util.logging.Logger.getLogger(VentaController.class.getName()).log(Level.INFO, null, nfEx);
        }
        return lista;
    }

    @RequestMapping(value = "getVenta", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> getVenta(@PathVariable long idModulo, @RequestBody Venta venta, HttpServletRequest request) {

        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        venta = ventaService.obtenerPorId(venta.getId());
        for (VentaProducto vp : venta.getVentaProductos()) {
            int stock = ventaService.obtenerStock(vp.getProducto().getIdProducto(), idModulo);
            vp.setStock(stock);
        }
        response.setData(venta);
        return response;
    }

    @RequestMapping(value = "anularVenta", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse anularVenta(@RequestBody Venta venta, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null) {
            venta.setIdUsuarioAnulacion((long) usuario.getIdUsuario());
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poer realizar esta aci&oacute;n.");
        }
        boolean anulada = ventaService.anularVenta(venta);
        if (!anulada) {
            response.setHasError(!anulada);
            response.addMensaje("Ha ocurrido un error, la venta no ha sido anulada");
        }
        return response;
    }

    @RequestMapping(value = "guardarCambios", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> guardarCambios(@PathVariable long idModulo, @RequestBody Venta venta, HttpServletRequest request) {
        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null) {
            if (venta.getId() > 0) {
                venta.setUsuarioModificacion(usuario.getIdUsuario());
            } else {
                venta.setUsuarioCreacion(usuario.getIdUsuario());
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta aci&oacute;n.");
            return response;
        }
        venta.setIdModulo(idModulo);
        boolean esVendeorPersonal = venta.getVendedor() != null ? venta.getVendedor().getPersonal() != null : false;
        boolean result;
        try {
            result = ventaService.guardarCambiosVenta(venta);
            if (esVendeorPersonal) {

                try {
                    GpPersonal personal = personalService.obtenerPorId(venta.getVendedor().getPersonal());
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
                Vendedor vendedor = venta.getVendedor();
                Turno turno = venta.getTurno();

                turno.getVendedores().add(vendedor);
                vendedor.getTurnos().add(turno);
                turnoService.actualizar(turno);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
            return response;
        }
        if (result) {
            for (VentaProducto ventaProducto : venta.getVentaProductos()) {
                int stock = ventaService.obtenerStock(ventaProducto.getProducto().getIdProducto(), idModulo);
                ventaProducto.setStock(stock);
            }
            response.setData(venta);
            response.addMensaje("Se han guardado los cambios correctamente");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al guardar los cambios");
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
        Venta venta = ventaService.consultarPreventa(preventa, idModulo, false);
        if (venta == null) {
            responsse.addMensaje("No se ha encontrado ninguna Pre Venta con ese n&uacute;mero, es posible que ya ha sido boletada.");
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

    @RequestMapping(value = "consultarVenta", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<Venta>> consultarVenta(
            @RequestParam String fIni,
            @RequestParam String fFin,
            @RequestParam String periodo,
            @RequestParam String nroVenta,
            @RequestParam String ordenarPor,
            @PathVariable long idModulo,
            HttpServletRequest request) {

        AjaxResponse<List<Venta>> responsse = new AjaxResponse<List<Venta>>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechIni = null;
        Date fechFin = null;
        if (fIni != null && fIni.length() == 10) {
            try {
                fechIni = sdf.parse(fIni);
            } catch (ParseException ex) {
                Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                responsse.setHasError(true);
                responsse.addMensaje("La primera fecha inicial es incorrecta");
                responsse.setData(new ArrayList<Venta>());
                return responsse;
            }
        }
        if (fFin != null && fFin.length() == 10) {
            try {
                fechFin = sdf.parse(fFin);
            } catch (ParseException ex) {
                Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                responsse.setHasError(true);
                responsse.addMensaje("La primera fecha final es incorrecta");
                responsse.setData(new ArrayList<Venta>());
                return responsse;
            }
        }

        List<Venta> ventas = ventaService.consultarVenta(fechIni, fechFin, periodo, nroVenta, ordenarPor, idModulo);
        if (ventas.isEmpty()) {
            responsse.addMensaje("No se ha encontrado ninguna venta con esos par&aacute;metros. Por favor verifique que ha puesto correctamente los datos en el formulario");
            responsse.setHasError(true);
        }
        responsse.setData(ventas);
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
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "listarDocumentos", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<HashMap<Integer, String>> listarDocumentosDeVenta() {
        AjaxResponse<HashMap<Integer, String>> response = new AjaxResponse<HashMap<Integer, String>>();
        response.setData(ventaService.listarDocumentos());
        return response;
    }

    @RequestMapping(value = "{idCliente}/{dias}/historial", method = {RequestMethod.GET})
    public @ResponseBody
    AjaxResponse<List<Venta>> obtenerHistorial(@PathVariable("idCliente") long idCliente, @PathVariable("dias") int dias, HttpServletRequest request) {
        AjaxResponse<List<Venta>> response = new AjaxResponse<List<Venta>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            List<Venta> ventas = ventaService.obtenerHistorial(idCliente, dias);
            response.setData(ventas);
        } else {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
        }
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

    @RequestMapping(value = "imprimirTicket")
    public ModelAndView imprimirTicket(@RequestParam long id, ModelAndView model, HttpServletRequest request) {
        Venta venta = ventaService.obtenerPorId(id);
        model.addObject("title", "Ticket");
        model.addObject("venta", venta);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm K a");
        model.addObject("ventafechaRegistro", sdf.format(Calendar.getInstance().getTime()));
        model.setViewName("Venta/ticket");
        return model;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "VentaPDF");

        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "VentaExcel");

        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        long idVenta;
        try {
            idVenta = Long.parseLong(request.getParameter("id"));
        } catch (Exception e) {
            idVenta = 0;
            java.util.logging.Logger.getLogger(VentaController.class.getName()).log(Level.INFO, null, e);
        }
        List<VentaProducto> lista = new ArrayList<VentaProducto>();
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Descripción del Producto", "Cantidad", "Precio", "Imp. Parcial");
        List<String> contentFields = Arrays.asList("producto:descripcion", "cantidadActual", "precio", "importeTotal");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 50);
        contentColumns.put(1, 12);
        contentColumns.put(2, 13);
        contentColumns.put(3, 25);
        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        headerColumns.put(1, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);

        String formaDePago = "";
        String nroVenta = "";
        String nombresCliente = "";
        String preventaNetoAPagar = "";

        if (idVenta > 0) {
            Venta venta = ventaService.obtenerPorId(idVenta);
            lista = venta.getVentaProductos();

            FormaPago fp = venta.getFormaDePago();
            formaDePago = fp.getDescripcion();
            nroVenta = venta.getNroVenta();
            nombresCliente = venta.getCliente().getNombreCliente();
            preventaNetoAPagar = venta.getPreventaNetoAPagar();
        }

        headerData.put("Nro. Venta", nroVenta);
        headerData.put("Forma de Pago", formaDePago);
        headerData.put("Cliente", nombresCliente);
        headerData.put("Importe Total", preventaNetoAPagar);

        model.addObject("Title", formaDePago);
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);

        return model;
    }

    @RequestMapping(value = "reporteConsultaPdf", method = RequestMethod.GET)
    public ModelAndView reporteConsultaPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultaReport(request, idModulo, "VentaConsultaPDF");

        return model;
    }

    @RequestMapping(value = "reporteConsultaExcel", method = RequestMethod.GET)
    public ModelAndView reporteConsultaExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultaReport(request, idModulo, "VentaConsultaExcel");

        return model;
    }

    private ModelAndView configConsultaReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<Venta> ventas = this.procesarConsulta(request, idModulo);

        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Nro Op", "Fecha", "Cliente", "Caja", "Vendedor", "No Doc", "Doc", "F.P", "Venta", "IGV", "Total");
        List<String> contentFields = Arrays.asList("nroOperacion", "ventafechaRegistro", "cliente:nombreCliente", "puntoDeVenta:nombrePc", "vendedor:nombreVendedor", "nroVenta", "docTipo", "formaDePago:descripcion", "subTotalPreventa", "impuestoPreventa", "preventaNetoAPagar");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 9);
        contentColumns.put(1, 9);
        contentColumns.put(2, 9);
        contentColumns.put(3, 9);
        contentColumns.put(4, 9);
        contentColumns.put(5, 9);
        contentColumns.put(6, 9);
        contentColumns.put(7, 9);
        contentColumns.put(8, 9);
        contentColumns.put(9, 9);
        contentColumns.put(10, 9);
        model.addObject("Title", "Reporte de Ventas");
        model.addObject("ContentData", ventas);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        if (!ventas.isEmpty()) {
            Venta venta = ventas.get(0);
            for (Map.Entry<String, String[]> entrySet : request.getParameterMap().entrySet()) {
                String key = entrySet.getKey();
                String[] value = entrySet.getValue();
                if (key.equalsIgnoreCase("cliente") && venta.getCliente() != null) {
                    long idCli = Long.parseLong(value[0]);
                    if (idCli > 0) {
                        headerData.put("Cliente", venta.getCliente().getNombreCliente());
                    }
                }
                if (key.equalsIgnoreCase("vendedor") && venta.getVendedor() != null) {
                    long idVen = Long.parseLong(value[0]);
                    if (idVen > 0) {
                        headerData.put("Cajero", venta.getVendedor().getNombreVendedor());
                    }
                }
                if (key.equalsIgnoreCase("turno") && venta.getTurno() != null) {
                    long idTurno = Long.parseLong(value[0]);
                    if (idTurno > 0) {
                        headerData.put("Turno", venta.getTurno().getDescripcion());
                    }
                }
                if (key.equalsIgnoreCase("formaPago") && venta.getFormaDePago() != null) {
                    long idFp = Long.parseLong(value[0]);
                    if (idFp > 0) {
                        headerData.put("Forma de Pago", venta.getFormaDePago().getDescripcion());
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
}
