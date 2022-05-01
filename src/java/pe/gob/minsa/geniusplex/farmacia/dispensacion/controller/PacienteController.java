package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Paciente;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.PacienteService;

@Controller
@RequestMapping("/Paciente/*")
public class PacienteController {

    @Autowired
    private PacienteService pacientService;


    @RequestMapping(value = "getPacientes", method = RequestMethod.GET)
    public @ResponseBody
    List<Paciente> getPacientes(@PathVariable long idModulo) {
        List<Paciente> lista = pacientService.listar();
        return lista;
    }

    @RequestMapping(value = "getPaciente", method = RequestMethod.GET)
    public @ResponseBody
    Paciente getPaciente(@RequestParam String id) {
        return pacientService.obtenerPorId(id);
    }

}
