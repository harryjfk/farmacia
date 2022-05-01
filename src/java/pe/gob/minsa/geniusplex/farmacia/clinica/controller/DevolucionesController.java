/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.clinica.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import pe.gob.minsa.geniusplex.farmacia.clinica.domain.Devolucion;
import pe.gob.minsa.geniusplex.farmacia.clinica.services.DevolucionesService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author stark
 */
@Controller
@RequestMapping("/{idModulo}/devoluciones/*")
public class DevolucionesController {

    @Autowired
    private DevolucionesService devolucionesService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private GpProductoService productoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("viewTitle", "Devoluciones");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Forma de Presentacion", "Cantidad", "Valor Unitario", "Valor Total","Fecha", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,id,produto.descripcion,produto.presentacion,cantidad,valorUnitario,valorTotal,fechaCreacion:date");
        model.addAttribute("findItem", "");
        return "Devoluciones/Listar";
    }

    @RequestMapping(value = "getDevoluciones", method = RequestMethod.GET)
    public @ResponseBody
    List<Devolucion> getDevolucion(@PathVariable long idModulo, HttpServletRequest request) {
        String idPaciente = request.getParameter("paciente");
        Paciente paciente = pacienteService.obtenerReferencia(idPaciente);

        List<Devolucion> lista = devolucionesService.listarDevolucionesPaciente(paciente, idModulo);
        return lista;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Devolucion>> insertar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Devolucion>> response = new AjaxResponse<List<Devolucion>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        Devolucion devolucion = new Devolucion();

        getData(request, devolucion);

        setMetaData(devolucion, usuario, idModulo);

        boolean result = devolucionesService.actualizar(devolucion);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Registro.");

        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Registro.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminar(@RequestParam long id) {
        AjaxResponse<Devolucion> response = new AjaxResponse<Devolucion>();
        boolean result = devolucionesService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente este registro.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este registro");
        }
        return response;
    }

//    @RequestMapping(value = "getOneDevolucion", method = RequestMethod.GET)
//    public @ResponseBody
//    Devolucion getOneDevolucion(@RequestParam long id) {
//       return devolucionService.obtenerPorId(id);
//    }
//    @RequestMapping(value = "modificar", method = RequestMethod.POST)
//    public @ResponseBody
//    AjaxResponse<List<Devolucion>> modificar(@PathVariable long idModulo, HttpServletRequest request) {
//        AjaxResponse<List<Devolucion>> response = new AjaxResponse<List<Devolucion>>();
//        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
//        long idMedida = Long.parseLong(request.getParameter("id"));
//        Devolucion devolucion = devolucionService.obtenerPorId(idMedida);
//        getData(request, devolucion);
//        
//        setMetaData(devolucion, usuario, idModulo);
//        devolucion.setUsuarioModificacion(usuario.getIdUsuario());
//
//        
//        boolean result = devolucionService.actualizar(devolucion);
//        if (result) {
//            response.addMensaje("Se ha modificado exitosamente el Registro.");
//            
//        } else {
//            response.addMensaje("Ha ocurrido un error al modificar este Registro.");
//            response.setHasError(true);
//        }
//
//        return response;
//    }
//    
    private void setMetaData(Devolucion devolucion, Usuario usuario, long idModulo) {
        devolucion.setUsuarioCreacion(usuario.getIdUsuario());
        devolucion.setUsuarioModificacion(usuario.getIdUsuario());
        devolucion.setIdModulo(idModulo);
    }

    private void getData(HttpServletRequest request, Devolucion devolucion) throws NumberFormatException {
        String idPaciente = request.getParameter("paciente");
        String valor = request.getParameter("valorUnitario");
        BigDecimal valorUnitario = new BigDecimal(valor);
        String tipo = request.getParameter("tipo");
        String observaciones = request.getParameter("observaciones");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int idProducto = Integer.parseInt(request.getParameter("producto"));
        GpProducto producto = productoService.obtenerPorId(idProducto);
        Paciente paciente = pacienteService.obtenerReferencia(idPaciente);
        String servicio = request.getParameter("servicio");

        devolucion.setPaciente(paciente);
        devolucion.setServicio(servicio);
        devolucion.setValorUnitario(valorUnitario);
        devolucion.setTipo(tipo);
        devolucion.setCantidad(cantidad);
        devolucion.setObservaciones(observaciones);
        devolucion.setProduto(producto);

    }
    
    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "DevolucionPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "DevolucionExcel");
        return model;
    }
    
    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        String paz = request.getParameter("paciente");
        Paciente paciente = pacienteService.obtenerPorId(paz);
        List<Devolucion> lista = devolucionesService.listarDevolucionesPaciente(paciente, idModulo);
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Código", "Descripción", "Forma de Presentacion", "Cantidad", "Valor Unitario", "Valor Total","Fecha");
        List<String> contentFields = Arrays.asList("id", "produto:descripcion", "produto:presentacion", "cantidad", "valorUnitario" , "valorTotal" , "fechaCreacion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 30);
        contentColumns.put(2, 15);
        contentColumns.put(3, 15);
        contentColumns.put(4, 15);
        contentColumns.put(5, 10);
        contentColumns.put(6, 10);
        model.addObject("Title", "Devoluciones");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
    
}
