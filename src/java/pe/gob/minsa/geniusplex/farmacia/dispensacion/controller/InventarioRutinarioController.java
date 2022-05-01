package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.InventarioRutinario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLotePk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.InventarioRutinarioService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProductoLoteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/inventarioRutinario/*")
public class InventarioRutinarioController {

    @Autowired
    private ProductoLoteService productoLoteService;

    @Autowired
    private GpAlmacenService almacenService;

    @Autowired
    private InventarioRutinarioService inventarioRutinarioService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarProductos(Model model) {
        model.addAttribute("viewTitle", "Inventario Rutinario");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Tipo", "F.F", "Stock", "Stock Correcto", "Precio", "Lote", "F.V", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,id,productolote.producto.descripcion,productolote.producto.idTipoProducto.nombreTipoProducto,productolote.producto.idFormaFarmaceutica.nombreFormaFarmaceutica,stock,stockReal,precio,productolote.lote.descripcion,productolote.fechaVencimiento:date");
        model.addAttribute("findItem", "getInventario");
        return "InventarioRutinario/listar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String ConsultarProductos(@PathVariable Long idModulo, Model model) {
        model.addAttribute("viewTitle", "Inventario Rutinario");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripcion", "Tipo", "F.F", "Stock", "Stock Correcto", "Precio", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,id,productolote.producto.descripcion,productolote.producto.idTipoProducto.nombreTipoProducto,productolote.producto.idFormaFarmaceutica.nombreFormaFarmaceutica,stock,stockReal,precio");
        model.addAttribute("findItem", "getInventario");
        if (idModulo != 7) {
            return "InventarioRutinario/consultar";
        }
        return "InventarioRutinario/consultardosis";
    }

    @RequestMapping(value = "getAlmacenes", method = RequestMethod.GET)
    public @ResponseBody
    List<GpAlmacen> getAlmacenes(@PathVariable long idModulo) {
        List<GpAlmacen> lista = almacenService.listarPorModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getInventario", method = RequestMethod.GET)
    public @ResponseBody
    InventarioRutinario getInventario(@RequestParam long id) {
        return inventarioRutinarioService.obtenerPorId(id);
    }

    @RequestMapping(value = "getProductos", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductoLote> getProductos(HttpServletRequest request) {
        int idAlmacen = Integer.parseInt(request.getParameter("almacen"));
        List<ProductoLote> lista = productoLoteService.encontrarPorAlmacen(idAlmacen);
        return lista;
    }

    @RequestMapping(value = "getInventarios", method = RequestMethod.GET)
    public @ResponseBody
    List<InventarioRutinario> getInventarios(@PathVariable long idModulo, HttpServletRequest request) {
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        List<InventarioRutinario> lista = inventarioRutinarioService.listarModuloAlmacen(idModulo, idAlmacen);
        return lista;
    }

    @RequestMapping(value = "consultarInventarios", method = RequestMethod.GET)
    public @ResponseBody
    List<InventarioRutinario> consultarInventarios(@PathVariable long idModulo, HttpServletRequest request) {
        int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("start");
            String endDate = request.getParameter("end");
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }
        } catch (ParseException ex) {
            Logger.getLogger(InventarioRutinarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<InventarioRutinario> lista = inventarioRutinarioService.ConsultaInventarioRutinario(idModulo, idAlmacen, start, end);
        return lista;
    }

    @RequestMapping(value = "obtenerStocksGeneral", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse<Map<String, Integer>> obtenerStockGlobal(
            @PathVariable Long idModulo, Integer idProducto) {
       
        AjaxResponse<Map<String, Integer>> response = new AjaxResponse<Map<String, Integer>>();
        if(idProducto != null) {
            Map<String, Integer> data = inventarioRutinarioService
                    .obtenerStocksGlobal(idModulo, idProducto);
            response.setData(data);
        } else {
            response.setHasError(true);
            response.addMensaje("Debe seleccionar un producto");
        }
        return response;
    }
    
    
    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<InventarioRutinario>> insertarInventario(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<InventarioRutinario>> response = new AjaxResponse<List<InventarioRutinario>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        long idAlmacen = Long.parseLong(request.getParameter("idAlmacen"));
        String idLote = request.getParameter("idLote");
        long idProducto = Long.parseLong(request.getParameter("idProducto"));
        ProductoLotePk productoLotePk = new ProductoLotePk(idProducto, idLote, idAlmacen);
        ProductoLote productoLote = productoLoteService.obtenerPorId(productoLotePk);
        InventarioRutinario inventarioRutinario = new InventarioRutinario();

        inventarioRutinario.setIdModulo(idModulo);
        inventarioRutinario.setUsuarioCreacion(usuario.getIdUsuario());
        inventarioRutinario.setUsuarioModificacion(usuario.getIdUsuario());
        inventarioRutinario.setProductolote(productoLote);
        inventarioRutinario.setStock(productoLote.getProducto().getStockMax());
        inventarioRutinario.setStockReal(productoLote.getProducto().getStockMax());
        inventarioRutinario.setPrecio(productoLote.getPrecio());

        if (inventarioRutinarioService.contieneProducto(idModulo, idAlmacen, idProducto, idLote)) {
            response.addMensaje("Este producto ya existe en el inventario");
            response.setHasError(true);
            return response;
        }

        boolean result = inventarioRutinarioService.insertar(inventarioRutinario);

        List<InventarioRutinario> data = new ArrayList<InventarioRutinario>();
        data.add(inventarioRutinario);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el producto al inventario");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este producto.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarInventario(@RequestParam long id) {
        AjaxResponse<InventarioRutinario> response = new AjaxResponse<InventarioRutinario>();
        boolean result = inventarioRutinarioService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el producto.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este producto.");
        }
        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<InventarioRutinario>> modificarInventario(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<InventarioRutinario>> response = new AjaxResponse<List<InventarioRutinario>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        long idInventario = Long.parseLong(request.getParameter("id"));
        long stock = Long.parseLong(request.getParameter("stock"));
        long stockReal = Long.parseLong(request.getParameter("stockReal"));
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        InventarioRutinario inventarioRutinario = inventarioRutinarioService.obtenerPorId(idInventario);
        inventarioRutinario.setUsuarioModificacion(usuario.getIdUsuario());
        inventarioRutinario.setStock(stock);
        inventarioRutinario.setStockReal(stockReal);
        inventarioRutinario.setPrecio(precio);

        boolean result = inventarioRutinarioService.actualizar(inventarioRutinario);

//        List<InventarioRutinario> data = new ArrayList<InventarioRutinario>();
        List<InventarioRutinario> data = inventarioRutinarioService.listarModuloAlmacen(idModulo, inventarioRutinario.getIdAlmacen());
//        data.add(inventarioRutinario);
        if (result) {
            response.addMensaje("Se ha actualizado exitosamente el producto  en el inventario");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al actualizar este producto.");
            response.setHasError(true);
        }

        return response;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<InventarioRutinario> lista = new ArrayList<InventarioRutinario>();
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Código", "Descripcion", "F.V.", "Lote", "Tipo", "F.F", "Stock", "Stock Correcto", "Precio");
        List<String> contentFields = Arrays.asList("id", "productolote:producto:descripcion", "productolote:fechaVencimiento", "productolote:lote:descripcion", "productolote:producto:idTipoProducto:nombreTipoProducto", "productolote:producto:idFormaFarmaceutica:nombreFormaFarmaceutica", "stock", "stockReal", "precio");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        GpAlmacen almacen;

        try {
            int idAlmacen = Integer.parseInt(request.getParameter("idAlmacen"));
            almacen = almacenService.obtenerPorId(idAlmacen);
            lista = inventarioRutinarioService.listarModuloAlmacen(idModulo, idAlmacen);
        } catch (Exception e) {
            almacen = new GpAlmacen();
            almacen.setDescripcion(" ");
            java.util.logging.Logger.getLogger(InventarioRutinarioController.class.getName()).log(Level.INFO, null, e);
        }

        contentColumns.put(0, 5);
        contentColumns.put(1, 17);
        contentColumns.put(2, 10);
        contentColumns.put(3, 10);
        contentColumns.put(4, 10);
        contentColumns.put(5, 10);
        contentColumns.put(6, 10);
        contentColumns.put(7, 10);
        contentColumns.put(8, 10);
        model.addObject("Title", "Reporte de Inventario Rutinario");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        headerData.put("Almacen", almacen.getDescripcion());
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "InventarioRutinarioPDF");

        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "InventarioRutinarioExcel");

        return model;
    }

}
