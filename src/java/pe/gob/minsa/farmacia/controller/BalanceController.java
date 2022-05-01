
package pe.gob.minsa.farmacia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Almacen;
import pe.gob.minsa.farmacia.domain.BalanceSemestral;
import pe.gob.minsa.farmacia.domain.Medicamento;
import pe.gob.minsa.farmacia.domain.Personal;
import pe.gob.minsa.farmacia.domain.Producto;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.farmacia.domain.Solicitud;
import pe.gob.minsa.farmacia.domain.SolicitudDetalle;
import pe.gob.minsa.farmacia.domain.TipoAccion;
import pe.gob.minsa.farmacia.services.impl.PersonalService;
import pe.gob.minsa.farmacia.services.impl.ProductoService;
import pe.gob.minsa.farmacia.services.impl.SolicitudDetalleService;
import pe.gob.minsa.farmacia.services.impl.SolicitudService;
import pe.gob.minsa.farmacia.services.impl.TipoAccionService;
import pe.gob.minsa.farmacia.util.BusinessException;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.JsonResponse;

@Controller
@RequestMapping("/Balance")
public class BalanceController {
    
    @Autowired
    SolicitudService solicitudService;
    @Autowired
    ProductoService productoService;
    @Autowired
    PersonalService personalService;
    @Autowired
    SolicitudDetalleService solicitudDetalleService;
    
