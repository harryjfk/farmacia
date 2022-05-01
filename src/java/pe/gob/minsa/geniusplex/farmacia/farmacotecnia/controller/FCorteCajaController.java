/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.CorteCajaDTO;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.CorteCajaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

@Controller
@RequestMapping("/{idModulo}/fcortecaja/*")
public class FCorteCajaController {

    @Autowired
    CorteCajaService corteCajaService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public String listarClientes(Model model) {
        model.addAttribute("viewTitle", "Corte de Caja");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Fecha", "Nro. Oper.", "Nro. Docum.", "Tipo", "Cliente", "Cajero", "F.P.", "Venta", "IGV", "Total", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "venta.nroOperacion,venta.ventafechaRegistro:date,venta.nroOperacion,venta.nroVenta,venta.docTipo,venta.cliente.nombreCliente,venta.vendedor.nombreVendedor,venta.formaDePago.descripcion,totalVenta, venta.impuestoPreventa, venta.preventaNetoAPagar");
        model.addAttribute("findItem", "");
        return "FCorteDeCaja/procesar";
    }

    @RequestMapping(value = "filtrar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<CorteCajaDTO> filtrar(@PathVariable long idModulo,
            @RequestBody FilterData fData, HttpServletRequest request) {

        AjaxResponse<CorteCajaDTO> response = new AjaxResponse<CorteCajaDTO>();
        if (fData != null) {
            fData.getParams().put("IdModulo", idModulo);
            fData.setOrderFields(new String[]{"formaDePago:descripcion"});
            fData.setDirs(new String[]{"ASC"});
            String periodo = null;
            if (fData.getParams().containsKey("periodo")) {
                periodo = fData.getParams().get("periodo").toString();
                fData.getParams().remove("periodo");
            }
            try {
                fData.getParams().put("procesoVenta", true);
                CorteCajaDTO cajaDTO = corteCajaService.obtenerCorteCaja(fData);
                if (periodo != null) {
                    cajaDTO.checkPeriodo(periodo);
                }
                if (cajaDTO.getTblVentas().isEmpty()) {
                    response.setHasError(true);
                    response.addMensaje("No se ha encontrado ninguna venta con los par&aacute;metros de b&uacute;squeda.");
                    return response;
                } else {
                    response.setData(cajaDTO);
                    return response;
                }
            } catch (BusinessException ex) {
                Logger.getLogger(FCorteCajaController.class.getName()).log(Level.SEVERE, null, ex);
                response.setHasError(true);
                response.setMssg(ex.getMensajesError());
                return response;
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error inesperado");
            return response;
        }

    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<CorteCajaDTO> elimiar(@RequestParam String nroOperacion, HttpServletRequest request) throws NoSuchFieldException {
        AjaxResponse<CorteCajaDTO> response = new AjaxResponse<CorteCajaDTO>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con sus credenciales para poder realizar esta acci&oacute;n.");
            return response;
        }
        boolean ok = corteCajaService.eliminarPorNroOperaion(nroOperacion);
        if (ok) {
            response.addMensaje("Se ha eliminada correctamente.");
            return response;
        } else {
            response.addMensaje("Ha ocurrido un error inesperada.");
            response.setHasError(!ok);
            return response;
        }
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo,
            @RequestParam HashMap<String, Object> params) {

        FilterData fData = new FilterData();
        fData.setParams(params);
        CorteCajaDTO cajaDTO = null;
        fData.getParams().put("IdModulo", idModulo);
        fData.setOrderFields(new String[]{"formaDePago:descripcion"});
        fData.setDirs(new String[]{"ASC"});
        String periodo = null;
        if (fData.getParams().containsKey("periodo")) {
            periodo = fData.getParams().get("periodo").toString();
            fData.getParams().remove("periodo");
        }
        try {
            cajaDTO = corteCajaService.obtenerCorteCaja(fData);
            if (periodo != null) {
                cajaDTO.checkPeriodo(periodo);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(FCorteCajaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView modelAndView = new ModelAndView("FCorteCajaPDF", "CorteCaja", cajaDTO);
        modelAndView.addObject("fData", fData);
        return modelAndView;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo,
            @RequestParam HashMap<String, Object> params) {

        FilterData fData = new FilterData();
        fData.setParams(params);

        CorteCajaDTO cajaDTO = null;
        fData.getParams().put("IdModulo", idModulo);
        fData.setOrderFields(new String[]{"formaDePago:descripcion"});
        fData.setDirs(new String[]{"ASC"});
        String periodo = null;
        if (fData.getParams().containsKey("periodo")) {
            periodo = fData.getParams().get("periodo").toString();
            fData.getParams().remove("periodo");
        }
        try {
            cajaDTO = corteCajaService.obtenerCorteCaja(fData);
            if (periodo != null) {
                cajaDTO.checkPeriodo(periodo);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(FCorteCajaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelAndView modelAndView = new ModelAndView("FCorteCajaExcel", "CorteCaja", cajaDTO);
        modelAndView.addObject("fData", fData);
        return modelAndView;
    }

}
