package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.ArrayList;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PuntoDeVenta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.enums.VentaEstadoEnum;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PVentaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

@Controller
@RequestMapping("/{idModulo}/ptoventa/*")
public class PVentaController {

    @Autowired
    private PVentaService ptoVentaService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPuntoDeVentas(Model model) {
        model.addAttribute("viewTitle", "Mantenimiento de Puntos de Ventas");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Nombre de PC", "Serie Boleta", "Acciones"}));
        model.addAttribute("ajaxList", "getPVentas");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "id,id,nombrePc,serieBoleta");
        model.addAttribute("findItem", "getPVenta");
        return "PuntoDeVenta/listar";
    }

    @RequestMapping(value = "getPVentas", method = RequestMethod.GET)
    public @ResponseBody
    List<PuntoDeVenta> getPVentas(@PathVariable long idModulo) {
        List<PuntoDeVenta> lista = ptoVentaService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getPVenta", method = RequestMethod.GET)
    public @ResponseBody
    PuntoDeVenta getPVenta(@RequestParam long id) {
        return ptoVentaService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<PuntoDeVenta>> insertarPuntoDeVenta(@PathVariable long idModulo,
            @RequestParam String nombrePC, @RequestParam String serieBoleta, HttpServletRequest request) {

        AjaxResponse<List<PuntoDeVenta>> response = new AjaxResponse<List<PuntoDeVenta>>();

        PuntoDeVenta ptoVenta = new PuntoDeVenta();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null & usuario.getIdUsuario() > 0) {
            ptoVenta.setUsuarioCreacion(usuario.getIdUsuario());
            ptoVenta.setUsuarioModificacion(usuario.getIdUsuario());
            ptoVenta.setIdModulo(idModulo);
            ptoVenta.setNombrePc(nombrePC);
            ptoVenta.setSerieBoleta(serieBoleta);

            boolean result = ptoVentaService.insertar(ptoVenta);
            List<PuntoDeVenta> data = ptoVentaService.listarModulo(idModulo);
            if (result) {
                response.addMensaje("Se ha insertado exitosamente el PuntoDeVenta.");
                response.setData(data);
            } else {
                response.addMensaje("Ha ocurrido un error al insertar este PuntoDeVenta.");
                response.setHasError(true);
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarPuntoDeVenta(@RequestParam long id) {
        AjaxResponse<PuntoDeVenta> response = new AjaxResponse<PuntoDeVenta>();
        boolean result = ptoVentaService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el ptoVenta.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este ptoVenta");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<PuntoDeVenta> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<PuntoDeVenta> response = new AjaxResponse<PuntoDeVenta>();
        boolean result = ptoVentaService.cambiarEstado(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        PuntoDeVenta pto = ptoVentaService.obtenerPorId(id);
        pto.setUsuarioModificacion(usuario.getIdUsuario());
        ptoVentaService.actualizar(pto);
        
        if (result) {
            response.addMensaje("Se ha cambiado el estado del ptoVenta");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este ptoVenta");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<PuntoDeVenta> modificarPuntoDeVenta(@PathVariable long idModulo, @RequestParam long id, @RequestParam String nombrePC,
            @RequestParam String serieBoleta, HttpServletRequest request) {

        AjaxResponse<PuntoDeVenta> response = new AjaxResponse<PuntoDeVenta>();
        PuntoDeVenta ptoVenta = ptoVentaService.obtenerPorId(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            ptoVenta.setUsuarioModificacion(usuario.getIdUsuario());
            ptoVenta.setSerieBoleta(serieBoleta);
            ptoVenta.setNombrePc(nombrePC);
            ptoVenta.setId(id);
            ptoVenta.setIdModulo(idModulo);
            boolean result = ptoVentaService.actualizar(ptoVenta);

            if (result) {
                response.addMensaje("Se ha modificado exitosamente el PuntoDeVenta.");
            } else {
                response.addMensaje("Ha ocurrido un error al modificar este PuntoDeVenta.");
                response.setHasError(true);
            }
        } else {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acioacute;n");
        }
        return response;
    }
    
    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PuntoDeVentaPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "PuntoDeVentaExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        List<PuntoDeVenta> data = ptoVentaService.listarModulo(idModulo);

        List<String> contentLabels = Arrays.asList("Código", "Nombre de PC", "Serie Boleta");
        List<String> contentFields = Arrays.asList("id","nombrePc","serieBoleta");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 10);
        contentColumns.put(1, 54);
        contentColumns.put(2, 33);

        ModelAndView model = new ModelAndView(viewName);
        model.addObject("Title", "Puntos de Venta");
        model.addObject("ContentData", data);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
}
