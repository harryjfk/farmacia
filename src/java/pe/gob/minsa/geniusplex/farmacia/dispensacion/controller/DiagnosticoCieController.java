package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.DiagnosticoCIE;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.DiagnosticoService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/diagnosticoCie/*")
public class DiagnosticoCieController {

    @Autowired
    private DiagnosticoService diagnosticoService;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ModelAndView listarDiagnosticos(ModelAndView model, HttpServletRequest request) {
        model.addObject("tableHeaders", Arrays.asList(new String[]{"Código", "Descripción", "Acciones"}));
        model.addObject("ajaxList", "getDiagnosticos");
        model.addObject("editUrl", "modificar");
        model.addObject("changeUrl", "cambiarEstado");
        model.addObject("removeUrl", "eliminar");
        model.addObject("tableProperties", "codigo,codigo,descripcion");
        model.addObject("findItem", "getDiagnostico");
        model.setViewName("DiagnosticoCie/listar");
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            model = new ModelAndView("redirect:/index", "usuario", usuario);
            return model;
        }
        return model;
    }

    @RequestMapping(value = "getDiagnosticos", method = RequestMethod.GET)
    public @ResponseBody
    List<DiagnosticoCIE> getDiagnosticos(@PathVariable long idModulo) {
        List<DiagnosticoCIE> lista = diagnosticoService.listar();
        return lista;
    }

    @RequestMapping(value = "getDiagnostico", method = RequestMethod.GET)
    public @ResponseBody
    DiagnosticoCIE getDiagnostico(@RequestParam String id) {
        return diagnosticoService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<DiagnosticoCIE>> insertarDiagnostico(@ModelAttribute DiagnosticoCIE diagnostico, HttpServletRequest request) {
        AjaxResponse<List<DiagnosticoCIE>> response = new AjaxResponse<List<DiagnosticoCIE>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        diagnostico.setUsuarioCreacion(usuario.getIdUsuario());
        diagnostico.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = diagnosticoService.insertar(diagnostico);
        List<DiagnosticoCIE> data = diagnosticoService.listar();
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Diagn&oacute;stico.");
            response.setData(data);
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Diagn&oacute;stico.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarDiagnostico(@RequestParam String id, HttpServletRequest request) {
        AjaxResponse<DiagnosticoCIE> response = new AjaxResponse<DiagnosticoCIE>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        boolean result = diagnosticoService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el Diagn&oacute;stico.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este Diagn&oacute;stico.");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<DiagnosticoCIE> cambiarEstado(@RequestParam String id, HttpServletRequest request) {
        AjaxResponse<DiagnosticoCIE> response = new AjaxResponse<DiagnosticoCIE>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario == null || usuario.getIdUsuario() == 0) {
            response.setHasError(true);
            response.addMensaje("Debe entrar al sistema con sus credenciales para poder realizar esta acci&oacute;n");
            return response;
        }
        boolean result = diagnosticoService.cambiarEstado(id);
        DiagnosticoCIE diagnosticoCIE = diagnosticoService.obtenerPorId(id);
        diagnosticoCIE.setUsuarioModificacion(usuario.getIdUsuario());
        diagnosticoService.actualizar(diagnosticoCIE);

        if (result) {
            response.addMensaje("Se ha cambiado el estado del Diagn&oacute;stico.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este Diagn&oacute;stico.");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<DiagnosticoCIE> modificarDiagnostico(@ModelAttribute DiagnosticoCIE diagnostico, HttpServletRequest request) {
        AjaxResponse<DiagnosticoCIE> response = new AjaxResponse<DiagnosticoCIE>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        diagnostico.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = diagnosticoService.actualizar(diagnostico);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Diagn&oacute;stico.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Diagn&oacute;stico.");
            response.setHasError(true);
        }
        return response;
    }
    
     @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "GenericExcel");
        return model;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) throws NumberFormatException {
        List<DiagnosticoCIE> lista = diagnosticoService.listar();
        ModelAndView model = new ModelAndView(viewName);
        List<String> contentLabels = Arrays.asList("Código", "Descripción");
        List<String> contentFields = Arrays.asList("codigo", "descripcion");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 50);
        contentColumns.put(1, 50);
        model.addObject("Title", "Reporte");
        model.addObject("ContentData", lista);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        return model;
    }
}
