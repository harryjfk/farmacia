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
import pe.gob.minsa.farmacia.domain.ConceptoDocumentoOrigen;
import pe.gob.minsa.farmacia.services.impl.ConceptoDocumentoOrigenService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
@RequestMapping("/ConceptoDocumentoOrigen")
public class ConceptoDocumentoOrigenController {
    
    @Autowired
    ConceptoDocumentoOrigenService conceptoDocumentoOrigenService;

    JsonResponse jsonResponse;
    
    @RequestMapping(value = "/listarIdDocumento", method = RequestMethod.GET)
    @ResponseBody
    public List<ConceptoDocumentoOrigen> listarDocumentoOrigen(@RequestParam int idConcepto) {        
        return conceptoDocumentoOrigenService.listar(idConcepto);
    }   
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarDocumentoOrigen(@RequestBody ConceptoDocumentoOrigen conceptoDocumentoOrigen, HttpServletRequest request) {
        
        conceptoDocumentoOrigen.setUsuarioCreacion(InterceptorSecurity.getIdUsuario(request));
        
        try {
            conceptoDocumentoOrigenService.insertar(conceptoDocumentoOrigen);
            jsonResponse = new JsonResponse();            
            jsonResponse.respuestaInsertar();
            jsonResponse.setData(conceptoDocumentoOrigen.getIdConceptoDocumentoOrigen());
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
    
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse eliminarDocumentoOrigen(@PathVariable int id) {

        try {
            conceptoDocumentoOrigenService.eliminar(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaEliminar();
        } catch (BusinessException e) {
            jsonResponse = new JsonResponse(false, e.getMensajesError());
        }

        return jsonResponse;
    }
}
