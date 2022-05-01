/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DemandaInsatisfecha;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLote;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.ProductoLotePk;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ClienteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.DemandaInsatisfechaService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProductoLoteService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/demandaInsatisfecha/*")
public class DemandaInsatisfechaController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private DemandaInsatisfechaService demandaService;
    @Autowired
    private ProductoLoteService productoLoteService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ModelAndView listarDemandasInsatisfechas(ModelAndView model, HttpServletRequest request) {
        model.addObject("viewTitle", "Mantenimiento de Demandas Insatisfechas");
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Tipo", "F.F", "Lote", "Precio", "Fecha", "Fecha Vencimiento", "Saldo", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "");
        model.addObject("changeUrl", "");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "id,id,producto.producto.descripcion,producto.producto.idTipoProducto.nombreTipoProducto,producto.producto.idFormaFarmaceutica.nombreFormaFarmaceutica,lote,producto.precio,fecha:date,producto.fechaVencimiento:date,saldo");
        model.addObject("findItem", "");
        model.setViewName("DemandaInsatisfecha/listar");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model = new ModelAndView("redirect:/index", "usuario", usuario);
            return model;
        }
        return model;
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String consultarDemandasInsatisfechas(Model model) {
        model.addAttribute("viewTitle", "Consultar Demandas Insatisfechas");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Tipo", "F.F", "Lote", "Precio", "Fecha", "F.V.", "Saldo", "Acciones"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "id,id,producto.producto.descripcion,producto.producto.idTipoProducto.nombreTipoProducto,producto.producto.idFormaFarmaceutica.nombreFormaFarmaceutica,lote,producto.precio,fecha:date,producto.fechaVencimiento:date,saldo");
        model.addAttribute("findItem", "");
        return "DemandaInsatisfecha/consultar";
    }

    @RequestMapping(value = "getClientes", method = RequestMethod.GET)
    public @ResponseBody
    List<Cliente> getClientes(@PathVariable long idModulo) {
        List<Cliente> lista = clienteService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<DemandaInsatisfecha> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        Long idCliente = null;
        if (request.getParameter("idCliente") != null && request.getParameter("idCliente").length() > 0) {
            idCliente = Long.parseLong(request.getParameter("idCliente"));
        }
        String idAlString = request.getParameter("idAlmacen");
        int idAlmacen = -1;
        if (idAlString != null && idAlString.length() > 0) {
            idAlmacen = Integer.parseInt(idAlString);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            Logger.getLogger(DemandaInsatisfechaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<DemandaInsatisfecha> lista = demandaService.ConsultaDemandaInsatisfecha(idModulo, idCliente, start, end, idAlmacen);
        return lista;
    }

    @RequestMapping(value = "getDemandas", method = RequestMethod.GET)
    public @ResponseBody
    List<DemandaInsatisfecha> getDemandas(@PathVariable long idModulo, HttpServletRequest request) {
        long idCliente = Long.parseLong(request.getParameter("idCliente"));
        List<DemandaInsatisfecha> lista = demandaService.listarModuloPaciente(idModulo, idCliente);
        return lista;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<DemandaInsatisfecha>> insertarDemanda(@PathVariable long idModulo, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        AjaxResponse<List<DemandaInsatisfecha>> response = new AjaxResponse<List<DemandaInsatisfecha>>();
        long idAlmacen = Long.parseLong(request.getParameter("idAlmacen"));
        String idLote = request.getParameter("idLote");
        Long idProducto = Long.parseLong(request.getParameter("idProducto"));
        Long idCliente = Long.parseLong(request.getParameter("idCliente"));

        Cliente cliente = clienteService.obtenerPorId(idCliente);
        if (demandaService.existeProducto(idModulo, idCliente, idProducto)) {
            response.addMensaje("Ya existe este producto.");
            response.setHasError(true);
            return response;
        }
        ProductoLotePk productoLotePk = new ProductoLotePk(idProducto, idLote, idAlmacen);
        ProductoLote productoLote = productoLoteService.obtenerPorId(productoLotePk);
        DemandaInsatisfecha demandaInsatisfecha = new DemandaInsatisfecha();
        demandaInsatisfecha.setCliente(cliente);
        demandaInsatisfecha.setProducto(productoLote);
        demandaInsatisfecha.setIdModulo(idModulo);
        demandaInsatisfecha.setUsuarioCreacion(usuario.getIdUsuario());
        demandaInsatisfecha.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = demandaService.insertar(demandaInsatisfecha);

        List<DemandaInsatisfecha> data = new ArrayList<DemandaInsatisfecha>();
        data.add(demandaInsatisfecha);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el producto");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este producto.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarDemanda(@RequestParam long id) {
        AjaxResponse<DemandaInsatisfecha> response = new AjaxResponse<DemandaInsatisfecha>();
        boolean result = demandaService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el producto.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar este producto.");
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView getDemandasPDF(@PathVariable long idModulo, HttpServletRequest request) {
        long idCliente = Long.parseLong(request.getParameter("idCliente"));
        List<DemandaInsatisfecha> lista = demandaService.listarModuloPaciente(idModulo, idCliente);
        ModelAndView model = new ModelAndView("DemandaInsatisfechaPDF");
        model.addObject("Data", lista);
        model.addObject("Cliente", clienteService.obtenerPorId(idCliente));
        return model;

    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView getDemandasExcel(@PathVariable long idModulo, HttpServletRequest request) {
        long idCliente = Long.parseLong(request.getParameter("idCliente"));
        List<DemandaInsatisfecha> lista = demandaService.listarModuloPaciente(idModulo, idCliente);
        ModelAndView model = new ModelAndView("DemandaInsatisfechaExcel");
        model.addObject("Data", lista);
        model.addObject("Cliente", clienteService.obtenerPorId(idCliente));
        return model;

    }

    @RequestMapping(value = "getProductosAlmacen", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductoLote> getProducots(@PathVariable long idModulo, HttpServletRequest request) {
        if (request.getParameter("almacen") == null) {
            return new ArrayList<ProductoLote>();
        }
        Integer idAlmacen = Integer.parseInt(request.getParameter("almacen"));
        List<ProductoLote> lista = productoLoteService.encontrarPorAlmacen(idAlmacen);
        return lista;
    }

    @RequestMapping(value = "insertarCliente", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Cliente>> insertarCliente(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Cliente>> response = new AjaxResponse<List<Cliente>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);

        Cliente cliente = new Cliente();
        String nombre = request.getParameter("nombre");
        String paterno = request.getParameter("paterno");
        String materno = request.getParameter("materno");
        String paciente = request.getParameter("paciente");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        cliente.setNombre(nombre);
        cliente.setApellidoPaterno(paterno);
        cliente.setApellidoMaterno(materno);
        cliente.setCodPersonal(paciente);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setUsuarioCreacion(usuario.getIdUsuario());
        cliente.setUsuarioModificacion(usuario.getIdUsuario());
        List<Cliente> clientes = clienteService.listarModulo(idModulo);

        cliente.setIdModulo(idModulo);
        if (clientes.contains(cliente)) {
            response.addMensaje("Este cliente ya existe.");
            response.setHasError(true);
            return response;
        }
        boolean result = clienteService.insertar(cliente);
        ArrayList<Cliente> data = new ArrayList<Cliente>();
        data.add(cliente);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el cliente");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este cliente.");
            response.setHasError(true);
        }

        return response;
    }
}
