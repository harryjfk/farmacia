package pe.gob.minsa.farmacia.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.InterceptorSecurity;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
@RequestMapping("/Periodo")
public class PeriodoController {
    
    @Autowired
    PeriodoService periodoService;

    JsonResponse jsonResponse;
    
    @RequestMapping(value = "/periodosPorAnio/{anio}", method = RequestMethod.GET)
    @ResponseBody
    public List<Periodo> listarPorAnio(@PathVariable int anio) {
        return periodoService.listarPorAnio(anio);
    }
    
    @RequestMapping(value = "/aperturar/{anio}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse aperturarAnio(@PathVariable int anio, HttpServletRequest request) {
        try{
            periodoService.aperturarAnio(anio, InterceptorSecurity.getIdUsuario(request));            
            jsonResponse = new JsonResponse(true, Arrays.asList("Se aperturó el año " + String.valueOf(anio)));            
        }catch(BusinessException ex){
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }
        
        return jsonResponse;
    }
    
    @RequestMapping(value = "/abrirMes/{idPeriodo}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int idPeriodo, HttpServletRequest request) {
        try{
            Periodo periodo = new Periodo();
            periodo.setIdPeriodo(idPeriodo);
            periodo.setUsuarioModificacion(InterceptorSecurity.getIdUsuario(request));
            periodoService.abrirMes(periodo);
            jsonResponse = new JsonResponse(true, Arrays.asList("Se cambió de estado correctamente."));            
        }catch(BusinessException ex){
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }
        return jsonResponse;
    }
}
