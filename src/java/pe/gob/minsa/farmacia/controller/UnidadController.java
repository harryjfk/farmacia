package pe.gob.minsa.farmacia.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.farmacia.domain.Unidad;
import pe.gob.minsa.farmacia.services.impl.UnidadService;

@Controller
public class UnidadController {

    @Autowired
    UnidadService unidadService;

    @RequestMapping(value = "/Unidad/unidadesJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<Unidad> obtenerUnidadesJSON(HttpServletRequest request, HttpServletResponse response) {
        return unidadService.listar();
    }

}
