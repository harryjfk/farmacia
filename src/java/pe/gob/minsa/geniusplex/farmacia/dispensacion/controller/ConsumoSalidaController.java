package pe.gob.minsa.geniusplex.farmacia.dispensacion.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.domain.Venta;
import pe.gob.minsa.geniusplex.farmacia.dispensacion.services.impl.VentaService;
import pe.gob.minsa.geniusplex.web.Consumo;

@Controller
@RequestMapping("/{idModulo}/consumoSalida/*")
public class ConsumoSalidaController {

    @Autowired
    private VentaService ventaService;

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String listarClientes(Model model) {

        model.addAttribute("viewTitle", "Consulta de Consumo por Tipo Salida");
        model.addAttribute("tableHeaders", Arrays.asList(new String[]{"Cód", "Prod", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "Total", "Precio", "Importe"}));
        model.addAttribute("ajaxList", "");
        model.addAttribute("editUrl", "");
        model.addAttribute("changeUrl", "");
        model.addAttribute("removeUrl", "");
        model.addAttribute("tableProperties", "");
        model.addAttribute("findItem", "");
        return "ConsumoSalida/consulta";
    }

    @RequestMapping(value = "getConsulta", method = RequestMethod.GET)
    public @ResponseBody
    List<Consumo> getConsulta(@PathVariable long idModulo, HttpServletRequest request) {
        return getConsultaData(request, idModulo);
    }

    private List<Consumo> getConsultaData(HttpServletRequest request, long idModulo) throws NumberFormatException {
        long tipoPago = Long.parseLong(request.getParameter("tipoPago"));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = null;
        Date end = null;
        try {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if (startDate != null && startDate.length() > 0) {
                start = format.parse(startDate);
            }
            if (endDate != null && endDate.length() > 0) {
                end = format.parse(endDate);
            }

        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(ConsumoSalidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Venta> lista = ventaService.ConsultaConsumoSalida(idModulo, tipoPago, start, end);
        HashMap<String, List<Venta>> monthMap = splitInMonth(lista, start, end);
        List<Consumo> consumos = new ArrayList<Consumo>();

        for (Map.Entry<String, List<Venta>> entrySet : monthMap.entrySet()) {
            String monthName = entrySet.getKey();
            List<Venta> ventas = entrySet.getValue();

            Consumo consumo = new Consumo(monthName);
            for (Venta venta : ventas) {
                consumo.AddVenta(venta);
            }

            consumos.add(consumo);
        }

        return consumos;
    }

    @RequestMapping(value = "reportePdf", method = RequestMethod.GET)
    public ModelAndView reportePdf(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Consumo> consumo = getConsultaData(request, idModulo);
        return new ModelAndView("ConsumoPacientePDF", "Data", consumo);
    }

    @RequestMapping(value = "reporteExcel", method = RequestMethod.GET)
    public ModelAndView reporteExcel(HttpServletRequest request, HttpServletResponse response, @PathVariable long idModulo) {
        List<Consumo> consumo = getConsultaData(request, idModulo);
        return new ModelAndView("ConsumoPacienteExcel", "Data", consumo);
    }

    private HashMap<String, List<Venta>> splitInMonth(List<Venta> ventas, Date startDate, Date endDate) {
        HashMap<String, List<Venta>> monthMap = new LinkedHashMap<String, List<Venta>>();

        GregorianCalendar startCalendar = new GregorianCalendar();
        GregorianCalendar endCalendar = new GregorianCalendar();

        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        int monthCount = (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)) * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        int length = startCalendar.get(Calendar.MONTH) + monthCount;

        for (int i = startCalendar.get(Calendar.MONTH); i <= length; i++) {
            String monthName = String.format(Locale.forLanguageTag("ES"), "%tB", startCalendar);
            monthName += String.format("-%d-%d", startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.YEAR));
            monthMap.put(monthName, new ArrayList<Venta>());
            startCalendar.add(Calendar.MONTH, 1);
        }

        for (Venta venta : ventas) {
            Date ventafechaRegistro = venta.getVentafechaRegistro();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(ventafechaRegistro);
            String monthName = String.format(Locale.forLanguageTag("ES"), "%tB", gc);
            monthName += String.format("-%d-%d", gc.get(Calendar.MONTH), gc.get(Calendar.YEAR));
            if (monthMap.containsKey(monthName)) {
                monthMap.get(monthName).add(venta);
            } else {
                List<Venta> l = new ArrayList<Venta>();
                l.add(venta);
                monthMap.put(monthName, l);
            }
        }

        return monthMap;
    }

    String getMonthFromInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

}
