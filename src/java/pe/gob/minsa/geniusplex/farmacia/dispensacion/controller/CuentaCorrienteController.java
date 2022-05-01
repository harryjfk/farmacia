package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums.VentaEstadoEnum;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ClienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.FormaPagoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

@Controller
@RequestMapping("/{idModulo}/cuentacorriente/*")
public class CuentaCorrienteController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private FormaPagoService fpService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public String procesarCuentaCorriente(Model model) {
        model.addAttribute("viewTitle", "Cuentas Corrientes");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Fecha", "Nro. Oper.", "Nro. Docum.", "Doc", "Cliente", "F.P.", "Total", "Cancelaci&oacute;n", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,ventafechaRegistro:date,nroOperacion,nroVenta,docTipo,cliente.nombreCliente,formaDePago.descripcion,preventaNetoAPagar,fechaCancelacion:date");
        model.addAttribute("findItem", "");
        return "CuentaCorriente/procesar";
    }

    @RequestMapping(value = "cancelar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Venta> eliminarCliente(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Venta> response = new AjaxResponse<Venta>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con sus credenciales para realizar esta acci&oacute;n");
            return response;
        }
        Venta venta = ventaService.obtenerPorId(id);
        venta.setIdUsuarioAnulacion((long) usuario.getIdUsuario());
        venta.setUsuarioModificacion(usuario.getIdUsuario());
        boolean ok = ventaService.cancelarVenta(venta);
        if (ok) {
            response.addMensaje("La venta ha sido cancelada correctamente");
        } else {
            response.setHasError(!ok);
            response.addMensaje("Ha ocurrido un cacelando la venta.");
        }
        return response;
    }

    @RequestMapping(value = "consultar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Venta>> consultar(@PathVariable long idModulo, @RequestBody FilterData fData, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        AjaxResponse<List<Venta>> response = new AjaxResponse<List<Venta>>();
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
        }
        try {
            List<Venta> ventas = ventaService.consultarCuentasCorriente(idModulo, fData);
            if (ventas.isEmpty()) {
                response.setHasError(true);
                response.addMensaje("No se ha encontrado ning&uacute;n documento");
            } else {
                response.setData(ventas);
            }
            HttpSession session = request.getSession();
            session.setAttribute("cuenta_corriete_ventas", ventas);
            session.setAttribute("cuenta_corriete_filter", fData);
        } catch (BusinessException ex) {
            Logger.getLogger(CuentaCorrienteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
        }

        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "CuentaCorrientePDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "CuentaCorrienteExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        List<Venta> ventas = new ArrayList<Venta>();
        if (request.getSession().getAttribute("cuenta_corriete_ventas") != null) {
            ventas = (List<Venta>) request.getSession().getAttribute("cuenta_corriete_ventas");
        }
        FilterData fData = (FilterData) request.getSession().getAttribute("cuenta_corriete_filter");

        String cliente = "TODOS";
        String formaDePago = "TODAS";
        String periodo = "";
        String fechaInicio = "_";
        String fechaFin = "_";
        String situacion = "TODAS";
        
        if (fData != null) {
            HashMap<String, Object> params = fData.getParams();
            if (params.containsKey("periodo")) {
                periodo = (String) params.get("periodo");
            }
            if (params.containsKey("estado")) {
                VentaEstadoEnum estado = Enum.valueOf(VentaEstadoEnum.class, (String) params.get("estado"));
                situacion = estado.toString();
            }
            if (params.containsKey("cliente")) {
                long idcliente = Long.parseLong(params.get("cliente").toString());
                cliente = clienteService.obtenerPorId(idcliente).getNombreCliente();
            }
            if (params.containsKey("formaDePago")) {
                long idfP = Long.parseLong(params.get("formaDePago").toString());
                formaDePago = fpService.obtenerPorId(idfP).getDescripcion();
            }
            if (params.containsKey("fechIni") && params.get("fechIni").toString().length() > 0) {
                fechaInicio = params.get("fechIni").toString();
            }
            if (params.containsKey("fechFin") && params.get("fechFin").toString().length() > 0) {
                fechaFin = params.get("fechFin").toString();
            }
        }

        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        
        headerColumns.put(0, 50);
        headerColumns.put(1, 50);
        headerData.put("Cliente", cliente);
        headerData.put("Rango de Fecha", fechaInicio + '-' + fechaFin);
        headerData.put("Forma de Pago", formaDePago);
        headerData.put("Situación", situacion);
        headerData.put("Periodo", periodo);

        List<String> contentLabels = Arrays.asList("Fecha", "Nro. Oper.", "Nro. Docum.", "Doc", "Cliente", "F.P.", "Total", "Cancelación");
        List<String> contentFields = Arrays.asList("ventafechaRegistro", "nroOperacion", "nroVenta", "docTipo", "cliente:nombreCliente", "formaDePago:descripcion", "preventaNetoAPagar", "fechaCancelacion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 12);
        contentColumns.put(1, 12);
        contentColumns.put(2, 12);
        contentColumns.put(3, 12);
        contentColumns.put(4, 16);
        contentColumns.put(5, 12);
        contentColumns.put(6, 12);
        contentColumns.put(7, 12);

        ModelAndView model = new ModelAndView(viewName);
        model.addObject("Title", "Cuentas Corrientes");
        model.addObject("ContentData", ventas);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }
}