    private ManagerDatatables getSolicitudDatatables(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Solicitud> solicitudes = solicitudService.listar();
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String establecimiento = request.getParameter("establecimiento")!=null?String.valueOf(request.getParameter("establecimiento")):"";
        String institucion = request.getParameter("institucion")!=null?String.valueOf(request.getParameter("institucion")):"";
        String medico = request.getParameter("medico")!=null?String.valueOf(request.getParameter("medico")):"";
        String fecha = request.getParameter("fechita")!=null?String.valueOf(request.getParameter("fechita")):formateador.format(new Date());

        try {
            managerDatatables.setiTotalRecords(solicitudes.size());
            
            Date fechaDate = formateador.parse(fecha);
            Calendar calendar = new GregorianCalendar(Integer.valueOf(fecha.substring(6, 9)).intValue(), Integer.valueOf(fecha.substring(3, 4)).intValue()-6, Integer.valueOf(fecha.substring(0, 1))); 
            Date fechita = new Date(calendar.getTimeInMillis());

            for (int i = 0; i <= solicitudes.size() - 1; ++i) {
                Solicitud d = solicitudes.get(i);
                if (d.getMedico().toLowerCase().contains(medico.toLowerCase()) && d.getEstablecimiento().toLowerCase().contains(establecimiento.toLowerCase())
                        && d.getInstitucion().toLowerCase().contains(institucion.toLowerCase()) && (fechaDate.after(new Date(d.getFecha().getTime())) && fechita.before(new Date(d.getFecha().getTime())))) {
                } else {
                    solicitudes.remove(i);
                    i = i - 1;
                }
            }
        
        } catch (ParseException e) {
           
        }

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(solicitudes, new Comparator<Solicitud>() {
            @Override
            public int compare(Solicitud o1, Solicitud o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((o1.getIdSolicitud() == o2.getIdSolicitud()) ? 0 : 1) * sortDirection;
                    case 1:
                        return o1.getMedico().toLowerCase().compareTo(o2.getMedico().toLowerCase()) * sortDirection;
                    case 2:
                        return o1.getEstablecimiento().toLowerCase().compareTo(o2.getEstablecimiento().toLowerCase()) * sortDirection;
                    case 3:
                        return o1.getInstitucion().toLowerCase().compareTo(o2.getInstitucion().toLowerCase()) * sortDirection;
                    case 4:
                        return o1.getActivoTexto().toLowerCase().compareTo(o2.getActivoTexto().toLowerCase()) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(solicitudes.size());

        if (solicitudes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, solicitudes.size());
        } else {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }

        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(solicitudes);

        return managerDatatables;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listarSolicitud() {
        return new ModelAndView("Balance");
    }

    @RequestMapping(value = "/solicitudesJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerSolicitudesJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSolicitudDatatables(request, response);
    }
    
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public ModelAndView balanceSolicitud(@RequestParam int idSolicitud) {

        try {
            Solicitud solicitud = solicitudService.obtenerPorId(idSolicitud);
            List<Personal> medicos = personalService.listarMedico();;
            
            ModelMap model = new ModelMap();
            model.put("medicos", medicos);
            Personal personal = personalService.obtenerPorId(solicitud.getIdMedico());
            model.put("personal", personal);
            model.put("solicitud", solicitud);

            return new ModelAndView("Balance/informe", model);
        } catch (BusinessException e) {
            //REGRESAR
            return new ModelAndView("redirect:error", "errores", e.getMensajesError());
        }
    }

    @RequestMapping(value = "/balanceJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerSolicitudesDetJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSolicitudDetDatatables(request, response);
    }
    
    private ManagerDatatables getSolicitudDetDatatables(HttpServletRequest request, HttpServletResponse response) {
        
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String establecimiento = request.getParameter("establecimiento")!=null?String.valueOf(request.getParameter("establecimiento")):"";
        String institucion = request.getParameter("institucion")!=null?String.valueOf(request.getParameter("institucion")):"";
        
        String fecha = request.getParameter("fechita")!=null?String.valueOf(request.getParameter("fechita")):formateador.format(new Date());
        String [] arrFecha = fecha.split("/");
        
        List<BalanceSemestral> solicitudes = solicitudDetalleService.listarBalance(arrFecha[2].concat(arrFecha[1].concat(arrFecha[0])));
        
        managerDatatables.setiTotalRecords(solicitudes.size());
        
        
            
         for (int i = 0; i <= solicitudes.size() - 1; ++i) {
            BalanceSemestral d = solicitudes.get(i);
            
            if (d.getEstablecimiento().toLowerCase().contains(establecimiento.toLowerCase()) && 
                 d.getInstitucion().toLowerCase().contains(institucion.toLowerCase())){
            } else {
                solicitudes.remove(i);
                i = i - 1;
            }
         }        

        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(solicitudes, new Comparator<BalanceSemestral>() {
            @Override
            public int compare(BalanceSemestral o1, BalanceSemestral o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return ((o1.getIdSolicitud() == o2.getIdSolicitud()) ? 0 : 1) * sortDirection;
                    case 1:
                        return ((o1.getIdSolicitudDetalleProducto() == o2.getIdSolicitudDetalleProducto()) ? 0 : 1) * sortDirection;
                    case 2:
                        return (o1.getFechaAprobacion() == o2.getFechaAprobacion() ? 0 : 1) * sortDirection;
                }
                return 0;
            }
        });

        managerDatatables.setiTotalDisplayRecords(solicitudes.size());

        if (solicitudes.size() < dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength) {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, solicitudes.size());
        } else {
            solicitudes = solicitudes.subList(dataTablesParam.iDisplayStart, dataTablesParam.iDisplayStart + dataTablesParam.iDisplayLength);
        }
        
        List<BalanceSemestral> lista = new ArrayList<BalanceSemestral>();
        for (int i = 0; i <= solicitudes.size() - 1; ++i) {
            BalanceSemestral sd = solicitudes.get(i);
            BalanceSemestral o = new BalanceSemestral();
            String[] des = sd.getDescripcionMedicamento().split("~");
            o.setIdSolicitud(sd.getIdSolicitud());
            o.setIdSolicitudDetalleProducto(sd.getIdSolicitudDetalleProducto());
            o.setEstablecimiento(sd.getEstablecimiento());
            o.setInstitucion(sd.getInstitucion());
            o.setNombreFormaFarmaceutica(sd.getNombreFormaFarmaceutica());
            o.setFormaPresentacion(sd.getFormaPresentacion());
            o.setFechaAprobacion(sd.getFechaAprobacion());
            o.setFecha(sd.getFecha());
            o.setAprobado(sd.getAprobado());
            o.setTipoMedicamento(sd.getTipoMedicamento());
            o.setDescripcionProducto(sd.getDescripcionProducto());
            o.setConcentracion(sd.getConcentracion());
            o.setCantidad(sd.getCantidad());
            o.setMotivo(sd.getMotivo());
            o.setCondicion(sd.getCondicion());
            o.setPrecio((des[3]!=null && des[4]!=null)?(Integer.valueOf(des[3].toString().trim()).intValue()>0?(String.valueOf(Double.valueOf(des[4].toString().trim())/Double.valueOf(des[3].toString().trim()))):"0"):"0");
            lista.add(o);
        }
        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(lista);

        return managerDatatables;
    }
    
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    public ModelAndView rptPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables balanceDatatables = getSolicitudDetDatatables(request, response);

        return new ModelAndView("BalancePDF", "Data", balanceDatatables.getAaData());
    }

    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables balanceDatatables = getSolicitudDetDatatables(request, response);

        return new ModelAndView("BalanceExcel", "Data", balanceDatatables.getAaData());
    }    
}
