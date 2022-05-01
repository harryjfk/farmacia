/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PerfilService;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.services.impl.UsuarioService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VendedorService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpPersonalService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

@Controller
@RequestMapping("/{idModulo}/vendedor/*")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private GpPersonalService gpPersonalService;
    @Autowired
    private PerfilService perfilService;

    /**
     * Genera todas las variables necesarias para mostrar la vista de
     * mantenimiento de Vendedores
     * <b>viewTitle</b>: titulo de la vista
     * <b>tableHeaders</b>: lista con los nombres de las columnas de la tabla
     * <b>ajaxList</b>: URL para obtener listado via Ajax
     * <b>editUrl</b>: URL para editar elemento via Ajax
     * <b>changeUrl</b>: URL para cambiar el estado del elemento elemento via
     * Ajax
     * <b>removeUrl</b>: URL para eliminar elemento elemento via Ajax
     * <b>tableProperties</b>: String de las propiedades del elemento que van a
     * ser mostradas en la tabla separadas por coma. <b>Nota</b>: la primera
     * propiedad debe ser el identificador del elemento (el cual no sera
     * mostrado, es solo para acciones del CRUD)
     * <b>findItem</b>: URL para encontrar un elemento via Ajax
     *
     * @param model
     * @return vista del listado de Vendedores
     */
    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarVendedores(Model model) {
        model.addAttribute("viewTitle", "Mantenimiento de Vendedores");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Apellidos y nombres", "Acciones"}));
        model.addAttribute("ajaxList", "getVendedores");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "idVendedor,idVendedor,nombreVendedor");
        model.addAttribute("findItem", "getVendedor");
        return "Vendedor/listar";
    }

    @RequestMapping(value = "getVendedores", method = RequestMethod.GET)
    public @ResponseBody
    List<Vendedor> getVendedores(@PathVariable long idModulo, @RequestParam(required = false) Long turno) {
        if (turno == null) {
            return vendedorService.listarModulo(idModulo);
        } else {
            return vendedorService.listarModulo(idModulo, turno);
        }
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Vendedor>> insertarMedico(@PathVariable long idModulo,
            @ModelAttribute Vendedor vendedor, HttpServletRequest request) {

        AjaxResponse<List<Vendedor>> response = new AjaxResponse<List<Vendedor>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }

        GpPersonal personal = null;
        personal = gpPersonalService.obtenerPorId(vendedor.getPersonal());
        Vendedor tmpVendedor = vendedorService.obtenerPorPersonalYModulo(vendedor.getPersonal(), idModulo);
        if (tmpVendedor != null) {
            response.setHasError(true);
            response.addMensaje("El personal seleccionado ya ha sido agregado a la lista de vendedores de este sub m&oacute;dulo. Por favor seleccione otro personal.");
            return response;
        }

        vendedor.setUsuarioCreacion(usuario.getIdUsuario());
        vendedor.setUsuarioModificacion(usuario.getIdUsuario());
        vendedor.setIdModulo(idModulo);

        boolean result = vendedorService.insertar(vendedor);
        List<Vendedor> data = vendedorService.listarModulo(idModulo);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Vendedor.");
            response.setData(data);
            if (personal != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                crearUsuario(personal, response, username, password, usuario);
            }

        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Vendedor.");
            response.setHasError(!result);
        }

        return response;
    }

    private void crearUsuario(GpPersonal personal, AjaxResponse<List<Vendedor>> response, String username, String password, Usuario usuarioCreacion) {
        try {
            vendedorService.crearUsuario(personal, username, password, usuarioCreacion);
            response.addMensaje("Se ha creado un usuario para este vendedor. Para establecerle un correo v&aacute;lido vaya al Mantenimiento de Usuarios en la Administraci&oacute;n.");
        } catch (BusinessException ex) {
            Logger.getLogger(VendedorController.class.getName()).log(Level.SEVERE, null, ex);
            for (String mssg : ex.getMensajesError()) {
                java.util.logging.Logger.getLogger(VendedorController.class.getName()).log(Level.INFO, mssg);
                response.addMensaje(mssg);
                response.setHasError(true);
            }
        }
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarVendedor(@RequestParam long id) {
        AjaxResponse<Vendedor> response = new AjaxResponse<Vendedor>();
        boolean result = vendedorService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el Vendedor.");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al borrar a este Vendedor");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Vendedor> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Vendedor> response = new AjaxResponse<Vendedor>();
        boolean result = vendedorService.cambiarEstado(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        Vendedor vendedor = vendedorService.obtenerPorId(id);
        vendedor.setUsuarioModificacion(usuario.getIdUsuario());
        vendedorService.actualizar(vendedor);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del Vendedor");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Vendedor");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Vendedor> modificarVendedor(@ModelAttribute Vendedor vendedor, HttpServletRequest request) {
        AjaxResponse<Vendedor> response = new AjaxResponse<Vendedor>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        vendedor.setUsuarioModificacion(usuario.getIdUsuario());
        String userNameNew = request.getParameter("username");
        String passwordNew = request.getParameter("password");
        boolean result = vendedorService.actualizar(vendedor);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Vendedor.");
            result = vendedorService.actualizarUsuario(vendedor, userNameNew, passwordNew, usuario);
            if(!result) {
                response.setHasError(true);
                response.setMssg(Arrays.asList("Se ha modificado exitosamente el Vendedor, pero no se ha podido actualizar sus credenciales."));
            }
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Vendedor.");
            response.setHasError(!result);
        }
        return response;
    }

    @RequestMapping(value = "getVendedor", method = RequestMethod.GET)
    public @ResponseBody
    Vendedor getVendedor(@PathVariable long idModulo, @RequestParam long id) {
        Vendedor vendedor = vendedorService.obtenerPorId(id);
        vendedorService.setCredentials(vendedor);
        return vendedor;
    }

    /**
     *
     * @return lista de personal
     */
    @RequestMapping(value = "listarPersonal", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<HashMap<String, String>> listarPersonal() {
        HashMap<String, String> names = new java.util.HashMap<String, String>();
        List<Personal> personalSinUsuario = personalService.listarSinUsuario();
        for (Personal p : personalSinUsuario) {
            names.put(p.getIdPersonal(), String.format("%s/%s/%s/%s", p.getIdPersonal(), p.getApellidoPaterno(), p.getApellidoMaterno(), p.getNombre()));
        }
//        for (int i = 0; i < 10; i++) {
//            Personal p = new Personal();
//            p.setApellidoMaterno("Apellido Materno de " + i);
//            p.setApellidoPaterno("Apellido Paterno de " + i);
//            p.setCargo("Cargo de " + i);
//            p.setIdPersonal("000" + i);
//            p.setNombre("Nombre de " + i);
//            names.put(p.getIdPersonal(), String.format("%s/%s/%s/%s", p.getIdPersonal(), p.getApellidoPaterno(), p.getApellidoMaterno(), p.getNombre()));
//        }
        AjaxResponse<HashMap<String, String>> response = new AjaxResponse<HashMap<String, String>>(names);
        return response;
    }

    @RequestMapping(value = "prepararReporte", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse prepararReporte(@PathVariable long idModulo, HttpServletRequest request,
            @ModelAttribute FilterData fData) {

        if (fData != null && fData.getParams() != null) {
            fData.getParams().put("IdModulo", idModulo);
        }
        HttpSession session = request.getSession();
        session.setAttribute("fData", fData);
        AjaxResponse response = new AjaxResponse();
        response.setHasError(false);
        return response;
    }

    //TODO tomar los valores de los filtros del datatable (hay un problema: el nombre del vendedor viene concatenado con sus apellidos ¿que hacer?)
    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        List<Vendedor> list = vendedorService.listarModulo(idModulo);
        return new ModelAndView("VendedorPDF", "Data", list);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        List<Vendedor> list = vendedorService.listarModulo(idModulo);
        return new ModelAndView("VendedorExcel", "Data", list);
    }

}
