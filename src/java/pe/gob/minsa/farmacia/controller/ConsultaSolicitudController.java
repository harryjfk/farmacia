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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import pe.gob.minsa.farmacia.domain.Historico;
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
public class ConsultaSolicitudController {
    
    @Autowired
    SolicitudService solicitudService;
    @Autowired
    ProductoService productoService;
    @Autowired
    PersonalService personalService;
    @Autowired
    SolicitudDetalleService solicitudDetalleService;
    
    @RequestMapping(value = "/MedicamentosAdquiridos", method = RequestMethod.GET)
    public ModelAndView listarSolicitudMedicamento() {
        return new ModelAndView("MedicamentosAdquiridos");
    }
    
    @RequestMapping(value = "/NoAprobados", method = RequestMethod.GET)
    public ModelAndView listarSolicitudNoAprobado() {
        return new ModelAndView("NoAprobados");
    }
    
    @RequestMapping(value = "/ConsumoHistorico", method = RequestMethod.GET)
    public ModelAndView listarSolicitudHistorico() {
        return new ModelAndView("ConsumoHistorico");
    }
    
    @RequestMapping(value = "/Solicitud/consultaSolicitudJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerSolicitudesDetJSON(HttpServletRequest request, HttpServletResponse response) {
        int tipoConsulta = Integer.parseInt(request.getParameter("tipoConsulta"));
        return getSolicitudDetDatatables(request, response, tipoConsulta);
    }
    
    @RequestMapping(value = "/Solicitud/consultaHistoricoJSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables obtenerHistoricoJSON(HttpServletRequest request, HttpServletResponse response) {
        return getSolicitudHistoricoDatatables(request, response);
    }
    
    private ManagerDatatables getSolicitudDetDatatables(HttpServletRequest request, HttpServletResponse response, int tipoConsulta) {
        
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<BalanceSemestral> solicitudes = new ArrayList<BalanceSemestral>();
        switch (tipoConsulta){
            case 1 : solicitudes = solicitudDetalleService.listarConsultaMedicamentos(); break;
            case 2 : solicitudes = solicitudDetalleService.listarConsultaNoAprobados(); break;
        }
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String establecimiento = request.getParameter("establecimiento")!=null?String.valueOf(request.getParameter("establecimiento")):"";
        String institucion = request.getParameter("institucion")!=null?String.valueOf(request.getParameter("institucion")):"";
        String producto = request.getParameter("producto")!=null?String.valueOf(request.getParameter("producto")):"";
        String fecIni = request.getParameter("fecIni")!=null?String.valueOf(request.getParameter("fecIni")):formateador.format(new Date());
        String fecFin = request.getParameter("fecFin")!=null?String.valueOf(request.getParameter("fecFin")):formateador.format(new Date());

        try {
            Date fechaDate1 = formateador.parse(fecIni);
            Date fechaDate2 = formateador.parse(fecFin);  
            managerDatatables.setiTotalRecords(solicitudes.size());
            
            for (int i = 0; i <= solicitudes.size() - 1; ++i) {
                BalanceSemestral d = solicitudes.get(i);
                if (d.getEstablecimiento().toLowerCase().contains(establecimiento.toLowerCase()) && 
                    d.getInstitucion().toLowerCase().contains(institucion.toLowerCase()) &&
                    d.getDescripcionProducto().toLowerCase().contains(producto.toLowerCase()) && 
                    ((fechaDate1.before(new Date(d.getFechaAprobacion()!=null?d.getFechaAprobacion().getTime():d.getFecha().getTime())) && fechaDate2.after(new Date(d.getFechaAprobacion()!=null?d.getFechaAprobacion().getTime():d.getFecha().getTime()))) || 
                     (fechaDate1.equals(formateador.parse(formateador.format(new Date(d.getFechaAprobacion()!=null?d.getFechaAprobacion().getTime():d.getFecha().getTime())))) || fechaDate2.equals(formateador.parse(formateador.format(new Date(d.getFechaAprobacion()!=null?d.getFechaAprobacion().getTime():d.getFecha().getTime())))))
                    )) {
                } else {
                    solicitudes.remove(i);
                    i = i - 1;
                }
            }
        
        } catch (ParseException e) {
           System.out.println("Se Produjo un Error!!!  "+e.getMessage());
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
                    case 3:
                        return o1.getDescripcionProducto().toLowerCase().compareTo(o2.getDescripcionProducto().toLowerCase()) * sortDirection;
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
            o.setFechaAprobacion(sd.getFechaAprobacion()!=null?sd.getFechaAprobacion():sd.getFecha());
            o.setFecha(sd.getFecha());
            o.setAprobado(sd.getAprobado());
            o.setTipoMedicamento(sd.getTipoMedicamento());
            o.setDescripcionProducto(sd.getDescripcionProducto());
            o.setConcentracion(sd.getConcentracion());
            o.setCantidad(sd.getCantidad());
            o.setCantidadSol(des[3]!=null?Integer.valueOf(des[3].toString()):0);
            o.setMotivo(sd.getMotivo());
            o.setCondicion(sd.getCondicion());
            o.setPrecio((des[3]!=null && des[4]!=null)?(Integer.valueOf(des[3].toString().trim()).intValue()>0?(String.valueOf(Double.valueOf(des[4].toString().trim())/Double.valueOf(des[3].toString().trim()))):"0"):"0");
            lista.add(o);
        }
        managerDatatables.setsEcho(dataTablesParam.sEcho);
        managerDatatables.setAaData(lista);

        return managerDatatables;
    }
    
