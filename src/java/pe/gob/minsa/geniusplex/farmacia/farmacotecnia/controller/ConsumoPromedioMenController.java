/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.farmacia.domain.Periodo;
import pe.gob.minsa.farmacia.domain.Usuario;
import pe.gob.minsa.farmacia.services.impl.PeriodoService;
import pe.gob.minsa.farmacia.services.impl.TipoProductoService;
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.ConsumoPromedioMenDto;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.ConsumoPromedioMensualService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/consumopromedomen/*")
public class ConsumoPromedioMenController {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private ConsumoPromedioMensualService cpmService;

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public ModelAndView consultar(@PathVariable long idModulo, HttpServletRequest request, ModelAndView model) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("anios", periodoService.listarAnios());
            model.addObject("almacenes", almacenService.listarPorModulo(idModulo));
            model.setViewName("ConsumoPromedioMen/consultar");
        } else {
            model.setViewName("redirect:/index");
            model.addObject("usuario", usuario);
        }
        return model;
    }

    @RequestMapping(value = "JSON", method = RequestMethod.GET)
    public @ResponseBody
    ManagerDatatables consultarJSON(@PathVariable long idModulo, HttpServletRequest request) {
        DataTablesParam params = DataTablesParamUtility.getParam(request);
        ManagerDatatables manager = new ManagerDatatables();

        int iDisplayStart, iDisplayLength;
        if (params != null) {
            iDisplayStart = params.iDisplayStart;
            iDisplayLength = params.iDisplayLength + 1;
        } else {
            iDisplayStart = 0;
            iDisplayLength = 1;
            params = new DataTablesParam();
            params.sEcho = "2";
        }

        Object[] sort = new Object[]{
            params.iSortingCols == 0 ? null : 1,
            params.iSortColumnIndex,
            params.sSortDirection
        };
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        String search = params.sSearch;
        String periodo = request.getParameter("idPeriodo");
        String desde = request.getParameter("desde");
        String hasta = request.getParameter("hasta");
        Date fDesde = null;
        Date fHasta = null;
        int anioActivo = periodoService.obtenerPeriodoActivo().getAnio();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            if (desde != null && desde.length() > 0) {
                desde += anioActivo;
                fDesde = sdf.parse(desde);
            }
            if (hasta != null && hasta.length() > 0) {
                hasta += anioActivo;
                fHasta = sdf.parse(hasta);
            }
        } catch (ParseException parseException) {
            java.util.logging.Logger.getLogger(ConsumoPromedioMenController.class.getName()).log(Level.SEVERE, null, parseException);
        }

        int idAlmacen = 0;
        String idAlString = request.getParameter("idAlmacen");
        if (idAlString != null && idAlString.length() > 0) {
            idAlmacen = Integer.parseInt(idAlString);
        }
        
        boolean allmonths = Boolean.parseBoolean(request.getParameter("allmonths"));
        if (allmonths) {
            fDesde = null;
            fHasta = null;
        }
        List<ConsumoPromedioMenDto> lista = cpmService.getConsumoPromedioMensual(idModulo, fDesde, fHasta, idAlmacen, search, range);
        List<ConsumoPromedioMenDto> data = obtenerData(lista);

        manager.setAaData(data);
        manager.setsEcho(params.sEcho);
        int total = lista.size();
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);

        //<editor-fold defaultstate="collapsed" desc="Guardo en session los datos por si se quieren imprimir">
        HttpSession session = request.getSession();
        session.setAttribute("farmacotecnia_consumo_print_data", null);
        HashMap<String, Object> printData = new HashMap<String, Object>();
        printData.put("data", data);
        printData.put("desde", fDesde);
        printData.put("hasta", fHasta);
        printData.put("periodo", periodo);
        printData.put("idAlmacen", idAlmacen);
        printData.put("allmonths", allmonths);
        session.setAttribute("farmacotecnia_consumo_print_data", printData);
//</editor-fold>

        return manager;
    }

    private List<ConsumoPromedioMenDto> obtenerData(List<ConsumoPromedioMenDto> mps) {
        List<ConsumoPromedioMenDto> data = new ArrayList<ConsumoPromedioMenDto>();
        for (ConsumoPromedioMenDto theDto : mps) {
            if (!data.contains(theDto)) {
                theDto.procesar();
                data.add(theDto);
            } else {
                int i = data.indexOf(theDto);
                ConsumoPromedioMenDto dto = data.get(i);
                dto.procesar(theDto);
            }
        }
        return data;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        HashMap<String, Object> printData = (HashMap<String, Object>) request.getSession().getAttribute("farmacotecnia_consumo_print_data");
        List<ConsumoPromedioMenDto> data = new ArrayList<ConsumoPromedioMenDto>();
        if (printData != null) {
            data = (List<ConsumoPromedioMenDto>) printData.get("data");
        }
        String almacen = "TODOS";
        String meses = "Todos los meses";
        String periodo = "";

        if (printData != null) {
            int idAlmacen = (Integer) printData.get("idAlmacen");
            if (idAlmacen > 0) {
                GpAlmacen gpAlmacen = almacenService.obtenerPorId(idAlmacen);
                almacen = gpAlmacen.getDescripcion();
            }
            Date fDesde = (Date) printData.get("desde");
            Date fHasta = (Date) printData.get("hasta");
            boolean allmonths = (Boolean) printData.get("allmonths");
            if (!allmonths) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMMM", Locale.forLanguageTag("ES-es"));
                meses = sdf.format(fDesde).toUpperCase();
                meses += " - " + sdf.format(fHasta).toUpperCase();
            }
            String idPeriodo = (String) printData.get("periodo");
            if (idPeriodo != null && idPeriodo.length() > 0) {
                Periodo p = new Periodo();
                p.setIdPeriodo(Integer.parseInt(idPeriodo));
                periodo = p.getNombreMes().toUpperCase() + " - " + p.getAnio();
            }
        }

        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 50);
        headerColumns.put(1, 50);
        headerData.put("Almacen", almacen);
        headerData.put("Meses", meses);
        headerData.put("Periodo", periodo);

        List<String> contentLabels = Arrays.asList("Código", "Descripción", "En", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ag", "Sep", "Oct", "Nov", "Dic", "Promedio", "Saldo Actual");
        List<String> contentFields = Arrays.asList("codigo", "descripcion", "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre", "promedio", "saldoActual");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 5);
        contentColumns.put(1, 8);
        contentColumns.put(2, 5);
        contentColumns.put(3, 5);
        contentColumns.put(4, 5);
        contentColumns.put(5, 5);
        contentColumns.put(6, 5);
        contentColumns.put(7, 5);
        contentColumns.put(8, 5);
        contentColumns.put(9, 5);
        contentColumns.put(10, 5);
        contentColumns.put(11, 5);
        contentColumns.put(12, 5);
        contentColumns.put(13, 5);
        contentColumns.put(14, 5);
        contentColumns.put(15, 5);

        ModelAndView model = new ModelAndView(viewName);
        model.addObject("Title", "Consumo Promedio Mensual");
        model.addObject("ContentData", data);
        model.addObject("ContentLabels", contentLabels);
        model.addObject("ContentFields", contentFields);
        model.addObject("ContentColumns", contentColumns);
        model.addObject("HeaderData", new HashMap[]{headerData});
        model.addObject("HeaderColumns", headerColumns);
        return model;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "ConsumoPromedioMensualPDF");
        return model;
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        ModelAndView model = configReport(request, idModulo, "ConsumoPromedioMensualExcel");
        return model;
    }
}
