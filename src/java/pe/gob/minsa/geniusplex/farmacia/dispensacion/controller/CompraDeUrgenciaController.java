/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraDeUrgencia;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.CompraUrgenciaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.CompraDeUrgenciaService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.CompraUrgenciaProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/compraDeUrgencia/*")
public class CompraDeUrgenciaController {

    @Autowired
    CompraDeUrgenciaService compraUrgenciaService;
    @Autowired
    private CompraUrgenciaProductoService compraUrgenciaProductoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarClientes(Model model) {
        model.addAttribute("viewTitle", "Mantenimiento de Compras de Urgencia");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"C&oacute;digo", "Descripci&oacute;n del Medicamento/Material", "Cantidad", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "producto.idProducto,producto.idProducto,producto.descripcion,cantidad");
        model.addAttribute("findItem", "");
        return "CompraDeUrgencia/listar";
    }

    @RequestMapping(value = "getProductos", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    AjaxResponse<CompraDeUrgencia> getProductos(@RequestBody CompraDeUrgencia compraDeUrgencia, 
            HttpServletRequest request) {

        AjaxResponse<CompraDeUrgencia> response = new AjaxResponse<CompraDeUrgencia>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        CompraDeUrgencia tmpCompra = compraUrgenciaService.obtener(compraDeUrgencia);
        if (tmpCompra != null) {
            compraDeUrgencia = tmpCompra;
            compraDeUrgencia.setUsuarioCreacion(usuario.getIdUsuario());
        } else {
            compraUrgenciaService.guardarCambios(compraDeUrgencia);
            compraDeUrgencia.setUsuarioModificacion(usuario.getIdUsuario());
        }
        response.setData(compraDeUrgencia);
        return response;
    }

    @RequestMapping(value = "guardarCambios", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<CompraDeUrgencia> guardarCambios(@RequestBody CompraDeUrgencia compraDeUrgencia, 
            HttpServletRequest request) {

        AjaxResponse<CompraDeUrgencia> response = new AjaxResponse<CompraDeUrgencia>();

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }
        compraDeUrgencia.setUsuarioModificacion(usuario.getIdUsuario());
        if(compraDeUrgencia.getId() == 0)
            compraDeUrgencia.setUsuarioCreacion(usuario.getIdUsuario());
        boolean result = compraUrgenciaService.guardarCambios(compraDeUrgencia);

        if (!result) {
            response.setHasError(result);
            response.addMensaje("Ha ocurrido un error al guardar los cambios.");
        } else {
            response.setData(compraDeUrgencia);
            response.addMensaje("Se han guardado los cambios correctamente.");
        }
        return response;
    }

    @RequestMapping(value = "prepararReporte", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse prepararReporte(HttpServletRequest request, @RequestBody FilterData fData) {
        HttpSession session = request.getSession();
        session.setAttribute("fData", fData);
        AjaxResponse response = new AjaxResponse();
        response.setHasError(false);
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response) {
        List<CompraUrgenciaProducto> list;
        CompraDeUrgencia compra = null;
        try {
            HttpSession session = request.getSession();
            FilterData fData = (FilterData) session.getAttribute("fData");
            if (fData != null) {
                long id = Long.parseLong(fData.getParams().get("compraDeUrgencia:id").toString());
                if (id == 0) {
                    return new ModelAndView("CompraDeUrgenciaPDF", "Data", null);
                }
                compra = compraUrgenciaService.obtenerPorId(id);
                list = compraUrgenciaProductoService.dynamicQuery(fData);
                compra.setCompraUrgenciaProductos(list);
            } else {
                return new ModelAndView("CompraDeUrgenciaPDF", "Data", null);
            }

        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CompraDeUrgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CompraDeUrgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ModelAndView("CompraDeUrgenciaPDF", "Data", compra);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response) {
        List<CompraUrgenciaProducto> list;
        CompraDeUrgencia cpmpra = null;
        try {
            HttpSession session = request.getSession();
            FilterData fData = (FilterData) session.getAttribute("fData");
            if (fData != null) {
                long id = Long.parseLong(fData.getParams().get("compraDeUrgencia:id").toString());
                if (id == 0) {
                    return new ModelAndView("CompraDeUrgenciaExcel", "Data", null);
                }
                cpmpra = compraUrgenciaService.obtenerPorId(id);
                list = compraUrgenciaProductoService.dynamicQuery(fData);
                cpmpra.setCompraUrgenciaProductos(list);
            } else {
                return new ModelAndView("CompraDeUrgenciaExcel", "Data", null);
            }

        } catch (NoSuchFieldException ex) {
            Logger.getLogger(CompraDeUrgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CompraDeUrgenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ModelAndView("CompraDeUrgenciaExcel", "Data", cpmpra);
    }
}
