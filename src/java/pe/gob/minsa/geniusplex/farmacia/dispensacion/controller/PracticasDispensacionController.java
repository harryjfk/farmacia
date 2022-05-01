package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PracticaDispensacion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ClienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PracticaDispensacionService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VendedorService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/practicasDispensacion/*")
public class PracticasDispensacionController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private GpProductoService productoService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private PracticaDispensacionService practicaService;

    @Autowired
    private PacienteService pacienteService;

    private String fechaInicio;
    private String fechaFinal;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPracticas(Model model, HttpServletRequest request) {
        model.addAttribute("viewTitle", "Mantenimiento de Buenas Prácticas de Dispensación");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Descripción del Producto", "Personal Atiende", "Personal Corrige", "Producto Válido", "Fecha", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,producto.descripcion,venta.vendedor.nombreVendedor,vendedor.nombreVendedor,productoCorregido.descripcion,fecha:date");
        model.addAttribute("findItem", "getPractica");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        return "PracticasDispensacion/listar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultarPracticas(Model model, HttpServletRequest request) {
        model.addAttribute("viewTitle", "Consultar de Buenas Prácticas de Dispensación");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Descripción del Producto", "Personal Atiende", "Personal Corrige", "Producto Válido", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,producto.descripcion,venta.vendedor.nombreVendedor,vendedor.nombreVendedor,productoCorregido.descripcion");
        model.addAttribute("findItem", "");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        return "PracticasDispensacion/consultar";
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<PracticaDispensacion> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        List<PracticaDispensacion> lista = getConsultData(request, idModulo);
        return lista;
    }

    @RequestMapping(value = "getVentas", method = RequestMethod.GET)
    public @ResponseBody
    List<Venta> getVentas(@PathVariable long idModulo, HttpServletRequest request) {
        long idCliente = Long.parseLong(request.getParameter("idCliente"));
        List<Venta> lista = ventaService.listarPorModuloClienteVentas(idModulo, idCliente);
        return lista;
    }

    @RequestMapping(value = "getPracticas", method = RequestMethod.GET)
    public @ResponseBody
    List<PracticaDispensacion> getPracticas(@PathVariable long idModulo, HttpServletRequest request) {
        long idVenta = Long.parseLong(request.getParameter("idVenta"));
        List<PracticaDispensacion> lista = practicaService.listarPorModuloVentas(idModulo, idVenta);
        return lista;
    }

    @RequestMapping(value = "getPractica", method = RequestMethod.GET)
    public @ResponseBody
    PracticaDispensacion getPractica(@PathVariable long idModulo, HttpServletRequest request) {
        long idPractica = Long.parseLong(request.getParameter("id"));
        PracticaDispensacion lista = practicaService.obtenerPorId(idPractica);
        return lista;
    }

    @RequestMapping(value = "getVenta", method = RequestMethod.GET)
    public @ResponseBody
    Venta getVenta(@PathVariable long idModulo, HttpServletRequest request) {
        long idVenta = Long.parseLong(request.getParameter("idVenta"));
        Venta lista = ventaService.obtenerPorId(idVenta);
        return lista;
    }

    @RequestMapping(value = "getClientes", method = RequestMethod.GET)
    public @ResponseBody
    List<Cliente> getClientes(@PathVariable long idModulo) {
        List<Cliente> lista = clienteService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<PracticaDispensacion>> insertarPractica(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<PracticaDispensacion>> response = new AjaxResponse<List<PracticaDispensacion>>();

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        PracticaDispensacion practicaDispensacion = new PracticaDispensacion();
        getData(request, practicaDispensacion);

        practicaDispensacion.setUsuarioCreacion(usuario.getIdUsuario());
        practicaDispensacion.setUsuarioModificacion(usuario.getIdUsuario());
        practicaDispensacion.setIdModulo(idModulo);

        boolean result = practicaService.insertar(practicaDispensacion);
        List<PracticaDispensacion> data = practicaService.listarModulo(idModulo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente la práctica de dispensación.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar esta práctica de dispensación.");
            response.setHasError(true);
        }
        return response;
    }

    private void getData(HttpServletRequest request, PracticaDispensacion practicaDispensacion) throws NumberFormatException {

        long idVenta = Long.parseLong(request.getParameter("idVenta"));
        long idVendedorCorrige = Long.parseLong(request.getParameter("idVendedor"));
        int idProductoError = Integer.parseInt(request.getParameter("idProductoErroneo"));
        int idProductoCorregido = Integer.parseInt(request.getParameter("idProductoCorregido"));

        String observacion = request.getParameter("observacion");

        Vendedor vendedor = vendedorService.obtenerPorId(idVendedorCorrige);
        GpProducto productoError = productoService.obtenerPorId(idProductoError);
        GpProducto productoCorregido = productoService.obtenerPorId(idProductoCorregido);
        Venta venta = ventaService.obtenerPorId(idVenta);

        practicaDispensacion.setObservacion(observacion);
        practicaDispensacion.setProducto(productoError);
        practicaDispensacion.setProductoCorregido(productoCorregido);
        practicaDispensacion.setVendedor(vendedor);
        practicaDispensacion.setVenta(venta);
        setDate(request, practicaDispensacion);
    }

    private void setDate(HttpServletRequest request, PracticaDispensacion practicaDispensacion) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        try {
            String startDate = request.getParameter("fecha");

            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        practicaDispensacion.setFecha(start);
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarPractica(@RequestParam long id) {
        AjaxResponse<PracticaDispensacion> response = new AjaxResponse<PracticaDispensacion>();
        boolean result = practicaService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el producto.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este producto");
        }
        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<PracticaDispensacion> modificarPractica(HttpServletRequest request) {
        AjaxResponse<PracticaDispensacion> response = new AjaxResponse<PracticaDispensacion>();
        long id = Long.parseLong(request.getParameter("id"));
        PracticaDispensacion practicaDispensacion = practicaService.obtenerPorId(id);
        getData(request, practicaDispensacion);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        practicaDispensacion.setUsuarioModificacion(usuario.getIdUsuario());

        boolean result = practicaService.actualizar(practicaDispensacion);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este producto.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PracticasDispensacionPDF");

        return model;
    }

    @RequestMapping(value = "reporteConsultaPdf", method = RequestMethod.GET)
    public ModelAndView reporteConsultaPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultReport(request, idModulo, "PracticasDispensacionPDF");

        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PracticasDispensacionExcel");

        return model;
    }

    @RequestMapping(value = "reporteConsultaExcel", method = RequestMethod.GET)
    public ModelAndView reporteConsultExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configConsultReport(request, idModulo, "PracticasDispensacionExcel");

        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        long idVenta = Long.parseLong(request.getParameter("idVenta"));
        Venta venta = ventaService.obtenerPorId(idVenta);
        List<PracticaDispensacion> lista = practicaService.listarPorModuloVentas(idModulo, idVenta);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Descripción del Producto", "Personal Atiende", "Personal Corrige", "Producto Válido", "Fecha");
        List<String> contentFields = Arrays.asList("producto:descripcion", "venta:vendedor:nombreVendedor", "vendedor:nombreVendedor", "productoCorregido:descripcion", "fecha");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 20);
        contentColumns.put(1, 20);
        contentColumns.put(2, 20);
        contentColumns.put(3, 20);
        contentColumns.put(4, 20);
        model.addObject("Title", "Reporte de Buenas Practicas de Dispensacion");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        headerData.put("Nro. Venta", venta.getNroVenta());
        headerData.put("Fecha de Registro", new SimpleDateFormat("dd/MM/yyyy").format(venta.getVentafechaRegistro()));
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    private ModelAndView configConsultReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<PracticaDispensacion> lista = getConsultData(request, idModulo);
        ModelAndView model = new ModelAndView(viewName);
        long idCliente;
        final String idClienteStr = request.getParameter("idCliente");
        Cliente cliente = null;
        if (idClienteStr != null && idClienteStr.length() > 0) {
            idCliente = Long.parseLong(idClienteStr);
            cliente = clienteService.obtenerPorId(idCliente);
        }
        List<String> contentLabels = Arrays.asList("Descripción del Producto", "Personal Atiende", "Personal Corrige", "Producto Válido", "Fecha");
        List<String> contentFields = Arrays.asList("producto:descripcion", "venta:vendedor:nombreVendedor", "vendedor:nombreVendedor", "productoCorregido:descripcion", "fecha");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 20);
        contentColumns.put(1, 20);
        contentColumns.put(2, 20);
        contentColumns.put(3, 20);
        contentColumns.put(4, 20);
        model.addObject("Title", "Reporte de Buenas Practicas de Dispensacion");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        if (cliente != null) {
            headerData.put("Cliente", cliente.getNombreCliente());
        }
        headerData.put("Periodo", fechaInicio + " - " + fechaFinal);
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    private List<PracticaDispensacion> getConsultData(HttpServletRequest request, long idModulo) throws NumberFormatException {
        long idCliente = 0;
        final String cliString = request.getParameter("idCliente");
        if (cliString != null && cliString.length() > 0) {
            idCliente = Long.parseLong(cliString);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        String nroVenta = request.getParameter("nroVenta");
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            fechaInicio = startDate;
            fechaFinal = endDate;
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(DemandaInsatisfechaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<PracticaDispensacion> lista = practicaService.ConsultaPracticaDispensacions(idModulo, idCliente, start, end, nroVenta);
        return lista;
    }
}
