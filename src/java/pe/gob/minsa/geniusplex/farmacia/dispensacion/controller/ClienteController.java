package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.Arrays;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Cliente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ClienteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpTipoDocumento;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpTipoDocumentoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/cliente/*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private GpTipoDocumentoService gpTipoDocumento;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarClientes(Model model) {
        model.addAttribute("viewTitle", "Mantenimiento de Clientes");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Nombre o Razón Social","Apellido Paterno","Apellido Materno", "Código", "DNI","No Afilicación","Dirección","Tipo Documento","No Documento","Teléfono", "Acciones"}));
        model.addAttribute("ajaxList", "getClientes");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "idCliente,nombre,apellidoPaterno,apellidoMaterno,codigo,dni,noAfiliacion,direccion,tipoDocumento.nombreTipoDocumento,noDocumento,telefono");
        model.addAttribute("findItem", "getCliente");
        return "Cliente/listar";
    }

    @RequestMapping(value = "getClientes", method = RequestMethod.GET)
    public @ResponseBody
    List<Cliente> getClientes(@PathVariable long idModulo) {
        List<Cliente> lista = clienteService.listarModulo(idModulo);
        return lista;
    }

    @RequestMapping(value = "getCliente", method = RequestMethod.GET)
    public @ResponseBody
    Cliente getCliente(@RequestParam long id) {
        return clienteService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Cliente>> insertarCliente(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Cliente>> response = new AjaxResponse<List<Cliente>>();

        Cliente cliente = this.getData(request);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        cliente.setUsuarioCreacion(usuario.getIdUsuario());
        cliente.setUsuarioModificacion(usuario.getIdUsuario());
        cliente.setIdModulo(idModulo);

        boolean result = clienteService.insertar(cliente);
        List<Cliente> data = clienteService.listarModulo(idModulo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Cliente.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Cliente.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarCliente(@RequestParam long id) {
        AjaxResponse<Cliente> response = new AjaxResponse<Cliente>();
        try {
            boolean result = clienteService.eliminar(id);
            if (result) {
                response.addMensaje("Se ha borrado exitosamente el cliente.");
            } else {
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error al borrar a este cliente");
            }
        } catch (Exception e) {
            response.setHasError(true);
                response.addMensaje("No se puede eliminar este cliente. Verifique que no est&eacute; relacionado con alg&uacute;n otro elemento como una venta o pre venta.");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Cliente> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Cliente> response = new AjaxResponse<Cliente>();
        boolean result = clienteService.cambiarEstado(id);
        Cliente cliente = clienteService.obtenerPorId(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        cliente.setUsuarioModificacion(usuario.getIdUsuario());
        clienteService.actualizar(cliente);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del cliente");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este cliente");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Cliente> modificarCliente(HttpServletRequest request) {
        AjaxResponse<Cliente> response = new AjaxResponse<Cliente>();
        Cliente cliente = this.getData(request);
         Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        cliente.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = clienteService.actualizar(cliente);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Cliente.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Cliente.");
            response.setHasError(true);
        }
        return response;
    }
    
    @RequestMapping(value = "obtenerHC", method = RequestMethod.POST)
    public @ResponseBody String obtenerHistoriaClinica(@PathVariable long idModulo, @RequestParam long idCliente, HttpServletRequest request) {
        return clienteService.obtenerHistoriaClinica(idModulo, idCliente);
    }
    

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Cliente> lista = clienteService.listarModulo(idModulo);
        return new ModelAndView("ClientePDF", "Data", lista);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Cliente> lista = clienteService.listarModulo(idModulo);
        return new ModelAndView("ClienteExcel", "Data", lista);
    }

    private Cliente getData(HttpServletRequest request) {
        String dni = request.getParameter("dni");
        String codPersonal = request.getParameter("codPersonal");
        String noAfiliacion = request.getParameter("noAfiliacion");
        String nombre = request.getParameter("nombre");
        String apellidoPaterno = request.getParameter("apellidoPaterno");
        String apellidoMaterno = request.getParameter("apellidoMaterno");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        int noDoc = Integer.parseInt(request.getParameter("noDocumento"));
        long tdoc = Long.parseLong(request.getParameter("TipoDocumentoSelect"));
        GpTipoDocumento tipoDocumento = gpTipoDocumento.obtenerPorId((int) tdoc);
        long idCliente =Long.parseLong(request.getParameter("idCliente"));
        Cliente cliente;
        
        if(idCliente == 0){
         cliente = new Cliente(codPersonal, noAfiliacion, nombre, apellidoPaterno, apellidoMaterno, direccion, tipoDocumento, noDoc, telefono, dni);
        }
        else{
            cliente = clienteService.obtenerPorId(idCliente);
            cliente.setCodPersonal(codPersonal);
            cliente.setNoAfiliacion(noAfiliacion);
            cliente.setNombre(nombre);
            cliente.setApellidoPaterno(apellidoPaterno);
            cliente.setApellidoMaterno(apellidoMaterno);
            cliente.setDireccion(direccion);
            cliente.setTipoDocumento(tipoDocumento);
            cliente.setNoDocumento(noDoc);
            cliente.setTelefono(telefono);
            cliente.setDni(dni);
        }
        return cliente;
    }
}
