package pe.gob.minsa.farmacia.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.farmacia.domain.ConceptoTipoDocumentoMov;
import pe.gob.minsa.farmacia.services.impl.ConceptoTipoDocumentoMovService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
@RequestMapping("/ConceptoTipoDocumentoMov")
public class ConceptoTipoDocumentoMovController {

    @Autowired
    ConceptoTipoDocumentoMovService conceptoTipoDocumentoMovService;

    JsonResponse jsonResponse;

    @RequestMapping(value = "/listarIdDocumento", method = RequestMethod.GET)
    @ResponseBody
    public List<ConceptoTipoDocumentoMov> listarTipoDocumentoMov(@RequestParam int idConcepto) {
        return conceptoTipoDocumentoMovService.listar(idConcepto);
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarTipoDocumentoMov(@RequestBody ConceptoTipoDocumentoMov conceptoTipoDocumentoMov, HttpServletRequest request) {

        conceptoTipoDocumentoMov.setUsuarioCreacion(InterceptorSecurity.getIdUsuario(request));

        try {
            conceptoTipoDocumentoMovService.insertar(conceptoTipoDocumentoMov);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
            jsonResponse.setData(conceptoTipoDocumentoMov.getIdConceptoTipoDocumentoMov());
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarTipoDocumentoMov(@PathVariable int id) {

        try {
            conceptoTipoDocumentoMovService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
}
