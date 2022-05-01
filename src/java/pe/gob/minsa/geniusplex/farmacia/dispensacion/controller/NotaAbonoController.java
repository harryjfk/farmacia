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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.VentaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.VentaKitProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.FormaPagoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.TurnoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/notaabono/*")
public class NotaAbonoController {

    @Autowired
    VentaService ventaService;
    @Autowired
    private FormaPagoService fpService;
    @Autowired
    private TurnoService turnoService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public ModelAndView procesarNotaAbono(ModelAndView model, HttpServletRequest request) {

        model.addObject("tableHeaders", Arrays.asList(new String[]{"Producto", "C&oacute;digo", "Cantidad Original", "Cantidad Modificada", "Precio", "Imp. Parcial", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "");
        model.addObject("tableProperties", "producto.idProducto,producto.descripcion,producto.idProducto,cantidad,cantidadActual,precio:money4,importeTotal:money4");
        model.addObject("findItem", "");
        model.addObject("viewTitle", "Nota de Abono");
        
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model.setViewName("redirect:/index");
        } else {
            Perfil perfilObj = WebUtil.usuarioPertenecePerfil("Supervisor", usuario);
            String perfil = "";
            if(perfilObj != null) {
                perfil = perfilObj.getNombrePerfil();
            }
            model.addObject("perfil", perfil.toLowerCase());
        }
        model.setViewName("NotaAbono/procesar");
        return model;
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
            response.addMensaje("Debe entrar al sistema con sus credenciales para poer realizar esta aci&oacute;n.");
            return response;
        }
        venta.setIdModulo(idModulo);
        boolean result;
        try {
            result = ventaService.guardarCambiosVenta(venta);
        } catch (BusinessException ex) {
            Logger.getLogger(NotaAbonoController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
            return response;
        }
        if (result) {
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
    AjaxResponse<VentaProducto> obtenerProducto(@RequestBody VentaProducto vProducto, @PathVariable Long idFormaPago, HttpServletRequest request) {
        AjaxResponse<VentaProducto> response = new AjaxResponse<VentaProducto>();
        FormaPago formaPago = null;
        if (idFormaPago != null) {
            formaPago = fpService.obtenerPorId(idFormaPago);
        }
        if (formaPago != null) {
            ventaService.sincronizarVentaProducto(vProducto, formaPago);
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
                Logger.getLogger(NotaAbonoController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(NotaAbonoController.class.getName()).log(Level.SEVERE, null, ex);
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
    AjaxResponse<List<VentaProducto>> obtenerProductosPorKit(@PathVariable String kitId, @RequestBody List<GpProducto> userSelected, HttpServletRequest request
    ) {
        AjaxResponse<List<VentaProducto>> response = new AjaxResponse<List<VentaProducto>>();
        try {
            long kId = Long.parseLong(kitId);
            VentaKitProductoDTO vkpdto = ventaService.obtenerProductosPorKit(kId, userSelected);
            List<VentaProducto> ventaProductos = vkpdto.getVentaProductos();
            if (ventaProductos.isEmpty()) {
                response.setHasError(true);
                response.addMensaje("No hay productos que agregar.");
            } else {
                response.setData(ventaProductos);
            }
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, numberFormatException);
            response.setHasError(true);
            response.addMensaje("Lo sentimos, ha ocurrido un error");
        } catch (BusinessException ex) {
            Logger.getLogger(NotaAbonoController.class.getName()).log(Level.SEVERE, null, ex);
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

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "NotaAbonoPDF");

        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "NotaAbonoExcel");

        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        String ventaId = request.getParameter("idVenta");
        ModelAndView model = new ModelAndView(viewName);
        if (ventaId != null && ventaId.length() > 0) {
            long idVenta = Long.parseLong(ventaId);
            Venta preVenta = ventaService.obtenerPorId(idVenta);
            List<VentaProducto> lista = preVenta.getVentaProductos();
            String formaDePago = "";
            FormaPago fp = preVenta.getFormaDePago();
            formaDePago = fp.getDescripcion();
            List<String> contentLabels = Arrays.asList("Descripción del Producto", "Cantidad Original", "Cantidad Modificada", "Precio", "Imp. Parcial");
            List<String> contentFields = Arrays.asList("producto:descripcion", "cantidad", "cantidadActual", "precio", "importeTotal");
            HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
            contentColumns.put(0, 50);
            contentColumns.put(1, 12);
            contentColumns.put(2, 15);
            contentColumns.put(3, 13);
            contentColumns.put(4, 10);
            model.addObject("Title", formaDePago);
            model.addObject("ContentData", lista);
            model.addObject("ContentLabels", contentLabels);
            model.addObject("ContentFields", contentFields);
            model.addObject("ContentColumns", contentColumns);
            HashMap<String, String> headerData = new HashMap<String, String>();

            headerData.put("Importe Total", preVenta.getPreventaNetoAPagar());
            headerData.put("Forma de Pago", formaDePago);
            headerData.put("Cliente", preVenta.getCliente().getNombreCliente());

            HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
            headerColumns.put(0, 50);
            model.addObject("HeaderData", new HashMap[]{headerData});
            model.addObject("HeaderColumns", headerColumns);
        }
        return model;
    }
}
