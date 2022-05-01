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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Materias;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.Matriz;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.MatrizMateria;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.MateriasService;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.MatrizService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpUnidadMedida;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpUnidadMedidaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/matriz/*")
public class MatrizController {

    @Autowired
    private MatrizService matrizService;
    @Autowired
    private MateriasService materiasService;
    @Autowired
    private GpUnidadMedidaService unidadService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPrecios(@PathVariable Long idModulo, Model model) {
        model.addAttribute("viewTitle", "Matriz de fórmula Magistral y Oficiales");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Descripci&oacute;n", "Unidad de Medida", "Porcentaje", "Precio", "Cantidad", "Acciones"}));
        model.addAttribute("ajaxList", "getMatrices");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,nombre,unidad.nombreUnidadMedida,porcentaje,precio:money2,cantidad");
        model.addAttribute("findItem", "getMatriz");
        List<GpUnidadMedida> umList = unidadService.listar();
        model.addAttribute("umLista", umList);
        List<Materias> materias = materiasService.listarModulo(idModulo);
        model.addAttribute("materias", materias);
        return "Matriz/listar";
    }

    @RequestMapping(value = "getMatrices", method = RequestMethod.GET)
    public @ResponseBody
    List<Matriz> getMatrices(@PathVariable long idModulo, HttpServletRequest request) {
        List<Matriz> lista = matrizService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getMatricesConInsumos", method = RequestMethod.GET)
    public @ResponseBody
    List<Matriz> getgetMatricesConInsumos(@PathVariable long idModulo, HttpServletRequest request) {
        List<Matriz> lista = matrizService.listarConInsumos(idModulo);
        return lista;
    }

    @RequestMapping(value = "getInsumos", method = RequestMethod.POST)
    public @ResponseBody
    List<MatrizMateria> getInsumos(Long matrizId) {
        Matriz matriz = matrizService.obtenerPorId(matrizId);
        return matriz.getMatrizInsumos();
    }

    @RequestMapping(value = "agregarInsumo", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse agregarInsumo(Long matrizId, Long materiaId, Integer cant) {
        AjaxResponse response = new AjaxResponse();
        Matriz matriz = matrizService.obtenerPorId(matrizId);
        Materias insumo = materiasService.obtenerPorId(materiaId);
        
        if(cant == null) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar la cantidad");
            return response;
        }
        
        boolean hasInsumo = matrizService.hasInsumo(matriz, insumo);
        
        MatrizMateria mm = new MatrizMateria();
        mm.setCantidad(cant);
        mm.setInsumo(insumo);
        mm.setMatriz(matriz);
        
        if (!hasInsumo) {
            matriz.getMatrizInsumos().add(mm);
            boolean ok = matrizService.actualizar(matriz);
            if (ok) {
                response.addMensaje("El insumo fue agragado correctamente");
                response.setData(mm);
            } else {
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error");
            }
        } else {
            response.setHasError(true);
            response.addMensaje("La matriz ya contiene el insumo seleccionado");
        }
        return response;
    }
    
    @RequestMapping(value = "eliminarInsumo", method = RequestMethod.POST)
    public @ResponseBody AjaxResponse eliminarInsumo(Long matrizId, Long materiaId) {
        AjaxResponse response = new AjaxResponse();
        Matriz matriz = matrizService.obtenerPorId(matrizId);
        Materias insumo = materiasService.obtenerPorId(materiaId);
        MatrizMateria mm = matrizService.getInsumo(matriz, insumo);
        boolean ok = matrizService.eliminarInsumo(matriz, mm);
        if(ok) {
            response.setData(matriz);
            response.addMensaje("Insumo eliminado correctamente");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error");
        }
        return response;
    }
    

    @RequestMapping(value = "getMatriz", method = RequestMethod.GET)
    public @ResponseBody
    Matriz getPrecio(@RequestParam long id) {
        Matriz matriz = matrizService.obtenerPorId(id);
        matrizService.sincronizar(matriz);
        return matriz;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Matriz>> insertarEquipo(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Matriz>> response = new AjaxResponse<List<Matriz>>();

        Matriz materia = new Matriz();
        setData(request, materia);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        materia.setUsuarioCreacion(usuario.getIdUsuario());
        materia.setUsuarioModificacion(usuario.getIdUsuario());
        materia.setIdModulo(idModulo);
        List<Matriz> lista = matrizService.listarModulo(idModulo);

        if (lista.contains(materia)) {
            response.addMensaje("Este producto ya existe.");
            response.setHasError(true);
            return response;
        }

        boolean result = matrizService.insertar(materia);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este producto.");
            response.setHasError(true);
        }

        return response;
    }

    private void setData(HttpServletRequest request, Matriz matriz) throws NumberFormatException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        Integer unidad = Integer.parseInt(request.getParameter("unidad"));
        String precio = request.getParameter("precio");
        String porcentaje = request.getParameter("porcentaje");
        Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
        BigDecimal realPrice = new BigDecimal(precio.replace(",", "."));

        matriz.setNombre(nombre);
        matriz.setPrecio(realPrice);
        GpUnidadMedida um = unidadService.obtenerPorId(unidad);
        matriz.setUnidad(um);
        matriz.setPorcentaje(porcentaje);
        matriz.setDescripcion(descripcion);
        matriz.setCantidad(cantidad);

    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarPrecio(@RequestParam long id) {
        AjaxResponse<Matriz> response = new AjaxResponse<Matriz>();
        boolean result = matrizService.eliminar(id);
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
    AjaxResponse<Matriz> modificar(HttpServletRequest request) {
        AjaxResponse<Matriz> response = new AjaxResponse<Matriz>();
        long idmatriz = Long.parseLong(request.getParameter("id"));
        Matriz matriz = matrizService.obtenerPorId(idmatriz);
        setData(request, matriz);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        matriz.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = matrizService.actualizar(matriz);
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
    List<Matriz> consultarInventarios(@PathVariable long idModulo, HttpServletRequest request) {
        String desc = request.getParameter("descripcion");
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

        List<Matriz> lista = matrizService.ConsultaMatriz(idModulo, desc, start, end);
        return lista;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericExcel");
        return model;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericPDF");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<Matriz> lista = matrizService.listarModulo(idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Nombre del Insumo", "Unidad de Medida", "Porcentaje", "Precios", "Cantidad");
        List<String> contentFields = Arrays.asList("nombre", "unidad.nombreUnidadMedida", "porcentaje", "precio", "cantidad");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 10);
        contentColumns.put(1, 60);
        contentColumns.put(2, 10);
        contentColumns.put(3, 10);
        contentColumns.put(4, 10);
        model.addObject("Title", "Matriz de fórmula Magistral y Oficiales");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
}
