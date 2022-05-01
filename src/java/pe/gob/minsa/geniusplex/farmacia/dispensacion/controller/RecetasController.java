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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLotePk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.RecetaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProductoLoteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.RecetaProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/recetas/*")
public class RecetasController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ProductoLoteService productoLoteService;
    @Autowired
    private RecetaProductoService recetaProductoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPacientes(Model model) {
        model.addAttribute("viewTitle", "Recetas Atendidas y no Atendidas");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripci&oacute;n", "Registro Sanitario", "Cantidad", "Lote", "Precio", "F.V", "Representaci&oacute;n", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "idReceta,idReceta,productoLote.producto.descripcion,productoLote.registroSanitario,cantidad,idLote,productoLote.precio,productoLote.fechaVencimiento:date,productoLote.producto.presentacion");
        model.addAttribute("findItem", "getReceta");
        return "Receta/listar";
    }

    @RequestMapping(value = "consulta", method = RequestMethod.GET)
    public String ConsultarRecetas(Model model) {
        model.addAttribute("viewTitle", "Recetas Atendidas y no Atendidas");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripcion", "Registro Sanitario", "Cantidad", "Lote", "Precio", "F.V", "Representaci&oacute;n"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "idReceta,idReceta,productoLote.producto.descripcion,productoLote.registroSanitario,cantidad,idLote,productoLote.precio,productoLote.fechaVencimiento:date,productoLote.producto.presentacion");
        model.addAttribute("findItem", "");
        return "Receta/consultar";
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<RecetaProducto>> insertar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<RecetaProducto>> response = new AjaxResponse<List<RecetaProducto>>();
        RecetaProducto receta = new RecetaProducto();

        String idPaciente = request.getParameter("id");
        Paciente paciente = pacienteService.obtenerPorId(idPaciente);
        String lote = request.getParameter("lote");
        Long idAlmacen = Long.parseLong(request.getParameter("almacen"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        long idProducto = Long.parseLong(request.getParameter("idProducto"));
        ProductoLotePk productoLotePk = new ProductoLotePk(idProducto, lote, idAlmacen);
        ProductoLote productoLote = productoLoteService.obtenerPorId(productoLotePk);
        receta.setCantidad(cantidad);
        receta.setProductoLote(productoLote);

        List<RecetaProducto> recetas = recetaProductoService.listarRecetasPacienteProducto(idModulo, paciente, lote, idAlmacen, idProducto);

        if (recetas != null && recetas.size() > 0) {
            response.addMensaje("Este Paciente ya posee este producto.");
            response.setHasError(true);
            return response;
        }

        receta.setPaciente(paciente);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        receta.setUsuarioCreacion(usuario.getIdUsuario());
        receta.setUsuarioModificacion(usuario.getIdUsuario());
        receta.setIdModulo(idModulo);
        boolean result = recetaProductoService.actualizar(receta);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente este producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar esta Receta.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<RecetaProducto> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        long paciente = Long.parseLong(request.getParameter("paciente"));
        String fIniString = request.getParameter("fechaIni");
        String fFinString = request.getParameter("fechaFin");
        Date fechaIni = null;
        Date fechaFin = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (fIniString != null) {
            try {
                fechaIni = sdf.parse(fIniString);
            } catch (ParseException ex) {
                Logger.getLogger(RecetasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fFinString != null) {
            try {
                fechaFin = sdf.parse(fFinString);
            } catch (ParseException ex) {
                Logger.getLogger(RecetasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<RecetaProducto> lista = recetaProductoService.ConsultaReceta(idModulo, paciente, fechaIni, fechaFin);
        return lista;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminar(@RequestParam long id) {
        AjaxResponse<RecetaProducto> response = new AjaxResponse<RecetaProducto>();
        boolean result = recetaProductoService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el registro.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este registro");
        }
        return response;
    }

    @RequestMapping(value = "getReceta", method = RequestMethod.GET)
    public @ResponseBody
    RecetaProducto getReceta(@PathVariable long idModulo, HttpServletRequest request) {
        long idReceta = Long.parseLong(request.getParameter("id"));

        RecetaProducto lista = recetaProductoService.obtenerPorId(idReceta);
        return lista;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<RecetaProducto> modificarRecetaProducto(HttpServletRequest request) {
        AjaxResponse<RecetaProducto> response = new AjaxResponse<RecetaProducto>();
        long idReceta = Long.parseLong(request.getParameter("id"));
        RecetaProducto receta = recetaProductoService.obtenerPorId(idReceta);
        String lote = request.getParameter("lote");
        Long idAlmacen = Long.parseLong(request.getParameter("almacen"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        long idProducto = Long.parseLong(request.getParameter("idProducto"));
        ProductoLotePk productoLotePk = new ProductoLotePk(idProducto, lote, idAlmacen);
        ProductoLote productoLote = productoLoteService.obtenerPorId(productoLotePk);

        receta.setCantidad(cantidad);
        receta.setProductoLote(productoLote);
        long idModulo = receta.getIdModulo();
        Paciente paciente = receta.getPaciente();
        List<RecetaProducto> recetas = recetaProductoService.listarRecetasPacienteProducto(idModulo, paciente, lote, idAlmacen, idProducto);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        receta.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = recetaProductoService.actualizar(receta);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente este producto.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Producto.");
            response.setHasError(true);
        }
        return response;
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
        String idPaciente = request.getParameter("paciente");
        String fIniString = request.getParameter("fechaIni");
        String fFinString = request.getParameter("fechaFin");
        Date fechaIni = null;
        Date fechaFin = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (fIniString != null) {
            try {
                fechaIni = sdf.parse(fIniString);
            } catch (ParseException ex) {
                Logger.getLogger(RecetasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (fFinString != null) {
            try {
                fechaFin = sdf.parse(fFinString);
            } catch (ParseException ex) {
                Logger.getLogger(RecetasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<RecetaProducto> lista = recetaProductoService.ConsultaReceta(idModulo, Long.parseLong(idPaciente), fechaIni, fechaFin);
        Paciente paciente = pacienteService.obtenerPorId(idPaciente);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Código", "Descripcion", "Registro Sanitario", "Cantidad", "Lote", "Precio", "F.V", "Representacion");
        List<String> contentFields = Arrays.asList("idReceta", "productoLote:producto:descripcion", "productoLote:registroSanitario", "cantidad", "idLote", "productoLote:precio", "productoLote:fechaVencimiento", "productoLote:producto:presentacion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 10);
        contentColumns.put(1, 25);
        contentColumns.put(2, 10);
        contentColumns.put(3, 10);
        contentColumns.put(4, 10);
        contentColumns.put(5, 10);
        contentColumns.put(6, 25);
        contentColumns.put(7, 25);
        model.addObject("Title", "Reporte de Recetas Atendidas y No Atendidas");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        HashMap<String, String> headerData = new HashMap<String, String>();
        headerData.put("Paciente", paciente.getNombres());
        headerData.put("Fecha de Nacimiento", new SimpleDateFormat("dd/MM/yyyy").format(paciente.getFechaNacimiento()));
        headerData.put("Historia Clinica", paciente.getHistoria());
        headerData.put("Código", paciente.getPaciente());
        headerData.put("Sexo", paciente.getSexo().toString());
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

}
