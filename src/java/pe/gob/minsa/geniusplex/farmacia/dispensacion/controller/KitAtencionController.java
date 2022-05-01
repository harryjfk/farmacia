/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Componente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencionProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ComponenteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.KitService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProcesoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.SubComponenteService;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpProductoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;
import pe.gob.minsa.geniusplex.web.objects.FilterData;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/kitatencion/*")
public class KitAtencionController {

    @Autowired
    private KitService kitservice;
    @Autowired
    private ComponenteService compService;
    @Autowired
    private SubComponenteService subCompService;
    @Autowired
    private ProcesoService procService;
    @Autowired
    private GpProductoService gpProductoService;
    @Autowired
    private ProcesoService procesoService;

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
     * @param idModulo
     * @param model
     * @param request
     * @return vista del listado de Vendedores
     */
    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ModelAndView listarKitsAtencion(@PathVariable long idModulo, ModelAndView model, HttpServletRequest request) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("viewTitle", "Mantenimiento de Kits de Atenci&oacute;n");
            model.addObject("procesos", procesoService.listar());
            model.addObject("componentes", compService.listar());
            model.setViewName("KitAtencion/listar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "getKitsAtencion", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables getKitsAtencion(@PathVariable long idModulo, HttpServletRequest request) {
        DataTablesParam params = DataTablesParamUtility.getParam(request);
        ManagerDatatables manager = new ManagerDatatables();

        int iDisplayStart, iDisplayLength;
        if (params != null) {
            iDisplayStart = params.iDisplayStart;
            iDisplayLength = params.iDisplayLength + 1;
        } else {
            iDisplayStart = 0;
            iDisplayLength = 1;
            params = new DataTablesParam();
            params.sEcho = "2";
        }

        Object[] sort = new Object[]{
            params.iSortingCols == 0 ? null : 1,
            params.iSortColumnIndex,
            params.sSortDirection
        };
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        String search = request.getParameter("sSearch");
        long compId = 0;
        long subId = 0;
        long procId = 0;
        try {
            compId = Long.parseLong(request.getParameter("componente"));
            subId = Long.parseLong(request.getParameter("subComponente"));
            procId = Long.parseLong(request.getParameter("proceso"));
        } catch (NumberFormatException numberFormatException) {
            java.util.logging.Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, numberFormatException);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String perString = request.getParameter("periodo");
        Date periodo = null;
        if (perString != null) {
            try {
                periodo = sdf.parse(perString);
            } catch (ParseException ex) {
                Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<KitAtencion> lista = kitservice.listarModuloRango(idModulo, compId, subId, procId, periodo, search, range, sort);

        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = lista.size();
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);

        return manager;
    }

    @RequestMapping(value = "listarJSON", method = RequestMethod.GET)
    public @ResponseBody
    List<KitAtencion> listar(@PathVariable long idModulo, @ModelAttribute FilterData fData) {
        List<KitAtencion> kits = new ArrayList<KitAtencion>();
        if (fData == null) {
            kits = kitservice.listar();
        }
        if (fData != null && fData.getParams() != null) {
            fData.getParams().put("IdModulo", idModulo);
        }
        kits = kitservice.listarProceso(fData);
        return kits;
    }

    @RequestMapping(value = "listarPorProcesoPeriodo", method = RequestMethod.GET)
    public @ResponseBody
    AjaxResponse<List<KitAtencion>> listarPorProcesoPeriodo(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<KitAtencion>> response = new AjaxResponse<List<KitAtencion>>();
        long proceso;
        int periodo;
        try {
            proceso = Long.parseLong(request.getParameter("proceso"));
            periodo = Integer.parseInt(request.getParameter("periodo"));
        } catch (NumberFormatException ex) {
            Logger.getLogger(KitService.class.getName() + " [insertar]").log(Level.SEVERE, null, ex);
            response.addMensaje("Ha ocurrido un error inesperado.");
            response.setHasError(true);
            return response;
        }

        Date per;
        DateFormat df = new SimpleDateFormat("yyyy");
        try {
            per = df.parse(String.valueOf(periodo));
        } catch (ParseException ex) {
            Logger.getLogger(KitService.class.getName()).log(Level.SEVERE, null, ex);
            response.addMensaje("Verifique que el per&iacute;odo sea un año v&aacute;lido de 4 digitos");
            response.setHasError(true);
            return response;
        }

        List<KitAtencion> kitAtencions = kitservice.encontrarPorProcesoPeriodo(proceso, idModulo, per);
        response.setData(kitAtencions);
        return response;
    }

    @RequestMapping(value = "getKitAtencion", method = RequestMethod.GET)
    public @ResponseBody
    KitAtencion getKitAtencion(@PathVariable long idModulo, @RequestParam long id) {
        return kitservice.obtenerPorId(id);
    }

    @RequestMapping(value = "editar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse editar(@PathVariable long idModulo, @RequestParam long id, @RequestParam String desc, long componente, long subComponente, long proceso, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        KitAtencion kit = kitservice.obtenerPorId(id);
        kit.setDescripcion(desc);

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        kit.setUsuarioModificacion(usuario.getIdUsuario());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String perFecha = request.getParameter("periodo");
        try {
            Date periodo = sdf.parse(perFecha);
            kit.setPeriodo(periodo);
        } catch (ParseException ex) {
            Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.addMensaje("Per&iacute;odo Inv&aacute;lido");
            return response;
        }
        boolean ok = kitservice.actualizar(kit, componente, subComponente, proceso);
        if (!ok) {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error no se han guardado los cambios");
        } else {
            response.addMensaje("Se ha actualizado correctamente el Kit de Atenci&oacute;n");
        }
        return response;
    }

    /**
     * Inserta un kit de atencion.
     *
     * @param idModulo id del modulo actual
     * @param request Peticion con los datos del kit
     * @return
     */
    @RequestMapping(value = "insertarKitAtencionProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<KitAtencionProducto>> insertarKitAtencionProducto(@PathVariable long idModulo, HttpServletRequest request) {

        AjaxResponse<List<KitAtencionProducto>> response = new AjaxResponse<List<KitAtencionProducto>>();

        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.addMensaje("Debe ingresar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            response.setHasError(true);
            return response;
        }

        long componente;
        long subcomponente;
        long proceso;
        int producto;
        double cant;
        int periodo;
        String desc = String.valueOf(request.getParameter("descripcion"));
        try {
            componente = Long.parseLong(request.getParameter("componente"));
            subcomponente = Long.parseLong(request.getParameter("subcomponente"));
            proceso = Long.parseLong(request.getParameter("proceso"));
            producto = Integer.parseInt(request.getParameter("producto"));
            periodo = Integer.parseInt(request.getParameter("periodo"));
            cant = Double.parseDouble(request.getParameter("cantidad"));
        } catch (NumberFormatException ex) {
            Logger.getLogger(KitService.class.getName() + " [insertar]").log(Level.SEVERE, null, ex);
            response.addMensaje("Ha ocurrido un error inesperado, por favor verifique que la cantidad es un n&uacute;mero o recrgue la p&aacute;gina.");
            response.setHasError(true);
            return response;
        }

        Componente comp = compService.obtenerPorId(componente);
        SubComponente subComp = subCompService.obtenerPorId(subcomponente);
        Proceso proc = procService.obtenerPorId(proceso);
        GpProducto prod = gpProductoService.obtenerPorId(producto);
        Date per;

        DateFormat df = new SimpleDateFormat("yyyy");
        try {
            per = df.parse(String.valueOf(periodo));
        } catch (ParseException ex) {
            Logger.getLogger(KitService.class.getName() + " [insertar]").log(Level.SEVERE, null, ex);
            response.addMensaje("Verifique que el per&iacute;odo sea un año v&aacute;lido de 4 digitos");
            response.setHasError(true);
            return response;
        }

        KitAtencion ka;
        try {
            ka = kitservice.encontrarPorCompSubPer(comp, subComp, proc, per, idModulo);
        } catch (BusinessException ex) {
            Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, ex);
            for (String mssg : ex.getMensajesError()) {
                response.addMensaje(mssg);
            }
            response.setHasError(true);
            return response;
        }
        if (ka == null) {
            ka = kitservice.crearKit(comp, subComp, proc, per, idModulo);
            ka.setDescripcion(desc);
            ka.setUsuarioCreacion(usuario.getIdUsuario());
            ka.setUsuarioModificacion(usuario.getIdUsuario());
        } else {
            ka.setUsuarioModificacion(usuario.getIdUsuario());
            ka.setDescripcion(desc);
        }

        boolean result = kitservice.crearProducto(ka, prod, cant);

        if (result) {
            response.addMensaje("Se ha agregado exitosamente el medicamento.");
            response.setHasError(false);
        } else {
            response.addMensaje("Ha ocurrido un error al agregar este medicamento.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse insertar(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
        } else {
            String desc = request.getParameter("desc");
            if (desc == null || desc.length() == 0) {
                response.setHasError(true);
                response.addMensaje("El campo Nombre es requerido");
            } else {
                long compId = 0;
                long subId = 0;
                long procId = 0;
                try {
                    compId = Long.parseLong(request.getParameter("componente"));
                    subId = Long.parseLong(request.getParameter("subComponente"));
                    procId = Long.parseLong(request.getParameter("proceso"));
                } catch (NumberFormatException numberFormatException) {
                    java.util.logging.Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, numberFormatException);
                    response.setHasError(true);
                    response.addMensaje("Ha ocurrido un error, los datos suministrados no son correctos. Aseg&uacute;rese que haya seleccionado un componente, sub componente y proceso");
                    return response;
                }
                if (compId == 0 || subId == 0 || procId == 0) {
                    response.setHasError(true);
                    response.addMensaje("Ha ocurrido un error, los datos suministrados no son correctos. Aseg&uacute;rese que haya seleccionado un componente, sub componente y proceso");
                    return response;
                }

                KitAtencion kit = new KitAtencion();
                kit.setDescripcion(desc);
                kit.setIdModulo(idModulo);
                kit.setUsuarioCreacion(usuario.getIdUsuario());
                kit.setUsuarioModificacion(usuario.getIdUsuario());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String perFecha = request.getParameter("periodo");
                try {
                    Date periodo = sdf.parse(perFecha);
                    kit.setPeriodo(periodo);
                } catch (ParseException ex) {
                    Logger.getLogger(KitAtencionController.class.getName()).log(Level.SEVERE, null, ex);
                    response.setHasError(true);
                    response.addMensaje("Per&iacute;odo Inv&aacute;lido");
                    return response;
                }

                boolean ok = kitservice.insertar(kit, compId, subId, procId);
                if (!ok) {
                    response.setHasError(true);
                    response.addMensaje("Ha ocurrido un error, no se ha registrado el nuevo Kit de Atenci&oacute;n");
                } else {
                    response.addMensaje("Se ha insertado correctamente el Kit de Atenci&oacute;n");
                }
            }
        }
        return response;
    }

    @RequestMapping(value = "getKitAtencionProducto", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    AjaxResponse<HashMap<String, Object>> getKitAtencionProducto(@RequestParam String id) {
        HashMap<String, Object> kaProducto = new HashMap<String, Object>();
        AjaxResponse<HashMap<String, Object>> response = new AjaxResponse<HashMap<String, Object>>();
        KitAtencionProducto producto;

        producto = kitservice.obtenerProductoPorId(id);
        kaProducto.put("cantidad", producto.getCantidad());
        kaProducto.put("producto", producto.getProducto().getIdProducto());
        String pk = producto.getKitAtencion().getId() + "-" + producto.getProducto().getIdProducto();
        kaProducto.put("id", pk);
        response.setData(kaProducto);

        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<KitAtencion> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<KitAtencion> response = new AjaxResponse<KitAtencion>();
        boolean result = kitservice.cambiarEstado(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        KitAtencion kit = kitservice.obtenerPorId(id);
        kit.setUsuarioModificacion(usuario.getIdUsuario());
        kitservice.actualizar(kit);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del KitAtencion");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Kit de Atencion");
        }
        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarKitAtencion(@RequestParam String id) {
        AjaxResponse<KitAtencion> response = new AjaxResponse<KitAtencion>();

        boolean result = kitservice.eliminarProducto(id);
        if (result) {
            response.addMensaje("Se ha eliminado exitosamente el medicamento del Kit de Atencion.");
        } else {
            response.setHasError(!result);
            response.addMensaje("Ha ocurrido un error al eliminar a este este medicamento del Kit de Atencion");
        }
        return response;

    }

    @RequestMapping(value = "eliminarKit", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarKit(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("No tiene permisos para realizar esta acci&oacute;n");
            return response;
        }
        boolean ok = kitservice.eliminar(id);
        if (!ok) {
            response.setHasError(!ok);
            response.addMensaje("Ha ocurrido un error inesperado");
        } else {
            response.addMensaje("Se ha eliminado correctamente el kit de catenci&oaute;n");
            response.setHasError(false);
        }
        return response;//ELIMINAR KIT
    }

    @RequestMapping(value = "{idKit}/productosJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerProductosJSON(@PathVariable long idModulo, @PathVariable long idKit, HttpServletRequest request) {
        DataTablesParam params = DataTablesParamUtility.getParam(request);
        ManagerDatatables manager = new ManagerDatatables();

        int iDisplayStart, iDisplayLength;
        if (params != null) {
            iDisplayStart = params.iDisplayStart;
            iDisplayLength = params.iDisplayLength + 1;
        } else {
            iDisplayStart = 0;
            iDisplayLength = 1;
            params = new DataTablesParam();
            params.sEcho = "2";
        }

        Object[] sort = new Object[]{
            params.iSortingCols == 0 ? null : 1,
            params.iSortColumnIndex,
            params.sSortDirection
        };
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        String search = request.getParameter("sSearch");
        List<KitAtencionProducto> lista = kitservice.listarProductosModuloRango(idKit, idModulo, search, range, sort);

        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = lista.size();
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);

        return manager;
    }

    @RequestMapping(value = "{kitid}/agregarProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse agregarProducto(@PathVariable long kitid, @RequestParam int idProducto, @RequestParam int cantidad, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para realizar esta acci&oacute;n");
        } else {
            boolean ok = kitservice.agregarProducto(kitid, idProducto, cantidad);
            if (!ok) {
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error. No se ha podido agregar el producto");
            } else {
                response.addMensaje("Se ha agregado/actualizado el producto correctamente");
            }
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        List<KitAtencion> list = kitservice.listarModulo(idModulo);
        //TODO Ir a las clases KitAtencionPDF y reporteExcel para moificarlas
        return new ModelAndView("KitAtencionPDF", "Data", list);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(@PathVariable long idModulo, HttpServletRequest request, HttpServletResponse response) {
        List<KitAtencion> list = kitservice.listarModulo(idModulo);
        return new ModelAndView("KitAtencionExcel", "Data", list);
    }

}
