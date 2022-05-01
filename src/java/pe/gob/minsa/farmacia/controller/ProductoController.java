package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import pe.gob.minsa.farmacia.domain.FormaFarmaceutica;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.domain.TipoProducto;
import pe.gob.minsa.farmacia.domain.UnidadMedida;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoIngresoDto;
import pe.gob.minsa.farmacia.domain.dto.AyudaProductoSalidaDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoAlertaVencimientoDto;
import pe.gob.minsa.farmacia.domain.dto.ProductoComp;
import pe.gob.minsa.farmacia.domain.param.ProductoAlmacenParam;
import pe.gob.minsa.farmacia.domain.param.ProductoParam;
import pe.gob.minsa.farmacia.services.impl.FormaFarmaceuticaService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.services.impl.ProductoSigaService;
import pe.gob.minsa.farmacia.services.impl.ProductoSismedService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.services.impl.UnidadMedidaService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;

@Controller
@RequestMapping("/Producto")
public class ProductoController {

    @Autowired
    ProductoService productoService;
    
    @Autowired
    FormaFarmaceuticaService formaFarmaceuticaService;
    
    @Autowired
    TipoProductoService tipoProductoService;
    
    @Autowired
    UnidadMedidaService unidadMedidaService;
    
    @Autowired
    ProductoSigaService productoSigaService;
    
    @Autowired
    ProductoSismedService productoSismedService;

    JsonResponse jsonResponse;

    private ManagerDatatables getProductoDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        ProductoParam productoParam = new ProductoParam();
        
        if(request.getParameter("descripcion") != null){
            productoParam.setDescripcion(request.getParameter("descripcion"));
        }
        
        if(request.getParameter("idFormaFarmaceutica") != null){
            productoParam.setIdFormaFarmaceutica(Integer.parseInt(request.getParameter("idFormaFarmaceutica")));
        }
        
        if(request.getParameter("idTipoProducto") != null){
            productoParam.setIdTipoProducto(Integer.parseInt(request.getParameter("idTipoProducto")));
        }
        
        if(request.getParameter("idUnidadMedida") != null){
            productoParam.setIdUnidadMedida(Integer.parseInt(request.getParameter("idUnidadMedida")));
        }
        
        if(request.getParameter("estrSop") != null){
            productoParam.setEstrSop(Integer.parseInt(request.getParameter("estrSop")));
        }
        
        if(request.getParameter("estrVta") != null){
            productoParam.setEstrVta(Integer.parseInt(request.getParameter("estrVta")));
        }
        
        if(request.getParameter("traNac") != null){
            productoParam.setTraNac(Integer.parseInt(request.getParameter("traNac")));
        }
        
        if(request.getParameter("traLoc") != null){
            productoParam.setTraLoc(Integer.parseInt(request.getParameter("traLoc")));
        }
        
        if(request.getParameter("narcotico") != null){            
            productoParam.setNarcotico(Integer.parseInt(request.getParameter("narcotico")));
        }       
        
        List<ProductoComp> productos = productoService.listar(productoParam);
        
