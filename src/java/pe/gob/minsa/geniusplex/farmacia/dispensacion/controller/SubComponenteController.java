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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.KitAtencion;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.PuntoDeVenta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.SubComponente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ComponenteService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.DiagnosticoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.SubComponenteService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpPersonal;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpProducto;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpPersonalService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/subcomponente/*")
public class SubComponenteController {

    @Autowired
    private SubComponenteService subComponenteService;
    @Autowired
    private GpPersonalService personalService;
    @Autowired
    private DiagnosticoService diagnosticoService;
    @Autowired
    private ComponenteService componenteService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarSubComponentes(ModelAndView model, HttpServletRequest request) {
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Diagnóstico", "Coordinador", "Acciones"}));
        model.addObject("ajaxList", "");
        model.addObject("editUrl", "modificar");
        model.addObject("changeUrl", "cambiarEstado");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "id,id,descripcion,diagnostico.descripcion,coordinador.nombreCompleto");
        model.addObject("findItem", "getSubComponente");
        model.setViewName("SubComponente/listar");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model = new ModelAndView("redirect:/index", "usuario", usuario);
            return model;
        }
        return model;
    }

    @RequestMapping(value = "getSubComponentes", method = RequestMethod.GET)
    public @ResponseBody
    List<SubComponente> getSubComponentes(@RequestParam(required = false) Long componente, HttpServletRequest request) {
        if (componente == null) {
            return subComponenteService.listar();
        } else {
            return subComponenteService.listar(componente);
        }
    }

    @RequestMapping(value = "{idSubComponente}/productosAllJSON", method = RequestMethod.POST)
    public @ResponseBody
    List<GpProducto> getAllProductos(@PathVariable Long idSubComponente, HttpServletRequest request) {
        return subComponenteService.listarRango(null, null, new Object[]{null}, idSubComponente);
    }

    @RequestMapping(value = "{idSubComponente}/getCoordinador", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<GpPersonal> getCoordinador(@PathVariable long idSubComponente) {
        SubComponente subComponente = subComponenteService.obtenerPorId(idSubComponente);
        AjaxResponse<GpPersonal> response = new AjaxResponse<GpPersonal>();
        if (subComponente.getCoordinador() != null) {
            response.setData(subComponente.getCoordinador());
            return response;
        }
        response.setHasError(true);
        return response;
    }

    @RequestMapping(value = "{idSubComponente}/getDiagnostico", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<DiagnosticoCIE> getDiagnostico(@PathVariable long idSubComponente) {
        SubComponente subComponente = subComponenteService.obtenerPorId(idSubComponente);
        AjaxResponse<DiagnosticoCIE> response = new AjaxResponse<DiagnosticoCIE>();
        if (subComponente.getDiagnostico() != null) {
            response.setData(subComponente.getDiagnostico());
            return response;
        }
        response.setHasError(true);
        return response;
    }

    @RequestMapping(value = "{idSubComponente}/productosJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables getProductos(@PathVariable Long idSubComponente, HttpServletRequest request) {
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
        String search = params.sSearch;
        List<GpProducto> lista = subComponenteService.listarRango(range, search, sort, idSubComponente);
        int total = subComponenteService.contarPaginado(search, idSubComponente);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "{idSubComponente}/kitsJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables getKits(@PathVariable long idModulo, @PathVariable Long idSubComponente, HttpServletRequest request) {
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
        String search = params.sSearch;
        List<KitAtencion> lista = subComponenteService.listarRangoKitAtencions(range, search, sort, idSubComponente, idModulo);
        int total = subComponenteService.contarRangoKitsAtencion(search, idSubComponente, idModulo);
        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);
        return manager;
    }

    @RequestMapping(value = "getSubComponente", method = RequestMethod.GET)
    public @ResponseBody
    SubComponente getSubComponente(@RequestParam Long id, HttpServletRequest request) {
        return subComponenteService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<SubComponente>> insertarSubComponente(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<SubComponente>> response = new AjaxResponse<List<SubComponente>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }

        SubComponente subComponente;
        try {
            subComponente = getData(request, usuario);           
            subComponente.setUsuarioCreacion(usuario.getIdUsuario());
            subComponente.setUsuarioModificacion(usuario.getIdUsuario());
            boolean result = subComponenteService.insertar(subComponente);
            result = chequearComponente(request, subComponente, result);
            String comp = request.getParameter("componente");
            List<SubComponente> data;
            if (comp != null) {
                data = subComponenteService.listar(Long.parseLong(comp));
            } else {
                data = subComponenteService.listar();
            }
            if (result) {
                response.addMensaje("Se ha insertado exitosamente el Sub Componente.");
                response.setData(data);
            } else {
                response.addMensaje("Ha ocurrido un error al insertar este Sub Componente.");
                response.setHasError(true);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
        }

        return response;
    }

    private boolean chequearComponente(HttpServletRequest request, SubComponente subComponente, boolean result) throws NumberFormatException {
        String comp = request.getParameter("componente");
        if (comp != null) {
            Componente componente = componenteService.obtenerPorId(Long.parseLong(comp));
            componente.getSubComponentes().add(subComponente);
            result = componenteService.actualizar(componente);
        }
        return result;
    }

    private SubComponente getData(HttpServletRequest request, Usuario usuario) throws BusinessException {
        SubComponente subComponente = null;
        String desc = request.getParameter("descripcion");
        String coorCod = request.getParameter("coordinador");
        String diagCod = request.getParameter("diagnostico");
        long id = 0;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException numberFormatException) {
            java.util.logging.Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, numberFormatException);
            throw new BusinessException(Arrays.asList("El id proporcionado no es v&aacute;lido"));
        }
        GpPersonal coordinador = personalService.obtenerPorId(coorCod);
        DiagnosticoCIE diagnostico = diagnosticoService.obtenerPorId(diagCod);
        subComponente = new SubComponente(id, desc);
        subComponente.setCoordinador(coordinador);
        subComponente.setDiagnostico(diagnostico);
        subComponente.setUsuarioCreacion(usuario.getIdUsuario());
        subComponente.setUsuarioModificacion(usuario.getIdUsuario());
        return subComponente;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarDiagnostico(@RequestParam Long id, HttpServletRequest request) {
        AjaxResponse<SubComponente> response = new AjaxResponse<SubComponente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        try {
            boolean result = subComponenteService.eliminar(id);
            if (result) {
                response.addMensaje("Se ha borrado exitosamente el Sub Componente.");
            } else {
                response.setHasError(true);
                response.addMensaje("Ha ocurrido un error al borrar a este Sub Componente.");
            }
        } catch (Exception e) {
            response.setHasError(true);
            response.addMensaje("Lo sentimos ha ocurrido un error. Es muy probable que este Sub Componente est&eacute; asociado a una Intervenci&oacute;n Sanitaria u otro elemento.");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<SubComponente> cambiarEstado(@RequestParam Long id, HttpServletRequest request) {
        AjaxResponse<SubComponente> response = new AjaxResponse<SubComponente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        boolean result = subComponenteService.cambiarEstado(id);
        SubComponente subComponente = subComponenteService.obtenerPorId(id);
        subComponente.setUsuarioModificacion(usuario.getIdUsuario());
        subComponenteService.actualizar(subComponente);

        if (result) {
            response.addMensaje("Se ha cambiado el estado del Sub Componente.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Sub Componente.");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<SubComponente> modificarCliente(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<SubComponente> response = new AjaxResponse<SubComponente>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        SubComponente subComponente;
        try {
            subComponente = getData(request, usuario);           
            subComponente.setUsuarioModificacion(usuario.getIdUsuario());
            boolean result = subComponenteService.actualizar(subComponente);
            String comp = request.getParameter("componente");
            List<SubComponente> data;
            if (comp != null) {
                data = subComponenteService.listar(Long.parseLong(comp));
            } else {
                data = subComponenteService.listar();
            }
            if (result) {
                response.addMensaje("Se ha modificado exitosamente el Sub Componente.");
            } else {
                response.addMensaje("Ha ocurrido un error al modificar este Sub Componente.");
                response.setHasError(true);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setHasError(true);
            response.setMssg(ex.getMensajesError());
        }

        return response;
    }

    @RequestMapping(value = "agregarProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse agregarProducto(@RequestParam int idProducto, @RequestParam long idSubComponente, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        try {
            boolean ok = subComponenteService.agregarProducto(idProducto, idSubComponente);
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "eliminarProducto", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarProducto(@RequestParam int idProducto, @RequestParam long idSubComponente, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        try {
            boolean ok = subComponenteService.eliminarProducto(idProducto, idSubComponente);
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "agregarKit", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse agregarKit(@RequestParam long id, @RequestParam long idSubComponente, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        try {
            boolean ok = subComponenteService.agregarKit(id, idSubComponente);
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "eliminarKit", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarKit(@RequestParam long id, @RequestParam long idSubComponente, HttpServletRequest request) {
        AjaxResponse response = new AjaxResponse();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe ingresar en el sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        try {
            boolean ok = subComponenteService.eliminarKit(id, idSubComponente);
        } catch (BusinessException ex) {
            Logger.getLogger(SubComponenteController.class.getName()).log(Level.SEVERE, null, ex);
            response.setMssg(ex.getMensajesError());
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "SubComponentePDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "SubComponenteExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        String comString = request.getParameter("componente");
        List<SubComponente> data;
        String componente = "TODOS";
        if (comString != null && comString.length() > 0) {
            final long idComp = Long.parseLong(comString);
            data = subComponenteService.listar(idComp);
            Componente comp = componenteService.obtenerPorId(idComp);
            componente = comp.getDescripcion();
        } else {
            data = subComponenteService.listar();
        }

        List<String> contentLabels = Arrays.asList("Código", "Descripción", "Diagnóstico", "Coordinador");
        List<String> contentFields = Arrays.asList("id", "descripcion", "diagnostico:descripcion", "coordinador:nombreCompleto");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 25);
        contentColumns.put(1, 25);
        contentColumns.put(2, 25);
        contentColumns.put(3, 25);

        ModelAndView model = new ModelAndView(viewName);
        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 100);
        headerData.put("Componente", componente);

        model.addObject("Title", "Sub Componentes");
        model.addObject("ContentData", data);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
}
