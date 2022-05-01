package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.math.BigDecimal;
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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Equipo;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.MateriasService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpTipoProductoService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpUnidadMedidaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/materias/*")
public class PreciosController {

    @Autowired
    private MateriasService materiasService;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private GpUnidadMedidaService unidadService;

    @Autowired
    private GpTipoProductoService tipoProductoService;

    @RequestMapping(value = "listarPrecios", method = RequestMethod.GET)
    public ModelAndView listarPrecios(HttpServletRequest request, ModelAndView model) {
        model.addObject("viewTitle", "Registro de Precios");
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Nombre del Insumo", "Unidad de Medida", "Precios", "Cantidad", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "modificar");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "id,nombre,unidad.nombreUnidadMedida,precio:money2,cantidad");
        model.addObject("findItem", "getPrecio");
        List<GpUnidadMedida> umList = unidadService.listar();
        model.addObject("umLista", umList);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.setViewName("Precios/listar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "listarMaterias", method = RequestMethod.GET)
    public ModelAndView listarMaterias(HttpServletRequest request, ModelAndView model) {
        model.addObject("viewTitle", "Registro Materias Primas, Envases y Precios");
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Nombre del Insumo", "Tipo de Producto", "Unidad de Medida", "Precio", "Cantidad", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "modificar");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "id,nombre,tipoProducto.nombreTipoProducto,unidad.nombreUnidadMedida,precio:money2,cantidad");
        model.addObject("findItem", "getPrecio");
        model.addObject("tipoProductos", tipoProductoService.listar());
        List<GpUnidadMedida> umList = unidadService.listar();
        model.addObject("umLista", umList);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.setViewName("Materias/listar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "getPrecios", method = RequestMethod.GET)
    public @ResponseBody
    List<Materias> getPrecios(@PathVariable long idModulo, HttpServletRequest request) {
        final String idAlmacenStr = request.getParameter("almacen");
        Integer idAlmacen=0;
        GpAlmacen almacen=null;
        if (idAlmacenStr != null) {
             idAlmacen = Integer.parseInt(idAlmacenStr);
             almacen = almacenService.obtenerPorId(idAlmacen);
        }
        
        
        List<Materias> lista = materiasService.listarPorModuloAlmacen(idModulo, almacen);
        return lista;
    }

    @RequestMapping(value = "getMaterias", method = RequestMethod.GET)
    public @ResponseBody
    List<Materias> getMaterias(@PathVariable long idModulo, HttpServletRequest request) {
        List<Materias> lista = materiasService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getPrecio", method = RequestMethod.GET)
    public @ResponseBody
    Materias getPrecio(@RequestParam long id) {
        return materiasService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Materias>> insertarEquipo(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Materias>> response = new AjaxResponse<List<Materias>>();

        Materias materia = new Materias();
        setData(request, materia);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        materia.setUsuarioCreacion(usuario.getIdUsuario());
        materia.setUsuarioModificacion(usuario.getIdUsuario());
        materia.setIdModulo(idModulo);
        Integer idAlmacen = Integer.parseInt(request.getParameter("almacen"));
        GpAlmacen almacen = almacenService.obtenerPorId(idAlmacen);
        materia.setAlmacen(almacen);
        List<Materias> lista = materiasService.listarPorModuloAlmacen(idModulo, almacen);

        if (lista.contains(materia)) {
            response.addMensaje("Este producto ya existe.");
            response.setHasError(true);
            return response;
        }

        boolean result = materiasService.insertar(materia);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este producto.");
            response.setHasError(true);
        }

        return response;
    }

    private void setData(HttpServletRequest request, Materias materia) throws NumberFormatException {
        String nombre = request.getParameter("nombre");
        Integer unidad = Integer.parseInt(request.getParameter("unidad"));
        Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
        Integer idTipoProducto = Integer.parseInt(request.getParameter("tipoProducto"));

        String precio = request.getParameter("precio");
        BigDecimal realPrice = new BigDecimal(precio.replace(",", "."));

        materia.setNombre(nombre);
        materia.setPrecio(realPrice);
        materia.setCantidad(cantidad);
        GpUnidadMedida um = unidadService.obtenerPorId(unidad);
        materia.setUnidad(um);

        GpTipoProducto tipoProducto = tipoProductoService.obtenerPorId(idTipoProducto);
        materia.setTipoProducto(tipoProducto);

    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarPrecio(@RequestParam long id) {
        AjaxResponse<Equipo> response = new AjaxResponse<Equipo>();
        boolean result = materiasService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el producto.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este producto");
        }
        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Materias> modificarEquipo(HttpServletRequest request) {
        AjaxResponse<Materias> response = new AjaxResponse<Materias>();
        long idmateria = Long.parseLong(request.getParameter("id"));
        Materias materia = materiasService.obtenerPorId(idmateria);
        setData(request, materia);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        materia.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = materiasService.actualizar(materia);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este producto.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public @ResponseBody
    List<Materias> consultarInventarios(@PathVariable long idModulo, HttpServletRequest request) {
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
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        List<Materias> lista = materiasService.ConsultaMaterias(idModulo, idAlmacen, start, end);
        return lista;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<Materias> lista = materiasService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Nombre del Insumo", "Unidad de Medida", "Precios", "Cantidad");
        List<String> contentFields = Arrays.asList("nombre", "unidad.nombreUnidadMedida", "precio", "cantidad");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 25);
        contentColumns.put(1, 25);
        contentColumns.put(2, 25);
        contentColumns.put(3, 25);
        model.addObject("Title", "Reporte");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }

    @RequestMapping(value = "getAlmacenes", method = RequestMethod.GET)
    public @ResponseBody
    List<GpAlmacen> getAlmacenes(@PathVariable long idModulo) {
        List<GpAlmacen> lista = almacenService.listarPorModulo(idModulo);
        return lista;
    }
}
