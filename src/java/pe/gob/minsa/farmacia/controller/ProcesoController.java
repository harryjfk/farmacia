package pe.gob.minsa.farmacia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;

@Controller
public class ProcesoController {
    
    @Autowired
    PeriodoService periodoService;
    
    @RequestMapping(value = "/ActualizacionCatalogo", method = RequestMethod.GET)
    public ModelAndView actualizacionCatalogoGET(){
        return new ModelAndView("ActualizacionCatalogo");
    }
    
    @RequestMapping(value = "/AperturaPeriodo", method = RequestMethod.GET)
    public ModelAndView aperturaPeriodoGET(){
        return new ModelAndView("AperturaPeriodo");
    }
    
    @RequestMapping(value = "/AnulacionGuiaRemision", method = RequestMethod.GET)
    public ModelAndView anulacionGuiaRemisionGET(){
        ModelMap model = new ModelMap();
        model.addAttribute("anios", periodoService.listarAnios());
        return new ModelAndView("AnulacionGuiaRemision", model);
    }    
}
