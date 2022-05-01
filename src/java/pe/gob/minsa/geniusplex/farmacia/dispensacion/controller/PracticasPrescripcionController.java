package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.BpPrescripcionDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.BpPrescService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

@Controller
@RequestMapping("/{idModulo}/practicasPrescripcion/*")
public class PracticasPrescripcionController {

    @Autowired
    BpPrescService bpPrescService;
    @Autowired
    VentaService ventaService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarPracticas(Model model, HttpServletRequest request) {
        model.addAttribute("viewTitle", "Mantenimiento de Buenas Prácticas de Prescripción");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Nro. Documento de venta", "Medicamentos e Insumos", "Prescriptor", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "codigo,producto.idProducto,nroVenta,medicamento,prescriptor.nombrePrescriptor");
        model.addAttribute("findItem", "getPractica");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        return "PracticasPrescripcion/listar";
    }
    @RequestMapping(value = "reporte", method = RequestMethod.GET)
    public String reportePracticas(Model model, HttpServletRequest request) {
        model.addAttribute("viewTitle", "Consulta de Buenas Prácticas de Prescripción");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Nro. Documento de venta", "Medicamentos e Insumos", "Prescriptor"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "codigo,producto.idProducto,nroVenta,medicamento,prescriptor.nombrePrescriptor");
        model.addAttribute("findItem", "getPractica");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        return "PracticasPrescripcion/consultar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<BpPrescripcionDTO> consultar(@PathVariable long idModulo, @RequestBody FilterData fData, HttpServletRequest request) {
        AjaxResponse<BpPrescripcionDTO> response = new AjaxResponse<BpPrescripcionDTO>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
        }
        if (fData.getParams().containsKey("ventafechaRegistro")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = (String) fData.getParams().get("ventafechaRegistro");
            try {
                fData.getParams().put("ventafechaRegistro", sdf.parse(date));
            } catch (ParseException ex) {
                Logger.getLogger(PracticasPrescripcionController.class.getName()).log(Level.SEVERE, null, ex);
                response.setHasError(true);
                response.addMensaje("La fecha de registro no es correcta, por favor verif&iacute;quela.");
                return response;
            }
        }
        BpPrescripcionDTO dto = bpPrescService.consultar(fData);
        request.getSession().setAttribute("bppData", dto);
        if (dto == null) {
            response.setHasError(true);
            response.addMensaje("No se encontraron detalles con esos par&aacute;metros de consulta");
        } else {
            response.setData(dto);
        }
        return response;
    }

    @RequestMapping(value = "getPractica", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<HashMap<String, Object>> getPractica(HttpServletRequest request) {
        AjaxResponse<HashMap<String, Object>> response = new AjaxResponse<HashMap<String, Object>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
        }
        String dataId = request.getParameter("id");
        String[] split = dataId.split("-");
        if (split.length == 2) {
            try {
                int idProd = Integer.parseInt(split[0]);
                long idVenta = Long.parseLong(split[1]);
                HashMap<String, Object> editData = bpPrescService.getEditData(idVenta, idProd);
                if (editData != null) {
                    editData.put("id", dataId);
                }
                response.setData(editData);
            } catch (NumberFormatException numberFormatException) {
                java.util.logging.Logger.getLogger(PracticasPrescripcionController.class.getName()).log(Level.SEVERE, null, numberFormatException);
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error inesperado");
                return response;
            }

        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error");
        }
        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse modificar(HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar al sistema con sus credenciales para realizar esta acci&oacute;n");
            return response;
        }
        String dataId = request.getParameter("id");
        String[] split = dataId.split("-");
        if (split.length == 2) {
            try {
                int idProd = Integer.parseInt(split[0]);
                long idVenta = Long.parseLong(split[1]);
                double cantidad = Double.parseDouble(request.getParameter("cantidad"));
                int productoId = Integer.parseInt(request.getParameter("producto"));
                boolean ok = bpPrescService.editarProducto(idVenta, idProd, cantidad, productoId);
                if (ok) {
                    response.addMensaje("Se han guardado los cambios correctamente");
                    Venta venta = ventaService.obtenerPorId(idVenta);
                    venta.setUsuarioModificacion(usuario.getIdUsuario());
                    ventaService.actualizar(venta);
                } else {
                    response.setHasError(true);
                    response.addMensaje("Ha ocurrido un error");
                    return response;
                }

            } catch (NumberFormatException numberFormatException) {
                java.util.logging.Logger.getLogger(PracticasPrescripcionController.class.getName()).log(Level.SEVERE, null, numberFormatException);
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error inesperado");
                return response;
            }

        }
        return response;
    }
    
    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminar(HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
        }
        String dataId = request.getParameter("id");
        String[] split = dataId.split("-");
        if (split.length == 2) {
            try {
                int idProd = Integer.parseInt(split[0]);
                long idVenta = Long.parseLong(split[1]);
                boolean ok = bpPrescService.eliminar(idVenta, idProd);
                if (ok) {
                    response.addMensaje("Se ha eliminado correctamente");
                } else {
                    response.setHasError(true);
                    response.addMensaje("Ha ocurrido un error");
                    return response;
                }

            } catch (NumberFormatException numberFormatException) {
                java.util.logging.Logger.getLogger(PracticasPrescripcionController.class.getName()).log(Level.SEVERE, null, numberFormatException);
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error inesperado");
                return response;
            }

        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response) {
        BpPrescripcionDTO dto = (BpPrescripcionDTO) request.getSession().getAttribute("bppData");
        ModelAndView model = new ModelAndView("PracticasPrescripcionPDF");

        List<String> contentLabels = Arrays.asList("Código", "Nro. Documento de Venta", "Medicamentos o Insumos", "Prescriptor");
        List<String> contentFields = Arrays.asList("producto:idProducto", "nroVenta", "medicamento", "prescriptor:nombrePrescriptor");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 25);
        contentColumns.put(1, 25);
        contentColumns.put(2, 25);
        contentColumns.put(3, 25);
        if (dto != null) {
            model.addObject("ContentData", dto.getDetalles());
        } else {
            model.addObject("ContentData", new ArrayList<Object>());
        }
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);

        HashMap<String, String> headerData = new HashMap<String, String>();
        String nombreCliente = dto != null ? dto.getVentas().get(0).getCliente().getNombreCliente(): "";
        headerData.put("Cliente", nombreCliente);
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);

        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        model.addObject("Title", "Buenas Prácticas de Prescripción");

        return model;
    }
    
    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response) {
        BpPrescripcionDTO dto = (BpPrescripcionDTO) request.getSession().getAttribute("bppData");
        ModelAndView model = new ModelAndView("GenericExcel");

        List<String> contentLabels = Arrays.asList("Código", "Nro. Documento de Venta", "Medicamentos o Insumos", "Prescriptor");
        List<String> contentFields = Arrays.asList("producto:idProducto", "nroVenta", "medicamento", "prescriptor:nombrePrescriptor");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 25);
        contentColumns.put(1, 25);
        contentColumns.put(2, 25);
        contentColumns.put(3, 25);
        if (dto != null) {
            model.addObject("ContentData", dto.getDetalles());
        } else {
            model.addObject("ContentData", new ArrayList<Object>());
        }
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);

        HashMap<String, String> headerData = new HashMap<String, String>();
        String nombreCliente = dto != null ? dto.getVentas().get(0).getCliente().getNombreCliente(): "";
        headerData.put("Cliente", nombreCliente);
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);

        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        model.addObject("Title", "Buenas Prácticas de Prescripción");

        return model;
    }

}
