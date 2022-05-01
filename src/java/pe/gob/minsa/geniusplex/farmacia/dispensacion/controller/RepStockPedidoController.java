package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.persistence.internal.helper.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.farmacia.domain.Inventario;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.dtos.RepStockDTO;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.RepStockService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/repstockpedido/*")
public class RepStockPedidoController {

    @Autowired
    RepStockService repStockService;
    
    @Autowired
    GpAlmacenService almacenService;
    
    @Autowired
    PeriodoService periodoService;

    @RequestMapping(value = "procesar", method = RequestMethod.GET)
    public String procesarCuentaCorriente(Model model) {
        model.addAttribute("viewTitle", "Reposici&oacute;n de Stocks o Pedidos");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Cod.", "Descripci&oacute;n", "F.F", "Cant.", "Precio", "Total", "Conteo F&iacute;sico", "Faltante (cant.)", "Sobrante (cant.)", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "idProducto,idProducto,descripcion,formaFarmaceutica,cantidad,precio,total,conteo,cantidadFaltante,totalFaltante,cantidadSobrante,totalSobrante,totalFisico,cantidadAlterado");
        model.addAttribute("findItem", "");
        return "RepStockPedido/procesar";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<RepStockDTO> consultar(HttpServletRequest request) {
        AjaxResponse<RepStockDTO> response = new AjaxResponse<RepStockDTO>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
            return response;
        }

        int almacen = Integer.parseInt(request.getParameter("almacen"));
        String fechCierre = request.getParameter("fechaCierre");
        int idPeriodo = Integer.parseInt(request.getParameter("periodo"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar;
        try {
            Date fecha = sdf.parse(fechCierre);
            calendar = GregorianCalendar.getInstance();
            calendar.setTime(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(RepStockPedidoController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.addMensaje("La fecha de cierre no es v&aacute;lida");
            return response;
        }
        Timestamp timestamp = Helper.timestampFromDate(calendar.getTime());
        RepStockDTO dto;
        Inventario inventario = repStockService.obtenerInventario(idPeriodo, almacen, timestamp);
        dto = repStockService.consultar(inventario);
        response.setData(dto);
        response.addMensaje(String.valueOf(inventario.getIdInventario()));
        return response;
    }

    @RequestMapping(value = "getAlmacenes", method = RequestMethod.GET)
    public @ResponseBody
    List<GpAlmacen> getAlmacenes(@PathVariable long idModulo) {
        List<GpAlmacen> lista = almacenService.listarPorModulo(idModulo);
        return lista;
    }
    
    @RequestMapping(value = "getPeriodos", method = RequestMethod.GET)
    public @ResponseBody
    List<Periodo> getPeriodos() {
        Calendar calendar = GregorianCalendar.getInstance();
        List<Periodo> lista = periodoService.listarPorAnio(calendar.get(Calendar.YEAR));
        return lista;
    }

}
