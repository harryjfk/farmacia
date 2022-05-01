package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Turno;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Vendedor;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.TurnoService;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VendedorService;
import pe.gob.minsa.geniusplex.web.AjaxResponse;
import pe.gob.minsa.geniusplex.web.WebUtil;

@Controller
@RequestMapping("/{idModulo}/turno/*")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private VendedorService vendedorSevice;

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listarTurnos(Model model, @PathVariable long idModulo) {
        model.addAttribute("viewTitle", "Mantenimiento de Turnos");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Código", "Descripci&oacute;n", "Inicio", "Termino", "Acciones"}));
        model.addAttribute("ajaxList", "getTurnos");
        model.addAttribute("editUrl", "modificar");
        model.addAttribute("changeUrl", "cambiarEstado");
        model.addAttribute("removeUrl", "eliminar");
        model.addAttribute("tableProperties", "idTurno,codigo,descripcion,horaInicio:time,horaFinal:time");
        model.addAttribute("findItem", "getTurno");
        model.addAttribute("idModulo", idModulo);
        return "Turno/listar";
    }

    @RequestMapping(value = "getTurnos", method = RequestMethod.GET)
    public @ResponseBody
    List<Turno> getTurnos(@PathVariable long idModulo, @RequestParam(required = false) Long idVendedor, HttpServletRequest request) {
        List<Turno> lista = turnoService.listarModulo(idModulo);
        if (idVendedor != null && idVendedor > 0) {
            Vendedor v = vendedorSevice.obtenerPorId(idVendedor);
            for (Iterator<Turno> it = lista.iterator(); it.hasNext();) {
                Turno turno = it.next();
                if (!turno.getVendedores().contains(v)) {
                    it.remove();
                }
            }
        }
        return lista;
    }

    @RequestMapping(value = "/{idTurno}/getVendedores", method = RequestMethod.GET)
    public @ResponseBody
    List<Vendedor> getVendedores(@PathVariable long idTurno) {
        Turno turno = turnoService.obtenerPorId(idTurno);
        return turno.getVendedores();
    }

    @RequestMapping(value = "getTurno", method = RequestMethod.GET)
    public @ResponseBody
    Turno getTurno(@RequestParam long id) {
        return turnoService.obtenerPorId(id);
    }

    @RequestMapping(value = "insertar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Turno>> insertarTurno(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Turno>> response = new AjaxResponse<List<Turno>>();
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        Turno turno = this.getTurnoData(request);
        turno.setUsuarioCreacion(usuario.getIdUsuario());
        turno.setUsuarioModificacion(usuario.getIdUsuario());
        turno.setIdModulo(idModulo);
        List<Turno> TurnosSolapados = turnoService.TurnosSolapados(idModulo, turno.getHoraInicio(), turno.getHoraFinal());

        if (TurnosSolapados.size() > 0) {
            response.addMensaje("El horario de este turno coincide con otro.");
            response.setHasError(true);
            return response;
        }

        boolean result = turnoService.insertar(turno);

        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Turno.");

        } else {
            response.addMensaje("Ha ocurrido un error al insertar este Turno.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "insertarVendedor", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<List<Turno>> insertarVendedor(@PathVariable long idModulo, HttpServletRequest request) {
        AjaxResponse<List<Turno>> response = new AjaxResponse<List<Turno>>();
        long idTurno = Long.parseLong(request.getParameter("turno"));
        long idVendedor = Long.parseLong(request.getParameter("vendedor"));
        Vendedor vendedor = vendedorSevice.obtenerPorId(idVendedor);
        Turno turno = turnoService.obtenerPorId(idTurno);
        List<Vendedor> vendedores = turno.getVendedores();
        if (vendedores.contains(vendedor)) {
            response.addMensaje("Este vendedor ya esta asignado a este turno.");
            response.setHasError(true);
            return response;

        }
        vendedores.add(vendedor);
        turno.setVendedores(vendedores);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        turno.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = turnoService.actualizar(turno);
        if (result) {
            response.addMensaje("Se ha insertado exitosamente el Turno.");
        } else {
            response.addMensaje("Ha ocurrido un error al insertar este vendedor.");
            response.setHasError(true);
        }

        return response;
    }

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarTurno(@RequestParam long id) {
        AjaxResponse<Turno> response = new AjaxResponse<Turno>();
        boolean result = turnoService.eliminar(id);
        if (result) {
            response.addMensaje("Se ha borrado exitosamente el turno.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al borrar a este turno");
        }
        return response;
    }

    @RequestMapping(value = "eliminarVendedor", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse eliminarVendedor(HttpServletRequest request) {
        AjaxResponse<Turno> response = new AjaxResponse<Turno>();
        long idTurno = Long.parseLong(request.getParameter("turno"));
        long idVendedor = Long.parseLong(request.getParameter("vendedor"));
        Vendedor vendedor = vendedorSevice.obtenerPorId(idVendedor);
        Turno turno = turnoService.obtenerPorId(idTurno);

        List<Vendedor> vendedores = turno.getVendedores();

        boolean remove = vendedores.remove(vendedor);
        turno.setVendedores(vendedores);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        turno.setUsuarioModificacion(usuario.getIdUsuario());
        boolean result = turnoService.actualizar(turno);
        if (result) {
            response.addMensaje("Se ha actualizado exitosamente el turno.");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al actualizar este turno");
        }
        return response;
    }

    @RequestMapping(value = "cambiarEstado", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Turno> cambiarEstado(@RequestParam long id, HttpServletRequest request) {
        AjaxResponse<Turno> response = new AjaxResponse<Turno>();
        boolean result = turnoService.cambiarEstado(id);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        Turno turno = turnoService.obtenerPorId(id);
        turno.setUsuarioModificacion(usuario.getIdUsuario());
        turnoService.actualizar(turno);
        if (result) {
            response.addMensaje("Se ha cambiado el estado del turno");
        } else {
            response.setHasError(true);
            response.addMensaje("Ha ocurrido un error al cambiar de estado a este turno");
        }

        return response;
    }

    @RequestMapping(value = "modificar", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResponse<Turno> modificarTurno(HttpServletRequest request) {
        Turno turno = this.getTurnoData(request);
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        turno.setUsuarioModificacion(usuario.getIdUsuario());
        AjaxResponse<Turno> response = new AjaxResponse<Turno>();
        boolean result = turnoService.actualizar(turno);
        if (result) {
            response.addMensaje("Se ha modificado exitosamente el Turno.");
        } else {
            response.addMensaje("Ha ocurrido un error al modificar este Turno.");
            response.setHasError(true);
        }
        return response;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Turno> lista = turnoService.listarModulo(idModulo);

        return new ModelAndView("TurnoPDF", "Data", lista);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Turno> lista = turnoService.listarModulo(idModulo);
        return new ModelAndView("TurnoExcel", "Data", lista);
    }

    private Turno getTurnoData(HttpServletRequest request) {
        SimpleDateFormat format = new SimpleDateFormat("H:mm");

        long id = Long.parseLong(request.getParameter("idTurno"));
        Turno turno = null;

        if (id == 0) {
            turno = new Turno();
        } else {
            turno = turnoService.obtenerPorId(id);
            turnoService.sincronizar(turno);
        }

        turno.setDescripcion(request.getParameter("descripcion"));
        Date inicio = null;
        Date fin = null;
        try {
            inicio = format.parse(request.getParameter("horaInicio"));
            fin = format.parse(request.getParameter("horaFinal"));
            if (inicio.compareTo(fin) > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fin);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                fin = calendar.getTime();
            }

        } catch (ParseException ex) {
            Logger.getLogger(TurnoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        turno.setHoraInicio(inicio);
        turno.setHoraFinal(fin);
        String[] vendedores = request.getParameterValues("vendedoresTurno");
        ArrayList<Vendedor> listaVendedores = new ArrayList<Vendedor>();
        if (vendedores != null && vendedores.length > 0) {
            long index = 0;
            for (String vendedor : vendedores) {
                index = Long.parseLong(vendedor);
                listaVendedores.add(vendedorSevice.obtenerPorId(index));
            }
        }
        turno.setVendedores(listaVendedores);

        return turno;
    }
}
