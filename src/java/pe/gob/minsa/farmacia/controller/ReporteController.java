package pe.gob.minsa.farmacia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.services.impl.ConceptoService;
import pe.gob.minsa.farmacia.services.impl.MovimientoService;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;

@Controller
public class ReporteController {
    
    @Autowired
    PeriodoService periodoService;
    
    @Autowired
    MovimientoService movimientoService;
    
    @Autowired
    ConceptoService conceptoService; 
    
    @Autowired
    TipoProductoService tipoProductoService;
    
    @RequestMapping(value = "/ControlCalidad", method = RequestMethod.GET)
    public ModelAndView rptControlCalidadGET(){
        ModelMap model = new ModelMap();
        model.addAttribute("anios", periodoService.listarAnios());
        return new ModelAndView("ControlCalidad", model);
    }
    
    @RequestMapping(value = "/EnvioInformacion", method = RequestMethod.GET)
    public ModelAndView rptEnvioInformacionGET(){
        return new ModelAndView("EnvioInformacion");
    }
    
    @RequestMapping(value = "/ExportarTablas", method = RequestMethod.GET)
    public ModelAndView rptExportarTablasGET(){
        return new ModelAndView("ExportarTablas");
    }
    
    @RequestMapping(value = "/InterfaceSIGA", method = RequestMethod.GET)
    public ModelAndView rptInterfaceSIGAGET(){
        return new ModelAndView("InterfaceSIGA");
    }
    
    @RequestMapping(value = "/InterfaceSISMED", method = RequestMethod.GET)
    public ModelAndView rptInterfaceSISMEDGET(){
        return new ModelAndView("InterfaceSISMED");
    }
    
    @RequestMapping(value = "/IndicadoresGestion", method = RequestMethod.GET)
    public ModelAndView rptIndicadoresGestionGET(){
        ModelMap model = new ModelMap();
        model.addAttribute("anios", periodoService.listarAnios());
        model.addAttribute("tiposProducto", tipoProductoService.listar());
        return new ModelAndView("IndicadoresGestion", model);
    }    
}