    private ManagerDatatables getSolicitudHistoricoDatatables(HttpServletRequest request, HttpServletResponse response) {
               
        ManagerDatatables managerDatatables = new ManagerDatatables();
        DataTablesParam dataTablesParam = DataTablesParamUtility.getParam(request);
        List<Historico> solicitudes = new ArrayList<Historico>();
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fecIni = request.getParameter("fecIni")!=null?(!String.valueOf(request.getParameter("fecIni")).equals("")?String.valueOf(request.getParameter("fecIni")):formateador.format(new Date())):formateador.format(new Date());
        String fecFin = request.getParameter("fecFin")!=null?(!String.valueOf(request.getParameter("fecFin")).equals("")?String.valueOf(request.getParameter("fecFin")):formateador.format(new Date())):formateador.format(new Date());
        String [] arrFecIni = fecIni.split("/");
        String [] arrFecFin = fecFin.split("/");
        solicitudes = solicitudDetalleService.listarConsultaHistoricos(arrFecIni[2].concat(arrFecIni[1].concat(arrFecIni[0])),arrFecFin[2].concat(arrFecFin[1].concat(arrFecFin[0])));
        
        final int sortColumnIndex = dataTablesParam.iSortColumnIndex;
        final int sortDirection = dataTablesParam.sSortDirection.equals("desc") ? -1 : 1;

        Collections.sort(solicitudes, new Comparator<Historico>() {
            @Override
            public int compare(Historico o1, Historico o2) {
                switch (sortColumnIndex) {
                    case 0:
                        return (o1.getFechaAprobacion() == o2.getFechaAprobacion() ? 0 : 1) * sortDirection;
                    case 1:
                        return o1.getDescripcionProducto().toLowerCase().compareTo(o2.getDescripcionProducto().toLowerCase()) * sortDirection;
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
    
    @RequestMapping(value = "/Solicitud/excelTwo", method = RequestMethod.GET)
    public ModelAndView rptExcel(HttpServletRequest request, HttpServletResponse response) {
        int tipoConsulta = Integer.parseInt(request.getParameter("tipoConsulta"));
        ManagerDatatables balanceDatatables = getSolicitudDetDatatables(request, response, tipoConsulta);

        return new ModelAndView("ConsultaSolicitudExcel", "Data", balanceDatatables.getAaData());
    }
    
    @RequestMapping(value = "/Solicitud/pdfMedicamento", method = RequestMethod.GET)
    public ModelAndView rptMedicamentoPDF(HttpServletRequest request, HttpServletResponse response) {
        
        ManagerDatatables balanceDatatables = getSolicitudDetDatatables(request, response, 1);

        return new ModelAndView("ConsultaMedicamentoPDF", "Data", balanceDatatables.getAaData());
    }
    
    @RequestMapping(value = "/Solicitud/pdfNoAprobado", method = RequestMethod.GET)
    public ModelAndView rptNoAprobadoPDF(HttpServletRequest request, HttpServletResponse response) {
        
        ManagerDatatables balanceDatatables = getSolicitudDetDatatables(request, response, 2);

        return new ModelAndView("ConsultaNoAprobadoPDF", "Data", balanceDatatables.getAaData());
    }
    
    @RequestMapping(value = "/Solicitud/pdfHistorico", method = RequestMethod.GET)
    public ModelAndView rptHistoricoPDF(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables balanceDatatables = getSolicitudHistoricoDatatables(request, response);

        return new ModelAndView("ConsultaHistoricoPDF", "Data", balanceDatatables.getAaData());
    }
    
    @RequestMapping(value = "/Solicitud/excelHistorico", method = RequestMethod.GET)
    public ModelAndView rptHistoricoExcel(HttpServletRequest request, HttpServletResponse response) {

        ManagerDatatables balanceDatatables = getSolicitudHistoricoDatatables(request, response);

        return new ModelAndView("ConsultaHistoricoExcel", "Data", balanceDatatables.getAaData());
    }
}
