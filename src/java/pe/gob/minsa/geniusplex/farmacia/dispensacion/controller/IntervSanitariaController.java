/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitaria;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.IntervSanitariaProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Prescriptor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.IntSanKitProductoDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.IntervSanitariaService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/intervencionsanitaria/*")
public class IntervSanitariaController {

    @Autowired
    IntervSanitariaService intervSanitariaService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public ModelAndView listarClientes(ModelAndView model, HttpServletRequest request) {
        model.addObject("viewTitle", "Salida por Intervenci&oacute;n Sanitaria");
        model.addObject("tableHeaders", Arrays.asList("Producto", "C&oacute;digo", "Cantidad", "Precio", "Imp. Parcial", "Acciones"));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "");
        model.addObject("tableProperties", "producto.idProducto,producto.descripcion,producto.idProducto,cantidad,precio:money4,importeParcial:money4");
        model.addObject("findItem", "");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model.setViewName("redirect:index");
        } else {
            model.setViewName("IntervecionSanitaria/procesar");
        }
        return model;
    }

    @RequestMapping(value = "getIntervSanitaria", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<IntervSanitaria> getIntervSanitaria(@PathVariable long idModulo, @RequestBody IntervSanitaria interv, HttpServletRequest request) {

        AjaxResponse<IntervSanitaria> response = new AjaxResponse<IntervSanitaria>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        interv = intervSanitariaService.obtenerPorId(interv.getNroSalida());
        response.setData(interv);
        return response;
    }

    @RequestMapping(value = "guardarCambios", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<IntervSanitaria> guardarCambios(@PathVariable long idModulo, @RequestBody HashMap<String, Object> map, HttpServletRequest request) {
        IntervSanitaria interv = crearIntervSanitaria(map);
        AjaxResponse<IntervSanitaria> response = new AjaxResponse<IntervSanitaria>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            if (interv.getNroSalida() == null || interv.getNroSalida().length() == 0) {
                interv.setUsuarioModificacion(usuario.getIdUsuario());
            } else {
                interv.setUsuarioCreacion(usuario.getIdUsuario());
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con su cuenta para poder realizar esta acci&oacute;n.");
            return response;
        }
        interv.setIdModulo(idModulo);
        boolean result = intervSanitariaService.guardarCambios(interv, idModulo);
        if (result) {
            response.setData(interv);
            response.addMensaje("Se han guardado los cambios correctamente");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al guardar los cambios");
        }
        return response;
    }

    @RequestMapping(value = "obtenerProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<IntervSanitariaProducto> obtenerProducto(@PathVariable long idModulo, @RequestBody IntervSanitariaProducto iSaniProducto, HttpServletRequest request) {
        AjaxResponse<IntervSanitariaProducto> response = new AjaxResponse<IntervSanitariaProducto>();
        intervSanitariaService.sincronizarIntervProducto(iSaniProducto);
        int stock = intervSanitariaService.obtenerStock(idModulo, iSaniProducto.getProducto());
        iSaniProducto.setStock(stock);
        response.setData(iSaniProducto);
        return response;
    }

    @RequestMapping(value = "{kitId}/obtenerProductosPorKit", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<IntervSanitariaProducto>> obtenerProductosPorKit(@PathVariable String kitId, @PathVariable long idModulo,
            @RequestBody List<GpProducto> userSelected, HttpServletRequest request) {
        AjaxResponse<List<IntervSanitariaProducto>> response = new AjaxResponse<List<IntervSanitariaProducto>>();
        try {
            long kId = Long.parseLong(kitId);
            IntSanKitProductoDTO intSanKitProductoDTO;
            try {
                intSanKitProductoDTO = intervSanitariaService.obtenerProductosPorKit(kId, userSelected, idModulo);
            } catch (BusinessException ex) {
                Logger.getLogger(IntervSanitariaController.class.getName()).log(Level.SEVERE, null, ex);
                response.setHasError(true);
                response.setMssg(ex.getMensajesError());
                return response;
            }
            List<IntervSanitariaProducto> intervProductos = intSanKitProductoDTO.getIntervSaniProductos();
            if (intervProductos.isEmpty()) {
                response.setHasError(true);
                response.addMensaje("No hay productos que agregar.");
            } else {
                if (intSanKitProductoDTO.isHasMessage()) {
                    response.setHasError(true);
                    response.addMensaje(intSanKitProductoDTO.getMessage());
                }
                response.setData(intervProductos);
            }
        } catch (NumberFormatException numberFormatException) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, numberFormatException);
            response.setHasError(true);
            response.addMensaje("Lo sentimos, ha ocurrido un error");
        }
        return response;
    }

    @RequestMapping(value = "{nroSalida}/reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response,
            @PathVariable long idModulo, @PathVariable String nroSalida) {

        IntervSanitaria interv = null;
        try {
            interv = intervSanitariaService.consultarPorNroSalida(nroSalida, idModulo);
        } catch (BusinessException ex) {
            Logger.getLogger(IntervSanitariaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("IntervSalidaPDF", "Data", interv);
    }

    @RequestMapping(value = "{nroSalida}/reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response,
            @PathVariable long idModulo, @PathVariable String nroSalida) {

        IntervSanitaria interv = null;
        try {
            interv = intervSanitariaService.consultarPorNroSalida(nroSalida, idModulo);
        } catch (BusinessException ex) {
            Logger.getLogger(IntervSanitariaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ModelAndView("IntervSalidaExcel", "Data", interv);
    }

    @RequestMapping(value = "anularIntervSanitaria", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<IntervSanitaria> anularIntervSanitaria(
            @PathVariable long idModulo, @RequestParam String nroSalida,
            HttpServletRequest request) {

        AjaxResponse<IntervSanitaria> response = new AjaxResponse<IntervSanitaria>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null) {
            try {
                IntervSanitaria intervSanitaria = intervSanitariaService.anularIntervencion(nroSalida, idModulo);
                response.setData(intervSanitaria);
                response.addMensaje(String.format("La salida %s se ha anulado correctamente", nroSalida));
                return response;
            } catch (BusinessException ex) {
                Logger.getLogger(IntervSanitariaController.class.getName()).log(Level.SEVERE, null, ex);
                response.setHasError(true);
                response.setMssg(ex.getMensajesError());
                return response;
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con su cuenta para poder realizar esta acci&oacute;n.");
            return response;
        }
    }

    @RequestMapping(value = "filtrarPorNroSalida", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<IntervSanitaria> filtrarPorNroSalida(String nroSalida, HttpServletRequest request) {
        AjaxResponse<IntervSanitaria> response = new AjaxResponse<IntervSanitaria>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null) {
            IntervSanitaria intervSanitaria = intervSanitariaService.obtenerPorId(nroSalida);
            if (intervSanitaria != null && (intervSanitaria.isAnulada() == null || !intervSanitaria.isAnulada())) {
                response.setData(intervSanitaria);
            } else if (intervSanitaria != null && intervSanitaria.isAnulada()) {
                response.setHasError(true);
                response.addMensaje(String.format("La salida con el n&uacute;mero %s est&aacute; ANULADA", nroSalida));
            } else {
                response.setHasError(true);
                response.addMensaje(String.format("No existe una salida con el n&uacute;mero %s", nroSalida));
            }

            return response;
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con su cuenta para poder realizar esta acci&oacute;n.");
            return response;
        }
    }

    @RequestMapping(value = "verStocks", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Integer>> obtenerStocks(@PathVariable long idModulo, @RequestBody List<IntervSanitariaProducto> intervSanitariaProductos, HttpServletRequest request) {
        AjaxResponse<List<Integer>> response = new AjaxResponse<List<Integer>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }

        List<Integer> stockList = new ArrayList<Integer>();
        //TODO Falta el Almacen
        for (IntervSanitariaProducto intervSanitariaProducto : intervSanitariaProductos) {
            int stock = intervSanitariaService.obtenerStock(idModulo, intervSanitariaProducto.getProducto());
            stockList.add(stock);
        }
        response.setData(stockList);
        return response;
    }
    
    private IntervSanitaria crearIntervSanitaria(HashMap<String, Object> map) {
        IntervSanitaria interv = new IntervSanitaria();
        if (map.containsKey("cliente")) {
            Cliente c = new Cliente();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("cliente");
            c.setApellidoMaterno(data.get("apellidoMaterno").toString());
            c.setApellidoPaterno(data.get("apellidoPaterno").toString());
            c.setNombre(data.get("nombre").toString());
            if (data.containsKey("idCliente")) {
                c.setIdCliente(Long.parseLong(data.get("idCliente").toString()));
            }
            if (data.containsKey("paciente")) {
                c.setCodPersonal(data.get("paciente").toString());
            }
            interv.setCliente(c);
        }
        if (map.containsKey("componente")) {
            Componente c = new Componente();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("componente");
            c.setId(Long.parseLong(limpiar(data.get("id").toString())));
            interv.setComponente(c);
        }
        if (map.containsKey("diagnostico")) {
            DiagnosticoCIE d = new DiagnosticoCIE();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("diagnostico");
            d.setCodigo(limpiar(data.get("codigo").toString()));
            interv.setDiagnostico(d);
        }
        if (map.containsKey("fechaRegistro")) {
            long data = Long.parseLong(map.get("fechaRegistro").toString());
            Date date = GregorianCalendar.getInstance().getTime();
            date.setTime(data);
            interv.setFechaRegistro(date);
        }
        if (map.containsKey("nroHistClinica")) {
            String nroHist = map.get("nroHistClinica").toString();
            interv.setNroHistClinica(nroHist);
        }
        if (map.containsKey("nroSalida") && map.get("nroSalida") != null) {
            String nroSalida = map.get("nroSalida").toString();
            interv.setNroSalida(nroSalida);
        }
        if (map.containsKey("referencia") && map.get("referencia") != null) {
            String referencia = map.get("referencia").toString();
            interv.setReferencia(referencia);
        }
        if (map.containsKey("prescriptor")) {
            Prescriptor p = new Prescriptor();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("prescriptor");
            p.setIdMedico(Long.parseLong(data.get("idMedico").toString()));
            interv.setPrescriptor(p);
        }
        if (map.containsKey("proceso")) {
            Proceso p = new Proceso();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("proceso");
            p.setId(Long.parseLong(data.get("id").toString()));
            interv.setProceso(p);
        }
        if (map.containsKey("subComponente")) {
            SubComponente p = new SubComponente();
            HashMap<String, Object> data = (HashMap<String, Object>) map.get("subComponente");
            p.setId(Long.parseLong(data.get("id").toString()));
            interv.setSubComponente(p);
        }
        if(map.containsKey("coordinador")) {
            interv.setCoordinador(map.get("coordinador").toString());
        }
        if (map.containsKey("intervSanitariaProductos")) {
            ArrayList<HashMap<String, Object>> intervProdsMap = (ArrayList<HashMap<String, Object>>) map.get("intervSanitariaProductos");
            ArrayList<IntervSanitariaProducto> intervProds = new ArrayList<IntervSanitariaProducto>();
            for (HashMap<String, Object> intervProdMap : intervProdsMap) {
                IntervSanitariaProducto intervProd = new IntervSanitariaProducto();
                if(intervProdMap.containsKey("cantidad")) {
                    intervProd.setCantidad(Double.parseDouble(intervProdMap.get("cantidad").toString()));
                }
                if(intervProdMap.containsKey("importeParcial")) {
                    intervProd.setImporteParcial(intervProdMap.get("importeParcial").toString());
                }
                if(intervProdMap.containsKey("precio")) {
                    intervProd.setPrecio(new BigDecimal(intervProdMap.get("precio").toString()));
                }
                if(intervProdMap.containsKey("producto")) {
                    HashMap<String, Object> data = (HashMap<String, Object>)intervProdMap.get("producto");
                    if(data.containsKey("idProducto")) {
                        GpProducto p = new GpProducto(Integer.parseInt(data.get("idProducto").toString()));
                        intervProd.setProducto(p);
                        intervProd.setIdProducto(p.getIdProducto());
                    }
                }
                intervProds.add(intervProd);
            }
            interv.setIntervSanitariaProductos(intervProds);
        }
        return interv;
    }

    private String limpiar(String data) {
        data = data.replaceAll("\\{|\\}|:|id|=", "");
        return data;
    }

}
