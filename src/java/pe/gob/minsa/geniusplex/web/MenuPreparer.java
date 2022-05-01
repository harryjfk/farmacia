package pe.gob.minsa.geniusplex.web;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.gob.minsa.farmacia.domain.Menu;
import pe.gob.minsa.farmacia.domain.Modulo;
import pe.gob.minsa.farmacia.domain.Opcion;
import pe.gob.minsa.farmacia.domain.Submenu;
import pe.gob.minsa.farmacia.domain.Submodulo;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.MenuService;
import pe.gob.minsa.farmacia.services.impl.ModuloService;
import pe.gob.minsa.farmacia.services.impl.OpcionService;
import pe.gob.minsa.farmacia.services.impl.PerfilOpcionService;
import pe.gob.minsa.farmacia.services.impl.SubmenuService;
import pe.gob.minsa.farmacia.services.impl.SubmoduloService;
import pe.gob.minsa.farmacia.util.BusinessException;

@Component
public class MenuPreparer implements ViewPreparer {

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
    PerfilOpcionService perfilOpcionService;

    @Override
    public void execute(Request rqst, AttributeContext ac) {

        Usuario usuario = (Usuario) rqst.getContext("session").get("usuarioIniciado");
        //String tituloExtra = (String) rqst.getContext("session").get("tituloExtra");
        String titleAttribute = "title";

        if (usuario == null) {

        } else {
            String enlace = rqst.getContext("request").get("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping").toString();

            //1 Vista
            //2 Acción
            String[] enlaceRutas = enlace.split("/");

            int idModulo = -1;
            int idSubmodulo = -1;

            List<Modulo> modulos = moduloService.listarParaSession(usuario.getIdUsuario());
            List<Submodulo> submodulos = submoduloService.listarParaSession(usuario.getIdUsuario());
            List<Menu> menus = menuService.listarParaSession(usuario.getIdUsuario());
            List<Submenu> submenus = submenuService.listarParaSession(usuario.getIdUsuario());

            List<String> breadCrumbs = new ArrayList<String>();

            if (enlaceRutas[1].equalsIgnoreCase("blank")) {
                //Los enlaces que son permitidos para todos los usuarios logeados.                
                if (enlaceRutas[2].equalsIgnoreCase("welcome")) {
                    rqst.getContext("request").put(titleAttribute, "Bienvenido");
                }

                if (enlaceRutas[2].equalsIgnoreCase("profile")) {
                    rqst.getContext("request").put(titleAttribute, "Mi Perfil");
                }
            } else {

                try {
                    Logger.getAnonymousLogger().log(Level.INFO, enlace);
                    Submenu submenu = submenuService.obtenerPorEnlace("/dispensacion" + enlace);
                    
                    Menu menu = menuService.obtenerPorId(submenu.getIdMenu());
                    Submodulo submodulo = submoduloService.obtenerPorId(menu.getIdSubmodulo());
                    Modulo modulo = moduloService.obtenerPorId(submodulo.getIdModulo());
                    
                    idModulo = modulo.getIdModulo();
                    idSubmodulo = submodulo.getIdSubmodulo();
                    
                    //Opciones del submenú según el usuario logeado
                    List<Opcion> opcionesSubmenu = opcionService.listarParaSession(usuario.getIdUsuario(), submenu.getIdSubmenu());
                    rqst.getContext("request").put("opcionesSubmenu", opcionesSubmenu);

                    //Barra de donde estoy
                    breadCrumbs.add(modulo.getNombreModulo());
                    breadCrumbs.add(submodulo.getNombreSubmodulo());
                    breadCrumbs.add(menu.getNombreMenu());
                    breadCrumbs.add(submenu.getNombreSubmenu());
                    
                    if(enlaceRutas.length >= 3){                        
                        for(Opcion o : opcionesSubmenu){                            
                            if(o.getAppOpcion().equalsIgnoreCase(enlaceRutas[2])){
                                breadCrumbs.add(o.getNombreOpcion());
                                break;
                            }
                        }
                    }
                    
                    rqst.getContext("request").put("breadCrumbs", breadCrumbs);

                    //Para Modulo y submodulo seleccionado
                    rqst.getContext("request").put("idModuloView", idModulo);
                    rqst.getContext("request").put("idSubmoduloView", idSubmodulo);

                    //Mandar título según submenu
                    if(breadCrumbs.size() == 4){
                        rqst.getContext("request").put(titleAttribute, breadCrumbs.get(3));
                    }else{
                        rqst.getContext("request").put(titleAttribute, breadCrumbs.get(4));
                    }                    
                } catch (BusinessException ex) {
                    
                }
            }

            //Todos los modulos, submoduslo, menus y submenus del usuario iniciado
            rqst.getContext("request").put("modulosTemplate", modulos);
            rqst.getContext("request").put("submodulosTemplate", submodulos);
            rqst.getContext("request").put("menusTemplate", menus);
            rqst.getContext("request").put("submenusTemplate", submenus);
        }
    }
}
