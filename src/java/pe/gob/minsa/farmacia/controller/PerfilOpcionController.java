package pe.gob.minsa.farmacia.controller;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Menu;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.PerfilOpcion;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.domain.dto.PerfilOpcionConfiguracion;
import pe.gob.minsa.farmacia.services.impl.MenuService;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.OpcionService;
import pe.gob.minsa.farmacia.services.impl.PerfilOpcionService;
import pe.gob.minsa.farmacia.services.impl.PerfilService;
import pe.gob.minsa.farmacia.services.impl.SubmenuService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;

@RequestMapping("/PerfilOpcion")
@Controller
public class PerfilOpcionController {

    @Autowired
    ModuloService moduloService;
    @Autowired
    SubmoduloService submoduloService;
    @Autowired
    MenuService menuService;
    @Autowired
    SubmenuService submenuService;
    @Autowired
    OpcionService opcionService;
    @Autowired
    PerfilService perfilService;
    @Autowired
    PerfilOpcionService perfilOpcionService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarPerfilOpcion() {
        List<Modulo> modulos = moduloService.listar();
        List<Perfil> perfiles = perfilService.listar();
        ModelMap model = new ModelMap();

        model.put("modulos", modulos);
        model.put("perfiles", perfiles);

        return new ModelAndView("PerfilOpcion", model);
    }
    
    @RequestMapping(value = "/opcionesPorSubmenuJSON", method = RequestMethod.GET)
    @ResponseBody
    public List<PerfilOpcionConfiguracion> listarOpcionePorSubmenuJSON(@RequestParam int idSubMenu, @RequestParam int idPerfil) {
        List<PerfilOpcionConfiguracion> perfilOpciones = perfilOpcionService.listarParaConfiguracion(idSubMenu, idPerfil);
        return perfilOpciones;
    }

    @RequestMapping(value = "/insertarOModificarOpciones", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public @ResponseBody int insertarOModificarOpciones(@RequestBody OpcionesPerfil opcionesPerfil) {
        
        for (int i = 0; i <= opcionesPerfil.getOpciones().size() - 1; ++i) {

            PerfilOpcionConfiguracion p = opcionesPerfil.getOpciones().get(i);
            PerfilOpcion perfilOpcion = new PerfilOpcion();
                        
            if (p.getIdPerfilOpcion() == 0) {

                if (p.getActivo() == 1) {                    
                    perfilOpcion.setIdPerfil(opcionesPerfil.getIdPerfil());
                    perfilOpcion.setIdOpcion(p.getIdOpcion());
                    perfilOpcion.setActivo(1);
                    perfilOpcionService.insertar(perfilOpcion);
                }

            } else {
                
                perfilOpcion.setIdPerfilOpcion(p.getIdPerfilOpcion());
                perfilOpcion.setIdPerfil(opcionesPerfil.getIdPerfil());
                perfilOpcion.setIdOpcion(p.getIdOpcion());
                perfilOpcion.setActivo(p.getActivo());
                perfilOpcionService.actualizar(perfilOpcion);
            }
        }

        return 1;
    }

}

class OpcionesPerfil {

    private List<PerfilOpcionConfiguracion> opciones;
    private int idPerfil;

    public OpcionesPerfil() {
        opciones = new ArrayList<PerfilOpcionConfiguracion>();
    }

    public List<PerfilOpcionConfiguracion> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<PerfilOpcionConfiguracion> opciones) {
        this.opciones = opciones;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
}
