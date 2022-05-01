/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Proceso;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.ProcesoService;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/proceso/*")
public class ProcController {
    
    @Autowired
    private ProcesoService procesoService;
    
    @RequestMapping(value = "getProcesos", method = RequestMethod.GET)
    public @ResponseBody List<Proceso> getProcesos(){
        return procesoService.listar();
    }
}
