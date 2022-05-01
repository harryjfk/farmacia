package pe.gob.minsa.farmacia.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Perfil;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.services.impl.UsuarioService;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PerfilService;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.farmacia.util.UtilMail;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PerfilService perfilService;
    @Autowired
    PersonalService personalService;
    @Autowired
    UtilMail utilMail;
    @Autowired
    SimpleMailMessage mensajeRecordarUsuario;

    JsonResponse jsonResponse;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView LoginGet(@ModelAttribute Usuario usuario) {
        return new ModelAndView("index");
    }
    
    @RequestMapping(value = "/Ici", method = RequestMethod.GET)
     public ModelAndView rptIci() {
        return new ModelAndView("IciExcel");
    }

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public ModelAndView LoginPost(@ModelAttribute Usuario usuario, HttpServletRequest request) {

        usuario = usuarioService.iniciarSesion(usuario.getNombreUsuario(), usuario.getClave());

        ModelAndView modelAndView;

        if (usuario.getIdUsuario() == 0) {
            modelAndView = new ModelAndView("index");
        } else {
            modelAndView = new ModelAndView("redirect:/blank/welcome", "usuario", usuario);
            HttpSession session = request.getSession();
            session.setAttribute("usuarioIniciado", usuario);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/blank/profile", method = RequestMethod.GET)
    public ModelAndView ProfileGET(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute("usuarioIniciado");

        return new ModelAndView("blank/profile", "usuario", usuario);
    }

    @RequestMapping(value = "/blank/profile", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse ProfilePOST(@RequestBody Usuario usuario, HttpServletRequest request) {
        HttpSession session = request.getSession();

        Usuario usuarioIniciado = (Usuario) session.getAttribute("usuarioIniciado");
        try {
            Usuario usuarioTemp = usuarioService.obtenerPorId(usuarioIniciado.getIdUsuario());

            usuarioTemp.setNombreUsuario(usuario.getNombreUsuario());
            usuarioTemp.setClave(usuario.getClave());
            usuarioTemp.setCorreo(usuario.getCorreo());

            usuarioService.actualizar(usuarioTemp);

            session.setAttribute("usuarioIniciado", usuarioTemp);
            jsonResponse = new JsonResponse();
            jsonResponse.setEstado(true);
            jsonResponse.getMensajesRepuesta().add("Se actulizó su perfil correctamente.");
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "rememberAccount", method = RequestMethod.POST)
    @ResponseBody
    public int RecordarCuenta(String correo) {
        int rpta = 0;

        Usuario usuario = new Usuario();

        for (Usuario u : usuarioService.listar()) {
            if (u.getCorreo().trim().equalsIgnoreCase(correo.trim())) {
                usuario = u;
            }
        }

        if (usuario.getIdUsuario() > 0) {
            mensajeRecordarUsuario.setTo(correo);
            utilMail.setSimpleMailMessage(mensajeRecordarUsuario);
            utilMail.sendMail(usuario.getNombreUsuario(), usuario.getClave());
            rpta = 1;
        }

        return rpta;
    }

    @RequestMapping(value = "/blank/welcome", method = RequestMethod.GET)
    public ModelAndView Welcome(@ModelAttribute Usuario usuario) {        
        return new ModelAndView("blank/welcome");
    }

    @RequestMapping(value = "/blank/error", method = RequestMethod.GET)
    public ModelAndView Error(@ModelAttribute List<String> errores) {
        return new ModelAndView("blank/error", "errores", errores);
    }
    
    private ManagerDatatables getUsuarioDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Usuario> usuarios = usuarioService.listar();

        managerDatatables.setiTotalRecords(usuarios.size());

        for (int i = 0; i <= usuarios.size() - 1; ++i) {
            Usuario u = usuarios.get(i);
            if (String.valueOf(u.getIdUsuario()).toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || u.getNombreUsuario().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || u.getPersonal().getNombreCompleto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())
                    || u.getPersonal().getNroDocumento().contains(dataTablesParam.sSearch.toLowerCase())
                    || u.getActivoTexto().toLowerCase().contains(dataTablesParam.sSearch.toLowerCase())) {
            } else {
                usuarios.remove(i);
                i = i - 1;
            }
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(usuarios, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario o1, Usuario o2) {
                switch (sortColumnIndex) {
                    case 1:
                        return String.valueOf(o1.getNombreUsuario()).toLowerCase().compareTo(String.valueOf(o2.getNombreUsuario()).toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getPersonal().getNombreCompleto().toLowerCase().compareTo(o2.getPersonal().getNombreCompleto().toLowerCase()) * sortDirection;
                    case 4:
                        return o1.getPersonal().getNroDocumento().toLowerCase().compareTo(o2.getPersonal().getNroDocumento().toLowerCase()) * sortDirection;
                    case 8:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;                    
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(usuarios.size());

        if (usuarios.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            usuarios = usuarios.subList(dataTablesParam.iDisplayStart, usuarios.size());
        } else {
            usuarios = usuarios.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(usuarios);

        return managerDatatables;
    }

    @RequestMapping(value = "/Usuario", method = RequestMethod.GET)
    public ModelAndView listarUsuarios() {
        return new ModelAndView("Usuario");
    }    

    @RequestMapping(value = "/Usuario/usuariosJSON", method = RequestMethod.GET)
    @ResponseBody
    public ManagerDatatables obtenerUsuariosJSON(HttpServletRequest request, HttpServletResponse response) {
        return getUsuarioDatatables(request, response);
    }

    @RequestMapping(value = "/Usuario/registrar", method = RequestMethod.GET)
    public ModelAndView registrarUsuario() {        
        List<Perfil> perfiles = perfilService.listar();

        ModelMap model = new ModelMap();
        model.put("perfiles", perfiles);

        return new ModelAndView("Usuario/registrar", model);
    }

    @RequestMapping(value = "/Usuario/registrar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse registrarUsuarioPOST(@RequestBody Usuario usuario) {

        try {
            usuarioService.insertar(usuario);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaInsertar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Usuario/modificar/{id}", method = RequestMethod.GET)
    public ModelAndView modificarUsuario(@PathVariable int id, HttpServletRequest request) {
        try {
            List<Perfil> perfiles = perfilService.listar();
            Usuario usuario = usuarioService.obtenerPorId(id);

            ModelMap model = new ModelMap();
            model.put("perfiles", perfiles);
            model.put("usuario", usuario);
            return new ModelAndView("Usuario/modificar", model);
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
            jsonResponse.setPaginaRedireccion(request.getContextPath() + "/Usuario");
            return new ModelAndView("blank/error", "reponseError", jsonResponse);
        }
    }

    @RequestMapping(value = "/Usuario/modificar", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse modificarUsuarioPOST(@RequestBody Usuario usuario) {
        try {
            usuarioService.actualizar(usuario);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaModificar();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }

        return jsonResponse;
    }

    @RequestMapping(value = "/Usuario/estado/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse cambiarEstado(@PathVariable int id) {
        try {
            usuarioService.cambiarEstado(id);
            jsonResponse = new JsonResponse();
            jsonResponse.respuestaCambiarEstado();
            jsonResponse.setPaginaRedireccion("listar");
        } catch (BusinessException ex) {
            jsonResponse = new JsonResponse(false, ex.getMensajesError());
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/Usuario/personalSinUsuario", method = RequestMethod.GET)
    @ResponseBody
    public List<Personal> personalSinUsuario() {
        return personalService.listarSinUsuario();
    }
}