        managerDatatables.setiTotalRecords(0);

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(productos, new Comparator<ProductoComp>() {
            @Override
            public int compare(ProductoComp o1, ProductoComp o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return String.valueOf(o1.getDescripcion()).toLowerCase().compareTo(String.valueOf(o2.getDescripcion()).toLowerCase()) * sortDirection;                    
                }
                return 0;
            }
        });

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
    
    private ManagerDatatables getProductoPorAlmacenDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        ProductoAlmacenParam productoAlmacenParam = new ProductoAlmacenParam();
        
        if(request.getParameter("descripcion") != null){
            productoAlmacenParam.setDescripcion(request.getParameter("descripcion"));
        }
        
        if(request.getParameter("idFormaFarmaceutica") != null){
            productoAlmacenParam.setIdFormaFarmaceutica(Integer.parseInt(request.getParameter("idFormaFarmaceutica")));
        }
        
        if(request.getParameter("idTipoProducto") != null){
            productoAlmacenParam.setIdTipoProducto(Integer.parseInt(request.getParameter("idTipoProducto")));
        }
        
        if(request.getParameter("idUnidadMedida") != null){
            productoAlmacenParam.setIdUnidadMedida(Integer.parseInt(request.getParameter("idUnidadMedida")));
        }
        
        if(request.getParameter("idAlmacen") != null){
            productoAlmacenParam.setIdAlmacen(Integer.parseInt(request.getParameter("idAlmacen")));
        }
        
        List<ProductoComp> productos = productoService.listarPorAlmacen(productoAlmacenParam);
        
        managerDatatables.setiTotalRecords(0);

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(productos, new Comparator<ProductoComp>() {
            @Override
            public int compare(ProductoComp o1, ProductoComp o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return String.valueOf(o1.getDescripcion()).toLowerCase().compareTo(String.valueOf(o2.getDescripcion()).toLowerCase()) * sortDirection;                    
                }
                return 0;
            }
        });

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
    
    private ManagerDatatables getProductosAyudaIngreso(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
                
        List<AyudaProductoIngresoDto> productos = productoService.listarAyudaProductoIngreso(request.getParameter("criterio"));
        
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
    
    private ManagerDatatables getProductosAyudaSalida(HttpServletRequest request, HttpServletResponse response) {
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
                
        List<AyudaProductoSalidaDto> productos = productoService.listarAyudaProductoSalida(request.getParameter("criterio"), Integer.parseInt(request.getParameter("idAlmacen")));
        
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
    
    private ManagerDatatables getProductosAlertaVencimiento(HttpServletRequest request, HttpServletResponse response){
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
                
        List<ProductoAlertaVencimientoDto> productos = productoService.listarAlertaVencimiento();
        
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
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarProducto() {
        
        List<FormaFarmaceutica> formasFarmaceuticas = formaFarmaceuticaService.listar();
        List<TipoProducto> tiposProducto = tipoProductoService.listar();
        List<UnidadMedida> unidadesMedida = unidadMedidaService.listar();
        
        ModelMap model = new ModelMap();
        model.put("formasFarmaceuticas", formasFarmaceuticas);
        model.put("tiposProducto", tiposProducto);
        model.put("unidadesMedida", unidadesMedida);
        
        return new ModelAndView("Producto", model);
    }

    @RequestMapping(value = "/productosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getProductoDatatables(request, response);
    }
    
    @RequestMapping(value = "/productosPorAlmacenJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosPorAlmacenJSON(HttpServletRequest request, HttpServletResponse response){
        return getProductoPorAlmacenDatatables(request, response);
    }
    
    @RequestMapping(value = "/productosIngresoJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosIngresoJSON(HttpServletRequest request, HttpServletResponse response){
        return getProductosAyudaIngreso(request, response);
    }
    
    @RequestMapping(value = "/productosSalidaJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosSalidaJSON(HttpServletRequest request, HttpServletResponse response){
        return getProductosAyudaSalida(request, response);
    }
    
    @RequestMapping(value = "/productosAlertaJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerProductosAlertaVencimientoJSON(HttpServletRequest request, HttpServletResponse response){
        return getProductosAlertaVencimiento(request, response);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView registrarProductoGET() {
        
        ModelMap model = new ModelMap();
        model.put("formasFarmaceuticas", formaFarmaceuticaService.listarActivos());
        model.put("tiposProducto", tipoProductoService.listarActivos());
        model.put("unidadesMedida", unidadMedidaService.listarActivos());
        model.put("productosSiga", productoSigaService.listarActivos());
        model.put("productosSismed", productoSismedService.listarActivos());
        
        return new ModelAndView("Producto/registrar", model);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarProductoPOST(@RequestBody Producto producto) {

        try {
            productoService.insertar(producto);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/productoJSON", method = RequestMethod.GET)
    @ResponseBody
    public Producto obtenerProductoJSON(@RequestParam int id, HttpServletResponse response) {
        Producto producto = new Producto();

        try {
            producto = productoService.obtenerPorId(id);
        } catch (BusinessException ex) {
            InterceptorSecurity.BussinessExceptionHandlerAjax(ex, response);
        }

        return producto;
    }

    @RequestMapping(value = "/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarProductoGET(@PathVariable int id, HttpServletRequest request) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            
            ModelMap model = new ModelMap();
            model.put("formasFarmaceuticas", formaFarmaceuticaService.listarActivos(producto.getIdFormaFarmaceutica()));
            model.put("tiposProducto", tipoProductoService.listarActivos(producto.getIdTipoProducto()));
            model.put("unidadesMedida", unidadMedidaService.listarActivos(producto.getIdUnidadMedida()));
            model.put("productosSiga", productoSigaService.listarActivos(producto.getIdProductoSiga()));
            model.put("productosSismed", productoSismedService.listarActivos(producto.getIdProductoSismed()));
            model.put("producto", producto);

            return new ModelAndView("Producto/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Producto");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarProductoPOST(@RequestBody Producto producto) {
        try {
            productoService.actualizar(producto);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            productoService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarProducto(@PathVariable int id) {
        try {
            productoService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();            
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProductoDatatables(request, response);

        return new ModelAndView("ProductoPDF", "Data", managerDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = getProductoDatatables(request, response);

        return new ModelAndView("ProductoExcel", "Data", managerDatatables.getAaData());
    }
}