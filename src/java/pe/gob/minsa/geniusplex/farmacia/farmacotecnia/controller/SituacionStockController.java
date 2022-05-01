/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.minsa.geniusplex.farmacia.farmacotecnia.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
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
import pe.gob.minsa.farmacia.util.DataTablesParam;
import pe.gob.minsa.farmacia.util.DataTablesParamUtility;
import pe.gob.minsa.farmacia.util.ManagerDatatables;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.domain.dtos.StockDto;
import pe.gob.minsa.geniusplex.farmacia.farmacotecnia.service.impl.SituacionStockService;
import pe.gob.minsa.geniusplex.farmacia.gf.domain.GpAlmacen;
import pe.gob.minsa.geniusplex.farmacia.gf.services.impl.GpAlmacenService;
import pe.gob.minsa.geniusplex.web.WebUtil;

/**
 *
 * @author armando
 */
@Controller
@RequestMapping("/{idModulo}/situacionstock/*")
public class SituacionStockController {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GpAlmacenService almacenService;
    @Autowired
    private SituacionStockService stockService;

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public ModelAndView consultar(@PathVariable long idModulo, HttpServletRequest request, ModelAndView model) {
        Usuario usuario = WebUtil.obtenerUsuarioEnSesion(request);
        if (usuario != null && usuario.getIdUsuario() > 0) {
            model.addObject("anios", periodoService.listarAnios());
            model.addObject("almacenes", almacenService.listarPorModulo(idModulo));
            model.setViewName("SituacionStockFarmacotecnia/consultar");
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
        
        int[] range = new int[]{
            iDisplayStart,
            iDisplayLength
        };
        
        String search = params.sSearch;
        String desde = request.getParameter("fechaDesde");
        String hasta = request.getParameter("fechaHasta");
        Date fDesde = null;
        Date fHasta = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (desde != null && desde.length() > 0) {
                fDesde = sdf.parse(desde);
            }
            if (hasta != null && hasta.length() > 0) {
                fHasta = sdf.parse(hasta);
            }
        } catch (ParseException parseException) {
            java.util.logging.Logger.getLogger(SituacionStockController.class.getName()).log(Level.SEVERE, null, parseException);
        }

        int idAlmacen = 0;
        String idAlString = request.getParameter("idAlmacen");
        if (idAlString != null && idAlString.length() > 0) {
            idAlmacen = Integer.parseInt(idAlString);
        }
        
        List<StockDto> lista = stockService.obtenerSituacionStock(idAlmacen, search, fDesde, fHasta, range);

        manager.setAaData(lista);
        manager.setsEcho(params.sEcho);
        int total = lista.size();
        manager.setiTotalRecords(total);
        manager.setiTotalDisplayRecords(total);

        //<editor-fold defaultstate="collapsed" desc="Guardo en session los datos por si se quieren imprimir">
        HttpSession session = request.getSession();
        HashMap<String, Object> printData = new HashMap<String, Object>();
        printData.put("data", lista);
        printData.put("desde", fDesde);
        printData.put("hasta", fHasta);
        printData.put("idAlmacen", idAlmacen);
//        printData.put("tipoProducto", tipoProducto);
        session.setAttribute("farmacotecnia_stock_print_data", printData);
//</editor-fold>

        return manager;
    }

    private ModelAndView configReport(HttpServletRequest request, long idModulo, String viewName) {
        HashMap<String, Object> printData = (HashMap<String, Object>) request.getSession().getAttribute("farmacotecnia_stock_print_data");
        List<StockDto> data = new ArrayList<StockDto>();

        if (printData != null) {
            data = (List<StockDto>) printData.get("data");
        }
        String almacen = "TODOS";
        String meses = "Todos los meses";
        String periodo = "";
//        String tipoProducto = "TODOS";

        if (printData != null) {
            int idAlmacen = (Integer) printData.get("idAlmacen");
            if (idAlmacen > 0) {
                GpAlmacen gpAlmacen = almacenService.obtenerPorId(idAlmacen);
                almacen = gpAlmacen.getDescripcion();
            }
            Date fDesde = (Date) printData.get("desde");
            Date fHasta = (Date) printData.get("hasta");
            if (fDesde != null && fHasta != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("ES-es"));
                meses = sdf.format(fDesde).toUpperCase() + " - " + sdf.format(fHasta).toUpperCase();
            }
        }

        HashMap<String, String> headerData = new HashMap<String, String>();
        HashMap<Integer, Integer> headerColumns = new HashMap<Integer, Integer>();
        headerColumns.put(0, 100);
//        headerColumns.put(1, 50);
        headerData.put("Almacen", almacen);
        headerData.put("Rango de Fechas", meses);
//        headerData.put("Tipo de Producto", tipoProducto);

        List<String> contentLabels = Arrays.asList("Código", "Insumo", "Unidad de Medida", "Ingreso", "Stock", "Precio");
        List<String> contentFields = Arrays.asList("id", "nombre", "unidad:nombreUnidadMedida", "ingreso", "stock", "precio");
        HashMap<Integer, Integer> contentColumns = new HashMap<Integer, Integer>();
        contentColumns.put(0, 16);
        contentColumns.put(1, 17);
        contentColumns.put(2, 16);
        contentColumns.put(3, 16);
        contentColumns.put(4, 16);
        contentColumns.put(5, 16);

        ModelAndView model = new ModelAndView(viewName);
        model.addObject("Title", "Situación de Stock");
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
